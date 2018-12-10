/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.newgen.formApplet.User;

import com.newgen.formApplet.*;
import com.newgen.formApplet.event.NGEvent;
import com.newgen.formApplet.event.NGPickListAdapter;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;//CR-8001-87420-Green File Digitization
import java.util.*;//CR-8001-87420-Green File Digitization
import java.text.*;//CR-8001-87420-Green File Digitization

/**
 *
 * @author vikas.tyagi
 */
public class PickListListener extends NGPickListAdapter
{    
    private int _iBatchSize = 10;
    private int _iTotalRecord;
    private int _iRecordFetched;    
    private NGPickList _objPickList;
    private NGFPropInterface _objFormObject;
	int _iBatchNo = 1;
	String str1="";
	String _strQuery = "";
	String strSource = "";
	XMLParser objProcessData = null;
 	String Col="";
	String tempStr="";
	int colCount=0;
    int bBGKRGID1=0;
	String BBGKRGVAL1="";
	int product_type_val=5;
	int sourceID1=0;
	SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
	SimpleDateFormat dateformat = new SimpleDateFormat("dd/MMM/yyyy");
    //----------------------------------------------------------------------------------------------------
    //Function Name 			: SampleListener
    //Date Written (DD/MM/YYYY)	: 14/04/2009
    //Author					: Vikas Tyagi
    //Input Parameters			: pPickList,pQuery,pColCnt,pColumn,pNgfInstance
    //Output Parameters			: none
    //Return Values				: none
    //Description				: Constructor.
    //----------------------------------------------------------------------------------------------------
    public PickListListener(NGPickList pPickList,NGFPropInterface objForm)
    {		////// System.out.println("inside  Pick List Listenenr constructor");
        _objFormObject = objForm;
        _objPickList = pPickList;
    }
	
	  public PickListListener(NGFPropInterface objForm, String controlName,NGPickList pPickList)
		{
			////// System.out.println("inside  Pick List Listenenr constructor");
			_objFormObject = objForm;
			_objPickList = pPickList;
			strSource=controlName;
			//_iTotalRecord = getTotalRecord();
		}
	

  

    //----------------------------------------------------------------------------------------------------
    //Function Name 			: getTotalRecord(FBD_7.2.1.3)
    //Date Written (DD/MM/YYYY)	: 17/03/2009
    //Author					: Vikas Tyagi
    //Input Parameters			: none
    //Output Parameters			: none
    //Return Values				: Total no of records for specified condition
    //Description				: Returns total no of records for specified condition
    //----------------------------------------------------------------------------------------------------
    // Changed By				        : Vikas Tyagi
    // Reason / Cause (Bug No if Any)	: FBD_7.2.3.0
    // Change  Description			    : Changes for caching.
    //----------------------------------------------------------------------------------------------------
    // Changed By				        : Vikas Tyagi
    // Reason / Cause (Bug No if Any)	: FBD_7.2.4.1
    // Change  Description			    : Optimization.
    //----------------------------------------------------------------------------------------------------
	private int getTotalRecord(String fieldValue)
    {
        try
        {      
			this.objProcessData = new XMLParser();
			this.objProcessData.setInputXML(this._objFormObject.getWFGeneralData());
			//fieldValue=_objFormObject.getNGValue(this.strSource);
			ArrayList alData=null;
			//=============  CPI CR Quote SYS==================
			/*****Start CR-8001-87420-Green File Digitization*****/
			if(this.strSource.equalsIgnoreCase("CPI_RM_ID"))
			{
				if(_objFormObject.getNGValue("CPI_IS_PF_FETCH").equalsIgnoreCase("Yes"))
				{
					if(fieldValue!=null)
					{
						alData = _objFormObject.getNGDataFromDataSource("select count(distinct TXT_HR_REF_NO) from MV_CPI_VW_EMPLOYEE_OMNI where UPPER(TXT_HR_REF_NO) like UPPER(N'"+fieldValue+"%')",1);
					}
					else
					{
						alData = _objFormObject.getNGDataFromDataSource("select count(distinct TXT_HR_REF_NO) from MV_CPI_VW_EMPLOYEE_OMNI",1);
					}
				}
				else
				{
					if(fieldValue!=null)
					{
						alData = _objFormObject.getNGDataFromDataSource("select count(distinct EMP_CODE) from NG_RM_MASTER where UPPER(EMP_CODE) like UPPER(N'"+fieldValue+"%')",1);
					}
					else
					{
						alData = _objFormObject.getNGDataFromDataSource("select count(distinct EMP_CODE) from NG_RM_MASTER",1);
					}
				}
			}
			//============= End CPI CR Quote SYS===============
			//TXT_HR_REF_NO,TXT_EMPLOYEE_NAME
			
			if(this.strSource.equalsIgnoreCase("CPI_ADDTNL_CIT_RM_ID") || this.strSource.equalsIgnoreCase("CPI_UW_EMP_ID") || this.strSource.equalsIgnoreCase("CPI_SECONDARY_MO_ID") || this.strSource.equalsIgnoreCase("CPI_RM_EMP_ID_SPG_IBG"))
			{
				if(fieldValue!=null)
				{	  
					  // System.out.println(this.strSource+"getTotalRecords...");
					  alData = _objFormObject.getNGDataFromDataSource("select count(distinct TXT_HR_REF_NO) from MV_CPI_VW_EMPLOYEE_OMNI where UPPER(TXT_HR_REF_NO) = UPPER(N'"+fieldValue+"%')",1);
				}
				else
				{
					 
					 alData = _objFormObject.getNGDataFromDataSource("select count(distinct TXT_HR_REF_NO) from MV_CPI_VW_EMPLOYEE_OMNI ",1);
				}
			}
			/*if(this.strSource.equalsIgnoreCase("CPI_UW_EMP_ID"))
			{
				if(fieldValue!=null)
				{
					  alData = _objFormObject.getNGDataFromDataSource("select count(distinct TXT_HR_REF_NO) from MV_CPI_VW_EMPLOYEE_OMNI where UPPER(TXT_HR_REF_NO) = UPPER(N'"+fieldValue+"%')",1);
				}
				else
				{
					 
					 alData = _objFormObject.getNGDataFromDataSource("select count(distinct TXT_HR_REF_NO) from MV_CPI_VW_EMPLOYEE_OMNI ",1);
				}
			}*/
			//CPI_PREV_POLICY_NO 
			if(this.strSource.equalsIgnoreCase("CPI_PREV_POLICY_NO"))
			{
				if(fieldValue!=null)
				{	  
					  // System.out.println(this.strSource+"getTotalRecords...");
					  alData = _objFormObject.getNGDataFromDataSource("select count(distinct GEN.TXT_POLICY_NO_CHAR) from GEN_PROP_INFORMATION_TAB@omnitopf GEN,UW_PRODUCT_MASTER@omnitopf UPM where GEN.NUM_PRODUCT_CODE = UPM.PRODUCTCODE and GEN.TXT_POLICY_NO_CHAR is not null and GEN.TXT_POLICY_NO_CHAR ='"+fieldValue+"' ",1);
				}
				else
				{					 
					 alData = _objFormObject.getNGDataFromDataSource("select count(distinct GEN.TXT_POLICY_NO_CHAR) from GEN_PROP_INFORMATION_TAB@omnitopf GEN,UW_PRODUCT_MASTER@omnitopf UPM where GEN.NUM_PRODUCT_CODE = UPM.PRODUCTCODE and GEN.TXT_POLICY_NO_CHAR is not null",1);
				}
			}
			//base policy number
			if(this.strSource.equalsIgnoreCase("CPI_POLICY_NUMBER_BASE"))
			{
				if(fieldValue!=null)
				{	  
					  // System.out.println(this.strSource+"getTotalRecords...");
					  alData = _objFormObject.getNGDataFromDataSource("select count(distinct GEN.TXT_POLICY_NO_CHAR) from GEN_PROP_INFORMATION_TAB@omnitopf GEN,UW_PRODUCT_MASTER@omnitopf UPM where GEN.NUM_PRODUCT_CODE = UPM.PRODUCTCODE and GEN.TXT_POLICY_NO_CHAR is not null and GEN.TXT_POLICY_NO_CHAR ='"+fieldValue+"' ",1);
				}
				else
				{					 
					 alData = _objFormObject.getNGDataFromDataSource("select count(distinct GEN.TXT_POLICY_NO_CHAR) from GEN_PROP_INFORMATION_TAB@omnitopf GEN,UW_PRODUCT_MASTER@omnitopf UPM where GEN.NUM_PRODUCT_CODE = UPM.PRODUCTCODE and GEN.TXT_POLICY_NO_CHAR is not null",1);
				}
			}
			/*****End CR-8001-87420-Green File Digitization*****/
			//============= End CPI CR Quote SYS===============
			/**************************************Client Registration CR Start*****************************************************/
			if(this.strSource.equalsIgnoreCase("CPI_POLICYNO_SEARCH"))
			{
				if(fieldValue!=null)
				{
					  alData = _objFormObject.getNGDataFromDataSource("select count(distinct POLICY_NUMBER) from NG_CPI_CLIENT_REG_MST where UPPER(POLICY_NUMBER) = UPPER(N'"+fieldValue+"%') AND UPPER(REGNO_STATUS) = 'APPROVED'",1);
				}
				else
				{
					 
					 alData = _objFormObject.getNGDataFromDataSource("select count(distinct POLICY_NUMBER) from NG_CPI_CLIENT_REG_MST ",1);
				}
			}
			/**************************************Client Registration CR End*****************************************************/	
			
			
			/********************CR-OMCPI-1314-03 CPU DataWashing Start**********************/
			if(this.strSource.equalsIgnoreCase("CPI_CPU_ASSIGN_TO"))
			{
				/**** Start CR-8001-70893 Marine CR *******************************/
				String prodCategory="";
				prodCategory = _objFormObject.getWFActivityName().equalsIgnoreCase("CPU_Assignment")?"DW_Health":"Marine";
				
				if(fieldValue!=null)
				{
					 // alData = _objFormObject.getNGDataFromDataSource("select count(distinct EMP_ID) from  NG_CPI_DW_ESCALATION_MASTER where UPPER(EMP_ID) like UPPER(N'"+fieldValue+"%')",1);
					 alData = _objFormObject.getNGDataFromDataSource("select count(distinct EMP_ID) from  NG_CPI_DW_ESCALATION_MASTER where prod_category = '"+prodCategory+"' and UPPER(EMP_ID) like UPPER(N'"+fieldValue+"%')",1);
				}
				else
				{

					 // alData = _objFormObject.getNGDataFromDataSource("select count(distinct EMP_ID) from NG_CPI_DW_ESCALATION_MASTER ",1);
					  alData = _objFormObject.getNGDataFromDataSource("select count(distinct EMP_ID) from NG_CPI_DW_ESCALATION_MASTER where prod_category ='"+prodCategory+"'",1);
				}
				/**** ENd CR-8001-70893 Marine CR *********************************/
			}
			/********************CR-OMCPI-1314-03 CPU DataWashing END**********************/
			
			/********************* CR 45 Network Partner *****************************/
			if(this.strSource.equalsIgnoreCase("CPI_NETWORK_PARTNER_NAME"))
			{
				if(fieldValue!=null)
				{
					alData = _objFormObject.getNGDataFromDataSource("Select count(DISTINCT NETWORK_PARTNER_NAME) from NG_CPI_NTWRK_PARTNER_MASTER where UPPER(NETWORK_PARTNER_NAME) like UPPER(N'"+fieldValue+"%')",1);
				}
				else
				{
					alData = _objFormObject.getNGDataFromDataSource("Select count(DISTINCT NETWORK_PARTNER_NAME) from NG_CPI_NTWRK_PARTNER_MASTER ",1);
				}
			}
			/********************* End CR 45 Network Partner *************************/
			
			/******************   CO Insurance CR 18 *************************/
			//satish
			if(this.strSource.equalsIgnoreCase("CPI_RM_NAME"))
			{
				if(fieldValue!=null)
				{
					  alData = _objFormObject.getNGDataFromDataSource("Select count(DISTINCT EMP_CODE) from NG_RM_MASTER where UPPER(RM_NAME) like UPPER(N'"+fieldValue+"%')",1);
				}
				else
				{
					 
					 alData = _objFormObject.getNGDataFromDataSource("Select count(DISTINCT EMP_CODE) from NG_RM_MASTER ",1);
				}
			}
			//satish
			/****************** End  CO Insurance CR 18 *************************/
			
			/********************* CR 28 by satish *****************************/
			//satish
			if(this.strSource.equalsIgnoreCase("CPI_NAME_OF_BROKER_AGENT"))
			{
				if(fieldValue!=null)
				{
					  alData = _objFormObject.getNGDataFromDataSource("Select count(DISTINCT AGENT_NAME) from ng_cpi_agent_master where UPPER(AGENT_NAME) like UPPER(N'"+fieldValue+"%')",1);
				}
				else
				{
					 
					 alData = _objFormObject.getNGDataFromDataSource("Select count(DISTINCT AGENT_NAME) from ng_cpi_agent_master ",1);
				}
			}
			//satish
			/********************** End of CR 28 ********************************/
			
			/**************************** CPI IL Location ****************/
			if((this.strSource.equalsIgnoreCase("CPI_IL_Location"))||(this.strSource.equalsIgnoreCase("Corp_IL_Location"))||(this.strSource.equalsIgnoreCase("End_IL_Location")))
			{
				if(fieldValue!=null)
				{
					////// System.out.println("sandeep Inside Total Row");
					 alData = _objFormObject.getNGDataFromDataSource("Select count(IL_LOCATION_VALUE) from NG_IL_LOCATION_MASTER where UPPER(IL_LOCATION_VALUE) like UPPER(N'"+fieldValue+"%')",1);
					 
					 ////// System.out.println("Pick list count"+alData);
					 // alData = _objFormObject.getNGDataFromDataSource("SELECT count(IL_LOCATION_VALUE) FROM NG_IL_LOCATION_MASTER  ",1);
				}
				else
				{
					 ////// System.out.println("Inside Total Row");
					 alData = _objFormObject.getNGDataFromDataSource("SELECT count(IL_LOCATION_VALUE) FROM NG_IL_LOCATION_MASTER  ",1);
					 ////// System.out.println("count"+alData);
				}
				////// System.out.println("Out Total Row");
			}
			/************************End  CPI IL Location****************/
			
			/*************************** PID-CPI process changes ***************************/
			/**************************** CPI moDE_OF_PAYMENT****************/
			if((this.strSource.equalsIgnoreCase("CPI_moDE_OF_PAYMENT"))||(this.strSource.equalsIgnoreCase("CPI_MODE_OF_PAYMENT2"))||(this.strSource.equalsIgnoreCase("CPI_MODE_OF_PAYMENT3")))
			{
				if(fieldValue!=null)
				{
					////// System.out.println("Inside Total Row");
					 alData = _objFormObject.getNGDataFromDataSource("Select count(MODE_OF_PAYMENT) from  NG_CPI_PAYMENT_MODE_MASTER where UPPER(MODE_OF_PAYMENT) like UPPER(N'"+fieldValue+"%')",1);
					 
				}
				else
				{
					 ////// System.out.println("Inside Total Row");
					 alData = _objFormObject.getNGDataFromDataSource("SELECT count(MODE_OF_PAYMENT) FROM  NG_CPI_PAYMENT_MODE_MASTER",1);
				}
				////// System.out.println("Pick list count"+alData);
			}
			/************************End  CPI moDE_OF_PAYMENT****************/
			
			/**************************** CPI Hypothecated_to****************/
			if((this.strSource.equalsIgnoreCase("CPI_HYPOTHECATED_TO")))
			{
				if(fieldValue!=null)
				{
					////// System.out.println("Inside Hypo Total Row and fieldValue"+fieldValue);
					 alData = _objFormObject.getNGDataFromDataSource("Select count(DISTINCT Hypothecated_To) from  NG_CPI_HYPOTHECATED_MASTER where UPPER(Hypothecated_To) like UPPER(N'"+fieldValue+"%')",1); 
					 
				}
				else
				{
					 ////// System.out.println("Inside Hypo Total Row");
					 alData = _objFormObject.getNGDataFromDataSource("SELECT count(DISTINCT Hypothecated_To) FROM  NG_CPI_HYPOTHECATED_MASTER",1);
				}
				////// System.out.println("Pick list count"+alData);
			}
			/************************End  CPI Hypothecated_to****************/
			
			/************************* CPI URN CR 8001-61339 Multiple Changes CR *****************************/ 			
			if((this.strSource.equalsIgnoreCase("CPI_Exception_To_MH")))
			{
				if(fieldValue!=null)
				{
					////// System.out.println("Inside EXCEPTION Total Row and fieldValue:" +fieldValue);
					alData = _objFormObject.getNGDataFromDataSource("Select count(DISTINCT MH_EXCEPTION_NAME) from  NG_CPI_MHEXCEPTION_MASTER where UPPER(MH_EXCEPTION_NAME) like UPPER(N'"+fieldValue+"%')",1); 
					 
				}
				else
				{
					////// System.out.println("Inside EXCEPTION Total Row");
					alData = _objFormObject.getNGDataFromDataSource("SELECT count(DISTINCT MH_EXCEPTION_NAME) FROM  NG_CPI_MHEXCEPTION_MASTER",1);
				}
					////// System.out.println("Pick list count : " +alData);
			}
			/************************* End CPI URN CR 8001-61339 Multiple Changes CR **************************/
			
			
			/*********************** End PID-CPI process changes ***************************/
			
			/************************   CPI_SPECIFIED_PERSON ****************/
			if((this.strSource.equalsIgnoreCase("CPI_SPECIFIED_PERSON"))  || (this.strSource.equalsIgnoreCase("Corp_SP_Name")))
			{
				if(fieldValue!=null)
				{
					////// System.out.println("sandeep");
					////// System.out.println("Inside Total Row");
					 alData = _objFormObject.getNGDataFromDataSource("Select count(SPECIFIED_PERSON) from NG_CPI_SP_MASTER where UPPER(SPECIFIED_PERSON) like UPPER(N'"+fieldValue+"%')",1);
					 
					 ////// System.out.println("Pick list count"+alData);
					 // alData = _objFormObject.getNGDataFromDataSource("SELECT count(IL_LOCATION_VALUE) FROM NG_IL_LOCATION_MASTER  ",1);
				}
				else
				{
					 ////// System.out.println("Inside Total Row");
					 alData = _objFormObject.getNGDataFromDataSource("SELECT count(SPECIFIED_PERSON) FROM NG_CPI_SP_MASTER  ",1);
					 ////// System.out.println("count"+alData);
				}
				////// System.out.println("Out Total Row");
			}
			
			/************************ END  CPI_SPECIFIED_PERSON ****************/
			
				/**************************** CPI_PRIMARY_VERTICAL ****************/
			if((this.strSource.equalsIgnoreCase("CPI_PRIMARY_VERTICAL")))
			{
				if(fieldValue!=null)
				{
					////// System.out.println("sandeep Inside Total Row");
					////// System.out.println("satish Inside Total Row");
					 alData = _objFormObject.getNGDataFromDataSource("Select count(DISTINCT PRIMARY_VERTICAL_VALUE) from PRIMARY_VERT_DETAILS_MAS where UPPER(PRIMARY_VERTICAL_VALUE) like UPPER(N'"+fieldValue+"%')",1);
					 
					 ////// System.out.println("Pick list count"+alData);
				}
				else
				{
					 ////// System.out.println("Inside Total Row");
					 ////// System.out.println("Inside Total Row:satish");
					 alData = _objFormObject.getNGDataFromDataSource("SELECT count(DISTINCT PRIMARY_VERTICAL_VALUE) FROM PRIMARY_VERT_DETAILS_MAS  ",1);
					 ////// System.out.println("count"+alData);
				}
				////// System.out.println("Out Total Row");
			}
			/************************End  CPI_PRIMARY_VERTICAL****************/
			
			/**************************** CPI_PRIMARY_SUB_VERTICAL ****************/
			if((this.strSource.equalsIgnoreCase("CPI_PRIMARY_SUB_VERTICAL")))
			{
			
			
				if(fieldValue!=null)
				{
					////// System.out.println("sandeep Inside Total Row");
					 alData = _objFormObject.getNGDataFromDataSource("Select count(DISTINCT PRIMARY_SUB_VERTICAL_Value) from PRIMARY_VERT_DETAILS_MAS where PRIMARY_SUB_VERTICAL_VALUE!=' ' and UPPER(PRIMARY_SUB_VERTICAL_Value) like UPPER(N'"+fieldValue+"%')",1);
					 
					 ////// System.out.println("Pick list count"+alData);
					 // alData = _objFormObject.getNGDataFromDataSource("SELECT count(IL_LOCATION_VALUE) FROM NG_IL_LOCATION_MASTER  ",1);
				}
				else
				{
					 ////// System.out.println("Inside Total Row");
					 alData = _objFormObject.getNGDataFromDataSource("SELECT count(DISTINCT PRIMARY_SUB_VERTICAL_Value) FROM PRIMARY_VERT_DETAILS_MAS where PRIMARY_SUB_VERTICAL_VALUE!=' '",1);
					 ////// System.out.println("count"+alData);
				}
				////// System.out.println("Out Total Row");
			}
			
			/************************End  CPI_PRIMARY_SUB_VERTICAL****************/
/**** Start CR-8001-70893 Marine CR *******************************/
			if((this.strSource.equalsIgnoreCase("CPI_SECONDARY_VERTICAL")))
			{
				if(fieldValue!=null)
				{
					// System.out.println("Inside CPI_SECONDARY_VERTICAL Total Row");
					 alData = _objFormObject.getNGDataFromDataSource("select count(distinct secondry_vertical_value) from SEC_VERT_DETAILS_MAS where secondry_vertical_value!=' ' and UPPER(secondry_vertical_value) like UPPER(N'"+fieldValue+"%')",1);
					 // System.out.println("Pick list count"+alData);
					 // alData = _objFormObject.getNGDataFromDataSource("select count(IL_LOCATION_VALUE) FROM NG_IL_LOCATION_MASTER  ",1);
				}
				else
				{
					 // System.out.println("Inside Total Row");
					 alData = _objFormObject.getNGDataFromDataSource("select count(distinct secondry_vertical_value) FROM SEC_VERT_DETAILS_MAS where secondry_vertical_value!=' '",1);
					 // System.out.println("count"+alData);
				}
				// System.out.println("Out Total Row");
			}
			/**** ENd CR-8001-70893 Marine CR *********************************/

			/**************************** CPI_SECONDARY_SUB_VERTICAL ****************/
			if((this.strSource.equalsIgnoreCase("CPI_SECONDARY_SUB_VERTICAL")))
			{
				if(fieldValue!=null)
				{
					////// System.out.println("Inside Total Row");
					 alData = _objFormObject.getNGDataFromDataSource("Select count(DISTINCT SECONDRY_SUB_VERTICAL_VALUE) from SEC_VERT_DETAILS_MAS where SECONDRY_SUB_VERTICAL_VALUE!=' ' and UPPER(SECONDRY_SUB_VERTICAL_VALUE) like UPPER(N'"+fieldValue+"%')",1);
					 ////// System.out.println("Pick list count"+alData);
					 // alData = _objFormObject.getNGDataFromDataSource("SELECT count(IL_LOCATION_VALUE) FROM NG_IL_LOCATION_MASTER  ",1);
				}
				else
				{
					 ////// System.out.println("Inside Total Row");
					 alData = _objFormObject.getNGDataFromDataSource("SELECT count(DISTINCT SECONDRY_SUB_VERTICAL_VALUE) FROM SEC_VERT_DETAILS_MAS where SECONDRY_SUB_VERTICAL_VALUE!=' '",1);
					 ////// System.out.println("count"+alData);
				}
				////// System.out.println("Out Total Row");
			}
			/************************End  CPI_SECONDARY_SUB_VERTICAL****************/
			
			/****************************CPI_SOURCE_NAME ****************/
			if((this.strSource.equalsIgnoreCase("CPI_SOURCE_NAME")) || (this.strSource.equalsIgnoreCase("CORP_SOURCE_NAME")))
			{
				if(fieldValue!=null)
				{
					////// System.out.println("sandeep Inside Total Row");
					
					 alData = _objFormObject.getNGDataFromDataSource("Select count(SOURCE) from NG_CPI_SOURCE_MASTER where UPPER(SOURCE) like UPPER(N'"+fieldValue+"%') and  primary_sub_vertical_id="+bBGKRGID1+"",1);
					 
					 ////// System.out.println("Pick list count"+alData);
					 // alData = _objFormObject.getNGDataFromDataSource("SELECT count(IL_LOCATION_VALUE) FROM NG_IL_LOCATION_MASTER  ",1);
				}
				else
				{
					 ////// System.out.println("Inside Total Row");
					 alData = _objFormObject.getNGDataFromDataSource("Select count(SOURCE) from NG_CPI_SOURCE_MASTER where primary_sub_vertical_id="+bBGKRGID1 +"",1);
					 ////// System.out.println("count"+alData);
				}
				////// System.out.println("Out Total Row");
			}
			/************************End CPI_SOURCE_NAME****************/
			
			/**************************** Chanannel  ****************/
			if((this.strSource.equalsIgnoreCase("CPI_CHANNEL_NAME")) || (this.strSource.equalsIgnoreCase("CORP_CHANNEL_NAME")))
			{
				if(fieldValue!=null)
				{
					if(BBGKRGVAL1.equalsIgnoreCase("BBG") || BBGKRGVAL1.equalsIgnoreCase("BRANCH BRANCHING")
					/***************************** CR 28 by satish *****************************/
					|| BBGKRGVAL1.equalsIgnoreCase("SEG") || BBGKRGVAL1.equalsIgnoreCase("NA")
					/*************************** End CR 28 by satish ***************************/
					 || BBGKRGVAL1.equalsIgnoreCase("COB") // CR-OMCPI-1314-02 FIG COB CR
					)
					{
						////// System.out.println("sandeep Inside Total Row");
						 alData = _objFormObject.getNGDataFromDataSource("Select count(CHANNEL) from NG_CPI_CHANNEL_MASTER where UPPER(CHANNEL) like UPPER(N'"+fieldValue+"%') and     primary_sub_vertical_id="+bBGKRGID1+"",1);
						 
						 ////// System.out.println("Pick list count"+alData);
						 // alData = _objFormObject.getNGDataFromDataSource("SELECT count(IL_LOCATION_VALUE) FROM NG_IL_LOCATION_MASTER  ",1);
					}
					else if(BBGKRGVAL1.equalsIgnoreCase("KRG"))
					{
						alData = _objFormObject.getNGDataFromDataSource("Select count(CHANNEL) from NG_CPI_SOURCE_CHANNEL_MASTER A,NG_CPI_SOURCE_MASTER B,NG_CPI_CHANNEL_MASTER C  Where UPPER(CHANNEL) like UPPER(N'"+fieldValue+"%') and B.Recordid=A.Source_ID and C.Recordid=A.Channel_ID and A.SOURCE_ID="+sourceID1,1);
					}
				}
				else
				{
				
					if(BBGKRGVAL1.equalsIgnoreCase("BBG") || BBGKRGVAL1.equalsIgnoreCase("BRANCH BRANCHING") 
					/***************************** CR 28 by satish *****************************/
					|| BBGKRGVAL1.equalsIgnoreCase("SEG") || BBGKRGVAL1.equalsIgnoreCase("NA")
					/*************************** End CR 28 by satish ***************************/
					)
					{
					 ////// System.out.println("Inside Total Row");
					  alData = _objFormObject.getNGDataFromDataSource("Select count(CHANNEL) from NG_CPI_CHANNEL_MASTER where primary_sub_vertical_id="+bBGKRGID1+"",1);
					 }
					 else if(BBGKRGVAL1.equalsIgnoreCase("KRG"))
					 {
					 alData = _objFormObject.getNGDataFromDataSource("select count(CHANNEL) from NG_CPI_SOURCE_CHANNEL_MASTER A,NG_CPI_SOURCE_MASTER B,NG_CPI_CHANNEL_MASTER C  Where UPPER(CHANNEL) like UPPER(N'"+fieldValue+"%') and B.Recordid=A.Source_ID and C.Recordid=A.Channel_ID and A.SOURCE_ID="+sourceID1,1);
					 }
					 ////// System.out.println("count"+alData);
				}
				////// System.out.println("Out Total Row");
			}
			/************************End  Chanannel****************/
						/****************************Branch Name****************/
			if((this.strSource.equalsIgnoreCase("CPI_BRANCH_NAME")) || (this.strSource.equalsIgnoreCase("CORP_BRANCH_NAME")))
			{
			
			
				if(fieldValue!=null)
				{
					if(BBGKRGVAL1.equalsIgnoreCase("BBG") || BBGKRGVAL1.equalsIgnoreCase("BRANCH BRANCHING") 
					/***************************** CR 28 by satish *****************************/
					|| BBGKRGVAL1.equalsIgnoreCase("SEG") || BBGKRGVAL1.equalsIgnoreCase("NA")
					/*************************** End CR 28 by satish ***************************/
					 || BBGKRGVAL1.equalsIgnoreCase("COB") // CR-OMCPI-1314-02 FIG COB CR
					)
					{
				
						////// System.out.println("sandeep Inside Total Row");
						 alData = _objFormObject.getNGDataFromDataSource("Select count(BRANCH) from ng_cpi_branch_master where UPPER(BRANCH) like UPPER(N'"+fieldValue+"%') and     primary_sub_vertical_id="+bBGKRGID1+"",1);
						 
						 ////// System.out.println("Pick list count"+alData);
						 // alData = _objFormObject.getNGDataFromDataSource("SELECT count(IL_LOCATION_VALUE) FROM NG_IL_LOCATION_MASTER  ",1);
					}
					else if(BBGKRGVAL1.equalsIgnoreCase("KRG"))
					{
						alData = _objFormObject.getNGDataFromDataSource("select count(BRANCH) from NG_CPI_SOURCE_BRANCH_MASTER A,NG_CPI_SOURCE_MASTER B,NG_CPI_BRANCH_MASTER C Where UPPER(BRANCH) like UPPER(N'"+fieldValue+"%') and B.Recordid=A.Source_ID and C.Recordid=A.Branch_ID  and A.SOURCE_ID="+sourceID1,1);
					}
				
				
				}
				else
				{
					 ////// System.out.println("Inside Total Row");
					if(BBGKRGVAL1.equalsIgnoreCase("BBG") || BBGKRGVAL1.equalsIgnoreCase("BRANCH BRANCHING")
					/***************************** CR 28 by satish *****************************/
					|| BBGKRGVAL1.equalsIgnoreCase("SEG") || BBGKRGVAL1.equalsIgnoreCase("NA")
					/*************************** End CR 28 by satish ***************************/
					)
					{
						alData = _objFormObject.getNGDataFromDataSource("Select count(BRANCH) from ng_cpi_branch_master where primary_sub_vertical_id= "+bBGKRGID1+"",1);
					}
					else if(BBGKRGVAL1.equalsIgnoreCase("KRG") | BBGKRGVAL1.equalsIgnoreCase("KEY RELATIONSHIP GROUP")) //CR-8001-87420 Green File Digitization)
					{
						alData = _objFormObject.getNGDataFromDataSource("select count(BRANCH) from NG_CPI_SOURCE_BRANCH_MASTER A,NG_CPI_SOURCE_MASTER B,NG_CPI_BRANCH_MASTER C Where B.Recordid=A.Source_ID and C.Recordid=A.Branch_ID  and A.SOURCE_ID="+sourceID1,1);
					}
				}
				////// System.out.println("Out Total Row");
			}
			
			/**************************END Branch Name****************/
			
			/*****************  CPI_naME_OF_LEADER by satish for CR21 *********************/
			if(this.strSource.equalsIgnoreCase("CPI_naME_OF_LEADER"))
			{
				if(fieldValue!=null)
				{
					////// System.out.println("satish Inside Total Row");
					alData = _objFormObject.getNGDataFromDataSource("Select count(LEADER_NAME) from NG_CPI_LEADER_MST where LEADER_CATEGORY_TYPE_ID="+product_type_val+" and UPPER(LEADER_NAME) like UPPER(N'"+fieldValue+"%')",1);
					////// System.out.println("Pick list count"+alData);
					 // alData = _objFormObject.getNGDataFromDataSource("SELECT count(IL_LOCATION_VALUE) FROM NG_IL_LOCATION_MASTER  ",1);
				}
				else
				{
					 ////// System.out.println("Inside Total Row");
					 alData = _objFormObject.getNGDataFromDataSource("SELECT count(LEADER_NAME) FROM NG_CPI_LEADER_MST where LEADER_CATEGORY_TYPE_ID="+product_type_val+" ",1);
					 ////// System.out.println("count"+alData);
				}
				////// System.out.println("Out Total Row");
			}
			/***************** End CPI_naME_OF_LEADER by satish for CR21 *********************/
					
			/**************************** CPI Product Name ****************/
		
			if((this.strSource.equalsIgnoreCase("End_PRODUCT_NAME"))||(this.strSource.equalsIgnoreCase("Corp_PRODUCT_NAME"))||(this.strSource.equalsIgnoreCase("CPI_PRODUCT_NAME")))
			{
				if(fieldValue!=null)
				{
					////// System.out.println("sandeep Inside Total Row=="+fieldValue);
					/***************** CR21 *********************/
					if((_objFormObject.getWFActivityName().equalsIgnoreCase("Co_Insurance")) || (_objFormObject.getWFActivityName().equalsIgnoreCase("Co_Insurance_RM")))
					{
						alData = _objFormObject.getNGDataFromDataSource("Select count(product_name) from NG_PRODUCT_MASTER where PRODUCT_CATEGORY_ID="+product_type_val+" and UPPER(PRODUCT_NAME) like UPPER(N'"+fieldValue+"%')",1);
						////// System.out.println("Pick list count"+alData);
					}
					/******************* CR 46 CPU DataWashing********************/
					else if(_objFormObject.getNGValue("CPI_DATAWASHING_TYPE").equalsIgnoreCase("Endorsement"))
					{
						////// System.out.println("CR 46 CPI_DATAWASHING_TYPE: "+_objFormObject.getNGValue("CPI_DATAWASHING_TYPE"));
						//String testString = "select count(product_name) from NG_PRODUCT_MASTER where PRODUCT_TYPE_ID="+product_type_val+" AND PRODUCT_ROUTING = '2' AND UPPER(PRODUCT_NAME) like UPPER(N'"+fieldValue+"%')";
						alData = _objFormObject.getNGDataFromDataSource("select count(product_name) from NG_PRODUCT_MASTER where PRODUCT_TYPE_ID="+product_type_val+" AND PRODUCT_ROUTING = '2' AND UPPER(PRODUCT_NAME) like UPPER(N'"+fieldValue+"%')",1); 
						////// System.out.println("Pick list count of data washingcase"+alData);
					}
					/*****************end CR 46 CPU DataWashing*******************/
					else 
					{
					/*****************End CR21 *********************/
						 alData = _objFormObject.getNGDataFromDataSource("Select count(product_name) from NG_PRODUCT_MASTER where PRODUCT_TYPE_ID="+product_type_val+" and UPPER(PRODUCT_NAME) like UPPER(N'"+fieldValue+"%')",1);
						////// System.out.println("Pick list count"+alData);
						 // alData = _objFormObject.getNGDataFromDataSource("SELECT count(IL_LOCATION_VALUE) FROM NG_IL_LOCATION_MASTER  ",1);
					}
				}
				else
				{
					/***************** CR21 *********************/
					if((_objFormObject.getWFActivityName().equalsIgnoreCase("Co_Insurance")) || (_objFormObject.getWFActivityName().equalsIgnoreCase("Co_Insurance_RM")))
					{
						////// System.out.println("Inside Total Row PRODUCT_CATEGORY_ID");
						alData = _objFormObject.getNGDataFromDataSource("SELECT count(product_name) FROM NG_PRODUCT_MASTER where PRODUCT_CATEGORY_ID="+product_type_val+" ",1);
					}
					/******************* CR 46 CPU DataWashing********************/
					else if(_objFormObject.getNGValue("CPI_DATAWASHING_TYPE").equalsIgnoreCase("Endorsement"))
					{
						////// System.out.println("inside else CR 46 CPI_DATAWASHING_TYPE: "+_objFormObject.getNGValue("CPI_DATAWASHING_TYPE"));
						alData = _objFormObject.getNGDataFromDataSource("select count(product_name) FROM NG_PRODUCT_MASTER where PRODUCT_TYPE_ID="+product_type_val+" AND PRODUCT_ROUTING = '2'",1); 
						////// System.out.println("Pick list count of data washingcase"+alData);
					}/*****************end CR 46 CPU DataWashing*******************/
					else
					/*****************End CR21 *********************/
					{
						////// System.out.println("Inside Total Row PRODUCT_TYPE_ID");
						alData = _objFormObject.getNGDataFromDataSource("SELECT count(product_name) FROM NG_PRODUCT_MASTER where PRODUCT_TYPE_ID="+product_type_val+" ",1);
					 
					}
					////// System.out.println("count"+alData);
				}
				////// System.out.println("Out Total Row");
			}
			/************************End  CPI Product Name****************/
					
			//ICICILOMBARD_HT_IL_LOCATION
			if(this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_IL_LOCATION"))
			{
				if(fieldValue!=null)
				{
					  alData = _objFormObject.getNGDataFromDataSource("Select count(DISTINCT ILBRANCHNAME) from NG_ICICI_MST_ILLOCATION where UPPER(ILBRANCHNAME) like UPPER(N'"+fieldValue+"%')",1);
				}
				else
				{
					 
					 alData = _objFormObject.getNGDataFromDataSource("Select count(DISTINCT ILBRANCHNAME) from NG_ICICI_MST_ILLOCATION ",1);
				}
				////// System.out.println("ICICILOMBARD_HT_IL_LOCATION count:"+alData);
			}
			
			/******************************* PID-HT process changes ********************************/
			//ICICILOMBARD_HT_IL_LOCATION_CODE
			if(this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_IL_LOCATION_CODE"))
			{
				if(fieldValue!=null)
				{
					  alData = _objFormObject.getNGDataFromDataSource("Select count(DISTINCT ilbranchcode) from NG_ICICI_MST_ILLOCATION where UPPER(ilbranchcode) like UPPER(N'"+fieldValue+"%')",1);
				}
				else
				{
					 
					 alData = _objFormObject.getNGDataFromDataSource("Select count(DISTINCT ilbranchcode) from NG_ICICI_MST_ILLOCATION ",1);
				}
				////// System.out.println("ICICILOMBARD_HT_IL_LOCATION_CODE count:"+alData);
			}
			/******************************End PID-HT process changes ******************************/
			
			//ICICILOMBARD_HT_SEARCH_CRITERIA
			else if(this.strSource.equalsIgnoreCase("SEARCH_STRING"))
			{
				fieldValue=_objFormObject.getNGValue(this.strSource);
				tempStr=_objFormObject.getNGValue("ICICILOMBARD_HT_SEARCH_CRITERIA");
			//	////// System.out.println("tempStr--"+tempStr);

				if(tempStr.equals("Agent Name"))
				{
					Col="i.TXT_INTERMEDIARY_NAME";
				}
				else if (tempStr.equals("Agent Code"))
				{
					Col="i.TXT_INTERMEDIARY_CD";
				}
				else if (tempStr.equals("Deal No"))
				{
					Col="d.TXT_DEAL_ID";
				}
				if(tempStr.equals("Deal Status"))
				{
					Col="d.TXT_DISPLAY_RM_BS";
				}
				
				if(fieldValue!=null)
				{
					  alData = _objFormObject.getNGDataFromDataSource("Select count (i.TXT_INTERMEDIARY_CD) from MV_GENMST_INTERMEDIARY i, MV_Gen_Deal_Detail d where d.TXT_INTERMEDIARY_CD=i.TXT_INTERMEDIARY_CD and UPPER("+Col+") like UPPER(N'"+fieldValue+"%')",1);
				}
				else
				{
					 
					 alData = _objFormObject.getNGDataFromDataSource("Select count (i.TXT_INTERMEDIARY_CD) from MV_GENMST_INTERMEDIARY i, MV_Gen_Deal_Detail d where d.TXT_INTERMEDIARY_CD=i.TXT_INTERMEDIARY_CD",1);
				}
			}
			
			//ICICILOMBARD_HT_SOURCE_BUSINESS BBG/BBG
			else if((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_SOURCE_BUSINESS")) && ((_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking (BBG)") || _objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BBG"))))
			{
				if(fieldValue!=null)
				{
					  alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT SOURCEBUSINESS) from NG_ICICI_MST_BBG_BUSINESS where UPPER(SOURCEBUSINESS) like UPPER(N'"+fieldValue+"%')",1);
				}
				else
				{
					 
					 alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT SOURCEBUSINESS) from NG_ICICI_MST_BBG_BUSINESS",1);
				}
			}
			//ICICILOMBARD_HT_SOURCE_BUSINESS KRG2
			else if((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_SOURCE_BUSINESS")) && (_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)")))
			{
				if(fieldValue!=null)
				{
								//	////// System.out.println("in not null");
					  alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT TXTSOURCEBUSINESS) from NG_ICICI_MST_KRG where UPPER(TXTSOURCEBUSINESS) like UPPER(N'"+fieldValue+"%') and  TXTSOURCEBUSINESS is not NULL",1);
				}
				else
				{
				//	 ////// System.out.println("in null");
					 alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT TXTSOURCEBUSINESS) from NG_ICICI_MST_KRG where TXTSOURCEBUSINESS is not NULL",1);
				}
			}
			
			//ICICILOMBARD_HT_SOURCE_BUSINESS BBG/ISEC
			else if(this.strSource != null && this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_SOURCE_BUSINESS") && _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG") && _objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("ISEC"))
			{
				ArrayList a1= new ArrayList();
				a1.add("2");
				alData.add(a1);
			}
			
			//ICICILOMBARD_HT_CHANNEL_SOURCE source count	
			else if((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_CHANNEL_SOURCE")))
			{
				if(fieldValue!=null)
				{
								
					  alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT CHANNELSOURCING) from NG_ICICI_MST_BBG_SOURCE where CHANNELSOURCING like N'"+fieldValue+"%'",1);
				}
				else
				{
					 
					 alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT CHANNELSOURCING) from NG_ICICI_MST_BBG_SOURCE",1);
				}
			}
			
			/******************************* PID-HT process changes ********************************/
			//for ICICILOMBARD_HT_PAYMENT_MODE
			else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_PAYMENT_MODE")))
			{
				if(fieldValue!=null)
				{
					alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT Payment_Mode) from NG_HT_PAYMENT_MODE_MASTER where UPPER(Payment_Mode) like UPPER(N'"+fieldValue+"%')",1);
				}
				else
				{
					alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT Payment_Mode) from NG_HT_PAYMENT_MODE_MASTER",1);
				}	
			}
			/******************************End PID-HT process changes ******************************/	
			
			
			/**************************************Start HT SP Code CR CR-8093-69682 getCount*****************************************************/
			//for ICICILOMBARD_HT_DEAL_IL_LOCATION
			else if((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_DEAL_IL_LOCATION")))
			{

				String sm_id =_objFormObject.getNGValue("ICICILOMBARD_HT_SM_ID");
				if(fieldValue!=null)
				{
					  alData = _objFormObject.getNGDataFromDataSource("select count(distinct b.TXT_OFFICE) from MV_CENTRAL_EMPLOYEE a, MV_GENMST_OFFICE b where a.NUM_OFFICE_CD=b.NUM_OFFICE_CD and a.TXT_HR_REF_NO='"+sm_id+"' and UPPER(b.TXT_OFFICE) like UPPER(N'"+fieldValue+"%')",1);
				}
				else
				{
					 
					 alData = _objFormObject.getNGDataFromDataSource("select count(distinct b.TXT_OFFICE) from MV_CENTRAL_EMPLOYEE a, MV_GENMST_OFFICE b where a.NUM_OFFICE_CD=b.NUM_OFFICE_CD AND a.TXT_HR_REF_NO='"+sm_id+"' ",1);
				}
				//////// System.out.println("ICICILOMBARD_HT_DEAL_IL_LOCATION count:"+alData);
			}
			//for ICICILOMBARD_HT_BANK_BRANCH_NAME
 			else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_BANK_BRANCH_NAME")))
			{
				if( _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking (BBG)") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("HOME") || (_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG") && _objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BBG")))
				{
					if(fieldValue!=null)
					{
						  alData = _objFormObject.getNGDataFromDataSource("select count(distinct BBGBRANCHNAME) from NG_ICICI_MST_BBG_HOMEBRANCH where UPPER(BBGBRANCHNAME) like UPPER(N'"+fieldValue+"%')",1);
					}
					else
					{
						 alData = _objFormObject.getNGDataFromDataSource("select count(distinct BBGBRANCHNAME) from NG_ICICI_MST_BBG_HOMEBRANCH",1);
					}
				}
				if((_objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE"))&&(_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING")))
				{
					String channel_source=_objFormObject.getNGValue("ICICILOMBARD_HT_CHANNEL_SOURCE");
					String deal_il_location=_objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_IL_LOCATION");
					if(fieldValue!=null)
					{
						  alData = _objFormObject.getNGDataFromDataSource("select count(distinct BANK_BRANCH_NAME) from NG_HT_MST_SP_CODE where CHANNEL_SOURCE ='"+channel_source+"' and DEAL_IL_LOCATION='"+deal_il_location+"' and UPPER(BANK_BRANCH_NAME) like UPPER(N'"+fieldValue+"%')",1);
					}
					else
					{
						 alData = _objFormObject.getNGDataFromDataSource("select count(distinct BANK_BRANCH_NAME) from NG_HT_MST_SP_CODE where CHANNEL_SOURCE ='"+channel_source+"' and DEAL_IL_LOCATION='"+deal_il_location+"'",1);
					}
				}
				//////// System.out.println("ICICILOMBARD_HT_BANK_BRANCH_NAME count:"+alData);
			}
			//for ICICILOMBARD_HT_SOL_ID
			else if((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_SOL_ID")) && _objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE") && (_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING")))
			{
				String channel_source=_objFormObject.getNGValue("ICICILOMBARD_HT_CHANNEL_SOURCE");
				String deal_il_location=_objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_IL_LOCATION");
				if(!fieldValue.equalsIgnoreCase(""))
				{
					alData = _objFormObject.getNGDataFromDataSource("select count(distinct SOL_ID) from NG_HT_MST_SP_CODE where CHANNEL_SOURCE ='"+channel_source+"' and DEAL_IL_LOCATION='"+deal_il_location+"' and UPPER(SOL_ID) like UPPER(N'%"+fieldValue+"%')",1);
				}

				else
				{
					alData = _objFormObject.getNGDataFromDataSource("select count(distinct SOL_ID) from NG_HT_MST_SP_CODE where CHANNEL_SOURCE='"+channel_source+"' and DEAL_IL_LOCATION='"+deal_il_location+"'",1);
				}
				//////// System.out.println("ICICILOMBARD_HT_SOL_ID count query results:alData"+alData);
			}
			//for ICICILOMBARD_HT_WRE
			else if((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_WRE")) && _objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE") && (_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING")))
			{
				String deal_il_location=_objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_IL_LOCATION");
				String sol_id=_objFormObject.getNGValue("ICICILOMBARD_HT_SOL_ID");
				String channel_source=_objFormObject.getNGValue("ICICILOMBARD_HT_CHANNEL_SOURCE");
				if(!fieldValue.equalsIgnoreCase(""))
				{	
					alData = _objFormObject.getNGDataFromDataSource("select count(distinct SP_CODE) from NG_HT_MST_SP_CODE where DEAL_IL_LOCATION='"+deal_il_location+"'and CHANNEL_SOURCE ='"+channel_source+"' and sol_id='"+sol_id+"' and UPPER(SP_CODE) like UPPER(N'%"+fieldValue+"%')",1);
				}
				else
				{
					alData = _objFormObject.getNGDataFromDataSource("select count(distinct SP_CODE) from NG_HT_MST_SP_CODE where DEAL_IL_LOCATION='"+deal_il_location+"'and CHANNEL_SOURCE ='"+channel_source+"' and SOL_ID='"+sol_id+"'",1);
				}
				//////// System.out.println("ICICILOMBARD_HT_WRE count query results:alData"+alData);
			}					
			/**************************************End HT SP Code CR CR-8093-69682 getCount*****************************************************/
			/***************** Start SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI**************************************/
			//for  ICICILOMBARD_HT_WRE KRG
			else if((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_WRE")) && (_objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE")) && (_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)")))
			{
				// System.out.println("Inside Count SP code: Yogesh");
				if(!fieldValue.equalsIgnoreCase(""))
				{	
					// System.out.println("Inside  if Count SP code: Yogesh");
					alData = _objFormObject.getNGDataFromDataSource("select count(distinct SP_CODE) from NG_HT_SP_CODE_KRG where UPPER(SP_CODE) like UPPER(N'%"+fieldValue+"%')",1);
				}
				else
				{
					// System.out.println("Inside else Count SP code: Yogesh");
					alData = _objFormObject.getNGDataFromDataSource("select count(distinct SP_CODE) from NG_HT_SP_CODE_KRG",1);
				}
				// System.out.println("ICICILOMBARD_HT_WRE count1:"+alData);
			}
			/***************** End SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI**************************************/
			
			/******Start HT CR-8127-95325-GST-Omniflow development******/
			//TXTGST_STATE_NAME count
			else if((this.strSource != null) && (this.strSource.equals("TXTGST_STATE_NAME")) && (_objFormObject.getNGValue("ICICILOMBARD_HT_GST_REGISTERED").equalsIgnoreCase("Yes")) && !(_objFormObject.getNGValue("ICICILOMBARD_HT_IAGENT").equalsIgnoreCase("Yes")))
			{
				// System.out.println("Inside Count GST State: Yogesh");//select a.txtstatename,a.txtstatecode from NG_HT_MST_GST_STATE a
				if(!fieldValue.equalsIgnoreCase(""))
				{	
					// System.out.println("Inside  if Count GST State: Yogesh");
					alData = _objFormObject.getNGDataFromDataSource("select count(distinct txtstatename) from NG_HT_MST_GST_STATE where UPPER(txtstatename) like UPPER(N'%"+fieldValue+"%')",1);
				}
				else
				{
					// System.out.println("Inside else Count GST State: Yogesh");
					alData = _objFormObject.getNGDataFromDataSource("select count(distinct txtstatename) from NG_HT_MST_GST_STATE",1);
				}
				// System.out.println("TXTGST_STATE_NAME count1:"+alData);
				
			}
			/******End HT CR-8127-95325-GST-Omniflow development******/
			
			/*****Start Change related to Application  Proposal no. field in Omni flow HT*****/
			else if((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_CHANNEL_LOAN_TYPE")))
			{
				System.out.println("Inside Count Change related to Application  Proposal no. field in Omni flow HT Yogesh");
				if(!fieldValue.equalsIgnoreCase(""))
				{	
					System.out.println("Inside  if Count Change related to Application  Proposal no. field in Omni flow HT: Yogesh");
					alData = _objFormObject.getNGDataFromDataSource("select count(distinct channel) from NG_HT_MST_KRG_CHANNEL_SOURCE where UPPER(channel) like UPPER(N'%"+fieldValue+"%')",1);
				}
				else
				{
					System.out.println("Inside else Count Change related to Application  Proposal no. field in Omni flow HT: Yogesh");
					alData = _objFormObject.getNGDataFromDataSource("select count(distinct channel) from NG_HT_MST_KRG_CHANNEL_SOURCE",1);
				}
				System.out.println("ICICILOMBARD_HT_CHANNEL_LOAN_TYPE count1:"+alData);
				
			}			
			/*****End Change related to Application  Proposal no. field in Omni flow HT*****/
			//ICICILOMBARD_HT_BRANCH_ID_UBO_NAME id/ubo name
			else if ((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_BRANCH_ID_UBO_NAME")))
			{
				if(_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)"))
				{
					if(fieldValue!=null)
					{
						  alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT Txtbranchidubo) from NG_ICICI_MST_KRG where UPPER(Txtbranchidubo) like UPPER(N'"+fieldValue+"%')",1);
					}
					else
					{
						 alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT Txtbranchidubo) from NG_ICICI_MST_KRG",1);//MHT-PID CR-8127-59721 enhancemnet additional CR
					}
				////// System.out.println("ICICILOMBARD_HT_BRANCH_ID_UBO_NAME count1:"+alData);
				}	
			}
			
			//count sm_Id
			else if ((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_SM_NAME")))
			{
				// Changes made by Bhagat for Centralized Deal//
				if (_objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_STATUS").equalsIgnoreCase("YES"))
				{
					if(fieldValue!=null)
					{
						/*  search with name issue */
						  //alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT TXT_EMPLOYEE_NAME) from MV_CENTRAL_EMPLOYEE WHERE UPPER(TXT_EMPLOYEE_NAME) like UPPER(N'"+fieldValue+"%')",1);
						  alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT txt_hr_ref_no) from MV_CENTRAL_EMPLOYEE WHERE UPPER(TXT_EMPLOYEE_NAME) like UPPER(N'"+fieldValue+"%')",1);
					}
					else
					{
						/*  search with name issue */
						 //alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT TXT_EMPLOYEE_NAME) from MV_CENTRAL_EMPLOYEE",1);
						 alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT txt_hr_ref_no) from MV_CENTRAL_EMPLOYEE",1);
					}
						////// System.out.println("alData--MV_CENTRAL_EMPLOYEE-->SM_NAME GetCount:" + alData);
				}
				else
				{
					if(fieldValue!=null)
					{
						
						  alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT ME.TXT_EMPLOYEE_NAME) from MV_GENMST_EMPLOYEE ME, MV_GEN_DEAL_DETAIL GD WHERE GD.NUM_MO_EMPLOYEE_NO= ME.num_employee_cd and GD.txt_deal_id='"+_objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_NO")+"' and  UPPER(ME.TXT_EMPLOYEE_NAME)  like UPPER(N'"+fieldValue+"%')",1);
					}
					else
					{
						 alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT ME.TXT_EMPLOYEE_NAME) from MV_GENMST_EMPLOYEE ME, MV_GEN_DEAL_DETAIL GD WHERE GD.NUM_MO_EMPLOYEE_NO= ME.num_employee_cd and GD.txt_deal_id='"+_objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_NO")+"'",1);
					}

					//// System.out.println("alData--MV_CENTRAL_EMPLOYEE-->SM_NAME GetCount:" + alData);
				}
			}
			
			
			
				/*if(fieldValue!=null)
				{
					
					  alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT ME.TXT_EMPLOYEE_NAME) from MV_GENMST_EMPLOYEE ME, MV_GEN_DEAL_DETAIL GD WHERE GD.NUM_MO_EMPLOYEE_NO= ME.num_employee_cd and GD.txt_deal_id='"+_objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_NO")+"' and  UPPER(ME.TXT_EMPLOYEE_NAME)  like UPPER(N'"+fieldValue+"%')",1);
				}
				else
				{
					 alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT ME.TXT_EMPLOYEE_NAME) from MV_GENMST_EMPLOYEE ME, MV_GEN_DEAL_DETAIL GD WHERE GD.NUM_MO_EMPLOYEE_NO= ME.num_employee_cd and GD.txt_deal_id='"+_objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_NO")+"'",1);
				}*/
					
				
			
			//searching the bank name
			else if ((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_BANK_NAME")))
			{
				if(fieldValue!=null)
				{
					
					  alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT txtbankname) from NG_ICICI_MST_BankName WHERE UPPER(txtbankname) like UPPER(N'"+fieldValue+"%')",1);
				}
				else
				{
					 alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT txtbankname) from NG_ICICI_MST_BankName",1);
				}
					
			}
			
			//ICICILOMBARD_HT_SUB_PRODUCT sub product
			else if ((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_SUB_PRODUCT")))
			{
			
			
			/*if (_objFormObject.getNGValue("ICICILOMBARD_HT_PRODUCT_CODE").equalsIgnoreCase("") || _objFormObject.getNGValue("ICICILOMBARD_HT_PRODUCT_CODE").equalsIgnoreCase(null))
				{
			_objFormObject.setNGValue("ICICILOMBARD_HT_PRODUCT_CODE",_objFormObject.getNGItemText("PRODUCT_HIDDEN",1));
			////// System.out.println("ICICILOMBARD_HT_SUB_PRODUCT query "+_objFormObject.getNGValue("ICICILOMBARD_HT_PRODUCT_CODE"));
				}*/
				//vishal
				//setProductCode();

				/*if(fieldValue!=null)
				{
					
					  alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT TXT_IL_SUB_PRODUCT_NAME) from MV_UW_SUB_PRODUCT_MASTER WHERE UPPER(TXT_IL_SUB_PRODUCT_NAME) like UPPER(N'"+fieldValue+"%') and num_IL_product_code='"+_objFormObject.getNGValue("ICICILOMBARD_HT_PRODUCT_CODE")+"'",1);
					  ////// System.out.println("select count(DISTINCT TXT_IL_SUB_PRODUCT_NAME) from MV_UW_SUB_PRODUCT_MASTER WHERE UPPER(TXT_IL_SUB_PRODUCT_NAME) like UPPER(N'"+fieldValue+"%') and num_IL_product_code='"+_objFormObject.getNGValue("ICICILOMBARD_HT_PRODUCT_CODE")+"'");
				}
				else
				{
					 alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT TXT_IL_SUB_PRODUCT_NAME) from MV_UW_SUB_PRODUCT_MASTER and num_IL_product_code='"+_objFormObject.getNGValue("ICICILOMBARD_HT_PRODUCT_CODE")+"'",1);
					  ////// System.out.println("select count(DISTINCT TXT_IL_SUB_PRODUCT_NAME) from MV_UW_SUB_PRODUCT_MASTER and num_IL_product_code='"+_objFormObject.getNGValue("ICICILOMBARD_HT_PRODUCT_CODE")+"'" );
				}
				////// System.out.println("ICICILOMBARD_HT_SUB_PRODUCT count:"+alData);
					*/
				////// System.out.println("fieldValue :"+fieldValue);
				if(fieldValue == null || fieldValue.equalsIgnoreCase(""))
				{
					////// System.out.println("vishal count inside if :");
					 /* alData = _objFormObject.getNGDataFromDataSource("select count(*) from(select DISTINCT TXT_IL_SUB_PRODUCT_NAME,TXT_IL_PRODUCT_NO_SUFIX from  MV_UW_SUB_PRODUCT_MASTER a,MV_GEN_DEAL_DETAIL b  where  a.NUM_IL_PRODUCT_CODE=b.NUM_PRODUCT_CODE and TXT_DEAL_ID='"+ _objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_NO")+ "'",1);
					  ////// System.out.println("select count(*) from(select DISTINCT TXT_IL_SUB_PRODUCT_NAME,TXT_IL_PRODUCT_NO_SUFIX from  MV_UW_SUB_PRODUCT_MASTER a,MV_GEN_DEAL_DETAIL b  where  a.NUM_IL_PRODUCT_CODE=b.NUM_PRODUCT_CODE and TXT_DEAL_ID='"+ _objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_NO")+ "'" );*/
					  
					  //----------Made Changes by vishal/Yogendra to fetch sub product on basis of deal no-------
					  alData = _objFormObject.getNGDataFromDataSource("select count(*) from(select DISTINCT TXT_IL_SUB_PRODUCT_NAME,TXT_IL_PRODUCT_NO_SUFIX from  MV_UW_SUB_PRODUCT_MASTER a,MV_GEN_DEAL_DETAIL b,MV_UW_DEAL_PLAN_MAP c where a.NUM_IL_PRODUCT_CODE=b.NUM_PRODUCT_CODE and a.TXT_IL_SUB_PRODUCT_CODE=c.NUM_PLAN_CODE and b.TXT_DEAL_ID=c.TXT_DEAL_ID and b.TXT_DEAL_ID='"+ _objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_NO")+ "')",1);
					  ////// System.out.println("select count(*) from(select DISTINCT TXT_IL_SUB_PRODUCT_NAME,TXT_IL_PRODUCT_NO_SUFIX from  MV_UW_SUB_PRODUCT_MASTER a,MV_GEN_DEAL_DETAIL b,MV_UW_DEAL_PLAN_MAP c where a.NUM_IL_PRODUCT_CODE=b.NUM_PRODUCT_CODE and a.TXT_IL_SUB_PRODUCT_CODE=c.NUM_PLAN_CODE and b.TXT_DEAL_ID=c.TXT_DEAL_ID and b.TXT_DEAL_ID='"+ _objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_NO")+ "')" );
				}
				else //if(fieldValue != null || !fieldValue.equalsIgnoreCase(""))
				{
					 ////// System.out.println("vishal count inside else :");
					  alData = _objFormObject.getNGDataFromDataSource("select count(*) from(select DISTINCT TXT_IL_SUB_PRODUCT_NAME,TXT_IL_PRODUCT_NO_SUFIX from  MV_UW_SUB_PRODUCT_MASTER a,MV_GEN_DEAL_DETAIL b,MV_UW_DEAL_PLAN_MAP c where upper(TXT_IL_SUB_PRODUCT_NAME) like upper(N'"+fieldValue+"%') and a.NUM_IL_PRODUCT_CODE=b.NUM_PRODUCT_CODE and a.TXT_IL_SUB_PRODUCT_CODE=c.NUM_PLAN_CODE and b.TXT_DEAL_ID=c.TXT_DEAL_ID and b.TXT_DEAL_ID'"+ _objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_NO")+ "')",1);
					  ////// System.out.println("select count(*) from(select DISTINCT TXT_IL_SUB_PRODUCT_NAME,TXT_IL_PRODUCT_NO_SUFIX from  MV_UW_SUB_PRODUCT_MASTER a,MV_GEN_DEAL_DETAIL b,MV_UW_DEAL_PLAN_MAP c where upper(TXT_IL_SUB_PRODUCT_NAME) like upper(N'"+fieldValue+"%') and a.NUM_IL_PRODUCT_CODE=b.NUM_PRODUCT_CODE and a.TXT_IL_SUB_PRODUCT_CODE=c.NUM_PLAN_CODE and b.TXT_DEAL_ID=c.TXT_DEAL_ID and b.TXT_DEAL_ID'"+ _objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_NO")+ "')");
				}
				////// System.out.println("ICICILOMBARD_HT_SUB_PRODUCT count:"+alData);	
				//-----------END of  sub product on basis of deal no----------------
			}
			
			//ICICILOMBARD_HT_CENTER_CODE_NAME count
		
			else if ((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_CENTER_CODE_NAME")))
			{
				if(fieldValue!=null)
				{
					
					  alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT CENTCODE_NAME) from NG_ICICI_MST_CENTERCODE WHERE UPPER(CENTCODE_NAME) like UPPER(N'"+fieldValue+"%')",1);
				}
				else
				{
					 alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT CENTCODE_NAME) from NG_ICICI_MST_CENTERCODE",1);
				}
					
			}
			/************************** CR-OF-MHT-1314-01 MHTProcess Implementaion Start***********/
			// MHT_IL_LOCATION_CODE location
			else if((this.strSource != null) && (this.strSource.equals("MHT_IL_LOCATION_CODE")))
			{
				if(!fieldValue.equalsIgnoreCase(""))
				{
					alData = _objFormObject.getNGDataFromDataSource("Select count(DISTINCT ILBRANCHCODE) from NG_MHT_MST_ILLOCATION where UPPER(ILBRANCHCODE) like UPPER(N'"+fieldValue+"%')",1);
				}
				else
				{
					alData = _objFormObject.getNGDataFromDataSource("Select count(DISTINCT ILBRANCHCODE) from NG_MHT_MST_ILLOCATION ",1);
				}
				////// System.out.println("MHT_IL_LOCATION_CODE count query results:alData"+alData);
			}
			//MHT-PID Process Integration - Search provided on both location name and code
			else if((this.strSource != null) && (this.strSource.equals("MHT_IL_LOCATION")))//MHT-PID Process Integration - Search provided on both location name and code
			{
				if(!fieldValue.equalsIgnoreCase(""))
				{
					////// System.out.println("Inside if part of MHT_IL_LOCATION count query results");
					alData = _objFormObject.getNGDataFromDataSource("Select count(DISTINCT ILBRANCHNAME) from NG_MHT_MST_ILLOCATION where UPPER(ILBRANCHNAME) like UPPER(N'"+fieldValue+"%')",1);
				}
				else
				{
					////// System.out.println("Inside else part of MHT_IL_LOCATION count query results");
					alData = _objFormObject.getNGDataFromDataSource("Select count(DISTINCT ILBRANCHNAME) from NG_MHT_MST_ILLOCATION ",1);
				}
				////// System.out.println("MHT_IL_LOCATION count query results:alData"+alData);
			}
			//MHT_SEARCH_CRITERIA
			else if(this.strSource.equalsIgnoreCase("MHTSEARCH_STRING"))
			{
				fieldValue=_objFormObject.getNGValue(this.strSource);
				tempStr=_objFormObject.getNGValue("MHT_SEARCH_CRITERIA");
				////// System.out.println("MHTSEARCH_STRING count case:search criteria value"+tempStr);

				if(tempStr.equals("Agent Name"))
				{
                    Col="i.TXT_INTERMEDIARY_NAME";
				}
				else if (tempStr.equals("Agent Code"))
				{
                    Col="i.TXT_INTERMEDIARY_CD";
				}
				else if (tempStr.equals("Deal No"))
				{
                    Col="d.TXT_DEAL_ID";
				}
				else if(tempStr.equals("Manual CN"))
				{
					Col="txt_cust_covernote_no";
				}
				if(tempStr.equals("Deal Status"))
				{
                    Col="d.TXT_DISPLAY_RM_BS";
				}

				if(fieldValue!=null)
				{
					if(Col.equalsIgnoreCase("txt_cust_covernote_no"))
					{
						//change due to TXT_COVERNOTE_EXCEPTION_STATUS invalid identifier error
						/*alData = _objFormObject.getNGDataFromDataSource("Select count (i.txt_cust_covernote_no) from MV_MHT_OMNIFLOW_MANUAL_VIEW i where i.txt_cust_covernote_no is not null and UPPER(TXT_COVERNOTE_EXCEPTION_STATUS) != UPPER('Cancelled') and UPPER("+Col+") like UPPER(N'%"+fieldValue+"%')",1);*/
					alData = _objFormObject.getNGDataFromDataSource("Select count (i.txt_cust_covernote_no) from MV_MHT_OMNIFLOW_MANUAL_VIEW i where i.txt_cust_covernote_no is not null and UPPER("+Col+") like UPPER(N'%"+fieldValue+"%')",1);

					}
					else
					{
						alData = _objFormObject.getNGDataFromDataSource("Select count (i.TXT_INTERMEDIARY_CD) from mv_mht_genmst_intermediary i, mv_mht_gen_deal_detail d where d.TXT_INTERMEDIARY_CD=i.TXT_INTERMEDIARY_CD and UPPER("+Col+") like UPPER(N'"+fieldValue+"%')",1);
					}
				}
				else
				{
					if(Col.equalsIgnoreCase("txt_cust_covernote_no"))
					{
						alData = _objFormObject.getNGDataFromDataSource("Select count (i.txt_cust_covernote_no) from MV_MHT_OMNIFLOW_MANUAL_VIEW i where UPPER("+Col+") like UPPER(N'%"+fieldValue+"%')",1);
					}
					else
					{
						alData = _objFormObject.getNGDataFromDataSource("Select count (i.TXT_INTERMEDIARY_CD) from mv_mht_genmst_intermediary i, mv_mht_gen_deal_detail d where d.TXT_INTERMEDIARY_CD=i.TXT_INTERMEDIARY_CD",1);
					}
				}
                                ////// System.out.println("count query results:alData"+alData);
			}
			/*************************** MHT-PID Process Integration ****************************/
			//modified code to fetch vertical code also
			else if((this.strSource != null) && (this.strSource.equals("MHT_PRIMARY_VERTICAL")))
			{
				if(fieldValue!=null)
				{
					alData = _objFormObject.getNGDataFromDataSource("Select count(prim_vert_name) from ng_mht_mst_prim_vertical where UPPER(prim_vert_name) like UPPER(N'"+fieldValue+"%')",1);
				}
				else
				{
					alData = _objFormObject.getNGDataFromDataSource("Select count(prim_vert_name) from ng_mht_mst_prim_vertical ",1);
				}
			}
			else if((this.strSource != null) && (this.strSource.equals("MHT_SUB_VERTICAL")))
			{
				if(fieldValue!=null)
				{
					alData = _objFormObject.getNGDataFromDataSource("Select count(sec_vert_name) from ng_mht_mst_sec_vertical where UPPER(sec_vert_name) like UPPER(N'"+fieldValue+"%')",1);
				}
				else
				{
					alData = _objFormObject.getNGDataFromDataSource("Select count(sec_vert_name) from ng_mht_mst_sec_vertical ",1);
				}

			}
			/*********************** End MHT-PID Process Integration ****************************/
			else if((this.strSource != null) && (this.strSource.equals("MHT_PRODUCT_NAME")))
			{
				if(fieldValue!=null)
				{
					alData = _objFormObject.getNGDataFromDataSource("Select count(DISTINCT productname) from ng_mht_mst_product where UPPER(productname) like UPPER(N'"+fieldValue+"%')",1);
				}
				else
				{
					alData = _objFormObject.getNGDataFromDataSource("Select count(DISTINCT productname) from ng_mht_mst_product ",1);
				}

			}

			//MHT_SOURCE_BUSINESS BBG/BBG
			else if((this.strSource != null) && (this.strSource.equalsIgnoreCase("MHT_SOURCE_BUSINESS")) && ((_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BBG") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking (BBG)") || _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BBG"))))
			{
				if(fieldValue!=null)
				{
					alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT SOURCEBUSINESS) from NG_MHT_MST_BBG_BUSINESS where UPPER(SOURCEBUSINESS) like UPPER(N'"+fieldValue+"%')",1);
				}
				else
				{

					alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT SOURCEBUSINESS) from NG_MHT_MST_BBG_BUSINESS",1);
				}
			}
			//MHT_SOURCE_BUSINESS KRG2
			else if((this.strSource != null) && (this.strSource.equalsIgnoreCase("MHT_SOURCE_BUSINESS")) && (_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") ||
			_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("KRG")))
			{
				////// System.out.println("MHT_SOURCE_BUSINESS count");
				if(fieldValue!=null)
				{
									////// System.out.println("MHT_SOURCE_BUSINESS in not null");
					alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT TXTSOURCEBUSINESS) from NG_MHT_MST_KRG where UPPER(TXTSOURCEBUSINESS) like UPPER(N'"+fieldValue+"%') and  TXTSOURCEBUSINESS is not NULL",1);
				}
				else
				{
					 ////// System.out.println("MHT_SOURCE_BUSINESS in null");
					alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT TXTSOURCEBUSINESS) from NG_MHT_MST_KRG where TXTSOURCEBUSINESS is not NULL",1);
				}
			}

			//MHT_SOURCE_BUSINESS BBG/ISEC
			else if(this.strSource != null && this.strSource.equalsIgnoreCase("MHT_SOURCE_BUSINESS") && _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BBG") && _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("ISEC"))
			{
				ArrayList a1= new ArrayList();
				a1.add("2");
				alData.add(a1);
			}

			//MHT_CHANNEL_SOURCE source count
			else if((this.strSource != null) && (this.strSource.equalsIgnoreCase("MHT_CHANNEL_SOURCE")))
			{
				////// System.out.println(" MHT_CHANNEL_SOURCE count  start");
				if(fieldValue!=null)
				{
					alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT CHANNELSOURCING) from NG_MHT_MST_BBG_SOURCE where CHANNELSOURCING like N'"+fieldValue+"%'",1);
				}
				else
				{
					alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT CHANNELSOURCING) from NG_MHT_MST_BBG_SOURCE",1);
				}
				////// System.out.println(" MHT_CHANNEL_SOURCE count=="+alData);
			}

			/*********************************************Start MHT SP Code CR-8127-69652 getcount**********************************/
			//for bank branch name getcount
			else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("MHT_BRANCH_ID")))
			{
				
				if(_objFormObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Policy Issuance") && _objFormObject.getNGValue("MHT_TYPE_OF_BUSINESS").equalsIgnoreCase("Intermediary") && _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BANCASSURANCE") && !_objFormObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER") && _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING"))
				{
					String channel_source=_objFormObject.getNGValue("MHT_CHANNEL_SOURCE");
					String deal_il_location=_objFormObject.getNGValue("MHT_DEAL_IL_LOCATION");
					if(fieldValue!=null)
					{
						alData = _objFormObject.getNGDataFromDataSource("select count(distinct SOL_ID) from NG_MHT_MST_SP_CODE where CHANNEL_SOURCE ='"+channel_source+"' and DEAL_IL_LOCATION='"+deal_il_location+"' and UPPER(BANK_BRANCH_NAME) like UPPER(N'"+fieldValue+"%')",1);
					}
					else
					{
						alData = _objFormObject.getNGDataFromDataSource("select count(distinct SOL_ID) from NG_MHT_MST_SP_CODE where CHANNEL_SOURCE ='"+channel_source+"' and DEAL_IL_LOCATION='"+deal_il_location+"'",1);
					}
					////// System.out.println("MHT_BANK_BRANCH_NAME count:"+alData);
				}
				else if( _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BBG") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking (BBG)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("KRG") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("HOME")|| (_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BBG") && _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BBG")))
				{	
					if(fieldValue!=null)
					{
						//alData = _objFormObject.getNGDataFromDataSource("select count(distinct BBGBRANCHNAME) from NG_MHT_MST_BBG_HOMEBRANCH where UPPER(BBGBRANCHNAME) like UPPER(N'"+fieldValue+"%')",1);
						alData = _objFormObject.getNGDataFromDataSource("select count(distinct branch_id) from NG_MHT_MST_BBGANDHOMEBRANCH where UPPER(branch_id) like UPPER(N'"+fieldValue+"%')",1);
					}
					else
					{
						//alData = _objFormObject.getNGDataFromDataSource("select count(distinct BBGBRANCHNAME) from NG_MHT_MST_BBG_HOMEBRANCH",1);
						alData = _objFormObject.getNGDataFromDataSource("select count(distinct branch_id) from NG_MHT_MST_BBGANDHOMEBRANCH",1);						
					}
				}
				
				// System.out.println("MHT_BRANCH_ID count:"+alData);
			}
			//for sol id getcount
			else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("MHT_BANK_BRANCH_NAME")))
			{
				if(_objFormObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Policy Issuance") && _objFormObject.getNGValue("MHT_TYPE_OF_BUSINESS").equalsIgnoreCase("Intermediary") && _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BANCASSURANCE") && !_objFormObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER") && _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING"))
				{
					String channel_source=_objFormObject.getNGValue("MHT_CHANNEL_SOURCE");
					String deal_il_location=_objFormObject.getNGValue("MHT_DEAL_IL_LOCATION");
					if(fieldValue!=null)
					{
						alData = _objFormObject.getNGDataFromDataSource("Select count(distinct SOL_ID) from NG_MHT_MST_SP_CODE where CHANNEL_SOURCE ='"+channel_source+"' and DEAL_IL_LOCATION='"+deal_il_location+"' and UPPER(SOL_ID) like UPPER(N'%"+fieldValue+"%')",1);
					}
					else
					{
						alData = _objFormObject.getNGDataFromDataSource("Select count(distinct SOL_ID) from NG_MHT_MST_SP_CODE where CHANNEL_SOURCE='"+channel_source+"' and DEAL_IL_LOCATION='"+deal_il_location+"'",1);

					}
					////// System.out.println("ICICILOMBARD_HT_SOL_ID count query results:alData"+alData);
				}
						
			}
			//for sp code getcount
			else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("MHT_SP_CODE")))
			{
				if(_objFormObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Policy Issuance") && _objFormObject.getNGValue("MHT_TYPE_OF_BUSINESS").equalsIgnoreCase("Intermediary") && _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BANCASSURANCE") && !_objFormObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER") && _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING"))
				{
					String deal_il_location=_objFormObject.getNGValue("MHT_DEAL_IL_LOCATION");
					String sol_id=_objFormObject.getNGValue("MHT_BRANCH_ID");
					String channel_source=_objFormObject.getNGValue("MHT_CHANNEL_SOURCE");
					if(fieldValue!=null)
					{	
						alData = _objFormObject.getNGDataFromDataSource("Select count(distinct SP_CODE) from NG_MHT_MST_SP_CODE where DEAL_IL_LOCATION='"+deal_il_location+"'and CHANNEL_SOURCE ='"+channel_source+"' and sol_id='"+sol_id+"' and UPPER(SP_CODE) like UPPER(N'%"+fieldValue+"%')",1);
					}
					else
					{
						alData = _objFormObject.getNGDataFromDataSource("Select count(distinct SP_CODE) from NG_MHT_MST_SP_CODE where DEAL_IL_LOCATION='"+deal_il_location+"'and CHANNEL_SOURCE ='"+channel_source+"' and SOL_ID='"+sol_id+"'",1);
					}
					////// System.out.println("MHT_SP_CODE count query results:alData"+alData);
				}
				/*******************Start SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI**********************/
				else
				{
					if(!((_objFormObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Policy Issuance") && _objFormObject.getNGValue("MHT_TYPE_OF_BUSINESS").equalsIgnoreCase("Direct"))) && (_objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BANCASSURANCE") || _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BA")) && (_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("KRG")) && !(_objFormObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER")))
					{
						// System.out.println("Inside get count sp code logic change");
						if(!fieldValue.equalsIgnoreCase(""))
						{	
							// System.out.println("Inside  if Count SP code: Yogesh");
							alData = _objFormObject.getNGDataFromDataSource("select count(distinct SP_CODE) from NG_MHT_SP_CODE_KRG where UPPER(SP_CODE) like UPPER(N'%"+fieldValue+"%')",1);
						}
						else
						{
							// System.out.println("Inside else Count SP code: Yogesh");
							alData = _objFormObject.getNGDataFromDataSource("select count(distinct SP_CODE) from NG_MHT_SP_CODE_KRG",1);
						}
						// System.out.println("MHT_SP_CODE count query results:alData"+alData);					
					}
				}	
				/*******************End SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI**********************/		
			}
			
			/*********************************************End MHT SP Code CR-8127-69652 getcount**********************************/
			/******Start MHT CR-8127-95325-GST-Omniflow development******/
			//MHT_TXTGST_STATE_NAME count
			else if((this.strSource != null) && (this.strSource.equals("MHT_TXTGST_STATE_NAME")) && (_objFormObject.getNGValue("MHT_GST_REGISTERED").equalsIgnoreCase("Yes")) && !(_objFormObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER")))
			{
				// System.out.println("Inside Count GST State: Yogesh");//select a.txtstatename,a.txtstatecode from NG_MHT_MST_GST_STATE a
				if(!fieldValue.equalsIgnoreCase(""))
				{	
					// System.out.println("Inside  if Count GST State: Yogesh");
					alData = _objFormObject.getNGDataFromDataSource("select count(distinct txtstatename) from NG_MHT_MST_GST_STATE where UPPER(txtstatename) like UPPER(N'%"+fieldValue+"%')",1);
				}
				else
				{
					// System.out.println("Inside else Count GST State: Yogesh");
					alData = _objFormObject.getNGDataFromDataSource("select count(distinct txtstatename) from NG_MHT_MST_GST_STATE",1);
				}
				// System.out.println("MHT_TXTGST_STATE_NAME count1:"+alData);
				
			}
			/******End MHT CR-8127-95325-GST-Omniflow development******/

			//MHT_BRANCH_ID_UBO_NAME id/ubo name
			else if ((this.strSource != null) && (this.strSource.equals("MHT_BRANCH_ID_UBO_NAME")))
			{
				if(_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") ||
				_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("KRG"))
				{
					if(fieldValue!=null)
					{
						alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT Txtbranchidubo) from NG_MHT_MST_KRG where UPPER(Txtbranchidubo) like UPPER(N'"+fieldValue+"%')",1);
					}
					else
					{
						alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT Txtbranchidubo) from NG_MHT_MST_KRG",1);//MHT-PID CR-8127-59721 enhancemnet additional CR						
					}
				////// System.out.println("MHT_BRANCH_ID_UBO_NAME count1:"+alData);
				}
			}

			//count sm_Id MHT_SM_NAME
			else if ((this.strSource != null) && (this.strSource.equals("MHT_SM_NAME")))
			{
				/**************************** MHT-PID Process Integration ***************************/
				if(fieldValue!=null)
				{
					alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT TXT_HR_REF_NO) from MV_MHT_GENMST_EMPLOYEE WHERE  UPPER(PRIMARY_MO_NAME)  like UPPER(N'"+fieldValue+"%')",1);
					//alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT ME.EMP_CODE) from NG_MHT_MST_RM ME WHERE  UPPER(ME.RM_NAME)  like UPPER(N'"+fieldValue+"%')",1);
				}
				else
				{
					alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT TXT_HR_REF_NO) from MV_MHT_GENMST_EMPLOYEE",1);
					//alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT ME.EMP_CODE) from NG_MHT_MST_RM ME",1);
				}
				/************************ End MHT-PID Process Integration ***************************/
				////// System.out.println("alData--MHT_SM_NAME-->" + alData);
			}
			


			//searching the bank name
			else if ((this.strSource != null) && (this.strSource.equals("MHT_PAYMENT_TYPE1_BANKNAME") || this.strSource.equals("MHT_PAYMENT_TYPE2_BANKNAME") || this.strSource.equals("MHT_PAYMENT_TYPE3_BANKNAME")))
			{
				if(fieldValue!=null)
				{

					  alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT txtbankname) from NG_MHT_MST_BankName WHERE UPPER(txtbankname) like UPPER(N'"+fieldValue+"%')",1);
				}
				else
				{
					 alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT txtbankname) from NG_MHT_MST_BankName",1);
				}
                ////// System.out.println("searching the bank name--MHT_PAYMENT_TYPE1_BANKNAME-->" + alData);

			}
			
			/*************************** MHT -PID Process Integration ****************************/
			//searching the payment mode
			else if ((this.strSource != null) && (this.strSource.equals("MHT_PAYMENT_TYPE1") || this.strSource.equals("MHT_PAYMENT_TYPE2") || this.strSource.equals("MHT_PAYMENT_TYPE3")))
			{
				if(fieldValue!=null)
				{

					  alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT payment_mode) from NG_MHT_PAYMENT_MODE_MASTER WHERE UPPER(payment_mode) like UPPER(N'"+fieldValue+"%')",1);
				}
				else
				{
					 alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT payment_mode) from NG_MHT_PAYMENT_MODE_MASTER",1);
				}
                ////// System.out.println("searching the bank name--MHT_PAYMENT_TYPE1-->" + alData);

			}
			/************************* End MHT -PID Process Integration **************************/

			//ICICILOMBARD_HT_CENTER_CODE_NAME count

			else if ((this.strSource != null) && (this.strSource.equals("MHT_CENTER_CODE_NAME")))
			{
				if(fieldValue!=null)
				{

					  alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT CENTCODE_NAME) from NG_ICICI_MST_CENTERCODE WHERE UPPER(CENTCODE_NAME) like UPPER(N'"+fieldValue+"%')",1);
				}
				else
				{
					 alData = _objFormObject.getNGDataFromDataSource("select count(DISTINCT CENTCODE_NAME) from NG_ICICI_MST_CENTERCODE",1);
				}

			}
			/************************** CR-OF-MHT-1314-01 MHTProcess Implementaion End***********/
			
            if (alData != null && alData.size() > 0)
            {
                ArrayList alRow = (ArrayList) alData.get(0);
                return Integer.parseInt(alRow.get(0).toString());
            }
            
        }
        catch (Exception ex)
        {
			////// System.out.println("count excptn");
            ex.printStackTrace();
        }
        return 0;
    } 


    //----------------------------------------------------------------------------------------------------
    //Function Name 			: NGPickList_Loading
    //Date Written (DD/MM/YYYY)	: 14/04/2009
    //Author					: Vikas Tyagi
    //Input Parameters			: e
    //Output Parameters			: none
    //Return Values				: none
    //Description				: Hanldes load event of picklist.
    //----------------------------------------------------------------------------------------------------
    public void NGPickList_Loading(NGEvent e)
    {    
		////// System.out.println("Load Pk");
		NGPickList localNGPickList = (NGPickList)e.getSource();
    	this.objProcessData = new XMLParser();
    	this.objProcessData.setInputXML(this._objFormObject.getWFGeneralData());
		String fieldValue=_objFormObject.getNGValue(this.strSource);
		////// System.out.println("str value----"+strSource);
		ArrayList alRows = new ArrayList(); 
		boolean result;
		////// System.out.println("in load-----"+_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL"));
		/*****Start CR-8001-87420-Green File Digitization*****/
		//============= CPI CR Quote SYS===============
		

		if ((this.strSource != null) && (this.strSource.equals("CPI_RM_ID")))
		{
			if(_objFormObject.getNGValue("CPI_IS_PF_FETCH").equalsIgnoreCase("Yes"))
			{
				// System.out.println("4RM ID KeyPress(F3)");
				_iTotalRecord=getTotalRecord("");
				colCount=6;			
				this._strQuery ="select distinct TXT_HR_REF_NO,TXT_EMPLOYEE_NAME,PRIM_VERT,PRIM_VERT_CD,PRIM_SUBVERT,PRIM_SUBVERT_CD FROM (select distinct TXT_HR_REF_NO,TXT_EMPLOYEE_NAME,PRIM_VERT,PRIM_VERT_CD,PRIM_SUBVERT,PRIM_SUBVERT_CD from mv_cpi_vw_employee_omni ORDER BY TXT_HR_REF_NO) where ROWNUM <= " + this._iBatchSize;
			}
			else
			{
			// System.out.println("4RM ID KeyPress(F3)");
			_iTotalRecord=getTotalRecord("");
			colCount=2;			
			this._strQuery ="select distinct EMP_CODE, RM_NAME   FROM (select distinct EMP_CODE, RM_NAME   from NG_RM_MASTER ORDER BY EMP_CODE) where ROWNUM <= " + this._iBatchSize;
			}
		}
		// System.out.println("LOAD"+_strQuery);
		//============= End CPI CR Quote SYS===============
		//TXT_HR_REF_NO,TXT_EMPLOYEE_NAME
		
		//additional cit/rm id 
		if ((this.strSource != null) && (this.strSource.equals("CPI_ADDTNL_CIT_RM_ID") || this.strSource.equals("CPI_UW_EMP_ID") || this.strSource.equals("CPI_SECONDARY_MO_ID") || this.strSource.equalsIgnoreCase("CPI_RM_EMP_ID_SPG_IBG")))
		{
			// System.out.println(this.strSource+" NGPickList_Loading...");
			_iTotalRecord=getTotalRecord("");
			colCount=2;			
			this._strQuery ="select distinct TXT_HR_REF_NO,TXT_EMPLOYEE_NAME FROM (select distinct TXT_HR_REF_NO,TXT_EMPLOYEE_NAME from MV_CPI_VW_EMPLOYEE_OMNI ORDER BY TXT_HR_REF_NO) where ROWNUM <= " + this._iBatchSize;
		}
		//uw_emp_id
		/*if ((this.strSource != null) && (this.strSource.equals("CPI_UW_EMP_ID")))
		{
			// System.out.println("CPI_UW_EMP_ID KeyPress(F3)");
			_iTotalRecord=getTotalRecord("");
			colCount=2;			
			this._strQuery ="select distinct TXT_HR_REF_NO,TXT_EMPLOYEE_NAME FROM (select distinct TXT_HR_REF_NO,TXT_EMPLOYEE_NAME from MV_CPI_VW_EMPLOYEE_OMNI ORDER BY TXT_HR_REF_NO) where ROWNUM <= " + this._iBatchSize;
		}*/
		//prev_policy_no
		if ((this.strSource != null) && (this.strSource.equals("CPI_PREV_POLICY_NO")))
		{
			// System.out.println(this.strSource+" NGPickList_Loading...");
			_iTotalRecord=getTotalRecord("");
			colCount=3;			
			this._strQuery ="select TXT_POLICY_NO_CHAR,TXT_CUSTOMER_ID,TXT_CUSTOMER_NAME from (select GEN.TXT_POLICY_NO_CHAR,TXT_CUSTOMER_ID,TXT_CUSTOMER_NAME FROM GEN_PROP_INFORMATION_TAB@omnitopf GEN,UW_PRODUCT_MASTER@omnitopf UPM where GEN.NUM_PRODUCT_CODE = UPM.PRODUCTCODE and GEN.TXT_POLICY_NO_CHAR is not null ORDER BY GEN.TXT_POLICY_NO_CHAR) where ROWNUM <= " + this._iBatchSize;
		}
		//base policy number
		if ((this.strSource != null) && (this.strSource.equals("CPI_POLICY_NUMBER_BASE")))
		{
			// System.out.println(this.strSource+" NGPickList_Loading...");
			_iTotalRecord=getTotalRecord("");
			colCount=3;			
			this._strQuery ="select TXT_POLICY_NO_CHAR,DAT_POLICY_EFF_FROMDATE,TXT_CUSTOMER_ID,TXT_CUSTOMER_NAME,PRODUCTNAME,NUM_IL_PRODUCT_CODE FROM (select GEN.TXT_POLICY_NO_CHAR,DAT_POLICY_EFF_FROMDATE,TXT_CUSTOMER_ID,TXT_CUSTOMER_NAME,UPM.PRODUCTNAME,UPM.NUM_IL_PRODUCT_CODE FROM GEN_PROP_INFORMATION_TAB@omnitopf GEN,UW_PRODUCT_MASTER@omnitopf UPM where GEN.NUM_PRODUCT_CODE = UPM.PRODUCTCODE and GEN.TXT_POLICY_NO_CHAR is not null ORDER BY GEN.TXT_POLICY_NO_CHAR) where ROWNUM <= " + this._iBatchSize;
		}
		/*****End CR-8001-87420-Green File Digitization*****/
		/**************************************Client Registration CR Start*****************************************************/
		if ((this.strSource != null) && (this.strSource.equals("CPI_POLICYNO_SEARCH")))
		{
			// System.out.println("CPI_POLICYNO_SEARCH KeyPress(F3)");
			_iTotalRecord=getTotalRecord("");
			colCount=2;			
			this._strQuery ="select distinct POLICY_NUMBER,PRODUCT_NAME,CST_RM_EMP_ID,CST_RM_NAME,IL_LOCATION,PRIMARY_VERTICAL,PRIMARY_SUB_VERTICAL,CUSTOMER_ID,BROKER_AGENT_NAME,CLIENT_NAME,PAYMENT_MODE,CD_ACCOUNT_NUMBER,PRODUCT_IRDACODE,PRIMARY_VERTICAL_CODE,PRIMARY_SUB_VERTICAL_CODE FROM (select distinct POLICY_NUMBER,PRODUCT_NAME,CST_RM_EMP_ID,CST_RM_NAME,IL_LOCATION,PRIMARY_VERTICAL,PRIMARY_SUB_VERTICAL,CUSTOMER_ID,BROKER_AGENT_NAME,CLIENT_NAME,PAYMENT_MODE,CD_ACCOUNT_NUMBER,PRODUCT_IRDACODE,PRIMARY_VERTICAL_CODE,PRIMARY_SUB_VERTICAL_CODE from NG_CPI_CLIENT_REG_MST where UPPER(REGNO_STATUS) = 'APPROVED' ORDER BY POLICY_NUMBER) where ROWNUM <= " + this._iBatchSize;
		}
		/**************************************Client Registration CR End*****************************************************/	
		/********************CR-OMCPI-1314-03 CPU DataWashing Start**********************/
		if ((this.strSource != null) && (this.strSource.equals("CPI_CPU_ASSIGN_TO")))
		{
			////// System.out.println("4 CPI_CPU_ASSIGN_TO KeyPress(F3)");
			_iTotalRecord=getTotalRecord("");
			colCount=2;			
			/**** Start CR-8001-70893 Marine CR *******************************/
			String prodCategory="";
			prodCategory = _objFormObject.getWFActivityName().equalsIgnoreCase("CPU_Assignment")?"DW_Health":"Marine";			
			//this._strQuery ="select distinct EMP_ID, EMP_NAME   FROM (select distinct EMP_ID, EMP_NAME from NG_CPI_DW_ESCALATION_MASTER ORDER BY EMP_ID) where ROWNUM <= " + this._iBatchSize;
			this._strQuery ="select distinct EMP_ID, EMP_NAME   FROM (select distinct EMP_ID, EMP_NAME from NG_CPI_DW_ESCALATION_MASTER where prod_category = '"+prodCategory+"' ORDER BY EMP_ID) where ROWNUM <= " + this._iBatchSize;
			/**** ENd CR-8001-70893 Marine CR *********************************/
		}
		////// System.out.println("LOAD"+_strQuery);
		/********************CR-OMCPI-1314-03 CPU DataWashing END**********************/
		
		/********************* CR 45 Network Partner *****************************/
		if ((this.strSource != null) && (this.strSource.equals("CPI_NETWORK_PARTNER_NAME")))
		{
			////// System.out.println("4CPI_NETWORK_PARTNER_NAME KeyPress(F3)");
			_iTotalRecord=getTotalRecord("");
			colCount=2;			
			this._strQuery ="SELECT DISTINCT NETWORK_PARTNER_NAME FROM (select DISTINCT NETWORK_PARTNER_NAME from NG_CPI_NTWRK_PARTNER_MASTER ORDER BY NETWORK_PARTNER_NAME) where ROWNUM <= " + this._iBatchSize;
		}
		////// System.out.println("LOAD"+_strQuery);
		/********************* End CR 45 Network Partner *************************/
		
		/****************** CO Insurance CR 18 *************************/
		//satish
		if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_RM_Name")))
		{
			////// System.out.println("Inside RM Name sandeep ");
			////// System.out.println("4RM ID KeyPress(F3)");
			_iTotalRecord=getTotalRecord("");
			colCount=2;			
			this._strQuery ="SELECT DISTINCT  RM_NAME,EMP_CODE   FROM (select DISTINCT EMP_CODE, RM_NAME   from NG_RM_MASTER ORDER BY EMP_CODE) where ROWNUM <= " + this._iBatchSize;
		}
		////// System.out.println("LOAD"+_strQuery);
		//satish
		/****************** End  CO Insurance CR 18 *************************/
		
		/********************* CR 28 by satish *****************************/
		//satish
		if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_NAME_OF_BROKER_AGENT")))
		{
			////// System.out.println("Inside NAME_OF_BROKER satish ");
			////// System.out.println("KeyPress(F3)");
			_iTotalRecord=getTotalRecord("");
			/*************************** PID-CPI process changes ***************************/
			//colCount=2;	
			//this._strQuery ="SELECT AGENT_NAME,IBANKAGENT   FROM (select DISTINCT AGENT_NAME,IBANKAGENT  from ng_cpi_agent_master ORDER BY AGENT_NAME) where ROWNUM <= " + this._iBatchSize;
			colCount=3;	
			this._strQuery ="SELECT AGENT_NAME,IBANKAGENT,INTERMEDIARY_CODE  FROM (select DISTINCT AGENT_NAME,IBANKAGENT,INTERMEDIARY_CODE  from ng_cpi_agent_master ORDER BY AGENT_NAME) where ROWNUM <= " + this._iBatchSize;
			/*********************** End PID-CPI process changes ***************************/
		}
		////// System.out.println("LOAD"+_strQuery);
		//satish
		/********************** End of CR 28 ********************************/
		
		/************************  CPI IL Location****************/
		
		if ((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_IL_Location"))||(this.strSource.equalsIgnoreCase("Corp_IL_Location"))||(this.strSource.equalsIgnoreCase("End_IL_Location"))))
		{
			////// System.out.println("Inside Load Condition KeyPress(F3)");
			_iTotalRecord=getTotalRecord("");
			colCount=2;			
			this._strQuery ="SELECT  IL_LOCATION_VALUE FROM NG_IL_LOCATION_MASTER  where ROWNUM <= " + this._iBatchSize +" order by IL_LOCATION_VALUE";
		}
		////// System.out.println("LOAD sandeep "+_strQuery);
	
	/************************End  CPI IL Location****************/
			
	/*************************** PID-CPI process changes ***************************/
	/**************************** CPI moDE_OF_PAYMENT****************/
		if ((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_moDE_OF_PAYMENT"))||(this.strSource.equalsIgnoreCase("CPI_MODE_OF_PAYMENT2"))||(this.strSource.equalsIgnoreCase("CPI_MODE_OF_PAYMENT3"))))
		{
			////// System.out.println("Inside Load Condition KeyPress(F3)");
			_iTotalRecord=getTotalRecord("");
			colCount=2;			
			this._strQuery ="SELECT MODE_OF_PAYMENT FROM NG_CPI_PAYMENT_MODE_MASTER  where ROWNUM <= " + this._iBatchSize +" order by MODE_OF_PAYMENT";
		}
	/************************End  CPI moDE_OF_PAYMENT****************/
	/*********************** End PID-CPI process changes ***************************/
	
	/**************************** CPI Hypothecated_to****************/
		if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_HYPOTHECATED_TO")))
		{
			////// System.out.println("Inside Load Condition KeyPress(F3)");
			_iTotalRecord=getTotalRecord("");
			colCount=2;			
			this._strQuery ="SELECT HYPOTHECATED_TO FROM (SELECT DISTINCT HYPOTHECATED_TO FROM NG_CPI_HYPOTHECATED_MASTER order by HYPOTHECATED_TO) where ROWNUM <= " + this._iBatchSize +"";
		}
	/************************End  CPI Hypothecated_to****************/	
	
	/************************* CPI URN CR 8001-61339 Multiple Changes CR *****************************/ 
		if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_Exception_To_MH")))
		{
			////// System.out.println("Inside Load Condition KeyPress(F3)");
			_iTotalRecord=getTotalRecord("");
			colCount=2;			
			this._strQuery ="SELECT MH_EXCEPTION_NAME FROM (SELECT DISTINCT MH_EXCEPTION_NAME FROM NG_CPI_MHEXCEPTION_MASTER order by MH_EXCEPTION_NAME) where ROWNUM <= " + this._iBatchSize +"";
		}
/*************************End CPI URN CR 8001-61339 Multiple Changes CR **************************/
	
	
		
	/************************  CPI_SPECIFIED_PERSON****************/
	
		if ((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_SPECIFIED_PERSON")) || (this.strSource.equalsIgnoreCase("Corp_SP_Name"))))
		{
			////// System.out.println("Inside Load Condition KeyPress(F3)");
			_iTotalRecord=getTotalRecord("");
			colCount=1;			
			this._strQuery ="SELECT  SPECIFIED_PERSON FROM NG_CPI_SP_MASTER  where ROWNUM <= " + this._iBatchSize +"order by SPECIFIED_PERSON";
		}
		////// System.out.println("LOAD sandeep "+_strQuery);
	
	/************************End  CPI_SPECIFIED_PERSON****************/
	
	/************************  PRIMARY_VERTICAL_Value****************/
		
		if ((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_PRIMARY_VERTICAL"))))
		{
			////// System.out.println("satish: Inside Load Condition KeyPress(F3)");
			_iTotalRecord=getTotalRecord("");
			/*************************** PID-CPI process changes ***************************/
			//colCount=1;		
			colCount=2;	
			//this._strQuery ="select PRIMARY_VERTICAL_VALUE from (SELECT  DISTINCT PRIMARY_VERTICAL_VALUE FROM PRIMARY_VERT_DETAILS_MAS) where ROWNUM <= " + this._iBatchSize +"order by PRIMARY_VERTICAL_VALUE";
			this._strQuery ="select PRIMARY_VERTICAL_VALUE,PRIMARY_VERTICAL_CODE from (SELECT  DISTINCT PRIMARY_VERTICAL_VALUE,PRIMARY_VERTICAL_CODE FROM PRIMARY_VERT_DETAILS_MAS) where ROWNUM <= " + this._iBatchSize +"order by PRIMARY_VERTICAL_VALUE";
			/*********************** End PID-CPI process changes ***************************/
		}
		////// System.out.println("LOAD"+_strQuery);
	
	/************************End  PRIMARY_VERTICAL_Value****************/
	
	/************************  PRIMARY_SUB_VERTICAL_Value****************/
		
		if ((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_PRIMARY_SUB_VERTICAL"))))
		{
			////// System.out.println("Inside Load Condition KeyPress(F3)");
			_iTotalRecord=getTotalRecord("");
			/*************************** PID-CPI process changes ***************************/
			colCount=2;
			//this._strQuery ="select PRIMARY_SUB_VERTICAL_VALUE from (SELECT  DISTINCT PRIMARY_SUB_VERTICAL_VALUE FROM PRIMARY_VERT_DETAILS_MAS where PRIMARY_SUB_VERTICAL_VALUE!=' ') where ROWNUM <= " + this._iBatchSize +"order by PRIMARY_SUB_VERTICAL_VALUE";
			this._strQuery ="select PRIMARY_SUB_VERTICAL_VALUE,prim_sub_vert_code from (SELECT  DISTINCT PRIMARY_SUB_VERTICAL_VALUE,prim_sub_vert_code FROM PRIMARY_VERT_DETAILS_MAS where PRIMARY_SUB_VERTICAL_VALUE!=' ') where ROWNUM <= " + this._iBatchSize +"order by PRIMARY_SUB_VERTICAL_VALUE";
			/*********************** End PID-CPI process changes ***************************/
		}
		////// System.out.println("LOAD"+_strQuery);
	
	/************************End  PRIMARY_SUB_VERTICAL_Value****************/
	/**** Start CR-8001-70893 Marine CR *******************************/
		if ((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_SECONDARY_VERTICAL"))))
		{
			// System.out.println("CPI_SECONDARY_VERTICAL Inside Load Condition KeyPress(F3)");
			_iTotalRecord=getTotalRecord("");
			colCount=2;		

			this._strQuery ="select secondry_vertical_value from (select  distinct secondry_vertical_value FROM SEC_VERT_DETAILS_MAS where secondry_vertical_value!=' ') where ROWNUM <= " + this._iBatchSize +"order by secondry_vertical_value";
		}
		// System.out.println("LOAD"+_strQuery);
	/**** ENd CR-8001-70893 Marine CR *********************************/	/************************ SECONDRY_SUB_VERTICAL_VALUE **************************/
		if ((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_SECONDARY_SUB_VERTICAL"))))
		{
			////// System.out.println("sandeep Inside Load Condition KeyPress(F3)");
			_iTotalRecord=getTotalRecord("");
			colCount=2;		

			this._strQuery ="select SECONDRY_SUB_VERTICAL_VALUE from (SELECT  DISTINCT SECONDRY_SUB_VERTICAL_VALUE FROM SEC_VERT_DETAILS_MAS where SECONDRY_SUB_VERTICAL_VALUE!=' ') where ROWNUM <= " + this._iBatchSize +"order by SECONDRY_SUB_VERTICAL_VALUE";
		}
		////// System.out.println("LOAD"+_strQuery);

		/********************************** End CPI_SECONDARY_SUB_VERTICAL *****************/
		
		/************************  CPI_SOURCE_NAME****************/
		
		if ((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_SOURCE_NAME")) || (this.strSource.equalsIgnoreCase("CORP_SOURCE_NAME"))))
		{
			////// System.out.println("Inside Load Condition KeyPress(F3)");
			_iTotalRecord=getTotalRecord("");
			colCount=2;			
			this._strQuery ="SELECT  SOURCE FROM NG_CPI_SOURCE_MASTER  where ROWNUM <= " + this._iBatchSize +" and  primary_sub_vertical_id="+bBGKRGID1 +" order by SOURCE";
		}
		////// System.out.println("LOAD sandeep "+_strQuery);
	
	/************************End  CPI_SOURCE_NAME****************/
	
	/************************  CPI_CHANNEL_NAME****************/
		
		if ((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_CHANNEL_NAME")) || (this.strSource.equalsIgnoreCase("CORP_CHANNEL_NAME"))))
		{
			////// System.out.println("Inside Load Condition KeyPress(F3)");
			_iTotalRecord=getTotalRecord("");
			colCount=2;		

				if(BBGKRGVAL1.equalsIgnoreCase("BBG")|| BBGKRGVAL1.equalsIgnoreCase("BRANCH BRANCHING") 
				/***************************** CR 28 by satish *****************************/
				|| BBGKRGVAL1.equalsIgnoreCase("SEG") || BBGKRGVAL1.equalsIgnoreCase("NA")
				/*************************** End CR 28 by satish ***************************/
				 || BBGKRGVAL1.equalsIgnoreCase("COB") // CR-OMCPI-1314-02 FIG COB CR
				)
				{
					this._strQuery ="SELECT  CHANNEL FROM NG_CPI_CHANNEL_MASTER  where ROWNUM <= " + this._iBatchSize +" and  primary_sub_vertical_id="+bBGKRGID1 +" order by CHANNEL";
				}
				else if(BBGKRGVAL1.equalsIgnoreCase("KRG")|| BBGKRGVAL1.equalsIgnoreCase("KEY RELATIONSHIP GROUP")) //CR-8001-87420 Green File Digitization)
				{
					//this._strQuery ="select CHANNEL from NG_CPI_SOURCE_CHANNEL_MASTER A,NG_CPI_SOURCE_MASTER B,NG_CPI_CHANNEL_MASTER C  Where B.Recordid=A.Source_ID and C.Recordid=A.Channel_ID and A.SOURCE_ID="+sourceID1+" and ROWNUM <= " + this._iBatchSize+" ";
					
					
					this._strQuery ="select CHANNEL from (select CHANNEL from NG_CPI_SOURCE_CHANNEL_MASTER A,NG_CPI_SOURCE_MASTER B,NG_CPI_CHANNEL_MASTER C Where B.Recordid=A.Source_ID and C.Recordid=A.Channel_ID  and A.SOURCE_ID="+sourceID1+" order by c.CHANNEL) where  ROWNUM <= " + this._iBatchSize;
					
					//this._strQuery ="select CHANNEL from NG_CPI_SOURCE_CHANNEL_MASTER A,NG_CPI_SOURCE_MASTER B,NG_CPI_CHANNEL_MASTER C Where B.Recordid=A.Source_ID and C.Recordid=A.Channel_ID  and A.SOURCE_ID="+sourceID1+" and ROWNUM <= " + this._iBatchSize;
					//this._strQuery ="SELECT  CHANNEL FROM NG_CPI_SOURCE_CHANNEL_MASTER  where ROWNUM <= " + this._iBatchSize +" and  primary_sub_vertical_id="+bBGKRGID1 +" order by CHANNEL";
				}
		}
		////// System.out.println("LOAD sandeep "+_strQuery);
	
	/************************End CPI_CHANNEL_NAME****************/
	
	/************************  CPI_BRANCH_NAME****************/
		
		if ((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_BRANCH_NAME")) || (this.strSource.equalsIgnoreCase("CORP_BRANCH_NAME"))))
		{
			////// System.out.println("Inside Load Condition KeyPress(F3)");
			_iTotalRecord=getTotalRecord("");
			//colCount=2;	//PID_CPI process changes
			colCount=10;
			if(BBGKRGVAL1.equalsIgnoreCase("BBG") || BBGKRGVAL1.equalsIgnoreCase("BRANCH BRANCHING") 
			/***************************** CR 28 by satish *****************************/
			|| BBGKRGVAL1.equalsIgnoreCase("SEG") || BBGKRGVAL1.equalsIgnoreCase("NA")
			/*************************** End CR 28 by satish ***************************/
			 || BBGKRGVAL1.equalsIgnoreCase("COB") // CR-OMCPI-1314-02 FIG COB CR
			)
			{
				/*************************** PID-CPI process changes ***************************/
				//this._strQuery ="SELECT  BRANCH FROM ng_cpi_branch_master  where ROWNUM <= " + this._iBatchSize +" and  primary_sub_vertical_id="+bBGKRGID1+ "  order by BRANCH";
				this._strQuery ="SELECT  BRANCH,branch_id,sp_name1,sp_code1,sp_name2,sp_code2,sp_name3,sp_code3,sp_name4,sp_code4 FROM ng_cpi_branch_master  where ROWNUM <= " + this._iBatchSize +" and  primary_sub_vertical_id="+bBGKRGID1+ "  order by BRANCH";
				
			}
			else if(BBGKRGVAL1.equalsIgnoreCase("KRG")|| BBGKRGVAL1.equalsIgnoreCase("KEY RELATIONSHIP GROUP"))//CR-8001-87420 Green File Digitization)
			{
				//this._strQuery ="select BRANCH from NG_CPI_SOURCE_BRANCH_MASTER A,NG_CPI_SOURCE_MASTER B,NG_CPI_BRANCH_MASTER C Where B.Recordid=A.Source_ID and C.Recordid=A.Branch_ID  and A.SOURCE_ID="+sourceID1+" and ROWNUM <= " + this._iBatchSize;
				this._strQuery ="select BRANCH,C.branch_id,sp_name1,sp_code1,sp_name2,sp_code2,sp_name3,sp_code3,sp_name4,sp_code4 from NG_CPI_SOURCE_BRANCH_MASTER A,NG_CPI_SOURCE_MASTER B,NG_CPI_BRANCH_MASTER C Where B.Recordid=A.Source_ID and C.Recordid=A.Branch_ID  and A.SOURCE_ID="+sourceID1+" and ROWNUM <= " + this._iBatchSize;
				/*********************** End PID-CPI process changes ***************************/
			}
		}
		////// System.out.println("LOAD sandeep "+_strQuery);
	
		/************************End  CPI_BRANCH_NAME****************/
	
		/*****************  CPI_naME_OF_LEADER by satish for CR21 *********************/
		if(this.strSource.equalsIgnoreCase("CPI_naME_OF_LEADER"))
		{
			////// System.out.println("Inside Load Condition KeyPress(F3)");
			_iTotalRecord=getTotalRecord("");
			colCount=2;	
			this._strQuery ="SELECT  LEADER_NAME   FROM (select  LEADER_NAME   from NG_CPI_LEADER_MST where  LEADER_CATEGORY_TYPE_ID="+product_type_val+" ORDER BY LEADER_NAME)  where ROWNUM <= " + this._iBatchSize;			
			
		}
		////// System.out.println("LOAD satish "+_strQuery);
		/***************** End CPI_naME_OF_LEADER by satish for CR21 *********************/	
			
		/************************  CPI Product Name****************/
		
		if((this.strSource.equalsIgnoreCase("End_PRODUCT_NAME"))||(this.strSource.equalsIgnoreCase("Corp_PRODUCT_NAME"))||(this.strSource.equalsIgnoreCase("CPI_PRODUCT_NAME")))
		{
			////// System.out.println("Inside Load Condition KeyPress(F3)");
			////// System.out.println("ActivityName : " + _objFormObject.getWFActivityName());
			////// System.out.println("CPI_cO_INSURANCE_STATUS : " + _objFormObject.getNGValue("CPI_cO_INSURANCE_STATUS"));
			_iTotalRecord=getTotalRecord("");
			colCount=2;
			/***************** CR21 *********************/
			if((_objFormObject.getWFActivityName().equalsIgnoreCase("Co_Insurance")) || (_objFormObject.getWFActivityName().equalsIgnoreCase("Co_Insurance_RM")))
			{
				this._strQuery ="SELECT  PRODUCT_NAME,IRDACODE   FROM (select PRODUCT_NAME,IRDACODE from NG_PRODUCT_MASTER where  PRODUCT_CATEGORY_ID="+product_type_val+" ORDER BY PRODUCT_NAME)  where ROWNUM <= " + this._iBatchSize;//Vendor login & weather Product
			} /*****************End CR21 *********************/
			/******************* CR 46 CPU DataWashing********************/
			else if(_objFormObject.getNGValue("CPI_DATAWASHING_TYPE").equalsIgnoreCase("Endorsement"))
			{
				this._strQuery = "SELECT PRODUCT_NAME,IRDACODE  FROM (select PRODUCT_NAME,IRDACODE from NG_PRODUCT_MASTER WHERE PRODUCT_TYPE_ID="+product_type_val+" AND PRODUCT_ROUTING = '2' ORDER BY PRODUCT_NAME) where ROWNUM <=" + this._iBatchSize;//Vendor login & weather Product
			} /*****************end CR 46 CPU DataWashing*******************/
			else
			{
				this._strQuery ="SELECT  PRODUCT_NAME,IRDACODE   FROM (select PRODUCT_NAME,IRDACODE from NG_PRODUCT_MASTER where  PRODUCT_TYPE_ID="+product_type_val+" ORDER BY PRODUCT_NAME)  where ROWNUM <= " + this._iBatchSize;//Vendor login & weather Product			
			}
		}
		////// System.out.println("LOAD sandeep "+_strQuery);
	
	/************************End  CPI Product Name****************/
			
		//il location loading
		if ((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_IL_LOCATION")))
		{
			_iTotalRecord=getTotalRecord("");
			colCount=2;
			this._strQuery ="SELECT DISTINCT ILBRANCHNAME, ILBRANCHCODE  FROM (select DISTINCT ILBRANCHNAME, ILBRANCHCODE  from NG_ICICI_MST_ILLOCATION ORDER BY ILBRANCHCODE) where ROWNUM <= " + this._iBatchSize;
			////// System.out.println("ICICILOMBARD_HT_IL_LOCATION query "+_strQuery);
		}
		
		/******************************* PID-HT process changes ********************************/
		//il location code loading
		if ((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_IL_LOCATION_CODE")))
		{
			_iTotalRecord=getTotalRecord("");
			colCount=2;
			this._strQuery ="SELECT DISTINCT ILBRANCHNAME, ILBRANCHCODE  FROM (select DISTINCT ILBRANCHNAME, ILBRANCHCODE  from NG_ICICI_MST_ILLOCATION ORDER BY ILBRANCHCODE) where ROWNUM <= " + this._iBatchSize;
			////// System.out.println("ICICILOMBARD_HT_IL_LOCATION_CODE query "+_strQuery);
		}
		/******************************End PID-HT process changes ******************************/
		
		//search criteria loading
		else if	((this.strSource != null) && (this.strSource.equals("SEARCH_STRING")))
		{
			colCount=4;
			_iTotalRecord=getTotalRecord(fieldValue);
			tempStr=_objFormObject.getNGValue("ICICILOMBARD_HT_SEARCH_CRITERIA");
			if(tempStr.equals("Agent Name"))
			{
				Col="i.TXT_INTERMEDIARY_NAME";
			}
			else if (tempStr.equals("Agent Code"))
			{
				Col="i.TXT_INTERMEDIARY_CD";
			}
			else if (tempStr.equals("Deal No"))
			{
				Col="d.TXT_DEAL_ID";
			}
			if(tempStr.equals("Deal Status"))
			{
				Col="d.TXT_DISPLAY_RM_BS";
			}
				
			if(fieldValue!=null)
			{

				this._strQuery ="select TXT_INTERMEDIARY_NAME,TXT_INTERMEDIARY_CD,txt_deal_id,TXT_DISPLAY_RM_BS from (select DISTINCT d.txt_deal_id as txt_deal_id,i.TXT_INTERMEDIARY_NAME as TXT_INTERMEDIARY_NAME,i.TXT_INTERMEDIARY_CD as TXT_INTERMEDIARY_CD,d.TXT_DISPLAY_RM_BS as TXT_DISPLAY_RM_BS from MV_GENMST_INTERMEDIARY i, MV_Gen_Deal_Detail d where d.TXT_INTERMEDIARY_CD=i.TXT_INTERMEDIARY_CD and UPPER("+Col+") like UPPER(N'"+fieldValue+"%') ORDER BY i.TXT_INTERMEDIARY_CD) where ROWNUM <= " + this._iBatchSize;
			}
			else
			{
				this._strQuery ="select TXT_INTERMEDIARY_NAME,TXT_INTERMEDIARY_CD,txt_deal_id,TXT_DISPLAY_RM_BS from (select DISTINCT d.txt_deal_id as txt_deal_id,i.TXT_INTERMEDIARY_NAME as TXT_INTERMEDIARY_NAME,i.TXT_INTERMEDIARY_CD as TXT_INTERMEDIARY_CD,d.TXT_DISPLAY_RM_BS as TXT_DISPLAY_RM_BS from MV_GENMST_INTERMEDIARY i, MV_Gen_Deal_Detail d where d.TXT_INTERMEDIARY_CD=i.TXT_INTERMEDIARY_CD ORDER BY i.TXT_INTERMEDIARY_CD) where ROWNUM <= " + this._iBatchSize;
			}
			////// System.out.println("LOAD"+_strQuery);
		}	
		
		
		
		//channel source loading(BBG)
		else if((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_SOURCE_BUSINESS")) && ((_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking (BBG)") || _objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BBG"))))
		{
			_iTotalRecord=getTotalRecord("");
			colCount=1;
			
			this._strQuery ="SELECT SOURCEBUSINESS FROM (select DISTINCT SOURCEBUSINESS from NG_ICICI_MST_BBG_BUSINESS ORDER BY SOURCEBUSINESS) where ROWNUM <= " + this._iBatchSize;
		}
		
		//SOURCE BUSINESS
		else if(this.strSource != null && this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_SOURCE_BUSINESS") && (
_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)")))
		{
			_iTotalRecord=getTotalRecord("");
			colCount=1;
		//	////// System.out.println("in load");
			this._strQuery = "select TXTSOURCEBUSINESS from (select DISTINCT TXTSOURCEBUSINESS from NG_ICICI_MST_KRG where TXTSOURCEBUSINESS is not NULL ORDER BY TXTSOURCEBUSINESS) where  ROWNUM <= " + this._iBatchSize;
		}
			
			//source business next
			/* else if(this.strSource != null && this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_SOURCE_BUSINESS") && _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equals("BBG") && _objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equals("ISEC"))	
			{
				//_iTotalRecord=2;
				//First row 
				////// System.out.println("adding elements");
				ArrayList alRow = new ArrayList(); 
				alRow.add("Center Sales"); 
				alRows.add(alRow); 

				//Second Row 
				alRow = new ArrayList(); 
				alRow.add("Sub Broker"); 
				alRows.add(alRow); 
				////// System.out.println("adding elements end"+alRows);
			} */
			
			//channel source
			else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_CHANNEL_SOURCE")))
			{
				_iTotalRecord=getTotalRecord("");
				colCount=1;
				this._strQuery ="select CHANNELSOURCING from(select DISTINCT CHANNELSOURCING from NG_ICICI_MST_BBG_SOURCE ORDER BY CHANNELSOURCING) where ROWNUM <= " + this._iBatchSize;
			}
			
			/******************************* PID-HT process changes ********************************/
			//for ICICILOMBARD_HT_PAYMENT_MODE
			else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_PAYMENT_MODE")))
			{
				_iTotalRecord=getTotalRecord("");
				colCount=1;
				this._strQuery ="select Payment_Mode from(select DISTINCT Payment_Mode from NG_HT_PAYMENT_MODE_MASTER ORDER BY Payment_Mode) where ROWNUM <= " + this._iBatchSize;
			}
			/******************************End PID-HT process changes ******************************/
			
			/**************************************Start HT SP Code CR CR-8093-69682 Loading*****************************************************/
			//deal_il_location loading
			else if((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_DEAL_IL_LOCATION")))
			{
				String sm_id =_objFormObject.getNGValue("ICICILOMBARD_HT_SM_ID");
				_iTotalRecord=getTotalRecord("");
				colCount=1;
				this._strQuery ="select distinct b.TXT_OFFICE from MV_CENTRAL_EMPLOYEE a, MV_GENMST_OFFICE b where a.NUM_OFFICE_CD=b.NUM_OFFICE_CD and a.TXT_HR_REF_NO='"+sm_id+"' and ROWNUM <= " + this._iBatchSize;
				//////// System.out.println("ICICILOMBARD_HT_DEAL_IL_LOCATION loading "+this._strQuery);
			}
			//bank branch loading
			//select BANK_BRANCH_NAME from NG_HT_MST_SP_CODE
			else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_BANK_BRANCH_NAME")))
			{

				if(_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking")|| _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking (BBG)") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("HOME")|| (_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG") && _objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BBG")))
				{
					_iTotalRecord=getTotalRecord("");
					colCount=1;
					this._strQuery ="select BBGBRANCHNAME from(select distinct BBGBRANCHNAME from NG_ICICI_MST_BBG_HOMEBRANCH ORDER BY BBGBRANCHNAME) where ROWNUM <= " + this._iBatchSize;

				}
				if((_objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE"))&&(_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING")))
				{
					String channel_source=_objFormObject.getNGValue("ICICILOMBARD_HT_CHANNEL_SOURCE");
					String deal_il_location=_objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_IL_LOCATION");
					_iTotalRecord=getTotalRecord("");
					colCount=2;
					this._strQuery ="select BANK_BRANCH_NAME,SOL_ID from(select distinct BANK_BRANCH_NAME,SOL_ID from NG_HT_MST_SP_CODE where CHANNEL_SOURCE='"+channel_source+"' and DEAL_IL_LOCATION='"+deal_il_location+"' ORDER BY BANK_BRANCH_NAME) where ROWNUM <= " + this._iBatchSize;
					//////// System.out.println("ICICILOMBARD_HT_BANK_BRANCH_NAME loading "+this._strQuery);
						
				}
			}
			//for sol_id loading
			else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_SOL_ID")) && _objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE") && (_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING")))
			{				
				String channel_source=_objFormObject.getNGValue("ICICILOMBARD_HT_CHANNEL_SOURCE");
				String deal_il_location=_objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_IL_LOCATION");
				_iTotalRecord=getTotalRecord("");
				colCount=2;
				this._strQuery ="select BANK_BRANCH_NAME,SOL_ID from(select distinct BANK_BRANCH_NAME,SOL_ID from NG_HT_MST_SP_CODE where CHANNEL_SOURCE='"+channel_source+"' and DEAL_IL_LOCATION='"+deal_il_location+"' ORDER BY SOL_ID) where ROWNUM <= " + this._iBatchSize;
				//////// System.out.println("ICICILOMBARD_HT_SOL_ID loading "+this._strQuery);
			}
			//for sp code loading
			else if((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_WRE")) && _objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE") && (_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING")))
			{
				String deal_il_location=_objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_IL_LOCATION");
				String sol_id=_objFormObject.getNGValue("ICICILOMBARD_HT_SOL_ID");
				String channel_source=_objFormObject.getNGValue("ICICILOMBARD_HT_CHANNEL_SOURCE");
				_iTotalRecord=getTotalRecord("");
				colCount=3;
				this._strQuery ="select SP_CODE,SP_NAME,SP_PAN from(select distinct SP_CODE,SP_NAME,SP_PAN from NG_HT_MST_SP_CODE where DEAL_IL_LOCATION='"+deal_il_location+"'and CHANNEL_SOURCE ='"+channel_source+"' and SOL_ID='"+sol_id+"' ORDER BY SP_CODE) where ROWNUM <= " + this._iBatchSize;
				//////// System.out.println("ICICILOMBARD_HT_WRE Loading"+this._strQuery);
			}
			/**************************************End HT SP Code CR CR-8093-69682 Loading*****************************************************/
			//for ICICILOMBARD_HT_WRE KRG
			/***************** Start SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI**************************************/
			else if((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_WRE")) && (_objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE")) && (_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)")))
			{
				// System.out.println("Inside the Loading sp code: Yogesh");
				_iTotalRecord=getTotalRecord("");
				colCount=2;
				this._strQuery ="select SP_CODE,SP_NAME from(select distinct SP_CODE,SP_NAME from NG_HT_SP_CODE_KRG ORDER BY SP_CODE) where ROWNUM <= " + this._iBatchSize;
				// System.out.println("ICICILOMBARD_HT_WRE Loading"+this._strQuery);
			}
			/***************** End SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI**************************************/
			//TXTGST_STATE_NAME
			/******Start HT CR-8127-95325-GST-Omniflow development******/
			else if((this.strSource != null) && (this.strSource.equals("TXTGST_STATE_NAME")) && (_objFormObject.getNGValue("ICICILOMBARD_HT_GST_REGISTERED").equalsIgnoreCase("Yes")) && !(_objFormObject.getNGValue("ICICILOMBARD_HT_IAGENT").equalsIgnoreCase("Yes")))
			{
				// System.out.println("Inside the Loading GST State: Yogesh");//select a.txtstatename,a.txtstatecode from NG_HT_MST_GST_STATE a
				_iTotalRecord=getTotalRecord("");
				colCount=1;
				this._strQuery ="select txtstatename from(select distinct txtstatename from NG_HT_MST_GST_STATE ORDER BY txtstatename) where ROWNUM <= " + this._iBatchSize;
				// System.out.println("TXTGST_STATE_NAME Loading"+this._strQuery);
			}
			/******End HT CR-8127-95325-GST-Omniflow development******/
			/*****Start Change related to Application  Proposal no. field in Omni flow HT*****/
			else if((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_CHANNEL_LOAN_TYPE")))
			{
				System.out.println("Inside the Loading Change related to Application  Proposal no. field in Omni flow HT: Yogesh");//select a.txtstatename,a.txtstatecode from NG_HT_MST_GST_STATE a
				_iTotalRecord=getTotalRecord("");
				colCount=1;
				this._strQuery ="select channel,source from(select distinct channel,source from NG_HT_MST_KRG_CHANNEL_SOURCE ORDER BY channel) where ROWNUM <= " + this._iBatchSize;
				System.out.println("ICICILOMBARD_HT_CHANNEL_LOAN_TYPE Loading"+this._strQuery);
			}
			/*****End Change related to Application  Proposal no. field in Omni flow HT*****/
			//BRANCH ID/UBO NAME
			else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_BRANCH_ID_UBO_NAME")))
			{
				_iTotalRecord=getTotalRecord("");
				colCount=1;
				if(_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)"))
				{
					this._strQuery ="select Txtbranchidubo from(select DISTINCT Txtbranchidubo from NG_ICICI_MST_KRG ORDER BY Txtbranchidubo) where ROWNUM <= " + this._iBatchSize;
				}	
					////// System.out.println("ICICILOMBARD_HT_BRANCH_ID_UBO_NAME count2:"+this._strQuery);
			}
			//sm id name load
			/********************** Start MO filteration for Centralised Deal Vertical **********************/
			else if ((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_SM_NAME")))
			{
				if (_objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_STATUS").equalsIgnoreCase("YES"))
				{
					_iTotalRecord=getTotalRecord("");
					colCount=4;
					this._strQuery ="select TXT_EMPLOYEE_NAME,TXT_HR_REF_NO,PRIM_SUBVERT,PRIM_VERT from(select Distinct TXT_EMPLOYEE_NAME,TXT_HR_REF_NO,PRIM_SUBVERT,PRIM_VERT from mv_central_employee where upper(TXT_EMPLOYEE_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY TXT_HR_REF_NO) where ROWNUM <= " + this._iBatchSize;

				}
				else
				{
					_iTotalRecord=getTotalRecord("");
					colCount=2;
					this._strQuery ="select TXT_EMPLOYEE_NAME,TXT_HR_REF_NO from(select Distinct me.TXT_EMPLOYEE_NAME as TXT_EMPLOYEE_NAME,me.TXT_HR_REF_NO as TXT_HR_REF_NO from MV_GENMST_EMPLOYEE me, MV_GEN_DEAL_DETAIL GD WHERE GD.NUM_MO_EMPLOYEE_NO= me.num_employee_cd and GD.txt_deal_id=N'"+_objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_NO")+"' and  UPPER(ME.TXT_EMPLOYEE_NAME)  like UPPER(N'"+fieldValue+"%') ORDER BY me.TXT_HR_REF_NO) where ROWNUM <= " + this._iBatchSize;
				}
				////// System.out.println("ICICILOMBARD_HT_SM_NAME Load"+_strQuery);
			}				
			/********************** End MO filteration for Centralised Deal Vertical   **********************/
			//bank name load
			else if ((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_BANK_NAME")))
			{
				_iTotalRecord=getTotalRecord("");
				colCount=2;
				 this._strQuery ="select txtbankname,TXTBANKCODE from(select DISTINCT txtbankname,TXTBANKCODE from NG_ICICI_MST_BankName ORDER BY txtbankname) where ROWNUM <= " + this._iBatchSize;
			}
			
			//center code load
			else if ((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_CENTER_CODE_NAME")))
			{
				 _iTotalRecord=getTotalRecord("");	
				 colCount=1;
				 this._strQuery ="select CENTCODE_NAME from(select DISTINCT CENTCODE_NAME from NG_ICICI_MST_CENTERCODE ORDER BY CENTCODE_NAME) where ROWNUM <= " + this._iBatchSize;
			}
			
			//sub product
			else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_SUB_PRODUCT")))
			{
				/*if (_objFormObject.getNGValue("ICICILOMBARD_HT_PRODUCT_CODE").equalsIgnoreCase("") || _objFormObject.getNGValue("ICICILOMBARD_HT_PRODUCT_CODE").equalsIgnoreCase(null))
				{
			_objFormObject.setNGValue("ICICILOMBARD_HT_PRODUCT_CODE",_objFormObject.getNGItemText("PRODUCT_HIDDEN",1));
			////// System.out.println("ICICILOMBARD_HT_SUB_PRODUCT query "+_objFormObject.getNGValue("ICICILOMBARD_HT_PRODUCT_CODE"));
				}*/
				//Vishal
				//setProductCode();
					_iTotalRecord=getTotalRecord("");
					colCount=2;
					str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);

					/*this._strQuery ="select TXT_IL_SUB_PRODUCT_NAME,TXT_IL_PRODUCT_NO_SUFIX from(select DISTINCT TXT_IL_SUB_PRODUCT_NAME,TXT_IL_PRODUCT_NO_SUFIX from MV_UW_SUB_PRODUCT_MASTER where num_IL_product_code='"+_objFormObject.getNGItemText("PRODUCT_HIDDEN",1)+"' ORDER BY TXT_IL_SUB_PRODUCT_NAME) where ROWNUM <= " + this._iBatchSize;*/
					/*this._strQuery ="select TXT_IL_SUB_PRODUCT_NAME,TXT_IL_PRODUCT_NO_SUFIX from(select DISTINCT TXT_IL_SUB_PRODUCT_NAME,TXT_IL_PRODUCT_NO_SUFIX from MV_UW_SUB_PRODUCT_MASTER where num_IL_product_code='"+_objFormObject.getNGValue("ICICILOMBARD_HT_PRODUCT_CODE")+"' ORDER BY TXT_IL_SUB_PRODUCT_NAME) where ROWNUM <= " + this._iBatchSize;
					////// System.out.println("ICICILOMBARD_HT_SUB_PRODUCT query "+_strQuery);*/
					//----------Made Changes by vishal/Yogendra to fetch sub product on basis of deal no-------
					this._strQuery ="select TXT_IL_SUB_PRODUCT_NAME,TXT_IL_PRODUCT_NO_SUFIX from(select DISTINCT TXT_IL_SUB_PRODUCT_NAME,TXT_IL_PRODUCT_NO_SUFIX from MV_UW_SUB_PRODUCT_MASTER a,MV_GEN_DEAL_DETAIL b,MV_UW_DEAL_PLAN_MAP c where  a.NUM_IL_PRODUCT_CODE=b.NUM_PRODUCT_CODE and a.TXT_IL_SUB_PRODUCT_CODE=c.NUM_PLAN_CODE and b.TXT_DEAL_ID=c.TXT_DEAL_ID and b.TXT_DEAL_ID='"+ _objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_NO")+ "' ORDER BY TXT_IL_SUB_PRODUCT_NAME) where ROWNUM <= " + this._iBatchSize;
					////// System.out.println("ICICILOMBARD_HT_SUB_PRODUCT query "+_strQuery);
					//-----------END of  sub product on basis of deal no----------------
			}
				
		////// System.out.println("MHT process loading started "+_strQuery);
        /************************** CR-OF-MHT-1314-01 MHTProcess Implementaion Start***********/
		if ((this.strSource != null) && this.strSource.equals("MHT_IL_LOCATION_CODE"))//MHT-PID Process Integration - Search provided on both location name and code
		{
			//SEARCH F3
			_iTotalRecord=getTotalRecord("");
			colCount=3;
			this._strQuery ="SELECT DISTINCT ILBRANCHNAME, ILBRANCHCODE, ZONEAREA  FROM (select DISTINCT ILBRANCHNAME, ILBRANCHCODE, ZONEAREA from NG_MHT_MST_ILLOCATION ORDER BY ILBRANCHCODE) where ROWNUM <= " + this._iBatchSize;
			////// System.out.println("MHT_IL_LOCATION query "+_strQuery);
		}
		//MHT-PID Process Integration - Search provided on both location name and code
		if ((this.strSource != null) && this.strSource.equals("MHT_IL_LOCATION"))//MHT-PID Process Integration - Search provided on both location name and code
		{
			//SEARCH F3
			_iTotalRecord=getTotalRecord("");
			colCount=3;
			this._strQuery ="SELECT DISTINCT ILBRANCHNAME, ILBRANCHCODE, ZONEAREA  FROM (select DISTINCT ILBRANCHNAME, ILBRANCHCODE, ZONEAREA from NG_MHT_MST_ILLOCATION ORDER BY ILBRANCHNAME) where ROWNUM <= " + this._iBatchSize;
			////// System.out.println("MHT_IL_LOCATION query "+_strQuery);
		}

		//search criteria loading
		else if	((this.strSource != null) && (this.strSource.equals("MHTSEARCH_STRING")))
		{
			colCount=5;
			_iTotalRecord=getTotalRecord(fieldValue);
			tempStr=_objFormObject.getNGValue("MHT_SEARCH_CRITERIA");
			////// System.out.println("MHTSEARCH_STRING:LOAD start with search criteria "+tempStr);
			if(tempStr.equals("Agent Name"))
			{
					Col="i.TXT_INTERMEDIARY_NAME";
			}
			else if (tempStr.equals("Agent Code"))
			{
					Col="i.TXT_INTERMEDIARY_CD";
			}
			else if (tempStr.equals("Deal No"))
			{
					Col="d.TXT_DEAL_ID";
			}
			else if (tempStr.equals("Manual CN"))
			{
					colCount=3;
					Col="txt_cust_covernote_no";
			}
			if(tempStr.equals("Deal Status"))
			{
					Col="d.TXT_DISPLAY_RM_BS";
			}

			if(fieldValue!=null)
			{
				if(Col.equalsIgnoreCase("txt_cust_covernote_no"))
				{

					this._strQuery = "select TXT_CUST_COVERNOTE_NO,TXT_COVERNOTE_DEAL_ID,YN_COVERNOTE_DEAL_ACCEPTANCE from (select DISTINCT TXT_CUST_COVERNOTE_NO,TXT_COVERNOTE_DEAL_ID,YN_COVERNOTE_DEAL_ACCEPTANCE from MV_MHT_OMNIFLOW_MANUAL_VIEW i where i.txt_cust_covernote_no is not null and UPPER("+Col+") like UPPER(N'%"+fieldValue+"%') order by i.txt_cust_covernote_no) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					this._strQuery ="select TXT_INTERMEDIARY_NAME,TXT_INTERMEDIARY_CD,txt_deal_id,TXT_DISPLAY_RM_BS,TXT_COVERNOTE_FLAG from (select DISTINCT d.txt_deal_id as txt_deal_id,i.TXT_INTERMEDIARY_NAME as TXT_INTERMEDIARY_NAME,i.TXT_INTERMEDIARY_CD as TXT_INTERMEDIARY_CD,d.TXT_DISPLAY_RM_BS as TXT_DISPLAY_RM_BS,d.TXT_COVERNOTE_FLAG from mv_mht_genmst_intermediary i, mv_mht_gen_deal_detail d where d.TXT_INTERMEDIARY_CD=i.TXT_INTERMEDIARY_CD and UPPER("+Col+") like UPPER(N'"+fieldValue+"%') ORDER BY i.TXT_INTERMEDIARY_CD) where ROWNUM <= " + this._iBatchSize;
				}
			}
			else
			{
				if(Col.equalsIgnoreCase("txt_cust_covernote_no"))
				{
					this._strQuery = "select TXT_CUST_COVERNOTE_NO,TXT_COVERNOTE_DEAL_ID,YN_COVERNOTE_DEAL_ACCEPTANCE from (select DISTINCT TXT_CUST_COVERNOTE_NO,TXT_COVERNOTE_DEAL_ID,YN_COVERNOTE_DEAL_ACCEPTANCE from MV_MHT_OMNIFLOW_MANUAL_VIEW i where i.txt_cust_covernote_no is not null order by i.txt_cust_covernote_no) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					this._strQuery ="select TXT_INTERMEDIARY_NAME,TXT_INTERMEDIARY_CD,txt_deal_id,TXT_DISPLAY_RM_BS,TXT_COVERNOTE_FLAG from (select DISTINCT d.txt_deal_id as txt_deal_id,i.TXT_INTERMEDIARY_NAME as TXT_INTERMEDIARY_NAME,i.TXT_INTERMEDIARY_CD as TXT_INTERMEDIARY_CD,d.TXT_DISPLAY_RM_BS as TXT_DISPLAY_RM_BS,d.TXT_COVERNOTE_FLAG from mv_mht_genmst_intermediary i, mv_mht_gen_deal_detail d where d.TXT_INTERMEDIARY_CD=i.TXT_INTERMEDIARY_CD ORDER BY i.TXT_INTERMEDIARY_CD) where ROWNUM <= " + this._iBatchSize;
				}
			}
			////// System.out.println("MHTSEARCH_STRING:while LOAD query"+_strQuery);
		}
		/*************************** MHT-PID Process Integration ****************************/
		//modified code to fetch vertical code also
		else if ((this.strSource != null) && (this.strSource.equals("MHT_PRIMARY_VERTICAL")))
		{
			_iTotalRecord=getTotalRecord("");
			colCount=1;
			this._strQuery ="SELECT DISTINCT prim_vert_name,prim_vert_code  FROM (select DISTINCT prim_vert_name,prim_vert_code from ng_mht_mst_prim_vertical ORDER BY prim_vert_name) where ROWNUM <= " + this._iBatchSize;
			////// System.out.println("MHT_PRIMARY_VERTICAL query "+_strQuery);
		}
		else if ((this.strSource != null) && (this.strSource.equals("MHT_SUB_VERTICAL")))
		{
			_iTotalRecord=getTotalRecord("");
			colCount=1;
			this._strQuery ="SELECT DISTINCT SEC_VERT_NAME,SEC_VERT_CODE  FROM (select DISTINCT SEC_VERT_NAME ,SEC_VERT_CODE from NG_MHT_MST_SEC_VERTICAL ORDER BY SEC_VERT_NAME) where ROWNUM <= " + this._iBatchSize;
			////// System.out.println("MHT_SUB_VERTICAL query "+_strQuery);
		}
		/*********************** End MHT-PID Process Integration ****************************/
		else if ((this.strSource != null) && (this.strSource.equals("MHT_PRODUCT_NAME")))
		{
			_iTotalRecord=getTotalRecord("");
			colCount=2;
			this._strQuery ="SELECT DISTINCT productname, productcode  FROM (select DISTINCT productname, productcode  from ng_mht_mst_product ORDER BY productcode) where ROWNUM <= " + this._iBatchSize;
			////// System.out.println("MHT_PRODUCT_NAME query "+_strQuery);
		}


		//channel source loading(BBG)
		else if((this.strSource != null) && (this.strSource.equalsIgnoreCase("MHT_SOURCE_BUSINESS")) && ((_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BBG") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking (BBG)") || _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BBG"))))
		{
			_iTotalRecord=getTotalRecord("");
			colCount=1;
			this._strQuery ="SELECT SOURCEBUSINESS FROM (select DISTINCT SOURCEBUSINESS from NG_MHT_MST_BBG_BUSINESS ORDER BY SOURCEBUSINESS) where ROWNUM <= " + this._iBatchSize;
		}
        
		//SOURCE BUSINESS
		else if(this.strSource != null && this.strSource.equalsIgnoreCase("MHT_SOURCE_BUSINESS") && (
_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("KRG")))
		{
			_iTotalRecord=getTotalRecord("");
			colCount=1;
			this._strQuery = "select TXTSOURCEBUSINESS from (select DISTINCT TXTSOURCEBUSINESS from NG_MHT_MST_KRG where TXTSOURCEBUSINESS is not NULL ORDER BY TXTSOURCEBUSINESS) where  ROWNUM <= " + this._iBatchSize;
		}
			

        //channel source
		else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("MHT_CHANNEL_SOURCE")))
		{
			_iTotalRecord=getTotalRecord("");
			colCount=1;
			this._strQuery ="select CHANNELSOURCING from(select DISTINCT CHANNELSOURCING from NG_MHT_MST_BBG_SOURCE ORDER BY CHANNELSOURCING) where ROWNUM <= " + this._iBatchSize;
		}

		/******************************************************Start MHT SP Code CR-8127-69652 Loading****************************************/
		//for bank branch name load
 		else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("MHT_BRANCH_ID")))
		{
			if(_objFormObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Policy Issuance") && _objFormObject.getNGValue("MHT_TYPE_OF_BUSINESS").equalsIgnoreCase("Intermediary") && _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BANCASSURANCE") && !_objFormObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER") && _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING"))
			{
				String channel_source=_objFormObject.getNGValue("MHT_CHANNEL_SOURCE");
				String deal_il_location=_objFormObject.getNGValue("MHT_DEAL_IL_LOCATION");
				_iTotalRecord=getTotalRecord("");
				colCount=2;
				this._strQuery ="select SOL_ID,BANK_BRANCH_NAME from(select distinct SOL_ID,BANK_BRANCH_NAME from NG_MHT_MST_SP_CODE where CHANNEL_SOURCE='"+channel_source+"' and DEAL_IL_LOCATION='"+deal_il_location+"' ORDER BY BANK_BRANCH_NAME) where ROWNUM <= " + this._iBatchSize;
				////// System.out.println("MHT_BANK_BRANCH_NAME LOAD Query"+this._strQuery);

			}
			else if(_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BBG") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking")|| _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking (BBG)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("KRG") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("HOME")|| (_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BBG") && _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BBG")))
			{
				_iTotalRecord=getTotalRecord("");
				//// System.out.println("MHT_BRANCH_ID _iTotalRecord =="+_iTotalRecord);
				
				colCount=4; //MHT-PID Process Integration
				this._strQuery ="select branch_id,branch_name,sp_code,sp_name from (select distinct branch_id,branch_name,sp_code,sp_name from NG_MHT_MST_BBGANDHOMEBRANCH ORDER BY branch_id) where ROWNUM <= " + this._iBatchSize;
				//// System.out.println("MHT_BRANCH_ID loading search column=="+this._strQuery);
				
			}
			// System.out.println("MHT_BRANCH_ID LOAD Query"+this._strQuery);
			
		}
		//for sol id load
		else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("MHT_BANK_BRANCH_NAME")))
		{
			if(_objFormObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Policy Issuance") && _objFormObject.getNGValue("MHT_TYPE_OF_BUSINESS").equalsIgnoreCase("Intermediary") && _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BANCASSURANCE") && !_objFormObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER") && _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING"))
			{
				String channel_source=_objFormObject.getNGValue("MHT_CHANNEL_SOURCE");
				String deal_il_location=_objFormObject.getNGValue("MHT_DEAL_IL_LOCATION");
				_iTotalRecord=getTotalRecord("");
				colCount=2;
				this._strQuery ="select BANK_BRANCH_NAME,SOL_ID from(select distinct BANK_BRANCH_NAME,SOL_ID from NG_MHT_MST_SP_CODE where CHANNEL_SOURCE='"+channel_source+"' and DEAL_IL_LOCATION='"+deal_il_location+"' ORDER BY SOL_ID) where ROWNUM <= " + this._iBatchSize;
				////// System.out.println("MHT_BRANCH_ID LOAD Query"+this._strQuery);
			}
		}
		//for sp code load
		else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("MHT_SP_CODE")))
		{
			if(_objFormObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Policy Issuance") && _objFormObject.getNGValue("MHT_TYPE_OF_BUSINESS").equalsIgnoreCase("Intermediary") && _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BANCASSURANCE") && !_objFormObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER") && _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING"))
			{
				String deal_il_location=_objFormObject.getNGValue("MHT_DEAL_IL_LOCATION");
				String sol_id=_objFormObject.getNGValue("MHT_BRANCH_ID");
				String channel_source=_objFormObject.getNGValue("MHT_CHANNEL_SOURCE");
				_iTotalRecord=getTotalRecord("");
				colCount=3;
				this._strQuery ="select SP_CODE,SP_NAME,SP_PAN from(select distinct SP_CODE,SP_NAME,SP_PAN from NG_MHT_MST_SP_CODE where DEAL_IL_LOCATION='"+deal_il_location+"'and CHANNEL_SOURCE ='"+channel_source+"' and SOL_ID='"+sol_id+"' ORDER BY SP_CODE) where ROWNUM <= " + this._iBatchSize;
				////// System.out.println("MHT_SP_CODE LOAD Query"+this._strQuery);
			}
			/********Start SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI ************/
			else
			{
				if(!((_objFormObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Policy Issuance") && _objFormObject.getNGValue("MHT_TYPE_OF_BUSINESS").equalsIgnoreCase("Direct"))) && (_objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BANCASSURANCE") || _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BA")) && (_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("KRG")) && !(_objFormObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER")))
				{
					// System.out.println("Inside load the sp code logic change");
					_iTotalRecord=getTotalRecord("");
					colCount=2;
					this._strQuery ="select SP_CODE,SP_NAME from(select distinct SP_CODE,SP_NAME from NG_MHT_SP_CODE_KRG ORDER BY SP_CODE) where ROWNUM <= " + this._iBatchSize;
					// System.out.println("MHT_SP_CODE Loading"+this._strQuery);			
				}
			}
			/***************************End SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI ******************************************/
		}
		/******************************************************End MHT SP Code CR-8127-69652 Loading *****************************************/
		/******Start MHT CR-8127-95325-GST-Omniflow development******/
		else if((this.strSource != null) && (this.strSource.equals("MHT_TXTGST_STATE_NAME")) && (_objFormObject.getNGValue("MHT_GST_REGISTERED").equalsIgnoreCase("Yes")) && !(_objFormObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER")))
		{
			// System.out.println("Inside the Loading GST State: Yogesh");//select a.txtstatename,a.txtstatecode from NG_MHT_MST_GST_STATE a
			_iTotalRecord=getTotalRecord("");
			colCount=1;
			this._strQuery ="select txtstatename from(select distinct txtstatename from NG_MHT_MST_GST_STATE ORDER BY txtstatename) where ROWNUM <= " + this._iBatchSize;
			// System.out.println("MHT_TXTGST_STATE_NAME Loading"+this._strQuery);
		}
		/******End MHT CR-8127-95325-GST-Omniflow development******/
		//BRANCH ID/UBO NAME
		else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("MHT_BRANCH_ID_UBO_NAME")))
		{
			_iTotalRecord=getTotalRecord("");
			colCount=1;
			if(_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || 
			_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("KRG"))
			{
				this._strQuery ="select Txtbranchidubo from(select DISTINCT Txtbranchidubo from NG_MHT_MST_KRG ORDER BY Txtbranchidubo) where ROWNUM <= " + this._iBatchSize;
			}
		////// System.out.println("MHT_BRANCH_ID_UBO_NAME count2:"+this._strQuery);
		}
		//sm id name load
		else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("MHT_SM_NAME")))
		{
			_iTotalRecord=getTotalRecord("");
			colCount=2;
			/**************************** MHT-PID Process Integration ***************************/
			this._strQuery ="select DISTINCT PRIMARY_MO_NAME,TXT_HR_REF_NO from (select DISTINCT PRIMARY_MO_NAME,TXT_HR_REF_NO from MV_MHT_GENMST_EMPLOYEE ORDER BY PRIMARY_MO_NAME) where ROWNUM <= " + this._iBatchSize;
			//this._strQuery ="select DISTINCT RM_NAME,EMP_CODE from (select me.RM_NAME as RM_NAME,me.EMP_CODE as EMP_CODE from NG_MHT_MST_RM me ORDER BY me.EMP_CODE) where ROWNUM <= " + this._iBatchSize;
			////// System.out.println("MHT_SM_NAME load-->" + _strQuery);
			/************************ End MHT-PID Process Integration ***************************/
		}
		
		else if ((this.strSource != null) && (this.strSource.equals("MHT_PAYMENT_TYPE1_BANKNAME")) || this.strSource.equals("MHT_PAYMENT_TYPE2_BANKNAME") || this.strSource.equals("MHT_PAYMENT_TYPE3_BANKNAME"))
		{
			_iTotalRecord=getTotalRecord("");
			colCount=2;
			 this._strQuery ="select txtbankname,TXTBANKCODE from(select DISTINCT txtbankname,TXTBANKCODE from NG_MHT_MST_BankName ORDER BY txtbankname) where ROWNUM <= " + this._iBatchSize;
								 ////// System.out.println("bank name load--MHT_PAYMENT_TYPE1_BANKNAME-->" + this._strQuery);
		}
		
		/*************************** MHT -PID Process Integration ****************************/
		else if ((this.strSource != null) && (this.strSource.equals("MHT_PAYMENT_TYPE1")) || this.strSource.equals("MHT_PAYMENT_TYPE2") || this.strSource.equals("MHT_PAYMENT_TYPE3"))
		{
			_iTotalRecord=getTotalRecord("");
			colCount=1;
			this._strQuery ="select payment_mode from(select DISTINCT payment_mode from NG_MHT_PAYMENT_MODE_MASTER ORDER BY payment_mode) where ROWNUM <= " + this._iBatchSize;
			////// System.out.println("bank name load--MHT_PAYMENT_TYPE-->" + this._strQuery);
		}
		/************************* End MHT -PID Process Integration **************************/

		//center code load
		else if ((this.strSource != null) && (this.strSource.equals("MHT_CENTER_CODE_NAME")))
		{
			 _iTotalRecord=getTotalRecord("");
			 colCount=1;
			 this._strQuery ="select CENTCODE_NAME from(select DISTINCT CENTCODE_NAME from NG_ICICI_MST_CENTERCODE ORDER BY CENTCODE_NAME) where ROWNUM <= " + this._iBatchSize;
								 
		}
		
		/************************** CR-OF-MHT-1314-01 MHTProcess Implementaion End***********/
		 if(this.strSource != null && this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_SOURCE_BUSINESS") && _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG") && _objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("ISEC"))
		{
			////// System.out.println("adding elements");
			ArrayList alRow = new ArrayList();
			alRow.add("Center Sales");
			alRows.add(alRow);

			//Second Row
			alRow = new ArrayList();
			alRow.add("Sub Broker");
			alRows.add(alRow);
			////// System.out.println("adding elements end"+alRows);
			////// System.out.println("in clooection lenght--"+alRows.size()+"alRows=="+alRows);
			result=localNGPickList.populateData(alRows);
		}
		else if(this.strSource != null && this.strSource.equalsIgnoreCase("MHT_SOURCE_BUSINESS") && _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BBG") && _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("ISEC"))
		{
			ArrayList alRow = new ArrayList();
			alRow.add("Center Sales");
			alRows.add(alRow);

			//Second Row
			alRow = new ArrayList();
			alRow.add("Sub Broker");
			alRows.add(alRow);
			result=localNGPickList.populateData(alRows);
		}
		else
		{
			result=localNGPickList.populateData(this._strQuery, colCount);
		}

		if (result)
		{
			if ((this._iBatchNo * this._iBatchSize + 1 > this. _iTotalRecord) && (this. _iTotalRecord > (this._iBatchNo - 1) * this._iBatchSize))
			  localNGPickList.enableButton("Next", false);
			else
			{
			  localNGPickList.enableButton("Next", true);
			}
			localNGPickList.enableButton("Previous", false);
		}

		if (this._iBatchNo * this._iBatchSize > this. _iTotalRecord)
			localNGPickList.setStatus("Showing " + ((this._iBatchNo - 1) * this._iBatchSize + 1) + " - " + this. _iTotalRecord + " of " + this. _iTotalRecord);
		else
			localNGPickList.setStatus("Showing " + ((this._iBatchNo - 1) * this._iBatchSize + 1) + " - " + (this._iBatchNo * this._iBatchSize) + " of " + this. _iTotalRecord);
		
	}
	
    


    //----------------------------------------------------------------------------------------------------
    //Function Name 			: btnNext_Clicked
    //Date Written (DD/MM/YYYY)	: 14/04/2009
    //Author					: Vikas Tyagi
    //Input Parameters			: e
    //Output Parameters			: none
    //Return Values				: none
    //Description				: Hanldes ckisk event of Next.
    //----------------------------------------------------------------------------------------------------
    // Changed By				        : Vikas Tyagi
    // Reason / Cause (Bug No if Any)	: FBD_7.2.4.0
    // Change  Description			    : Changes for unicode support.
    //----------------------------------------------------------------------------------------------------
    public void btnNext_Clicked(NGEvent e)
    {
		////// System.out.println("inseide next");
		String str1;
		NGPickList localNGPickList = (NGPickList)e.getSource();
		this.objProcessData.setInputXML(this._objFormObject.getWFGeneralData());
		String fieldValue="";
		
		//_iTotalRecord = getTotalRecord(fieldValue);

		this._iBatchNo += 1;
		
		if ((this._iBatchNo * this._iBatchSize + 1 > this. _iTotalRecord) && (this. _iTotalRecord > (this._iBatchNo - 1) * this._iBatchSize))
		{
			localNGPickList.enableButton("Next", false);
		}
		/*****Start CR-8001-87420-Green File Digitization*****/
		//============== CPI CR Quote SYS===============		
		
		if((this.strSource != null) && (this.strSource.equals("CPI_RM_ID")))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
			if(_objFormObject.getNGValue("CPI_IS_PF_FETCH").equalsIgnoreCase("Yes"))
			{
				colCount=6;
				if(fieldValue!=null)
				{
					this._strQuery ="select distinct TXT_HR_REF_NO,TXT_EMPLOYEE_NAME,PRIM_VERT,PRIM_VERT_CD,PRIM_SUBVERT,PRIM_SUBVERT_CD from(select distinct TXT_HR_REF_NO,TXT_EMPLOYEE_NAME,PRIM_VERT,PRIM_VERT_CD,PRIM_SUBVERT,PRIM_SUBVERT_CD from MV_CPI_VW_EMPLOYEE_OMNI where TXT_HR_REF_NO >N'" + str1 +"' and UPPER(TXT_HR_REF_NO) like UPPER(N'"+fieldValue+"%') ORDER BY TXT_HR_REF_NO) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					this._strQuery = "select distinct TXT_HR_REF_NO,TXT_EMPLOYEE_NAME,PRIM_VERT,PRIM_VERT_CD,PRIM_SUBVERT,PRIM_SUBVERT_CD FROM (select distinct TXT_HR_REF_NO,TXT_EMPLOYEE_NAME,PRIM_VERT,PRIM_VERT_CD,PRIM_SUBVERT,PRIM_SUBVERT_CD from MV_CPI_VW_EMPLOYEE_OMNI where TXT_HR_REF_NO >N'" + str1 +"' ORDER BY TXT_HR_REF_NO) where ROWNUM <= " + this._iBatchSize;
				}
			}
			else
			{
				colCount=2;
				if(fieldValue!=null)
				{
					this._strQuery ="select distinct EMP_CODE, RM_NAME FROM (select distinct EMP_CODE, RM_NAME  from NG_RM_MASTER where EMP_CODE >N'" + str1 +"' and UPPER(RM_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY EMP_CODE) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					this._strQuery = "select distinct EMP_CODE, RM_NAME FROM (select distinct RM_NAME, EMP_CODE  from NG_RM_MASTER where EMP_CODE >N'" + str1 +"' ORDER BY EMP_CODE) where ROWNUM <= " + this._iBatchSize;
				}
			}		
			// System.out.println("2_Next strQuery"+_strQuery);
		}
		//============== End CPI CR Quote SYS===============	
		//additional cit/rm id
		//TXT_HR_REF_NO,TXT_EMPLOYEE_NAME
		if ((this.strSource != null) && (this.strSource.equals("CPI_ADDTNL_CIT_RM_ID") || this.strSource.equals("CPI_UW_EMP_ID") || this.strSource.equals("CPI_SECONDARY_MO_ID") || this.strSource.equalsIgnoreCase("CPI_RM_EMP_ID_SPG_IBG")))
		{
			// System.out.println(this.strSource+" btnNext_Clicked...");
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);			
			colCount=2;
			str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
			if(fieldValue!=null)
			{
				this._strQuery ="select distinct TXT_HR_REF_NO,TXT_EMPLOYEE_NAME FROM (select distinct TXT_HR_REF_NO,TXT_EMPLOYEE_NAME from MV_CPI_VW_EMPLOYEE_OMNI where TXT_HR_REF_NO >N'" + str1 +"' and UPPER(TXT_HR_REF_NO) like UPPER(N'"+fieldValue+"%') ORDER BY TXT_HR_REF_NO) where ROWNUM <= " + this._iBatchSize;
			}
			else
			{
				this._strQuery = "select distinct TXT_HR_REF_NO,TXT_EMPLOYEE_NAME FROM (select distinct TXT_HR_REF_NO,TXT_EMPLOYEE_NAME from MV_CPI_VW_EMPLOYEE_OMNI where TXT_HR_REF_NO >N'" + str1 +"' ORDER BY TXT_HR_REF_NO) where ROWNUM <= " + this._iBatchSize;
			}
			// System.out.println("2_Next strQuery"+_strQuery);
		}
		//uw_emp_id
		/*if ((this.strSource != null) && (this.strSource.equals("CPI_UW_EMP_ID")))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);			

			colCount=2;
			str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
			if(fieldValue!=null)
			{
				this._strQuery ="select distinct TXT_HR_REF_NO,TXT_EMPLOYEE_NAME FROM (select distinct TXT_HR_REF_NO,TXT_EMPLOYEE_NAME from MV_CPI_VW_EMPLOYEE_OMNI where TXT_HR_REF_NO >N'" + str1 +"' and UPPER(TXT_HR_REF_NO) like UPPER(N'"+fieldValue+"%') ORDER BY TXT_HR_REF_NO) where ROWNUM <= " + this._iBatchSize;
			}
			else
			{
				this._strQuery = "select distinct TXT_HR_REF_NO,TXT_EMPLOYEE_NAME FROM (select distinct TXT_HR_REF_NO,TXT_EMPLOYEE_NAME from MV_CPI_VW_EMPLOYEE_OMNI where TXT_HR_REF_NO >N'" + str1 +"' ORDER BY TXT_HR_REF_NO) where ROWNUM <= " + this._iBatchSize;
			}
			// System.out.println("2_Next strQuery"+_strQuery);
		}*/
		if ((this.strSource != null) && (this.strSource.equals("CPI_PREV_POLICY_NO")))
		{
			// System.out.println(this.strSource+" btnNext_Clicked...");
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);			
			colCount=3;
			str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
			if(fieldValue!=null)
			{
				this._strQuery ="select TXT_POLICY_NO_CHAR,TXT_CUSTOMER_ID,TXT_CUSTOMER_NAME FROM (select distinct GEN.TXT_POLICY_NO_CHAR,TXT_CUSTOMER_ID,TXT_CUSTOMER_NAME FROM GEN_PROP_INFORMATION_TAB@omnitopf GEN,UW_PRODUCT_MASTER@omnitopf UPM where GEN.NUM_PRODUCT_CODE = UPM.PRODUCTCODE and GEN.TXT_POLICY_NO_CHAR is not null and GEN.TXT_POLICY_NO_CHAR >N'" + str1 +"' and GEN.TXT_POLICY_NO_CHAR='"+fieldValue+"' ORDER BY GEN.TXT_POLICY_NO_CHAR) where ROWNUM <= " + this._iBatchSize;
			}
			else
			{
				this._strQuery = "select TXT_POLICY_NO_CHAR,TXT_CUSTOMER_ID,TXT_CUSTOMER_NAME FROM (select distinct GEN.TXT_POLICY_NO_CHAR,TXT_CUSTOMER_ID,TXT_CUSTOMER_NAME FROM GEN_PROP_INFORMATION_TAB@omnitopf GEN,UW_PRODUCT_MASTER@omnitopf UPM where GEN.NUM_PRODUCT_CODE = UPM.PRODUCTCODE and GEN.TXT_POLICY_NO_CHAR is not null and GEN.TXT_POLICY_NO_CHAR >N'" + str1 +"' ORDER BY GEN.TXT_POLICY_NO_CHAR) where ROWNUM <= " + this._iBatchSize;
			}
			// System.out.println("2_Next strQuery"+_strQuery);
		}
		//base policy no
		if ((this.strSource != null) && (this.strSource.equals("CPI_POLICY_NUMBER_BASE")))
		{
			// System.out.println(this.strSource+" btnNext_Clicked...");
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);			
			colCount=6;
			str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);

			if(fieldValue!=null)
			{
				this._strQuery ="select TXT_POLICY_NO_CHAR,DAT_POLICY_EFF_FROMDATE,TXT_CUSTOMER_ID,TXT_CUSTOMER_NAME,PRODUCTNAME,NUM_IL_PRODUCT_CODE FROM (select GEN.TXT_POLICY_NO_CHAR,DAT_POLICY_EFF_FROMDATE,TXT_CUSTOMER_ID,TXT_CUSTOMER_NAME,UPM.PRODUCTNAME,UPM.NUM_IL_PRODUCT_CODE from GEN_PROP_INFORMATION_TAB@omnitopf GEN,UW_PRODUCT_MASTER@omnitopf UPM where GEN.NUM_PRODUCT_CODE = UPM.PRODUCTCODE and GEN.TXT_POLICY_NO_CHAR is not null and GEN.TXT_POLICY_NO_CHAR >N'" + str1 +"' and GEN.TXT_POLICY_NO_CHAR='"+fieldValue+"' ORDER BY GEN.TXT_POLICY_NO_CHAR) where ROWNUM <= " + this._iBatchSize;

			}
			else
			{
				this._strQuery = "select TXT_POLICY_NO_CHAR,DAT_POLICY_EFF_FROMDATE,TXT_CUSTOMER_ID,TXT_CUSTOMER_NAME,PRODUCTNAME,NUM_IL_PRODUCT_CODE FROM (select GEN.TXT_POLICY_NO_CHAR,DAT_POLICY_EFF_FROMDATE,TXT_CUSTOMER_ID,TXT_CUSTOMER_NAME,UPM.PRODUCTNAME,UPM.NUM_IL_PRODUCT_CODE from GEN_PROP_INFORMATION_TAB@omnitopf GEN,UW_PRODUCT_MASTER@omnitopf UPM where GEN.NUM_PRODUCT_CODE = UPM.PRODUCTCODE and GEN.TXT_POLICY_NO_CHAR is not null and GEN.TXT_POLICY_NO_CHAR >N'" + str1 +"' ORDER BY GEN.TXT_POLICY_NO_CHAR) where ROWNUM <= " + this._iBatchSize;

			}
			// System.out.println("2_Next strQuery"+_strQuery);
		}
		//need to write code RM_ID by concerning with satish
		/*****End CR-8001-87420-Green File Digitization*****/

		/**************************************Client Registration CR Start*****************************************************/
		if((this.strSource != null) && (this.strSource.equals("CPI_POLICYNO_SEARCH")))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			
			colCount=2;
			str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
			
			if(fieldValue!=null)
			{
				this._strQuery ="select distinct POLICY_NUMBER,PRODUCT_NAME,CST_RM_EMP_ID,CST_RM_NAME,IL_LOCATION,PRIMARY_VERTICAL,PRIMARY_SUB_VERTICAL,CUSTOMER_ID,BROKER_AGENT_NAME,CLIENT_NAME,PAYMENT_MODE,CD_ACCOUNT_NUMBER,PRODUCT_IRDACODE,PRIMARY_VERTICAL_CODE,PRIMARY_SUB_VERTICAL_CODE FROM (select distinct POLICY_NUMBER,PRODUCT_NAME,CST_RM_EMP_ID,CST_RM_NAME,IL_LOCATION,PRIMARY_VERTICAL,PRIMARY_SUB_VERTICAL,CUSTOMER_ID,BROKER_AGENT_NAME,CLIENT_NAME,PAYMENT_MODE,CD_ACCOUNT_NUMBER,PRODUCT_IRDACODE,PRIMARY_VERTICAL_CODE,PRIMARY_SUB_VERTICAL_CODE from NG_CPI_CLIENT_REG_MST where POLICY_NUMBER >N'" + str1 +"' and UPPER(RM_NAME) like UPPER(N'"+fieldValue+"%') AND UPPER(REGNO_STATUS) = 'APPROVED' ORDER BY POLICY_NUMBER) where ROWNUM <= " + this._iBatchSize;
			}
			else
			{
				this._strQuery = "select distinct POLICY_NUMBER,PRODUCT_NAME,CST_RM_EMP_ID,CST_RM_NAME,IL_LOCATION,PRIMARY_VERTICAL,PRIMARY_SUB_VERTICAL,CUSTOMER_ID,BROKER_AGENT_NAME,CLIENT_NAME,PAYMENT_MODE,CD_ACCOUNT_NUMBER,PRODUCT_IRDACODE,PRIMARY_VERTICAL_CODE,PRIMARY_SUB_VERTICAL_CODE FROM (select distinct POLICY_NUMBER,PRODUCT_NAME,CST_RM_EMP_ID,CST_RM_NAME,IL_LOCATION,PRIMARY_VERTICAL,PRIMARY_SUB_VERTICAL,CUSTOMER_ID,BROKER_AGENT_NAME,CLIENT_NAME,PAYMENT_MODE,CD_ACCOUNT_NUMBER,PRODUCT_IRDACODE,PRIMARY_VERTICAL_CODE,PRIMARY_SUB_VERTICAL_CODE from NG_CPI_CLIENT_REG_MST where POLICY_NUMBER >N'" + str1 +"' AND UPPER(REGNO_STATUS) = 'APPROVED' ORDER BY POLICY_NUMBER) where ROWNUM <= " + this._iBatchSize;
			}
			// System.out.println("2_Next strQuery"+_strQuery);
		}
		/**************************************Client Registration CR End*****************************************************/
	
		/********************CR-OMCPI-1314-03 CPU DataWashing Start**********************/
		if((this.strSource != null) && (this.strSource.equals("CPI_CPU_ASSIGN_TO")))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			
			colCount=2;
			str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
		/**** Start CR-8001-70893 Marine CR *******************************/
			String prodCategory="";
			prodCategory = _objFormObject.getWFActivityName().equalsIgnoreCase("CPU_Assignment")?"DW_Health":"Marine";			
			if(fieldValue!=null)
			{
				//this._strQuery ="select distinct EMP_ID, EMP_NAME FROM (select distinct EMP_ID, EMP_NAME from NG_CPI_DW_ESCALATION_MASTER where EMP_ID >N'" + str1 +"' and UPPER(EMP_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY EMP_ID) where ROWNUM <= " + this._iBatchSize;
				this._strQuery ="select distinct EMP_ID, EMP_NAME FROM (select distinct EMP_ID, EMP_NAME from NG_CPI_DW_ESCALATION_MASTER where prod_category = '"+prodCategory+"' and EMP_ID >N'" + str1 +"' and UPPER(EMP_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY EMP_ID) where ROWNUM <= " + this._iBatchSize;
			}
			else
			{
				//this._strQuery = "select distinct EMP_ID, EMP_NAME FROM (select distinct EMP_ID, EMP_NAME from NG_CPI_DW_ESCALATION_MASTER where EMP_ID >N'" + str1 +"' ORDER BY EMP_ID) where ROWNUM <= " + this._iBatchSize;
				this._strQuery = "select distinct EMP_ID, EMP_NAME FROM (select distinct EMP_ID, EMP_NAME from NG_CPI_DW_ESCALATION_MASTER where prod_category = '"+prodCategory+"' and EMP_ID >N'" + str1 +"' ORDER BY EMP_ID) where ROWNUM <= " + this._iBatchSize;
			}
			/**** ENd CR-8001-70893 Marine CR *********************************/

			////// System.out.println("2_Next strQuery"+_strQuery);
		}
		/********************CR-OMCPI-1314-03 CPU DataWashing END**********************/
		
		/********************* CR 45 Network Partner *****************************/
		if((this.strSource != null) && (this.strSource.equals("CPI_NETWORK_PARTNER_NAME")))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			
			colCount=2;
			str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
			
			if(fieldValue!=null)
			{
				this._strQuery ="SELECT DISTINCT NETWORK_PARTNER_NAME FROM (select DISTINCT NETWORK_PARTNER_NAME  from NG_CPI_NTWRK_PARTNER_MASTER where NETWORK_PARTNER_NAME >N'" + str1 +"' and UPPER(NETWORK_PARTNER_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY NETWORK_PARTNER_NAME) where ROWNUM <= " + this._iBatchSize;
			}
			else
			{
				this._strQuery = "SELECT DISTINCT NETWORK_PARTNER_NAME FROM (select DISTINCT NETWORK_PARTNER_NAME  from NG_CPI_NTWRK_PARTNER_MASTER where NETWORK_PARTNER_NAME >N'" + str1 +"' ORDER BY NETWORK_PARTNER_NAME) where ROWNUM <= " + this._iBatchSize;
			}
			////// System.out.println("2_Next strQuery"+_strQuery);
		}
		/********************* End CR 45 Network Partner *************************/
		
			/******************  CO Insurance CR 18 *************************/
			//satish
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_RM_Name")))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			
			colCount=2;
			str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 1);
			////// System.out.println("sandeep##"+str1);
			
			
		
			if(fieldValue!=null)
			{
				this._strQuery ="SELECT DISTINCT RM_NAME,EMP_CODE  FROM (select DISTINCT RM_NAME, EMP_CODE   from NG_RM_MASTER where EMP_CODE >N'" + str1 +"' and UPPER(RM_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY EMP_CODE) where ROWNUM <= " + this._iBatchSize;
			}
			else
			{
				this._strQuery = "SELECT DISTINCT   RM_NAME,EMP_CODE  FROM (select DISTINCT RM_NAME, EMP_CODE  from NG_RM_MASTER where EMP_CODE >N'" + str1 +"' ORDER BY EMP_CODE) where ROWNUM <= " + this._iBatchSize;
			}
			////// System.out.println("2_Next strQuery1"+_strQuery);
		}
		//satish
		/****************** End  CO Insurance CR 18 *************************/
		
		/********************* CR 28 by satish *****************************/
		//satish
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_NAME_OF_BROKER_AGENT")))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			
			//colCount=2;//PID_CPI process changes
			colCount=3; //PID_CPI process changes
			str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
			////// System.out.println("satish##"+str1);
			
			
			/*************************** PID-CPI process changes ***************************/
			if(fieldValue!=null)
			{
				//this._strQuery ="SELECT AGENT_NAME,IBANKAGENT  FROM (select DISTINCT AGENT_NAME, IBANKAGENT  from ng_cpi_agent_master where AGENT_NAME >N'" + str1 +"' and UPPER(AGENT_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY AGENT_NAME) where ROWNUM <= " + this._iBatchSize;
				this._strQuery ="SELECT AGENT_NAME,IBANKAGENT,INTERMEDIARY_CODE  FROM (select DISTINCT AGENT_NAME, IBANKAGENT,INTERMEDIARY_CODE  from ng_cpi_agent_master where AGENT_NAME >N'" + str1 +"' and UPPER(AGENT_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY AGENT_NAME) where ROWNUM <= " + this._iBatchSize;

			}
			else
			{
				//this._strQuery = "SELECT AGENT_NAME,IBANKAGENT FROM (select DISTINCT AGENT_NAME, IBANKAGENT  from ng_cpi_agent_master where AGENT_NAME >N'" + str1 +"' ORDER BY AGENT_NAME) where ROWNUM <= " + this._iBatchSize;
				this._strQuery = "SELECT AGENT_NAME,IBANKAGENT,INTERMEDIARY_CODE FROM (select DISTINCT AGENT_NAME,IBANKAGENT,INTERMEDIARY_CODE  from ng_cpi_agent_master where AGENT_NAME >N'" + str1 +"' ORDER BY AGENT_NAME) where ROWNUM <= " + this._iBatchSize;
				
			}
			/*********************** End PID-CPI process changes ***************************/
			////// System.out.println("2_Next strQuery1"+_strQuery);
		}
		//satish
		/********************** End of CR 28 ********************************/
		
		/************************ CPI IL Location****************/
			
		if((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_IL_Location"))||(this.strSource.equalsIgnoreCase("Corp_IL_Location"))||(this.strSource.equalsIgnoreCase("End_IL_Location"))))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			
			colCount=1;
			str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
			////// System.out.println("sandeep##"+str1);
			
			////// System.out.println("field value"+fieldValue+""+_iTotalRecord);
			
		
			if((fieldValue!=null)&&(fieldValue!=""))
			{
			
			////// System.out.println("inside ");
			
			
				
				this._strQuery ="SELECT  IL_LOCATION_VALUE FROM NG_IL_LOCATION_MASTER    where IL_LOCATION_VALUE >N'" + str1 +"' and UPPER(IL_LOCATION_VALUE) like UPPER(N'"+fieldValue+"%') and ROWNUM <= " + this._iBatchSize+" ORDER BY IL_LOCATION_VALUE";
			
			}
			else
			{
		
		
					this._strQuery = "SELECT    IL_LOCATION_VALUE FROM (SELECT   IL_LOCATION_VALUE FROM NG_IL_LOCATION_MASTER where IL_LOCATION_VALUE >N'" + str1 +"' ORDER BY IL_LOCATION_VALUE) where ROWNUM <= " + this._iBatchSize;
		
				
				
			}
			////// System.out.println("2_Next strQuery1"+_strQuery);
		}
		
		
		/************************End CPI IL Location****************/
			
	/*************************** PID-CPI process changes ***************************/
	/**************************** CPI moDE_OF_PAYMENT****************/
		if ((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_moDE_OF_PAYMENT"))||(this.strSource.equalsIgnoreCase("CPI_MODE_OF_PAYMENT2"))||(this.strSource.equalsIgnoreCase("CPI_MODE_OF_PAYMENT3"))))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			
			colCount=1;
			str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);			
			////// System.out.println("field value"+fieldValue+""+_iTotalRecord);			
		
			if((fieldValue!=null)&&(fieldValue!=""))
			{			
				////// System.out.println("inside ");				
				this._strQuery ="SELECT MODE_OF_PAYMENT FROM NG_CPI_PAYMENT_MODE_MASTER where MODE_OF_PAYMENT >N'" + str1 +"' and UPPER(MODE_OF_PAYMENT) like UPPER(N'"+fieldValue+"%') and ROWNUM <= " + this._iBatchSize+" ORDER BY MODE_OF_PAYMENT";			
			}
			else
			{
				this._strQuery = "SELECT MODE_OF_PAYMENT FROM (SELECT MODE_OF_PAYMENT FROM NG_CPI_PAYMENT_MODE_MASTER where MODE_OF_PAYMENT >N'" + str1 +"' ORDER BY MODE_OF_PAYMENT) where ROWNUM <= " + this._iBatchSize;			
			}
			////// System.out.println("2_Next strQuery1"+_strQuery);
		}
	/************************End  CPI moDE_OF_PAYMENT****************/
	/*********************** End PID-CPI process changes ***************************/
	
	/**************************** CPI Hypothecated_to****************/
		if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_HYPOTHECATED_TO")))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			
			colCount=1;
			str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);			
			////// System.out.println("field value"+fieldValue+""+_iTotalRecord);			
			////// System.out.println("str1"+str1);
			
			if((fieldValue!=null)&&(fieldValue!=""))
			{			
				////// System.out.println("inside if Hypo btnNext_Clicked  ");				
				this._strQuery ="SELECT HYPOTHECATED_TO FROM (SELECT DISTINCT HYPOTHECATED_TO FROM NG_CPI_HYPOTHECATED_MASTER where HYPOTHECATED_TO >N'" + str1 +"' and UPPER(HYPOTHECATED_TO) like UPPER(N'"+fieldValue+"%') ORDER BY HYPOTHECATED_TO) where  ROWNUM <= " + this._iBatchSize;		
			}
			else
			{
				////// System.out.println("inside else Hypo btnNext_Clicked  ");	
				this._strQuery = "SELECT  HYPOTHECATED_TO FROM (SELECT DISTINCT HYPOTHECATED_TO FROM NG_CPI_HYPOTHECATED_MASTER where HYPOTHECATED_TO > N'" + str1 +"' ORDER BY HYPOTHECATED_TO) where ROWNUM <= "+ this._iBatchSize;			
			}
			////// System.out.println("2_Next strQuery1"+_strQuery);
		}
	/************************End  CPI Hypothecated_to****************/	
	
	
	/************************* CPI URN CR 8001-61339 Multiple Changes CR *****************************/  
		if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_Exception_To_MH")))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			
			colCount=1;
			str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);			
			////// System.out.println("field value"+fieldValue+""+_iTotalRecord);			
			////// System.out.println("str1"+str1);
			
			if((fieldValue!=null)&&(fieldValue!=""))
			{			
				////// System.out.println("inside if EXCEPTION btnNext_Clicked  ");				
				this._strQuery ="SELECT MH_EXCEPTION_NAME FROM (SELECT DISTINCT MH_EXCEPTION_NAME FROM NG_CPI_MHEXCEPTION_MASTER where MH_EXCEPTION_NAME >N'" + str1 +"' and UPPER(MH_EXCEPTION_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY MH_EXCEPTION_NAME) where  ROWNUM <= " + this._iBatchSize;		
			}
			else
			{
				////// System.out.println("inside else EXCEPTION btnNext_Clicked  ");	
				this._strQuery = "SELECT  MH_EXCEPTION_NAME FROM (SELECT DISTINCT MH_EXCEPTION_NAME FROM NG_CPI_MHEXCEPTION_MASTER where MH_EXCEPTION_NAME > N'" + str1 +"' ORDER BY MH_EXCEPTION_NAME) where ROWNUM <= "+ this._iBatchSize;			
			}
			////// System.out.println("2_Next strQuery1 : "+_strQuery);
		}
		
	/*************************End CPI URN CR 8001-61339 Multiple Changes CR **************************/
	
	
	
	
		/************************ CPI_SPECIFIED_PERSON ****************/
		if((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_SPECIFIED_PERSON"))  || (this.strSource.equalsIgnoreCase("Corp_SP_Name"))))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			
			colCount=1;
			str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
			////// System.out.println("sandeep##"+str1);
			
			////// System.out.println("field value"+fieldValue+""+_iTotalRecord);
			
		
			if((fieldValue!=null)&&(fieldValue!=""))
			{
			
			////// System.out.println("inside ");
				this._strQuery ="SELECT  SPECIFIED_PERSON FROM NG_CPI_SP_MASTER    where SPECIFIED_PERSON >N'" + str1 +"' and UPPER(SPECIFIED_PERSON) like UPPER(N'"+fieldValue+"%') and ROWNUM <= " + this._iBatchSize+" ORDER BY SPECIFIED_PERSON";
				//this._strQuery = "SELECT     IL_LOCATION_VALUE FROM (SELECT   IL_LOCATION_VALUE FROM NG_IL_LOCATION_MASTER where IL_LOCATION_VALUE >'" + str1 +"' ORDER BY IL_LOCATION_VALUE) where ROWNUM <= " + this._iBatchSize;
				
			}
			else
			{
				this._strQuery = "SELECT    SPECIFIED_PERSON FROM (SELECT   SPECIFIED_PERSON FROM NG_CPI_SP_MASTER where SPECIFIED_PERSON >N'" + str1 +"' ORDER BY SPECIFIED_PERSON) where ROWNUM <= " + this._iBatchSize;
			}
			////// System.out.println("2_Next strQuery1"+_strQuery);
		}
		
		
		/************************End CPI_SPECIFIED_PERSON****************/
		
		/************************ CPI_PRIMARY_VERTICAL ****************/
		if((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_PRIMARY_VERTICAL"))))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			
			//colCount=1;//PID_CPI process changes
			colCount=2;//PID_CPI process changes
			str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
			////// System.out.println("sandeep##"+str1);
			
			////// System.out.println("field value"+fieldValue+""+_iTotalRecord);
			
		
			if((fieldValue!=null)&&(fieldValue!=""))
			{
			
			////// System.out.println("inside btnNext");
			/*************************** PID-CPI process changes ***************************/
				//this._strQuery ="Select * from (SELECT DISTINCT PRIMARY_VERTICAL_VALUE FROM PRIMARY_VERT_DETAILS_MAS where PRIMARY_VERTICAL_VALUE >N'" + str1 +"' and UPPER(PRIMARY_VERTICAL_VALUE) like UPPER(N'"+fieldValue+"%')) where ROWNUM <= " + this._iBatchSize+" ORDER BY PRIMARY_VERTICAL_VALUE";
				this._strQuery ="Select * from (SELECT DISTINCT PRIMARY_VERTICAL_VALUE,PRIMARY_VERTICAL_CODE FROM PRIMARY_VERT_DETAILS_MAS where PRIMARY_VERTICAL_VALUE >N'" + str1 +"' and UPPER(PRIMARY_VERTICAL_VALUE) like UPPER(N'"+fieldValue+"%')) where ROWNUM <= " + this._iBatchSize+" ORDER BY PRIMARY_VERTICAL_VALUE";
								
			}
			else
			{
				//this._strQuery = "SELECT PRIMARY_VERTICAL_VALUE FROM (SELECT DISTINCT PRIMARY_VERTICAL_VALUE FROM PRIMARY_VERT_DETAILS_MAS where PRIMARY_VERTICAL_VALUE >N'" + str1 +"' ORDER BY PRIMARY_VERTICAL_VALUE) where ROWNUM <= " + this._iBatchSize;
				this._strQuery = "SELECT PRIMARY_VERTICAL_VALUE,CODE FROM (SELECT DISTINCT PRIMARY_VERTICAL_VALUE,PRIMARY_VERTICAL_CODE FROM PRIMARY_VERT_DETAILS_MAS where PRIMARY_VERTICAL_VALUE >N'" + str1 +"' ORDER BY PRIMARY_VERTICAL_VALUE) where ROWNUM <= " + this._iBatchSize;
			}
			/*********************** End PID-CPI process changes ***************************/
			////// System.out.println("2_Next strQuery1"+_strQuery);
		}
		
		
		/************************End CPI_PRIMARY_VERTICAL****************/
		
		/************************ CPI_PRIMARY_SUB_VERTICAL ****************/
		if((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_PRIMARY_SUB_VERTICAL"))))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			
			//colCount=1;//PID_CPI process changes
			colCount=2;//PID_CPI process changes
			str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
			////// System.out.println("sandeep##"+str1);
			
			////// System.out.println("field value"+fieldValue+""+_iTotalRecord);
			
			/*************************** PID-CPI process changes ***************************/
			if((fieldValue!=null)&&(fieldValue!=""))
			{
			
				////// System.out.println("inside btnNext ");
				//this._strQuery ="Select * from (SELECT DISTINCT PRIMARY_SUB_VERTICAL_VALUE FROM PRIMARY_VERT_DETAILS_MAS where PRIMARY_SUB_VERTICAL_VALUE!=' ' and PRIMARY_SUB_VERTICAL_VALUE >N'" + str1 +"' and UPPER(PRIMARY_SUB_VERTICAL_VALUE) like UPPER(N'"+fieldValue+"%')) where ROWNUM <= " + this._iBatchSize+" ORDER BY PRIMARY_SUB_VERTICAL_VALUE";
				this._strQuery ="Select * from (SELECT DISTINCT PRIMARY_SUB_VERTICAL_VALUE,prim_sub_vert_code FROM PRIMARY_VERT_DETAILS_MAS where PRIMARY_SUB_VERTICAL_VALUE!=' ' and PRIMARY_SUB_VERTICAL_VALUE >N'" + str1 +"' and UPPER(PRIMARY_SUB_VERTICAL_VALUE) like UPPER(N'"+fieldValue+"%')) where ROWNUM <= " + this._iBatchSize+" ORDER BY PRIMARY_SUB_VERTICAL_VALUE";
			}
			else
			{
				//this._strQuery = "SELECT PRIMARY_SUB_VERTICAL_VALUE FROM (SELECT DISTINCT PRIMARY_SUB_VERTICAL_VALUE FROM PRIMARY_VERT_DETAILS_MAS where PRIMARY_SUB_VERTICAL_VALUE!=' ' and  PRIMARY_SUB_VERTICAL_VALUE >N'" + str1 +"' ORDER BY PRIMARY_SUB_VERTICAL_VALUE) where ROWNUM <= " + this._iBatchSize;
				this._strQuery = "SELECT PRIMARY_SUB_VERTICAL_VALUE,prim_sub_vert_code FROM (SELECT DISTINCT PRIMARY_SUB_VERTICAL_VALUE,prim_sub_vert_code FROM PRIMARY_VERT_DETAILS_MAS where PRIMARY_SUB_VERTICAL_VALUE!=' ' and  PRIMARY_SUB_VERTICAL_VALUE >N'" + str1 +"' ORDER BY PRIMARY_SUB_VERTICAL_VALUE) where ROWNUM <= " + this._iBatchSize;
			}
			/*********************** End PID-CPI process changes ***************************/
			////// System.out.println("2_Next strQuery1"+_strQuery);
		}
		
		
		/************************End CPI_PRIMARY_SUB_VERTICAL****************/
		/**** Start CR-8001-70893 Marine CR *******************************/
		if((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_SECONDARY_VERTICAL"))))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			
			colCount=1;
			str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
			// System.out.println("sandeep##"+str1);
			
			// System.out.println("field value"+fieldValue+""+_iTotalRecord);
			
		
			if((fieldValue!=null)&&(fieldValue!=""))
			{
			
				// System.out.println("inside ");
				this._strQuery ="select * from (select distinct SECONDRY_VERTICAL_VALUE FROM SEC_VERT_DETAILS_MAS where SECONDRY_VERTICAL_VALUE!=' ' and SECONDRY_VERTICAL_VALUE >N'" + str1 +"' and UPPER(SECONDRY_VERTICAL_VALUE) like UPPER(N'"+fieldValue+"%')) where ROWNUM <= " + this._iBatchSize+" ORDER BY SECONDRY_VERTICAL_VALUE";
			}
			else
			{
				this._strQuery = "select SECONDRY_VERTICAL_VALUE FROM (select distinct SECONDRY_VERTICAL_VALUE FROM SEC_VERT_DETAILS_MAS where SECONDRY_VERTICAL_VALUE!=' ' and  SECONDRY_VERTICAL_VALUE >N'" + str1 +"' ORDER BY SECONDRY_VERTICAL_VALUE) where ROWNUM <= " + this._iBatchSize;
			}
			// System.out.println("2_Next strQuery1"+_strQuery);
		}
		/**** ENd CR-8001-70893 Marine CR *********************************/		/************************ CPI_SECONDARY_SUB_VERTICAL ****************/
		if((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_SECONDARY_SUB_VERTICAL"))))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			
			colCount=1;
			str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
			////// System.out.println("sandeep##"+str1);
			
			////// System.out.println("field value"+fieldValue+""+_iTotalRecord);
			
		
			if((fieldValue!=null)&&(fieldValue!=""))
			{
			
				////// System.out.println("inside ");
				this._strQuery ="Select * from (SELECT DISTINCT SECONDRY_SUB_VERTICAL_VALUE FROM SEC_VERT_DETAILS_MAS where SECONDRY_SUB_VERTICAL_VALUE!=' ' and SECONDRY_SUB_VERTICAL_VALUE >N'" + str1 +"' and UPPER(SECONDRY_SUB_VERTICAL_VALUE) like UPPER(N'"+fieldValue+"%')) where ROWNUM <= " + this._iBatchSize+" ORDER BY SECONDRY_SUB_VERTICAL_VALUE";
			}
			else
			{
				this._strQuery = "SELECT SECONDRY_SUB_VERTICAL_VALUE FROM (SELECT DISTINCT SECONDRY_SUB_VERTICAL_VALUE FROM SEC_VERT_DETAILS_MAS where SECONDRY_SUB_VERTICAL_VALUE!=' ' and  SECONDRY_SUB_VERTICAL_VALUE >N'" + str1 +"' ORDER BY SECONDRY_SUB_VERTICAL_VALUE) where ROWNUM <= " + this._iBatchSize;
			}
			////// System.out.println("2_Next strQuery1"+_strQuery);
		}
		
		
		/************************End CPI_SECONDARY_SUB_VERTICAL****************/
		
		/************************ CPI_SOURCE_NAME ****************/
		if((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_SOURCE_NAME")) || (this.strSource.equalsIgnoreCase("CORP_SOURCE_NAME"))))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			
			colCount=1;
			str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
			////// System.out.println("sandeep##"+str1);
			
			////// System.out.println("field value"+fieldValue+""+_iTotalRecord);
			
			if((fieldValue!=null)&&(fieldValue!=""))
			{
			
				////// System.out.println("inside ");
				this._strQuery ="SELECT   SOURCE FROM NG_CPI_SOURCE_MASTER    where SOURCE >N'" + str1 +"' and UPPER(SOURCE) like UPPER(N'"+fieldValue+"%') and   primary_sub_vertical_id="+bBGKRGID1+" and ROWNUM <= " + this._iBatchSize+" ORDER BY SOURCE";
				//this._strQuery = "SELECT     IL_LOCATION_VALUE FROM (SELECT   IL_LOCATION_VALUE FROM NG_IL_LOCATION_MASTER where IL_LOCATION_VALUE >'" + str1 +"' ORDER BY IL_LOCATION_VALUE) where ROWNUM <= " + this._iBatchSize;
			}
			else
			{
				this._strQuery = "SELECT     SOURCE FROM (SELECT   SOURCE FROM NG_CPI_SOURCE_MASTER where SOURCE >N'" + str1 +"'  and ORDER BY SOURCE) where ROWNUM <= " + this._iBatchSize+"  and   primary_sub_vertical_id="+bBGKRGID1+" ";
			}
			////// System.out.println("2_Next strQuery1"+_strQuery);
		}
		
		
		/************************End CPI_SOURCE_NAME****************/
			
			/************************ CPI_CHANNEL_NAME ****************/
		if((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_CHANNEL_NAME")) || (this.strSource.equalsIgnoreCase("CORP_CHANNEL_NAME"))))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			
			colCount=1;
			str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
			////// System.out.println("sandeep##"+str1);
			
			////// System.out.println("field value"+fieldValue+""+_iTotalRecord);
			
		
			if((fieldValue!=null)&&(fieldValue!=""))
			{
				if(BBGKRGVAL1.equalsIgnoreCase("BBG") || BBGKRGVAL1.equalsIgnoreCase("BRANCH BRANCHING")  
				/***************************** CR 28 by satish *****************************/
				|| BBGKRGVAL1.equalsIgnoreCase("SEG") || BBGKRGVAL1.equalsIgnoreCase("NA")
				/*************************** End CR 28 by satish ***************************/
				 || BBGKRGVAL1.equalsIgnoreCase("COB") // CR-OMCPI-1314-02 FIG COB CR
				)
				{
					////// System.out.println("inside ");
					this._strQuery ="SELECT   CHANNEL FROM NG_CPI_CHANNEL_MASTER    where CHANNEL >N'" + str1 +"' and UPPER(CHANNEL) like UPPER(N'"+fieldValue+"%') and   primary_sub_vertical_id="+bBGKRGID1+" and ROWNUM <= " + this._iBatchSize+" ORDER BY CHANNEL";
					//this._strQuery = "SELECT     IL_LOCATION_VALUE FROM (SELECT   IL_LOCATION_VALUE FROM NG_IL_LOCATION_MASTER where IL_LOCATION_VALUE >'" + str1 +"' ORDER BY IL_LOCATION_VALUE) where ROWNUM <= " + this._iBatchSize;
				}
				else if(BBGKRGVAL1.equalsIgnoreCase("KRG") || BBGKRGVAL1.equalsIgnoreCase("KEY RELATIONSHIP GROUP"))//CR-8001-87420 Green File Digitization)
				{
				
							this._strQuery ="select CHANNEL from (select CHANNEL from NG_CPI_SOURCE_CHANNEL_MASTER A,NG_CPI_SOURCE_MASTER B,NG_CPI_CHANNEL_MASTER C  Where  UPPER(CHANNEL) like UPPER(N'"+fieldValue+"%') and CHANNEL >N'" + str1 +"' and B.Recordid=A.Source_ID and C.Recordid=A.Channel_ID and A.SOURCE_ID="+sourceID1+" order by c.CHANNEL) where  ROWNUM <= " + this._iBatchSize;
				
					//this._strQuery ="select CHANNEL from NG_CPI_SOURCE_CHANNEL_MASTER A,NG_CPI_SOURCE_MASTER B,NG_CPI_CHANNEL_MASTER C  Where  UPPER(CHANNEL) like UPPER(N'"+fieldValue+"%') and CHANNEL >N'" + str1 +"' and B.Recordid=A.Source_ID and C.Recordid=A.Channel_ID and A.SOURCE_ID="+sourceID1+" and ROWNUM <= " + this._iBatchSize+" ORDER BY CHANNEL";
				}
			}
			else
			{
				if(BBGKRGVAL1.equalsIgnoreCase("BBG") || BBGKRGVAL1.equalsIgnoreCase("BRANCH BRANCHING") 
				/***************************** CR 28 by satish *****************************/
				|| BBGKRGVAL1.equalsIgnoreCase("SEG") || BBGKRGVAL1.equalsIgnoreCase("NA")
				/*************************** End CR 28 by satish ***************************/
				)
				{
				this._strQuery = "SELECT     CHANNEL FROM (SELECT   CHANNEL FROM NG_CPI_CHANNEL_MASTER where CHANNEL >N'" + str1 +"'  and ORDER BY CHANNEL) where ROWNUM <= " + this._iBatchSize+"  and   primary_sub_vertical_id="+bBGKRGID1+" ";
				}
				else if(BBGKRGVAL1.equalsIgnoreCase("KRG") || BBGKRGVAL1.equalsIgnoreCase("KEY RELATIONSHIP GROUP")) //CR-8001-87420 Green File Digitization)
				{
				
					//this._strQuery ="select CHANNEL from NG_CPI_SOURCE_CHANNEL_MASTER A,NG_CPI_SOURCE_MASTER B,NG_CPI_CHANNEL_MASTER C  Where  B.Recordid=A.Source_ID and C.Recordid=A.Channel_ID and A.SOURCE_ID="+sourceID1+" and ROWNUM <= " + this._iBatchSize+" ORDER BY CHANNEL";
				
					this._strQuery ="select CHANNEL from (select CHANNEL from NG_CPI_SOURCE_CHANNEL_MASTER A,NG_CPI_SOURCE_MASTER B,NG_CPI_CHANNEL_MASTER C  Where   CHANNEL >N'" + str1 +"' and B.Recordid=A.Source_ID and C.Recordid=A.Channel_ID and A.SOURCE_ID="+sourceID1+" order by c.CHANNEL) where  ROWNUM <= " + this._iBatchSize;
				
				}
				
			}
			////// System.out.println("2_Next strQuery1"+_strQuery);
		}
		
		
		/************************End CPI_CHANNEL_NAME****************/
		
		/************************ CPI_BRANCH_NAME ****************/
		if((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_BRANCH_NAME")) || (this.strSource.equalsIgnoreCase("CORP_BRANCH_NAME"))))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			
			//colCount=1;  //PID_CPI process changes
			colCount=10;
			str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
			////// System.out.println("sandeep##"+str1);
			
			////// System.out.println("field value"+fieldValue+""+_iTotalRecord);
			
		
			if((fieldValue!=null)&&(fieldValue!=""))
			{
			
				////// System.out.println("inside ");
			
				if(BBGKRGVAL1.equalsIgnoreCase("BBG") || BBGKRGVAL1.equalsIgnoreCase("BRANCH BRANCHING")
				/***************************** CR 28 by satish *****************************/
				|| BBGKRGVAL1.equalsIgnoreCase("SEG") || BBGKRGVAL1.equalsIgnoreCase("NA")
				/*************************** End CR 28 by satish ***************************/
				 || BBGKRGVAL1.equalsIgnoreCase("COB") // CR-OMCPI-1314-02 FIG COB CR
				)
				{
					//this._strQuery ="SELECT   BRANCH FROM ng_cpi_branch_master    where BRANCH >'" + str1 +"' and UPPER(BRANCH) like UPPER(N'"+fieldValue+"%') and   primary_sub_vertical_id=bBGKRGID1 and ROWNUM <= " + this._iBatchSize+" ORDER BY BRANCH";
					/*************************** PID-CPI process changes ***************************/
					//this._strQuery ="SELECT   BRANCH FROM ng_cpi_branch_master    where BRANCH >N'" + str1 +"' and UPPER(BRANCH) like UPPER(N'"+fieldValue+"%') and   primary_sub_vertical_id="+bBGKRGID1+ " and ROWNUM <= " + this._iBatchSize+" ORDER BY BRANCH";
					this._strQuery ="SELECT   BRANCH,branch_id,sp_name1,sp_code1,sp_name2,sp_code2,sp_name3,sp_code3,sp_name4,sp_code4 FROM ng_cpi_branch_master    where BRANCH >N'" + str1 +"' and UPPER(BRANCH) like UPPER(N'"+fieldValue+"%') and   primary_sub_vertical_id="+bBGKRGID1+ " and ROWNUM <= " + this._iBatchSize+" ORDER BY BRANCH";
					
				}
				else if(BBGKRGVAL1.equalsIgnoreCase("KRG")|| BBGKRGVAL1.equalsIgnoreCase("KEY RELATIONSHIP GROUP"))//CR-8001-87420 Green File Digitization)
				{
					//this._strQuery ="select BRANCH from NG_CPI_SOURCE_BRANCH_MASTER A,NG_CPI_SOURCE_MASTER B,NG_CPI_BRANCH_MASTER C Where BRANCH >N'" + str1 +"' and UPPER(BRANCH) like UPPER(N'"+fieldValue+"%') and  ROWNUM <= " + this._iBatchSize+"and B.Recordid=A.Source_ID and C.Recordid=A.Branch_ID  and A.SOURCE_ID="+sourceID1+" ORDER BY BRANCH";
					this._strQuery ="select BRANCH,C.branch_id,sp_name1,sp_code1,sp_name2,sp_code2,sp_name3,sp_code3,sp_name4,sp_code4 from NG_CPI_SOURCE_BRANCH_MASTER A,NG_CPI_SOURCE_MASTER B,NG_CPI_BRANCH_MASTER C Where BRANCH >N'" + str1 +"' and UPPER(BRANCH) like UPPER(N'"+fieldValue+"%') and  ROWNUM <= " + this._iBatchSize+"and B.Recordid=A.Source_ID and C.Recordid=A.Branch_ID  and A.SOURCE_ID="+sourceID1+" ORDER BY BRANCH";
					/*********************** End PID-CPI process changes ***************************/
				}
			
				//+" ORDER BY BRANCH";
				//this._strQuery = "SELECT     IL_LOCATION_VALUE FROM (SELECT   IL_LOCATION_VALUE FROM NG_IL_LOCATION_MASTER where IL_LOCATION_VALUE >'" + str1 +"' ORDER BY IL_LOCATION_VALUE) where ROWNUM <= " + this._iBatchSize;
				
			}
			else
			{
				if(BBGKRGVAL1.equalsIgnoreCase("BBG") || BBGKRGVAL1.equalsIgnoreCase("BRANCH BRANCHING") 
				/***************************** CR 28 by satish *****************************/
				|| BBGKRGVAL1.equalsIgnoreCase("SEG") || BBGKRGVAL1.equalsIgnoreCase("NA")
				/*************************** End CR 28 by satish ***************************/
				)
				{
					/*************************** PID-CPI process changes ***************************/
					//this._strQuery = "SELECT     BRANCH FROM (SELECT   BRANCH FROM ng_cpi_branch_master where BRANCH >N'" + str1 +"'  and ORDER BY BRANCH) where ROWNUM <= " + this._iBatchSize+ " and   primary_sub_vertical_id="+bBGKRGID1+"";
					this._strQuery = "SELECT     BRANCH,branch_id,sp_name1,sp_code1,sp_name2,sp_code2,sp_name3,sp_code3,sp_name4,sp_code4 FROM (SELECT   BRANCH,branch_id,sp_name1,sp_code1,sp_name2,sp_code2,sp_name3,sp_code3,sp_name4,sp_code4 FROM ng_cpi_branch_master where BRANCH >N'" + str1 +"'  and ORDER BY BRANCH) where ROWNUM <= " + this._iBatchSize+ " and   primary_sub_vertical_id="+bBGKRGID1+"";
					
				}
				else if(BBGKRGVAL1.equalsIgnoreCase("KRG") || BBGKRGVAL1.equalsIgnoreCase("KEY RELATIONSHIP GROUP"))//CR-8001-87420 Green File Digitization)
				{
					//this._strQuery = "select BRANCH from NG_CPI_SOURCE_BRANCH_MASTER A,NG_CPI_SOURCE_MASTER B,NG_CPI_BRANCH_MASTER C Where BRANCH >N'" + str1 +"'  and ROWNUM <= " + this._iBatchSize+ " and B.Recordid=A.Source_ID and C.Recordid=A.Branch_ID  and A.SOURCE_ID="+sourceID1+" ORDER BY BRANCH";
					this._strQuery = "select BRANCH,C.branch_id,sp_name1,sp_code1,sp_name2,sp_code2,sp_name3,sp_code3,sp_name4,sp_code4 from NG_CPI_SOURCE_BRANCH_MASTER A,NG_CPI_SOURCE_MASTER B,NG_CPI_BRANCH_MASTER C Where BRANCH >N'" + str1 +"'  and ROWNUM <= " + this._iBatchSize+ " and B.Recordid=A.Source_ID and C.Recordid=A.Branch_ID  and A.SOURCE_ID="+sourceID1+" ORDER BY BRANCH";
					/*********************** End PID-CPI process changes ***************************/
				}
			}
			////// System.out.println("2_Next strQuery1"+_strQuery);
		}
				/************************End CPI_BRANCH_NAME****************/
			
			/*****************  CPI_naME_OF_LEADER by satish for CR21 *********************/
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_naME_OF_LEADER")))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			
			colCount=1;
			str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
			////// System.out.println("satish##"+str1);
			
			////// System.out.println("field value"+fieldValue+""+_iTotalRecord);
			
		
			if((fieldValue!=null)&&(fieldValue!=""))
			{
			
			////// System.out.println("inside ");
				 this._strQuery = "select LEADER_NAME from(select LEADER_NAME from NG_CPI_LEADER_MST where LEADER_CATEGORY_TYPE_ID="+product_type_val+" and LEADER_NAME >N'" + str1 +"' and UPPER(LEADER_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY LEADER_NAME) where  ROWNUM <= " + this._iBatchSize;
			}
			else
			{
				 this._strQuery = "select LEADER_NAME from(select LEADER_NAME from NG_CPI_LEADER_MST where LEADER_CATEGORY_TYPE_ID="+product_type_val+" and LEADER_NAME >N'" + str1 +"' ORDER BY LEADER_NAME) where  ROWNUM <= " + this._iBatchSize;
			}
			////// System.out.println("2_Next strQuery1"+_strQuery);
		}
		
		/***************** End CPI_naME_OF_LEADER by satish for CR21 *********************/
			
			/************************ Product Name ****************/
		if((this.strSource != null) && ((this.strSource.equalsIgnoreCase("End_PRODUCT_NAME"))||(this.strSource.equalsIgnoreCase("Corp_PRODUCT_NAME"))||(this.strSource.equalsIgnoreCase("CPI_PRODUCT_NAME"))))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			
			//colCount=1;//Vendor login & weather Product
			colCount=2;//Vendor login & weather Product
			str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
			////// System.out.println("sandeep##"+str1);
			////// System.out.println("field value"+fieldValue+""+_iTotalRecord);
			
			if((fieldValue!=null)&&(fieldValue!=""))
			{
				////// System.out.println("inside ");
				/***************** CR21 *********************/
				if((_objFormObject.getWFActivityName().equalsIgnoreCase("Co_Insurance")) || (_objFormObject.getWFActivityName().equalsIgnoreCase("Co_Insurance_RM")))
				{
					this._strQuery = "select PRODUCT_NAME,IRDACODE from(select PRODUCT_NAME,IRDACODE from NG_PRODUCT_MASTER where PRODUCT_CATEGORY_ID="+product_type_val+" and PRODUCT_NAME >N'" + str1 +"' and UPPER(PRODUCT_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY PRODUCT_NAME) where  ROWNUM <= " + this._iBatchSize;//Vendor login & weather Product
				}
				/******************* CR 46 CPU DataWashing********************/
				else if(_objFormObject.getNGValue("CPI_DATAWASHING_TYPE").equalsIgnoreCase("Endorsement"))
				{
					////// System.out.println("5.CR 46 CPI_DATAWASHING_TYPE: "+_objFormObject.getNGValue("CPI_DATAWASHING_TYPE"));
					this._strQuery = "select PRODUCT_NAME,IRDACODE from(select PRODUCT_NAME,IRDACODE from NG_PRODUCT_MASTER where PRODUCT_TYPE_ID="+product_type_val+" AND PRODUCT_ROUTING = '2' AND PRODUCT_NAME >N'" + str1 +"' AND UPPER(PRODUCT_NAME) like UPPER('"+fieldValue+"%') ORDER BY PRODUCT_NAME) where  ROWNUM <= " + this._iBatchSize;//Vendor login & weather Product
				}/*****************end CR 46 CPU DataWashing*******************/
				else
				/*****************End CR21 *********************/
				{
					this._strQuery = "select PRODUCT_NAME,IRDACODE from(select PRODUCT_NAME,IRDACODE from NG_PRODUCT_MASTER where PRODUCT_TYPE_ID="+product_type_val+" and PRODUCT_NAME >N'" + str1 +"' and UPPER(PRODUCT_NAME) like UPPER('"+fieldValue+"%') ORDER BY PRODUCT_NAME) where  ROWNUM <= " + this._iBatchSize;//Vendor login & weather Product
				}
			}
			else
			{
				/***************** CR21 *********************/
				if((_objFormObject.getWFActivityName().equalsIgnoreCase("Co_Insurance")) || (_objFormObject.getWFActivityName().equalsIgnoreCase("Co_Insurance_RM")))
				{
					this._strQuery = "select PRODUCT_NAME,IRDACODE from(select PRODUCT_NAME,IRDACODE from NG_PRODUCT_MASTER where PRODUCT_CATEGORY_ID="+product_type_val+" and PRODUCT_NAME >N'" + str1 +"' ORDER BY PRODUCT_NAME) where  ROWNUM <= " + this._iBatchSize;//Vendor login & weather Product
				}
				/******************* CR 46 CPU DataWashing********************/
				else if(_objFormObject.getNGValue("CPI_DATAWASHING_TYPE").equalsIgnoreCase("Endorsement"))
				{
					////// System.out.println("6.CR 46 CPI_DATAWASHING_TYPE: "+_objFormObject.getNGValue("CPI_DATAWASHING_TYPE"));
					this._strQuery = "select PRODUCT_NAME,IRDACODE from(select PRODUCT_NAME,IRDACODE from NG_PRODUCT_MASTER where PRODUCT_TYPE_ID="+product_type_val+" AND PRODUCT_ROUTING = '2' AND PRODUCT_NAME >N'" + str1 +"' ORDER BY PRODUCT_NAME) where  ROWNUM <= " + this._iBatchSize;	//Vendor login & weather Product
				}/*****************end CR 46 CPU DataWashing*******************/
				else
				/*****************End CR21 *********************/
				{
					this._strQuery = "select PRODUCT_NAME,IRDACODE from(select PRODUCT_NAME,IRDACODE from NG_PRODUCT_MASTER where PRODUCT_TYPE_ID="+product_type_val+" and PRODUCT_NAME >N'" + str1 +"' ORDER BY PRODUCT_NAME) where  ROWNUM <= " + this._iBatchSize;//Vendor login & weather Product			
				}
			}
			////// System.out.println("2_Next strQuery1"+_strQuery);
		}
		/************************End Product Name****************/
		
		
		
				//serach cirteria next
		if((this.strSource != null) && (this.strSource.equals("SEARCH_STRING")))
		{
			fieldValue=_objFormObject.getNGValue(this.strSource);
			
			colCount=4;
			tempStr=_objFormObject.getNGValue("ICICILOMBARD_HT_SEARCH_CRITERIA");
			str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 1);
			if(tempStr.equals("Agent Name"))
			{
				Col="i.TXT_INTERMEDIARY_NAME";
			}
			else if (tempStr.equals("Agent Code"))
			{
				Col="i.TXT_INTERMEDIARY_CD";
			}
			else if (tempStr.equals("Deal No"))
			{
				Col="d.TXT_DEAL_ID";
			}
			if(tempStr.equals("Deal Status"))
			{
				Col="d.TXT_DISPLAY_RM_BS";
			}
				
			if(fieldValue!=null)
			{

				this._strQuery ="select TXT_INTERMEDIARY_NAME,TXT_INTERMEDIARY_CD, txt_deal_id,TXT_DISPLAY_RM_BS from (select i.TXT_INTERMEDIARY_NAME as TXT_INTERMEDIARY_NAME,i.TXT_INTERMEDIARY_CD as TXT_INTERMEDIARY_CD, d.txt_deal_id as txt_deal_id,d.TXT_DISPLAY_RM_BS as TXT_DISPLAY_RM_BS from MV_GENMST_INTERMEDIARY i, MV_Gen_Deal_Detail d where d.TXT_INTERMEDIARY_CD=i.TXT_INTERMEDIARY_CD  and UPPER("+Col+") like UPPER(N'"+fieldValue+"%') and d.TXT_INTERMEDIARY_CD >N'" + str1 +"' ORDER BY i.TXT_INTERMEDIARY_CD) where ROWNUM <= " + this._iBatchSize;
			}
			else
			{
				this._strQuery ="select TXT_INTERMEDIARY_NAME,TXT_INTERMEDIARY_CD, txt_deal_id,TXT_DISPLAY_RM_BS from (select i.TXT_INTERMEDIARY_NAME as TXT_INTERMEDIARY_NAME,i.TXT_INTERMEDIARY_CD as TXT_INTERMEDIARY_CD, d.txt_deal_id as txt_deal_id,d.TXT_DISPLAY_RM_BS as TXT_DISPLAY_RM_BS from MV_GENMST_INTERMEDIARY i, MV_Gen_Deal_Detail d where d.TXT_INTERMEDIARY_CD=i.TXT_INTERMEDIARY_CD and d.TXT_INTERMEDIARY_CD >N'" + str1 +"' ORDER BY i.TXT_INTERMEDIARY_CD) where ROWNUM <= " + this._iBatchSize;
			}	
			////// System.out.println("ICICILOMBARD_HT_Query "+_strQuery);
		
		}

		//il location next
		
		else if((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_IL_LOCATION")))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			colCount=2;
			str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 1);
			
		
			if(fieldValue!=null)
			{
				this._strQuery ="SELECT ILBRANCHNAME, ILBRANCHCODE FROM (select DISTINCT ILBRANCHNAME, ILBRANCHCODE  from NG_ICICI_MST_ILLOCATION where ILBRANCHCODE >N'" + str1 +"' and UPPER(ILBRANCHNAME) like UPPER(N'"+fieldValue+"%') ORDER BY ILBRANCHCODE) where ROWNUM <= " + this._iBatchSize;
			}
			else
			{
				this._strQuery = "SELECT ILBRANCHNAME, ILBRANCHCODE FROM (select DISTINCT ILBRANCHNAME, ILBRANCHCODE  from NG_ICICI_MST_ILLOCATION where ILBRANCHCODE >N'" + str1 +"' ORDER BY ILBRANCHCODE) where ROWNUM <= " + this._iBatchSize;
			}

		  ////// System.out.println("ICICILOMBARD_HT_IL_LOCATION****Next query ::" + this._strQuery);
		}
		
		
		/******************************* PID-HT process changes ********************************/
		//il location code next
		else if((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_IL_LOCATION_CODE")))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			colCount=2;
			str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 1);
			
		
			if(fieldValue!=null)
			{
				this._strQuery ="SELECT ILBRANCHNAME, ILBRANCHCODE FROM (select DISTINCT ILBRANCHNAME, ILBRANCHCODE  from NG_ICICI_MST_ILLOCATION where ILBRANCHCODE >N'" + str1 +"' and UPPER(ILBRANCHCODE) like UPPER(N'"+fieldValue+"%') ORDER BY ILBRANCHCODE) where ROWNUM <= " + this._iBatchSize;
			}
			else
			{
				this._strQuery = "SELECT ILBRANCHNAME, ILBRANCHCODE FROM (select DISTINCT ILBRANCHNAME, ILBRANCHCODE  from NG_ICICI_MST_ILLOCATION where ILBRANCHCODE >N'" + str1 +"' ORDER BY ILBRANCHCODE) where ROWNUM <= " + this._iBatchSize;
			}

		  ////// System.out.println("ICICILOMBARD_HT_IL_LOCATION_CODE****Next query ::" + this._strQuery);
		}
		/******************************End PID-HT process changes ******************************/

		 
		 //channel source
		else if((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_SOURCE_BUSINESS")) && ((_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking (BBG)") || _objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BBG"))))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=1;
				str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
				if(fieldValue!=null)
				{
					this._strQuery ="SELECT SOURCEBUSINESS FROM (select DISTINCT(SOURCEBUSINESS) from NG_ICICI_MST_BBG_BUSINESS where  SOURCEBUSINESS >N'" + str1 +"' and UPPER(SOURCEBUSINESS) like UPPER(N'"+fieldValue+"%') ORDER BY SOURCEBUSINESS) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					this._strQuery ="SELECT SOURCEBUSINESS FROM (select DISTINCT(SOURCEBUSINESS) from NG_ICICI_MST_BBG_BUSINESS where  SOURCEBUSINESS >N'" + str1 +"' ORDER BY SOURCEBUSINESS) where ROWNUM <= " + this._iBatchSize;
				}

			} 

		else if((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_SOURCE_BUSINESS")) && (_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)")))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);	
				colCount=1;
				str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
				if(fieldValue!=null)
				{
								
					  this._strQuery = "select TXTSOURCEBUSINESS from (select DISTINCT(TXTSOURCEBUSINESS) from NG_ICICI_MST_KRG where TXTSOURCEBUSINESS >N'" + str1 +"' and UPPER(TXTSOURCEBUSINESS) like UPPER(N'"+fieldValue+"%') and TXTSOURCEBUSINESS is not NULL ORDER BY TXTSOURCEBUSINESS) where  ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					 
					 this._strQuery = "select TXTSOURCEBUSINESS from (select DISTINCT(TXTSOURCEBUSINESS) from NG_ICICI_MST_KRG where TXTSOURCEBUSINESS >N'" + str1 +"' and TXTSOURCEBUSINESS is not NULL ORDER BY TXTSOURCEBUSINESS) where  ROWNUM <= " + this._iBatchSize;
				}
			}
			
			//channel; source
			else if((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_CHANNEL_SOURCE")))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=1;
				str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
				if(fieldValue!=null)
				{
								
					  this._strQuery = "select CHANNELSOURCING from(select DISTINCT(CHANNELSOURCING) from NG_ICICI_MST_BBG_SOURCE where CHANNELSOURCING >N'" + str1 +"' and UPPER(CHANNELSOURCING) like UPPER(N'"+fieldValue+"%') ORDER BY CHANNELSOURCING) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					 
					 this._strQuery = "select CHANNELSOURCING from(select DISTINCT(CHANNELSOURCING) from NG_ICICI_MST_BBG_SOURCE where CHANNELSOURCING >N'" + str1 +"' ORDER BY CHANNELSOURCING) where ROWNUM <= " + this._iBatchSize;
				}
			}
		
			/******************************* PID-HT process changes ********************************/
			//for ICICILOMBARD_HT_PAYMENT_MODE
			else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_PAYMENT_MODE")))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=1;
				str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
				if(fieldValue!=null)
				{
					  this._strQuery = "select Payment_Mode from(select DISTINCT(Payment_Mode) from NG_HT_PAYMENT_MODE_MASTER where Payment_Mode >N'" + str1 +"' and UPPER(Payment_Mode) like UPPER(N'"+fieldValue+"%') ORDER BY Payment_Mode) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					 this._strQuery = "select Payment_Mode from(select DISTINCT(Payment_Mode) from NG_HT_PAYMENT_MODE_MASTER where Payment_Mode >N'" + str1 +"' ORDER BY Payment_Mode) where ROWNUM <= " + this._iBatchSize;
				}
			}
			/******************************End PID-HT process changes ******************************/
			
			
			/**************************************Start HT SP Code CR CR-8093-69682 Next*****************************************************/
			//deal_il_location next
			else if((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_DEAL_IL_LOCATION")))
			{
				String sm_id =_objFormObject.getNGValue("ICICILOMBARD_HT_SM_ID");
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=1;
				str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);

				if(fieldValue!=null)
				{
					this._strQuery ="select b.TXT_OFFICE from MV_CENTRAL_EMPLOYEE a, MV_GENMST_OFFICE b where a.NUM_OFFICE_CD=b.NUM_OFFICE_CD and a.TXT_HR_REF_NO='"+sm_id+"' and b.TXT_OFFICE >N'" + str1 +"' and UPPER(b.TXT_OFFICE) like UPPER(N'"+fieldValue+"%') and ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					this._strQuery = "select b.TXT_OFFICE from MV_CENTRAL_EMPLOYEE a, MV_GENMST_OFFICE b where a.NUM_OFFICE_CD=b.NUM_OFFICE_CD and a.TXT_HR_REF_NO='"+sm_id+"' and b.TXT_OFFICE >N'" + str1 +"' and ROWNUM <= " + this._iBatchSize;
				}
				////// System.out.println("ICICILOMBARD_HT_DEAL_IL_LOCATION****Next query ::" + this._strQuery);
			}
			//bank branch next
			else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_BANK_BRANCH_NAME")))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=1;
				str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
				if(_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking (BBG)") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("HOME") || (_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG") && _objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BBG")))
				{
					if(fieldValue!=null)
					{
						  this._strQuery = "select BBGBRANCHNAME from(select distinct(BBGBRANCHNAME) from NG_ICICI_MST_BBG_HOMEBRANCH where BBGBRANCHNAME >N'" + str1 +"' and UPPER(BBGBRANCHNAME) like UPPER(N'"+fieldValue+"%') ORDER BY BBGBRANCHNAME) where ROWNUM <= " + this._iBatchSize;
					}
					else
					{
						 this._strQuery = "select BBGBRANCHNAME from(select distinct(BBGBRANCHNAME) from NG_ICICI_MST_BBG_HOMEBRANCH where BBGBRANCHNAME >N'" + str1 +"' ORDER BY BBGBRANCHNAME) where ROWNUM <= " + this._iBatchSize;
					}
				}
				if((_objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE"))&&(_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING")))
				{
					fieldValue=localNGPickList.getSearchFilterValue();
					_iTotalRecord=getTotalRecord(fieldValue);
					colCount=2;
					str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 1);
					//not required for home
					String channel_source=_objFormObject.getNGValue("ICICILOMBARD_HT_CHANNEL_SOURCE");
					String deal_il_location=_objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_IL_LOCATION");
					if(fieldValue!=null)
					{
						  this._strQuery = "select BANK_BRANCH_NAME,SOL_ID from(select distinct BANK_BRANCH_NAME,SOL_ID from NG_HT_MST_SP_CODE where CHANNEL_SOURCE ='"+channel_source+"' and DEAL_IL_LOCATION='"+deal_il_location+"' and BANK_BRANCH_NAME >N'" + str1 +"' and UPPER(BANK_BRANCH_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY BANK_BRANCH_NAME) where ROWNUM <= " + this._iBatchSize;
					}
					else
					{
						 this._strQuery = "select BANK_BRANCH_NAME,SOL_ID from(select distinct BANK_BRANCH_NAME,SOL_ID from NG_HT_MST_SP_CODE where CHANNEL_SOURCE ='"+channel_source+"' and DEAL_IL_LOCATION='"+deal_il_location+"' and BANK_BRANCH_NAME >N'" + str1 +"' ORDER BY BANK_BRANCH_NAME) where ROWNUM <= " + this._iBatchSize;
					}
				////// System.out.println("ICICILOMBARD_HT_BANK_BRANCH_NAME****Next query ::" +this._strQuery);
				}
			}
			//sol_id next
			else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_SOL_ID")) && _objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE") && (_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING")))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=2;
				str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 1);
				String channel_source=_objFormObject.getNGValue("ICICILOMBARD_HT_CHANNEL_SOURCE");
				String deal_il_location=_objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_IL_LOCATION");
				if(fieldValue!=null)
				{
					  this._strQuery = "select BANK_BRANCH_NAME,SOL_ID from(select distinct BANK_BRANCH_NAME,SOL_ID from NG_HT_MST_SP_CODE where CHANNEL_SOURCE ='"+channel_source+"' and DEAL_IL_LOCATION='"+deal_il_location+"' and SOL_ID >N'" + str1 +"' and UPPER(SOL_ID) like UPPER(N'"+fieldValue+"%') ORDER BY SOL_ID) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					 this._strQuery = "select BANK_BRANCH_NAME,SOL_ID from(select distinct BANK_BRANCH_NAME,SOL_ID from NG_HT_MST_SP_CODE where CHANNEL_SOURCE ='"+channel_source+"' and DEAL_IL_LOCATION='"+deal_il_location+"' and SOL_ID >N'" + str1 +"' ORDER BY SOL_ID) where ROWNUM <= " + this._iBatchSize;
				}
				////// System.out.println("ICICILOMBARD_HT_SOL_ID****Next query ::" + this._strQuery);
			}
			//for sp code next
			else if((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_WRE")) && _objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE") && (_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING")))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=3;
				str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 2);
				String deal_il_location=_objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_IL_LOCATION");
				String sol_id=_objFormObject.getNGValue("ICICILOMBARD_HT_SOL_ID");
				String channel_source=_objFormObject.getNGValue("ICICILOMBARD_HT_CHANNEL_SOURCE");
				if(fieldValue!=null)
				{
					this._strQuery = "select SP_CODE,SP_NAME,SP_PAN from(select distinct SP_CODE,SP_NAME,SP_PAN from NG_HT_MST_SP_CODE where DEAL_IL_LOCATION='"+deal_il_location+"'and CHANNEL_SOURCE ='"+channel_source+"' and SOL_ID='"+sol_id+"' and SP_CODE >N'" + str1 +"' and UPPER(SP_CODE) like UPPER(N'"+fieldValue+"%') ORDER BY SP_CODE) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					 this._strQuery = "select SP_CODE,SP_NAME,SP_PAN from(select distinct SP_CODE,SP_NAME,SP_PAN from NG_HT_MST_SP_CODE where DEAL_IL_LOCATION='"+deal_il_location+"'and CHANNEL_SOURCE ='"+channel_source+"' and SOL_ID='"+sol_id+"' and SP_CODE >N'" + str1 +"' ORDER BY SP_CODE) where ROWNUM <= " + this._iBatchSize;
				}
				////// System.out.println("ICICILOMBARD_HT_WRE****Next query ::" + this._strQuery);
			}				
			/**************************************End HT SP Code CR CR-8093-69682 Next*****************************************************/
			/***************** Start SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI**************************************/
			// ICICILOMBARD_HT_WRE KRG
			else if((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_WRE")) && (_objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE")) && (_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)")))
			{
				// System.out.println("Inside the Next sp code: Yogesh");
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=2;
				str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
				if(fieldValue!=null)
				{
					// System.out.println("Inside the if Next sp code: Yogesh");
					this._strQuery = "select SP_CODE,SP_NAME from(select distinct SP_CODE,SP_NAME from NG_HT_SP_CODE_KRG where SP_CODE >N'" + str1 +"' and UPPER(SP_CODE) like UPPER(N'"+fieldValue+"%') ORDER BY SP_CODE) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					// System.out.println("Inside the else Next sp code: Yogesh");
					this._strQuery = "select SP_CODE,SP_NAME from(select distinct SP_CODE,SP_NAME from NG_HT_SP_CODE_KRG where SP_CODE >N'" + str1 +"' ORDER BY SP_CODE) where ROWNUM <= " + this._iBatchSize;
				}
				// System.out.println("ICICILOMBARD_HT_WRE****Next query ::" + this._strQuery);
			}
			/***************** End SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI**************************************/
			
			/******Start HT CR-8127-95325-GST-Omniflow development******/
			//TXTGST_STATE_NAME next
			else if((this.strSource != null) && (this.strSource.equals("TXTGST_STATE_NAME")) && (_objFormObject.getNGValue("ICICILOMBARD_HT_GST_REGISTERED").equalsIgnoreCase("Yes")) && !(_objFormObject.getNGValue("ICICILOMBARD_HT_IAGENT").equalsIgnoreCase("Yes")))
			{
				// System.out.println("Inside the Next GST State: Yogesh");//select a.txtstatename,a.txtstatecode from NG_HT_MST_GST_STATE a
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=1;
				str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);

				if(fieldValue!=null)
				{
					this._strQuery ="select txtstatename from NG_HT_MST_GST_STATE where txtstatename >N'" + str1 +"' and UPPER(txtstatename) like UPPER(N'"+fieldValue+"%') and ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					this._strQuery = "select txtstatename from NG_HT_MST_GST_STATE where txtstatename >N'" + str1 +"' and ROWNUM <= " + this._iBatchSize;
				}
				// System.out.println("TXTGST_STATE_NAME****Next query ::" + this._strQuery);
			}
			/******End HT CR-8127-95325-GST-Omniflow development******/
			/*****Start Change related to Application  Proposal no. field in Omni flow HT*****/
			else if((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_CHANNEL_LOAN_TYPE")))
			{
				System.out.println("Inside the Next Change related to Application  Proposal no. field in Omni flow HT: Yogesh");//select a.txtstatename,a.txtstatecode from NG_HT_MST_GST_STATE a
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=2;
				str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 1);

				if(fieldValue!=null)
				{
					this._strQuery ="select channel from NG_HT_MST_KRG_CHANNEL_SOURCE where channel >N'" + str1 +"' and UPPER(channel) like UPPER(N'"+fieldValue+"%') and ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					this._strQuery = "select channel from NG_HT_MST_KRG_CHANNEL_SOURCE where channel >N'" + str1 +"' and ROWNUM <= " + this._iBatchSize;
				}
				System.out.println("ICICILOMBARD_HT_CHANNEL_LOAN_TYPE****Next query ::" + this._strQuery);
			}
			/*****End Change related to Application  Proposal no. field in Omni flow HT*****/
			//BRANCH ID/UBO NAME NEXT
		
			else if ((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_BRANCH_ID_UBO_NAME")))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=1;
				str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
				if(_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)"))
				{
					if(fieldValue!=null)
					{
						  this._strQuery = "select Txtbranchidubo from(select DISTINCT(Txtbranchidubo) from NG_ICICI_MST_KRG where Txtbranchidubo >N'" + str1 +"' and UPPER(Txtbranchidubo) like UPPER(N'"+fieldValue+"%') ORDER BY Txtbranchidubo) where ROWNUM <= " + this._iBatchSize;
					}
					else
					{
						 this._strQuery = "select Txtbranchidubo from(select DISTINCT(Txtbranchidubo) from NG_ICICI_MST_KRG where Txtbranchidubo >N'" + str1 +"' ORDER BY Txtbranchidubo) where ROWNUM <= " + this._iBatchSize;
					}
				////// System.out.println("ICICILOMBARD_HT_BRANCH_ID_UBO_NAME count3:"+this._strQuery);
				}	
			}
			
			//next sm id sm name
			
			/********************** Start MO filteration for Centralised Deal Vertical **********************/
			else if ((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_SM_NAME")))
			{
				if (_objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_STATUS").equalsIgnoreCase("YES"))
				{
					fieldValue=localNGPickList.getSearchFilterValue();
					_iTotalRecord=getTotalRecord(fieldValue);
					colCount=4;
					str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 1);
					if(fieldValue!=null)
					{
					this._strQuery ="select TXT_EMPLOYEE_NAME,TXT_HR_REF_NO,PRIM_SUBVERT,PRIM_VERT from(select Distinct TXT_EMPLOYEE_NAME as TXT_EMPLOYEE_NAME,TXT_HR_REF_NO as TXT_HR_REF_NO,PRIM_SUBVERT as PRIM_SUBVERT,PRIM_VERT as PRIM_VERT from MV_CENTRAL_EMPLOYEE WHERE UPPER(TXT_EMPLOYEE_NAME) like UPPER(N'"+fieldValue+"%') and TXT_HR_REF_NO >N'" + str1 +"' ORDER BY TXT_HR_REF_NO) where ROWNUM <= " + this._iBatchSize;
					}
					else
					{
						this._strQuery ="select TXT_EMPLOYEE_NAME,TXT_HR_REF_NO,PRIM_SUBVERT,PRIM_VERT from(select Distinct TXT_EMPLOYEE_NAME as TXT_EMPLOYEE_NAME,TXT_HR_REF_NO as TXT_HR_REF_NO,PRIM_SUBVERT as PRIM_SUBVERT,PRIM_VERT as PRIM_VERT from MV_CENTRAL_EMPLOYEE WHERE TXT_HR_REF_NO >N'" + str1 +"' ORDER BY TXT_HR_REF_NO) where ROWNUM <= " + this._iBatchSize;
					}
					////// System.out.println("ICICILOMBARD_HT_SM_NAME Next"+_strQuery);					
				}
				else
				{
					fieldValue=localNGPickList.getSearchFilterValue();
					_iTotalRecord=getTotalRecord(fieldValue);
					colCount=2;
					str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 1);
					if(fieldValue!=null)
					{
						this._strQuery ="select TXT_EMPLOYEE_NAME,TXT_HR_REF_NO from(select Distinct me.TXT_EMPLOYEE_NAME as TXT_EMPLOYEE_NAME,me.TXT_HR_REF_NO as TXT_HR_REF_NO from MV_GENMST_EMPLOYEE me, MV_GEN_DEAL_DETAIL GD WHERE GD.NUM_MO_EMPLOYEE_NO= me.num_employee_cd and GD.txt_deal_id='"+_objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_NO")+"' and  UPPER(ME.TXT_EMPLOYEE_NAME)  like UPPER(N'"+fieldValue+"%') and TXT_HR_REF_NO >N'" + str1 +"' ORDER BY me.TXT_HR_REF_NO) where ROWNUM <= " + this._iBatchSize;
					}
					else
					{
						this._strQuery ="select TXT_EMPLOYEE_NAME,TXT_HR_REF_NO from(select Distinct me.TXT_EMPLOYEE_NAME as TXT_EMPLOYEE_NAME,me.TXT_HR_REF_NO as TXT_HR_REF_NO from MV_GENMST_EMPLOYEE me, MV_GEN_DEAL_DETAIL GD WHERE GD.NUM_MO_EMPLOYEE_NO= me.num_employee_cd and GD.txt_deal_id='"+_objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_NO")+"' and TXT_HR_REF_NO >N'" + str1 +"' ORDER BY me.TXT_HR_REF_NO) where ROWNUM <= " + this._iBatchSize;
					}	
				}
			}			
			/********************** End MO filteration for Centralised Deal Vertical   **********************/

			
			//NEXT BANK NAME
			
			else if ((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_BANK_NAME")))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=2;
				str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
				if(fieldValue!=null)
				{
					  this._strQuery ="select txtbankname,TXTBANKCODE from(select DISTINCT txtbankname,TXTBANKCODE from NG_ICICI_MST_BankName WHERE UPPER(txtbankname)  like UPPER(N'%"+fieldValue+"%') and txtbankname >N'" + str1 +"' ORDER BY txtbankname) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					 this._strQuery ="select txtbankname,TXTBANKCODE from(select DISTINCT txtbankname,TXTBANKCODE from NG_ICICI_MST_BankName WHERE txtbankname >N'" + str1 +"' ORDER BY txtbankname) where ROWNUM <= " + this._iBatchSize;
				}	
			}
			
			//sub product next
			else if ((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_SUB_PRODUCT")))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=2;
				str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
				////// System.out.println("str1 4 button next "+str1);
				
				/*if (_objFormObject.getNGValue("ICICILOMBARD_HT_PRODUCT_CODE").equalsIgnoreCase("") || _objFormObject.getNGValue("ICICILOMBARD_HT_PRODUCT_CODE").equalsIgnoreCase(null))
				{
			_objFormObject.setNGValue("ICICILOMBARD_HT_PRODUCT_CODE",_objFormObject.getNGItemText("PRODUCT_HIDDEN",1));
			////// System.out.println("ICICILOMBARD_HT_SUB_PRODUCT query "+_objFormObject.getNGValue("ICICILOMBARD_HT_PRODUCT_CODE"));
				}*/
				//setProductCode();

				/*if(fieldValue!=null)
				{
					this._strQuery ="select TXT_IL_SUB_PRODUCT_NAME,TXT_IL_PRODUCT_NO_SUFIX from(select DISTINCT TXT_IL_SUB_PRODUCT_NAME,TXT_IL_PRODUCT_NO_SUFIX from MV_UW_SUB_PRODUCT_MASTER where num_IL_product_code='"+_objFormObject.getNGValue("ICICILOMBARD_HT_PRODUCT_CODE")+"' and TXT_IL_SUB_PRODUCT_NAME >N'" + str1 +"' and UPPER(TXT_IL_SUB_PRODUCT_NAME)  like UPPER(N'"+fieldValue+"%') ORDER BY TXT_IL_SUB_PRODUCT_NAME) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					this._strQuery ="select TXT_IL_SUB_PRODUCT_NAME,TXT_IL_PRODUCT_NO_SUFIX from(select DISTINCT TXT_IL_SUB_PRODUCT_NAME,TXT_IL_PRODUCT_NO_SUFIX from MV_UW_SUB_PRODUCT_MASTER where num_IL_product_code='"+_objFormObject.getNGValue("ICICILOMBARD_HT_PRODUCT_CODE")+"' and TXT_IL_SUB_PRODUCT_NAME >N'" + str1 +"' ORDER BY TXT_IL_SUB_PRODUCT_NAME) where ROWNUM <= " + this._iBatchSize;
				}
				////// System.out.println("ICICILOMBARD_HT_SUB_PRODUCT query "+_strQuery);*/
				
				//----------Made Changes by vishal/Yogendra to fetch sub product on basis of deal no-------
				////// System.out.println("str1 for next :"+str1);
				if(fieldValue == null || fieldValue.equalsIgnoreCase(""))
				{
					this._strQuery ="select TXT_IL_SUB_PRODUCT_NAME,TXT_IL_PRODUCT_NO_SUFIX from(select DISTINCT TXT_IL_SUB_PRODUCT_NAME,TXT_IL_PRODUCT_NO_SUFIX from  MV_UW_SUB_PRODUCT_MASTER a,MV_GEN_DEAL_DETAIL b,MV_UW_DEAL_PLAN_MAP c where a.NUM_IL_PRODUCT_CODE=b.NUM_PRODUCT_CODE and a.TXT_IL_SUB_PRODUCT_CODE=c.NUM_PLAN_CODE and b.TXT_DEAL_ID=c.TXT_DEAL_ID and b.TXT_DEAL_ID='"+ _objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_NO")+"' and TXT_IL_SUB_PRODUCT_NAME >N'" + str1 +"' ORDER BY TXT_IL_SUB_PRODUCT_NAME) where ROWNUM <= " + this._iBatchSize;
					////// System.out.println("vishal nxt, inside if :");
				}
				else
				{
					this._strQuery ="select TXT_IL_SUB_PRODUCT_NAME,TXT_IL_PRODUCT_NO_SUFIX from(select DISTINCT TXT_IL_SUB_PRODUCT_NAME,TXT_IL_PRODUCT_NO_SUFIX from  MV_UW_SUB_PRODUCT_MASTER a,MV_GEN_DEAL_DETAIL b,MV_UW_DEAL_PLAN_MAP c where a.NUM_IL_PRODUCT_CODE=b.NUM_PRODUCT_CODE and a.TXT_IL_SUB_PRODUCT_CODE=c.NUM_PLAN_CODE and b.TXT_DEAL_ID=c.TXT_DEAL_ID and b.TXT_DEAL_ID='"+ _objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_NO")+"' and TXT_IL_SUB_PRODUCT_NAME >N'" + str1 +"' and UPPER(TXT_IL_SUB_PRODUCT_NAME)  like UPPER(N'"+fieldValue+"%') ORDER BY TXT_IL_SUB_PRODUCT_NAME) where ROWNUM <= " + this._iBatchSize;
					////// System.out.println("vishal nxt, inside else :");
				}
				////// System.out.println("ICICILOMBARD_HT_SUB_PRODUCT query "+_strQuery);
				//-----------END of  sub product on basis of deal no----------------
			}
			
			
			//center code next
			else if ((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_CENTER_CODE_NAME")))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=1;
				str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
				if(fieldValue!=null)
				{
					this._strQuery ="select CENTCODE_NAME from(select DISTINCT CENTCODE_NAME from NG_ICICI_MST_CENTERCODE where CENTCODE_NAME >N'" + str1 +"' and UPPER(CENTCODE_NAME)  like UPPER(N'%"+fieldValue+"%') ORDER BY CENTCODE_NAME) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					this._strQuery ="select CENTCODE_NAME from(select DISTINCT CENTCODE_NAME from NG_ICICI_MST_CENTERCODE where CENTCODE_NAME >N'" + str1 +"' ORDER BY CENTCODE_NAME) where ROWNUM <= " + this._iBatchSize;
				}
			}

                
			/************************** CR-OF-MHT-1314-01 MHTProcess Implementaion Start***********/
			if((this.strSource != null) && (this.strSource.equals("MHTSEARCH_STRING")))
			{
				fieldValue=_objFormObject.getNGValue(this.strSource);
				////// System.out.println("btnNext_Clicked(): MHTSEARCH_STRING fieldValue: "+fieldValue);
				colCount=5;
				tempStr=_objFormObject.getNGValue("MHT_SEARCH_CRITERIA");
				
				if(tempStr.equals("Agent Name"))
				{
						Col="i.TXT_INTERMEDIARY_NAME";
				}
				else if (tempStr.equals("Agent Code"))
				{
						Col="i.TXT_INTERMEDIARY_CD";
						//Col="i.TXT_INTERMEDIARY_NAME";
				}
				else if (tempStr.equals("Deal No"))
				{
						Col="d.TXT_DEAL_ID";
						//Col="i.TXT_INTERMEDIARY_NAME";
				}
				else if (tempStr.equals("Manual CN"))
				{
						colCount=3;
						Col="txt_cust_covernote_no";
				}
				if(tempStr.equals("Deal Status"))
				{
						Col="d.TXT_DISPLAY_RM_BS";
						//Col="i.TXT_INTERMEDIARY_NAME";
				}
				////// System.out.println("btnNext_Clicked(): Col "+Col);
				

				if(fieldValue!=null && fieldValue.length() > 0)
				{
					////// System.out.println("btnNext_Clicked(): in if condition fieldValue "+fieldValue);
					if(Col.equalsIgnoreCase("txt_cust_covernote_no"))
					{
						str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
						this._strQuery = "select TXT_CUST_COVERNOTE_NO,TXT_COVERNOTE_DEAL_ID,YN_COVERNOTE_DEAL_ACCEPTANCE from (select DISTINCT TXT_CUST_COVERNOTE_NO,TXT_COVERNOTE_DEAL_ID,YN_COVERNOTE_DEAL_ACCEPTANCE from MV_MHT_OMNIFLOW_MANUAL_VIEW i where i.txt_cust_covernote_no is not null and UPPER("+Col+") like UPPER(N'%"+fieldValue+"%') and i.txt_cust_covernote_no >N'" + str1 +"' order by i.txt_cust_covernote_no) where ROWNUM <= " + this._iBatchSize;
					}
					else
					{
						str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 1);
						this._strQuery ="select TXT_INTERMEDIARY_NAME,TXT_INTERMEDIARY_CD, txt_deal_id,TXT_DISPLAY_RM_BS,TXT_COVERNOTE_FLAG from (select i.TXT_INTERMEDIARY_NAME as TXT_INTERMEDIARY_NAME,i.TXT_INTERMEDIARY_CD as TXT_INTERMEDIARY_CD, d.txt_deal_id as txt_deal_id,d.TXT_DISPLAY_RM_BS as TXT_DISPLAY_RM_BS,d.TXT_COVERNOTE_FLAG from mv_mht_genmst_intermediary i, mv_mht_gen_deal_detail d where d.TXT_INTERMEDIARY_CD=i.TXT_INTERMEDIARY_CD  and UPPER("+Col+") like UPPER(N'"+fieldValue+"%') and d.TXT_INTERMEDIARY_CD >N'" + str1 +"' ORDER BY i.TXT_INTERMEDIARY_CD) where ROWNUM <= " + this._iBatchSize;
					}
				}
				else
				{
					if(Col.equalsIgnoreCase("txt_cust_covernote_no"))
					{
						str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
						this._strQuery = "select TXT_CUST_COVERNOTE_NO,TXT_COVERNOTE_DEAL_ID,YN_COVERNOTE_DEAL_ACCEPTANCE from (select DISTINCT TXT_CUST_COVERNOTE_NO,TXT_COVERNOTE_DEAL_ID,YN_COVERNOTE_DEAL_ACCEPTANCE from MV_MHT_OMNIFLOW_MANUAL_VIEW i where i.txt_cust_covernote_no is not null and i.txt_cust_covernote_no >N'"+ str1 +"' order by i.txt_cust_covernote_no) where ROWNUM <= " + this._iBatchSize;
					}
					else
					{
						str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 1);
						this._strQuery ="select TXT_INTERMEDIARY_NAME,TXT_INTERMEDIARY_CD, txt_deal_id,TXT_DISPLAY_RM_BS,TXT_COVERNOTE_FLAG from (select i.TXT_INTERMEDIARY_NAME as TXT_INTERMEDIARY_NAME,i.TXT_INTERMEDIARY_CD as TXT_INTERMEDIARY_CD, d.txt_deal_id as txt_deal_id,d.TXT_DISPLAY_RM_BS as TXT_DISPLAY_RM_BS,d.TXT_COVERNOTE_FLAG from mv_mht_genmst_intermediary i, mv_mht_gen_deal_detail d where d.TXT_INTERMEDIARY_CD=i.TXT_INTERMEDIARY_CD and d.TXT_INTERMEDIARY_CD >N'" + str1 +"' ORDER BY i.TXT_INTERMEDIARY_CD) where ROWNUM <= " + this._iBatchSize;
					}
				}
				////// System.out.println("btnNext_Clicked(): QUERY "+_strQuery);
			}

			//il location next

			else if((this.strSource != null) && (this.strSource.equals("MHT_IL_LOCATION_CODE")))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=3;
				str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 1);

				if(!fieldValue.equalsIgnoreCase(""))
				{
					/*************************** MHT-PID Process Integration ****************************/
					//this._strQuery ="SELECT ILBRANCHNAME, ILBRANCHCODE FROM (select DISTINCT ILBRANCHNAME, ILBRANCHCODE  from NG_MHT_MST_ILLOCATION where ILBRANCHCODE >N'" + str1 +"' and UPPER(ILBRANCHNAME) like UPPER(N'"+fieldValue+"%') ORDER BY ILBRANCHCODE) where ROWNUM <= " + this._iBatchSize;
					this._strQuery ="SELECT ILBRANCHNAME, ILBRANCHCODE, ZONEAREA FROM (select DISTINCT ILBRANCHNAME, ILBRANCHCODE, ZONEAREA from NG_MHT_MST_ILLOCATION where ILBRANCHCODE >N'" + str1 +"' and UPPER(ILBRANCHCODE) like UPPER(N'"+fieldValue+"%') ORDER BY ILBRANCHCODE) where ROWNUM <= " + this._iBatchSize;
					/************************End MHT-PID Process Integration ****************************/
				}
				else
				{
					this._strQuery = "SELECT ILBRANCHNAME, ILBRANCHCODE, ZONEAREA FROM (select DISTINCT ILBRANCHNAME, ILBRANCHCODE, ZONEAREA from NG_MHT_MST_ILLOCATION where ILBRANCHCODE >N'" + str1 +"' ORDER BY ILBRANCHCODE) where ROWNUM <= " + this._iBatchSize;
				}
				////// System.out.println("MHT_IL_LOCATION_CODE****Next query ::" + this._strQuery);
			}
			
		/*************************** MHT-PID Process Integration ****************************/
		//Search provided on both location name and code	
			else if((this.strSource != null) && (this.strSource.equals("MHT_IL_LOCATION")))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=3;
				str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);

				if(!fieldValue.equalsIgnoreCase(""))
				{
					/*************************** MHT-PID Process Integration ****************************/
					//this._strQuery ="SELECT ILBRANCHNAME, ILBRANCHCODE FROM (select DISTINCT ILBRANCHNAME, ILBRANCHCODE  from NG_MHT_MST_ILLOCATION where ILBRANCHCODE >N'" + str1 +"' and UPPER(ILBRANCHNAME) like UPPER(N'"+fieldValue+"%') ORDER BY ILBRANCHCODE) where ROWNUM <= " + this._iBatchSize;
					this._strQuery ="SELECT ILBRANCHNAME, ILBRANCHCODE, ZONEAREA FROM (select DISTINCT ILBRANCHNAME, ILBRANCHCODE, ZONEAREA from NG_MHT_MST_ILLOCATION where ILBRANCHNAME >N'" + str1 +"' and UPPER(ILBRANCHNAME) like UPPER(N'"+fieldValue+"%') ORDER BY ILBRANCHNAME) where ROWNUM <= " + this._iBatchSize;
					/************************End MHT-PID Process Integration ****************************/
				}
				else
				{
					this._strQuery = "SELECT ILBRANCHNAME, ILBRANCHCODE, ZONEAREA FROM (select DISTINCT ILBRANCHNAME, ILBRANCHCODE, ZONEAREA from NG_MHT_MST_ILLOCATION where ILBRANCHNAME >N'" + str1 +"' ORDER BY ILBRANCHNAME) where ROWNUM <= " + this._iBatchSize;
				}
				////// System.out.println("MHT_IL_LOCATION****Next query ::" + this._strQuery);
			}
		//modified code to fetch vertical code also			
			else if((this.strSource != null) && (this.strSource.equals("MHT_PRIMARY_VERTICAL")))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=1;
				str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);

				if(fieldValue!=null)
				{
					this._strQuery ="SELECT prim_vert_name,prim_vert_code FROM (select DISTINCT prim_vert_name,prim_vert_code from ng_mht_mst_prim_vertical where prim_vert_name >N'" + str1 +"' and UPPER(prim_vert_name) like UPPER(N'"+fieldValue+"%') ORDER BY prim_vert_name) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					this._strQuery = "SELECT prim_vert_name,prim_vert_code FROM (select DISTINCT prim_vert_name,prim_vert_code from ng_mht_mst_prim_vertical where prim_vert_name >N'" + str1 +"' ORDER BY prim_vert_name) where ROWNUM <= " + this._iBatchSize;
				}
				////// System.out.println("MHT_PRIMARY_VERTICAL****Next query ::" + this._strQuery);
			}
			
			 else if((this.strSource != null) && (this.strSource.equals("MHT_SUB_VERTICAL")))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=1;
				str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);

				if(fieldValue!=null)
				{
					this._strQuery ="SELECT sec_vert_name,sec_vert_code FROM (select DISTINCT sec_vert_name,sec_vert_code from ng_mht_mst_sec_vertical where sec_vert_name >N'" + str1 +"' and UPPER(sec_vert_name) like UPPER(N'"+fieldValue+"%') ORDER BY sec_vert_name) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					this._strQuery = "SELECT sec_vert_name,sec_vert_code FROM (select DISTINCT sec_vert_name,sec_vert_code  from ng_mht_mst_sec_vertical where sec_vert_name >N'" + str1 +"' ORDER BY sec_vert_name) where ROWNUM <= " + this._iBatchSize;
				}
				////// System.out.println("MHT_SUB_VERTICAL****Next query ::" + this._strQuery);
			}
			/*********************** End MHT-PID Process Integration ****************************/
			
			else if((this.strSource != null) && (this.strSource.equals("MHT_PRODUCT_NAME")))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=2;
				str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 1);

				if(fieldValue!=null)
				{
					this._strQuery ="SELECT productname, productcode FROM (select DISTINCT productname, productcode  from ng_mht_mst_product where productcode >N'" + str1 +"' and UPPER(productname) like UPPER(N'"+fieldValue+"%') ORDER BY productcode) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					this._strQuery = "SELECT productname, productcode FROM (select DISTINCT productname, productcode  from ng_mht_mst_product where productcode >N'" + str1 +"' ORDER BY productcode) where ROWNUM <= " + this._iBatchSize;
				}
				////// System.out.println("MHT_PRODUCT_NAME****Next query ::" + this._strQuery);
			}


			//channel source
			else if((this.strSource != null) && (this.strSource.equalsIgnoreCase("MHT_SOURCE_BUSINESS")) && ((_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BBG") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking (BBG)") || _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BBG"))))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=1;
				str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
				if(fieldValue!=null)
				{
						this._strQuery ="SELECT SOURCEBUSINESS FROM (select DISTINCT(SOURCEBUSINESS) from NG_MHT_MST_BBG_BUSINESS where  SOURCEBUSINESS >N'" + str1 +"' and UPPER(SOURCEBUSINESS) like UPPER(N'"+fieldValue+"%') ORDER BY SOURCEBUSINESS) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{
						this._strQuery ="SELECT SOURCEBUSINESS FROM (select DISTINCT(SOURCEBUSINESS) from NG_MHT_MST_BBG_BUSINESS where  SOURCEBUSINESS >N'" + str1 +"' ORDER BY SOURCEBUSINESS) where ROWNUM <= " + this._iBatchSize;
				}

			}

			else if((this.strSource != null) && (this.strSource.equalsIgnoreCase("MHT_SOURCE_BUSINESS")) && (_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") ||
			_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("KRG")))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=1;
				str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
				if(fieldValue!=null)
				{

					this._strQuery = "select TXTSOURCEBUSINESS from (select DISTINCT(TXTSOURCEBUSINESS) from NG_MHT_MST_KRG where TXTSOURCEBUSINESS >N'" + str1 +"' and UPPER(TXTSOURCEBUSINESS) like UPPER(N'"+fieldValue+"%') and TXTSOURCEBUSINESS is not NULL ORDER BY TXTSOURCEBUSINESS) where  ROWNUM <= " + this._iBatchSize;
				}
				else
				{

					this._strQuery = "select TXTSOURCEBUSINESS from (select DISTINCT(TXTSOURCEBUSINESS) from NG_MHT_MST_KRG where TXTSOURCEBUSINESS >N'" + str1 +"' and TXTSOURCEBUSINESS is not NULL ORDER BY TXTSOURCEBUSINESS) where  ROWNUM <= " + this._iBatchSize;
				}
			}

			//channel; source
			else if((this.strSource != null) && (this.strSource.equalsIgnoreCase("MHT_CHANNEL_SOURCE")))
			{
				////// System.out.println(" MHT_CHANNEL_SOURCE Loading start==");
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=1;
				str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
				////// System.out.println(" MHT_CHANNEL_SOURCE Loading fieldValue=="+fieldValue);
				if(fieldValue!=null)
				{

					this._strQuery = "select CHANNELSOURCING from(select DISTINCT(CHANNELSOURCING) from NG_MHT_MST_BBG_SOURCE where CHANNELSOURCING >N'" + str1 +"' and UPPER(CHANNELSOURCING) like UPPER(N'"+fieldValue+"%') ORDER BY CHANNELSOURCING) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{

					this._strQuery = "select CHANNELSOURCING from(select DISTINCT(CHANNELSOURCING) from NG_MHT_MST_BBG_SOURCE where CHANNELSOURCING >N'" + str1 +"' ORDER BY CHANNELSOURCING) where ROWNUM <= " + this._iBatchSize;
				}
				
				 ////// System.out.println("MHT_CHANNEL_SOURCE****Loading ::" + this._strQuery);
			}

			/**************************************************************Start MHT SP Code CR-8127-69652 Next************************************************/
			//for bank branch name next
			else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("MHT_BRANCH_ID")))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				//// System.out.println("MHT_BRANCH_ID next button click =="+ fieldValue);
				_iTotalRecord=getTotalRecord(fieldValue);

				//// System.out.println("MHT_BRANCH_ID next button _iTotalRecord =="+ _iTotalRecord);
				
				if(_objFormObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Policy Issuance") && _objFormObject.getNGValue("MHT_TYPE_OF_BUSINESS").equalsIgnoreCase("Intermediary") && _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BANCASSURANCE") && !_objFormObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER") && _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING"))
				{
					
					colCount=2;
					str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
					//not required for home
					String channel_source=_objFormObject.getNGValue("MHT_CHANNEL_SOURCE");
					String deal_il_location=_objFormObject.getNGValue("MHT_DEAL_IL_LOCATION");
					if(fieldValue!=null)
					{
						this._strQuery = "select SOL_ID,BANK_BRANCH_NAME from(select distinct SOL_ID,BANK_BRANCH_NAME from NG_MHT_MST_SP_CODE where CHANNEL_SOURCE ='"+channel_source+"' and DEAL_IL_LOCATION='"+deal_il_location+"' and BANK_BRANCH_NAME >N'" + str1 +"' and UPPER(BANK_BRANCH_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY BANK_BRANCH_NAME) where ROWNUM <= " + this._iBatchSize;
					}
					else
					{
						this._strQuery = "select SOL_ID,BANK_BRANCH_NAME from(select distinct SOL_ID,BANK_BRANCH_NAME from NG_MHT_MST_SP_CODE where CHANNEL_SOURCE ='"+channel_source+"' and DEAL_IL_LOCATION='"+deal_il_location+"' and BANK_BRANCH_NAME >N'" + str1 +"' ORDER BY BANK_BRANCH_NAME) where ROWNUM <= " + this._iBatchSize;
					}
					////// System.out.println("MHT_BANK_BRANCH_NAME****Next query ::" + this._strQuery);
				}
				else if(_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BBG") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking (BBG)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("KRG") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("HOME") || (_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BBG") && _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BBG")))
				{
					colCount=4;
				    str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
					if(fieldValue!=null)
					{
						// this._strQuery = "select BBGBRANCHNAME from(select distinct(BBGBRANCHNAME) from NG_MHT_MST_BBG_HOMEBRANCH where BBGBRANCHNAME >N'" + str1 +"' and UPPER(BBGBRANCHNAME) like UPPER(N'"+fieldValue+"%') ORDER BY BBGBRANCHNAME) where ROWNUM <= " + this._iBatchSize;
						 this._strQuery = "select  branch_id,branch_name,sp_code,sp_name from (select distinct branch_id,branch_name,sp_code,sp_name from NG_MHT_MST_BBGANDHOMEBRANCH where branch_id >N'" + str1 +"' and UPPER(branch_id) like UPPER(N'"+fieldValue+"%') ORDER BY branch_id) where ROWNUM <= " + this._iBatchSize;
					}
					else
					{
						// this._strQuery = "select branch_id,branch_name,sp_code,sp_name from(select distinctbranch_id,branch_name,sp_code,sp_name from NG_MHT_MST_BBG_HOMEBRANCH where BBGBRANCHNAME >N'" + str1 +"' ORDER BY BBGBRANCHNAME) where ROWNUM <= " + this._iBatchSize;
						this._strQuery = "select branch_id,branch_name,sp_code,sp_name from(select distinctbranch_id,branch_name,sp_code,sp_name from NG_MHT_MST_BBGANDHOMEBRANCH where branch_id >N'" + str1 +"' ORDER BY branch_id) where ROWNUM <= " + this._iBatchSize;
					}
				}
				// System.out.println("MHT_BANK_BRANCH_NAME****Next query ::" + this._strQuery);
				
			}
			//for sol id next
			else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("MHT_BANK_BRANCH_NAME")))
			{
				if(_objFormObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Policy Issuance") && _objFormObject.getNGValue("MHT_TYPE_OF_BUSINESS").equalsIgnoreCase("Intermediary") && _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BANCASSURANCE") && !_objFormObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER") && _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING"))
				{
					fieldValue=localNGPickList.getSearchFilterValue();
					_iTotalRecord=getTotalRecord(fieldValue);
					colCount=2;
					str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 1);
					String channel_source=_objFormObject.getNGValue("MHT_CHANNEL_SOURCE");
					String deal_il_location=_objFormObject.getNGValue("MHT_DEAL_IL_LOCATION");
					if(fieldValue!=null)
					{
						this._strQuery = "select BANK_BRANCH_NAME,SOL_ID from(select distinct BANK_BRANCH_NAME,SOL_ID from NG_MHT_MST_SP_CODE where CHANNEL_SOURCE ='"+channel_source+"' and DEAL_IL_LOCATION='"+deal_il_location+"' and SOL_ID >N'" + str1 +"' and UPPER(SOL_ID) like UPPER(N'"+fieldValue+"%') ORDER BY SOL_ID) where ROWNUM <= " + this._iBatchSize;
					}
					else
					{
						this._strQuery = "select BANK_BRANCH_NAME,SOL_ID from(select distinct BANK_BRANCH_NAME,SOL_ID from NG_MHT_MST_SP_CODE where CHANNEL_SOURCE ='"+channel_source+"' and DEAL_IL_LOCATION='"+deal_il_location+"' and SOL_ID >N'" + str1 +"' ORDER BY SOL_ID) where ROWNUM <= " + this._iBatchSize;
					}
					////// System.out.println("MHT_BRANCH_ID****Next query ::" + this._strQuery);
				}

			}
			//for sp code next
			else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("MHT_SP_CODE")))
			{
				if(_objFormObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Policy Issuance") && _objFormObject.getNGValue("MHT_TYPE_OF_BUSINESS").equalsIgnoreCase("Intermediary") && _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BANCASSURANCE") && !_objFormObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER") && _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING"))
				{
					fieldValue=localNGPickList.getSearchFilterValue();
					_iTotalRecord=getTotalRecord(fieldValue);
					colCount=3;
					str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
					String deal_il_location=_objFormObject.getNGValue("MHT_DEAL_IL_LOCATION");
					String sol_id=_objFormObject.getNGValue("MHT_BRANCH_ID");
					String channel_source=_objFormObject.getNGValue("MHT_CHANNEL_SOURCE");
					if(fieldValue!=null)
					{
						this._strQuery = "select SP_CODE,SP_NAME,SP_PAN from(select distinct SP_CODE,SP_NAME,SP_PAN from NG_MHT_MST_SP_CODE where DEAL_IL_LOCATION='"+deal_il_location+"'and CHANNEL_SOURCE ='"+channel_source+"' and SOL_ID='"+sol_id+"' and SP_CODE >N'" + str1 +"' and UPPER(SP_CODE) like UPPER(N'"+fieldValue+"%') ORDER BY SP_CODE) where ROWNUM <= " + this._iBatchSize;
					}
					else
					{
						this._strQuery = "select SP_CODE,SP_NAME,SP_PAN from(select distinct SP_CODE,SP_NAME,SP_PAN from NG_MHT_MST_SP_CODE where DEAL_IL_LOCATION='"+deal_il_location+"'and CHANNEL_SOURCE ='"+channel_source+"' and SOL_ID='"+sol_id+"' and SP_CODE >N'" + str1 +"' ORDER BY SP_CODE) where ROWNUM <= " + this._iBatchSize;
					}
					////// System.out.println("MHT_SP_CODE****Next query ::" + this._strQuery);
				}
				/**********Start SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI ********/
				else
				{
					if(!((_objFormObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Policy Issuance") && _objFormObject.getNGValue("MHT_TYPE_OF_BUSINESS").equalsIgnoreCase("Direct"))) && (_objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BANCASSURANCE") || _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BA")) && (_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("KRG")) && !(_objFormObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER")))
					{
						// System.out.println("Inside the next sp code logic change");
						fieldValue=localNGPickList.getSearchFilterValue();
						_iTotalRecord=getTotalRecord(fieldValue);
						colCount=2;
						str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);//see
						if(fieldValue!=null)
						{
							// System.out.println("Inside the if Next sp code: Yogesh");
							this._strQuery = "select SP_CODE,SP_NAME from(select distinct SP_CODE,SP_NAME from NG_MHT_SP_CODE_KRG where SP_CODE >N'" + str1 +"' and UPPER(SP_CODE) like UPPER(N'"+fieldValue+"%') ORDER BY SP_CODE) where ROWNUM <= " + this._iBatchSize;
						}
						else
						{
							// System.out.println("Inside the else Next sp code: Yogesh");
							this._strQuery = "select SP_CODE,SP_NAME from(select distinct SP_CODE,SP_NAME from NG_MHT_SP_CODE_KRG where SP_CODE >N'" + str1 +"' ORDER BY SP_CODE) where ROWNUM <= " + this._iBatchSize;
						}
						// System.out.println("MHT_SP_CODE****Next query ::" + this._strQuery);
					}
				}
				/************End SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI **************/
			}
			/**************************************************************End MHT SP Code CR-8127-69652 Next************************************************/
			/******Start MHT CR-8127-95325-GST-Omniflow development******/
			//MHT_TXTGST_STATE_NAME next
			else if((this.strSource != null) && (this.strSource.equals("MHT_TXTGST_STATE_NAME")) && (_objFormObject.getNGValue("MHT_GST_REGISTERED").equalsIgnoreCase("Yes")) && !(_objFormObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER")))
			{
				// System.out.println("Inside the Next GST State: Yogesh");//select a.txtstatename,a.txtstatecode from NG_MHT_MST_GST_STATE a
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=1;
				str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);

				if(fieldValue!=null)
				{
					this._strQuery ="select txtstatename from NG_MHT_MST_GST_STATE where txtstatename >N'" + str1 +"' and UPPER(txtstatename) like UPPER(N'"+fieldValue+"%') and ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					this._strQuery = "select txtstatename from NG_MHT_MST_GST_STATE where txtstatename >N'" + str1 +"' and ROWNUM <= " + this._iBatchSize;
				}
				// System.out.println("MHT_TXTGST_STATE_NAME****Next query ::" + this._strQuery);
			}
			/******End MHT CR-8127-95325-GST-Omniflow development******/
			//BRANCH ID/UBO NAME NEXT
			else if ((this.strSource != null) && (this.strSource.equals("MHT_BRANCH_ID_UBO_NAME")))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=1;
				str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
				if(_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") ||
				_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("KRG"))
				{
					if(fieldValue!=null)
					{
						  this._strQuery = "select Txtbranchidubo from(select DISTINCT(Txtbranchidubo) from NG_MHT_MST_KRG where Txtbranchidubo >N'" + str1 +"' and UPPER(Txtbranchidubo) like UPPER(N'"+fieldValue+"%') ORDER BY Txtbranchidubo) where ROWNUM <= " + this._iBatchSize;
					}
					else
					{
						 this._strQuery = "select Txtbranchidubo from(select DISTINCT(Txtbranchidubo) from NG_MHT_MST_KRG where Txtbranchidubo >N'" + str1 +"' ORDER BY Txtbranchidubo) where ROWNUM <= " + this._iBatchSize;
					}
				////// System.out.println("ICICILOMBARD_HT_BRANCH_ID_UBO_NAME count3:"+this._strQuery);
				}
			////// System.out.println("ICICILOMBARD_HT_BRANCH_ID_UBO_NAME count4:"+this._strQuery);
			}

			//next sm id sm name
			else if ((this.strSource != null) && (this.strSource.equals("MHT_SM_NAME")))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=2;
				str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 1);
				if(fieldValue!=null)
				{
					/**************************** MHT-PID Process Integration ***************************/
					this._strQuery ="select PRIMARY_MO_NAME,TXT_HR_REF_NO from(select DISTINCT PRIMARY_MO_NAME, TXT_HR_REF_NO from MV_MHT_GENMST_EMPLOYEE where UPPER(PRIMARY_MO_NAME)  like UPPER(N'"+fieldValue+"%') and TXT_HR_REF_NO >N'" + str1 +"' ORDER BY TXT_HR_REF_NO) where ROWNUM <= " + this._iBatchSize;
					//this._strQuery ="select RM_NAME,EMP_CODE from(select me.RM_NAME as RM_NAME,me.EMP_CODE as EMP_CODE from NG_MHT_MST_RM me where UPPER(me.RM_NAME)  like UPPER(N'"+fieldValue+"%') and EMP_CODE >N'" + str1 +"' ORDER BY me.EMP_CODE) where ROWNUM <= " + this._iBatchSize;
					/************************ End MHT-PID Process Integration ***************************/
				}
				else
				{					
					/**************************** MHT-PID Process Integration ***************************/
					this._strQuery ="select PRIMARY_MO_NAME,TXT_HR_REF_NO from(select DISTINCT PRIMARY_MO_NAME, TXT_HR_REF_NO from MV_MHT_GENMST_EMPLOYEE where TXT_HR_REF_NO >N'" + str1 +"' ORDER BY TXT_HR_REF_NO) where ROWNUM <= " + this._iBatchSize;
					//this._strQuery ="select RM_NAME,EMP_CODE from(select me.RM_NAME as RM_NAME,me.EMP_CODE as EMP_CODE from NG_MHT_MST_RM me where EMP_CODE >N'" + str1 +"' ORDER BY me.EMP_CODE) where ROWNUM <= " + this._iBatchSize;
					/************************ End MHT-PID Process Integration ***************************/
				}
				////// System.out.println("alData--MV_CENTRAL_EMPLOYEE Next-->" + _strQuery);
			}
			

			//NEXT BANK NAME

			else if ((this.strSource != null) && (this.strSource.equals("MHT_PAYMENT_TYPE1_BANKNAME") || this.strSource.equals("MHT_PAYMENT_TYPE2_BANKNAME") || this.strSource.equals("MHT_PAYMENT_TYPE3_BANKNAME") ))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=2;
				str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
				if(fieldValue!=null)
				{
					  this._strQuery ="select txtbankname,TXTBANKCODE from(select DISTINCT txtbankname,TXTBANKCODE from NG_MHT_MST_BankName WHERE UPPER(txtbankname)  like UPPER(N'%"+fieldValue+"%') and txtbankname >N'" + str1 +"' ORDER BY txtbankname) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					 this._strQuery ="select txtbankname,TXTBANKCODE from(select DISTINCT txtbankname,TXTBANKCODE from NG_MHT_MST_BankName WHERE txtbankname >N'" + str1 +"' ORDER BY txtbankname) where ROWNUM <= " + this._iBatchSize;
				}
                ////// System.out.println("CLICK ON NEXT BUTTON--MHT_PAYMENT_TYPE1_BANKNAME-->" + this._strQuery);
			}
			
			/*************************** MHT -PID Process Integration ****************************/
			//NEXT PAYMENT_TYPE
			else if ((this.strSource != null) && (this.strSource.equals("MHT_PAYMENT_TYPE1") || this.strSource.equals("MHT_PAYMENT_TYPE2") || this.strSource.equals("MHT_PAYMENT_TYPE3") ))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=1;
				str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
				if(fieldValue!=null)
				{
					  this._strQuery ="select payment_mode from(select DISTINCT payment_mode from NG_MHT_PAYMENT_MODE_MASTER WHERE UPPER(payment_mode)  like UPPER(N'%"+fieldValue+"%') and payment_mode >N'" + str1 +"' ORDER BY payment_mode) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					 this._strQuery ="select payment_mode from(select DISTINCT payment_mode from NG_MHT_PAYMENT_MODE_MASTER WHERE payment_mode >N'" + str1 +"' ORDER BY payment_mode) where ROWNUM <= " + this._iBatchSize;
				}
                ////// System.out.println("CLICK ON NEXT BUTTON--MHT_PAYMENT_TYPE-->" + this._strQuery);
			}
			/************************* End MHT -PID Process Integration **************************/

			//center code next
			else if ((this.strSource != null) && (this.strSource.equals("MHT_CENTER_CODE_NAME")))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=1;
				str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);
				if(fieldValue!=null)
				{
					this._strQuery ="select CENTCODE_NAME from(select DISTINCT CENTCODE_NAME from NG_ICICI_MST_CENTERCODE where CENTCODE_NAME >N'" + str1 +"' and UPPER(CENTCODE_NAME)  like UPPER(N'%"+fieldValue+"%') ORDER BY CENTCODE_NAME) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					this._strQuery ="select CENTCODE_NAME from(select DISTINCT CENTCODE_NAME from NG_ICICI_MST_CENTERCODE where CENTCODE_NAME >N'" + str1 +"' ORDER BY CENTCODE_NAME) where ROWNUM <= " + this._iBatchSize;
				}
			}
            /************************** CR-OF-MHT-1314-01 MHTProcess Implementaion End***********/
			else{}
		
		 if (localNGPickList.populateData(this._strQuery, colCount))
		  {
			localNGPickList.enableButton("Previous", true); 
		  }

		  if (this._iBatchNo * this._iBatchSize > this. _iTotalRecord)
			localNGPickList.setStatus("Showing " + ((this._iBatchNo - 1) * this._iBatchSize + 1) + " - " + this. _iTotalRecord + " of " + this. _iTotalRecord);
		  else
			localNGPickList.setStatus("Showing " + ((this._iBatchNo - 1) * this._iBatchSize + 1) + " - " + (this._iBatchNo * this._iBatchSize) + " of " + this. _iTotalRecord);
		
	}
        


    //----------------------------------------------------------------------------------------------------
    //Function Name 			: btnPrev_Clicked
    //Date Written (DD/MM/YYYY)	: 14/04/2009
    //Author					: Vikas Tyagi
    //Input Parameters			: e
    //Output Parameters			: none
    //Return Values				: none
    //Description				: Hanldes ckisk event of Previous.
    //----------------------------------------------------------------------------------------------------
    // Changed By				        : Vikas Tyagi
    // Reason / Cause (Bug No if Any)	: FBD_7.2.4.0
    // Change  Description			    : Changes for unicode support.
    //----------------------------------------------------------------------------------------------------
     public void btnPrev_Clicked(NGEvent e)
    {
		String str1="";
		NGPickList localNGPickList = (NGPickList)e.getSource();
		this.objProcessData.setInputXML(this._objFormObject.getWFGeneralData());
		
		this._iBatchNo -= 1;
		String fieldValue="";
		
		
       
		 if (this._iBatchNo == 1)
		{
		  localNGPickList.enableButton("Previous", false);
		}
		
		localNGPickList.enableButton("Next", true);
		//============== CPI CR Quote SYS===============		
/*****Start CR-8001-87420-Green File Digitization*****/
		
		if((this.strSource != null) && (this.strSource.equals("CPI_RM_ID")))
		{
			//EMP_CODE, RM_NAME from NG_RM_MASTER
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			
			str1 = localNGPickList.getValueAt(1, 1);
			// System.out.println("1 str1"+str1);
			str1 = localNGPickList.getValueAt(0, 0);
			// System.out.println("2 str1"+str1);
			if(_objFormObject.getNGValue("CPI_IS_PF_FETCH").equalsIgnoreCase("Yes"))
			{
				colCount=6;	
				if(fieldValue!=null)
				{
					this._strQuery = "select TXT_HR_REF_NO,TXT_EMPLOYEE_NAME,PRIM_VERT,PRIM_VERT_CD,PRIM_SUBVERT,PRIM_SUBVERT_CD FROM (select distinct TXT_HR_REF_NO,TXT_EMPLOYEE_NAME,PRIM_VERT,PRIM_VERT_CD,PRIM_SUBVERT,PRIM_SUBVERT_CD from MV_CPI_VW_EMPLOYEE_OMNI where TXT_HR_REF_NO <N'"+str1+"' and UPPER(TXT_HR_REF_NO) like UPPER(N'"+fieldValue+"%')  ORDER BY TXT_HR_REF_NO desc ) where ROWNUM <= " + this._iBatchSize+" ORDER BY TXT_HR_REF_NO";
				}
				else
				{
					this._strQuery = "select TXT_HR_REF_NO,TXT_EMPLOYEE_NAME,PRIM_VERT,PRIM_VERT_CD,PRIM_SUBVERT,PRIM_SUBVERT_CD FROM (select distinct TXT_HR_REF_NO,TXT_EMPLOYEE_NAME,PRIM_VERT,PRIM_VERT_CD,PRIM_SUBVERT,PRIM_SUBVERT_CD from MV_CPI_VW_EMPLOYEE_OMNI where TXT_HR_REF_NO <N'"+str1+"'ORDER BY TXT_HR_REF_NO desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY TXT_HR_REF_NO";
				}
			}
			else
			{
				colCount=2;
				if(fieldValue!=null)
				{
					this._strQuery = "select EMP_CODE, RM_NAME FROM (select distinct EMP_CODE, RM_NAME from NG_RM_MASTER where EMP_CODE <N'"+str1+"' and UPPER(EMP_CODE) like UPPER(N'"+fieldValue+"%')  ORDER BY EMP_CODE desc ) where ROWNUM <= " + this._iBatchSize+" ORDER BY EMP_CODE";
				}
				else
				{
					this._strQuery = "select EMP_CODE, RM_NAME FROM (select distinct EMP_CODE, RM_NAME from NG_RM_MASTER where RM_NAME <N'"+str1+"'ORDER BY RM_NAME desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY RM_NAME";
				}
			}
			// System.out.println("2_PRE_strQuery"+_strQuery);
		}
		//sandeep yadav
		//============== End CPI CR Quote SYS===============
		
		
		//TXT_HR_REF_NO,TXT_EMPLOYEE_NAME
		if ((this.strSource != null) && (this.strSource.equals("CPI_ADDTNL_CIT_RM_ID") || this.strSource.equals("CPI_UW_EMP_ID") || this.strSource.equals("CPI_SECONDARY_MO_ID") || this.strSource.equalsIgnoreCase("CPI_RM_EMP_ID_SPG_IBG")))
		{
			// System.out.println(this.strSource+" btnPrev_Clicked");
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			colCount=2;	
			str1 = localNGPickList.getValueAt(1, 1);
			// System.out.println("1 str1"+str1);
			str1 = localNGPickList.getValueAt(0, 0);
			// System.out.println("2 str1"+str1);
			
			if(fieldValue!=null)
			{
				this._strQuery = "select TXT_HR_REF_NO,TXT_EMPLOYEE_NAME FROM (select distinct TXT_HR_REF_NO,TXT_EMPLOYEE_NAME from MV_CPI_VW_EMPLOYEE_OMNI where TXT_HR_REF_NO <N'"+str1+"' and UPPER(TXT_HR_REF_NO) like UPPER(N'"+fieldValue+"%')  ORDER BY TXT_HR_REF_NO desc ) where ROWNUM <= " + this._iBatchSize+" ORDER BY TXT_HR_REF_NO";
			}
			else
			{
				this._strQuery = "select TXT_HR_REF_NO,TXT_EMPLOYEE_NAME FROM (select distinct TXT_HR_REF_NO,TXT_EMPLOYEE_NAME from MV_CPI_VW_EMPLOYEE_OMNI where TXT_HR_REF_NO <N'"+str1+"'ORDER BY TXT_HR_REF_NO desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY TXT_HR_REF_NO";
			}
			// System.out.println("2_PRE_strQuery"+_strQuery);
		}
		//uw_emp_id 
		/*if ((this.strSource != null) && (this.strSource.equals("CPI_UW_EMP_ID")))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			colCount=2;	
			str1 = localNGPickList.getValueAt(1, 1);
			// System.out.println("1 str1"+str1);
			str1 = localNGPickList.getValueAt(0, 0);
			// System.out.println("2 str1"+str1);
			
			if(fieldValue!=null)
			{
				this._strQuery = "select TXT_HR_REF_NO,TXT_EMPLOYEE_NAME FROM (select distinct TXT_HR_REF_NO,TXT_EMPLOYEE_NAME from MV_CPI_VW_EMPLOYEE_OMNI where TXT_HR_REF_NO <N'"+str1+"' and UPPER(TXT_HR_REF_NO) like UPPER(N'"+fieldValue+"%')  ORDER BY TXT_HR_REF_NO desc ) where ROWNUM <= " + this._iBatchSize+" ORDER BY TXT_HR_REF_NO";
			}
			else
			{
				this._strQuery = "select TXT_HR_REF_NO,TXT_EMPLOYEE_NAME FROM (select distinct TXT_HR_REF_NO,TXT_EMPLOYEE_NAME from MV_CPI_VW_EMPLOYEE_OMNI where TXT_HR_REF_NO <N'"+str1+"'ORDER BY TXT_HR_REF_NO desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY TXT_HR_REF_NO";
			}
			// System.out.println("2_PRE_strQuery"+_strQuery);
		}*/
		//prev_policy_no
		if ((this.strSource != null) && (this.strSource.equals("CPI_PREV_POLICY_NO")))
		{
			// System.out.println(this.strSource+" btnPrev_Clicked");
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			colCount=3;	
			//str1 = localNGPickList.getValueAt(1, 1);
			//// System.out.println("1 str1"+str1);
			str1 = localNGPickList.getValueAt(0, 0);
			// System.out.println("2 str1"+str1);
			
			if(fieldValue!=null)
			{
				this._strQuery = "select TXT_POLICY_NO_CHAR,TXT_CUSTOMER_ID,TXT_CUSTOMER_NAME FROM (select GEN.TXT_POLICY_NO_CHAR,TXT_CUSTOMER_ID,TXT_CUSTOMER_NAME FROM GEN_PROP_INFORMATION_TAB@omnitopf GEN,UW_PRODUCT_MASTER@omnitopf UPM where GEN.NUM_PRODUCT_CODE = UPM.PRODUCTCODE and GEN.TXT_POLICY_NO_CHAR is not null and GEN.TXT_POLICY_NO_CHAR <N'"+str1+"' and GEN.TXT_POLICY_NO_CHAR='"+fieldValue+"' ORDER BY GEN.TXT_POLICY_NO_CHAR desc ) where ROWNUM <= " + this._iBatchSize+" ORDER BY TXT_POLICY_NO_CHAR";
			}
			else
			{
				this._strQuery = "select TXT_POLICY_NO_CHAR,TXT_CUSTOMER_ID,TXT_CUSTOMER_NAME FROM (select GEN.TXT_POLICY_NO_CHAR,TXT_CUSTOMER_ID,TXT_CUSTOMER_NAME FROM GEN_PROP_INFORMATION_TAB@omnitopf GEN,UW_PRODUCT_MASTER@omnitopf UPM where GEN.NUM_PRODUCT_CODE = UPM.PRODUCTCODE and GEN.TXT_POLICY_NO_CHAR is not null and GEN.TXT_POLICY_NO_CHAR <N'"+str1+"'ORDER BY GEN.TXT_POLICY_NO_CHAR desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY TXT_POLICY_NO_CHAR";
			}
			// System.out.println("2_PRE_strQuery"+_strQuery);
		}
		//base policy no
		if ((this.strSource != null) && (this.strSource.equals("CPI_POLICY_NUMBER_BASE")))
		{
			// System.out.println(this.strSource+" btnPrev_Clicked");
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			colCount=6;	
			//str1 = localNGPickList.getValueAt(1, 1);
			//// System.out.println("1 str1"+str1);
			str1 = localNGPickList.getValueAt(0, 0);
			// System.out.println("2 str1"+str1);
			
			if(fieldValue!=null)
			{
				this._strQuery = "select TXT_POLICY_NO_CHAR,DAT_POLICY_EFF_FROMDATE,TXT_CUSTOMER_ID,TXT_CUSTOMER_NAME,PRODUCTNAME,NUM_IL_PRODUCT_CODE FROM (select GEN.TXT_POLICY_NO_CHAR,DAT_POLICY_EFF_FROMDATE,TXT_CUSTOMER_ID,TXT_CUSTOMER_NAME,UPM.PRODUCTNAME,UPM.NUM_IL_PRODUCT_CODE from GEN_PROP_INFORMATION_TAB@omnitopf GEN,UW_PRODUCT_MASTER@omnitopf UPM where GEN.NUM_PRODUCT_CODE = UPM.PRODUCTCODE and GEN.TXT_POLICY_NO_CHAR is not null and GEN.TXT_POLICY_NO_CHAR <N'"+str1+"' and GEN.TXT_POLICY_NO_CHAR='"+fieldValue+"'  ORDER BY GEN.TXT_POLICY_NO_CHAR desc ) where ROWNUM <= " + this._iBatchSize+" ORDER BY TXT_POLICY_NO_CHAR";

			}
			else
			{
				this._strQuery = "select TXT_POLICY_NO_CHAR,DAT_POLICY_EFF_FROMDATE,TXT_CUSTOMER_ID,TXT_CUSTOMER_NAME,PRODUCTNAME,NUM_IL_PRODUCT_CODE FROM (select GEN.TXT_POLICY_NO_CHAR,DAT_POLICY_EFF_FROMDATE,TXT_CUSTOMER_ID,TXT_CUSTOMER_NAME,UPM.PRODUCTNAME,UPM.NUM_IL_PRODUCT_CODE from GEN_PROP_INFORMATION_TAB@omnitopf GEN,UW_PRODUCT_MASTER@omnitopf UPM where GEN.NUM_PRODUCT_CODE = UPM.PRODUCTCODE and GEN.TXT_POLICY_NO_CHAR is not null and GEN.TXT_POLICY_NO_CHAR <N'"+str1+"'ORDER BY GEN.TXT_POLICY_NO_CHAR desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY TXT_POLICY_NO_CHAR";

			}
			// System.out.println("2_PRE_strQuery"+_strQuery);
		}
		/*****End CR-8001-87420-Green File Digitization*****/

		/**************************************Client Registration CR Start*****************************************************/
		if((this.strSource != null) && (this.strSource.equals("CPI_POLICYNO_SEARCH")))
		{
			//POLICY_NUMBER, RM_NAME from NG_CPI_CLIENT_REG_MST
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			colCount=2;
			str1 = localNGPickList.getValueAt(0, 0);
			// System.out.println("2 str1"+str1);
			if(fieldValue!=null)
			{
				this._strQuery = "select POLICY_NUMBER,PRODUCT_NAME,CST_RM_EMP_ID,CST_RM_NAME,IL_LOCATION,PRIMARY_VERTICAL,PRIMARY_SUB_VERTICAL,CUSTOMER_ID,BROKER_AGENT_NAME,CLIENT_NAME,PAYMENT_MODE,CD_ACCOUNT_NUMBER,PRODUCT_IRDACODE,PRIMARY_VERTICAL_CODE,PRIMARY_SUB_VERTICAL_CODE FROM (select distinct POLICY_NUMBER,PRODUCT_NAME,CST_RM_EMP_ID,CST_RM_NAME,IL_LOCATION,PRIMARY_VERTICAL,PRIMARY_SUB_VERTICAL,CUSTOMER_ID,BROKER_AGENT_NAME,CLIENT_NAME,PAYMENT_MODE,CD_ACCOUNT_NUMBER,PRODUCT_IRDACODE,PRIMARY_VERTICAL_CODE,PRIMARY_SUB_VERTICAL_CODE from NG_CPI_CLIENT_REG_MST where POLICY_NUMBER <N'"+str1+"' and UPPER(POLICY_NUMBER) like UPPER(N'"+fieldValue+"%') AND UPPER(REGNO_STATUS) = 'APPROVED' ORDER BY POLICY_NUMBER desc ) where ROWNUM <= " + this._iBatchSize+" ORDER BY POLICY_NUMBER";
			}
			else
			{
				this._strQuery = "select POLICY_NUMBER,PRODUCT_NAME,CST_RM_EMP_ID,CST_RM_NAME,IL_LOCATION,PRIMARY_VERTICAL,PRIMARY_SUB_VERTICAL,CUSTOMER_ID,BROKER_AGENT_NAME,CLIENT_NAME,PAYMENT_MODE,CD_ACCOUNT_NUMBER,PRODUCT_IRDACODE,PRIMARY_VERTICAL_CODE,PRIMARY_SUB_VERTICAL_CODE FROM (select distinct POLICY_NUMBER,PRODUCT_NAME,CST_RM_EMP_ID,CST_RM_NAME,IL_LOCATION,PRIMARY_VERTICAL,PRIMARY_SUB_VERTICAL,CUSTOMER_ID,BROKER_AGENT_NAME,CLIENT_NAME,PAYMENT_MODE,CD_ACCOUNT_NUMBER,PRODUCT_IRDACODE,PRIMARY_VERTICAL_CODE,PRIMARY_SUB_VERTICAL_CODE from NG_CPI_CLIENT_REG_MST where RM_NAME <N'"+str1+"' AND UPPER(REGNO_STATUS) = 'APPROVED' ORDER BY RM_NAME desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY RM_NAME";
			}
			// System.out.println("2_PRE_strQuery"+_strQuery);
		}
		/**************************************Client Registration CR End*****************************************************/	
		/********************CR-OMCPI-1314-03 CPU DataWashing Start**********************/
		if((this.strSource != null) && (this.strSource.equals("CPI_CPU_ASSIGN_TO")))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			colCount=2;	
			str1 = localNGPickList.getValueAt(1, 1);
			////// System.out.println("1 str1"+str1);
			str1 = localNGPickList.getValueAt(0, 0);
			////// System.out.println("2 str1"+str1);
			/**** Start CR-8001-70893 Marine CR *******************************/
			String prodCategory="";
			prodCategory = _objFormObject.getWFActivityName().equalsIgnoreCase("CPU_Assignment")?"DW_Health":"Marine";
			
			if(fieldValue!=null)
			{
				//this._strQuery = "select EMP_ID, EMP_NAME FROM (select distinct EMP_ID, EMP_NAME from NG_CPI_DW_ESCALATION_MASTER where EMP_ID <N'"+str1+"' and UPPER(EMP_ID) like UPPER(N'"+fieldValue+"%')  ORDER BY EMP_ID desc ) where ROWNUM <= " + this._iBatchSize+" ORDER BY EMP_ID";
				this._strQuery = "select EMP_ID, EMP_NAME FROM (select distinct EMP_ID, EMP_NAME from NG_CPI_DW_ESCALATION_MASTER where prod_category = '"+prodCategory+"' and  EMP_ID <N'"+str1+"' and UPPER(EMP_ID) like UPPER(N'"+fieldValue+"%')  ORDER BY EMP_ID desc ) where ROWNUM <= " + this._iBatchSize+" ORDER BY EMP_ID";
			}
			else
			{
				//this._strQuery = "select EMP_ID, EMP_NAME FROM (select distinct EMP_ID, EMP_NAME from NG_CPI_DW_ESCALATION_MASTER where EMP_NAME <N'"+str1+"'ORDER BY EMP_NAME desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY EMP_NAME";
				this._strQuery = "select EMP_ID, EMP_NAME FROM (select distinct EMP_ID, EMP_NAME from NG_CPI_DW_ESCALATION_MASTER where prod_category = '"+prodCategory+"' and EMP_NAME <N'"+str1+"'ORDER BY EMP_NAME desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY EMP_NAME";
			}
			/**** ENd CR-8001-70893 Marine CR *********************************/
			////// System.out.println("2_PRE_strQuery"+_strQuery);
		}
		/********************CR-OMCPI-1314-03 CPU DataWashing END**********************/
		
		/********************* CR 45 Network Partner *****************************/
		if((this.strSource != null) && (this.strSource.equals("CPI_NETWORK_PARTNER_NAME")))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			colCount=2;	
			str1 = localNGPickList.getValueAt(0, 1);
			////// System.out.println("1 str1"+str1);
			str1 = localNGPickList.getValueAt(0, 0);
			////// System.out.println("2 str1"+str1);
			
			if(fieldValue!=null)
			{
				this._strQuery = "SELECT NETWORK_PARTNER_NAME FROM (select DISTINCT NETWORK_PARTNER_NAME from NG_CPI_NTWRK_PARTNER_MASTER where NETWORK_PARTNER_NAME <N'"+str1+"' and UPPER(NETWORK_PARTNER_NAME) like UPPER(N'"+fieldValue+"%')  ORDER BY NETWORK_PARTNER_NAME desc ) where ROWNUM <= " + this._iBatchSize+" ORDER BY NETWORK_PARTNER_NAME";
			}
			else
			{
				this._strQuery = "SELECT NETWORK_PARTNER_NAME FROM (select DISTINCT NETWORK_PARTNER_NAME from NG_CPI_NTWRK_PARTNER_MASTER where NETWORK_PARTNER_NAME <N'"+str1+"'ORDER BY NETWORK_PARTNER_NAME desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY NETWORK_PARTNER_NAME";
			}
			////// System.out.println("2_PRE_strQuery"+_strQuery);
		}
		/********************* End CR 45 Network Partner *************************/
		
		/******************  CO Insurance CR 18 *************************/
			//satish
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_RM_Name")))
		{
		
			//EMP_CODE, RM_NAME from NG_RM_MASTER
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			colCount=2;	
			str1 = localNGPickList.getValueAt(0, 1);
			////// System.out.println("1 str1"+str1);
			str1 = localNGPickList.getValueAt(0, 1);
			////// System.out.println("2 str1"+str1);
			
			if(fieldValue!=null)
			{
				this._strQuery ="SELECT DISTINCT RM_NAME,EMP_CODE  FROM (select DISTINCT RM_NAME, EMP_CODE   from NG_RM_MASTER where EMP_CODE <N'" + str1 +"' and UPPER(RM_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY EMP_CODE desc ) where ROWNUM <= " + this._iBatchSize +" ORDER BY EMP_CODE ";
				//this._strQuery = "SELECT RM_NAME,EMP_CODE  FROM (select DISTINCT EMP_CODE, RM_NAME from NG_RM_MASTER where EMP_CODE <'"+str1+"' and UPPER(EMP_CODE) like UPPER('"+fieldValue+"%')  ORDER BY EMP_CODE desc ) where ROWNUM <= " + this._iBatchSize+" ORDER BY EMP_CODE";
			}
			else
			{
			
			
			this._strQuery = "SELECT DISTINCT   RM_NAME,EMP_CODE  FROM (select DISTINCT RM_NAME, EMP_CODE  from NG_RM_MASTER where EMP_CODE <N'" + str1 +"' ORDER BY EMP_CODE) where ROWNUM <= " + this._iBatchSize +"ORDER BY RM_NAME desc";
				//this._strQuery = "SELECT RM_NAME,EMP_CODE  FROM (select DISTINCT EMP_CODE, RM_NAME from NG_RM_MASTER where RM_NAME <'"+str1+"'ORDER BY RM_NAME desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY RM_NAME";
			}
			////// System.out.println("2_PRE_strQuery1"+_strQuery);
		}
		//satish
		/******************End  CO Insurance CR 18 *************************/
			
		/********************* CR 28 by satish *****************************/
		//satish
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_NAME_OF_BROKER_AGENT")))
		{
		
			//AGENT_NAME, IBANKAGENT from ng_cpi_agent_master
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			//colCount=2;//PID_CPI process changes	
			colCount=3;	//PID_CPI process changes
			str1 = localNGPickList.getValueAt(0, 1);
			////// System.out.println("1 str1"+str1);
			str1 = localNGPickList.getValueAt(0, 0);
			////// System.out.println("2 str1"+str1);
			/*************************** PID-CPI process changes ***************************/
			if(fieldValue!=null)
			{
				//this._strQuery ="SELECT AGENT_NAME, IBANKAGENT  FROM (select DISTINCT AGENT_NAME, IBANKAGENT from ng_cpi_agent_master where AGENT_NAME <N'" + str1 +"' and UPPER(AGENT_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY AGENT_NAME desc ) where ROWNUM <= " + this._iBatchSize +" ORDER BY AGENT_NAME ";
				this._strQuery ="SELECT AGENT_NAME, IBANKAGENT,INTERMEDIARY_CODE  FROM (select DISTINCT AGENT_NAME, IBANKAGENT,INTERMEDIARY_CODE from ng_cpi_agent_master where AGENT_NAME <N'" + str1 +"' and UPPER(AGENT_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY AGENT_NAME desc ) where ROWNUM <= " + this._iBatchSize +" ORDER BY AGENT_NAME ";
			}
			else
			{
				//this._strQuery = "SELECT AGENT_NAME, IBANKAGENT FROM (select DISTINCT AGENT_NAME, IBANKAGENT from ng_cpi_agent_master where AGENT_NAME <N'" + str1 +"' ORDER BY AGENT_NAME) where ROWNUM <= " + this._iBatchSize +"ORDER BY AGENT_NAME desc";
				this._strQuery = "SELECT AGENT_NAME, IBANKAGENT FROM (select DISTINCT AGENT_NAME, IBANKAGENT from ng_cpi_agent_master where AGENT_NAME <N'" + str1 +"' ORDER BY AGENT_NAME) where ROWNUM <= " + this._iBatchSize +"ORDER BY AGENT_NAME desc";
			}
			/*********************** End PID-CPI process changes ***************************/
			////// System.out.println("2_PRE_strQuery1"+_strQuery);
		}
		//satish
		/********************** End of CR 28 ********************************/
				
		/************************ CPI IL Location****************/
	
		if((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_IL_Location"))||(this.strSource.equalsIgnoreCase("Corp_IL_Location"))||(this.strSource.equalsIgnoreCase("End_IL_Location"))))
		{
		
			//EMP_CODE, RM_NAME from NG_RM_MASTER
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			colCount=2;	
			str1 = localNGPickList.getValueAt(0, 1);
			////// System.out.println("1 str1"+str1);
			str1 = localNGPickList.getValueAt(0, 0);
			////// System.out.println("2 str1"+str1);
			
			////// System.out.println("field value"+fieldValue);
			if(fieldValue!=null)
			{
			
			////// System.out.println("inside block");
				this._strQuery ="SELECT   IL_LOCATION_VALUE  FROM (select   IL_LOCATION_VALUE   from NG_IL_LOCATION_MASTER where IL_LOCATION_VALUE <N'" + str1 +"' and UPPER(IL_LOCATION_VALUE) like UPPER(N'"+fieldValue+"%') ORDER BY IL_LOCATION_VALUE desc ) where ROWNUM <= " + this._iBatchSize +" ORDER BY IL_LOCATION_VALUE ";
				//this._strQuery = "SELECT RM_NAME,EMP_CODE  FROM (select   EMP_CODE, RM_NAME from NG_RM_MASTER where EMP_CODE <'"+str1+"' and UPPER(EMP_CODE) like UPPER('"+fieldValue+"%')  ORDER BY EMP_CODE desc ) where ROWNUM <= " + this._iBatchSize+" ORDER BY EMP_CODE";
			}
			else
			{
			
			
			this._strQuery = "SELECT   IL_LOCATION_VALUE  FROM (select   IL_LOCATION_VALUE   from NG_IL_LOCATION_MASTER where IL_LOCATION_VALUE <N'" + str1 +"' ORDER BY IL_LOCATION_VALUE) where ROWNUM <= " + this._iBatchSize +"ORDER BY IL_LOCATION_VALUE desc";
				//this._strQuery = "SELECT RM_NAME,EMP_CODE  FROM (select   EMP_CODE, RM_NAME from NG_RM_MASTER where RM_NAME <'"+str1+"'ORDER BY RM_NAME desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY RM_NAME";
			}
			////// System.out.println("2_PRE_strQuery1"+_strQuery);
		}
		
		/************************End  CPI IL Location****************/
		
		/*************************** PID-CPI process changes ***************************/
		/**************************** CPI moDE_OF_PAYMENT****************/
		if ((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_moDE_OF_PAYMENT"))||(this.strSource.equalsIgnoreCase("CPI_MODE_OF_PAYMENT2"))||(this.strSource.equalsIgnoreCase("CPI_MODE_OF_PAYMENT3"))))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			colCount=1;	
			str1 = localNGPickList.getValueAt(0, 0);
			////// System.out.println("str1"+str1);			
			////// System.out.println("field value"+fieldValue);
			if(fieldValue!=null)
			{
				////// System.out.println("inside block");
				this._strQuery ="SELECT MODE_OF_PAYMENT FROM (select MODE_OF_PAYMENT from NG_CPI_PAYMENT_MODE_MASTER where MODE_OF_PAYMENT <N'" + str1 +"' and UPPER(MODE_OF_PAYMENT) like UPPER(N'"+fieldValue+"%') ORDER BY MODE_OF_PAYMENT desc) where ROWNUM <= " + this._iBatchSize +" ORDER BY MODE_OF_PAYMENT ";
			}
			else
			{		
				this._strQuery = "SELECT MODE_OF_PAYMENT FROM (select MODE_OF_PAYMENT from NG_CPI_PAYMENT_MODE_MASTER where MODE_OF_PAYMENT <N'" + str1 +"' ORDER BY MODE_OF_PAYMENT) where ROWNUM <= " + this._iBatchSize +"ORDER BY MODE_OF_PAYMENT desc";
			}
			////// System.out.println("2_PRE_strQuery1"+_strQuery);
		}
		/************************End  CPI moDE_OF_PAYMENT****************/
		/*********************** End PID-CPI process changes ***************************/
		
		/**************************** CPI Hypothecated_to****************/
		if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_HYPOTHECATED_TO")))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			colCount=1;	
			str1 = localNGPickList.getValueAt(0, 0);
			////// System.out.println("str1"+str1);			
			////// System.out.println("field value"+fieldValue);
			if(fieldValue!=null)
			{
				////// System.out.println("inside if btnPrev_Clicked block");
				this._strQuery ="SELECT HYPOTHECATED_TO FROM (select DISTINCT HYPOTHECATED_TO from NG_CPI_HYPOTHECATED_MASTER where HYPOTHECATED_TO < N'" + str1 +"' and UPPER(HYPOTHECATED_TO) like UPPER(N'"+fieldValue+"%') ORDER BY HYPOTHECATED_TO desc) where ROWNUM <= " + this._iBatchSize +" ORDER BY  HYPOTHECATED_TO";
			}
			else
			{	
				////// System.out.println("inside else btnPrev_Clicked block");
				this._strQuery = "SELECT HYPOTHECATED_TO FROM (select DISTINCT HYPOTHECATED_TO from NG_CPI_HYPOTHECATED_MASTER where HYPOTHECATED_TO < N'" + str1 +"' ORDER BY HYPOTHECATED_TO desc) where ROWNUM <= " + this._iBatchSize +"ORDER BY HYPOTHECATED_TO";
			}
			////// System.out.println("2_PRE_strQuery1"+_strQuery);
		}
		/************************End  CPI Hypothecated_to****************/
		
		
		/************************* CPI URN CR 8001-61339 Multiple Changes CR *****************************/ 
		if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_Exception_To_MH")))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			colCount=1;	
			str1 = localNGPickList.getValueAt(0, 0);
			////// System.out.println("str1"+str1);			
			////// System.out.println("field value"+fieldValue);
			if(fieldValue!=null)
			{
				////// System.out.println("inside if btnPrev_Clicked block");
				this._strQuery ="SELECT MH_EXCEPTION_NAME FROM (select DISTINCT MH_EXCEPTION_NAME from NG_CPI_MHEXCEPTION_MASTER where MH_EXCEPTION_NAME < N'" + str1 +"' and UPPER(MH_EXCEPTION_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY MH_EXCEPTION_NAME desc) where ROWNUM <= " + this._iBatchSize +" ORDER BY  MH_EXCEPTION_NAME";
			}
			else
			{	
				////// System.out.println("inside else btnPrev_Clicked block");
				this._strQuery = "SELECT MH_EXCEPTION_NAME FROM (select DISTINCT MH_EXCEPTION_NAME from NG_CPI_MHEXCEPTION_MASTER where MH_EXCEPTION_NAME < N'" + str1 +"' ORDER BY MH_EXCEPTION_NAME desc) where ROWNUM <= " + this._iBatchSize +"ORDER BY MH_EXCEPTION_NAME";
			}
			////// System.out.println("2_PRE_strQuery1"+_strQuery);
		}
/*************************End CPI URN CR 8001-61339 Multiple Changes CR **************************/
		
		
		
		
		/************************ CPI_SPECIFIED_PERSON****************/
	
		if((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_SPECIFIED_PERSON"))  || (this.strSource.equalsIgnoreCase("Corp_SP_Name"))))
		{
		
			//EMP_CODE, RM_NAME from NG_RM_MASTER
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			colCount=2;	
			str1 = localNGPickList.getValueAt(0, 1);
			////// System.out.println("1 str1"+str1);
			str1 = localNGPickList.getValueAt(0, 0);
			////// System.out.println("2 str1"+str1);
			
			////// System.out.println("field value"+fieldValue);
			if(fieldValue!=null)
			{
			
			////// System.out.println("sandeep inside block");
				this._strQuery ="SELECT   SPECIFIED_PERSON  FROM (select   SPECIFIED_PERSON   from NG_CPI_SP_MASTER where SPECIFIED_PERSON <N'" + str1 +"' and UPPER(SPECIFIED_PERSON) like UPPER(N'"+fieldValue+"%') ORDER BY SPECIFIED_PERSON desc ) where ROWNUM <= " + this._iBatchSize +" ORDER BY SPECIFIED_PERSON ";
				//this._strQuery = "SELECT RM_NAME,EMP_CODE  FROM (select   EMP_CODE, RM_NAME from NG_RM_MASTER where EMP_CODE <'"+str1+"' and UPPER(EMP_CODE) like UPPER('"+fieldValue+"%')  ORDER BY EMP_CODE desc ) where ROWNUM <= " + this._iBatchSize+" ORDER BY EMP_CODE";
			}
			else
			{
			
			
			this._strQuery = "SSELECT   SPECIFIED_PERSON  FROM (select   SPECIFIED_PERSON   from NG_CPI_SP_MASTER where SPECIFIED_PERSON <N'" + str1 +"' ORDER BY SPECIFIED_PERSON) where ROWNUM <= " + this._iBatchSize +"ORDER BY SPECIFIED_PERSON desc";
				//this._strQuery = "SELECT RM_NAME,EMP_CODE  FROM (select   EMP_CODE, RM_NAME from NG_RM_MASTER where RM_NAME <'"+str1+"'ORDER BY RM_NAME desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY RM_NAME";
			}
			////// System.out.println("2_PRE_strQuery1"+_strQuery);
		}
		
		/************************End  CPI_SPECIFIED_PERSON****************/
		/************************ CPI_PRIMARY_VERTICAL****************/
	
		if((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_PRIMARY_VERTICAL"))))
		{
		
			//EMP_CODE, RM_NAME from NG_RM_MASTER
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			colCount=2;	
			str1 = localNGPickList.getValueAt(0, 1);
			////// System.out.println("1 str1"+str1);
			str1 = localNGPickList.getValueAt(0, 0);
			////// System.out.println("2 str1"+str1);
			////// System.out.println("inside btnPrevious");
			////// System.out.println("field value"+fieldValue);
			/*************************** PID-CPI process changes ***************************/
			if(fieldValue!=null)
			{
			
			////// System.out.println("inside block");
				//this._strQuery ="SELECT PRIMARY_VERTICAL_VALUE  FROM (select DISTINCT PRIMARY_VERTICAL_VALUE from PRIMARY_VERT_DETAILS_MAS where PRIMARY_VERTICAL_VALUE <N'" + str1 +"' and UPPER(PRIMARY_VERTICAL_VALUE) like UPPER(N'"+fieldValue+"%') ORDER BY PRIMARY_VERTICAL_VALUE desc ) where ROWNUM <= " + this._iBatchSize +" ORDER BY PRIMARY_VERTICAL_VALUE ";
				this._strQuery ="SELECT PRIMARY_VERTICAL_VALUE,PRIMARY_VERTICAL_CODE  FROM (select DISTINCT PRIMARY_VERTICAL_VALUE,PRIMARY_VERTICAL_CODE from PRIMARY_VERT_DETAILS_MAS where PRIMARY_VERTICAL_VALUE <N'" + str1 +"' and UPPER(PRIMARY_VERTICAL_VALUE) like UPPER(N'"+fieldValue+"%') ORDER BY PRIMARY_VERTICAL_VALUE desc ) where ROWNUM <= " + this._iBatchSize +" ORDER BY PRIMARY_VERTICAL_VALUE ";
				
			}
			else
			{
						
			//this._strQuery = "SELECT  PRIMARY_VERTICAL_VALUE  FROM (select DISTINCT PRIMARY_VERTICAL_VALUE from PRIMARY_VERT_DETAILS_MAS where PRIMARY_VERTICAL_VALUE <N'" + str1 +"' ORDER BY PRIMARY_VERTICAL_VALUE) where ROWNUM <= " + this._iBatchSize +"ORDER BY PRIMARY_VERTICAL_VALUE desc";
			this._strQuery = "SELECT  PRIMARY_VERTICAL_VALUE,PRIMARY_VERTICAL_CODE  FROM (select DISTINCT PRIMARY_VERTICAL_VALUE,PRIMARY_VERTICAL_CODE from PRIMARY_VERT_DETAILS_MAS where PRIMARY_VERTICAL_VALUE <N'" + str1 +"' ORDER BY PRIMARY_VERTICAL_VALUE) where ROWNUM <= " + this._iBatchSize +"ORDER BY PRIMARY_VERTICAL_VALUE desc";
				
			}
			/*********************** End PID-CPI process changes ***************************/
			////// System.out.println("2_PRE_strQuery1"+_strQuery);
		}
		
		/************************End  CPI_PRIMARY_VERTICAL****************/
		/************************ CPI_PRIMARY_SUB_VERTICAL****************/
	
		if((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_PRIMARY_SUB_VERTICAL"))))
		{
		
			//EMP_CODE, RM_NAME from NG_RM_MASTER
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			colCount=2;	
			str1 = localNGPickList.getValueAt(0, 1);
			////// System.out.println("1 str1"+str1);
			str1 = localNGPickList.getValueAt(0, 0);
			////// System.out.println("2 str1"+str1);
			
			////// System.out.println("field value"+fieldValue);
			/*************************** PID-CPI process changes ***************************/
			if(fieldValue!=null)
			{
				////// System.out.println("inside block");
				//this._strQuery ="SELECT   PRIMARY_SUB_VERTICAL_Value  FROM (select   DISTINCT PRIMARY_SUB_VERTICAL_Value   from PRIMARY_VERT_DETAILS_MAS where PRIMARY_SUB_VERTICAL_VALUE!=' ' and  PRIMARY_SUB_VERTICAL_Value <N'" + str1 +"' and UPPER(PRIMARY_SUB_VERTICAL_Value) like UPPER(N'"+fieldValue+"%') ORDER BY PRIMARY_SUB_VERTICAL_Value desc ) where ROWNUM <= " + this._iBatchSize +" ORDER BY PRIMARY_SUB_VERTICAL_Value ";
				this._strQuery ="SELECT   PRIMARY_SUB_VERTICAL_Value,prim_sub_vert_code  FROM (select   DISTINCT PRIMARY_SUB_VERTICAL_Value,prim_sub_vert_code   from PRIMARY_VERT_DETAILS_MAS where PRIMARY_SUB_VERTICAL_VALUE!=' ' and  PRIMARY_SUB_VERTICAL_Value <N'" + str1 +"' and UPPER(PRIMARY_SUB_VERTICAL_Value) like UPPER(N'"+fieldValue+"%') ORDER BY PRIMARY_SUB_VERTICAL_Value desc ) where ROWNUM <= " + this._iBatchSize +" ORDER BY PRIMARY_SUB_VERTICAL_Value ";
			}
			else
			{
				//this._strQuery = "SELECT   PRIMARY_SUB_VERTICAL_Value  FROM (select DISTINCT PRIMARY_SUB_VERTICAL_Value   from PRIMARY_VERT_DETAILS_MAS where PRIMARY_SUB_VERTICAL_VALUE!=' ' and  PRIMARY_SUB_VERTICAL_Value <N'" + str1 +"' ORDER BY PRIMARY_SUB_VERTICAL_Value) where ROWNUM <= " + this._iBatchSize +"ORDER BY PRIMARY_SUB_VERTICAL_Value desc";
				this._strQuery = "SELECT   PRIMARY_SUB_VERTICAL_Value,prim_sub_vert_code  FROM (select DISTINCT PRIMARY_SUB_VERTICAL_Value,prim_sub_vert_code   from PRIMARY_VERT_DETAILS_MAS where PRIMARY_SUB_VERTICAL_VALUE!=' ' and  PRIMARY_SUB_VERTICAL_Value <N'" + str1 +"' ORDER BY PRIMARY_SUB_VERTICAL_Value) where ROWNUM <= " + this._iBatchSize +"ORDER BY PRIMARY_SUB_VERTICAL_Value desc";
			}
			/*********************** End PID-CPI process changes ***************************/
			////// System.out.println("2_PRE_strQuery1"+_strQuery);
		}
		
		/************************End  CPI_PRIMARY_SUB_VERTICAL****************/
		/**** Start CR-8001-70893 Marine CR *******************************/
		if((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_SECONDARY_VERTICAL"))))
		{
		
			//EMP_CODE, RM_NAME from NG_RM_MASTER
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			colCount=2;	
			str1 = localNGPickList.getValueAt(0, 1);
			// System.out.println("1 str1"+str1);
			str1 = localNGPickList.getValueAt(0, 0);
			// System.out.println("2 str1"+str1);
			
			// System.out.println("field value"+fieldValue);
			if(fieldValue!=null)
			{
				// System.out.println("inside block");
				this._strQuery ="select SECONDRY_VERTICAL_VALUE  FROM (select distinct SECONDRY_VERTICAL_VALUE from SEC_VERT_DETAILS_MAS where SECONDRY_VERTICAL_VALUE!=' ' and  SECONDRY_VERTICAL_VALUE <N'" + str1 +"' and UPPER(SECONDRY_VERTICAL_VALUE) like UPPER(N'"+fieldValue+"%') ORDER BY SECONDRY_VERTICAL_VALUE desc ) where ROWNUM <= " + this._iBatchSize +" ORDER BY SECONDRY_VERTICAL_VALUE ";
			}
			else
			{
				this._strQuery = "select SECONDRY_VERTICAL_VALUE FROM (select distinct SECONDRY_VERTICAL_VALUE from SEC_VERT_DETAILS_MAS where SECONDRY_VERTICAL_VALUE!=' ' and  SECONDRY_VERTICAL_VALUE <N'" + str1 +"' ORDER BY SECONDRY_VERTICAL_VALUE) where ROWNUM <= " + this._iBatchSize +"ORDER BY SECONDRY_VERTICAL_VALUE desc";
			}
			// System.out.println("2_PRE_strQuery1"+_strQuery);
		}
		/**** ENd CR-8001-70893 Marine CR *********************************/
		/************************ CPI_SECONDARY_SUB_VERTICAL****************/
	
		if((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_SECONDARY_SUB_VERTICAL"))))
		{
		
			//EMP_CODE, RM_NAME from NG_RM_MASTER
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			colCount=2;	
			str1 = localNGPickList.getValueAt(0, 1);
			////// System.out.println("1 str1"+str1);
			str1 = localNGPickList.getValueAt(0, 0);
			////// System.out.println("2 str1"+str1);
			
			////// System.out.println("field value"+fieldValue);
			if(fieldValue!=null)
			{
				////// System.out.println("inside block");
				this._strQuery ="SELECT SECONDRY_SUB_VERTICAL_VALUE  FROM (select DISTINCT SECONDRY_SUB_VERTICAL_VALUE from SEC_VERT_DETAILS_MAS where SECONDRY_SUB_VERTICAL_VALUE!=' ' and  SECONDRY_SUB_VERTICAL_VALUE <N'" + str1 +"' and UPPER(SECONDRY_SUB_VERTICAL_VALUE) like UPPER(N'"+fieldValue+"%') ORDER BY SECONDRY_SUB_VERTICAL_VALUE desc ) where ROWNUM <= " + this._iBatchSize +" ORDER BY SECONDRY_SUB_VERTICAL_VALUE ";
			}
			else
			{
				this._strQuery = "SELECT SECONDRY_SUB_VERTICAL_VALUE FROM (select DISTINCT SECONDRY_SUB_VERTICAL_VALUE from SEC_VERT_DETAILS_MAS where SECONDRY_SUB_VERTICAL_VALUE!=' ' and  SECONDRY_SUB_VERTICAL_VALUE <N'" + str1 +"' ORDER BY SECONDRY_SUB_VERTICAL_VALUE) where ROWNUM <= " + this._iBatchSize +"ORDER BY SECONDRY_SUB_VERTICAL_VALUE desc";
			}
			////// System.out.println("2_PRE_strQuery1"+_strQuery);
		}
		
		/************************End  CPI_SECONDARY_SUB_VERTICAL****************/
		
		/************************ CPI_SOURCE_NAME****************/
	
		if((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_SOURCE_NAME")) || (this.strSource.equalsIgnoreCase("CORP_SOURCE_NAME"))))
		{
		
			//EMP_CODE, RM_NAME from NG_RM_MASTER
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			colCount=2;	
			str1 = localNGPickList.getValueAt(0, 1);
			////// System.out.println("1 str1"+str1);
			str1 = localNGPickList.getValueAt(0, 0);
			////// System.out.println("2 str1"+str1);
			
			////// System.out.println("field value"+fieldValue);
			if(fieldValue!=null)
			{
				////// System.out.println("inside block");
				this._strQuery ="SELECT  SOURCE  FROM (select  SOURCE   from NG_CPI_SOURCE_MASTER where SOURCE <N'" + str1 +"' and    primary_sub_vertical_id="+bBGKRGID1 +" and UPPER(SOURCE) like UPPER(N'"+fieldValue+"%') ORDER BY SOURCE desc ) where ROWNUM <= " + this._iBatchSize +"  ORDER BY SOURCE ";
				//this._strQuery = "SELECT RM_NAME,EMP_CODE  FROM (select   EMP_CODE, RM_NAME from NG_RM_MASTER where EMP_CODE <'"+str1+"' and UPPER(EMP_CODE) like UPPER('"+fieldValue+"%')  ORDER BY EMP_CODE desc ) where ROWNUM <= " + this._iBatchSize+" ORDER BY EMP_CODE";
			}
			else
			{
				this._strQuery = "SELECT  SOURCE  FROM (select  SOURCE   from NG_CPI_SOURCE_MASTER where SOURCE <N'" + str1 +"' ORDER BY SOURCE) where ROWNUM <= " + this._iBatchSize +" and    primary_sub_vertical_id="+bBGKRGID1  +"ORDER BY SOURCE desc";
				//this._strQuery = "SELECT RM_NAME,EMP_CODE  FROM (select   EMP_CODE, RM_NAME from NG_RM_MASTER where RM_NAME <'"+str1+"'ORDER BY RM_NAME desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY RM_NAME";
			}
			////// System.out.println("2_PRE_strQuery1"+_strQuery);
		}
		
		/************************End  CPI_SOURCE_NAME****************/
		/************************ CPI_CHANNEL_NAME****************/
	
		if((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_CHANNEL_NAME")) || (this.strSource.equalsIgnoreCase("CORP_CHANNEL_NAME"))))
		{
		
			//EMP_CODE, RM_NAME from NG_RM_MASTER
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			colCount=2;	
			str1 = localNGPickList.getValueAt(0, 1);
			////// System.out.println("1 str1"+str1);
			str1 = localNGPickList.getValueAt(0, 0);
			////// System.out.println("2 str1"+str1);
			
			////// System.out.println("field value"+fieldValue);
			if(fieldValue!=null)
			{
				if(BBGKRGVAL1.equalsIgnoreCase("BBG") || BBGKRGVAL1.equalsIgnoreCase("BRANCH BRANCHING")
				/***************************** CR 28 by satish *****************************/
				|| BBGKRGVAL1.equalsIgnoreCase("SEG") || BBGKRGVAL1.equalsIgnoreCase("NA")
				/*************************** End CR 28 by satish ***************************/
				 || BBGKRGVAL1.equalsIgnoreCase("COB") // CR-OMCPI-1314-02 FIG COB CR
				)
				{
					////// System.out.println("inside block");
					this._strQuery ="SELECT  CHANNEL  FROM (select  CHANNEL   from NG_CPI_CHANNEL_MASTER where CHANNEL <N'" + str1 +"' and    primary_sub_vertical_id="+bBGKRGID1+" and  UPPER(CHANNEL) like UPPER(N'"+fieldValue+"%') ORDER BY CHANNEL desc ) where ROWNUM <= " + this._iBatchSize +"  ORDER BY CHANNEL ";
					//this._strQuery = "SELECT RM_NAME,EMP_CODE  FROM (select   EMP_CODE, RM_NAME from NG_RM_MASTER where EMP_CODE <'"+str1+"' and UPPER(EMP_CODE) like UPPER('"+fieldValue+"%')  ORDER BY EMP_CODE desc ) where ROWNUM <= " + this._iBatchSize+" ORDER BY EMP_CODE";
				}
				else if(BBGKRGVAL1.equalsIgnoreCase("KRG")|| BBGKRGVAL1.equalsIgnoreCase("KEY RELATIONSHIP GROUP"))//CR-8001-87420 Green File Digitization)
				{
					this._strQuery ="select CHANNEL from (select  CHANNEL  from NG_CPI_SOURCE_CHANNEL_MASTER A,NG_CPI_SOURCE_MASTER B,NG_CPI_CHANNEL_MASTER C Where   CHANNEL <N'" + str1 +"' and UPPER(CHANNEL) like UPPER(N'"+fieldValue+"%') and B.Recordid=A.Source_ID and C.Recordid=A.Channel_ID  and A.SOURCE_ID="+sourceID1 +" ORDER BY CHANNEL desc )  where ROWNUM <= " + this._iBatchSize +" ORDER BY CHANNEL";
				
					//this._strQuery ="select CHANNEL from (select  CHANNEL  from NG_CPI_SOURCE_CHANNEL_MASTER A,NG_CPI_SOURCE_MASTER B,NG_CPI_CHANNEL_MASTER C Where   CHANNEL <N'" + str1 +"' and UPPER(CHANNEL) like UPPER(N'"+fieldValue+"%') and B.Recordid=A.Source_ID and C.Recordid=A.Channel_ID  and A.SOURCE_ID="+sourceID1 +" ORDER BY BRANCH desc )  where ROWNUM <= " + this._iBatchSize +" ORDER BY CHANNEL";
				}
			}
			else
			{
				if(BBGKRGVAL1.equalsIgnoreCase("BBG") || BBGKRGVAL1.equalsIgnoreCase("BRANCH BRANCHING")
				/***************************** CR 28 by satish *****************************/
				|| BBGKRGVAL1.equalsIgnoreCase("SEG") || BBGKRGVAL1.equalsIgnoreCase("NA")
				/*************************** End CR 28 by satish ***************************/
				)
				{
					this._strQuery = "SELECT  CHANNEL  FROM (select  CHANNEL   from NG_CPI_CHANNEL_MASTER where CHANNEL <N'" + str1 +"' ORDER BY CHANNEL) where ROWNUM <= " + this._iBatchSize +" and    primary_sub_vertical_id="+bBGKRGID1+"  ORDER BY CHANNEL desc";
			
					//this._strQuery = "SELECT RM_NAME,EMP_CODE  FROM (select   EMP_CODE, RM_NAME from NG_RM_MASTER where RM_NAME <'"+str1+"'ORDER BY RM_NAME desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY RM_NAME";
				}
				else if(BBGKRGVAL1.equalsIgnoreCase("KRG") || BBGKRGVAL1.equalsIgnoreCase("KEY RELATIONSHIP GROUP"))//CR-8001-87420 Green File Digitization)
				{
					//this._strQuery ="select CHANNEL from (select  CHANNEL  from NG_CPI_SOURCE_CHANNEL_MASTER A,NG_CPI_SOURCE_MASTER B,NG_CPI_CHANNEL_MASTER C Where   CHANNEL <N'" + str1 +"' and UPPER(CHANNEL) like UPPER(N'"+fieldValue+"%') and B.Recordid=A.Source_ID and C.Recordid=A.Channel_ID  and A.SOURCE_ID="+sourceID1 +" ORDER BY BRANCH desc )  where ROWNUM <= " + this._iBatchSize +" ORDER BY CHANNEL";
					this._strQuery = "select CHANNEL from NG_CPI_SOURCE_CHANNEL_MASTER A,NG_CPI_SOURCE_MASTER B,NG_CPI_CHANNEL_MASTER C  Where CHANNEL <N'" + str1 +"' and ROWNUM <= " + this._iBatchSize +"and B.Recordid=A.Source_ID and C.Recordid=A.Channel_ID and A.SOURCE_ID="+sourceID1;
				}
			}
			////// System.out.println("2_PRE_strQuery1"+_strQuery);
		}
		
		/************************End  CPI_CHANNEL_NAME****************/
		
			/************************ CPI_BRANCH_NAME****************/
	
		if((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_BRANCH_NAME")) || (this.strSource.equalsIgnoreCase("CORP_BRANCH_NAME"))))
		{
		
			//EMP_CODE, RM_NAME from NG_RM_MASTER
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			//colCount=2; //PID_CPI process changes
			colCount=10;	 //PID_CPI process changes
			str1 = localNGPickList.getValueAt(0, 1);
			////// System.out.println("1 str1"+str1);
			str1 = localNGPickList.getValueAt(0, 0);
			////// System.out.println("2 str1"+str1);
			
			////// System.out.println("field value"+fieldValue);
			if(fieldValue!=null)
			{
				////// System.out.println("inside block");
			
				if(BBGKRGVAL1.equalsIgnoreCase("BBG") || BBGKRGVAL1.equalsIgnoreCase("BRANCH BRANCHING")
				/***************************** CR 28 by satish *****************************/
				|| BBGKRGVAL1.equalsIgnoreCase("SEG") || BBGKRGVAL1.equalsIgnoreCase("NA")
				/*************************** End CR 28 by satish ***************************/
				 || BBGKRGVAL1.equalsIgnoreCase("COB") // CR-OMCPI-1314-02 FIG COB CR
				)
				{
					/*************************** PID-CPI process changes ***************************/
					//this._strQuery ="SELECT  BRANCH  FROM (select  BRANCH   from ng_cpi_branch_master where BRANCH <N'" + str1 +"' and UPPER(BRANCH) like UPPER(N'"+fieldValue+"%') and    primary_sub_vertical_id="+bBGKRGID1+" ORDER BY BRANCH desc ) where ROWNUM <= " + this._iBatchSize +" ORDER BY BRANCH ";
					this._strQuery ="SELECT  BRANCH,branch_id,sp_name1,sp_code1,sp_name2,sp_code2,sp_name3,sp_code3,sp_name4,sp_code4  FROM (select  BRANCH,branch_id,sp_name1,sp_code1,sp_name2,sp_code2,sp_name3,sp_code3,sp_name4,sp_code4   from ng_cpi_branch_master where BRANCH <N'" + str1 +"' and UPPER(BRANCH) like UPPER(N'"+fieldValue+"%') and    primary_sub_vertical_id="+bBGKRGID1+" ORDER BY BRANCH desc ) where ROWNUM <= " + this._iBatchSize +" ORDER BY BRANCH ";
					/*********************** End PID-CPI process changes ***************************/
					//this._strQuery = "SELECT RM_NAME,EMP_CODE  FROM (select   EMP_CODE, RM_NAME from NG_RM_MASTER where EMP_CODE <'"+str1+"' and UPPER(EMP_CODE) like UPPER('"+fieldValue+"%')  ORDER BY EMP_CODE desc ) where ROWNUM <= " + this._iBatchSize+" ORDER BY EMP_CODE";
				}
				else if(BBGKRGVAL1.equalsIgnoreCase("KRG") || BBGKRGVAL1.equalsIgnoreCase("KEY RELATIONSHIP GROUP"))//CR-8001-87420 Green File Digitization)
				{
					/*************************** PID-CPI process changes ***************************/
					//this._strQuery ="select BRANCH from (select  BRANCH  from NG_CPI_SOURCE_BRANCH_MASTER A,NG_CPI_SOURCE_MASTER B,NG_CPI_BRANCH_MASTER C Where   BRANCH <N'" + str1 +"' and UPPER(BRANCH) like UPPER(N'"+fieldValue+"%') and B.Recordid=A.Source_ID and C.Recordid=A.Branch_ID  and A.SOURCE_ID="+sourceID1 +" ORDER BY BRANCH desc )  where ROWNUM <= " + this._iBatchSize +" ORDER BY BRANCH";
					this._strQuery ="select BRANCH,branch_id,sp_name1,sp_code1,sp_name2,sp_code2,sp_name3,sp_code3,sp_name4,sp_code4 from (select  BRANCH,C.branch_id,sp_name1,sp_code1,sp_name2,sp_code2,sp_name3,sp_code3,sp_name4,sp_code4  from NG_CPI_SOURCE_BRANCH_MASTER A,NG_CPI_SOURCE_MASTER B,NG_CPI_BRANCH_MASTER C Where   BRANCH <N'" + str1 +"' and UPPER(BRANCH) like UPPER(N'"+fieldValue+"%') and B.Recordid=A.Source_ID and C.Recordid=A.Branch_ID  and A.SOURCE_ID="+sourceID1 +" ORDER BY BRANCH desc )  where ROWNUM <= " + this._iBatchSize +" ORDER BY BRANCH";
					/*********************** End PID-CPI process changes ***************************/
				}
			}
			else
			{
				if(BBGKRGVAL1.equalsIgnoreCase("BBG") || BBGKRGVAL1.equalsIgnoreCase("BRANCH BRANCHING")
				/***************************** CR 28 by satish *****************************/
				|| BBGKRGVAL1.equalsIgnoreCase("SEG") || BBGKRGVAL1.equalsIgnoreCase("NA")
				/*************************** End CR 28 by satish ***************************/
				)
				{
					/*************************** PID-CPI process changes ***************************/
					//this._strQuery = "SELECT  BRANCH  FROM (select  BRANCH   from ng_cpi_branch_master where BRANCH <N'" + str1 +"' ORDER BY BRANCH) where ROWNUM <= " + this._iBatchSize +" and    primary_sub_vertical_id="+bBGKRGID1+" ORDER BY BRANCH desc";
					this._strQuery = "SELECT  BRANCH,branch_id,sp_name1,sp_code1,sp_name2,sp_code2,sp_name3,sp_code3,sp_name4,sp_code4 FROM (select  BRANCH,branch_id,sp_name1,sp_code1,sp_name2,sp_code2,sp_name3,sp_code3,sp_name4,sp_code4   from ng_cpi_branch_master where BRANCH <N'" + str1 +"' ORDER BY BRANCH) where ROWNUM <= " + this._iBatchSize +" and    primary_sub_vertical_id="+bBGKRGID1+" ORDER BY BRANCH desc";
					
								
				}
				else if(BBGKRGVAL1.equalsIgnoreCase("KRG") || BBGKRGVAL1.equalsIgnoreCase("KEY RELATIONSHIP GROUP"))//CR-8001-87420 Green File Digitization)
				{
					//this._strQuery ="select BRANCH from (select branch from NG_CPI_SOURCE_BRANCH_MASTER A,NG_CPI_SOURCE_MASTER B,NG_CPI_BRANCH_MASTER C Where ROWNUM <= " + this._iBatchSize +" and BRANCH <N'" + str1 +"' and UPPER(BRANCH) like UPPER(N'"+fieldValue+"%') and B.Recordid=A.Source_ID and C.Recordid=A.Branch_ID  and A.SOURCE_ID="+sourceID1 +" ORDER BY BRANCH desc) where ROWNUM <= " + this._iBatchSize +" ORDER BY BRANCH ";
					this._strQuery ="select BRANCH,branch_id,sp_name1,sp_code1,sp_name2,sp_code2,sp_name3,sp_code3,sp_name4,sp_code4 from (select branch,C.branch_id,sp_name1,sp_code1,sp_name2,sp_code2,sp_name3,sp_code3,sp_name4,sp_code4 from NG_CPI_SOURCE_BRANCH_MASTER A,NG_CPI_SOURCE_MASTER B,NG_CPI_BRANCH_MASTER C Where ROWNUM <= " + this._iBatchSize +" and BRANCH <N'" + str1 +"' and UPPER(BRANCH) like UPPER(N'"+fieldValue+"%') and B.Recordid=A.Source_ID and C.Recordid=A.Branch_ID  and A.SOURCE_ID="+sourceID1 +" ORDER BY BRANCH desc) where ROWNUM <= " + this._iBatchSize +" ORDER BY BRANCH ";
					/*********************** End PID-CPI process changes ***************************/
				}
				
			}
			////// System.out.println("2_PRE_strQuery1"+_strQuery);
		}
		
		/************************End  CPI_BRANCH_NAME****************/
		
		/*****************  CPI_naME_OF_LEADER by satish for CR21 *********************/
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_naME_OF_LEADER")))
		{
			//EMP_CODE, RM_NAME from NG_RM_MASTER
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			colCount=2;	
			str1 = localNGPickList.getValueAt(0, 1);
			////// System.out.println("1 str1"+str1);
			str1 = localNGPickList.getValueAt(0, 0);
			////// System.out.println("2 str1"+str1);
			
			////// System.out.println("field value"+fieldValue);
			if(fieldValue!=null)
			{
				////// System.out.println("inside block");
				 this._strQuery = "select LEADER_NAME from(select LEADER_NAME from NG_CPI_LEADER_MST where LEADER_CATEGORY_TYPE_ID="+product_type_val+"  and LEADER_NAME <N'" + str1 +"' and UPPER(LEADER_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY LEADER_NAME desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY LEADER_NAME";
			}
			else
			{
				this._strQuery = "select LEADER_NAME from(select LEADER_NAME from NG_CPI_LEADER_MST where LEADER_CATEGORY_TYPE_ID="+product_type_val+"  and LEADER_NAME <N'" + str1 +"'  ORDER BY LEADER_NAME desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY LEADER_NAME";
			}
			////// System.out.println("2_PRE_strQuery1"+_strQuery);
		}		
		/***************** End CPI_naME_OF_LEADER by satish for CR21 *********************/
		
				/************************ Product Name****************/
	
		if((this.strSource != null) && ((this.strSource.equalsIgnoreCase("End_PRODUCT_NAME"))||(this.strSource.equalsIgnoreCase("Corp_PRODUCT_NAME"))||(this.strSource.equalsIgnoreCase("CPI_PRODUCT_NAME"))))
		{
			//EMP_CODE, RM_NAME from NG_RM_MASTER
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			colCount=2;	
			str1 = localNGPickList.getValueAt(0, 1);
			////// System.out.println("1 str1"+str1);
			str1 = localNGPickList.getValueAt(0, 0);
			////// System.out.println("2 str1"+str1);
			
			////// System.out.println("field value"+fieldValue);
			if(fieldValue!=null)
			{
				////// System.out.println("inside block");
				/***************** CR21 *********************/
				if((_objFormObject.getWFActivityName().equalsIgnoreCase("Co_Insurance")) || (_objFormObject.getWFActivityName().equalsIgnoreCase("Co_Insurance_RM")))
				{
					this._strQuery = "select PRODUCT_NAME,IRDACODE from(select PRODUCT_NAME,IRDACODE from NG_PRODUCT_MASTER where PRODUCT_CATEGORY_ID="+product_type_val+"  and PRODUCT_NAME <N'" + str1 +"' and UPPER(PRODUCT_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY PRODUCT_NAME desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY PRODUCT_NAME";//Vendor login & weather Product
				}
				/******************* CR 46 CPU DataWashing********************/
				else if(_objFormObject.getNGValue("CPI_DATAWASHING_TYPE").equalsIgnoreCase("Endorsement"))
				{
					////// System.out.println("7.CR 46 CPI_DATAWASHING_TYPE: "+_objFormObject.getNGValue("CPI_DATAWASHING_TYPE"));
					this._strQuery = "select PRODUCT_NAME,IRDACODE from(select PRODUCT_NAME,IRDACODE from NG_PRODUCT_MASTER where PRODUCT_TYPE_ID="+product_type_val+" AND PRODUCT_ROUTING = '2' AND PRODUCT_NAME <N'" + str1 +"' and UPPER(PRODUCT_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY PRODUCT_NAME desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY PRODUCT_NAME";//Vendor login & weather Product
				}/*****************end CR 46 CPU DataWashing*******************/
				else
				/*****************End CR21 *********************/
				{	
					this._strQuery = "select PRODUCT_NAME,IRDACODE from(select PRODUCT_NAME,IRDACODE from NG_PRODUCT_MASTER where PRODUCT_TYPE_ID="+product_type_val+"  and PRODUCT_NAME <N'" + str1 +"' and UPPER(PRODUCT_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY PRODUCT_NAME desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY PRODUCT_NAME";//Vendor login & weather Product
					}
			}
			else
			{
				/***************** CR21 *********************/
				if((_objFormObject.getWFActivityName().equalsIgnoreCase("Co_Insurance")) || (_objFormObject.getWFActivityName().equalsIgnoreCase("Co_Insurance_RM")))
				{
					this._strQuery = "select PRODUCT_NAME,IRDACODE from(select PRODUCT_NAME,IRDACODE from NG_PRODUCT_MASTER where PRODUCT_CATEGORY_ID="+product_type_val+"  and PRODUCT_NAME <N'" + str1 +"'  ORDER BY PRODUCT_NAME desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY PRODUCT_NAME";//Vendor login & weather Product
				}
				/******************* CR 46 CPU DataWashing********************/
				else if(_objFormObject.getNGValue("CPI_DATAWASHING_TYPE").equalsIgnoreCase("Endorsement"))
				{
					////// System.out.println("8.CR 46 CPI_DATAWASHING_TYPE: "+_objFormObject.getNGValue("CPI_DATAWASHING_TYPE"));
					this._strQuery = "select PRODUCT_NAME,IRDACODE from(select PRODUCT_NAME,IRDACODE from NG_PRODUCT_MASTER where PRODUCT_TYPE_ID="+product_type_val+"  AND PRODUCT_ROUTING = '2' AND PRODUCT_NAME <N'" + str1 +"'  ORDER BY PRODUCT_NAME desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY PRODUCT_NAME";//Vendor login & weather Product	
				}/*****************end CR 46 CPU DataWashing*******************/
				else
				/*****************End CR21 *********************/
				{	
					this._strQuery = "select PRODUCT_NAME,IRDACODE from(select PRODUCT_NAME,IRDACODE from NG_PRODUCT_MASTER where PRODUCT_TYPE_ID="+product_type_val+"  and PRODUCT_NAME <N'" + str1 +"'  ORDER BY PRODUCT_NAME desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY PRODUCT_NAME";	//Vendor login & weather Product		
				}
			}
			////// System.out.println("2_PRE_strQuery1"+_strQuery);
		}
		
		/************************End  Product Name****************/
		
		//searchString
		
		if((this.strSource != null) && (this.strSource.equals("SEARCH_STRING")))
		{
			fieldValue=_objFormObject.getNGValue(this.strSource);
			colCount=4;
			str1 = localNGPickList.getValueAt(0, 1);
			
			tempStr=_objFormObject.getNGValue("ICICILOMBARD_HT_SEARCH_CRITERIA");
			if(tempStr.equals("Agent Name"))
			{
				Col="i.TXT_INTERMEDIARY_NAME";				
			}
			else if (tempStr.equals("Agent Code"))
			{
				Col="i.TXT_INTERMEDIARY_CD";
			}
			else if (tempStr.equals("Deal No"))
			{
				Col="d.TXT_DEAL_ID";
			}
			if(tempStr.equals("Deal Status"))
			{
				Col="d.TXT_DISPLAY_RM_BS";
			}
				
			if(fieldValue!=null)
			{

				this._strQuery ="select TXT_INTERMEDIARY_NAME,TXT_INTERMEDIARY_CD, txt_deal_id,TXT_DISPLAY_RM_BS from (select i.TXT_INTERMEDIARY_NAME as TXT_INTERMEDIARY_NAME,i.TXT_INTERMEDIARY_CD as TXT_INTERMEDIARY_CD, d.txt_deal_id as txt_deal_id,d.TXT_DISPLAY_RM_BS as TXT_DISPLAY_RM_BS from MV_GENMST_INTERMEDIARY i, MV_Gen_Deal_Detail d where d.TXT_INTERMEDIARY_CD=i.TXT_INTERMEDIARY_CD  and UPPER("+Col+") like UPPER(N'"+fieldValue+"%') and d.TXT_INTERMEDIARY_CD <N'" + str1 +"' ORDER BY i.TXT_INTERMEDIARY_CD desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY TXT_INTERMEDIARY_CD";
			}
			else
			{
			
				this._strQuery ="select TXT_INTERMEDIARY_NAME,TXT_INTERMEDIARY_CD, txt_deal_id,TXT_DISPLAY_RM_BS from (select i.TXT_INTERMEDIARY_NAME as TXT_INTERMEDIARY_NAME,i.TXT_INTERMEDIARY_CD as TXT_INTERMEDIARY_CD, d.txt_deal_id as txt_deal_id,d.TXT_DISPLAY_RM_BS as TXT_DISPLAY_RM_BS from MV_GENMST_INTERMEDIARY i, MV_Gen_Deal_Detail d where d.TXT_INTERMEDIARY_CD=i.TXT_INTERMEDIARY_CD and d.TXT_INTERMEDIARY_CD <N'" + str1 +"' ORDER BY i.TXT_INTERMEDIARY_CD desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY TXT_INTERMEDIARY_CD";
			}	
			////// System.out.println("LOAD"+_strQuery);
		
		}
		
		//il location
		
		
		else if((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_IL_LOCATION")))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			colCount=2;
			str1 = localNGPickList.getValueAt(0, 1);
			if(fieldValue!=null)
			{
				this._strQuery = "SELECT ILBRANCHNAME, ILBRANCHCODE FROM (select DISTINCT ILBRANCHNAME, ILBRANCHCODE from NG_ICICI_MST_ILLOCATION where ILBRANCHCODE <N'"+str1+"' and UPPER(ILBRANCHNAME) like UPPER(N'"+fieldValue+"%')  ORDER BY ILBRANCHCODE desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY ILBRANCHCODE";
			}
			else
			{
				this._strQuery = "SELECT ILBRANCHNAME, ILBRANCHCODE FROM (select DISTINCT ILBRANCHNAME, ILBRANCHCODE from NG_ICICI_MST_ILLOCATION where ILBRANCHCODE <N'"+str1+"'ORDER BY ILBRANCHCODE desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY ILBRANCHCODE";
			}
			////// System.out.println("ICICILOMBARD_HT_IL_LOCATION query "+_strQuery);
		}
		
		/******************************* PID-HT process changes ********************************/
		//il location code
		else if((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_IL_LOCATION_CODE")))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			colCount=2;
			str1 = localNGPickList.getValueAt(0, 1);
			if(fieldValue!=null)
			{
				this._strQuery = "SELECT ILBRANCHNAME, ILBRANCHCODE FROM (select DISTINCT ILBRANCHNAME, ILBRANCHCODE from NG_ICICI_MST_ILLOCATION where ILBRANCHCODE <N'"+str1+"' and UPPER(ILBRANCHCODE) like UPPER(N'"+fieldValue+"%')  ORDER BY ILBRANCHCODE desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY ILBRANCHCODE";
			}
			else
			{
				this._strQuery = "SELECT ILBRANCHNAME, ILBRANCHCODE FROM (select DISTINCT ILBRANCHNAME, ILBRANCHCODE from NG_ICICI_MST_ILLOCATION where ILBRANCHCODE <N'"+str1+"'ORDER BY ILBRANCHCODE desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY ILBRANCHCODE";
			}
			////// System.out.println("ICICILOMBARD_HT_IL_LOCATION_CODE query "+_strQuery);
		}
		/******************************End PID-HT process changes ******************************/

		//source business prev
		
		else if((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_SOURCE_BUSINESS")) && ((_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking (BBG)") || _objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BBG"))))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
				colCount=1;
				str1 = localNGPickList.getValueAt(0, 0);
				if(fieldValue!=null)
				{
					this._strQuery ="SELECT SOURCEBUSINESS FROM (select DISTINCT(SOURCEBUSINESS) from NG_ICICI_MST_BBG_BUSINESS where  SOURCEBUSINESS <N'" + str1 +"' and UPPER(SOURCEBUSINESS) like UPPER(N'"+fieldValue+"%') ORDER BY SOURCEBUSINESS desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY SOURCEBUSINESS";
				}
				else
				{
					this._strQuery ="SELECT SOURCEBUSINESS FROM (select DISTINCT(SOURCEBUSINESS) from NG_ICICI_MST_BBG_BUSINESS where  SOURCEBUSINESS <N'" + str1 +"' ORDER BY SOURCEBUSINESS desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY SOURCEBUSINESS";
				}

			}
			
			//source business prev
		else if((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_SOURCE_BUSINESS")) && (_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)")))
			{
				
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=1;
				str1 = localNGPickList.getValueAt(0, 0);
				if(fieldValue!=null)
				{
								
					  this._strQuery = "select TXTSOURCEBUSINESS from (select DISTINCT(TXTSOURCEBUSINESS) from NG_ICICI_MST_KRG where TXTSOURCEBUSINESS <N'" + str1 +"' and UPPER(TXTSOURCEBUSINESS) like UPPER(N'"+fieldValue+"%') ORDER BY TXTSOURCEBUSINESS desc) where  ROWNUM <= " + this._iBatchSize+" ORDER BY TXTSOURCEBUSINESS";
				}
				else
				{
					 
					 this._strQuery = "select TXTSOURCEBUSINESS from (select DISTINCT(TXTSOURCEBUSINESS) from NG_ICICI_MST_KRG where TXTSOURCEBUSINESS <N'" + str1 +"'  ORDER BY TXTSOURCEBUSINESS desc) where  ROWNUM <= " + this._iBatchSize+" ORDER BY TXTSOURCEBUSINESS";
				}
			}
		
		//channel source prev
		else if((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_CHANNEL_SOURCE")))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=1;
				str1 = localNGPickList.getValueAt(0, 0);
				if(fieldValue!=null)
				{
								
					  this._strQuery = "select CHANNELSOURCING from(select DISTINCT(CHANNELSOURCING) from NG_ICICI_MST_BBG_SOURCE where CHANNELSOURCING <N'" + str1 +"' and UPPER(CHANNELSOURCING) like UPPER(N'"+fieldValue+"%') ORDER BY CHANNELSOURCING desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY CHANNELSOURCING";
				}
				else
				{
					 
					 this._strQuery = "select CHANNELSOURCING from(select DISTINCT(CHANNELSOURCING) from NG_ICICI_MST_BBG_SOURCE where CHANNELSOURCING <N'" + str1 +"' ORDER BY CHANNELSOURCING desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY CHANNELSOURCING";
				}
			}
			
			/******************************* PID-HT process changes ********************************/
			//for ICICILOMBARD_HT_PAYMENT_MODE
			else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_PAYMENT_MODE")))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=1;
				str1 = localNGPickList.getValueAt(0, 0);
				if(fieldValue!=null)
				{
					  this._strQuery = "select Payment_Mode from(select DISTINCT(Payment_Mode) from NG_HT_PAYMENT_MODE_MASTER where Payment_Mode <N'" + str1 +"' and UPPER(Payment_Mode) like UPPER(N'"+fieldValue+"%') ORDER BY Payment_Mode desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY Payment_Mode";
				}
				else
				{
					 this._strQuery = "select Payment_Mode from(select DISTINCT(Payment_Mode) from NG_HT_PAYMENT_MODE_MASTER where Payment_Mode <N'" + str1 +"' ORDER BY Payment_Mode desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY Payment_Mode";
				}
			}
			
			
			/******************************End PID-HT process changes ******************************/
			
			/**************************************Start HT SP Code CR CR-8093-69682 previous*****************************************************/
			//deal_il_location previous
			else if((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_DEAL_IL_LOCATION")))
			{
				String sm_id =_objFormObject.getNGValue("ICICILOMBARD_HT_SM_ID");
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=1;
				str1 = localNGPickList.getValueAt(0, 0);
				if(fieldValue!=null)
				{
					this._strQuery = "select b.TXT_OFFICE from MV_CENTRAL_EMPLOYEE a, MV_GENMST_OFFICE b where a.NUM_OFFICE_CD=b.NUM_OFFICE_CD and a.TXT_HR_REF_NO='"+sm_id+"' and b.TXT_OFFICE <N'" + str1 +"' and UPPER(b.TXT_OFFICE) like UPPER(N'"+fieldValue+"%') and ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					this._strQuery = "select b.TXT_OFFICE from MV_CENTRAL_EMPLOYEE a, MV_GENMST_OFFICE b where a.NUM_OFFICE_CD=b.NUM_OFFICE_CD and a.TXT_HR_REF_NO='"+sm_id+"' and b.TXT_OFFICE <N'" + str1 +"' and ROWNUM <= "+ this._iBatchSize;
				}
				////// System.out.println("ICICILOMBARD_HT_DEAL_IL_LOCATION**** Previous query "+this._strQuery);
			}
			//bank branch previous
			else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_BANK_BRANCH_NAME")))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=1;
				str1 = localNGPickList.getValueAt(0, 0);
				if(_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking")  || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking (BBG)") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("HOME")|| (_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG") && _objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BBG")))
				{
					if(fieldValue!=null)
					{
						  this._strQuery = "select BBGBRANCHNAME from(select distinct(BBGBRANCHNAME) from NG_ICICI_MST_BBG_HOMEBRANCH where BBGBRANCHNAME <N'" + str1 +"' and UPPER(BBGBRANCHNAME) like UPPER(N'"+fieldValue+"%') ORDER BY BBGBRANCHNAME desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY BBGBRANCHNAME";
					}
					else
					{
						 this._strQuery = "select BBGBRANCHNAME from(select distinct(BBGBRANCHNAME) from NG_ICICI_MST_BBG_HOMEBRANCH where BBGBRANCHNAME <N'" + str1 +"' ORDER BY BBGBRANCHNAME desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY BBGBRANCHNAME";
					}
				}
				if((_objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE"))&&(_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING")))
				{
					fieldValue=localNGPickList.getSearchFilterValue();
					_iTotalRecord=getTotalRecord(fieldValue);
					colCount=2;
					str1 = localNGPickList.getValueAt(0, 1);
					//not required for home
					String channel_source=_objFormObject.getNGValue("ICICILOMBARD_HT_CHANNEL_SOURCE");
					String deal_il_location=_objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_IL_LOCATION");
					if(fieldValue!=null)
					{
						this._strQuery = "select BANK_BRANCH_NAME,SOL_ID from(select distinct BANK_BRANCH_NAME,SOL_ID from NG_HT_MST_SP_CODE where CHANNEL_SOURCE ='"+channel_source+"' and DEAL_IL_LOCATION='"+deal_il_location+"' and  BANK_BRANCH_NAME <N'" + str1 +"' and UPPER(BANK_BRANCH_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY BANK_BRANCH_NAME desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY BANK_BRANCH_NAME";
					}
					else
					{
						this._strQuery = "select BANK_BRANCH_NAME,SOL_ID from(select distinct BANK_BRANCH_NAME,SOL_ID from NG_HT_MST_SP_CODE where CHANNEL_SOURCE ='"+channel_source+"' and DEAL_IL_LOCATION='"+deal_il_location+"' and  and BANK_BRANCH_NAME <N'" + str1 +"' ORDER BY BANK_BRANCH_NAME desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY BANK_BRANCH_NAME";
					}
				////// System.out.println("ICICILOMBARD_HT_BANK_BRANCH_NAME**** Previous query "+this._strQuery);
				}
			}	
			//sol id previous
			else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_SOL_ID")) && _objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE") && (_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING")))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=2;
				str1 = localNGPickList.getValueAt(0, 1);
				String channel_source=_objFormObject.getNGValue("ICICILOMBARD_HT_CHANNEL_SOURCE");
				String deal_il_location=_objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_IL_LOCATION");
				if(fieldValue!=null)
				{
					this._strQuery = "select BANK_BRANCH_NAME,SOL_ID from(select distinct BANK_BRANCH_NAME,SOL_ID from NG_HT_MST_SP_CODE where CHANNEL_SOURCE ='"+channel_source+"' and DEAL_IL_LOCATION='"+deal_il_location+"' and  SOL_ID <N'" + str1 +"' and UPPER(SOL_ID) like UPPER(N'"+fieldValue+"%') ORDER BY SOL_ID desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY SOL_ID";
				}
				else
				{
					this._strQuery = "select BANK_BRANCH_NAME,SOL_ID from(select distinct BANK_BRANCH_NAME,SOL_ID from NG_HT_MST_SP_CODE where CHANNEL_SOURCE ='"+channel_source+"' and DEAL_IL_LOCATION='"+deal_il_location+"' and SOL_ID <N'" + str1 +"' ORDER BY SOL_ID desc) where ROWNUM <= " +this._iBatchSize+" ORDER BY SOL_ID";
				}
				////// System.out.println("ICICILOMBARD_HT_SOL_ID**** Previous query "+this._strQuery);
			}
			//sp_code previous
			else if((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_WRE")) && _objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE") && (_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING")))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=3;
				str1 = localNGPickList.getValueAt(0, 2);
				String deal_il_location=_objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_IL_LOCATION");
				String sol_id=_objFormObject.getNGValue("ICICILOMBARD_HT_SOL_ID");
				String channel_source=_objFormObject.getNGValue("ICICILOMBARD_HT_CHANNEL_SOURCE");
				if(fieldValue!=null)
				{
					this._strQuery = "select SP_CODE,SP_NAME,SP_PAN from(select distinct SP_CODE,SP_NAME,SP_PAN from NG_HT_MST_SP_CODE where DEAL_IL_LOCATION='"+deal_il_location+"'and CHANNEL_SOURCE ='"+channel_source+"' and SOL_ID='"+sol_id+"' and SP_CODE <N'" + str1 +"' and UPPER(SP_CODE) like UPPER(N'"+fieldValue+"%') ORDER BY SP_CODE desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY SP_CODE";
				}
				else
				{
					 this._strQuery = "select SP_CODE,SP_NAME,SP_PAN from(select distinct SP_CODE,SP_NAME,SP_PAN from NG_HT_MST_SP_CODE where DEAL_IL_LOCATION='"+deal_il_location+"' and CHANNEL_SOURCE ='"+channel_source+"' and SOL_ID='"+sol_id+"' and SP_CODE <N'" + str1 +"' ORDER BY SP_CODE desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY SP_CODE";
				}
				////// System.out.println("ICICILOMBARD_HT_WRE**** Previous query "+this._strQuery);
			}
			/**************************************End HT SP Code CR CR-8093-69682 previous*****************************************************/
			/***************** Start SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI**************************************/
			//for ICICILOMBARD_HT_WRE KRG
			else if((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_WRE")) && (_objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE")) && (_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)")))
			{
				// System.out.println("Inside the Previous sp code: Yogesh");
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=2;
				str1 = localNGPickList.getValueAt(0, 1);
				if(fieldValue!=null)
				{
					// System.out.println("Inside the if Previous sp code: Yogesh");
					this._strQuery = "select SP_CODE,SP_NAME from(select distinct SP_CODE,SP_NAME from NG_HT_SP_CODE_KRG where SP_CODE <N'" + str1 +"' and UPPER(SP_CODE) like UPPER(N'"+fieldValue+"%') ORDER BY SP_CODE desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY SP_CODE";
				}
				else
				{
					// System.out.println("Inside the else Previous sp code: Yogesh");
					this._strQuery = "select SP_CODE,SP_NAME from(select distinct SP_CODE,SP_NAME from NG_HT_SP_CODE_KRG where SP_CODE <N'" + str1 +"' ORDER BY SP_CODE desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY SP_CODE";
				}
				// System.out.println("ICICILOMBARD_HT_WRE**** Previous query "+this._strQuery);
			}
			/***************** End SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI**************************************/
			
			/******Start HT CR-8127-95325-GST-Omniflow development******/
			//TXTGST_STATE_NAME Previous
			else if((this.strSource != null) && (this.strSource.equals("TXTGST_STATE_NAME")) && (_objFormObject.getNGValue("ICICILOMBARD_HT_GST_REGISTERED").equalsIgnoreCase("Yes")) && !(_objFormObject.getNGValue("ICICILOMBARD_HT_IAGENT").equalsIgnoreCase("Yes")))
			{
				// System.out.println("Inside the Previous GST State: Yogesh");//select a.txtstatename,a.txtstatecode from NG_HT_MST_GST_STATE a
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=1;
				str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);

				if(fieldValue!=null)
				{
					this._strQuery ="select txtstatename from NG_HT_MST_GST_STATE where txtstatename <N'" + str1 +"' and UPPER(txtstatename) like UPPER(N'"+fieldValue+"%') and ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					this._strQuery = "select txtstatename from NG_HT_MST_GST_STATE where txtstatename <N'" + str1 +"' and ROWNUM <= " + this._iBatchSize;
				}
				// System.out.println("TXTGST_STATE_NAME**** Previous query "+this._strQuery);
			}
			/******End HT CR-8127-95325-GST-Omniflow development******/
			/*****Start Change related to Application  Proposal no. field in Omni flow HT*****/
			else if((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_CHANNEL_LOAN_TYPE")))
			{
				System.out.println("Inside the Next Change related to Application  Proposal no. field in Omni flow HT: Yogesh");//select a.txtstatename,a.txtstatecode from NG_HT_MST_GST_STATE a
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=2;
				str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 1);

				if(fieldValue!=null)
				{
					this._strQuery ="select channel from NG_HT_MST_KRG_CHANNEL_SOURCE where channel <N'" + str1 +"' and UPPER(channel) like UPPER(N'"+fieldValue+"%') and ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					this._strQuery = "select channel from NG_HT_MST_KRG_CHANNEL_SOURCE where channel <N'" + str1 +"' and ROWNUM <= " + this._iBatchSize;
				}
				System.out.println("ICICILOMBARD_HT_CHANNEL_LOAN_TYPE****Next query ::" + this._strQuery);
			}
			/*****End Change related to Application  Proposal no. field in Omni flow HT*****/
			//BRANCH ID/UBO NAME PREVIOUS	
			else if ((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_BRANCH_ID_UBO_NAME")))
			{
				
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=1;
				str1 = localNGPickList.getValueAt(0, 0);
				if(_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)"))
				{
					if(fieldValue!=null)
					{
						  this._strQuery = "select Txtbranchidubo from(select DISTINCT(Txtbranchidubo) from NG_ICICI_MST_KRG where Txtbranchidubo <N'" + str1 +"' and UPPER(Txtbranchidubo) like UPPER(N'"+fieldValue+"%') ORDER BY Txtbranchidubo DESC) where ROWNUM <= " + this._iBatchSize+" ORDER BY Txtbranchidubo";
					}
					else
					{
						 this._strQuery = "select Txtbranchidubo from(select DISTINCT(Txtbranchidubo) from NG_ICICI_MST_KRG where Txtbranchidubo <N'" + str1 +"' ORDER BY Txtbranchidubo DESC) where ROWNUM <= " + this._iBatchSize+" ORDER BY Txtbranchidubo";;
					}
				////// System.out.println("ICICILOMBARD_HT_BRANCH_ID_UBO_NAME count5:"+this._strQuery);
				}	
			}
			
			//sm id sm name prev
			/********************** Start MO filteration for Centralised Deal Vertical **********************/
			else if ((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_SM_NAME")))
			{
				if (_objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_STATUS").equalsIgnoreCase("YES"))
				{
					fieldValue=localNGPickList.getSearchFilterValue();
					_iTotalRecord=getTotalRecord(fieldValue);
					colCount=4;
					str1 = localNGPickList.getValueAt(0, 1);
					if(fieldValue!=null)
					{
						this._strQuery ="select TXT_EMPLOYEE_NAME,TXT_HR_REF_NO,PRIM_SUBVERT,PRIM_VERT from(select Distinct TXT_EMPLOYEE_NAME as TXT_EMPLOYEE_NAME,TXT_HR_REF_NO as TXT_HR_REF_NO,PRIM_SUBVERT as PRIM_SUBVERT,PRIM_VERT as PRIM_VERT from MV_CENTRAL_EMPLOYEE WHERE UPPER(TXT_EMPLOYEE_NAME)  like UPPER(N'"+fieldValue+"%') and TXT_HR_REF_NO <N'" + str1 +"' ORDER BY TXT_HR_REF_NO desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY TXT_HR_REF_NO";
					}
					else
					{
						this._strQuery ="select TXT_EMPLOYEE_NAME,TXT_HR_REF_NO,PRIM_SUBVERT,PRIM_VERT from(select Distinct TXT_EMPLOYEE_NAME as TXT_EMPLOYEE_NAME,TXT_HR_REF_NO as TXT_HR_REF_NO,PRIM_SUBVERT as PRIM_SUBVERT,PRIM_VERT as PRIM_VERT from MV_CENTRAL_EMPLOYEE WHERE UPPER(TXT_EMPLOYEE_NAME)  like UPPER(N'"+fieldValue+"%') and TXT_HR_REF_NO <N'" + str1 +"' ORDER BY TXT_HR_REF_NO desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY TXT_HR_REF_NO";
					}						
				}	
				else
				{
					fieldValue=localNGPickList.getSearchFilterValue();
					_iTotalRecord=getTotalRecord(fieldValue);
					colCount=2;
					str1 = localNGPickList.getValueAt(0, 1);
					if(fieldValue!=null)
					{
						this._strQuery ="select TXT_EMPLOYEE_NAME,TXT_HR_REF_NO from(select Distinct me.TXT_EMPLOYEE_NAME as TXT_EMPLOYEE_NAME,me.TXT_HR_REF_NO as TXT_HR_REF_NO from MV_GENMST_EMPLOYEE me, MV_GEN_DEAL_DETAIL GD WHERE GD.NUM_MO_EMPLOYEE_NO= me.num_employee_cd and GD.txt_deal_id='"+_objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_NO")+"' and  UPPER(ME.TXT_EMPLOYEE_NAME)  like UPPER(N'"+fieldValue+"%') and TXT_HR_REF_NO <N'" + str1 +"' ORDER BY me.TXT_HR_REF_NO desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY TXT_HR_REF_NO";
					}
					else
					{
						this._strQuery ="select TXT_EMPLOYEE_NAME,TXT_HR_REF_NO from(select Distinct me.TXT_EMPLOYEE_NAME as TXT_EMPLOYEE_NAME,me.TXT_HR_REF_NO as TXT_HR_REF_NO from MV_GENMST_EMPLOYEE me, MV_GEN_DEAL_DETAIL GD WHERE GD.NUM_MO_EMPLOYEE_NO= me.num_employee_cd and GD.txt_deal_id='"+_objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_NO")+"' and TXT_HR_REF_NO <N'" + str1 +"' ORDER BY me.TXT_HR_REF_NO desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY TXT_HR_REF_NO";
					}
				}
				////// System.out.println("ICICILOMBARD_HT_SM_NAME Prev"+_strQuery);
			}
			/********************** End MO filteration for Centralised Deal Vertical   **********************/
			
			//BANK NAME PREV
			
			else if ((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_BANK_NAME")))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=2;
				str1 = localNGPickList.getValueAt(0, 0);
				if(fieldValue!=null)
				{

					  this._strQuery ="select txtbankname,TXTBANKCODE from(select DISTINCT txtbankname,TXTBANKCODE from NG_ICICI_MST_BankName WHERE UPPER(txtbankname)  like UPPER(N'%"+fieldValue+"%') and txtbankname <N'" + str1 +"' ORDER BY txtbankname DESC) where ROWNUM <= " + this._iBatchSize+" ORDER BY txtbankname";
				}
				else
				{
					 this._strQuery ="select txtbankname,TXTBANKCODE from(select DISTINCT txtbankname,TXTBANKCODE from NG_ICICI_MST_BankName WHERE txtbankname <N'" + str1 +"' ORDER BY txtbankname DESC) where ROWNUM <= " + this._iBatchSize+" ORDER BY txtbankname";
				}	
			}
			
			//sub product prev
			else if ((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_SUB_PRODUCT")))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=2;
				str1 = localNGPickList.getValueAt(0, 0);
				/*if (_objFormObject.getNGValue("ICICILOMBARD_HT_PRODUCT_CODE").equalsIgnoreCase("") || _objFormObject.getNGValue("ICICILOMBARD_HT_PRODUCT_CODE").equalsIgnoreCase(null))
				{
			_objFormObject.setNGValue("ICICILOMBARD_HT_PRODUCT_CODE",_objFormObject.getNGItemText("PRODUCT_HIDDEN",1));
			////// System.out.println("ICICILOMBARD_HT_SUB_PRODUCT query "+_objFormObject.getNGValue("ICICILOMBARD_HT_PRODUCT_CODE"));
				}*/
				//vishal
				//setProductCode();
				/*if(fieldValue!=null)
				{
					this._strQuery ="select TXT_IL_SUB_PRODUCT_NAME,TXT_IL_PRODUCT_NO_SUFIX from(select DISTINCT TXT_IL_SUB_PRODUCT_NAME,TXT_IL_PRODUCT_NO_SUFIX from MV_UW_SUB_PRODUCT_MASTER where num_IL_product_code='"+_objFormObject.getNGValue("ICICILOMBARD_HT_PRODUCT_CODE")+"' and TXT_IL_SUB_PRODUCT_NAME <N'" + str1 +"' and UPPER(TXT_IL_SUB_PRODUCT_NAME)  like UPPER(N'"+fieldValue+"%') ORDER BY TXT_IL_SUB_PRODUCT_NAME desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY TXT_IL_SUB_PRODUCT_NAME";
				}
				else
				{
					this._strQuery ="select TXT_IL_SUB_PRODUCT_NAME,TXT_IL_PRODUCT_NO_SUFIX from(select DISTINCT(TXT_IL_SUB_PRODUCT_NAME,TXT_IL_PRODUCT_NO_SUFIX) from MV_UW_SUB_PRODUCT_MASTER where num_IL_product_code='"+_objFormObject.getNGValue("ICICILOMBARD_HT_PRODUCT_CODE")+"' and TXT_IL_SUB_PRODUCT_NAME <N'" + str1 +"' ORDER BY TXT_IL_SUB_PRODUCT_NAME desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY TXT_IL_SUB_PRODUCT_NAME";
				}
				////// System.out.println("ICICILOMBARD_HT_SUB_PRODUCT query "+_strQuery);*/
				//----------Made Changes by vishal/Yogendra to fetch sub product on basis of deal no-------//
				
				////// System.out.println("str1 for prev: "+str1);
				if(fieldValue == null || fieldValue.equalsIgnoreCase(""))
				{
					this._strQuery ="select TXT_IL_SUB_PRODUCT_NAME,TXT_IL_PRODUCT_NO_SUFIX from(select DISTINCT TXT_IL_SUB_PRODUCT_NAME,TXT_IL_PRODUCT_NO_SUFIX from  MV_UW_SUB_PRODUCT_MASTER a,MV_GEN_DEAL_DETAIL b,MV_UW_DEAL_PLAN_MAP c where a.NUM_IL_PRODUCT_CODE=b.NUM_PRODUCT_CODE and a.TXT_IL_SUB_PRODUCT_CODE=c.NUM_PLAN_CODE and b.TXT_DEAL_ID=c.TXT_DEAL_ID and b.TXT_DEAL_ID='"+ _objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_NO")+"' and TXT_IL_SUB_PRODUCT_NAME <N'" + str1 +"' ORDER BY TXT_IL_SUB_PRODUCT_NAME desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY TXT_IL_SUB_PRODUCT_NAME";
					////// System.out.println("vishal prev: inside if :");
				}
				else
				{
					this._strQuery ="select TXT_IL_SUB_PRODUCT_NAME,TXT_IL_PRODUCT_NO_SUFIX from(select DISTINCT TXT_IL_SUB_PRODUCT_NAME,TXT_IL_PRODUCT_NO_SUFIX from  MV_UW_SUB_PRODUCT_MASTER a,MV_GEN_DEAL_DETAIL b,MV_UW_DEAL_PLAN_MAP c where a.NUM_IL_PRODUCT_CODE=b.NUM_PRODUCT_CODE and a.TXT_IL_SUB_PRODUCT_CODE=c.NUM_PLAN_CODE and b.TXT_DEAL_ID=c.TXT_DEAL_ID and b.TXT_DEAL_ID='"+ _objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_NO")+"' and TXT_IL_SUB_PRODUCT_NAME <N'" + str1 +"' and UPPER(TXT_IL_SUB_PRODUCT_NAME)  like UPPER(N'"+fieldValue+"%') ORDER BY TXT_IL_SUB_PRODUCT_NAME desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY TXT_IL_SUB_PRODUCT_NAME";
					////// System.out.println("vishal prev: inside else :");
				}
				////// System.out.println("ICICILOMBARD_HT_SUB_PRODUCT query "+this. _strQuery);
				//-----------END of  sub product on basis of deal no----------------//
			}
			
			//center code prev
			else if ((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_CENTER_CODE_NAME")))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=1;
				str1 = localNGPickList.getValueAt(0, 0);
				if(fieldValue!=null)
				{
					this._strQuery ="select CENTCODE_NAME from(select DISTINCT CENTCODE_NAME from NG_ICICI_MST_CENTERCODE where CENTCODE_NAME <N'" + str1 +"' and UPPER(CENTCODE_NAME)  like UPPER(N'%"+fieldValue+"%') ORDER BY CENTCODE_NAME desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY CENTCODE_NAME";
				}
				else
				{
					this._strQuery ="select CENTCODE_NAME from(select DISTINCT CENTCODE_NAME from NG_ICICI_MST_CENTERCODE where CENTCODE_NAME <N'" + str1 +"' ORDER BY CENTCODE_NAME desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY CENTCODE_NAME";
				}
			}

                /************************** CR-OF-MHT-1314-01 MHTProcess Implementaion Start***********/

            if((this.strSource != null) && (this.strSource.equals("MHTSEARCH_STRING")))
			{
				fieldValue=_objFormObject.getNGValue(this.strSource);
				////// System.out.println("MHTSEARCH_STRING previous click,fieldValue: "+fieldValue);
				colCount=5;

				tempStr=_objFormObject.getNGValue("MHT_SEARCH_CRITERIA");
				////// System.out.println("click on previous button tempStr"+tempStr);
				if(tempStr.equals("Agent Name"))
				{
					Col="i.TXT_INTERMEDIARY_NAME";
				}
				else if (tempStr.equals("Agent Code"))
				{
					Col="i.TXT_INTERMEDIARY_CD";
				}
				else if (tempStr.equals("Deal No"))
				{
					Col="d.TXT_DEAL_ID";
				}
				else if (tempStr.equals("Manual CN"))
				{
					colCount=3;
					Col="txt_cust_covernote_no";
				}
				if(tempStr.equals("Deal Status"))
				{
					Col="d.TXT_DISPLAY_RM_BS";
				}

				if(fieldValue!=null)
				{
					if(Col.equalsIgnoreCase("txt_cust_covernote_no"))
					{
						str1 = localNGPickList.getValueAt(0, 0);
						////// System.out.println("MHTSEARCH_STRING start on previous click: "+str1);
						this._strQuery = "select TXT_CUST_COVERNOTE_NO,TXT_COVERNOTE_DEAL_ID,YN_COVERNOTE_DEAL_ACCEPTANCE from (select DISTINCT TXT_CUST_COVERNOTE_NO,TXT_COVERNOTE_DEAL_ID,YN_COVERNOTE_DEAL_ACCEPTANCE from MV_MHT_OMNIFLOW_MANUAL_VIEW i where i.txt_cust_covernote_no is not null and UPPER("+Col+") like UPPER(N'%"+fieldValue+"%') and i.txt_cust_covernote_no <N'"+ str1 +"' order by i.txt_cust_covernote_no desc) where ROWNUM <= " + this._iBatchSize +"order by txt_cust_covernote_no";
					}
					else
					{
						str1 = localNGPickList.getValueAt(0, 1);
						////// System.out.println("MHTSEARCH_STRING start on previous click: "+str1);
						this._strQuery ="select TXT_INTERMEDIARY_NAME,TXT_INTERMEDIARY_CD, txt_deal_id,TXT_DISPLAY_RM_BS,TXT_COVERNOTE_FLAG from (select i.TXT_INTERMEDIARY_NAME as TXT_INTERMEDIARY_NAME,i.TXT_INTERMEDIARY_CD as TXT_INTERMEDIARY_CD, d.txt_deal_id as txt_deal_id,d.TXT_DISPLAY_RM_BS as TXT_DISPLAY_RM_BS,d.TXT_COVERNOTE_FLAG from mv_mht_genmst_intermediary i, mv_mht_gen_deal_detail d where d.TXT_INTERMEDIARY_CD=i.TXT_INTERMEDIARY_CD  and UPPER("+Col+") like UPPER(N'"+fieldValue+"%') and d.TXT_INTERMEDIARY_CD <N'" + str1 +"' ORDER BY i.TXT_INTERMEDIARY_CD desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY TXT_INTERMEDIARY_CD";
					}
				}
				else
				{
					if(Col.equalsIgnoreCase("txt_cust_covernote_no"))
					{
						str1 = localNGPickList.getValueAt(0, 0);
						////// System.out.println("MHTSEARCH_STRING start on previous click: "+str1);
						this._strQuery = "select TXT_CUST_COVERNOTE_NO,TXT_COVERNOTE_DEAL_ID,YN_COVERNOTE_DEAL_ACCEPTANCE from (select DISTINCT TXT_CUST_COVERNOTE_NO,TXT_COVERNOTE_DEAL_ID,YN_COVERNOTE_DEAL_ACCEPTANCE from MV_MHT_OMNIFLOW_MANUAL_VIEW i where i.txt_cust_covernote_no is not null and i.txt_cust_covernote_no <N'"+ str1 +"' order by i.txt_cust_covernote_no desc) where ROWNUM <= " + this._iBatchSize +"order by txt_cust_covernote_no";
					}
					else
					{
						str1 = localNGPickList.getValueAt(0, 1);
						////// System.out.println("MHTSEARCH_STRING start on previous click: "+str1);
						this._strQuery ="select TXT_INTERMEDIARY_NAME,TXT_INTERMEDIARY_CD, txt_deal_id,TXT_DISPLAY_RM_BS,TXT_COVERNOTE_FLAG from (select i.TXT_INTERMEDIARY_NAME as TXT_INTERMEDIARY_NAME,i.TXT_INTERMEDIARY_CD as TXT_INTERMEDIARY_CD, d.txt_deal_id as txt_deal_id,d.TXT_DISPLAY_RM_BS as TXT_DISPLAY_RM_BS,d.TXT_COVERNOTE_FLAG from mv_mht_genmst_intermediary i, mv_mht_gen_deal_detail d where d.TXT_INTERMEDIARY_CD=i.TXT_INTERMEDIARY_CD and d.TXT_INTERMEDIARY_CD <N'" + str1 +"' ORDER BY i.TXT_INTERMEDIARY_CD desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY TXT_INTERMEDIARY_CD";
					}
				}
				////// System.out.println("click on previous button LOAD query"+_strQuery);

			}

		//il location previous


		else if((this.strSource != null) && (this.strSource.equals("MHT_IL_LOCATION_CODE")))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			colCount=3;
			str1 = localNGPickList.getValueAt(0, 1);
			if(!fieldValue.equalsIgnoreCase(""))
			{
				/*************************** MHT-PID Process Integration ****************************/
				//this._strQuery = "SELECT ILBRANCHNAME, ILBRANCHCODE FROM (select DISTINCT ILBRANCHNAME, ILBRANCHCODE from NG_MHT_MST_ILLOCATION where ILBRANCHCODE <N'"+str1+"' and UPPER(ILBRANCHNAME) like UPPER(N'"+fieldValue+"%')  ORDER BY ILBRANCHCODE desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY ILBRANCHCODE";
				this._strQuery = "SELECT ILBRANCHNAME, ILBRANCHCODE, ZONEAREA FROM (select DISTINCT ILBRANCHNAME, ILBRANCHCODE, ZONEAREA from NG_MHT_MST_ILLOCATION where ILBRANCHCODE <N'"+str1+"' and UPPER(ILBRANCHCODE) like UPPER(N'"+fieldValue+"%')  ORDER BY ILBRANCHCODE desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY ILBRANCHCODE";
				/*********************** End MHT-PID Process Integration ****************************/
			}
			else
			{
				this._strQuery = "SELECT ILBRANCHNAME, ILBRANCHCODE, ZONEAREA FROM (select DISTINCT ILBRANCHNAME, ILBRANCHCODE, ZONEAREA from NG_MHT_MST_ILLOCATION where ILBRANCHCODE <N'"+str1+"'ORDER BY ILBRANCHCODE desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY ILBRANCHCODE";
			}
			////// System.out.println("MHT_IL_LOCATION_CODE query "+_strQuery);
		}
		//MHT_PRIMARY_VERTICAL
		/*************************** MHT-PID Process Integration ****************************/
		//Search provided on both location name and code
		else if((this.strSource != null) && (this.strSource.equals("MHT_IL_LOCATION")))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			colCount=3;
			str1 = localNGPickList.getValueAt(0, 0);
			if(!fieldValue.equalsIgnoreCase(""))
			{
				/*************************** MHT-PID Process Integration ****************************/
				//this._strQuery = "SELECT ILBRANCHNAME, ILBRANCHCODE FROM (select DISTINCT ILBRANCHNAME, ILBRANCHCODE from NG_MHT_MST_ILLOCATION where ILBRANCHCODE <N'"+str1+"' and UPPER(ILBRANCHNAME) like UPPER(N'"+fieldValue+"%')  ORDER BY ILBRANCHCODE desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY ILBRANCHCODE";
				this._strQuery = "SELECT ILBRANCHNAME, ILBRANCHCODE, ZONEAREA FROM (select DISTINCT ILBRANCHNAME, ILBRANCHCODE, ZONEAREA from NG_MHT_MST_ILLOCATION where ILBRANCHNAME <N'"+str1+"' and UPPER(ILBRANCHNAME) like UPPER(N'"+fieldValue+"%')  ORDER BY ILBRANCHNAME desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY ILBRANCHNAME";
				/*********************** End MHT-PID Process Integration ****************************/
			}
			else
			{
				this._strQuery = "SELECT ILBRANCHNAME, ILBRANCHCODE, ZONEAREA FROM (select DISTINCT ILBRANCHNAME, ILBRANCHCODE, ZONEAREA from NG_MHT_MST_ILLOCATION where ILBRANCHNAME <N'"+str1+"'ORDER BY ILBRANCHNAME desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY ILBRANCHNAME";
			}
			////// System.out.println("MHT_IL_LOCATION query "+_strQuery);
		}
		//modified code to fetch vertical code also	
		else if((this.strSource != null) && (this.strSource.equals("MHT_PRIMARY_VERTICAL")))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			colCount=1;
			str1 = localNGPickList.getValueAt(0, 0);
			if(fieldValue!=null)
			{
				this._strQuery = "SELECT prim_vert_name,prim_vert_code FROM (select DISTINCT prim_vert_name,prim_vert_code from ng_mht_mst_prim_vertical where prim_vert_name <N'"+str1+"' and UPPER(prim_vert_name) like UPPER(N'"+fieldValue+"%')  ORDER BY prim_vert_name desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY prim_vert_name";
			}
			else
			{
				this._strQuery = "SELECT prim_vert_name,prim_vert_code FROM (select DISTINCT prim_vert_name,prim_vert_code from ng_mht_mst_prim_vertical where prim_vert_name <N'"+str1+"'ORDER BY prim_vert_name desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY prim_vert_name";
			}
			////// System.out.println("MHT_PRIMARY_VERTICAL query "+_strQuery);
		}
		
		else if((this.strSource != null) && (this.strSource.equals("MHT_SUB_VERTICAL")))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			colCount=1;
			str1 = localNGPickList.getValueAt(0, 0);
			if(fieldValue!=null)
			{
				this._strQuery = "SELECT sec_vert_name,sec_vert_code FROM (select DISTINCT sec_vert_name,sec_vert_code from ng_mht_mst_sec_vertical where sec_vert_name <N'"+str1+"' and UPPER(sec_vert_name) like UPPER(N'"+fieldValue+"%')  ORDER BY sec_vert_name desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY sec_vert_name";
			}
			else
			{
				this._strQuery = "SELECT sec_vert_name,sec_vert_code FROM (select DISTINCT sec_vert_name,sec_vert_code from ng_mht_mst_sec_vertical where sec_vert_name <N'"+str1+"'ORDER BY sec_vert_name desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY sec_vert_name";
			}
			////// System.out.println("MHT_SUB_VERTICAL query "+_strQuery);
		}
		/*********************** End MHT-PID Process Integration ****************************/
		
		else if((this.strSource != null) && (this.strSource.equals("MHT_PRODUCT_NAME")))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			colCount=2;
			str1 = localNGPickList.getValueAt(0, 1);
			if(fieldValue!=null)
			{
				this._strQuery = "SELECT productname, productcode FROM (select DISTINCT productname, productcode from ng_mht_mst_product where productcode <N'"+str1+"' and UPPER(productname) like UPPER(N'"+fieldValue+"%')  ORDER BY productcode desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY productcode";
			}
			else
			{
				this._strQuery = "SELECT productname, productcode FROM (select DISTINCT productname, productcode from ng_mht_mst_product where productcode <N'"+str1+"'ORDER BY productcode desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY productcode";
			}
			////// System.out.println("MHT_PRODUCT_NAME query "+_strQuery);
		}

		//source business prev

		else if((this.strSource != null) && (this.strSource.equalsIgnoreCase("MHT_SOURCE_BUSINESS")) && ((_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BBG") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking (BBG)") || _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BBG"))))
		{
				fieldValue=localNGPickList.getSearchFilterValue();
		_iTotalRecord=getTotalRecord(fieldValue);
				colCount=1;
				str1 = localNGPickList.getValueAt(0, 0);
				if(fieldValue!=null)
				{
						this._strQuery ="SELECT SOURCEBUSINESS FROM (select DISTINCT(SOURCEBUSINESS) from NG_MHT_MST_BBG_BUSINESS where  SOURCEBUSINESS <N'" + str1 +"' and UPPER(SOURCEBUSINESS) like UPPER(N'"+fieldValue+"%') ORDER BY SOURCEBUSINESS desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY SOURCEBUSINESS";
				}
				else
				{
						this._strQuery ="SELECT SOURCEBUSINESS FROM (select DISTINCT(SOURCEBUSINESS) from NG_MHT_MST_BBG_BUSINESS where  SOURCEBUSINESS <N'" + str1 +"' ORDER BY SOURCEBUSINESS desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY SOURCEBUSINESS";
				}

		}

			//source business prev
		else if((this.strSource != null) && (this.strSource.equalsIgnoreCase("MHT_SOURCE_BUSINESS")) && (_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("KRG")))
		{

				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=1;
				str1 = localNGPickList.getValueAt(0, 0);
				if(fieldValue!=null)
				{

						  this._strQuery = "select TXTSOURCEBUSINESS from (select DISTINCT(TXTSOURCEBUSINESS) from NG_MHT_MST_KRG where TXTSOURCEBUSINESS <N'" + str1 +"' and UPPER(TXTSOURCEBUSINESS) like UPPER(N'"+fieldValue+"%') ORDER BY TXTSOURCEBUSINESS desc) where  ROWNUM <= " + this._iBatchSize+" ORDER BY TXTSOURCEBUSINESS";
				}
				else
				{

						 this._strQuery = "select TXTSOURCEBUSINESS from (select DISTINCT(TXTSOURCEBUSINESS) from NG_MHT_MST_KRG where TXTSOURCEBUSINESS <N'" + str1 +"'  ORDER BY TXTSOURCEBUSINESS desc) where  ROWNUM <= " + this._iBatchSize+" ORDER BY TXTSOURCEBUSINESS";
				}
		}

		//channel source prev
		else if((this.strSource != null) && (this.strSource.equalsIgnoreCase("MHT_CHANNEL_SOURCE")))
		{
			////// System.out.println("alData--MHT_CHANNEL_SOURCE start prev-->");
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			colCount=1;
			str1 = localNGPickList.getValueAt(0, 0);
			if(fieldValue!=null)
			{

					  this._strQuery = "select CHANNELSOURCING from(select DISTINCT(CHANNELSOURCING) from NG_MHT_MST_BBG_SOURCE where CHANNELSOURCING <N'" + str1 +"' and UPPER(CHANNELSOURCING) like UPPER(N'"+fieldValue+"%') ORDER BY CHANNELSOURCING desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY CHANNELSOURCING";
			}
			else
			{

					 this._strQuery = "select CHANNELSOURCING from(select DISTINCT(CHANNELSOURCING) from NG_MHT_MST_BBG_SOURCE where CHANNELSOURCING <N'" + str1 +"' ORDER BY CHANNELSOURCING desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY CHANNELSOURCING";
			}
			////// System.out.println("alData--MHT_CHANNEL_SOURCE prev-->" + this._strQuery);
				
		}

		/******************************************Start MHT SP Code CR-8127-69652 previous***************************************************************/
			//for bank branch name prev
			else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("MHT_BRANCH_ID")))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				//// System.out.println("MHT_BRANCH_ID previous button click" + fieldValue);
				_iTotalRecord=getTotalRecord(fieldValue);
				//// System.out.println("MHT_BRANCH_ID previous button _iTotalRecord" + _iTotalRecord);
				
				//// System.out.println("MHT_BRANCH_ID previous button str1" + str1);
				if(_objFormObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Policy Issuance") && _objFormObject.getNGValue("MHT_TYPE_OF_BUSINESS").equalsIgnoreCase("Intermediary") && _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BANCASSURANCE") && !_objFormObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER") && _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING"))
				{
					
					colCount=2;
					str1 = localNGPickList.getValueAt(0, 1);
					//not required for home
					String channel_source=_objFormObject.getNGValue("MHT_CHANNEL_SOURCE");
					String deal_il_location=_objFormObject.getNGValue("MHT_DEAL_IL_LOCATION");
					if(fieldValue!=null)
					{
						this._strQuery = "select SOL_ID,BANK_BRANCH_NAME from(select distinct SOL_ID,BANK_BRANCH_NAME from NG_MHT_MST_SP_CODE where CHANNEL_SOURCE ='"+channel_source+"' and DEAL_IL_LOCATION='"+deal_il_location+"' and  BANK_BRANCH_NAME <N'" + str1 +"' and UPPER(BANK_BRANCH_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY BANK_BRANCH_NAME desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY BANK_BRANCH_NAME";
					}
					else
					{
						this._strQuery = "select SOL_ID,BANK_BRANCH_NAME from(select distinct SOL_ID,BANK_BRANCH_NAME from NG_MHT_MST_SP_CODE where CHANNEL_SOURCE ='"+channel_source+"' and DEAL_IL_LOCATION='"+deal_il_location+"' and BANK_BRANCH_NAME <N'" + str1 +"' ORDER BY BANK_BRANCH_NAME desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY BANK_BRANCH_NAME";
					}
					////// System.out.println("MHT_BANK_BRANCH_NAME**** Previous query "+_strQuery);
				}
				else if(_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BBG") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking")  || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking (BBG)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("KRG") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("HOME")|| (_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BBG") && _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BBG")))
				{
					colCount=4;
					str1 = localNGPickList.getValueAt(0, 0);
					if(fieldValue!=null)
					{
						//this._strQuery = "select BBGBRANCHNAME from(select distinct(BBGBRANCHNAME) from NG_MHT_MST_BBG_HOMEBRANCH where BBGBRANCHNAME <N'" + str1 +"' and UPPER(BBGBRANCHNAME) like UPPER(N'"+fieldValue+"%') ORDER BY BBGBRANCHNAME desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY BBGBRANCHNAME";
						  this._strQuery = "select branch_id,branch_name,sp_code,sp_name from(select distinct branch_id,branch_name,sp_code,sp_name from NG_MHT_MST_BBGANDHOMEBRANCH where branch_id <N'" + str1 +"' and UPPER(branch_id) like UPPER(N'"+fieldValue+"%') ORDER BY branch_id desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY branch_id";
						  
					}
					else
					{
						// this._strQuery = "select BBGBRANCHNAME from(select distinct(BBGBRANCHNAME) from NG_MHT_MST_BBG_HOMEBRANCH where BBGBRANCHNAME <N'" + str1 +"' ORDER BY BBGBRANCHNAME desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY BBGBRANCHNAME";
						this._strQuery = "select branch_id,branch_name,sp_code,sp_name from (select distinct branch_id,branch_name,sp_code,sp_name from NG_MHT_MST_BBGANDHOMEBRANCH where branch_id <N'" + str1 +"' ORDER BY branch_id desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY branch_id";
					}
				}
				// System.out.println("MHT_BANK_BRANCH_NAME**** Previous query "+this._strQuery);
				
			}
			//for sol id prev
			else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("MHT_BANK_BRANCH_NAME")))
			{
				if(_objFormObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Policy Issuance") && _objFormObject.getNGValue("MHT_TYPE_OF_BUSINESS").equalsIgnoreCase("Intermediary") && _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BANCASSURANCE") && !_objFormObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER") && _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING"))
				{
					fieldValue=localNGPickList.getSearchFilterValue();
					_iTotalRecord=getTotalRecord(fieldValue);
					colCount=2;
					str1 = localNGPickList.getValueAt(0, 1);
					String channel_source=_objFormObject.getNGValue("MHT_CHANNEL_SOURCE");
					String deal_il_location=_objFormObject.getNGValue("MHT_DEAL_IL_LOCATION");
					if(fieldValue!=null)
					{
						this._strQuery = "select SOL_ID,BANK_BRANCH_NAME from(select distinct BANK_BRANCH_NAME,SOL_ID from NG_MHT_MST_SP_CODE where CHANNEL_SOURCE ='"+channel_source+"' and DEAL_IL_LOCATION='"+deal_il_location+"' and  SOL_ID <N'" + str1 +"' and UPPER(SOL_ID) like UPPER(N'"+fieldValue+"%') ORDER BY SOL_ID desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY SOL_ID";
					}
					else
					{
						this._strQuery = "select SOL_ID,BANK_BRANCH_NAME from(select distinct BANK_BRANCH_NAME,SOL_ID from NG_MHT_MST_SP_CODE where CHANNEL_SOURCE ='"+channel_source+"' and DEAL_IL_LOCATION='"+deal_il_location+"' and SOL_ID <N'" + str1 +"' ORDER BY SOL_ID desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY SOL_ID";
					}
					////// System.out.println("MHT_BRANCH_ID**** Previous query "+_strQuery);
				}
			}
			//for sp code prev
			else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("MHT_SP_CODE")))
			{
				if(_objFormObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Policy Issuance") && _objFormObject.getNGValue("MHT_TYPE_OF_BUSINESS").equalsIgnoreCase("Intermediary") && _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BANCASSURANCE") && !_objFormObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER") && _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING"))
				{
					_iTotalRecord=getTotalRecord(fieldValue);
					colCount=3;
					str1 = localNGPickList.getValueAt(0, 2);
					String deal_il_location=_objFormObject.getNGValue("MHT_DEAL_IL_LOCATION");
					String sol_id=_objFormObject.getNGValue("MHT_BRANCH_ID");
					String channel_source=_objFormObject.getNGValue("MHT_CHANNEL_SOURCE");
					if(fieldValue!=null)
					{
						this._strQuery = "select SP_CODE,SP_NAME,SP_PAN from(select distinct SP_CODE,SP_NAME,SP_PAN from NG_MHT_MST_SP_CODE where DEAL_IL_LOCATION='"+deal_il_location+"'and CHANNEL_SOURCE ='"+channel_source+"' and SOL_ID='"+sol_id+"' and SP_CODE <N'" + str1 +"' and UPPER(SP_CODE) like UPPER(N'"+fieldValue+"%') ORDER BY SP_CODE desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY SP_CODE";
					}
					else
					{
						 this._strQuery = "select SP_CODE,SP_NAME,SP_PAN from(select distinct SP_CODE,SP_NAME,SP_PAN from NG_MHT_MST_SP_CODE where DEAL_IL_LOCATION='"+deal_il_location+"' and CHANNEL_SOURCE ='"+channel_source+"' and SOL_ID='"+sol_id+"' and SP_CODE <N'" + str1 +"' ORDER BY SP_CODE desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY SP_CODE";
					}
					////// System.out.println("MHT_SP_CODE**** Previous query "+_strQuery);
				}
				/**********Start SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI **************/
				else
				{
					if(!((_objFormObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Policy Issuance") && _objFormObject.getNGValue("MHT_TYPE_OF_BUSINESS").equalsIgnoreCase("Direct"))) && (_objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BANCASSURANCE") || _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BA")) && (_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("KRG")) && !(_objFormObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER")))
					{
						// System.out.println("inside the prev sp code logic change ");
						fieldValue=localNGPickList.getSearchFilterValue();
						_iTotalRecord=getTotalRecord(fieldValue);
						colCount=2;
						str1 = localNGPickList.getValueAt(0, 1);					
						if(fieldValue!=null)
						{
							// System.out.println("Inside the if Previous sp code: Yogesh");
							this._strQuery = "select SP_CODE,SP_NAME from(select distinct SP_CODE,SP_NAME from NG_MHT_SP_CODE_KRG where SP_CODE <N'" + str1 +"' and UPPER(SP_CODE) like UPPER(N'"+fieldValue+"%') ORDER BY SP_CODE desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY SP_CODE";
						}
						else
						{
							// System.out.println("Inside the else Previous sp code: Yogesh");
							this._strQuery = "select SP_CODE,SP_NAME from(select distinct SP_CODE,SP_NAME from NG_MHT_SP_CODE_KRG where SP_CODE <N'" + str1 +"' ORDER BY SP_CODE desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY SP_CODE";
						}
						// System.out.println("MHT_SP_CODE**** Previous query "+_strQuery);
					}
				}
				/***********End SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI ***********/
			}
			/******************************************End MHT SP Code CR-8127-69652 previous***************************************************************/
			/******Start MHT CR-8127-95325-GST-Omniflow development******/
			//MHT_TXTGST_STATE_NAME Previous
			else if((this.strSource != null) && (this.strSource.equals("MHT_TXTGST_STATE_NAME")) && (_objFormObject.getNGValue("MHT_GST_REGISTERED").equalsIgnoreCase("Yes")) && !(_objFormObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER")))
			{
				// System.out.println("Inside the Previous GST State: Yogesh");//select a.txtstatename,a.txtstatecode from NG_MHT_MST_GST_STATE a
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=1;
				str1 = localNGPickList.getValueAt(localNGPickList.getRecordFetchedInBatch() - 1, 0);

				if(fieldValue!=null)
				{
					this._strQuery ="select txtstatename from NG_MHT_MST_GST_STATE where txtstatename <N'" + str1 +"' and UPPER(txtstatename) like UPPER(N'"+fieldValue+"%') and ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					this._strQuery = "select txtstatename from NG_MHT_MST_GST_STATE where txtstatename <N'" + str1 +"' and ROWNUM <= " + this._iBatchSize;
				}
				// System.out.println("MHT_TXTGST_STATE_NAME**** Previous query "+this._strQuery);
			}
			/******End MHT CR-8127-95325-GST-Omniflow development******/

			//BRANCH ID/UBO NAME PREVIOUS
			else if ((this.strSource != null) && (this.strSource.equals("MHT_BRANCH_ID_UBO_NAME")))
			{

				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=1;
				str1 = localNGPickList.getValueAt(0, 0);
				if(_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") ||
				_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("KRG"))
				{
					if(fieldValue!=null)
					{
						  this._strQuery = "select Txtbranchidubo from(select DISTINCT(Txtbranchidubo) from NG_MHT_MST_KRG where Txtbranchidubo <N'" + str1 +"' and UPPER(Txtbranchidubo) like UPPER(N'"+fieldValue+"%') ORDER BY Txtbranchidubo DESC) where ROWNUM <= " + this._iBatchSize+" ORDER BY Txtbranchidubo";
					}
					else
					{
						 this._strQuery = "select Txtbranchidubo from(select DISTINCT(Txtbranchidubo) from NG_MHT_MST_KRG where Txtbranchidubo <N'" + str1 +"' ORDER BY Txtbranchidubo DESC) where ROWNUM <= " + this._iBatchSize+" ORDER BY Txtbranchidubo";;
					}
				////// System.out.println("MHT_BRANCH_ID_UBO_NAME count5:"+this._strQuery);
				}
			////// System.out.println("MHT_BRANCH_ID_UBO_NAME count6:"+this._strQuery);
			}

			//sm id sm name prev
			else if ((this.strSource != null) && (this.strSource.equals("MHT_SM_NAME")))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=2;
				str1 = localNGPickList.getValueAt(0, 1);
				if(fieldValue!=null)
				{
					/**************************** MHT-PID Process Integration ***************************/
					this._strQuery ="select PRIMARY_MO_NAME,TXT_HR_REF_NO from(select DISTINCT PRIMARY_MO_NAME, TXT_HR_REF_NO from MV_MHT_GENMST_EMPLOYEE me where UPPER(PRIMARY_MO_NAME)  like UPPER(N'"+fieldValue+"%') and TXT_HR_REF_NO <N'" + str1 +"' ORDER BY TXT_HR_REF_NO desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY TXT_HR_REF_NO";
					//this._strQuery ="select RM_NAME,EMP_CODE from(select me.RM_NAME as RM_NAME,me.EMP_CODE as EMP_CODE from NG_MHT_MST_RM me where UPPER(ME.RM_NAME)  like UPPER(N'"+fieldValue+"%') and EMP_CODE <N'" + str1 +"' ORDER BY me.EMP_CODE desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY EMP_CODE";
					/************************ End MHT-PID Process Integration ***************************/
				}
				else
				{
					/**************************** MHT-PID Process Integration ***************************/
					this._strQuery ="select PRIMARY_MO_NAME,TXT_HR_REF_NO from(select DISTINCT PRIMARY_MO_NAME,TXT_HR_REF_NO from MV_MHT_GENMST_EMPLOYEE me where TXT_HR_REF_NO <N'" + str1 +"' ORDER BY TXT_HR_REF_NO desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY TXT_HR_REF_NO";
					//this._strQuery ="select RM_NAME,EMP_CODE from(select me.RM_NAME as RM_NAME,me.EMP_CODE as EMP_CODE from NG_MHT_MST_RM me where EMP_CODE <N'" + str1 +"' ORDER BY me.EMP_CODE desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY EMP_CODE";
					/************************ End MHT-PID Process Integration ***************************/
				}
				////// System.out.println("alData--MHT_SM_NAME Previous-->" + _strQuery);
			}
			
			//BANK NAME PREV

			else if ((this.strSource != null) && (this.strSource.equals("PAYMENT_TYPE1_BANKNAME") || this.strSource.equals("PAYMENT_TYPE2_BANKNAME")
                        || this.strSource.equals("PAYMENT_TYPE3_BANKNAME")))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=2;
				str1 = localNGPickList.getValueAt(0, 0);
				if(fieldValue!=null)
				{

					  this._strQuery ="select txtbankname,TXTBANKCODE from(select DISTINCT txtbankname,TXTBANKCODE from NG_ICICI_MST_BankName WHERE UPPER(txtbankname)  like UPPER(N'%"+fieldValue+"%') and txtbankname <N'" + str1 +"' ORDER BY txtbankname DESC) where ROWNUM <= " + this._iBatchSize+" ORDER BY txtbankname";
				}
				else
				{
					 this._strQuery ="select txtbankname,TXTBANKCODE from(select DISTINCT txtbankname,TXTBANKCODE from NG_ICICI_MST_BankName WHERE txtbankname <N'" + str1 +"' ORDER BY txtbankname DESC) where ROWNUM <= " + this._iBatchSize+" ORDER BY txtbankname";
				}
			}

			//center code prev
			else if ((this.strSource != null) && (this.strSource.equals("MHT_CENTER_CODE_NAME")))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=1;
				str1 = localNGPickList.getValueAt(0, 0);
				if(fieldValue!=null)
				{
					this._strQuery ="select CENTCODE_NAME from(select DISTINCT CENTCODE_NAME from NG_ICICI_MST_CENTERCODE where CENTCODE_NAME <N'" + str1 +"' and UPPER(CENTCODE_NAME)  like UPPER(N'%"+fieldValue+"%') ORDER BY CENTCODE_NAME desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY CENTCODE_NAME";
				}
				else
				{
					this._strQuery ="select CENTCODE_NAME from(select DISTINCT CENTCODE_NAME from NG_ICICI_MST_CENTERCODE where CENTCODE_NAME <N'" + str1 +"' ORDER BY CENTCODE_NAME desc) where ROWNUM <= " + this._iBatchSize+" ORDER BY CENTCODE_NAME";
				}
			}
            /************************** CR-OF-MHT-1314-01 MHTProcess Implementaion End***********/
			
		localNGPickList.populateData(this._strQuery, colCount);

      localNGPickList.setStatus("Showing " + ((this._iBatchNo - 1) * this._iBatchSize + 1) + " - " + (this._iBatchNo * this._iBatchSize) + " of " + this. _iTotalRecord);
 
    }

    //----------------------------------------------------------------------------------------------------
    //Function Name 			: btnOk_Clicked
    //Date Written (DD/MM/YYYY)	: 14/04/2009
    //Author					: Vikas Tyagi
    //Input Parameters			: e
    //Output Parameters			: none
    //Return Values				: none
    //Description				: Hanldes ckisk event of OK.
    //----------------------------------------------------------------------------------------------------
    public void btnOk_Clicked(NGEvent e)
    {
        _objPickList.setVisible(false);
		NGPickList localNGPickList = (NGPickList)e.getSource();
		this.objProcessData = new XMLParser();
    	this.objProcessData.setInputXML(this._objFormObject.getWFGeneralData());
		ArrayList localArrayList1 = localNGPickList.getSelectedData();
		ArrayList localArrayList2 =null;
		////// System.out.println("arraylist1----"+localArrayList1);
/*****Start CR-8001-87420-Green File Digitization*****/
		//============== CPI CR Quote SYS===============


		if((this.strSource != null) && (this.strSource.equals("CPI_RM_ID")))
		{
			if(_objFormObject.getNGValue("CPI_IS_PF_FETCH").equalsIgnoreCase("Yes"))
			{
				localArrayList2 = (ArrayList)localArrayList1.get(0);
				_objFormObject.setNGValue("CPI_RM_ID",localArrayList2.get(0).toString());
				_objFormObject.setNGValue("CPI_RM_NAME",localArrayList2.get(1).toString());
				_objFormObject.setNGValue("CPI_PRIMARY_VERTICAL",localArrayList2.get(2).toString());
				_objFormObject.setNGValue("CPI_PRIMARY_VERTICAL_CODE",localArrayList2.get(3).toString());
				_objFormObject.setNGValue("CPI_PRIMARY_SUB_VERTICAL",localArrayList2.get(4).toString());
				_objFormObject.setNGValue("CPI_SUB_VERTICAL_CODE",localArrayList2.get(5).toString());
			}
			else
			{
				localArrayList2 = (ArrayList)localArrayList1.get(0);
				_objFormObject.setNGValue("CPI_RM_ID",localArrayList2.get(0).toString());
				_objFormObject.setNGValue("CPI_RM_NAME",localArrayList2.get(1).toString());
				/*************************************Start Yogesh Marine CR*****************************/
				if((_objFormObject.getNGValue("CPI_PROCESS_TYPE").equalsIgnoreCase("Corporate Policy Insurance")) && (_objFormObject.getNGValue("CPI_QUOTE_SYS_ID") != null || !_objFormObject.getNGValue("CPI_QUOTE_SYS_ID").equalsIgnoreCase("")))
				{
					// System.out.println("Inside the btn ok cpi_rm_name");
					_objFormObject.setNGValue("CORP_RM_NAME",localArrayList2.get(1).toString());
				}
				/*************************************End Yogesh Marine CR*****************************/
			}
		}
		//sandeep  
		//============== End CPI CR Quote SYS===============
		//additional cit rm name
		if ((this.strSource != null) && (this.strSource.equals("CPI_ADDTNL_CIT_RM_ID")))
		{
			// System.out.println(this.strSource+" btnOk_Clicked");
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("CPI_ADDTNL_CIT_RM_ID",localArrayList2.get(0).toString());
			_objFormObject.setNGValue("CPI_ADDTNL_CIT_RM_NAME",localArrayList2.get(1).toString());			
		}
		//uw emp id
		if ((this.strSource != null) && (this.strSource.equals("CPI_UW_EMP_ID")))
		{
			// System.out.println(this.strSource+" btnOk_Clicked");
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("CPI_UW_EMP_ID",localArrayList2.get(0).toString());
			_objFormObject.setNGValue("CPI_UW_NAME",localArrayList2.get(1).toString());			
		}
		//secondary mo id
		if ((this.strSource != null) && (this.strSource.equals("CPI_SECONDARY_MO_ID")))
		{
			// System.out.println(this.strSource+" btnOk_Clicked");
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("CPI_SECONDARY_MO_ID",localArrayList2.get(0).toString());
			_objFormObject.setNGValue("CPI_SECONDARY_MO_NAME",localArrayList2.get(1).toString());			
		}
		//rm_emp_id_spg_ibg
		if ((this.strSource != null) && (this.strSource.equals("CPI_RM_EMP_ID_SPG_IBG")))
		{
			// System.out.println(this.strSource+" btnOk_Clicked");
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("CPI_RM_EMP_ID_SPG_IBG",localArrayList2.get(0).toString());
			_objFormObject.setNGValue("CPI_RM_NAME_SPG_IBG",localArrayList2.get(1).toString());			
		}
		//pre_policy_no
		if ((this.strSource != null) && (this.strSource.equals("CPI_PREV_POLICY_NO")))
		{
			// System.out.println(this.strSource+" btnOk_Clicked");
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("CPI_PREV_POLICY_NO",localArrayList2.get(0).toString());
			_objFormObject.setNGValue("CPI_CUSTOMER_ID",localArrayList2.get(1).toString());
			_objFormObject.setNGValue("CPI_CUSTOMER_NAME",localArrayList2.get(2).toString());
		}
		//base policy no
		if ((this.strSource != null) && (this.strSource.equals("CPI_POLICY_NUMBER_BASE")))
		{
			// System.out.println(this.strSource+" btnOk_Clicked");
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("CPI_POLICY_NUMBER_BASE",localArrayList2.get(0).toString());
			String str=localArrayList2.get(1).toString();
			// System.out.println("str--->:"+str);
			String formattedate="";
			try{
			Date date =null;
			date = dateFormat2.parse(str);
			// System.out.println("Inside policy start date value :"+date);
			formattedate=dateformat.format(date);
			}
			catch(Exception expt)
			{}
			_objFormObject.setNGValue("CPI_POLICY_START_DATE",formattedate);
			_objFormObject.setNGValue("CPI_CUSTOMER_ID",localArrayList2.get(2).toString());
			_objFormObject.setNGValue("CPI_CUSTOMER_NAME",localArrayList2.get(3).toString());
			_objFormObject.setNGValue("CPI_PRODUCT_NAME",localArrayList2.get(4).toString());
			_objFormObject.setNGValue("CPI_PRODUCT_IRDACODE",localArrayList2.get(5).toString());			
		}
		/*****End CR-8001-87420-Green File Digitization*****/
		/**************************************Client Registration CR Start*****************************************************/
		if((this.strSource != null) && (this.strSource.equals("CPI_POLICYNO_SEARCH")))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("CPI_POLICYNO_SEARCH",localArrayList2.get(0).toString());
			_objFormObject.setNGValue("CPI_PRODUCT_NAME",localArrayList2.get(1).toString());
			_objFormObject.setNGValue("CPI_RM_ID",localArrayList2.get(2).toString());
			_objFormObject.setNGValue("CPI_RM_NAME",localArrayList2.get(3).toString());
			_objFormObject.setNGValue("CPI_IL_LOCATION",localArrayList2.get(4).toString());
			_objFormObject.setNGValue("CPI_PRIMARY_VERTICAL",localArrayList2.get(5).toString());
			_objFormObject.setNGValue("CPI_PRIMARY_SUB_VERTICAL",localArrayList2.get(6).toString());
			_objFormObject.setNGValue("CPI_CUSTOMER_ID",localArrayList2.get(7).toString());
			_objFormObject.setNGValue("CPI_NAME_OF_BROKER_AGENT",localArrayList2.get(8).toString());
			_objFormObject.setNGValue("CPI_CUSTOMER_NAME",localArrayList2.get(9).toString());
			_objFormObject.setNGValue("CPI_MODE_OF_PAYMENT",localArrayList2.get(10).toString());
			_objFormObject.setNGValue("CPI_INST_NO_CD_NO_DEP_SLIP_NO",localArrayList2.get(11).toString());
			_objFormObject.setNGValue("CPI_PRODUCT_IRDACODE",localArrayList2.get(12).toString());
			_objFormObject.setNGValue("CPI_PRIMARY_VERTICAL_CODE",localArrayList2.get(13).toString());
			_objFormObject.setNGValue("CPI_SUB_VERTICAL_CODE",localArrayList2.get(14).toString());
		}
		/**************************************Client Registration CR End*****************************************************/		
		/********************CR-OMCPI-1314-03 CPU DataWashing Start**********************/
		if((this.strSource != null) && (this.strSource.equals("CPI_CPU_ASSIGN_TO")))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("CPI_CPU_ASSIGN_TO",localArrayList2.get(0).toString());
			//_objFormObject.setNGValue("CPI_RM_NAME",localArrayList2.get(1).toString());
		}
		/********************CR-OMCPI-1314-03 CPU DataWashing END**********************/
		
		/********************* CR 45 Network Partner *****************************/
		if((this.strSource != null) && (this.strSource.equals("CPI_NETWORK_PARTNER_NAME")))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("CPI_NETWORK_PARTNER_NAME",localArrayList2.get(0).toString());
		}
		/********************* End CR 45 Network Partner *************************/
		
		/******************  CO Insurance CR 18 *************************/
			//satish
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_RM_Name")))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("CPI_RM_ID",localArrayList2.get(1).toString());
			_objFormObject.setNGValue("CPI_RM_NAME",localArrayList2.get(0).toString());
		}
		//satish
		/******************End  CO Insurance CR 18 *************************/	
		
		/********************* CR 28 by satish *****************************/
		//satish
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_NAME_OF_BROKER_AGENT")))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("CPI_NAME_OF_BROKER_AGENT",localArrayList2.get(0).toString());
			//_objFormObject.setNGValue("CPI_RM_NAME",localArrayList2.get(0).toString());
			/*************************** PID-CPI process changes ***************************/
			_objFormObject.setNGValue("CPI_AGENT_CODE",localArrayList2.get(2).toString());
			/*********************** End PID-CPI process changes ***************************/
		}
		//satish
		/********************** End of CR 28 ********************************/
		
		/************************  CPI IL Location	************************************/
	
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_IL_Location")))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("CPI_IL_Location",localArrayList2.get(0).toString());
			//_objFormObject.setNGValue("CPI_RM_NAME",localArrayList2.get(0).toString());
			
			////// System.out.println("111"+localArrayList2.get(0).toString()+"\t"+localArrayList2.get(0).toString());
		}
		
		
		
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("Corp_IL_Location")))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("Corp_IL_Location",localArrayList2.get(0).toString());
			//_objFormObject.setNGValue("CPI_RM_NAME",localArrayList2.get(0).toString());
			
			////// System.out.println("111"+localArrayList2.get(0).toString()+"\t"+localArrayList2.get(0).toString());
		}
		

		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("End_IL_Location")))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("End_IL_Location",localArrayList2.get(0).toString());
			//_objFormObject.setNGValue("CPI_RM_NAME",localArrayList2.get(0).toString());
			
			////// System.out.println("111"+localArrayList2.get(0).toString()+"\t"+localArrayList2.get(0).toString());
		}
		
		////// System.out.println("Exit Prev");
		/************************  CPI IL Location	************************************/
		
		/*************************** PID-CPI process changes ***************************/
		/**************************** CPI moDE_OF_PAYMENT****************/
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_moDE_OF_PAYMENT")))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("CPI_moDE_OF_PAYMENT",localArrayList2.get(0).toString());
		}			
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_MODE_OF_PAYMENT2")))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("CPI_MODE_OF_PAYMENT2",localArrayList2.get(0).toString());
		}
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_MODE_OF_PAYMENT3")))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("CPI_MODE_OF_PAYMENT3",localArrayList2.get(0).toString());
		}			
		////// System.out.println("Exit Prev");
		/************************End  CPI moDE_OF_PAYMENT****************/
		/*********************** End PID-CPI process changes ***************************/
		
		/**************************** CPI Hypothecated_to****************/
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_HYPOTHECATED_TO")))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("CPI_HYPOTHECATED_TO",localArrayList2.get(0).toString());
		}
		////// System.out.println("Exit Prev");
		/************************End  CPI Hypothecated_to****************/
		
		
		/************************* CPI URN CR 8001-61339 Multiple Changes CR *****************************/ 
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_Exception_To_MH")))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("CPI_Exception_To_MH",localArrayList2.get(0).toString());
		}
		////// System.out.println("Exit Prev");
/*************************End CPI URN CR 8001-61339 Multiple Changes CR **************************/
		
		/************************  SPECIFIED_PERSON	************************************/
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_SPECIFIED_PERSON"))  || (this.strSource.equalsIgnoreCase("Corp_SP_Name")))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("CPI_SPECIFIED_PERSON",localArrayList2.get(0).toString());
			_objFormObject.setNGValue("Corp_SP_Name",localArrayList2.get(0).toString());
			//_objFormObject.setNGValue("CPI_RM_NAME",localArrayList2.get(0).toString());
			
			////// System.out.println("111"+localArrayList2.get(0).toString()+"\t"+localArrayList2.get(0).toString());
		}
		
		/************************  SPECIFIED_PERSON	************************************/
		/************************  CPI_PRIMARY_VERTICAL	************************************/
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_PRIMARY_VERTICAL")))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("CPI_PRIMARY_VERTICAL",localArrayList2.get(0).toString());
			_objFormObject.setNGValue("CPI_PRIMARY_VERTICAL_CODE",localArrayList2.get(1).toString());//PID_CPI process changes
			
			
			////// System.out.println("111"+localArrayList2.get(0).toString()+"\t"+localArrayList2.get(0).toString());
		}
		
		/************************  CPI_PRIMARY_VERTICAL	************************************/
		/************************  CPI_PRIMARY_SUB_VERTICAL	************************************/
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_PRIMARY_SUB_VERTICAL")))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("CPI_PRIMARY_SUB_VERTICAL",localArrayList2.get(0).toString());
			_objFormObject.setNGValue("CPI_SUB_VERTICAL_CODE",localArrayList2.get(1).toString());//PID_CPI process changes
			
			////// System.out.println("111"+localArrayList2.get(0).toString()+"\t"+localArrayList2.get(0).toString());
		}
		
		/************************  CPI_PRIMARY_SUB_VERTICAL	************************************/
		/**** Start CR-8001-70893 Marine CR *******************************/
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_SECONDARY_VERTICAL")))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("CPI_SECONDARY_VERTICAL",localArrayList2.get(0).toString());
			
			
			// System.out.println("CPI_SECONDARY_VERTICAL=="+localArrayList2.get(0).toString()+"\t"+localArrayList2.get(0).toString());
		}
		/**** ENd CR-8001-70893 Marine CR *********************************/
		/************************  CPI_SECONDARY_SUB_VERTICAL	************************************/
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_SECONDARY_SUB_VERTICAL")))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("CPI_SECONDARY_SUB_VERTICAL",localArrayList2.get(0).toString());
			//_objFormObject.setNGValue("CPI_RM_NAME",localArrayList2.get(0).toString());
			
			////// System.out.println("111"+localArrayList2.get(0).toString()+"\t"+localArrayList2.get(0).toString());
		}
		
		/************************  CPI_SECONDARY_SUB_VERTICAL	************************************/
		/************************  CPI_SOURCE_NAME	************************************/
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_SOURCE_NAME")))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			if(localArrayList2 != null)
			{
				////// System.out.println("Validation CR 28 :localArrayList2: inside  if");
				_objFormObject.setNGValue("CPI_SOURCE_NAME",localArrayList2.get(0).toString());
			}
			else
			{
				_objFormObject.setNGValue("CPI_SOURCE_NAME","");
				////// System.out.println("Validation CR 28 :localArrayList2: inside  if");
			}
			//_objFormObject.setNGValue("CPI_RM_NAME",localArrayList2.get(0).toString());
			
			////// System.out.println("111"+localArrayList2.get(0).toString()+"\t"+localArrayList2.get(0).toString());
		}
		
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CORP_SOURCE_NAME")))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("CORP_SOURCE_NAME",localArrayList2.get(0).toString());
			//_objFormObject.setNGValue("CPI_RM_NAME",localArrayList2.get(0).toString());
			
			////// System.out.println("111"+localArrayList2.get(0).toString()+"\t"+localArrayList2.get(0).toString());
		}
		
		/************************  CPI_SOURCE_NAME	************************************/
		
		/************************  CPI_CHANNEL_NAME	************************************/
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_CHANNEL_NAME")))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("CPI_CHANNEL_NAME",localArrayList2.get(0).toString());
			//_objFormObject.setNGValue("CPI_RM_NAME",localArrayList2.get(0).toString());
			
			////// System.out.println("111"+localArrayList2.get(0).toString()+"\t"+localArrayList2.get(0).toString());
		}
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CORP_CHANNEL_NAME")))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("CORP_CHANNEL_NAME",localArrayList2.get(0).toString());
			//_objFormObject.setNGValue("CPI_RM_NAME",localArrayList2.get(0).toString());
			
			////// System.out.println("111"+localArrayList2.get(0).toString()+"\t"+localArrayList2.get(0).toString());
		}
		
		/************************  CPI_CHANNEL_NAME	************************************/
		
		/************************  CPI_BRANCH_NAME	************************************/
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_BRANCH_NAME")))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("CPI_BRANCH_NAME",localArrayList2.get(0).toString());
			//_objFormObject.setNGValue("CPI_RM_NAME",localArrayList2.get(0).toString());
			/*************************** PID-CPI process changes ***************************/
			_objFormObject.setNGValue("CPI_BRANCH_ID",localArrayList2.get(1).toString());
			_objFormObject.setNGValue("CPI_SP_NAME1",localArrayList2.get(2).toString());
			_objFormObject.setNGValue("CPI_SP_CODE1",localArrayList2.get(3).toString());
			_objFormObject.setNGValue("CPI_SP_NAME2",localArrayList2.get(4).toString());
			_objFormObject.setNGValue("CPI_SP_CODE2",localArrayList2.get(5).toString());
			_objFormObject.setNGValue("CPI_SP_NAME3",localArrayList2.get(6).toString());
			_objFormObject.setNGValue("CPI_SP_CODE3",localArrayList2.get(7).toString());
			_objFormObject.setNGValue("CPI_SP_NAME4",localArrayList2.get(8).toString());
			_objFormObject.setNGValue("CPI_SP_CODE4",localArrayList2.get(9).toString());
			/*********************** End PID-CPI process changes ***************************/
			////// System.out.println("111"+localArrayList2.get(0).toString()+"\t"+localArrayList2.get(0).toString());
		}
		
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CORP_BRANCH_NAME")))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("CORP_BRANCH_NAME",localArrayList2.get(0).toString());
			//_objFormObject.setNGValue("CPI_RM_NAME",localArrayList2.get(0).toString());
			/*************************** PID-CPI process changes ***************************/
			_objFormObject.setNGValue("CPI_BRANCH_ID",localArrayList2.get(1).toString());
			_objFormObject.setNGValue("CPI_SP_NAME1",localArrayList2.get(2).toString());
			_objFormObject.setNGValue("CPI_SP_CODE1",localArrayList2.get(3).toString());
			_objFormObject.setNGValue("CPI_SP_NAME2",localArrayList2.get(4).toString());
			_objFormObject.setNGValue("CPI_SP_CODE2",localArrayList2.get(5).toString());
			_objFormObject.setNGValue("CPI_SP_NAME3",localArrayList2.get(6).toString());
			_objFormObject.setNGValue("CPI_SP_CODE3",localArrayList2.get(7).toString());
			_objFormObject.setNGValue("CPI_SP_NAME4",localArrayList2.get(8).toString());
			_objFormObject.setNGValue("CPI_SP_CODE4",localArrayList2.get(9).toString());
			/*********************** End PID-CPI process changes ***************************/
			
			////// System.out.println("111"+localArrayList2.get(0).toString()+"\t"+localArrayList2.get(0).toString());
		}
		
		/************************  CPI_BRANCH_NAME	************************************/
		
		/*****************  CPI_naME_OF_LEADER by satish for CR21 *********************/
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_naME_OF_LEADER")))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("CPI_naME_OF_LEADER",localArrayList2.get(0).toString());
			////// System.out.println("111"+localArrayList2.get(0).toString()+"\t"+localArrayList2.get(0).toString());
		}		
		/***************** End CPI_naME_OF_LEADER by satish for CR21 *********************/
		
		/************************  Product Name	************************************/
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_PRODUCT_NAME")))
		{
			////// System.out.println("satish : On OK click");
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("CPI_PRODUCT_NAME",localArrayList2.get(0).toString());
			// System.out.println("PRODUCT_IRDACODE value setting....."+localArrayList2.get(1).toString());
			_objFormObject.setNGValue("CPI_PRODUCT_IRDACODE",localArrayList2.get(1).toString());//Vendor login & weather Product//
			////// System.out.println("111"+localArrayList2.get(0).toString()+"\t"+localArrayList2.get(0).toString());
		}
		/************************  End Product Name	************************************/
		
		/************************  Product Name	************************************/
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("Corp_PRODUCT_NAME")))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("Corp_PRODUCT_NAME",localArrayList2.get(0).toString());
			_objFormObject.setNGValue("CPI_PRODUCT_IRDACODE",localArrayList2.get(1).toString());//Vendor login & weather Product
			////// System.out.println("111"+localArrayList2.get(0).toString()+"\t"+localArrayList2.get(0).toString());
		}		
		/************************  End Product Name	************************************/
		
		/************************  Product Name	************************************/
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("End_PRODUCT_NAME")))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("End_PRODUCT_NAME",localArrayList2.get(0).toString());
			_objFormObject.setNGValue("CPI_PRODUCT_IRDACODE",localArrayList2.get(1).toString());//Vendor login & weather Product
			////// System.out.println("111"+localArrayList2.get(0).toString()+"\t"+localArrayList2.get(0).toString());
		}		
		/************************  End Product Name	************************************/

		if(strSource.equalsIgnoreCase("SEARCH_STRING"))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("ICICILOMBARD_HT_AGENT_NAME",localArrayList2.get(0).toString());
			////// System.out.println("111"+localArrayList2.get(0).toString()+"\t"+localArrayList2.get(0).toString());
			_objFormObject.setNGValue("ICICILOMBARD_HT_AGENT_CODE",localArrayList2.get(1).toString());
			////// System.out.println("111"+localArrayList2.get(1).toString()+"\t"+localArrayList2.get(1).toString());
			_objFormObject.setNGValue("ICICILOMBARD_HT_DEAL_NO",localArrayList2.get(2).toString());
			////// System.out.println("111"+localArrayList2.get(2).toString()+"\t"+localArrayList2.get(2).toString());
			_objFormObject.setNGValue("ICICILOMBARD_HT_DEAL_STATUS",localArrayList2.get(3).toString());
			////// System.out.println("111"+localArrayList2.get(3).toString()+"\t"+localArrayList2.get(3).toString());
			
		}
		
		else if(strSource.equalsIgnoreCase("ICICILOMBARD_HT_IL_LOCATION"))
		{
			 localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("ICICILOMBARD_HT_IL_LOCATION",localArrayList2.get(0).toString());
			_objFormObject.setNGValue("ICICILOMBARD_HT_IL_LOCATION_CODE",localArrayList2.get(1).toString());
		}
		/******************************* PID-HT process changes ********************************/
		else if(strSource.equalsIgnoreCase("ICICILOMBARD_HT_IL_LOCATION_CODE"))
		{
			 localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("ICICILOMBARD_HT_IL_LOCATION",localArrayList2.get(0).toString());
			_objFormObject.setNGValue("ICICILOMBARD_HT_IL_LOCATION_CODE",localArrayList2.get(1).toString());
		}
		/******************************End PID-HT process changes ******************************/


		
		else if(strSource.equalsIgnoreCase("ICICILOMBARD_HT_SOURCE_BUSINESS"))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);	
			_objFormObject.setNGValue("ICICILOMBARD_HT_SOURCE_BUSINESS",localArrayList2.get(0).toString());
		}
		
		else if(strSource.equalsIgnoreCase("ICICILOMBARD_HT_CHANNEL_SOURCE"))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("ICICILOMBARD_HT_CHANNEL_SOURCE",localArrayList2.get(0).toString());
		}
		
		/******************************* PID-HT process changes ********************************/
		//for ICICILOMBARD_HT_PAYMENT_MODE
		else if(strSource.equalsIgnoreCase("ICICILOMBARD_HT_PAYMENT_MODE"))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("ICICILOMBARD_HT_PAYMENT_MODE",localArrayList2.get(0).toString());
		}
		/******************************End PID-HT process changes ******************************/
		
		/**************************************Start HT SP Code CR CR-8093-69682 okClicked*****************************************************/
		else if(strSource.equalsIgnoreCase("ICICILOMBARD_HT_DEAL_IL_LOCATION"))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("ICICILOMBARD_HT_DEAL_IL_LOCATION",localArrayList2.get(0).toString());
		}
		else if(strSource.equalsIgnoreCase("ICICILOMBARD_HT_BANK_BRANCH_NAME"))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("ICICILOMBARD_HT_BANK_BRANCH_NAME",localArrayList2.get(0).toString());
			if((_objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE"))&&(_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING")))
			{
				localArrayList2 = (ArrayList)localArrayList1.get(0);
				_objFormObject.setNGValue("ICICILOMBARD_HT_BANK_BRANCH_NAME",localArrayList2.get(0).toString());
				_objFormObject.setNGValue("ICICILOMBARD_HT_SOL_ID",localArrayList2.get(1).toString());
			}
		}
		else if(strSource.equalsIgnoreCase("ICICILOMBARD_HT_SOL_ID"))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("ICICILOMBARD_HT_BANK_BRANCH_NAME",localArrayList2.get(0).toString());
			_objFormObject.setNGValue("ICICILOMBARD_HT_SOL_ID",localArrayList2.get(1).toString());
		}
		else if(strSource.equalsIgnoreCase("ICICILOMBARD_HT_WRE"))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("ICICILOMBARD_HT_WRE",localArrayList2.get(0).toString());
			_objFormObject.setNGValue("ICICILOMBARD_HT_SP_NAME",localArrayList2.get(1).toString());
			_objFormObject.setNGValue("ICICILOMBARD_HT_SP_PAN",localArrayList2.get(2).toString());
		}
		/**************************************End HT SP Code CR CR-8093-69682 okClicked*****************************************************/
		/***************** Start SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI**************************************/
		//for ICICILOMBARD_HT_WRE KRG
		else if(strSource.equalsIgnoreCase("ICICILOMBARD_HT_WRE") && (_objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE")) && (_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)")))
		{
			// System.out.println("Inside the value set sp code: Yogesh");
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("ICICILOMBARD_HT_WRE",localArrayList2.get(0).toString());
			_objFormObject.setNGValue("ICICILOMBARD_HT_SP_NAME",localArrayList2.get(1).toString());			
		}
		/***************** End SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI**************************************/
		/******Start HT CR-8127-95325-GST-Omniflow development******/
		//TXTGST_STATE_NAME value set
		else if((this.strSource != null) && (this.strSource.equals("TXTGST_STATE_NAME")) && (_objFormObject.getNGValue("ICICILOMBARD_HT_GST_REGISTERED").equalsIgnoreCase("Yes")) && !(_objFormObject.getNGValue("ICICILOMBARD_HT_IAGENT").equalsIgnoreCase("Yes")))
		{
			// System.out.println("Inside the value set GST State: Yogesh");//select a.txtstatename,a.txtstatecode from ng_icici_mst_state a
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("TXTGST_STATE_NAME",localArrayList2.get(0).toString());			
		}
		/******End HT CR-8127-95325-GST-Omniflow development******/
		/*****Start Change related to Application  Proposal no. field in Omni flow HT*****/
		else if((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_CHANNEL_LOAN_TYPE")))
		{
			System.out.println("Inside the value set Change related to Application  Proposal no. field in Omni flow HT: Yogesh");//select a.txtstatename,a.txtstatecode from ng_icici_mst_state a
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("ICICILOMBARD_HT_CHANNEL_LOAN_TYPE",localArrayList2.get(0).toString());
			_objFormObject.setNGValue("ICICILOMBARD_HT_SOURCE_BUSINESS",localArrayList2.get(1).toString());			
		}
		/*****End Change related to Application  Proposal no. field in Omni flow HT*****/
		else if(strSource.equalsIgnoreCase("ICICILOMBARD_HT_BRANCH_ID_UBO_NAME"))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("ICICILOMBARD_HT_BRANCH_ID_UBO_NAME",localArrayList2.get(0).toString());
		}
		/********************** Start MO filteration for Centralised Deal Vertical **********************/
		else if(strSource.equalsIgnoreCase("ICICILOMBARD_HT_SM_NAME"))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("ICICILOMBARD_HT_SM_NAME",localArrayList2.get(0).toString());
			_objFormObject.setNGValue("ICICILOMBARD_HT_SM_ID",localArrayList2.get(1).toString());
			if (_objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_STATUS").equalsIgnoreCase("YES"))
			{				
				_objFormObject.setNGValue("ICICILOMBARD_HT_CEN_SUB_VERTICAL",localArrayList2.get(2).toString());
				_objFormObject.setNGValue("ICICILOMBARD_HT_CEN_PRIM_VERTICAL",localArrayList2.get(3).toString());
			}
		}
		/********************** End MO filteration for Centralised Deal Vertical   **********************/

		
		else if(strSource.equalsIgnoreCase("ICICILOMBARD_HT_BANK_NAME"))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("ICICILOMBARD_HT_BANK_NAME",localArrayList2.get(0).toString());
			_objFormObject.setNGValue("ICICILOMBARD_HT_BANK_CODE",localArrayList2.get(1).toString());
		}
		
		else if(strSource.equalsIgnoreCase("ICICILOMBARD_HT_SUB_PRODUCT"))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("ICICILOMBARD_HT_SUB_PRODUCT",localArrayList2.get(0).toString());
			_objFormObject.setNGValue("ICICILOMBARD_HT_PRODUCT_ABBR_CODE",localArrayList2.get(1).toString());
		}
		
		else if ((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_CENTER_CODE_NAME")))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("ICICILOMBARD_HT_CENTER_CODE_NAME",localArrayList2.get(0).toString());
			//_objFormObject.setNGValue("ICICILOMBARD_HT_PRODUCT_ABBR_CODE",localArrayList2.get(1).toString());
		}
		else
		{}

        /************************** CR-OF-MHT-1314-01 MHTProcess Implementaion Start***********/
        if(strSource.equalsIgnoreCase("MHTSEARCH_STRING"))
		{
			if(_objFormObject.getNGValue("MHT_SEARCH_CRITERIA").equalsIgnoreCase("Manual CN"))
				{
					localArrayList2 = (ArrayList)localArrayList1.get(0);
					String manulCn= localArrayList2.get(0).toString();
					String dealNo= localArrayList2.get(1).toString();
					String cnAcceptance= localArrayList2.get(2).toString();
					////// System.out.println("btnOk_Clicked():MHTSEARCH_STRING start dealNo"+dealNo);
					////// System.out.println("btnOk_Clicked():MHTSEARCH_STRING start manulCn"+manulCn);
					////// System.out.println("btnOk_Clicked():MHTSEARCH_STRING start cnAcceptance"+cnAcceptance);
					if(cnAcceptance != null & cnAcceptance.equalsIgnoreCase("Y"))
					{
						ArrayList dealData = new ArrayList();
						String dealQuery = "select TXT_INTERMEDIARY_NAME,TXT_INTERMEDIARY_CD, txt_deal_id,TXT_DISPLAY_RM_BS,TXT_COVERNOTE_FLAG from (select i.TXT_INTERMEDIARY_NAME as TXT_INTERMEDIARY_NAME,i.TXT_INTERMEDIARY_CD as TXT_INTERMEDIARY_CD, d.txt_deal_id as txt_deal_id,d.TXT_DISPLAY_RM_BS as TXT_DISPLAY_RM_BS,d.TXT_COVERNOTE_FLAG from mv_mht_genmst_intermediary i, mv_mht_gen_deal_detail d where  UPPER(d.txt_deal_id) = UPPER(N'"+dealNo+"') and d.TXT_INTERMEDIARY_CD=i.TXT_INTERMEDIARY_CD ORDER BY i.TXT_INTERMEDIARY_CD desc) where ROWNUM <= 1 ORDER BY TXT_INTERMEDIARY_CD";
						if(dealNo != null && manulCn != null)
						{
							////// System.out.println("btnOk_Clicked():before called dealQuery"+dealQuery);
							dealData = _objFormObject.getNGDataFromDataSource(dealQuery, 5);
							////// System.out.println("btnOk_Clicked():dealData"+dealData);
						}
						if(dealData != null && dealData.size() > 0)
						{
							ArrayList deal1 = new ArrayList();
							deal1 = (ArrayList)dealData.get(0);
							////// System.out.println("btnOk_Clicked():deal1"+deal1);
							_objFormObject.setNGValue("MHT_AGENT_NAME",deal1.get(0).toString());
							////// System.out.println("satish1"+deal1.get(0).toString()+"\t"+deal1.get(0).toString());
							_objFormObject.setNGValue("MHT_AGENT_CODE",deal1.get(1).toString());
							////// System.out.println("satish2"+deal1.get(1).toString()+"\t"+deal1.get(1).toString());
							_objFormObject.setNGValue("MHT_DEAL_NO",deal1.get(2).toString());
							////// System.out.println("satish3"+deal1.get(2).toString()+"\t"+deal1.get(2).toString());
							_objFormObject.setNGValue("MHT_DEAL_STATUS",deal1.get(3).toString());
							////// System.out.println("satish4"+deal1.get(3).toString()+"\t"+deal1.get(3).toString());
							_objFormObject.setNGValue("MHT_COVER_NOTE_TYPE",deal1.get(4).toString());
							// System.out.println("setting value for cover note type satish4 MHT_COVER_NOTE_TYPE"+deal1.get(4).toString()+"\t"+deal1.get(4).toString());
							_objFormObject.setNGValue("MHT_MANUAL_COVERNOTE_NO",manulCn);
							////// System.out.println("satish5 manulCn=="+manulCn);
						}
						else
						{
							JOptionPane.showMessageDialog(null,"Deal Details doesn't Exist, Please Select other Manual CN");
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null,"Manual CN is not received by RM in Pathfinder System ");
					}
				}
				else
				{
					localArrayList2 = (ArrayList)localArrayList1.get(0);
					_objFormObject.setNGValue("MHT_AGENT_NAME",localArrayList2.get(0).toString());
					////// System.out.println("111"+localArrayList2.get(0).toString()+"\t"+localArrayList2.get(0).toString());
					_objFormObject.setNGValue("MHT_AGENT_CODE",localArrayList2.get(1).toString());
					////// System.out.println("111"+localArrayList2.get(1).toString()+"\t"+localArrayList2.get(1).toString());
					_objFormObject.setNGValue("MHT_DEAL_NO",localArrayList2.get(2).toString());
					////// System.out.println("111"+localArrayList2.get(2).toString()+"\t"+localArrayList2.get(2).toString());
					_objFormObject.setNGValue("MHT_DEAL_STATUS",localArrayList2.get(3).toString());
					////// System.out.println("111"+localArrayList2.get(3).toString()+"\t"+localArrayList2.get(3).toString());
					_objFormObject.setNGValue("MHT_COVER_NOTE_TYPE",localArrayList2.get(4).toString());
					////// System.out.println("COVER_NOTE_TYPE"+localArrayList2.get(4).toString()+"\t"+localArrayList2.get(4).toString());
				}

		}

		else if(strSource.equalsIgnoreCase("MHT_IL_LOCATION") || strSource.equalsIgnoreCase("MHT_IL_LOCATION_CODE"))//MHT-PID Process Integration - Search provided on both location name and code
		{
			 localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("MHT_IL_LOCATION",localArrayList2.get(0).toString());
			_objFormObject.setNGValue("MHT_IL_LOCATION_CODE",localArrayList2.get(1).toString());
			_objFormObject.setNGValue("MHT_ZONE",localArrayList2.get(2).toString());
		}
		/*************************** MHT-PID Process Integration ****************************/
		//modified code to fetch vertical code also	
		else if(strSource.equalsIgnoreCase("MHT_PRIMARY_VERTICAL"))
		{
			 localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("MHT_PRIMARY_VERTICAL",localArrayList2.get(0).toString());
			_objFormObject.setNGValue("MHT_PRIMARY_VERTICAL_CODE",localArrayList2.get(1).toString());
		}
		
		else if(strSource.equalsIgnoreCase("MHT_SUB_VERTICAL"))
		{
			 localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("MHT_SUB_VERTICAL",localArrayList2.get(0).toString());
			_objFormObject.setNGValue("MHT_SUB_VERTICAL_CODE",localArrayList2.get(1).toString());			
		}
		/*********************** End MHT-PID Process Integration ****************************/
		
		else if(strSource.equalsIgnoreCase("MHT_PRODUCT_NAME"))
		{
			 localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("MHT_PRODUCT_NAME",localArrayList2.get(0).toString());
			_objFormObject.setNGValue("MHT_PRODUCT_CODE",localArrayList2.get(1).toString());
		}



		else if(strSource.equalsIgnoreCase("MHT_SOURCE_BUSINESS"))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("MHT_SOURCE_BUSINESS",localArrayList2.get(0).toString());
		}

		else if(strSource.equalsIgnoreCase("MHT_CHANNEL_SOURCE"))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("MHT_CHANNEL_SOURCE",localArrayList2.get(0).toString());
		}
		/********************************************** Start MHT Sp Code CR-8127-69652 okClicked******************************/
		else if(strSource.equalsIgnoreCase("MHT_BRANCH_ID"))
		{//yy
			localArrayList2 = (ArrayList)localArrayList1.get(0);

			if(_objFormObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Policy Issuance") && _objFormObject.getNGValue("MHT_TYPE_OF_BUSINESS").equalsIgnoreCase("Intermediary") && _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BANCASSURANCE") && !_objFormObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER") && _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING"))
			{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("MHT_BANK_BRANCH_NAME",localArrayList2.get(1).toString());
			_objFormObject.setNGValue("MHT_BRANCH_ID",localArrayList2.get(0).toString());
			}
			else
			{
			_objFormObject.setNGValue("MHT_BRANCH_ID",localArrayList2.get(0).toString());
			_objFormObject.setNGValue("MHT_BANK_BRANCH_NAME",localArrayList2.get(1).toString());
			_objFormObject.setNGValue("MHT_SP_CODE",localArrayList2.get(2).toString());
			_objFormObject.setNGValue("MHT_SP_NAME",localArrayList2.get(3).toString());
			}
			
		}

		else if(strSource.equalsIgnoreCase("MHT_BANK_BRANCH_NAME"))
		{
			if(_objFormObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Policy Issuance") && _objFormObject.getNGValue("MHT_TYPE_OF_BUSINESS").equalsIgnoreCase("Intermediary") && _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BANCASSURANCE") && !_objFormObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER") && _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING"))
			{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("MHT_BANK_BRANCH_NAME",localArrayList2.get(0).toString());
			_objFormObject.setNGValue("MHT_BRANCH_ID",localArrayList2.get(1).toString());
			}
		}
		else if(strSource.equalsIgnoreCase("MHT_SP_CODE"))
		{
			if(_objFormObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Policy Issuance") && _objFormObject.getNGValue("MHT_TYPE_OF_BUSINESS").equalsIgnoreCase("Intermediary") && _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BANCASSURANCE") && !_objFormObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER") && _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING"))
			{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("MHT_SP_CODE",localArrayList2.get(0).toString());
			_objFormObject.setNGValue("MHT_SP_NAME",localArrayList2.get(1).toString());
			_objFormObject.setNGValue("MHT_SP_PAN",localArrayList2.get(2).toString());
			}
			/******Start SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI *****************/
			else
			{
				// System.out.println("Inside the SP code for BA and KRG");
				if(!((_objFormObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Policy Issuance") && _objFormObject.getNGValue("MHT_TYPE_OF_BUSINESS").equalsIgnoreCase("Direct"))) && (_objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BANCASSURANCE") || _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BA")) && (_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("KRG")) && !(_objFormObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER")))
				{
					// System.out.println("Inside the sp code logic change setting value");
					localArrayList2 = (ArrayList)localArrayList1.get(0);
					_objFormObject.setNGValue("MHT_SP_CODE",localArrayList2.get(0).toString());
					_objFormObject.setNGValue("MHT_SP_NAME",localArrayList2.get(1).toString());
				}
			}
			/*********End SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI ***************/
		}
		/********************************************** End MHT Sp Code CR-8127-69652 okClicked******************************/
		/******Start MHT CR-8127-95325-GST-Omniflow development******/
		//MHT_TXTGST_STATE_NAME value set
		else if((this.strSource != null) && (this.strSource.equals("MHT_TXTGST_STATE_NAME")) && (_objFormObject.getNGValue("MHT_GST_REGISTERED").equalsIgnoreCase("Yes")) && !(_objFormObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER")))
		{
			// System.out.println("Inside the value set GST State: Yogesh");//select a.txtstatename,a.txtstatecode from NG_MHT_MST_GST_STATE a
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("MHT_TXTGST_STATE_NAME",localArrayList2.get(0).toString());			
		}
		/******End MHT CR-8127-95325-GST-Omniflow development******/
		else if(strSource.equalsIgnoreCase("MHT_BRANCH_ID_UBO_NAME"))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("MHT_BRANCH_ID_UBO_NAME",localArrayList2.get(0).toString());
		}

		else if(strSource.equalsIgnoreCase("MHT_SM_NAME"))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("MHT_SM_NAME",localArrayList2.get(0).toString());
			_objFormObject.setNGValue("MHT_SM_ID",localArrayList2.get(1).toString());
			//_objFormObject.setNGLocked("MHT_SM_NAME",false);
			//_objFormObject.setNGLocked("MHT_SM_ID",false);
		}

		else if(strSource.equalsIgnoreCase("MHT_PAYMENT_TYPE1_BANKNAME"))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("MHT_PAYMENT_TYPE1_BANKNAME",localArrayList2.get(0).toString());
			//_objFormObject.setNGValue("ICICILOMBARD_HT_BANK_CODE",localArrayList2.get(1).toString());
		}		
		else if(strSource.equalsIgnoreCase("MHT_PAYMENT_TYPE2_BANKNAME"))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("MHT_PAYMENT_TYPE2_BANKNAME",localArrayList2.get(0).toString());
			//_objFormObject.setNGValue("ICICILOMBARD_HT_BANK_CODE",localArrayList2.get(1).toString());
		}
        else if(strSource.equalsIgnoreCase("MHT_PAYMENT_TYPE3_BANKNAME"))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("MHT_PAYMENT_TYPE3_BANKNAME",localArrayList2.get(0).toString());
			//_objFormObject.setNGValue("ICICILOMBARD_HT_BANK_CODE",localArrayList2.get(1).toString());
		}

		/*************************** MHT-PID Process Integration ****************************/
		else if(strSource.equalsIgnoreCase("MHT_PAYMENT_TYPE1"))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("MHT_PAYMENT_TYPE1",localArrayList2.get(0).toString());
		}
		else if(strSource.equalsIgnoreCase("MHT_PAYMENT_TYPE2"))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("MHT_PAYMENT_TYPE2",localArrayList2.get(0).toString());
		}
        else if(strSource.equalsIgnoreCase("MHT_PAYMENT_TYPE3"))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("MHT_PAYMENT_TYPE3",localArrayList2.get(0).toString());
		}
		/************************* End MHT-PID Process Integration **************************/
		
		else if ((this.strSource != null) && (this.strSource.equals("MHT_CENTER_CODE_NAME")))
		{
			localArrayList2 = (ArrayList)localArrayList1.get(0);
			_objFormObject.setNGValue("MHT_CENTER_CODE_NAME",localArrayList2.get(0).toString());
			
		}
               /************************** CR-OF-MHT-1314-01 MHTProcess Implementaion End***********/
	
    }

    //----------------------------------------------------------------------------------------------------
    //Function Name 			: btnCancel_Clicked
    //Date Written (DD/MM/YYYY)	: 14/04/2009
    //Author					: Vikas Tyagi
    //Input Parameters			: e
    //Output Parameters			: none
    //Return Values				: none
    //Description				: Hanldes ckisk event of Cancel.
    //----------------------------------------------------------------------------------------------------
    public void btnCancel_Clicked(NGEvent e)
    {
		// bhagat 
/*****Start CR-8001-87420-Green File Digitization*****/
			
		if((this.strSource != null) && (this.strSource.equals("CPI_RM_ID")))
		{
			if(_objFormObject.getNGValue("CPI_IS_PF_FETCH").equalsIgnoreCase("Yes"))
			{				
				_objFormObject.setNGValue("CPI_RM_ID","");
				_objFormObject.setNGValue("CPI_RM_NAME","");
				_objFormObject.setNGValue("CPI_PRIMARY_VERTICAL","");
				_objFormObject.setNGValue("CPI_PRIMARY_VERTICAL_CODE","");
				_objFormObject.setNGValue("CPI_PRIMARY_SUB_VERTICAL","");
				_objFormObject.setNGValue("CPI_SUB_VERTICAL_CODE","");
			}
			else
			{
				_objFormObject.setNGValue("CPI_RM_ID","");
				_objFormObject.setNGValue("CPI_RM_NAME","");
				/***************************************Start Yogesh Marine CR**************/
				if((_objFormObject.getNGValue("CPI_PROCESS_TYPE").equalsIgnoreCase("Corporate Policy Insurance")) && (_objFormObject.getNGValue("CPI_QUOTE_SYS_ID") != null || !_objFormObject.getNGValue("CPI_QUOTE_SYS_ID").equalsIgnoreCase("")))
				{
					// System.out.println("Inside the btn cancel cpi_rm_id Yogesh");
					_objFormObject.setNGValue("CORP_RM_NAME","");
				}
				/***************************************End Yogesh Marine CR**************/
			}
		}
		
		//additional cit rm id
		if ((this.strSource != null) && (this.strSource.equals("CPI_ADDTNL_CIT_RM_ID")))
		{
			// System.out.println(this.strSource+ "btnCancel_Clicked");
			_objFormObject.setNGValue("CPI_ADDTNL_CIT_RM_ID","");
			_objFormObject.setNGValue("CPI_ADDTNL_CIT_RM_NAME","");			
		}
		//uw emp id
		if ((this.strSource != null) && (this.strSource.equals("CPI_UW_EMP_ID")))
		{
			// System.out.println(this.strSource+ "btnCancel_Clicked");
			_objFormObject.setNGValue("CPI_UW_EMP_ID","");
			_objFormObject.setNGValue("CPI_UW_NAME","");			
		}
		//secondary mo id
		if ((this.strSource != null) && (this.strSource.equals("CPI_SECONDARY_MO_ID")))
		{
			// System.out.println(this.strSource+" btnCancel_Clicked");			
			_objFormObject.setNGValue("CPI_SECONDARY_MO_ID","");
			_objFormObject.setNGValue("CPI_SECONDARY_MO_NAME","");			
		}
		//prev policy no
		if ((this.strSource != null) && (this.strSource.equals("CPI_PREV_POLICY_NO")))
		{
			// System.out.println(this.strSource+" btnCancel_Clicked");			
			_objFormObject.setNGValue("CPI_PREV_POLICY_NO","");
			_objFormObject.setNGValue("CPI_CUSTOMER_ID","");
			_objFormObject.setNGValue("CPI_CUSTOMER_NAME","");
		}
		//base policy no
		if ((this.strSource != null) && (this.strSource.equals("CPI_POLICY_NUMBER_BASE")))
		{
			// System.out.println(this.strSource+" btnCancel_Clicked");		
			_objFormObject.setNGValue("CPI_POLICY_NUMBER_BASE","");
			_objFormObject.setNGValue("CPI_POLICY_START_DATE","");
			_objFormObject.setNGValue("CPI_CUSTOMER_ID","");
			_objFormObject.setNGValue("CPI_CUSTOMER_NAME","");
			_objFormObject.setNGValue("CPI_PRODUCT_NAME","");
			_objFormObject.setNGValue("CPI_PRODUCT_IRDACODE","");
		}
		/*****End CR-8001-87420-Green File Digitization*****/

		/**************************************Client Registration CR Start*****************************************************/
		if((this.strSource != null) && (this.strSource.equals("CPI_POLICYNO_SEARCH")))
		{
			_objFormObject.setNGValue("CPI_POLICYNO_SEARCH","");
			_objFormObject.setNGValue("CPI_PRODUCT_NAME","");
			_objFormObject.setNGValue("CPI_RM_ID","");
			_objFormObject.setNGValue("CPI_RM_NAME","");
			_objFormObject.setNGValue("CPI_IL_LOCATION","");
			_objFormObject.setNGValue("CPI_PRIMARY_VERTICAL","");
			_objFormObject.setNGValue("CPI_PRIMARY_SUB_VERTICAL","");
			_objFormObject.setNGValue("CPI_CUSTOMER_ID","");
			_objFormObject.setNGValue("CPI_NAME_OF_BROKER_AGENT","");
			_objFormObject.setNGValue("CPI_CUSTOMER_NAME","");
			_objFormObject.setNGValue("CPI_MODE_OF_PAYMENT","");
			_objFormObject.setNGValue("CPI_INST_NO_CD_NO_DEP_SLIP_NO","");
			_objFormObject.setNGValue("CPI_PRODUCT_IRDACODE","");
			_objFormObject.setNGValue("CPI_PRIMARY_VERTICAL_CODE","");
			_objFormObject.setNGValue("CPI_SUB_VERTICAL_CODE","");
		}
		/**************************************Client Registration CR End*****************************************************/		
		/********************CR-OMCPI-1314-03 CPU DataWashing Start**********************/
		if((this.strSource != null) && (this.strSource.equals("CPI_CPU_ASSIGN_TO")))
		{
			_objFormObject.setNGValue("CPI_CPU_ASSIGN_TO","");
			//_objFormObject.setNGValue("CPI_RM_NAME","");
		}
		/********************CR-OMCPI-1314-03 CPU DataWashing END**********************/

		/********************* CR 45 Network Partner *****************************/
		if((this.strSource != null) && (this.strSource.equals("CPI_NETWORK_PARTNER_NAME")))
		{
			_objFormObject.setNGValue("CPI_NETWORK_PARTNER_NAME","");
		}
		/********************* End CR 45 Network Partner *************************/
		
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_RM_Name")))
		{
			_objFormObject.setNGValue("CPI_RM_ID","");
			_objFormObject.setNGValue("CPI_RM_NAME","");
		}
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_NAME_OF_BROKER_AGENT")))
		{
			_objFormObject.setNGValue("CPI_NAME_OF_BROKER_AGENT","");
			/*************************** PID-CPI process changes ***************************/
			_objFormObject.setNGValue("CPI_AGENT_CODE","");
			/*********************** End PID-CPI process changes ***************************/
		}
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_IL_Location")))
		{
			_objFormObject.setNGValue("CPI_IL_Location","");
		}
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("Corp_IL_Location")))
		{
			_objFormObject.setNGValue("Corp_IL_Location","");
		}
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("End_IL_Location")))
		{
			_objFormObject.setNGValue("End_IL_Location","");
		}
		/*************************** PID-CPI process changes ***************************/
		/**************************** CPI moDE_OF_PAYMENT****************/
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_moDE_OF_PAYMENT")))
		{
			_objFormObject.setNGValue("CPI_moDE_OF_PAYMENT","");
		}
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_MODE_OF_PAYMENT2")))
		{
			_objFormObject.setNGValue("CPI_MODE_OF_PAYMENT2","");
		}
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_MODE_OF_PAYMENT3")))
		{
			_objFormObject.setNGValue("CPI_MODE_OF_PAYMENT3","");
		}		
		/************************End  CPI moDE_OF_PAYMENT****************/
		/*********************** End PID-CPI process changes ***************************/
		
		/**************************** CPI Hypothecated_to****************/
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_HYPOTHECATED_TO")))
		{
			_objFormObject.setNGValue("CPI_HYPOTHECATED_TO","");
		}
		/************************End  CPI Hypothecated_to****************/	

		/************************* CPI URN CR 8001-61339 Multiple Changes CR *****************************/ 
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_Exception_To_MH")))
		{
			_objFormObject.setNGValue("CPI_Exception_To_MH","");
		}
/*************************End CPI URN CR 8001-61339 Multiple Changes CR **************************/


			
		
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_SPECIFIED_PERSON"))  || (this.strSource.equalsIgnoreCase("Corp_SP_Name")))
		{
			_objFormObject.setNGValue("CPI_SPECIFIED_PERSON","");
			_objFormObject.setNGValue("Corp_SP_Name","");
		}
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_PRIMARY_VERTICAL")))
		{
			_objFormObject.setNGValue("CPI_PRIMARY_VERTICAL","");
			_objFormObject.setNGValue("CPI_PRIMARY_VERTICAL_CODE","");//PID_CPI process changes
		}
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_PRIMARY_SUB_VERTICAL")))
		{
			_objFormObject.setNGValue("CPI_PRIMARY_SUB_VERTICAL","");
			_objFormObject.setNGValue("CPI_SUB_VERTICAL_CODE","");//PID_CPI process changes
		}
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_SECONDARY_SUB_VERTICAL")))
		{
			_objFormObject.setNGValue("CPI_SECONDARY_SUB_VERTICAL","");
		}
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_SECONDARY_VERTICAL"))) //CR-8001-70893 Marine CR
		{
			_objFormObject.setNGValue("CPI_SECONDARY_VERTICAL","");
		}		
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_SOURCE_NAME")))
		{
			_objFormObject.setNGValue("CPI_SOURCE_NAME","");
		}
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CORP_SOURCE_NAME")))
		{
			_objFormObject.setNGValue("CORP_SOURCE_NAME","");
		}
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_CHANNEL_NAME")))
		{
			_objFormObject.setNGValue("CPI_CHANNEL_NAME","");
		}
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CORP_CHANNEL_NAME")))
		{
			_objFormObject.setNGValue("CORP_CHANNEL_NAME","");
		}
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_BRANCH_NAME")))
		{
			_objFormObject.setNGValue("CPI_BRANCH_NAME","");
			/*************************** PID-CPI process changes ***************************/
			_objFormObject.setNGValue("CPI_BRANCH_ID","");
			_objFormObject.setNGValue("CPI_SP_NAME1","");
			_objFormObject.setNGValue("CPI_SP_CODE1","");
			_objFormObject.setNGValue("CPI_SP_NAME2","");
			_objFormObject.setNGValue("CPI_SP_CODE2","");
			_objFormObject.setNGValue("CPI_SP_NAME3","");
			_objFormObject.setNGValue("CPI_SP_CODE3","");
			_objFormObject.setNGValue("CPI_SP_NAME4","");
			_objFormObject.setNGValue("CPI_SP_CODE4","");

			/*********************** End PID-CPI process changes ***************************/
			
		}
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CORP_BRANCH_NAME")))
		{
			_objFormObject.setNGValue("CORP_BRANCH_NAME","");
			/*************************** PID-CPI process changes ***************************/
			_objFormObject.setNGValue("CPI_BRANCH_ID","");
			_objFormObject.setNGValue("CPI_SP_NAME1","");
			_objFormObject.setNGValue("CPI_SP_CODE1","");
			_objFormObject.setNGValue("CPI_SP_NAME2","");
			_objFormObject.setNGValue("CPI_SP_CODE2","");
			_objFormObject.setNGValue("CPI_SP_NAME3","");
			_objFormObject.setNGValue("CPI_SP_CODE3","");
			_objFormObject.setNGValue("CPI_SP_NAME4","");
			_objFormObject.setNGValue("CPI_SP_CODE4","");

			/*********************** End PID-CPI process changes ***************************/
		}
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_naME_OF_LEADER")))
		{
			_objFormObject.setNGValue("CPI_naME_OF_LEADER","");
		}		
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_PRODUCT_NAME")))
		{
			_objFormObject.setNGValue("CPI_PRODUCT_NAME","");
			_objFormObject.setNGValue("CPI_PRODUCT_IRDACODE","");//Vendor login & weather Product
		}	
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("Corp_PRODUCT_NAME")))
		{
			_objFormObject.setNGValue("Corp_PRODUCT_NAME","");
			_objFormObject.setNGValue("CPI_PRODUCT_IRDACODE","");//Vendor login & weather Product
		}		
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("End_PRODUCT_NAME")))
		{
			_objFormObject.setNGValue("End_PRODUCT_NAME","");
			_objFormObject.setNGValue("CPI_PRODUCT_IRDACODE","");//Vendor login & weather Product
		}			
		/*if(strSource.equalsIgnoreCase("SEARCH_STRING"))
		{
			_objFormObject.setNGValue("ICICILOMBARD_HT_AGENT_NAME","");
			_objFormObject.setNGValue("ICICILOMBARD_HT_AGENT_CODE","");
			_objFormObject.setNGValue("ICICILOMBARD_HT_DEAL_STATUS","");
			
		}*/
		/*
		if(strSource.equalsIgnoreCase("ICICILOMBARD_HT_IL_LOCATION"))
		{
			_objFormObject.setNGValue("ICICILOMBARD_HT_IL_LOCATION","");
			_objFormObject.setNGValue("ICICILOMBARD_HT_IL_LOCATION_CODE","");
		}		
		else if(strSource.equalsIgnoreCase("ICICILOMBARD_HT_SOURCE_BUSINESS"))
		{	
			_objFormObject.setNGValue("ICICILOMBARD_HT_SOURCE_BUSINESS","");
		}
		
		else if(strSource.equalsIgnoreCase("ICICILOMBARD_HT_CHANNEL_SOURCE"))
		{
			_objFormObject.setNGValue("ICICILOMBARD_HT_CHANNEL_SOURCE","");
		}
		else if(strSource.equalsIgnoreCase("ICICILOMBARD_HT_BANK_BRANCH_NAME"))
		{
			_objFormObject.setNGValue("ICICILOMBARD_HT_BANK_BRANCH_NAME","");
		}
		
		else if(strSource.equalsIgnoreCase("ICICILOMBARD_HT_BRANCH_ID_UBO_NAME"))
		{
			_objFormObject.setNGValue("ICICILOMBARD_HT_BRANCH_ID_UBO_NAME","");
		}
		
		else if(strSource.equalsIgnoreCase("ICICILOMBARD_HT_SM_NAME"))
		{
			_objFormObject.setNGValue("ICICILOMBARD_HT_SM_NAME","");
			_objFormObject.setNGValue("ICICILOMBARD_HT_SM_ID","");
		}
		
		else if(strSource.equalsIgnoreCase("ICICILOMBARD_HT_BANK_NAME"))
		{
			_objFormObject.setNGValue("ICICILOMBARD_HT_BANK_NAME","");
			_objFormObject.setNGValue("ICICILOMBARD_HT_BANK_CODE","");
		}
		
		else if(strSource.equalsIgnoreCase("ICICILOMBARD_HT_SUB_PRODUCT"))
		{
			_objFormObject.setNGValue("ICICILOMBARD_HT_SUB_PRODUCT","");
			_objFormObject.setNGValue("ICICILOMBARD_HT_PRODUCT_ABBR_CODE","");
		}
		
		else if ((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_CENTER_CODE_NAME")))
		{
			_objFormObject.setNGValue("ICICILOMBARD_HT_CENTER_CODE_NAME","");
			//_objFormObject.setNGValue("ICICILOMBARD_HT_PRODUCT_ABBR_CODE",localArrayList2.get(1).toString());
		}
		*/
                /************************** CR-OF-MHT-1314-01 MHTProcess Implementaion Start***********/


        if(strSource.equalsIgnoreCase("SEARCH_STRING"))
		{
			_objFormObject.setNGValue("ICICILOMBARD_HT_AGENT_NAME","");
			_objFormObject.setNGValue("ICICILOMBARD_HT_AGENT_CODE","");
			_objFormObject.setNGValue("ICICILOMBARD_HT_DEAL_STATUS","");

		}

		if(strSource.equalsIgnoreCase("MHT_IL_LOCATION") || strSource.equalsIgnoreCase("MHT_IL_LOCATION_CODE"))//MHT-PID Process Integration - Search provided on both location name and code
		{
			_objFormObject.setNGValue("MHT_IL_LOCATION","");
			_objFormObject.setNGValue("MHT_IL_LOCATION_CODE","");
			_objFormObject.setNGValue("MHT_ZONE","");
		}
		/*************************** MHT-PID Process Integration ****************************/
		//modified code to fetch vertical code also
		if(strSource.equalsIgnoreCase("MHT_PRIMARY_VERTICAL"))
		{
			_objFormObject.setNGValue("MHT_PRIMARY_VERTICAL","");
			_objFormObject.setNGValue("MHT_PRIMARY_VERTICAL_CODE","");
		
		}
		else if(strSource.equalsIgnoreCase("MHT_SUB_VERTICAL"))
		{
			_objFormObject.setNGValue("MHT_SUB_VERTICAL","");
			_objFormObject.setNGValue("MHT_SUB_VERTICAL_CODE","");			
		}
		/*********************** End MHT-PID Process Integration ****************************/
		else if(strSource.equalsIgnoreCase("MHT_PRODUCT_NAME"))
		{
			_objFormObject.setNGValue("MHT_PRODUCT_NAME","");
			_objFormObject.setNGValue("MHT_PRODUCT_CODE","");
		}
		else if(strSource.equalsIgnoreCase("MHT_SOURCE_BUSINESS"))
		{
			_objFormObject.setNGValue("MHT_SOURCE_BUSINESS","");
		}
		else if(strSource.equalsIgnoreCase("MHT_CHANNEL_SOURCE"))
		{
			_objFormObject.setNGValue("MHT_CHANNEL_SOURCE","");
		}
		else if(strSource.equalsIgnoreCase("MHT_BANK_BRANCH_NAME"))
		{
			_objFormObject.setNGValue("MHT_BANK_BRANCH_NAME","");
			////// System.out.println("Clear Bank Branch Name");
		}
		else if(strSource.equalsIgnoreCase("MHT_BRANCH_ID"))
		{
			_objFormObject.setNGValue("MHT_BRANCH_ID","");
		}
		else if(strSource.equalsIgnoreCase("MHT_SP_CODE"))
		{
			_objFormObject.setNGValue("MHT_SP_CODE","");
			_objFormObject.setNGValue("MHT_SP_NAME","");
			_objFormObject.setNGValue("MHT_SP_PAN","");
		}
		else if(strSource.equalsIgnoreCase("MHT_BRANCH_ID_UBO_NAME"))
		{
			_objFormObject.setNGValue("MHT_BRANCH_ID_UBO_NAME","");
		}
		else if(strSource.equalsIgnoreCase("MHT_SM_NAME"))
		{
			_objFormObject.setNGValue("MHT_SM_NAME","");
			_objFormObject.setNGValue("MHT_SM_ID","");
		}
		else if(strSource.equalsIgnoreCase("MHT_PAYMENT_TYPE1_BANKNAME"))
		{
			_objFormObject.setNGValue("MHT_PAYMENT_TYPE1_BANKNAME","");
			//_objFormObject.setNGValue("ICICILOMBARD_HT_BANK_CODE","");
		}
        else if(strSource.equalsIgnoreCase("MHT_PAYMENT_TYPE2_BANKNAME"))
		{
			_objFormObject.setNGValue("MHT_PAYMENT_TYPE2_BANKNAME","");
			//_objFormObject.setNGValue("ICICILOMBARD_HT_BANK_CODE","");
		}
        else if(strSource.equalsIgnoreCase("MHT_PAYMENT_TYPE3_BANKNAME"))
		{
			_objFormObject.setNGValue("MHT_PAYMENT_TYPE3_BANKNAME","");
			//_objFormObject.setNGValue("ICICILOMBARD_HT_BANK_CODE","");
		}
		/*************************** MHT-PID Process Integration ****************************/
		else if(strSource.equalsIgnoreCase("MHT_PAYMENT_TYPE1"))
		{
			_objFormObject.setNGValue("MHT_PAYMENT_TYPE1","");
		}
		else if(strSource.equalsIgnoreCase("MHT_PAYMENT_TYPE2"))
		{
			_objFormObject.setNGValue("MHT_PAYMENT_TYPE2","");
		}
		else if(strSource.equalsIgnoreCase("MHT_PAYMENT_TYPE3"))
		{
			_objFormObject.setNGValue("MHT_PAYMENT_TYPE3","");
		}
		/************************* End MHT-PID Process Integration **************************/

		else if ((this.strSource != null) && (this.strSource.equals("MHT_CENTER_CODE_NAME")))
		{
			_objFormObject.setNGValue("MHT_CENTER_CODE_NAME","");
		}
        /************************** CR-OF-MHT-1314-01 MHTProcess Implementaion End***********/
	    _objPickList.setVisible(false);
    }
	
	public void btnSearch_Clicked(NGEvent e)
	{
		NGPickList localNGPickList = (NGPickList)e.getSource();
    	this.objProcessData = new XMLParser();
    	this.objProcessData.setInputXML(this._objFormObject.getWFGeneralData());
		String fieldValue=localNGPickList.getSearchFilterValue();
		_iTotalRecord=getTotalRecord(fieldValue);
	//	////// System.out.println("str value----"+strSource);
		ArrayList alRows = new ArrayList(); 
		_iBatchNo = 1;
		boolean result;
		/*****Start CR-8001-87420-Green File Digitization*****/

		//============== CPI CR Quote SYS===============	
		//select txt_hr_ref_no,txt_employee_name,prim_vert,prim_vert_cd,prim_subvert,prim_subvert_cd from mv_cpi_vw_employee_omni	CPI_PRIMARY_SUB_VERTICAL,CPI_SUB_VERTICAL_CODE		
		if((this.strSource != null) && (this.strSource.equals("CPI_RM_ID")))
		{
			if(_objFormObject.getNGValue("CPI_IS_PF_FETCH").equalsIgnoreCase("Yes"))
			{
				colCount=6;
				if(fieldValue!=null)
				{
					this._strQuery ="select TXT_HR_REF_NO,TXT_EMPLOYEE_NAME,PRIM_VERT,PRIM_VERT_CD,PRIM_SUBVERT,PRIM_SUBVERT_CD FROM (select distinct TXT_HR_REF_NO,TXT_EMPLOYEE_NAME,PRIM_VERT,PRIM_VERT_CD,PRIM_SUBVERT,PRIM_SUBVERT_CD from MV_CPI_VW_EMPLOYEE_OMNI where  UPPER(TXT_HR_REF_NO) like UPPER(N'"+fieldValue+"%') ORDER BY TXT_HR_REF_NO ) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					this._strQuery ="select TXT_HR_REF_NO,TXT_EMPLOYEE_NAME,PRIM_VERT,PRIM_VERT_CD,PRIM_SUBVERT,PRIM_SUBVERT_CD FROM (select distinct TXT_HR_REF_NO,TXT_EMPLOYEE_NAME,PRIM_VERT,PRIM_VERT_CD,PRIM_SUBVERT,PRIM_SUBVERT_CD from MV_CPI_VW_EMPLOYEE_OMNI ORDER BY TXT_HR_REF_NO) where ROWNUM <= " + this._iBatchSize;
				}
			}
			else
			{
				colCount=2;
				if(fieldValue!=null)
				{
					this._strQuery ="select EMP_CODE, RM_NAME FROM (select distinct EMP_CODE, RM_NAME from NG_RM_MASTER where  UPPER(EMP_CODE) like UPPER(N'"+fieldValue+"%') ORDER BY EMP_CODE ) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					this._strQuery ="select EMP_CODE, RM_NAME FROM (select distinct EMP_CODE, RM_NAME from NG_RM_MASTER ORDER BY EMP_CODE) where ROWNUM <= " + this._iBatchSize;
				}
			}
			// System.out.println("_Search_strQuery "+_strQuery);
		}
		
		//sandeep yadav
		//============== End CPI CR Quote SYS===============
		
		
		//TXT_HR_REF_NO,TXT_EMPLOYEE_NAME 
		if((this.strSource != null) && (this.strSource.equals("CPI_ADDTNL_CIT_RM_ID") || this.strSource.equals("CPI_UW_EMP_ID") || this.strSource.equals("CPI_SECONDARY_MO_ID")))
		{
			colCount=2;
			// System.out.println(this.strSource+" btnSearch_Clicked....");
			if(fieldValue!=null)
			{
				this._strQuery ="select TXT_HR_REF_NO,TXT_EMPLOYEE_NAME FROM (select distinct TXT_HR_REF_NO,TXT_EMPLOYEE_NAME from MV_CPI_VW_EMPLOYEE_OMNI where  UPPER(TXT_HR_REF_NO) like UPPER(N'"+fieldValue+"%') ORDER BY TXT_HR_REF_NO ) where ROWNUM <= " + this._iBatchSize;
			}
			else
			{
				this._strQuery ="select TXT_HR_REF_NO,TXT_EMPLOYEE_NAME FROM (select distinct TXT_HR_REF_NO,TXT_EMPLOYEE_NAME from MV_CPI_VW_EMPLOYEE_OMNI ORDER BY TXT_HR_REF_NO) where ROWNUM <= " + this._iBatchSize;
			}
			// System.out.println("_Search_strQuery "+_strQuery);
		}
		//uw_emp_id
		/*if((this.strSource != null) && (this.strSource.equals("CPI_UW_EMP_ID")))
		{
			colCount=2;
			// System.out.println(this.strSource+" btnSearch_Clicked....");
			if(fieldValue!=null)
			{
				this._strQuery ="select TXT_HR_REF_NO,TXT_EMPLOYEE_NAME FROM (select distinct TXT_HR_REF_NO,TXT_EMPLOYEE_NAME from MV_CPI_VW_EMPLOYEE_OMNI where  UPPER(TXT_HR_REF_NO) like UPPER(N'"+fieldValue+"%') ORDER BY TXT_HR_REF_NO ) where ROWNUM <= " + this._iBatchSize;
			}
			else
			{
				this._strQuery ="select TXT_HR_REF_NO,TXT_EMPLOYEE_NAME FROM (select distinct TXT_HR_REF_NO,TXT_EMPLOYEE_NAME from MV_CPI_VW_EMPLOYEE_OMNI ORDER BY TXT_HR_REF_NO) where ROWNUM <= " + this._iBatchSize;
			}
			// System.out.println("_Search_strQuery "+_strQuery);
		}*/
		//prev policy no
		if((this.strSource != null) && (this.strSource.equals("CPI_PREV_POLICY_NO")))
		{
			colCount=3;
			// System.out.println(this.strSource+" btnSearch_Clicked....");
			if(fieldValue!=null)
			{
				this._strQuery ="select TXT_POLICY_NO_CHAR,TXT_CUSTOMER_ID,TXT_CUSTOMER_NAME FROM (select GEN.TXT_POLICY_NO_CHAR,TXT_CUSTOMER_ID,TXT_CUSTOMER_NAME FROM GEN_PROP_INFORMATION_TAB@omnitopf GEN,UW_PRODUCT_MASTER@omnitopf UPM where GEN.NUM_PRODUCT_CODE = UPM.PRODUCTCODE and GEN.TXT_POLICY_NO_CHAR is not null and GEN.TXT_POLICY_NO_CHAR='"+fieldValue+"' ORDER BY GEN.TXT_POLICY_NO_CHAR ) where ROWNUM <= " + this._iBatchSize;
			}
			else
			{
				this._strQuery ="select TXT_POLICY_NO_CHAR,TXT_CUSTOMER_ID,TXT_CUSTOMER_NAME FROM (select GEN.TXT_POLICY_NO_CHAR,TXT_CUSTOMER_ID,TXT_CUSTOMER_NAME FROM GEN_PROP_INFORMATION_TAB@omnitopf GEN,UW_PRODUCT_MASTER@omnitopf UPM where GEN.NUM_PRODUCT_CODE = UPM.PRODUCTCODE and GEN.TXT_POLICY_NO_CHAR is not null ORDER BY GEN.TXT_POLICY_NO_CHAR) where ROWNUM <= " + this._iBatchSize;
			}
			// System.out.println("_Search_strQuery "+_strQuery);
		}
		//base policy no
		if((this.strSource != null) && (this.strSource.equals("CPI_POLICY_NUMBER_BASE")))
		{
			colCount=6;
			// System.out.println(this.strSource+" btnSearch_Clicked....");
			if(fieldValue!=null)
			{
				this._strQuery ="select TXT_POLICY_NO_CHAR,DAT_POLICY_EFF_FROMDATE,TXT_CUSTOMER_ID,TXT_CUSTOMER_NAME,PRODUCTNAME,NUM_IL_PRODUCT_CODE FROM (select GEN.TXT_POLICY_NO_CHAR,DAT_POLICY_EFF_FROMDATE,TXT_CUSTOMER_ID,TXT_CUSTOMER_NAME,UPM.PRODUCTNAME,UPM.NUM_IL_PRODUCT_CODE from GEN_PROP_INFORMATION_TAB@omnitopf GEN,UW_PRODUCT_MASTER@omnitopf UPM where GEN.NUM_PRODUCT_CODE = UPM.PRODUCTCODE and GEN.TXT_POLICY_NO_CHAR is not null and GEN.TXT_POLICY_NO_CHAR='"+fieldValue+"' ORDER BY GEN.TXT_POLICY_NO_CHAR ) where ROWNUM <= " + this._iBatchSize;
			}
			else
			{
				this._strQuery ="select TXT_POLICY_NO_CHAR,DAT_POLICY_EFF_FROMDATE,TXT_CUSTOMER_ID,TXT_CUSTOMER_NAME,PRODUCTNAME,NUM_IL_PRODUCT_CODE FROM (select GEN.TXT_POLICY_NO_CHAR,DAT_POLICY_EFF_FROMDATE,TXT_CUSTOMER_ID,TXT_CUSTOMER_NAME,UPM.PRODUCTNAME,UPM.NUM_IL_PRODUCT_CODE from GEN_PROP_INFORMATION_TAB@omnitopf GEN,UW_PRODUCT_MASTER@omnitopf UPM where GEN.NUM_PRODUCT_CODE = UPM.PRODUCTCODE and GEN.TXT_POLICY_NO_CHAR is not null ORDER BY GEN.TXT_POLICY_NO_CHAR) where ROWNUM <= " + this._iBatchSize;
			}
			// System.out.println("_Search_strQuery "+_strQuery);
		}
		
		/*****End CR-8001-87420-Green File Digitization*****/

		/**************************************Client Registration CR Start*****************************************************/
		if((this.strSource != null) && (this.strSource.equals("CPI_POLICYNO_SEARCH")))
		{
			colCount=2;
			if(fieldValue!=null)
			{
				this._strQuery ="select POLICY_NUMBER,PRODUCT_NAME,CST_RM_EMP_ID,CST_RM_NAME,IL_LOCATION,PRIMARY_VERTICAL,PRIMARY_SUB_VERTICAL,CUSTOMER_ID,BROKER_AGENT_NAME,CLIENT_NAME,PAYMENT_MODE,CD_ACCOUNT_NUMBER,PRODUCT_IRDACODE,PRIMARY_VERTICAL_CODE,PRIMARY_SUB_VERTICAL_CODE FROM (select distinct POLICY_NUMBER,PRODUCT_NAME,CST_RM_EMP_ID,CST_RM_NAME,IL_LOCATION,PRIMARY_VERTICAL,PRIMARY_SUB_VERTICAL,CUSTOMER_ID,BROKER_AGENT_NAME,CLIENT_NAME,PAYMENT_MODE,CD_ACCOUNT_NUMBER,PRODUCT_IRDACODE,PRIMARY_VERTICAL_CODE,PRIMARY_SUB_VERTICAL_CODE from NG_CPI_CLIENT_REG_MST where  UPPER(POLICY_NUMBER) like UPPER(N'"+fieldValue+"%') AND UPPER(REGNO_STATUS) = 'APPROVED' ORDER BY POLICY_NUMBER ) where ROWNUM <= " + this._iBatchSize;
			}
			else
			{
				this._strQuery ="select POLICY_NUMBER,PRODUCT_NAME,CST_RM_EMP_ID,CST_RM_NAME,IL_LOCATION,PRIMARY_VERTICAL,PRIMARY_SUB_VERTICAL,CUSTOMER_ID,BROKER_AGENT_NAME,CLIENT_NAME,PAYMENT_MODE,CD_ACCOUNT_NUMBER,PRODUCT_IRDACODE,PRIMARY_VERTICAL_CODE,PRIMARY_SUB_VERTICAL_CODE FROM (select distinct POLICY_NUMBER,PRODUCT_NAME,CST_RM_EMP_ID,CST_RM_NAME,IL_LOCATION,PRIMARY_VERTICAL,PRIMARY_SUB_VERTICAL,CUSTOMER_ID,BROKER_AGENT_NAME,CLIENT_NAME,PAYMENT_MODE,CD_ACCOUNT_NUMBER,PRODUCT_IRDACODE,PRIMARY_VERTICAL_CODE,PRIMARY_SUB_VERTICAL_CODE from NG_CPI_CLIENT_REG_MST where UPPER(REGNO_STATUS) = 'APPROVED' ORDER BY POLICY_NUMBER) where ROWNUM <= " + this._iBatchSize;
			}
			// System.out.println("_Search_strQuery "+_strQuery);
		}
		/**************************************Client Registration CR End*****************************************************/
		/********************CR-OMCPI-1314-03 CPU DataWashing Start**********************/
		if((this.strSource != null) && (this.strSource.equals("CPI_CPU_ASSIGN_TO")))
		{
			colCount=2;
			/**** Start CR-8001-70893 Marine CR *******************************/
			String prodCategory="";
			prodCategory = _objFormObject.getWFActivityName().equalsIgnoreCase("CPU_Assignment")?"DW_Health":"Marine";
			if(fieldValue!=null)
			{
				//this._strQuery ="select EMP_ID, EMP_NAME FROM (select distinct EMP_ID, EMP_NAME from NG_CPI_DW_ESCALATION_MASTER where UPPER(EMP_ID) like UPPER(N'"+fieldValue+"%') ORDER BY EMP_ID ) where ROWNUM <= " + this._iBatchSize;
				this._strQuery ="select EMP_ID, EMP_NAME FROM (select distinct EMP_ID, EMP_NAME from NG_CPI_DW_ESCALATION_MASTER where prod_category = '"+prodCategory+"' and UPPER(EMP_ID) like UPPER(N'"+fieldValue+"%') ORDER BY EMP_ID ) where ROWNUM <= " + this._iBatchSize;
			}
			else
			{
				//this._strQuery ="select EMP_ID, EMP_NAME FROM (select distinct EMP_ID, EMP_NAME from NG_CPI_DW_ESCALATION_MASTER ORDER BY EMP_ID) where ROWNUM <= " + this._iBatchSize;
				this._strQuery ="select EMP_ID, EMP_NAME FROM (select distinct EMP_ID, EMP_NAME from NG_CPI_DW_ESCALATION_MASTER where prod_category = '"+prodCategory+"' ORDER BY EMP_ID) where ROWNUM <= " + this._iBatchSize;
			}
			/**** ENd CR-8001-70893 Marine CR *********************************/
			////// System.out.println("_Search_strQuery "+_strQuery);
		}
		/********************CR-OMCPI-1314-03 CPU DataWashing END**********************/
		
		/********************* CR 45 Network Partner *****************************/
		if((this.strSource != null) && (this.strSource.equals("CPI_NETWORK_PARTNER_NAME")))
		{
			colCount=2;
			if(fieldValue!=null)
			{
				this._strQuery ="SELECT NETWORK_PARTNER_NAME FROM (select DISTINCT NETWORK_PARTNER_NAME from NG_CPI_NTWRK_PARTNER_MASTER where  UPPER(NETWORK_PARTNER_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY NETWORK_PARTNER_NAME ) where ROWNUM <= " + this._iBatchSize;
			}
			else
			{
				this._strQuery ="SELECT NETWORK_PARTNER_NAME FROM (select DISTINCT NETWORK_PARTNER_NAME from NG_CPI_NTWRK_PARTNER_MASTER ORDER BY NETWORK_PARTNER_NAME) where ROWNUM <= " + this._iBatchSize;
			}
			////// System.out.println("_Search_strQuery "+_strQuery);
		}
		/********************* End CR 45 Network Partner *************************/
		
		/******************  CO Insurance CR 18 *************************/
			//satish
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_RM_Name")))
		{
			colCount=2;
			if(fieldValue!=null)
			{
				this._strQuery ="SELECT RM_NAME,EMP_CODE  FROM (select DISTINCT  RM_NAME,EMP_CODE from NG_RM_MASTER where  UPPER(RM_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY EMP_CODE ) where ROWNUM <= " + this._iBatchSize;
			}
			else
			{
				this._strQuery ="SELECT EMP_CODE, RM_NAME FROM (select DISTINCT RM_NAME,EMP_CODE  from NG_RM_MASTER ORDER BY EMP_CODE) where ROWNUM <= " + this._iBatchSize;
			}
			////// System.out.println("_Search_strQuery "+_strQuery);
		}
		//satish
		/******************End  CO Insurance CR 18 *************************/	
		
		/********************* CR 28 by satish *****************************/
		//satish
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_NAME_OF_BROKER_AGENT")))
		{
			/*************************** PID-CPI process changes ***************************/
			//colCount=2;
			colCount=3;
			if(fieldValue!=null)
			{
				//this._strQuery ="SELECT AGENT_NAME, IBANKAGENT  FROM (select DISTINCT  AGENT_NAME, IBANKAGENT from ng_cpi_agent_master where  UPPER(AGENT_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY AGENT_NAME ) where ROWNUM <= " + this._iBatchSize;
				this._strQuery ="SELECT AGENT_NAME, IBANKAGENT,INTERMEDIARY_CODE  FROM (select DISTINCT  AGENT_NAME, IBANKAGENT,INTERMEDIARY_CODE from ng_cpi_agent_master where  UPPER(AGENT_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY AGENT_NAME ) where ROWNUM <= " + this._iBatchSize;
			}
			else
			{
				//this._strQuery ="SELECT AGENT_NAME, IBANKAGENT  FROM (select DISTINCT AGENT_NAME, IBANKAGENT   from ng_cpi_agent_master ORDER BY AGENT_NAME) where ROWNUM <= " + this._iBatchSize;
				this._strQuery ="SELECT AGENT_NAME, IBANKAGENT,INTERMEDIARY_CODE  FROM (select DISTINCT AGENT_NAME,IBANKAGENT,INTERMEDIARY_CODE   from ng_cpi_agent_master ORDER BY AGENT_NAME) where ROWNUM <= " + this._iBatchSize;
			}
			/*********************** End PID-CPI process changes ***************************/
			////// System.out.println("_Search_strQuery "+_strQuery);
		}
		//satish
		/********************** End of CR 28 ********************************/
		
		/************************  IL Location Green File Endorsement****************/
	
		if((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_IL_Location"))||(this.strSource.equalsIgnoreCase("Corp_IL_Location"))||(this.strSource.equalsIgnoreCase("End_IL_Location"))))
		{
			colCount=1;
			if(fieldValue!=null)
			{
			
			this._strQuery ="SELECT IL_LOCATION_VALUE from NG_IL_LOCATION_MASTER where  UPPER(IL_LOCATION_VALUE) like UPPER(N'"+fieldValue+"%') and ROWNUM <= " + this._iBatchSize+"order by IL_LOCATION_VALUE";

			}
			else
			{
				this._strQuery ="SELECT IL_LOCATION_VALUE FROM (select   IL_LOCATION_VALUE  from NG_IL_LOCATION_MASTER ORDER BY IL_LOCATION_VALUE) where ROWNUM <= " + this._iBatchSize+"order by IL_LOCATION_VALUE";
			}
			////// System.out.println("_Search_strQuery "+_strQuery);
		}
		
	/************************End   IL Location Green File Endorsement****************/	
	
	/*************************** PID-CPI process changes ***************************/
	/**************************** CPI moDE_OF_PAYMENT****************/
		if ((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_moDE_OF_PAYMENT"))||(this.strSource.equalsIgnoreCase("CPI_MODE_OF_PAYMENT2"))||(this.strSource.equalsIgnoreCase("CPI_MODE_OF_PAYMENT3"))))
		{
			colCount=1;
			if(fieldValue!=null)
			{
			
			this._strQuery ="SELECT MODE_OF_PAYMENT from NG_CPI_PAYMENT_MODE_MASTER where UPPER(MODE_OF_PAYMENT) like UPPER(N'"+fieldValue+"%') and ROWNUM <= " + this._iBatchSize+"order by MODE_OF_PAYMENT";

			}
			else
			{
				this._strQuery ="SELECT MODE_OF_PAYMENT FROM (select MODE_OF_PAYMENT from NG_CPI_PAYMENT_MODE_MASTER ORDER BY MODE_OF_PAYMENT) where ROWNUM <= " + this._iBatchSize+"order by MODE_OF_PAYMENT";
			}
			////// System.out.println("_Search_strQuery "+_strQuery);
		}
	/************************End  CPI moDE_OF_PAYMENT****************/
	/*********************** End PID-CPI process changes ***************************/ 
	
	/**************************** CPI Hypothecated_to****************/
		if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_HYPOTHECATED_TO")))
		{
			colCount=1;
			////// System.out.println("fieldValue "+fieldValue);
			if(fieldValue!=null)
			{
			
			this._strQuery ="select Hypothecated_To from (SELECT DISTINCT Hypothecated_To from NG_CPI_HYPOTHECATED_MASTER where UPPER(Hypothecated_To) like UPPER(N'"+fieldValue+"%') order by HYPOTHECATED_TO) where ROWNUM <= " + this._iBatchSize+" order by HYPOTHECATED_TO";

			}
			else
			{
				this._strQuery ="SELECT Hypothecated_To FROM (select DISTINCT Hypothecated_To from NG_CPI_HYPOTHECATED_MASTER ORDER BY HYPOTHECATED_TO) where ROWNUM <= " + this._iBatchSize+" order by HYPOTHECATED_TO";
			}
			////// System.out.println("_Search_strQuery "+_strQuery);
		}
	/************************End  CPI Hypothecated_to****************/
	
	
	
	
	/************************* CPI URN CR 8001-61339 Multiple Changes CR *****************************/  
		if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_Exception_To_MH")))
		{
			colCount=1;
			////// System.out.println("fieldValue "+fieldValue);
			if(fieldValue!=null)
			{
			
			this._strQuery ="select MH_EXCEPTION_NAME from (SELECT DISTINCT MH_EXCEPTION_NAME from NG_CPI_MHEXCEPTION_MASTER where UPPER(MH_EXCEPTION_NAME) like UPPER(N'"+fieldValue+"%') order by MH_EXCEPTION_NAME) where ROWNUM <= " + this._iBatchSize+" order by MH_EXCEPTION_NAME";

			}
			else
			{
				this._strQuery ="SELECT MH_EXCEPTION_NAME FROM (select DISTINCT MH_EXCEPTION_NAME from NG_CPI_MHEXCEPTION_MASTER ORDER BY MH_EXCEPTION_NAME) where ROWNUM <= " + this._iBatchSize+" order by MH_EXCEPTION_NAME";
			}
			////// System.out.println("_Search_strQuery "+_strQuery);
		}
/*************************End CPI URN CR 8001-61339 Multiple Changes CR **************************/

		
		/************************  CPI_SPECIFIED_PERSON****************/
	
		if((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_SPECIFIED_PERSON"))  || (this.strSource.equalsIgnoreCase("Corp_SP_Name"))))
		{
			colCount=1;
			if(fieldValue!=null)
			{
			
			this._strQuery ="SELECT SPECIFIED_PERSON from NG_CPI_SP_MASTER where  UPPER(SPECIFIED_PERSON) like UPPER(N'"+fieldValue+"%') and ROWNUM <= " + this._iBatchSize+"order by SPECIFIED_PERSON";
			
			
			//this._strQuery ="SELECT IL_LOCATION_VALUE from NG_IL_LOCATION_MASTER where  UPPER(IL_LOCATION_VALUE) like UPPER('"+fieldValue+"%') and ROWNUM <= " + this._iBatchSize +" ORDER BY IL_LOCATION_VALUE";
			
				
				
				//this._strQuery ="SELECT IL_LOCATION_VALUE  FROM (select    IL_LOCATION_VALUE from NG_IL_LOCATION_MASTER where  UPPER(IL_LOCATION_VALUE) like UPPER('"+fieldValue+"%') ORDER BY IL_LOCATION_VALUE ) where ROWNUM <= " + this._iBatchSize;
			}
			else
			{
				this._strQuery ="SELECT SPECIFIED_PERSON FROM (select   SPECIFIED_PERSON  from NG_CPI_SP_MASTER ORDER BY SPECIFIED_PERSON) where ROWNUM <= " + this._iBatchSize+"order by SPECIFIED_PERSON";
			}
			////// System.out.println("_Search_strQuery "+_strQuery);
		}
		
	/************************End   CPI_SPECIFIED_PERSON ****************/	
	
		/************************  CPI_PRIMARY_VERTICAL****************/
	
		if((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_PRIMARY_VERTICAL"))))
		{
			/*************************** PID-CPI process changes ***************************/
			//colCount=1;
			colCount=2;
			if(fieldValue!=null)
			{
			
			//this._strQuery ="Select * from (SELECT DISTINCT PRIMARY_VERTICAL_VALUE from PRIMARY_VERT_DETAILS_MAS where  UPPER(PRIMARY_VERTICAL_VALUE) like UPPER(N'"+fieldValue+"%')) where ROWNUM <= " + this._iBatchSize+"order by PRIMARY_VERTICAL_VALUE";
			this._strQuery ="Select * from (SELECT DISTINCT PRIMARY_VERTICAL_VALUE,PRIMARY_VERTICAL_CODE from PRIMARY_VERT_DETAILS_MAS where  UPPER(PRIMARY_VERTICAL_VALUE) like UPPER(N'"+fieldValue+"%')) where ROWNUM <= " + this._iBatchSize+"order by PRIMARY_VERTICAL_VALUE";
			
			}
			else
			{
				//this._strQuery ="SELECT PRIMARY_VERTICAL_VALUE FROM (select DISTINCT PRIMARY_VERTICAL_VALUE  from PRIMARY_VERT_DETAILS_MAS ORDER BY PRIMARY_VERTICAL_VALUE) where ROWNUM <= " + this._iBatchSize+"order by PRIMARY_VERTICAL_VALUE";
				this._strQuery ="SELECT PRIMARY_VERTICAL_VALUE,PRIMARY_VERTICAL_CODE FROM (select DISTINCT PRIMARY_VERTICAL_VALUE,PRIMARY_VERTICAL_CODE  from PRIMARY_VERT_DETAILS_MAS ORDER BY PRIMARY_VERTICAL_VALUE) where ROWNUM <= " + this._iBatchSize+"order by PRIMARY_VERTICAL_VALUE";
			}
			/*********************** End PID-CPI process changes ***************************/
			////// System.out.println("_Search_strQuery "+_strQuery);
		}
		
	/************************End   CPI_PRIMARY_VERTICAL****************/	

	/************************  CPI_PRIMARY_SUB_VERTICAL****************/
	
		if((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_PRIMARY_SUB_VERTICAL"))))
		{
			/*************************** PID-CPI process changes ***************************/
			//colCount=1;
			colCount=2;
			if(fieldValue!=null)
			{
				//this._strQuery ="Select * from (SELECT DISTINCT PRIMARY_SUB_VERTICAL_Value from PRIMARY_VERT_DETAILS_MAS where PRIMARY_SUB_VERTICAL_VALUE!=' ' and   UPPER(PRIMARY_SUB_VERTICAL_Value) like UPPER(N'"+fieldValue+"%') ) where ROWNUM <= " + this._iBatchSize+"order by PRIMARY_SUB_VERTICAL_Value";
				this._strQuery ="Select * from (SELECT DISTINCT PRIMARY_SUB_VERTICAL_Value,prim_sub_vert_code from PRIMARY_VERT_DETAILS_MAS where PRIMARY_SUB_VERTICAL_VALUE!=' ' and   UPPER(PRIMARY_SUB_VERTICAL_Value) like UPPER(N'"+fieldValue+"%') ) where ROWNUM <= " + this._iBatchSize+"order by PRIMARY_SUB_VERTICAL_Value";
			}
			else
			{
				//this._strQuery ="SELECT PRIMARY_SUB_VERTICAL_Value FROM (select DISTINCT  PRIMARY_SUB_VERTICAL_Value  from PRIMARY_VERT_DETAILS_MAS where PRIMARY_SUB_VERTICAL_VALUE!=' ' ORDER BY PRIMARY_SUB_VERTICAL_Value) where ROWNUM <= " + this._iBatchSize+"order by PRIMARY_SUB_VERTICAL_Value";
				this._strQuery ="SELECT PRIMARY_SUB_VERTICAL_Value,prim_sub_vert_code FROM (select DISTINCT  PRIMARY_SUB_VERTICAL_Value,prim_sub_vert_code  from PRIMARY_VERT_DETAILS_MAS where PRIMARY_SUB_VERTICAL_VALUE!=' ' ORDER BY PRIMARY_SUB_VERTICAL_Value) where ROWNUM <= " + this._iBatchSize+"order by PRIMARY_SUB_VERTICAL_Value";
			}
			/*********************** End PID-CPI process changes ***************************/
			////// System.out.println("_Search_strQuery "+_strQuery);
		}
		
	/************************End   CPI_PRIMARY_SUB_VERTICAL****************/
	/**** Start CR-8001-70893 Marine CR *******************************/
	if((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_SECONDARY_VERTICAL"))))
	{
		colCount=1;
		if(fieldValue!=null)
		{
			this._strQuery ="select * from (select distinct SECONDRY_VERTICAL_VALUE from SEC_VERT_DETAILS_MAS where SECONDRY_VERTICAL_VALUE!=' ' and UPPER(SECONDRY_VERTICAL_VALUE) like UPPER(N'"+fieldValue+"%') ) where ROWNUM <= " + this._iBatchSize+"order by SECONDRY_VERTICAL_VALUE";
		}
		else
		{
			this._strQuery ="select SECONDRY_VERTICAL_VALUE FROM (select distinct SECONDRY_VERTICAL_VALUE  from SEC_VERT_DETAILS_MAS where SECONDRY_VERTICAL_VALUE!=' ' ORDER BY SECONDRY_VERTICAL_VALUE) where ROWNUM <= " + this._iBatchSize+"order by SECONDRY_VERTICAL_VALUE";
		}
		// System.out.println("_Search_strQuery "+_strQuery);
	}
	/**** ENd CR-8001-70893 Marine CR *********************************/
	/************************  CPI_SECONDARY_SUB_VERTICAL****************/
	
		if((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_SECONDARY_SUB_VERTICAL"))))
		{
			colCount=1;
			if(fieldValue!=null)
			{
				this._strQuery ="Select * from (SELECT DISTINCT SECONDRY_SUB_VERTICAL_VALUE from SEC_VERT_DETAILS_MAS where SECONDRY_SUB_VERTICAL_VALUE!=' ' and UPPER(SECONDRY_SUB_VERTICAL_VALUE) like UPPER(N'"+fieldValue+"%') ) where ROWNUM <= " + this._iBatchSize+"order by SECONDRY_SUB_VERTICAL_VALUE";
			}
			else
			{
				this._strQuery ="SELECT SECONDRY_SUB_VERTICAL_VALUE FROM (select DISTINCT SECONDRY_SUB_VERTICAL_VALUE  from SEC_VERT_DETAILS_MAS where SECONDRY_SUB_VERTICAL_VALUE!=' ' ORDER BY SECONDRY_SUB_VERTICAL_VALUE) where ROWNUM <= " + this._iBatchSize+"order by SECONDRY_SUB_VERTICAL_VALUE";
			}
			////// System.out.println("_Search_strQuery "+_strQuery);
		}
		
	/************************End   CPI_SECONDARY_SUB_VERTICAL****************/	
	
		/************************  CPI_SOURCE_NAME****************/
	
		if((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_SOURCE_NAME")) || (this.strSource.equalsIgnoreCase("CORP_SOURCE_NAME"))))
		{
			colCount=1;
			if(fieldValue!=null)
			{
				this._strQuery ="SELECT SOURCE from NG_CPI_SOURCE_MASTER where  UPPER(SOURCE) like UPPER(N'"+fieldValue+"%') and ROWNUM <= " + this._iBatchSize+"  and    primary_sub_vertical_id="+bBGKRGID1 +" order by SOURCE";
					
				//this._strQuery ="SELECT IL_LOCATION_VALUE from NG_IL_LOCATION_MASTER where  UPPER(IL_LOCATION_VALUE) like UPPER('"+fieldValue+"%') and ROWNUM <= " + this._iBatchSize +" ORDER BY IL_LOCATION_VALUE";
			
				//this._strQuery ="SELECT IL_LOCATION_VALUE  FROM (select    IL_LOCATION_VALUE from NG_IL_LOCATION_MASTER where  UPPER(IL_LOCATION_VALUE) like UPPER('"+fieldValue+"%') ORDER BY IL_LOCATION_VALUE ) where ROWNUM <= " + this._iBatchSize;
			}
			else
			{
				this._strQuery ="SELECT SOURCE FROM (select   SOURCE  from NG_CPI_SOURCE_MASTER ORDER BY SOURCE) where ROWNUM <= " + this._iBatchSize+ "  and    primary_sub_vertical_id="+bBGKRGID1 +"order by SOURCE";
			}
			////// System.out.println("_Search_strQuery "+_strQuery);
		}
		
	/************************End   CPI_SOURCE_NAME****************/	
	
		/************************  CPI_CHANNEL_NAME****************/
	
		if((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_CHANNEL_NAME"))|| (this.strSource.equalsIgnoreCase("CORP_CHANNEL_NAME"))))
		{
			colCount=1;
			if(fieldValue!=null)
			{
				if(BBGKRGVAL1.equalsIgnoreCase("BBG") || BBGKRGVAL1.equalsIgnoreCase("BRANCH BRANCHING")
				/***************************** CR 28 by satish *****************************/
				|| BBGKRGVAL1.equalsIgnoreCase("SEG") || BBGKRGVAL1.equalsIgnoreCase("NA")
				/*************************** End CR 28 by satish ***************************/
				 || BBGKRGVAL1.equalsIgnoreCase("COB") // CR-OMCPI-1314-02 FIG COB CR
				)
				{
					this._strQuery ="SELECT CHANNEL from NG_CPI_CHANNEL_MASTER where  UPPER(CHANNEL) like UPPER(N'"+fieldValue+"%') and ROWNUM <= " + this._iBatchSize+"  and    primary_sub_vertical_id="+bBGKRGID1 +" order by CHANNEL";
				}
				else if(BBGKRGVAL1.equalsIgnoreCase("KRG") || BBGKRGVAL1.equalsIgnoreCase("KEY RELATIONSHIP GROUP"))//CR-8001-87420 Green File Digitization)
				{
					this._strQuery ="select CHANNEL from NG_CPI_SOURCE_CHANNEL_MASTER A,NG_CPI_SOURCE_MASTER B,NG_CPI_CHANNEL_MASTER C  Where UPPER(CHANNEL) like UPPER(N'"+fieldValue+"%') and ROWNUM <= " + this._iBatchSize+"  and B.Recordid=A.Source_ID and C.Recordid=A.Channel_ID and A.SOURCE_ID="+sourceID1+" order by CHANNEL";
				}
				//this._strQuery ="SELECT IL_LOCATION_VALUE from NG_IL_LOCATION_MASTER where  UPPER(IL_LOCATION_VALUE) like UPPER('"+fieldValue+"%') and ROWNUM <= " + this._iBatchSize +" ORDER BY IL_LOCATION_VALUE";
			
				//this._strQuery ="SELECT IL_LOCATION_VALUE  FROM (select    IL_LOCATION_VALUE from NG_IL_LOCATION_MASTER where  UPPER(IL_LOCATION_VALUE) like UPPER('"+fieldValue+"%') ORDER BY IL_LOCATION_VALUE ) where ROWNUM <= " + this._iBatchSize;
			}
			else
			{
				if(BBGKRGVAL1.equalsIgnoreCase("BBG") || BBGKRGVAL1.equalsIgnoreCase("BRANCH BRANCHING")
				/***************************** CR 28 by satish *****************************/
				|| BBGKRGVAL1.equalsIgnoreCase("SEG") || BBGKRGVAL1.equalsIgnoreCase("NA")
				/*************************** End CR 28 by satish ***************************/
				)
				{
					this._strQuery ="SELECT CHANNEL FROM (select   CHANNEL  from NG_CPI_CHANNEL_MASTER ORDER BY CHANNEL) where ROWNUM <= " + this._iBatchSize+ "  and    primary_sub_vertical_id="+bBGKRGID1  +"order by CHANNEL";
				}
				else if(BBGKRGVAL1.equalsIgnoreCase("KRG") || BBGKRGVAL1.equalsIgnoreCase("KEY RELATIONSHIP GROUP"))//CR-8001-87420 Green File Digitization)
				{
					this._strQuery ="select CHANNEL from NG_CPI_SOURCE_CHANNEL_MASTER A,NG_CPI_SOURCE_MASTER B,NG_CPI_CHANNEL_MASTER C  Where  ROWNUM <= " + this._iBatchSize+"  and B.Recordid=A.Source_ID and C.Recordid=A.Channel_ID and A.SOURCE_ID="+sourceID1+" order by CHANNEL";
				}
				////// System.out.println("_Search_strQuery "+_strQuery);
			}
		}
		
		/************************End   CPI_CHANNEL_NAME****************/	
		
		/************************  CPI_BRANCH_NAME****************/
	
		if((this.strSource != null) && ((this.strSource.equalsIgnoreCase("CPI_BRANCH_NAME")) || (this.strSource.equalsIgnoreCase("CORP_BRANCH_NAME"))))
		{
			//colCount=1;//PID_CPI process changes
			colCount=10; //PID_CPI process changes
			if(fieldValue!=null)
			{
				//	this._strQuery ="SELECT BRANCH from (SELECT   BRANCH FROM NG_CPI_SOURCE_MASTER where SOURCE >N'" + str1 +"'  and ORDER BY BRANCH) where  UPPER(BRANCH) like UPPER(N'"+fieldValue+"%') and ROWNUM <= " + this._iBatchSize+"  and    primary_sub_vertical_id=bBGKRGID1 ";
				
				if(BBGKRGVAL1.equalsIgnoreCase("BBG") || BBGKRGVAL1.equalsIgnoreCase("BRANCH BRANCHING") 
				/***************************** CR 28 by satish *****************************/
				|| BBGKRGVAL1.equalsIgnoreCase("SEG") || BBGKRGVAL1.equalsIgnoreCase("NA")
				/*************************** End CR 28 by satish ***************************/
				 || BBGKRGVAL1.equalsIgnoreCase("COB") // CR-OMCPI-1314-02 FIG COB CR
				)
				{
					/*************************** PID-CPI process changes ***************************/
					//this._strQuery ="SELECT BRANCH from NG_CPI_BRANCH_MASTER where  UPPER(BRANCH) like UPPER(N'"+fieldValue+"%') and ROWNUM <= " + this._iBatchSize+"  and    primary_sub_vertical_id="+bBGKRGID1+"  order by BRANCH";
					this._strQuery ="SELECT BRANCH,branch_id,sp_name1,sp_code1,sp_name2,sp_code2,sp_name3,sp_code3,sp_name4,sp_code4 from NG_CPI_BRANCH_MASTER where  UPPER(BRANCH) like UPPER(N'"+fieldValue+"%') and ROWNUM <= " + this._iBatchSize+"  and    primary_sub_vertical_id="+bBGKRGID1+"  order by BRANCH";
					
				}
				else if(BBGKRGVAL1.equalsIgnoreCase("KRG") || BBGKRGVAL1.equalsIgnoreCase("KEY RELATIONSHIP GROUP"))//CR-8001-87420 Green File Digitization
				{
					//this._strQuery ="select BRANCH from NG_CPI_SOURCE_BRANCH_MASTER A,NG_CPI_SOURCE_MASTER B,NG_CPI_BRANCH_MASTER C Where   UPPER(BRANCH) like UPPER(N'"+fieldValue+"%') and ROWNUM <= " + this._iBatchSize+"  and    B.Recordid=A.Source_ID and C.Recordid=A.Branch_ID  and A.SOURCE_ID="+sourceID1+"  order by BRANCH";
					this._strQuery ="select BRANCH,C.branch_id,sp_name1,sp_code1,sp_name2,sp_code2,sp_name3,sp_code3,sp_name4,sp_code4 from NG_CPI_SOURCE_BRANCH_MASTER A,NG_CPI_SOURCE_MASTER B,NG_CPI_BRANCH_MASTER C Where   UPPER(BRANCH) like UPPER(N'"+fieldValue+"%') and ROWNUM <= " + this._iBatchSize+"  and    B.Recordid=A.Source_ID and C.Recordid=A.Branch_ID  and A.SOURCE_ID="+sourceID1+"  order by BRANCH";
					/*********************** End PID-CPI process changes ***************************/
				}
			}
			else
			{
				if(BBGKRGVAL1.equalsIgnoreCase("BBG") || BBGKRGVAL1.equalsIgnoreCase("BRANCH BRANCHING")
				/***************************** CR 28 by satish *****************************/
				|| BBGKRGVAL1.equalsIgnoreCase("SEG") || BBGKRGVAL1.equalsIgnoreCase("NA")
				/*************************** End CR 28 by satish ***************************/
				)
				{
					/*************************** PID-CPI process changes ***************************/
					//this._strQuery ="SELECT BRANCH FROM (select   BRANCH  from NG_CPI_BRANCH_MASTER ORDER BY BRANCH) where ROWNUM <= " + this._iBatchSize+ "  and    primary_sub_vertical_id="+bBGKRGID1 +" order by BRANCH";
					this._strQuery ="SELECT BRANCH,branch_id,sp_name1,sp_code1,sp_name2,sp_code2,sp_name3,sp_code3,sp_name4,sp_code4 FROM (select   BRANCH,branch_id,sp_name1,sp_code1,sp_name2,sp_code2,sp_name3,sp_code3,sp_name4,sp_code4  from NG_CPI_BRANCH_MASTER ORDER BY BRANCH) where ROWNUM <= " + this._iBatchSize+ "  and    primary_sub_vertical_id="+bBGKRGID1 +" order by BRANCH";
					
				}
				else if(BBGKRGVAL1.equalsIgnoreCase("KRG") || BBGKRGVAL1.equalsIgnoreCase("KEY RELATIONSHIP GROUP"))//CR-8001-87420 Green File Digitization
				{
					this._strQuery ="select BRANCH,C.branch_id,sp_name1,sp_code1,sp_name2,sp_code2,sp_name3,sp_code3,sp_name4,sp_code4 from NG_CPI_SOURCE_BRANCH_MASTER A,NG_CPI_SOURCE_MASTER B,NG_CPI_BRANCH_MASTER C Where   ROWNUM <= " + this._iBatchSize+"  and    B.Recordid=A.Source_ID and C.Recordid=A.Branch_ID  and A.SOURCE_ID="+sourceID1+"  order by BRANCH";
					/*********************** End PID-CPI process changes ***************************/
				}
				////// System.out.println("_Search_strQuery "+_strQuery);
			}
		
		}
	
	/************************End   CPI_BRANCH_NAME****************/	
	
	/*****************  CPI_naME_OF_LEADER by satish for CR21 *********************/	
		if((this.strSource != null) && (this.strSource.equalsIgnoreCase("CPI_naME_OF_LEADER")))
		{
			colCount=1;
			if(fieldValue!=null)
			{
				this._strQuery ="SELECT LEADER_NAME FROM (select  LEADER_NAME from NG_CPI_LEADER_MST where  LEADER_CATEGORY_TYPE_ID="+product_type_val+" and UPPER(LEADER_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY LEADER_NAME ) where ROWNUM <= " + this._iBatchSize;
			}
			else
			{
				this._strQuery ="SELECT LEADER_NAME FROM (select  LEADER_NAME from NG_CPI_LEADER_MST where  LEADER_CATEGORY_TYPE_ID="+product_type_val+"  ORDER BY LEADER_NAME ) where ROWNUM <= " + this._iBatchSize;
			}
			////// System.out.println("_Search_strQuery "+_strQuery);
		}
	/***************** End CPI_naME_OF_LEADER by satish for CR21 *********************/
	
		/************************  Product Name****************/
	
		if((this.strSource != null) && ((this.strSource.equalsIgnoreCase("End_PRODUCT_NAME"))||(this.strSource.equalsIgnoreCase("Corp_PRODUCT_NAME"))||(this.strSource.equalsIgnoreCase("CPI_PRODUCT_NAME"))))
		{
			//colCount=1;//Vendor login & weather Product
			colCount=2;//Vendor login & weather Product
			if(fieldValue!=null)
			{
				/***************** CR21 *********************/
				if((_objFormObject.getWFActivityName().equalsIgnoreCase("Co_Insurance")) || (_objFormObject.getWFActivityName().equalsIgnoreCase("Co_Insurance_RM")))
				{
					this._strQuery ="SELECT PRODUCT_NAME,IRDACODE FROM (select  PRODUCT_NAME,IRDACODE from NG_PRODUCT_MASTER where  PRODUCT_CATEGORY_ID="+product_type_val+" and UPPER(PRODUCT_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY PRODUCT_NAME ) where ROWNUM <= " + this._iBatchSize;//Vendor login & weather Product
				}
				/******************* CR 46 CPU DataWashing********************/
				else if(_objFormObject.getNGValue("CPI_DATAWASHING_TYPE").equalsIgnoreCase("Endorsement"))
				{
					////// System.out.println("1.CR 46 CPI_DATAWASHING_TYPE: "+_objFormObject.getNGValue("CPI_DATAWASHING_TYPE"));
					this._strQuery ="SELECT PRODUCT_NAME,IRDACODE FROM (select  PRODUCT_NAME,IRDACODE from NG_PRODUCT_MASTER where  PRODUCT_TYPE_ID="+product_type_val+" AND PRODUCT_ROUTING = '2' AND UPPER(PRODUCT_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY PRODUCT_NAME ) where ROWNUM <= " + this._iBatchSize;	//Vendor login & weather Product
				}/*****************end CR 46 CPU DataWashing*******************/
				else
				/*****************End CR21 *********************/
				{	
					this._strQuery ="SELECT PRODUCT_NAME,IRDACODE FROM (select  PRODUCT_NAME,IRDACODE from NG_PRODUCT_MASTER where  PRODUCT_TYPE_ID="+product_type_val+" and UPPER(PRODUCT_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY PRODUCT_NAME ) where ROWNUM <= " + this._iBatchSize;	//Vendor login & weather Product	
				}
			}
			else
			{
				/***************** CR21 *********************/
				if((_objFormObject.getWFActivityName().equalsIgnoreCase("Co_Insurance")) || (_objFormObject.getWFActivityName().equalsIgnoreCase("Co_Insurance_RM")))
				{
					this._strQuery ="SELECT PRODUCT_NAME,IRDACODE FROM (select  PRODUCT_NAME,IRDACODE from NG_PRODUCT_MASTER where  PRODUCT_CATEGORY_ID="+product_type_val+"  ORDER BY PRODUCT_NAME ) where ROWNUM <= " + this._iBatchSize;//Vendor login & weather Product
				}
				/******************* CR 46 CPU DataWashing********************/
				else if(_objFormObject.getNGValue("CPI_DATAWASHING_TYPE").equalsIgnoreCase("Endorsement"))
				{
					////// System.out.println("2.CR 46 CPI_DATAWASHING_TYPE: "+_objFormObject.getNGValue("CPI_DATAWASHING_TYPE"));
					this._strQuery ="SELECT PRODUCT_NAME,IRDACODE FROM (select  PRODUCT_NAME,IRDACODE from NG_PRODUCT_MASTER where  PRODUCT_TYPE_ID="+product_type_val+"  AND PRODUCT_ROUTING = '2' ORDER BY PRODUCT_NAME ) where ROWNUM <= " + this._iBatchSize;	//Vendor login & weather Product
				}/*****************end CR 46 CPU DataWashing*******************/
				else
				/*****************End CR21 *********************/
				{
					this._strQuery ="SELECT PRODUCT_NAME,IRDACODE FROM (select  PRODUCT_NAME,IRDACODE from NG_PRODUCT_MASTER where  PRODUCT_TYPE_ID="+product_type_val+"  ORDER BY PRODUCT_NAME ) where ROWNUM <= " + this._iBatchSize;		//Vendor login & weather Product
				}
			}
			////// System.out.println("_Search_strQuery "+_strQuery);
		}
		
	/************************End   Product Name****************/	

	
	
		if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_IL_LOCATION")))
		{
			colCount=2;
			if(fieldValue!=null)
			{
				this._strQuery ="SELECT ILBRANCHNAME, ILBRANCHCODE FROM (select DISTINCT ILBRANCHNAME, ILBRANCHCODE from NG_ICICI_MST_ILLOCATION where  UPPER(ILBRANCHNAME) like UPPER(N'"+fieldValue+"%') ORDER BY ILBRANCHCODE) where ROWNUM <= " + this._iBatchSize;
			}
			else
			{
				this._strQuery ="SELECT ILBRANCHNAME, ILBRANCHCODE FROM (select DISTINCT ILBRANCHNAME, ILBRANCHCODE from NG_ICICI_MST_ILLOCATION ORDER BY ILBRANCHCODE) where ROWNUM <= " + this._iBatchSize;
			}
			////// System.out.println("ICICILOMBARD_HT_IL_LOCATION query "+_strQuery);
		}
		
		/******************************* PID-HT process changes ********************************/
		if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_IL_LOCATION_CODE")))
		{
			colCount=2;
			if(fieldValue!=null)
			{
				this._strQuery ="SELECT ILBRANCHNAME, ILBRANCHCODE FROM (select DISTINCT ILBRANCHNAME, ILBRANCHCODE from NG_ICICI_MST_ILLOCATION where  UPPER(ILBRANCHCODE) like UPPER(N'"+fieldValue+"%') ORDER BY ILBRANCHCODE) where ROWNUM <= " + this._iBatchSize;
			}
			else
			{
				this._strQuery ="SELECT ILBRANCHNAME, ILBRANCHCODE FROM (select DISTINCT ILBRANCHNAME, ILBRANCHCODE from NG_ICICI_MST_ILLOCATION ORDER BY ILBRANCHCODE) where ROWNUM <= " + this._iBatchSize;
			}
			////// System.out.println("ICICILOMBARD_HT_IL_LOCATION_CODE query "+_strQuery);
		}
		/******************************End PID-HT process changes ******************************/
		
		//channel source loading(BBG)
		else if((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_SOURCE_BUSINESS")) && ((_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking (BBG)") || _objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BBG"))))
			{
				colCount=1;

				if(fieldValue!=null)
				{
					this._strQuery ="SELECT SOURCEBUSINESS FROM (select DISTINCT(SOURCEBUSINESS) from NG_ICICI_MST_BBG_BUSINESS where  UPPER(SOURCEBUSINESS) like UPPER(N'"+fieldValue+"%') ORDER BY SOURCEBUSINESS) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					this._strQuery ="SELECT SOURCEBUSINESS FROM (select DISTINCT(SOURCEBUSINESS) from NG_ICICI_MST_BBG_BUSINESS ORDER BY SOURCEBUSINESS) where ROWNUM <= " + this._iBatchSize;
				}

			
			}
		
		//SOURCE BUSINESS
		else if((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_SOURCE_BUSINESS")) && (_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)")))
			{
				colCount=1;
				if(fieldValue!=null)
				{
								
					  this._strQuery = "select TXTSOURCEBUSINESS from (select DISTINCT(TXTSOURCEBUSINESS) from NG_ICICI_MST_KRG where UPPER(TXTSOURCEBUSINESS) like UPPER(N'"+fieldValue+"%') and TXTSOURCEBUSINESS is not NULL ORDER BY TXTSOURCEBUSINESS) where  ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					 
					 this._strQuery = "select TXTSOURCEBUSINESS from (select DISTINCT(TXTSOURCEBUSINESS) from NG_ICICI_MST_KRG and TXTSOURCEBUSINESS is not NULL ORDER BY TXTSOURCEBUSINESS) where  ROWNUM <= " + this._iBatchSize;
				}
			}
			
			//source business next
			else if(this.strSource != null && this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_SOURCE_BUSINESS") && _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG") && _objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("ISEC"))
			{
				
				//First row 
				ArrayList alRow = new ArrayList(); 
				alRow.add("Center Sales"); 
				alRows.add(alRow); 

				//Second Row 
				alRow = new ArrayList(); 
				alRow.add("Sub Broker"); 
				alRows.add(alRow); 
			}
			
			//channel source
			else if ((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_CHANNEL_SOURCE")))
			{
				colCount=1;
				if(fieldValue!=null)
				{
					this._strQuery ="select CHANNELSOURCING from(select DISTINCT(CHANNELSOURCING) from NG_ICICI_MST_BBG_SOURCE where UPPER(CHANNELSOURCING) like UPPER(N'"+fieldValue+"%') ORDER BY CHANNELSOURCING) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					this._strQuery ="select CHANNELSOURCING from(select DISTINCT(CHANNELSOURCING) from NG_ICICI_MST_BBG_SOURCE ORDER BY CHANNELSOURCING) where ROWNUM <= " + this._iBatchSize;
				}
			}
			
			/******************************* PID-HT process changes ********************************/
			//for ICICILOMBARD_HT_PAYMENT_MODE
			else if((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_PAYMENT_MODE")))
			{
				if(fieldValue!=null)
				{
					this._strQuery ="select Payment_Mode from(select DISTINCT(Payment_Mode) from NG_HT_PAYMENT_MODE_MASTER where UPPER(Payment_Mode) like UPPER(N'"+fieldValue+"%') ORDER BY Payment_Mode) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					this._strQuery ="select Payment_Mode from(select DISTINCT(Payment_Mode) from NG_HT_PAYMENT_MODE_MASTER ORDER BY Payment_Mode) where ROWNUM <= " + this._iBatchSize;
				}
			}
			/******************************End PID-HT process changes ******************************/
			
			/**************************************Start HT SP Code CR CR-8093-69682 Search*****************************************************/
			//deal_il_location search
			else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_DEAL_IL_LOCATION")))
			{
				String sm_id =_objFormObject.getNGValue("ICICILOMBARD_HT_SM_ID");
				colCount=1;
				if(fieldValue!=null)
				{
					this._strQuery ="select b.TXT_OFFICE from MV_CENTRAL_EMPLOYEE a, MV_GENMST_OFFICE b where a.NUM_OFFICE_CD=b.NUM_OFFICE_CD and a.TXT_HR_REF_NO='"+sm_id+"' and UPPER(b.TXT_OFFICE) like UPPER(N'"+fieldValue+"%') and ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					this._strQuery ="select b.TXT_OFFICE from MV_CENTRAL_EMPLOYEE a, MV_GENMST_OFFICE b where a.NUM_OFFICE_CD=b.NUM_OFFICE_CD and a.TXT_HR_REF_NO='"+sm_id+"' and UPPER(b.TXT_OFFICE) like UPPER(N'"+fieldValue+"%') and ROWNUM <= " + this._iBatchSize;
				}
				////// System.out.println("ICICILOMBARD_HT_DEAL_IL_LOCATION****Search query "+this._strQuery);
			}
			//bank branch search
			//select BANK_BRANCH_NAME from NG_HT_MST_SP_CODE
			else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_BANK_BRANCH_NAME")))
			{
				if(_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking (BBG)") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("HOME")|| (_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG") && _objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BBG")))
				{
					colCount=1;
					if(fieldValue!=null)
					{
						this._strQuery ="select BBGBRANCHNAME from(select distinct(BBGBRANCHNAME) from NG_ICICI_MST_BBG_HOMEBRANCH where UPPER(BBGBRANCHNAME) like UPPER(N'"+fieldValue+"%') ORDER BY BBGBRANCHNAME) where ROWNUM <= " + this._iBatchSize;
					}
					else
					{
						this._strQuery ="select BBGBRANCHNAME from(select distinct(BBGBRANCHNAME) from NG_ICICI_MST_BBG_HOMEBRANCH ORDER BY BBGBRANCHNAME) where ROWNUM <= " + this._iBatchSize;
					}
				}
				if((_objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE"))&&(_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING")))
				{					
					String channel_source=_objFormObject.getNGValue("ICICILOMBARD_HT_CHANNEL_SOURCE");
					String deal_il_location=_objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_IL_LOCATION");
					colCount=2;
					if(fieldValue!=null)
					{
						this._strQuery ="select BANK_BRANCH_NAME,SOL_ID from(select distinct BANK_BRANCH_NAME,SOL_ID from NG_HT_MST_SP_CODE where CHANNEL_SOURCE ='"+channel_source+"' and DEAL_IL_LOCATION='"+deal_il_location+"' and UPPER(BANK_BRANCH_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY BANK_BRANCH_NAME) where ROWNUM <= " + this._iBatchSize;
					}
					else
					{
						this._strQuery ="select BANK_BRANCH_NAME,SOL_ID from(select distinct BANK_BRANCH_NAME,SOL_ID from NG_HT_MST_SP_CODE where CHANNEL_SOURCE ='"+channel_source+"' and DEAL_IL_LOCATION='"+deal_il_location+"' ORDER BY BANK_BRANCH_NAME) where ROWNUM <= " + this._iBatchSize;
					}
					////// System.out.println("ICICILOMBARD_HT_BANK_BRANCH_NAME****Search query "+this._strQuery);
				}
			}
			//sol id search
			else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_SOL_ID")) && _objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE") && (_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING")))
			{
				String channel_source=_objFormObject.getNGValue("ICICILOMBARD_HT_CHANNEL_SOURCE");
				String deal_il_location=_objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_IL_LOCATION");
				colCount=2;
				if(fieldValue!=null)
				{
					this._strQuery ="select BANK_BRANCH_NAME,SOL_ID from(select distinct BANK_BRANCH_NAME,SOL_ID from NG_HT_MST_SP_CODE where CHANNEL_SOURCE ='"+channel_source+"' and DEAL_IL_LOCATION='"+deal_il_location+"' and UPPER(SOL_ID) like UPPER(N'"+fieldValue+"%') ORDER BY SOL_ID) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					this._strQuery ="select BANK_BRANCH_NAME,SOL_ID from(select distinct BANK_BRANCH_NAME,SOL_ID from NG_HT_MST_SP_CODE where CHANNEL_SOURCE ='"+channel_source+"' and DEAL_IL_LOCATION='"+deal_il_location+"' ORDER BY SOL_ID) where ROWNUM <= " + this._iBatchSize;
				}
				////// System.out.println("ICICILOMBARD_HT_SOL_ID****Search query "+this._strQuery);
			}	
			//sp_code search
			else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_WRE")) &&  _objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE") && (_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING")))
			{
				String deal_il_location=_objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_IL_LOCATION");
				String sol_id=_objFormObject.getNGValue("ICICILOMBARD_HT_SOL_ID");
				String channel_source=_objFormObject.getNGValue("ICICILOMBARD_HT_CHANNEL_SOURCE");
				colCount=3;
				if(fieldValue!=null)
				{
					this._strQuery ="select SP_CODE,SP_NAME,SP_PAN from(select distinct SP_CODE,SP_NAME,SP_PAN from NG_HT_MST_SP_CODE where DEAL_IL_LOCATION='"+deal_il_location+"' and CHANNEL_SOURCE ='"+channel_source+"' and SOL_ID='"+sol_id+"' and UPPER(SP_CODE) like UPPER(N'"+fieldValue+"%') ORDER BY SP_CODE) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					this._strQuery ="select SP_CODE,SP_NAME,SP_PAN from(select distinct SP_CODE,SP_NAME,SP_PAN from NG_HT_MST_SP_CODE where DEAL_IL_LOCATION='"+deal_il_location+"' and CHANNEL_SOURCE ='"+channel_source+"' and SOL_ID='"+sol_id+"' ORDER BY SOL_ID) where ROWNUM <= " + this._iBatchSize;
				}
				////// System.out.println("ICICILOMBARD_HT_WRE****Search query "+this._strQuery);
			}
			/************************************** End HT SP Code CR CR-8093-69682 Search*****************************************************/
			/***************** Start SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI**************************************/
			//ICICILOMBARD_HT_WRE KRG
			else if((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_WRE")) && (_objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("BANCASSURANCE")) && (_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)")))
			{
				// System.out.println("Inside the Search sp code: Yogesh");
				colCount=2;
				if(fieldValue!=null)
				{
					// System.out.println("Inside the if Search sp code: Yogesh");
					this._strQuery ="select SP_CODE,SP_NAME from(select distinct SP_CODE,SP_NAME from NG_HT_SP_CODE_KRG where UPPER(SP_CODE) like UPPER(N'"+fieldValue+"%') ORDER BY SP_CODE) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					// System.out.println("Inside the else Search sp code: Yogesh");
					this._strQuery ="select SP_CODE,SP_NAME from(select distinct SP_CODE,SP_NAME from NG_HT_SP_CODE_KRG where ORDER BY SP_CODE) where ROWNUM <= " + this._iBatchSize;
				}
				// System.out.println("ICICILOMBARD_HT_WRE****Search query "+this._strQuery);
			}			
			/***************** End SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI**************************************/
			
			/******Start HT CR-8127-95325-GST-Omniflow development******/
			//TXTGST_STATE_NAME Search
			else if((this.strSource != null) && (this.strSource.equals("TXTGST_STATE_NAME")) && (_objFormObject.getNGValue("ICICILOMBARD_HT_GST_REGISTERED").equalsIgnoreCase("Yes")) && !(_objFormObject.getNGValue("ICICILOMBARD_HT_IAGENT").equalsIgnoreCase("Yes")))
			{
				// System.out.println("Inside the Search GST State: Yogesh");//select a.txtstatename,a.txtstatecode from NG_HT_MST_GST_STATE a
				colCount=1;
				if(fieldValue!=null)
				{
					// System.out.println("Inside the if Search GST State: Yogesh");
					this._strQuery ="select txtstatename from(select distinct txtstatename from NG_HT_MST_GST_STATE where UPPER(txtstatename) like UPPER(N'"+fieldValue+"%') ORDER BY txtstatename) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					// System.out.println("Inside the else Search GST State: Yogesh");
					this._strQuery ="select txtstatename from(select distinct txtstatename from NG_HT_MST_GST_STATE where ORDER BY txtstatename) where ROWNUM <= " + this._iBatchSize;
				}
				// System.out.println("TXTGST_STATE_NAME****Search query "+this._strQuery);
			}
			/******End HT CR-8127-95325-GST-Omniflow development******/
			/*****Start Change related to Application  Proposal no. field in Omni flow HT*****/
			else if((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_CHANNEL_LOAN_TYPE")))
			{
				System.out.println("Inside the Search Change related to Application  Proposal no. field in Omni flow HT: Yogesh");//select a.txtstatename,a.txtstatecode from NG_HT_MST_GST_STATE a
				colCount=2;
				if(fieldValue!=null)
				{
					System.out.println("Inside the if Search Change related to Application  Proposal no. field in Omni flow HT: Yogesh");
					this._strQuery ="select channel from(select distinct channel from NG_HT_MST_KRG_CHANNEL_SOURCE where UPPER(channel) like UPPER(N'"+fieldValue+"%') ORDER BY channel) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					System.out.println("Inside the else Search Change related to Application  Proposal no. field in Omni flow HT: Yogesh");
					this._strQuery ="select channel from(select distinct channel from NG_HT_MST_KRG_CHANNEL_SOURCE where ORDER BY channel) where ROWNUM <= " + this._iBatchSize;
				}
				System.out.println("ICICILOMBARD_HT_CHANNEL_LOAN_TYPE****Search query "+this._strQuery);
			}
			/*****End Change related to Application  Proposal no. field in Omni flow HT*****/			
				//BRANCH ID/UBO NAME
				else if ((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_BRANCH_ID_UBO_NAME")))
				{
					colCount=1;
					if(_objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)"))
					{
						
						if(fieldValue!=null)
						{
							this._strQuery ="select Txtbranchidubo from(select DISTINCT(Txtbranchidubo) from NG_ICICI_MST_KRG where UPPER(Txtbranchidubo) like UPPER(N'"+fieldValue+"%') ORDER BY Txtbranchidubo) where ROWNUM <= " + this._iBatchSize;//MHT-PID CR-8127-59721 enhancemnet additional CR
						}
						else
						{
							this._strQuery ="select Txtbranchidubo from(select DISTINCT(Txtbranchidubo) from NG_ICICI_MST_KRG ORDER BY Txtbranchidubo) where ROWNUM <= " + this._iBatchSize;
						}
					////// System.out.println("ICICILOMBARD_HT_BRANCH_ID_UBO_NAME count6:"+this._strQuery);
					}	
				}
				//sm id name Search
				/********************** Start MO filteration for Centralised Deal Vertical **********************/
				else if ((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_SM_NAME")))
				{					
					if (_objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_STATUS").equalsIgnoreCase("YES"))
					{
						colCount=4;
						if(fieldValue!=null)
						{
							this._strQuery ="select TXT_EMPLOYEE_NAME,TXT_HR_REF_NO,PRIM_SUBVERT,PRIM_VERT from(select Distinct TXT_EMPLOYEE_NAME as TXT_EMPLOYEE_NAME,TXT_HR_REF_NO as TXT_HR_REF_NO,PRIM_SUBVERT as PRIM_SUBVERT,PRIM_VERT as PRIM_VERT from MV_CENTRAL_EMPLOYEE WHERE UPPER(TXT_EMPLOYEE_NAME)  like UPPER(N'"+fieldValue+"%') ORDER BY TXT_HR_REF_NO) where ROWNUM <= " + this._iBatchSize;
						}
						else
						{
							this._strQuery ="select TXT_EMPLOYEE_NAME,TXT_HR_REF_NO,PRIM_SUBVERT,PRIM_VERT from(select Distinct TXT_EMPLOYEE_NAME as TXT_EMPLOYEE_NAME,TXT_HR_REF_NO as TXT_HR_REF_NO,PRIM_SUBVERT as PRIM_SUBVERT,PRIM_VERT as PRIM_VERT from MV_CENTRAL_EMPLOYEE ORDER BY TXT_HR_REF_NO) where ROWNUM <= " + this._iBatchSize;
						}						
					}
					else
					{					
						colCount=2;
						if(fieldValue!=null)
						{
							this._strQuery ="select TXT_EMPLOYEE_NAME,TXT_HR_REF_NO from(select Distinct me.TXT_EMPLOYEE_NAME as TXT_EMPLOYEE_NAME,me.TXT_HR_REF_NO as TXT_HR_REF_NO from MV_GENMST_EMPLOYEE me, MV_GEN_DEAL_DETAIL GD WHERE GD.NUM_MO_EMPLOYEE_NO= me.num_employee_cd and GD.txt_deal_id=N'"+_objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_NO")+"' and  UPPER(ME.TXT_EMPLOYEE_NAME)  like UPPER(N'"+fieldValue+"%') ORDER BY me.TXT_HR_REF_NO) where ROWNUM <= " + this._iBatchSize;
						}
						else
						{
							this._strQuery ="select TXT_EMPLOYEE_NAME,TXT_HR_REF_NO from(select Distinct me.TXT_EMPLOYEE_NAME as TXT_EMPLOYEE_NAME,me.TXT_HR_REF_NO as TXT_HR_REF_NO from MV_GENMST_EMPLOYEE me, MV_GEN_DEAL_DETAIL GD WHERE GD.NUM_MO_EMPLOYEE_NO= me.num_employee_cd and GD.txt_deal_id=N'"+_objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_NO")+"' ORDER BY me.TXT_HR_REF_NO) where ROWNUM <= " + this._iBatchSize;
						}
					}
					////// System.out.println("ICICILOMBARD_HT_SM_NAME Search "+_strQuery);
				}
				/********************** End MO filteration for Centralised Deal Vertical   **********************/
				//BANK NAME
				
				//bank name load
				else if ((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_BANK_NAME")))
				{
					//_iTotalRecord=getTotalRecord(""); // comment by manoj jain
					colCount=2;
					 if(fieldValue!=null)
						{
							this._strQuery ="select txtbankname,TXTBANKCODE from(select DISTINCT txtbankname,TXTBANKCODE from NG_ICICI_MST_BankName where UPPER(txtbankname) like UPPER(N'"+fieldValue+"%') ORDER BY txtbankname) where ROWNUM <= " + this._iBatchSize;
						}
						else
						{
							this._strQuery ="select txtbankname,TXTBANKCODE from(select DISTINCT txtbankname,TXTBANKCODE from NG_ICICI_MST_BankName ORDER BY txtbankname) where ROWNUM <= " + this._iBatchSize;
						}		
				}

				//sub product search
					else if ((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_SUB_PRODUCT")))
					{
						//fieldValue=localNGPickList.getSearchFilterValue();
						//_iTotalRecord=getTotalRecord(fieldValue);
						colCount=2;
						//str1 = localNGPickList.getValueAt(0, 0);
						/*if (_objFormObject.getNGValue("ICICILOMBARD_HT_PRODUCT_CODE").equalsIgnoreCase("") || _objFormObject.getNGValue("ICICILOMBARD_HT_PRODUCT_CODE").equalsIgnoreCase(null))
				{
			_objFormObject.setNGValue("ICICILOMBARD_HT_PRODUCT_CODE",_objFormObject.getNGItemText("PRODUCT_HIDDEN",1));
			////// System.out.println("ICICILOMBARD_HT_SUB_PRODUCT query "+_objFormObject.getNGValue("ICICILOMBARD_HT_PRODUCT_CODE"));
				}*/
				//setProductCode();
						/*if(fieldValue!=null)
						{
							this._strQuery ="select TXT_IL_SUB_PRODUCT_NAME,TXT_IL_PRODUCT_NO_SUFIX from(select DISTINCT TXT_IL_SUB_PRODUCT_NAME,TXT_IL_PRODUCT_NO_SUFIX from MV_UW_SUB_PRODUCT_MASTER where num_IL_product_code='"+_objFormObject.getNGValue("ICICILOMBARD_HT_PRODUCT_CODE")+"' and UPPER(TXT_IL_SUB_PRODUCT_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY TXT_IL_SUB_PRODUCT_NAME) where ROWNUM <= " + this._iBatchSize;
						}
						else
						{
							this._strQuery ="select TXT_IL_SUB_PRODUCT_NAME,TXT_IL_PRODUCT_NO_SUFIX from(select DISTINCT TXT_IL_SUB_PRODUCT_NAME,TXT_IL_PRODUCT_NO_SUFIX from MV_UW_SUB_PRODUCT_MASTER where num_IL_product_code='"+_objFormObject.getNGValue("ICICILOMBARD_HT_PRODUCT_CODE")+"' ORDER BY TXT_IL_SUB_PRODUCT_NAME) where ROWNUM <= " + this._iBatchSize;
						}
						////// System.out.println("ICICILOMBARD_HT_SUB_PRODUCT query "+_strQuery);*/
						if(fieldValue!=null)
						{
							this._strQuery ="select TXT_IL_SUB_PRODUCT_NAME,TXT_IL_PRODUCT_NO_SUFIX from(select DISTINCT TXT_IL_SUB_PRODUCT_NAME,TXT_IL_PRODUCT_NO_SUFIX  from  MV_UW_SUB_PRODUCT_MASTER a,MV_GEN_DEAL_DETAIL b,MV_UW_DEAL_PLAN_MAP c where a.NUM_IL_PRODUCT_CODE=b.NUM_PRODUCT_CODE and a.TXT_IL_SUB_PRODUCT_CODE=c.NUM_PLAN_CODE and b.TXT_DEAL_ID=c.TXT_DEAL_ID and b.TXT_DEAL_ID='"+ _objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_NO")+"' and UPPER(TXT_IL_SUB_PRODUCT_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY TXT_IL_SUB_PRODUCT_NAME) where ROWNUM <= " + this._iBatchSize;
							////// System.out.println("search1");
						}
						else
						{
							this._strQuery ="select TXT_IL_SUB_PRODUCT_NAME,TXT_IL_PRODUCT_NO_SUFIX from(select DISTINCT TXT_IL_SUB_PRODUCT_NAME,TXT_IL_PRODUCT_NO_SUFIX  from  MV_UW_SUB_PRODUCT_MASTER a,MV_GEN_DEAL_DETAIL b,MV_UW_DEAL_PLAN_MAP c where a.NUM_IL_PRODUCT_CODE=b.NUM_PRODUCT_CODE and a.TXT_IL_SUB_PRODUCT_CODE=c.NUM_PLAN_CODE and b.TXT_DEAL_ID=c.TXT_DEAL_ID and b.TXT_DEAL_ID='"+ _objFormObject.getNGValue("ICICILOMBARD_HT_DEAL_NO")+"' ORDER BY TXT_IL_SUB_PRODUCT_NAME) where ROWNUM <= " + this._iBatchSize;
							////// System.out.println("search1");
						}
						////// System.out.println("ICICILOMBARD_HT_SUB_PRODUCT query "+_strQuery);
					}
				
				//center code search
			else if ((this.strSource != null) && (this.strSource.equals("ICICILOMBARD_HT_CENTER_CODE_NAME")))
			{
				fieldValue=localNGPickList.getSearchFilterValue();
				_iTotalRecord=getTotalRecord(fieldValue);
				colCount=1;
				str1 = localNGPickList.getValueAt(0, 0);
				if(fieldValue!=null)
				{
					this._strQuery ="select CENTCODE_NAME from(select DISTINCT CENTCODE_NAME from NG_ICICI_MST_CENTERCODE where UPPER(CENTCODE_NAME)  like UPPER(N'%"+fieldValue+"%') ORDER BY CENTCODE_NAME) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					this._strQuery ="select CENTCODE_NAME from(select DISTINCT CENTCODE_NAME from NG_ICICI_MST_CENTERCODE ORDER BY CENTCODE_NAME) where ROWNUM <= " + this._iBatchSize;
				}
			}	
		/************************** CR-OF-MHT-1314-01 MHTProcess Implementaion Start***********/

        if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("MHT_IL_LOCATION_CODE")))
		{
			colCount=3;
			if(!fieldValue.equalsIgnoreCase(""))
			{
				/*************************** MHT-PID Process Integration ****************************/
				//this._strQuery ="SELECT ILBRANCHNAME, ILBRANCHCODE FROM (select DISTINCT ILBRANCHNAME, ILBRANCHCODE from NG_MHT_MST_ILLOCATION where  UPPER(ILBRANCHNAME) like UPPER(N'"+fieldValue+"%') ORDER BY ILBRANCHCODE) where ROWNUM <= " + this._iBatchSize;
				this._strQuery ="SELECT ILBRANCHNAME, ILBRANCHCODE, ZONEAREA FROM (select DISTINCT ILBRANCHNAME, ILBRANCHCODE, ZONEAREA from NG_MHT_MST_ILLOCATION where  UPPER(ILBRANCHCODE) like UPPER(N'"+fieldValue+"%') ORDER BY ILBRANCHCODE) where ROWNUM <= " + this._iBatchSize;
				/*************************** MHT-PID Process Integration ****************************/
			}
			else
			{
				this._strQuery ="SELECT ILBRANCHNAME, ILBRANCHCODE, ZONEAREA FROM (select DISTINCT ILBRANCHNAME, ILBRANCHCODE, ZONEAREA from NG_MHT_MST_ILLOCATION ORDER BY ILBRANCHCODE) where ROWNUM <= " + this._iBatchSize;
			}
			////// System.out.println("MHT_IL_LOCATION_CODE query "+_strQuery);
		}
		/*************************** MHT-PID Process Integration ****************************/
		//Search provided on both location name and code
		else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("MHT_IL_LOCATION")))
		{
			colCount=3;
			if(!fieldValue.equalsIgnoreCase(""))
			{
				/*************************** MHT-PID Process Integration ****************************/
				//this._strQuery ="SELECT ILBRANCHNAME, ILBRANCHCODE FROM (select DISTINCT ILBRANCHNAME, ILBRANCHCODE from NG_MHT_MST_ILLOCATION where  UPPER(ILBRANCHNAME) like UPPER(N'"+fieldValue+"%') ORDER BY ILBRANCHCODE) where ROWNUM <= " + this._iBatchSize;
				this._strQuery ="SELECT ILBRANCHNAME, ILBRANCHCODE, ZONEAREA FROM (select DISTINCT ILBRANCHNAME, ILBRANCHCODE, ZONEAREA from NG_MHT_MST_ILLOCATION where  UPPER(ILBRANCHNAME) like UPPER(N'"+fieldValue+"%') ORDER BY ILBRANCHNAME) where ROWNUM <= " + this._iBatchSize;
				/*************************** MHT-PID Process Integration ****************************/
			}
			else
			{
				this._strQuery ="SELECT ILBRANCHNAME, ILBRANCHCODE, ZONEAREA FROM (select DISTINCT ILBRANCHNAME, ILBRANCHCODE, ZONEAREA from NG_MHT_MST_ILLOCATION ORDER BY ILBRANCHNAME) where ROWNUM <= " + this._iBatchSize;
			}
			////// System.out.println("MHT_IL_LOCATION query "+_strQuery);
		}
		//modified code to fetch vertical code also
		else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("MHT_PRIMARY_VERTICAL")))
		{
			colCount=1;
			if(fieldValue!=null)
			{
				this._strQuery ="SELECT prim_vert_name,prim_vert_code FROM (select DISTINCT prim_vert_name,prim_vert_code from ng_mht_mst_prim_vertical where  UPPER(prim_vert_name) like UPPER(N'"+fieldValue+"%') ORDER BY prim_vert_name) where ROWNUM <= " + this._iBatchSize;
			}
			else
			{
				this._strQuery ="SELECT prim_vert_name,prim_vert_code FROM (select DISTINCT prim_vert_name,prim_vert_code from ng_mht_mst_prim_vertical ORDER BY prim_vert_name) where ROWNUM <= " + this._iBatchSize;
			}
			////// System.out.println("MHT_PRIMARY_VERTICAL query "+_strQuery);
		}
		else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("MHT_SUB_VERTICAL")))
		{
			colCount=2;
			if(fieldValue!=null)
			{
				this._strQuery ="SELECT sec_vert_name,sec_vert_code FROM (select DISTINCT sec_vert_name,sec_vert_code from ng_mht_mst_sec_vertical where  UPPER(sec_vert_name) like UPPER(N'"+fieldValue+"%') ORDER BY sec_vert_name) where ROWNUM <= " + this._iBatchSize;
			}
			else
			{
				this._strQuery ="SELECT sec_vert_name,sec_vert_code FROM (select DISTINCT sec_vert_name,sec_vert_code from ng_mht_mst_sec_vertical ORDER BY sec_vert_name) where ROWNUM <= " + this._iBatchSize;
			}
			////// System.out.println("MHT_SUB_VERTICAL query "+_strQuery);
		}
		/*********************** End MHT-PID Process Integration ****************************/
		else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("MHT_PRODUCT_NAME")))
		{
			colCount=2;
			if(fieldValue!=null)
			{
				this._strQuery ="SELECT productname, productcode FROM (select DISTINCT productname, productcode from ng_mht_mst_product where  UPPER(productname) like UPPER(N'"+fieldValue+"%') ORDER BY productcode) where ROWNUM <= " + this._iBatchSize;
			}
			else
			{
				this._strQuery ="SELECT productname, productcode FROM (select DISTINCT productname, productcode from ng_mht_mst_product ORDER BY productcode) where ROWNUM <= " + this._iBatchSize;
			}
			////// System.out.println("MHT_PRODUCT_NAME query "+_strQuery);
		}

		//channel source loading(BBG)
		else if((this.strSource != null) && (this.strSource.equalsIgnoreCase("MHT_SOURCE_BUSINESS")) && ((_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BBG") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking (BBG)") || _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BBG"))))
		{
			colCount=1;

			if(fieldValue!=null)
			{
				this._strQuery ="SELECT SOURCEBUSINESS FROM (select DISTINCT(SOURCEBUSINESS) from NG_MHT_MST_BBG_BUSINESS where  UPPER(SOURCEBUSINESS) like UPPER(N'"+fieldValue+"%') ORDER BY SOURCEBUSINESS) where ROWNUM <= " + this._iBatchSize;
			}
			else
			{
				this._strQuery ="SELECT SOURCEBUSINESS FROM (select DISTINCT(SOURCEBUSINESS) from NG_MHT_MST_BBG_BUSINESS ORDER BY SOURCEBUSINESS) where ROWNUM <= " + this._iBatchSize;
			}


		}

		//SOURCE BUSINESS
		else if((this.strSource != null) && (this.strSource.equalsIgnoreCase("MHT_SOURCE_BUSINESS")) && (_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("KRG")))
		{
			colCount=1;
			if(fieldValue!=null)
			{

				  this._strQuery = "select TXTSOURCEBUSINESS from (select DISTINCT(TXTSOURCEBUSINESS) from NG_MHT_MST_KRG where UPPER(TXTSOURCEBUSINESS) like UPPER(N'"+fieldValue+"%') and TXTSOURCEBUSINESS is not NULL ORDER BY TXTSOURCEBUSINESS) where  ROWNUM <= " + this._iBatchSize;
			}
			else
			{

				 this._strQuery = "select TXTSOURCEBUSINESS from (select DISTINCT(TXTSOURCEBUSINESS) from NG_MHT_MST_KRG and TXTSOURCEBUSINESS is not NULL ORDER BY TXTSOURCEBUSINESS) where  ROWNUM <= " + this._iBatchSize;
			}
		}

			//source business next
		else if(this.strSource != null && this.strSource.equalsIgnoreCase("MHT_SOURCE_BUSINESS") && _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BBG") && _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("ISEC"))
		{

			//First row
			ArrayList alRow = new ArrayList();
			alRow.add("Center Sales");
			alRows.add(alRow);

			//Second Row
			alRow = new ArrayList();
			alRow.add("Sub Broker");
			alRows.add(alRow);
		}

		//channel source
		else if ((this.strSource != null) && (this.strSource.equals("MHT_CHANNEL_SOURCE")))
		{
			colCount=1;
			if(fieldValue!=null)
			{
				this._strQuery ="select CHANNELSOURCING from(select DISTINCT(CHANNELSOURCING) from NG_MHT_MST_BBG_SOURCE where UPPER(CHANNELSOURCING) like UPPER(N'"+fieldValue+"%') ORDER BY CHANNELSOURCING) where ROWNUM <= " + this._iBatchSize;
			}
			else
			{
				this._strQuery ="select CHANNELSOURCING from(select DISTINCT(CHANNELSOURCING) from NG_MHT_MST_BBG_SOURCE ORDER BY CHANNELSOURCING) where ROWNUM <= " + this._iBatchSize;
			}
			 ////// System.out.println("alData--MHT_CHANNEL_SOURCE -->" + _strQuery);
		}

		/************************************************Start MHT SP Code CR-8127-69652 Search****************************************************************/
		//for bank branch name search

 		else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("MHT_BRANCH_ID")))
		{
			if(_objFormObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Policy Issuance") && _objFormObject.getNGValue("MHT_TYPE_OF_BUSINESS").equalsIgnoreCase("Intermediary") && _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BANCASSURANCE") && !_objFormObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER") && _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING"))
			{
				String channel_source=_objFormObject.getNGValue("MHT_CHANNEL_SOURCE");
				String deal_il_location=_objFormObject.getNGValue("MHT_DEAL_IL_LOCATION");
				colCount=2;
				if(fieldValue!=null)
				{
					this._strQuery ="select SOL_ID,BANK_BRANCH_NAME from(select distinct SOL_ID,BANK_BRANCH_NAME from NG_MHT_MST_SP_CODE where CHANNEL_SOURCE ='"+channel_source+"' and DEAL_IL_LOCATION='"+deal_il_location+"' and UPPER(BANK_BRANCH_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY BANK_BRANCH_NAME) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					this._strQuery ="select SOL_ID,BANK_BRANCH_NAME from(select distinct SOL_ID,BANK_BRANCH_NAME from NG_MHT_MST_SP_CODE where CHANNEL_SOURCE ='"+channel_source+"' and DEAL_IL_LOCATION='"+deal_il_location+"' ORDER BY BANK_BRANCH_NAME) where ROWNUM <= " + this._iBatchSize;
				}
				////// System.out.println("MHT_BANK_BRANCH_NAME****Search query "+_strQuery);
			}
			else if(_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BBG") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Branch Banking (BBG)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("KRG") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("HOME")|| (_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BBG") && _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BBG")))
			{
				colCount=4;
				if(fieldValue!=null)
				{
					this._strQuery ="select  branch_id,branch_name,sp_code,sp_name from (select distinct branch_id,branch_name,sp_code,sp_name from NG_MHT_MST_BBGANDHOMEBRANCH where UPPER(branch_id) like UPPER(N'"+fieldValue+"%') ORDER BY branch_id) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					this._strQuery ="select  branch_id,branch_name,sp_code,sp_name from (select distinct branch_id,branch_name,sp_code,sp_name from NG_MHT_MST_BBGANDHOMEBRANCH ORDER BY branch_id) where ROWNUM <= " + this._iBatchSize;
				}

			}
			// System.out.println("MHT_BRANCH_ID****Search query "+_strQuery);
			
		}
		//for sol id search
		else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("MHT_BANK_BRANCH_NAME")))
		{
			if(_objFormObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Policy Issuance") && _objFormObject.getNGValue("MHT_TYPE_OF_BUSINESS").equalsIgnoreCase("Intermediary") && _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BANCASSURANCE") && !_objFormObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER") && _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING"))
			{
				String channel_source=_objFormObject.getNGValue("MHT_CHANNEL_SOURCE");
				String deal_il_location=_objFormObject.getNGValue("MHT_DEAL_IL_LOCATION");
				colCount=2;
				if(fieldValue!=null)
				{
					this._strQuery ="select BANK_BRANCH_NAME,SOL_ID from(select distinct BANK_BRANCH_NAME,SOL_ID from NG_MHT_MST_SP_CODE where CHANNEL_SOURCE ='"+channel_source+"' and DEAL_IL_LOCATION='"+deal_il_location+"' and UPPER(SOL_ID) like UPPER(N'"+fieldValue+"%') ORDER BY SOL_ID) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					this._strQuery ="select BANK_BRANCH_NAME,SOL_ID from(select distinct BANK_BRANCH_NAME,SOL_ID from NG_MHT_MST_SP_CODE where CHANNEL_SOURCE ='"+channel_source+"' and DEAL_IL_LOCATION='"+deal_il_location+"' ORDER BY SOL_ID) where ROWNUM <= " + this._iBatchSize;
				}
				////// System.out.println("MHT_BRANCH_ID****Search query "+_strQuery);
			}
		}
		//for sp code search
		else if ((this.strSource != null) && (this.strSource.equalsIgnoreCase("MHT_SP_CODE")))
		{
			if(_objFormObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Policy Issuance") && _objFormObject.getNGValue("MHT_TYPE_OF_BUSINESS").equalsIgnoreCase("Intermediary") && _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BANCASSURANCE") && !_objFormObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER") && _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BRANCH BRANCHING"))
			{
				String deal_il_location=_objFormObject.getNGValue("MHT_DEAL_IL_LOCATION");
				String sol_id=_objFormObject.getNGValue("MHT_BRANCH_ID");
				String channel_source=_objFormObject.getNGValue("MHT_CHANNEL_SOURCE");
				colCount=3;
				if(fieldValue!=null)
				{
					this._strQuery ="select SP_CODE,SP_NAME,SP_PAN from(select distinct SP_CODE,SP_NAME,SP_PAN from NG_MHT_MST_SP_CODE where DEAL_IL_LOCATION='"+deal_il_location+"' and CHANNEL_SOURCE ='"+channel_source+"' and SOL_ID='"+sol_id+"' and UPPER(SP_CODE) like UPPER(N'"+fieldValue+"%') ORDER BY SP_CODE) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					this._strQuery ="select SP_CODE,SP_NAME,SP_PAN from(select distinct SP_CODE,SP_NAME,SP_PAN from NG_MHT_MST_SP_CODE where DEAL_IL_LOCATION='"+deal_il_location+"' and CHANNEL_SOURCE ='"+channel_source+"' and SOL_ID='"+sol_id+"' ORDER BY SOL_ID) where ROWNUM <= " + this._iBatchSize;
				}
				////// System.out.println("MHT_SP_CODE****Search query "+_strQuery);
			}
			/*********Start SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI **************/
			else
			{
				if((_objFormObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Policy Issuance") || _objFormObject.getNGValue("MHT_TYPE_OF_CATEGORY").equalsIgnoreCase("Endorsement")) || (!_objFormObject.getNGValue("MHT_TYPE_OF_BUSINESS").equalsIgnoreCase("Direct")) && (_objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BANCASSURANCE") || _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("BA")) && (_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("KRG") ) && (!_objFormObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER")))
				{
					// System.out.println("Inside the search sp code logic change");
					colCount=2;
					if(fieldValue!=null)
					{
						// System.out.println("Inside the if Search sp code: Yogesh");
						this._strQuery ="select SP_CODE,SP_NAME from(select distinct SP_CODE,SP_NAME from NG_MHT_SP_CODE_KRG where UPPER(SP_CODE) like UPPER(N'"+fieldValue+"%') ORDER BY SP_CODE) where ROWNUM <= " + this._iBatchSize;
					}
					else
					{
						// System.out.println("Inside the else Search sp code: Yogesh");
						this._strQuery ="select SP_CODE,SP_NAME from(select distinct SP_CODE,SP_NAME from NG_MHT_SP_CODE_KRG where ORDER BY SP_CODE) where ROWNUM <= " + this._iBatchSize;
					}
					// System.out.println("MHT_SP_CODE****Search query "+this._strQuery);
				}
			}
			/**************End SP Code Change in logic of SP code and name field in Omniflow HT,MHT and CPI ********/
		}
		/************************************************End MHT SP Code CR-8127-69652 Search ****************************************************************/
		/******Start MHT CR-8127-95325-GST-Omniflow development******/
			//MHT_TXTGST_STATE_NAME Search
			else if((this.strSource != null) && (this.strSource.equals("MHT_TXTGST_STATE_NAME")) && (_objFormObject.getNGValue("MHT_GST_REGISTERED").equalsIgnoreCase("Yes")) && !(_objFormObject.getNGValue("MHT_CASE_CATEGORY").equalsIgnoreCase("IPARTNER")))
			{
				// System.out.println("Inside the Search GST State: Yogesh");//select a.txtstatename,a.txtstatecode from NG_MHT_MST_GST_STATE a
				colCount=1;
				if(fieldValue!=null)
				{
					// System.out.println("Inside the if Search GST State: Yogesh");
					this._strQuery ="select txtstatename from(select distinct txtstatename from NG_MHT_MST_GST_STATE where UPPER(txtstatename) like UPPER(N'"+fieldValue+"%') ORDER BY txtstatename) where ROWNUM <= " + this._iBatchSize;
				}
				else
				{
					// System.out.println("Inside the else Search GST State: Yogesh");
					this._strQuery ="select txtstatename from(select distinct txtstatename from NG_MHT_MST_GST_STATE where ORDER BY txtstatename) where ROWNUM <= " + this._iBatchSize;
				}
				// System.out.println("MHT_TXTGST_STATE_NAME****Search query "+this._strQuery);
			}
			/******End MHT CR-8127-95325-GST-Omniflow development******/
		//BRANCH ID/UBO NAME
		else if ((this.strSource != null) && (this.strSource.equals("MHT_BRANCH_ID_UBO_NAME")))
		{
			colCount=1;
			if(_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Key Relationship Group (KRG 1)") || _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("Relationship Group (KRG 2)") ||
			_objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("KRG"))
			{

				if(fieldValue!=null)
				{
					this._strQuery ="select Txtbranchidubo from(select DISTINCT(Txtbranchidubo) from NG_MHT_MST_KRG where UPPER(Txtbranchidubo) like UPPER(N'"+fieldValue+"%') ORDER BY Txtbranchidubo) where ROWNUM <= " + this._iBatchSize;	//MHT-PID CR-8127-59721 enhancemnet additional CR
				}
				else
				{
					this._strQuery ="select Txtbranchidubo from(select DISTINCT(Txtbranchidubo) from NG_MHT_MST_KRG ORDER BY Txtbranchidubo) where ROWNUM <= " + this._iBatchSize;
				}
			////// System.out.println("MHT_BRANCH_ID_UBO_NAME count7:"+this._strQuery);
			}
		////// System.out.println("MHT_BRANCH_ID_UBO_NAME count8:"+this._strQuery);
		}
		//sm id name load
		else if ((this.strSource != null) && (this.strSource.equals("MHT_SM_NAME")))
		{
			colCount=2;
			if(fieldValue!=null)
			{
				/**************************** MHT-PID Process Integration ***************************/
				this._strQuery ="select PRIMARY_MO_NAME,TXT_HR_REF_NO from(select DISTINCT PRIMARY_MO_NAME, TXT_HR_REF_NO from MV_MHT_GENMST_EMPLOYEE where UPPER(PRIMARY_MO_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY TXT_HR_REF_NO) where ROWNUM <= " + this._iBatchSize;
				//this._strQuery ="select RM_NAME,EMP_CODE from(select me.RM_NAME as RM_NAME,me.EMP_CODE as EMP_CODE from NG_MHT_MST_RM me where UPPER(ME.RM_NAME) like UPPER(N'"+fieldValue+"%') ORDER BY me.EMP_CODE) where ROWNUM <= " + this._iBatchSize;
				/************************ End MHT-PID Process Integration ***************************/
			}
			else
			{
				/**************************** MHT-PID Process Integration ***************************/
				this._strQuery ="select PRIMARY_MO_NAME,TXT_HR_REF_NO from(select PRIMARY_MO_NAME, TXT_HR_REF_NO from MV_MHT_GENMST_EMPLOYEE ORDER BY TXT_HR_REF_NO) where ROWNUM <= " + this._iBatchSize;
				//this._strQuery ="select RM_NAME,EMP_CODE from(select me.RM_NAME as RM_NAME,me.EMP_CODE as EMP_CODE from NG_MHT_MST_RM me ORDER BY me.EMP_CODE) where ROWNUM <= " + this._iBatchSize;
				/************************ End MHT-PID Process Integration ***************************/
			}
			////// System.out.println("alData-- MHT_SM_NAME-->" + _strQuery);
		}
		

		//BANK NAME

		//bank name load
		else if ((this.strSource != null) && (this.strSource.equals("MHT_PAYMENT_TYPE1_BANKNAME") || this.strSource.equals("MHT_PAYMENT_TYPE2_BANKNAME") || this.strSource.equals("MHT_PAYMENT_TYPE3_BANKNAME")))
		{
			//_iTotalRecord=getTotalRecord(""); // comment by manoj jain
			colCount=2;
			if(fieldValue!=null)
			{
				this._strQuery ="select txtbankname,TXTBANKCODE from(select DISTINCT txtbankname,TXTBANKCODE from NG_MHT_MST_BankName where UPPER(txtbankname) like UPPER(N'"+fieldValue+"%') ORDER BY txtbankname) where ROWNUM <= " + this._iBatchSize;
			}
			else
			{
				this._strQuery ="select txtbankname,TXTBANKCODE from(select DISTINCT txtbankname,TXTBANKCODE from NG_MHT_MST_BankName ORDER BY txtbankname) where ROWNUM <= " + this._iBatchSize;
			}
		}
		
		/*************************** MHT-PID Process Integration ****************************/
		//PAYMENT TYPE load
		else if ((this.strSource != null) && (this.strSource.equals("MHT_PAYMENT_TYPE1") || this.strSource.equals("MHT_PAYMENT_TYPE2") || this.strSource.equals("MHT_PAYMENT_TYPE3")))
		{
			//_iTotalRecord=getTotalRecord(""); // comment by manoj jain
			colCount=2;
			if(fieldValue!=null)
			{
				this._strQuery ="select payment_mode from(select DISTINCT payment_mode from NG_MHT_PAYMENT_MODE_MASTER where UPPER(payment_mode) like UPPER(N'"+fieldValue+"%') ORDER BY payment_mode) where ROWNUM <= " + this._iBatchSize;
			}
			else
			{
				this._strQuery ="select payment_mode from(select DISTINCT payment_mode from NG_MHT_PAYMENT_MODE_MASTER ORDER BY payment_mode) where ROWNUM <= " + this._iBatchSize;
			}
		}
		/************************* End MHT-PID Process Integration **************************/

		//sub product search
			
		else if ((this.strSource != null) && (this.strSource.equals("MHT_CENTER_CODE_NAME")))
		{
			fieldValue=localNGPickList.getSearchFilterValue();
			_iTotalRecord=getTotalRecord(fieldValue);
			colCount=1;
			str1 = localNGPickList.getValueAt(0, 0);
			if(fieldValue!=null)
			{
					this._strQuery ="select CENTCODE_NAME from(select DISTINCT CENTCODE_NAME from NG_ICICI_MST_CENTERCODE where UPPER(CENTCODE_NAME)  like UPPER(N'%"+fieldValue+"%') ORDER BY CENTCODE_NAME) where ROWNUM <= " + this._iBatchSize;
			}
			else
			{
					this._strQuery ="select CENTCODE_NAME from(select DISTINCT CENTCODE_NAME from NG_ICICI_MST_CENTERCODE ORDER BY CENTCODE_NAME) where ROWNUM <= " + this._iBatchSize;
			}
		}

		/************************** CR-OF-MHT-1314-01 MHTProcess Implementaion End***********/
				
						
		if(this.strSource != null && this.strSource.equalsIgnoreCase("ICICILOMBARD_HT_SOURCE_BUSINESS") && _objFormObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL").equalsIgnoreCase("BBG") && _objFormObject.getNGValue("ICICILOMBARD_HT_VERTICAL").equalsIgnoreCase("ISEC"))	
		{
			result=localNGPickList.populateData(alRows);
		}	
        else if(this.strSource != null && this.strSource.equalsIgnoreCase("MHT_SOURCE_BUSINESS") && _objFormObject.getNGValue("MHT_SUB_VERTICAL").equalsIgnoreCase("BBG") && _objFormObject.getNGValue("MHT_PRIMARY_VERTICAL").equalsIgnoreCase("ISEC"))
		{
			result=localNGPickList.populateData(alRows);
		}
		else
		{
			 localNGPickList.clearData();
			result=localNGPickList.populateData(this._strQuery, colCount);
		}
		
		if (result)
		  {
			if ((this._iBatchNo * this._iBatchSize + 1 > this. _iTotalRecord) && (this. _iTotalRecord > (this._iBatchNo - 1) * this._iBatchSize))
			  localNGPickList.enableButton("Next", false);
			else
			{
			  localNGPickList.enableButton("Next", true);
			}
			localNGPickList.enableButton("Previous", false);
		  }
		  
		 // else
		 // {
			
			 //JOptionPane.showMessageDialog(null,"Your Search returned no results !!");
		// }

		  if (this._iBatchNo * this._iBatchSize > this. _iTotalRecord)
			localNGPickList.setStatus("Showing " + ((this._iBatchNo - 1) * this._iBatchSize + 1) + " - " + this. _iTotalRecord + " of " + this. _iTotalRecord);
		  else
			localNGPickList.setStatus("Showing " + ((this._iBatchNo - 1) * this._iBatchSize + 1) + " - " + (this._iBatchNo * this._iBatchSize) + " of " + this. _iTotalRecord);
		
	}	
	//Changes Made by Vishal Gupta IN oder to set product CODE for HT
	/*public void setProductCode()
		{
			if (_objFormObject.getNGValue("ICICILOMBARD_HT_PRODUCT_CODE").equalsIgnoreCase("") ||					_objFormObject.getNGValue("ICICILOMBARD_HT_PRODUCT_CODE").equalsIgnoreCase(null))
				{
					_objFormObject.setNGValue("ICICILOMBARD_HT_PRODUCT_CODE",_objFormObject.getNGItemText(		"PRODUCT_HIDDEN",1));
			////// System.out.println("ICICILOMBARD_HT_SUB_PRODUCT query "+_objFormObject.getNGValue("ICICILOMBARD_HT_PRODUCT_CODE"));
				}
		}*/
	//End of Changes Made by Vishal Gupta IN oder to set product CODE for HT


}

