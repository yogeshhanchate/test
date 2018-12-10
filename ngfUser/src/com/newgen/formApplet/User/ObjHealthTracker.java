/**
* Group										 : Application Projects
* Project									 : App Proj1 
* Process Name								 : Icici HealthTracker Process
* Date Written (DD/MM/YYYY)                  : 15-April-2011
* Date Modified								 : 
* Date Modified								 : 
* Author									 : Vikesh Pandey
* Input Parameters							 : 
* Output Parameters                          : true/false
* Return Values                              : 
* Description                                : Process logic for Data Entry
* Change By                                  : 
* NOTE:	ICICILOMBARD_HT_VERTICAL represents the PrimaryVertical
*		ICICILOMBARD_HT_SUB_VERTICAL represents the SecondaryVertical
**/
package com.newgen.formApplet.User;

import com.newgen.formApplet.*;
import com.newgen.formApplet.User.*;
import com.newgen.formApplet.User.NGEjbCalls;
import netscape.javascript.*;
//Import this package for using JSobject
import netscape.javascript.JSObject;
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
import javax.swing.plaf.ColorUIResource;
import javax.swing.text.html.ListView;


public class ObjHealthTracker extends NGFUserAdapter
{
	FormApplet formApplet=null;
	AppletContext appletContext=null;
	String sEncoding = "UTF-8";
	private NGFPropInterface formObject = null;
	WFXmlResponse xmlResponse=null;
	WFXmlList RecordList=null;
	//declaring parameters for use by the process
	String wsName=null; 
	String wiName=null;
	//end here
	//declare local variables to this file
	String fieldName = ""; 
	String fieldValue = "";
	String cenDealStatus = ""; //CR-OMHT-1314-01-Wallet_Insurance

	String noOfCols="";
	String query="";
	String inputXml="";
	String outputXml="";
	String result= "";
	XMLParser generalDataParser = new XMLParser();
	NGEjbCalls ngEjbCalls;
	String[] arrfieldName;
	boolean flag;
	int iTemp1=0;
	int debug=0;  // for enabling/disabling the debug mode.. 0-disable; 1-enable
	int counter=0;
	int counter1=0;
	HashMap map = null; //CR Motor,Home & Travel processing

	/********************** Start Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR ***********************/
	JSObject js =null;
	String url =null;
	/********************** End Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR *************************/
		
NGFPortabilitySubForm ngfPortabilitySubForm =null;  //onkar


	
	//setting the applet object--mandatory
	public void setAppletObject(Object appletObject) //mandatory
	{
		formApplet = (FormApplet)appletObject;
		appletContext = formApplet.getAppletContext();		
		if (formApplet.getParameter("ENCODING") != null && !formApplet.getParameter("ENCODING").equals(""))
		{
			sEncoding = formApplet.getParameter("ENCODING");
		}
	}
	
	public ObjHealthTracker(NGFPropInterface formObject)//formObject Mandatory
	{
		this.formObject = formObject;		
	}
	 
	//This method will be executed on form load for every workstep
	public void executeHTOnload(XMLParser gDataParser)
	{
		try
		{
		
		//defining the parameters for setting the height, top , left, width etc of the frames
			int top=0;
			int topFrame=0;
			int left=0; 
			int formHeight=0;
			int width=1050;
			arrfieldName= new String[2];
			int iTemp=0;
			System.out.println("inside executeHTOnload");
			//String ttt="";
			//formObject.setNGValue("ICICILOMBARD_HT_PROCESSINSTANCEID");
			//ttt=formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL");
			//System.out.println("ttt"+ttt);
			generalDataParser.setInputXML(formObject.getWFGeneralData());
			wsName = generalDataParser.getValueOf("ActivityName").toUpperCase();
			wiName = generalDataParser.getValueOf("ProcessInstanceId").toUpperCase();

			//setting the frames on the form
			formObject.setNGControlTop("FRAME_BANNER",topFrame);
			formObject.setNGControlLeft("FRAME_BANNER",left);
			topFrame+=115;
			iTemp1=topFrame;
			formObject.setNGControlTop("FRAME_SEARCH_CRITERIA",topFrame);
			formObject.setNGControlLeft("FRAME_SEARCH_CRITERIA",left);
			topFrame+=186;
			formObject.setNGControlTop("FRAME_DATA_ENTRY_PARENT",topFrame);
			formObject.setNGControlLeft("FRAME_DATA_ENTRY_PARENT",left);
			
			/***** CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket*******/
			/*formObject.setNGControlTop("FRAME_SELECT_SUB_VERTICAL",top);
			formObject.setNGControlLeft("FRAME_SELECT_SUB_VERTICAL",left);
			formObject.setNGControlWidth("FRAME_SELECT_SUB_VERTICAL",width);
			top+=35;*/
			/***** End CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket***/
			formObject.setNGControlTop("FRAME_SELECT_VERTICAL",top);
			formObject.setNGControlLeft("FRAME_SELECT_VERTICAL",left);
			formObject.setNGControlWidth("FRAME_SELECT_VERTICAL",width);
			top+=34;
			/***** CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket*******/
			formObject.setNGControlTop("FRAME_SELECT_SUB_VERTICAL",top);
			formObject.setNGControlLeft("FRAME_SELECT_SUB_VERTICAL",left);
			formObject.setNGControlWidth("FRAME_SELECT_SUB_VERTICAL",width);
			top+=35;
			/***** End CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket***/
			formObject.setNGControlTop("FRAME_PRODUCT",top);
			formObject.setNGControlLeft("FRAME_PRODUCT",left);
			formObject.setNGControlWidth("FRAME_PRODUCT",width);
			top+=56;
			formObject.setNGControlTop("FRAME_PROD_ABBR_CODE",top);
			formObject.setNGControlLeft("FRAME_PROD_ABBR_CODE",left);
			formObject.setNGControlWidth("FRAME_PROD_ABBR_CODE",width);
			top+=30;
			formObject.setNGControlTop("FRAME_TXN_TYPE",top);
			formObject.setNGControlLeft("FRAME_TXN_TYPE",left);
			formObject.setNGControlWidth("FRAME_TXN_TYPE",width);
			top+=31;
			formObject.setNGControlTop("FRAME_PREV_POLICY_NUMBER",top);
			formObject.setNGControlLeft("FRAME_PREV_POLICY_NUMBER",left);
			formObject.setNGControlWidth("FRAME_PREV_POLICY_NUMBER",width);
			top+=32;
			formObject.setNGControlTop("FRAME_IAGENT_PROPOSAL",top);
			formObject.setNGControlLeft("FRAME_IAGENT_PROPOSAL",left);
			formObject.setNGControlWidth("FRAME_IAGENT_PROPOSAL",width);
			//top+=80//Start Change related to Application  Proposal no. field in Omni flow HT
			top+=139;
			formObject.setNGControlTop("FRAME_SEARCH_BANK",top);
			formObject.setNGControlLeft("FRAME_SEARCH_BANK",left);
			formObject.setNGControlWidth("FRAME_SEARCH_BANK",width);
			//top+=60;			//PID-HT process changes
			top+=85;			//PID-HT process changes
			formObject.setNGControlTop("FRAME_CHEQUE_DD",top);
			formObject.setNGControlLeft("FRAME_CHEQUE_DD",left);
			formObject.setNGControlWidth("FRAME_CHEQUE_DD",width);
			top+=86;
			formObject.setNGControlTop("FRAME_SAVINGS_ACCOUNT",top);
			formObject.setNGControlLeft("FRAME_SAVINGS_ACCOUNT",left);
			formObject.setNGControlWidth("FRAME_SAVINGS_ACCOUNT",width);
			top+=32;
			formObject.setNGControlTop("FRAME_CREDIT_CARD",top);
			formObject.setNGControlLeft("FRAME_CREDIT_CARD",left);
			formObject.setNGControlWidth("FRAME_CREDIT_CARD",width);
			top+=115;
			formObject.setNGControlTop("FRAME_PID_NO",top);
			formObject.setNGControlLeft("FRAME_PID_NO",left);
			formObject.setNGControlWidth("FRAME_PID_NO",width);
			top+=32;
			formObject.setNGControlTop("FRAME_SALESMGR_EMPID",top);
			formObject.setNGControlLeft("FRAME_SALESMGR_EMPID",left);
			formObject.setNGControlWidth("FRAME_SALESMGR_EMPID",width);
			top+=88;
			formObject.setNGControlTop("FRAME_IF_PRODUCT_HSP",top);
			formObject.setNGControlLeft("FRAME_IF_PRODUCT_HSP",left);
			formObject.setNGControlWidth("FRAME_IF_PRODUCT_HSP",width);
			//top+=285;
			top+=265; //CR25_Masters of KRG,Removal of fields & CR26 RMT Bucket
			formObject.setNGControlTop("FRAME_MISC",top);
			formObject.setNGControlLeft("FRAME_MISC",left);
			formObject.setNGControlWidth("FRAME_MISC",width);
			//top+=438;
			//top+=430; //CR25_Masters of KRG,Removal of fields & CR26 RMT Bucket  HT- CR-8093-59790 (HT-FlapPrint) CR
			//top+=470; //CR25_Masters of KRG,Removal of fields & CR26 RMT Bucket//Start Change related to Application  Proposal no. field in Omni flow HT commented
			top+=586;
			formObject.setNGControlTop("FRAME_SOURCE_BUSINESS",top);
			formObject.setNGControlLeft("FRAME_SOURCE_BUSINESS",left);
			formObject.setNGControlWidth("FRAME_SOURCE_BUSINESS",width);
			//top+=70;//Start Change related to Application  Proposal no. field in Omni flow HT commeted
			top+=93;
			formObject.setNGControlTop("FRAME_BANK_BRANCH_NAME",top);
			formObject.setNGControlLeft("FRAME_BANK_BRANCH_NAME",left);
			formObject.setNGControlWidth("FRAME_BANK_BRANCH_NAME",width);
			top+=60;//prev 36 sp code cr
			
			formObject.setNGControlTop("FRAME_IS_EMP_CODE",top);
			formObject.setNGControlLeft("FRAME_IS_EMP_CODE",left);
			formObject.setNGControlWidth("FRAME_IS_EMP_CODE",width);
			top+=60;
			formObject.setNGControlTop("FRAME_WRE_WRM",top);
			formObject.setNGControlLeft("FRAME_WRE_WRM",left);
			formObject.setNGControlWidth("FRAME_WRE_WRM",width);
			//top+=63;
			top+=90;//CR25_Masters of KRG,Removal of fields & RMT Bucket//sp code cr yogesh frame hidding
			formObject.setNGControlTop("FRAME_CHANNEL_EMP_INFO",top);
			formObject.setNGControlLeft("FRAME_CHANNEL_EMP_INFO",left);
			formObject.setNGControlWidth("FRAME_CHANNEL_EMP_INFO",width);
			top+=57;
			formObject.setNGControlTop("FRAME_BSM_BCM",top);
			formObject.setNGControlLeft("FRAME_BSM_BCM",left);
			formObject.setNGControlWidth("FRAME_BSM_BCM",width);
			top+=80;
			formObject.setNGControlTop("FRAME_CENTER_CODE_RM",top);
			formObject.setNGControlLeft("FRAME_CENTER_CODE_RM",left);
			formObject.setNGControlWidth("FRAME_CENTER_CODE_RM",width);
			top+=57;
			/********************** Start Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR ***********************/
			
			//System.out.println("Setting New FRAME FETCHCOPY At executeHTOnload HT- CR-8093-59790 (HT-FlapPrint) CR :" +top);
			formObject.setNGControlTop("FRM_FETCH_COPY",top);
			formObject.setNGControlLeft("FRM_FETCH_COPY",left);
			formObject.setNGControlWidth("FRM_FETCH_COPY",width);
			top+=38;//yogesh uncommented
	
			/********************** End Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR *************************/
			/******Start HT CR-8127-95325-GST-Omniflow development******/
			System.out.println("inside executeHTOnload FRAME_GST_GRID setting Yogesh.");
			formObject.setNGControlTop("FRAME_GST_GRID",top);
			formObject.setNGControlLeft("FRAME_GST_GRID",left);
			formObject.setNGControlWidth("FRAME_GST_GRID",width);
			top+=300;
			/******End HT CR-8127-95325-GST-Omniflow development******/
			
			/***** CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket*******/		
			/* formObject.setNGControlTop("FRAME_SUB_BROKER",top);
			formObject.setNGControlLeft("FRAME_SUB_BROKER",left);
			formObject.setNGControlWidth("FRAME_SUB_BROKER",width);
			top+=86; */
			/***** End CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket***/
			iTemp=top;
			formObject.setNGControlTop("FRAME_PATHFINDER_STATUS",top);
			formObject.setNGControlLeft("FRAME_PATHFINDER_STATUS",left);
			formObject.setNGControlWidth("FRAME_PATHFINDER_STATUS",width);
			top+=94;
			
			/***** CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket*******/	
			/*formObject.setNGControlTop("FRAME_UNIQUE_ID",top);
			formObject.setNGControlLeft("FRAME_UNIQUE_ID",left);
			formObject.setNGControlWidth("FRAME_UNIQUE_ID",width);
			top+=36; */
			/***** End CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket***/
			
			formObject.setNGControlTop("FRAME_DEQC_DECISION",top);
			formObject.setNGControlLeft("FRAME_DEQC_DECISION",left);
			formObject.setNGControlWidth("FRAME_DEQC_DECISION",width);
			top+=34;
			
			formObject.setNGControlTop("FRAME_COPS_DECISION",top);
			formObject.setNGControlLeft("FRAME_COPS_DECISION",left);
			formObject.setNGControlWidth("FRAME_COPS_DECISION",width);
			top+=40;
			
			/******************************* PID-HT process changes ********************************/
			formObject.setNGControlTop("FRAME_ROUTETO",top);
			formObject.setNGControlLeft("FRAME_ROUTETO",left);
			formObject.setNGControlWidth("FRAME_ROUTETO",width);
			top+=40;
			/******************************End PID-HT process changes ******************************/
			
			formHeight=top+topFrame+20;
			//formHeight=top+topFrame+800;
			formObject.setNGFormHeight(formHeight);
			//Hidden feild for product code by Vishal Gupta//
			formObject.setNGEnable("PRODUCT_HIDDEN",false);
			formObject.setNGVisible("PRODUCT_HIDDEN",false);
			formObject.setNGEnable("ICICILOMBARD_HT_DEAL_STATUS",false);
			formObject.setNGVisible("ICICILOMBARD_HT_DEAL_STATUS",true);//sp code yogesh CR-8093-69682 
			formObject.setNGEnable("ICICILOMBARD_HT_PRODUCT_CODE",false);
			formObject.setNGVisible("ICICILOMBARD_HT_PRODUCT_CODE",false);
			//END OF Hidden feild for product code by Vishal Gupta//
			formObject.setNGEnable("FRAME_CENTER_CODE_RM",false);
			
			formObject.setSpecificDateFormat("ICICILOMBARD_HT_INSTRUMENT_DATE", "dd/MM/yyyy");//Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR
			//System.out.println("Checking ICICILOMBARD_HT_INSTRUMENT_DATE OnLoad-----");//Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR
			/******************************* PID-HT process changes ********************************/
			if(wsName.equalsIgnoreCase("BSG_DATAENTRY_QC") || wsName.equalsIgnoreCase("COPS_TEAM"))
			{
				formObject.NGAddItem("ICICILOMBARD_HT_Route_To","--Select--");
				formObject.NGAddItem("ICICILOMBARD_HT_Route_To","BSG_Exception");
				formObject.NGAddItem("ICICILOMBARD_HT_Route_To","RM_Exception");
			}
			/******************************End PID-HT process changes ******************************/
			
			/******Start HT CR-8127-95325-GST-Omniflow development******/
			if((wsName.equalsIgnoreCase("BSG_DataEntry") || wsName.equalsIgnoreCase("BSG_DataEntry_QC") || wsName.equalsIgnoreCase("Exception") || wsName.equalsIgnoreCase("RM_Exception")))
			{
				System.out.println("inside if executeHTOnload HT CR-8127-95325-GST-Omniflow development");
				formObject.setNGEnable("FRAME_GST_GRID",true);
				if(formObject.getNGValue("ICICILOMBARD_HT_GST_REGISTERED").equalsIgnoreCase("Yes") && formObject.getNGValue("ICICILOMBARD_HT_IAGENT").equalsIgnoreCase("No"))
				{
					System.out.println("inside sub if executeHTOnload HT CR-8127-95325-GST-Omniflow development");
					formObject.setNGEnable("TXTGST_NUMBER",true);
					//formObject.setNGLocked("TXTGST_NUMBER",false);				
					formObject.setNGEnable("TXTGST_STATE_NAME",true);
					formObject.setNGLocked("TXTGST_STATE_NAME",false);
					formObject.setNGEnable("Add_GST",true);
					formObject.setNGEnable("Mod_GST",true);
					formObject.setNGEnable("Del_GST",true);
					formObject.setNGEnable("ICICILOMBARD_HT_qGrdGstVar",true);
				}
				/*****Start Change related to Application  Proposal no. field in Omni flow HT*****/
				if(formObject.getNGValue("ICICILOMBARD_HT_IAGENT").equalsIgnoreCase("Yes"))
				{
					formObject.setNGEnable("ICICILOMBARD_HT_PF_PROPOSAL_NO",true);
					formObject.setNGLocked("ICICILOMBARD_HT_PF_PROPOSAL_NO",true);
					formObject.setNGEnable("ICICILOMBARD_HT_CNFRM_PF_PROPOSAL_NO",true);
					formObject.setNGLocked("ICICILOMBARD_HT_CNFRM_PF_PROPOSAL_NO",true);
					formObject.setNGEnable("ICICILOMBARD_HT_PROPOSAL_NO",false);
					formObject.setNGLocked("ICICILOMBARD_HT_PROPOSAL_NO",false);
					formObject.setNGEnable("ICICILOMBARD_HT_CONFIRM_PROPOSAL_NO",false);
					formObject.setNGLocked("ICICILOMBARD_HT_CONFIRM_PROPOSAL_NO",false);
				}
				formObject.setNGEnable("ICICILOMBARD_HT_FUTURE_DATED",true);
				formObject.setNGLocked("ICICILOMBARD_HT_FUTURE_DATED",true);
				formObject.setNGEnable("ICICILOMBARD_HT_LOAN_SANCTIONED_AMT",true);
				formObject.setNGLocked("ICICILOMBARD_HT_LOAN_SANCTIONED_AMT",true);				
				formObject.setNGLocked("ICICILOMBARD_HT_DEAL_IL_LOCATION",false);				
				/*****End Change related to Application  Proposal no. field in Omni flow HT*****/				
			}
			else
			{	
				System.out.println("inside else executeHTOnload HT CR-8127-95325-GST-Omniflow development");
				formObject.setNGEnable("ICICILOMBARD_HT_GST_REGISTERED",false);		
				formObject.setNGEnable("TXTGST_NUMBER",false);
				formObject.setNGEnable("TXTGST_STATE_NAME",false);
				formObject.setNGEnable("Add_GST",false);
				formObject.setNGEnable("Mod_GST",false);
				formObject.setNGEnable("Del_GST",false);
				formObject.setNGEnable("ICICILOMBARD_HT_qGrdGstVar",true);
				/*****Start Change related to Application  Proposal no. field in Omni flow HT*****/
				formObject.setNGEnable("ICICILOMBARD_HT_PF_PROPOSAL_NO",false);
				formObject.setNGLocked("ICICILOMBARD_HT_PF_PROPOSAL_NO",false);
				formObject.setNGEnable("ICICILOMBARD_HT_CNFRM_PF_PROPOSAL_NO",false);
				formObject.setNGLocked("ICICILOMBARD_HT_CNFRM_PF_PROPOSAL_NO",false);
				formObject.setNGEnable("ICICILOMBARD_HT_FUTURE_DATED",false);
				formObject.setNGLocked("ICICILOMBARD_HT_FUTURE_DATED",false);
				formObject.setNGEnable("ICICILOMBARD_HT_LOAN_SANCTIONED_AMT",false);
				formObject.setNGLocked("ICICILOMBARD_HT_LOAN_SANCTIONED_AMT",false);				
				formObject.setNGLocked("ICICILOMBARD_HT_DEAL_IL_LOCATION",false);
				/*****End Change related to Application  Proposal no. field in Omni flow HT*****/
			}
			/******End HT CR-8127-95325-GST-Omniflow development******/
			
			//if the workstep in Data Entry
			if(wsName.equalsIgnoreCase("BSG_DATAENTRY"))
			{
				/*************************************** Start HT sp code CR-8093-69682************************************************/
				if((formObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE"))&&(formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)")))
				{
					System.out.println("Inside executeHTOnload sp code :");
					formObject.setNGLocked("ICICILOMBARD_HT_DEAL_STATUS",false);
					formObject.setNGLocked("ICICILOMBARD_HT_DEAL_IL_LOCATION",false);
					formObject.setNGLocked("ICICILOMBARD_HT_SOL_ID",false);
					formObject.setNGLocked("ICICILOMBARD_HT_BANK_BRANCH_NAME",false);
					formObject.setNGLocked("ICICILOMBARD_HT_WRE",false);
					formObject.setNGLocked("ICICILOMBARD_HT_SP_NAME",false);
					formObject.setNGLocked("ICICILOMBARD_HT_SP_PAN",false);
				}
				/*************************************** End HT sp code CR-8093-69682************************************************/
				
				if(debug==1)
				{
					//System.out.println("inside dataentry load-----");
				}
				//setting these frames to false for Data entry workstep
				formObject.setNGVisible("FRAME_PATHFINDER_STATUS", false);
				formObject.setNGVisible("FRAME_DEQC_DECISION", false);
				formObject.setNGVisible("FRAME_COPS_DECISION", false);
				formObject.setNGVisible("FRAME_ROUTETO", false); //PID-HT process changes
					
				/********************** Start Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR ***********************/
					
					//System.out.println("INSIDE BSG_DATAENTRY Disabling Frame FRM_FETCH_COPY For HT- CR-8093-59790 (HT-FlapPrint) CR");
					formObject.setNGEnable("FRM_FETCH_COPY",false);
					
				/********************** End Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR *************************/
					//formObject.setNGValue("ICICILOMBARD_HT_IAGENT","No");
					//formObject.setNGEnable("ICICILOMBARD_HT_IAGENT",false);
					//formObject.setNGEnable("ICICILOMBARD_HT_IL_LOCATION_CODE",false); //PID-HT process changes
					
					formObject.setNGEnable("FRAME_SOURCE_BUSINESS",true);	

					//resetting formheight
					/***** CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket*******/
					formHeight-=160;
					//formHeight-=200;
					/***** End CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket***/
					//formHeight+=50; // commentted by manoj jain
					formObject.setNGFormHeight(formHeight);
					//allocating the array of field names
					
					String arrfieldName18[]= new String[1];
					
					formObject.setNGValue("ICICILOMBARD_HT_PROCESSINSTANCEID",formObject.getNGValue("ICICILOMBARD_HT_WorkItemName"));
					
					formObject.NGClear("ICICILOMBARD_HT_INTRODUCED_BY_EMAIL");
					arrfieldName18[0]="ICICILOMBARD_HT_INTRODUCED_BY_EMAIL";
					query="select mailid from pdbuser where userindex="+formObject.getUserId();
					noOfCols="1";
					getData(query,noOfCols,arrfieldName18,flag);
					
					
					
					//enabling disabling the frame hsp
					
					if(formObject.getNGValue("ICICILOMBARD_HT_PRODUCT_ABBR_CODE")!=null)
					{
						//Vishal_HSP

						if((formObject.getNGValue("ICICILOMBARD_HT_PRODUCT_ABBR_CODE").equalsIgnoreCase("hsp") && !formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("COMPLETE HEALTH INSURANCE"))
						/*********************** CR 24 HSP product by satish *************************/
						|| formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("HOMEINSURANCEPRODUCT") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("SECURE MIND")|| formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("Secure Mind A") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("GROUP SECURE MIND") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("GROUP PERSONAL ACCIDENT") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("CCPI") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("Asset Protect") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("Merchant Cover") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("Home")
						/********************** end CR 24 HSP product by satish **********************/
						)
						{
							//System.out.println("Vishal_HSP true");
							//System.out.println("ICICILOMBARD_HT_PRODUCT:- "+formObject.getNGValue("ICICILOMBARD_HT_PRODUCT"));
							formObject.setNGEnable("FRAME_IF_PRODUCT_HSP",true);
							/****************** CR 24 HSP product by satish *********************/
							if(!(formObject.getNGValue("ICICILOMBARD_HT_PRODUCT_ABBR_CODE").equalsIgnoreCase("hsp") && !formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("COMPLETE HEALTH INSURANCE")))
							{
								formObject.setNGEnable("ICICILOMBARD_HT_IAGENT",false);
							}
							/**************** end CR 24 HSP product by satish *******************/
						}
						else
						{
							//System.out.println("Vishal_HSP false");
							
							formObject.setNGEnable("FRAME_IF_PRODUCT_HSP",false);
							//cr 24
							formObject.setNGEnable("ICICILOMBARD_HT_IAGENT",true);
						}
					}
								
					
					//enabling banker code,wre,wrm,empcode on bbg.branch banking,home,krg2	
					if(formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL")!=null & formObject.isNGEnable("ICICILOMBARD_HT_VERTICAL") && formObject.getNGValue("ICICILOMBARD_HT_VERTICAL")!=null)
					{
						//System.out.println("satish1: enabling banker code,wre,wrm,empcode");
						if(formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking (BBG)") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || (formObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BBG") && formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG")))
						{
							//System.out.println("satish2: enabling banker code,wre,wrm,empcode");
							formObject.setNGEnable("ICICILOMBARD_HT_PRIVILEGE_BANKER_CODE",true); 	
							formObject.setNGEnable("FRAME_IS_EMP_CODE",true); 	
							formObject.setNGEnable("FRAME_WRE_WRM", true);							
						}
						else
						{
							formObject.setNGEnable("ICICILOMBARD_HT_PRIVILEGE_BANKER_CODE",false); 
							formObject.setNGEnable("FRAME_IS_EMP_CODE",false); 
							formObject.setNGEnable("FRAME_WRE_WRM", false);
						}
						
						/************************ Start CR-OMHT-1314-01 Wallet_Insurance************/					
						/*if(formObject.getNGValue("ICICILOMBARD_HT_DEAL_NO") != null && !formObject.getNGValue("ICICILOMBARD_HT_DEAL_STATUS").equalsIgnoreCase("YES"))
						{
							formObject.setNGEnable("ICICILOMBARD_HT_IL_LOCATION",false);
								
						}*/
						/************************ End CR-OMHT-1314-01 Wallet_Insurance************/
						if((formObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE"))&&(formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)")))
						{
							System.out.println("inside executeHTOnload sp code 2 :");
							formObject.setNGEnable("ICICILOMBARD_HT_WRE",true);
							formObject.setNGLocked("ICICILOMBARD_HT_WRE",false);
						}
					}	
					//end
					
				if(formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL")!=null)
				{	
					if(formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking (BBG)") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)")) 
	//				|| formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("HOME"))
	// 				Commented by Yogendra Saraswat
	//				|| formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("krg2"))
					{
						//System.out.println("satish3: enabling FRAME_BANK_BRANCH_NAME");
						formObject.setNGEnable("ICICILOMBARD_HT_SOURCE_BUSINESS",true); 
						formObject.setNGEnable("ICICILOMBARD_HT_CHANNEL_SOURCE",true); 
						formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",true); 
						if(formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)")|| formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("HOME"))
						{
							formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",false);
						}
					}
					else
					{
						formObject.setNGEnable("ICICILOMBARD_HT_SOURCE_BUSINESS",false); 
						formObject.setNGEnable("ICICILOMBARD_HT_CHANNEL_SOURCE",false); 
						formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",false); 
					}
					
				}	
				//end
				//enabling  disabling the bsm bcm on home sub vertical
				if(formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL")!=null)
				{
					if(formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("home"))
					{
						formObject.setNGEnable("FRAME_BSM_BCM",true); 
					}
					else
					{
						formObject.setNGEnable("FRAME_BSM_BCM",false); 
					}
				}	
				//end
				
				/**********start adding new product RETAIL PURE KHOKHA************/
				if(formObject.getNGValue("ICICILOMBARD_HT_PRODUCT") != null && formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("RETAIL PURE KHOKHA")) 
				{
					//System.out.println("new product test if");
					formObject.setNGEnable("ICICILOMBARD_HT_IAGENT",false);
				}
				else
				{
					//System.out.println("new product test if else");
					formObject.setNGEnable("ICICILOMBARD_HT_IAGENT",true);
				}
				/**********end adding new product RETAIL PURE KHOKHA**************/
				
				//enabling prev policy no
				if(!formObject.getNGValue("ICICILOMBARD_HT_TRANSACTION_TYPE").equalsIgnoreCase("--Select--"))
				{
					if(formObject.getNGValue("ICICILOMBARD_HT_TRANSACTION_TYPE").equalsIgnoreCase("Renewal"))
					{
						formObject.setNGEnable("FRAME_PREV_POLICY_NUMBER",true);
					}
					else
					{
						formObject.setNGEnable("FRAME_PREV_POLICY_NUMBER",false);
					}
				}

				
				if(formObject.isNGEnable("ICICILOMBARD_HT_SOURCE_BUSINESS") && formObject.getNGValue("ICICILOMBARD_HT_SOURCE_BUSINESS")!=null)	
				{
					if(formObject.getNGValue("ICICILOMBARD_HT_SOURCE_BUSINESS").equalsIgnoreCase("Center Sales") || formObject.getNGValue("ICICILOMBARD_HT_SOURCE_BUSINESS").equalsIgnoreCase("Centre Sales"))
					{
						formObject.setNGEnable("FRAME_CENTER_CODE_RM",true);
					}
					/***** CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket*******/
					/*else if(formObject.getNGValue("ICICILOMBARD_HT_SOURCE_BUSINESS").equalsIgnoreCase("Sub Broker"))
					{
						formObject.setNGEnable("FRAME_SUB_BROKER",true);
					} */
					/***** End CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket***/
					else
					{
						formObject.setNGEnable("FRAME_CENTER_CODE_RM",false);
						//formObject.setNGEnable("FRAME_SUB_BROKER",false); //CR25_Masters of KRG,Removal of fields & CR26 RMT Bucket 
					}
				}
				
				//
				if(formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL")!=null)
				{
					if(formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)"))
					{
						formObject.setNGEnable("FRAME_CHANNEL_EMP_INFO",true); 
					}
					else
					{
						formObject.setNGEnable("FRAME_CHANNEL_EMP_INFO",false); 
					}
				}
			}
			
			else if(wsName.equalsIgnoreCase("BSG_DATAENTRY_QC"))
			{
				if(debug==1)
				{
					//System.out.println("inside dataentry qc load-----");
				}				
				//setting these frames to false for Data entry workstep
				//formObject.setNGVisible("FRAME_UNIQUE_ID",false);//CR25_Masters of KRG,Removal of fields & CR26 RMT Bucket
				formObject.setNGVisible("FRAME_COPS_DECISION", false);

				formObject.setNGVisible("FRAME_DEQC_DECISION", true);
				formObject.setNGVisible("FRAME_PATHFINDER_STATUS", true);
				formObject.setNGVisible("FRAME_ROUTETO", true); //PID-HT process changes

				/********************** Start Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR ***********************/
				
				//System.out.println(" Inside BSG_DATAENTRY_QC Enabling FRM_FETCH_COPY For HT- CR-8093-59790 (HT-FlapPrint) CR ");	
				formObject.setNGVisible("FRM_FETCH_COPY",true);
				formObject.setNGEnable("FRM_FETCH_COPY", true);
					
				/********************** End Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR *************************/
				//enabling the DEQC frame	
				formObject.setNGEnable("FRAME_DEQC_DECISION", true);
				formObject.setNGEnable("FRAME_PATHFINDER_STATUS",false);
				
				//setting the height of the frame and the form
				formObject.setNGControlTop("FRAME_PATHFINDER_STATUS",iTemp);
				formObject.setNGControlLeft("FRAME_PATHFINDER_STATUS",left);
				formObject.setNGControlWidth("FRAME_PATHFINDER_STATUS",width);
				iTemp+=94;
								
				
				formObject.setNGControlTop("FRAME_DEQC_DECISION",iTemp);
				formObject.setNGControlLeft("FRAME_DEQC_DECISION",left);
				formObject.setNGControlWidth("FRAME_DEQC_DECISION",width);				
				iTemp+=34;
				
				formObject.setNGControlTop("FRM_CUSTOMER_MOBILE_NO",iTemp);
				formObject.setNGControlLeft("FRM_CUSTOMER_MOBILE_NO",left);
				formObject.setNGControlWidth("FRM_CUSTOMER_MOBILE_NO",width);
				
				/******************************* PID-HT process changes ********************************/
				iTemp+=34;
				formObject.setNGControlTop("FRAME_ROUTETO",iTemp);
				formObject.setNGControlLeft("FRAME_ROUTETO",left);
				formObject.setNGControlWidth("FRAME_ROUTETO",width);
				iTemp+=56;
				/******************************End PID-HT process changes ******************************/
		
				/********************** Start Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR ***********************/
				
				//System.out.println("Setting New  Frame FRM_FETCH_COPY At BSG_DATAENTRY_QC For HT- CR-8093-59790 (HT-FlapPrint) CR ");
				formObject.setNGControlTop("FRM_FETCH_COPY",iTemp);
				formObject.setNGControlLeft("FRM_FETCH_COPY",left);
				formObject.setNGControlWidth("FRM_FETCH_COPY",width);
				iTemp+=38;  // 34 now changed to 41 NOW 45			
				/********************** End Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR *************************/
				/******Start HT CR-8127-95325-GST-Omniflow development******/
				/*System.out.println("Inside else if BSG_DATAENTRY_QC For HT- CR-8127-95325-GST :");
				formObject.setNGControlTop("FRAME_GST_GRID",iTemp);
				formObject.setNGControlLeft("FRAME_GST_GRID",left);
				formObject.setNGControlWidth("FRAME_GST_GRID",width);
				iTemp+=300;*/
				/******End HT CR-8127-95325-GST-Omniflow development******/
				
				formHeight=iTemp+topFrame+60; // default height was 50 now its 60 HT- CR-8093-59790 (HT-FlapPrint) CR
				//System.out.println("Checking formHeight For FlapPrint CR :"+formHeight);
				formObject.setNGFormHeight(formHeight);

				formObject.setNGEnable("FRAME_SEARCH_CRITERIA", false);
				formObject.setNGEnable("FRAME_SELECT_SUB_VERTICAL", false);
				formObject.setNGEnable("FRAME_SELECT_VERTICAL", false);
				formObject.setNGEnable("ICICILOMBARD_HT_PRODUCT", false);
				
				/********************** Start Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR ***********************/
				
				formObject.setNGEnable("FRAME_IAGENT_PROPOSAL", true);    //Changes made For (true) Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR

				//System.out.println("FRAME_IAGENT_PROPOSAL==="+formObject.getNGValue("FRAME_IAGENT_PROPOSAL"));
				//formObject.setNGEnable("ICICILOMBARD_HT_PROPOSAL_NO",false);
				//formObject.setNGEnable("ICICILOMBARD_HT_CONFIRM_PROPOSAL_NO",false);
				//System.out.println("ICICILOMBARD_HT_PROPOSAL_NO==="+formObject.getNGValue("ICICILOMBARD_HT_PROPOSAL_NO"));
				
				/********************** End Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR *************************/
				
				formObject.setNGEnable("ICICILOMBARD_HT_BANK_NAME", false);
				formObject.setNGVisible("FRM_CUSTOMER_MOBILE_NO", true);

				formObject.setNGEnable("ICICILOMBARD_HT_CUSTOMERNUMBER", true);
			
				formObject.setNGEnable("FRAME_SOURCE_BUSINESS",true);
				formObject.setNGEnable("ICICILOMBARD_HT_SOURCE_BUSINESS",false); 
				formObject.setNGEnable("ICICILOMBARD_HT_CHANNEL_SOURCE",false); 
								
				/******************************* PID-HT process changes ********************************/
				/*PID_Tagging Logic same as MHT*/
				
				if(!formObject.getNGValue("ICICILOMBARD_HT_TAG_STATUS").equalsIgnoreCase(""))
					{
						//System.out.println("******PID case and tagged******");
						//System.out.println("HT_TAG_STATUS1==="+formObject.getNGValue("ICICILOMBARD_HT_TAG_STATUS"));
						formObject.setNGEnable("ICICILOMBARD_HT_PID_PAYMENT_TYPE", false);
						formObject.setNGLocked("ICICILOMBARD_HT_PID_PAYMENT_TYPE", false);
						formObject.setNGEnable("FRAME_CHEQUE_DD",false);
						formObject.setNGEnable("FRAME_SAVINGS_ACCOUNT",false);
						formObject.setNGEnable("FRAME_CREDIT_CARD",false);
						formObject.setNGLocked("ICICILOMBARD_HT_PAYMENT_MODE", false);	
						formObject.setNGEnable("ICICILOMBARD_HT_PAYMENT_MODE",false);
						formObject.setNGLocked("ICICILOMBARD_HT_PAYMENT_ID", false);
						formObject.setNGEnable("ICICILOMBARD_HT_PAYMENT_ID",false);
					}
					else if(formObject.getNGValue("ICICILOMBARD_HT_PID_PAYMENT_TYPE").equalsIgnoreCase("Yes"))
					{
						//System.out.println("******PID case but not tagged yet******");
						//System.out.println("HT_TAG_STATUS2==="+formObject.getNGValue("ICICILOMBARD_HT_TAG_STATUS"));
						//System.out.println("HT_PID_PAYMENT_TYPE==="+formObject.getNGValue("ICICILOMBARD_HT_PID_PAYMENT_TYPE"));
						formObject.setNGEnable("FRAME_CHEQUE_DD",false);
						formObject.setNGEnable("FRAME_SAVINGS_ACCOUNT",false);
						formObject.setNGEnable("FRAME_CREDIT_CARD",false);
						//bug fixing 
						formObject.setNGLocked("ICICILOMBARD_HT_PAYMENT_MODE", false);	
						formObject.setNGEnable("ICICILOMBARD_HT_PAYMENT_MODE",false);
						formObject.setNGLocked("ICICILOMBARD_HT_PID_PAYMENT_TYPE", false);	
						formObject.setNGEnable("ICICILOMBARD_HT_PID_PAYMENT_TYPE",false);
					}
					/*else
					{
						//System.out.println("******Non PID case ******");
						//System.out.println("HT_TAG_STATUS3==="+formObject.getNGValue("ICICILOMBARD_HT_TAG_STATUS"));
						//System.out.println("ICICILOMBARD_HT_PID_PAYMENT_TYPE==="+formObject.getNGValue("ICICILOMBARD_HT_PID_PAYMENT_TYPE"));
						formObject.setNGEnable("FRAME_CHEQUE_DD",true);
						formObject.setNGEnable("FRAME_SAVINGS_ACCOUNT",true);
						formObject.setNGEnable("FRAME_CREDIT_CARD",true);
					}*/
					//System.out.println("HT_TAG_STATUS4==="+formObject.getNGValue("ICICILOMBARD_HT_TAG_STATUS"));
					 if(formObject.getNGValue("ICICILOMBARD_HT_PID_PAYMENT_TYPE").equalsIgnoreCase("No"))
					 {
						formObject.setNGLocked("ICICILOMBARD_HT_PID_PAYMENT_TYPE", false);
						formObject.setNGEnable("ICICILOMBARD_HT_PID_PAYMENT_TYPE",false);
					 }

				
				/******************************End PID-HT process changes ******************************/					
				/************************ Start CR-OMHT-1314-01 Wallet_Insurance************/					
				/*if(formObject.getNGValue("ICICILOMBARD_HT_DEAL_NO") != null && !formObject.getNGValue("ICICILOMBARD_HT_DEAL_STATUS").equalsIgnoreCase("YES"))
				{
					formObject.setNGEnable("ICICILOMBARD_HT_IL_LOCATION",false);
				}*/
				/************************ End CR-OMHT-1314-01 Wallet_Insurance************/
				
				
				//enabling banker code,wre,wrm,empcode on bbg.branch banking,home,krg2				
				if(formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking (BBG)") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 2)") || (formObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BBG") && formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG")))
				{
					//System.out.println("satish4: enabling HT_PRIVILEGE_BANKER_CODE");
					formObject.setNGEnable("ICICILOMBARD_HT_PRIVILEGE_BANKER_CODE",true); 	
					formObject.setNGEnable("FRAME_IS_EMP_CODE",true); 	
					formObject.setNGEnable("FRAME_WRE_WRM", true);
					
				}
				else
				{
					formObject.setNGEnable("ICICILOMBARD_HT_PRIVILEGE_BANKER_CODE",false); 
					formObject.setNGEnable("FRAME_IS_EMP_CODE",false); 
					formObject.setNGEnable("FRAME_WRE_WRM", false);
				}
				//end
				
				//enabling disabling the frame hsp
					//Vishal_HSP			
				if((formObject.getNGValue("ICICILOMBARD_HT_PRODUCT_ABBR_CODE").equalsIgnoreCase("hsp") && !formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("COMPLETE HEALTH INSURANCE"))
				/*********************** CR 24 HSP product by satish *************************/
				|| formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("HOMEINSURANCEPRODUCT") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("SECURE MIND")|| formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("Secure Mind A") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("GROUP SECURE MIND") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("GROUP PERSONAL ACCIDENT") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("CCPI") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("Asset Protect") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("Merchant Cover") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("Home")
				/********************** end CR 24 HSP product by satish **********************/
				)
				{
					//System.out.println("Vishal_HSP true");
					//System.out.println("ICICILOMBARD_HT_PRODUCT:- "+formObject.getNGValue("ICICILOMBARD_HT_PRODUCT"));
					formObject.setNGEnable("FRAME_IF_PRODUCT_HSP",true); 
					/****************** CR 24 HSP product by satish *********************/
					if(!(formObject.getNGValue("ICICILOMBARD_HT_PRODUCT_ABBR_CODE").equalsIgnoreCase("hsp") && !formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("COMPLETE HEALTH INSURANCE")))
					{
						formObject.setNGEnable("ICICILOMBARD_HT_IAGENT",false);
					}
					/**************** end CR 24 HSP product by satish *******************/
				}
				else
				{
					//System.out.println("Vishal_HSP false");
					formObject.setNGEnable("FRAME_IF_PRODUCT_HSP",false); 
					//cr 24
					formObject.setNGEnable("ICICILOMBARD_HT_IAGENT",true);
				}
				//end
				
				//enabling disabling the Product
				// Yogendra Saraswat --  for CRITICAL CARE case--
				if(formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("CRITICAL CARE") 
				/************************** CR 24 HSP product by satish **************************/
				|| formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("HOMEINSURANCEPRODUCT") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("SECURE MIND")|| formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("Secure Mind A") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("GROUP SECURE MIND") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("GROUP PERSONAL ACCIDENT") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("CCPI") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("Asset Protect") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("Merchant Cover") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("Home")|| formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("GROUP SAFEGUARD INSURANCE")
				/************************ end CR 24 HSP product by satish ************************/
				)
				{
					formObject.setNGEnable("ICICILOMBARD_HT_SUB_PRODUCT",false);
					/*********************** CR 24 HSP product by satish ***********************/
					/*if(!(formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("CRITICAL CARE") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("WALLET INSURANCE"))) //CR-OMHT-1314-01-Wallet_Insurance
					{
						formObject.setNGEnable("ICICILOMBARD_HT_IAGENT",false);
					} FlapPrint Code Commented Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR*/
					/********************* end CR 24 HSP product by satish *********************/
				} /************************ Start CR-OMHT-1314-01 Wallet_Insurance************/
				else if(formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("WALLET INSURANCE") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("RETAIL PURE KHOKHA")) //adding new product RETAIL PURE KHOKHA
				{
					formObject.setNGEnable("ICICILOMBARD_HT_SUB_PRODUCT",false);
					formObject.setNGEnable("FRAME_PROD_ABBR_CODE",false);
					formObject.setNGEnable("ICICILOMBARD_HT_LAN",false);
					/**********start adding new product RETAIL PURE KHOKHA************/
					/*if(formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("RETAIL PURE KHOKHA"))
					{
						formObject.setNGEnable("ICICILOMBARD_HT_IAGENT",false);

					}FlapPrint Code Commented Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR */
					/**********end adding new product RETAIL PURE KHOKHA**************/
				} /************************ End CR-OMHT-1314-01 Wallet_Insurance************/
				/***********************Start CR Motor,Home & Travel processing*************/
				else if(formObject.getNGValue("ICICILOMBARD_HT_PRODUCT") != null)
				{
					String field[]= {"ProductList"};
					String productlistQuery = "select productname,producttype from NG_ICICI_MST_Product";
					noOfCols="2";
					getData(productlistQuery,noOfCols,field,flag);
					if(map.size() > 1 && map.containsKey(formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").trim()))
					{
						//System.out.println("map=="+map.containsKey(formObject.getNGValue("ICICILOMBARD_HT_PRODUCT")));
						//System.out.println("product=="+formObject.getNGValue("ICICILOMBARD_HT_PRODUCT"));
						//formObject.setNGEnable("ICICILOMBARD_HT_IAGENT",true); FlapPrint Code Commented Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR
						formObject.setNGEnable("ICICILOMBARD_HT_SUB_PRODUCT",false);
						formObject.setNGEnable("FRAME_PROD_ABBR_CODE",false);
						map = null;
					}
					else
					{
						formObject.setNGEnable("ICICILOMBARD_HT_SUB_PRODUCT",true);
						//cr 24
						//formObject.setNGEnable("ICICILOMBARD_HT_IAGENT",true); FlapPrint Code Commented Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR
						
					}
					//map = null;
					
				} /***********************End CR Motor,Home & Travel processing***************/
				
				// Yogendra Saraswat -- end for CRITICAL CARE case--
				//end
				
				//enabling  disabling the frame source busness,channel source,bank branch name
				if(formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking (BBG)") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)"))
//				|| formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("HOME"))
// 				Commented by Yogendra Saraswat
//				|| formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("krg2"))
				{
					formObject.setNGEnable("ICICILOMBARD_HT_SOURCE_BUSINESS",true); 
					formObject.setNGEnable("ICICILOMBARD_HT_CHANNEL_SOURCE",true); 
					formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",true); 
					if(formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)")|| formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("HOME"))
						{
							formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",false);
						}
				}
				else
				{
					formObject.setNGEnable("ICICILOMBARD_HT_SOURCE_BUSINESS",false); 
					formObject.setNGEnable("ICICILOMBARD_HT_CHANNEL_SOURCE",false); 
					formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",false); 
				}
				//end
				//enabling  disabling the bsm bcm on home sub vertical
				if(formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("home"))
				{
					formObject.setNGEnable("FRAME_BSM_BCM",true); 
				}
				else
				{
					formObject.setNGEnable("FRAME_BSM_BCM",false); 
				}
				//end
				
				//enabling prev policy no
				if(formObject.getNGValue("ICICILOMBARD_HT_TRANSACTION_TYPE").equalsIgnoreCase("Rollover"))
				{
					formObject.setNGEnable("FRAME_PREV_POLICY_NUMBER",true);
				}
				else
				{
					formObject.setNGEnable("FRAME_PREV_POLICY_NUMBER",false);
				}
				
				/********************** Start Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR ***********************/
					
					if(formObject.getNGValue("ICICILOMBARD_HT_TRANSACTION_TYPE").equalsIgnoreCase("Renewal") || formObject.getNGValue("ICICILOMBARD_HT_TRANSACTION_TYPE").equalsIgnoreCase("Rollover"))
					{
						//System.out.println("Enabling Policy No ForHT- CR-8093-59790 (HT-FlapPrint) CR ");
						//System.out.println("FRAME_PREV_POLICY_NUMBER :- "+formObject.getNGValue("ICICILOMBARD_HT_TRANSACTION_TYPE"));
						formObject.setNGEnable("FRAME_PREV_POLICY_NUMBER",true);
						
					}
					else
					{
						//System.out.println("Disabling Policy No ForHT- CR-8093-59790 (HT-FlapPrint) CR ");
						//System.out.println("FRAME_PREV_POLICY_NUMBER:- "+formObject.getNGValue("ICICILOMBARD_HT_TRANSACTION_TYPE"));
						formObject.setNGEnable("FRAME_PREV_POLICY_NUMBER",false);
						
					}
				
				/********************** End Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR *************************/
				
				/********************** Start Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR ***********************/

					/*//System.out.println("Checking For ICICILOMBARD_HT_IAGENT Value For HT- CR-8093-59790 (HT-FlapPrint) CR");
					if(formObject.getNGValue("ICICILOMBARD_HT_IAGENT").equalsIgnoreCase("Yes"))
					{
						//System.out.println("Checking Value Of ICICILOMBARD_HT_IAGENT :- "+formObject.getNGValue("ICICILOMBARD_HT_IAGENT"));
						//System.out.println("Enabling ICICILOMBARD_HT_IAGENT For HT- CR-8093-59790 (HT-FlapPrint) CR");
						formObject.setNGEnable("ICICILOMBARD_HT_IAGENT",true);
					}
					else
					{
						//System.out.println("Checking Value Of ICICILOMBARD_HT_IAGENT :- "+formObject.getNGValue("ICICILOMBARD_HT_IAGENT"));
						//System.out.println("Disabling ICICILOMBARD_HT_IAGENT For HT- CR-8093-59790 (HT-FlapPrint) CR");
						formObject.setNGEnable("ICICILOMBARD_HT_IAGENT",false);
					}*/

				/********************** End Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR *************************/
				
			}
			
			else if(wsName.equalsIgnoreCase("COPS_TEAM"))
			{
				if(debug==1)
				{
					//System.out.println("inside cops team");
				}
				
				//formObject.setNGVisible("FRAME_UNIQUE_ID",false);//CR25_Masters of KRG,Removal of fields & CR26 RMT Bucket
				formObject.setNGVisible("FRAME_COPS_DECISION", false);
				formObject.setNGVisible("FRAME_PATHFINDER_STATUS", false);
				formObject.setNGVisible("FRAME_SEARCH_CRITERIA", false);
				
				formObject.setNGVisible("FRAME_DEQC_DECISION", true);
				formObject.setNGVisible("FRAME_ROUTETO", true); //PID-HT process changes
					
				/********************** Start Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR ***********************/
				
				//System.out.println(" Inside COPS_TEAM Enabling FRM_FETCH_COPY For HT- CR-8093-59790 (HT-FlapPrint) CR");	
				formObject.setNGVisible("FRM_FETCH_COPY",true);
				formObject.setNGEnable("FRM_FETCH_COPY", true);
					
				/********************** End Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR *************************/
				formObject.setNGControlTop("FRAME_DATA_ENTRY_PARENT",iTemp1);
				formObject.setNGControlLeft("FRAME_DATA_ENTRY_PARENT",left);

				formObject.setNGControlTop("FRAME_DEQC_DECISION",iTemp);
				formObject.setNGControlLeft("FRAME_DEQC_DECISION",left);
				iTemp+=34;
				
				formObject.setNGControlTop("FRM_CUSTOMER_MOBILE_NO",iTemp);
				formObject.setNGControlLeft("FRM_CUSTOMER_MOBILE_NO",left);
				formObject.setNGControlWidth("FRM_CUSTOMER_MOBILE_NO",width);
				
				/******************************* PID-HT process changes ********************************/
				iTemp+=34;
				formObject.setNGControlTop("FRAME_ROUTETO",iTemp);
				formObject.setNGControlLeft("FRAME_ROUTETO",left);
				formObject.setNGControlWidth("FRAME_ROUTETO",width);
				iTemp+=56; // Start Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR
				/******************************End PID-HT process changes ******************************/
			
			
				/********************** Start Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR ***********************/
				
				//System.out.println("Setting New  Frame FRM_FETCH_COPY In COPS_TEAM Bucket For HT- CR-8093-59790 (HT-FlapPrint) CR");
				formObject.setNGControlTop("FRM_FETCH_COPY",iTemp);
				formObject.setNGControlLeft("FRM_FETCH_COPY",left);
				formObject.setNGControlWidth("FRM_FETCH_COPY",width);
				iTemp+=38;  // 34 now changed to 41 NOW 45
				/********************** End Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR *************************/
				/******Start HT CR-8127-95325-GST-Omniflow development******/
				/*System.out.println("Inside else if COPS_TEAM HT FRAME_GST_GRID setting Yogesh: ");
				formObject.setNGControlTop("FRAME_GST_GRID",iTemp);
				formObject.setNGControlLeft("FRAME_GST_GRID",left);
				formObject.setNGControlWidth("FRAME_GST_GRID",width);
				iTemp+=300;*/
				/******End HT CR-8127-95325-GST-Omniflow development******/
				
				formHeight=iTemp+topFrame-120;  // default height was 120 now its 60 End Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR
				//System.out.println("Checking formHeight For HT- CR-8093-59790 (HT-FlapPrint) CR : "+formHeight);
		
				formObject.setNGFormHeight(formHeight);

				formObject.setNGEnable("FRAME_SELECT_SUB_VERTICAL",false);
				formObject.setNGEnable("FRAME_SELECT_VERTICAL",false);
				formObject.setNGEnable("FRAME_PRODUCT",false);
				formObject.setNGEnable("FRAME_PROD_ABBR_CODE",false);
				formObject.setNGEnable("FRAME_PREV_POLICY_NUMBER",false);
				formObject.setNGEnable("FRAME_IAGENT_PROPOSAL",false);
				formObject.setNGEnable("FRAME_SEARCH_BANK",false);
				formObject.setNGEnable("FRAME_CHEQUE_DD",false);
				formObject.setNGEnable("FRAME_SAVINGS_ACCOUNT",false);
				formObject.setNGEnable("FRAME_CREDIT_CARD",false);
				formObject.setNGEnable("FRAME_PID_NO",false);
				formObject.setNGEnable("FRAME_SALESMGR_EMPID",false);
				formObject.setNGEnable("FRAME_IF_PRODUCT_HSP",false);
				formObject.setNGEnable("FRAME_MISC",false);
				formObject.setNGEnable("FRAME_SOURCE_BUSINESS",false);
				formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",false);
				formObject.setNGEnable("FRAME_IS_EMP_CODE",false);
				formObject.setNGEnable("FRAME_WRE_WRM",false);
				formObject.setNGEnable("FRAME_CHANNEL_EMP_INFO",false);
				formObject.setNGEnable("FRAME_BSM_BCM",false);
				formObject.setNGEnable("FRAME_CENTER_CODE_RM",false);
				//formObject.setNGEnable("FRAME_SUB_BROKER",false);//CR25_Masters of KRG,Removal of fields & RMT Bucket 
				formObject.setNGEnable("FRAME_PATHFINDER_STATUS",false);
				formObject.setNGVisible("FRM_CUSTOMER_MOBILE_NO",true);
				formObject.setNGEnable("ICICILOMBARD_HT_CUSTOMERNUMBER", false);
					
				formObject.setNGEnable("FRAME_DEQC_DECISION",true);
				
				
				if(formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking (BBG)") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || (formObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BBG") && formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG")))
				{
					formObject.setNGEnable("ICICILOMBARD_HT_PRIVILEGE_BANKER_CODE",true); 	
				}
				else
				{
					formObject.setNGEnable("ICICILOMBARD_HT_PRIVILEGE_BANKER_CODE",false); 
				}
			
			// code added by manoj
			
				if(formObject.getNGValue("ICICILOMBARD_HT_TRANSACTION_TYPE").equalsIgnoreCase("Rollover"))
				{
				formObject.setNGEnable("FRAME_TXN_TYPE",true);
				
				formObject.setNGEnable("ICICILOMBARD_HT_TRANSACTION_TYPE",false);
				formObject.setNGEnable("BTN_PRT_POPUP", true); 
				
				// portability button is visible for transction type = "Rollover"
				}
				else
				{
				formObject.setNGEnable("FRAME_TXN_TYPE",false);
				}
			
			}//COPS_TEAM end
			
			/****************************** CR 24 HSP product by satish ******************************/
			else if(wsName.equalsIgnoreCase("COPS_QC") || wsName.equalsIgnoreCase("COPS_QC1"))
			/**************************** end CR 24 HSP product by satish ****************************/
			{
				if(debug==1)
				{
					//System.out.println("inside cops team");
				}
				//System.out.println("inside cops qc team");
				//formObject.setNGVisible("FRAME_UNIQUE_ID",false);//CR25_Masters of KRG,Removal of fields & CR26 RMT Bucket
				formObject.setNGVisible("FRAME_COPS_DECISION", false);
				formObject.setNGVisible("FRAME_PATHFINDER_STATUS", true);
				formObject.setNGVisible("FRAME_SEARCH_CRITERIA", false);
				
				formObject.setNGVisible("FRAME_DEQC_DECISION", true);
				
				formObject.setNGControlTop("FRAME_DATA_ENTRY_PARENT",iTemp1);
				formObject.setNGControlLeft("FRAME_DATA_ENTRY_PARENT",left);
				formObject.setNGControlTop("FRAME_DEQC_DECISION",iTemp);
				formObject.setNGControlLeft("FRAME_DEQC_DECISION",left);
				iTemp+=34;
				
				formObject.setNGControlTop("FRAME_PATHFINDER_STATUS",iTemp);
				formObject.setNGControlLeft("FRAME_PATHFINDER_STATUS",left);
				iTemp+=94;
				
				formObject.setNGControlTop("FRM_CUSTOMER_MOBILE_NO",iTemp);
				formObject.setNGControlLeft("FRM_CUSTOMER_MOBILE_NO",left);
				formObject.setNGControlWidth("FRM_CUSTOMER_MOBILE_NO",width);

				formHeight=iTemp+topFrame-120;
				
				formObject.setNGFormHeight(formHeight);

				formObject.setNGEnable("FRAME_SELECT_SUB_VERTICAL",false);
				formObject.setNGEnable("FRAME_SELECT_VERTICAL",false);
				formObject.setNGEnable("FRAME_PRODUCT",false);
				formObject.setNGEnable("FRAME_PROD_ABBR_CODE",false);
				formObject.setNGEnable("FRAME_TXN_TYPE",false);
				formObject.setNGEnable("FRAME_PREV_POLICY_NUMBER",false);
				formObject.setNGEnable("FRAME_IAGENT_PROPOSAL",false);
				formObject.setNGEnable("FRAME_SEARCH_BANK",false);
				formObject.setNGEnable("FRAME_CHEQUE_DD",false);
				formObject.setNGEnable("FRAME_SAVINGS_ACCOUNT",false);
				formObject.setNGEnable("FRAME_CREDIT_CARD",false);
				formObject.setNGEnable("FRAME_PID_NO",false);
				formObject.setNGEnable("FRAME_SALESMGR_EMPID",false);
				formObject.setNGEnable("FRAME_IF_PRODUCT_HSP",false);
				formObject.setNGEnable("FRAME_MISC",false);
				formObject.setNGEnable("FRAME_SOURCE_BUSINESS",false);
				formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",false);
				formObject.setNGEnable("FRAME_IS_EMP_CODE",false);
				formObject.setNGEnable("FRAME_WRE_WRM",false);
				formObject.setNGEnable("FRAME_CHANNEL_EMP_INFO",false);
				formObject.setNGEnable("FRAME_BSM_BCM",false);
				formObject.setNGEnable("FRAME_CENTER_CODE_RM",false);
				//formObject.setNGEnable("FRAME_SUB_BROKER",false);//CR25_Masters of KRG,Removal of fields & RMT Bucket
				formObject.setNGEnable("FRAME_PATHFINDER_STATUS",true);
				formObject.setNGEnable("FRAME_DEQC_DECISION",false);
				formObject.setNGEnable("ICICILOMBARD_HT_PATHFINDER_STATUS",true);
				
				
				formObject.setNGEnable("ICICILOMBARD_HT_CUSTOMERNUMBER", false);
				formObject.setNGVisible("FRM_CUSTOMER_MOBILE_NO",true);
			
				if(formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking (BBG)") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || (formObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BBG") && formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG")))
				{
					formObject.setNGEnable("ICICILOMBARD_HT_PRIVILEGE_BANKER_CODE",true); 	
				}
				else
				{
					formObject.setNGEnable("ICICILOMBARD_HT_PRIVILEGE_BANKER_CODE",false); 
				}
				// code added by manoj
			
				if(formObject.getNGValue("ICICILOMBARD_HT_TRANSACTION_TYPE").equalsIgnoreCase("Rollover"))
				{
				formObject.setNGEnable("FRAME_TXN_TYPE",true);
				
				formObject.setNGEnable("ICICILOMBARD_HT_TRANSACTION_TYPE",false);
				formObject.setNGEnable("BTN_PRT_POPUP", true); // portability button is visible for transction type = "Rollover"
				}
				else
				{
				formObject.setNGEnable("FRAME_TXN_TYPE",false);
				}
			
			}//cops_qc cops_team end	
			
		
		/*	else if(wsName.equalsIgnoreCase("RMT"))
			{
				//formObject.setNGVisible("FRAME_UNIQUE_ID",true);//CR25_Masters of KRG,Removal of fields & CR26 RMT Bucket 
				formObject.setNGVisible("FRAME_PATHFINDER_STATUS", true);
				formObject.setNGVisible("FRAME_COPS_DECISION", false);
				//formObject.setNGVisible("FRAME_DEQC_DECISION", true);
				formObject.setNGVisible("FRAME_SEARCH_CRITERIA", false);

				formObject.setNGControlTop("FRAME_DATA_ENTRY_PARENT",iTemp1);
				formObject.setNGControlLeft("FRAME_DATA_ENTRY_PARENT",left);

				//formObject.setNGControlTop("FRAME_DEQC_DECISION",iTemp);
				//formObject.setNGControlLeft("FRAME_DEQC_DECISION",left);

				formObject.setNGControlTop("FRAME_PATHFINDER_STATUS",iTemp);
				formObject.setNGControlLeft("FRAME_PATHFINDER_STATUS",left);
				iTemp+=130;
				
				formObject.setNGControlTop("FRM_CUSTOMER_MOBILE_NO",iTemp);
				formObject.setNGControlLeft("FRM_CUSTOMER_MOBILE_NO",left);
				formObject.setNGControlWidth("FRM_CUSTOMER_MOBILE_NO",width);

				formHeight=iTemp+topFrame-120;
				
				formObject.setNGFormHeight(formHeight);

				formObject.setNGEnable("FRAME_SELECT_SUB_VERTICAL",false);
				formObject.setNGEnable("FRAME_SELECT_VERTICAL",false);
				formObject.setNGEnable("FRAME_PRODUCT",false);
				formObject.setNGEnable("FRAME_PROD_ABBR_CODE",false);
				formObject.setNGEnable("FRAME_TXN_TYPE",false);
				formObject.setNGEnable("FRAME_PREV_POLICY_NUMBER",false);
				formObject.setNGEnable("FRAME_IAGENT_PROPOSAL",false);
				formObject.setNGEnable("FRAME_SEARCH_BANK",false);
				formObject.setNGEnable("FRAME_CHEQUE_DD",false);
				formObject.setNGEnable("FRAME_SAVINGS_ACCOUNT",false);
				formObject.setNGEnable("FRAME_CREDIT_CARD",false);
				formObject.setNGEnable("FRAME_PID_NO",false);
				formObject.setNGEnable("FRAME_SALESMGR_EMPID",false);
				formObject.setNGEnable("FRAME_IF_PRODUCT_HSP",false);
				formObject.setNGEnable("FRAME_MISC",false);
				formObject.setNGEnable("FRAME_SOURCE_BUSINESS",false);
				formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",false);
				formObject.setNGEnable("FRAME_IS_EMP_CODE",false);
				formObject.setNGEnable("FRAME_WRE_WRM",false);
				formObject.setNGEnable("FRAME_CHANNEL_EMP_INFO",false);
				formObject.setNGEnable("FRAME_BSM_BCM",false);
				formObject.setNGEnable("FRAME_CENTER_CODE_RM",false);
				//formObject.setNGEnable("FRAME_SUB_BROKER",false);//CR25_Masters of KRG,Removal of fields & RMT Bucket
				formObject.setNGEnable("FRAME_PATHFINDER_STATUS",false);
				formObject.setNGEnable("FRAME_DEQC_DECISION",false);
				//formObject.setNGEnable("FRAME_UNIQUE_ID",true);//CR25_Masters of KRG,Removal of fields & CR26 RMT Bucket 
				formObject.setNGEnable("ICICILOMBARD_HT_CUSTOMERNUMBER", false);
				formObject.setNGVisible("FRM_CUSTOMER_MOBILE_NO",true);
				
				if(formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking (BBG)") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || (formObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BBG") && formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG")))
				{
					formObject.setNGEnable("ICICILOMBARD_HT_PRIVILEGE_BANKER_CODE",true); 	
				}
				else
				{
					formObject.setNGEnable("ICICILOMBARD_HT_PRIVILEGE_BANKER_CODE",false); 
				}
				// code added by manoj
			
				if(formObject.getNGValue("ICICILOMBARD_HT_TRANSACTION_TYPE").equalsIgnoreCase("Rollover"))
				{
				formObject.setNGEnable("FRAME_TXN_TYPE",true);
				formObject.setNGEnable("ICICILOMBARD_HT_TRANSACTION_TYPE",false);
				formObject.setNGEnable("BTN_PRT_POPUP", true); // portability button is visible for transction type = "Rollover"
				}
				else
				{
				formObject.setNGEnable("FRAME_TXN_TYPE",false);
				}
			}  */
			
			else if(wsName.equalsIgnoreCase("EXCEPTION") || wsName.equalsIgnoreCase("RM_EXCEPTION")) //PID-HT process changes
			{
				//System.out.println("inside excption load");
				//formObject.setNGVisible("FRAME_UNIQUE_ID",false);//CR25_Masters of KRG,Removal of fields & CR26 RMT Bucket
				formObject.setNGVisible("FRAME_COPS_DECISION", false);
				formObject.setNGVisible("FRAME_DEQC_DECISION", false);
				formObject.setNGVisible("FRAME_PATHFINDER_STATUS", false); 
				/******************************* PID-HT process changes ********************************/
				String prevWs = formObject.getNGValue("ICICILOMBARD_HT_PREVIOUSWORKSTEP");
				String consPrevWs = formObject.getNGValue("ICICILOMBARD_HT_CONS_PREV_WORKSTEP");
				RouteTo_ClarificationWS(prevWs,consPrevWs,wsName);
				/******************************End PID-HT process changes ******************************/
				
				formObject.setNGVisible("FRAME_SEARCH_CRITERIA", true);
				formObject.setNGVisible("FRAME_ROUTETO", true);//PID-HT process changes
				
				/********************** Start Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR ***********************/
				
				//System.out.println(" Inside EXCEPTION/RM_EXCEPTION Enabling FRM_FETCH_COPY For HT- CR-8093-59790 (HT-FlapPrint) CR");	
				formObject.setNGVisible("FRM_FETCH_COPY",true);
				formObject.setNGEnable("FRM_FETCH_COPY", true);
					
				/********************** End Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR *************************/
				formObject.setNGVisible("ICICILOMBARD_HT_WorkItemName", false);

				formObject.setNGEnable("FRAME_SELECT_SUB_VERTICAL",false);
				formObject.setNGEnable("FRAME_SELECT_VERTICAL",false);
				formObject.setNGEnable("FRAME_PRODUCT",true);
				formObject.setNGEnable("FRAME_PROD_ABBR_CODE",true);
				formObject.setNGEnable("FRAME_TXN_TYPE",true);
				formObject.setNGEnable("FRAME_PREV_POLICY_NUMBER",false);
				formObject.setNGEnable("FRAME_IAGENT_PROPOSAL",true);
				formObject.setNGEnable("FRAME_SEARCH_BANK",true);
				formObject.setNGEnable("ICICILOMBARD_HT_BANK_NAME",false);
				formObject.setNGEnable("ICICILOMBARD_HT_CUSTOMERNUMBER", false);
				formObject.setNGVisible("FRM_CUSTOMER_MOBILE_NO",true);

				formObject.setNGControlTop("FRAME_SEARCH_CRITERIA",iTemp1);
				formObject.setNGControlLeft("FRAME_SEARCH_CRITERIA",left);
				iTemp1+=186;	
				formObject.setNGControlTop("FRAME_DATA_ENTRY_PARENT",iTemp1);
				formObject.setNGControlLeft("FRAME_DATA_ENTRY_PARENT",left);
				
				/******************************* PID-HT process changes ********************************/
				iTemp+=34;
				formObject.setNGControlTop("FRAME_ROUTETO",iTemp);
				formObject.setNGControlLeft("FRAME_ROUTETO",left);
				formObject.setNGControlWidth("FRAME_ROUTETO",width);
				iTemp+=56; // Start Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR
				/******************************End PID-HT process changes ******************************/

				formObject.setNGControlTop("FRM_CUSTOMER_MOBILE_NO",iTemp);
				formObject.setNGControlLeft("FRM_CUSTOMER_MOBILE_NO",left);
				formObject.setNGControlWidth("FRM_CUSTOMER_MOBILE_NO",width);
				
				
				iTemp+=34;
				//System.out.println("After FRM_CUSTOMER_MOBILE_NO At EXCEPTION/RM_EXCEPTION");
				
				/********************** Start Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR ***********************/
				
				//System.out.println("Setting New  Frame FRM_FETCH_COPY At EXCEPTION/RM_EXCEPTION For HT- CR-8093-59790 (HT-FlapPrint) CR");
				formObject.setNGControlTop("FRM_FETCH_COPY",iTemp);
				formObject.setNGControlLeft("FRM_FETCH_COPY",left);
				formObject.setNGControlWidth("FRM_FETCH_COPY",width);
				iTemp+=38;  // 34 now changed to 41 NOW 45

				/********************** End Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR *************************/
				/******Start HT CR-8127-95325-GST-Omniflow development******/
				/*System.out.println("Inside Exception and RM_EXCEPTION FRAME_GST_GRID setting Yogesh:");
				formObject.setNGControlTop("FRAME_GST_GRID",iTemp);
				formObject.setNGControlLeft("FRAME_GST_GRID",left);
				formObject.setNGControlWidth("FRAME_GST_GRID",width);
				iTemp+=300;*/
				/******End HT CR-8127-95325-GST-Omniflow development******/
				
				formHeight=iTemp+400;  // It was 350 now changed to 400 Start Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR
				formObject.setNGFormHeight(formHeight);
				//System.out.println("formHeight==="+formHeight);
				formObject.setNGEnable("ICICILOMBARD_HT_PRODUCT",false);		
				formObject.setNGEnable("FRAME_SOURCE_BUSINESS",true);
				
				
				/************************ Start CR-OMHT-1314-01 Wallet_Insurance************/					
				/*if(formObject.getNGValue("ICICILOMBARD_HT_DEAL_NO") != null && !formObject.getNGValue("ICICILOMBARD_HT_DEAL_STATUS").equalsIgnoreCase("YES"))
				{
					formObject.setNGEnable("ICICILOMBARD_HT_IL_LOCATION",false);
						
				}*/
				/************************ End CR-OMHT-1314-01 Wallet_Insurance************/
				//Vishal i-Partner//
				//enabling banker code,wre,wrm,empcode on bbg.branch banking,home,krg2				
				if(formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking (BBG)") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || (formObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BBG") && formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG")))
				{
					formObject.setNGEnable("ICICILOMBARD_HT_PRIVILEGE_BANKER_CODE",true); 	
					formObject.setNGEnable("FRAME_IS_EMP_CODE",true); 	
					formObject.setNGEnable("FRAME_WRE_WRM", true);					
				}
				else
				{
					formObject.setNGEnable("ICICILOMBARD_HT_PRIVILEGE_BANKER_CODE",false); 
					formObject.setNGEnable("FRAME_IS_EMP_CODE",false); 
					formObject.setNGEnable("FRAME_WRE_WRM", false);
				}
				//end
				
				//enabling disabling the frame hsp
			//Vishal_HSP
						if((formObject.getNGValue("ICICILOMBARD_HT_PRODUCT_ABBR_CODE").equalsIgnoreCase("hsp") && !formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("COMPLETE HEALTH INSURANCE"))
						/*********************** CR 24 HSP product by satish ************************/
						|| formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("HOMEINSURANCEPRODUCT") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("SECURE MIND")|| formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("Secure Mind A") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("GROUP SECURE MIND") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("GROUP PERSONAL ACCIDENT") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("CCPI") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("Asset Protect") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("Merchant Cover") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("Home")
						/********************* end CR 24 HSP product by satish **********************/
						)
						{
							//System.out.println("Vishal_HSP true");
							//System.out.println("ICICILOMBARD_HT_PRODUCT:- "+formObject.getNGValue("ICICILOMBARD_HT_PRODUCT"));
							formObject.setNGEnable("FRAME_IF_PRODUCT_HSP",true);
							/****************** CR 24 HSP product by satish *********************/
							if(!(formObject.getNGValue("ICICILOMBARD_HT_PRODUCT_ABBR_CODE").equalsIgnoreCase("hsp") && !formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("COMPLETE HEALTH INSURANCE")))
							{
								formObject.setNGEnable("ICICILOMBARD_HT_IAGENT",false);
							}
							/**************** end CR 24 HSP product by satish *******************/
						}
						else
						{
							//System.out.println("Vishal_HSP false");
							/************************ Start CR-OMHT-1314-01 Wallet_Insurance************/
							if(formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("WALLET INSURANCE") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("RETAIL PURE KHOKHA")) //adding new product RETAIL PURE KHOKHA
							{
								formObject.setNGEnable("ICICILOMBARD_HT_LAN",false);

							}
							/************************ End CR-OMHT-1314-01 Wallet_Insurance************/
							formObject.setNGEnable("FRAME_IF_PRODUCT_HSP",false);
							formObject.setNGEnable("ICICILOMBARD_HT_IAGENT",true);  //Added  ICICILOMBARD_HT_IAGENT = true For Flapprint Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR
							// cr 24
							/**********start adding new product RETAIL PURE KHOKHA************/
							/*if(formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("RETAIL PURE KHOKHA"))
							{
								formObject.setNGEnable("ICICILOMBARD_HT_IAGENT",false);
							}
							else
							{
								formObject.setNGEnable("ICICILOMBARD_HT_IAGENT",true);

							}FlapPrint Code Commented Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR*/
							/**********end adding new product RETAIL PURE KHOKHA**************/
						}
				//end
				
				//enabling disabling the Product
				// Yogendra Saraswat --  for CRITICAL CARE case--
				if(formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("CRITICAL CARE") 
				/************************** CR 24 HSP product by satish **************************/
				|| formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("HOMEINSURANCEPRODUCT") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("SECURE MIND")|| formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("Secure Mind A") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("GROUP SECURE MIND") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("GROUP PERSONAL ACCIDENT") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("CCPI") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("Asset Protect") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("Merchant Cover") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("Home")|| formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("GROUP SAFEGUARD INSURANCE")
				/************************ end CR 24 HSP product by satish ************************/
				)
				{
					formObject.setNGEnable("ICICILOMBARD_HT_SUB_PRODUCT",false);
					/*********************** CR 24 HSP product by satish ***********************/
					/*if(!(formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("CRITICAL CARE"))) //CR-OMHT-1314-01-Wallet_Insurance
					{
						formObject.setNGEnable("ICICILOMBARD_HT_IAGENT",false);

					}FlapPrint Code Commented Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR*/
					/********************* end CR 24 HSP product by satish *********************/
					
				}/************************ Start CR-OMHT-1314-01 Wallet_Insurance************/
				else if(formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("WALLET INSURANCE") ||  formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("RETAIL PURE KHOKHA"))
				{
					formObject.setNGEnable("ICICILOMBARD_HT_SUB_PRODUCT",false);
					formObject.setNGEnable("FRAME_PROD_ABBR_CODE",false);
					formObject.setNGEnable("ICICILOMBARD_HT_LAN",false);
				} /************************ End CR-OMHT-1314-01 Wallet_Insurance************/
				/***********************Start CR Motor,Home & Travel processing*************/
				else if(formObject.getNGValue("ICICILOMBARD_HT_PRODUCT") != null)
				{
					String field[]= {"ProductList"};
					String productlistQuery = "select productname,producttype from NG_ICICI_MST_Product";
					noOfCols="2";
					getData(productlistQuery,noOfCols,field,flag);
					if(map.size() > 1 && map.containsKey(formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").trim()))
					{
						//formObject.setNGEnable("ICICILOMBARD_HT_IAGENT",true);
						//System.out.println("map exception bucket=="+map.containsKey(formObject.getNGValue("ICICILOMBARD_HT_PRODUCT")));
						//System.out.println("product exception bucket=="+formObject.getNGValue("ICICILOMBARD_HT_PRODUCT"));
						formObject.setNGEnable("ICICILOMBARD_HT_SUB_PRODUCT",false);
						//formObject.setNGEnable("ICICILOMBARD_HT_PRODUCT_ABBR_CODE",false);
						formObject.setNGEnable("FRAME_PROD_ABBR_CODE",false);
						map = null;
						
					}
					else
					{
						
						formObject.setNGEnable("ICICILOMBARD_HT_SUB_PRODUCT",true);
						//cr 24
						//formObject.setNGEnable("ICICILOMBARD_HT_IAGENT",true);FlapPrint Code Commented Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR*/
					}
					
				} /***********************End CR Motor,Home & Travel processing***************/
				else
				{
					formObject.setNGEnable("ICICILOMBARD_HT_SUB_PRODUCT",true);
					//cr 24
					//formObject.setNGEnable("ICICILOMBARD_HT_IAGENT",true);FlapPrint Code Commented Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR*/
				}
				// Yogendra Saraswat -- end for CRITICAL CARE case--
				//end
				
				//enabling  disabling the frame source busness,channel source,bank branch name
				if(formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking (BBG)") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)"))
//				|| formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("HOME"))
// 				Disabled by Yogendra Saraswat
//				|| formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("krg2"))
				{
					formObject.setNGEnable("ICICILOMBARD_HT_SOURCE_BUSINESS",true); 
					formObject.setNGEnable("ICICILOMBARD_HT_CHANNEL_SOURCE",true); 
					formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",true); 
					if(formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("HOME"))
						{
							formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",false);
						}
				}
				else
				{
					formObject.setNGEnable("ICICILOMBARD_HT_SOURCE_BUSINESS",false); 
					formObject.setNGEnable("ICICILOMBARD_HT_CHANNEL_SOURCE",false); 
					formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",false); 
				}
				//end
				//enabling  disabling the bsm bcm on home sub vertical
				if(formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("home"))
				{
					formObject.setNGEnable("FRAME_BSM_BCM",true); 
				}
				else
				{
					formObject.setNGEnable("FRAME_BSM_BCM",false); 
				}
				//end
				
				//prev policy
				/********************** Start Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR ***********************/
					
				if(formObject.getNGValue("ICICILOMBARD_HT_TRANSACTION_TYPE").equalsIgnoreCase("Renewal") || formObject.getNGValue("ICICILOMBARD_HT_TRANSACTION_TYPE").equalsIgnoreCase("Rollover"))
				{
					//System.out.println("Inside EXCEPTION for Policy No For HT- CR-8093-59790 (HT-FlapPrint) CR");
					//System.out.println("FRAME_PREV_POLICY_NUMBER:- "+formObject.getNGValue("ICICILOMBARD_HT_TRANSACTION_TYPE"));
					formObject.setNGEnable("FRAME_PREV_POLICY_NUMBER",true);
						
				}
				else
				{
					//System.out.println("INSIDE EXCEPTION Policy No For HT- CR-8093-59790 (HT-FlapPrint) CR");
					//System.out.println("FRAME_PREV_POLICY_NUMBER:- "+formObject.getNGValue("ICICILOMBARD_HT_TRANSACTION_TYPE"));
					formObject.setNGEnable("FRAME_PREV_POLICY_NUMBER",false);
						
				}
				
				/********************** End Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR *************************/
				
				/********************** Start Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR ***********************/

				/*//System.out.println("Checking ICICILOMBARD_HT_IAGENT Value");
				if(formObject.getNGValue("ICICILOMBARD_HT_IAGENT").equalsIgnoreCase("Yes"))
				{
					//System.out.println("Enabling ICICILOMBARD_HT_IAGENT For ICICILOMBARD_HT_IAGENT");
					//System.out.println("Checking Value Of ICICILOMBARD_HT_IAGENT :- "+formObject.getNGValue("ICICILOMBARD_HT_IAGENT"));
					formObject.setNGEnable("ICICILOMBARD_HT_IAGENT",true);
				}
				else
				{
					//System.out.println("Disabling ICICILOMBARD_HT_IAGENT For ICICILOMBARD_HT_IAGENT");
					//System.out.println("Checking Value Of ICICILOMBARD_HT_IAGENT :- "+formObject.getNGValue("ICICILOMBARD_HT_IAGENT"));
					formObject.setNGEnable("ICICILOMBARD_HT_IAGENT",false);
				}*/

			/********************** End Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR *************************/

			}



			//if the workstep is work exit
			else if(wsName.equalsIgnoreCase("Work Exit"))
			{
				if(debug==1)
				{
					//System.out.println("inside cops team");
				}
				//System.out.println("inside cops qc team");
				//formObject.setNGVisible("FRAME_UNIQUE_ID",true);//CR25_Masters of KRG,Removal of fields & CR26 RMT Bucket
				formObject.setNGVisible("FRAME_COPS_DECISION", false);
				formObject.setNGVisible("FRAME_PATHFINDER_STATUS", true);
				formObject.setNGVisible("FRAME_SEARCH_CRITERIA", true);
				
				formObject.setNGVisible("FRAME_DEQC_DECISION", true);

				formObject.setNGControlTop("FRAME_SEARCH_CRITERIA",iTemp1);
				formObject.setNGControlLeft("FRAME_SEARCH_CRITERIA",left);
				iTemp1+=186;	
				formObject.setNGControlTop("FRAME_DATA_ENTRY_PARENT",iTemp1);
				formObject.setNGControlLeft("FRAME_DATA_ENTRY_PARENT",left);
			
				//iTemp+=168;
				iTemp+=130;//CR25_Masters of KRG,Removal of fields & CR26 RMT Bucket
				formObject.setNGControlTop("FRM_CUSTOMER_MOBILE_NO",iTemp);
				formObject.setNGControlLeft("FRM_CUSTOMER_MOBILE_NO",left);
				formObject.setNGControlWidth("FRM_CUSTOMER_MOBILE_NO",width);

				formHeight=iTemp+350;
				
				
				formObject.setNGFormHeight(formHeight);
	
				formObject.setNGEnable("FRAME_SELECT_SUB_VERTICAL",false);
				formObject.setNGEnable("FRAME_SELECT_VERTICAL",false);
				formObject.setNGEnable("FRAME_PRODUCT",false);
				formObject.setNGEnable("FRAME_PROD_ABBR_CODE",false);
				formObject.setNGEnable("FRAME_TXN_TYPE",false);
				formObject.setNGEnable("FRAME_PREV_POLICY_NUMBER",false);
				formObject.setNGEnable("FRAME_IAGENT_PROPOSAL",false);
				formObject.setNGEnable("FRAME_SEARCH_BANK",false);
				formObject.setNGEnable("FRAME_CHEQUE_DD",false);
				formObject.setNGEnable("FRAME_SAVINGS_ACCOUNT",false);
				formObject.setNGEnable("FRAME_CREDIT_CARD",false);
				formObject.setNGEnable("FRAME_PID_NO",false);
				formObject.setNGEnable("FRAME_SALESMGR_EMPID",false);
				formObject.setNGEnable("FRAME_IF_PRODUCT_HSP",false);
				formObject.setNGEnable("FRAME_MISC",false);
				formObject.setNGEnable("FRAME_SOURCE_BUSINESS",false);
				formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",false);
				formObject.setNGEnable("FRAME_IS_EMP_CODE",false);
				formObject.setNGEnable("FRAME_WRE_WRM",false);
				formObject.setNGEnable("FRAME_CHANNEL_EMP_INFO",false);
				formObject.setNGEnable("FRAME_BSM_BCM",false);
				formObject.setNGEnable("FRAME_CENTER_CODE_RM",false);
				//formObject.setNGEnable("FRAME_SUB_BROKER",false);//CR25_Masters of KRG,Removal of fields & RMT Bucket
				formObject.setNGEnable("FRAME_PATHFINDER_STATUS",true);

				formObject.setNGEnable("FRAME_DEQC_DECISION",false);
				formObject.setNGEnable("ICICILOMBARD_HT_CUSTOMERNUMBER", false);
				formObject.setNGVisible("FRM_CUSTOMER_MOBILE_NO",true);
				
				if(formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking (BBG)") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || (formObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BBG") && formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG")))
				{
					formObject.setNGEnable("ICICILOMBARD_HT_PRIVILEGE_BANKER_CODE",true); 	
				}
				else
				{
					formObject.setNGEnable("ICICILOMBARD_HT_PRIVILEGE_BANKER_CODE",false); 
				}
				
				// code added by manoj
			
				if(formObject.getNGValue("ICICILOMBARD_HT_TRANSACTION_TYPE").equalsIgnoreCase("Rollover"))
				{
				formObject.setNGEnable("FRAME_TXN_TYPE",true);
				
				formObject.setNGEnable("ICICILOMBARD_HT_TRANSACTION_TYPE",false);
				formObject.setNGEnable("BTN_PRT_POPUP", true); // portability button is visible for transction type = "Rollover"
				}
				else
				{
				formObject.setNGEnable("FRAME_TXN_TYPE",false);
				}
			
			}//work exit end	
			
			//if the workstep is Discard exit
			else if(wsName.equalsIgnoreCase("Discard_Exit"))
			{
				if(debug==1)
				{
					//System.out.println("inside cops team");
				}
				//System.out.println("inside cops qc team");
				//formObject.setNGVisible("FRAME_UNIQUE_ID",true);//CR25_Masters of KRG,Removal of fields & CR26 RMT Bucket
				formObject.setNGVisible("FRAME_COPS_DECISION", false);
				formObject.setNGVisible("FRAME_PATHFINDER_STATUS", true);
				formObject.setNGVisible("FRAME_SEARCH_CRITERIA", true);
				
				formObject.setNGVisible("FRAME_DEQC_DECISION", true);
				
				formObject.setNGControlTop("FRAME_SEARCH_CRITERIA",iTemp1);
				formObject.setNGControlLeft("FRAME_SEARCH_CRITERIA",left);
				iTemp1+=186;	
				formObject.setNGControlTop("FRAME_DATA_ENTRY_PARENT",iTemp1);
				formObject.setNGControlLeft("FRAME_DATA_ENTRY_PARENT",left);
		
				//iTemp+=168;
				iTemp+=130; //CR25_Masters of KRG,Removal of fields & CR26 RMT Bucket
				formObject.setNGControlTop("FRM_CUSTOMER_MOBILE_NO",iTemp);
				formObject.setNGControlLeft("FRM_CUSTOMER_MOBILE_NO",left);
				formObject.setNGControlWidth("FRM_CUSTOMER_MOBILE_NO",width);

				formHeight=iTemp+350;
								
				formObject.setNGFormHeight(formHeight);

				formObject.setNGEnable("FRAME_SELECT_SUB_VERTICAL",false);
				formObject.setNGEnable("FRAME_SELECT_VERTICAL",false);
				formObject.setNGEnable("FRAME_PRODUCT",false);
				formObject.setNGEnable("FRAME_PROD_ABBR_CODE",false);
				formObject.setNGEnable("FRAME_TXN_TYPE",false);
				formObject.setNGEnable("FRAME_PREV_POLICY_NUMBER",false);
				formObject.setNGEnable("FRAME_IAGENT_PROPOSAL",false);
				formObject.setNGEnable("FRAME_SEARCH_BANK",false);
				formObject.setNGEnable("FRAME_CHEQUE_DD",false);
				formObject.setNGEnable("FRAME_SAVINGS_ACCOUNT",false);
				formObject.setNGEnable("FRAME_CREDIT_CARD",false);
				formObject.setNGEnable("FRAME_PID_NO",false);
				formObject.setNGEnable("FRAME_SALESMGR_EMPID",false);
				formObject.setNGEnable("FRAME_IF_PRODUCT_HSP",false);
				formObject.setNGEnable("FRAME_MISC",false);
				formObject.setNGEnable("FRAME_SOURCE_BUSINESS",false);
				formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",false);
				formObject.setNGEnable("FRAME_IS_EMP_CODE",false);
				formObject.setNGEnable("FRAME_WRE_WRM",false);
				formObject.setNGEnable("FRAME_CHANNEL_EMP_INFO",false);
				formObject.setNGEnable("FRAME_BSM_BCM",false);
				formObject.setNGEnable("FRAME_CENTER_CODE_RM",false);
				//formObject.setNGEnable("FRAME_SUB_BROKER",false);//CR25_Masters of KRG,Removal of fields & RMT Bucket
				formObject.setNGEnable("FRAME_PATHFINDER_STATUS",true);

				formObject.setNGEnable("FRAME_DEQC_DECISION",false);
				formObject.setNGEnable("ICICILOMBARD_HT_CUSTOMERNUMBER", false);
				formObject.setNGVisible("FRM_CUSTOMER_MOBILE_NO",true);
				
				if(formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking (BBG)") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || (formObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BBG") && (formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL")).equalsIgnoreCase("BBG")))
				{
					formObject.setNGEnable("ICICILOMBARD_HT_PRIVILEGE_BANKER_CODE",true); 	
				}
				else
				{
					formObject.setNGEnable("ICICILOMBARD_HT_PRIVILEGE_BANKER_CODE",false); 
				}
				
				// code added by manoj
			
				if(formObject.getNGValue("ICICILOMBARD_HT_TRANSACTION_TYPE").equalsIgnoreCase("Rollover"))
				{
				formObject.setNGEnable("FRAME_TXN_TYPE",true);
				//System.out.println("ICICILOMBARD_HT_TRANSACTION_TYPE"+formObject.getNGValue("ICICILOMBARD_HT_TRANSACTION_TYPE"));
				formObject.setNGEnable("ICICILOMBARD_HT_TRANSACTION_TYPE",false);
				formObject.setNGEnable("BTN_PRT_POPUP", true); // portability button is visible for transction type = "Rollover"
				}
				else
				{
				formObject.setNGEnable("FRAME_TXN_TYPE",false);
				}
			
			}//discard exit end
			
			else{}
		}
	
		catch(Exception e)
		{
			e.printStackTrace();
		}	
	}

	//this method will be executed for all the registered commands.

	public String executeHTCommand(XMLParser generalDataParser,String strInputXml,String sCommandName,AppletContext appletContext1)
	{
		try
		{	
			//setting the parameters required of the form
			
			generalDataParser.setInputXML(formObject.getWFGeneralData());
			wsName = generalDataParser.getValueOf("ActivityName").toUpperCase();
			if(debug==1)
			{
				//System.out.println("inside execute comaand HT--- "+wsName);
			}		
			if(wsName.equalsIgnoreCase("BSG_DATAENTRY"))
			{	
				//code for BTN_SEARCH_STRING for Search Criteria
				if(debug==1)
				{
					//System.out.println("in data entry comd");
				}
							
				//method when the user clicks the submit serach button
			    if(sCommandName.equalsIgnoreCase("SUBMIT_SEARCH"))
				{
					formObject.setNGLocked("ICICILOMBARD_HT_DEAL_IL_LOCATION",false);//sp code yogesh CR-8093-69682 
					if(formObject.getNGValue("ICICILOMBARD_HT_AGENT_NAME").equals("") || formObject.getNGValue("ICICILOMBARD_HT_AGENT_CODE").equals("") || formObject.getNGValue("ICICILOMBARD_HT_DEAL_NO").equals(""))
					{
						JOptionPane.showMessageDialog(null,"Agent Name/Agent Code/Deal No cannot be left blank  !!");
						formObject.NGClear("ICICILOMBARD_HT_SUB_VERTICAL");
						formObject.NGClear("ICICILOMBARD_HT_PRODUCT");
						formObject.NGClear("ICICILOMBARD_HT_SM_ID");
						formObject.NGClear("ICICILOMBARD_HT_SM_NAME");
						/******Start HT SP code CR-8093-69682 Yogesh*********/
						if((formObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE"))&&(formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING")))
						{
						formObject.NGClear("ICICILOMBARD_HT_DEAL_IL_LOCATION");
						formObject.NGClear("ICICILOMBARD_HT_SOURCE_BUSINESS");
						formObject.NGClear("ICICILOMBARD_HT_CHANNEL_SOURCE");
						formObject.NGClear("ICICILOMBARD_HT_BANK_BRANCH_NAME");
						formObject.NGClear("ICICILOMBARD_HT_SOL_ID");
						formObject.NGClear("ICICILOMBARD_HT_SP_NAME");
						formObject.NGClear("ICICILOMBARD_HT_WRE");
						formObject.NGClear("ICICILOMBARD_HT_SP_PAN");
						/******End HT SP code CR-8093-69682 Yogesh*********/
						}
					}
					else
					{
						ICICI_Clear_Control clearControl= new ICICI_Clear_Control(formObject);
						boolean bool=clearControl.icici_clear_all_control();
						
						formObject.NGClear("ICICILOMBARD_HT_SUB_VERTICAL");
						formObject.NGClear("ICICILOMBARD_HT_PRODUCT");
						formObject.NGClear("ICICILOMBARD_HT_SM_ID");
						formObject.NGClear("ICICILOMBARD_HT_SM_NAME");
						
						/******Start HT SP code CR-8093-69682 Yogesh*********/
						if((formObject.getNGValue("ICICILOMBARD_HT_DEAL_STATUS").equalsIgnoreCase("Yes")) && (formObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE"))&&(formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING")))
						{
						formObject.NGClear("ICICILOMBARD_HT_DEAL_IL_LOCATION");
						formObject.NGClear("ICICILOMBARD_HT_SOURCE_BUSINESS");
						formObject.NGClear("ICICILOMBARD_HT_CHANNEL_SOURCE");
						formObject.NGClear("ICICILOMBARD_HT_BANK_BRANCH_NAME");
						formObject.NGClear("ICICILOMBARD_HT_SOL_ID");
						formObject.NGClear("ICICILOMBARD_HT_SP_NAME");
						formObject.NGClear("ICICILOMBARD_HT_WRE");
						formObject.NGClear("ICICILOMBARD_HT_SP_PAN");	
						}
						/******End HT SP code CR-8093-69682 Yogesh*********/
						formObject.setNGEnable("ICICILOMBARD_HT_IL_LOCATION",true); //CR-OMHT-1314-01-Wallet_Insurance
					/******************************* PID-HT process changes ********************************/	
					//Fetching vertical and sub vertical code also for PID-HT process changes
						String arrfieldVertical[]= new String[2];
						fieldValue=formObject.getNGValue("ICICILOMBARD_HT_DEAL_NO");
						noOfCols="2";
																		
						arrfieldVertical[0]="ICICILOMBARD_HT_SUB_VERTICAL";
						arrfieldVertical[1]="ICICILOMBARD_HT_SUB_VERTICAL_CODE";
						//ICICILOMBARD_HT_SUB_VERTICAL represents the SecondaryVertical
						//query="select distinct gt.TXT_FULL_VERTICAL_DESC from MV_GENMST_TAB_VERTICAL_N gt,MV_Gen_Deal_Detail gd where gt.TXT_DEAL_ID = gd.TXT_DEAL_ID and gd.TXT_DEAL_ID=N'"+fieldValue+"' and gt.NUM_SEC_VERTICAL_CD=gd.NUM_PRIM_VERTICAL_CD";
						
						query="select distinct gt.TXT_FULL_VERTICAL_DESC, gt.NUM_SEC_VERTICAL_CD from MV_GENMST_TAB_VERTICAL_N gt,MV_Gen_Deal_Detail gd where gt.TXT_DEAL_ID = gd.TXT_DEAL_ID and gd.TXT_DEAL_ID=N'"+fieldValue+"' and gt.NUM_SEC_VERTICAL_CD=gd.NUM_PRIM_VERTICAL_CD";						
						getData(query,noOfCols,arrfieldVertical,flag);
						
						/***** CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket*******/
						//ICICILOMBARD_HT_VERTICAL represents the PrimaryVertical 
						arrfieldVertical[0]="ICICILOMBARD_HT_VERTICAL";
						arrfieldVertical[1]="ICICILOMBARD_HT_VERTICAL_CODE";
						//query = "select txt_full_vertical_desc from ng_ht_test_ver_mas";
						//query = "select distinct gt.txt_full_prim_vertical_desc from MV_GENMST_TAB_VERTICAL_N gt,MV_Gen_Deal_Detail gd  where gt.TXT_DEAL_ID = gd.TXT_DEAL_ID and gt.TXT_DEAL_ID=N'"+fieldValue+"'";
						
						query = "select distinct gt.TXT_FULL_PRIM_VERTICAL_DESC, gt.NUM_PRIM_VERTICAL_CD from MV_GENMST_TAB_VERTICAL_N gt,MV_Gen_Deal_Detail gd  where gt.TXT_DEAL_ID = gd.TXT_DEAL_ID and gt.TXT_DEAL_ID=N'"+fieldValue+"'";
						getData(query,noOfCols,arrfieldVertical,flag);
						/***** End CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket***/
						/******************************End PID-HT process changes ******************************/
				
						String arrfieldName5[]= new String[1];
						noOfCols="1";
						arrfieldName5[0]="ICICILOMBARD_HT_PRODUCT";
						query="select distinct gt.PRODUCTNAME from MV_UW_PRODUCT_MASTER gt,MV_Gen_Deal_Detail gd where gd.TXT_DEAL_ID=N'"+fieldValue+"' and gt.PRODUCTCODE=gd.NUM_PRODUCT_CODE";						
						getData(query,noOfCols,arrfieldName5,flag);
						
			            // code added by Yogendra Saraswat		
						arrfieldName5[0]="ICICILOMBARD_HT_CENTER_CODE_NAME";
						query="select CENTCODE_NAME  from  NG_ICICI_MST_CENTERCODE order by CENTCODE_NAME";						
						getData(query,noOfCols,arrfieldName5,flag);
						
						/************************ Start CR-OMHT-1314-01 Wallet_Insurance************/
						
						/**************************************SP Code CR CR-8093-69682*****************************************************/
						if((formObject.getNGValue("ICICILOMBARD_HT_DEAL_STATUS").equalsIgnoreCase("Yes")) && (formObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE"))&&(formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING")))
						{
							//System.out.println("enable if condition");
							formObject.setNGEnable("ICICILOMBARD_HT_DEAL_IL_LOCATION",true);
							formObject.setNGLocked("ICICILOMBARD_HT_DEAL_IL_LOCATION",false);
						}
						else
						{
							//System.out.println("enable else condition");
							formObject.setNGEnable("ICICILOMBARD_HT_DEAL_IL_LOCATION",false);
						}
						if((formObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE"))&&(formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)")))
						{
							System.out.println("Inside executeHTCommand sp code :");
							formObject.setNGEnable("ICICILOMBARD_HT_WRE",true);
							formObject.setNGLocked("ICICILOMBARD_HT_WRE",false);
						}
						cenDealStatus = formObject.getNGValue("ICICILOMBARD_HT_DEAL_STATUS");
						//System.out.println("cenDealStatus=="+cenDealStatus);
						if(!cenDealStatus.equalsIgnoreCase("YES") && (formObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE"))&&(formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING")))
						{
							//System.out.println("Inside the if condition cenDealStatus satish=="+cenDealStatus);
							arrfieldName5[0]="ICICILOMBARD_HT_DEAL_IL_LOCATION";
							String productlistQuery = "select il.txt_office from MV_GENMST_OFFICE il,MV_Gen_Deal_Detail gd where il.num_office_cd = gd.num_office_cd and gd.TXT_DEAL_ID=N'"+fieldValue+"'";
							noOfCols="1";
							//System.out.println("Inside the Deal Location before getData() execution query"+productlistQuery);
							getData(productlistQuery,noOfCols,arrfieldName5,flag);
							//System.out.println("Inside the Deal Location After getData() execution");
							formObject.setNGEnable("ICICILOMBARD_HT_DEAL_IL_LOCATION",false);
						}
						/**************************************End SP Code CR CR-8093-69682*****************************************************/
						
						/*cenDealStatus = formObject.getNGValue("ICICILOMBARD_HT_DEAL_STATUS");
						//System.out.println("cenDealStatus=="+cenDealStatus);
						if(!cenDealStatus.equalsIgnoreCase("YES"))
						{
							//System.out.println("cenDealStatus satish=="+cenDealStatus);
							String arrILLocFields[]= {"ICICILOMBARD_HT_IL_LOCATION","ICICILOMBARD_HT_IL_LOCATION_CODE"};
							String productlistQuery = "select il.txt_office,il.num_office_cd from MV_GENMST_OFFICE il,MV_Gen_Deal_Detail gd where il.num_office_cd = gd.num_office_cd and gd.TXT_DEAL_ID=N'"+fieldValue+"'";
							noOfCols="2";
							getData(productlistQuery,noOfCols,arrILLocFields,flag);
							formObject.setNGEnable("ICICILOMBARD_HT_IL_LOCATION",false);
							//formObject.setNGEnable("ICICILOMBARD_HT_IL_LOCATION_CODE",false);
						} */
						
						/************************ End CR-OMHT-1314-01 Wallet_Insurance************/
						
						formObject.setNGEnable("FRAME_SOURCE_BUSINESS",true);
						
					}	
				}
				
				 // Yogendra Saraswat --  for CRITICAL CARE case--
				else if(sCommandName.equalsIgnoreCase("SELECT_PRODUCT_HSP"))
				{
					fieldName="ICICILOMBARD_HT_PRODUCT";
					if(debug==1)
					{
						//System.out.println("inside command product");
					}
					fieldValue=formObject.getNGValue(fieldName);
					
					//changes
					formObject.NGClear("ICICILOMBARD_HT_SUB_PRODUCT");
					
					if(fieldValue.equalsIgnoreCase("CRITICAL CARE") 
					//************** CR 24 HSP product by satish ****************/
					|| fieldValue.equalsIgnoreCase("HOMEINSURANCEPRODUCT") || fieldValue.equalsIgnoreCase("SECURE MIND")|| fieldValue.equalsIgnoreCase("Secure Mind A") || fieldValue.equalsIgnoreCase("GROUP SECURE MIND") || fieldValue.equalsIgnoreCase("GROUP PERSONAL ACCIDENT") || fieldValue.equalsIgnoreCase("CCPI") || fieldValue.equalsIgnoreCase("Asset Protect") || fieldValue.equalsIgnoreCase("Merchant Cover") || fieldValue.equalsIgnoreCase("Home")|| formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("GROUP SAFEGUARD INSURANCE")
					/************* end CR 24 HSP product by satish ***************/
					)
					{
						formObject.setNGEnable("ICICILOMBARD_HT_SUB_PRODUCT",false);
						formObject.setNGEnable("FRAME_IF_PRODUCT_HSP",true);
						formObject.setNGEnable("ICICILOMBARD_HT_PRODUCT_ABBR_CODE",true);
						formObject.setNGEnable("FRAME_PROD_ABBR_CODE",true);
						/*********************** CR 24 HSP product by satish ***********************/
						if(!fieldValue.equalsIgnoreCase("CRITICAL CARE"))
						{
							formObject.setNGEnable("ICICILOMBARD_HT_IAGENT",false);
						}
												
						/********************* end CR 24 HSP product by satish *********************/
					} /************************ Start CR-OMHT-1314-01 Wallet_Insurance************/
					else if(fieldValue.equalsIgnoreCase("WALLET INSURANCE") || fieldValue.equalsIgnoreCase("RETAIL PURE KHOKHA"))
					{
						formObject.setNGEnable("ICICILOMBARD_HT_SUB_PRODUCT",false);
						formObject.setNGEnable("FRAME_PROD_ABBR_CODE",false);
						formObject.setNGEnable("ICICILOMBARD_HT_LAN",false);
						
						/**********start adding new product RETAIL PURE KHOKHA************/
						if(formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("RETAIL PURE KHOKHA"))
						{
							formObject.setNGEnable("ICICILOMBARD_HT_IAGENT",false);
						}
						/**********end adding new product RETAIL PURE KHOKHA**************/
					} /************************ End CR-OMHT-1314-01 Wallet_Insurance************/
					/***********************Start CR Motor,Home & Travel processing*************/
					else if(formObject.getNGValue("ICICILOMBARD_HT_PRODUCT") != null && !formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equals("--Select--"))
					{
						String field[]= {"ProductList"};
						String productlistQuery = "select productname,producttype from NG_ICICI_MST_Product";
						noOfCols="2";
						//System.out.println("satish"+formObject.getNGValue("ICICILOMBARD_HT_PRODUCT"));
						formObject.setNGEnable("ICICILOMBARD_HT_SUB_PRODUCT",true);
						getData(productlistQuery,noOfCols,field,flag);
						//System.out.println("(map.size()=="+map.size());
						//System.out.println("Flag value=="+map.containsKey(formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").trim()));
						if(map.size() > 1 && map.containsKey(formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").trim()))
						{
							//System.out.println("map BSG_DATAENTRY bucket=="+map.containsKey(formObject.getNGValue("ICICILOMBARD_HT_PRODUCT")));
						//System.out.println("product BSG_DATAENTRY bucket=="+formObject.getNGValue("ICICILOMBARD_HT_PRODUCT"));
							formObject.setNGEnable("ICICILOMBARD_HT_IAGENT",true);
							formObject.setNGEnable("ICICILOMBARD_HT_SUB_PRODUCT",false);
							//formObject.setNGEnable("ICICILOMBARD_HT_PRODUCT_ABBR_CODE",false);
							formObject.setNGEnable("FRAME_PROD_ABBR_CODE",false);
							map = null;
						}  						
						
					} /***********************End CR Motor,Home & Travel processing***************/
					/*************** CR 24 HSP product by satish ****************/
					else 
					{
						formObject.setNGEnable("ICICILOMBARD_HT_IAGENT",true);
					}
					/************* end CR 24 HSP product by satish ***************/
					
				}
				// Yogendra Saraswat -- end for CRITICAL CARE case--
				
				//command set for change of vertical value
				/*else if(sCommandName.equalsIgnoreCase("SELECT_PRODUCT_HSP"))
				{
					fieldName="ICICILOMBARD_HT_PRODUCT";
					if(debug==1)
					{
						//System.out.println("inside command product change");
					}
					fieldValue=formObject.getNGValue(fieldName);
					
					//changes
					formObject.NGClear("ICICILOMBARD_HT_SUB_PRODUCT");
					formObject.NGClear("ICICILOMBARD_HT_PRODUCT_ABBR_CODE");

					if(fieldValue.equals("--Select--"))
					{
					
					}
					else 				
					{
						formObject.NGClear("PRODUCT_HIDDEN");
						String arrfieldName6[]= new String[1];
						noOfCols="1";
						query="select productcode from MV_UW_PRODUCT_MASTER where productname='"+fieldValue+"'";
						arrfieldName6[0]="PRODUCT_HIDDEN";
						getData(query,noOfCols,arrfieldName6,flag);
						//System.out.println("on load product click");

					}	
				}  */
	
			//code for SELECT_SUB_VERTICAL for selecting the sub vertical value
				else if(sCommandName.equalsIgnoreCase("SELECT_SUB_VERTICAL"))
				{	
					//System.out.println("test SELECT_SUB_VERTICAL sCommandName===="+sCommandName);
					fieldName= "ICICILOMBARD_HT_SUB_VERTICAL";
					fieldValue=formObject.getNGValue(fieldName);
					if(debug==1)
					{
						//System.out.println("fieldValue of sub vertical----"+fieldValue);
					}
					
					fieldValue=fieldValue.trim();
					//enabling/diabling the controls
					/***** CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket*******/
					formObject.setNGEnable("ICICILOMBARD_HT_SUB_VERTICAL",false);
					/***** End CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket***/
					formObject.setNGEnable("FRAME_SELECT_VERTICAL",false);
					//formObject.setNGEnable("FRAME_SOURCE_BUSINESS",false);
					if(debug==1)
					{
						//System.out.println("clearing fields false");
					}
					
					formObject.setNGEnable("ICICILOMBARD_HT_SOURCE_BUSINESS",false);
					formObject.setNGEnable("ICICILOMBARD_HT_CHANNEL_SOURCE",false);
					
					formObject.setNGEnable("FRAME_IS_EMP_CODE",false);
					formObject.setNGEnable("FRAME_WRE_WRM",false);
					formObject.setNGEnable("ICICILOMBARD_HT_PRIVILEGE_BANKER_CODE",false); 
					formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",false);
					formObject.setNGEnable("FRAME_CHANNEL_EMP_INFO",false);
					formObject.setNGEnable("FRAME_BSM_BCM",false);
					
					//clear the dependent fields
					//formObject.NGClear("ICICILOMBARD_HT_VERTICAL");
					formObject.setNGValue("ICICILOMBARD_HT_SOURCE_BUSINESS","");
					formObject.setNGValue("ICICILOMBARD_HT_CHANNEL_SOURCE","");
					formObject.setNGValue("ICICILOMBARD_HT_EMPCODE_CSO","");
					//formObject.setNGValue("ICICILOMBARD_HT_WRM","");//CR25_Masters of KRG,Removal of fields & RMT Bucket 
					formObject.setNGValue("ICICILOMBARD_HT_WRE","");
					formObject.setNGValue("ICICILOMBARD_HT_PRIVILEGE_BANKER_CODE",""); 
					//formObject.NGClear("ICICILOMBARD_HT_BANK_BRANCH_NAME");
					formObject.setNGValue("ICICILOMBARD_HT_CHANNEL_EMP_INFO","");
					formObject.NGClear("ICICILOMBARD_HT_BRANCH_ID_UBO_NAME");
					formObject.setNGValue("ICICILOMBARD_HT_BSM_ID","");
					formObject.setNGValue("ICICILOMBARD_HT_BCM_ID","");

					/***** CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket*******/
					/*if(fieldValue.equals("--Select--"))
					{
					}
					else */
					/***** End CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket***/
					if(!fieldValue.equalsIgnoreCase(""))
					{
						//System.out.println("else fieldValue value1===="+fieldValue);
						
						/***** CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket*******/
						//reload
						//added by satish
						//populating ICICILOMBARD_HT_VERTICAL on form load
						/*formObject.NGClear("ICICILOMBARD_HT_VERTICAL");
						formObject.NGAddItem("ICICILOMBARD_HT_VERTICAL","--Select--");
						formObject.NGAddItem("ICICILOMBARD_HT_VERTICAL","BBG");
						formObject.NGAddItem("ICICILOMBARD_HT_VERTICAL","ISEC");
						formObject.NGAddItem("ICICILOMBARD_HT_VERTICAL","KRG");*/
						//end added by satish
						/***** End CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket***/
						
						if(fieldValue.equals("BBG") || fieldValue.equals("Branch Banking") || fieldValue.equals("BRANCH BRANCHING") || fieldValue.equals("Branch Banking (BBG)") || fieldValue.equalsIgnoreCase("Key Relationship Group") || fieldValue.equalsIgnoreCase("Key Relationship Group (KRG 1)") || fieldValue.equalsIgnoreCase("Relationship Group (KRG 2)"))
						{
							if(debug==1)
							{
								//System.out.println("inside bbg/branch bank----"+fieldValue);
							}
							//System.out.println("fieldValue value2===="+fieldValue);
							formObject.setNGEnable("FRAME_SELECT_VERTICAL",true);
							//formObject.setNGEnable("FRAME_SOURCE_BUSINESS",true);
							if(debug==1)
							{
								//System.out.println("setting enable in bbg brnch bnking----");
							}
							formObject.setNGEnable("ICICILOMBARD_HT_SOURCE_BUSINESS",true);
							formObject.setNGEnable("ICICILOMBARD_HT_CHANNEL_SOURCE",true);
							formObject.setNGEnable("FRAME_IS_EMP_CODE",true);
							formObject.setNGEnable("FRAME_WRE_WRM",true);							
							formObject.setNGEnable("ICICILOMBARD_HT_PRIVILEGE_BANKER_CODE",true); 
							formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",true);
							//formObject.setNGEnable("FRAME_CHANNEL_EMP_INFO",true);
							//System.out.println("fieldValue value3===="+fieldValue);

						}
							
						if(fieldValue.equalsIgnoreCase("Key Relationship Group") || fieldValue.equalsIgnoreCase("Key Relationship Group (KRG 1)") || fieldValue.equalsIgnoreCase("Relationship Group (KRG 2)"))
						{
							//formObject.setNGEnable("FRAME_SOURCE_BUSINESS",true);
							formObject.setNGEnable("ICICILOMBARD_HT_SOURCE_BUSINESS",true);
							formObject.setNGEnable("ICICILOMBARD_HT_CHANNEL_SOURCE",true);
							formObject.setNGEnable("FRAME_CHANNEL_EMP_INFO",true);
							formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",false);
						}	

						if(fieldValue.equals("HOME"))
						{
							//formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",true);
							formObject.setNGEnable("FRAME_BSM_BCM",true);
						}
					}	
				}
				
				//vertical change
				else if(sCommandName.equalsIgnoreCase("SELECT_VERTICAL"))
				{
					if(debug==1)
					{
						//System.out.println("inside vertical");
					}
					/***** CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket*******/	
					formObject.setNGEnable("ICICILOMBARD_HT_VERTICAL",false);
					/***** End CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket***/
					//System.out.println("test SELECT_VERTICAL sCommandName===="+sCommandName);
					formObject.setNGValue("ICICILOMBARD_HT_SOURCE_BUSINESS","");
					formObject.setNGValue("ICICILOMBARD_HT_CHANNEL_SOURCE","");
					//formObject.setNGValue("ICICILOMBARD_HT_EMPCODE_CSO","");
					//formObject.setNGValue("ICICILOMBARD_HT_WRM","");
					//formObject.setNGValue("ICICILOMBARD_HT_WRE","");

					//formObject.setNGEnable("FRAME_IS_EMP_CODE",false);
					//formObject.setNGEnable("FRAME_WRE_WRM",false);
					//formObject.setNGEnable("ICICILOMBARD_HT_PRIVILEGE_BANKER_CODE",false); 
					//formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",false);
					
					/***** CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket*******/
					/*if(formObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equals("--Select--"))
					{
					}
					else*/
					/***** End CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket***/
					if(!formObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase(""))//CR25_Masters of KRG,Removal of fields & RMT Bucket 
					{
						
						if (formObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("ISEC") && (formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL")).equalsIgnoreCase("BBG") )
						{
							if(debug==1)
							{
								//System.out.println("setting enable/diable one-one in bbg isec----");
							}
							formObject.setNGEnable("ICICILOMBARD_HT_SOURCE_BUSINESS",true);
							formObject.setNGValue("ICICILOMBARD_HT_CHANNEL_SOURCE","");
							formObject.setNGEnable("ICICILOMBARD_HT_CHANNEL_SOURCE",false);
						}
						else if(formObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BBG") && (formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL")).equalsIgnoreCase("BBG"))
						{
							String arrfieldName9[]=new String[1];
							if(debug==1)
							{
								//System.out.println("inside bbg and bbg");
							}
							formObject.setNGEnable("ICICILOMBARD_HT_SOURCE_BUSINESS",true);
							formObject.setNGEnable("FRAME_IS_EMP_CODE",true);
							formObject.setNGEnable("FRAME_WRE_WRM",true);
							formObject.setNGEnable("ICICILOMBARD_HT_PRIVILEGE_BANKER_CODE",true); 
							formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",true);
							formObject.setNGEnable("ICICILOMBARD_HT_CHANNEL_SOURCE",true);
					
						}
						/***** CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket*******/
						else if(formObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("Bancassurance") && (formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group")))
						{
							formObject.setNGEnable("FRAME_CHANNEL_EMP_INFO",true);
						} /***** End CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket***/
						else
						{
							//System.out.println("inside----else");
						}
					}
				} 

			// if the transaction type selected as renewal
				else if(sCommandName.equalsIgnoreCase("TRANSACTION_TYPE_RENEWAL"))
				{
					formObject.setNGEnable("FRAME_PREV_POLICY_NUMBER",false);
					formObject.setNGValue("ICICILOMBARD_HT_PREVIOUS_POLICY_NO","");
					fieldName= "ICICILOMBARD_HT_TRANSACTION_TYPE";
					fieldValue=formObject.getNGValue(fieldName);	
					if(fieldValue.equals("Renewal"))
					{
						formObject.setNGEnable("FRAME_PREV_POLICY_NUMBER",true);
					}
				}
		
				//block for selection of payment mode
				/******************************* PID-HT process changes ********************************/
					/*else if(sCommandName.equalsIgnoreCase("SELECT_PAYMENT_MODE"))
					{
						fieldValue=formObject.getNGValue("ICICILOMBARD_HT_PAYMENT_MODE");
						formObject.setNGEnable("FRAME_CHEQUE_DD",false);
						formObject.setNGEnable("FRAME_SAVINGS_ACCOUNT",false);
						formObject.setNGEnable("FRAME_CREDIT_CARD",false);
						formObject.setNGEnable("FRAME_PID_NO",false);
						if(fieldValue.equalsIgnoreCase("Cheque") || fieldValue.equalsIgnoreCase("Demand Draft"))
						{
							formObject.setNGValue("ICICILOMBARD_HT_SAVINGS_ACCOUNT_NO","");
							formObject.setNGValue("ICICILOMBARD_HT_PID_NO","");
							formObject.setNGValue("ICICILOMBARD_HT_CREDIT_CARD_NO","");
							formObject.setNGValue("ICICILOMBARD_HT_CREDITCARD_EXP_DATE","");
							formObject.setNGValue("ICICILOMBARD_HT_AUTHORIZATION_CODE","");
							formObject.setNGValue("ICICILOMBARD_HT_AUTHORIZATION_DATE","");
							formObject.setNGEnable("FRAME_CHEQUE_DD",true);
						}					
						else if(fieldValue.equalsIgnoreCase("Fund Transfer"))
						{
							formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_NO","");
							formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_DATE","");
							formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_AMOUNT","");
							formObject.setNGValue("ICICILOMBARD_HT_PID_NO","");
							formObject.setNGValue("ICICILOMBARD_HT_CREDIT_CARD_NO","");
							formObject.setNGValue("ICICILOMBARD_HT_CREDITCARD_EXP_DATE","");
							formObject.setNGValue("ICICILOMBARD_HT_AUTHORIZATION_CODE","");
							formObject.setNGValue("ICICILOMBARD_HT_AUTHORIZATION_DATE","");
							formObject.setNGEnable("FRAME_SAVINGS_ACCOUNT",true);
						}					
						else if(fieldValue.equalsIgnoreCase("Credit Card"))
						{
							formObject.setNGValue("ICICILOMBARD_HT_PID_NO","");
							formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_NO","");
							formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_DATE","");
							formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_AMOUNT","");
							formObject.setNGValue("ICICILOMBARD_HT_SAVINGS_ACCOUNT_NO","");
												
							formObject.setNGEnable("FRAME_CREDIT_CARD",true);
						}					
						else if(fieldValue.equalsIgnoreCase("CDBG"))
						{
							formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_NO","");
							formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_DATE","");
							formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_AMOUNT","");
							formObject.setNGValue("ICICILOMBARD_HT_SAVINGS_ACCOUNT_NO","");
							formObject.setNGValue("ICICILOMBARD_HT_CREDIT_CARD_NO","");
							formObject.setNGValue("ICICILOMBARD_HT_CREDITCARD_EXP_DATE","");
							formObject.setNGValue("ICICILOMBARD_HT_AUTHORIZATION_CODE","");
							formObject.setNGValue("ICICILOMBARD_HT_AUTHORIZATION_DATE","");
							
							formObject.setNGEnable("FRAME_PID_NO",true);
						}					
						else if(fieldValue.equalsIgnoreCase("ECS"))
						{
							formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_NO","");
							formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_DATE","");
							formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_AMOUNT","");
							formObject.setNGValue("ICICILOMBARD_HT_SAVINGS_ACCOUNT_NO","");
							formObject.setNGValue("ICICILOMBARD_HT_CREDIT_CARD_NO","");
							formObject.setNGValue("ICICILOMBARD_HT_CREDITCARD_EXP_DATE","");
							formObject.setNGValue("ICICILOMBARD_HT_AUTHORIZATION_CODE","");
							formObject.setNGValue("ICICILOMBARD_HT_AUTHORIZATION_DATE","");
							formObject.setNGValue("ICICILOMBARD_HT_PID_NO","");
						}					
						else
						{
						}
					}*/
				/******************************End PID-HT process changes ******************************/
				if((formObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE"))&&(formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)")))
				{
					System.out.println("inside executeHTCommand sp code :");
					formObject.setNGEnable("ICICILOMBARD_HT_WRE",true);
					formObject.setNGLocked("ICICILOMBARD_HT_WRE",false);
				}
			}




			else if(wsName.equalsIgnoreCase("BSG_DATAENTRY_QC"))
			{
				
				// if the transaction type selected as renewal
				if(sCommandName.equalsIgnoreCase("TRANSACTION_TYPE_RENEWAL"))
				{
					formObject.setNGEnable("FRAME_PREV_POLICY_NUMBER",false);
					formObject.setNGValue("ICICILOMBARD_HT_PREVIOUS_POLICY_NO","");
					fieldName= "ICICILOMBARD_HT_TRANSACTION_TYPE";
					fieldValue=formObject.getNGValue(fieldName);	
					if(fieldValue.equals("Renewal"))
					{
						formObject.setNGEnable("FRAME_PREV_POLICY_NUMBER",true);
						
						//System.out.println(":::Inside REnewal");
						
				
					}
					// code added by Yogendra Saraswat
					//for making CUSTOMERNUMBER field disabled and cleared 
					//in case of transaction type not Rollover
					if(fieldValue.equals("Rollover"))
					{
						
						
						//System.out.println(":::Inside Rollover");
						
					
						formObject.setNGEnable("FRM_CUSTOMER_MOBILE_NO",true);
						formObject.setNGValue("ICICILOMBARD_HT_CUSTOMERNUMBER","");
					}
					else
					{
					
							
						formObject.setNGEnable("FRM_CUSTOMER_MOBILE_NO",false);
						formObject.setNGValue("ICICILOMBARD_HT_CUSTOMERNUMBER","");
					}
					// code ended by Yogendra Saraswat
				}
				
				//block for selection of payment mode
			/******************************* PID-HT process changes ********************************/
				/*else if(sCommandName.equalsIgnoreCase("SELECT_PAYMENT_MODE"))
				{
					fieldValue=formObject.getNGValue("ICICILOMBARD_HT_PAYMENT_MODE");
					formObject.setNGEnable("FRAME_CHEQUE_DD",false);
					formObject.setNGEnable("FRAME_SAVINGS_ACCOUNT",false);
					formObject.setNGEnable("FRAME_CREDIT_CARD",false);
					formObject.setNGEnable("FRAME_PID_NO",false);
					if(fieldValue.equalsIgnoreCase("Cheque") || fieldValue.equalsIgnoreCase("Demand Draft"))
					{
						formObject.setNGValue("ICICILOMBARD_HT_SAVINGS_ACCOUNT_NO","");
						formObject.setNGValue("ICICILOMBARD_HT_PID_NO","");
						formObject.setNGValue("ICICILOMBARD_HT_CREDIT_CARD_NO","");
						formObject.setNGValue("ICICILOMBARD_HT_CREDITCARD_EXP_DATE","");
						formObject.setNGValue("ICICILOMBARD_HT_AUTHORIZATION_CODE","");
						formObject.setNGValue("ICICILOMBARD_HT_AUTHORIZATION_DATE","");
						formObject.setNGEnable("FRAME_CHEQUE_DD",true);
					}
					
					else if(fieldValue.equalsIgnoreCase("Fund Transfer"))
					{
						formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_NO","");
						formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_DATE","");
						formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_AMOUNT","");
						formObject.setNGValue("ICICILOMBARD_HT_PID_NO","");
						formObject.setNGValue("ICICILOMBARD_HT_CREDIT_CARD_NO","");
						formObject.setNGValue("ICICILOMBARD_HT_CREDITCARD_EXP_DATE","");
						formObject.setNGValue("ICICILOMBARD_HT_AUTHORIZATION_CODE","");
						formObject.setNGValue("ICICILOMBARD_HT_AUTHORIZATION_DATE","");
						formObject.setNGEnable("FRAME_SAVINGS_ACCOUNT",true);
					}
					
					else if(fieldValue.equalsIgnoreCase("Credit Card"))
					{
						formObject.setNGValue("ICICILOMBARD_HT_PID_NO","");
						formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_NO","");
						formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_DATE","");
						formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_AMOUNT","");
						formObject.setNGValue("ICICILOMBARD_HT_SAVINGS_ACCOUNT_NO","");
											
						formObject.setNGEnable("FRAME_CREDIT_CARD",true);
					}
					
					else if(fieldValue.equalsIgnoreCase("CDBG"))
					{
						formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_NO","");
						formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_DATE","");
						formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_AMOUNT","");
						formObject.setNGValue("ICICILOMBARD_HT_SAVINGS_ACCOUNT_NO","");
						formObject.setNGValue("ICICILOMBARD_HT_CREDIT_CARD_NO","");
						formObject.setNGValue("ICICILOMBARD_HT_CREDITCARD_EXP_DATE","");
						formObject.setNGValue("ICICILOMBARD_HT_AUTHORIZATION_CODE","");
						formObject.setNGValue("ICICILOMBARD_HT_AUTHORIZATION_DATE","");
						
						formObject.setNGEnable("FRAME_PID_NO",true);
					}
					
					else if(fieldValue.equalsIgnoreCase("ECS"))
					{
						formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_NO","");
						formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_DATE","");
						formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_AMOUNT","");
						formObject.setNGValue("ICICILOMBARD_HT_SAVINGS_ACCOUNT_NO","");
						formObject.setNGValue("ICICILOMBARD_HT_CREDIT_CARD_NO","");
						formObject.setNGValue("ICICILOMBARD_HT_CREDITCARD_EXP_DATE","");
						formObject.setNGValue("ICICILOMBARD_HT_AUTHORIZATION_CODE","");
						formObject.setNGValue("ICICILOMBARD_HT_AUTHORIZATION_DATE","");
						formObject.setNGValue("ICICILOMBARD_HT_PID_NO","");
					}
				}*/
			/******************************End PID-HT process changes ******************************/
			}
			
			//exception commands
			else if(wsName.equalsIgnoreCase("EXCEPTION"))
			{
				
				//block for selection of payment mode
			/******************************* PID-HT process changes ********************************/
				/*if(sCommandName.equalsIgnoreCase("SELECT_PAYMENT_MODE"))
				{
					fieldValue=formObject.getNGValue("ICICILOMBARD_HT_PAYMENT_MODE");
					formObject.setNGEnable("FRAME_CHEQUE_DD",false);
					formObject.setNGEnable("FRAME_SAVINGS_ACCOUNT",false);
					formObject.setNGEnable("FRAME_CREDIT_CARD",false);
					formObject.setNGEnable("FRAME_PID_NO",false);
					if(fieldValue.equalsIgnoreCase("Cheque") || fieldValue.equalsIgnoreCase("Demand Draft"))
					{
						formObject.setNGValue("ICICILOMBARD_HT_SAVINGS_ACCOUNT_NO","");
						formObject.setNGValue("ICICILOMBARD_HT_PID_NO","");
						formObject.setNGValue("ICICILOMBARD_HT_CREDIT_CARD_NO","");
						formObject.setNGValue("ICICILOMBARD_HT_CREDITCARD_EXP_DATE","");
						formObject.setNGValue("ICICILOMBARD_HT_AUTHORIZATION_CODE","");
						formObject.setNGValue("ICICILOMBARD_HT_AUTHORIZATION_DATE","");
						formObject.setNGEnable("FRAME_CHEQUE_DD",true);
					}
					
					else if(fieldValue.equalsIgnoreCase("Fund Transfer"))
					{
						formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_NO","");
						formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_DATE","");
						formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_AMOUNT","");
						formObject.setNGValue("ICICILOMBARD_HT_PID_NO","");
						formObject.setNGValue("ICICILOMBARD_HT_CREDIT_CARD_NO","");
						formObject.setNGValue("ICICILOMBARD_HT_CREDITCARD_EXP_DATE","");
						formObject.setNGValue("ICICILOMBARD_HT_AUTHORIZATION_CODE","");
						formObject.setNGValue("ICICILOMBARD_HT_AUTHORIZATION_DATE","");
						formObject.setNGEnable("FRAME_SAVINGS_ACCOUNT",true);
					}
					
					else if(fieldValue.equalsIgnoreCase("Credit Card"))
					{
						formObject.setNGValue("ICICILOMBARD_HT_PID_NO","");
						formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_NO","");
						formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_DATE","");
						formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_AMOUNT","");
						formObject.setNGValue("ICICILOMBARD_HT_SAVINGS_ACCOUNT_NO","");
											
						formObject.setNGEnable("FRAME_CREDIT_CARD",true);
					}
					
					else if(fieldValue.equalsIgnoreCase("CDBG"))
					{
						formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_NO","");
						formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_DATE","");
						formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_AMOUNT","");
						formObject.setNGValue("ICICILOMBARD_HT_SAVINGS_ACCOUNT_NO","");
						formObject.setNGValue("ICICILOMBARD_HT_CREDIT_CARD_NO","");
						formObject.setNGValue("ICICILOMBARD_HT_CREDITCARD_EXP_DATE","");
						formObject.setNGValue("ICICILOMBARD_HT_AUTHORIZATION_CODE","");
						formObject.setNGValue("ICICILOMBARD_HT_AUTHORIZATION_DATE","");
						
						formObject.setNGEnable("FRAME_PID_NO",true);
					}
					
					else if(fieldValue.equalsIgnoreCase("ECS"))
					{
						formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_NO","");
						formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_DATE","");
						formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_AMOUNT","");
						formObject.setNGValue("ICICILOMBARD_HT_SAVINGS_ACCOUNT_NO","");
						formObject.setNGValue("ICICILOMBARD_HT_CREDIT_CARD_NO","");
						formObject.setNGValue("ICICILOMBARD_HT_CREDITCARD_EXP_DATE","");
						formObject.setNGValue("ICICILOMBARD_HT_AUTHORIZATION_CODE","");
						formObject.setNGValue("ICICILOMBARD_HT_AUTHORIZATION_DATE","");
						formObject.setNGValue("ICICILOMBARD_HT_PID_NO","");
					}	
				}
				else */
			/******************************End PID-HT process changes ******************************/
				if(sCommandName.equalsIgnoreCase("TRANSACTION_TYPE_RENEWAL"))
				{
					
					formObject.setNGEnable("FRAME_PREV_POLICY_NUMBER",false);
					formObject.setNGValue("ICICILOMBARD_HT_PREVIOUS_POLICY_NO","");
					fieldName= "ICICILOMBARD_HT_TRANSACTION_TYPE";
					fieldValue=formObject.getNGValue(fieldName);	
					
					if(fieldValue.equals("Renewal"))
					{
						formObject.setNGEnable("FRAME_PREV_POLICY_NUMBER",true);
						
					}
				}
			}
			
			else
			{
			}
			return "1";
		}	
		catch(Exception e)
		{
			e.printStackTrace();
			return "0";
		}
	}

	//this method is used to get the data from DB	
	public void getData(String query,String noOfCols,String[] arrfieldName,boolean flag)
	{
		try
		{
			//passing the control and form data to NGEjbCalls class
			ngEjbCalls= new NGEjbCalls(formObject);
			// get input xml for the query
			inputXml=ngEjbCalls.callSelectInputXML(query,noOfCols);
			//System.out.println("input xml:-------->"+inputXml);
			// get output xml for the query
			outputXml=ngEjbCalls.executeXmlGeneric(inputXml);
			//System.out.println("output xml:-------->"+outputXml);
			/***********************Start CR Motor,Home & Travel processing*************/
			if(arrfieldName[0].equalsIgnoreCase("ProductList"))
			{
				map = getProductList(outputXml,arrfieldName);
			}
			else
			{
				result= ngEjbCalls.xmlParse(outputXml,arrfieldName,noOfCols,flag);
			}
			/***********************End CR Motor,Home & Travel processing***************/
			//System.out.println("result :-------->"+result);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/***********************Start CR Motor,Home & Travel processing*************/
	public HashMap getProductList(String outputXml,String[] arrfieldName)
	{
		map = new HashMap();
		if (!(outputXml.substring(outputXml.indexOf("<Results>") + 9, outputXml.indexOf("</Results>")).equals("")))
		{
			xmlResponse = new WFXmlResponse(outputXml);
			if(arrfieldName[0].equalsIgnoreCase("ProductList"))
			{
				String sResult = null;
				String splitData = null;
				for (RecordList = xmlResponse.createList("Results", "Result"); RecordList.hasMoreElements(); RecordList.skip())
				{
					sResult = RecordList.getVal("Result");
					//get the actual result
					splitData=sResult.substring(1,sResult.length());
					String strArr[] = splitData.split("\\|");
					//System.out.println("strArr[0]=="+strArr[0]);
					//System.out.println("strArr[1]=="+strArr[1]);
					map.put(strArr[0].trim(),strArr[1].trim());
					//map.add(sResult);
				}
			} 
		}
		//System.out.println("map[1]=="+map);
		return map;
	}
	/***********************End CR Motor,Home & Travel processing***************/
	/******************************* PID-HT process changes ********************************/
	public void RouteTo_ClarificationWS(String PreviousWS,String ConsPrevWS,String activityName)
	{
		//formObject.NGRemoveItemAt("MHT_Route_To",1);
		formObject.NGClear("ICICILOMBARD_HT_Route_To");
		//System.out.println("PreviousWS :"+PreviousWS);
		//System.out.println("ConsPrevWS :"+ConsPrevWS);
		//System.out.println("activityName :"+activityName);
			// Exception for new worksteps
		if(ConsPrevWS.equalsIgnoreCase("BSG_DataEntry_QC") || ConsPrevWS.equalsIgnoreCase("COPS_Team"))
		{
			if(activityName.equalsIgnoreCase("RM_Exception"))
			{
				 //System.out.println("RouteTo_ClarificationWS 1");

				 formObject.NGAddItem("ICICILOMBARD_HT_Route_To","BSG_Exception");
			}
			if(activityName.equalsIgnoreCase("Exception")) //Exception represents BSG_Exception only.
			{

				formObject.NGAddItem("ICICILOMBARD_HT_Route_To","RM_Exception");
			}

			/**************** Start CR-8093-79840 Additional Requirement HT *********************/
			/*if(ConsPrevWS.equalsIgnoreCase("COPS_Team") && activityName.equalsIgnoreCase("Exception") && (formObject.getNGValue("ICICILOMBARD_HT_EXCEPTION_NAME").equalsIgnoreCase("Deviation Approval Required") || formObject.getNGValue("ICICILOMBARD_HT_EXCEPTION_NAME").equalsIgnoreCase("Banking ID locked Limit exhausted") || formObject.getNGValue("ICICILOMBARD_HT_EXCEPTION_NAME").equalsIgnoreCase("I-Partner Inward Approval Required") || formObject.getNGValue("ICICILOMBARD_HT_EXCEPTION_NAME").equalsIgnoreCase("Banking ID locked Limit exhausted") ||formObject.getNGValue("ICICILOMBARD_HT_EXCEPTION_NAME").equalsIgnoreCase("Subcover plans not mapped in Deal") || formObject.getNGValue("ICICILOMBARD_HT_EXCEPTION_NAME").equalsIgnoreCase("Short or Excess Premium") || formObject.getNGValue("ICICILOMBARD_HT_EXCEPTION_NAME").equalsIgnoreCase("Others")))//Exception represents BSG_Exception only.
			{
				System.out.println("Inside Route_To RM_Exception : Exception is :" +formObject.getNGValue("ICICILOMBARD_HT_EXCEPTION_NAME"));
				formObject.NGAddItem("ICICILOMBARD_HT_Route_To","RM_Exception");		
			}*/
			/**************** End CR-8093-79840 Additional Requirement HT *********************/

		}
	}
	/******************************End PID-HT process changes ******************************/
	
	public String executeHTEvent(String pEventName, String pControlName, String pControlValue, String pReserved)
	{
		generalDataParser.setInputXML(formObject.getWFGeneralData());
		wsName = generalDataParser.getValueOf("ActivityName").toUpperCase();
	
		/********************** Start Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR ***********************/
		wiName = generalDataParser.getValueOf("ProcessInstanceId").toUpperCase();
		
		/********************** End Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR *************************/
		cenDealStatus = formObject.getNGValue("ICICILOMBARD_HT_DEAL_STATUS"); //CR-OMHT-1314-01-Wallet_Insurance 
		NGPickList picklist=null;
		PickListListener pl=null;
		
		// comment by manoj jain	
		
		//********* SUB FORM***********
		
		if((wsName.equalsIgnoreCase("BSG_DataEntry")|| wsName.equalsIgnoreCase("BSG_DataEntry_QC")|| wsName.equalsIgnoreCase("Exception")||wsName.equalsIgnoreCase("COPS_Team")||wsName.equalsIgnoreCase("COPS_QC")
		/****************************** CR 24 HSP product by satish ******************************/
		||wsName.equalsIgnoreCase("COPS_QC1")
		/**************************** end CR 24 HSP product by satish ****************************/
		//||wsName.equalsIgnoreCase("RMT") //CR25_Masters of KRG,Removal of fields & CR26 RMT Bucket 
		) && formObject.getNGValue("ICICILOMBARD_HT_TRANSACTION_TYPE").equals("Rollover"))
		{
	
			formObject.setNGEnable("BTN_PRT_POPUP", true); // portability button is visible for transction type = "Rollover"
		
			if(pControlName.equalsIgnoreCase("BTN_PRT_POPUP") && formObject.getNGValue("BTN_PRT_POPUP").equals("Portability") && pEventName.equalsIgnoreCase("click"))
			{
				ngfPortabilitySubForm = new NGFPortabilitySubForm(formObject);
				ngfPortabilitySubForm.portabilityMatrixSubForm(pEventName, pControlName, pControlValue,  pReserved,this.formObject.getWFActivityName());
											  
			}
		}
		else
		{
			formObject.setNGEnable("BTN_PRT_POPUP", false);
		}		
		
		/******Start HT CR-8127-95325-GST-Omniflow development******/
		if((wsName.equalsIgnoreCase("BSG_DataEntry") || wsName.equalsIgnoreCase("BSG_DataEntry_QC") || wsName.equalsIgnoreCase("Exception") || wsName.equalsIgnoreCase("RM_Exception")))
		{
			formObject.setNGEnable("FRAME_GST_GRID",true);
			formObject.setNGEnable("ICICILOMBARD_HT_GST_REGISTERED",true);
			if(formObject.getNGValue("ICICILOMBARD_HT_GST_REGISTERED").equalsIgnoreCase("Yes")  && formObject.getNGValue("ICICILOMBARD_HT_IAGENT").equalsIgnoreCase("No"))
			{
				formObject.setNGEnable("TXTGST_NUMBER",true);
				//formObject.setNGLocked("TXTGST_NUMBER",false);				
				formObject.setNGEnable("TXTGST_STATE_NAME",true);
				formObject.setNGLocked("TXTGST_STATE_NAME",false);
				formObject.setNGEnable("Add_GST",true);
				formObject.setNGEnable("Mod_GST",true);
				formObject.setNGEnable("Del_GST",true);
				formObject.setNGEnable("ICICILOMBARD_HT_qGrdGstVar",true);
			}
			/*****Start Change related to Application  Proposal no. field in Omni flow HT*****/
			if(formObject.getNGValue("ICICILOMBARD_HT_IAGENT").equalsIgnoreCase("Yes"))
			{
				formObject.setNGEnable("ICICILOMBARD_HT_PF_PROPOSAL_NO",true);
				formObject.setNGLocked("ICICILOMBARD_HT_PF_PROPOSAL_NO",true);
				formObject.setNGEnable("ICICILOMBARD_HT_CNFRM_PF_PROPOSAL_NO",true);
				formObject.setNGLocked("ICICILOMBARD_HT_CNFRM_PF_PROPOSAL_NO",true);
				formObject.setNGEnable("ICICILOMBARD_HT_PROPOSAL_NO",false);
				formObject.setNGLocked("ICICILOMBARD_HT_PROPOSAL_NO",false);
				formObject.setNGEnable("ICICILOMBARD_HT_CONFIRM_PROPOSAL_NO",false);
				formObject.setNGLocked("ICICILOMBARD_HT_CONFIRM_PROPOSAL_NO",false);
			}
			formObject.setNGEnable("ICICILOMBARD_HT_FUTURE_DATED",true);
			formObject.setNGLocked("ICICILOMBARD_HT_FUTURE_DATED",true);
			formObject.setNGEnable("ICICILOMBARD_HT_LOAN_SANCTIONED_AMT",true);
			formObject.setNGLocked("ICICILOMBARD_HT_LOAN_SANCTIONED_AMT",true);				
			formObject.setNGLocked("ICICILOMBARD_HT_DEAL_IL_LOCATION",false);				
			/*****End Change related to Application  Proposal no. field in Omni flow HT*****/
		}
		else
		{
			formObject.setNGEnable("ICICILOMBARD_HT_GST_REGISTERED",false);
			formObject.setNGEnable("TXTGST_NUMBER",false);
			formObject.setNGEnable("TXTGST_STATE_NAME",false);
			formObject.setNGEnable("Add_GST",false);
			formObject.setNGEnable("Mod_GST",false);
			formObject.setNGEnable("Del_GST",false);
			formObject.setNGEnable("ICICILOMBARD_HT_qGrdGstVar",true);
			/*****Start Change related to Application  Proposal no. field in Omni flow HT*****/
			formObject.setNGEnable("ICICILOMBARD_HT_PF_PROPOSAL_NO",false);
			formObject.setNGLocked("ICICILOMBARD_HT_PF_PROPOSAL_NO",false);
			formObject.setNGEnable("ICICILOMBARD_HT_CNFRM_PF_PROPOSAL_NO",false);
			formObject.setNGLocked("ICICILOMBARD_HT_CNFRM_PF_PROPOSAL_NO",false);
			formObject.setNGEnable("ICICILOMBARD_HT_FUTURE_DATED",false);
			formObject.setNGLocked("ICICILOMBARD_HT_FUTURE_DATED",false);
			formObject.setNGEnable("ICICILOMBARD_HT_LOAN_SANCTIONED_AMT",false);
			formObject.setNGLocked("ICICILOMBARD_HT_LOAN_SANCTIONED_AMT",false);				
			formObject.setNGLocked("ICICILOMBARD_HT_DEAL_IL_LOCATION",false);
			/*****End Change related to Application  Proposal no. field in Omni flow HT*****/			
		}
		/******End HT CR-8127-95325-GST-Omniflow development******/
	
		/********************** Start Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR ***********************/
		
		if((wsName.equalsIgnoreCase("BSG_DATAENTRY_QC") || wsName.equalsIgnoreCase("COPS_TEAM") || wsName.equalsIgnoreCase("EXCEPTION") || wsName.equalsIgnoreCase("RM_EXCEPTION")) && pControlName.equalsIgnoreCase("BTN_FETCHCOPY") && pEventName.equalsIgnoreCase("CLICK"))
		{
				//System.out.println("Calling FetchCopy():event For HT- CR-8093-59790 (HT-FlapPrint) CR ");
				url="FetchCopyWorkItem.jsp?Process=HT&ActivityName="+wsName+"&Pid="+wiName;
				showPage(url,"FetchCopy");//BTN_FETCHCOPY(Button Name)
		}
		
		/********************** End Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR *************************/
		//********* SUB FORM***********
		
		//event for il location change
		//Vishal il location for Exception
		//if((pEventName.equalsIgnoreCase("KeyPress(F3)")) && (pControlName.equalsIgnoreCase("ICICILOMBARD_HT_IL_LOCATION") && cenDealStatus.equalsIgnoreCase("YES")) && ((wsName.equalsIgnoreCase("BSG_DATAENTRY")) || (wsName.equalsIgnoreCase("BSG_DATAENTRY_QC")) || (wsName.equalsIgnoreCase("EXCEPTION"))))
		if((pEventName.equalsIgnoreCase("KeyPress(F3)")) && (pControlName.equalsIgnoreCase("ICICILOMBARD_HT_IL_LOCATION") || pControlName.equalsIgnoreCase("ICICILOMBARD_HT_IL_LOCATION_CODE")) && ((wsName.equalsIgnoreCase("BSG_DATAENTRY")) || (wsName.equalsIgnoreCase("BSG_DATAENTRY_QC")) || (wsName.equalsIgnoreCase("EXCEPTION"))))  //PID-HT process changes
		{
			String name="Branch Name,Branch code";
			picklist=formObject.getNGPickList(pControlName, name,true) ;
			
			pl=new PickListListener(formObject,pControlName,picklist);
			picklist.addPickListListener(pl);
			picklist.setSearchEnable(true);
			picklist.setVisible(true);

		}
		
			//event for il search string
		else if((pEventName.equalsIgnoreCase("KeyPress(F3)")) && (pControlName.equalsIgnoreCase("SEARCH_STRING")) && (wsName.equalsIgnoreCase("BSG_DATAENTRY")))
		{
			if(!formObject.getNGValue("ICICILOMBARD_HT_SEARCH_CRITERIA").equals("--Select--"))
			{
				String name="Agent Name, Agent Code, Deal No, Deal Status";		
				picklist=formObject.getNGPickList(pControlName, name,true) ;	
				pl=new PickListListener(formObject,pControlName,picklist);
				picklist.addPickListListener(pl);
				picklist.setVisible(true);
			}
			else
			{
				JOptionPane.showMessageDialog(null,"Please select the search criteria first !!");
			}
	
			
		} 
		
		//event for Source Business
		else if((pEventName.equalsIgnoreCase("KeyPress(F3)")) && (pControlName.equalsIgnoreCase("ICICILOMBARD_HT_SOURCE_BUSINESS")) && (wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DATAENTRY_QC")))
		{
			String name="Source Business";		
			picklist=formObject.getNGPickList(pControlName, name,true) ;	
			pl=new PickListListener(formObject,pControlName,picklist);
			picklist.addPickListListener(pl);
			picklist.setSearchEnable(true);
			picklist.setVisible(true);
		}
		
		////event for channel source
		else if((pEventName.equalsIgnoreCase("KeyPress(F3)")) && (pControlName.equalsIgnoreCase("ICICILOMBARD_HT_CHANNEL_SOURCE")) && (wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DATAENTRY_QC")))
		{
			String name="Channel Source";		
			picklist=formObject.getNGPickList(pControlName, name,true) ;	
			pl=new PickListListener(formObject,pControlName,picklist);
			picklist.addPickListListener(pl);
			picklist.setSearchEnable(true);
			picklist.setVisible(true);
		}
			
		/******************************* PID-HT process changes ********************************/
		////event for BBG bank branch name
		else if((pEventName.equalsIgnoreCase("KeyPress(F3)")) && (pControlName.equalsIgnoreCase("ICICILOMBARD_HT_PAYMENT_MODE")) && (wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DATAENTRY_QC") || wsName.equalsIgnoreCase("EXCEPTION")))
		{
			String name="Payment mode";	
			//System.out.println("inside F3 event--"+pControlName);
			picklist=formObject.getNGPickList(pControlName, name,true) ;	
			pl=new PickListListener(formObject,pControlName,picklist);
			picklist.addPickListListener(pl);
			picklist.setSearchEnable(true);
			picklist.setVisible(true);
		}		
		/**************************************SP Code CR CR-8093-69682*****************************************************/		
		//event for deal il location
		else if((pEventName.equalsIgnoreCase("KeyPress(F3)")) && (pControlName.equalsIgnoreCase("ICICILOMBARD_HT_DEAL_IL_LOCATION")) && (wsName.equalsIgnoreCase("BSG_DATAENTRY")))
		{
			if((formObject.getNGValue("ICICILOMBARD_HT_DEAL_STATUS").equalsIgnoreCase("Yes")) && (formObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE"))&&(formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING")))
			{
				String name="Deal IL Location";		
				//System.out.println("inside event--"+pControlName);
				picklist=formObject.getNGPickList(pControlName, name,true) ;	
				pl=new PickListListener(formObject,pControlName,picklist);
				picklist.addPickListListener(pl);
				picklist.setSearchEnable(true);
				picklist.setVisible(true);
			}
		}
		//event for sol id and bank branch name
		else if((pEventName.equalsIgnoreCase("KeyPress(F3)")) && (pControlName.equalsIgnoreCase("ICICILOMBARD_HT_SOL_ID") || pControlName.equalsIgnoreCase("ICICILOMBARD_HT_BANK_BRANCH_NAME")) && (wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DATAENTRY_QC")))
		{
			String name="";
			if((formObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE"))&&(formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING")))
			{
				name="Bank Branch Name,SOL Id";
			}
			else
			{
				name="Bank Branch Name";	
			}
			//System.out.println("inside bank branch name event "+pControlName);			
			picklist=formObject.getNGPickList(pControlName, name,true) ;	
			pl=new PickListListener(formObject,pControlName,picklist);
			picklist.addPickListListener(pl);
			picklist.setSearchEnable(true);
			picklist.setVisible(true);			
		}
		//event for sp code
		else if((pEventName.equalsIgnoreCase("KeyPress(F3)")) && (pControlName.equalsIgnoreCase("ICICILOMBARD_HT_WRE"))&& (wsName.equalsIgnoreCase("BSG_DATAENTRY")) && (formObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE")) && (formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING")))
		{
			String name="SP Code,SP Name,SP PAN";
			//System.out.println("inside sp code event "+pControlName);
			picklist=formObject.getNGPickList(pControlName, name,true) ;	
			pl=new PickListListener(formObject,pControlName,picklist);
			picklist.addPickListListener(pl);
			picklist.setSearchEnable(true);
			picklist.setVisible(true);
			
		}
		/*******************************Start SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI *******************************/
		//event for sp code krg 
		else if((pEventName.equalsIgnoreCase("KeyPress(F3)")) && (pControlName.equalsIgnoreCase("ICICILOMBARD_HT_WRE"))&& (wsName.equalsIgnoreCase("BSG_DATAENTRY")) && (formObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE")) && (formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)")))
		{
			String name="SP Code,SP Name";
			//System.out.println("inside sp code event "+pControlName);
			picklist=formObject.getNGPickList(pControlName, name,true) ;	
			pl=new PickListListener(formObject,pControlName,picklist);
			picklist.addPickListListener(pl);
			picklist.setSearchEnable(true);
			picklist.setVisible(true);
		}
		/*else if((pEventName.equalsIgnoreCase("KEYPRESS") || pEventName.equalsIgnoreCase("Change")) && (pControlName.equalsIgnoreCase("ICICILOMBARD_HT_WRE"))&& (wsName.equalsIgnoreCase("BSG_DATAENTRY")) && (formObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE")) && (formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)")))
		{
			formObject.setNGValue("ICICILOMBARD_HT_WRE", "");
			formObject.setNGValue("ICICILOMBARD_HT_SP_NAME", "");
			formObject.setNGValue("ICICILOMBARD_HT_SP_PAN", "");
		}*/		
		/*******************************End SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI *******************************/
		/******Start HT CR-8127-95325-GST-Omniflow development******/
		else if((pEventName.equalsIgnoreCase("Change") || pEventName.equalsIgnoreCase("Click")) && pControlName.equalsIgnoreCase("ICICILOMBARD_HT_GST_REGISTERED") && (wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DATAENTRY_QC") || wsName.equalsIgnoreCase("exception") || wsName.equalsIgnoreCase("RM_EXCEPTION")) && formObject.getNGValue("ICICILOMBARD_HT_IAGENT").equalsIgnoreCase("No"))
		{
			System.out.println("inside Change ICICILOMBARD_HT_GST_REGISTERED1");			
			if(formObject.getNGValue("ICICILOMBARD_HT_GST_REGISTERED").equalsIgnoreCase("Yes"))
			{
				System.out.println("if Enable controls");
				formObject.setNGEnable("TXTGST_NUMBER",true);
				//formObject.setNGLocked("TXTGST_NUMBER",false);				
				formObject.setNGEnable("TXTGST_STATE_NAME",true);
				formObject.setNGLocked("TXTGST_STATE_NAME",false);
				formObject.setNGEnable("Add_GST",true);
				formObject.setNGEnable("Mod_GST",true);
				formObject.setNGEnable("Del_GST",true);
				formObject.setNGEnable("ICICILOMBARD_HT_qGrdGstVar",true);
			}
			else
			{
				System.out.println("Else disable controls");
				formObject.setNGValue("TXTGST_NUMBER","");
				formObject.setNGValue("TXTGST_STATE_NAME","");
				formObject.NGClear("ICICILOMBARD_HT_qGrdGstVar");				
				formObject.setNGEnable("TXTGST_NUMBER",false);
				//formObject.setNGLocked("TXTGST_NUMBER",false);				
				formObject.setNGEnable("TXTGST_STATE_NAME",false);				
				formObject.setNGEnable("Add_GST",false);
				formObject.setNGEnable("Mod_GST",false);
				formObject.setNGEnable("Del_GST",false);
				formObject.setNGEnable("ICICILOMBARD_HT_qGrdGstVar",true);
			}
			/*****Start Change related to Application  Proposal no. field in Omni flow HT*****/
			if(formObject.getNGValue("ICICILOMBARD_HT_IAGENT").equalsIgnoreCase("Yes"))
			{
				formObject.setNGEnable("ICICILOMBARD_HT_PF_PROPOSAL_NO",true);
				formObject.setNGLocked("ICICILOMBARD_HT_PF_PROPOSAL_NO",true);
				formObject.setNGEnable("ICICILOMBARD_HT_CNFRM_PF_PROPOSAL_NO",true);
				formObject.setNGLocked("ICICILOMBARD_HT_CNFRM_PF_PROPOSAL_NO",true);
				formObject.setNGEnable("ICICILOMBARD_HT_PROPOSAL_NO",false);
				formObject.setNGLocked("ICICILOMBARD_HT_PROPOSAL_NO",false);
				formObject.setNGEnable("ICICILOMBARD_HT_CONFIRM_PROPOSAL_NO",false);
				formObject.setNGLocked("ICICILOMBARD_HT_CONFIRM_PROPOSAL_NO",false);
			}			
			formObject.setNGEnable("ICICILOMBARD_HT_FUTURE_DATED",true);
			formObject.setNGLocked("ICICILOMBARD_HT_FUTURE_DATED",true);
			formObject.setNGEnable("ICICILOMBARD_HT_LOAN_SANCTIONED_AMT",true);
			formObject.setNGLocked("ICICILOMBARD_HT_LOAN_SANCTIONED_AMT",true);				
			formObject.setNGLocked("ICICILOMBARD_HT_DEAL_IL_LOCATION",false);			
			/*****End Change related to Application  Proposal no. field in Omni flow HT*****/
		}		
		/******End HT CR-8127-95325-GST-Omniflow development******/
		else if((pEventName.equalsIgnoreCase("KEYPRESS") || pEventName.equalsIgnoreCase("Change")) && pControlName.equalsIgnoreCase("ICICILOMBARD_HT_SM_NAME") && wsName.equalsIgnoreCase("BSG_DATAENTRY"))
		{
			if(formObject.getNGValue("ICICILOMBARD_HT_DEAL_STATUS").equalsIgnoreCase("YES"))
			{
			//System.out.println("inside ICICILOMBARD_HT_DEAL_IL_LOCATION SP Code");
			formObject.setNGValue("ICICILOMBARD_HT_DEAL_IL_LOCATION", "");
			formObject.setNGValue("ICICILOMBARD_HT_SOURCE_BUSINESS", "");
			formObject.setNGValue("ICICILOMBARD_HT_CHANNEL_SOURCE", "");
			formObject.setNGValue("ICICILOMBARD_HT_BANK_BRANCH_NAME", "");			
			formObject.setNGValue("ICICILOMBARD_HT_SOL_ID", "");
			formObject.setNGValue("ICICILOMBARD_HT_WRE", "");
			formObject.setNGValue("ICICILOMBARD_HT_SP_NAME", "");
			formObject.setNGValue("ICICILOMBARD_HT_SP_PAN", "");
			//System.out.println("SP All Clear on deal");
			}
		}
		else if((pEventName.equalsIgnoreCase("KEYPRESS") || pEventName.equalsIgnoreCase("Change")) && pControlName.equalsIgnoreCase("ICICILOMBARD_HT_DEAL_IL_LOCATION") && wsName.equalsIgnoreCase("BSG_DATAENTRY"))
		{
			if(formObject.getNGValue("ICICILOMBARD_HT_DEAL_STATUS").equalsIgnoreCase("YES"))
			{
			//System.out.println("inside ICICILOMBARD_HT_DEAL_IL_LOCATION SP Code");
			formObject.setNGValue("ICICILOMBARD_HT_SOURCE_BUSINESS", "");
			formObject.setNGValue("ICICILOMBARD_HT_CHANNEL_SOURCE", "");
			formObject.setNGValue("ICICILOMBARD_HT_BANK_BRANCH_NAME", "");			
			formObject.setNGValue("ICICILOMBARD_HT_SOL_ID", "");
			formObject.setNGValue("ICICILOMBARD_HT_WRE", "");
			formObject.setNGValue("ICICILOMBARD_HT_SP_NAME", "");
			formObject.setNGValue("ICICILOMBARD_HT_SP_PAN", "");
			//System.out.println("SP All Clear on deal");
			}
		}
		else if((pEventName.equalsIgnoreCase("KEYPRESS") || pEventName.equalsIgnoreCase("Change")) && pControlName.equalsIgnoreCase("ICICILOMBARD_HT_SOURCE_BUSINESS") && wsName.equalsIgnoreCase("BSG_DATAENTRY"))
		{
			//System.out.println("inside ICICILOMBARD_HT_DEAL_IL_LOCATION SP Code");
			formObject.setNGValue("ICICILOMBARD_HT_CHANNEL_SOURCE", "");
			formObject.setNGValue("ICICILOMBARD_HT_BANK_BRANCH_NAME", "");
			formObject.setNGValue("ICICILOMBARD_HT_SOL_ID", "");
			formObject.setNGValue("ICICILOMBARD_HT_WRE", "");
			formObject.setNGValue("ICICILOMBARD_HT_SP_NAME", "");
			formObject.setNGValue("ICICILOMBARD_HT_SP_PAN", "");
			//System.out.println("SP All Clear on source business");
		}
		else if((pEventName.equalsIgnoreCase("KEYPRESS") || pEventName.equalsIgnoreCase("Change")) && pControlName.equalsIgnoreCase("ICICILOMBARD_HT_CHANNEL_SOURCE") && wsName.equalsIgnoreCase("BSG_DATAENTRY"))
		{
			//System.out.println("inside ICICILOMBARD_HT_DEAL_IL_LOCATION SP Code");
			formObject.setNGValue("ICICILOMBARD_HT_BANK_BRANCH_NAME", "");
			formObject.setNGValue("ICICILOMBARD_HT_SOL_ID", "");
			formObject.setNGValue("ICICILOMBARD_HT_WRE", "");
			formObject.setNGValue("ICICILOMBARD_HT_SP_NAME", "");
			formObject.setNGValue("ICICILOMBARD_HT_SP_PAN", "");
			//System.out.println("SP All Clear on channel source");
		}
		else if((pEventName.equalsIgnoreCase("KEYPRESS") || pEventName.equalsIgnoreCase("Change")) && (pControlName.equalsIgnoreCase("ICICILOMBARD_HT_BANK_BRANCH_NAME") || pControlName.equalsIgnoreCase("ICICILOMBARD_HT_SOL_ID")) && wsName.equalsIgnoreCase("BSG_DATAENTRY"))
		{
			//System.out.println("inside ICICILOMBARD_HT_DEAL_IL_LOCATION SP Code");
			formObject.setNGValue("ICICILOMBARD_HT_WRE", "");
			formObject.setNGValue("ICICILOMBARD_HT_SP_NAME", "");
			formObject.setNGValue("ICICILOMBARD_HT_SP_PAN", "");
			//System.out.println("SP All Clear on sol and branch name");
		}
		/**************************************End SP Code CR CR-8093-69682*****************************************************/
		else if((pEventName.equalsIgnoreCase("Change") || pEventName.equalsIgnoreCase("Click")) && pControlName.equalsIgnoreCase("ICICILOMBARD_HT_PID_PAYMENT_TYPE") && wsName.equalsIgnoreCase("BSG_DATAENTRY"))
		{
			if(formObject.getNGValue("ICICILOMBARD_HT_PID_PAYMENT_TYPE").equalsIgnoreCase("Yes"))
			{
				formObject.setNGValue("ICICILOMBARD_HT_PAYMENT_MODE","");
				formObject.setNGEnable("ICICILOMBARD_HT_PAYMENT_MODE",false);
				formObject.setNGLocked("ICICILOMBARD_HT_PAYMENT_MODE", false);
				formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_NO", "");
				formObject.setNGLocked("ICICILOMBARD_HT_INSTRUMENT_NO", false);
				formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_AMOUNT", "");
				formObject.setNGLocked("ICICILOMBARD_HT_INSTRUMENT_AMOUNT", false);
				formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_DATE", "");
				formObject.setNGEnable("ICICILOMBARD_HT_INSTRUMENT_DATE", false);
				formObject.setNGValue("ICICILOMBARD_HT_BANK_NAME", "");
				formObject.setNGLocked("ICICILOMBARD_HT_BANK_NAME", false);
				formObject.setNGValue("ICICILOMBARD_HT_PAYMENT_ID", "");
				formObject.setNGLocked("ICICILOMBARD_HT_PAYMENT_ID", false);
				
				formObject.setNGValue("ICICILOMBARD_HT_SAVINGS_ACCOUNT_NO","");
				//formObject.setNGValue("ICICILOMBARD_HT_PID_NO","");
				formObject.setNGValue("ICICILOMBARD_HT_CREDIT_CARD_NO","");
				formObject.setNGValue("ICICILOMBARD_HT_CREDITCARD_EXP_DATE","");
				formObject.setNGValue("ICICILOMBARD_HT_AUTHORIZATION_CODE","");
				formObject.setNGValue("ICICILOMBARD_HT_AUTHORIZATION_DATE","");
				
				formObject.setNGEnable("FRAME_CHEQUE_DD",false);
				formObject.setNGEnable("FRAME_SAVINGS_ACCOUNT",false);
				formObject.setNGEnable("FRAME_CREDIT_CARD",false);
				formObject.setNGEnable("ICICILOMBARD_HT_BANK_NAME",false);
				formObject.setNGEnable("ICICILOMBARD_HT_PAYMENT_ID",false);
				//formObject.setNGEnable("FRAME_PID_NO",false);	
			}
			else
			{
				formObject.setNGEnable("ICICILOMBARD_HT_PAYMENT_MODE",true);
				formObject.setNGLocked("ICICILOMBARD_HT_PAYMENT_MODE", false);
				formObject.setNGEnable("ICICILOMBARD_HT_BANK_NAME",true);
				formObject.setNGEnable("ICICILOMBARD_HT_PAYMENT_ID",true);
				formObject.setNGLocked("ICICILOMBARD_HT_PAYMENT_ID", true);
				
				/*formObject.setNGLocked("ICICILOMBARD_HT_INSTRUMENT_NO", true);
				formObject.setNGEnable("ICICILOMBARD_HT_INSTRUMENT_NO",true);
				
				formObject.setNGLocked("ICICILOMBARD_HT_INSTRUMENT_NO", true);
				formObject.setNGEnable("ICICILOMBARD_HT_INSTRUMENT_NO",true);
				
				formObject.setNGLocked("ICICILOMBARD_HT_INSTRUMENT_AMOUNT", true);
				formObject.setNGEnable("ICICILOMBARD_HT_INSTRUMENT_AMOUNT",true);
			
				formObject.setNGEnable("ICICILOMBARD_HT_INSTRUMENT_DATE", true);
				formObject.setNGEnable("ICICILOMBARD_HT_INSTRUMENT_DATE",true);
			
				formObject.setNGLocked("ICICILOMBARD_HT_BANK_NAME", true);
				
				formObject.setNGLocked("ICICILOMBARD_HT_PAYMENT_ID", true);
				
				formObject.setNGEnable("FRAME_CHEQUE_DD",true);
				formObject.setNGEnable("FRAME_SAVINGS_ACCOUNT",true);
				formObject.setNGEnable("FRAME_CREDIT_CARD",true);
				formObject.setNGEnable("ICICILOMBARD_HT_BANK_NAME",true);
				formObject.setNGEnable("ICICILOMBARD_HT_PAYMENT_ID",true);*/
			}
		}
		else if((pEventName.equalsIgnoreCase("Change") || pEventName.equalsIgnoreCase("KEYPRESS")) && pControlName.equalsIgnoreCase("ICICILOMBARD_HT_PAYMENT_MODE") && (wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DATAENTRY_QC") || wsName.equalsIgnoreCase("EXCEPTION")))
		{
			String fieldValue = formObject.getNGValue("ICICILOMBARD_HT_PAYMENT_MODE");
			formObject.setNGEnable("FRAME_CHEQUE_DD",false);
			formObject.setNGEnable("FRAME_SAVINGS_ACCOUNT",false);
			formObject.setNGEnable("FRAME_CREDIT_CARD",false);
			formObject.setNGEnable("FRAME_PID_NO",false);
			if(fieldValue.equalsIgnoreCase("Cheque") || fieldValue.equalsIgnoreCase("Demand Draft"))
			{
				formObject.setNGValue("ICICILOMBARD_HT_SAVINGS_ACCOUNT_NO","");
				//formObject.setNGValue("ICICILOMBARD_HT_PID_NO","");
				formObject.setNGValue("ICICILOMBARD_HT_CREDIT_CARD_NO","");
				formObject.setNGValue("ICICILOMBARD_HT_CREDITCARD_EXP_DATE","");
				formObject.setNGValue("ICICILOMBARD_HT_AUTHORIZATION_CODE","");
				formObject.setNGValue("ICICILOMBARD_HT_AUTHORIZATION_DATE","");
				formObject.setNGEnable("FRAME_CHEQUE_DD",true);
			}					
			else if(fieldValue.equalsIgnoreCase("Fund Transfer"))
			{
				formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_NO","");
				formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_DATE","");
				formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_AMOUNT","");
				//formObject.setNGValue("ICICILOMBARD_HT_PID_NO","");
				formObject.setNGValue("ICICILOMBARD_HT_CREDIT_CARD_NO","");
				formObject.setNGValue("ICICILOMBARD_HT_CREDITCARD_EXP_DATE","");
				formObject.setNGValue("ICICILOMBARD_HT_AUTHORIZATION_CODE","");
				formObject.setNGValue("ICICILOMBARD_HT_AUTHORIZATION_DATE","");
				formObject.setNGEnable("FRAME_SAVINGS_ACCOUNT",true);
			}					
			else if(fieldValue.equalsIgnoreCase("Credit Card"))
			{
				//formObject.setNGValue("ICICILOMBARD_HT_PID_NO","");
				formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_NO","");
				formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_DATE","");
				formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_AMOUNT","");
				formObject.setNGValue("ICICILOMBARD_HT_SAVINGS_ACCOUNT_NO","");
									
				formObject.setNGEnable("FRAME_CREDIT_CARD",true);
			}					
			else if(fieldValue.equalsIgnoreCase("CDBG"))
			{
				formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_NO","");
				formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_DATE","");
				formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_AMOUNT","");
				formObject.setNGValue("ICICILOMBARD_HT_SAVINGS_ACCOUNT_NO","");
				formObject.setNGValue("ICICILOMBARD_HT_CREDIT_CARD_NO","");
				formObject.setNGValue("ICICILOMBARD_HT_CREDITCARD_EXP_DATE","");
				formObject.setNGValue("ICICILOMBARD_HT_AUTHORIZATION_CODE","");
				formObject.setNGValue("ICICILOMBARD_HT_AUTHORIZATION_DATE","");
				formObject.setNGEnable("FRAME_PID_NO",true);
			}					
			else if(fieldValue.equalsIgnoreCase("ECS"))
			{
				formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_NO","");
				formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_DATE","");
				formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_AMOUNT","");
				formObject.setNGValue("ICICILOMBARD_HT_SAVINGS_ACCOUNT_NO","");
				formObject.setNGValue("ICICILOMBARD_HT_CREDIT_CARD_NO","");
				formObject.setNGValue("ICICILOMBARD_HT_CREDITCARD_EXP_DATE","");
				formObject.setNGValue("ICICILOMBARD_HT_AUTHORIZATION_CODE","");
				formObject.setNGValue("ICICILOMBARD_HT_AUTHORIZATION_DATE","");
				//formObject.setNGValue("ICICILOMBARD_HT_PID_NO","");
			}					
			else if(fieldValue.equalsIgnoreCase("Others"))
			{
				formObject.setNGEnable("FRAME_CHEQUE_DD",true);
				formObject.setNGEnable("ICICILOMBARD_HT_INSTRUMENT_NO",true);
				formObject.setNGEnable("ICICILOMBARD_HT_INSTRUMENT_DATE",true);
				formObject.setNGEnable("ICICILOMBARD_HT_INSTRUMENT_AMOUNT",true);
				formObject.setNGEnable("FRAME_SAVINGS_ACCOUNT",true);
				formObject.setNGEnable("FRAME_CREDIT_CARD",true);
				//formObject.setNGEnable("FRAME_PID_NO",true);
			}
			else
			{}
		}
		/******************************End PID-HT process changes ******************************/	
		////event for branch id ubo name	
		else if((pEventName.equalsIgnoreCase("KeyPress(F3)")) && (pControlName.equalsIgnoreCase("ICICILOMBARD_HT_BRANCH_ID_UBO_NAME")) && (wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DATAENTRY_QC")))
		{
			String name="Branch ID UBO Name";		
			//System.out.println("inside event--"+pControlName);
			picklist=formObject.getNGPickList(pControlName, name,true) ;	
			pl=new PickListListener(formObject,pControlName,picklist);
			picklist.addPickListListener(pl);
			picklist.setSearchEnable(true);
			picklist.setVisible(true);
		}
			
		////event for sm name	
		/********************** Start MO filteration for Centralised Deal Vertical **********************/		
		else if((pEventName.equalsIgnoreCase("KeyPress(F3)")) && (pControlName.equalsIgnoreCase("ICICILOMBARD_HT_SM_NAME")) && (wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DATAENTRY_QC") || wsName.equalsIgnoreCase("EXCEPTION")) && (formObject.getNGValue("ICICILOMBARD_HT_VERTICAL")!=null) && (formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL")!=null) && !(formObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("NA")) && !(formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("NA")))
		{
			String name="SM NAME, SM ID";
			if (formObject.getNGValue("ICICILOMBARD_HT_DEAL_STATUS").equalsIgnoreCase("YES"))
			{
				 name="SM NAME, SM ID, SUB VERTICAL, PRIM VERTICAL";					
			}			
			picklist=formObject.getNGPickList(pControlName, name,true) ;	
			pl=new PickListListener(formObject,pControlName,picklist);
			picklist.addPickListListener(pl);
			picklist.setSearchEnable(true);
			picklist.setVisible(true);
		}		
		/********************** End MO filteration for Centralised Deal Vertical   **********************/

		////event for bank name	
		else if(pEventName.equalsIgnoreCase("KeyPress(F3)") && pControlName.equalsIgnoreCase("ICICILOMBARD_HT_BANK_NAME") && wsName.equalsIgnoreCase("BSG_DATAENTRY"))
		{
			String name="Bank Name,Bank code";		
			picklist=formObject.getNGPickList(pControlName, name,true) ;	
			pl=new PickListListener(formObject,pControlName,picklist);
			picklist.addPickListListener(pl);
			 picklist.setSearchEnable(true);
			picklist.setVisible(true);
		}
			
		////event for sub product name
		else if(pEventName.equalsIgnoreCase("KeyPress(F3)") && pControlName.equalsIgnoreCase("ICICILOMBARD_HT_SUB_PRODUCT") && (wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DATAENTRY_QC") || wsName.equalsIgnoreCase("EXCEPTION")))
		{
			String name="Sub Product, Product Abbr. Code";		
			picklist=formObject.getNGPickList(pControlName, name,true) ;	
			pl=new PickListListener(formObject,pControlName,picklist);
			picklist.addPickListListener(pl);
			 picklist.setSearchEnable(true);
			picklist.setVisible(true);
			//System.out.println("inside f3");
		}
			
		//change event sub product
		else if(pEventName.equalsIgnoreCase("Change") && pControlName.equalsIgnoreCase("ICICILOMBARD_HT_PRODUCT_ABBR_CODE") && (wsName.equalsIgnoreCase("BSG_DATAENTRY")| wsName.equalsIgnoreCase("BSG_DATAENTRY_QC") | wsName.equalsIgnoreCase("Exception")))
		{
			if((formObject.getNGValue("ICICILOMBARD_HT_PRODUCT_ABBR_CODE").equalsIgnoreCase("hsp") && !formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("COMPLETE HEALTH INSURANCE"))
			/*********************** CR 24 HSP product by satish *************************/
			|| formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("HOMEINSURANCEPRODUCT") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("SECURE MIND")|| formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("Secure Mind A") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("GROUP SECURE MIND") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("GROUP PERSONAL ACCIDENT") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("CCPI") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("Asset Protect") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("Merchant Cover") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("Home")
			/********************** end CR 24 HSP product by satish **********************/
			)
			{
				formObject.setNGEnable("FRAME_IF_PRODUCT_HSP",true);
				/****************** CR 24 HSP product by satish *********************/
				/*if(!(formObject.getNGValue("ICICILOMBARD_HT_PRODUCT_ABBR_CODE").equalsIgnoreCase("hsp") && !formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("COMPLETE HEALTH INSURANCE")))
				{
					formObject.setNGEnable("ICICILOMBARD_HT_IAGENT",false);

				}FlapPrint Code Commented Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR */
				/**************** end CR 24 HSP product by satish *******************/
			}
			else
			{
				formObject.setNGEnable("FRAME_IF_PRODUCT_HSP",true);
				
				formObject.setNGValue("ICICILOMBARD_HT_DOB","");
				formObject.setNGValue("ICICILOMBARD_HT_SANCTION_DATE","");
				formObject.setNGValue("ICICILOMBARD_HT_DISBURSAL_DATE","");
				formObject.setNGValue("ICICILOMBARD_HT_OCCUPATION","");
				formObject.setNGValue("ICICILOMBARD_HT_SUM_INSURED","");
				//formObject.setNGValue("ICICILOMBARD_HT_SECONDARY_DEAL_NO","");
				formObject.setNGValue("ICICILOMBARD_HT_DEAL1","");
				formObject.setNGValue("ICICILOMBARD_HT_DEAL2","");
				/***** CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket*******/
				//formObject.setNGValue("ICICILOMBARD_HT_PRIMARY_VERTICAL","");
				//formObject.setNGValue("ICICILOMBARD_HT_SECONDARY_VERTICAL","");
				/***** End CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket***/
			
				formObject.NGClear("ICICILOMBARD_HT_APPLICANT_NO");
				formObject.NGAddItem("ICICILOMBARD_HT_APPLICANT_NO","--Select--");
				formObject.NGAddItem("ICICILOMBARD_HT_APPLICANT_NO","1");
				formObject.NGAddItem("ICICILOMBARD_HT_APPLICANT_NO","2");
				formObject.NGAddItem("ICICILOMBARD_HT_APPLICANT_NO","3");
				formObject.NGAddItem("ICICILOMBARD_HT_APPLICANT_NO","4");
				formObject.NGAddItem("ICICILOMBARD_HT_APPLICANT_NO","5");
				formObject.NGAddItem("ICICILOMBARD_HT_APPLICANT_NO","6");
				formObject.NGAddItem("ICICILOMBARD_HT_APPLICANT_NO","7");
								
				formObject.NGClear("ICICILOMBARD_HT_HSP_VERTICAL");
				formObject.NGAddItem("ICICILOMBARD_HT_HSP_VERTICAL","--Select--");
				formObject.NGAddItem("ICICILOMBARD_HT_HSP_VERTICAL","CSG");
				formObject.NGAddItem("ICICILOMBARD_HT_HSP_VERTICAL","EMMG");
				formObject.NGAddItem("ICICILOMBARD_HT_HSP_VERTICAL","HEG");
				formObject.NGAddItem("ICICILOMBARD_HT_HSP_VERTICAL","HLG");
				formObject.NGAddItem("ICICILOMBARD_HT_HSP_VERTICAL","HFC");
				formObject.NGAddItem("ICICILOMBARD_HT_HSP_VERTICAL","XSell");
				formObject.NGAddItem("ICICILOMBARD_HT_HSP_VERTICAL","RAOG");
				formObject.setNGEnable("FRAME_IF_PRODUCT_HSP",false);
				//cr 24
				//formObject.setNGEnable("ICICILOMBARD_HT_IAGENT",true); FlapPrint Code Commented Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR
			}

				formObject.setNGValue("ICICILOMBARD_HT_PREFIX_PROPOSAL_NO","");
				formObject.setNGValue("ICICILOMBARD_HT_PREFIX_CONFIRM_PROPOSAL_NO","");
			
				if(debug==1)
				{
					//System.out.println("inside command SUB_PRODUCT_CHANGE change");
				}

				formObject.setNGValue("ICICILOMBARD_HT_PREFIX_PROPOSAL_NO",formObject.getNGValue("ICICILOMBARD_HT_PRODUCT_ABBR_CODE"));
				formObject.setNGValue("ICICILOMBARD_HT_PREFIX_CONFIRM_PROPOSAL_NO",formObject.getNGValue("ICICILOMBARD_HT_PRODUCT_ABBR_CODE"));
				
				// Yogendra Saraswat --  for CRITICAL CARE case--
				if(formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("CRITICAL CARE")
				/************************** CR 24 HSP product by satish **************************/
				|| formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("HOMEINSURANCEPRODUCT") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("SECURE MIND")|| formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("Secure Mind A") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("GROUP SECURE MIND") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("GROUP PERSONAL ACCIDENT") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("CCPI") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("Asset Protect") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("Merchant Cover") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("Home")
				/************************ end CR 24 HSP product by satish ************************/
				)
				{
					formObject.NGClear("ICICILOMBARD_HT_PREFIX_PROPOSAL_NO");
				    formObject.NGClear("ICICILOMBARD_HT_PREFIX_CONFIRM_PROPOSAL_NO");
					formObject.setNGValue("ICICILOMBARD_HT_PREFIX_PROPOSAL_NO",formObject.getNGValue("ICICILOMBARD_HT_PRODUCT_ABBR_CODE"));
					formObject.setNGValue("ICICILOMBARD_HT_PREFIX_CONFIRM_PROPOSAL_NO",formObject.getNGValue("ICICILOMBARD_HT_PRODUCT_ABBR_CODE"));
				}
				// Yogendra Saraswat --  end for CRITICAL CARE case--
		}

		//center code	
		else if((pEventName.equalsIgnoreCase("KeyPress(F3)")) && (pControlName.equalsIgnoreCase("ICICILOMBARD_HT_CENTER_CODE_NAME")) && (wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DATAENTRY_QC") || wsName.equalsIgnoreCase("exception")))			
		{
			String name="Center Code Name";		
			picklist=formObject.getNGPickList(pControlName, name,true) ;	
			pl=new PickListListener(formObject,pControlName,picklist);
			picklist.addPickListListener(pl);
			 picklist.setSearchEnable(true);
			picklist.setVisible(true);
		}
		/******Start HT CR-8127-95325-GST-Omniflow development******/
		else if((pEventName.equalsIgnoreCase("KeyPress(F3)")) && (pControlName.equalsIgnoreCase("TXTGST_STATE_NAME")) && (wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DATAENTRY_QC") || wsName.equalsIgnoreCase("exception") ||wsName.equalsIgnoreCase("RM_EXCEPTION")) && (formObject.getNGValue("ICICILOMBARD_HT_GST_REGISTERED").equalsIgnoreCase("Yes")) && formObject.getNGValue("ICICILOMBARD_HT_IAGENT").equalsIgnoreCase("No"))			
		{
			System.out.println("inside Change ICICILOMBARD_HT_GST_REGISTERED1");
			String name="State Name";		
			picklist=formObject.getNGPickList(pControlName, name,true) ;	
			pl=new PickListListener(formObject,pControlName,picklist);
			picklist.addPickListListener(pl);
			 picklist.setSearchEnable(true);
			picklist.setVisible(true);
		}
		/******End HT CR-8127-95325-GST-Omniflow development******/
		/*****Start Change related to Application  Proposal no. field in Omni flow HT*****/
		else if((pEventName.equalsIgnoreCase("KeyPress(F3)")) && (pControlName.equalsIgnoreCase("ICICILOMBARD_HT_CHANNEL_LOAN_TYPE")) && (wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DATAENTRY_QC") || wsName.equalsIgnoreCase("exception") ||wsName.equalsIgnoreCase("RM_EXCEPTION")))			
		{
			System.out.println("inside Change ICICILOMBARD_HT_CHANNEL_LOAN_TYPE");
			String name="Channel,Source";		
			picklist=formObject.getNGPickList(pControlName, name,true) ;	
			pl=new PickListListener(formObject,pControlName,picklist);
			picklist.addPickListListener(pl);
			 picklist.setSearchEnable(true);
			picklist.setVisible(true);
		}
		/*****End Change related to Application  Proposal no. field in Omni flow HT*****/
		//source business	
		else if(pEventName.equalsIgnoreCase("Change") && pControlName.equalsIgnoreCase("ICICILOMBARD_HT_SOURCE_BUSINESS"))			
		{
			
			if(wsName.equalsIgnoreCase("BSG_DATAENTRY") | wsName.equalsIgnoreCase("BSG_DATAENTRY_QC") | wsName.equalsIgnoreCase("exception"))
			{
				if(formObject.getNGValue("ICICILOMBARD_HT_SOURCE_BUSINESS").equalsIgnoreCase("Sub Broker"))
				{
					//formObject.setNGEnable("FRAME_SUB_BROKER",true);//CR25_Masters of KRG,Removal of fields & RMT Bucket
					formObject.setNGEnable("FRAME_CENTER_CODE_RM",true);
					//formObject.setNGValue("ICICILOMBARD_HT_CENTER_CODE_NAME","");
					formObject.setNGListIndex("ICICILOMBARD_HT_CENTER_CODE_NAME",0);
					formObject.setNGValue("ICICILOMBARD_HT_RM_EMPLOYEE","");
					formObject.setNGEnable("FRAME_CENTER_CODE_RM",false);
				}

				else if(formObject.getNGValue("ICICILOMBARD_HT_SOURCE_BUSINESS").equalsIgnoreCase("Center Sales") || formObject.getNGValue("ICICILOMBARD_HT_SOURCE_BUSINESS").equalsIgnoreCase("Centre Sales"))
				{
					formObject.setNGEnable("FRAME_CENTER_CODE_RM",true);
					/***** CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket*******/
					/* formObject.setNGEnable("FRAME_SUB_BROKER",true);
					formObject.setNGValue("ICICILOMBARD_HT_SUB_BROKER_CODE","");
					formObject.setNGValue("ICICILOMBARD_HT_TERRITORY_MANAGER","");
					formObject.setNGValue("ICICILOMBARD_HT_SUB_BROKER",""); 
					formObject.setNGEnable("FRAME_SUB_BROKER",false);*/
					/***** End CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket***/
				}
				else
				{
					formObject.setNGEnable("FRAME_CENTER_CODE_RM",true);
					//formObject.setNGValue("ICICILOMBARD_HT_CENTER_CODE_NAME","");
					formObject.setNGListIndex("ICICILOMBARD_HT_CENTER_CODE_NAME",0);
					formObject.setNGValue("ICICILOMBARD_HT_RM_EMPLOYEE","");
					formObject.setNGEnable("FRAME_CENTER_CODE_RM",false);
					/***** CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket*******/
					/*formObject.setNGEnable("FRAME_SUB_BROKER",true);
					formObject.setNGValue("ICICILOMBARD_HT_SUB_BROKER_CODE","");
					formObject.setNGValue("ICICILOMBARD_HT_TERRITORY_MANAGER","");
					formObject.setNGValue("ICICILOMBARD_HT_SUB_BROKER","");
					formObject.setNGEnable("FRAME_SUB_BROKER",false);*/
					/***** End CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket***/
				}
			}
		}
		
		//prodcut change	
		else if(pEventName.equalsIgnoreCase("Click") && pControlName.equalsIgnoreCase("ICICILOMBARD_HT_PRODUCT") && wsName.equalsIgnoreCase("BSG_DATAENTRY"))
		{
			String fieldName="ICICILOMBARD_HT_PRODUCT";
			String fieldValue=formObject.getNGValue(fieldName);
			
			//changes
			formObject.NGClear("ICICILOMBARD_HT_SUB_PRODUCT");
			formObject.NGClear("ICICILOMBARD_HT_PRODUCT_ABBR_CODE");

			if(fieldValue.equals("--Select--"))
			{
			
			}
			else 				
			{
				formObject.NGClear("PRODUCT_HIDDEN");
				String arrfieldName6[]= new String[1];
				noOfCols="1";
				query="select productcode from MV_UW_PRODUCT_MASTER where productname=N'"+fieldValue+"'";
				arrfieldName6[0]="PRODUCT_HIDDEN";
				getData(query,noOfCols,arrfieldName6,flag);
				//System.out.println("ICICILOMBARD_HT_PRODUCT_CODE: "+ formObject.getNGItemText("PRODUCT_HIDDEN",1));
				formObject.setNGValue("ICICILOMBARD_HT_PRODUCT_CODE",formObject.getNGItemText("PRODUCT_HIDDEN",1));
				//System.out.println("ICICILOMBARD_HT_PRODUCT_CODE: "+ formObject.getNGValue("ICICILOMBARD_HT_PRODUCT_CODE"));
			}
		}
		/********************** Start Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR ***********************/
			
			if((pEventName.equalsIgnoreCase("Change") || pEventName.equalsIgnoreCase("Click")) && pControlName.equalsIgnoreCase("ICICILOMBARD_HT_TRANSACTION_TYPE") && (wsName.equalsIgnoreCase("BSG_DATAENTRY_QC") || wsName.equalsIgnoreCase("EXCEPTION") || wsName.equalsIgnoreCase("RM_EXCEPTION"))) 
			{
				if(formObject.getNGValue("ICICILOMBARD_HT_TRANSACTION_TYPE").equalsIgnoreCase("Renewal") || formObject.getNGValue("ICICILOMBARD_HT_TRANSACTION_TYPE").equalsIgnoreCase("Rollover"))
				{
				
					//System.out.println("Inside Change Event Chicking For Policy No For HT- CR-8093-59790 (HT-FlapPrint) CR");
					formObject.setNGEnable("FRAME_PREV_POLICY_NUMBER",true);
					//System.out.println("FRAME_PREV_POLICY_NUMBER:- "+formObject.getNGValue("FRAME_PREV_POLICY_NUMBER"));
				}
				else
				{
					//System.out.println("Inside Change Event Disabling For Policy No For HT- CR-8093-59790 (HT-FlapPrint) CR");
					formObject.setNGEnable("FRAME_PREV_POLICY_NUMBER",false);
					//System.out.println("FRAME_PREV_POLICY_NUMBER:- "+formObject.getNGValue("FRAME_PREV_POLICY_NUMBER"));
				}

			}
			
			if((pEventName.equalsIgnoreCase("Change")) && (pControlName.equalsIgnoreCase("ICICILOMBARD_HT_IL_LOCATION") || pControlName.equalsIgnoreCase("ICICILOMBARD_HT_IL_LOCATION_CODE")))
			{
				//System.out.println("Inside Change Event Checking For Location");	
				String loc_name = formObject.getNGValue("ICICILOMBARD_HT_IL_LOCATION");
				String loc_code = formObject.getNGValue("ICICILOMBARD_HT_IL_LOCATION_CODE");
				//System.out.println("loc_name==="+loc_name);
				//System.out.println("loc_code==="+loc_code);			
				String locQuery = "SELECT CAL_NAME FROM NG_HT_CAL_MAPPING_MASTER WHERE STATE_ID=(SELECT STATEID FROM NG_ICICI_MST_ILLocation WHERE ILBRANCHCODE='"+loc_code+"')";
				String v_CalName= "";
				//System.out.println("locQuery ::\t" + locQuery);	
				ArrayList getCalname = formObject.getNGDataFromDataSource(locQuery, 1);
					if (getCalname != null) {
						for (int i = 0; i < getCalname.size(); i++) {
							v_CalName = (getCalname.get(i)).toString();
							v_CalName = v_CalName.substring((v_CalName.indexOf("[") + 1), (v_CalName.indexOf("]")));
							//System.out.println("v_CalName ::" + i + "\t" + v_CalName);					
						}
				}
				if(!v_CalName.equalsIgnoreCase(""))
				{				
					formObject.setNGValue("ICICILOMBARD_HT_CALENDAR_NAME",v_CalName);
					//System.out.println("Calendar_name successfully set for il_location===> "+loc_code);		
				}
				else
				{
					formObject.setNGValue("ICICILOMBARD_HT_CALENDAR_NAME","Cal_Common");
					//System.out.println("Calendar_name not found for il_location===> "+loc_code);	
				}				
			}

		/********************** End Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR *************************/		
		
		
		return "";
	}
	
	/********************** Start Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR ***********************/

	public void showPage(String url,String type)
	{
		//System.out.println("Entering showPage at : "+url +" ===type=="+type);
		js =formObject.getJSObject();	
		String str[]= new String[1];
		str[0] = url;
		Object obj = null;
		
		if(type.equalsIgnoreCase("FetchCopy")) //BTN_FETCHCOPY(Button Name)
		{
			//System.out.println("Entering showPage Function For FetchCopy Functionality: ");
			obj=js.call("OpenFetchCopy_HT",str);
		}
		//System.out.println("Exiting showPage at : ");
	}

	/********************** End Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR *************************/

}