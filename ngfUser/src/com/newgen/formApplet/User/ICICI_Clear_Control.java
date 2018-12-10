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


public class ICICI_Clear_Control
{
				private NGFPropInterface formObject = null;
				public ICICI_Clear_Control(NGFPropInterface formObject) //formObject Mandatory
				{
					this.formObject = formObject;
				}

				public boolean icici_clear_all_control()
				{
				///////////////Text Control//////////INSTRUMENT_NO
				formObject.setNGValue("ICICILOMBARD_HT_PREVIOUS_POLICY_NO","");
				formObject.setNGValue("ICICILOMBARD_HT_PROPOSAL_NO","");
				formObject.setNGValue("ICICILOMBARD_HT_CONFIRM_PROPOSAL_NO","");
				formObject.setNGValue("ICICILOMBARD_HT_PREFIX_PROPOSAL_NO","");
				formObject.setNGValue("ICICILOMBARD_HT_PREFIX_CONFIRM_PROPOSAL_NO","");
				formObject.setNGValue("ICICILOMBARD_HT_PREVIOUS_POLICY_NO","");
				formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_DATE","");
				formObject.setNGValue("ICICILOMBARD_HT_INSTRUMENT_AMOUNT","");
				formObject.setNGValue("ICICILOMBARD_HT_CREDIT_CARD_NO","");
				formObject.setNGValue("ICICILOMBARD_HT_CREDITCARD_EXP_DATE","");
				formObject.setNGValue("ICICILOMBARD_HT_AUTHORIZATION_CODE","");
				formObject.setNGValue("ICICILOMBARD_HT_AUTHORIZATION_DATE","");
				formObject.setNGValue("ICICILOMBARD_HT_PID_NO","");
				formObject.setNGValue("ICICILOMBARD_HT_CUSTOMER_NAME","");
				formObject.setNGValue("ICICILOMBARD_HT_DOB","");
				formObject.setNGValue("ICICILOMBARD_HT_SANCTION_DATE","");
				formObject.setNGValue("ICICILOMBARD_HT_DISBURSAL_DATE","");
				formObject.setNGValue("ICICILOMBARD_HT_OCCUPATION","");
				formObject.setNGValue("ICICILOMBARD_HT_SUM_INSURED","");
				/***** CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket*******/
				/*formObject.setNGValue("ICICILOMBARD_HT_SECONDARY_DEAL_NO","");
				formObject.setNGValue("ICICILOMBARD_HT_PRIMARY_VERTICAL","");
				formObject.setNGValue("ICICILOMBARD_HT_SECONDARY_VERTICAL","");*/
				/***** End CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket***/
				formObject.setNGValue("ICICILOMBARD_HT_REFERENCE1","");
				formObject.setNGValue("ICICILOMBARD_HT_REFERENCE2","");
				formObject.setNGValue("ICICILOMBARD_HT_CONTACT1","");
				formObject.setNGValue("ICICILOMBARD_HT_CONTACT2","");
				formObject.setNGValue("ICICILOMBARD_HT_IL_LOCATION","");
				formObject.setNGValue("ICICILOMBARD_HT_IL_LOCATION_CODE","");


				formObject.setNGValue("ICICILOMBARD_HT_PREMIUM_AMOUNT","");
				formObject.setNGValue("ICICILOMBARD_HT_LAN","");
				formObject.setNGValue("ICICILOMBARD_HT_PAN","");
				formObject.setNGValue("ICICILOMBARD_HT_EMPCODE_CSO","");
				//formObject.setNGValue("ICICILOMBARD_HT_WRM","");//CR25_Masters of KRG,Removal of fields & RMT Bucket 
				formObject.setNGValue("ICICILOMBARD_HT_WRE","");
				formObject.setNGValue("ICICILOMBARD_HT_CHANNEL_EMP_INFO","");
				formObject.setNGValue("ICICILOMBARD_HT_BSM_ID","");
				formObject.setNGValue("ICICILOMBARD_HT_BCM_ID","");
				formObject.setNGValue("ICICILOMBARD_HT_RO_DSA_COUNSELOR_ID","");
				//formObject.setNGValue("ICICILOMBARD_HT_TRAINEE_ID","");//CR25_Masters of KRG,Removal of fields & RMT Bucket
				formObject.setNGValue("ICICILOMBARD_HT_TA_CODE","");

				formObject.setNGValue("ICICILOMBARD_HT_RM_EMPLOYEE","");
				formObject.setNGValue("ICICILOMBARD_HT_PAYMENT_ID","");
				formObject.setNGValue("ICICILOMBARD_HT_CUSTOMER_ID","");
				formObject.setNGValue("ICICILOMBARD_HT_REMARKS","");
				/***** CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket*******/
				/*formObject.setNGValue("ICICILOMBARD_HT_SUB_BROKER","");
				formObject.setNGValue("ICICILOMBARD_HT_SUB_BROKER_CODE","");
				formObject.setNGValue("ICICILOMBARD_HT_TERRITORY_MANAGER","");*/
				formObject.setNGValue("ICICILOMBARD_HT_SUB_VERTICAL","");
				formObject.setNGValue("ICICILOMBARD_HT_VERTICAL","");
				/***** End CR25_Changes in Masters of KRG & Fields_Removal & CR26_Removal of RMT Bucket***/

				// Combo box////////////////
				//formObject.NGClear("ICICILOMBARD_HT_SUB_VERTICAL");
				//formObject.NGClear("ICICILOMBARD_HT_VERTICAL");
						
				formObject.NGClear("ICICILOMBARD_HT_PRODUCT");
				formObject.setNGValue("ICICILOMBARD_HT_PRODUCT_ABBR_CODE","");
				formObject.setNGValue("ICICILOMBARD_HT_SUB_PRODUCT","");
				
				formObject.NGClear("ICICILOMBARD_HT_TRANSACTION_TYPE");
				formObject.NGAddItem("ICICILOMBARD_HT_TRANSACTION_TYPE","--Select--");
				formObject.NGAddItem("ICICILOMBARD_HT_TRANSACTION_TYPE","New");
				formObject.NGAddItem("ICICILOMBARD_HT_TRANSACTION_TYPE","Renewal");
				formObject.NGAddItem("ICICILOMBARD_HT_TRANSACTION_TYPE","Rollover");
				
				formObject.setNGValue("ICICILOMBARD_HT_BANK_NAME","");
				
				/******************************* PID-HT process changes ********************************/
				/*formObject.NGClear("ICICILOMBARD_HT_PAYMENT_MODE");
				formObject.NGAddItem("ICICILOMBARD_HT_PAYMENT_MODE","--Select--");
				formObject.NGAddItem("ICICILOMBARD_HT_PAYMENT_MODE","Credit Card");
				formObject.NGAddItem("ICICILOMBARD_HT_PAYMENT_MODE","Cheque");
				formObject.NGAddItem("ICICILOMBARD_HT_PAYMENT_MODE","ECS");
				formObject.NGAddItem("ICICILOMBARD_HT_PAYMENT_MODE","Fund Transfer");
				formObject.NGAddItem("ICICILOMBARD_HT_PAYMENT_MODE","Demand Draft");
				formObject.NGAddItem("ICICILOMBARD_HT_PAYMENT_MODE","CDBG");*/
				/******************************End PID-HT process changes ******************************/
								
				formObject.setNGValue("ICICILOMBARD_HT_SM_ID","");
				formObject.setNGValue("ICICILOMBARD_HT_SM_NAME","");
				
				/**************************************Start HT SP Code CR CR-8093-69682*****************************************************/
				formObject.setNGValue("ICICILOMBARD_HT_DEAL_IL_LOCATION","");
				formObject.setNGValue("ICICILOMBARD_HT_SP_NAME","");
				formObject.setNGValue("ICICILOMBARD_HT_SP_PAN","");
				/**************************************End HT SP Code CR CR-8093-69682*****************************************************/
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
				
				formObject.setNGValue("ICICILOMBARD_HT_SOURCE_BUSINESS","");
				formObject.setNGValue("ICICILOMBARD_HT_CHANNEL_SOURCE","");
				formObject.setNGValue("ICICILOMBARD_HT_BANK_BRANCH_NAME","");
				formObject.setNGValue("ICICILOMBARD_HT_BRANCH_ID_UBO_NAME","");
				formObject.setNGValue("ICICILOMBARD_HT_CENTER_CODE_NAME","");
				formObject.NGClear("ICICILOMBARD_HT_ECS_MANDATE");
				formObject.NGAddItem("ICICILOMBARD_HT_ECS_MANDATE","--Select--");
				formObject.NGAddItem("ICICILOMBARD_HT_ECS_MANDATE","Yes");
				formObject.NGAddItem("ICICILOMBARD_HT_ECS_MANDATE","No");
				formObject.NGClear("ICICILOMBARD_HT_IS_EMI");
				formObject.NGAddItem("ICICILOMBARD_HT_IS_EMI","--Select--");
				formObject.NGAddItem("ICICILOMBARD_HT_IS_EMI","Yes");
				formObject.NGAddItem("ICICILOMBARD_HT_IS_EMI","No");
				
				formObject.setNGEnable("FRAME_PREV_POLICY_NUMBER",false);
				formObject.setNGEnable("FRAME_CHEQUE_DD",false);
				formObject.setNGEnable("FRAME_SAVINGS_ACCOUNT",false);
				formObject.setNGEnable("FRAME_CREDIT_CARD",false);
				formObject.setNGEnable("FRAME_PID_NO",false);
				formObject.setNGEnable("FRAME_IF_PRODUCT_HSP",false);
				//formObject.setNGEnable("FRAME_SOURCE_BUSINESS",false);
				formObject.setNGEnable("FRAME_IS_EMP_CODE",false);
				formObject.setNGEnable("FRAME_WRE_WRM",false);
				
				formObject.setNGEnable("FRAME_CHANNEL_EMP_INFO",false);
				formObject.setNGEnable("FRAME_BSM_BCM",false);
				formObject.setNGEnable("FRAME_CENTER_CODE_RM",false);
				formObject.setNGEnable("FRAME_SUB_BROKER",false);
				return true;

				}
	



}
