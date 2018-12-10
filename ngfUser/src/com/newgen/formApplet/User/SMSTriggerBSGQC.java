package com.newgen.formApplet.User;

import com.newgen.formApplet.*;
import com.newgen.formApplet.User.*;
import netscape.javascript.*;
import com.newgen.formApplet.User.NGEjbCalls;

//Import this package for using JSobject
import netscape.javascript.*;
import org.apache.commons.httpclient.*;
import java.applet.*;

//import org.apache.commons.httpclient.methods.GetMethod;
import java.io.*;
import java.net.*;

import java.text.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import netscape.javascript.JSObject;


class SMSTriggerBSGQC{

String inXml="";
String outXml="";

String params="";
String  const1="95";
String  bank = "ICICILombrd";
String  mobNo="";
String SMSContent="Thank you for your interest in Icici Lombard health insurance. We have received your proposal for Health Insurance. We will get back to you on the status of your proposal";
String IHealthCareIntimation ="IHealthCareIntimation";

NGEjbCalls ngEjbCalls = null;
XMLParser objSMSTrigger = null;
	private NGFPropInterface formObject = null;
	public SMSTriggerBSGQC(NGFPropInterface formObject)//formObject Mandatory
		{
			this.formObject = formObject;
		}

		
	public int smsTrigger()
	{   
	    // "'"+const1+"','"+bank+"','"+mobNo+"','"+SMSContent+"','"+IHealthCareIntimation+"'",
		
		System.out.println("Input Xmltest ::"+params);
		try
		{
			ngEjbCalls = new NGEjbCalls(formObject);
			this.objSMSTrigger = new XMLParser();
			mobNo= formObject.getNGValue("ICICILOMBARD_HT_CUSTOMERNUMBER");
			params="'"+const1+"','"+bank+"','"+mobNo+"','"+SMSContent+"','"+IHealthCareIntimation+"'";
			inXml= ngEjbCalls.callProcedureInputXML("SP_RealTimeAlertsQueue",params,"5");
			System.out.println("Input Xml ::"+inXml);
			outXml= ngEjbCalls.executeXmlGeneric(inXml);
			System.out.println("outXml Xml ::"+outXml);
			objSMSTrigger.setInputXML(outXml);
			
			//if(outXml.equals("") || Integer.parseInt(outXml.substring(outXml.indexOf("<MainCode>")+10 , outXml.indexOf("</MainCode>")))!=0)
			//{
			//	JOptionPane.showMessageDialog(null," SMS can not be sent to the Customer ..!!" );
			//	return 0;
			//}
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		finally
		{
					return 1;
		}	


	}	
	
	
}