/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.newgen.formApplet.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import netscape.javascript.JSObject;
import com.newgen.formApplet.XMLParser;
import com.newgen.formApplet.User.NGEjbCalls;
import java.text.*;
import java.util.*;


/**
 *
 * @author ILOM09138
 */
public class ICICI_LOM_MHT_Validation
{

    private NGFPropInterface formObject = null;
	public JSObject js1 = null;
	String[] str = new String[1];
	Object obj1 = new Object();
	JSObject jsObj = null; //MHT-PID CR-8127-59721 enhancemnet additional CR

    public ICICI_LOM_MHT_Validation(NGFPropInterface formObject)
    {
        this.formObject = formObject;
    }

    /**
     * 
     * @param pEvent
     * @param ActivityName
     * @param WorkItemName
     * @return
     */
    public int mandatoryControls(String pEvent,String ActivityName,String WorkItemName)
    {
        String wsName = formObject.getWFActivityName();
		XMLParser generalDataParser = new XMLParser();
        String fieldName="";
		String value="";
		String Label="";
		String exceptionStatus = "";
		String wiName = ""; //MHT-PID Process Integration
		String url = ""; //MHT-PID Process Integration
		
        
		if(pEvent.equals("D"))
        {
            /*************************** MHT-PID Process Integration ****************************/
			/*if(wsName.equalsIgnoreCase("BSG_DATAENTRY"))
			{
				//System.out.println("BSG_DATAENTRY done click"+wsName);
				generalDataParser.setInputXML(formObject.getWFGeneralData());
				wiName = generalDataParser.getValueOf("ProcessInstanceId");
				//System.out.println("wsName"+wsName);
				//System.out.println("wiName"+wiName);
				url = "AddFolder.jsp?Process=MHT" + "&Pid=" + wiName;
				js1 = formObject.getJSObject();
                //showPage(url, "addfolder");
			}*/
			
			/************************* End MHT-PID Process Integration **************************/
			
			String str[]= new String[1];
			str[0] = "ChkExpRaise";
			JSObject js=formObject.getJSObject() ;
			Object obj=js.call("MHT_JSCheckExceptionStatus",str);
				// Exception for new worksteps
			if(wsName.equalsIgnoreCase("BSG_DataEntry_QC") || wsName.equalsIgnoreCase("IPartner_Global") || wsName.equalsIgnoreCase("COPS_Team") || wsName.equalsIgnoreCase("COPS_Endorsement") || wsName.equalsIgnoreCase("COPS_Priority") || wsName.equalsIgnoreCase("COPS_IPartner") || wsName.equalsIgnoreCase("COPS_OpenCN") || wsName.equalsIgnoreCase("COPS_Cust_Id") || wsName.equalsIgnoreCase("COPS_CustId_Priority") || wsName.equalsIgnoreCase("COPS_Calculator") || wsName.equalsIgnoreCase("COPS_MAT") || wsName.equalsIgnoreCase("COPS_QC") || wsName.equalsIgnoreCase("BSG_Exception") || wsName.equalsIgnoreCase("BSG_Endorsement"))
			{
				//System.out.println("wsName"+wsName);
				//System.out.println("Exception Validation");
				/*String str[]= new String[1];
				str[0] = "ChkExpRaise";
				JSObject js=formObject.getJSObject() ;
				Object obj=js.call("MHT_JSCheckExceptionStatus",str);*/
				//System.out.println("Exception Status: "+obj.toString());
				
				//Adding Cops Worksteps for discard
				if((wsName.equalsIgnoreCase("BSG_Exception") || wsName.equalsIgnoreCase("BSG_DataEntry_QC") || wsName.equalsIgnoreCase("BSG_Endorsement") || wsName.equalsIgnoreCase("COPS_Team") || wsName.equalsIgnoreCase("COPS_Priority") || wsName.equalsIgnoreCase("COPS_IPartner") || wsName.equalsIgnoreCase("COPS_Cust_Id") || wsName.equalsIgnoreCase("COPS_CustId_Priority") || wsName.equalsIgnoreCase("COPS_Calculator") || wsName.equalsIgnoreCase("COPS_Endorsement") || wsName.equalsIgnoreCase("COPS_MAT") || wsName.equalsIgnoreCase("COPS_OpenCN") || wsName.equalsIgnoreCase("COPS_QC")) && formObject.getNGValue("MHT_DISCARD").equalsIgnoreCase("true"))
				{
					//System.out.println("BSG_Exception/BSG_DataEntry_QC/COPS MHT_DISCARD Status: "+formObject.getNGValue("MHT_DISCARD").equalsIgnoreCase("true"));
					if(validateType("MHT_DISCARD_REASON",formObject.getNGValue("MHT_DISCARD_REASON"),"emptyCheck","Discard Reason")==0)
					return 0;
				}
				else if(!(wsName.equalsIgnoreCase("BSG_Exception") || wsName.equalsIgnoreCase("BSG_DataEntry_QC") ||  wsName.equalsIgnoreCase("IPartner_Global") || wsName.equalsIgnoreCase("BSG_Endorsement")) && formObject.getNGValue("MHT_REJECT_TO_IT").equalsIgnoreCase("true"))
				{
					//System.out.println("MHT_SAMADHAN_ID Status: "+formObject.getNGValue("MHT_SAMADHAN_ID"));
					if(validateType("MHT_SAMADHAN_ID",formObject.getNGValue("MHT_SAMADHAN_ID"),"emptyCheck","Samadhan Intraction ID")==0)
					   return 0;
					
				}
				else if(obj.toString().equalsIgnoreCase("R"))
				{
					//System.out.println("Exception Status: "+obj.toString());
					exceptionStatus = obj.toString();
					/******************************* MHT-PID CR-8127-59721 enhancemnet additional CR ********************************/
					//System.out.println("Start Raising Exception For MHT-PID CR-8127-59721 enhancemnet additional CR At:"+wsName);					
					exceptionDetails(wsName);
					//System.out.println("After Raising Exception For MHT-PID CR-8127-59721 enhancemnet additional CR");
					/******************************End MHT-PID CR-8127-59721 enhancemnet additional CR ******************************/
					formObject.setNGEnable("FRAME_ROUTETO",true);
					formObject.setNGEnable("MHT_Route_To",true);
					formObject.setNGLocked("MHT_Route_To",true);
					if(validateType("MHT_Route_To",formObject.getNGValue("MHT_Route_To"),"emptyCheck","Route To")==0)
						return 0;
				}
				
			}
			
			if(wsName.equalsIgnoreCase("RM_Exception") || wsName.equalsIgnoreCase("RM_Endorsement"))
            {
				//System.out.println("wsName"+wsName);
				//System.out.println("Exception Validation");
				/*String str[]= new String[1];
				str[0] = "ChkExpRaise";
				JSObject js=formObject.getJSObject() ;
				Object obj=js.call("MHT_JSCheckExceptionStatus",str);*/
				
				if((wsName.equals("RM_Exception") || wsName.equals("RM_Endorsement")) && formObject.getNGValue("MHT_DISCARD").equalsIgnoreCase("true"))
				{
					//System.out.println("RM_Exception/RM_Endorsement MHT_DISCARD Status: "+formObject.getNGValue("MHT_DISCARD").equalsIgnoreCase("true"));
					if(validateType("MHT_DISCARD_REASON",formObject.getNGValue("MHT_DISCARD_REASON"),"emptyCheck","Discard Reason")==0)
					   return 0;
				}
				else if(obj.toString().equalsIgnoreCase("R") && (formObject.getNGValue("MHT_CONS_PREV_WORKSTEP").equalsIgnoreCase("BSG_DataEntry_QC") || formObject.getNGValue("MHT_CONS_PREV_WORKSTEP").equalsIgnoreCase("IPartner_Global") || formObject.getNGValue("MHT_CONS_PREV_WORKSTEP").equalsIgnoreCase("BSG_Endorsement")))
				{
					//System.out.println("RM_Exception/RM_Endorsement Status: "+obj.toString());
					formObject.setNGEnable("FRAME_ROUTETO",true);
					formObject.setNGEnable("MHT_Route_To",true);
					formObject.setNGLocked("MHT_Route_To",true);
					formObject.setNGListIndex("MHT_Route_To",0);
					formObject.setNGLocked("MHT_Route_To",false);
					formObject.setNGEnable("MHT_Route_To",false);
					JOptionPane.showMessageDialog(null,"Please Clear Exception ");
					return 0;
				}
					// Exception for new worksteps
				else if(obj.toString().equalsIgnoreCase("R") && (formObject.getNGValue("MHT_CONS_PREV_WORKSTEP").equalsIgnoreCase("COPS_Team") || wsName.equalsIgnoreCase("COPS_Endorsement") || formObject.getNGValue("MHT_CONS_PREV_WORKSTEP").equalsIgnoreCase("COPS_Priority") || formObject.getNGValue("MHT_CONS_PREV_WORKSTEP").equalsIgnoreCase("COPS_IPartner") || formObject.getNGValue("MHT_CONS_PREV_WORKSTEP").equalsIgnoreCase("COPS_OpenCN") || formObject.getNGValue("MHT_CONS_PREV_WORKSTEP").equalsIgnoreCase("COPS_Cust_Id") || formObject.getNGValue("MHT_CONS_PREV_WORKSTEP").equalsIgnoreCase("COPS_CustId_Priority") || formObject.getNGValue("MHT_CONS_PREV_WORKSTEP").equalsIgnoreCase("COPS_MAT") || formObject.getNGValue("MHT_CONS_PREV_WORKSTEP").equalsIgnoreCase("COPS_QC") || formObject.getNGValue("MHT_CONS_PREV_WORKSTEP").equalsIgnoreCase("COPS_Calculator"))) //MHT-PID Phase1 Process Integration
				{
					//System.out.println("COPSteam in related with rm exception Status: "+obj.toString());
					formObject.setNGEnable("FRAME_ROUTETO",true);
					formObject.setNGEnable("MHT_Route_To",true);
					formObject.setNGLocked("MHT_Route_To",true);
					if(validateType("MHT_Route_To",formObject.getNGValue("MHT_Route_To"),"emptyCheck","Route To")==0)
						return 0;
				}
            }
			
			/******************************* MHT-PID CR-8127-59721 enhancemnet additional CR ********************************/
			if(wsName.equalsIgnoreCase("BSG_DataEntry_QC") || wsName.equalsIgnoreCase("BSG_Endorsement"))
			{
				if(!formObject.getNGValue("MHT_MANUAL_COVERNOTE_NO").equals("") || (formObject.getNGValue("MHT_CASE_CATEGORY") != null && formObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER")))
				{					
					//Setting Normal as type of policy for Manual Covernote and IPartner cases
					formObject.setNGListIndex("MHT_TYPE_OF_POLICY",1);
					formObject.setNGEnable("MHT_TYPE_OF_POLICY",false);
					formObject.setNGLocked("MHT_TYPE_OF_POLICY",false);
					//System.out.println("MHT_TYPE_OF_POLICY in qc ===>"+formObject.getNGValue("MHT_TYPE_OF_POLICY"));
				}
			}
			/******************************End MHT-PID CR-8127-59721 enhancemnet additional CR ******************************/
			 if(wsName.equals("Reject_To_IT"))
			{
				//System.out.println("wsName"+wsName);
				//System.out.println("Reject_To_IT Validation");
				if(formObject.getNGValue("MHT_REJECT_TO_IT").equalsIgnoreCase("true"))
				{
					JOptionPane.showMessageDialog(null,"Please Clear the Reject To IT check ");
					return 0;
				}
				
				
			}
			String Disc=formObject.getNGValue("MHT_DISCARD");
			String Rej=formObject.getNGValue("MHT_REJECT_TO_IT");
			//System.out.println("Discard Value "+Disc);
			//System.out.println("Reject Value "+Rej);
			
			if(obj == null || !obj.toString().equalsIgnoreCase("R") && (Disc.equalsIgnoreCase("false") && Rej.equalsIgnoreCase("false")))
			{
				//System.out.println("WITHOUT EXCEPTION "+obj.toString());
				
            //start FRAME_SELECT_VERTICAL
							
				if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_SELECT_VERTICAL")) && (validateType("MHT_PRIMARY_VERTICAL",formObject.getNGValue("MHT_PRIMARY_VERTICAL"),"emptyCheck","Primary Vertical")==0))
					return 0;
					
				else if((wsName.equals("BSG_DataEntry")|| wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_SELECT_VERTICAL")) && (validateType("MHT_SUB_VERTICAL",formObject.getNGValue("MHT_SUB_VERTICAL"),"emptyCheck","Sub Vertical")==0))
					return 0;

				else if((wsName.equals("BSG_DataEntry")|| wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("MHT_PRODUCT_NAME")) && (validateType("MHT_PRODUCT_NAME",formObject.getNGValue("MHT_PRODUCT_NAME"),"emptyCheck","Product")==0))
					return 0;
				//end FRAME_SELECT_VERTICAL
			
				//start FRAME_TRANSACTION_TYPE
				else if((wsName.equals("BSG_DataEntry")|| wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_TRANSACTION_TYPE")) && (validateType("MHT_TRANSACTION_TYPE",formObject.getNGValue("MHT_TRANSACTION_TYPE"),"emptyCheck","Transaction Type")==0))
					return 0;

				else if((wsName.equals("BSG_DataEntry")|| wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_TRANSACTION_TYPE")) && (formObject.getNGValue("MHT_TRANSACTION_TYPE").equals("RENEWAL") || formObject.getNGValue("MHT_TRANSACTION_TYPE").equals("ROLL OVER") || formObject.getNGValue("MHT_TRANSACTION_TYPE").equals("USED")) && (validateType("MHT_PREVIOUS_POLICY_NO",formObject.getNGValue("MHT_PREVIOUS_POLICY_NO"),"emptyCheck","Previous Policy No")==0))  //CR-8127-83510 : MHT/PID Enhancement Development
					return 0;
				//end FRAME_TRANSACTION_TYPE

				//start FRAME_SEARCH_CRITERIA
				else if((wsName.equals("BSG_DataEntry") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_COVERNOTE_TYPE") && formObject.getNGValue("MHT_MANUAL_COVERNOTE_NO") != null) && (validateType("COVER_NOTE_TYPE",formObject.getNGValue("MHT_COVER_NOTE_TYPE"),"emptyCheck","COVER NOTE TYPE")==0)) //CR-8127-83510 : MHT/PID Enhancement Development
					return 0;
				/*************************** MHT-PID Process Integration ****************************/
				
				/**************************** CR-8127-83510 : Start MHT/PID Enhancement Development ***************************/   
				else if((wsName.equals("BSG_DataEntry")) &&(formObject.isNGEnable("FRAME_SEARCH_CRITERIA")) && (formObject.getNGValue("MHT_SEARCH_CRITERIA").equalsIgnoreCase("Manual CN")) && (validateType("MHT_MANUAL_COVERNOTE_NO",formObject.getNGValue("MHT_MANUAL_COVERNOTE_NO"),"emptyCheck","MANUAL COVERNOTE NO")==0))
					return 0;
					
				else if((wsName.equals("BSG_DataEntry")) &&(formObject.isNGEnable("FRAME_SEARCH_CRITERIA") && formObject.getNGValue("MHT_MANUAL_COVERNOTE_NO") != null) && (duplicateCheck("MHT_MANUAL_COVERNOTE_NO",formObject.getNGValue("MHT_MANUAL_COVERNOTE_NO"))==0))
					return 0;
				
				/**************************** CR-8127-83510 : End MHT/PID Enhancement Development *****************************/
				/************************* End MHT-PID Process Integration **************************/

				else if((wsName.equals("BSG_DataEntry") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_BSGQC_DETAILS") && formObject.getNGValue("MHT_TYPE_OF_POLICY") != null) && (validateType("MHT_TYPE_OF_POLICY",formObject.getNGValue("MHT_TYPE_OF_POLICY"),"emptyCheck","TYPE OF POLICY")==0)) //CR-8127-83510 : MHT/PID Enhancement Development
					return 0;
				//End FRAME_SEARCH_CRITERIA

				//START FRAME_SALESMGR_EMPID
				
				
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_SALESMGR_EMPID")) && (validateType("MHT_PAS_SYSTEM",formObject.getNGValue("MHT_PAS_SYSTEM"),"emptyCheck","PAS System")==0))
					return 0;
				else if((wsName.equals("BSG_DataEntry")|| wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_SALESMGR_EMPID")) && (validateType("MHT_SM_NAME",formObject.getNGValue("MHT_SM_NAME"),"emptyCheck","Sales Manager Name")==0))
					return 0;
					
				else if((wsName.equals("BSG_DataEntry")|| wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") ||wsName.equals("BSG_DataEntry_QC")) &&(formObject.isNGEnable("FRAME_SALESMGR_EMPID")) && (validateType("MHT_SM_ID",formObject.getNGValue("MHT_SM_ID"),"emptyCheck","Sales Manager/Emp ID")==0))
					return 0;

				else if((wsName.equals("BSG_DataEntry") ||wsName.equals("BSG_DataEntry_QC")|| wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception")) &&(formObject.isNGEnable("FRAME_SALESMGR_EMPID")) && (validateType("MHT_IL_LOCATION",formObject.getNGValue("MHT_IL_LOCATION"),"emptyCheck","IL Location")==0))
					return 0;
					
				/*else if((wsName.equals("BSG_DataEntry") ||wsName.equals("BSG_DataEntry_QC")|| wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception")) &&(formObject.isNGEnable("FRAME_SALESMGR_EMPID")) && (validateType("MHT_IL_LOCATION_CODE",formObject.getNGValue("MHT_IL_LOCATION_CODE"),"emptyCheck","IL Location Code")==0))
					return 0;*/
				/*************************** MHT-PID Process Integration ****************************/
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")|| wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception")) && (formObject.isNGEnable("FRAME_SALESMGR_EMPID")) && ((formObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Endorsement") && formObject.getNGValue("MHT_ENDORSEMENT_TYPE").equalsIgnoreCase("Others")) || formObject.getNGValue("MHT_TRANSACTION_TYPE").equalsIgnoreCase("RENEWAL")) && (validateType("MHT_CUSTOMER_ID",formObject.getNGValue("MHT_CUSTOMER_ID"),"emptyCheck","Customer ID")==0))
					return 0;
			   /*************************** MHT-PID Phase1 Process Integration ****************************/	
			   /*else if((wsName.equals("BSG_DataEntry")|| wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") ||wsName.equals("BSG_DataEntry_QC")) && (formObject.isNGEnable("FRAME_SALESMGR_EMPID")) && ((formObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Endorsement") && formObject.getNGValue("MHT_ENDORSEMENT_TYPE").equalsIgnoreCase("Others")) || formObject.getNGValue("MHT_TRANSACTION_TYPE").equalsIgnoreCase("RENEWAL")) && (validateType("MHT_CUSTOMER_NAME",formObject.getNGValue("MHT_CUSTOMER_NAME"),"emptyCheck","Customer Name")==0))
					return 0;*/
				else if((wsName.equals("BSG_DataEntry")|| wsName.equals("COPS_Cust_Id") || wsName.equals("COPS_CustId_Priority")) && (formObject.isNGEnable("FRAME_SALESMGR_EMPID")) && (validateType("MHT_CUSTOMER_NAME",formObject.getNGValue("MHT_CUSTOMER_NAME"),"emptyCheck","Customer Name")==0))
				return 0;
					
				else if((wsName.equals("COPS_Cust_Id") ||wsName.equals("COPS_CustId_Priority")) && (formObject.isNGEnable("FRAME_SALESMGR_EMPID")) && (validateType("MHT_CUSTOMER_ID",formObject.getNGValue("MHT_CUSTOMER_ID"),"emptyCheck","Customer ID")==0))
					return 0;		
					
			   
				/************************* End MHT-PID Process Integration **************************/
				/************************* End MHT-PID Phase1 Process Integration **************************/	
						
				//END FRAME_SALESMGR_EMPID

				//start FRAME_PAYMENTID
				else if((wsName.equals("IPartner_Global")) &&(formObject.isNGEnable("FRAME_PAYMENTID")) && (validateType("MHT_IPARTNER_PAYMENT_ID_NO",formObject.getNGValue("MHT_IPARTNER_PAYMENT_ID_NO"),"emptyCheck","Payment ID No.(IPartner)")==0))
					return 0;				
				
				/*************************** MHT-PID Process Integration ****************************/	
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement")) &&(formObject.isNGEnable("FRAME_PAYMENTID") && formObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Endorsement")) && (validateType("MHT_ENDORSEMENT_TYPE",formObject.getNGValue("MHT_ENDORSEMENT_TYPE"),"emptyCheck","Endorsement Type")==0))
					return 0;
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement")) &&(formObject.isNGEnable("FRAME_PAYMENTID") && formObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Endorsement")) && (validateType("MHT_ENDORSEMENT_NO",formObject.getNGValue("MHT_ENDORSEMENT_NO"),"emptyCheck","Endorsement No.")==0))
					return 0;
				 else if((wsName.equals("BSG_DataEntry") ||wsName.equals("BSG_DataEntry_QC")|| wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement")) && (formObject.isNGEnable("FRAME_PAYMENTID")) && (validateType("MHT_PID_PAYMENT_TYPE",formObject.getNGValue("MHT_PID_PAYMENT_TYPE"),"emptyCheck","PID Tagging")==0))
					return 0;
				 else if((wsName.equals("BSG_DataEntry") ||wsName.equals("BSG_DataEntry_QC")|| wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && (validateType("MHT_PF_PAYMENT_ID_NO",formObject.getNGValue("MHT_PF_PAYMENT_ID_NO"),"emptyCheck","Payment ID1 No.(Pathfinder)")==0))
					return 0;
				/************************* End MHT-PID Process Integration **************************/
				//end FRAME_PAYMENTID

				//start FRAME_MODEOF_PAYMENT
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && validateType("MHT_pAYMENT_TYPE1",formObject.getNGValue("MHT_pAYMENT_TYPE1"),"emptyCheck","Mode of Payment")==0)
					return 0;			
				//start MHT_pAYMENT_TYPE1
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE1").equalsIgnoreCase("Credit Card") &&validateType("MHT_pAYMENT_TYPE1_NO",formObject.getNGValue("MHT_pAYMENT_TYPE1_NO"),"emptyCheck","Auth code")==0)
					return 0;
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE1").equalsIgnoreCase("Credit Card") &&validateType("MHT_pAYMENT_TYPE1_DATE",formObject.getNGValue("MHT_pAYMENT_TYPE1_DATE"),"emptyCheck","Auth date")==0)
					return 0;
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE1").equalsIgnoreCase("Credit Card") &&validateType("MHT_pAYMENT_TYPE1_AMOUNT",formObject.getNGValue("MHT_pAYMENT_TYPE1_AMOUNT"),"emptyCheck","CC amount")==0)
					return 0;
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE1").equalsIgnoreCase("Credit Card") &&validateType("MHT_pAYMENT_TYPE1_EXPIRYDATE",formObject.getNGValue("MHT_pAYMENT_TYPE1_EXPIRYDATE"),"emptyCheck","CC expiry date")==0)
					return 0;	
				
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE1").equalsIgnoreCase("Cheque") && validateType("MHT_pAYMENT_TYPE1_NO",formObject.getNGValue("MHT_pAYMENT_TYPE1_NO"),"emptyCheck","Instrument No.")==0)
					return 0;
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE1").equalsIgnoreCase("Cheque") && validateType("MHT_pAYMENT_TYPE1_DATE",formObject.getNGValue("MHT_pAYMENT_TYPE1_DATE"),"emptyCheck","Instrument date")==0)
					return 0;
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE1").equalsIgnoreCase("Cheque") && validateType("MHT_pAYMENT_TYPE1_AMOUNT",formObject.getNGValue("MHT_pAYMENT_TYPE1_AMOUNT"),"emptyCheck","Instrument amount")==0)
					return 0;
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE1").equalsIgnoreCase("Cheque") && validateType("MHT_pAYMENT_TYPE1_BANKNAME",formObject.getNGValue("MHT_pAYMENT_TYPE1_BANKNAME"),"emptyCheck","Bank name")==0)
						return 0;	
				
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE1").equalsIgnoreCase("Fund Transfer") && validateType("MHT_pAYMENT_TYPE1_DATE",formObject.getNGValue("MHT_pAYMENT_TYPE1_DATE"),"emptyCheck","FT date")==0)
					return 0;
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE1").equalsIgnoreCase("Fund Transfer") && validateType("MHT_pAYMENT_TYPE1_AMOUNT",formObject.getNGValue("MHT_pAYMENT_TYPE1_AMOUNT"),"emptyCheck","FT amount")==0)
					return 0;
				
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE1").equalsIgnoreCase("Demand Draft") && validateType("MHT_pAYMENT_TYPE1_NO",formObject.getNGValue("MHT_pAYMENT_TYPE1_NO"),"emptyCheck","Instrument No.")==0)
					return 0;
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE1").equalsIgnoreCase("Demand Draft") && validateType("MHT_pAYMENT_TYPE1_DATE",formObject.getNGValue("MHT_pAYMENT_TYPE1_DATE"),"emptyCheck","Instrument date")==0)
					return 0;
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE1").equalsIgnoreCase("Demand Draft") && validateType("MHT_pAYMENT_TYPE1_AMOUNT",formObject.getNGValue("MHT_pAYMENT_TYPE1_AMOUNT"),"emptyCheck","Instrument amount")==0)
					return 0;
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE1").equalsIgnoreCase("Demand Draft") && validateType("MHT_pAYMENT_TYPE1_BANKNAME",formObject.getNGValue("MHT_pAYMENT_TYPE1_BANKNAME"),"emptyCheck","Bank name")==0)
					return 0;	
				
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE1").equalsIgnoreCase("CDBG") && validateType("MHT_pAYMENT_TYPE1_NO",formObject.getNGValue("MHT_pAYMENT_TYPE1_NO"),"emptyCheck","CDBG No.")==0)
					return 0;
				
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE1").equalsIgnoreCase("Cash") && validateType("MHT_pAYMENT_TYPE1_DATE",formObject.getNGValue("MHT_pAYMENT_TYPE1_DATE"),"emptyCheck","Cash received date")==0)
					return 0;
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE1").equalsIgnoreCase("Cash") && validateType("MHT_pAYMENT_TYPE1_AMOUNT",formObject.getNGValue("MHT_pAYMENT_TYPE1_AMOUNT"),"emptyCheck","Cash amount")==0)
					return 0;			
				//end MHT_pAYMENT_TYPE1
				
				//start MHT_pAYMENT_TYPE2
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE2").equalsIgnoreCase("Credit Card") && validateType("MHT_pAYMENT_TYPE2_NO",formObject.getNGValue("MHT_pAYMENT_TYPE2_NO"),"emptyCheck","Auth code")==0)
					return 0;
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE2").equalsIgnoreCase("Credit Card") && validateType("MHT_pAYMENT_TYPE2_DATE",formObject.getNGValue("MHT_pAYMENT_TYPE2_DATE"),"emptyCheck","Auth date")==0)
					return 0;
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE2").equalsIgnoreCase("Credit Card") && validateType("MHT_pAYMENT_TYPE2_AMOUNT",formObject.getNGValue("MHT_pAYMENT_TYPE2_AMOUNT"),"emptyCheck","CC amount")==0)
					return 0;
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE2").equalsIgnoreCase("Credit Card") && validateType("MHT_pAYMENT_TYPE2_EXPIRYDATE",formObject.getNGValue("MHT_pAYMENT_TYPE2_EXPIRYDATE"),"emptyCheck","CC expiry date")==0)
					return 0;	
				
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE2").equalsIgnoreCase("Cheque") && validateType("MHT_pAYMENT_TYPE2_NO",formObject.getNGValue("MHT_pAYMENT_TYPE2_NO"),"emptyCheck","Instrument No.")==0)
					return 0;
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE2").equalsIgnoreCase("Cheque") && validateType("MHT_pAYMENT_TYPE2_DATE",formObject.getNGValue("MHT_pAYMENT_TYPE2_DATE"),"emptyCheck","Instrument date")==0)
					return 0;
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE2").equalsIgnoreCase("Cheque") && validateType("MHT_pAYMENT_TYPE2_AMOUNT",formObject.getNGValue("MHT_pAYMENT_TYPE2_AMOUNT"),"emptyCheck","Instrument amount")==0)
					return 0;
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE2").equalsIgnoreCase("Cheque") && validateType("MHT_pAYMENT_TYPE2_BANKNAME",formObject.getNGValue("MHT_pAYMENT_TYPE2_BANKNAME"),"emptyCheck","Bank name")==0)
					return 0;	
				
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE2").equalsIgnoreCase("Fund Transfer") && validateType("MHT_pAYMENT_TYPE2_DATE",formObject.getNGValue("MHT_pAYMENT_TYPE2_DATE"),"emptyCheck","FT date")==0)
					return 0;
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE2").equalsIgnoreCase("Fund Transfer") && validateType("MHT_pAYMENT_TYPE2_AMOUNT",formObject.getNGValue("MHT_pAYMENT_TYPE2_AMOUNT"),"emptyCheck","FT amount")==0)
					return 0;
				
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE2").equalsIgnoreCase("Demand Draft") && validateType("MHT_pAYMENT_TYPE2_NO",formObject.getNGValue("MHT_pAYMENT_TYPE2_NO"),"emptyCheck","Instrument No.")==0)
					return 0;
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE2").equalsIgnoreCase("Demand Draft") && validateType("MHT_pAYMENT_TYPE2_DATE",formObject.getNGValue("MHT_pAYMENT_TYPE2_DATE"),"emptyCheck","Instrument date")==0)
					return 0;
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE2").equalsIgnoreCase("Demand Draft") && validateType("MHT_pAYMENT_TYPE2_AMOUNT",formObject.getNGValue("MHT_pAYMENT_TYPE2_AMOUNT"),"emptyCheck","Instrument amount")==0)
					return 0;
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE2").equalsIgnoreCase("Demand Draft") && validateType("MHT_pAYMENT_TYPE2_BANKNAME",formObject.getNGValue("MHT_pAYMENT_TYPE2_BANKNAME"),"emptyCheck","Bank name")==0)
					return 0;	
				
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE2").equalsIgnoreCase("CDBG") && validateType("MHT_pAYMENT_TYPE2_NO",formObject.getNGValue("MHT_pAYMENT_TYPE2_NO"),"emptyCheck","CDBG No.")==0)
					return 0;
				
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE2").equalsIgnoreCase("Cash") && validateType("MHT_pAYMENT_TYPE2_DATE",formObject.getNGValue("MHT_pAYMENT_TYPE2_DATE"),"emptyCheck","Cash received date")==0)
					return 0;
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE2").equalsIgnoreCase("Cash") && validateType("MHT_pAYMENT_TYPE2_AMOUNT",formObject.getNGValue("MHT_pAYMENT_TYPE2_AMOUNT"),"emptyCheck","Cash amount")==0)
					return 0;
				
				//end MHT_pAYMENT_TYPE2
				
				//start MHT_pAYMENT_TYPE3
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE3").equalsIgnoreCase("Credit Card") && validateType("MHT_pAYMENT_TYPE3_NO",formObject.getNGValue("MHT_pAYMENT_TYPE3_NO"),"emptyCheck","Auth code")==0)
					return 0;
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE3").equalsIgnoreCase("Credit Card") && validateType("MHT_pAYMENT_TYPE3_DATE",formObject.getNGValue("MHT_pAYMENT_TYPE3_DATE"),"emptyCheck","Auth date")==0)
					return 0;
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE3").equalsIgnoreCase("Credit Card") && validateType("MHT_pAYMENT_TYPE3_AMOUNT",formObject.getNGValue("MHT_pAYMENT_TYPE3_AMOUNT"),"emptyCheck","CC amount")==0)
					return 0;
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE3").equalsIgnoreCase("Credit Card") && validateType("MHT_pAYMENT_TYPE3_EXPIRYDATE",formObject.getNGValue("MHT_pAYMENT_TYPE3_EXPIRYDATE"),"emptyCheck","CC expiry date")==0)
					return 0;
				
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE3").equalsIgnoreCase("Cheque") && validateType("MHT_pAYMENT_TYPE3_NO",formObject.getNGValue("MHT_pAYMENT_TYPE3_NO"),"emptyCheck","Instrument No.")==0)
					return 0;
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE3").equalsIgnoreCase("Cheque") && validateType("MHT_pAYMENT_TYPE3_DATE",formObject.getNGValue("MHT_pAYMENT_TYPE3_DATE"),"emptyCheck","Instrument date")==0)
					return 0;
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE3").equalsIgnoreCase("Cheque") && validateType("MHT_pAYMENT_TYPE3_AMOUNT",formObject.getNGValue("MHT_pAYMENT_TYPE3_AMOUNT"),"emptyCheck","Instrument amount")==0)
					return 0;
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE3").equalsIgnoreCase("Cheque") && validateType("MHT_pAYMENT_TYPE3_BANKNAME",formObject.getNGValue("MHT_pAYMENT_TYPE3_BANKNAME"),"emptyCheck","Bank name")==0)
					return 0;
				
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE3").equalsIgnoreCase("Fund Transfer") && validateType("MHT_pAYMENT_TYPE3_DATE",formObject.getNGValue("MHT_pAYMENT_TYPE3_DATE"),"emptyCheck","FT date")==0)
					return 0;
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE3").equalsIgnoreCase("Fund Transfer") && validateType("MHT_pAYMENT_TYPE3_AMOUNT",formObject.getNGValue("MHT_pAYMENT_TYPE3_AMOUNT"),"emptyCheck","FT amount")==0)
					return 0;
				
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE3").equalsIgnoreCase("Demand Draft") && validateType("MHT_pAYMENT_TYPE3_NO",formObject.getNGValue("MHT_pAYMENT_TYPE3_NO"),"emptyCheck","Instrument No.")==0)
					return 0;
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE3").equalsIgnoreCase("Demand Draft") && validateType("MHT_pAYMENT_TYPE3_DATE",formObject.getNGValue("MHT_pAYMENT_TYPE3_DATE"),"emptyCheck","Instrument date")==0)
					return 0;
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE3").equalsIgnoreCase("Demand Draft") && validateType("MHT_pAYMENT_TYPE3_AMOUNT",formObject.getNGValue("MHT_pAYMENT_TYPE3_AMOUNT"),"emptyCheck","Instrument amount")==0)
					return 0;
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE3").equalsIgnoreCase("Demand Draft") && validateType("MHT_pAYMENT_TYPE3_BANKNAME",formObject.getNGValue("MHT_pAYMENT_TYPE3_BANKNAME"),"emptyCheck","Bank name")==0)
					return 0;
				
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE3").equalsIgnoreCase("CDBG") && validateType("MHT_pAYMENT_TYPE3_NO",formObject.getNGValue("MHT_pAYMENT_TYPE3_NO"),"emptyCheck","CDBG No.")==0)
					return 0;
				
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE3").equalsIgnoreCase("Cash") && validateType("MHT_pAYMENT_TYPE3_DATE",formObject.getNGValue("MHT_pAYMENT_TYPE3_DATE"),"emptyCheck","Cash received date")==0)
					return 0;
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")||  wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception") || wsName.equals("BSG_Endorsement")) && (formObject.isNGEnable("FRAME_MODEOF_PAYMENT")) && formObject.getNGValue("MHT_pAYMENT_TYPE3").equalsIgnoreCase("Cash") && validateType("MHT_pAYMENT_TYPE3_AMOUNT",formObject.getNGValue("MHT_pAYMENT_TYPE3_AMOUNT"),"emptyCheck","Cash amount")==0)
					return 0;		
				//end MHT_pAYMENT_TYPE3
				//end FRAME_MODEOF_PAYMENT 
				
				//start FRAME_IAGENT_PROPOSAL
				else if((wsName.equals("IPartner_Global") ||  wsName.equals("BSG_Exception") || wsName.equals("RM_Exception")) && (formObject.isNGEnable("FRAME_IAGENT_PROPOSAL")) && (formObject.getNGValue("MHT_cASE_CATEGORY").equalsIgnoreCase("IPARTNER")) && (validateType("MHT_iPARTNER_PROPOSAL_NO",formObject.getNGValue("MHT_iPARTNER_PROPOSAL_NO"),"emptyCheck","I-partner Proposal No.")==0))
					return 0;
				//end FRAME_IAGENT_PROPOSAL
				
				//start FRAME_MISC		COPS_Calculator	
				/*************************** MHT-PID Process Integration ****************************/
				else if((((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC") || wsName.equals("BSG_Endorsement") || wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception")) && formObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Policy Issuance"))  || (wsName.equals("COPS_Calculator"))) && (formObject.isNGEnable("FRAME_MISC")) && (validateType("MHT_pREMIUM_AMOUNT",formObject.getNGValue("MHT_pREMIUM_AMOUNT"),"emptyCheck","Total Premium")==0))
					return 0;
				else if((wsName.equals("BSG_DataEntry") /*|| wsName.equals("COPS_Calculator")*/ || wsName.equals("BSG_DataEntry_QC") || wsName.equals("BSG_Endorsement") || wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception")) && (formObject.isNGEnable("FRAME_MISC")) && formObject.isNGEnable("MHT_PAN_CARD_NO") && (validateType("MHT_PAN_CARD_NO",formObject.getNGValue("MHT_PAN_CARD_NO"),"emptyCheck","PAN CARDNO")==0))
					return 0;
				/************************* End MHT-PID Process Integration **************************/
					
				else if((wsName.equals("IPartner_Global")) && (formObject.isNGEnable("FRAME_MISC")) && (formObject.getNGValue("MHT_cASE_CATEGORY").equalsIgnoreCase("IPARTNER"))  && (validateType("MHT_aLTERNATE_POLICY_NUMBER",formObject.getNGValue("MHT_aLTERNATE_POLICY_NUMBER"),"emptyCheck","Alternate Policy No.")==0))
					return 0;
					
				//need to add product type motor condition for MHT_fINAL_QUOTE_NO
				/*else if((wsName.equals("BSG_DataEntry") ||wsName.equals("BSG_DataEntry_QC")|| wsName.equals("BSG_Exception") || wsName.equals("RM_Exception")) && (formObject.isNGEnable("FRAME_MISC") && formObject.isNGEnable("MHT_fINAL_QUOTE_NO")) && (formObject.getNGValue("MHT_PRODUCT_TYPE") != null && formObject.getNGValue("MHT_PRODUCT_TYPE").equalsIgnoreCase("MOTOR") && formObject.getNGValue("MHT_tYPE_OF_CATEGORY").equalsIgnoreCase("Policy Issuance")) && (validateType("MHT_fINAL_QUOTE_NO",formObject.getNGValue("MHT_fINAL_QUOTE_NO"),"emptyCheck","Final Quote No.")==0))*/
				else if((wsName.equals("BSG_DataEntry") ||wsName.equals("BSG_DataEntry_QC")|| wsName.equals("BSG_Exception") || wsName.equals("RM_Exception")) && (formObject.isNGEnable("FRAME_MISC") && formObject.isNGEnable("MHT_fINAL_QUOTE_NO") && formObject.getNGValue("MHT_tYPE_OF_CATEGORY").equalsIgnoreCase("Policy Issuance")) && (validateType("MHT_fINAL_QUOTE_NO",formObject.getNGValue("MHT_fINAL_QUOTE_NO"),"emptyCheck","Final Quote No.")==0))
				{
					//System.out.println("test in final quoteno");
					return 0;
				}
				//end FRAME_MISC
				
				//start FRAME_SOURCE_BUSINESS 
				else if((wsName.equals("BSG_DataEntry") ||wsName.equals("BSG_DataEntry_QC")|| wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception")) && (formObject.isNGEnable("FRAME_SOURCE_BUSINESS") && formObject.isNGEnable("MHT_sOURCE_BUSINESS")) && (validateType("MHT_sOURCE_BUSINESS",formObject.getNGValue("MHT_sOURCE_BUSINESS"),"emptyCheck","Source Business")==0))
					return 0;
					
				else if((wsName.equals("BSG_DataEntry") ||wsName.equals("BSG_DataEntry_QC")|| wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception")) && (formObject.isNGEnable("FRAME_SOURCE_BUSINESS") && formObject.isNGEnable("MHT_cHANNEL_SOURCE")) && (validateType("MHT_cHANNEL_SOURCE",formObject.getNGValue("MHT_cHANNEL_SOURCE"),"emptyCheck","Channel Source")==0))
					return 0;
				//end FRAME_SOURCE_BUSINESS
				
				/*************************** MHT-PID Process Integration ****************************/			
				/******* Branch_ID mandatory check instead of bANK_BRANCH_NAME ****************/
				//start FRAME_BANK_BRANCH_NAME 
				/*else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")|| wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception")) && (formObject.isNGEnable("FRAME_BANK_BRANCH_NAME")) && (validateType("MHT_bANK_BRANCH_NAME",formObject.getNGValue("MHT_bANK_BRANCH_NAME"),"emptyCheck","Bank Branch Name")==0))
				return 0;*/
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")|| wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception")) && (formObject.isNGEnable("FRAME_BANK_BRANCH_NAME") && (formObject.isNGEnable("MHT_BRANCH_ID"))) && (validateType("MHT_BRANCH_ID",formObject.getNGValue("MHT_BRANCH_ID"),"emptyCheck","BRANCH ID")==0))
				return 0;
				else if((wsName.equals("BSG_DataEntry_QC") || wsName.equals("BSG_Endorsement")) && formObject.getNGValue("MHT_PID_PAYMENT_TYPE").equalsIgnoreCase("Yes") && !formObject.getNGValue("MHT_DISCARD").equalsIgnoreCase("true") && (validateType("MHT_TAG_STATUS",formObject.getNGValue("MHT_TAG_STATUS"),"customValCheck","Case is not Tagged with PID, you cannot move the case")==0))
				return 0;
				/*******************Start SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI**********************/
				else if((wsName.equals("BSG_DataEntry")) && !((formObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Policy Issuance") && formObject.getNGValue("MHT_TYPE_OF_BUSINESS").equalsIgnoreCase("Direct"))) && (formObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("Bancassurance") || formObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BA")) && (formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("KRG")) && !(formObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER")) && (formObject.isNGEnable("FRAME_BANK_BRANCH_NAME")) && (formObject.isNGEnable("MHT_SP_CODE")) && (validateType("MHT_SP_CODE",formObject.getNGValue("MHT_SP_CODE"),"emptyCheck","SP Code")==0))
				{
				System.out.println("Inside the sp code logic change Validation Yogesh: frame enable:"+formObject.isNGEnable("FRAME_BANK_BRANCH_NAME") +"Sp Code enable :"+ formObject.isNGEnable("MHT_SP_CODE"));
				return 0;
				}
				/*******************End SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI**********************/
				/******Start MHT CR-8127-95325-GST-Omniflow development******/
				else if((wsName.equalsIgnoreCase("BSG_DataEntry") || wsName.equalsIgnoreCase("BSG_DataEntry_QC")|| wsName.equalsIgnoreCase("BSG_Exception") || wsName.equalsIgnoreCase("RM_Exception")) && (formObject.isNGEnable("FRAME_GST_GRID")) && !(formObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER")) && (validateType("MHT_GST_REGISTERED",formObject.getNGValue("MHT_GST_REGISTERED"),"emptyCheck","MHT_GST_REGISTERED")==0))
				return 0;				
				else if((wsName.equalsIgnoreCase("BSG_DataEntry") || wsName.equalsIgnoreCase("BSG_DataEntry_QC")|| wsName.equalsIgnoreCase("BSG_Exception") || wsName.equalsIgnoreCase("RM_Exception")) && (formObject.isNGEnable("FRAME_GST_GRID")) && !(formObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER")) && (formObject.getNGValue("MHT_GST_REGISTERED").equalsIgnoreCase("Yes")))
				{
					//System.out.println("Checking grid empty..."+formObject.getNGValue("MHT_qGrdGstVar").isEmpty());
					/*if(formObject.getNGValue("MHT_qGstGrdVar").isEmpty())
					{
						if(validateType("MHT_TXTGST_NUMBER",formObject.getNGValue("MHT_TXTGST_NUMBER"),"emptyCheck","GST Number")==0)
						return 0;
						if(validateType("MHT_TXTGST_STATE_NAME",formObject.getNGValue("MHT_TXTGST_STATE_NAME"),"emptyCheck","GST State")==0)
						return 0;					
					}*/
					if(validateType("MHT_TXTGST_NUMBER",formObject.getNGValue("MHT_TXTGST_NUMBER"),"onlyAlphaNumeric","GST Number")==0)
					return 0;
					/*String str_gst_no=formObject.getNGValue("MHT_TXTGST_NUMBER");
					if(str_gst_no.length()!=15)
					{	
						JOptionPane.showMessageDialog(null," GST Number length should be  15.");				
						formObject.NGFocus("MHT_TXTGST_NUMBER");
						return 0;			
					}*/
				}
				/******End MHT CR-8127-95325-GST-Omniflow development******/

				//end FRAME_BANK_BRANCH_NAME
				/*********************** End MHT-PID Process Integration ****************************/		
				//start FRAME_ISEMP_CODE
				else if((wsName.equals("BSG_DataEntry") ||wsName.equals("BSG_DataEntry_QC")|| wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception")) && (formObject.isNGEnable("FRAME_ISEMP_CODE")) && (validateType("MHT_eMPCODE_CSO",formObject.getNGValue("MHT_eMPCODE_CSO"),"emptyCheck","I&S Emp Code /Branch Staff Emp Code/CAM/CSO/NR ASM/MAVAS SE/SM/SEG CVRE/CVRM/WRE/WRM")==0))
					return 0;
				//end FRAME_ISEMP_CODE
				
				//start FRAME_SP_CODE
				else if((wsName.equals("BSG_DataEntry") ||wsName.equals("BSG_DataEntry_QC")|| wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception")) && (formObject.isNGEnable("FRAME_BANK_BRANCH_NAME")) && (validateType("MHT_sP_CODE",formObject.getNGValue("MHT_sP_CODE"),"emptyCheck","SP Code")==0))
					return 0;
				//end FRAME_SP_CODE
				
				//start FRAME_CHANL_EMP
				else if((wsName.equals("BSG_DataEntry") ||wsName.equals("BSG_DataEntry_QC")|| wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception")) && (formObject.isNGEnable("FRAME_CHANL_EMP") && formObject.isNGEnable("MHT_cHANNEL_EMP_INFO")) &&  (validateType("MHT_cHANNEL_EMP_INFO",formObject.getNGValue("MHT_cHANNEL_EMP_INFO"),"emptyCheck","Channel Emp Info")==0))
					return 0;
					
				else if((wsName.equals("BSG_DataEntry") ||wsName.equals("BSG_DataEntry_QC")|| wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception")) && (formObject.isNGEnable("FRAME_CHANL_EMP") && formObject.isNGEnable("MHT_bRANCH_ID_UBO_NAME")) && (validateType("MHT_bRANCH_ID_UBO_NAME",formObject.getNGValue("MHT_bRANCH_ID_UBO_NAME"),"emptyCheck","Branch ID /UBO Name")==0))
					return 0;
				//end FRAME_CHANL_EMP
				
				//start FRAME_BSM
				else if((wsName.equals("BSG_DataEntry") ||wsName.equals("BSG_DataEntry_QC")|| wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception")) && (formObject.isNGEnable("FRAME_BSM") && formObject.isNGEnable("MHT_bSM_ID")) && (validateType("MHT_bSM_ID",formObject.getNGValue("MHT_bSM_ID"),"emptyCheck","BSM ID")==0))
					return 0;
					
				else if((wsName.equals("BSG_DataEntry") ||wsName.equals("BSG_DataEntry_QC")|| wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception")) && (formObject.isNGEnable("FRAME_BSM") && formObject.isNGEnable("MHT_bCM_ID")) && (validateType("MHT_bCM_ID",formObject.getNGValue("MHT_bCM_ID"),"emptyCheck","BCM ID")==0))
					return 0;
					
				else if((wsName.equals("BSG_DataEntry") ||wsName.equals("BSG_DataEntry_QC")|| wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception")) && (formObject.isNGEnable("FRAME_BSM") && formObject.isNGEnable("MHT_rO_DSA_COUNSELOR_ID")) && (validateType("MHT_rO_DSA_COUNSELOR_ID",formObject.getNGValue("MHT_rO_DSA_COUNSELOR_ID"),"emptyCheck","RO/DSA/Counselor ID")==0))
					return 0;
				//end FRAME_BSM
				
				//start FRAME_CENTER_CODE
				else if((wsName.equals("BSG_DataEntry") ||wsName.equals("BSG_DataEntry_QC")|| wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception")) && (formObject.isNGEnable("FRAME_CENTER_CODE") && formObject.isNGEnable("MHT_cENTER_CODE_NAME")) && (validateType("MHT_cENTER_CODE_NAME",formObject.getNGValue("MHT_cENTER_CODE_NAME"),"emptyCheck","Center Code Name")==0))
					return 0;
					
				else if((wsName.equals("BSG_DataEntry") ||wsName.equals("BSG_DataEntry_QC")|| wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception")) && (formObject.isNGEnable("FRAME_CENTER_CODE") && formObject.isNGEnable("MHT_rM_EMPLOYEE")) && (validateType("MHT_rM_EMPLOYEE",formObject.getNGValue("MHT_rM_EMPLOYEE"),"emptyCheck","RM Employee")==0))
					return 0;
				//end FRAME_CENTER_CODE
							
				//start FRAME_BSGQC_DETAILS			
				else if((wsName.equals("BSG_DataEntry_QC")) && (formObject.isNGEnable("FRAME_BSGQC_DETAILS") && exceptionStatus.equalsIgnoreCase("") && formObject.getNGValue("MHT_DISCARD").equalsIgnoreCase("false")) && (validateType("MHT_tYPE_OF_POLICY",formObject.getNGValue("MHT_tYPE_OF_POLICY"),"emptyCheck","Type Of Policy")==0))
					return 0;
				//end FRAME_BSGQC_DETAILS
				
				//start FRAME_OPEN_CNDETAILS			
				else if(wsName.equals("BSG_OpenCN") && (formObject.isNGEnable("FRAME_OPEN_CNDETAILS")) && (validateType("MHT_eNGINE_NUMBER",formObject.getNGValue("MHT_eNGINE_NUMBER"),"emptyCheck","Engine Number")==0))
					return 0;
					
				else if(wsName.equals("BSG_OpenCN") && (formObject.isNGEnable("FRAME_OPEN_CNDETAILS")) && (validateType("MHT_cHASIS_NUMBER",formObject.getNGValue("MHT_cHASIS_NUMBER"),"emptyCheck","Chasis Number")==0))
					return 0;
				//end FRAME_OPEN_CNDETAILS
			
				//start FRAME_COPS_DETAILS
				/*************************** MHT-PID Process Integration ****************************/			
				else if((wsName.equals("COPS_Team") || wsName.equalsIgnoreCase("COPS_Endorsement") || wsName.equals("COPS_QC") ||  wsName.equals("COPS_Priority") || wsName.equals("COPS_OPEN_CN") || wsName.equals("COPS_Endorsement")) && (formObject.isNGEnable("FRAME_COPS_DETAILS") && formObject.getNGValue("MHT_REJECT_TO_IT").equalsIgnoreCase("false") && exceptionStatus.equalsIgnoreCase("") && formObject.getNGValue("MHT_PAS_SYSTEM").equalsIgnoreCase("Pathfinder")) && (validateType("MHT_pATHFINDER_PROPOSAL_NO",formObject.getNGValue("MHT_pATHFINDER_PROPOSAL_NO"),"emptyCheck","Pathfinder Proposal No.")==0))
					return 0;
				
				else if((wsName.equals("COPS_Team") || wsName.equalsIgnoreCase("COPS_Endorsement") || wsName.equals("COPS_QC") || wsName.equals("COPS_Priority") || wsName.equals("COPS_OPEN_CN") || wsName.equals("COPS_Endorsement")) && (formObject.isNGEnable("FRAME_COPS_DETAILS") && formObject.getNGValue("MHT_REJECT_TO_IT").equalsIgnoreCase("false") && exceptionStatus.equalsIgnoreCase("") && formObject.getNGValue("MHT_PAS_SYSTEM").equalsIgnoreCase("OM")) && (validateType("MHT_OM_POLICY_NO",formObject.getNGValue("MHT_OM_POLICY_NO"),"emptyCheck","OM Policy No.")==0))
					return 0;
				else if((wsName.equals("COPS_Team") || wsName.equalsIgnoreCase("COPS_Endorsement") || wsName.equals("COPS_QC") || wsName.equals("COPS_Priority") || wsName.equals("COPS_OPEN_CN") || wsName.equals("COPS_Endorsement")) && (formObject.isNGEnable("FRAME_COPS_DETAILS") && formObject.getNGValue("MHT_REJECT_TO_IT").equalsIgnoreCase("false") && exceptionStatus.equalsIgnoreCase("") && formObject.getNGValue("MHT_PAS_SYSTEM").equalsIgnoreCase("Pathfinder")) && (duplicateCheck("MHT_pATHFINDER_PROPOSAL_NO",formObject.getNGValue("MHT_pATHFINDER_PROPOSAL_NO"))==0))
					return 0;
				else if((wsName.equals("COPS_Team") || wsName.equalsIgnoreCase("COPS_Endorsement") || wsName.equals("COPS_QC") || wsName.equals("COPS_Priority") || wsName.equals("COPS_OPEN_CN") || wsName.equals("COPS_Endorsement")) && (formObject.isNGEnable("FRAME_COPS_DETAILS") && formObject.getNGValue("MHT_REJECT_TO_IT").equalsIgnoreCase("false") && exceptionStatus.equalsIgnoreCase("") && formObject.getNGValue("MHT_PAS_SYSTEM").equalsIgnoreCase("OM")) && (duplicateCheck("MHT_OM_POLICY_NO",formObject.getNGValue("MHT_OM_POLICY_NO"))==0))
					return 0;
				/************************* End MHT-PID Process Integration **************************/
				//end FRAME_COPS_DETAILS
				
				/**************************** CR-8127-83510 : Start MHT/PID Enhancement Development ***************************/
				else if((wsName.equals("COPS_MAT")) && (validateType("MHT_pATHFINDER_PROPOSAL_NO",formObject.getNGValue("MHT_pATHFINDER_PROPOSAL_NO"),"emptyCheck","Pathfinder Proposal No.")==0))
					return 0;
					
				else if((wsName.equals("COPS_MAT")) && (validateType("MHT_pATHFINDER_PROPOSAL_NO",formObject.getNGValue("MHT_pATHFINDER_PROPOSAL_NO"),"OnlyNumeric","Pathfinder Proposal No.")==0))
				return 0;
					
				else if((wsName.equals("COPS_Team") || wsName.equalsIgnoreCase("COPS_Endorsement") || wsName.equals("COPS_QC") ||  wsName.equals("COPS_Priority") || wsName.equals("COPS_OPEN_CN") || wsName.equals("COPS_Endorsement")) && (formObject.isNGEnable("FRAME_COPS_DETAILS") && formObject.getNGValue("MHT_REJECT_TO_IT").equalsIgnoreCase("false") && exceptionStatus.equalsIgnoreCase("") && formObject.getNGValue("MHT_PAS_SYSTEM").equalsIgnoreCase("Pathfinder")) && (validateType("MHT_pATHFINDER_PROPOSAL_NO",formObject.getNGValue("MHT_pATHFINDER_PROPOSAL_NO"),"OnlyNumeric","Pathfinder Proposal No.")==0))
				return 0;
	
				/**************************** CR-8127-83510 : End MHT/PID Enhancement Development *****************************/
				
				//start FRAME_REJECT_TO_IT addition of new workstep			
				else if((wsName.equals("COPS_Team") || wsName.equalsIgnoreCase("COPS_Endorsement") || wsName.equals("COPS_Priority") || wsName.equals("COPS_OpenCN") || wsName.equals("COPS_IPartner") || wsName.equals("COPS_Cust_Id") || wsName.equals("COPS_CustId_Priority") || wsName.equals("COPS_Calculator") || wsName.equals("COPS_MAT") || wsName.equals("COPS_QC")) && (formObject.isNGEnable("FRAME_REJECT_TO_IT")) && (formObject.getNGValue("MHT_rEJECT_TO_IT").equalsIgnoreCase("true")) && (validateType("MHT_sAMADHAN_ID",formObject.getNGValue("MHT_sAMADHAN_ID"),"emptyCheck","Samadhan ID")==0))
					return 0;
				//end FRAME_REJECT_TO_IT
				
				//start FRAME_DISCARD 
				//Adding Cops_Workstep 	for discard		
				else if((wsName.equals("BSG_Exception") || wsName.equals("BSG_DataEntry_QC") || wsName.equals("IPartner_Global") || wsName.equalsIgnoreCase("COPS_Team") || wsName.equalsIgnoreCase("COPS_Priority") || wsName.equalsIgnoreCase("COPS_IPartner") || wsName.equalsIgnoreCase("COPS_Cust_Id") || wsName.equalsIgnoreCase("COPS_CustId_Priority") || wsName.equalsIgnoreCase("COPS_Calculator") || wsName.equalsIgnoreCase("COPS_Endorsement") || wsName.equalsIgnoreCase("COPS_MAT") || wsName.equalsIgnoreCase("COPS_OpenCN") || wsName.equalsIgnoreCase("COPS_QC")) && (formObject.isNGEnable("FRAME_DISCARD")) && (formObject.getNGValue("MHT_dISCARD").equalsIgnoreCase("true")) && (validateType("MHT_dISCARD_REASON",formObject.getNGValue("MHT_dISCARD_REASON"),"emptyCheck","Discard Reason")==0))				
				return 0;
				//end FRAME_DISCARD 
				// Document Upload 
				/*else if((wsName.equals("BSG_DataEntry_QC") || wsName.equals("BSG_Endorsement")) && !formObject.getNGValue("MHT_DISCARD").equalsIgnoreCase("true") && (validateType("IS_DOC_UPLOADED",formObject.getNGValue("MHT_IS_DOC_UPLOADED"),"customValCheck","Kindly Upload At Least One Document")==0))
					return 0; */			
					
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC")) && (formObject.isNGEnable("FRAME_MISC"))  && validateType("MHT_POLICY_PID_START_DATE",formObject.getNGValue("MHT_POLICY_PID_START_DATE"),"emptyCheck","Policy Start Date")==0)
				return 0; //MHT-PID CR-8127-59721 enhancemnet additional CR
				
				/*cust id numeric and min length validation*/
				
				else if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC") || wsName.equalsIgnoreCase("COPS_Cust_Id") || wsName.equalsIgnoreCase("COPS_CustId_Priority")) && (!formObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER")) && (validateType("MHT_CUSTOMER_ID",formObject.getNGValue("MHT_CUSTOMER_ID"),"OnlyNumeric","CUSTOMER ID")==0))
				return 0;
				
				/**************************** CR-8127-83510 : Start MHT/PID Enhancement Development ***************************/
				else if((wsName.equals("BSG_DataEntry")) && (formObject.isNGEnable("FRAME_SALESMGR_EMPID") && formObject.getNGValue("MHT_SM_ID") != null) && (autoLockCheck("MHT_SM_ID",formObject.getNGValue("MHT_SM_ID"))==0 || autoLockCheck("SM_ID_BSG",formObject.getNGValue("MHT_SM_ID"))==0))
				return 0;
				
				else if((wsName.equals("IPartner_Global")) && (formObject.getNGValue("MHT_SM_ID") != null) && (autoLockCheck("MHT_SM_ID",formObject.getNGValue("MHT_SM_ID"))==0 || autoLockCheck("SM_ID_BSG",formObject.getNGValue("MHT_SM_ID"))==0))
				return 0;
				/**************************** CR-8127-83510 : End MHT/PID Enhancement Development *****************************/
				
				/*int xx=validateType("MHT_CUSTOMER_ID",formObject.getNGValue("MHT_CUSTOMER_ID"),"OnlyNumeric","CUSTOMER ID");
				//System.out.println("XX"+xx);
				
				if(xx==0)
				{ //System.out.println("IN CUST ID NUMERIC VALIDATION");
				  return 0;
				} */
				String Test_custid=formObject.getNGValue("MHT_CUSTOMER_ID");
				//System.out.println("TESTCUST_ID"+Test_custid);
				//System.out.println("case category"+formObject.getNGValue("MHT_CASE_CATEGORY"));
				//System.out.println("wsName-===="+wsName);

				
				if((wsName.equals("BSG_DataEntry") || wsName.equals("BSG_DataEntry_QC") || wsName.equalsIgnoreCase("COPS_Cust_Id") || wsName.equalsIgnoreCase("COPS_CustId_Priority")) && (!formObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER")) && (Test_custid.length()!=0 && Test_custid.length()<12))
				{
					//System.out.println("CUSTOMER_ID length is  "+Test_custid.length());
					JOptionPane.showMessageDialog(null, "Min length of Customer ID is 12 ");
					formObject.NGFocus("MHT_CUSTOMER_ID");
					return 0;
				}
				/*End cust id numeric and min length validation	*/
				
				/**************************** CR-8127-83510 : Start MHT/PID Enhancement Development ***************************/
				String Proposal_Number=formObject.getNGValue("MHT_PATHFINDER_PROPOSAL_NO");
				System.out.println("Checking For Proposal_Number Validation : CR-8127-83510 : MHT/PID Enhancement Development");

				if((wsName.equals("COPS_Team") || wsName.equals("COPS_Priority") || wsName.equalsIgnoreCase("COPS_QC") || wsName.equalsIgnoreCase("COPS_MAT")) && (Proposal_Number.length()!=0 && Proposal_Number.length()<10))
				{
					System.out.println("CR-8127-83510 : MHT/PID Enhancement Development.. Proposal_Number is =="+Proposal_Number);
					System.out.println("CR-8127-83510 : MHT/PID Enhancement Development.. Proposal_Number length is =="+Proposal_Number.length());
					JOptionPane.showMessageDialog(null, "Pathfinder Proposal No Should Be 10 Digits");
					formObject.NGFocus("MHT_PATHFINDER_PROPOSAL_NO");
					return 0;
				}
				/**************************** CR-8127-83510 : End MHT/PID Enhancement Development *****************************/
				
				/**************************** CR-8127-83510 : Start MHT/PID Enhancement Development ***************************/
				if((wsName.equals("BSG_DataEntry")) && (formObject.getNGValue("MHT_SM_ID") != null))
				{
					try 
					{
						String sQuery = "";
						String sQuery1 = "";
						String sm_id;
						String sUserName="";
						
						System.out.println("Setting SM_Email_ID And DataEntryUser EmailId Value To Database==:");
						sm_id = formObject.getNGValue("MHT_SM_ID");
						sUserName = formObject.getUserName();
						System.out.println("CR-8127-83510 : DataEntry Username Is ==:" + sUserName);
						System.out.println("CR-8127-83510 : SM ID Value Is ==:"+formObject.getNGValue("MHT_SM_ID"));
						
						XMLParser generalDataParser1 = new XMLParser();
						generalDataParser1.setInputXML(formObject.getWFGeneralData());
						sQuery="select emailid1 from NG_MHT_RM_ESCALATION_DETAILS where empid1='"+sm_id+"'";	
						sQuery1="select emailid1 from NG_MHT_BSG_ESCALATION_DETAILS where empid1='"+sUserName+"'";
						System.out.println("CR-8127-83510 : SM EmailID sQuery Is ==:" + sQuery);
						System.out.println("CR-8127-83510 : DataEntryUser EmailID Is ==:" + sQuery1);
						
						ArrayList smidEmail = formObject.getNGDataFromDataSource(sQuery, 1);
						ArrayList deuserEmail = formObject.getNGDataFromDataSource(sQuery1, 1);
						System.out.println("CR-8127-83510 : Corresponding SM_Email_ID For Mentioned SM_ID Is :: " + smidEmail);
						System.out.println("CR-8127-83510 : Corresponding DataEntryEmailID For Mentioned User Is :: " + deuserEmail);
						if (smidEmail != null) 
						{
							for (int i = 0; i < smidEmail.size(); i++) 
							{
								String smEmail = (smidEmail.get(i)).toString();
								smEmail = smEmail.substring((smEmail.indexOf("[") + 1), (smEmail.indexOf("]")));
								System.out.println("CR-8127-83510 : SM_Email_ID IS ::" + smEmail);
								formObject.setNGValue("MHT_SM_EMAIL_ID",smEmail);
							}
						}
						if (deuserEmail != null) 
						{
							for (int j = 0; j < deuserEmail.size(); j++) 
							{
								String deEmial = (deuserEmail.get(j)).toString();
								deEmial = deEmial.substring((deEmial.indexOf("[") + 1), (deEmial.indexOf("]")));
								System.out.println("CR-8127-83510 : DataEntry Email_ID IS ::" + deEmial);
								formObject.setNGValue("MHT_DATAENTRY_EMAIL_ID",deEmial);
							}
						}							
					}
					catch (NullPointerException nex)
					{
						JOptionPane.showMessageDialog(null,"There was some error in fetching data: NullPointerException");
						System.out.println("Inside Catch Null Pointer Exception : " + nex);
						nex.printStackTrace();
					} 
					catch (Exception ex) 
					{
						JOptionPane.showMessageDialog(null,"There was some error in fetching data: Exception");
						System.out.println("Inside Catch Exception : " + ex);
						ex.printStackTrace();
					}
				}
				/**************************** CR-8127-83510 : End MHT/PID Enhancement Development *****************************/
			}
		} 
		return 1;
	}

    /**
     *
     */
    public void validateTextControl(String fieldName,String value)
    {
        String wsName=formObject.getWFActivityName();
		if((wsName.equals("BSG_DataEntry")||wsName.equals("BSG_DataEntry_QC")) && (value.length()>1) && (formObject.isNGEnable(fieldName)))
		{
			if(fieldName.equals("MHTSEARCH_STRING") )
			{
				int xx=validateType(fieldName,value,"IsAlphaNumeric","Search Keyword");
			}
			if(fieldName.equals("MHT_pAYMENT_TYPE1_NO")  )
			{
				int xx=validateType(fieldName,value,"OnlyNumeric","InstrumentNo/Authcode/CDBGNo");
			}
			if(fieldName.equals("MHT_pAYMENT_TYPE2_NO")  )
			{
				int xx=validateType(fieldName,value,"OnlyNumeric","InstrumentNo/Authcode/CDBGNo");
			}
			if(fieldName.equals("MHT_pAYMENT_TYPE3_NO")  )
			{
				int xx=validateType(fieldName,value,"OnlyNumeric","InstrumentNo/Authcode/CDBGNo");
			}
			if(fieldName.equals("MHT_pAYMENT_TYPE1_AMOUNT")  )
			{
				int xx=validateType(fieldName,value,"OnlyNumeric","Instrument/FT/CC/Cash Amount");
			}
			if(fieldName.equals("MHT_pAYMENT_TYPE2_AMOUNT")  )
			{
				int xx=validateType(fieldName,value,"OnlyNumeric","Instrument/FT/CC/Cash Amount");
			}
			if(fieldName.equals("MHT_pAYMENT_TYPE3_AMOUNT")  )
			{
				int xx=validateType(fieldName,value,"OnlyNumeric","Instrument/FT/CC/Cash Amount");
			}
			if(fieldName.equals("MHT_pAYMENT_TYPE1_EXPIRYDATE")  )
			{
				int xx=validateType(fieldName,value,"dateFormat(dd/mm/yyyy)","CC Expiry Date");
			}
			if(fieldName.equals("MHT_pAYMENT_TYPE2_EXPIRYDATE")  )
			{
				int xx=validateType(fieldName,value,"dateFormat(dd/mm/yyyy)","CC Expiry Date");
			}
			if(fieldName.equals("MHT_pAYMENT_TYPE3_EXPIRYDATE")  )
			{
				int xx=validateType(fieldName,value,"dateFormat(dd/mm/yyyy)","CC Expiry Date");
			}
			if(fieldName.equals("MHT_pAYMENT_TYPE1_DATE")  )
			{
				int xx=validateType(fieldName,value,"dateFormat(dd/mm/yyyy)","Instrument/FT/Auth/CashReceived date");
			}
			if(fieldName.equals("MHT_pAYMENT_TYPE2_DATE")  )
			{
				int xx=validateType(fieldName,value,"dateFormat(dd/mm/yyyy)","Instrument/FT/Auth/CashReceived date");
			}
			if(fieldName.equals("MHT_pAYMENT_TYPE2_DATE")  )
			{
				int xx=validateType(fieldName,value,"dateFormat(dd/mm/yyyy)","Instrument/FT/Auth/CashReceived date");
			}
			if(fieldName.equals("MHT_pF_PAYMENT_ID_NO")  )
			{
				int xx=validateType(fieldName,value,"IsAlphaNumeric","Payment ID1 No.(Pathfinder)");
			}
			if(fieldName.equals("MHT_sM_ID")  )
			{
				int xx=validateType(fieldName,value,"IsAlphaNumeric","SM ID/Emp ID");
			}
			
			if(fieldName.equals("MHT_pREMIUM_AMOUNT"))
			{
				int xx=validateType(fieldName,value,"OnlyNumeric","Total Premium");
				//System.out.println("xx==="+xx);
				/*************************** MHT-PID Process Integration ****************************/
				/*if(xx==1)
				{
					int premium_amt=Integer.parseInt(value);
					//System.out.println("premium_amt==="+premium_amt);
					if(premium_amt>100000)
					{
						//JOptionPane.showMessageDialog(null,"PAN No is enabled !!");
						formObject.setNGEnable("MHT_PAN_CARD_NO",true);
					}
					else
					{
						formObject.setNGValue("MHT_PAN_CARD_NO","");
						formObject.setNGEnable("MHT_PAN_CARD_NO",false);
					
					}
				}*/
				/************************* End MHT-PID Process Integration **************************/
			}
			
        }
        
    }


    /**
     *
     */
    public int validateType(String fieldName,String value,String validationType,String displayName)
	{
		String submittedValue = value;
		String Type = "";
		if(validationType.equals("emptyCheck"))
		{
			//System.out.println("inside validateType-->value: " + value);
			if(value == "" || value==null || value.length()==0 || value.equals("--Select--"))
			{
				JOptionPane.showMessageDialog(null," "+displayName+" can not be left blank. ");
				//System.out.println("inside validateType: satish");
				//formObject.NGClear(fieldName);
				formObject.NGFocus(fieldName);
				return 0;
			}
		}

		/****************************** CR 24 HSP product by satish ******************************/
		if(validationType.equals("customValCheck"))
		{
			//System.out.println("inside validateType-->value: " + value);
			if(value == "" || value==null || value.length()==0 || value.equals("--Select--"))
			{
				JOptionPane.showMessageDialog(null,displayName);
				//System.out.println("inside validateType: satish");				
				if(fieldName.equalsIgnoreCase("IS_DOC_UPLOADED"))
				{
					//System.out.println("Focus on BTN_DOCUPLOAD: " + fieldName);
					formObject.NGFocus("BTN_DOCUPLOAD");
				}
				return 0;
			}
		}
		if(validationType.equals("lengthCheck"))
		{
			//System.out.println("inside validateType-->value: " + value);
			if(value.length()<=15 || value.length()>=20)
			{
				JOptionPane.showMessageDialog(null," "+displayName+" length should be between 15-20. ");
				//System.out.println("inside validateType: satish");
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
		////System.out.println("Pattern p" + p);
		Matcher m = p.matcher(submittedValue.toString());
		////System.out.println("Matcher m " + m);
		boolean bInvalid = m.matches();
		////System.out.println("The valid  :=:  " + bInvalid);
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


    /**
     *
     */
    //method to check if the string used is empty
	public int emptyCheckValidation(String fieldName,String fieldValue,String validationType,String displayName)
	{		////System.out.println("Inside empty check  ");
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
	/*************************** MHT-PID Process Integration ****************************/
	/*public void showPage(String url, String type) {
        //System.out.println("Entering showPage at : ");

        str[0] = url;
        if (type.equalsIgnoreCase("addfolder")) {
            obj1 = js1.call("OpenDocUpload_MHT", str);
        } 

        //System.out.println("Exiting showPage at : ");
    }*/
	
	public int duplicateCheck(String FiledName, String ProposalPolicyNo)
	{
		int countValue = 0;
		String errMsg = "";
        try {
            //System.out.println("Inside duplicateCheck: ");
            //System.out.println("ProposalPolicyNo: " + ProposalPolicyNo);
			XMLParser generalDataParser = new XMLParser();
			generalDataParser.setInputXML(formObject.getWFGeneralData());
            String c_ProcessId = generalDataParser.getValueOf("ProcessInstanceId");
			String sQuery = "";
			/***********************Start CR Motor,Home & Travel processing*************/
			if(FiledName.equalsIgnoreCase("MHT_pATHFINDER_PROPOSAL_NO"))
			{
				sQuery="select wi_name from ng_lombard_mht_ext  where PATHFINDER_PROPOSAL_NO='"+ProposalPolicyNo+"'  and  CURRENT_WORKSTEP not in ('Discard_Exit')";
				errMsg = "Pathfinder Proposal No already exists";
			}
			else if(FiledName.equalsIgnoreCase("MHT_OM_POLICY_NO"))
			{
				sQuery="select wi_name from ng_lombard_mht_ext  where OM_POLICY_NO='"+ProposalPolicyNo+"'  and  CURRENT_WORKSTEP not in ('Discard_Exit')";
				errMsg = "Policy No already exists";
			}
			else if(FiledName.equalsIgnoreCase("MHT_MANUAL_COVERNOTE_NO"))
			{
				System.out.println("Inside duplicateCheck Function Checking For Duplication of Manual CoverNote : CR-8127-83510 : MHT/PID Enhancement Development");
				sQuery="select wi_name from ng_lombard_mht_ext  where MANUAL_COVERNOTE_NO='"+ProposalPolicyNo+"'  and  CURRENT_WORKSTEP not in ('Discard_Exit')";
				errMsg = "Manual Cover Note No already exists";
			}		
			/***********************End CR Motor,Home & Travel processing***************/
			//System.out.println("duplicateCheck sQuery:" + sQuery);
            ArrayList getPid = formObject.getNGDataFromDataSource(sQuery, 1);
            if (getPid != null) {
                for (int i = 0; i < getPid.size(); i++) {
                    String s_pid = (getPid.get(i)).toString();
                    s_pid = s_pid.substring((s_pid.indexOf("[") + 1), (s_pid.indexOf("]")));
                    //System.out.println("wi_name ::" + i + "\t" + s_pid);
                    if (!s_pid.equalsIgnoreCase(c_ProcessId)) {
                        ++countValue;
                    }
                }
            }
            //System.out.println("duplicate countValue: " + countValue);
        } catch (NullPointerException nex) {
            JOptionPane.showMessageDialog(null,"There was some error in fetching data: NullPointerException");
			//System.out.println("duplicateCheck nex bError : " + nex);
            nex.printStackTrace();
        } catch (Exception ex) {
			JOptionPane.showMessageDialog(null,"There was some error in fetching data: Exception");
			//System.out.println("duplicateCheck ex bError : " + ex);
            ex.printStackTrace();
        }
        if (countValue > 0)
		{
			JOptionPane.showMessageDialog(null,errMsg+" already exists");
			formObject.setNGValue(FiledName,"");
			return 0;
		}
		else
			return 1;
	}
	/************************* End MHT-PID Process Integration **************************/
	
	/**************************** CR-8127-83510 : Start MHT/PID Enhancement Development ***************************/	
	public int autoLockCheck(String FieldName, String SMIDValue)
	{
		int countValue = 0;
		String errMsg = "";
        try 
		{
            System.out.println("Inside autoLockCheck Function : CR-8127-83510 : MHT/PID Enhancement Development");
            System.out.println("Inside autoLockCheck Function Checking Value of SMIDValue Is ==:" + SMIDValue);
			System.out.println("Inside autoLockCheck Function Checking Value of CallTagNumber ==:"+formObject.getNGValue("MHT_CALL_TAG_NUMBER"));
			
			XMLParser generalDataParser = new XMLParser();
			generalDataParser.setInputXML(formObject.getWFGeneralData());
			String sQuery = "";
				
			if(FieldName.equalsIgnoreCase("MHT_SM_ID") && formObject.getNGValue("MHT_CALL_TAG_NUMBER").equalsIgnoreCase(""))
			{
				System.out.println("User Have Not Entetred MHT_CALL_TAG_NUMBER So Autolock Enabled For RM Bucket : CR-8127-83510 : MHT/PID Enhancement Development");
				sQuery="select SM_ID from MV_MHT_RM_AUTOLOCK where SM_ID='"+SMIDValue+"'";
				errMsg = "SMID Is Locked.Please Clear The Cases From RM_Exception";
			}
			else if(FieldName.equalsIgnoreCase("SM_ID_BSG") && formObject.getNGValue("MHT_CALL_TAG_NUMBER").equalsIgnoreCase(""))
			{
				System.out.println("User Have Not Entetred MHT_CALL_TAG_NUMBER So Autolock Enabled For BSG Bucket : CR-8127-83510 : MHT/PID Enhancement Development");
				sQuery="select SM_ID from MV_MHT_BSG_AUTOLOCK where SM_ID='"+SMIDValue+"'";
				errMsg = "SMID Is Locked.Please Clear The Cases From BSG_Exception";
			}
			else
			{
				System.out.println("User Have Entetred MHT_CALL_TAG_NUMBER So Autolock Is Not Enabled : CR-8127-83510 : MHT/PID Enhancement Development");
			}
			
			System.out.println("autoLockCheck sQuery Is : CR-8127-83510 : MHT/PID Enhancement Development :" + sQuery);
            ArrayList getPid = formObject.getNGDataFromDataSource(sQuery, 1);
			System.out.println("UserIDs After AutoLock Function : CR-8127-83510 : MHT/PID Enhancement Development : " + getPid);
            if (getPid != null) 
			{
				for (int i = 0; i < getPid.size(); i++) 
				{
                    ++countValue;
                }
            }
            System.out.println("autoLockCheck countValue IS : CR-8127-83510 : MHT/PID Enhancement Development :" + countValue);
        } 
		catch (NullPointerException nex)
		{
            JOptionPane.showMessageDialog(null,"There was some error in fetching data: NullPointerException");
			System.out.println("Inside Catch autoLockCheck Null Pointer Exception : CR-8127-83510 : MHT/PID Enhancement Development : " + nex);
            nex.printStackTrace();
        } 
		catch (Exception ex) 
		{
			JOptionPane.showMessageDialog(null,"There was some error in fetching data: Exception");
			System.out.println("Inside Catch autoLockCheck Exception : CR-8127-83510 : MHT/PID Enhancement Development : " + ex);
            ex.printStackTrace();
        }
        if (countValue > 0)
		{
			JOptionPane.showMessageDialog(null,errMsg+"..");
			return 0;
		}
		else
			return 1;
	}	
	/**************************** CR-8127-83510 : End MHT/PID Enhancement Development *****************************/
	
	/******************************* MHT-PID CR-8127-59721 enhancemnet additional CR ********************************/
	public void exceptionDetails(String currentwrkstp)
	{
			
			  //JSObject js1 = null;
			jsObj = formObject.getJSObject();
			String str[]= new String[1];
			str[0] = currentwrkstp;
			//System.out.println("Start Raising Exception For MHT-PID CR-8127-59721 enhancemnet additional CR At :"+str[0]);
			////System.out.println("Value of js: "+js);
			Object obj = jsObj.call("setExceptionName_MHT", str);
			//System.out.println("After Raising Exception For MHT-PID CR-8127-59721 enhancemnet additional CR");	
			
	}
	/******************************End MHT-PID CR-8127-59721 enhancemnet additional CR ******************************/

}