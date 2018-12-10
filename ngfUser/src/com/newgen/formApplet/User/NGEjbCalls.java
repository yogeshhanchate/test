package com.newgen.formApplet.User;

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
import java.text.DateFormat;
import java.awt.Color;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import netscape.javascript.*;
import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import netscape.javascript.JSObject;
import java.text.ParseException;

import com.newgen.formApplet.*;
import com.newgen.formApplet.User.*;



public class NGEjbCalls
{

	/*--------------------------------------------------------------------------------------
	Description: Function for fetching data from database
	----------------------------------------------------------------------------------------*/
	NGFPropInterface formObject =null;
	FormApplet formApplet=null;
	AppletContext appletContext=null;
	String sEncoding = "UTF-8";
	String wsName=null; 
	String processName=null;
	String sEngineName=null;
	String sSessionId=null;
	String serverUrl=null;
	String sJTSIP=null;
	String sJTSPORT=null;
	String ServletUrl=null;
	String sProcessDefId=null;
	String wiName=null;
	WFXmlResponse xmlResponse=null;
	WFXmlList RecordList=null;
	XMLParser generalDataParser = new XMLParser();
	String sMainCode="";
	String sResult="";
	int debug=0;  // for enabling/disabling the debug mode.. 0-disable; 1-enable

	//setting the form object in the constructor; mandatory 
	public NGEjbCalls(NGFPropInterface formObject)
	{
		this.formObject=formObject;
	}
	
	public void setAppletObject(Object appletObject) //mandatory if the file is used as an applet
	{
		formApplet = (FormApplet)appletObject;
		appletContext = formApplet.getAppletContext();		
		if (formApplet.getParameter("ENCODING") != null && !formApplet.getParameter("ENCODING").equals(""))
		{
			sEncoding = formApplet.getParameter("ENCODING");
		}
	}
	
	//method to get the input xml for select query	
	public String callSelectInputXML(String strQuery,String noofcol)
	{
		try
		{
			//setting the form parameters
			generalDataParser.setInputXML(formObject.getWFGeneralData());
			wsName = generalDataParser.getValueOf("ActivityName").toUpperCase();
			processName=generalDataParser.getValueOf("ProcessName").toUpperCase();
			sEngineName = generalDataParser.getValueOf("EngineName"); 
			sSessionId = generalDataParser.getValueOf("DMSSessionId"); 
			serverUrl = generalDataParser.getValueOf("ServletPath") ;
			sJTSIP = generalDataParser.getValueOf("JTSIP") ;
			sJTSPORT = generalDataParser.getValueOf("JTSPORT") ;
			ServletUrl = serverUrl + "/servlet/ExternalServlet?JTSIP="+sJTSIP+"&JTSPORT="+sJTSPORT;
			sProcessDefId = generalDataParser.getValueOf("ProcessDefId");
			wiName = generalDataParser.getValueOf("ProcessInstanceId").toUpperCase();
			if(debug==1)
			{
				//// System.out.println("sEngineName-------"+sEngineName);
			}
			return "<?xml version=\"1.0\"?>"+
			"<WMTestSelect_Input><Option>WFSelectTest_new</Option>"+
			"<sQry>"+strQuery+"</sQry>"+ 
			"<EngineName>"+sEngineName+"</EngineName>"+ 
			"<NoOfCols>"+noofcol+"</NoOfCols>"+ 
			"<SessionId>"+sSessionId+"</SessionId>"+
			"</WMTestSelect_Input>";
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "error";
		}
	}

	/*--------------------------------------------------------------------------------------
	Description: method to get the input xml for update query	
	----------------------------------------------------------------------------------------*/	
	
	public String callUpdateInputXML(String sTable,String sCols,String sValues,String sWhereClause) 
	{
		try
		{		
			generalDataParser.setInputXML(formObject.getWFGeneralData());
			sEngineName = generalDataParser.getValueOf("EngineName"); 
			sSessionId = generalDataParser.getValueOf("DMSSessionId");
			sProcessDefId = generalDataParser.getValueOf("ProcessDefId");
			return "<?xml version=\"1.0\"?>"+
			"<WFUpdate_Input><Option>WFUpdate_new</Option>"+
			"<TableName>" + sTable + "</TableName>" + 
			"<ColName>" + sCols + "</ColName>" + 
			"<Values>" + sValues + "</Values>" + 
			"<ProcessDefId>" + sProcessDefId + "</ProcessDefId>\n" + 
			"<WhereClause>" + sWhereClause + "</WhereClause>" + 
			"<EngineName>" + sEngineName + "</EngineName>" + 
			"<SessionId>" + sSessionId + "</SessionId>" + "</WFUpdate_Input>";
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "error";
		}
   }
   
   
   /*--------------------------------------------------------------------------------------
	Description: method to get the input xml for execute update query	
	----------------------------------------------------------------------------------------*/	
	
	public String callIGSetDataInputXML(String sbQry) 
	{
		String sInputXml ="";
		try
		{		
			generalDataParser.setInputXML(formObject.getWFGeneralData());
			sEngineName = generalDataParser.getValueOf("EngineName"); 
			sSessionId = generalDataParser.getValueOf("DMSSessionId");
			sProcessDefId = generalDataParser.getValueOf("ProcessDefId");
			sInputXml = "<?xml version=\"1.0\"?>";
            sInputXml = sInputXml + "<WFCustomBean_Input><Option>IGSetData</Option>";
            sInputXml = sInputXml + "<QryOption>IGSetData</QryOption>";
            sInputXml = sInputXml + "<EngineName>" + sEngineName + "</EngineName>";
            sInputXml = sInputXml + "<SessionId>" + sSessionId + "</SessionId>";
            sInputXml = sInputXml + "<Query>" + sbQry.toString() + "</Query>";
            sInputXml = sInputXml + "<QueryCount>1</QueryCount>";
            sInputXml = sInputXml + "</WFCustomBean_Input>";
			
			return sInputXml;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "error";
		}
   }
  
	/*--------------------------------------------------------------------------------------
	Description: method to get the input xml for insert query	
	----------------------------------------------------------------------------------------*/	   
	
	public String callInsertInputXML(String sTable,String sCols,String sValues) 
	{
		try
		{
			generalDataParser.setInputXML(formObject.getWFGeneralData());
			String sEngineName = generalDataParser.getValueOf("EngineName"); 
			String sSessionId = generalDataParser.getValueOf("DMSSessionId");
			sProcessDefId = generalDataParser.getValueOf("ProcessDefId");
			
			return "<?xml version=\"1.0\"?>"+
			"<WFInsert_Input><Option>WFInsert_new</Option>"+
			"<TableName>" + sTable + "</TableName>" + 
			"<ColName>" + sCols + "</ColName>" + 
			"<Values>" + sValues + "</Values>" + 
			"<ProcessDefId>" + sProcessDefId + "</ProcessDefId>\n" + 
			"<EngineName>" + sEngineName + "</EngineName>" + 
			"<SessionId>" + sSessionId + "</SessionId>" + 
			"</WFInsert_Input>";		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "error";
		}
	}
   
	/*--------------------------------------------------------------------------------------
	Description: Function for executing a stored procedure
	----------------------------------------------------------------------------------------*/
	public String callProcedureInputXML(String sProcName,String sParams,String sCols) 
	{
		try
		{
			generalDataParser.setInputXML(formObject.getWFGeneralData());
			String sEngineName = generalDataParser.getValueOf("EngineName"); 
			String sSessionId = generalDataParser.getValueOf("DMSSessionId"); 
			
			return "<?xml version=\"1.0\"?>"+
			 "<WMTestProcedure_Input><Option>WFTestProcedure_new</Option>"+
			 "<SessionId>" + sSessionId + "</SessionId>\n" + 
			 "<ProcName>" + sProcName + "</ProcName>\n" + 
			 "<Params>" + sParams + "</Params>" + 
			 "<EngineName>" + sEngineName + "</EngineName>" + 
			 "<NoOfCols>" + sCols + "</NoOfCols>" + "<WMTestProcedure_Input>";
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "error";
		}
	}
	/*-----------------------------------------------------------------------------
	  Wfcustom_ejb
	---------------------------------------------------------------------------*/
	public String getExtServletURL(String serverUrl)
	{
		String strAppServerIp = "10.24.155.135";
		String strAppServerPort = "3333";
		String strAppServerType = "WebSphere";
		String strEngineName = "omniflow";
		String strServletParam = "?JTSIP=" + strAppServerIp + "&JTSPORT=" + strAppServerPort;
        StringBuffer sbURL = new StringBuffer(serverUrl);
        sbURL.append("/servlet/ExternalServlet");
        sbURL.append(strServletParam);
        sbURL.append("&EngineName=");
        sbURL.append(strEngineName);
        sbURL.append("&AppServerType=");
        sbURL.append(strAppServerType);
        return sbURL.toString();
    }
	
	public String executeXmlGenerictest(String sInputXML)
	{ 
	
	if(debug==1)
	{
		//// System.out.println("executeXmlGeneric:"+System.currentTimeMillis()); 
	}	
	//XMLParser generalDataParser = new XMLParser(); 
	generalDataParser.setInputXML(formObject.getWFGeneralData()); 
	String serverUrl = generalDataParser.getValueOf("ServletPath") ; 
	//String ServletUrl = serverUrl + "/servlet/ExternalServlet";  
	String ServletUrl = getExtServletURL(serverUrl);
	//// System.out.println("ServletUrl===  "+ServletUrl);
	//ServletUrl sample eg: http://10.24.155.135:9082/webdesktop/servlet/ExternalServlet
	
	String sOutputXML = ""; 
	//Execute XML through the Servlet 
	try
	{  
		URL url = new URL(ServletUrl);  
		URLConnection urlConnection = url.openConnection();  
		if (urlConnection instanceof HttpURLConnection)  
		{   
		((HttpURLConnection)urlConnection).setRequestMethod("POST");  
		}  
		else  
		{   
		throw new Exception("this connection is NOT an HttpUrlConnection connection");  
		}  
		urlConnection.setDoInput(true);  
		urlConnection.setDoOutput(true);  
		urlConnection.setRequestProperty("Content-Type", "application/octet-stream"); 
		 // send data to servlet    
		 BufferedWriter os = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(),"UTF-8")); 
		 os.write(sInputXML, 0, sInputXML.length());
		 os.close();  os = null;    
		 // read any response data, and store in a ByteArrayOutputStream  
		 ByteArrayOutputStream baos = null;  
		 InputStream is = null;  
		 if ((is = urlConnection.getInputStream())!=null)  
		 {   
		 baos = new ByteArrayOutputStream();   
		 byte ba [] = new byte[1];      
		 while ((is.read(ba,0,1)) != (-1))   
		 baos.write(ba,0,1);      
		 baos.flush();   
		 is.close();  
		 sOutputXML =  new String(baos.toByteArray(), "UTF-8");  
		 } 
	 }
	 catch (Exception e) 
	 { 
		e.printStackTrace(); 
	}
		if(debug==1)
		{
			//// System.out.println("executeXmlGeneric:"+System.currentTimeMillis());
		}
	 return sOutputXML; 
 }
	/*-----------------------------------------------------------------------------
	  end Wfcustom_ejb
	---------------------------------------------------------------------------*/
	
	/*--------------------------------------------------------------------------------------
	Description: Function for generating output xml
	
	----------------------------------------------------------------------------------------*/	   
	
	public String executeXmlGeneric(String sInputXML)
	{ 
	
	if(debug==1)
	{
		//// System.out.println("executeXmlGeneric:"+System.currentTimeMillis()); 
	}	
	//XMLParser generalDataParser = new XMLParser(); 
	generalDataParser.setInputXML(formObject.getWFGeneralData()); 
	String serverUrl = generalDataParser.getValueOf("ServletPath") ; 
	String ServletUrl = serverUrl + "/servlet/ExternalServlet";  
	//String ServletUrl = getExtServletURL(serverUrl);
	//// System.out.println("ServletUrl===  "+ServletUrl);
	//ServletUrl sample eg: http://10.24.155.135:9082/webdesktop/servlet/ExternalServlet
	
	String sOutputXML = ""; 
	//Execute XML through the Servlet 
	try
	{  
		URL url = new URL(ServletUrl);  
		URLConnection urlConnection = url.openConnection();  
		if (urlConnection instanceof HttpURLConnection)  
		{   
		((HttpURLConnection)urlConnection).setRequestMethod("POST");  
		}  
		else  
		{   
		throw new Exception("this connection is NOT an HttpUrlConnection connection");  
		}  
		urlConnection.setDoInput(true);  
		urlConnection.setDoOutput(true);  
		urlConnection.setRequestProperty("Content-Type", "application/octet-stream"); 
		 // send data to servlet    
		 BufferedWriter os = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(),"UTF-8")); 
		 os.write(sInputXML, 0, sInputXML.length());
		 os.close();  os = null;    
		 // read any response data, and store in a ByteArrayOutputStream  
		 ByteArrayOutputStream baos = null;  
		 InputStream is = null;  
		 if ((is = urlConnection.getInputStream())!=null)  
		 {   
		 baos = new ByteArrayOutputStream();   
		 byte ba [] = new byte[1];      
		 while ((is.read(ba,0,1)) != (-1))   
		 baos.write(ba,0,1);      
		 baos.flush();   
		 is.close();  
		 sOutputXML =  new String(baos.toByteArray(), "UTF-8");  
		 } 
	 }
	 catch (Exception e) 
	 { 
		e.printStackTrace(); 
	}
		if(debug==1)
		{
			//// System.out.println("executeXmlGeneric:"+System.currentTimeMillis());
		}
	 return sOutputXML; 
 }
	/*--------------------------------------------------------------------------------------
	Description: Function for parsing the output Xml
	----------------------------------------------------------------------------------------*/	   
	
	public String xmlParse(String outputXml,String[] arrfieldName,String noOfCols,boolean flag)
	{
		String sResult1=null;
		try
		{

			//check if there is any problem in the result returned or no rows found
			if(outputXml.equals("") || Integer.parseInt(outputXml.substring(outputXml.indexOf("<MainCode>")+10 , outputXml.indexOf("</MainCode>")))!=0)
			{
				////// System.out.println("Error in Output xml ");
				JOptionPane.showMessageDialog(null,"No results found !!");
			}
			else
			{
				generalDataParser.setInputXML(outputXml);
				//checking for no rows found
				//formObject.NGAddItem(arrfieldName[count],"--Select--");
				if (!(outputXml.substring(outputXml.indexOf("<Results>") + 9, outputXml.indexOf("</Results>")).equals("")))
				{
					//// System.out.println("flag===="+flag);
					xmlResponse = new WFXmlResponse(outputXml);
					//checking the no of cols and processing them seperaetly
					if(noOfCols.equals("1"))
					{
						//// System.out.println("noOfCols===="+noOfCols);
						 if(arrfieldName[0].equalsIgnoreCase("ICICILOMBARD_HT_INTRODUCED_BY_EMAIL") || arrfieldName[0].equalsIgnoreCase("ICICILOMBARD_HT_DEAL_IL_LOCATION")/*|| arrfieldName[0].equalsIgnoreCase("ICICILOMBARD_HT_SUB_VERTICAL") || arrfieldName[0].equalsIgnoreCase("ICICILOMBARD_HT_VERTICAL")//commented for PID-HT process changes*/)
						 /***** CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket*******/
						{
							for (RecordList = xmlResponse.createList("Results", "Result"); RecordList.hasMoreElements(); RecordList.skip())
							{
								sResult = RecordList.getVal("Result");
								//// System.out.println("sResult for vertical & non vertical===="+sResult);
								//get the actual result
								sResult=sResult.substring(1);
								//adding the result in the required control
								formObject.setNGValue(arrfieldName[0],sResult);
							}
						} 
						else
						{
							formObject.NGAddItem(arrfieldName[0],"--Select--");
							for (RecordList = xmlResponse.createList("Results", "Result"); RecordList.hasMoreElements(); RecordList.skip())
							{
								sResult = RecordList.getVal("Result");
								////// System.out.println("sResult1===="+sResult);
								//get the actual result
								sResult=sResult.substring(1);
								////// System.out.println("sResult2===="+sResult);
								//adding the result in the required control
								formObject.NGAddItem(arrfieldName[0],sResult);
							}
						}
						
					}/************************ Start CR-OMHT-1314-01 Wallet_Insurance************/
					else if(noOfCols.equals("2") && (arrfieldName[0].equalsIgnoreCase("ICICILOMBARD_HT_IL_LOCATION") || arrfieldName[0].equalsIgnoreCase("ICICILOMBARD_HT_SUB_VERTICAL") || arrfieldName[0].equalsIgnoreCase("ICICILOMBARD_HT_VERTICAL")))//added verticals for PID-HT process changes
					{
						for (RecordList = xmlResponse.createList("Results", "Result");RecordList.hasMoreElements();RecordList.skip())
						{
							sResult=RecordList.getVal("Result");
							//get the actual result
							sResult1=sResult.substring(1,sResult.length());
							//split the result by the seperator and get the values seperately
							String strArr[] = sResult1.split("\\|");
							//// System.out.println("strArr.length===="+strArr.length);
							formObject.setNGValue(arrfieldName[0],strArr[0]);
							formObject.setNGValue(arrfieldName[1],strArr[1]);						
						}						
					} /************************ End CR-OMHT-1314-01 Wallet_Insurance************/
					else
					{
						//// System.out.println(" else noOfCols===="+noOfCols);
						for(int count=0;count<arrfieldName.length;count++)
						{
							formObject.NGAddItem(arrfieldName[count],"--Select--");
						}	
						for (RecordList = xmlResponse.createList("Results", "Result");RecordList.hasMoreElements();RecordList.skip())
						{
							sResult=RecordList.getVal("Result");
							//get the actual result
							sResult1=sResult.substring(1,sResult.length());
							//split the result by the seperator and get the values seperately
							String strArr[] = sResult1.split("\\|");
							//// System.out.println("strArr.length===="+strArr.length);
							//adding values to the controls on the form
							if(!flag)
							{
								if(debug==1)
								{
								//	//// System.out.println("arrfieldName.length"+arrfieldName.length+"strArr.length"+strArr.length);
								}
								formObject.NGAddItem(arrfieldName[0],strArr[0],strArr[1]);
							}
							else
							{
								for (int count=0;count<arrfieldName.length;count++)
								{
									formObject.NGAddItem(arrfieldName[count],strArr[count]);
								}
								
							}		
						}
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null,"No results found !!");
				}	
			}
			return "1";	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "error";
		}	
	}

        /************************** CR-OF-MHT-1314-01 MHTProcess Implementaion Start***********/
        /**
         *  Method is used for MHT Process purpose
         */
	public String xmlMHTParse(String outputXml,String[] arrfieldName,String noOfCols,boolean flag)
	{
            String sResult1=null;
            try
            {
                //check if there is any problem in the result returned or no rows found
                if(outputXml.equals("") || Integer.parseInt(outputXml.substring(outputXml.indexOf("<MainCode>")+10 , outputXml.indexOf("</MainCode>")))!=0)
                {
                    ////// System.out.println("Error in Output xml ");
                    JOptionPane.showMessageDialog(null,"No results found !!");
                }
                else
                {
                    generalDataParser.setInputXML(outputXml);
                    //checking for no rows found
                    
                    if (!(outputXml.substring(outputXml.indexOf("<Results>") + 9, outputXml.indexOf("</Results>")).equals("")))
                    {
                        //// System.out.println("xmlMHTParse():===="+flag);
                        xmlResponse = new WFXmlResponse(outputXml);
                        //checking the no of cols and processing them seperaetly
                        if(noOfCols.equals("1"))
                        {
                            //// System.out.println("xmlMHTParse():noOfCols===="+noOfCols);
							//MHT-PID Process Integration -- Commented MHT_PRIMARY_VERTICAL,MHT_SUB_VERTICAL condition
							if(/*arrfieldName[0].equalsIgnoreCase("MHT_SUB_VERTICAL") || arrfieldName[0].equalsIgnoreCase("MHT_PRIMARY_VERTICAL") || */arrfieldName[0].equalsIgnoreCase("MHT_PRODUCT_NAME")||arrfieldName[0].equalsIgnoreCase("MHT_DEAL_IL_LOCATION"))
							{
								for (RecordList = xmlResponse.createList("Results", "Result"); RecordList.hasMoreElements(); RecordList.skip())
								{
									sResult = RecordList.getVal("Result");
									//// System.out.println("xmlMHTParse(): vertical,subvertical and product===="+sResult);
									//get the actual result
									sResult=sResult.substring(1);
									//adding the result in the required control
									formObject.setNGValue(arrfieldName[0],sResult);
								}
							}
							else
							{
								formObject.NGAddItem(arrfieldName[0],"--Select--");
								for (RecordList = xmlResponse.createList("Results", "Result"); RecordList.hasMoreElements(); RecordList.skip())
								{
									sResult = RecordList.getVal("Result");
									////// System.out.println("xmlMHTParse(): in else sResult1===="+sResult);
									//get the actual result
									sResult=sResult.substring(1);
									////// System.out.println("sResult2===="+sResult);
									//adding the result in the required control
									formObject.NGAddItem(arrfieldName[0],sResult);
								}
							}
                        }
						/************************ Start CR-OMHT-1314-01 Wallet_Insurance************/
						//MHT-PID Process Integration -- Added MHT_PRIMARY_VERTICAL,MHT_SUB_VERTICAL,SM_ID,SM_NAME condition
						// If Subvertical E CHANNEL AND FINANCIAL INSTITUTION GROUP THEN INTERCHAGE PRIMARY VERTICAL AND SUBVERTICAL VALUES
						else if(noOfCols.equals("2") && (arrfieldName[0].equalsIgnoreCase("MHT_IL_LOCATION") || arrfieldName[0].equalsIgnoreCase("MHT_SUB_VERTICAL") || arrfieldName[0].equalsIgnoreCase("MHT_PRIMARY_VERTICAL") || arrfieldName[0].equalsIgnoreCase("MHT_SM_NAME")))
						{
							for (RecordList = xmlResponse.createList("Results", "Result");RecordList.hasMoreElements();RecordList.skip())
							{
								sResult=RecordList.getVal("Result");
								//get the actual result
								sResult1=sResult.substring(1,sResult.length());
								//split the result by the seperator and get the values seperately
								String strArr[] = sResult1.split("\\|");
								//// System.out.println("strArr.length===="+strArr.length);
								// If Subvertical E CHANNEL AND FINANCIAL INSTITUTION GROUP THEN INTERCHAGE PRIMARY VERTICAL AND SUBVERTICAL VALUES
								if(arrfieldName[0].equalsIgnoreCase("MHT_SUB_VERTICAL") && strArr[0] != null && (strArr[0].equalsIgnoreCase("E CHANNEL")))
								{
									//// System.out.println("if E CHANNEL===="+strArr[0]);
									//// System.out.println("if E code===="+strArr[1]);
									formObject.setNGValue("MHT_PRIMARY_VERTICAL",strArr[0]);
									formObject.setNGValue("MHT_PRIMARY_VERTICAL_CODE",strArr[1]);
									formObject.setNGValue(arrfieldName[0],"null");
									formObject.setNGValue(arrfieldName[1],"102017000"); // set constant for Subvertical E CHANNEL AND FINANCIAL INSTITUTION GROUP
									
								}
								else
								{
									formObject.setNGValue(arrfieldName[0],strArr[0]);
									formObject.setNGValue(arrfieldName[1],strArr[1]);
								}
							}
						}
						// Comment for If Subvertical E CHANNEL AND FINANCIAL INSTITUTION GROUP THEN INTERCHAGE PRIMARY VERTICAL AND SUBVERTICAL VALUES
						/*else if(noOfCols.equals("3") && (arrfieldName[0].equalsIgnoreCase("MHT_PRIMARY_VERTICAL")))
						{
							for (RecordList = xmlResponse.createList("Results", "Result");RecordList.hasMoreElements();RecordList.skip())
							{
								//// System.out.println("strArr[1]():===="+arrfieldName[0]);
								sResult=RecordList.getVal("Result");
								//get the actual result
								sResult1=sResult.substring(1,sResult.length());
								//split the result by the seperator and get the values seperately
								String strArr[] = sResult1.split("\\|");
								//// System.out.println("strArr.length1 ===="+strArr.length);
								formObject.setNGValue(arrfieldName[0],strArr[0]);
								formObject.setNGValue(arrfieldName[1],strArr[1]);
								//// System.out.println("strArr[1] value  ===="+strArr[1]);
								if(strArr[1] == null || strArr[1].trim().equals("null") || strArr[1].equals(""))
								{
									//// System.out.println("strArr.length1 ===="+strArr.length);
									//// System.out.println("strArr[1]():===="+strArr[1]);
									//// System.out.println("strArr[2]():===="+strArr[2]);
									formObject.setNGValue(arrfieldName[1],strArr[2]); // set 
								}
							}

						} /************************ End CR-OMHT-1314-01 Wallet_Insurance************/
                        
						else
                        {
                            //// System.out.println(" else noOfCols===="+noOfCols);
                            for(int count=0;count<arrfieldName.length;count++)
                            {
                                    formObject.NGAddItem(arrfieldName[count],"--Select--");
                            }
                            for (RecordList = xmlResponse.createList("Results", "Result");RecordList.hasMoreElements();RecordList.skip())
                            {
                                sResult=RecordList.getVal("Result");
                                //get the actual result
                                sResult1=sResult.substring(1,sResult.length());
                                //split the result by the seperator and get the values seperately
                                String strArr[] = sResult1.split("\\|");
                                //// System.out.println("strArr.length===="+strArr.length);
                                //adding values to the controls on the form
                                if(!flag)
                                {
                                        if(debug==1)
                                        {
                                        //	//// System.out.println("arrfieldName.length"+arrfieldName.length+"strArr.length"+strArr.length);
                                        }
                                        formObject.NGAddItem(arrfieldName[0],strArr[0],strArr[1]);
                                }
                                else
                                {
                                        for (int count=0;count<arrfieldName.length;count++)
                                        {
                                                formObject.NGAddItem(arrfieldName[count],strArr[count]);
                                        }

                                }
                            }
                        }
                     }
                     else
                     {
                        JOptionPane.showMessageDialog(null,"No results found !!");
                     }
                 }
                    return "1";
            }
            catch(Exception e)
            {
                    e.printStackTrace();
                    return "error";
            }
	}
        /************************** CR-OF-MHT-1314-01 MHTProcess Implementaion End***********/
	
	
}