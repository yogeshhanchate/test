package com.newgen.formApplet.User;

import com.newgen.formApplet.*;
import com.newgen.formApplet.User.*;
import netscape.javascript.*;


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
import com.newgen.formApplet.User.NGEjbCalls;

public class Validation{
String htinstdate=null;
String instdate;
JSObject jsObj = null;
private NGFPropInterface formObject = null;
	public Validation(NGFPropInterface formObject) //formObject Mandatory
	{
		this.formObject = formObject;
	}
public int MandatoryControls(String pEvent,String ActivityName,String WorkItemName)
{
	String wsName=formObject.getWFActivityName();
	String fieldName="";
	String value="";
	String Label="";
	String exceptionStatus = "";//PID-HT process changes
	//////System.out.println("isNGEnable"+formObject.isNGEnable("FRAME_PREV_POLICY_NUMBER"));
	//////System.out.println("isNGEnable"+formObject.isNGEnable("ICICILOMBARD_HT_PREVIOUS_POLICY_NO"));
//	////System.out.println("wsName"+wsName);
	//JOptionPane.showMessageDialog(null,"ICICILOMBARD_HT_CUSTOMERNUMBER"+formObject.getNGValue("ICICILOMBARD_HT_CUSTOMERNUMBER"));
	//////////COPS_Team
	/*if(wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")||wsName.equals("COPS_Team"))
	{
		//////System.out.println("wsName"+wsName);
		//////System.out.println("Exception");
		String str[]= new String[1];
		//////System.out.println("Exception2");	
		str[0] = "ChkExpClear";
		JSObject js=formObject.getJSObject() ;
		//////System.out.println("Exception"+js);

		//String 
		 Object obj=js.call("JSCheckExceptionStatus",str);
	//	 ////System.out.println(obj.toString());
			
	/////////////
	} */
	if(pEvent.equals("D"))
	{
		/*if(wsName.equals("Exception") )
		{
			String str[]= new String[1];
		//////System.out.println("Exception2");	
			str[0] = "ChkExpClear";
			JSObject js=formObject.getJSObject() ;
		//	////System.out.println("Exception"+js);

			//String 
			 Object obj=js.call("JSCheckExceptionStatus",str);
			 String excep_status=obj.toString();
	//	 ////System.out.println("excep_status-->"+excep_status);
	 
		 if(excep_status.equals("R"))
			{
				JOptionPane.showMessageDialog(null,"Please Clear Exception ");
				return 0;
			}

		}
		///////////////end Exception//////////
		///////////////////////////Exception Raised by user/////
		if(wsName.equals("BSG_DataEntry_QC")||wsName.equals("COPS_Team"))
		{
		String str[]= new String[1];
		//////System.out.println("Exception2");	
			str[0] = "ChkExpClear";
			JSObject js=formObject.getJSObject() ;
		//	////System.out.println("Exception"+js);

			//String 
			 Object obj=js.call("JSCheckExceptionStatus",str);
			 String excep_status=obj.toString();
			//////System.out.println("excep_status-->"+excep_status);
			if(excep_status.equals("R"))
				{
				return 1;
				}

		} */
		
		/******************************* PID-HT process changes ********************************/
		String str[]= new String[1];
		str[0] = "ChkExpRaise";
		JSObject js=formObject.getJSObject() ;
		Object obj=js.call("JSCheckExceptionStatus",str);
		formObject.setNGValue("ICICILOMBARD_HT_LAST_PROCESSED_BY",formObject.getUserName()); //Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR
		System.out.println("LAST_PROCESSED_BY=="+formObject.getNGValue("ICICILOMBARD_HT_LAST_PROCESSED_BY"));
		if((wsName.equalsIgnoreCase("BSG_DataEntry_QC") || wsName.equalsIgnoreCase("COPS_Team")))
		{
			if(obj.toString().equalsIgnoreCase("R"))
			{
				////System.out.println("Exception Status: "+obj.toString());
				exceptionStatus = obj.toString();
				/********************** Start Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR ***********************/
				//String crwrkstep="BSG_QC";
				////System.out.println("Start Raising Exception For Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR At:"+wsName);					
				exceptionDetails(wsName);
				////System.out.println("After Raising Exception For Omniflow HT- CR-8093-59790 (HT-FlapPrint) C");
				/********************** End Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR *************************/
				formObject.setNGEnable("FRAME_ROUTETO",true);
				formObject.setNGEnable("ICICILOMBARD_HT_Route_To",true);
				formObject.setNGLocked("ICICILOMBARD_HT_Route_To",true);
				if(validateType("ICICILOMBARD_HT_Route_To",formObject.getNGValue("ICICILOMBARD_HT_Route_To"),"emptyCheck","Route To")==0)
					return 0;
			}
		}
		
		/********************** Start Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR ***********************/

		if(wsName.equalsIgnoreCase("BSG_DataEntry_QC") && (formObject.getNGValue("ICICILOMBARD_HT_PID_PAYMENT_TYPE").equalsIgnoreCase("Yes")))
		{
			
				////System.out.println("Inside BSG_DataEntry_QC Checking Date Validation For HT- CR-8093-59790 (HT-FlapPrint) CR");
				////System.out.println("ICICILOMBARD_HT_POLICY_PID_START_DATE At BSG_DataEntry_QC : "+formObject.getNGValue("ICICILOMBARD_HT_POLICY_PID_START_DATE"));
				////System.out.println("ICICILOMBARD_HT_INSTRUMENT_DATE At BSG_DataEntry_QC : "+formObject.getNGValue("ICICILOMBARD_HT_INSTRUMENT_DATE"));
			
				
				if(validateType("ICICILOMBARD_HT_POLICY_PID_START_DATE",formObject.getNGValue("ICICILOMBARD_HT_POLICY_PID_START_DATE"),"emptyCheck","PID Policy Start Date")==0)
				{
					return 0;
				}
				////System.out.println("Before DATESCOMPARE Function For HT- CR-8093-59790 (HT-FlapPrint) CR ");
				
				/*htinstdate = formObject.getNGValue("ICICILOMBARD_HT_INSTRUMENT_DATE");
				////System.out.println("ICICILOMBARD_HT_INSTRUMENT_DATE"+htinstdate);
				instdate = formatConvert(htinstdate);
				////System.out.println("ICICILOMBARD_HT_INSTRUMENT_DATE After Conversion :" +instdate);*/
		}
		
		if(wsName.equalsIgnoreCase("BSG_DataEntry_QC") && (formObject.getNGValue("ICICILOMBARD_HT_PID_PAYMENT_TYPE").equalsIgnoreCase("Yes")) && (formObject.getNGValue("ICICILOMBARD_HT_INSTRUMENT_DATE") != null && !formObject.getNGValue("ICICILOMBARD_HT_INSTRUMENT_DATE").equalsIgnoreCase("")))
		{		
				////System.out.println("Calling Date Compare Function IF Both Dates Are Filled.. ");
				int date_diff = datesCompare(formObject.getNGValue("ICICILOMBARD_HT_POLICY_PID_START_DATE"),formObject.getNGValue("ICICILOMBARD_HT_INSTRUMENT_DATE"));
				/*if (date_diff >= 3) 
				{
					////System.out.println("Inside datesCompare Function At BSG_DataEntry_QC Checking 64 VB Case For HT- CR-8093-59790 (HT-FlapPrint) CR ");
					JOptionPane.showMessageDialog(null, "64 VB Case");
					formObject.NGFocus("ICICILOMBARD_HT_POLICY_PID_START_DATE");
					return 0;
				}*/	
				if(date_diff < 0)
				{
					JOptionPane.showMessageDialog(null, "64 VB Case");
					formObject.NGFocus("ICICILOMBARD_HT_POLICY_PID_START_DATE");
					////System.out.println("Inside Else : Policy Start Date is less than Payment Date ");
					return 0;
				}
		}
		
		/********************** End Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR *************************/

		if(wsName.equalsIgnoreCase("Exception") || wsName.equalsIgnoreCase("RM_Exception"))
		{
			////System.out.println("wsName"+wsName);
			////System.out.println("Exception Validation");
			/*String str[]= new String[1];
			str[0] = "ChkExpRaise";
			JSObject js=formObject.getJSObject() ;
			Object obj=js.call("MHT_JSCheckExceptionStatus",str);*/
			
			
			if(obj.toString().equalsIgnoreCase("R") && (formObject.getNGValue("ICICILOMBARD_HT_CONS_PREV_WORKSTEP").equalsIgnoreCase("BSG_DataEntry_QC") || formObject.getNGValue("ICICILOMBARD_HT_CONS_PREV_WORKSTEP").equalsIgnoreCase("COPS_Team")))
			{
				////System.out.println("RM_Exception/RM_Endorsement Status: "+obj.toString());
				/*formObject.setNGEnable("FRAME_ROUTETO",true);
				formObject.setNGEnable("MHT_Route_To",true);
				formObject.setNGLocked("MHT_Route_To",true);
				formObject.setNGListIndex("MHT_Route_To",0);
				formObject.setNGLocked("MHT_Route_To",false);
				formObject.setNGEnable("MHT_Route_To",false);
				JOptionPane.showMessageDialog(null,"Please Clear Exception ");
				return 0;*/
				formObject.setNGEnable("FRAME_ROUTETO",true);
				formObject.setNGEnable("ICICILOMBARD_HT_Route_To",true);
				formObject.setNGLocked("ICICILOMBARD_HT_Route_To",true);
				if(validateType("ICICILOMBARD_HT_Route_To",formObject.getNGValue("ICICILOMBARD_HT_Route_To"),"emptyCheck","Route To")==0)
					return 0;
				/**************** Start CR-8093-79840 Additional Requirement HT *********************/
				System.out.println("Selected Exception Name is"+formObject.getNGValue("ICICILOMBARD_HT_EXCEPTION_NAME"));
				if(formObject.getNGValue("ICICILOMBARD_HT_CONS_PREV_WORKSTEP").equalsIgnoreCase("COPS_Team") && wsName.equalsIgnoreCase("Exception") && !(formObject.getNGValue("ICICILOMBARD_HT_EXCEPTION_NAME").equalsIgnoreCase("Deviation Approval Required") || formObject.getNGValue("ICICILOMBARD_HT_EXCEPTION_NAME").equalsIgnoreCase("Banking ID locked Limit exhausted") || formObject.getNGValue("ICICILOMBARD_HT_EXCEPTION_NAME").equalsIgnoreCase("I-Partner Inward Approval Required") || formObject.getNGValue("ICICILOMBARD_HT_EXCEPTION_NAME").equalsIgnoreCase("Subcover plans not mapped in Deal") || formObject.getNGValue("ICICILOMBARD_HT_EXCEPTION_NAME").equalsIgnoreCase("Short or Excess Premium") || formObject.getNGValue("ICICILOMBARD_HT_EXCEPTION_NAME").equalsIgnoreCase("Others")))
				{
					System.out.println("Exception Name:"+formObject.getNGValue("ICICILOMBARD_HT_EXCEPTION_NAME"));
					JOptionPane.showMessageDialog(null,"Please Clear Exception.......!"+formObject.getNGValue("ICICILOMBARD_HT_EXCEPTION_NAME"));
					return 0;
				}
				/**************** End CR-8093-79840 Additional Requirement HT *********************/
			}			
		}
		/******************************End PID-HT process changes ******************************/
		if(obj == null || !obj.toString().equalsIgnoreCase("R"))
		{
////////////////////Exception Raised by user
//FRAME_SELECT_SUB_VERTICAL

			if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_SELECT_SUB_VERTICAL")) && 
			(validateType("ICICILOMBARD_HT_SUB_VERTICAL",formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL"),"emptyCheck","Sub Vertical")==0)) 
			return 0;
			// end FRAME_SELECT_SUB_VERTICAL
			
			//introduced email
			/************************ Start CR-OMHT-1314-01 Wallet_Insurance************/
			/*else if(wsName.equals("BSG_DataEntry") && (validateType("ICICILOMBARD_HT_INTRODUCED_BY_EMAIL",formObject.getNGValue("ICICILOMBARD_HT_INTRODUCED_BY_EMAIL"),"emptyCheck","Email Id of the User introducing the WorkItem")==0)) 
			return 0;*/
			/************************ End CR-OMHT-1314-01 Wallet_Insurance************/

			//FRAME_SELECT_VERTICAL
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_SELECT_VERTICAL")) && 
			(validateType("ICICILOMBARD_HT_VERTICAL",formObject.getNGValue("ICICILOMBARD_HT_VERTICAL"),"emptyCheck","Vertical")==0)) 
			return 0;
			//end FRAME_SELECT_VERTICAL

			//FRAME_PRODUCT
			/*else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_PRODUCT")) && 
			(validateType("ICICILOMBARD_HT_PRODUCT",formObject.getNGValue("ICICILOMBARD_HT_PRODUCT"),"emptyCheck","Product")==0)) 
			return 0;*/
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("ICICILOMBARD_HT_PRODUCT")) && 
			(validateType("ICICILOMBARD_HT_PRODUCT",formObject.getNGValue("ICICILOMBARD_HT_PRODUCT"),"emptyCheck","Product")==0)) 
			return 0;
			/*else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_PRODUCT")) && 
			(validateType("ICICILOMBARD_HT_SUB_PRODUCT",formObject.getNGValue("ICICILOMBARD_HT_SUB_PRODUCT"),"emptyCheck","Sub Product")==0)) 
			return 0;*/
			// Yogendra Saraswat for Critical Care CR
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("ICICILOMBARD_HT_SUB_PRODUCT")) && 
			(validateType("ICICILOMBARD_HT_SUB_PRODUCT",formObject.getNGValue("ICICILOMBARD_HT_SUB_PRODUCT"),"emptyCheck","Sub Product")==0)) 
			return 0; 
			//end FRAME_PRODUCT

			//FRAME_PROD_ABBR_CODE
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_PROD_ABBR_CODE")) && 
			(validateType("ICICILOMBARD_HT_PRODUCT_ABBR_CODE",formObject.getNGValue("ICICILOMBARD_HT_PRODUCT_ABBR_CODE"),"emptyCheck","Product Abbreviation Code")==0)) 
			return 0; 
			//end FRAME_PROD_ABBR_CODE

			//FRAME_TXN_TYPE
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_TXN_TYPE")) && 
			(validateType("ICICILOMBARD_HT_TRANSACTION_TYPE",formObject.getNGValue("ICICILOMBARD_HT_TRANSACTION_TYPE"),"emptyCheck","Transaction Type")==0)) 
			return 0;
			//end FRAME_TXN_TYPE

			//FRAME_PREV_POLICY_NUMBER
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_PREV_POLICY_NUMBER")) && ((formObject.getNGValue("ICICILOMBARD_HT_TRANSACTION_TYPE")).equals("Renewal")) && (validateType("ICICILOMBARD_HT_PREVIOUS_POLICY_NO",formObject.getNGValue("ICICILOMBARD_HT_PREVIOUS_POLICY_NO"),"emptyCheck","Previous Policy No")==0)) 
			return 0;
			//end FRAME_PREV_POLICY_NUMBER

			//FRAME_IAGENT_PROPOSAL

			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_IAGENT_PROPOSAL")) && 
			(validateType("ICICILOMBARD_HT_IAGENT",formObject.getNGValue("ICICILOMBARD_HT_IAGENT"),"emptyCheck","I Agent")==0)) 
			return 0;


			/*****Start Change related to Application  Proposal no. field in Omni flow HT*****/
			/*commented for removed mandatory proposal no.			
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_IAGENT_PROPOSAL")) && 
			(validateType("ICICILOMBARD_HT_PROPOSAL_NO",formObject.getNGValue("ICICILOMBARD_HT_PROPOSAL_NO"),"emptyCheck","Proposal No.")==0)) 
			return 0;
			

			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_IAGENT_PROPOSAL")) && 
			(validateType("ICICILOMBARD_HT_CONFIRM_PROPOSAL_NO",formObject.getNGValue("ICICILOMBARD_HT_CONFIRM_PROPOSAL_NO"),"emptyCheck","Confirm Proposal No")==0)) 
			return 0;
			*/
			/*****End Change related to Application  Proposal no. field in Omni flow HT*****/
			
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") || wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_IAGENT_PROPOSAL")) && (Equality_Proposal_No()==0)) 
			return 0;

			/*****Start Change related to Application  Proposal no. field in Omni flow HT*****/
			/*
			else if((wsName.equals("BSG_DataEntry")) &&(formObject.isNGEnable("FRAME_IAGENT_PROPOSAL")) && (Exit_ProposalNo("ICICILOMBARD_HT_PROPOSAL_NO",formObject.getNGValue("ICICILOMBARD_HT_PROPOSAL_NO"))==0)) 
			return 0;*/
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_TXN_TYPE")) && 
			(validateType("ICICILOMBARD_HT_FUTURE_DATED",formObject.getNGValue("ICICILOMBARD_HT_FUTURE_DATED"),"emptyCheck","Future Dated")==0)) 
			return 0;
			
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") || wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_IAGENT_PROPOSAL")) && (Equality_PF_Proposal_No()==0)) 
			return 0;
		
			else if((wsName.equals("BSG_DataEntry") ||wsName.equals("BSG_DataEntry_QC")||wsName.equals("Exception")) &&(formObject.isNGEnable("FRAME_MISC")) && (validateType("ICICILOMBARD_HT_LOAN_SANCTIONED_AMT",formObject.getNGValue("ICICILOMBARD_HT_LOAN_SANCTIONED_AMT"),"OnlyNumeric","Loan Sanctioned Amount")==0)) 
			return 0;
			
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_IAGENT_PROPOSAL")) && (formObject.getNGValue("ICICILOMBARD_HT_IAGENT").equalsIgnoreCase("Yes")) && 
			(validateType("ICICILOMBARD_HT_PF_PROPOSAL_NO",formObject.getNGValue("ICICILOMBARD_HT_PF_PROPOSAL_NO"),"emptyCheck","PF Proposal No.")==0)) 
			return 0;
			

			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_IAGENT_PROPOSAL")) && (formObject.getNGValue("ICICILOMBARD_HT_IAGENT").equalsIgnoreCase("Yes")) && 
			(validateType("ICICILOMBARD_HT_CNFRM_PF_PROPOSAL_NO",formObject.getNGValue("ICICILOMBARD_HT_CNFRM_PF_PROPOSAL_NO"),"emptyCheck","Confirm PF Proposal No")==0)) 
			return 0;
			/*****End Change related to Application  Proposal no. field in Omni flow HT*****/

			//end FRAME_IAGENT_PROPOSAL

			//FRAME_SEARCH_BANK
			else if(wsName.equals("BSG_DataEntry") && formObject.isNGEnable("FRAME_SEARCH_BANK") && (validateType("ICICILOMBARD_HT_PID_PAYMENT_TYPE",formObject.getNGValue("ICICILOMBARD_HT_PID_PAYMENT_TYPE"),"emptyCheck","PID Tagging")==0)) 
			return 0;
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) && formObject.isNGEnable("FRAME_SEARCH_BANK") && !formObject.getNGValue("ICICILOMBARD_HT_PID_PAYMENT_TYPE").equalsIgnoreCase("Yes") && (validateType("ICICILOMBARD_HT_BANK_NAME",formObject.getNGValue("ICICILOMBARD_HT_BANK_NAME"),"emptyCheck","Bank Name")==0)) 
			return 0;
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) && formObject.isNGEnable("FRAME_SEARCH_BANK") && !formObject.getNGValue("ICICILOMBARD_HT_PID_PAYMENT_TYPE").equalsIgnoreCase("Yes") && (validateType("ICICILOMBARD_HT_PAYMENT_MODE",formObject.getNGValue("ICICILOMBARD_HT_PAYMENT_MODE"),"emptyCheck","Payment Mode")==0)) 
			return 0;

			//end FRAME_SEARCH_BANK

			//FRAME_CHEQUE_DD

			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) && formObject.isNGEnable("FRAME_CHEQUE_DD") && !formObject.getNGValue("ICICILOMBARD_HT_PID_PAYMENT_TYPE").equalsIgnoreCase("Yes") && (validateType("ICICILOMBARD_HT_INSTRUMENT_NO",formObject.getNGValue("ICICILOMBARD_HT_INSTRUMENT_NO"),"emptyCheck","Instrument No")==0)) 
			return 0;
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) && formObject.isNGEnable("FRAME_CHEQUE_DD") && !formObject.getNGValue("ICICILOMBARD_HT_PID_PAYMENT_TYPE").equalsIgnoreCase("Yes") && (validateType("ICICILOMBARD_HT_INSTRUMENT_DATE",formObject.getNGValue("ICICILOMBARD_HT_INSTRUMENT_DATE"),"emptyCheck","Instrument Date")==0)) 
			return 0;
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) && formObject.isNGEnable("FRAME_CHEQUE_DD") && !formObject.getNGValue("ICICILOMBARD_HT_PID_PAYMENT_TYPE").equalsIgnoreCase("Yes") && (validateType("ICICILOMBARD_HT_INSTRUMENT_AMOUNT",formObject.getNGValue("ICICILOMBARD_HT_INSTRUMENT_AMOUNT"),"emptyCheck","Instrument Amount")==0)) 
			return 0;
			//end FRAME_CHEQUE_DD
			//FRAME_SAVINGS_ACCOUNT
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_SAVINGS_ACCOUNT")) && (validateType("ICICILOMBARD_HT_SAVINGS_ACCOUNT_NO",formObject.getNGValue("ICICILOMBARD_HT_SAVINGS_ACCOUNT_NO"),"emptyCheck","Saving Account No")==0)) 
			return 0;
			// end FRAME_SAVINGS_ACCOUNT
			//FRAME_CREDIT_CARD
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_CREDIT_CARD")) && (validateType("ICICILOMBARD_HT_CREDIT_CARD_NO",formObject.getNGValue("ICICILOMBARD_HT_CREDIT_CARD_NO"),"emptyCheck","Credit Card No")==0)) 
			return 0;
			
			/****************************** CR 24 HSP product by satish ******************************/
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_CREDIT_CARD")) && (validateType("ICICILOMBARD_HT_CREDIT_CARD_NO",formObject.getNGValue("ICICILOMBARD_HT_CREDIT_CARD_NO"),"lengthCheck","Credit Card No")==0)) 
			return 0;
			/**************************** end CR 24 HSP product by satish ****************************/
			
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_CREDIT_CARD")) && (validateType("ICICILOMBARD_HT_CREDITCARD_EXP_DATE",formObject.getNGValue("ICICILOMBARD_HT_CREDITCARD_EXP_DATE"),"emptyCheck","Credit Card Expiry Date")==0)) 
			return 0;
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_CREDIT_CARD")) && (validateType("ICICILOMBARD_HT_AUTHORIZATION_CODE",formObject.getNGValue("ICICILOMBARD_HT_AUTHORIZATION_CODE"),"emptyCheck","Authorization Code")==0)) 
			return 0;
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_CREDIT_CARD")) && (validateType("ICICILOMBARD_HT_AUTHORIZATION_DATE",formObject.getNGValue("ICICILOMBARD_HT_AUTHORIZATION_DATE"),"emptyCheck","Authorization Date")==0)) 
			return 0;
			// end FRAME_CREDIT_CARD
			//FRAME_PID_NO
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_PID_NO")) && (validateType("ICICILOMBARD_HT_PID_NO",formObject.getNGValue("ICICILOMBARD_HT_PID_NO"),"emptyCheck","PID No")==0)) 
			return 0;
			//end FRAME_PID_NO
			//FRAME_SALESMGR_EMPID
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_SALESMGR_EMPID")) && (validateType("ICICILOMBARD_HT_SM_ID",formObject.getNGValue("ICICILOMBARD_HT_SM_ID"),"emptyCheck","Sales Manager/Emp ID")==0)) 
			return 0;
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_SALESMGR_EMPID")) && (validateType("ICICILOMBARD_HT_SM_NAME",formObject.getNGValue("ICICILOMBARD_HT_SM_NAME"),"emptyCheck","Sales Manager Name")==0)) 
			return 0;
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_SALESMGR_EMPID")) && (validateType("ICICILOMBARD_HT_CUSTOMER_NAME",formObject.getNGValue("ICICILOMBARD_HT_CUSTOMER_NAME"),"emptyCheck","Customer Name")==0)) 
			return 0;
			// end FRAME_SALESMGR_EMPID
			//FRAME_MISC
			
			/*Yogendra Saraswat
			else if((wsName.equals("BSG_DataEntry") ||wsName.equals("BSG_DataEntry_QC")||wsName.equals("Exception")) &&(formObject.isNGEnable("FRAME_MISC")) && (validateType("ICICILOMBARD_HT_REFERENCE1",formObject.getNGValue("ICICILOMBARD_HT_REFERENCE1"),"emptyCheck","Reference1")==0)) 
			return 0;
			*/
			else if((wsName.equals("BSG_DataEntry") ||wsName.equals("BSG_DataEntry_QC")||wsName.equals("Exception")) &&(formObject.isNGEnable("FRAME_MISC")) && (validateType("ICICILOMBARD_HT_IL_LOCATION",formObject.getNGValue("ICICILOMBARD_HT_IL_LOCATION"),"emptyCheck","IL Location")==0)) 
			return 0;
			else if((wsName.equals("BSG_DataEntry") ||wsName.equals("BSG_DataEntry_QC")||wsName.equals("Exception")) &&(formObject.isNGEnable("FRAME_MISC")) && (validateType("ICICILOMBARD_HT_IL_LOCATION_CODE",formObject.getNGValue("ICICILOMBARD_HT_IL_LOCATION_CODE"),"emptyCheck","IL Location Code")==0)) 
			return 0;
			else if((wsName.equals("BSG_DataEntry") ||wsName.equals("BSG_DataEntry_QC")||wsName.equals("Exception")) &&(formObject.isNGEnable("FRAME_MISC")) && (validateType("ICICILOMBARD_HT_PREMIUM_AMOUNT",formObject.getNGValue("ICICILOMBARD_HT_PREMIUM_AMOUNT"),"emptyCheck","Premium Amount")==0)) 
			return 0;
			///ICICILOMBARD_HT_PAN
			else if((wsName.equals("BSG_DataEntry") ||wsName.equals("BSG_DataEntry_QC")||wsName.equals("Exception")) &&(formObject.isNGEnable("ICICILOMBARD_HT_PAN")) && (validateType("ICICILOMBARD_HT_PAN",formObject.getNGValue("ICICILOMBARD_HT_PAN"),"emptyCheck","Pan No.")==0)) 
			return 0;

			//ICICILOMBARD_HT_PAN

			/****************************** CR 24 HSP product by satish ******************************/
			else if((wsName.equals("BSG_DataEntry") ||wsName.equals("BSG_DataEntry_QC")||wsName.equals("Exception")) && (formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("HOMEINSURANCEPRODUCT") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("SECURE MIND")|| formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("Secure Mind A") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("GROUP SECURE MIND") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("GROUP PERSONAL ACCIDENT") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("CCPI") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("Asset Protect") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("Merchant Cover") || formObject.getNGValue("ICICILOMBARD_HT_PRODUCT").equalsIgnoreCase("Home")) && formObject.isNGEnable("FRAME_MISC") && (validateType("ICICILOMBARD_HT_LAN",formObject.getNGValue("ICICILOMBARD_HT_LAN"),"emptyCheck","LAN")==0))
			return 0;
			/**************************** end CR 24 HSP product by satish ****************************/
			
			else if((wsName.equals("BSG_DataEntry") ||wsName.equals("BSG_DataEntry_QC")||wsName.equals("Exception")) &&(formObject.isNGEnable("ICICILOMBARD_HT_PRIVILEGE_BANKER_CODE")) && (validateType("ICICILOMBARD_HT_PRIVILEGE_BANKER_CODE",formObject.getNGValue("ICICILOMBARD_HT_PRIVILEGE_BANKER_CODE"),"emptyCheck","Privilege Banker Code")==0)) 
			return 0;
			
			/* commented by Yogendra Saraswat
			else if((wsName.equals("BSG_DataEntry") ||wsName.equals("BSG_DataEntry_QC")||wsName.equals("Exception")) &&(formObject.isNGEnable("FRAME_MISC")) && (validateType("ICICILOMBARD_HT_TRAINEE_ID",formObject.getNGValue("ICICILOMBARD_HT_TRAINEE_ID"),"emptyCheck","Trainee ID")==0)) 
			return 0;
			else if((wsName.equals("BSG_DataEntry") ||wsName.equals("BSG_DataEntry_QC")||wsName.equals("Exception")) &&(formObject.isNGEnable("FRAME_MISC")) && (validateType("ICICILOMBARD_HT_TA_CODE",formObject.getNGValue("ICICILOMBARD_HT_TA_CODE"),"emptyCheck","TA Code")==0)) 
			return 0;
			*/
			else if((wsName.equals("BSG_DataEntry") ||wsName.equals("BSG_DataEntry_QC")||wsName.equals("Exception")) &&(formObject.isNGEnable("FRAME_MISC")) && (validateType("ICICILOMBARD_HT_ECS_MANDATE",formObject.getNGValue("ICICILOMBARD_HT_ECS_MANDATE"),"emptyCheck","ECS Mandate")==0)) 
			return 0;
			else if((wsName.equals("BSG_DataEntry") ||wsName.equals("BSG_DataEntry_QC")||wsName.equals("Exception")) && (formObject.isNGEnable("FRAME_MISC")) && (!formObject.getNGValue("ICICILOMBARD_HT_PID_PAYMENT_TYPE").equalsIgnoreCase("Yes")) && (validateType("ICICILOMBARD_HT_PAYMENT_ID",formObject.getNGValue("ICICILOMBARD_HT_PAYMENT_ID"),"emptyCheck","Payment ID")==0)) //PID-HT process changes
			return 0;
			/******************************* PID-HT process changes ********************************/
			/*else if((wsName.equals("BSG_DataEntry") ||wsName.equals("BSG_DataEntry_QC")||wsName.equals("Exception")) &&(formObject.isNGEnable("FRAME_MISC")) && (validateType("ICICILOMBARD_HT_CUSTOMER_ID",formObject.getNGValue("ICICILOMBARD_HT_CUSTOMER_ID"),"emptyCheck","Customer ID")==0)) 
			return 0;*/
			//Customer_id numeric field validation
			else if((wsName.equals("BSG_DataEntry") ||wsName.equals("BSG_DataEntry_QC")||wsName.equals("Exception")) &&(formObject.isNGEnable("FRAME_MISC")) && (!formObject.getNGValue("ICICILOMBARD_HT_CUSTOMER_ID").equalsIgnoreCase("")) && (validateType("ICICILOMBARD_HT_CUSTOMER_ID",formObject.getNGValue("ICICILOMBARD_HT_CUSTOMER_ID"),"OnlyNumeric","Customer ID")==0)) 
			return 0;
			//Payment_id numeric field validation
			else if((wsName.equals("BSG_DataEntry") ||wsName.equals("BSG_DataEntry_QC")||wsName.equals("Exception")) && (formObject.getNGValue("ICICILOMBARD_HT_PID_PAYMENT_TYPE").equalsIgnoreCase("No")) && (!formObject.getNGValue("ICICILOMBARD_HT_PAYMENT_ID").equalsIgnoreCase("")) && (validateType("ICICILOMBARD_HT_PAYMENT_ID",formObject.getNGValue("ICICILOMBARD_HT_PAYMENT_ID"),"OnlyNumeric","Payment ID")==0)) 
			return 0;
			/******************************End PID-HT process changes ******************************/
			/*else if((wsName.equals("BSG_DataEntry") ||wsName.equals("BSG_DataEntry_QC")||wsName.equals("Exception")) &&(formObject.isNGEnable("FRAME_MISC")) && (validateType("ICICILOMBARD_HT_REMARKS",formObject.getNGValue("ICICILOMBARD_HT_REMARKS"),"emptyCheck","REMARKS")==0)) 
			return 0;*/

			//end FRAME_MISC

			//FRAME_SOURCE_BUSINESS
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("ICICILOMBARD_HT_SOURCE_BUSINESS")) && (validateType("ICICILOMBARD_HT_SOURCE_BUSINESS",formObject.getNGValue("ICICILOMBARD_HT_SOURCE_BUSINESS"),"emptyCheck","Source Business")==0)) 
			return 0;
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("ICICILOMBARD_HT_CHANNEL_SOURCE")) && (validateType("ICICILOMBARD_HT_CHANNEL_SOURCE",formObject.getNGValue("ICICILOMBARD_HT_CHANNEL_SOURCE"),"emptyCheck","Channel Source")==0)) 
			return 0;
			//FRAME_CHANNEL_EMP_INFO
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_CHANNEL_EMP_INFO")) && (validateType("ICICILOMBARD_HT_CHANNEL_EMP_INFO",formObject.getNGValue("ICICILOMBARD_HT_CHANNEL_EMP_INFO"),"emptyCheck","CHANNEL EMP INFO")==0)) 
			return 0;
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_CHANNEL_EMP_INFO")) && (validateType("ICICILOMBARD_HT_BRANCH_ID_UBO_NAME",formObject.getNGValue("ICICILOMBARD_HT_BRANCH_ID_UBO_NAME"),"emptyCheck","BRANCH ID UBO NAME")==0)) 
			return 0;
			//end  FRAME_CHANNEL_EMP_INFO

			//FRAME_BSM_BCM
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_BSM_BCM")) && (validateType("ICICILOMBARD_HT_BSM_ID",formObject.getNGValue("ICICILOMBARD_HT_BSM_ID"),"emptyCheck","BSM ID")==0)) 
			return 0;
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_BSM_BCM")) && (validateType("ICICILOMBARD_HT_BCM_ID",formObject.getNGValue("ICICILOMBARD_HT_BCM_ID"),"emptyCheck","BCM ID")==0)) 
			return 0;
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_BSM_BCM")) && (validateType("ICICILOMBARD_HT_RO_DSA_COUNSELOR_ID",formObject.getNGValue("ICICILOMBARD_HT_RO_DSA_COUNSELOR_ID"),"emptyCheck","RO DSA COUNSELOR ID")==0)) 
			return 0;
			//end  FRAME_BSM_BCM

			//FRAME_BANK_BRANCH_NAME
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_BANK_BRANCH_NAME")) && (validateType("ICICILOMBARD_HT_BANK_BRANCH_NAME",formObject.getNGValue("ICICILOMBARD_HT_BANK_BRANCH_NAME"),"emptyCheck","Branch Name")==0)) 
			return 0;
			//end  FRAME_BANK_BRANCH_NAME
			
			//FRAME_IS_EMP_CODE
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_IS_EMP_CODE")) && (validateType("ICICILOMBARD_HT_EMPCODE_CSO",formObject.getNGValue("ICICILOMBARD_HT_EMPCODE_CSO"),"emptyCheck","Is Code Emp ")==0)) 
			return 0;

			//end  FRAME_IS_EMP_CODE

			//FRAME_WRE_WRM
			/***** CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket*******/
			/*else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_WRE_WRM")) && (validateType("ICICILOMBARD_HT_WRM",formObject.getNGValue("ICICILOMBARD_HT_WRM"),"emptyCheck","WRM")==0)) 
			return 0;
			/***** End CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket***/
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_WRE_WRM")) && (validateType("ICICILOMBARD_HT_WRE",formObject.getNGValue("ICICILOMBARD_HT_WRE"),"emptyCheck","SP Code")==0)) 
			return 0;
			//FRAME_CENTER_CODE_RM
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_CENTER_CODE_RM")) && (validateType("ICICILOMBARD_HT_CENTER_CODE_NAME",formObject.getNGValue("ICICILOMBARD_HT_CENTER_CODE_NAME"),"emptyCheck","Center Code Name")==0)) 
			return 0;
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_CENTER_CODE_RM")) && (validateType("ICICILOMBARD_HT_RM_EMPLOYEE",formObject.getNGValue("ICICILOMBARD_HT_RM_EMPLOYEE"),"emptyCheck","RM Employee")==0)) 
			return 0;
			//end FRAME_CENTER_CODE_RM

			//FRAME_IF_PRODUCT_HSP
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_IF_PRODUCT_HSP")) && (validateType("ICICILOMBARD_HT_APPLICANT_NO",formObject.getNGValue("ICICILOMBARD_HT_APPLICANT_NO"),"emptyCheck","Applicant No")==0)) 
			return 0;
			/***** CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket*******/
			/*else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_IF_PRODUCT_HSP")) && (validateType("ICICILOMBARD_HT_HSP_VERTICAL",formObject.getNGValue("ICICILOMBARD_HT_HSP_VERTICAL"),"emptyCheck","Verticals")==0)) 
			return 0;
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_IF_PRODUCT_HSP")) && (validateType("ICICILOMBARD_HT_PRIMARY_VERTICAL",formObject.getNGValue("ICICILOMBARD_HT_PRIMARY_VERTICAL"),"emptyCheck","Primary Vertical")==0)) 
			return 0;
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_IF_PRODUCT_HSP")) && (validateType("ICICILOMBARD_HT_SECONDARY_VERTICAL",formObject.getNGValue("ICICILOMBARD_HT_SECONDARY_VERTICAL"),"emptyCheck","Secondary Vertical")==0)) 
			return 0; */
			/***** End CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket***/
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_IF_PRODUCT_HSP")) && (validateType("ICICILOMBARD_HT_DOB",formObject.getNGValue("ICICILOMBARD_HT_DOB"),"emptyCheck","DOB")==0)) 
			return 0;
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_IF_PRODUCT_HSP")) && (validateType("ICICILOMBARD_HT_DISBURSAL_DATE",formObject.getNGValue("ICICILOMBARD_HT_DISBURSAL_DATE"),"emptyCheck","DISBURSAL DATE")==0)) 
			return 0;
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_IF_PRODUCT_HSP")) && (validateType("ICICILOMBARD_HT_SANCTION_DATE",formObject.getNGValue("ICICILOMBARD_HT_SANCTION_DATE"),"emptyCheck","SANCTION DATE")==0)) 
			return 0;
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_IF_PRODUCT_HSP")) && (validateType("ICICILOMBARD_HT_OCCUPATION",formObject.getNGValue("ICICILOMBARD_HT_OCCUPATION"),"emptyCheck","OCCUPATION")==0)) 
			return 0;
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_IF_PRODUCT_HSP")) && (validateType("ICICILOMBARD_HT_SUM_INSURED",formObject.getNGValue("ICICILOMBARD_HT_SUM_INSURED"),"emptyCheck","SUM INSURED")==0)) 
			return 0;
			/***************** cr 24 - commented by satish *******************/
			/*else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_IF_PRODUCT_HSP")) && (validateType("ICICILOMBARD_HT_DEAL1",formObject.getNGValue("ICICILOMBARD_HT_DEAL1"),"emptyCheck","DEAL 1")==0)) 
			return 0;
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_IF_PRODUCT_HSP")) && (validateType("ICICILOMBARD_HT_DEAL2",formObject.getNGValue("ICICILOMBARD_HT_DEAL2"),"emptyCheck","DEAL 2")==0)) 
			return 0;*/
			/***************** cr 24 - commented by satish *******************/

			/***** CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket*******/
			/*//FRAME_SUB_BROKER
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_SUB_BROKER")) && (validateType("ICICILOMBARD_HT_SUB_BROKER_CODE",formObject.getNGValue("ICICILOMBARD_HT_SUB_BROKER_CODE"),"emptyCheck","Sub Broker Code")==0)) 
			return 0;
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_SUB_BROKER")) && (validateType("ICICILOMBARD_HT_TERRITORY_MANAGER",formObject.getNGValue("ICICILOMBARD_HT_TERRITORY_MANAGER"),"emptyCheck","Territory Manager")==0)) 
			return 0;
			else if((wsName.equals("BSG_DataEntry")||wsName.equals("Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_SUB_BROKER")) && (validateType("ICICILOMBARD_HT_SUB_BROKER",formObject.getNGValue("ICICILOMBARD_HT_SUB_BROKER"),"emptyCheck","Sub Broker")==0)) 
			return 0;
			//end FRAME_SUB_BROKER */
			/***** End CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket***/
			// BSG_DataEntry_QC
			else if((wsName.equals("BSG_DataEntry_QC")||wsName.equals("Exception")||wsName.equals("COPS_Team")||wsName.equals("COPS_QC")
			/****************************** CR 24 HSP product by satish ******************************/
			||wsName.equals("COPS_QC1")
			/**************************** end CR 24 HSP product by satish ****************************/
			) &&(validateType("ICICILOMBARD_HT_DATAENTRY_QC_DECISION",formObject.getNGValue("ICICILOMBARD_HT_DATAENTRY_QC_DECISION"),"emptyCheck","Decision")==0)) 
			return 0;
			/***** CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket*******/
			//FRAME_UNIQUE_ID
			//else if((wsName.equals("RMT")) &&(validateType("ICICILOMBARD_HT_UNIQUE_ID",formObject.getNGValue("ICICILOMBARD_HT_UNIQUE_ID"),"emptyCheck","Unique ID")==0)) 
			//return 0;
			//end FRAME_UNIQUE_ID
			/***** End CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket***/
			//FRAME_CUSTOMER MOBILE
			else if((wsName.equals("BSG_DataEntry_QC"))&&((formObject.getNGValue("ICICILOMBARD_HT_TRANSACTION_TYPE")).equals("Rollover"))&&(validateType("ICICILOMBARD_HT_CUSTOMERNUMBER",formObject.getNGValue("ICICILOMBARD_HT_CUSTOMERNUMBER"),"emptyCheck","Customer Mobile Number")==0)) 
			return 0;		
			/****************************** PID-HT process changes ******************************/
			else if(wsName.equals("BSG_DataEntry_QC") && formObject.getNGValue("ICICILOMBARD_HT_PID_PAYMENT_TYPE").equals("Yes") && !formObject.getNGValue("ICICILOMBARD_HT_DATAENTRY_QC_DECISION").equals("Reject") && (validateType("ICICILOMBARD_HT_TAG_STATUS",formObject.getNGValue("ICICILOMBARD_HT_TAG_STATUS"),"customValCheck","Case is not tagged, you can not submit the case")==0)) //bug fixing
			return 0;
			/******************************End PID-HT process changes ******************************/
			/******************************SP Code CR-8093-69682********************************/
			else if((formObject.getNGValue("ICICILOMBARD_HT_DEAL_STATUS").equalsIgnoreCase("Yes"))&&(formObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE"))&&(formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING"))&&(wsName.equals("BSG_DataEntry")) &&(formObject.isNGEnable("FRAME_SALESMGR_EMPID")) && (validateType("ICICILOMBARD_HT_DEAL_IL_LOCATION",formObject.getNGValue("ICICILOMBARD_HT_DEAL_IL_LOCATION"),"emptyCheck","DEAL IL LOCATION")==0)) 
			return 0;
			else if((formObject.getNGValue("ICICILOMBARD_HT_DEAL_STATUS").equalsIgnoreCase("Yes"))&&(formObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE"))&&(formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING"))&&(wsName.equals("BSG_DataEntry")) &&(formObject.isNGEnable("FRAME_BANK_BRANCH_NAME")) && (validateType("ICICILOMBARD_HT_SOL_ID",formObject.getNGValue("ICICILOMBARD_HT_SOL_ID"),"emptyCheck","SOL Id")==0)) 
			return 0;
			
			/******************************SP Code CR-8093-69682***********************************/
			
			/******Start HT CR-8127-95325-GST-Omniflow development******/
			else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC") || wsName.equals("Exception") || wsName.equals("RM_Exception")) && (formObject.getNGValue("ICICILOMBARD_HT_IAGENT").equalsIgnoreCase("No")) &&(validateType("ICICILOMBARD_HT_GST_REGISTERED",formObject.getNGValue("ICICILOMBARD_HT_GST_REGISTERED"),"emptyCheck","GST Registered")==0))
			return 0;			
			else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC") || wsName.equals("Exception") || wsName.equals("RM_Exception")) && (formObject.getNGValue("ICICILOMBARD_HT_IAGENT").equalsIgnoreCase("No")) && (formObject.getNGValue("ICICILOMBARD_HT_GST_REGISTERED").equalsIgnoreCase("Yes")))
			{				
				if(validateType("TXTGST_NUMBER",formObject.getNGValue("TXTGST_NUMBER"),"onlyAlphaNumeric","GST Number")==0)
				return 0;								
			}			
			/******End HT CR-8127-95325-GST-Omniflow development******/
			//sm mail id and superior mail id

			//FRAME_CUSTOMER MOBILE
			/* else
			{
				if(wsName.equalsIgnoreCase("BSG_DataEntry"))
				{
					getSMMailId();
				}
				return 1;
			}	 */	
			else
			{
				int i=0;
				if(wsName.equalsIgnoreCase("BSG_DataEntry_QC"))
				{
				//	////System.out.println("SMSTriggerBSGQC   initiated");
					SMSTriggerBSGQC sMSTriggerBSGQC= new SMSTriggerBSGQC(formObject);
					i =	sMSTriggerBSGQC.smsTrigger();
				//	////System.out.println("SMSTriggerBSGQC   end");
				//	////System.out.println("Returned value from procedure in validation"+i);
				}
				else
				{
					i=1;
				}
				
				if(wsName.equalsIgnoreCase("BSG_DataEntry"))
				{
					getSMMailId();
				}
			
			return i;
			} 
		}
		return 1;
		
	}
else
	return 1;

}
//////////////
public int Equality_Proposal_No()
{
//////System.out.println("Equality_Proposal_No");

String PROPOSAL_NO=formObject.getNGValue("ICICILOMBARD_HT_PROPOSAL_NO");
String PREFIX_PROPOSAL_NO=formObject.getNGValue("ICICILOMBARD_HT_PREFIX_PROPOSAL_NO");

String Proposal_no1=PREFIX_PROPOSAL_NO+""+PROPOSAL_NO;
//////System.out.println("Proposal_no1"+Proposal_no1);
String CONFIRM_PROPOSAL_NO=formObject.getNGValue("ICICILOMBARD_HT_CONFIRM_PROPOSAL_NO");
String PREFIX_CONFIRM_PROPOSAL_NO=formObject.getNGValue("ICICILOMBARD_HT_PREFIX_CONFIRM_PROPOSAL_NO");
String Proposal_no2=PREFIX_CONFIRM_PROPOSAL_NO+""+CONFIRM_PROPOSAL_NO;
//////System.out.println("Proposal_no2"+Proposal_no2);
if(!Proposal_no1.equals(Proposal_no2))
{
	JOptionPane.showMessageDialog(null,"The values of the Proposal No. does not match  !!");
	return 0;
}
else
{
	formObject.setNGValue("ICICILOMBARD_HT_FINAL_PROPOSAL_NO",Proposal_no1);
	formObject.setNGValue("ICICILOMBARD_HT_FINAL_CONFIRM_PROPOSAL_NO",Proposal_no2);
	return 1;
}

}
/*****Start Change related to Application  Proposal no. field in Omni flow HT*****/
public int Equality_PF_Proposal_No()
{
	String PF_PROPOSAL_NO=formObject.getNGValue("ICICILOMBARD_HT_PF_PROPOSAL_NO");
	String CONFIRM_PF_PROPOSAL_NO=formObject.getNGValue("ICICILOMBARD_HT_CNFRM_PF_PROPOSAL_NO");	
	
	if(!PF_PROPOSAL_NO.equals(CONFIRM_PF_PROPOSAL_NO))
	{
		JOptionPane.showMessageDialog(null,"The values of the PF Proposal No. does not match  !!");
		return 0;
	}
	else
	{
		return 1;
	}
}
/*****End Change related to Application  Proposal no. field in Omni flow HT*****/
//////////////
public int Exit_ProposalNo(String FiledName, String ProposalNo)
{
	WFXmlList RecordList= null;
	WFXmlResponse xmlResponse=null;
	
	/***********************Start CR Motor,Home & Travel processing*************/
	String query="";
	if(formObject.getNGValue("ICICILOMBARD_HT_PREFIX_PROPOSAL_NO").equals("") || formObject.getNGValue("ICICILOMBARD_HT_PREFIX_PROPOSAL_NO") == null)
	{
		query="select count(CURRENTWORKSTEP) Count_DealNO from NG_ICICI_HT_EXT  where PROPOSAL_NO='"+ProposalNo+"'  and  CURRENTWORKSTEP not in ('Work Exit','Discard_Exit')";
	}
	else
	{
		query="select count(CURRENTWORKSTEP) Count_DealNO from NG_ICICI_HT_EXT  where PROPOSAL_NO='"+ProposalNo+"' and PREFIX_PROPOSAL_NO='"+formObject.getNGValue("ICICILOMBARD_HT_PREFIX_PROPOSAL_NO") +"' and  CURRENTWORKSTEP not in ('Work Exit','Discard_Exit')";
	}
	/***********************End CR Motor,Home & Travel processing***************/
				NGEjbCalls	ngEjbCalls= new NGEjbCalls(formObject);
					// get input xml for the query
					String inputXml=ngEjbCalls.callSelectInputXML(query,"1");
					////System.out.println("input xml:-------->"+inputXml);
					// get output xml for the query
					String outputXml=ngEjbCalls.executeXmlGeneric(inputXml);
					////System.out.println("outputXml---->"+outputXml+"outputXml");
					if(outputXml.equals("") || Integer.parseInt(outputXml.substring(outputXml.indexOf("<MainCode>")+10 , outputXml.indexOf("</MainCode>")))!=0)
					{
						//////System.out.println("Error in Output xml ");

						JOptionPane.showMessageDialog(null,"No results found !!");
					}
					String sResult="";
					xmlResponse = new WFXmlResponse(outputXml);
					for (RecordList = xmlResponse.createList("Results", "Result"); RecordList.hasMoreElements(); RecordList.skip())
						{
							sResult = RecordList.getVal("Result");
							//get the actual result
							sResult=sResult.substring(1);
						}
					if(Integer.parseInt(sResult)>0)
					{
						JOptionPane.showMessageDialog(null,"A WorkItem has been already associated with this Proposal No. Please enter different Proposal no");
						formObject.setNGValue("ICICILOMBARD_HT_PROPOSAL_NO","");
						formObject.setNGValue("ICICILOMBARD_HT_CONFIRM_PROPOSAL_NO","");
						return 0;
					}	
					
		return 1;			

}
	
public void ValidateTextControl(String fieldName,String value)
{
/*
	if(fieldName.equals("SEARCH_STRING") && (value.length()>1))
	{
	int x=validateType(fieldName,value,"emptyCheck","Search Keyword"); 
	}
*/	
//////System.out.println("fieldName--"+fieldName);
//////System.out.println("value--"+value);
		String wsName=formObject.getWFActivityName();
		if((wsName.equals("BSG_DataEntry")||wsName.equals("BSG_DataEntry_QC")||wsName.equals("Exception")) && (value.length()>1) && (formObject.isNGEnable(fieldName)))
		{
			if(fieldName.equals("SEARCH_STRING") )
			{
			// int xx=validateType(fieldName,value,"IsAlphaNumeric_SpecialChar","Search Keyword"); 
			int xx=validateType(fieldName,value,"IsAlphaNumeric","Search Keyword"); 
			}
			/* if(fieldName.equals("ICICILOMBARD_HT_PREVIOUS_POLICY_NO")  )
			{
			int xx=validateType(fieldName,value,"IsAlphaNumeric","Previous Policy No"); 
			} */
			if(fieldName.equals("ICICILOMBARD_HT_INSTRUMENT_NO")  )
			{
			int xx=validateType(fieldName,value,"OnlyNumeric","Instrument No"); 
			}
			
			if(fieldName.equals("ICICILOMBARD_HT_INSTRUMENT_AMOUNT")  )
			{
			int xx=validateType(fieldName,value,"OnlyNumeric","Instrument Amount"); 
			}
			
			if(fieldName.equals("ICICILOMBARD_HT_SAVINGS_ACCOUNT_NO")  )
			{
			int xx=validateType(fieldName,value,"OnlyNumeric","Savings Account No"); 
			}
			
			if(fieldName.equals("ICICILOMBARD_HT_CREDIT_CARD_NO")  )
			{
			int xx=validateType(fieldName,value,"OnlyNumeric","Credit Card No"); 
			}
		
			if(fieldName.equals("ICICILOMBARD_HT_CREDITCARD_EXP_DATE")  )
			{
			int xx=validateType(fieldName,value,"dateFormat(dd/mm/yyyy)","Credit Card Date"); 
			}


			if(fieldName.equals("ICICILOMBARD_HT_AUTHORIZATION_CODE")  )
			{
			int xx=validateType(fieldName,value,"OnlyNumeric","Authorization Code"); 
			}
			if(fieldName.equals("ICICILOMBARD_HT_AUTHORIZATION_DATE")  )
			{
			int xx=validateType(fieldName,value,"dateFormat(dd/mm/yyyy)","Authorization Date"); 
			}

			if(fieldName.equals("ICICILOMBARD_HT_PID_NO")  )
			{
			int xx=validateType(fieldName,value,"IsAlphaNumeric","PID No"); 
			}
			if(fieldName.equals("ICICILOMBARD_HT_SM_ID")  )
			{
			int xx=validateType(fieldName,value,"IsAlphaNumeric","SM ID"); 
			}
			if(fieldName.equals("ICICILOMBARD_HT_SUM_INSURED")  )
			{
			int xx=validateType(fieldName,value,"OnlyNumeric","Sum Insured"); 
			}

			if(fieldName.equals("ICICILOMBARD_HT_CONTACT1")  )
			{
			int xx=validateType(fieldName,value,"OnlyNumeric","Contact1"); 
			}	
			if(fieldName.equals("ICICILOMBARD_HT_CONTACT2")  )
			{
			int xx=validateType(fieldName,value,"OnlyNumeric","Contact2"); 
			}				
			/////  Customer Mobile no
			if(fieldName.equals("ICICILOMBARD_HT_CUSTOMERNUMBER")  )
			{
			int xx=validateType(fieldName,value,"OnlyNumeric","Customer Mobile No."); 
			}	
			//
			if(fieldName.equals("ICICILOMBARD_HT_PREMIUM_AMOUNT")  )
			{
			int xx=validateType(fieldName,value,"OnlyNumeric","Premium Amount"); 
			if(xx==1)
			{
			int premium_amt=Integer.parseInt(value);
			if(premium_amt>=100000)
			{
			//JOptionPane.showMessageDialog(null,"PAN No is enabled !!");
			formObject.setNGEnable("ICICILOMBARD_HT_PAN",true);
			}
			else
			{
			formObject.setNGValue("ICICILOMBARD_HT_PAN","");
			formObject.setNGEnable("ICICILOMBARD_HT_PAN",false);
			
			}
			}
			}
		
			if(fieldName.equals("ICICILOMBARD_HT_LAN"))
			{
			int xx=validateType(fieldName,value,"IsAlphaNumeric_SpecialChar","LAN No"); 
			}
			/***** CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket*******/
			/*if(fieldName.equals("ICICILOMBARD_HT_TRAINEE_ID")  )
			{
			int xx=validateType(fieldName,value,"OnlyNumeric","Trainee ID"); 
			} */
			/***** End CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket***/

			/* if(fieldName.equals("ICICILOMBARD_HT_SOURCE_BUSINESS") )
			{
				
					// added by manoj jain
					////System.out.println("beorfer saveeeeeeee----");
					value=value.trim();
					formObject.setNGEnable("FRAME_SUB_BROKER",true);
					formObject.setNGEnable("FRAME_CENTER_CODE_RM",true);
					formObject.setNGValue("ICICILOMBARD_HT_SUB_BROKER_CODE","");
					formObject.setNGValue("ICICILOMBARD_HT_TERRITORY_MANAGER","");
					formObject.setNGValue("ICICILOMBARD_HT_SUB_BROKER","");
					formObject.setNGValue("ICICILOMBARD_HT_CENTER_CODE_NAME","");
					formObject.setNGValue("ICICILOMBARD_HT_RM_EMPLOYEE","");
					formObject.setNGEnable("FRAME_CENTER_CODE_RM",false);
					formObject.setNGEnable("FRAME_SUB_BROKER",false);
					//String str11="Centre Sales";
					//////System.out.println("fieldName--"+fieldName);
					//////System.out.println("valuelength--"+value.length()+"---"+str11.length());
					//////System.out.println("value--"+value+"---"+value.equalsIgnoreCase("Center Sales"));
				
					if(value.equalsIgnoreCase("Sub Broker"))
					{
						////System.out.println("beorfer saveeeeeeee sub broker----");
						formObject.setNGEnable("FRAME_SUB_BROKER",true);
						formObject.setNGValue("ICICILOMBARD_HT_SUB_BROKER_CODE","");
						formObject.setNGValue("ICICILOMBARD_HT_TERRITORY_MANAGER","");
						formObject.setNGValue("ICICILOMBARD_HT_SUB_BROKER","");
						formObject.setNGEnable("FRAME_CENTER_CODE_RM",true);
						formObject.setNGValue("ICICILOMBARD_HT_CENTER_CODE_NAME","");
						formObject.setNGValue("ICICILOMBARD_HT_RM_EMPLOYEE","");
						formObject.setNGEnable("FRAME_CENTER_CODE_RM",false);
					}
		
					else if(value.equalsIgnoreCase("Center Sales") || value.equalsIgnoreCase("Centre Sales"))
					{
						boolean flag=true;
						////System.out.println("beorfer saveeeeeeee center sales----");
						formObject.setNGEnable("FRAME_CENTER_CODE_RM",true);
						formObject.setNGValue("ICICILOMBARD_HT_CENTER_CODE_NAME","");
						formObject.setNGValue("ICICILOMBARD_HT_RM_EMPLOYEE","");
						formObject.setNGEnable("FRAME_SUB_BROKER",true);
						formObject.setNGValue("ICICILOMBARD_HT_SUB_BROKER_CODE","");
						formObject.setNGValue("ICICILOMBARD_HT_TERRITORY_MANAGER","");
						formObject.setNGValue("ICICILOMBARD_HT_SUB_BROKER","");
						formObject.setNGEnable("FRAME_SUB_BROKER",false);
					}

				
			} */
		}

	
}	
public int validateType(String fieldName,String value,String validationType,String displayName) 
	{		
		String submittedValue = value;		
		System.out.println("dsfsdfsdfs: "+value);
		String Type = "";
		if(validationType.equals("emptyCheck"))
		{
			////System.out.println("inside validateType-->value: " + value);
			if(value == "" || value==null || value.length()==0 || value.equals("--Select--"))
			{
				JOptionPane.showMessageDialog(null," "+displayName+" can not be left blank. ");
				////System.out.println("inside validateType: satish");
				//formObject.NGClear(fieldName);
				formObject.NGFocus(fieldName);
				return 0;
			}
		}
		
		/****************************** PID-HT process changes ******************************/
		if(validationType.equals("customValCheck"))
		{
			////System.out.println("inside validateType-->value: " + value);
			if(value == "" || value==null || value.length()==0 || value.equals("--Select--"))
			{
				JOptionPane.showMessageDialog(null,displayName);
				////System.out.println("inside validateType: satish");				
			    return 0;
			}
		}
		/******************************End PID-HT process changes ******************************/
		/****************************** CR 24 HSP product by satish ******************************/
		if(validationType.equals("lengthCheck"))
		{
			////System.out.println("inside validateType-->value: " + value);
			if(value.length()<=15 || value.length()>=20)
			{
				JOptionPane.showMessageDialog(null," "+displayName+" length should be between 15-20. ");
				////System.out.println("inside validateType: satish");
				//formObject.NGClear(fieldName);
				formObject.NGFocus(fieldName);
				return 0;
			}
		}
		/**************************** end CR 24 HSP product by satish ****************************/		
		
		else if(validationType.equals("IsAlphaNumeric"))
			Type = "[0-9a-zA-Z \t]*";
		else if(validationType.equals("onlyAlphaNumeric"))
			Type = "[0-9a-zA-Z\t]*";	
		else if(validationType.equals("onlyAlphaNumeric_star"))
			Type = "[0-9a-zA-Z*\t]*";
		else if(validationType.equals("OnlyNumeric_decimal"))
			Type = "([0-9]*)?+(\\.[0-9][0-9]*)?";
		else if(validationType.equals("IsAlphaNumeric_SpecialChar"))
			Type = "[0-9a-zA-Z.,_ \t/-]*";
		else if(validationType.equals("OnlyNumeric"))
			Type = "[0-9\t]*";		
		else if(validationType.equals("Remarks"))
			Type = "[0-9a-zA-Z.,@$*#%?()_\\u000A \t/-]*";
		else if(validationType.equals("OnlyAlphabets"))	
			Type = "[a-zA-Z \t]*";
		else if(validationType.equals("CustName"))
			Type = "[0-9a-zA-Z.,@&*'()_ \t/-]*";
		else if(validationType.equals("OnlyNumeric_hyphen"))
			Type = "([0-9]*)?+(\\-[0-9][0-9]*)?";	
		else if(validationType.equals("dateFormat(dd/mm/yyyy)"))
			Type = "^(0[1-9]|1[012])[- //.](0[1-9]|[12][0-9]|3[01])[- //.](19|20)\\d\\d$";
		Pattern p = Pattern.compile(Type);		
		//////System.out.println("Pattern p" + p);		
		Matcher m = p.matcher(submittedValue.toString());
		//////System.out.println("Matcher m " + m);
		boolean bInvalid = m.matches();
		//////System.out.println("The valid  :=:  " + bInvalid);
		if(!bInvalid)
		{					
			if(validationType.equalsIgnoreCase("IsAlphaNumeric"))  
			{
				JOptionPane.showMessageDialog(null,"Only Alphabets,Numbers and Spaces are allowed in " + displayName + ".");
				formObject.setNGValue(fieldName,"");
				formObject.NGFocus(fieldName);
				return 0;
			}else if(validationType.equalsIgnoreCase("onlyAlphaNumeric"))  
			{
				JOptionPane.showMessageDialog(null,"Only Alphabets and Numbers are allowed in " + displayName + ".");
				formObject.setNGValue(fieldName,"");
				formObject.NGFocus(fieldName);
				return 0;
			}else if(validationType.equalsIgnoreCase("OnlyNumeric_decimal"))
			{
				JOptionPane.showMessageDialog(null,"Only Numbers and a Decimal are allowed in " + displayName + ".");		
				formObject.setNGValue(fieldName,"");
				formObject.NGFocus(fieldName);
				return 0;
			}else if(validationType.equalsIgnoreCase("IsAlphaNumeric_SpecialChar"))
			{
				JOptionPane.showMessageDialog(null,"Only Alphabets, Numbers,Spaces,Dot,Underscore,Comma and - are allowed in " + displayName + ".");
				formObject.setNGValue(fieldName,"");
				formObject.NGFocus(fieldName);
				return 0;
			}else if(validationType.equalsIgnoreCase("OnlyNumeric"))
			{
				JOptionPane.showMessageDialog(null,"Only Numbers are allowed in " + displayName + ".");
				formObject.setNGValue(fieldName,"");
				formObject.NGFocus(fieldName);
				return 0;
			}else if(validationType.equalsIgnoreCase("OnlyAlphabets"))  
			{
				JOptionPane.showMessageDialog(null,"Only Alphabets and Spaces are allowed in " + displayName + ".");				
				formObject.setNGValue(fieldName,"");
				//formObject.NGFocus(fieldName);
				return 0;
			}else if(validationType.equalsIgnoreCase("CustName"))  
			{				
				Type = "[0-9a-zA-Z.,@&*'()_ \t/-]*";
				return 0;
			}else if(validationType.equalsIgnoreCase("Remarks"))  
			{
				Type = "[0-9a-zA-Z.,@$*#%?()_ \t/-]*";				
				return 0;
			}else if(validationType.equalsIgnoreCase("OnlyNumeric_hyphen"))  
			{
				JOptionPane.showMessageDialog(null,"Only Numbers and - are allowed in " + displayName + ".");				
				formObject.setNGValue(fieldName,"");
				formObject.NGFocus(fieldName);
				return 0;
			}
			else if(validationType.equalsIgnoreCase("dateFormat(dd/mm/yyyy)"))  
			{
				JOptionPane.showMessageDialog(null," Date should be in (dd/mm/yyyy) format" + displayName + ".");				
				formObject.setNGValue(fieldName,"");
				formObject.NGFocus(fieldName);
				return 0;
			}			
		}
		return 1;		
	}


/********************** Start Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR ***********************/

	public int datesCompare(String sDate1, String sDate2) 
	{
		////System.out.println("Inside DatesCompare Function Comparing Dates Along With Days For Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR");
		////System.out.println("ICICILOMBARD_HT_POLICY_PID_START_DATE Inside DatesCompare Function :" +sDate1);
		////System.out.println("ICICILOMBARD_HT_INSTRUMENT_DATE Inside DatesCompare Function :" +sDate2);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date1 = null;
		Date date2 = null;
		int day=0;
		try
		{
			////System.out.println("Inside Try Function ");
			date1 = formatter.parse(sDate1);
			date2 = formatter.parse(sDate2);
		}
		catch (ParseException e) 
		{
			////System.out.println("Parser Error");
		}
		/*if(date1.compareTo(date2)>=0)
		{
			day=(int)((date1.getTime()-date2.getTime())/(1000*60*60*24));
			////System.out.println("No of days:"+day);
			return day;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Policy Start Date Should Not Be Less Than Payment Date.");
			////System.out.println("Inside Else : Policy Start Date is less than Payment Date ");
			return 0;
		}*/
		
		if(date1 != null && date2 != null)
		{
			day=(int)((date1.getTime()-date2.getTime())/(1000*60*60*24));
			////System.out.println("Calculating No of days:"+day);
		}
		
		return day;
	

	}
	
	/********************** End Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR *************************/


	public void getData(String query,String noOfCols,String[] arrfieldName,boolean flag)
	{
		try
		{
		String inputXml="";
		String outputXml="";
		String result= "";
	
		NGEjbCalls ngEjbCalls;
	
			//passing the control and form data to NGEjbCalls class
			ngEjbCalls= new NGEjbCalls(formObject);
			// get input xml for the query
			inputXml=ngEjbCalls.callSelectInputXML(query,noOfCols);
			////System.out.println("input xml:-------->"+inputXml);
			// get output xml for the query
			outputXml=ngEjbCalls.executeXmlGeneric(inputXml);
			////System.out.println("output xml:-------->"+outputXml);
			result= ngEjbCalls.xmlParse(outputXml,arrfieldName,noOfCols,flag);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	//method to check if the string used is empty
	public int emptyCheckValidation(String fieldName,String fieldValue,String validationType,String displayName)
	{		//////System.out.println("Inside empty check  ");
		if( fieldValue == "" || fieldValue==null || fieldValue.length()==0 || fieldValue.equals("--Select--"))
		{
			JOptionPane.showMessageDialog(null," "+displayName+" can not be left blank"+fieldName);
			 if(fieldValue.equals("--Select--"))
			formObject.NGClear(fieldName);
			else
			formObject.setNGValue(fieldName,"");

			formObject.NGFocus(fieldName);
			return 0;
		}
		else return 1;
	}
	
	public void getSMMailId()
	{
		String query="select RMEMAIL,IMMSUPEMAIL from NG_LOMBARD_EMP_DETAIL where rmid='"+formObject.getNGValue("ICICILOMBARD_HT_SM_ID")+"'";
		String outputXml="";
		String inputXml=""; 
		String result= "";
		String noOfCols="2";
		WFXmlList RecordList= null;
		WFXmlResponse xmlResponse=null;
		String sResult="";
		
		NGEjbCalls ngEjbCalls=new NGEjbCalls(formObject);
		// get input xml for the query
		inputXml=ngEjbCalls.callSelectInputXML(query,noOfCols);
		////System.out.println("input xml:-------->"+inputXml);
		// get output xml for the query
		outputXml=ngEjbCalls.executeXmlGeneric(inputXml);
		////System.out.println("output xml:-------->"+outputXml);
		
		if(outputXml.equals("") || Integer.parseInt(outputXml.substring(outputXml.indexOf("<MainCode>")+10 , outputXml.indexOf("</MainCode>")))!=0)
		{
			////System.out.println("Error in Output xml ");

			//JOptionPane.showMessageDialog(null,"No results found !!");
		}
					
		xmlResponse = new WFXmlResponse(outputXml);
		for (RecordList = xmlResponse.createList("Results", "Result"); RecordList.hasMoreElements(); RecordList.skip())
		{
			sResult = RecordList.getVal("Result");
			//get the actual result
			//sResult=sResult.substring(1);
			sResult=sResult.substring(1,sResult.length());
			//split the result by the seperator and get the values seperately
			String strArr[] = sResult.split("\\|");
			formObject.setNGValue("ICICILOMBARD_HT_SM_EMAIL_ID",strArr[0]);	
			formObject.setNGValue("ICICILOMBARD_HT_IMMEDIATE_SUPERIOR_SM_ID",strArr[1]);
		}

	}
	/********************** Start Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR ***********************/
	public void exceptionDetails(String currentwrkstp)
	{
			
			  //JSObject js1 = null;
			jsObj = formObject.getJSObject();
			String str[]= new String[1];
			str[0] = currentwrkstp;
			////System.out.println("Start Raising Exception For Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR At :"+str[0]);
			//////System.out.println("Value of js: "+js);
			Object obj = jsObj.call("setExceptionName_HT", str);
			////System.out.println("After Raising Exception For Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR");	
			
	}
	/********************** End Omniflow HT- CR-8093-59790 (HT-FlapPrint) CR *************************/


}