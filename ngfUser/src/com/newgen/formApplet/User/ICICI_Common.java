//----------------------------------------------------------------------------------------------------
//		NEWGEN SOFTWARE TECHNOLOGIES LIMITED
//Group						: APPPROJ
//Product / Project			: LnS
//Module					: NGFUSER
//File Name					: ICICI_Common.java
//Author					: Tarit Gupta
//Date written (DD/MM/YYYY)	: 23/06/2009
//Description				: Common function.
//----------------------------------------------------------------------------------------------------
//			CHANGE HISTORY
//----------------------------------------------------------------------------------------------------
// Date			 Change By	 Change Description (Bug No. (If Any))
// (DD/MM/YYYY)
//----------------------------------------------------------------------------------------------------
package com.newgen.formApplet.User;
import com.newgen.formApplet.FormApplet;
import com.newgen.formApplet.XMLParser;
import java.net.*;
import java.applet.*;
import java.util.*;
import java.io.*;
import java.lang.*;
import java.lang.String;
import java.lang.Character;
import java.lang.Number;
import java.lang.Float;
import java.text.SimpleDateFormat;
import java.awt.Color;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import netscape.javascript.*;
import javax.swing.*;
import netscape.javascript.JSObject;
import com.newgen.formApplet.Controls.InputVerifier.NGTextComponent;


// Referenced classes of package com.newgen.formApplet.User:
//            NGFUserAdapter

public class ICICI_Common
{

    private NGFPropInterface formObject = null;
	SimpleDateFormat dateformat=new SimpleDateFormat("dd/MM/yyyy");
	
	public ICICI_Common(NGFPropInterface pObj)
	{
		this.formObject = pObj;
	}
	 public String get_WMTestProcedure(String strParam, String procName,String sNoOfCol)
    {
       XMLParser generalDataParser = new XMLParser();
        generalDataParser.setInputXML(formObject.getWFGeneralData());
		String sEngineName = generalDataParser.getValueOf("EngineName"); 
		String sSessionId = generalDataParser.getValueOf("DMSSessionId");
	   
	   return "<?xml version=\"1.0\"?>" +
                "<WMTestProcedure_Input>" +
                "<Option>WFTestProcedure_new</Option>" +
                "<SessionId>"+sSessionId+"</SessionId>\n"+
                "<ProcName>"+procName+"</ProcName>\n" +
                "<Params>"+strParam+"</Params>"+
                "<EngineName>"+sEngineName+"</EngineName>" +
                "<NoOfCols>"+sNoOfCol+"</NoOfCols>" +
                "<WMTestProcedure_Input>";

    }
	public String WFUpdate_new(String strValues,String sWhere,String tableName, String columnName,String sProcessDefid) {
    
			XMLParser generalDataParser = new XMLParser();
			generalDataParser.setInputXML(formObject.getWFGeneralData());
			String sEngineName = generalDataParser.getValueOf("EngineName"); 
			String sSessionId = generalDataParser.getValueOf("DMSSessionId");

			return  "<?xml version=\"1.0\"?>"+
			"<WFUpdate_Input><Option>WFUpdate_new</Option>"+
			"<TableName>"+tableName+"</TableName>"+
			"<ColName>"+columnName+"</ColName>"+
			"<Values>"+strValues+"</Values>"+
			"<ProcessDefId>"+sProcessDefid+"</ProcessDefId>\n"+
			"<WhereClause>"+sWhere+"</WhereClause>"+
			"<EngineName>"+sEngineName+"</EngineName>"+
			"<SessionId>"+sSessionId+"</SessionId>"+
			"</WFUpdate_Input>";
		}
		public String WFInsert_new(String strValues,String tableName, String columnName,String sProcessDefid) {
    
			XMLParser generalDataParser = new XMLParser();
			generalDataParser.setInputXML(formObject.getWFGeneralData());
			String sEngineName = generalDataParser.getValueOf("EngineName"); 
			String sSessionId = generalDataParser.getValueOf("DMSSessionId");

			return  "<?xml version=\"1.0\"?>"+
			"<WFInsert_Input><Option>WFInsert_new</Option>"+
			"<TableName>"+tableName+"</TableName>"+
			"<ColName>"+columnName+"</ColName>"+
			"<Values>"+strValues+"</Values>"+
			"<ProcessDefId>"+sProcessDefid+"</ProcessDefId>\n"+
			"<EngineName>"+sEngineName+"</EngineName>"+
			"<SessionId>"+sSessionId+"</SessionId>"+
			"</WFInsert_Input>";
		}
  
	 public String WMTestSelect(String strQuery, String noofcol) 
	{
		
		XMLParser generalDataParser = new XMLParser();
        generalDataParser.setInputXML(formObject.getWFGeneralData());
		String sEngineName = generalDataParser.getValueOf("EngineName"); 
		String sSessionId = generalDataParser.getValueOf("DMSSessionId");
	//	//System.out.println("WMTestSelect:"+System.currentTimeMillis());
		return "<?xml version=\"1.0\"?>"+
			"<WMTestSelect_Input><Option>WFSelectTest_new</Option>"+
			"<sQry>"+strQuery+"</sQry>"+
			"<EngineName>"+sEngineName+"</EngineName>"+
			"<NoOfCols>"+noofcol+"</NoOfCols>"+
			"<SessionId>"+sSessionId+"</SessionId></WMTestSelect_Input>";
    }
	public String executeXmlGeneric(String sInputXML) 
	{
        XMLParser generalDataParser = new XMLParser();
        generalDataParser.setInputXML(formObject.getWFGeneralData());

        String serverUrl = generalDataParser.getValueOf("ServletPath"); 
		////System.out.println("serverUrl "+serverUrl);
        String ServletUrl = serverUrl + "/servlet/ExternalServlet";
        String sOutputXML = "";

		URL url = null;
		URLConnection urlConnection = null; 

        //Execute XML through the Servlet
        try 
		{
            
			//URL url = new URL(ServletUrl);
            //URLConnection urlConnection = url.openConnection();

			// Changed by Dinesh 16062010

			url = new URL(ServletUrl);
            urlConnection = url.openConnection();
			
            ////System.out.println(urlConnection instanceof HttpURLConnection);

            if (urlConnection instanceof HttpURLConnection) 
			{
				
                ((HttpURLConnection) urlConnection).setRequestMethod("POST");
				
            } 
			else 
			{
				
                throw new Exception("this connection is NOT an HttpUrlConnection connection");
				
            }

            //Connection.setUseCaches(false);
            //urlConnection.setDefaultUseCaches(false);
            urlConnection.setDoInput(true);
			
            urlConnection.setDoOutput(true);
		
            urlConnection.setRequestProperty("Content-Type",
                "application/octet-stream");
			

            //urlConnection.connect();

            //----------------------------------------------------------------------
            // send data to servlet
            //----------------------------------------------------------------------
            BufferedWriter os = new BufferedWriter(new OutputStreamWriter(
                        urlConnection.getOutputStream(), "UTF-8"));
			
            os.write(sInputXML, 0, sInputXML.length());
			
            os.close();
            os = null;

            //----------------------------------------------------------------------
            // read any response data, and store in a ByteArrayOutputStream
            //----------------------------------------------------------------------
            ByteArrayOutputStream baos = null;
            InputStream is = null;
            ////System.out.println("urlConnection.getInputStream()" +urlConnection.getInputStream());
            is = urlConnection.getInputStream();
			
            //System.out.println("IS   :" + is);
            //System.out.println((is) != null);

            if ((is) != null) 
			{
                baos = new ByteArrayOutputStream();

                byte[] ba = new byte[1024];

                while ((is.read(ba, 0, 1)) != (-1)) 
				{
                  				
					baos.write(ba, 0, 1);
                }

                baos.flush();
                is.close();
				
                sOutputXML = new String(baos.toByteArray(), "UTF-8");
				
            }
        } 
		catch (Exception e) 
		{
            e.printStackTrace();
        }
		finally
        {
            if (urlConnection != null && urlConnection instanceof HttpURLConnection)
            {
                ((HttpURLConnection) urlConnection).disconnect();
                urlConnection = null;
            }
        }
		
        return sOutputXML;
    }
   
}