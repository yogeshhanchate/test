/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.newgen.formApplet.User;

import com.newgen.formApplet.NGPickList;
import com.newgen.formApplet.XMLParser;
import java.applet.AppletContext;
import java.util.*;
import java.util.HashMap;
import javax.swing.JOptionPane;
import netscape.javascript.JSObject;
import java.text.*;

/**
 *
 * @author ILOM09138
 */
public class ICICI_LOMBARD_MHT
{

    NGFPropInterface formObject;
    String[] arrFieldName;
    XMLParser generalDataParser = new XMLParser();
    String wsName =null;
    String wiName =null;
    String fieldName = "";
    String fieldValue = "";
    String noOfCols="";
    String query="";
    int debug=0;  // for enabling/disabling the debug mode.. 0-disable; 1-enable
    NGEjbCalls ngEjbCalls;
    HashMap map = null;
    String inputXml="";
    String outputXml="";
    String result= "";
    WFXmlResponse xmlResponse=null;
    WFXmlList RecordList=null;
    boolean flag;
	JSObject js =null;
	String url =null;
	SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
	DateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.S");

    public ICICI_LOMBARD_MHT(NGFPropInterface formObject)
    {
        this.formObject = formObject;

    }

    /**
     * This method will be executed on form load for every workstep.
     * fieldValueBagSet() of ngfUser represents the executeMHTOnload(-)
     */
    public void executeMHTOnload(XMLParser gDataParser)
    {
        try
        {
            System.out.println("executeMHTOnload():started  ");
			arrFieldName = new String[2];
            generalDataParser.setInputXML(formObject.getWFGeneralData());
            wsName = generalDataParser.getValueOf("ActivityName").toUpperCase();
            wiName = generalDataParser.getValueOf("ProcessInstanceId").toLowerCase();
			
			formObject.setNGLocked("MHT_WorkItemName", false); // applicable to all worksteps
			System.out.println("MHT_TRANSACTION_TYPE satish====== "+formObject.getNGValue("MHT_TRANSACTION_TYPE"));
			
			
			/*************************** MHT-PID Process Integration ****************************/
			if(wsName.equalsIgnoreCase("COPS_Cust_Id") || wsName.equalsIgnoreCase("COPS_CustId_Priority"))
			{
				formObject.setNGEnable("MHT_PID_PAYMENT_TYPE",false);
				formObject.setNGLocked("MHT_PID_PAYMENT_TYPE",false);
				//formObject.setNGEnable("FRAME_MODEOF_PAYMENT",false);
				formObject.setNGLocked("FRAME_MODEOF_PAYMENT", false);
			}
			/*if(wsName.equalsIgnoreCase("BSG_MAT") || wsName.equalsIgnoreCase("COPS_MAT"))
			{
				formObject.setNGListIndex("MHT_PID_PAYMENT_TYPE",2);
				formObject.setNGEnable("MHT_PID_PAYMENT_TYPE",false);
				formObject.setNGLocked("MHT_PID_PAYMENT_TYPE",false);
				formObject.setNGEnable("FRAME_MODEOF_PAYMENT",true);
				formObject.setNGLocked("FRAME_MODEOF_PAYMENT", true);
			}	
			if(formObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Endorsement") && (!wsName.equalsIgnoreCase("COPS_Calculator") && !wsName.equalsIgnoreCase("BSG_Endorsement") && !wsName.equalsIgnoreCase("BSG_DATAENTRY") && !wsName.equalsIgnoreCase("BSG_MAT") && !wsName.equalsIgnoreCase("COPS_MAT")))
			{
				//formObject.setNGListIndex("MHT_PID_PAYMENT_TYPE",2);
				formObject.setNGEnable("MHT_PID_PAYMENT_TYPE",false);
				formObject.setNGLocked("MHT_PID_PAYMENT_TYPE",false);
				formObject.setNGEnable("FRAME_MODEOF_PAYMENT",false);
				formObject.setNGLocked("FRAME_MODEOF_PAYMENT", false);
			}*/
			if(wsName.equalsIgnoreCase("BSG_MAT") || wsName.equalsIgnoreCase("COPS_MAT"))
			{
				formObject.setNGListIndex("MHT_PID_PAYMENT_TYPE",2);
				formObject.setNGEnable("MHT_PID_PAYMENT_TYPE",false);
				formObject.setNGLocked("MHT_PID_PAYMENT_TYPE",false);
				formObject.setNGEnable("FRAME_MODEOF_PAYMENT",true);
				formObject.setNGLocked("FRAME_MODEOF_PAYMENT", true);
				formObject.setNGEnable("MHT_pAYMENT_TYPE1",false);
				formObject.setNGEnable("MHT_pAYMENT_TYPE2",false);
				formObject.setNGEnable("MHT_pAYMENT_TYPE3",false);
				if(!formObject.getNGValue("MHT_pAYMENT_TYPE1").equalsIgnoreCase("MAT"))
				{
					enableAllPaymentDetails1();
					formObject.setNGLocked("MHT_PF_PAYMENT_ID_NO",true);
				}
				if(!formObject.getNGValue("MHT_pAYMENT_TYPE2").equalsIgnoreCase("MAT"))
				{
					enableAllPaymentDetails2();
					formObject.setNGLocked("MHT_PF_PAYMENT_ID_NO2",true);
				}
				if(!formObject.getNGValue("MHT_pAYMENT_TYPE3").equalsIgnoreCase("MAT"))
				{
					enableAllPaymentDetails3();
					formObject.setNGLocked("MHT_PF_PAYMENT_ID_NO3",true);
				}
			}
			
			if(wsName.equalsIgnoreCase("COPS_Team") || wsName.equalsIgnoreCase("COPS_Endorsement") || wsName.equalsIgnoreCase("COPS_QC") || wsName.equalsIgnoreCase("COPS_Priority") || wsName.equalsIgnoreCase("COPS_IPartner") || wsName.equalsIgnoreCase("COPS_Endorsement"))
			{
				String pasSystemValue = formObject.getNGValue("MHT_PAS_SYSTEM");
				if(pasSystemValue != null && !pasSystemValue.equalsIgnoreCase("--Select--"))
				{
					if(pasSystemValue.equalsIgnoreCase("Pathfinder"))
					{
						formObject.setNGEnable("MHT_PATHFINDER_PROPOSAL_NO", true);
						formObject.setNGLocked("MHT_PATHFINDER_PROPOSAL_NO", true);
						formObject.setNGValue("MHT_OM_POLICY_NO","");
						formObject.setNGLocked("MHT_OM_POLICY_NO",false);
					}
					else if(pasSystemValue.equalsIgnoreCase("OM"))
					{
						formObject.setNGEnable("MHT_OM_POLICY_NO", true);
						formObject.setNGLocked("MHT_OM_POLICY_NO", true);
						formObject.setNGValue("MHT_PATHFINDER_PROPOSAL_NO","");
						formObject.setNGLocked("MHT_PATHFINDER_PROPOSAL_NO",false);                    
					}					
				}
			}
			/************************* End MHT-PID Process Integration **************************/
			// exception for cops calculator
			if(wsName.equalsIgnoreCase("BSG_DataEntry_QC") || wsName.equalsIgnoreCase("BSG_Endorsement") || wsName.equalsIgnoreCase("IPartner_Global")) //MHT-PID Phase1 Process Integration
			{
			    
				System.out.println("BSG_DataEntry_QC: exception enabled started");
				if(formObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Policy Issuance"))
				{
					//formObject.setNGEnable("FRAME_ROUTETO", true);
					formObject.NGAddItem("MHT_Route_To","RM_Exception");
				}
				else if(formObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Endorsement"))
				{
					//formObject.setNGEnable("FRAME_ROUTETO", true);
					formObject.NGAddItem("MHT_Route_To","RM_Endorsement");
				}
			}
			/*************************** MHT-PID Process Integration ****************************/
			// Exception for new worksteps
			if(wsName.equalsIgnoreCase("COPS_Team") || wsName.equalsIgnoreCase("COPS_Endorsement") || wsName.equalsIgnoreCase("COPS_Priority") || wsName.equalsIgnoreCase("COPS_IPartner") || wsName.equalsIgnoreCase("COPS_OpenCN") || wsName.equalsIgnoreCase("COPS_Cust_Id") || wsName.equalsIgnoreCase("COPS_CustId_Priority") || wsName.equalsIgnoreCase("COPS_MAT") || wsName.equalsIgnoreCase("COPS_QC") || wsName.equalsIgnoreCase("COPS_Calculator")) //MHT-PID Phase1 Process Integration
			{
				formObject.NGClear("MHT_Route_To");
				System.out.println("COPS_Team: exception enabled started");
				if(formObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Policy Issuance"))
				{	
					formObject.NGAddItem("MHT_Route_To","--Select--");
					formObject.NGAddItem("MHT_Route_To","BSG_Exception");
					formObject.NGAddItem("MHT_Route_To","RM_Exception");
				}
				else if(formObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Endorsement"))
				{	
					formObject.NGAddItem("MHT_Route_To","--Select--");
					formObject.NGAddItem("MHT_Route_To","BSG_Exception");
					formObject.NGAddItem("MHT_Route_To","RM_Endorsement");
				}
				
			}
			
			if(!(wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DataEntry_QC") || wsName.equalsIgnoreCase("BSG_Exception"))) //CR-8127-83510 : MHT/PID Enhancement Development)

            {	
				System.out.println("CR-8127-83510 : Start MHT/PID Enhancement Development : Inside PageOnload Condition");
				formObject.setNGLocked("MHT_CALL_TAG_NUMBER",false);
			}
			/*********************** End MHT-PID Process Integration ****************************/			
			/******************************* MHT-PID CR-8127-59721 enhancemnet additional CR ********************************/
			if(!(wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DataEntry_QC") || wsName.equalsIgnoreCase("RM_Endorsement") || wsName.equalsIgnoreCase("RM_Exception") || wsName.equalsIgnoreCase("BSG_Exception")))
            {	
				System.out.println("MHT_POLICY_PID_START_DATE==");
				formObject.setNGEnable("MHT_POLICY_PID_START_DATE", false);
				//formObject.setNGLocked("MHT_POLICY_PID_START_DATE", false);
			}
			/******************************End MHT-PID CR-8127-59721 enhancemnet additional CR ******************************/
			/******Start MHT CR-8127-95325-GST-Omniflow development******/
			if(wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DataEntry_QC") || wsName.equalsIgnoreCase("BSG_Exception") || wsName.equalsIgnoreCase("RM_Exception"))
			{
				System.out.println("inside if executeMHTOnload MHT CR-8127-95325-GST-Omniflow development");
				formObject.setNGEnable("FRAME_GST_GRID",true);
				if(formObject.getNGValue("MHT_GST_REGISTERED").equalsIgnoreCase("Yes") && !formObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER"))
				{
					System.out.println("inside sub if executeMHTOnload MHT CR-8127-95325-GST-Omniflow development");
					formObject.setNGEnable("MHT_TXTGST_NUMBER",true);
					//formObject.setNGLocked("MHT_TXTGST_NUMBER",false);				
					formObject.setNGEnable("MHT_TXTGST_STATE_NAME",true);
					formObject.setNGLocked("MHT_TXTGST_STATE_NAME",false);
					formObject.setNGEnable("Add_GST",true);
					formObject.setNGEnable("Mod_GST",true);
					formObject.setNGEnable("Del_GST",true);
					formObject.setNGEnable("MHT_qGstGrdVar",true);
				}
			}
			else
			{
				System.out.println("inside disable controls else condition executeMHTOnload MHT CR-8127-95325-GST-Omniflow development");
				formObject.setNGEnable("MHT_GST_REGISTERED",false);
				formObject.setNGEnable("MHT_TXTGST_NUMBER",false);
				formObject.setNGEnable("MHT_TXTGST_STATE_NAME",false);
				formObject.setNGEnable("Add_GST",false);
				formObject.setNGEnable("Mod_GST",false);
				formObject.setNGEnable("Del_GST",false);
				formObject.setNGEnable("MHT_qGstGrdVar",true);
			}			
			/******End MHT CR-8127-95325-GST-Omniflow development******/

            if(wsName.equalsIgnoreCase("BSG_DATAENTRY"))
            {
				System.out.println("BSG_DATAENTRY onload:started  ");
                formObject.setNGEnable("FRAME_CENTER_CODE", false);
                formObject.setNGEnable("FRAME_DISCARD", false);
                formObject.setNGEnable("FRAME_REJECT_TO_IT", false);
                formObject.setNGEnable("FRAME_ROUTETO", false);
                formObject.setNGEnable("FRAME_SOURCE_BUSINESS",false);
				formObject.setNGEnable("FRAME_OPEN_CNDETAILS",false);
				formObject.setNGEnable("FRAME_BSGQC_DETAILS",false);
                formObject.setNGEnable("FRAME_COPS_DETAILS",false);
				formObject.setNGEnable("FRAME_FETCH_COPY",false);
				formObject.setNGEnable("FRAME_PRINT_FORM",false);
				
				formObject.setNGEnable("MHT_PATHFINDER_PROPOSAL_NO",false);
                //formObject.setNGValue("MHT_IPARTNER_PAYMENT_ID_NO","");
                formObject.setNGEnable("MHT_IPARTNER_PAYMENT_ID_NO",false);
                formObject.setNGEnable("MHT_COVER_NOTE_TYPE",false);
				formObject.setNGEnable("MHT_PF_POLICY_NUMBER",false);
				formObject.setNGEnable("MHT_ALTERNATE_POLICY_NUMBER",false);
				formObject.setNGEnable("MHT_PROPOSAL_STATUS",false);
				formObject.setNGEnable("MHT_IPARTNER_PROPOSAL_NO",false);
				formObject.setNGEnable("MHT_FINAL_QUOTE_NO",false);
				/********Start CR-OMHT-1314-02 Omniflow_development for ILLocation Changes******/
				formObject.setNGEnable("MHT_IL_LOCATION_CODE",true);
				formObject.setNGLocked("MHT_IL_LOCATION_CODE",false);
				formObject.setNGEnable("MHT_IL_LOCATION",true);
				formObject.setNGLocked("MHT_IL_LOCATION",false);
				/********End CR-OMHT-1314-02 Omniflow_development for ILLocation Changes*******/
				
				formObject.setNGLocked("MHT_CASE_CATEGORY", false);
				formObject.setNGLocked("MHT_PRIMARY_VERTICAL", false); 
				formObject.setNGLocked("MHT_SUB_VERTICAL", false);
				formObject.setNGLocked("MHT_PRODUCT_NAME", false);
				formObject.setNGLocked("MHT_SM_NAME", false);
				formObject.setNGLocked("MHT_SOURCE_BUSINESS", false);
				formObject.setNGLocked("MHT_CHANNEL_SOURCE", false);
				formObject.setNGLocked("MHT_BANK_BRANCH_NAME", false);
				formObject.setNGLocked("MHT_BRANCH_ID", false);
				formObject.setNGLocked("MHT_SP_NAME", false);
				formObject.setNGLocked("MHT_SP_CODE", false);				
				/***************************Start MHT SP Code CR-8127-69652*************************/
				formObject.setNGLocked("MHT_DEAL_IL_LOCATION",false);
				System.out.println("SP Fields are locked in DataEntry");
				/***************************End MHT SP Code CR-8127-69652*************************/
				formObject.setNGLocked("MHT_PRIVILEGE_BANKER_CODE", false);
				formObject.setNGLocked("MHT_CIF_ID", false);
				formObject.setNGLocked("MHT_BRANCH_ID_UBO_NAME", false);
				formObject.setNGLocked("MHT_COVER_NOTE_TYPE",false);

				/*************************** MHT-PID Process Integration ****************************/
				formObject.setNGLocked("MHT_PAN_CARD_NO",false);
				formObject.setNGEnable("MHT_PAN_CARD_NO",false);
				if(formObject.getNGValue("MHT_PAS_SYSTEM").equalsIgnoreCase(""))
				{
					formObject.setNGValue("MHT_PAS_SYSTEM","Pathfinder");
				}
				formObject.setNGLocked("MHT_PAYMENT_TYPE1",false);
				formObject.setNGLocked("MHT_PAYMENT_TYPE2",false);
				formObject.setNGLocked("MHT_PAYMENT_TYPE3",false);
				if((formObject.getNGValue("MHT_PAYMENT_TYPE1")).equalsIgnoreCase(""))
				{
					setPaymentType1Blank();
				}
				if((formObject.getNGValue("MHT_PAYMENT_TYPE2")).equalsIgnoreCase(""))
				{
					setPaymentType2Blank();
				}
				if((formObject.getNGValue("MHT_PAYMENT_TYPE3")).equalsIgnoreCase(""))
				{
					setPaymentType3Blank();
				}			
				formObject.setNGEnable("MHT_CALL_TAG_NUMBER",true);
				formObject.setNGLocked("MHT_CALL_TAG_NUMBER",true);
				if(formObject.getNGValue("MHT_PAS_SYSTEM") != null && formObject.getNGValue("MHT_PAS_SYSTEM").equalsIgnoreCase("OM"))
				{
					System.out.println("Om case="+formObject.getNGValue("MHT_PAS_SYSTEM"));
					formObject.setNGEnable("MHT_FINAL_QUOTE_NO",false);
					formObject.setNGLocked("MHT_FINAL_QUOTE_NO",false);
				}
				else
				{
					System.out.println("else case Om case="+formObject.getNGValue("MHT_PAS_SYSTEM"));
					formObject.setNGEnable("MHT_FINAL_QUOTE_NO",true);
					formObject.setNGLocked("MHT_FINAL_QUOTE_NO",true);
				}
				/************************* End MHT-PID Process Integration **************************/
                /**************************** CR-8127-83510 : Start MHT/PID Enhancement Development ***************************/
					System.out.println("CR-8127-83510 : Start MHT/PID Enhancement Development : Inside Onload BSG_Dataentry");
					formObject.setNGEnable("FRAME_PRIVILEGE_BANKER_CODE",true);
					formObject.setNGLocked("FRAME_PRIVILEGE_BANKER_CODE",true);
					formObject.setNGEnable("MHT_PRIVILEGE_BANKER_CODE",false);
					formObject.setNGEnable("MHT_CIF_ID",false);
					formObject.setNGEnable("MHT_CALL_TAG_NUMBER",true);
					formObject.setNGLocked("MHT_CALL_TAG_NUMBER",true);
					
				/**************************** CR-8127-83510 : End MHT/PID Enhancement Development *****************************/
				
				formObject.setNGValue("MHT_WI_NAME",formObject.getNGValue("MHT_WorkItemName"));
				System.out.println("CR-8127-83510 : Start MHT/PID Enhancement Development :::: Before IF Condition..."+formObject.getNGValue("MHT_SUB_VERTICAL"));

               // enabling banker code,empcode,source and channel on bbg.branch banking,home,krg2
                if(formObject.getNGValue("MHT_SUB_VERTICAL") != null)
                {
					System.out.println("MHT_SUB_VERTICAL onload:started  ");
					System.out.println("CR-8127-83510 : Start MHT/PID Enhancement Development : Inside IF Condition..."+formObject.getNGValue("MHT_SUB_VERTICAL"));
                    if((formObject.getNGValue("MHT_PRIMARY_VERTICAL") != null && formObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BBG") && formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BBG"))
                         || (formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BBG") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking (BBG)") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") ||
						 formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("KRG")))
                    {
						System.out.println("CR-8127-83510 : Start MHT/PID Enhancement Development : Inside IF Condition KRG");
                        //formObject.setNGEnable("MHT_PRIVILEGE_BANKER_CODE",true); 	due to field is not there in DB
						formObject.setNGEnable("FRAME_ISEMP_CODE",true);
						formObject.setNGEnable("FRAME_SOURCE_BUSINESS",true);
                        formObject.setNGEnable("MHT_SOURCE_BUSINESS",true);
                        formObject.setNGEnable("MHT_CHANNEL_SOURCE",true);
						formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",true);
						//formObject.setNGEnable("FRAME_PRIVILEGE_BANKER_CODE",true);//MHT-PID Process Integration     //commented due to CR-8127-83510 : MHT/PID Enhancement CR
						//formObject.setNGLocked("FRAME_PRIVILEGE_BANKER_CODE",true);//MHT-PID Process Integration	 //commented due to CR-8127-83510 : MHT/PID Enhancement CR
					/**************************** CR-8127-83510 : Start MHT/PID Enhancement Development ***************************/
						formObject.setNGEnable("MHT_PRIVILEGE_BANKER_CODE",true);
						formObject.setNGLocked("MHT_PRIVILEGE_BANKER_CODE",true);
						formObject.setNGEnable("MHT_CIF_ID",true);
						formObject.setNGLocked("MHT_CIF_ID",true);
						/**************************** CR-8127-83510 : End MHT/PID Enhancement Development *****************************/
                        //formObject.setNGEnable("FRAME_SP_CODE",true); //MHT-PID Process Integration
						formObject.setNGEnable("FRAME_CHANL_EMP",false);
                        if(formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("KRG"))
                        {
							System.out.println("CR-8127-83510 : Start MHT/PID Enhancement Development : Inside IF Condition KRG 2");
							/********Start SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI *******************/
                            //formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",false);commented yogesh for sp code logic change
							/*********End SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI ******************/

							//formObject.setNGEnable("FRAME_PRIVILEGE_BANKER_CODE",false);//MHT-PID Process Integration		//commented due to CR-8127-83510 : MHT/PID Enhancement CR
							//formObject.setNGLocked("FRAME_PRIVILEGE_BANKER_CODE",false);//MHT-PID Process Integration		//commented due to CR-8127-83510 : MHT/PID Enhancement CR
                            formObject.setNGEnable("FRAME_CHANL_EMP",true);
                        }
                    }
                    else
                    {
						System.out.println("CR-8127-83510 : Start MHT/PID Enhancement Development : Inside Else Condition MHT_SUB_VERTICAL");
                        formObject.setNGEnable("FRAME_ISEMP_CODE",false);
						formObject.setNGEnable("FRAME_SOURCE_BUSINESS",false);
                        //formObject.setNGEnable("MHT_SOURCE_BUSINESS",false);
						//formObject.setNGEnable("MHT_CHANNEL_SOURCE",false);
						formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",false);
						//formObject.setNGEnable("FRAME_PRIVILEGE_BANKER_CODE",false);//MHT-PID Process Integration		//commented due to CR-8127-83510 : MHT/PID Enhancement CR
						//formObject.setNGLocked("FRAME_PRIVILEGE_BANKER_CODE",false);//MHT-PID Process Integration		//commented due to CR-8127-83510 : MHT/PID Enhancement CR
                        formObject.setNGEnable("FRAME_CHANL_EMP",false);
                        //formObject.setNGEnable("FRAME_SP_CODE",false); //MHT-PID Process Integration
                    }
                    //enabling  disabling the bsm bcm on home sub vertical
                    if(formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("home"))
                    {
							System.out.println("CR-8127-83510 : Start MHT/PID Enhancement Development : Inside MHT_SUB_VERTICAL Condition Home Vaibhav");//CR-8127-83510 : MHT/PID Enhancement Development
                            formObject.setNGEnable("FRAME_BSM",true);
							formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",true);
							//formObject.setNGEnable("FRAME_PRIVILEGE_BANKER_CODE",true);//MHT-PID Process Integration     //commented due to CR-8127-83510 : MHT/PID Enhancement CR
							//formObject.setNGLocked("FRAME_PRIVILEGE_BANKER_CODE",true);//MHT-PID Process Integration     //commented due to CR-8127-83510 : MHT/PID Enhancement CR
						/**************************** CR-8127-83510 : Start MHT/PID Enhancement Development ***************************/
							formObject.setNGEnable("MHT_PRIVILEGE_BANKER_CODE",true);
							formObject.setNGLocked("MHT_PRIVILEGE_BANKER_CODE",true);
							formObject.setNGEnable("MHT_CIF_ID",true);
							formObject.setNGLocked("MHT_CIF_ID",true);
							/**************************** CR-8127-83510 : End MHT/PID Enhancement Development *****************************/
                    }
                    else
                    {
                            formObject.setNGEnable("FRAME_BSM",false);
                    }
					
					if(formObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("Bancassurance") && (formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("KRG")))
					{
						formObject.setNGEnable("FRAME_CHANL_EMP",true);
					}
                }
				/*******************Start SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI**********************/
				if(!((formObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Policy Issuance") && formObject.getNGValue("MHT_TYPE_OF_BUSINESS").equalsIgnoreCase("Direct"))) && (formObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("Bancassurance") || formObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BA")) && (formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("KRG")) && !(formObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER")))
				{
					System.out.println("Inside the disable BRANCH_ID and BANK_BRANCH_NAME: Yogesh");
					formObject.setNGEnable("MHT_BRANCH_ID",false);
					formObject.setNGEnable("MHT_BANK_BRANCH_NAME",false);						
				}
				/*******************End SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI**********************/
                if(!formObject.getNGValue("MHT_TRANSACTION_TYPE").equalsIgnoreCase("--Select--"))
                {
					System.out.println("MHT_TRANSACTION_TYPE onload:started  ");
                    if(formObject.getNGValue("MHT_TRANSACTION_TYPE").equalsIgnoreCase("RENEWAL") || formObject.getNGValue("MHT_TRANSACTION_TYPE").equalsIgnoreCase("ROLL OVER"))
                    {
                        formObject.setNGEnable("MHT_PREVIOUS_POLICY_NO",true);
                    }
                    else
                    {
                        formObject.setNGEnable("MHT_PREVIOUS_POLICY_NO",false);
                    }
                }
                if(formObject.isNGEnable("MHT_SOURCE_BUSINESS") && formObject.getNGValue("MHT_SOURCE_BUSINESS")!=null)
                {
					System.out.println("MHT_SOURCE_BUSINESS onload:started  ");
                    if(formObject.getNGValue("MHT_SOURCE_BUSINESS").equalsIgnoreCase("Center Sales") || formObject.getNGValue("MHT_SOURCE_BUSINESS").equalsIgnoreCase("Centre Sales"))
                    {
                            formObject.setNGEnable("FRAME_CENTER_CODE",true);
                    }
                    else
                    {
                            formObject.setNGEnable("FRAME_CENTER_CODE",false);
                    }
                }
				if(formObject.getNGValue("MHT_TYPE_OF_BUSINESS")!=null)
                {
				
                    if(!formObject.getNGValue("MHT_TYPE_OF_BUSINESS").equalsIgnoreCase("--Select--") && formObject.getNGValue("MHT_TYPE_OF_BUSINESS").equalsIgnoreCase("Direct"))
                    {
							System.out.println("MHT_TYPE_OF_BUSINESS onload:started  ");
                            formObject.setNGEnable("MHT_PRIMARY_VERTICAL",true);
							formObject.setNGEnable("MHT_SUB_VERTICAL",true);
							formObject.setNGEnable("MHT_PRODUCT_NAME",true);
							/********Start CR-OMHT-1314-02 Omniflow_development for ILLocation Changes******/
							/*formObject.setNGEnable("MHT_IL_LOCATION",true);
							formObject.setNGEnable("MHT_IL_LOCATION_CODE",true);*/
							/********End CR-OMHT-1314-02 Omniflow_development for ILLocation Changes*******/
                    }
                    
                }
				System.out.println("MHT_TYPE_OF_CATEGORY loading "+formObject.getNGValue("MHT_TYPE_OF_CATEGORY"));
				System.out.println("MHT_PRODUCT_TYPE loading "+formObject.getNGValue("MHT_PRODUCT_TYPE"));
				if(formObject.getNGValue("MHT_TYPE_OF_CATEGORY") !=null &&  formObject.getNGValue("MHT_PRODUCT_TYPE") != null)
				{
					System.out.println("MHT_FINAL_QUOTE_NO");
					if(formObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Policy Issuance") && formObject.getNGValue("MHT_PRODUCT_TYPE").equalsIgnoreCase("MOTOR"))
					{
						System.out.println("MHT_FINAL_QUOTE_NO loading ");
						formObject.setNGEnable("MHT_FINAL_QUOTE_NO",true);
					}
					
				}
				
				
			}
			/*************************** MHT-PID Process Integration ****************************/
			else if(wsName.equalsIgnoreCase("COPS_Calculator") || wsName.equalsIgnoreCase("COPS_Cust_Id") || wsName.equalsIgnoreCase("BSG_MAT") || wsName.equalsIgnoreCase("COPS_MAT") || wsName.equalsIgnoreCase("COPS_CustId_Priority"))
			{
				//reject to it change formObject.setNGEnable("FRAME_REJECT_TO_IT", false);
				formObject.setNGEnable("FRAME_CATEGORY_TYPE", false);
				formObject.setNGEnable("FRAME_SEARCH_CRITERIA", false);
				formObject.setNGEnable("FRAME_SELECT_VERTICAL", false);
				formObject.setNGEnable("FRAME_TRANSACTION_TYPE", false);
				formObject.setNGEnable("FRAME_IAGENT_PROPOSAL",false);
				formObject.setNGEnable("FRAME_COPS_DETAILS",false);
				formObject.setNGEnable("FRAME_OPEN_CNDETAILS",false);
				formObject.setNGEnable("FRAME_CENTER_CODE", false);
				formObject.setNGEnable("FRAME_PRINT_FORM",false);
				
				formObject.setNGEnable("FRAME_PAYMENTID", false);
				formObject.setNGEnable("FRAME_BSGQC_DETAILS", false);
				
								
				formObject.setNGEnable("FRAME_ISEMP_CODE",false);
				formObject.setNGEnable("FRAME_SOURCE_BUSINESS",false);
				formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",false);
				formObject.setNGEnable("FRAME_PRIVILEGE_BANKER_CODE",false);//MHT-PID Process Integration
				formObject.setNGLocked("FRAME_PRIVILEGE_BANKER_CODE",false);//MHT-PID Process Integration
				formObject.setNGEnable("FRAME_CHANL_EMP",false);
				
				if(wsName.equalsIgnoreCase("COPS_Calculator"))
				{
					formObject.setNGEnable("FRAME_SALESMGR_EMPID",false);
					formObject.setNGEnable("FRAME_MISC",true);
					formObject.setNGLocked("MHT_PAN_CARD_NO",false);
					formObject.setNGEnable("MHT_PF_POLICY_NUMBER",false);
					formObject.setNGEnable("MHT_ALTERNATE_POLICY_NUMBER",false);
					formObject.setNGEnable("MHT_PROPOSAL_STATUS",false);
					formObject.setNGEnable("MHT_IPARTNER_PROPOSAL_NO",false);
					formObject.setNGEnable("MHT_FINAL_QUOTE_NO",false);
					
				}
				else if(wsName.equalsIgnoreCase("COPS_Cust_Id") || wsName.equalsIgnoreCase("COPS_CustId_Priority"))
				{
					formObject.setNGEnable("FRAME_MISC",false);
					formObject.setNGEnable("FRAME_SALESMGR_EMPID",true);
					formObject.setNGEnable("MHT_PAS_SYSTEM",false);
					formObject.setNGEnable("MHT_SM_NAME",false);
					formObject.setNGEnable("MHT_SM_ID",false);
					formObject.setNGEnable("MHT_IL_LOCATION",false);
					formObject.setNGEnable("MHT_IL_LOCATION_CODE",false);
					formObject.setNGEnable("MHT_CUSTOMER_ID",true);
					formObject.setNGEnable("MHT_CUSTOMER_NAME",true);
				}
				else if(wsName.equalsIgnoreCase("BSG_MAT"))
				{
					formObject.setNGEnable("FRAME_REJECT_TO_IT", false);
					formObject.setNGEnable("FRAME_SALESMGR_EMPID",false);
					formObject.setNGEnable("FRAME_MISC",false);
					formObject.setNGEnable("FRAME_MODEOF_PAYMENT", true);					
					formObject.setNGLocked("FRAME_MODEOF_PAYMENT", true);
					formObject.setNGEnable("PAYMENT_TYPE1", false);
					formObject.setNGEnable("PAYMENT_TYPE2", false);
					formObject.setNGEnable("PAYMENT_TYPE3", false);
					//Discard only to Cops
					formObject.setNGEnable("FRAME_DISCARD", false);
					
				}
				else if(wsName.equalsIgnoreCase("COPS_MAT"))
				{
					formObject.setNGEnable("FRAME_SALESMGR_EMPID",false);
					formObject.setNGEnable("FRAME_MISC",false);
					formObject.setNGEnable("FRAME_MODEOF_PAYMENT", false);
					formObject.setNGLocked("FRAME_MODEOF_PAYMENT", false);
					System.out.println("Inside Onload COPS_MAT Enabling FRAME_COPS_DETAILS For Proposal Number : CR-8127-83510 : MHT/PID Enhancement Development");
					formObject.setNGEnable("FRAME_COPS_DETAILS",true);  //CR-8127-83510 : MHT/PID Enhancement Development
				}
				
			}
			/************************End MHT-PID Process Integration ****************************/
            else if(wsName.equalsIgnoreCase("BSG_DataEntry_QC") || wsName.equalsIgnoreCase("BSG_Endorsement"))
            {
                if(debug==1)
                {
                    System.out.println("ICICI_LOMBARD_MHT.executeMHTOnload()BSG_DataEntry_QC loading");
                }
				
				formObject.setNGEnable("FRAME_REJECT_TO_IT", false);
				formObject.setNGEnable("FRAME_CATEGORY_TYPE", false);
				formObject.setNGEnable("FRAME_SEARCH_CRITERIA", false);
				formObject.setNGEnable("FRAME_SELECT_VERTICAL", false);
				formObject.setNGEnable("FRAME_TRANSACTION_TYPE", false);
				formObject.setNGEnable("FRAME_IAGENT_PROPOSAL",false);
				formObject.setNGEnable("FRAME_COPS_DETAILS",false);
				formObject.setNGEnable("FRAME_OPEN_CNDETAILS",false);
				formObject.setNGEnable("FRAME_CENTER_CODE", false);
				formObject.setNGEnable("FRAME_PRINT_FORM",true);
				
				
				String typeOfBus = formObject.getNGValue("MHT_TYPE_OF_BUSINESS");
				String caseCat = formObject.getNGValue("MHT_CASE_CATEGORY");
				formObject.setNGEnable("MHT_SM_NAME", false);
				formObject.setNGEnable("MHT_SM_ID", false);
				
				/**************************** CR-8127-83510 : Start MHT/PID Enhancement Development ***************************/
				System.out.println("Inside Onload BSG_DataEntry_QC And BSG_Endorsement : CR-8127-83510 : MHT/PID Enhancement Development.");
				formObject.setNGEnable("FRAME_PRIVILEGE_BANKER_CODE",false);
				formObject.setNGLocked("FRAME_PRIVILEGE_BANKER_CODE",false);
				/**************************** CR-8127-83510 : End MHT/PID Enhancement Development *****************************/
				
				/*************************** MHT-PID Process Integration ****************************/
				if(wsName.equalsIgnoreCase("BSG_DataEntry_QC"))
				{					
					if(!formObject.getNGValue("MHT_TAG_STATUS").equalsIgnoreCase(""))
					{
						System.out.println("******PID case and tagged******");
						System.out.println("MHT_TAG_STATUS1==="+formObject.getNGValue("MHT_TAG_STATUS"));
						formObject.setNGEnable("MHT_PID_PAYMENT_TYPE", false);
						formObject.setNGLocked("MHT_PID_PAYMENT_TYPE", false);
						formObject.setNGEnable("FRAME_MODEOF_PAYMENT", false);
						formObject.setNGLocked("FRAME_MODEOF_PAYMENT", false);
					}
					else if(formObject.getNGValue("MHT_PID_PAYMENT_TYPE").equalsIgnoreCase("Yes"))
					{
						System.out.println("******PID case but not tagged yet******");
						System.out.println("MHT_TAG_STATUS2==="+formObject.getNGValue("MHT_TAG_STATUS"));
						System.out.println("MHT_PID_PAYMENT_TYPE==="+formObject.getNGValue("MHT_PID_PAYMENT_TYPE"));
						formObject.setNGEnable("FRAME_MODEOF_PAYMENT", false);
						formObject.setNGLocked("FRAME_MODEOF_PAYMENT", false);
					}
					else
					{
						System.out.println("******Non PID case ******");
						System.out.println("MHT_TAG_STATUS3==="+formObject.getNGValue("MHT_TAG_STATUS"));
						System.out.println("MHT_PID_PAYMENT_TYPE==="+formObject.getNGValue("MHT_PID_PAYMENT_TYPE"));
						formObject.setNGEnable("FRAME_MODEOF_PAYMENT", true);
						formObject.setNGLocked("FRAME_MODEOF_PAYMENT", true);
					}
					System.out.println("MHT_TAG_STATUS4==="+formObject.getNGValue("MHT_TAG_STATUS"));
					/**************************** CR-8127-83510 : Start MHT/PID Enhancement Development ***************************/
					
					if(!formObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER"))
					{
						System.out.println("Inside Onload BSG_DataEntry_QC : CR-8127-83510 : Start MHT/PID Enhancement Development");
						formObject.setNGEnable("FRAME_PRIVILEGE_BANKER_CODE",true);
						formObject.setNGLocked("FRAME_PRIVILEGE_BANKER_CODE",true);
						formObject.setNGEnable("MHT_PRIVILEGE_BANKER_CODE",false);
						formObject.setNGEnable("MHT_CIF_ID",false);
						formObject.setNGEnable("MHT_CALL_TAG_NUMBER",true);
						formObject.setNGLocked("MHT_CALL_TAG_NUMBER",true);
					}
					/**************************** CR-8127-83510 : End MHT/PID Enhancement Development *****************************/
				}
				/************************* End MHT-PID Process Integration **************************/
				formObject.setNGEnable("FRAME_PAYMENTID", true);
				formObject.setNGEnable("FRAME_BSGQC_DETAILS", true);
				formObject.setNGEnable("TYPE_OF_POLICY", true);
				formObject.setNGEnable("MHT_PATHFINDER_PROPOSAL_NO",true);
				formObject.setNGEnable("FRAME_DISCARD", true);
				formObject.setNGEnable("MHT_PREMIUM_AMOUNT",true);
				/********Start CR-OMHT-1314-02 Omniflow_development for ILLocation Changes******/
				formObject.setNGEnable("MHT_IL_LOCATION", true);	
				formObject.setNGEnable("MHT_IL_LOCATION_CODE", true);
				/********End CR-OMHT-1314-02 Omniflow_development for ILLocation Changes*******/
				
				//formObject.setNGEnable("FRAME_ROUTETO", false);
				formObject.setNGEnable("MHT_IPARTNER_PAYMENT_ID_NO",false);
                formObject.setNGEnable("MHT_COVER_NOTE_TYPE",false);
				formObject.setNGEnable("MHT_PF_POLICY_NUMBER",false);
				formObject.setNGEnable("MHT_PROPOSAL_STATUS",false);
				formObject.setNGEnable("MHT_ALTERNATE_POLICY_NUMBER",false);
				formObject.setNGEnable("MHT_FINAL_QUOTE_NO",false);
				formObject.setNGEnable("MHT_ENDORSEMENT_NO",false);
				formObject.setNGEnable("MHT_ENDORSEMENT_TYPE",false);
				formObject.setNGLocked("MHT_ENDORSEMENT_TYPE",false);
								
				formObject.setNGLocked("MHT_CASE_CATEGORY", false);
				formObject.setNGLocked("MHT_PRIMARY_VERTICAL", false); 
				formObject.setNGLocked("MHT_SUB_VERTICAL", false);
				formObject.setNGLocked("MHT_PRODUCT_NAME", false);
				formObject.setNGLocked("MHT_SM_NAME", false);
				formObject.setNGLocked("MHT_SOURCE_BUSINESS", false);
				formObject.setNGLocked("MHT_CHANNEL_SOURCE", false);
				formObject.setNGLocked("MHT_BANK_BRANCH_NAME", false);
				formObject.setNGLocked("MHT_BRANCH_ID", false);
				formObject.setNGLocked("MHT_SP_NAME", false);
				formObject.setNGLocked("MHT_SP_CODE", false);
				
				
				formObject.setNGLocked("MHT_PRIVILEGE_BANKER_CODE", false);
				formObject.setNGLocked("MHT_CIF_ID", false);
				formObject.setNGLocked("MHT_BRANCH_ID_UBO_NAME", false);
				formObject.setNGLocked("MHT_COVER_NOTE_TYPE",false);
				formObject.setNGLocked("MHT_IL_LOCATION",false);
				formObject.setNGLocked("MHT_IL_LOCATION_CODE",false);
				
				/********Start CR-OMHT-1314-02 Omniflow_development for ILLocation Changes******/
				/*if(typeOfBus != null && typeOfBus.equalsIgnoreCase("DIRECT"))
				{
					 formObject.setNGEnable("MHT_IL_LOCATION", true);	
					 formObject.setNGEnable("MHT_IL_LOCATION_CODE", true);
				}*/
				/********End CR-OMHT-1314-02 Omniflow_development for ILLocation Changes*******/
				
				if(caseCat != null && caseCat.equalsIgnoreCase("IPARTNER"))
				{
					formObject.setNGEnable("MHT_CUSTOMER_ID",false);
					formObject.setNGEnable("MHT_CUSTOMER_NAME",false);
					formObject.setNGEnable("MHT_PF_PAYMENT_ID_NO",false);
					formObject.setNGEnable("MHT_PF_PAYMENT_ID_NO2",false);
					formObject.setNGEnable("MHT_PF_PAYMENT_ID_NO3",false);
					
				}
				if(formObject.getNGValue("MHT_TYPE_OF_CATEGORY") !=null &&  formObject.getNGValue("MHT_PRODUCT_TYPE") != null)
				{
					System.out.println("MHT_FINAL_QUOTE_NO in QC");
					if(formObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Policy Issuance") && formObject.getNGValue("MHT_PRODUCT_TYPE").equalsIgnoreCase("MOTOR"))
					{
						System.out.println("MHT_FINAL_QUOTE_NO in QC loading ");
						formObject.setNGEnable("MHT_FINAL_QUOTE_NO",true);
					}
					
				}
				//MHT_TYPE_OF_POLICY load
				/*************************** MHT-PID Process Integration ****************************/
				System.out.println("MHT_TYPE_OF_POLICY onload: ");
				String introDate = "";
				introDate = formObject.getNGValue("MHT_INTRO_DATETIME");
				System.out.println("introDate==="+introDate);
				String todayDate = dateformat.format(new Date());
				System.out.println("todayDate==="+todayDate);
				formObject.setNGListIndex("MHT_TYPE_OF_POLICY",1);
				
				if(!formObject.getNGValue("MHT_MANUAL_COVERNOTE_NO").equals("") || (formObject.getNGValue("MHT_CASE_CATEGORY") != null && formObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER")))
				{					
					System.out.println("MCN / Ipartner case ===>");
					formObject.setNGListIndex("MHT_TYPE_OF_POLICY",1);
					formObject.setNGEnable("MHT_TYPE_OF_POLICY",false);
					formObject.setNGLocked("MHT_TYPE_OF_POLICY",false);
				}
				if(!introDate.equalsIgnoreCase(todayDate))
				{
					System.out.println("Introdate and currentdate match ===>");
					formObject.setNGListIndex("MHT_TYPE_OF_POLICY",1);
					formObject.setNGEnable("MHT_TYPE_OF_POLICY",false);
					formObject.setNGLocked("MHT_TYPE_OF_POLICY",false);
				}
				System.out.println("Inside Onload BSG_QC Before IF Condition : CR-8127-83510 : Start MHT/PID Enhancement Development..."+formObject.getNGValue("MHT_SUB_VERTICAL"));
				/************************* End MHT-PID Process Integration **************************/
				if(formObject.getNGValue("MHT_SUB_VERTICAL") != null)
                {
					System.out.println("MHT_SUB_VERTICAL onload:started  ");
					System.out.println("Inside Onload BSG_QC Inside IF Condition : CR-8127-83510 : Start MHT/PID Enhancement Development..."+formObject.getNGValue("MHT_SUB_VERTICAL"));
                    if((formObject.getNGValue("MHT_PRIMARY_VERTICAL") != null && formObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BBG") && formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BBG"))
                         || (formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BBG") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking (BBG)") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") ||
						 formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("KRG")))
                    {
						System.out.println("Inside Onload BSG_QC Inside IF Condition BSG_QC KRG: CR-8127-83510 : Start MHT/PID Enhancement Development...");
                        //formObject.setNGEnable("MHT_PRIVILEGE_BANKER_CODE",true); 	due to field is not there in DB
						formObject.setNGEnable("FRAME_ISEMP_CODE",true);
						formObject.setNGEnable("FRAME_SOURCE_BUSINESS",true);
                        formObject.setNGEnable("MHT_SOURCE_BUSINESS",true);
                        formObject.setNGEnable("MHT_CHANNEL_SOURCE",true);
						formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",true);
						//formObject.setNGEnable("FRAME_PRIVILEGE_BANKER_CODE",true);//MHT-PID Process Integration	   //commented due to CR-8127-83510 : MHT/PID Enhancement CR 
						//formObject.setNGLocked("FRAME_PRIVILEGE_BANKER_CODE",true);//MHT-PID Process Integration	   //commented due to CR-8127-83510 : MHT/PID Enhancement CR 
					/**************************** CR-8127-83510 : Start MHT/PID Enhancement Development ***************************/
						formObject.setNGEnable("MHT_PRIVILEGE_BANKER_CODE",true);
						formObject.setNGLocked("MHT_PRIVILEGE_BANKER_CODE",true);
						formObject.setNGEnable("MHT_CIF_ID",true);
						formObject.setNGLocked("MHT_CIF_ID",true);
						/**************************** CR-8127-83510 : End MHT/PID Enhancement Development *****************************/
                        //formObject.setNGEnable("FRAME_SP_CODE",true); //MHT-PID Process Integration
						formObject.setNGEnable("FRAME_CHANL_EMP",false);
                        if(formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("KRG"))
                        {
							System.out.println("Inside Onload BSG_QC Inside IF Condition BSG_QC KRG 2: CR-8127-83510 : Start MHT/PID Enhancement Development...");
                           /*******Start SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI **********/
							//formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",false);commented yogesh for sp code logic change
							/*******End SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI **********/
							//formObject.setNGEnable("FRAME_PRIVILEGE_BANKER_CODE",false);//MHT-PID Process Integration		//commented due to CR-8127-83510 : MHT/PID Enhancement CR
							//formObject.setNGLocked("FRAME_PRIVILEGE_BANKER_CODE",false);//MHT-PID Process Integration		//commented due to CR-8127-83510 : MHT/PID Enhancement CR
                            formObject.setNGEnable("FRAME_CHANL_EMP",true);
                        }
                    }
                    else
                    {
						System.out.println("Inside Onload BSG_QC Inside Else Condition BSG_QC MHT_SUB_VERTICAL: CR-8127-83510 : Start MHT/PID Enhancement Development...");
                        formObject.setNGEnable("FRAME_ISEMP_CODE",false);
						formObject.setNGEnable("FRAME_SOURCE_BUSINESS",false);
                        //formObject.setNGEnable("MHT_SOURCE_BUSINESS",false);
						//formObject.setNGEnable("MHT_CHANNEL_SOURCE",false);
						formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",false);
						//formObject.setNGEnable("FRAME_PRIVILEGE_BANKER_CODE",false);//MHT-PID Process Integration		//commented due to CR-8127-83510 : MHT/PID Enhancement CR
						//formObject.setNGLocked("FRAME_PRIVILEGE_BANKER_CODE",false);//MHT-PID Process Integration		//commented due to CR-8127-83510 : MHT/PID Enhancement CR
                        formObject.setNGEnable("FRAME_CHANL_EMP",false);
                        //formObject.setNGEnable("FRAME_SP_CODE",false); //MHT-PID Process Integration
                    }
                    //enabling  disabling the bsm bcm on home sub vertical
                    if(formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("home"))
                    {
							System.out.println("Inside Onload BSG_QC Inside Condition BSG_QC Home: CR-8127-83510 : Start MHT/PID Enhancement Development...");
                            formObject.setNGEnable("FRAME_BSM",true);
							formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",true);
							//formObject.setNGEnable("FRAME_PRIVILEGE_BANKER_CODE",true);//MHT-PID Process Integration		//commented due to CR-8127-83510 : MHT/PID Enhancement CR
							//formObject.setNGLocked("FRAME_PRIVILEGE_BANKER_CODE",true);//MHT-PID Process Integration		//commented due to CR-8127-83510 : MHT/PID Enhancement CR
						/**************************** CR-8127-83510 : Start MHT/PID Enhancement Development ***************************/
						formObject.setNGEnable("MHT_PRIVILEGE_BANKER_CODE",true);
						formObject.setNGLocked("MHT_PRIVILEGE_BANKER_CODE",true);
						formObject.setNGEnable("MHT_CIF_ID",true);
						formObject.setNGLocked("MHT_CIF_ID",true);
						/**************************** CR-8127-83510 : End MHT/PID Enhancement Development *****************************/
                    }
                    else
                    {
                            formObject.setNGEnable("FRAME_BSM",false);
                    }
                }
				/*******************Start SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI**********************/
				if(!((formObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Policy Issuance") && formObject.getNGValue("MHT_TYPE_OF_BUSINESS").equalsIgnoreCase("Direct"))) && (formObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("Bancassurance") || formObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BA")) && (formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("KRG")) && !(formObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER")))
				{
					System.out.println("Inside the disable BRANCH_ID and BANK_BRANCH_NAME: Yogesh");
					formObject.setNGEnable("MHT_BRANCH_ID",false);
					formObject.setNGEnable("MHT_BANK_BRANCH_NAME",false);						
				}
				/*******************End SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI**********************/
				
				 if(formObject.isNGEnable("MHT_SOURCE_BUSINESS") && formObject.getNGValue("MHT_SOURCE_BUSINESS")!=null)
                {
                    if(formObject.getNGValue("MHT_SOURCE_BUSINESS").equalsIgnoreCase("Center Sales") || formObject.getNGValue("MHT_SOURCE_BUSINESS").equalsIgnoreCase("Centre Sales"))
                    {
                            formObject.setNGEnable("FRAME_CENTER_CODE",true);
                    }
                    else
                    {
                            formObject.setNGEnable("FRAME_CENTER_CODE",false);
                    }
                }
				if(formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("home"))
				{
					formObject.setNGEnable("FRAME_BSM",true); 
				}
				else
				{
					formObject.setNGEnable("FRAME_BSM",false); 
				}
			}
            else if(wsName.equalsIgnoreCase("RM_Exception") || wsName.equalsIgnoreCase("BSG_Exception") || wsName.equalsIgnoreCase("RM_Endorsement"))
            {
				System.out.println("Exception load started");
				String prevWs = formObject.getNGValue("MHT_PREVIOUS_WORKSTEP");
				String consPrevWs = formObject.getNGValue("MHT_CONS_PREV_WORKSTEP");
				RouteTo_ClarificationWS(prevWs,consPrevWs,wsName);
				
				if(wsName.equalsIgnoreCase("BSG_Exception"))
				{
					formObject.setNGEnable("FRAME_DISCARD",true);
					/**************************** CR-8127-83510 : Start MHT/PID Enhancement Development ***************************/
					if(!formObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER"))
					{
						System.out.println("CR-8127-83510 : Start MHT/PID Enhancement Development : Inside BSG_Exception Other than ipartner ");
						formObject.setNGEnable("FRAME_PRIVILEGE_BANKER_CODE",true);
						formObject.setNGLocked("FRAME_PRIVILEGE_BANKER_CODE",true); 
						formObject.setNGEnable("MHT_PRIVILEGE_BANKER_CODE",false);
						formObject.setNGEnable("MHT_CIF_ID",false);
						formObject.setNGEnable("MHT_CALL_TAG_NUMBER",true);
						formObject.setNGLocked("MHT_CALL_TAG_NUMBER",true);
					}
					else
					{
						System.out.println("CR-8127-83510 : Start MHT/PID Enhancement Development : Inside BSG_Exception Onload ipartner case");
						formObject.setNGEnable("FRAME_PRIVILEGE_BANKER_CODE",false);
						formObject.setNGLocked("FRAME_PRIVILEGE_BANKER_CODE",false); 
					}
					/**************************** CR-8127-83510 : End MHT/PID Enhancement Development *****************************/
				}
				else
				{
					formObject.setNGEnable("FRAME_DISCARD",false);
					formObject.setNGEnable("FRAME_PRIVILEGE_BANKER_CODE",false); //CR-8127-83510 : MHT/PID Enhancement Development
					formObject.setNGLocked("FRAME_PRIVILEGE_BANKER_CODE",false); //CR-8127-83510 : MHT/PID Enhancement Development
				}
				/******************************* MHT-PID CR-8127-59721 enhancemnet additional CR ********************************/
				if(wsName.equalsIgnoreCase("RM_Exception") || wsName.equalsIgnoreCase("BSG_Exception"))
				{
					if(formObject.getNGValue("MHT_TRANSACTION_TYPE") != null && (formObject.getNGValue("MHT_TRANSACTION_TYPE").equals("ROLL OVER") || formObject.getNGValue("MHT_TRANSACTION_TYPE").equals("RENEWAL")) )
					{
						System.out.println("if transaction enable and desiable");
						formObject.setNGEnable("FRAME_TRANSACTION_TYPE",true);
						formObject.setNGEnable("MHT_PREVIOUS_POLICY_NO",true);
						formObject.setNGEnable("MHT_COVER_NOTE_TYPE",false);
						formObject.setNGEnable("MHT_TRANSACTION_TYPE",false);
						
					}
					else 
					{
						formObject.setNGEnable("FRAME_TRANSACTION_TYPE",false);
					}
					System.out.println("transaction enable and desiable");
					formObject.setNGEnable("FRAME_PAYMENTID",true);
					formObject.setNGEnable("MHT_IAS_ID",true);
					formObject.setNGEnable("MHT_IPARTNER_PAYMENT_ID_NO",false);
					formObject.setNGEnable("MHT_BREAK_IN_ID",false);
					formObject.setNGEnable("MHT_ENDORSEMENT_TYPE",false);
					formObject.setNGEnable("MHT_PID_PAYMENT_TYPE",false);
					
					if(formObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Endorsement"))
					{
						System.out.println("if MHT_TYPE_OF_CATEGORY enable and desiable");
						formObject.setNGEnable("MHT_ENDORSEMENT_NO",true);
					}
					else
					{
						System.out.println("else MHT_TYPE_OF_CATEGORY enable and desiable");
						formObject.setNGEnable("MHT_ENDORSEMENT_NO",false);
					}
					
				}
				else
				{
					formObject.setNGEnable("FRAME_PAYMENTID",false);
					formObject.setNGEnable("FRAME_TRANSACTION_TYPE",false);
				}
				
				/******************************End MHT-PID CR-8127-59721 enhancemnet additional CR ******************************/
				formObject.setNGEnable("FRAME_CATEGORY_TYPE",false);
				formObject.setNGEnable("FRAME_SEARCH_CRITERIA",false);
				formObject.setNGEnable("FRAME_SELECT_VERTICAL",false);
				
				formObject.setNGEnable("FRAME_TRANSACTION_TYPE",false);
				formObject.setNGEnable("FRAME_IAGENT_PROPOSAL",false);
				formObject.setNGEnable("FRAME_SALESMGR_EMPID",false);
				
				/******************************* MHT-PID CR-8127-59721 enhancemnet additional CR ********************************/
				/*formObject.setNGEnable("FRAME_TRANSACTION_TYPE",false);//MHT-PID CR-8127-59721 enhancemnet additional CR
				formObject.setNGEnable("FRAME_PAYMENTID",false);//MHT-PID CR-8127-59721 enhancemnet additional CR
				formObject.setNGEnable("FRAME_MISC",false);//MHT-PID CR-8127-59721 enhancemnet additional CR*/
				formObject.setNGEnable("FRAME_MISC",true);
				formObject.setNGEnable("MHT_FINAL_QUOTE_NO",true);
				formObject.setNGEnable("MHT_PREMIUM_AMOUNT",false);
				formObject.setNGEnable("MHT_PAN_CARD_NO",false);
				formObject.setNGEnable("MHT_ALTERNATE_POLICY_NUMBER",false);
				formObject.setNGEnable("MHT_PF_POLICY_NUMBER",false);
				formObject.setNGEnable("MHT_PROPOSAL_STATUS",false);
				/******************************End MHT-PID CR-8127-59721 enhancemnet additional CR ******************************/
				formObject.setNGEnable("FRAME_MODEOF_PAYMENT",false);
				formObject.setNGLocked("FRAME_MODEOF_PAYMENT", false);
				formObject.setNGEnable("FRAME_PRINT_FORM",false);
				
				formObject.setNGEnable("FRAME_SOURCE_BUSINESS",false);
				formObject.setNGEnable("FRAME_ISEMP_CODE",false);
				
				//formObject.setNGEnable("FRAME_SP_CODE",false); //MHT-PID Process Integration
				
				formObject.setNGEnable("MHT_SOURCE_BUSINESS",false);
				formObject.setNGEnable("MHT_CHANNEL_SOURCE",false);
				formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",false);
				//formObject.setNGEnable("FRAME_PRIVILEGE_BANKER_CODE",false);//MHT-PID Process Integration //MHT-PID Process Integration //commented due to CR-8127-83510 : MHT/PID Enhancement CR
				//formObject.setNGLocked("FRAME_PRIVILEGE_BANKER_CODE",false);//MHT-PID Process Integration //MHT-PID Process Integration //commented due to CR-8127-83510 : MHT/PID Enhancement CR
				formObject.setNGEnable("FRAME_CHANL_EMP",false);
				//formObject.setNGEnable("FRAME_SP_CODE",false); //MHT-PID Process Integration
				formObject.setNGEnable("FRAME_BSM",false);
				formObject.setNGEnable("FRAME_CENTER_CODE",false);
				
				formObject.setNGEnable("FRAME_COPS_DETAILS",false);
				formObject.setNGEnable("FRAME_OPEN_CNDETAILS",false);
				formObject.setNGEnable("FRAME_BSGQC_DETAILS",false);
				formObject.setNGEnable("FRAME_ROUTETO",false);
				formObject.setNGEnable("FRAME_REJECT_TO_IT", false);
			
            }
				// Exception for new worksteps
            else if(wsName.equalsIgnoreCase("COPS_IPartner") || wsName.equalsIgnoreCase("COPS_Priority") || wsName.equalsIgnoreCase("COPS_Team") || wsName.equalsIgnoreCase("COPS_Endorsement") || wsName.equalsIgnoreCase("COPS_QC") ||  wsName.equalsIgnoreCase("BSG_OpenCN") || wsName.equalsIgnoreCase("COPS_OpenCN") || wsName.equalsIgnoreCase("COPS_Cust_Id") || wsName.equalsIgnoreCase("COPS_CustId_Priority"))
            {
				System.out.println("COPS load started");
				formObject.setNGEnable("FRAME_REJECT_TO_IT", true);
				
				
				if(wsName.equalsIgnoreCase("COPS_Team") || wsName.equalsIgnoreCase("COPS_Priority") || wsName.equalsIgnoreCase("COPS_QC")) //MHT-PID Process Integration
				{
					System.out.println("Inside Onload COPS_Team,COPS_Priority,COPS_QC Enabling FRAME_COPS_DETAILS For Proposal Number : CR-8127-83510 : MHT/PID Enhancement Development");
					formObject.setNGEnable("FRAME_COPS_DETAILS", true);
					formObject.setNGEnable("FRAME_OPEN_CNDETAILS",false);
					
				}
				else if(wsName.equalsIgnoreCase("BSG_OpenCN"))// || wsName.equalsIgnoreCase("COPS_OpenCN"))
				{
					formObject.setNGEnable("FRAME_REJECT_TO_IT", false);
					formObject.setNGEnable("FRAME_COPS_DETAILS", false);
					if(wsName.equalsIgnoreCase("BSG_OpenCN"))
					{
						formObject.setNGEnable("FRAME_OPEN_CNDETAILS",true);
					}
					else
					{
						formObject.setNGEnable("FRAME_OPEN_CNDETAILS",false);
					}
				}
				else if(wsName.equalsIgnoreCase("COPS_IPartner"))
				{
					formObject.setNGEnable("FRAME_COPS_DETAILS", false);
					formObject.setNGEnable("FRAME_OPEN_CNDETAILS",false);
				}
				
				formObject.setNGEnable("FRAME_CATEGORY_TYPE",false);
				formObject.setNGEnable("FRAME_SEARCH_CRITERIA",false);
				formObject.setNGEnable("FRAME_SELECT_VERTICAL",false);
				formObject.setNGEnable("FRAME_TRANSACTION_TYPE",false);
				formObject.setNGEnable("FRAME_IAGENT_PROPOSAL",false);
				formObject.setNGEnable("FRAME_BSGQC_DETAILS",false);
				/*************************** MHT-PID Process Integration ****************************/
				if(wsName.equalsIgnoreCase("BSG_OpenCN") || wsName.equalsIgnoreCase("COPS_OpenCN"))
				{
					formObject.setNGEnable("FRAME_SALESMGR_EMPID",false);
				}
				formObject.setNGLocked("MHT_SM_NAME",false);
				formObject.setNGLocked("MHT_SM_ID",false);
				formObject.setNGLocked("MHT_IL_LOCATION",false);
				formObject.setNGLocked("MHT_IL_LOCATION_CODE",false);
				formObject.setNGLocked("MHT_CUSTOMER_ID",false);
				formObject.setNGLocked("MHT_CUSTOMER_NAME",false);
				/************************* End MHT-PID Process Integration **************************/
				formObject.setNGEnable("FRAME_PAYMENTID",false);
				formObject.setNGEnable("FRAME_SOURCE_BUSINESS",false);
				formObject.setNGEnable("FRAME_ISEMP_CODE",false);
				formObject.setNGEnable("FRAME_MODEOF_PAYMENT",false);
				formObject.setNGLocked("FRAME_MODEOF_PAYMENT", false);
				formObject.setNGEnable("FRAME_MISC",false);
				
				//Enable Discard for all Cops
				formObject.setNGEnable("FRAME_DISCARD",true);
				formObject.setNGEnable("FRAME_ROUTETO",false);
				
				
				
				formObject.setNGEnable("MHT_SOURCE_BUSINESS",false);
				formObject.setNGEnable("MHT_CHANNEL_SOURCE",false);
				formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",false);
				formObject.setNGEnable("FRAME_PRIVILEGE_BANKER_CODE",false);//MHT-PID Process Integration
				formObject.setNGLocked("FRAME_PRIVILEGE_BANKER_CODE",false);//MHT-PID Process Integration
				formObject.setNGEnable("FRAME_CHANL_EMP",false);
				//formObject.setNGEnable("FRAME_SP_CODE",false); //MHT-PID Process Integration
				formObject.setNGEnable("FRAME_BSM",false);
				formObject.setNGEnable("FRAME_CENTER_CODE",false);
				formObject.setNGEnable("FRAME_PRINT_FORM",false);
				
				/*************************** MHT-PID Process Integration ****************************/
				/*if((formObject.getNGValue("MHT_PRODUCT_TYPE") != null || !formObject.getNGValue("MHT_PRODUCT_TYPE").equalsIgnoreCase("")) && formObject.getNGValue("MHT_PRODUCT_TYPE").equalsIgnoreCase("MOTOR"))
				{
					System.out.println("MOtor");
					formObject.setNGEnable("MHT_OPEN_COVER_NOTE",true);
				}
				else
				{
					System.out.println("else motor");
					formObject.setNGEnable("MHT_OPEN_COVER_NOTE",false);
				}*/
				/************************* End MHT-PID Process Integration **************************/
				
				

            }
            else if(wsName.equalsIgnoreCase("Reject_To_IT") || wsName.equalsIgnoreCase("IPartner_Global"))
            {
				System.out.println("Reject_To_IT & IPartner_Global load started");
				if(wsName.equalsIgnoreCase("Reject_To_IT"))
				{
					formObject.setNGEnable("FRAME_REJECT_TO_IT", true);
					formObject.setNGEnable("FRAME_DISCARD",false);
					formObject.setNGEnable("FRAME_ROUTETO",false);
					formObject.setNGEnable("FRAME_SALESMGR_EMPID",false); //CR-8127-83510 : MHT/PID Enhancement Development
				}
				else if(wsName.equalsIgnoreCase("IPartner_Global"))
				{
					formObject.setNGEnable("FRAME_REJECT_TO_IT", false);
					formObject.setNGEnable("FRAME_DISCARD",true);
					formObject.setNGEnable("FRAME_ROUTETO",true);
					
					/**************************** CR-8127-83510 : Start MHT/PID Enhancement Development ***************************/
					System.out.println("Inside IPartner_Global OnLoad : CR-8127-83510 : MHT/PID Enhancement Development");
					formObject.setNGEnable("FRAME_SALESMGR_EMPID",true);  
					formObject.setNGEnable("MHT_PAS_SYSTEM",false);
					formObject.setNGEnable("MHT_SM_NAME",false);
					formObject.setNGEnable("MHT_DEAL_IL_LOCATION",false);
					formObject.setNGEnable("MHT_SM_ID",false);
					formObject.setNGEnable("MHT_CUSTOMER_ID",false);
					formObject.setNGEnable("MHT_CUSTOMER_NAME",false);
					formObject.setNGEnable("MHT_IL_LOCATION_CODE",true);
					formObject.setNGLocked("MHT_IL_LOCATION_CODE",false);
					formObject.setNGEnable("MHT_IL_LOCATION",true);
					formObject.setNGLocked("MHT_IL_LOCATION",false);
					/**************************** CR-8127-83510 : End MHT/PID Enhancement Development *****************************/
					
					System.out.println("IPartner_Global loading "+formObject.getNGValue("MHT_TYPE_OF_CATEGORY"));
				System.out.println("IPartner_Global MHT_TRANSACTION_TYPE loading "+formObject.getNGValue("MHT_TRANSACTION_TYPE"));
					
					
				} 
				formObject.setNGEnable("FRAME_CATEGORY_TYPE",false);
				formObject.setNGEnable("FRAME_SEARCH_CRITERIA",false);
				formObject.setNGEnable("FRAME_SELECT_VERTICAL",false);
				formObject.setNGEnable("FRAME_TRANSACTION_TYPE",false);
				formObject.setNGEnable("FRAME_IAGENT_PROPOSAL",false);
				
				formObject.setNGEnable("FRAME_PAYMENTID",false);
				formObject.setNGEnable("FRAME_MISC",false);
				formObject.setNGEnable("FRAME_MODEOF_PAYMENT",false);
				formObject.setNGLocked("FRAME_MODEOF_PAYMENT", false);
				formObject.setNGEnable("FRAME_PRINT_FORM",false);
				
				
				
				formObject.setNGEnable("FRAME_SOURCE_BUSINESS",false);
				formObject.setNGEnable("FRAME_ISEMP_CODE",false);
				
				
								
				//formObject.setNGEnable("FRAME_SP_CODE",false); //MHT-PID Process Integration
				
				formObject.setNGEnable("MHT_SOURCE_BUSINESS",false);
				formObject.setNGEnable("MHT_CHANNEL_SOURCE",false);
				formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",false);
				formObject.setNGEnable("FRAME_PRIVILEGE_BANKER_CODE",false);//MHT-PID Process Integration
				formObject.setNGLocked("FRAME_PRIVILEGE_BANKER_CODE",false);//MHT-PID Process Integration
				formObject.setNGEnable("FRAME_CHANL_EMP",false);
				//formObject.setNGEnable("FRAME_SP_CODE",false); //MHT-PID Process Integration
				formObject.setNGEnable("FRAME_BSM",false);
				formObject.setNGEnable("FRAME_CENTER_CODE",false);
				
				formObject.setNGEnable("FRAME_COPS_DETAILS",false);
				formObject.setNGEnable("FRAME_OPEN_CNDETAILS",false);
				formObject.setNGEnable("FRAME_BSGQC_DETAILS",false);
				formObject.setNGEnable("FRAME_ROUTETO",false);

            }
            else if(wsName.equalsIgnoreCase("Discard_Exit") || wsName.equalsIgnoreCase("Work_Exit"))
            {
				System.out.println("Discard_Exit & Work_Exit load started");
				formObject.setNGEnable("FRAME_REJECT_TO_IT", false);
				formObject.setNGEnable("FRAME_CATEGORY_TYPE",false);
				formObject.setNGEnable("FRAME_SEARCH_CRITERIA",false);
				formObject.setNGEnable("FRAME_SELECT_VERTICAL",false);
				formObject.setNGEnable("FRAME_TRANSACTION_TYPE",false);
				formObject.setNGEnable("FRAME_IAGENT_PROPOSAL",false);
				formObject.setNGEnable("FRAME_SALESMGR_EMPID",false);
				formObject.setNGEnable("FRAME_PAYMENTID",false);
				formObject.setNGEnable("FRAME_MISC",false);
				formObject.setNGEnable("FRAME_MODEOF_PAYMENT",false);
				formObject.setNGLocked("FRAME_MODEOF_PAYMENT", false);
				formObject.setNGEnable("FRAME_SOURCE_BUSINESS",false);
				formObject.setNGEnable("FRAME_ISEMP_CODE",false);
				//formObject.setNGEnable("FRAME_SP_CODE",false); //MHT-PID Process Integration
				formObject.setNGEnable("MHT_SOURCE_BUSINESS",false);
				formObject.setNGEnable("MHT_CHANNEL_SOURCE",false);
				formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",false);
				formObject.setNGEnable("FRAME_PRIVILEGE_BANKER_CODE",false);//MHT-PID Process Integration
				formObject.setNGLocked("FRAME_PRIVILEGE_BANKER_CODE",false);//MHT-PID Process Integration
				formObject.setNGEnable("FRAME_CHANL_EMP",false);
				//formObject.setNGEnable("FRAME_SP_CODE",false); //MHT-PID Process Integration
				formObject.setNGEnable("FRAME_BSM",false);
				formObject.setNGEnable("FRAME_CENTER_CODE",false);
				formObject.setNGEnable("FRAME_DISCARD",false);
				formObject.setNGEnable("FRAME_COPS_DETAILS",false);
				formObject.setNGEnable("FRAME_OPEN_CNDETAILS",false);
				formObject.setNGEnable("FRAME_BSGQC_DETAILS",false);
				formObject.setNGEnable("FRAME_ROUTETO",false);
				formObject.setNGEnable("FRAME_REMARKS",false);				
				formObject.setNGEnable("FRAME_FETCH_COPY",true);
				formObject.setNGEnable("FetchCopy",true);
				if(wsName.equalsIgnoreCase("Work_Exit"))
				{
					System.out.println("test Work_Exit");
					formObject.setNGEnable("FRAME_PRINT_FORM",true);
					
				}
				else
				{
					System.out.println("test else Work_Exit");
					formObject.setNGEnable("FRAME_PRINT_FORM",false);
				}
				formObject.setNGEnable("PrintForm",true);
				formObject.setNGEnable("FRAME_VIEW_DOCUMENT",true);
				//formObject.setNGEnable("Btn_DocUpload",false);
					formObject.setNGEnable("ViewDocument",true);
				
            }
        }
        catch(Exception exception)
        {
            
        }
    }
	
	
	void RouteTo_ClarificationWS(String PreviousWS,String ConsPrevWS,String ActivityName)
	{
		//formObject.NGRemoveItemAt("MHT_Route_To",1);
		formObject.NGClear("MHT_Route_To");
		System.out.println("PreviousWS :"+PreviousWS);
		System.out.println("ConsPrevWS :"+ConsPrevWS);
		System.out.println("ActivityName :"+ActivityName);
			// Exception for new worksteps
		if(ConsPrevWS.equalsIgnoreCase("COPS_IPartner") || ConsPrevWS.equalsIgnoreCase("COPS_Priority") || ConsPrevWS.equalsIgnoreCase("COPS_Team") || ConsPrevWS.equalsIgnoreCase("COPS_Endorsement") || ConsPrevWS.equalsIgnoreCase("COPS_OpenCN") || ConsPrevWS.equalsIgnoreCase("COPS_Cust_Id") || ConsPrevWS.equalsIgnoreCase("COPS_CustId_Priority") || ConsPrevWS.equalsIgnoreCase("COPS_Calculator") || ConsPrevWS.equalsIgnoreCase("COPS_MAT") || ConsPrevWS.equalsIgnoreCase("COPS_QC"))
		{
			if(ActivityName.equalsIgnoreCase("RM_Exception"))
			{
				 System.out.println("RouteTo_ClarificationWS 1");
				 formObject.NGAddItem("MHT_Route_To","BSG_Exception");
							  
			}
			if(ActivityName.equalsIgnoreCase("BSG_Exception"))
			{
				System.out.println("RouteTo_ClarificationWS 2");
				if(formObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Endorsement"))
				{
					formObject.NGAddItem("MHT_Route_To","RM_Endorsement");
				}
				else
				{
					formObject.NGAddItem("MHT_Route_To","RM_Exception");
				}
						  
			}
			if(ActivityName.equalsIgnoreCase("RM_Endorsement"))
			{
				System.out.println("RouteTo_ClarificationWS 3");
				formObject.NGAddItem("MHT_Route_To","BSG_Exception");
						  
			}
		}
		
	}



    /**
     * This method will be executed for all the registered commands
     */
    public String executeMHTCommand(XMLParser generalDataParser,String strInputXml,String sCommandName,AppletContext appletContext1)
    {
        try
        {
            System.out.println("executeMHTCommand(): started");
            generalDataParser.setInputXML(formObject.getWFGeneralData());
            wsName = generalDataParser.getValueOf("ActivityName").toUpperCase();
            ICICI_LOM_MHT_ClearControl  ic_ClearControl = new ICICI_LOM_MHT_ClearControl(formObject);			
            if(wsName.equalsIgnoreCase("BSG_DATAENTRY"))
            {
                
				
				System.out.println("executeMHTCommand(): BSG_DATAENTRY started");


                formObject.setNGEnable("MHT_PATHFINDER_PROPOSAL_NO",false);
                //formObject.setNGValue("MHT_IPARTNER_PAYMENT_ID_NO","");
                formObject.setNGEnable("MHT_IPARTNER_PAYMENT_ID_NO",false);
                formObject.setNGEnable("MHT_COVER_NOTE_TYPE",false);
				formObject.setNGEnable("MHT_PF_POLICY_NUMBER",false);
				formObject.setNGEnable("MHT_ALTERNATE_POLICY_NUMBER",false);
				formObject.setNGEnable("MHT_PROPOSAL_STATUS",false);
				formObject.setNGEnable("MHT_IPARTNER_PROPOSAL_NO",false);
				//formObject.setNGEnable("MHT_FINAL_QUOTE_NO",false);
				
				 
				if(sCommandName.equalsIgnoreCase("SUBMIT_SEARCH"))
				{
					
				String catValue = formObject.getNGValue("MHT_TYPE_OF_CATEGORY");
				String busiValue = formObject.getNGValue("MHT_TYPE_OF_BUSINESS");
				System.out.println("SUBMIT_SEARCH(): SUBMIT_SEARCH started");
				/***************************Start MHT SP Code CR-8127-69652*************************/
				formObject.setNGValue("MHT_DEAL_IL_LOCATION","");
				formObject.setNGValue("MHT_SOURCE_BUSINESS","");
				formObject.setNGValue("MHT_CHANNEL_SOURCE","");
				formObject.setNGValue("MHT_BANK_BRANCH_NAME","");
				formObject.setNGValue("MHT_BRANCH_ID","");
				formObject.setNGValue("MHT_SP_CODE","");
				formObject.setNGValue("MHT_SP_NAME","");
				formObject.setNGValue("MHT_SP_PAN","");
				/***************************End MHT SP Code CR-8127-69652*************************/
				if(catValue.equalsIgnoreCase("") || catValue.equalsIgnoreCase("--Select--"))
				{
					System.out.println("catValue"+catValue);
					JOptionPane.showMessageDialog(null,"Please Select Type of Category");
					formObject.NGFocus("MHT_TYPE_OF_CATEGORY");
				}
				else if(!catValue.equalsIgnoreCase("") && catValue.equalsIgnoreCase("Policy Issuance") && (busiValue.equalsIgnoreCase("")  || busiValue.equalsIgnoreCase("--Select--")))
				{
					System.out.println("busiValue"+busiValue);
					JOptionPane.showMessageDialog(null,"Please Select Type of Business");
					formObject.NGFocus("MHT_TYPE_OF_BUSINESS");
				}
				if(catValue != null && !catValue.equalsIgnoreCase("--Select--"))
				{
					if(catValue.equalsIgnoreCase("Policy Issuance"))
					{
					   System.out.println("executeMHTCommand(): Policy Issuance calling ic_ClearControl on button click");
					   boolean bool1 = ic_ClearControl.clear_all_control();
					   //formObject.setNGEnable("FRAME_SEARCH_CRITERIA",true);
					   formObject.setNGEnable("FRAME_TRANSACTION_TYPE",true);

					   formObject.setNGValue("MHT_CASE_CATEGORY", "NORMAL");
					   formObject.setNGLocked("MHT_CASE_CATEGORY", false);
					   formObject.setNGEnable("MHT_ENDORSEMENT_NO",false);
					   formObject.setNGEnable("MHT_TRANSACTION_TYPE",true);

					   formObject.setNGEnable("MHT_SEARCH_CRITERIA",true);

					   formObject.setNGEnable("MHT_TYPE_OF_BUSINESS",true);
						/*************************** MHT-PID Process Integration ****************************/
						//formObject.setNGListIndex("MHT_PID_PAYMENT_TYPE",2);
						formObject.setNGEnable("MHT_PID_PAYMENT_TYPE",true);
						formObject.setNGLocked("MHT_PID_PAYMENT_TYPE",true);
						formObject.setNGListIndex("MHT_ENDORSEMENT_TYPE",0);
						formObject.setNGEnable("MHT_ENDORSEMENT_TYPE",false);
						formObject.setNGLocked("MHT_ENDORSEMENT_TYPE",false);
						/************************* End MHT-PID Process Integration **************************/
					   
					}
					else if(catValue != null && catValue.equalsIgnoreCase("Endorsement"))
					{
						 System.out.println("executeMHTCommand(): Endorsement calling ic_ClearControl on button click");
						 boolean bool2 = ic_ClearControl.clear_all_control();
						 formObject.setNGListIndex("MHT_TYPE_OF_BUSINESS",0);
						 formObject.setNGEnable("MHT_TYPE_OF_BUSINESS",false);
						 
						 formObject.setNGEnable("MHT_CASE_CATEGORY",false);
						 formObject.setNGValue("MHT_COVER_NOTE_TYPE","");
						 formObject.setNGEnable("MHT_COVER_NOTE_TYPE",false);
						 formObject.setNGValue("MHT_PREVIOUS_POLICY_NO","");
						 formObject.setNGEnable("MHT_PREVIOUS_POLICY_NO",false);
						 
						 /*************************** MHT-PID Process Integration ****************************/
						 /*formObject.setNGValue("MHT_OPEN_COVER_NOTE","");
						 formObject.setNGEnable("MHT_OPEN_COVER_NOTE",false);*/
						 /************************* End MHT-PID Process Integration **************************/
						 formObject.setNGListIndex("MHT_TRANSACTION_TYPE",0);
						 formObject.setNGEnable("MHT_TRANSACTION_TYPE",false);

						 
						 
						 //formObject.setNGValue("MHT_PF_POLICY_NUMBER","");
						 
						 
						 
						 formObject.setNGValue("MHT_FINAL_QUOTE_NO","");
						 formObject.setNGEnable("MHT_FINAL_QUOTE_NO",false);
						 formObject.setNGValue("MHT_CASE_CATEGORY","");
						 formObject.setNGEnable("MHT_CASE_CATEGORY",false);

						 
						 formObject.setNGListIndex("MHT_SEARCH_CRITERIA",0);
						 formObject.setNGEnable("MHT_SEARCH_CRITERIA",false);
						 formObject.setNGValue("MHT_AGENT_NAME","");
						 formObject.setNGEnable("MHT_AGENT_NAME",false);
						 formObject.setNGValue("MHT_AGENT_CODE","");
						 formObject.setNGEnable("MHT_AGENT_CODE",false);
						 formObject.setNGValue("MHT_DEAL_NO","");
						 formObject.setNGEnable("MHT_DEAL_NO",false);
						 formObject.setNGValue("MHT_DEAL_STATUS","");
						 formObject.setNGValue("MHT_MANUAL_COVERNOTE_NO","");
						 formObject.setNGEnable("MHT_MANUAL_COVERNOTE_NO",false);


						 formObject.setNGValue("MHT_PRIMARY_VERTICAL","");
						 formObject.setNGEnable("MHT_PRIMARY_VERTICAL",true);
						 formObject.setNGValue("MHT_SUB_VERTICAL","");
						 formObject.setNGEnable("MHT_SUB_VERTICAL",true);
						 formObject.setNGValue("MHT_PRODUCT_NAME","");
						 formObject.setNGEnable("MHT_PRODUCT_NAME",true);
						 /********Start CR-OMHT-1314-02 Omniflow_development for ILLocation Changes******/
						 /*formObject.setNGValue("MHT_IL_LOCATION","");
						 formObject.setNGEnable("MHT_IL_LOCATION",true);
						 formObject.setNGValue("MHT_IL_LOCATION_CODE",""); */
						 /********End CR-OMHT-1314-02 Omniflow_development for ILLocation Changes*******/
						 
						 formObject.setNGValue("MHT_AGENT_NAME","");
						 formObject.setNGEnable("MHT_AGENT_NAME",false);



					   


						 formObject.setNGEnable("FRAME_TRANSACTION_TYPE",false);

						  //formObject.setNGEnable("FRAME_SEARCH_CRITERIA",false);

						  formObject.setNGEnable("MHT_ENDORSEMENT_NO",true);
						  /*************************** MHT-PID Process Integration ****************************/
							/*formObject.setNGListIndex("MHT_PID_PAYMENT_TYPE",2);
							formObject.setNGEnable("MHT_PID_PAYMENT_TYPE",false);
							formObject.setNGLocked("MHT_PID_PAYMENT_TYPE",false);*/
							formObject.setNGEnable("MHT_ENDORSEMENT_TYPE",true);
							formObject.setNGLocked("MHT_ENDORSEMENT_TYPE",true);
							formObject.setNGEnable("FRAME_MODEOF_PAYMENT",true);
							formObject.setNGLocked("FRAME_MODEOF_PAYMENT", true);	 
							formObject.setNGEnable("MHT_SM_NAME",true);
							formObject.setNGLocked("MHT_SM_NAME",false);
							formObject.setNGEnable("MHT_SM_ID",true);
							formObject.setNGLocked("MHT_SM_ID",false);
						  /************************* End MHT-PID Process Integration **************************/
						  
					}
				} 
				if(busiValue != null && !busiValue.equalsIgnoreCase("--Select--"))
				{
							//boolean bool = ic_ClearControl.clear_all_control();
					if(busiValue.equalsIgnoreCase("Intermediary"))
					{
						System.out.println("executeMHTCommand(): Intermediary calling ic_ClearControl on button click");
						boolean bool = ic_ClearControl.clear_all_control();
						//formObject.setNGEnable("FRAME_SEARCH_CRITERIA",true);
						formObject.setNGEnable("FRAME_TRANSACTION_TYPE",true);
						formObject.setNGEnable("MHT_SEARCH_CRITERIA",true);
						formObject.setNGEnable("MHT_CASE_CATEGORY", true);
					/**************************** MHT-PID Process Integration ***************************/	 
						formObject.setNGEnable("MHT_SM_NAME",false);
						formObject.setNGLocked("MHT_SM_NAME",true);
						formObject.setNGEnable("MHT_SM_ID",false);
						formObject.setNGLocked("MHT_SM_ID",true);
					/************************ End MHT-PID Process Integration ***************************/
						/***************************Start MHT SP Code CR-8127-69652*************************/
						if((catValue != null && catValue.equalsIgnoreCase("Policy Issuance")) && (busiValue != null && busiValue.equalsIgnoreCase("Intermediary")) && (formObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BANCASSURANCE"))&&(formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING")))
						{
							formObject.setNGEnable("MHT_DEAL_IL_LOCATION",true);
							formObject.setNGEnable("MHT_BRANCH_ID",true);
							formObject.setNGEnable("MHT_SP_CODE",true);
						}
						/***************************End MHT SP Code CR-8127-69652*************************/

					}
					else if(busiValue.equalsIgnoreCase("Direct"))
					{
						System.out.println("executeMHTCommand(): Direct calling ic_ClearControl on button click");
						 boolean bool = ic_ClearControl.clear_all_control();  
						formObject.setNGEnable("MHT_CASE_CATEGORY", false);
						formObject.setNGLocked("MHT_CASE_CATEGORY", true);


						 formObject.setNGListIndex("MHT_SEARCH_CRITERIA",0);
						 formObject.setNGEnable("MHT_SEARCH_CRITERIA",false);
						 formObject.setNGValue("MHT_AGENT_NAME","");
						 formObject.setNGEnable("MHT_AGENT_NAME",false);
						 formObject.setNGValue("MHT_AGENT_CODE","");
						 formObject.setNGEnable("MHT_AGENT_CODE",false);
						 formObject.setNGValue("MHT_DEAL_NO","");
						 formObject.setNGEnable("MHT_DEAL_NO",false);
						 formObject.setNGValue("MHT_DEAL_STATUS","");
						 formObject.setNGValue("MHT_MANUAL_COVERNOTE_NO","");
						 formObject.setNGEnable("MHT_MANUAL_COVERNOTE_NO",false);

						// formObject.setNGEnable("FRAME_SEARCH_CRITERIA",false);


						 formObject.setNGValue("MHT_PRIMARY_VERTICAL","");
						 formObject.setNGEnable("MHT_PRIMARY_VERTICAL",true);
						 formObject.setNGValue("MHT_SUB_VERTICAL","");
						 formObject.setNGEnable("MHT_SUB_VERTICAL",true);
						 formObject.setNGValue("MHT_PRODUCT_NAME","");
						 formObject.setNGEnable("MHT_PRODUCT_NAME",true);
						 /********Start CR-OMHT-1314-02 Omniflow_development for ILLocation Changes******/
									 /*formObject.setNGValue("MHT_IL_LOCATION","");
						 formObject.setNGEnable("MHT_IL_LOCATION",true);
						 formObject.setNGValue("MHT_IL_LOCATION_CODE","");*/
									 /********End CR-OMHT-1314-02 Omniflow_development for ILLocation Changes*******/
						 formObject.setNGValue("MHT_AGENT_NAME","");
						 formObject.setNGEnable("MHT_AGENT_NAME",false);
					/**************************** MHT-PID Process Integration ***************************/	 
						formObject.setNGEnable("MHT_SM_NAME",true);
						formObject.setNGLocked("MHT_SM_NAME",false);
						formObject.setNGEnable("MHT_SM_ID",true);
						formObject.setNGLocked("MHT_SM_ID",false);
					/************************ End MHT-PID Process Integration ***************************/
					/***************************Start MHT SP Code CR-8127-69652*************************/
					formObject.setNGValue("MHT_DEAL_IL_LOCATION","");
					formObject.setNGEnable("MHT_DEAL_IL_LOCATION",false);
					/***************************End MHT SP Code CR-8127-69652*************************/
					}

				}
				if((catValue != null && catValue.equalsIgnoreCase("Policy Issuance")) && (busiValue != null && busiValue.equalsIgnoreCase("Intermediary")) )
				{
					if(formObject.getNGValue("MHT_AGENT_CODE").equals("") || formObject.getNGValue("MHT_AGENT_NAME").equals("") || formObject.getNGValue("MHT_DEAL_NO").equals("") || (formObject.getNGValue("MHT_DEAL_NO").equals("") && formObject.getNGValue("MHT_MANUAL_COVERNOTE_NO").equals("")))
					{
						JOptionPane.showMessageDialog(null,"Agent Name/Agent Code/Deal No/Manual CN/SM ID/SM Name cannot be left blank  !!");
						formObject.NGClear("MHT_PRIMARY_VERTICAL");
						formObject.NGClear("MHT_SUB_VERTICAL");
						formObject.NGClear("MHT_PRODUCT_NAME");
					/**************************** MHT-PID Process Integration ***************************/
						formObject.NGClear("MHT_SM_NAME");
						formObject.NGClear("MHT_SM_ID");
					/************************ End MHT-PID Process Integration ***************************/
						/********Start CR-OMHT-1314-02 Omniflow_development for ILLocation Changes******/
						/*formObject.NGClear("MHT_IL_LOCATION");
						formObject.NGClear("MHT_IL_LOCATION_CODE");*/
						/********End CR-OMHT-1314-02 Omniflow_development for ILLocation Changes*******/
						formObject.setNGEnable("FRAME_SELECT_VERTICAL",true);	
					}
					else
					{
						//ICICI_LOM_MHT_ClearControl  ic_ClearControl = new ICICI_LOM_MHT_ClearControl(formObject);
						boolean bool = ic_ClearControl.clear_all_control();

						formObject.NGClear("MHT_PRIMARY_VERTICAL");
						formObject.NGClear("MHT_SUB_VERTICAL");
						formObject.NGClear("MHT_PRODUCT_NAME");
					/**************************** MHT-PID Process Integration ***************************/
						formObject.NGClear("MHT_SM_NAME");
						formObject.NGClear("MHT_SM_ID");
					/************************ End MHT-PID Process Integration ***************************/
					/********Start CR-OMHT-1314-02 Omniflow_development for ILLocation Changes******/
						/*formObject.NGClear("MHT_IL_LOCATION");
						formObject.NGClear("MHT_IL_LOCATION_CODE");*/
					/********End CR-OMHT-1314-02 Omniflow_development for ILLocation Changes*******/
						
						System.out.println("below start");
						
						fieldValue = formObject.getNGValue("MHT_DEAL_NO");
						
				/**************************** MHT-PID Process Integration ***************************/
				//Setting MHT_PRIMARY_VERTICAL_CODE along with 	MHT_PRIMARY_VERTICAL
				// gd.NUM_SEC_VERTICAL_CD represents the primary vertical code only
				// gt.NUM_PRIM_VERTICAL_CD  represents the primary vertical code
				// If Subvertical E CHANNEL THEN INTERCHAGE PRIMARY VERTICAL AND SUBVERTICAL VALUES 
						String fields[]= new String[2];
						noOfCols="2";					
						fields[0] = "MHT_PRIMARY_VERTICAL";
						fields[1] = "MHT_PRIMARY_VERTICAL_CODE";
						//fields[2] = "MHT_PRIMARY_VERTICAL_CODE";
						//query = "select distinct gt.txt_full_prim_vertical_desc, gt.NUM_PRIM_VERTICAL_CD,gd.NUM_SEC_VERTICAL_CD from MV_MHT_GENMST_TAB_VERTICAL_N gt,MV_MHT_Gen_Deal_Detail gd  where gt.TXT_DEAL_ID = gd.TXT_DEAL_ID and gt.TXT_DEAL_ID=N'"+fieldValue+"'";
						query = "select distinct gt.txt_full_prim_vertical_desc, gt.NUM_PRIM_VERTICAL_CD from MV_MHT_GENMST_TAB_VERTICAL_N gt,MV_MHT_Gen_Deal_Detail gd  where gt.TXT_DEAL_ID = gd.TXT_DEAL_ID and gt.TXT_DEAL_ID=N'"+fieldValue+"'";
				/************************ End MHT-PID Process Integration ***************************/
						getData(query,noOfCols,fields,flag);

						System.out.println("MHT_PRIMARY_VERTICAL done"+query);
				
				/**************************** MHT-PID Process Integration ***************************/
				//Setting MHT_SUB_VERTICAL_CODE along with MHT_SUB_VERTICAL
						String field[]= new String[2];
						noOfCols="2";
						field[0] = "MHT_SUB_VERTICAL";
						field[1] = "MHT_SUB_VERTICAL_CODE";
						query="select distinct gt.TXT_FULL_VERTICAL_DESC, gt.NUM_SEC_VERTICAL_CD from MV_MHT_GENMST_TAB_VERTICAL_N gt,MV_MHT_Gen_Deal_Detail gd where gt.TXT_DEAL_ID = gd.TXT_DEAL_ID and gd.TXT_DEAL_ID=N'"+fieldValue+"' and gt.NUM_SEC_VERTICAL_CD=gd.NUM_PRIM_VERTICAL_CD";
				/************************ End MHT-PID Process Integration ***************************/
						getData(query,noOfCols,field,flag);
						System.out.println("MHT_SUB_VERTICAL done");
						
				/**************************** MHT-PID Process Integration ***************************/
				//Setting SM ID and SM Name based on Deal No
						field[0] = "MHT_SM_NAME";
						field[1] = "MHT_SM_ID";
						query="select distinct gt.PRIMARY_MO_NAME,gt.TXT_HR_REF_NO from MV_MHT_GENMST_EMPLOYEE gt,MV_MHT_Gen_Deal_Detail gd where gt.TXT_DEAL_ID = gd.TXT_DEAL_ID and gd.TXT_DEAL_ID=N'"+fieldValue+"'";
						
						getData(query,noOfCols,field,flag);
						System.out.println("MHT_SM_NAME done");
						
						formObject.setNGEnable("MHT_SM_NAME",false);
						formObject.setNGLocked("MHT_SM_NAME",true);
						formObject.setNGEnable("MHT_SM_ID",false);
						formObject.setNGLocked("MHT_SM_ID",true);
				/************************ End MHT-PID Process Integration ***************************/

						noOfCols="1";
						String prod_field[]= new String[1];
						prod_field[0]="MHT_PRODUCT_NAME";
						query="select distinct gt.PRODUCTNAME from MV_MHT_UW_PRODUCT_MASTER gt,MV_MHT_Gen_Deal_Detail gd where gd.TXT_DEAL_ID=N'"+fieldValue+"' and gt.PRODUCTCODE=gd.NUM_PRODUCT_CODE";
						getData(query,noOfCols,prod_field,flag);
						
						System.out.println("MHT_PRODUCT_NAME done");

						

						System.out.println("MHT_CENTER_CODE_NAME done");
					/********Start CR-OMHT-1314-02 Omniflow_development for ILLocation Changes******/
						/*if(fieldValue != null)
						{
								String arrILLocFields[]= {"MHT_IL_LOCATION","MHT_IL_LOCATION_CODE"};
								String productlistQuery = "select il.txt_office,il.num_office_cd from MV_MHT_GENMST_OFFICE il,MV_MHT_Gen_Deal_Detail gd where il.num_office_cd = gd.num_office_cd and gd.TXT_DEAL_ID=N'"+fieldValue+"'";
								noOfCols="2";
								getData(productlistQuery,noOfCols,arrILLocFields,flag);
								formObject.setNGEnable("MHT_IL_LOCATION",false);
								formObject.setNGEnable("MHT_IL_LOCATION_CODE",false);
						} */
					/********End CR-OMHT-1314-02 Omniflow_development for ILLocation Changes*******/
					
					/***************************Start MHT SP Code CR-8127-69652*************************/
					
					if(fieldValue != null)
					{//yy
						if((catValue != null && catValue.equalsIgnoreCase("Policy Issuance")) && (busiValue != null && busiValue.equalsIgnoreCase("Intermediary")) && (formObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BANCASSURANCE"))&&(formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING")))
						{
							String arrILLocFields[]= new String[1];
							arrILLocFields[0]="MHT_DEAL_IL_LOCATION";
							String productlistQuery = "select il.txt_office from MV_MHT_GENMST_OFFICE il,MV_MHT_Gen_Deal_Detail gd where il.num_office_cd = gd.num_office_cd and gd.TXT_DEAL_ID=N'"+fieldValue+"'";
							noOfCols="1";
							getData(productlistQuery,noOfCols,arrILLocFields,flag);
							System.out.println("Inside the Deal Location After getData() execution");
							formObject.setNGEnable("MHT_DEAL_IL_LOCATION",false);
						}
					} 				
					/***************************End MHT SP Code CR-8127-69652*************************/
					formObject.setNGEnable("FRAME_SELECT_VERTICAL",false);
					}
				}
			}
				else if(sCommandName.equalsIgnoreCase("TRANSACTION_TYPE_RENEWAL"))
				{
							System.out.println("TRANSACTION_TYPE_RENEWAL: MHT_PREVIOUS_POLICY_NO execute cmd");
							System.out.println("Inside executeMHTCommand Enabling MHT_PREVIOUS_POLICY_NO Based on TRANSACTION_TYPE_RENEWAL : CR-8127-83510 : MHT/PID Enhancement Development");
							fieldName= "MHT_TRANSACTION_TYPE";
							fieldValue=formObject.getNGValue(fieldName);
							
							if(fieldValue.equals("RENEWAL") || fieldValue.equals("ROLL OVER") || fieldValue.equals("USED")) //CR-8127-83510 : MHT/PID Enhancement Development
							{
								formObject.setNGEnable("MHT_PREVIOUS_POLICY_NO",true);
								/*if(fieldValue.equals("ROLL OVER"))
								{
									System.out.println("in roll over=="+fieldValue);
									formObject.setNGValue("MHT_PREVIOUS_POLICY_NO","");
								}*/
							}
							else
							{
								formObject.setNGEnable("MHT_PREVIOUS_POLICY_NO",false);
								formObject.setNGValue("MHT_PREVIOUS_POLICY_NO","");
							}
				}
            }//bsg dataentry end
		}//try end
        catch(Exception exception)
        {

        }

        return "";
    }

    /**
     * This method will be executed for all the Event based Fields
     */
     public String executeMHTEvent(String pEventName,String pControlName,String pControlValue,String pReserved)
     {
		 System.out.println("executeMHTEvent():pEventName started  "+pEventName);
		 System.out.println("executeMHTEvent():pControlName started  "+pControlName);
         generalDataParser.setInputXML(formObject.getWFGeneralData());
         wsName = generalDataParser.getValueOf("ActivityName").toUpperCase();
		 wiName = generalDataParser.getValueOf("ProcessInstanceId").toUpperCase();
         String dealNo = formObject.getNGValue("MHT_DEAL_NO");
		 String businessType = formObject.getNGValue("MHT_TYPE_OF_BUSINESS");
		 String typeOfCat = formObject.getNGValue("MHT_TYPE_OF_CATEGORY");
		String sUserName = formObject.getUserName(); 
         NGPickList picklist=null;
         PickListListener pl=null;
		 ICICI_LOM_MHT_ClearControl  ic_ClearControl = new ICICI_LOM_MHT_ClearControl(formObject);
		 System.out.println("wiName===="+wiName);	 
		 
		 if((wsName.equalsIgnoreCase("Reject_To_IT")) && pControlName.equalsIgnoreCase("MHT_REJECT_TO_IT") && pEventName.equalsIgnoreCase("CLICK") || pEventName.equalsIgnoreCase("change"))
		 {
			System.out.println("Reject_To_IT():event  "+formObject.getNGValue("MHT_REJECT_TO_IT"));
			if(!formObject.getNGValue("MHT_REJECT_TO_IT").equalsIgnoreCase("") && formObject.getNGValue("MHT_REJECT_TO_IT").equalsIgnoreCase("false"))
			{
				formObject.setNGValue("MHT_SAMADHAN_ID","");
				formObject.setNGEnable("MHT_SAMADHAN_ID",false);
			}
		 }
		 
		 //Adding Cops Worksteps
		  if((wsName.equalsIgnoreCase("BSG_DataEntry_QC") || wsName.equalsIgnoreCase("BSG_Endorsement") || wsName.equalsIgnoreCase("RM_Exception") || wsName.equalsIgnoreCase("BSG_Exception") || wsName.equalsIgnoreCase("RM_Endorsement") || wsName.equalsIgnoreCase("IPartner_Global") || wsName.equalsIgnoreCase("COPS_Team") || wsName.equalsIgnoreCase("COPS_Priority") || wsName.equalsIgnoreCase("COPS_IPartner") || wsName.equalsIgnoreCase("COPS_Cust_Id") || wsName.equalsIgnoreCase("COPS_CustId_Priority") || wsName.equalsIgnoreCase("COPS_Calculator") || wsName.equalsIgnoreCase("COPS_Endorsement") || wsName.equalsIgnoreCase("COPS_MAT") || wsName.equalsIgnoreCase("COPS_OpenCN") || wsName.equalsIgnoreCase("COPS_QC")) && pControlName.equalsIgnoreCase("MHT_DISCARD") && pEventName.equalsIgnoreCase("CLICK") || pEventName.equalsIgnoreCase("change")) //MHT-PID Process Integration
		 {
			System.out.println("MHT_DISCARD():event  "+formObject.getNGValue("MHT_DISCARD"));
			if(!formObject.getNGValue("MHT_DISCARD").equalsIgnoreCase("") && formObject.getNGValue("MHT_DISCARD").equalsIgnoreCase("false"))
			{
				System.out.println("MHT_DISCARD():event  "+formObject.getNGValue("MHT_DISCARD"));
				formObject.setNGListIndex("MHT_DISCARD_REASON",0);
				formObject.setNGLocked("MHT_DISCARD_REASON",false);
				formObject.setNGEnable("MHT_DISCARD_REASON",false);
				
			}
			else if(formObject.getNGValue("MHT_DISCARD").equalsIgnoreCase("true"))
			{
				System.out.println("Reject_To_IT():event else  "+formObject.getNGValue("MHT_DISCARD"));
				formObject.setNGLocked("MHT_DISCARD_REASON",true);
				formObject.setNGEnable("MHT_DISCARD_REASON",true);
				
			}
		 }
			// Exception for new worksteps
		 if((wsName.equalsIgnoreCase("COPS_IPartner") || wsName.equalsIgnoreCase("COPS_Priority") || wsName.equalsIgnoreCase("COPS_Team") || wsName.equalsIgnoreCase("COPS_Endorsement") || wsName.equalsIgnoreCase("COPS_MAT") ||wsName.equalsIgnoreCase("COPS_OpenCN") || wsName.equalsIgnoreCase("COPS_Cust_Id") || wsName.equalsIgnoreCase("COPS_CustId_Priority") || wsName.equalsIgnoreCase("COPS_Calculator") || wsName.equalsIgnoreCase("COPS_QC")) && pControlName.equalsIgnoreCase("MHT_REJECT_TO_IT") && pEventName.equalsIgnoreCase("CLICK") || pEventName.equalsIgnoreCase("change"))
		 {
			System.out.println("MHT_REJECT_TO_IT():event  "+formObject.getNGValue("MHT_REJECT_TO_IT"));
			if(!formObject.getNGValue("MHT_REJECT_TO_IT").equalsIgnoreCase("") && formObject.getNGValue("MHT_REJECT_TO_IT").equalsIgnoreCase("true"))
			{
				formObject.setNGEnable("MHT_SAMADHAN_ID",true);
			}
			else
			{
				formObject.setNGValue("MHT_SAMADHAN_ID","");
				formObject.setNGEnable("MHT_SAMADHAN_ID",false);
			}
		 }
		
		if(!wsName.equalsIgnoreCase("BSG_DATAENTRY")/* || wsName.equalsIgnoreCase("BSG_DATAENTRY_QC") || wsName.equalsIgnoreCase("COPS_Team") || wsName.equalsIgnoreCase("Reject_To_IT") || wsName.equalsIgnoreCase("RM_Exception") || wsName.equalsIgnoreCase("BSG_Exception") ||  wsName.equalsIgnoreCase("COPS_IPartner") ||  wsName.equalsIgnoreCase("COPS_Priority") ||  wsName.equalsIgnoreCase("BSG_OpenCN") || wsName.equalsIgnoreCase("COPS_OpenCN") || wsName.equalsIgnoreCase("IPartner_Global")) */&& pControlName.equalsIgnoreCase("FetchCopy") && pEventName.equalsIgnoreCase("CLICK"))
		{
				System.out.println("FetchCopy():event  ");
				url="FetchCopyWorkItem.jsp?Process=MHT&ActivityName="+wsName+"&Pid="+wiName;
				showPage(url,"FetchCopy");
		}
		
		if((wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DATAENTRY_QC") || wsName.equalsIgnoreCase("BSG_Endorsement") || wsName.equalsIgnoreCase("COPS_Team") || wsName.equalsIgnoreCase("COPS_Endorsement") || wsName.equalsIgnoreCase("BSG_MAT") || wsName.equalsIgnoreCase("COPS_MAT") || wsName.equalsIgnoreCase("COPS_QC") || wsName.equalsIgnoreCase("Reject_To_IT") || wsName.equalsIgnoreCase("RM_Exception") || wsName.equalsIgnoreCase("BSG_Exception") ||  wsName.equalsIgnoreCase("COPS_IPartner") ||  wsName.equalsIgnoreCase("COPS_Priority") ||  wsName.equalsIgnoreCase("BSG_OpenCN") || wsName.equalsIgnoreCase("COPS_OpenCN") || wsName.equalsIgnoreCase("IPartner_Global") || wsName.equalsIgnoreCase("Work_Exit")) && pControlName.equalsIgnoreCase("ViewDocument") && pEventName.equalsIgnoreCase("CLICK")) //MHT-PID Process Integration
		{
			System.out.println("ViewDocument:event  ");
			String sQuery = "SELECT IP FROM NG_MHT_CONFIG_MASTER where SrNo=1 and rownum<=1";
			System.out.println("DocView sQuery:" + sQuery);
			ArrayList getVal = formObject.getNGDataFromDataSource(sQuery, 1);
			String s_IP = (getVal.get(0)).toString();
			s_IP = s_IP.substring(s_IP.indexOf("[") + 1, s_IP.indexOf("]"));
			System.out.println("s_IP: " + s_IP);
			String appName = "";
			if(s_IP.equalsIgnoreCase("10.16.167.106"))
			{
				appName = "Omniflow";
			}
			else
			{
				appName = "OmniflowCloud";
			}
			url = "http://"+s_IP+"/omnidocs/integration/foldView/viewFoldList.jsp?Application="+appName+"&FolderName=" + wiName;
			
			showPage(url,"ViewDocument");
		}
		
		if((wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DATAENTRY_QC") || wsName.equalsIgnoreCase("BSG_Endorsement") || wsName.equalsIgnoreCase("COPS_Team") || wsName.equalsIgnoreCase("COPS_Endorsement")  || wsName.equalsIgnoreCase("BSG_MAT") || wsName.equalsIgnoreCase("COPS_MAT") || wsName.equalsIgnoreCase("COPS_QC") || wsName.equalsIgnoreCase("Reject_To_IT") || wsName.equalsIgnoreCase("RM_Exception") || wsName.equalsIgnoreCase("BSG_Exception") ||  wsName.equalsIgnoreCase("COPS_IPartner") ||  wsName.equalsIgnoreCase("COPS_Priority") ||  wsName.equalsIgnoreCase("BSG_OpenCN") || wsName.equalsIgnoreCase("COPS_OpenCN") || wsName.equalsIgnoreCase("IPartner_Global") ||  wsName.equalsIgnoreCase("RM_Endorsement") ||  wsName.equalsIgnoreCase("COPS_Calculator") ||  wsName.equalsIgnoreCase("COPS_Cust_Id") ||  wsName.equalsIgnoreCase("COPS_CustId_Priority")) && pControlName.equalsIgnoreCase("RemarksHistory") && pEventName.equalsIgnoreCase("CLICK")) //MHT-PID Process Integration //CR-8127-83510 : MHT/PID Enhancement Development(FR 1.7)
		{
			System.out.println("Checking For Remarks Histroy : CR-8127-83510 : MHT/PID Enhancement Development(FR 1.7)");
			url="ShowHistory.jsp?Process=MHT&ActivityName="+wsName+"&Pid="+wiName;
				showPage(url,"RemarksHistory");
		}
		/**************************** MHT-PID Process Integration ***************************/
		if((wsName.equalsIgnoreCase("BSG_DATAENTRY_QC") || wsName.equalsIgnoreCase("BSG_Endorsement") || wsName.equalsIgnoreCase("Work_Exit")) && pControlName.equalsIgnoreCase("PrintForm") && pEventName.equalsIgnoreCase("CLICK"))
		{
			url="PrintSlip.jsp?Process=MHT&Pid="+wiName;
				showPage(url,"PrintSlip");
		}
		/*if((wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DATAENTRY_QC") || wsName.equalsIgnoreCase("BSG_Endorsement") ||  wsName.equalsIgnoreCase("BSG_OpenCN") || wsName.equalsIgnoreCase("BSG_Exception")) && pControlName.equalsIgnoreCase("Btn_DocUpload") && pEventName.equalsIgnoreCase("CLICK"))
		{
			url = "DocUpload.jsp?Process=MHT" + "&Pid=" + wiName + "&userId=" + sUserName + "&cabinetName=ilombardcabinet";
				showPage(url,"DocumentUpload");
		}*/
		/************************* End MHT-PID Process Integration **************************/
		
		if(pEventName.equalsIgnoreCase("click") && pControlName.equalsIgnoreCase("MHT_TYPE_OF_CATEGORY") && wsName.equalsIgnoreCase("BSG_DATAENTRY"))
		{
			System.out.println("executeMHTEvent(): MHT_TYPE_OF_CATEGORY onclick");
			String catValue = formObject.getNGValue("MHT_TYPE_OF_CATEGORY");
			if(catValue != null && !catValue.equalsIgnoreCase("--Select--"))
			{
				formObject.setNGEnable("FRAME_SEARCH_CRITERIA",true);
				if(catValue.equalsIgnoreCase("Policy Issuance"))
				{
					System.out.println("Policy Issuance started");
					formObject.setNGEnable("FRAME_TRANSACTION_TYPE",true);
					formObject.setNGEnable("MHT_TRANSACTION_TYPE",true);
					formObject.setNGValue("MHT_CASE_CATEGORY", "NORMAL");
				    formObject.setNGLocked("MHT_CASE_CATEGORY", false);
					formObject.setNGEnable("MHT_TYPE_OF_BUSINESS",true);
					formObject.setNGEnable("MHT_SEARCH_CRITERIA",true);
					formObject.setNGEnable("MHTSEARCH_STRING",true);
					formObject.setNGEnable("MHT_ENDORSEMENT_NO",false);
					formObject.setNGEnable("MHT_FINAL_QUOTE_NO",false);
					/*************************** MHT-PID Process Integration ****************************/
					//formObject.setNGListIndex("MHT_PID_PAYMENT_TYPE",1);					
					formObject.setNGEnable("MHT_PID_PAYMENT_TYPE",true);
					formObject.setNGLocked("MHT_PID_PAYMENT_TYPE",true);
					formObject.setNGListIndex("MHT_ENDORSEMENT_TYPE",0);
					formObject.setNGEnable("MHT_ENDORSEMENT_TYPE",false);
					formObject.setNGLocked("MHT_ENDORSEMENT_TYPE",false);
					/************************* End MHT-PID Process Integration **************************/
					
				}
				else if(catValue.equalsIgnoreCase("Endorsement"))
				{
					 System.out.println("Endorsement started");
					 formObject.setNGValue("MHT_CASE_CATEGORY", "");
				     formObject.setNGEnable("MHT_CASE_CATEGORY",false);
					 formObject.setNGListIndex("MHT_TYPE_OF_BUSINESS",0);
					 formObject.setNGEnable("MHT_TYPE_OF_BUSINESS",false);
					 formObject.setNGEnable("MHT_SEARCH_CRITERIA",false);
					 formObject.setNGEnable("MHTSEARCH_STRING",false);
					 
					 if(formObject.isNGEnable("FRAME_TRANSACTION_TYPE"))
					 {
						formObject.setNGEnable("FRAME_TRANSACTION_TYPE",false);
					 }
					 formObject.setNGEnable("MHT_FINAL_QUOTE_NO",false);
					 formObject.setNGEnable("MHT_ENDORSEMENT_NO",true);
					 /*************************** MHT-PID Process Integration ****************************/
					/*formObject.setNGListIndex("MHT_PID_PAYMENT_TYPE",2);
					formObject.setNGEnable("MHT_PID_PAYMENT_TYPE",false);
					formObject.setNGLocked("MHT_PID_PAYMENT_TYPE",false);*/
					formObject.setNGEnable("MHT_ENDORSEMENT_TYPE",true);
					formObject.setNGLocked("MHT_ENDORSEMENT_TYPE",true);
					formObject.setNGEnable("FRAME_MODEOF_PAYMENT",true);
					formObject.setNGLocked("FRAME_MODEOF_PAYMENT",true);
					/************************* End MHT-PID Process Integration **************************/
					 
					 
				}
			}
		}
		else if(pEventName.equalsIgnoreCase("click") && pControlName.equalsIgnoreCase("MHT_TYPE_OF_BUSINESS") && wsName.equalsIgnoreCase("BSG_DATAENTRY"))
		{
			System.out.println("executeMHTEvent(): MHT_TYPE_OF_BUSINESS onclick");
			String busiValue = formObject.getNGValue("MHT_TYPE_OF_BUSINESS");
			
			if(busiValue != null && !busiValue.equalsIgnoreCase("--Select--"))
			{
				formObject.setNGEnable("FRAME_SEARCH_CRITERIA",true);
				//boolean bool = ic_ClearControl.clear_all_control();
				if(busiValue.equalsIgnoreCase("Intermediary"))
				{
					formObject.setNGEnable("MHT_CASE_CATEGORY", true);
					formObject.setNGEnable("FRAME_TRANSACTION_TYPE",true);
					formObject.setNGEnable("MHT_SEARCH_CRITERIA",true);
				}
				else if(busiValue.equalsIgnoreCase("Direct"))
				{
					//formObject.setNGEnable("MHT_CASE_CATEGORY", false);
					formObject.setNGEnable("MHT_CASE_CATEGORY", false);
					formObject.setNGEnable("MHT_SEARCH_CRITERIA",false);
                    
				}
				
			}
		}		
		/*************************** MHT-PID Process Integration ****************************/
		if(pEventName.equalsIgnoreCase("click") && pControlName.equalsIgnoreCase("MHT_PAS_SYSTEM") && ( wsName.equalsIgnoreCase("BSG_DATAENTRY")))
		{
			System.out.println("executeMHTEvent(): MHT_PAS_SYSTEM onclick");
			String pasSystemValue = formObject.getNGValue("MHT_PAS_SYSTEM");
			if(pasSystemValue != null && !pasSystemValue.equalsIgnoreCase("--Select--"))
			{
				if(pasSystemValue.equalsIgnoreCase("OM"))
				{
					System.out.println("event Om case="+formObject.getNGValue("MHT_PAS_SYSTEM"));
					formObject.setNGValue("MHT_FINAL_QUOTE_NO", "");
					formObject.setNGEnable("MHT_FINAL_QUOTE_NO",false);
					formObject.setNGLocked("MHT_FINAL_QUOTE_NO",false);
				}
				else 
				{
					System.out.println("event else Om case="+formObject.getNGValue("MHT_PAS_SYSTEM"));
					formObject.setNGEnable("MHT_FINAL_QUOTE_NO",true);
					formObject.setNGLocked("MHT_FINAL_QUOTE_NO",true);                    
				}				
			}
		}
		
		if(pEventName.equalsIgnoreCase("click") && pControlName.equalsIgnoreCase("MHT_PAS_SYSTEM") && (wsName.equalsIgnoreCase("COPS_Team") || wsName.equalsIgnoreCase("COPS_Endorsement") || wsName.equalsIgnoreCase("COPS_QC") || wsName.equalsIgnoreCase("COPS_Priority") || wsName.equalsIgnoreCase("COPS_IPartner")))
		{
			System.out.println("executeMHTEvent(): MHT_PAS_SYSTEM onclick");
			String pasSystemValue = formObject.getNGValue("MHT_PAS_SYSTEM");
			if(pasSystemValue != null && !pasSystemValue.equalsIgnoreCase("--Select--"))
			{
				if(pasSystemValue.equalsIgnoreCase("Pathfinder"))
				{
					formObject.setNGEnable("MHT_PATHFINDER_PROPOSAL_NO", true);
					formObject.setNGLocked("MHT_PATHFINDER_PROPOSAL_NO", true);
					formObject.setNGValue("MHT_OM_POLICY_NO","");
					formObject.setNGLocked("MHT_OM_POLICY_NO",false);
				}
				else if(pasSystemValue.equalsIgnoreCase("OM"))
				{
					formObject.setNGEnable("MHT_OM_POLICY_NO", true);
					formObject.setNGLocked("MHT_OM_POLICY_NO", true);
					formObject.setNGValue("MHT_PATHFINDER_PROPOSAL_NO","");
					formObject.setNGLocked("MHT_PATHFINDER_PROPOSAL_NO",false);                    
				}					
			}
		}
		
		if((pEventName.equalsIgnoreCase("change") || pEventName.equalsIgnoreCase("KEYPRESS")) && pControlName.equalsIgnoreCase("MHT_pREMIUM_AMOUNT") && (wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DATAENTRY_QC") || wsName.equalsIgnoreCase("BSG_Endorsement")))
		{
			int premium_amt = Integer.parseInt(formObject.getNGValue("MHT_pREMIUM_AMOUNT"));
			System.out.println("premium_amt==="+premium_amt);
			if(premium_amt>=100000)
			{
				//JOptionPane.showMessageDialog(null,"PAN No is enabled !!");
				formObject.setNGEnable("MHT_PAN_CARD_NO",true);
				formObject.setNGLocked("MHT_PAN_CARD_NO",true);
			}
			else
			{
				formObject.setNGValue("MHT_PAN_CARD_NO","");
				formObject.setNGEnable("MHT_PAN_CARD_NO",false);
				formObject.setNGLocked("MHT_PAN_CARD_NO",false);
			
			}
		}
		
		//setting calendar name on il_location change event
		if((pEventName.equalsIgnoreCase("change") || pEventName.equalsIgnoreCase("KEYPRESS")) && (pControlName.equalsIgnoreCase("MHT_IL_LOCATION") || pControlName.equalsIgnoreCase("MHT_IL_LOCATION_CODE")))
		{
			String loc_name = formObject.getNGValue("MHT_IL_LOCATION");
			String loc_code = formObject.getNGValue("MHT_IL_LOCATION_CODE");
			System.out.println("loc_name==="+loc_name);
			System.out.println("loc_code==="+loc_code);			
			String locQuery = "SELECT CAL_NAME FROM NG_MHT_CAL_MAPPING_MASTER WHERE STATE_ID=(SELECT STATEID FROM NG_MHT_MST_ILLOCATION WHERE ILBRANCHCODE='"+loc_code+"')";
			String v_CalName= "";
			
			ArrayList getCalname = formObject.getNGDataFromDataSource(locQuery, 1);
				if (getCalname != null) {
					for (int i = 0; i < getCalname.size(); i++) {
						v_CalName = (getCalname.get(i)).toString();
						v_CalName = v_CalName.substring((v_CalName.indexOf("[") + 1), (v_CalName.indexOf("]")));
						System.out.println("v_CalName ::" + i + "\t" + v_CalName);					
					}
			}
			if(!v_CalName.equalsIgnoreCase(""))
			{				
				formObject.setNGValue("MHT_CALENDAR_NAME",v_CalName);
				System.out.println("Calendar_name successfully set for il_location===> "+loc_code);		
			}
			else
			{
				formObject.setNGValue("MHT_CALENDAR_NAME","Cal_Common");
				System.out.println("Calendar_name not found for il_location===> "+loc_code);	
			}				
		}
		/************************* End MHT-PID Process Integration **************************/		
         //event for il location change
        //else if(pEventName.equalsIgnoreCase("KeyPress(F3)") && (pControlName.equalsIgnoreCase("MHT_IL_LOCATION") && dealNo.equals("")) && (wsName.equalsIgnoreCase("BSG_DATAENTRY"))) //CR-OMHT-1314-02 Omniflow_development for ILLocation Changes
		else if(pEventName.equalsIgnoreCase("KeyPress(F3)") && (pControlName.equalsIgnoreCase("MHT_IL_LOCATION") || pControlName.equalsIgnoreCase("MHT_IL_LOCATION_CODE")) && (wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DATAENTRY_QC") || wsName.equalsIgnoreCase("BSG_Endorsement") || wsName.equalsIgnoreCase("IPartner_Global")))//MHT-PID Process Integration - Search provided on both location name and code //CR-8127-83510 : MHT/PID Enhancement Development
         {
             System.out.println("CR-8127-83510 : Start MHT/PID Enhancement Development :  Enabling Location F3 In IPartner_Global Bucket");
			 String name = "Branch Name,Branch code,Zone";
             picklist = formObject.getNGPickList(pControlName, name, true);
             pl = new PickListListener(formObject,pControlName,picklist);
             picklist.addPickListListener(pl);
             picklist.setSearchEnable(true);
             picklist.setVisible(true);
         }
		 /*************************** MHT-PID Process Integration ****************************/
		//modified code to fetch vertical code also
		 else if(pEventName.equalsIgnoreCase("KeyPress(F3)") && pControlName.equalsIgnoreCase("MHT_PRIMARY_VERTICAL") && ((businessType != null && businessType.equalsIgnoreCase("Direct")) || (typeOfCat != null && typeOfCat.equalsIgnoreCase("Endorsement"))) && (wsName.equalsIgnoreCase("BSG_DATAENTRY")))
         {
             String name = "Primary Vertical,Primary Vertical Code";
             picklist = formObject.getNGPickList(pControlName, name, true);
             pl = new PickListListener(formObject,pControlName,picklist);
             picklist.addPickListListener(pl);
             picklist.setSearchEnable(true);
             picklist.setVisible(true);
         }
		  else if(pEventName.equalsIgnoreCase("KeyPress(F3)") && pControlName.equalsIgnoreCase("MHT_SUB_VERTICAL") && ((businessType != null && businessType.equalsIgnoreCase("Direct")) || (typeOfCat != null && typeOfCat.equalsIgnoreCase("Endorsement"))) && (wsName.equalsIgnoreCase("BSG_DATAENTRY")))
         {
             String name = "Secondary Vertical,Secondary Vertical Code";
             picklist = formObject.getNGPickList(pControlName, name, true);
             pl = new PickListListener(formObject,pControlName,picklist);
             picklist.addPickListListener(pl);
             picklist.setSearchEnable(true);
             picklist.setVisible(true);
         }
		 /*********************** End MHT-PID Process Integration ****************************/
		  else if(pEventName.equalsIgnoreCase("KeyPress(F3)") && pControlName.equalsIgnoreCase("MHT_PRODUCT_NAME") && ((businessType != null && businessType.equalsIgnoreCase("Direct")) || (typeOfCat != null && typeOfCat.equalsIgnoreCase("Endorsement"))) && (wsName.equalsIgnoreCase("BSG_DATAENTRY")))
         {
             String name = "Product Name,Product Code";
             picklist = formObject.getNGPickList(pControlName, name, true);
             pl = new PickListListener(formObject,pControlName,picklist);
             picklist.addPickListListener(pl);
             picklist.setSearchEnable(true);
             picklist.setVisible(true);
         }
         else if((pEventName.equalsIgnoreCase("KeyPress(F3)")) && (pControlName.equalsIgnoreCase("MHTSEARCH_STRING")) && (wsName.equalsIgnoreCase("BSG_DATAENTRY")))
         {
			System.out.println("search criteria value"+formObject.getNGValue("MHT_SEARCH_CRITERIA"));
            if(!formObject.getNGValue("MHT_SEARCH_CRITERIA").equals("") && !formObject.getNGValue("MHT_SEARCH_CRITERIA").equalsIgnoreCase("Manual CN"))
            {
				System.out.println("Deal scenario=="+formObject.getNGValue("MHT_SEARCH_CRITERIA"));
				String name="Agent Name, Agent Code, Deal No, Deal Status,CoverNote Type";
				picklist=formObject.getNGPickList(pControlName, name,true) ;
				pl=new PickListListener(formObject,pControlName,picklist);
				picklist.addPickListListener(pl);
				picklist.setVisible(true);
            }
            else if(!formObject.getNGValue("MHT_SEARCH_CRITERIA").equals("") && formObject.getNGValue("MHT_SEARCH_CRITERIA").equalsIgnoreCase("Manual CN"))
            {
					if(formObject.getNGValue("MHTSEARCH_STRING").equals(""))
					{
						JOptionPane.showMessageDialog(null,"Please Enter Manual CN Number");
					}
					System.out.println("Manual CN=="+formObject.getNGValue("MHT_SEARCH_CRITERIA"));
                    String name="Manual CN,Deal No,Manual CN Acceptance";
                    picklist=formObject.getNGPickList(pControlName, name,true) ;
                    pl=new PickListListener(formObject,pControlName,picklist);
                    picklist.addPickListListener(pl);
                    picklist.setVisible(true);
            }
			else
            {
                    JOptionPane.showMessageDialog(null,"Please select the search criteria first !!");
					formObject.NGFocus("MHT_SEARCH_CRITERIA");
            }
         }
         //event for Source Business
         else if((pEventName.equalsIgnoreCase("KeyPress(F3)")) && (pControlName.equalsIgnoreCase("MHT_SOURCE_BUSINESS")) && (wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DATAENTRY_QC")))
         {
                String name="Source Business";
                picklist=formObject.getNGPickList(pControlName, name,true) ;
                pl=new PickListListener(formObject,pControlName,picklist);
                picklist.addPickListListener(pl);
                picklist.setSearchEnable(true);
                picklist.setVisible(true);
         }
         ////event for channel source
        else if((pEventName.equalsIgnoreCase("KeyPress(F3)")) && (pControlName.equalsIgnoreCase("MHT_CHANNEL_SOURCE")) && (wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DATAENTRY_QC") || wsName.equalsIgnoreCase("BSG_Endorsement")))
        {
				System.out.println(" KeyPress(F3) MHT_CHANNEL_SOURCE==");
                String name="Channel Source";
                picklist=formObject.getNGPickList(pControlName, name,true) ;
                pl=new PickListListener(formObject,pControlName,picklist);
                picklist.addPickListListener(pl);
                picklist.setSearchEnable(true);
                picklist.setVisible(true);
        }
		////event for BBG bank branch name
		/*************************** MHT-PID Process Integration ****************************/
        /*else if((pEventName.equalsIgnoreCase("KeyPress(F3)")) && (pControlName.equalsIgnoreCase("MHT_BANK_BRANCH_NAME")) && (wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DATAENTRY_QC") || wsName.equalsIgnoreCase("BSG_Endorsement")))
        {
                String name="Bank Branch Name";

                System.out.println("inside event--"+pControlName);
                picklist=formObject.getNGPickList(pControlName, name,true) ;
                pl=new PickListListener(formObject,pControlName,picklist);
                picklist.addPickListListener(pl);
                picklist.setSearchEnable(true);
                picklist.setVisible(true);
		 } */
				
        /**************************Start MHT SP Code CR-8127-69652 ******************************/
		////event for BBG bank branch name
		else if((pEventName.equalsIgnoreCase("KeyPress(F3)")) && (pControlName.equalsIgnoreCase("MHT_BANK_BRANCH_NAME")) && (wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DATAENTRY_QC")) && (typeOfCat != null && typeOfCat.equalsIgnoreCase("Policy Issuance")) && (businessType != null && businessType.equalsIgnoreCase("Intermediary") && !formObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER")) && (formObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BANCASSURANCE") && formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING")))
        {	
			String name="Bank Branch Name,SOL ID";
			System.out.println("inside event--"+pControlName);
			picklist=formObject.getNGPickList(pControlName, name,true) ;
			pl=new PickListListener(formObject,pControlName,picklist);
			picklist.addPickListListener(pl);
			picklist.setSearchEnable(true);
			picklist.setVisible(true);
		}
		else if((pEventName.equalsIgnoreCase("KeyPress(F3)")) && (pControlName.equalsIgnoreCase("MHT_BRANCH_ID")) && (wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DATAENTRY_QC") || wsName.equalsIgnoreCase("BSG_Endorsement")))
         {
			if((typeOfCat != null && typeOfCat.equalsIgnoreCase("Policy Issuance")) && (businessType != null && businessType.equalsIgnoreCase("Intermediary")) && (formObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BANCASSURANCE") && !formObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER")) && (formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING")))
			{//yy 
				 String name = "SOL ID,Bank Branch Name,";
				 picklist = formObject.getNGPickList(pControlName, name, true);
				 pl = new PickListListener(formObject,pControlName,picklist);
				 picklist.addPickListListener(pl);
				 picklist.setSearchEnable(true);
				 picklist.setVisible(true);
			}
			else
			{
				String name="BankBranchId,BankBranchName,SPcode,SPname";
				System.out.println("inside event--"+pControlName);
				picklist=formObject.getNGPickList(pControlName, name,true) ;
				pl=new PickListListener(formObject,pControlName,picklist);
				picklist.addPickListListener(pl);
				picklist.setSearchEnable(true);
				picklist.setVisible(true);
			}
		}
		else if((pEventName.equalsIgnoreCase("KeyPress(F3)")) && (pControlName.equalsIgnoreCase("MHT_SP_CODE")) && (wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DATAENTRY_QC"))&&(typeOfCat != null && typeOfCat.equalsIgnoreCase("Policy Issuance")) && (businessType != null && businessType.equalsIgnoreCase("Intermediary")) && (formObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BANCASSURANCE") && !formObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER"))&&(formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING")))
		{			
			String name="SP Code,SP Name,SP PAN";
			System.out.println("inside event"+pControlName);
			picklist=formObject.getNGPickList(pControlName, name,true) ;	
			pl=new PickListListener(formObject,pControlName,picklist);
			picklist.addPickListListener(pl);
			picklist.setSearchEnable(true);
			picklist.setVisible(true);
			
		}
		/***************************Start SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI ******************************************/
		else if((pEventName.equalsIgnoreCase("KeyPress(F3)")) && (pControlName.equalsIgnoreCase("MHT_SP_CODE")) && (wsName.equalsIgnoreCase("BSG_DATAENTRY")) && !((typeOfCat != null && typeOfCat.equalsIgnoreCase("Policy Issuance")  && businessType != null && businessType.equalsIgnoreCase("Direct"))) && (formObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BANCASSURANCE") || formObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BA")) && (formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("KRG")) && !(formObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER")))
		{			
			System.out.println("*******Start SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI ******");
			String name="SP Code,SP Name";
			System.out.println("inside event sp code krg f3 yogesh:"+pControlName);
			picklist=formObject.getNGPickList(pControlName, name,true) ;	
			pl=new PickListListener(formObject,pControlName,picklist);
			picklist.addPickListListener(pl);
			picklist.setSearchEnable(true);
			picklist.setVisible(true);			
		}		
		
		/***************************End SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI ******************************************/
		/******Start MHT CR-8127-95325-GST-Omniflow development******/
		else if((pEventName.equalsIgnoreCase("KeyPress(F3)")) && (pControlName.equalsIgnoreCase("MHT_TXTGST_STATE_NAME")) && (wsName.equalsIgnoreCase("BSG_DATAENTRY")|| wsName.equalsIgnoreCase("BSG_DataEntry_QC")|| wsName.equalsIgnoreCase("BSG_Exception") || wsName.equalsIgnoreCase("RM_Exception")) && (formObject.getNGValue("MHT_GST_REGISTERED").equalsIgnoreCase("Yes")) && !formObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER"))
		{
			System.out.println("Inside MHT CR-8127-95325-GST-Omniflow development Keypress event");
			String name="State Name";
			System.out.println("inside event MHT CR-8127-95325-GST-Omniflow development f3 yogesh:"+pControlName);
			picklist=formObject.getNGPickList(pControlName, name,true) ;	
			pl=new PickListListener(formObject,pControlName,picklist);
			picklist.addPickListListener(pl);
			picklist.setSearchEnable(true);
			picklist.setVisible(true);
		}
		else if(pEventName.equalsIgnoreCase("Click") && pControlName.equalsIgnoreCase("MHT_GST_REGISTERED") && (wsName.equalsIgnoreCase("BSG_DATAENTRY")|| wsName.equalsIgnoreCase("BSG_DataEntry_QC")|| wsName.equalsIgnoreCase("BSG_Exception") || wsName.equalsIgnoreCase("RM_Exception")) && !formObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER"))
		{
			System.out.println("Inside MHT CR-8127-95325-GST-Omniflow development change event");			
			if(formObject.getNGValue("MHT_GST_REGISTERED").equalsIgnoreCase("Yes"))
			{
				System.out.println("inside enable sub if executeMHTEvent MHT CR-8127-95325-GST-Omniflow development");
				formObject.setNGEnable("MHT_TXTGST_NUMBER",true);								
				formObject.setNGEnable("MHT_TXTGST_STATE_NAME",true);
				formObject.setNGLocked("MHT_TXTGST_STATE_NAME",false);
				formObject.setNGEnable("Add_GST",true);
				formObject.setNGEnable("Mod_GST",true);
				formObject.setNGEnable("Del_GST",true);
				formObject.setNGEnable("MHT_qGstGrdVar",true);
			}
			else
			{
				System.out.println("inside disable sub else executeMHTEvent MHT CR-8127-95325-GST-Omniflow development");
				formObject.setNGValue("MHT_TXTGST_NUMBER","");
				formObject.setNGValue("MHT_TXTGST_STATE_NAME","");
				formObject.NGClear("MHT_qGstGrdVar");
				formObject.setNGEnable("MHT_TXTGST_NUMBER",false);
				formObject.setNGEnable("MHT_TXTGST_STATE_NAME",false);
				formObject.setNGEnable("Add_GST",false);
				formObject.setNGEnable("Mod_GST",false);
				formObject.setNGEnable("Del_GST",false);
				formObject.setNGEnable("MHT_qGstGrdVar",true);
			}
		}		
		/******End MHT CR-8127-95325-GST-Omniflow development******/

		else if((pEventName.equalsIgnoreCase("Change")) && (pControlName.equalsIgnoreCase("MHT_CHANNEL_SOURCE")) && (wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DATAENTRY_QC") || wsName.equalsIgnoreCase("BSG_Endorsement")))
		{
			if((typeOfCat != null && typeOfCat.equalsIgnoreCase("Policy Issuance")) && (businessType != null && businessType.equalsIgnoreCase("Intermediary")) && (formObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BANCASSURANCE"))&&(formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING")))
			{
			System.out.println("Inside SP code validation");
			formObject.setNGValue("MHT_BANK_BRANCH_NAME","");
			formObject.setNGValue("MHT_BRANCH_ID","");
			formObject.setNGValue("MHT_SP_CODE","");
			formObject.setNGValue("MHT_SP_NAME","");
			formObject.setNGValue("MHT_SP_PAN","");
			System.out.println("All SP Clear on Channel source");
			}
		}
		else if((pEventName.equalsIgnoreCase("Change")) && (pControlName.equalsIgnoreCase("MHT_BANK_BRANCH_NAME") || pControlName.equalsIgnoreCase("MHT_BRANCH_ID")) && (wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DATAENTRY_QC") || wsName.equalsIgnoreCase("BSG_Endorsement")))
		{
			if((typeOfCat != null && typeOfCat.equalsIgnoreCase("Policy Issuance")) && (businessType != null && businessType.equalsIgnoreCase("Intermediary")) && (formObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BANCASSURANCE"))&&(formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING")))
			{
				formObject.setNGValue("MHT_SP_CODE","");
				formObject.setNGValue("MHT_SP_NAME","");
				formObject.setNGValue("MHT_SP_PAN","");
				System.out.println("All SP Clear on solid and branch name");
			}
		}
		/***************************END MHT-PID Process Integration ****************************/
		/**************************End MHT SP Code CR-8127-69652 ******************************/
		 ////event for branch id ubo name
        else if((pEventName.equalsIgnoreCase("KeyPress(F3)")) && (pControlName.equalsIgnoreCase("MHT_BRANCH_ID_UBO_NAME")) && (wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DATAENTRY_QC") || wsName.equalsIgnoreCase("BSG_Endorsement")))
        {
                String name="Branch ID UBO Name";
                System.out.println("inside event--"+pControlName);
                picklist=formObject.getNGPickList(pControlName, name,true) ;
                pl=new PickListListener(formObject,pControlName,picklist);
                picklist.addPickListListener(pl);
                picklist.setSearchEnable(true);
                picklist.setVisible(true);
        }
         ////event for sm name
        else if((pEventName.equalsIgnoreCase("KeyPress(F3)")) && (pControlName.equalsIgnoreCase("MHT_SM_NAME")) && (wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DATAENTRY_QC") || wsName.equalsIgnoreCase("BSG_Endorsement")))
        {
                String name="SM NAME, SM ID";
                System.out.println("inside event--"+pControlName);
                picklist=formObject.getNGPickList(pControlName, name,true) ;
                pl=new PickListListener(formObject,pControlName,picklist);
                picklist.addPickListListener(pl);
                picklist.setSearchEnable(true);
                picklist.setVisible(true);
        }		
		// ============= Check SM User in the OmniFlow ====================
		/*else if(pEventName.equalsIgnoreCase("LOSTFOCUS") && pControlName.equalsIgnoreCase("MHT_SM_NAME") && (wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DATAENTRY_QC")))
        {
            Validate_SM("MHT_SM_ID");
		}*/

        ////event for bank name
        else if(pEventName.equalsIgnoreCase("KeyPress(F3)") && (pControlName.equalsIgnoreCase("MHT_PAYMENT_TYPE1_BANKNAME") || pControlName.equalsIgnoreCase("MHT_PAYMENT_TYPE2_BANKNAME") || pControlName.equalsIgnoreCase("MHT_PAYMENT_TYPE3_BANKNAME")) && (wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DATAENTRY_QC") || wsName.equalsIgnoreCase("BSG_Endorsement")))
        {
                String name="Bank Name,Bank code";
                picklist=formObject.getNGPickList(pControlName, name,true) ;
                pl=new PickListListener(formObject,pControlName,picklist);
                picklist.addPickListListener(pl);
                 picklist.setSearchEnable(true);
                picklist.setVisible(true);
        }
		
		/*************************** MHT-PID Process Integration ****************************/
		////event for Payment Mode
        else if(pEventName.equalsIgnoreCase("KeyPress(F3)") && (pControlName.equalsIgnoreCase("MHT_PAYMENT_TYPE1") || pControlName.equalsIgnoreCase("MHT_PAYMENT_TYPE2") || pControlName.equalsIgnoreCase("MHT_PAYMENT_TYPE3")) && (wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DATAENTRY_QC") || wsName.equalsIgnoreCase("BSG_Endorsement")))
        {
                String name="Payment Mode";
                picklist=formObject.getNGPickList(pControlName, name,true) ;
                pl=new PickListListener(formObject,pControlName,picklist);
                picklist.addPickListListener(pl);
                picklist.setSearchEnable(true);
                picklist.setVisible(true);
        }
		/************************* End MHT-PID Process Integration **************************/
		
         //center code
        else if((pEventName.equalsIgnoreCase("KeyPress(F3)")) && (pControlName.equalsIgnoreCase("MHT_CENTER_CODE_NAME")) && (wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DATAENTRY_QC") || wsName.equalsIgnoreCase("BSG_Endorsement")))
        {
                String name="Center Code Name";
                picklist=formObject.getNGPickList(pControlName, name,true) ;
                pl=new PickListListener(formObject,pControlName,picklist);
                picklist.addPickListListener(pl);
                 picklist.setSearchEnable(true);
                picklist.setVisible(true);
        }

         //source business
        else if(pEventName.equalsIgnoreCase("Change") && pControlName.equalsIgnoreCase("MHT_SOURCE_BUSINESS"))
        {
			String field[] = new String[1];
			String noOfCols = "1";
			/**************************Start MHT SP Code CR-8127-69652 ******************************/
			if((typeOfCat != null && typeOfCat.equalsIgnoreCase("Policy Issuance")) && (businessType != null && businessType.equalsIgnoreCase("Intermediary")) && (formObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BANCASSURANCE"))&&(formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING")))
			{
				formObject.setNGValue("MHT_CHANNEL_SOURCE","");
				formObject.setNGValue("MHT_BANK_BRANCH_NAME","");
				formObject.setNGValue("MHT_BRANCH_ID","");
				formObject.setNGValue("MHT_SP_CODE","");
				formObject.setNGValue("MHT_SP_NAME","");
				formObject.setNGValue("MHT_SP_PAN","");
				System.out.println("All SP clear on source business");
			}
			/**************************End MHT SP Code CR-8127-69652 ******************************/
            if(wsName.equalsIgnoreCase("BSG_DATAENTRY") | wsName.equalsIgnoreCase("BSG_DATAENTRY_QC") | wsName.equalsIgnoreCase("BSG_Endorsement"))
            {
                if(formObject.getNGValue("MHT_SOURCE_BUSINESS").equalsIgnoreCase("Center Sales") || formObject.getNGValue("MHT_SOURCE_BUSINESS").equalsIgnoreCase("Centre Sales"))
                {
                    formObject.setNGEnable("FRAME_CENTER_CODE",true);
					field[0]="MHT_CENTER_CODE_NAME";
					String query="select CENTCODE_NAME  from  NG_MHT_MST_CENTERCODE order by CENTCODE_NAME";
					getData(query,noOfCols,field,flag);
                }
                else
                {
                    formObject.setNGEnable("FRAME_CENTER_CODE",true);
                    formObject.setNGListIndex("MHT_CENTER_CODE_NAME",0);
                    formObject.setNGValue("MHT_RM_EMPLOYEE","");
                    formObject.setNGEnable("FRAME_CENTER_CODE",false);
                }
            }
        }
         //prodcut change
        else if(pEventName.equalsIgnoreCase("Change") && pControlName.equalsIgnoreCase("MHT_PRODUCT_NAME"))
        {
			if(wsName.equalsIgnoreCase("BSG_DATAENTRY"))
			{
				String fieldName="MHT_PRODUCT_NAME";
				String fieldValue=formObject.getNGValue(fieldName);
				String cValue = formObject.getNGValue("MHT_TYPE_OF_CATEGORY");
				String bValue = formObject.getNGValue("MHT_TYPE_OF_BUSINESS");
				String searchValue = formObject.getNGValue("MHT_SEARCH_CRITERIA");
				System.out.println("searchValue=="+searchValue);
				System.out.println("cValue=="+formObject.getNGValue("MHT_TYPE_OF_CATEGORY"));
				System.out.println("bValue=="+formObject.getNGValue("MHT_TYPE_OF_BUSINESS"));
						   
				String field[]= {"ProductList"};
				String productlistQuery = "select productname,producttype,productcode from NG_MHT_MST_Product";
				noOfCols="3";
				System.out.println("satish"+formObject.getNGValue("MHT_PRODUCT_NAME"));
				getData(productlistQuery,noOfCols,field,flag);
				System.out.println("(map.size()=="+map.size());
				System.out.println("Flag value=="+map.containsKey(formObject.getNGValue("MHT_PRODUCT_NAME").trim()));
				if(map.size() > 1 && map.containsKey(formObject.getNGValue("MHT_PRODUCT_NAME").trim()))
				{
					System.out.println("map BSG_DATAENTRY bucket=="+map.containsKey(formObject.getNGValue("MHT_PRODUCT_NAME")));
					String prodCodeType = (String)map.get(formObject.getNGValue("MHT_PRODUCT_NAME"));
					String type[] = prodCodeType.split("\\:");
					System.out.println("prodCodeType=="+prodCodeType);
					System.out.println("type=="+type);
					if(type != null && type.length >0)
					{
						formObject.setNGValue("MHT_PRODUCT_TYPE",type[0]);
						formObject.setNGValue("MHT_PRODUCT_CODE",type[1]);
						if(type[0].equalsIgnoreCase("MOTOR"))
						{
							//formObject.setNGEnable("MHT_ALTERNATE_POLICY_NUMBER",true);
							System.out.println("type[0]=="+type[0]);
							System.out.println("catValue1=="+cValue);
							if(!cValue.equals("") && cValue.equalsIgnoreCase("Policy Issuance"))
							{
								System.out.println("cValue2=="+cValue);
								formObject.setNGEnable("MHT_FINAL_QUOTE_NO",true);
							}
							if(searchValue != null && !searchValue.trim().equals("") && !searchValue.equalsIgnoreCase("--Select--"))
							{
								System.out.println("searchValue sat=="+searchValue);
								/*if(searchValue.equalsIgnoreCase("Manual CN"))
								{
									formObject.setNGValue("MHT_COVER_NOTE_TYPE","Manual CN");
								}
								else 
								{
									formObject.setNGValue("MHT_COVER_NOTE_TYPE","Auto CN");
								} */
							}
						}
						else if(!type[0].equalsIgnoreCase("MOTOR"))
						{
							formObject.setNGValue("MHT_COVER_NOTE_TYPE","");
							formObject.setNGEnable("MHT_COVER_NOTE_TYPE",false);
						}
						
					}
					
					
					map = null;
				}
			}
            

            
        }
		else if(pEventName.equalsIgnoreCase("Change") && pControlName.equalsIgnoreCase("MHT_SUB_VERTICAL"))
		{	
			System.out.println("test MHT_SUB_VERTICAL Change====");
			fieldName= "MHT_SUB_VERTICAL";
			fieldValue=formObject.getNGValue(fieldName);
			String primValue = formObject.getNGValue("MHT_PRIMARY_VERTICAL");
			if(debug==1)
			{
				System.out.println("fieldValue of sub vertical----"+fieldValue);
			}
			
			if(wsName.equalsIgnoreCase("BSG_DATAENTRY"))
			{
				fieldValue=fieldValue.trim();
				//enabling/diabling the controls
				System.out.println("change in sub vertical===="+fieldValue);
				
				//formObject.setNGEnable("MHT_SUB_VERTICAL",false);
				
				if(debug==1)
				{
					System.out.println("clearing fields false");
				}
				
				/*formObject.setNGEnable("MHT_SOURCE_BUSINESS",false);
				formObject.setNGEnable("MHT_CHANNEL_SOURCE",false);
				
				formObject.setNGEnable("FRAME_ISEMP_CODE",false);
				//formObject.setNGEnable("FRAME_WRE_WRM",false);
				
				formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",false);
				formObject.setNGEnable("FRAME_CHANL_EMP",false);
				formObject.setNGEnable("FRAME_BSM",false); */
				
				//clear the dependent fields
				
				formObject.setNGValue("MHT_SOURCE_BUSINESS","");
				formObject.setNGValue("MHT_CHANNEL_SOURCE","");
				formObject.setNGValue("MHT_EMPCODE_CSO","");
								
				
				formObject.setNGValue("MHT_CHANNEL_EMP_INFO","");
				formObject.NGClear("MHT_BRANCH_ID_UBO_NAME");
				formObject.setNGValue("MHT_BSM_ID","");
				formObject.setNGValue("MHT_BCM_ID","");
				
				/**************************** CR-8127-83510 : Start MHT/PID Enhancement Development ***************************/
					System.out.println("CR-8127-83510 : Start MHT/PID Enhancement Development : Inside OnChange BSG_Dataentry");
					formObject.setNGEnable("FRAME_PRIVILEGE_BANKER_CODE",true);
					formObject.setNGLocked("FRAME_PRIVILEGE_BANKER_CODE",true);
					formObject.setNGEnable("MHT_PRIVILEGE_BANKER_CODE",false);
					formObject.setNGEnable("MHT_CIF_ID",false);
					formObject.setNGEnable("MHT_CALL_TAG_NUMBER",true);
					formObject.setNGLocked("MHT_CALL_TAG_NUMBER",true);
					
				/**************************** CR-8127-83510 : End MHT/PID Enhancement Development *****************************/
				
				
				/*if(!formObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase(""))
				{
					
					if (formObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("ISEC") && (formObject.getNGValue("MHT_SUB_VERTICAL")).equalsIgnoreCase("BBG") )
					{
						
						formObject.setNGEnable("FRAME_SOURCE_BUSINESS",true);
						formObject.setNGEnable("MHT_SOURCE_BUSINESS",true);
						formObject.setNGValue("MHT_CHANNEL_SOURCE","");
						formObject.setNGEnable("MHT_CHANNEL_SOURCE",false);
					}
					else if(formObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BBG") && (formObject.getNGValue("MHT_SUB_VERTICAL")).equalsIgnoreCase("BBG"))
					{
						formObject.setNGEnable("FRAME_SOURCE_BUSINESS",true);
						formObject.setNGEnable("MHT_SOURCE_BUSINESS",true);
						formObject.setNGEnable("MHT_CHANNEL_SOURCE",true);
						formObject.setNGEnable("FRAME_ISEMP_CODE",true);
						formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",true);
						
				
					}
					else if(formObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("Bancassurance") && (formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group")))
					{
						formObject.setNGEnable("FRAME_CHANNEL_EMP_INFO",true);
					}
					
				} */

			
				if(!fieldValue.equalsIgnoreCase("") && primValue != null && !primValue.equalsIgnoreCase(""))
				{
					System.out.println("else fieldValue value1===="+fieldValue);
					
				/*	if(fieldValue.equals("BBG") || fieldValue.equals("Branch Banking") || fieldValue.equals("BRANCH BRANCHING") || fieldValue.equals("Branch Banking (BBG)") || fieldValue.equalsIgnoreCase("Key Relationship Group") || fieldValue.equalsIgnoreCase("Key Relationship Group (KRG 1)") || fieldValue.equalsIgnoreCase("Relationship Group (KRG 2)"))
					{
						if(debug==1)
						{
							System.out.println("inside bbg/branch bank----"+fieldValue);
						}
						System.out.println("fieldValue value2===="+fieldValue);
						formObject.setNGEnable("FRAME_SELECT_VERTICAL",true);
						formObject.setNGEnable("FRAME_SOURCE_BUSINESS",true);
						
						
						formObject.setNGEnable("MHT_SOURCE_BUSINESS",true);
						formObject.setNGEnable("MHT_CHANNEL_SOURCE",true);
						formObject.setNGEnable("FRAME_ISEMP_CODE",true);
						formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",true);
						//formObject.setNGEnable("FRAME_CHANL_EMP",true);
						System.out.println("fieldValue value3===="+fieldValue);

					}
						
					if(fieldValue.equalsIgnoreCase("Key Relationship Group") || fieldValue.equalsIgnoreCase("Key Relationship Group (KRG 1)") || fieldValue.equalsIgnoreCase("Relationship Group (KRG 2)"))
					{
						//formObject.setNGEnable("FRAME_SOURCE_BUSINESS",true);
						formObject.setNGEnable("MHT_SOURCE_BUSINESS",true);
						formObject.setNGEnable("MHT_CHANNEL_SOURCE",true);
						formObject.setNGEnable("FRAME_CHANL_EMP",true);
						formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",false);
					}	

					if(fieldValue.equals("HOME"))
					{
						formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",true);
						formObject.setNGEnable("FRAME_BSM_BCM",true);
					} */
					
					
					 if((formObject.getNGValue("MHT_PRIMARY_VERTICAL") != null && formObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BBG") && formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BBG"))
                         || (formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BBG") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking (BBG)") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("KRG")))
                    {
                        System.out.println("all 1===="+fieldValue);
						formObject.setNGEnable("FRAME_ISEMP_CODE",true);
						formObject.setNGEnable("FRAME_SOURCE_BUSINESS",true);
                        formObject.setNGEnable("MHT_SOURCE_BUSINESS",true);
                        formObject.setNGEnable("MHT_CHANNEL_SOURCE",true);
						formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",true);
						//formObject.setNGEnable("FRAME_PRIVILEGE_BANKER_CODE",true);//MHT-PID Process Integration  //commented due to CR-8127-83510 : MHT/PID Enhancement CR 
						//formObject.setNGLocked("FRAME_PRIVILEGE_BANKER_CODE",true);//MHT-PID Process Integration  //commented due to CR-8127-83510 : MHT/PID Enhancement CR 
						/**************************** CR-8127-83510 : Start MHT/PID Enhancement Development ***************************/
						formObject.setNGEnable("MHT_PRIVILEGE_BANKER_CODE",true);
						formObject.setNGLocked("MHT_PRIVILEGE_BANKER_CODE",true);
						formObject.setNGEnable("MHT_CIF_ID",true);
						formObject.setNGLocked("MHT_CIF_ID",true);
						/**************************** CR-8127-83510 : End MHT/PID Enhancement Development *****************************/
                        //formObject.setNGEnable("FRAME_SP_CODE",true); //MHT-PID Process Integration
						formObject.setNGEnable("FRAME_CHANL_EMP",false);
                        if(formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("KRG"))
                        {
							System.out.println("all 2===="+fieldValue);
                            /*****Start SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI **********/
							//formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",false);commented yogesh for sp code logic change
						    /******End SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI *********/
							//formObject.setNGEnable("FRAME_PRIVILEGE_BANKER_CODE",false);//MHT-PID Process Integration  //commented due to CR-8127-83510 : MHT/PID Enhancement CR 
							//formObject.setNGLocked("FRAME_PRIVILEGE_BANKER_CODE",false);//MHT-PID Process Integration  //commented due to CR-8127-83510 : MHT/PID Enhancement CR 
                            formObject.setNGEnable("FRAME_CHANL_EMP",true);
                        }
                    }
                    else
                    {
						System.out.println("all else===="+fieldValue);
                        formObject.setNGEnable("FRAME_ISEMP_CODE",false);
						formObject.setNGEnable("FRAME_SOURCE_BUSINESS",false);
                        //formObject.setNGEnable("MHT_SOURCE_BUSINESS",false);
						//formObject.setNGEnable("MHT_CHANNEL_SOURCE",false);
						formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",false);
						//formObject.setNGEnable("FRAME_PRIVILEGE_BANKER_CODE",false);//MHT-PID Process Integration  //commented due to CR-8127-83510 : MHT/PID Enhancement CR 
						//formObject.setNGLocked("FRAME_PRIVILEGE_BANKER_CODE",false);//MHT-PID Process Integration  //commented due to CR-8127-83510 : MHT/PID Enhancement CR 
                        formObject.setNGEnable("FRAME_CHANL_EMP",false);
                        //formObject.setNGEnable("FRAME_SP_CODE",false); //MHT-PID Process Integration
                    } 
                    //enabling  disabling the bsm bcm on home sub vertical
                    if(formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("home"))
                    {
							System.out.println("home case===="+fieldValue);
                            formObject.setNGEnable("FRAME_BSM",true);
							formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",true);
							//formObject.setNGEnable("FRAME_PRIVILEGE_BANKER_CODE",true);//MHT-PID Process Integration  //commented due to CR-8127-83510 : MHT/PID Enhancement CR 
							//formObject.setNGLocked("FRAME_PRIVILEGE_BANKER_CODE",true);//MHT-PID Process Integration  //commented due to CR-8127-83510 : MHT/PID Enhancement CR 
							/**************************** CR-8127-83510 : Start MHT/PID Enhancement Development ***************************/
							formObject.setNGEnable("MHT_PRIVILEGE_BANKER_CODE",true);
							formObject.setNGLocked("MHT_PRIVILEGE_BANKER_CODE",true);
							formObject.setNGEnable("MHT_CIF_ID",true);
							formObject.setNGLocked("MHT_CIF_ID",true);
							/**************************** CR-8127-83510 : End MHT/PID Enhancement Development *****************************/
                    }
                    else
                    {
                            formObject.setNGEnable("FRAME_BSM",false);
                    }
					
					System.out.println("separate case===="+fieldValue);
					if (formObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("ISEC") && (formObject.getNGValue("MHT_SUB_VERTICAL")).equalsIgnoreCase("BBG") )
					{
						System.out.println("ISEC case===="+fieldValue);
						formObject.setNGEnable("FRAME_SOURCE_BUSINESS",true);
						formObject.setNGEnable("MHT_SOURCE_BUSINESS",true);
						formObject.setNGValue("MHT_CHANNEL_SOURCE","");
						formObject.setNGEnable("MHT_CHANNEL_SOURCE",false);
					}
					
					if(formObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("Bancassurance") && (formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("KRG")))
					{
						formObject.setNGEnable("FRAME_CHANL_EMP",true);
					}
					/*******************Start SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI**********************/
					if(!((formObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Policy Issuance") && formObject.getNGValue("MHT_TYPE_OF_BUSINESS").equalsIgnoreCase("Direct"))) && (formObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("Bancassurance") || formObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BA")) && (formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("KRG")) && !(formObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER")))
					{
						System.out.println("Inside the BRANCH_ID and BANK_BRANCH_NAME Disable");
						formObject.setNGEnable("MHT_BRANCH_ID",false);
						formObject.setNGEnable("MHT_BANK_BRANCH_NAME",false);						
					}
					/*******************End SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI**********************/
				}	
			
			}
			
			
		}
		
		/*************************** MHT-PID Process Integration ****************************/
		else if(pEventName.equalsIgnoreCase("click") && pControlName.equalsIgnoreCase("MHT_PID_PAYMENT_TYPE") && (wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DATAENTRY_QC") || wsName.equalsIgnoreCase("COPS_Calculator") || wsName.equalsIgnoreCase("BSG_Endorsement")) && formObject.getNGValue("MHT_TAG_STATUS").equalsIgnoreCase(""))
		{
			System.out.println("executeMHTEvent(): MHT_PID_PAYMENT_TYPE on change");
			String pidCase = formObject.getNGValue("MHT_PID_PAYMENT_TYPE");
			System.out.println("MHT_PID_PAYMENT_TYPE value: "+formObject.getNGValue("MHT_PID_PAYMENT_TYPE"));
			if(pidCase.equalsIgnoreCase("No") || pidCase.equalsIgnoreCase(""))
			{
				System.out.println("sat value1: "+formObject.getNGValue("MHT_PID_PAYMENT_TYPE"));
				formObject.setNGEnable("FRAME_MODEOF_PAYMENT", true);
				formObject.setNGLocked("FRAME_MODEOF_PAYMENT", true);
				formObject.setNGLocked("MHT_PF_PAYMENT_ID_NO",true);
				formObject.setNGLocked("MHT_PF_PAYMENT_ID_NO2",true);
				formObject.setNGLocked("MHT_PF_PAYMENT_ID_NO3",true);
				formObject.setNGEnable("MHT_pAYMENT_TYPE1",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE1",false);
				if(formObject.getNGValue("MHT_pAYMENT_TYPE1").equalsIgnoreCase(""))
					setPaymentType1Blank();
				if(formObject.getNGValue("MHT_pAYMENT_TYPE2").equalsIgnoreCase(""))
					setPaymentType2Blank();
				if(formObject.getNGValue("MHT_pAYMENT_TYPE3").equalsIgnoreCase(""))
					setPaymentType3Blank();				
			}
			else
			{
				System.out.println("sat value2: "+formObject.getNGValue("MHT_PID_PAYMENT_TYPE"));
				formObject.setNGValue("MHT_PF_PAYMENT_ID_NO","");
				formObject.setNGLocked("MHT_PF_PAYMENT_ID_NO",false);
				formObject.setNGEnable("MHT_pAYMENT_TYPE1",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE1","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE1",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE1",false);
				setPaymentType1Blank();
				
				formObject.setNGValue("MHT_PF_PAYMENT_ID_NO2","");
				formObject.setNGLocked("MHT_PF_PAYMENT_ID_NO2",false);
				formObject.setNGEnable("MHT_pAYMENT_TYPE2",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE2","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE2",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2",false);
				setPaymentType2Blank();
				
				formObject.setNGValue("MHT_PF_PAYMENT_ID_NO3","");
				formObject.setNGLocked("MHT_PF_PAYMENT_ID_NO3",false);
				formObject.setNGEnable("MHT_pAYMENT_TYPE3",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE3","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE3",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3",false);
				setPaymentType3Blank();
				
				formObject.setNGEnable("FRAME_MODEOF_PAYMENT", false);
				formObject.setNGLocked("FRAME_MODEOF_PAYMENT", false);
			}
		}
		/************************* End MHT-PID Process Integration **************************/
		
		//Mode of Payment event KEYPRESS
		else if((pEventName.equalsIgnoreCase("change") || pEventName.equalsIgnoreCase("KEYPRESS")) && pControlName.equalsIgnoreCase("MHT_pAYMENT_TYPE1") && (wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DATAENTRY_QC") || wsName.equalsIgnoreCase("COPS_Calculator") || wsName.equalsIgnoreCase("BSG_Endorsement") || wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception")) && formObject.getNGValue("MHT_PID_PAYMENT_TYPE").equalsIgnoreCase("No") && formObject.getNGValue("MHT_TAG_STATUS").equalsIgnoreCase(""))
		{
			System.out.println("executeMHTEvent(): MHT_pAYMENT_TYPE1 on change");
			String pymntValue = formObject.getNGValue("MHT_pAYMENT_TYPE1");
			System.out.println("MHT_pAYMENT_TYPE1 value: "+formObject.getNGValue("MHT_pAYMENT_TYPE1"));
			
			if(pymntValue.equalsIgnoreCase("Credit Card"))
			{
				System.out.println("MHT_pAYMENT_TYPE1 Credit Card=="+pymntValue);
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_NO",true);
				/*if(!formObject.getNGValue("MHT_pAYMENT_TYPE1_NO").equals("") && formObject.getNGValue("MHT_pAYMENT_TYPE1_NO").length() > 0)
				{
					System.out.println("Credit Card if condition"+pymntValue);
					formObject.setNGValue("MHT_pAYMENT_TYPE1_NO","");
				}*/
				formObject.setNGLocked("MHT_pAYMENT_TYPE1_NO",true);
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_DATE",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE1_DATE",true);
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_AMOUNT",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE1_AMOUNT",true);
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_EXPIRYDATE",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE1_EXPIRYDATE",true);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_BANKNAME",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE1_BANKNAME","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_BANKNAME",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE1_BANKNAME",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE2",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2",false);
				setPaymentType2Blank();
				formObject.setNGEnable("MHT_pAYMENT_TYPE3",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3",false);
				setPaymentType3Blank();
				
				System.out.println("MHT_pAYMENT_TYPE1_NO Credit Card=="+formObject.getNGValue("MHT_pAYMENT_TYPE1_NO"));
			}
			else if(pymntValue.equals("Cheque"))
			{
				System.out.println("MHT_pAYMENT_TYPE1 Cheque=="+pymntValue);
				formObject.setNGEnable("MHT_pAYMENT_TYPE2",true);
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_NO",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE1_NO",true);
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_DATE",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE1_DATE",true);
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_AMOUNT",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE1_AMOUNT",true);
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_BANKNAME",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE1_BANKNAME",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_EXPIRYDATE",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE1_EXPIRYDATE","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_EXPIRYDATE",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE1_EXPIRYDATE",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE2",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2",false);
				setPaymentType2Blank();
				formObject.setNGEnable("MHT_pAYMENT_TYPE3",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3",false);
				setPaymentType3Blank();
			}
			else if(pymntValue.equals("Fund Transfer"))
			{
				System.out.println("MHT_pAYMENT_TYPE1 Fund Transfer=="+pymntValue);
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_DATE",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE1_DATE",true);
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_AMOUNT",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE1_AMOUNT",true);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_NO",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE1_NO","");
				formObject.setNGLocked("MHT_pAYMENT_TYPE1_NO",false);
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_NO",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_EXPIRYDATE",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE1_EXPIRYDATE","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_EXPIRYDATE",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE1_EXPIRYDATE",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_BANKNAME",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE1_BANKNAME","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_BANKNAME",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE1_BANKNAME",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE2",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2",false);
				setPaymentType2Blank();
				formObject.setNGEnable("MHT_pAYMENT_TYPE3",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3",false);
				setPaymentType3Blank();
			}
			else if(pymntValue.equals("Demand Draft"))
			{
				System.out.println("MHT_pAYMENT_TYPE1Demand Draft=="+pymntValue);
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_NO",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE1_NO",true);
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_DATE",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE1_DATE",true);
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_AMOUNT",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE1_AMOUNT",true);
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_BANKNAME",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE1_BANKNAME",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_EXPIRYDATE",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE1_EXPIRYDATE","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_EXPIRYDATE",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE1_EXPIRYDATE",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE2",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2",false);
				setPaymentType2Blank();
				formObject.setNGEnable("MHT_pAYMENT_TYPE3",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3",false);
				setPaymentType3Blank();
			}
			else if(pymntValue.equalsIgnoreCase("CDBG"))
			{
				System.out.println("MHT_pAYMENT_TYPE1CDBG=="+pymntValue);
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_NO",true);			
				formObject.setNGLocked("MHT_pAYMENT_TYPE1_NO",true);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_DATE",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE1_DATE","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_DATE",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE1_DATE",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_AMOUNT",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE1_AMOUNT","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_AMOUNT",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE1_AMOUNT",false);
				
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_EXPIRYDATE",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE1_EXPIRYDATE","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_EXPIRYDATE",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE1_EXPIRYDATE",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_BANKNAME",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE1_BANKNAME","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_BANKNAME",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE1_BANKNAME",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE2",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2",false);
				setPaymentType2Blank();
				formObject.setNGEnable("MHT_pAYMENT_TYPE3",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3",false);
				setPaymentType3Blank();
			}
			else if(pymntValue.equals("Cash"))
			{
				System.out.println("MHT_pAYMENT_TYPECash=="+pymntValue);
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_NO",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE1_NO","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_NO",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE1_NO",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_DATE",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE1_DATE",true);
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_AMOUNT",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE1_AMOUNT",true);
				
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_BANKNAME",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE1_BANKNAME","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_BANKNAME",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE1_BANKNAME",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_EXPIRYDATE",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE1_EXPIRYDATE","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_EXPIRYDATE",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE1_EXPIRYDATE",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE2",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2",false);
				setPaymentType2Blank();
				formObject.setNGEnable("MHT_pAYMENT_TYPE3",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3",false);
				setPaymentType3Blank();
			}
			else if(pymntValue.equalsIgnoreCase("DA"))
			{
				/*if(!formObject.getNGValue("MHT_CASE_CATEGORY").equals("") && formObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER"))
				{
					System.out.println("inside DA ---> MHT_CASE_CATEGORY value: "+formObject.getNGValue("MHT_CASE_CATEGORY"));
					JOptionPane.showMessageDialog(null,"DA Mode is Applicable For IPartner Cases Only");
				}*/
				System.out.println("inside DA ---> MHT_CASE_CATEGORY value: "+formObject.getNGValue("MHT_CASE_CATEGORY"));
				if(!formObject.getNGValue("MHT_CASE_CATEGORY").equals("") && !formObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER"))
				{
					System.out.println("inside if ---> MHT_CASE_CATEGORY value: "+formObject.getNGValue("MHT_CASE_CATEGORY"));
					JOptionPane.showMessageDialog(null,"DA Mode is Applicable For IPartner Cases Only");
					formObject.setNGValue("MHT_pAYMENT_TYPE1_NO","");
					formObject.setNGValue("MHT_pAYMENT_TYPE1_DATE","");
					formObject.setNGValue("MHT_pAYMENT_TYPE1_AMOUNT","");
					formObject.setNGValue("MHT_pAYMENT_TYPE1_EXPIRYDATE","");
					formObject.setNGValue("MHT_pAYMENT_TYPE1_BANKNAME","");
					formObject.setNGListIndex("MHT_pAYMENT_TYPE1",0);
				}
						
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_NO",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE1_NO",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_DATE",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE1_DATE",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_AMOUNT",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE1_AMOUNT",false);
								
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_BANKNAME",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE1_BANKNAME",false);
								
				formObject.setNGEnable("MHT_pAYMENT_TYPE1_EXPIRYDATE",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE1_EXPIRYDATE",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE2",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2",false);
				setPaymentType2Blank();
				formObject.setNGEnable("MHT_pAYMENT_TYPE3",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3",false);
				setPaymentType3Blank();
			}
			else if(pymntValue.equalsIgnoreCase("MAT") || pymntValue.equalsIgnoreCase("MBT") || pymntValue.equalsIgnoreCase("Others"))
			{
				System.out.println("inside else null ---> MHT_pAYMENT_TYPE1 value: "+formObject.getNGValue("MHT_pAYMENT_TYPE1"));
				enableAllPaymentDetails1();
				formObject.setNGEnable("MHT_pAYMENT_TYPE2",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2",false);
				setPaymentType2Blank();
				formObject.setNGEnable("MHT_pAYMENT_TYPE3",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3",false);
				setPaymentType3Blank();
			}
			else if(pymntValue.equalsIgnoreCase("CHEQUE") || pymntValue.equalsIgnoreCase("DEMAND DRAFT") || pymntValue.equalsIgnoreCase("FUND TRANSFER") || pymntValue.equalsIgnoreCase("CASH"))
			{
			}
			else
			{
				System.out.println("inside else ---> MHT_pAYMENT_TYPE1 value: "+formObject.getNGValue("MHT_pAYMENT_TYPE1"));
				setPaymentType1Blank();
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE2",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE2","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE2",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2",false);
				setPaymentType2Blank();
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE3",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE3","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE3",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3",false);
				setPaymentType3Blank();
			}
		}
		else if((pEventName.equalsIgnoreCase("change") || pEventName.equalsIgnoreCase("KEYPRESS")) && pControlName.equalsIgnoreCase("MHT_pAYMENT_TYPE2") && (wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DATAENTRY_QC") || wsName.equalsIgnoreCase("BSG_Endorsement") || wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception")) && formObject.getNGValue("MHT_PID_PAYMENT_TYPE").equalsIgnoreCase("No") && formObject.getNGValue("MHT_TAG_STATUS").equalsIgnoreCase(""))
		{
			System.out.println("executeMHTEvent(): MHT_pAYMENT_TYPE2 on change");
			String pymntValue = formObject.getNGValue("MHT_pAYMENT_TYPE2");
			System.out.println("MHT_pAYMENT_TYPE1 value: "+formObject.getNGValue("MHT_pAYMENT_TYPE2"));
			
			if(pymntValue.equalsIgnoreCase("Credit Card"))
			{
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_NO",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2_NO",true);
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_DATE",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2_DATE",true);
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_AMOUNT",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2_AMOUNT",true);
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_EXPIRYDATE",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2_EXPIRYDATE",true);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_BANKNAME",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE2_BANKNAME","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_BANKNAME",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2_BANKNAME",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE3",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3",false);
				setPaymentType3Blank();
			}
			else if(pymntValue.equals("Cheque"))
			{
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_NO",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2_NO",true);
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_DATE",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2_DATE",true);
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_AMOUNT",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2_AMOUNT",true);
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_BANKNAME",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2_BANKNAME",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_EXPIRYDATE",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE2_EXPIRYDATE","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_EXPIRYDATE",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2_EXPIRYDATE",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE3",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3",false);
				setPaymentType3Blank();
			}
			else if(pymntValue.equals("Fund Transfer"))
			{
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_DATE",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2_DATE",true);
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_AMOUNT",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2_AMOUNT",true);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_NO",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE2_NO","");
				formObject.setNGLocked("MHT_pAYMENT_TYPE2_NO",false);
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_NO",false);
								
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_EXPIRYDATE",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE2_EXPIRYDATE","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_EXPIRYDATE",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2_EXPIRYDATE",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_BANKNAME",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE2_BANKNAME","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_BANKNAME",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2_BANKNAME",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE3",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3",false);
				setPaymentType3Blank();
			}
			else if(pymntValue.equals("Demand Draft"))
			{
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_NO",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2_NO",true);
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_DATE",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2_DATE",true);
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_AMOUNT",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2_AMOUNT",true);
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_BANKNAME",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2_BANKNAME",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_EXPIRYDATE",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE2_EXPIRYDATE","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_EXPIRYDATE",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2_EXPIRYDATE",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE3",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3",false);
				setPaymentType3Blank();
			}
			else if(pymntValue.equalsIgnoreCase("CDBG"))
			{
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_NO",true);			
				formObject.setNGLocked("MHT_pAYMENT_TYPE2_NO",true);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_DATE",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE2_DATE","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_DATE",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2_DATE",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_AMOUNT",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE2_AMOUNT","");
				formObject.setNGLocked("MHT_pAYMENT_TYPE2_AMOUNT",false);
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_AMOUNT",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_EXPIRYDATE",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE2_EXPIRYDATE","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_EXPIRYDATE",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2_EXPIRYDATE",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_BANKNAME",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE2_BANKNAME","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_BANKNAME",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2_BANKNAME",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE3",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3",false);
				setPaymentType3Blank();
			}
			else if(pymntValue.equals("Cash"))
			{
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_NO",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE2_NO","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_NO",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2_NO",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_DATE",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2_DATE",true);
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_AMOUNT",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2_AMOUNT",true);
								
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_BANKNAME",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE2_BANKNAME","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_BANKNAME",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2_BANKNAME",false);
								
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_EXPIRYDATE",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE2_EXPIRYDATE","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_EXPIRYDATE",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2_EXPIRYDATE",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE3",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3",false);
				setPaymentType3Blank();
			}
			else if(pymntValue.equalsIgnoreCase("DA"))
			{
				/*if(!formObject.getNGValue("MHT_CASE_CATEGORY").equals("") && formObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER"))
				{
					System.out.println("inside DA ---> MHT_CASE_CATEGORY value: "+formObject.getNGValue("MHT_CASE_CATEGORY"));
					JOptionPane.showMessageDialog(null,"DA Mode is Applicable For IPartner Cases Only");
				}*/

				if(!formObject.getNGValue("MHT_CASE_CATEGORY").equals("") && !formObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER"))
				{
					System.out.println("inside if ---> MHT_CASE_CATEGORY value: "+formObject.getNGValue("MHT_CASE_CATEGORY"));
					JOptionPane.showMessageDialog(null,"DA Mode is Applicable For IPartner Cases Only");
					formObject.setNGValue("MHT_pAYMENT_TYPE2_NO","");
					formObject.setNGValue("MHT_pAYMENT_TYPE2_DATE","");
					formObject.setNGValue("MHT_pAYMENT_TYPE2_AMOUNT","");
					formObject.setNGValue("MHT_pAYMENT_TYPE2_EXPIRYDATE","");
					formObject.setNGValue("MHT_pAYMENT_TYPE2_BANKNAME","");
					formObject.setNGListIndex("MHT_pAYMENT_TYPE2",0);
				}
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_NO",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2_NO",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_DATE",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2_DATE",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_AMOUNT",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2_AMOUNT",false);
								
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_BANKNAME",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2_BANKNAME",false);
								
				formObject.setNGEnable("MHT_pAYMENT_TYPE2_EXPIRYDATE",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE2_EXPIRYDATE",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE3",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3",false);
				setPaymentType3Blank();
			}
			else if(pymntValue.equalsIgnoreCase("MAT") || pymntValue.equalsIgnoreCase("MBT") || pymntValue.equalsIgnoreCase("Others"))
			{
				System.out.println("inside else null ---> MHT_pAYMENT_TYPE2 value: "+formObject.getNGValue("MHT_pAYMENT_TYPE2"));
				enableAllPaymentDetails2();
				formObject.setNGEnable("MHT_pAYMENT_TYPE3",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3",false);
				setPaymentType3Blank();
			}
			else if(pymntValue.equalsIgnoreCase("CHEQUE") || pymntValue.equalsIgnoreCase("DEMAND DRAFT") || pymntValue.equalsIgnoreCase("FUND TRANSFER") || pymntValue.equalsIgnoreCase("CASH"))
			{
			}
			else
			{
				System.out.println("inside else ---> MHT_pAYMENT_TYPE2 value: "+formObject.getNGValue("MHT_pAYMENT_TYPE2"));
				setPaymentType2Blank();
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE3",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE3","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE3",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3",false);
				setPaymentType3Blank();
			}
		}
		else if((pEventName.equalsIgnoreCase("change") || pEventName.equalsIgnoreCase("KEYPRESS")) && pControlName.equalsIgnoreCase("MHT_pAYMENT_TYPE3") && (wsName.equalsIgnoreCase("BSG_DATAENTRY") || wsName.equalsIgnoreCase("BSG_DATAENTRY_QC") || wsName.equalsIgnoreCase("BSG_Endorsement") || wsName.equals("BSG_Exception") || wsName.equals("RM_Endorsement") || wsName.equals("RM_Exception")) && formObject.getNGValue("MHT_PID_PAYMENT_TYPE").equalsIgnoreCase("No") && formObject.getNGValue("MHT_TAG_STATUS").equalsIgnoreCase(""))
		{
			System.out.println("executeMHTEvent(): MHT_pAYMENT_TYPE3 on change");
			String pymntValue = formObject.getNGValue("MHT_pAYMENT_TYPE3");
			System.out.println("MHT_pAYMENT_TYPE1 value: "+formObject.getNGValue("MHT_pAYMENT_TYPE3"));
			
			if(pymntValue.equalsIgnoreCase("Credit Card"))
			{
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_NO",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3_NO",true);
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_DATE",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3_DATE",true);
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_AMOUNT",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3_AMOUNT",true);
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_EXPIRYDATE",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3_EXPIRYDATE",true);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_BANKNAME",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE3_BANKNAME","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_BANKNAME",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3_BANKNAME",false);
			}
			else if(pymntValue.equals("Cheque"))
			{
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_NO",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3_NO",true);
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_DATE",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3_DATE",true);
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_AMOUNT",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3_AMOUNT",true);
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_BANKNAME",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3_BANKNAME",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_EXPIRYDATE",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE3_EXPIRYDATE","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_EXPIRYDATE",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3_EXPIRYDATE",false);
			}
			else if(pymntValue.equals("Fund Transfer"))
			{
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_DATE",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3_DATE",true);
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_AMOUNT",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3_AMOUNT",true);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_NO",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE3_NO","");
				formObject.setNGLocked("MHT_pAYMENT_TYPE3_NO",false);
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_NO",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_EXPIRYDATE",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE3_EXPIRYDATE","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_EXPIRYDATE",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3_EXPIRYDATE",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_BANKNAME",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE3_BANKNAME","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_BANKNAME",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3_BANKNAME",false);
			}
			else if(pymntValue.equals("Demand Draft"))
			{
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_NO",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3_NO",true);
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_DATE",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3_DATE",true);
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_AMOUNT",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3_AMOUNT",true);
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_BANKNAME",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3_BANKNAME",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_EXPIRYDATE",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE3_EXPIRYDATE","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_EXPIRYDATE",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3_EXPIRYDATE",false);
			}
			else if(pymntValue.equalsIgnoreCase("CDBG"))
			{
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_NO",true);			
				formObject.setNGLocked("MHT_pAYMENT_TYPE3_NO",true);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_DATE",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE3_DATE","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_DATE",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3_DATE",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_AMOUNT",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE3_AMOUNT","");
				formObject.setNGLocked("MHT_pAYMENT_TYPE3_AMOUNT",false);
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_AMOUNT",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_EXPIRYDATE",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE3_EXPIRYDATE","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_EXPIRYDATE",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3_EXPIRYDATE",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_BANKNAME",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE3_BANKNAME","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_BANKNAME",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3_BANKNAME",false);
			}
			else if(pymntValue.equals("Cash"))
			{
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_NO",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE3_NO","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_NO",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3_NO",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_DATE",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3_DATE",true);
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_AMOUNT",true);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3_AMOUNT",true);
				
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_EXPIRYDATE",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE3_EXPIRYDATE","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_EXPIRYDATE",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3_EXPIRYDATE",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_BANKNAME",true);
				formObject.setNGValue("MHT_pAYMENT_TYPE3_BANKNAME","");
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_BANKNAME",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3_BANKNAME",false);
			}
			else if(pymntValue.equalsIgnoreCase("DA"))
			{
				/*if(!formObject.getNGValue("MHT_CASE_CATEGORY").equals("") && !formObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER"))
				{
					System.out.println("inside DA ---> MHT_CASE_CATEGORY value: "+formObject.getNGValue("MHT_CASE_CATEGORY"));
					JOptionPane.showMessageDialog(null,"DA Mode is Applicable For IPartner Cases Only");
				}*/

				if(!formObject.getNGValue("MHT_CASE_CATEGORY").equals("") && !formObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER"))
				{
					System.out.println("inside if ---> MHT_CASE_CATEGORY value: "+formObject.getNGValue("MHT_CASE_CATEGORY"));
					JOptionPane.showMessageDialog(null,"DA Mode is Applicable For IPartner Cases Only");
					formObject.setNGValue("MHT_pAYMENT_TYPE3_NO","");
					formObject.setNGValue("MHT_pAYMENT_TYPE3_DATE","");
					formObject.setNGValue("MHT_pAYMENT_TYPE3_AMOUNT","");
					formObject.setNGValue("MHT_pAYMENT_TYPE3_EXPIRYDATE","");
					formObject.setNGValue("MHT_pAYMENT_TYPE3_BANKNAME","");
					formObject.setNGListIndex("MHT_pAYMENT_TYPE3",0);
				}
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_NO",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3_NO",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_DATE",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3_DATE",false);
				
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_AMOUNT",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3_AMOUNT",false);
								
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_BANKNAME",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3_BANKNAME",false);
								
				formObject.setNGEnable("MHT_pAYMENT_TYPE3_EXPIRYDATE",false);
				formObject.setNGLocked("MHT_pAYMENT_TYPE3_EXPIRYDATE",false);
				
				
			}
			else if(pymntValue.equalsIgnoreCase("MAT") || pymntValue.equalsIgnoreCase("MBT") || pymntValue.equalsIgnoreCase("Others"))
			{
				System.out.println("inside else null ---> MHT_pAYMENT_TYPE3 value: "+formObject.getNGValue("MHT_pAYMENT_TYPE3"));
				enableAllPaymentDetails3();
			}
			else if(pymntValue.equalsIgnoreCase("CHEQUE") || pymntValue.equalsIgnoreCase("DEMAND DRAFT") || pymntValue.equalsIgnoreCase("FUND TRANSFER") || pymntValue.equalsIgnoreCase("CASH"))
			{
			}
			else
			{
				System.out.println("inside else2 ---> MHT_pAYMENT_TYPE3 value: "+formObject.getNGValue("MHT_pAYMENT_TYPE3"));
				setPaymentType3Blank();
			}
		}
		//vertical change
		/*else if(pEventName.equalsIgnoreCase("Change") && pControlName.equalsIgnoreCase("MHT_PRIMARY_VERTICAL"))
		{
			if(debug==1)
			{
				System.out.println("inside vertical");
			}
			
			if(wsName.equalsIgnoreCase("BSG_DATAENTRY"))
			{
				//formObject.setNGEnable("MHT_PRIMARY_VERTICAL",false);
			
				System.out.println("test MHT_PRIMARY_VERTICAL ====");
				formObject.setNGValue("MHT_SOURCE_BUSINESS","");
				formObject.setNGValue("MHT_CHANNEL_SOURCE","");
				//formObject.setNGValue("ICICILOMBARD_HT_EMPCODE_CSO","");
				//formObject.setNGValue("ICICILOMBARD_HT_WRM","");
				//formObject.setNGValue("ICICILOMBARD_HT_WRE","");

				//formObject.setNGEnable("FRAME_ISEMP_CODE",false);
				//formObject.setNGEnable("FRAME_WRE_WRM",false);
				//formObject.setNGEnable("ICICILOMBARD_HT_PRIVILEGE_BANKER_CODE",false); 
				//formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",false);
			
			
				if(!formObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase(""))
				{
					
					if (formObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("ISEC") && (formObject.getNGValue("MHT_SUB_VERTICAL")).equalsIgnoreCase("BBG") )
					{
						if(debug==1)
						{
							System.out.println("setting enable/diable one-one in bbg isec----");
						}
						formObject.setNGEnable("MHT_SOURCE_BUSINESS",true);
						formObject.setNGValue("MHT_CHANNEL_SOURCE","");
						formObject.setNGEnable("MHT_CHANNEL_SOURCE",false);
					}
					else if(formObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BBG") && (formObject.getNGValue("MHT_SUB_VERTICAL")).equalsIgnoreCase("BBG"))
					{
						String arrfieldName9[]=new String[1];
						if(debug==1)
						{
							System.out.println("inside bbg and bbg");
						}
						formObject.setNGEnable("MHT_SOURCE_BUSINESS",true);
						formObject.setNGEnable("FRAME_ISEMP_CODE",true);
						formObject.setNGEnable("FRAME_BANK_BRANCH_NAME",true);
						formObject.setNGEnable("MHT_CHANNEL_SOURCE",true);
				
					}
					else if(formObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("Bancassurance") && (formObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group")))
					{
						formObject.setNGEnable("FRAME_CHANNEL_EMP_INFO",true);
					}
					else
					{
						System.out.println("inside----else");
					}
				}
			}
				
			
		}  */



         return "";
	}

    public void getData(String query,String noOfCols,String[] arrfieldName,boolean flag)
    {
        try
        {
            //passing the control and form data to NGEjbCalls class
            ngEjbCalls= new NGEjbCalls(formObject);
            // get input xml for the query
            inputXml=ngEjbCalls.callSelectInputXML(query,noOfCols);
            System.out.println("input xml:-------->"+inputXml);
            // get output xml for the query
            outputXml=ngEjbCalls.executeXmlGeneric(inputXml);
            System.out.println("output xml:-------->"+outputXml);

            if(arrfieldName[0].equalsIgnoreCase("ProductList"))
            {
                map = getProductList(outputXml,arrfieldName);
            }
            else
            {
                result= ngEjbCalls.xmlMHTParse(outputXml,arrfieldName,noOfCols,flag);
            }

            System.out.println("result :-------->"+result);
        }
        catch(Exception e)
        {
                e.printStackTrace();
        }
    }


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
                    System.out.println("strArr[0]=="+strArr[0]);
                    System.out.println("strArr[1]=="+strArr[1]);
                    map.put(strArr[0].trim(),strArr[1].trim()+":"+strArr[2].trim());
                    //map.add(sResult);
                }
            }
        }
        System.out.println("map[1]=="+map);
        return map;
    }
	
	public void showPage(String url,String type)
	{
		System.out.println("Entering showPage at : "+url +" ===type=="+type);
		js =formObject.getJSObject();	
		String str[]= new String[1];
		str[0] = url;
		Object obj = null;
		
		if(type.equalsIgnoreCase("FetchCopy"))
		{
			obj=js.call("OpenFetchCopy_MHT",str);
		}
		else if(type.equalsIgnoreCase("ViewDocument"))
		{
			obj=js.call("OpenDocumentView_MHT",str);
		}
		else if(type.equalsIgnoreCase("RemarksHistory"))
		{
			obj=js.call("OpenHistory_MHT",str);
		}
		/*************************** MHT -PID Process Integration ****************************/
		else if(type.equalsIgnoreCase("PrintSlip"))
		{
			obj=js.call("OpenPrintSlip_MHT",str);
		}
		else if(type.equalsIgnoreCase("DocumentUpload"))
		{
			obj=js.call("OpenDocUpload_MHT",str);
		}
		/************************* End MHT -PID Process Integration **************************/
		System.out.println("Exiting showPage at : ");
	}
	
	void Validate_SM(String pControlName)
	{
  
		// ============= RM ID Validation at  ====================
		System.out.println("SM Validation:");
		String sResult="";			
		//String lstrActivityName = formObject.getWFActivityName();
		String sM_ID=formObject.getNGValue(pControlName);
		System.out.println("sM_ID: "+sM_ID);			
		//String sRM_Name_Field="";
		
		//System.out.println("sRM_Name_Field:: "+sRM_Name_Field);	
		if (formObject.getNGValue(pControlName).equalsIgnoreCase(""))
		{
			JOptionPane.showMessageDialog(null,"Please fill SM Name.");				
			formObject.NGFocus(pControlName);
		}
		else
		{
			// ============= Check RM User in the OmniFlow ====================
			String sQuery="SELECT count(1) FROM PDBUSER where useralive = 'Y' and USERNAME='" +sM_ID+"'"; 
			System.out.println("sQuery: "+sQuery);
			ArrayList getUser=formObject.getNGDataFromDataSource(sQuery,1);
			String s_RMUser=(getUser.get(0)).toString();
			s_RMUser=s_RMUser.substring(s_RMUser.indexOf("[")+1,s_RMUser.indexOf("]"));
			System.out.println("s_RMUser: "+s_RMUser);
			int iRMValue = Integer.parseInt(s_RMUser);
			System.out.println("iRMValue: "+iRMValue);

			// *********** Checking RM USer Exist status ****************
			
			if (iRMValue>0)
			{
				// ************* RM ID Locked Status ***************
				/*sQuery="SELECT count(1) FROM NG_MHT_MST_RM where EMP_CODE='" +sM_ID+"'";
				System.out.println("sQuery: "+sQuery);
				ArrayList getReason=formObject.getNGDataFromDataSource(sQuery,1);
				String s_Field_Value=(getReason.get(0)).toString();
				s_Field_Value=s_Field_Value.substring(s_Field_Value.indexOf("[")+1,s_Field_Value.indexOf("]"));
				System.out.println("s_Field_Value: "+s_Field_Value);
				int iValue = Integer.parseInt(s_Field_Value);
				System.out.println("iValue: "+iValue);
				// *********** Checking RM USer Exist status ****************
				if (iValue>0)
				{
					System.out.println("RM ID Found:");
					sQuery="SELECT USERLOCKED FROM USERSECURITY WHERE USERINDEX = (SELECT USERINDEX FROM PDBUSER WHERE USERNAME ='" +sM_ID+"')";
					// *********** Checking RM Locked status ****************
					System.out.println("sQuery: "+sQuery);
					ArrayList getRMLockedStatus=formObject.getNGDataFromDataSource(sQuery,1);
					System.out.println("getRMLockedStatussize(): "+getRMLockedStatus.size());
					String s_Value=(getRMLockedStatus.get(0)).toString();
					s_Value=s_Value.substring(s_Value.indexOf("[")+1,s_Value.indexOf("]"));
					System.out.println("s_Value: "+s_Value);
					if(s_Value.equalsIgnoreCase("Y"))
					{
						System.out.println("SM ID Found but is Locked:");
						JOptionPane.showMessageDialog(null,"Specified SM ID is Locked.");
						formObject.setNGValue(pControlName,"");
						formObject.setNGValue("MHT_SM_NAME","");
						formObject.NGFocus("MHT_SM_NAME");	
					}
				}
				else
				{
					System.out.println("SM ID Not Found:");
					JOptionPane.showMessageDialog(null,"SM does not exist in Master.");
					formObject.setNGValue(pControlName,"");
					formObject.setNGValue("MHT_SM_NAME","");
					formObject.NGFocus("MHT_SM_NAME");	
				} */
			}
			else
			{
				System.out.println("SM ID Not Found:");
				JOptionPane.showMessageDialog(null,"SM does not exist in OmniFlow System or is InActive.");
				formObject.setNGValue(pControlName,"");
				formObject.setNGValue("MHT_SM_NAME","");
				formObject.NGFocus("MHT_SM_NAME");				
			}
		}
		// ============= End Check RM User in the OmniFlow ====================	 
	}
  
	public void setPaymentType1Blank()
	{
		formObject.setNGEnable("MHT_pAYMENT_TYPE1_NO",true);
		formObject.setNGValue("MHT_pAYMENT_TYPE1_NO","");						
		formObject.setNGLocked("MHT_pAYMENT_TYPE1_NO",false);
		
		formObject.setNGEnable("MHT_pAYMENT_TYPE1_DATE",true);
		formObject.setNGValue("MHT_pAYMENT_TYPE1_DATE","");
		formObject.setNGEnable("MHT_pAYMENT_TYPE1_DATE",false);
		formObject.setNGLocked("MHT_pAYMENT_TYPE1_DATE",false);
		
		formObject.setNGEnable("MHT_pAYMENT_TYPE1_AMOUNT",true);
		formObject.setNGValue("MHT_pAYMENT_TYPE1_AMOUNT","");
		formObject.setNGLocked("MHT_pAYMENT_TYPE1_AMOUNT",false);
		
		formObject.setNGEnable("MHT_pAYMENT_TYPE1_EXPIRYDATE",true);
		formObject.setNGValue("MHT_pAYMENT_TYPE1_EXPIRYDATE","");
		formObject.setNGEnable("MHT_pAYMENT_TYPE1_EXPIRYDATE",false);
		//formObject.setNGLocked("MHT_pAYMENT_TYPE1_EXPIRYDATE",false);
		
		formObject.setNGEnable("MHT_pAYMENT_TYPE1_BANKNAME",true);
		formObject.setNGValue("MHT_pAYMENT_TYPE1_BANKNAME","");
		formObject.setNGEnable("MHT_pAYMENT_TYPE1_BANKNAME",false);
		formObject.setNGLocked("MHT_pAYMENT_TYPE1_BANKNAME",false);
	}
	
	public void setPaymentType2Blank()
	{
		formObject.setNGEnable("MHT_pAYMENT_TYPE2_NO",true);
		formObject.setNGValue("MHT_pAYMENT_TYPE2_NO","");						
		formObject.setNGLocked("MHT_pAYMENT_TYPE2_NO",false);
		
		formObject.setNGEnable("MHT_pAYMENT_TYPE2_DATE",true);
		formObject.setNGValue("MHT_pAYMENT_TYPE2_DATE","");
		formObject.setNGEnable("MHT_pAYMENT_TYPE2_DATE",false);
		formObject.setNGLocked("MHT_pAYMENT_TYPE2_DATE",false);
		
		formObject.setNGEnable("MHT_pAYMENT_TYPE2_AMOUNT",true);
		formObject.setNGValue("MHT_pAYMENT_TYPE2_AMOUNT","");
		formObject.setNGLocked("MHT_pAYMENT_TYPE2_AMOUNT",false);
		
		formObject.setNGEnable("MHT_pAYMENT_TYPE2_EXPIRYDATE",true);
		formObject.setNGValue("MHT_pAYMENT_TYPE2_EXPIRYDATE","");
		formObject.setNGEnable("MHT_pAYMENT_TYPE2_EXPIRYDATE",false);
		//formObject.setNGLocked("MHT_pAYMENT_TYPE2_EXPIRYDATE",false);
		
		formObject.setNGEnable("MHT_pAYMENT_TYPE2_BANKNAME",true);
		formObject.setNGValue("MHT_pAYMENT_TYPE2_BANKNAME","");
		formObject.setNGEnable("MHT_pAYMENT_TYPE2_BANKNAME",false);
		formObject.setNGLocked("MHT_pAYMENT_TYPE2_BANKNAME",false);
	}
	
	public void setPaymentType3Blank()
	{
		formObject.setNGEnable("MHT_pAYMENT_TYPE3_NO",true);
		formObject.setNGValue("MHT_pAYMENT_TYPE3_NO","");						
		formObject.setNGLocked("MHT_pAYMENT_TYPE3_NO",false);
		
		formObject.setNGEnable("MHT_pAYMENT_TYPE3_DATE",true);
		formObject.setNGValue("MHT_pAYMENT_TYPE3_DATE","");
		formObject.setNGEnable("MHT_pAYMENT_TYPE3_DATE",false);
		formObject.setNGLocked("MHT_pAYMENT_TYPE3_DATE",false);
		
		formObject.setNGEnable("MHT_pAYMENT_TYPE3_AMOUNT",true);
		formObject.setNGValue("MHT_pAYMENT_TYPE3_AMOUNT","");
		formObject.setNGLocked("MHT_pAYMENT_TYPE3_AMOUNT",false);
		
		formObject.setNGEnable("MHT_pAYMENT_TYPE3_EXPIRYDATE",true);
		formObject.setNGValue("MHT_pAYMENT_TYPE3_EXPIRYDATE","");
		formObject.setNGEnable("MHT_pAYMENT_TYPE3_EXPIRYDATE",false);
		//formObject.setNGLocked("MHT_pAYMENT_TYPE3_EXPIRYDATE",false);
		
		formObject.setNGEnable("MHT_pAYMENT_TYPE3_BANKNAME",true);
		formObject.setNGValue("MHT_pAYMENT_TYPE3_BANKNAME","");
		formObject.setNGEnable("MHT_pAYMENT_TYPE3_BANKNAME",false);
		formObject.setNGLocked("MHT_pAYMENT_TYPE3_BANKNAME",false);
	}
	
	public void enableAllPaymentDetails1()
	{
		formObject.setNGEnable("MHT_pAYMENT_TYPE1_NO",true);					
		formObject.setNGLocked("MHT_pAYMENT_TYPE1_NO",true);
		
		formObject.setNGEnable("MHT_pAYMENT_TYPE1_DATE",true);
		formObject.setNGLocked("MHT_pAYMENT_TYPE1_DATE",false);
		
		formObject.setNGEnable("MHT_pAYMENT_TYPE1_AMOUNT",true);
		formObject.setNGLocked("MHT_pAYMENT_TYPE1_AMOUNT",true);
		
		formObject.setNGEnable("MHT_pAYMENT_TYPE1_EXPIRYDATE",true);
		formObject.setNGLocked("MHT_pAYMENT_TYPE1_EXPIRYDATE",false);
		
		formObject.setNGEnable("MHT_pAYMENT_TYPE1_BANKNAME",true);
		formObject.setNGLocked("MHT_pAYMENT_TYPE1_BANKNAME",false);
	}
	public void enableAllPaymentDetails2()
	{
		formObject.setNGEnable("MHT_pAYMENT_TYPE2_NO",true);					
		formObject.setNGLocked("MHT_pAYMENT_TYPE2_NO",true);
		
		formObject.setNGEnable("MHT_pAYMENT_TYPE2_DATE",true);
		formObject.setNGLocked("MHT_pAYMENT_TYPE2_DATE",false);
		
		formObject.setNGEnable("MHT_pAYMENT_TYPE2_AMOUNT",true);
		formObject.setNGLocked("MHT_pAYMENT_TYPE2_AMOUNT",true);
		
		formObject.setNGEnable("MHT_pAYMENT_TYPE2_EXPIRYDATE",true);
		formObject.setNGLocked("MHT_pAYMENT_TYPE2_EXPIRYDATE",false);
		
		formObject.setNGEnable("MHT_pAYMENT_TYPE2_BANKNAME",true);
		formObject.setNGLocked("MHT_pAYMENT_TYPE2_BANKNAME",false);
	}
	public void enableAllPaymentDetails3()
	{
		formObject.setNGEnable("MHT_pAYMENT_TYPE3_NO",true);					
		formObject.setNGLocked("MHT_pAYMENT_TYPE3_NO",true);
		
		formObject.setNGEnable("MHT_pAYMENT_TYPE3_DATE",true);
		formObject.setNGLocked("MHT_pAYMENT_TYPE3_DATE",false);
		
		formObject.setNGEnable("MHT_pAYMENT_TYPE3_AMOUNT",true);
		formObject.setNGLocked("MHT_pAYMENT_TYPE3_AMOUNT",true);
		
		formObject.setNGEnable("MHT_pAYMENT_TYPE3_EXPIRYDATE",true);
		formObject.setNGLocked("MHT_pAYMENT_TYPE3_EXPIRYDATE",false);
		
		formObject.setNGEnable("MHT_pAYMENT_TYPE3_BANKNAME",true);
		formObject.setNGLocked("MHT_pAYMENT_TYPE3_BANKNAME",false);
	}
}
