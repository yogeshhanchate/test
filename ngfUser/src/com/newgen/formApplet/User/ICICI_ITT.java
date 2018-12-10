// Decompiled by DJ v3.7.7.81 Copyright 2004 Atanas Neshkov  Date: 6/19/2009 10:05:08 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ngfUser.java
//----------------------------------------------------------------------------------------------------
//		NEWGEN SOFTWARE TECHNOLOGIES LIMITED
//Group						: APPPROJ
//Product / Project			: ITT
//Module					: NGFUSER
//File Name					: ICICI_ITT.java
//Author					: Yogendra Kaushik
//Date written (DD/MM/YYYY)	: 31/03/2010
//Description				:Main file.
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
//import java.text.*;
import java.awt.Color;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import netscape.javascript.*;
import javax.swing.*;
import netscape.javascript.JSObject;
import com.newgen.formApplet.Controls.InputVerifier.NGTextComponent;


// Referenced classes of package com.newgen.formApplet.User:
//            NGFUserAdapter

public class ICICI_ITT 
{
    private NGFPropInterface formObject = null;
	ICICI_Common icicicmn=null;
	XMLParser xmlParser=null;
	ICICI_Validations ICICIval=null;
	SimpleDateFormat dateformat=new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat dateformat1=new SimpleDateFormat("dd/MM/yyyy");

	String sMainCode="";
	String sRowsAry[];
	String sColAry[];
	String sOutputxml="";
	String sInputxml="";
	String sResult="";
	JSObject js =null;
	Object obj=new Object();
	Object objC=new Object();
	String[] str=new String[1];
	String[] strC=new String[1];
	String url="";
	String sGenData="";
	int  UserId;
	XMLParser xmlParserGenData=null;

	boolean bError=false;
	boolean bBrncInfoErr=false;
	boolean isBusinessUser=false;
	boolean isTechnicalUser=false;

	boolean bUATDate=false;

	String Message="";
	ArrayList businessGroup=new ArrayList();
	Map<String,String> businessUserH=new HashMap<String,String>();
	Map<String,String> application=new HashMap<String,String>();

	String ApplicationAr[];
	String BuheadAr[];
	int MainFrameHg=0;
	public ICICI_ITT()
	{	
		xmlParser=new XMLParser();
		ICICIval=new ICICI_Validations();
	}
	public void set_ITT(NGFPropInterface pObj)
	{
		this.formObject = pObj;
		sGenData=formObject.getWFGeneralData();
		xmlParserGenData=new XMLParser(sGenData);
		icicicmn=new ICICI_Common(pObj);
	}
	public void paintFrame()
	{
	}
    public String executeCommand(String strInputXml)
    {
        String lstrProcessId = xmlParserGenData.getValueOf("ProcessInstanceId");
		String lstrProcessName = formObject.getWFProcessName();
		String sActivityName = formObject.getWFActivityName();
		String sCommandName;
		
		xmlParser.setInputXML(strInputXml);
		sCommandName = xmlParser.getValueOf("CommandName");
		js =formObject.getJSObject();
		if(sCommandName.equalsIgnoreCase("load"))
		{	
			formObject.setNGValue("ITT_REMARK","");
			if(sActivityName.equalsIgnoreCase("Work Introduction1"))
			{
				String sStatus="";
				formObject.setNGFormHeight(500);
				formObject.setNGFormWidth(600);	
				
				//Check the Group(s) of User
				sStatus=chkUserGroup();				

				//Disable Decision Frame
				formObject.setNGEnable("FRAME_DECISION",false);
				formObject.setNGVisible("FRAME_DECISION",false);		
				//Set Fields in Hide Mode.
				formObject.setNGVisible("VENDOR_NAME",false);
				formObject.setNGVisible("ITT_VENDOR_NAME",false);	
				formObject.setNGVisible("INTERNAL_DEVELOPMENT_TEAM",false);
				formObject.setNGVisible("ITT_INTERNAL_DEV_TEAM",false);

				//Disable History Button
				formObject.setNGVisible("Btn_History",false);

				//formObject.setNGLocked("ITT_CR_TYPE",false);
				//formObject.setNGLocked("ITT_CR_TYPE_TECHNICAL",false);

				formObject.NGFocus("ITT_BUSINESS_GROUP");		// Default Focus

				if(isBusinessUser)
				{
					formObject.NGClear("ITT_CR_TYPE");				
					formObject.NGAddItem("ITT_CR_TYPE", "Business");	
					formObject.setNGListIndex("ITT_CR_TYPE",0);					
				}
				else if (isTechnicalUser)
				{
					formObject.NGClear("ITT_CR_TYPE");									
					formObject.NGAddItem("ITT_CR_TYPE", "Technical");
					formObject.setNGListIndex("ITT_CR_TYPE",0);					
				}
			}
			else if(sActivityName.equalsIgnoreCase("Feasibility Study"))
			{
				formObject.setNGFormHeight(500);
				formObject.setNGFormWidth(600);				

				formObject.setNGLocked("ITT_BUSINESS_GROUP",false);
				formObject.setNGLocked("ITT_APPLICATION",false);
				//formObject.setNGLocked("ITT_COMPANY",false);
				formObject.setNGLocked("ITT_TECHNOLOGY_GROUP",false);
				formObject.setNGLocked("ITT_BENEFICIARY_ENTITIES",false);
				formObject.setNGLocked("ITT_BUSINESS_USER_HEAD",false);
				formObject.setNGLocked("ITT_CR_TYPE",false);
				//formObject.setNGLocked("ITT_CR_TYPE_TECHNICAL",false);
				formObject.setNGLocked("ITT_CR_PRIORITY",false);
				formObject.setNGLocked("ITT_REQUEST_TYPE",false);
				formObject.setNGLocked("ITT_STRATEGIC_CHANGE",false);
				formObject.setNGLocked("ITT_TECHNICAL_CHANGE",false);
				formObject.setNGEnable("ITT_ESTIMATED_BRS_SIGNOFF_DATE",false);
				formObject.setNGEnable("ITT_REQUESTED_GO_LIVE_DATE",false);
				formObject.setNGEnable("ITT_VENDOR_TEAM_DECSION",false);
				formObject.setNGLocked("ITT_NATURE_OF_REQUIREMENT",false);

				//Enable Decision Frame
				formObject.setNGVisible("FRAME_DECISION",true);
				//Set Fields in Hide Mode.
				formObject.setNGVisible("INTERNAL_DEVELOPMENT_TEAM",false);
				formObject.setNGVisible("ITT_INTERNAL_DEV_TEAM",false);
				formObject.setNGVisible("VENDOR_NAME",false);
				formObject.setNGVisible("ITT_VENDOR_NAME",false);		

				//Disable BA Decision and CR decision 
				formObject.setNGVisible("BA_DECISION",false);
				formObject.setNGVisible("ITT_BA_DECISION",false);
				formObject.setNGVisible("CR_DECISION",false);
				formObject.setNGVisible("ITT_CR_DECSION",false);

				formObject.NGFocus("ITT_FEASIBILITY_TEAM_DECISION");		// Default Focus
				
			}
			else if(sActivityName.equalsIgnoreCase("Internal Feasibility") || sActivityName.equalsIgnoreCase("External Feasibility"))
			{
				formObject.setNGFormHeight(500);
				formObject.setNGFormWidth(600);				

				formObject.setNGLocked("ITT_BUSINESS_GROUP",false);
				formObject.setNGLocked("ITT_APPLICATION",false);
				//formObject.setNGLocked("ITT_COMPANY",false);
				formObject.setNGLocked("ITT_TECHNOLOGY_GROUP",false);
				formObject.setNGLocked("ITT_BENEFICIARY_ENTITIES",false);
				formObject.setNGLocked("ITT_BUSINESS_USER_HEAD",false);
				formObject.setNGLocked("ITT_CR_TYPE",false);
				//formObject.setNGLocked("ITT_CR_TYPE_TECHNICAL",false);
				formObject.setNGLocked("ITT_CR_PRIORITY",false);
				formObject.setNGLocked("ITT_REQUEST_TYPE",false);
				formObject.setNGLocked("ITT_STRATEGIC_CHANGE",false);
				formObject.setNGLocked("ITT_TECHNICAL_CHANGE",false);
				formObject.setNGEnable("ITT_ESTIMATED_BRS_SIGNOFF_DATE",false);
				formObject.setNGEnable("ITT_REQUESTED_GO_LIVE_DATE",false);
				formObject.setNGEnable("ITT_FEASIBILITY_TEAM_DECISION",false);
				formObject.setNGLocked("ITT_NATURE_OF_REQUIREMENT",false);

				//Enable Decision Frame
				formObject.setNGVisible("FRAME_DECISION",true);

				//Disable BA Decision and CR decision 
				formObject.setNGVisible("BA_DECISION",false);
				formObject.setNGVisible("ITT_BA_DECISION",false);
				formObject.setNGVisible("CR_DECISION",false);
				formObject.setNGVisible("ITT_CR_DECSION",false);

				if(sActivityName.equalsIgnoreCase("Internal Feasibility"))
				{
					formObject.setNGVisible("INTERNAL_DEVELOPMENT_TEAM",true);
					formObject.setNGVisible("ITT_INTERNAL_DEV_TEAM",true);
					formObject.setNGVisible("VENDOR_NAME",false);
					formObject.setNGVisible("ITT_VENDOR_NAME",false);

					formObject.NGFocus("ITT_INTERNAL_DEV_TEAM");		// Default Focus	
				}
				if(sActivityName.equalsIgnoreCase("External Feasibility"))
				{
					formObject.setNGVisible("VENDOR_NAME",true);
					formObject.setNGVisible("ITT_VENDOR_NAME",true);
					formObject.setNGVisible("INTERNAL_DEVELOPMENT_TEAM",false);
					formObject.setNGVisible("ITT_INTERNAL_DEV_TEAM",false);

					formObject.NGFocus("ITT_VENDOR_NAME");		// Default Focus	
				}
				//Make ReadOnly field Vender Team Decision.
				formObject.setNGLocked("ITT_VENDOR_TEAM_DECSION",true);				
			}
			else if(sActivityName.equalsIgnoreCase("BA Team"))
			{
				formObject.setNGFormHeight(500);
				formObject.setNGFormWidth(600);				

				formObject.setNGLocked("ITT_BUSINESS_GROUP",false);
				formObject.setNGLocked("ITT_APPLICATION",false);
				//formObject.setNGLocked("ITT_COMPANY",false);
				formObject.setNGLocked("ITT_TECHNOLOGY_GROUP",false);
				formObject.setNGLocked("ITT_BENEFICIARY_ENTITIES",false);
				formObject.setNGLocked("ITT_BUSINESS_USER_HEAD",false);
				formObject.setNGLocked("ITT_CR_TYPE",false);
				//formObject.setNGLocked("ITT_CR_TYPE_TECHNICAL",false);
				formObject.setNGLocked("ITT_CR_PRIORITY",false);
				formObject.setNGLocked("ITT_REQUEST_TYPE",false);
				formObject.setNGLocked("ITT_STRATEGIC_CHANGE",false);
				formObject.setNGLocked("ITT_TECHNICAL_CHANGE",false);
				formObject.setNGEnable("ITT_ESTIMATED_BRS_SIGNOFF_DATE",false);
				formObject.setNGEnable("ITT_REQUESTED_GO_LIVE_DATE",false);
				formObject.setNGEnable("ITT_FEASIBILITY_TEAM_DECISION",false);
				formObject.setNGEnable("ITT_VENDOR_TEAM_DECSION",false);
				formObject.setNGLocked("ITT_NATURE_OF_REQUIREMENT",false);
				
				//Enable Decision Frame
				formObject.setNGVisible("FRAME_DECISION",true);

				//Disable BA Decision and CR decision 
				formObject.setNGVisible("BA_DECISION",true);
				formObject.setNGVisible("ITT_BA_DECISION",true);
				formObject.setNGVisible("CR_DECISION",false);
				formObject.setNGVisible("ITT_CR_DECSION",false);

				formObject.NGFocus("ITT_BA_DECISION");		// Default Focus
			}
			else if(sActivityName.equalsIgnoreCase("CR Exceptions"))
			{
				formObject.setNGFormHeight(500);
				formObject.setNGFormWidth(600);				

				formObject.setNGLocked("ITT_BUSINESS_GROUP",false);
				formObject.setNGLocked("ITT_APPLICATION",false);
				//formObject.setNGLocked("ITT_COMPANY",false);
				formObject.setNGLocked("ITT_TECHNOLOGY_GROUP",false);
				formObject.setNGLocked("ITT_BENEFICIARY_ENTITIES",false);
				formObject.setNGLocked("ITT_BUSINESS_USER_HEAD",false);
				formObject.setNGLocked("ITT_CR_TYPE",false);
				//formObject.setNGLocked("ITT_CR_TYPE_TECHNICAL",false);
				formObject.setNGLocked("ITT_CR_PRIORITY",false);
				formObject.setNGLocked("ITT_REQUEST_TYPE",false);
				formObject.setNGLocked("ITT_STRATEGIC_CHANGE",false);
				formObject.setNGLocked("ITT_TECHNICAL_CHANGE",false);
				formObject.setNGEnable("ITT_ESTIMATED_BRS_SIGNOFF_DATE",false);
				formObject.setNGEnable("ITT_REQUESTED_GO_LIVE_DATE",false);
				formObject.setNGEnable("ITT_FEASIBILITY_TEAM_DECISION",false);
				formObject.setNGEnable("ITT_VENDOR_TEAM_DECSION",false);
				formObject.setNGLocked("ITT_NATURE_OF_REQUIREMENT",false);				
							
				//Enable Decision Frame
				formObject.setNGVisible("FRAME_DECISION",true);

				//Disable BA Decision and CR decision 
				formObject.setNGLocked("BA_DECISION",false);
				formObject.setNGEnable("ITT_BA_DECISION",false);
				formObject.setNGVisible("CR_DECISION",true);
				formObject.setNGVisible("ITT_CR_DECSION",true);
				
				formObject.NGFocus("ITT_CR_DECSION");		// Default Focus
			}
			else if(sActivityName.equalsIgnoreCase("Project Manager"))
			{
				formObject.setNGFormHeight(950);
				formObject.setNGFormWidth(600);		

				formObject.setNGLocked("ITT_BUSINESS_GROUP",false);
				formObject.setNGLocked("ITT_APPLICATION",false);
				//formObject.setNGLocked("ITT_COMPANY",false);
				formObject.setNGLocked("ITT_TECHNOLOGY_GROUP",false);
				formObject.setNGLocked("ITT_BENEFICIARY_ENTITIES",false);
				formObject.setNGLocked("ITT_BUSINESS_USER_HEAD",false);
				formObject.setNGLocked("ITT_CR_TYPE",false);
				//formObject.setNGLocked("ITT_CR_TYPE_TECHNICAL",false);
				formObject.setNGLocked("ITT_CR_PRIORITY",false);
				formObject.setNGLocked("ITT_REQUEST_TYPE",false);
				formObject.setNGLocked("ITT_STRATEGIC_CHANGE",false);
				formObject.setNGLocked("ITT_TECHNICAL_CHANGE",false);

				formObject.setNGEnable("ITT_ESTIMATED_BRS_SIGNOFF_DATE",false);
				formObject.setNGEnable("ITT_REQUESTED_GO_LIVE_DATE",false);

				formObject.setNGEnable("ITT_FEASIBILITY_TEAM_DECISION",false);
				formObject.setNGEnable("ITT_VENDOR_TEAM_DECSION",false);				
				formObject.setNGEnable("ITT_BA_DECISION",false);
				formObject.setNGLocked("ITT_PROJECT_COST",false);
				formObject.setNGLocked("ITT_EFFORT",false);
				formObject.setNGLocked("ITT_RESOURCES",false);
				//formObject.setNGEnable("ITT_VENDOR_COSTING_TEAM_DECISION",false);
				formObject.setNGEnable("ITT_BUSINESS_TESTING_TEAM_DECISION",false);	
				formObject.setNGEnable("ITT_BA_TESTING_TEAM_DECISION",false);
				formObject.setNGEnable("ITT_BUH_DECISION",false);
				formObject.setNGEnable("ITT_TECHNICAL_HEAD_DECISION",false);	

				formObject.setNGLocked("ITT_NATURE_OF_REQUIREMENT",false);
				formObject.setNGEnable("ITT_PLANNED_DELIVERY_DATE",false);
				formObject.setNGLocked("ITT_REASON_FOR_CHANGE",false);
							
				//Enable Decision Frame
				formObject.setNGVisible("FRAME_DECISION",true);

				formObject.setNGVisible("DEPLOYMENT_DATE",false);
				formObject.setNGVisible("ITT_DEPLOYMENT_DATE",false);
				formObject.setNGEnable("ITT_DEPLOYMENT_DATE",false);

				//Hide The Grid data initially (Change Request)
				formObject.setNGVisible("TEST_CASE_NO",false);
				//formObject.setNGVisible("TESTCASENO",false);
				formObject.setNGVisible("TESTCASENO",false);
				formObject.setNGVisible("BUG_SEVERITY",false);
				formObject.setNGVisible("BUGSEVERITY",false);
				formObject.setNGVisible("BUG_STATUS",false);
				formObject.setNGVisible("BUGSTATUS",false);
				formObject.setNGVisible("BUG_DESCRIPTION",false);
				formObject.setNGVisible("BUGDESCRIPTION",false);
				formObject.setNGVisible("ADD_IN_LIST",false);
				formObject.setNGVisible("MODIFY_IN_LIST",false);
				formObject.setNGVisible("DELETE_IN_LIST",false);
				formObject.setNGVisible("LIST_VIEW",false);

				//Set Default Focus
				formObject.NGFocus("ITT_PLANNED_UAT_DATE");	

				if(!formObject.getNGValue("ITT_PREV_WRK_STEP").equalsIgnoreCase("Vndr Costing Team"))
				{
					formObject.setNGEnable("EXPECTED_UAT_SIGNOFF_DATE",false);
				}
				/*
				else if((!formObject.isNGLocked("ITT_PROJECT_COST")) && (!formObject.isNGLocked("ITT_EFFORT")) && (!formObject.isNGLocked("ITT_RESOURCES")))
				{
					System.out.println("ITT_PROJECT_COST	:"+formObject.isNGLocked("ITT_PROJECT_COST"));
					System.out.println("ITT_EFFORT	:"+formObject.isNGLocked("ITT_EFFORT"));
					System.out.println("ITT_RESOURCES	:"+formObject.isNGLocked("ITT_RESOURCES"));
					formObject.setNGEnable("EXPECTED_UAT_SIGNOFF_DATE",true);
				}
				*/

				//formObject.NGAddItem("ITT_PROJECT_MANAGER_DECISION", "Approve");	
				formObject.NGAddItem("ITT_PROJECT_MANAGER_DECISION", "Discard");
				formObject.NGAddItem("ITT_PROJECT_MANAGER_DECISION", "Reject");
				formObject.NGAddItem("ITT_PROJECT_MANAGER_DECISION", "Send to Vendor Costing Team");
			}
			else if(sActivityName.equalsIgnoreCase("Vndr Costing Team"))
			{
				formObject.setNGFormHeight(650);
				formObject.setNGFormWidth(600);		

				formObject.setNGVisible("FRAME_REASON_FOR_CHANGE",false);	

				formObject.setNGLocked("ITT_BUSINESS_GROUP",false);
				formObject.setNGLocked("ITT_APPLICATION",false);
				//formObject.setNGLocked("ITT_COMPANY",false);
				formObject.setNGLocked("ITT_TECHNOLOGY_GROUP",false);
				formObject.setNGLocked("ITT_BENEFICIARY_ENTITIES",false);
				formObject.setNGLocked("ITT_BUSINESS_USER_HEAD",false);
				formObject.setNGLocked("ITT_CR_TYPE",false);
				//formObject.setNGLocked("ITT_CR_TYPE_TECHNICAL",false);
				formObject.setNGLocked("ITT_CR_PRIORITY",false);
				formObject.setNGLocked("ITT_REQUEST_TYPE",false);
				formObject.setNGLocked("ITT_STRATEGIC_CHANGE",false);
				formObject.setNGLocked("ITT_TECHNICAL_CHANGE",false);
				formObject.setNGEnable("ITT_ESTIMATED_BRS_SIGNOFF_DATE",false);
				formObject.setNGEnable("ITT_REQUESTED_GO_LIVE_DATE",false);
				formObject.setNGEnable("ITT_FEASIBILITY_TEAM_DECISION",false);
				formObject.setNGEnable("ITT_VENDOR_TEAM_DECSION",false);				
				formObject.setNGEnable("ITT_BA_DECISION",false);
				formObject.setNGLocked("ITT_PROJECT_COST",true);
				formObject.setNGEnable("ITT_EFFORT",true);
				formObject.setNGEnable("ITT_RESOURCES",true);
				formObject.setNGEnable("ITT_PLANNED_UAT_DATE",false);
				formObject.setNGEnable("ITT_EXPECTED_UAT_SIGNOFF_DATE",false);				
				formObject.setNGEnable("ITT_PROJECT_MANAGER_DECISION",false);

				//formObject.setNGVisible("ITT_VENDOR_COSTING_TEAM_DECISION",false);
				formObject.setNGVisible("ITT_BUSINESS_TESTING_TEAM_DECISION",false);	
				formObject.setNGVisible("ITT_BA_TESTING_TEAM_DECISION",false);
				formObject.setNGVisible("ITT_BUH_DECISION",false);
				formObject.setNGVisible("ITT_TECHNICAL_HEAD_DECISION",false);
				formObject.setNGVisible("VENDER_COSTING_TEAM_DECISION",false);
				formObject.setNGVisible("BUSINESS_TESTING_TEAM_DECISION",false);	
				formObject.setNGVisible("BA_TESTING_TEAM_DECISION",false);
				formObject.setNGVisible("BUH_DECISION",false);
				formObject.setNGVisible("TECHNOLOGY_HEAD_DECISION",false);
				formObject.setNGLocked("ITT_NATURE_OF_REQUIREMENT",false);	
				//Hide the grid data
				formObject.setNGVisible("TEST_CASE_NO",false);
				formObject.setNGVisible("TESTCASENO",false);
				formObject.setNGVisible("BUG_SEVERITY",false);
				formObject.setNGVisible("BUGSEVERITY",false);
				formObject.setNGVisible("BUG_STATUS",false);
				formObject.setNGVisible("BUGSTATUS",false);
				formObject.setNGVisible("BUG_DESCRIPTION",false);
				formObject.setNGVisible("BUGDESCRIPTION",false);
				formObject.setNGVisible("ADD_IN_LIST",false);
				formObject.setNGVisible("MODIFY_IN_LIST",false);
				formObject.setNGVisible("DELETE_IN_LIST",false);
				formObject.setNGVisible("LIST_VIEW",false);
							
				//Enable Decision Frame
				formObject.setNGVisible("FRAME_DECISION",true);
				//Set Default Focus
				formObject.NGFocus("ITT_PROJECT_COST");		
			}
			else if(sActivityName.equalsIgnoreCase("Vndr Development"))
			{
				formObject.setNGFormHeight(800);
				formObject.setNGFormWidth(600);		
				//System.out.println("\nControl inside Vender Development\n");

				formObject.setNGLocked("ITT_BUSINESS_USER_HEAD",false);
				formObject.setNGLocked("ITT_CR_PRIORITY",false);
				formObject.setNGLocked("ITT_CR_TYPE",false);
				//formObject.setNGLocked("ITT_CR_TYPE_TECHNICAL",false);
				formObject.setNGLocked("ITT_REQUEST_TYPE",false);
				formObject.setNGLocked("ITT_STRATEGIC_CHANGE",false);
				formObject.setNGLocked("ITT_TECHNICAL_CHANGE",false);
				formObject.setNGEnable("ITT_ESTIMATED_BRS_SIGNOFF_DATE",false);
				formObject.setNGEnable("ITT_REQUESTED_GO_LIVE_DATE",false);
				formObject.setNGLocked("ITT_PROJECT_COST",false);
				formObject.setNGLocked("ITT_EFFORT",false);
				formObject.setNGLocked("ITT_RESOURCES",false);
				formObject.setNGEnable("ITT_FEASIBILITY_TEAM_DECISION",false);
				formObject.setNGEnable("ITT_VENDOR_TEAM_DECSION",false);				
				formObject.setNGEnable("ITT_BA_DECISION",false);
				formObject.setNGEnable("ITT_VENDOR_COSTING_TEAM_DECISION",false);
				formObject.setNGEnable("ITT_BUH_DECISION",false);
				formObject.setNGEnable("ITT_PLANNED_UAT_DATE",false);
				formObject.setNGEnable("ITT_EXPECTED_UAT_SIGNOFF_DATE",false);				
				formObject.setNGEnable("ITT_PROJECT_MANAGER_DECISION",false);

				//formObject.setNGVisible("VMT_DECISION",false);
				//formObject.setNGVisible("ITT_VMT_TEAM_DECISION",false);
				formObject.setNGVisible("BA_TESTING_TEAM_DECISION",false);
				formObject.setNGVisible("ITT_BA_TESTING_TEAM_DECISION",false);
				formObject.setNGVisible("BUSINESS_TESTING_TEAM_DECISION",false);
				formObject.setNGVisible("ITT_BUSINESS_TESTING_TEAM_DECISION",false);
				formObject.setNGVisible("BUG_TYPE",false);
				formObject.setNGVisible("ITT_BUG_TYPE",false);
				formObject.setNGVisible("PAT_TEAM_DECISION",false);
				formObject.setNGVisible("ITT_PAT_TEAM_DECSION",false);
				formObject.setNGVisible("INFRA_DECISION",false);
				formObject.setNGVisible("ITT_INFRA_TEAM_DECISION",false);
				formObject.setNGLocked("ITT_NATURE_OF_REQUIREMENT",false);
				formObject.setNGEnable("ITT_PLANNED_DELIVERY_DATE",false);
				formObject.setNGEnable("ITT_DEPLOYMENT_DATE",false);
				/*
				//Hide the grid data initially.
				formObject.setNGVisible("TEST_CASE_NO",false);
				formObject.setNGVisible("TESTCASENO",false);
				formObject.setNGVisible("BUG_SEVERITY",false);
				formObject.setNGVisible("BUGSEVERITY",false);
				formObject.setNGVisible("BUG_STATUS",false);
				formObject.setNGVisible("BUGSTATUS",false);
				formObject.setNGVisible("BUG_DESCRIPTION",false);
				formObject.setNGVisible("BUGDESCRIPTION",false);
				formObject.setNGVisible("ADD_IN_LIST",false);
				formObject.setNGVisible("MODIFY_IN_LIST",false);
				formObject.setNGVisible("DELETE_IN_LIST",false);
				formObject.setNGVisible("LIST_VIEW",false);
				*/
				//Enable Decision Frame
				formObject.setNGVisible("FRAME_DECISION",true);
				//Set Default Focus
				formObject.NGFocus("ITT_REMARK");		
			}
			else if(sActivityName.equalsIgnoreCase("Business Head"))
			{
				formObject.setNGFormHeight(600);
				formObject.setNGFormWidth(600);		

				formObject.setNGLocked("ITT_BUSINESS_GROUP",false);
				formObject.setNGLocked("ITT_APPLICATION",false);
				//formObject.setNGLocked("ITT_COMPANY",false);
				formObject.setNGLocked("ITT_TECHNOLOGY_GROUP",false);
				formObject.setNGLocked("ITT_BENEFICIARY_ENTITIES",false);
				formObject.setNGLocked("ITT_BUSINESS_USER_HEAD",false);
				formObject.setNGLocked("ITT_CR_TYPE",false);
				//formObject.setNGLocked("ITT_CR_TYPE_TECHNICAL",false);
				formObject.setNGLocked("ITT_CR_PRIORITY",false);
				formObject.setNGLocked("ITT_REQUEST_TYPE",false);
				formObject.setNGLocked("ITT_STRATEGIC_CHANGE",false);
				formObject.setNGLocked("ITT_TECHNICAL_CHANGE",false);
				formObject.setNGEnable("ITT_ESTIMATED_BRS_SIGNOFF_DATE",false);
				formObject.setNGEnable("ITT_REQUESTED_GO_LIVE_DATE",false);

				formObject.setNGEnable("ITT_FEASIBILITY_TEAM_DECISION",false);
				formObject.setNGEnable("ITT_VENDOR_TEAM_DECSION",false);				
				formObject.setNGEnable("ITT_BA_DECISION",false);
				formObject.setNGLocked("ITT_PROJECT_COST",false);
				formObject.setNGLocked("ITT_EFFORT",false);
				formObject.setNGLocked("ITT_RESOURCES",false);			
				//Bug No: 25 System Testing
				//formObject.setNGVisible("ITT_VENDOR_COSTING_TEAM_DECISION",true);
				//formObject.setNGVisible("VENDER_COSTING_TEAM_DECISION",true);
				//formObject.setNGEnable("ITT_VENDOR_COSTING_TEAM_DECISION",false);

				formObject.setNGVisible("TECHNICAL_HEAD_DECISION",false);
				formObject.setNGVisible("ITT_TECHNICAL_HEAD_DECISION",false);
				formObject.setNGLocked("ITT_NATURE_OF_REQUIREMENT",false);
				formObject.setNGEnable("ITT_PLANNED_DELIVERY_DATE",false);
										
				//Enable Decision Frame
				formObject.setNGVisible("FRAME_DECISION",true);
				//Set Default Focus
				formObject.NGFocus("ITT_BUH_DECISION");		
			}
			else if(sActivityName.equalsIgnoreCase("Technology Head"))
			{
				formObject.setNGFormHeight(600);
				formObject.setNGFormWidth(600);		

				formObject.setNGLocked("ITT_BUSINESS_GROUP",false);
				formObject.setNGLocked("ITT_APPLICATION",false);
				//formObject.setNGLocked("ITT_COMPANY",false);
				formObject.setNGLocked("ITT_TECHNOLOGY_GROUP",false);
				formObject.setNGLocked("ITT_BENEFICIARY_ENTITIES",false);
				formObject.setNGLocked("ITT_BUSINESS_USER_HEAD",false);
				formObject.setNGLocked("ITT_CR_TYPE",false);
				//formObject.setNGLocked("ITT_CR_TYPE_TECHNICAL",false);
				formObject.setNGLocked("ITT_CR_PRIORITY",false);
				formObject.setNGLocked("ITT_REQUEST_TYPE",false);
				formObject.setNGLocked("ITT_STRATEGIC_CHANGE",false);
				formObject.setNGLocked("ITT_TECHNICAL_CHANGE",false);
				formObject.setNGEnable("ITT_ESTIMATED_BRS_SIGNOFF_DATE",false);
				formObject.setNGEnable("ITT_REQUESTED_GO_LIVE_DATE",false);

				formObject.setNGEnable("ITT_FEASIBILITY_TEAM_DECISION",false);
				formObject.setNGEnable("ITT_VENDOR_TEAM_DECSION",false);				
				formObject.setNGEnable("ITT_BA_DECISION",false);
				formObject.setNGLocked("ITT_PROJECT_COST",false);
				formObject.setNGLocked("ITT_EFFORT",false);
				formObject.setNGLocked("ITT_RESOURCES",false);			

				formObject.setNGVisible("ITT_VENDOR_COSTING_TEAM_DECISION",false);
				formObject.setNGVisible("VENDER_COSTING_TEAM_DECISION",false);
				formObject.setNGVisible("TECHNICAL_HEAD_DECISION",true);
				formObject.setNGVisible("ITT_TECHNICAL_HEAD_DECISION",true);

				formObject.setNGVisible("BUH_DECISION",false);
				formObject.setNGVisible("ITT_BUH_DECISION",false);
				formObject.setNGLocked("ITT_NATURE_OF_REQUIREMENT",false);
				formObject.setNGEnable("ITT_PLANNED_DELIVERY_DATE",false);
										
				//Enable Decision Frame
				formObject.setNGVisible("FRAME_DECISION",true);
				//Set Default Focus
				formObject.NGFocus("TECHNICAL_HEAD_DECISION");		
			}
			else if((sActivityName.equalsIgnoreCase("BA Testing Team")) || (sActivityName.equalsIgnoreCase("Issue Resolution")) || (sActivityName.equalsIgnoreCase("Busines Testing Team")))
			{
				formObject.setNGFormHeight(870);
				formObject.setNGFormWidth(600);		
				//System.out.println("\nControl inside BA Testing Team\n");

				formObject.setNGLocked("ITT_BUSINESS_USER_HEAD",false);
				formObject.setNGLocked("ITT_CR_PRIORITY",false);
				formObject.setNGLocked("ITT_CR_TYPE",false);
				//formObject.setNGLocked("ITT_CR_TYPE_TECHNICAL",false);
				formObject.setNGLocked("ITT_REQUEST_TYPE",false);
				formObject.setNGLocked("ITT_STRATEGIC_CHANGE",false);
				formObject.setNGLocked("ITT_TECHNICAL_CHANGE",false);
				formObject.setNGEnable("ITT_ESTIMATED_BRS_SIGNOFF_DATE",false);
				formObject.setNGEnable("ITT_REQUESTED_GO_LIVE_DATE",false);
				formObject.setNGLocked("ITT_PROJECT_COST",false);
				formObject.setNGLocked("ITT_EFFORT",false);
				formObject.setNGLocked("ITT_RESOURCES",false);
				formObject.setNGEnable("ITT_FEASIBILITY_TEAM_DECISION",false);
				formObject.setNGEnable("ITT_VENDOR_TEAM_DECSION",false);				
				formObject.setNGEnable("ITT_BA_DECISION",false);
				//formObject.setNGEnable("ITT_VENDOR_COSTING_TEAM_DECISION",false);
				formObject.setNGEnable("ITT_BUH_DECISION",false);
				formObject.setNGEnable("ITT_PLANNED_UAT_DATE",false);
				formObject.setNGEnable("ITT_EXPECTED_UAT_SIGNOFF_DATE",false);				
				formObject.setNGEnable("ITT_PROJECT_MANAGER_DECISION",false);
				formObject.setNGEnable("ITT_BUSINESS_TESTING_TEAM_DECISION",false);

				formObject.setNGVisible("PAT_TEAM_DECISION",false);
				formObject.setNGVisible("ITT_PAT_TEAM_DECSION",false);				

				formObject.setNGVisible("INFRA_DECISION",false);
				formObject.setNGVisible("ITT_INFRA_TEAM_DECISION",false);
				formObject.setNGLocked("ITT_NATURE_OF_REQUIREMENT",false);		
				formObject.setNGEnable("ITT_PLANNED_DELIVERY_DATE",false);
				
				formObject.setNGLocked("ITT_BUG_TYPE",false);

				//Enable Decision Frame
				formObject.setNGVisible("FRAME_DECISION",true);
				//Set Default Focus
				formObject.NGFocus("ITT_BA_TESTING_TEAM_DECISION");		

				if(sActivityName.equalsIgnoreCase("Issue Resolution"))
				{
					formObject.setNGVisible("BUSINESS_TESTING_TEAM_DECISION",false);
					formObject.setNGVisible("ITT_BUSINESS_TESTING_TEAM_DECISION",false);
					formObject.setNGLocked("ITT_BUG_TYPE",true);
					formObject.setNGEnable("ITT_BA_TESTING_TEAM_DECISION",false);
					formObject.NGFocus("ITT_REMARK");		
				}
				if(sActivityName.equalsIgnoreCase("Busines Testing Team"))
				{
					formObject.setNGEnable("ITT_BA_TESTING_TEAM_DECISION",false);
					formObject.setNGLocked("ITT_BUG_TYPE",false);

					formObject.setNGLocked("BUSINESS_TESTING_TEAM_DECISION",true);
					formObject.setNGEnable("ITT_BUSINESS_TESTING_TEAM_DECISION",true);
					formObject.NGFocus("BUSINESS_TESTING_TEAM_DECISION");		
				}
			}
			else if(sActivityName.equalsIgnoreCase("Infra Team"))
			{
				formObject.setNGFormHeight(830);
				formObject.setNGFormWidth(600);		

				formObject.setNGLocked("ITT_BUSINESS_GROUP",false);
				formObject.setNGLocked("ITT_APPLICATION",false);
				//formObject.setNGLocked("ITT_COMPANY",false);
				formObject.setNGLocked("ITT_TECHNOLOGY_GROUP",false);
				formObject.setNGLocked("ITT_BENEFICIARY_ENTITIES",false);
				formObject.setNGLocked("ITT_BUSINESS_USER_HEAD",false);
				formObject.setNGLocked("ITT_CR_TYPE",false);
				//formObject.setNGLocked("ITT_CR_TYPE_TECHNICAL",false);
				formObject.setNGLocked("ITT_CR_PRIORITY",false);
				formObject.setNGLocked("ITT_REQUEST_TYPE",false);
				formObject.setNGLocked("ITT_STRATEGIC_CHANGE",false);
				formObject.setNGLocked("ITT_TECHNICAL_CHANGE",false);
				formObject.setNGEnable("ITT_ESTIMATED_BRS_SIGNOFF_DATE",false);
				formObject.setNGEnable("ITT_REQUESTED_GO_LIVE_DATE",false);

				formObject.setNGEnable("ITT_FEASIBILITY_TEAM_DECISION",false);
				formObject.setNGEnable("ITT_VENDOR_TEAM_DECSION",false);				
				formObject.setNGEnable("ITT_BA_DECISION",false);
				formObject.setNGLocked("ITT_PROJECT_COST",false);
				formObject.setNGLocked("ITT_EFFORT",false);
				formObject.setNGLocked("ITT_RESOURCES",false);			
				formObject.setNGEnable("ITT_PLANNED_UAT_DATE",false);
				formObject.setNGEnable("ITT_EXPECTED_UAT_SIGNOFF_DATE",false);
				//formObject.setNGLocked("ITT_VENDOR_COSTING_TEAM_DECISION",false);
				formObject.setNGEnable("ITT_PROJECT_MANAGER_DECISION",false);	
				formObject.setNGEnable("ITT_BUH_DECISION",false);	

				//formObject.setNGVisible("VMT_DECISION",false);
				//formObject.setNGVisible("ITT_VMT_TEAM_DECISION",false);
				formObject.setNGVisible("BA_TESTING_TEAM_DECISION",false);
				formObject.setNGVisible("ITT_BA_TESTING_TEAM_DECISION",false);
				formObject.setNGVisible("BUSINESS_TESTING_TEAM_DECISION",false);
				formObject.setNGVisible("ITT_BUSINESS_TESTING_TEAM_DECISION",false);
				formObject.setNGVisible("BUG_TYPE",false);
				formObject.setNGVisible("ITT_BUG_TYPE",false);
				formObject.setNGVisible("PAT_TEAM_DECISION",false);
				formObject.setNGVisible("ITT_PAT_TEAM_DECSION",false);
				formObject.setNGLocked("ITT_NATURE_OF_REQUIREMENT",false);
				formObject.setNGEnable("ITT_PLANNED_DELIVERY_DATE",false);
				/*
				//Hide the grid data initially.
				formObject.setNGVisible("TEST_CASE_NO",false);
				formObject.setNGVisible("TESTCASENO",false);
				formObject.setNGVisible("BUG_SEVERITY",false);
				formObject.setNGVisible("BUGSEVERITY",false);
				formObject.setNGVisible("BUG_STATUS",false);
				formObject.setNGVisible("BUGSTATUS",false);
				formObject.setNGVisible("BUG_DESCRIPTION",false);
				formObject.setNGVisible("BUGDESCRIPTION",false);
				formObject.setNGVisible("ADD_IN_LIST",false);
				formObject.setNGVisible("MODIFY_IN_LIST",false);
				formObject.setNGVisible("DELETE_IN_LIST",false);
				formObject.setNGVisible("LIST_VIEW",false);
				*/
				formObject.setNGVisible("ITT_INFRA_TEAM_DECISION",true);
				formObject.setNGVisible("ITT_REMARK",true);
										
				//Enable Decision Frame
				formObject.setNGVisible("FRAME_DECISION",true);
				//Set Default Focus
				formObject.NGFocus("ITT_INFRA_TEAM_DECISION");		
			}
			else if(sActivityName.equalsIgnoreCase("PAT"))
			{
				formObject.setNGFormHeight(868);
				formObject.setNGFormWidth(600);		

				formObject.setNGLocked("ITT_BUSINESS_GROUP",false);
				formObject.setNGLocked("ITT_APPLICATION",false);
				//formObject.setNGLocked("ITT_COMPANY",false);
				formObject.setNGLocked("ITT_TECHNOLOGY_GROUP",false);
				formObject.setNGLocked("ITT_BENEFICIARY_ENTITIES",false);
				formObject.setNGLocked("ITT_BUSINESS_USER_HEAD",false);
				formObject.setNGLocked("ITT_CR_TYPE",false);
				//formObject.setNGLocked("ITT_CR_TYPE_TECHNICAL",false);
				formObject.setNGLocked("ITT_CR_PRIORITY",false);
				formObject.setNGLocked("ITT_REQUEST_TYPE",false);
				formObject.setNGLocked("ITT_STRATEGIC_CHANGE",false);
				formObject.setNGLocked("ITT_TECHNICAL_CHANGE",false);
				formObject.setNGEnable("ITT_ESTIMATED_BRS_SIGNOFF_DATE",false);
				formObject.setNGEnable("ITT_REQUESTED_GO_LIVE_DATE",false);
				formObject.setNGEnable("ITT_FEASIBILITY_TEAM_DECISION",false);
				formObject.setNGEnable("ITT_VENDOR_TEAM_DECSION",false);				
				formObject.setNGEnable("ITT_BA_DECISION",false);
				formObject.setNGLocked("ITT_PROJECT_COST",false);
				formObject.setNGLocked("ITT_EFFORT",false);
				formObject.setNGLocked("ITT_RESOURCES",false);			
				formObject.setNGEnable("ITT_PLANNED_UAT_DATE",false);
				formObject.setNGEnable("ITT_EXPECTED_UAT_SIGNOFF_DATE",false);
				//formObject.setNGEnable("ITT_VENDOR_COSTING_TEAM_DECISION",false);
				formObject.setNGEnable("ITT_PROJECT_MANAGER_DECISION",false);	
				formObject.setNGEnable("ITT_BUH_DECISION",false);	

				//formObject.setNGLocked("VMT_DECISION",false);
				//formObject.setNGLocked("ITT_VMT_TEAM_DECISION",false);
				formObject.setNGLocked("BA_TESTING_TEAM_DECISION",false);
				formObject.setNGEnable("ITT_BA_TESTING_TEAM_DECISION",false);
				formObject.setNGLocked("BUSINESS_TESTING_TEAM_DECISION",false);
				formObject.setNGEnable("ITT_BUSINESS_TESTING_TEAM_DECISION",false);
				formObject.setNGLocked("BUG_TYPE",false);
				formObject.setNGLocked("ITT_BUG_TYPE",false);
				//formObject.setNGLocked("PAT_TEAM_DECISION",false);
				//formObject.setNGLocked("ITT_PAT_TEAM_DECSION",false);
				formObject.setNGLocked("ITT_NATURE_OF_REQUIREMENT",false);
				formObject.setNGEnable("ITT_INFRA_TEAM_DECISION",false);
				formObject.setNGEnable("ITT_PLANNED_DELIVERY_DATE",false);

				formObject.setNGVisible("ITT_PAT_TEAM_DECSION",true);
				formObject.setNGVisible("ITT_REMARK",true);										
				formObject.setNGVisible("INFRA_DECISION",false);
				formObject.setNGVisible("ITT_INFRA_TEAM_DECISION",false);			
				//Enable Decision Frame
				formObject.setNGVisible("FRAME_DECISION",true);
				//Set Default Focus
				formObject.NGFocus("ITT_PAT_TEAM_DECSION");		
			}
			else if((sActivityName.equalsIgnoreCase("Discard1")) || (sActivityName.equalsIgnoreCase("Work Exit1")))
			{
				formObject.setNGFormHeight(868);
				formObject.setNGFormWidth(600);		
			}
			//Remove ToolTip		
			formObject.removeNGTooltip();
		}
		return "";
 }
	
 public void executeEvent(String strInputXml)
 {		
		try
		{
       		
		}
		catch(NullPointerException nex)
		{
			nex.printStackTrace();
			bError=true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			bError=true;
		}
 }

 public String fieldValueBagSet(String sActivityName)
 {	 
	 try
	    {	
			int MainFrameHg=0;
			String sStatus="";
	
			// Check the Group(s) of User
			sStatus=chkUserGroup();				
			/*
			//This Code is use to direct call from DB using Hash Map.to get value of BusinessGroup.
			String str_data = getDataBGroupBuhApp();
			System.out.println("Return Value of getDataBGroupBuhApp	->	"+str_data);
			*/

			if(sActivityName.equalsIgnoreCase("Work Introduction1"))
			{	
				String strBusinessGroup = fillBusinessGroup();
				//System.out.println("Return Value of fillBusinessGroup	->	"+strBusinessGroup);			
				formObject.setSpecificDateFormat("ITT_ESTIMATED_BRS_SIGNOFF_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_REQUESTED_GO_LIVE_DATE","dd/MM/yyyy");
				//Set Remark Capson as Brief Description
				formObject.setNGValue("REMARKS","Brief Description");

				//Setting Frame Hight
				formObject.setNGControlTop("FRAME_REMARKS",formObject.getNGControlHeight("FRAME_COMMON")+1+formObject.getNGControlHeight("FRAME_REMARKS"));
			}
			else if(sActivityName.equalsIgnoreCase("Feasibility Study"))
			{
				formObject.setSpecificDateFormat("ITT_ESTIMATED_BRS_SIGNOFF_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_REQUESTED_GO_LIVE_DATE","dd/MM/yyyy");	
				
				if(formObject.getNGValue("ITT_PREV_WRK_STEP").equalsIgnoreCase("Internal Feasibility") || formObject.getNGValue("ITT_PREV_WRK_STEP").equalsIgnoreCase("External Feasibility"))
				{
					System.out.println("ITT_PREV_WRK_STEP: "+formObject.getNGValue("ITT_PREV_WRK_STEP"));
					System.out.println("Value Saved in DB "+formObject.getNGValue("ITT_FEASIBILITY_TEAM_DECISION"));

					if(!formObject.getNGValue("ITT_FEASIBILITY_TEAM_DECISION").equalsIgnoreCase("Accept"))
						formObject.NGAddItem("ITT_FEASIBILITY_TEAM_DECISION", "Accept");
					if(!formObject.getNGValue("ITT_FEASIBILITY_TEAM_DECISION").equalsIgnoreCase("Reject"))
						formObject.NGAddItem("ITT_FEASIBILITY_TEAM_DECISION", "Reject");

					if((!formObject.getNGValue("ITT_FEASIBILITY_TEAM_DECISION").equalsIgnoreCase("Send To Internal Feasibility Team")) && (formObject.getNGValue("ITT_PREV_WRK_STEP").equalsIgnoreCase("Internal Feasibility")))
						formObject.NGAddItem("ITT_FEASIBILITY_TEAM_DECISION", "Send To Internal Feasibility Team");
					if((!formObject.getNGValue("ITT_FEASIBILITY_TEAM_DECISION").equalsIgnoreCase("Send To External Feasibility Team")) && (formObject.getNGValue("ITT_PREV_WRK_STEP").equalsIgnoreCase("External Feasibility")))
						formObject.NGAddItem("ITT_FEASIBILITY_TEAM_DECISION", "Send To External Feasibility Team");

				}
				else if(!formObject.getNGValue("ITT_PREV_WRK_STEP").equalsIgnoreCase("Internal Feasibility") || !formObject.getNGValue("ITT_PREV_WRK_STEP").equalsIgnoreCase("External Feasibility"))
				{
					System.out.println("Value Saved in DB "+formObject.getNGValue("ITT_FEASIBILITY_TEAM_DECISION"));
					if(formObject.getNGValue("ITT_FEASIBILITY_TEAM_DECISION").equalsIgnoreCase("Send To Internal Feasibility Team"))
					{
						formObject.NGAddItem("ITT_FEASIBILITY_TEAM_DECISION", "Send To External Feasibility Team");
					}
					else if(formObject.getNGValue("ITT_FEASIBILITY_TEAM_DECISION").equalsIgnoreCase("Send To External Feasibility Team"))
					{
						formObject.NGAddItem("ITT_FEASIBILITY_TEAM_DECISION", "Send To Internal Feasibility Team");
					}
					else
					{
						formObject.NGAddItem("ITT_FEASIBILITY_TEAM_DECISION", "Send To Internal Feasibility Team");
						formObject.NGAddItem("ITT_FEASIBILITY_TEAM_DECISION", "Send To External Feasibility Team");
					}
				}

				if(formObject.getNGValue("ITT_PREV_WRK_STEP").trim().equalsIgnoreCase("Internal Feasibility"))
				{
					formObject.setNGVisible("INTERNAL_DEVELOPMENT_TEAM",true);
					formObject.setNGVisible("ITT_INTERNAL_DEV_TEAM",true);
					formObject.setNGLocked("ITT_INTERNAL_DEV_TEAM",false);
				}
				if(formObject.getNGValue("ITT_PREV_WRK_STEP").trim().equalsIgnoreCase("External Feasibility"))
				{
					formObject.setNGVisible("VENDOR_NAME",true);
					formObject.setNGVisible("ITT_VENDOR_NAME",true);
					formObject.setNGLocked("ITT_VENDOR_NAME",false);
				}

				//Setting Frame Hight
				formObject.setNGControlHeight("FRAME_DECISION",formObject.getNGControlHeight("FRAME_DECISION")-23);
				formObject.setNGControlTop("FRAME_REMARKS",formObject.getNGControlTop("FRAME_REMARKS")-23);
				
			}
			else if((sActivityName.equalsIgnoreCase("Internal Feasibility")) || (sActivityName.equalsIgnoreCase("External Feasibility")))
			{
				//Call for filling value of Vendor Name and Internal Development Team
				String strVndrDevTeam = fillVendorNameInternalDevTeam(sActivityName);

				formObject.setSpecificDateFormat("ITT_ESTIMATED_BRS_SIGNOFF_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_REQUESTED_GO_LIVE_DATE","dd/MM/yyyy");					

				//Setting Frame Hight
				formObject.setNGControlHeight("FRAME_DECISION",formObject.getNGControlHeight("FRAME_DECISION")-23);
				formObject.setNGControlTop("FRAME_REMARKS",formObject.getNGControlTop("FRAME_REMARKS")-23);
			}
			else if(sActivityName.equalsIgnoreCase("BA Team"))
			{
				System.out.println("ITT_INTERNAL_DEV_TEAM	->"+formObject.getNGValue("ITT_INTERNAL_DEV_TEAM"));
				System.out.println("ITT_VENDOR_NAME	->"+formObject.getNGValue("ITT_VENDOR_NAME"));
				if(!formObject.getNGValue("ITT_INTERNAL_DEV_TEAM").equalsIgnoreCase(""))
				{
					formObject.setNGVisible("INTERNAL_DEVELOPMENT_TEAM",true);
					formObject.setNGVisible("ITT_INTERNAL_DEV_TEAM",true);
					formObject.setNGVisible("VENDOR_NAME",false);
					formObject.setNGVisible("ITT_VENDOR_NAME",false);
					formObject.setNGLocked("ITT_INTERNAL_DEV_TEAM",false);
				}
				if(!formObject.getNGValue("ITT_VENDOR_NAME").equalsIgnoreCase(""))
				{
					formObject.setNGVisible("VENDOR_NAME",true);
					formObject.setNGVisible("ITT_VENDOR_NAME",true);
					formObject.setNGVisible("INTERNAL_DEVELOPMENT_TEAM",false);
					formObject.setNGVisible("ITT_INTERNAL_DEV_TEAM",false);
					formObject.setNGLocked("ITT_VENDOR_NAME",false);
				}

				//Setting Date Format
				formObject.setSpecificDateFormat("ITT_ESTIMATED_BRS_SIGNOFF_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_REQUESTED_GO_LIVE_DATE","dd/MM/yyyy");
			}
			else if(sActivityName.equalsIgnoreCase("CR Exceptions"))
			{					
				//Setting Date Format
				formObject.setSpecificDateFormat("ITT_ESTIMATED_BRS_SIGNOFF_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_REQUESTED_GO_LIVE_DATE","dd/MM/yyyy");

				if(!formObject.getNGValue("ITT_INTERNAL_DEV_TEAM").equalsIgnoreCase(""))
				{
					formObject.setNGVisible("INTERNAL_DEVELOPMENT_TEAM",true);
					formObject.setNGVisible("ITT_INTERNAL_DEV_TEAM",true);
					formObject.setNGVisible("VENDOR_NAME",false);
					formObject.setNGVisible("ITT_VENDOR_NAME",false);
					formObject.setNGLocked("ITT_INTERNAL_DEV_TEAM",false);
				}
				if(!formObject.getNGValue("ITT_VENDOR_NAME").equalsIgnoreCase(""))
				{
					formObject.setNGVisible("VENDOR_NAME",true);
					formObject.setNGVisible("ITT_VENDOR_NAME",true);
					formObject.setNGVisible("INTERNAL_DEVELOPMENT_TEAM",false);
					formObject.setNGVisible("ITT_INTERNAL_DEV_TEAM",false);
					formObject.setNGLocked("ITT_VENDOR_NAME",false);
				}
			}
			else if(sActivityName.equalsIgnoreCase("Project Manager"))
			{	
				if(!formObject.getNGValue("ITT_INTERNAL_DEV_TEAM").equalsIgnoreCase(""))
				{
					formObject.setNGVisible("INTERNAL_DEVELOPMENT_TEAM",true);
					formObject.setNGVisible("ITT_INTERNAL_DEV_TEAM",true);
					formObject.setNGVisible("VENDOR_NAME",false);
					formObject.setNGVisible("ITT_VENDOR_NAME",false);
					formObject.setNGLocked("ITT_INTERNAL_DEV_TEAM",false);

					//Enable to value of Cost, Effort and Resource.
					formObject.setNGLocked("ITT_PROJECT_COST",true);
					formObject.setNGLocked("ITT_EFFORT",true);
					formObject.setNGLocked("ITT_RESOURCES",true);
					formObject.setNGEnable("ITT_EXPECTED_UAT_SIGNOFF_DATE",true);
					formObject.setNGEnable("ITT_PLANNED_DELIVERY_DATE",true);

					//Setting Project Manager Option.
					formObject.NGClear("ITT_PROJECT_MANAGER_DECISION");				
					formObject.setNGListIndex("ITT_PROJECT_MANAGER_DECISION",0);
					formObject.NGAddItem("ITT_PROJECT_MANAGER_DECISION", "Approve");	
					formObject.NGAddItem("ITT_PROJECT_MANAGER_DECISION", "Discard");
					formObject.NGAddItem("ITT_PROJECT_MANAGER_DECISION", "Reject");

					//Set Default Focus
					formObject.NGFocus("ITT_PROJECT_COST");	
				}
				if(!formObject.getNGValue("ITT_VENDOR_NAME").equalsIgnoreCase(""))
				{
					formObject.setNGVisible("VENDOR_NAME",true);
					formObject.setNGVisible("ITT_VENDOR_NAME",true);
					formObject.setNGVisible("INTERNAL_DEVELOPMENT_TEAM",false);
					formObject.setNGVisible("ITT_INTERNAL_DEV_TEAM",false);
					formObject.setNGLocked("ITT_VENDOR_NAME",false);
					if((!formObject.getNGValue("ITT_PROJECT_COST").equalsIgnoreCase("")) && (!formObject.getNGValue("ITT_EFFORT").equalsIgnoreCase("")) && (!formObject.getNGValue("ITT_RESOURCES").equalsIgnoreCase("")))
					{
						formObject.NGClear("ITT_PROJECT_MANAGER_DECISION");				
						formObject.setNGListIndex("ITT_PROJECT_MANAGER_DECISION",0);
						formObject.NGAddItem("ITT_PROJECT_MANAGER_DECISION", "Approve");	
						formObject.NGAddItem("ITT_PROJECT_MANAGER_DECISION", "Discard");
						formObject.NGAddItem("ITT_PROJECT_MANAGER_DECISION", "Reject");
						formObject.NGAddItem("ITT_PROJECT_MANAGER_DECISION", "Send to Vendor Costing Team");
					}
				}
				if(formObject.getNGValue("ITT_PREV_WRK_STEP").equalsIgnoreCase("Vndr Costing Team"))
				{
					formObject.setNGEnable("ITT_EXPECTED_UAT_SIGNOFF_DATE",true);
				}
				else
				{
					formObject.setNGEnable("ITT_EXPECTED_UAT_SIGNOFF_DATE",false);
				}
				//Project Manager Decision on the basis of BUH.
				//if((formObject.getNGValue("ITT_PREV_WRK_STEP").equalsIgnoreCase("Business Head")) || (formObject.getNGValue("ITT_PREV_WRK_STEP").equalsIgnoreCase("Technology Head")))
				//{
					if((formObject.getNGValue("ITT_BUH_DECISION").equalsIgnoreCase("Approved")) || (formObject.getNGValue("ITT_TECHNICAL_HEAD_DECISION").equalsIgnoreCase("Approved")))
					{
						System.out.println("Removing Option.. Coming from BUH");
						//Bug No. 6 UAT Release
						formObject.NGClear("ITT_PROJECT_MANAGER_DECISION");				
						formObject.setNGListIndex("ITT_PROJECT_MANAGER_DECISION",0);
						formObject.NGAddItem("ITT_PROJECT_MANAGER_DECISION", "Discard");
						formObject.NGAddItem("ITT_PROJECT_MANAGER_DECISION", "Reject");

						formObject.NGAddItem("ITT_PROJECT_MANAGER_DECISION", "Send to Vendor Development Team");

						// Added by Dinesh Kumar on 25/05/2010, this option will be available to technical also.
						formObject.NGAddItem("ITT_PROJECT_MANAGER_DECISION", "Send to Infra Team");
					}
				//}
				//if(formObject.getNGValue("ITT_PREV_WRK_STEP").equalsIgnoreCase("Busines Testing Team"))
				//{
					if(formObject.getNGValue("ITT_BUSINESS_TESTING_TEAM_DECISION").equalsIgnoreCase("Approved"))	
					{
						//Bug No. 6 UAT Release
						System.out.println("Removing Option.. Coming from BTT");
						formObject.NGClear("ITT_PROJECT_MANAGER_DECISION");				
						formObject.setNGListIndex("ITT_PROJECT_MANAGER_DECISION",0);
						formObject.NGAddItem("ITT_PROJECT_MANAGER_DECISION", "Discard");
						formObject.NGAddItem("ITT_PROJECT_MANAGER_DECISION", "Reject");
						//formObject.NGAddItem("ITT_PROJECT_MANAGER_DECISION", "Send to VMT for Deployment");						
						formObject.NGAddItem("ITT_PROJECT_MANAGER_DECISION", "Send to Infra Team");						
					}
				//}				

				formObject.setNGValue("ITT_FLAG_BOUNCE_BACK_TO_BA_TEAM","N");
				formObject.setNGValue("ITT_FLAG_BOUNCE_BACK_TO_BU","N");
				formObject.setNGValue("ITT_FLAG_BOUNCE_BACK_TO_BA_TESTEAM","N");

				formObject.setNGLocked("ITT_CR_TYPE",false);
				//formObject.setNGLocked("ITT_CR_TYPE_TECHNICAL",false);

				//Setting Date Format
				formObject.setSpecificDateFormat("ITT_ESTIMATED_BRS_SIGNOFF_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_REQUESTED_GO_LIVE_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_PLANNED_DELIVERY_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_EXPECTED_UAT_SIGNOFF_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_PLANNED_UAT_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_DEPLOYMENT_DATE","dd/MM/yyyy");

				System.out.println("bUATDate	::"+bUATDate);

				System.out.println("ITT_PLANNED_UAT_DATE"+formObject.getNGValue("ITT_PLANNED_UAT_DATE"));
				formObject.setNGValue("Planned_Uat_Date",formObject.getNGValue("ITT_PLANNED_UAT_DATE"));
				/*
				if((!formObject.getNGValue("ITT_PREV_WRK_STEP").equalsIgnoreCase("BA Team")) && (!formObject.getNGValue("ITT_PLANNED_UAT_DATE").equalsIgnoreCase("")))
				{

				}
				*/

				/*
				if(!formObject.getNGValue("ITT_PREV_WRK_STEP").equalsIgnoreCase("BA Testing Team"))
				{
					//Set the form Height at run time
					formObject.setNGFormHeight(800);
					formObject.setNGFormWidth(600);		

					formObject.setNGControlHeight("FRAME_DECISION",formObject.getNGControlHeight("FRAME_DECISION")-135);
					formObject.setNGControlTop("FRAME_REASON_FOR_CHANGE",formObject.getNGControlTop("FRAME_REASON_FOR_CHANGE")-135);
					formObject.setNGControlTop("FRAME_REMARKS",formObject.getNGControlTop("FRAME_REMARKS")-135);
				}
				else if(formObject.getNGValue("ITT_PREV_WRK_STEP").equalsIgnoreCase("BA Testing Team"))
				{
					//Set the form Height at run time
					formObject.setNGFormHeight(950);
					formObject.setNGFormWidth(600);		
				*/
					formObject.setNGVisible("DEPLOYMENT_DATE",true);
					formObject.setNGVisible("ITT_DEPLOYMENT_DATE",true);
					formObject.setNGVisible("TEST_CASE_NO",true);
					formObject.setNGVisible("TESTCASENO",true);
					formObject.setNGVisible("BUG_SEVERITY",true);
					formObject.setNGVisible("BUGSEVERITY",true);
					formObject.setNGVisible("BUG_STATUS",true);
					formObject.setNGVisible("BUGSTATUS",true);
					formObject.setNGVisible("BUG_DESCRIPTION",true);
					formObject.setNGVisible("BUGDESCRIPTION",true);
					formObject.setNGVisible("ADD_IN_LIST",true);
					formObject.setNGVisible("MODIFY_IN_LIST",true);
					formObject.setNGVisible("DELETE_IN_LIST",true);
					formObject.setNGVisible("LIST_VIEW",true);
					//Make grid Field read-only at this WS.
					formObject.setNGLocked("TESTCASENO",false);
					formObject.setNGEnable("BUGSTATUS",false);
					formObject.setNGEnable("BUGSEVERITY",false);
					formObject.setNGLocked("BUGDESCRIPTION",false);
					formObject.setNGEnable("ADD_IN_LIST",false);
					formObject.setNGEnable("MODIFY_IN_LIST",false);
					formObject.setNGEnable("DELETE_IN_LIST",false);
				/*
				}
				*/
			}
			else if(sActivityName.equalsIgnoreCase("Vndr Costing Team"))
			{
				if(!formObject.getNGValue("ITT_INTERNAL_DEV_TEAM").equalsIgnoreCase(""))
				{
					formObject.setNGVisible("INTERNAL_DEVELOPMENT_TEAM",true);
					formObject.setNGVisible("ITT_INTERNAL_DEV_TEAM",true);
					formObject.setNGVisible("VENDOR_NAME",false);
					formObject.setNGVisible("ITT_VENDOR_NAME",false);
					formObject.setNGLocked("ITT_INTERNAL_DEV_TEAM",false);
				}
				if(!formObject.getNGValue("ITT_VENDOR_NAME").equalsIgnoreCase(""))
				{
					formObject.setNGVisible("VENDOR_NAME",true);
					formObject.setNGVisible("ITT_VENDOR_NAME",true);
					formObject.setNGVisible("INTERNAL_DEVELOPMENT_TEAM",false);
					formObject.setNGVisible("ITT_INTERNAL_DEV_TEAM",false);
					formObject.setNGLocked("ITT_VENDOR_NAME",false);
				}
				
				//Setting Date Format
				formObject.setSpecificDateFormat("ITT_ESTIMATED_BRS_SIGNOFF_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_REQUESTED_GO_LIVE_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_PLANNED_UAT_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_EXPECTED_UAT_SIGNOFF_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_PLANNED_DELIVERY_DATE","dd/MM/yyyy");

				//Setting Frame Hight
				formObject.setNGControlHeight("FRAME_DECISION",formObject.getNGControlHeight("FRAME_DECISION")-190);
				formObject.setNGControlTop("FRAME_REMARKS",formObject.getNGControlTop("FRAME_REMARKS")-(192+formObject.getNGControlHeight("FRAME_REASON_FOR_CHANGE")));				
			}
			else if(sActivityName.equalsIgnoreCase("Vndr Development"))
			{
				if(!formObject.getNGValue("ITT_INTERNAL_DEV_TEAM").equalsIgnoreCase(""))
				{
					formObject.setNGVisible("INTERNAL_DEVELOPMENT_TEAM",true);
					formObject.setNGVisible("ITT_INTERNAL_DEV_TEAM",true);
					formObject.setNGVisible("VENDOR_NAME",false);
					formObject.setNGVisible("ITT_VENDOR_NAME",false);
					formObject.setNGLocked("ITT_INTERNAL_DEV_TEAM",false);
				}
				if(!formObject.getNGValue("ITT_VENDOR_NAME").equalsIgnoreCase(""))
				{
					formObject.setNGVisible("VENDOR_NAME",true);
					formObject.setNGVisible("ITT_VENDOR_NAME",true);
					formObject.setNGVisible("INTERNAL_DEVELOPMENT_TEAM",false);
					formObject.setNGVisible("ITT_INTERNAL_DEV_TEAM",false);
					formObject.setNGLocked("ITT_VENDOR_NAME",false);
				}
				
				//Setting Date Format
				formObject.setSpecificDateFormat("ITT_ESTIMATED_BRS_SIGNOFF_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_REQUESTED_GO_LIVE_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_PLANNED_UAT_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_EXPECTED_UAT_SIGNOFF_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_PLANNED_DELIVERY_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_DEPLOYMENT_DATE","dd/MM/yyyy");

				formObject.setNGControlLeft("EXPACTED_UAT_SIGN_OFF_DATE",10);
				formObject.setNGControlTop("EXPACTED_UAT_SIGN_OFF_DATE",100);
				formObject.setNGControlLeft("ITT_EXPECTED_UAT_SIGNOFF_DATE",142);
				formObject.setNGControlTop("ITT_EXPECTED_UAT_SIGNOFF_DATE",100);

				formObject.setNGControlLeft("DEPLOYMENT_DATE",10);
				formObject.setNGControlTop("DEPLOYMENT_DATE",130);
				formObject.setNGControlLeft("ITT_DEPLOYMENT_DATE",142);
				formObject.setNGControlTop("ITT_DEPLOYMENT_DATE",130);

				//Set alignment of grid data (Change Request)
				//Test Case No.
				formObject.setNGControlLeft("TEST_CASE_NO",297);
				formObject.setNGControlTop("TEST_CASE_NO",130);
				formObject.setNGControlLeft("TESTCASENO",446);
				formObject.setNGControlTop("TESTCASENO",130);
				formObject.setNGLocked("TESTCASENO",false);
				//Bug Status
				formObject.setNGControlLeft("BUG_STATUS",10);
				formObject.setNGControlTop("BUG_STATUS",160);
				formObject.setNGControlLeft("BUGSTATUS",142);
				formObject.setNGControlTop("BUGSTATUS",160);
				formObject.setNGEnable("BUGSTATUS",false);
				//Bug Saverity
				formObject.setNGControlLeft("BUG_SEVERITY",297);
				formObject.setNGControlTop("BUG_SEVERITY",160);
				formObject.setNGControlLeft("BUGSEVERITY",446);
				formObject.setNGControlTop("BUGSEVERITY",160);
				formObject.setNGEnable("BUGSEVERITY",false);
				//Bug Description
				formObject.setNGControlLeft("BUG_DESCRIPTION",10);
				formObject.setNGControlTop("BUG_DESCRIPTION",190);
				formObject.setNGControlLeft("BUGDESCRIPTION",142);
				formObject.setNGControlTop("BUGDESCRIPTION",190);
				formObject.setNGLocked("BUGDESCRIPTION",false);
				//Add
				formObject.setNGControlTop("ADD_IN_LIST",235);
				formObject.setNGControlLeft("ADD_IN_LIST",142);
				formObject.setNGEnable("ADD_IN_LIST",false);
				//Modify
				formObject.setNGControlTop("MODIFY_IN_LIST",235);
				formObject.setNGControlLeft("MODIFY_IN_LIST",252);
				formObject.setNGEnable("MODIFY_IN_LIST",false);
				//Delete
				formObject.setNGControlTop("DELETE_IN_LIST",235);
				formObject.setNGControlLeft("DELETE_IN_LIST",362);
				formObject.setNGEnable("DELETE_IN_LIST",false);

				//Setting Frame Hight
				//formObject.setNGControlHeight("FRAME_DECISION",formObject.getNGControlHeight("FRAME_DECISION")-170);
				//formObject.setNGControlTop("FRAME_REMARKS",formObject.getNGControlTop("FRAME_REMARKS")-170);
				
				formObject.setNGControlHeight("FRAME_DECISION",formObject.getNGControlHeight("FRAME_DECISION")-65);
				formObject.setNGControlTop("FRAME_REMARKS",formObject.getNGControlTop("FRAME_REMARKS")-65);
				formObject.setNGControlTop("LIST_VIEW",formObject.getNGControlTop("LIST_VIEW")-65);
			}
			else if(sActivityName.equalsIgnoreCase("Business Head"))
			{
				System.out.println("ITT_INTERNAL_DEV_TEAM	:"+formObject.getNGValue("ITT_INTERNAL_DEV_TEAM"));
				System.out.println("ITT_VENDOR_NAME	:"+formObject.getNGValue("ITT_VENDOR_NAME"));
				if(!formObject.getNGValue("ITT_INTERNAL_DEV_TEAM").equalsIgnoreCase(""))
				{
					System.out.println("Control Inside Internal");
					formObject.setNGVisible("INTERNAL_DEVELOPMENT_TEAM",true);
					formObject.setNGVisible("ITT_INTERNAL_DEV_TEAM",true);
					formObject.setNGVisible("VENDOR_NAME",false);
					formObject.setNGVisible("ITT_VENDOR_NAME",false);
					formObject.setNGLocked("ITT_INTERNAL_DEV_TEAM",false);
				}
				if(!formObject.getNGValue("ITT_VENDOR_NAME").equalsIgnoreCase(""))
				{
					System.out.println("Control Inside External");
					formObject.setNGVisible("VENDOR_NAME",true);
					formObject.setNGVisible("ITT_VENDOR_NAME",true);
					formObject.setNGVisible("INTERNAL_DEVELOPMENT_TEAM",false);
					formObject.setNGVisible("ITT_INTERNAL_DEV_TEAM",false);
					formObject.setNGLocked("ITT_VENDOR_NAME",false);
				}
				
				//Setting Date Format
				formObject.setSpecificDateFormat("ITT_ESTIMATED_BRS_SIGNOFF_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_REQUESTED_GO_LIVE_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_PLANNED_DELIVERY_DATE","dd/MM/yyyy");
			}
			else if(sActivityName.equalsIgnoreCase("Technology Head"))
			{
				if(!formObject.getNGValue("ITT_INTERNAL_DEV_TEAM").equalsIgnoreCase(""))
				{
					formObject.setNGVisible("INTERNAL_DEVELOPMENT_TEAM",true);
					formObject.setNGVisible("ITT_INTERNAL_DEV_TEAM",true);
					formObject.setNGVisible("VENDOR_NAME",false);
					formObject.setNGVisible("ITT_VENDOR_NAME",false);
					formObject.setNGLocked("ITT_INTERNAL_DEV_TEAM",false);
				}
				if(!formObject.getNGValue("ITT_VENDOR_NAME").equalsIgnoreCase(""))
				{
					formObject.setNGVisible("VENDOR_NAME",true);
					formObject.setNGVisible("ITT_VENDOR_NAME",true);
					formObject.setNGVisible("INTERNAL_DEVELOPMENT_TEAM",false);
					formObject.setNGVisible("ITT_INTERNAL_DEV_TEAM",false);
					formObject.setNGLocked("ITT_VENDOR_NAME",false);
				}
				
				//Setting Date Format
				formObject.setSpecificDateFormat("ITT_ESTIMATED_BRS_SIGNOFF_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_REQUESTED_GO_LIVE_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_PLANNED_DELIVERY_DATE","dd/MM/yyyy");
			}
			else if((sActivityName.equalsIgnoreCase("BA Testing Team")) || (sActivityName.equalsIgnoreCase("Issue Resolution")) || (sActivityName.equalsIgnoreCase("Busines Testing Team")))
			{
				if(!formObject.getNGValue("ITT_INTERNAL_DEV_TEAM").equalsIgnoreCase(""))
				{
					formObject.setNGVisible("INTERNAL_DEVELOPMENT_TEAM",true);
					formObject.setNGVisible("ITT_INTERNAL_DEV_TEAM",true);
					formObject.setNGVisible("VENDOR_NAME",false);
					formObject.setNGVisible("ITT_VENDOR_NAME",false);
					formObject.setNGLocked("ITT_INTERNAL_DEV_TEAM",false);
				}
				if(!formObject.getNGValue("ITT_VENDOR_NAME").equalsIgnoreCase(""))
				{
					formObject.setNGVisible("VENDOR_NAME",true);
					formObject.setNGVisible("ITT_VENDOR_NAME",true);
					formObject.setNGVisible("INTERNAL_DEVELOPMENT_TEAM",false);
					formObject.setNGVisible("ITT_INTERNAL_DEV_TEAM",false);
					formObject.setNGLocked("ITT_VENDOR_NAME",false);
				}
				
				//Setting Date Format
				formObject.setSpecificDateFormat("ITT_ESTIMATED_BRS_SIGNOFF_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_REQUESTED_GO_LIVE_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_PLANNED_UAT_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_EXPECTED_UAT_SIGNOFF_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_PLANNED_DELIVERY_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_DEPLOYMENT_DATE","dd/MM/yyyy");

				if(sActivityName.equalsIgnoreCase("Busines Testing Team"))
				{
					formObject.NGFocus("ITT_BUSINESS_TESTING_TEAM_DECISION");		// Default Focus
					//Make Read Only Grid data.
					formObject.setNGLocked("TESTCASENO",false);
					formObject.setNGEnable("BUGSTATUS",false);
					formObject.setNGEnable("BUGSEVERITY",false);
					formObject.setNGLocked("BUGDESCRIPTION",false);
					formObject.setNGEnable("ADD_IN_LIST",false);
					formObject.setNGEnable("MODIFY_IN_LIST",false);
					formObject.setNGEnable("DELETE_IN_LIST",false);
					//Set alignment for Bug Status.
					formObject.setNGControlLeft("BUG_STATUS",10);
					formObject.setNGControlTop("BUG_STATUS",190);
					formObject.setNGControlLeft("BUGSTATUS",142);
					formObject.setNGControlTop("BUGSTATUS",190);
				}
				if(sActivityName.equalsIgnoreCase("Issue Resolution"))
				{
					formObject.setNGControlLeft("DEPLOYMENT_DATE",10);
					formObject.setNGControlTop("DEPLOYMENT_DATE",130);
					formObject.setNGControlLeft("ITT_DEPLOYMENT_DATE",142);
					formObject.setNGControlTop("ITT_DEPLOYMENT_DATE",130);
					formObject.setNGEnable("ITT_DEPLOYMENT_DATE",false);
					
					//Set alignment of grid data (Change Request)
					//Bug Status
					formObject.setNGLocked("TESTCASENO",false);

					formObject.setNGControlLeft("BUG_STATUS",10);
					formObject.setNGControlTop("BUG_STATUS",160);
					formObject.setNGControlLeft("BUGSTATUS",142);
					formObject.setNGControlTop("BUGSTATUS",160);
					formObject.setNGEnable("BUGSTATUS",false);
					//Bug Saverity
					formObject.setNGControlLeft("BUG_SEVERITY",10);
					formObject.setNGControlTop("BUG_SEVERITY",190);
					formObject.setNGControlLeft("BUGSEVERITY",142);
					formObject.setNGControlTop("BUGSEVERITY",190);
					formObject.setNGEnable("BUGSEVERITY",false);
					//Bug Description
					formObject.setNGControlLeft("BUG_DESCRIPTION",10);
					formObject.setNGControlTop("BUG_DESCRIPTION",220);
					formObject.setNGControlLeft("BUGDESCRIPTION",142);
					formObject.setNGControlTop("BUGDESCRIPTION",220);
					formObject.setNGLocked("BUGDESCRIPTION",false);
					//Add
					formObject.setNGControlTop("ADD_IN_LIST",265);
					formObject.setNGControlLeft("ADD_IN_LIST",142);
					formObject.setNGEnable("ADD_IN_LIST",false);
					//Modify
					formObject.setNGControlTop("MODIFY_IN_LIST",265);
					formObject.setNGControlLeft("MODIFY_IN_LIST",252);
					formObject.setNGEnable("MODIFY_IN_LIST",true);
					//Delete
					formObject.setNGControlTop("DELETE_IN_LIST",265);
					formObject.setNGControlLeft("DELETE_IN_LIST",362);
					formObject.setNGEnable("DELETE_IN_LIST",false);

					formObject.setNGControlHeight("FRAME_DECISION",formObject.getNGControlHeight("FRAME_DECISION")-30);
					formObject.setNGControlTop("FRAME_REMARKS",formObject.getNGControlTop("FRAME_REMARKS")-30);
					formObject.setNGControlTop("LIST_VIEW",formObject.getNGControlTop("LIST_VIEW")-30);
				}
				if(sActivityName.equalsIgnoreCase("Busines Testing Team"))
				{
					formObject.setNGControlLeft("DEPLOYMENT_DATE",10);
					formObject.setNGControlTop("DEPLOYMENT_DATE",160);
					formObject.setNGControlLeft("ITT_DEPLOYMENT_DATE",142);
					formObject.setNGControlTop("ITT_DEPLOYMENT_DATE",160);
					formObject.setNGEnable("ITT_DEPLOYMENT_DATE",false);
				}
				if(sActivityName.equalsIgnoreCase("BA Testing Team"))
				{
					//Set the grid data alignment.
					formObject.setNGControlLeft("TEST_CASE_NO",10);
					formObject.setNGControlTop("TEST_CASE_NO",190);
					//formObject.setNGControlLeft("TESTCASENO",142);
					//formObject.setNGControlTop("TESTCASENO",190);
					formObject.setNGControlLeft("TESTCASENO",142);
					formObject.setNGControlTop("TESTCASENO",190);

					formObject.setNGControlLeft("BUG_STATUS",297);
					formObject.setNGControlTop("BUG_STATUS",190);
					//formObject.setNGControlLeft("BUGSTATUS",446);
					//formObject.setNGControlTop("BUGSTATUS",190);
					formObject.setNGControlLeft("BUGSTATUS",446);
					formObject.setNGControlTop("BUGSTATUS",190);

					if(formObject.getNGValue("ITT_CR_TYPE").equalsIgnoreCase("Business"))
					{
						formObject.NGClear("ITT_BA_TESTING_TEAM_DECISION");	
						formObject.setNGListIndex("ITT_BA_TESTING_TEAM_DECISION",0);								
						formObject.NGAddItem("ITT_BA_TESTING_TEAM_DECISION", "Send to Issue Resolution Team");
						formObject.NGAddItem("ITT_BA_TESTING_TEAM_DECISION", "Send to BU Testing Team");			
					}
					else if(formObject.getNGValue("ITT_CR_TYPE").equalsIgnoreCase("Technical"))
					{
						formObject.NGClear("ITT_BA_TESTING_TEAM_DECISION");	
						formObject.setNGListIndex("ITT_BA_TESTING_TEAM_DECISION",0);								
						formObject.NGAddItem("ITT_BA_TESTING_TEAM_DECISION", "Send to Project Manager");	
						formObject.NGAddItem("ITT_BA_TESTING_TEAM_DECISION", "Send to Issue Resolution Team");
					}
						formObject.setNGControlLeft("DEPLOYMENT_DATE",10);
						formObject.setNGControlTop("DEPLOYMENT_DATE",160);
						formObject.setNGControlLeft("ITT_DEPLOYMENT_DATE",142);
						formObject.setNGControlTop("ITT_DEPLOYMENT_DATE",160);
						formObject.setNGEnable("ITT_DEPLOYMENT_DATE",false);

						// Added by Dinesh Kumar on 08/06/2010 to set Bug Type combo to -- Select --
						formObject.setNGListIndex("ITT_BUG_TYPE",0);
				}
				//Setting Frame Hight
				//formObject.setNGControlHeight("FRAME_DECISION",formObject.getNGControlHeight("FRAME_DECISION")-30);
				//formObject.setNGControlTop("FRAME_REMARKS",formObject.getNGControlTop("FRAME_REMARKS")-30);
			}
			else if(sActivityName.equalsIgnoreCase("Infra Team"))
			{
				if(formObject.getNGValue("ITT_PREV_WRK_STEP").trim().equalsIgnoreCase("Project Manager"))
				{
					formObject.NGAddItem("ITT_INFRA_TEAM_DECISION", "Deployed on Production");
				}
				if(!formObject.getNGValue("ITT_INTERNAL_DEV_TEAM").equalsIgnoreCase(""))
				{
					formObject.setNGVisible("INTERNAL_DEVELOPMENT_TEAM",true);
					formObject.setNGVisible("ITT_INTERNAL_DEV_TEAM",true);
					formObject.setNGVisible("VENDOR_NAME",false);
					formObject.setNGVisible("ITT_VENDOR_NAME",false);
					formObject.setNGLocked("ITT_INTERNAL_DEV_TEAM",false);
				}
				if(!formObject.getNGValue("ITT_VENDOR_NAME").equalsIgnoreCase(""))
				{
					formObject.setNGVisible("VENDOR_NAME",true);
					formObject.setNGVisible("ITT_VENDOR_NAME",true);
					formObject.setNGVisible("INTERNAL_DEVELOPMENT_TEAM",false);
					formObject.setNGVisible("ITT_INTERNAL_DEV_TEAM",false);
					formObject.setNGLocked("ITT_VENDOR_NAME",false);
				}
				
				//Setting Date Format
				formObject.setSpecificDateFormat("ITT_ESTIMATED_BRS_SIGNOFF_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_REQUESTED_GO_LIVE_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_PLANNED_UAT_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_EXPECTED_UAT_SIGNOFF_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_PLANNED_DELIVERY_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_DEPLOYMENT_DATE","dd/MM/yyyy");

				formObject.setNGControlLeft("INFRA_DECISION",10);
				formObject.setNGControlTop("INFRA_DECISION",100);

				formObject.setNGControlLeft("ITT_INFRA_TEAM_DECISION",142);
				formObject.setNGControlTop("ITT_INFRA_TEAM_DECISION",100);

				formObject.setNGControlLeft("DEPLOYMENT_DATE",10);
				formObject.setNGControlTop("DEPLOYMENT_DATE",130);

				formObject.setNGControlLeft("ITT_DEPLOYMENT_DATE",142);
				formObject.setNGControlTop("ITT_DEPLOYMENT_DATE",130);

				//Set alignment of grid data (Change Request)
				//Test Case No.
				formObject.setNGControlLeft("TEST_CASE_NO",10);
				formObject.setNGControlTop("TEST_CASE_NO",160);
				formObject.setNGControlLeft("TESTCASENO",142);
				formObject.setNGControlTop("TESTCASENO",160);
				formObject.setNGLocked("TESTCASENO",false);
				//Bug Status
				formObject.setNGControlLeft("BUG_STATUS",297);
				formObject.setNGControlTop("BUG_STATUS",160);
				formObject.setNGControlLeft("BUGSTATUS",446);
				formObject.setNGControlTop("BUGSTATUS",160);
				formObject.setNGEnable("BUGSTATUS",false);
				//Bug Saverity
				formObject.setNGControlLeft("BUG_SEVERITY",10);
				formObject.setNGControlTop("BUG_SEVERITY",190);
				formObject.setNGControlLeft("BUGSEVERITY",142);
				formObject.setNGControlTop("BUGSEVERITY",190);
				formObject.setNGEnable("BUGSEVERITY",false);
				//Bug Description
				formObject.setNGControlLeft("BUG_DESCRIPTION",10);
				formObject.setNGControlTop("BUG_DESCRIPTION",220);
				formObject.setNGControlLeft("BUGDESCRIPTION",142);
				formObject.setNGControlTop("BUGDESCRIPTION",220);
				formObject.setNGLocked("BUGDESCRIPTION",false);
				//Add
				formObject.setNGControlTop("ADD_IN_LIST",265);
				formObject.setNGControlLeft("ADD_IN_LIST",142);
				formObject.setNGEnable("ADD_IN_LIST",false);
				//Modify
				formObject.setNGControlTop("MODIFY_IN_LIST",265);
				formObject.setNGControlLeft("MODIFY_IN_LIST",252);
				formObject.setNGEnable("MODIFY_IN_LIST",false);
				//Delete
				formObject.setNGControlTop("DELETE_IN_LIST",265);
				formObject.setNGControlLeft("DELETE_IN_LIST",362);
				formObject.setNGEnable("DELETE_IN_LIST",false);

				//Setting Frame Hight
				formObject.setNGControlHeight("FRAME_DECISION",formObject.getNGControlHeight("FRAME_DECISION")-33);
				formObject.setNGControlTop("FRAME_REMARKS",formObject.getNGControlTop("FRAME_REMARKS")-33);
				formObject.setNGControlTop("LIST_VIEW",formObject.getNGControlTop("LIST_VIEW")-33);
			}
			else if(sActivityName.equalsIgnoreCase("PAT"))
			{
				if(!formObject.getNGValue("ITT_INTERNAL_DEV_TEAM").equalsIgnoreCase(""))
				{
					formObject.setNGVisible("INTERNAL_DEVELOPMENT_TEAM",true);
					formObject.setNGVisible("ITT_INTERNAL_DEV_TEAM",true);
					formObject.setNGVisible("VENDOR_NAME",false);
					formObject.setNGVisible("ITT_VENDOR_NAME",false);
					formObject.setNGLocked("ITT_INTERNAL_DEV_TEAM",false);
				}
				if(!formObject.getNGValue("ITT_VENDOR_NAME").equalsIgnoreCase(""))
				{
					formObject.setNGVisible("VENDOR_NAME",true);
					formObject.setNGVisible("ITT_VENDOR_NAME",true);
					formObject.setNGVisible("INTERNAL_DEVELOPMENT_TEAM",false);
					formObject.setNGVisible("ITT_INTERNAL_DEV_TEAM",false);
					formObject.setNGLocked("ITT_VENDOR_NAME",false);
				}
				//Make Read Only Grid data.
				formObject.setNGLocked("TESTCASENO",false);
				formObject.setNGEnable("BUGSTATUS",false);
				formObject.setNGEnable("BUGSEVERITY",false);
				formObject.setNGLocked("BUGDESCRIPTION",false);
				formObject.setNGEnable("ADD_IN_LIST",false);
				formObject.setNGEnable("MODIFY_IN_LIST",false);
				formObject.setNGEnable("DELETE_IN_LIST",false);				
				//Setting Date Format
				formObject.setSpecificDateFormat("ITT_ESTIMATED_BRS_SIGNOFF_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_REQUESTED_GO_LIVE_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_PLANNED_UAT_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_EXPECTED_UAT_SIGNOFF_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_PLANNED_DELIVERY_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_DEPLOYMENT_DATE","dd/MM/yyyy");

				formObject.setNGEnable("ITT_DEPLOYMENT_DATE",false);

				//Setting Frame Hight
				//formObject.setNGControlHeight("FRAME_DECISION",formObject.getNGControlHeight("FRAME_DECISION")-60);
				//formObject.setNGControlTop("FRAME_REMARKS",formObject.getNGControlTop("FRAME_REMARKS")-60);
			}
			else if((sActivityName.equalsIgnoreCase("Discard1")) || (sActivityName.equalsIgnoreCase("Work Exit1")))
			{
				if(!formObject.getNGValue("ITT_INTERNAL_DEV_TEAM").equalsIgnoreCase(""))
				{
					formObject.setNGVisible("INTERNAL_DEVELOPMENT_TEAM",true);
					formObject.setNGVisible("ITT_INTERNAL_DEV_TEAM",true);
					formObject.setNGVisible("VENDOR_NAME",false);
					formObject.setNGVisible("ITT_VENDOR_NAME",false);
					formObject.setNGLocked("ITT_INTERNAL_DEV_TEAM",false);
				}
				if(!formObject.getNGValue("ITT_VENDOR_NAME").equalsIgnoreCase(""))
				{
					formObject.setNGVisible("VENDOR_NAME",true);
					formObject.setNGVisible("ITT_VENDOR_NAME",true);
					formObject.setNGVisible("INTERNAL_DEVELOPMENT_TEAM",false);
					formObject.setNGVisible("ITT_INTERNAL_DEV_TEAM",false);
					formObject.setNGLocked("ITT_VENDOR_NAME",false);
				}
				//System.out.println("ITT_DEPLOYMENT_DATE	->"+formObject.getNGValue("ITT_DEPLOYMENT_DATE"));
				//System.out.println("ITT_PLANNED_UAT_DATE	->"+formObject.getNGValue("ITT_PLANNED_UAT_DATE"));
				//System.out.println("ITT_PLANNED_DELIVERY_DATE	->"+formObject.getNGValue("ITT_PLANNED_DELIVERY_DATE"));
				//System.out.println("ITT_EXPECTED_UAT_SIGNOFF_DATE	->"+formObject.getNGValue("ITT_EXPECTED_UAT_SIGNOFF_DATE"));
				
				//Setting Date Format
				formObject.setSpecificDateFormat("ITT_ESTIMATED_BRS_SIGNOFF_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_REQUESTED_GO_LIVE_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_PLANNED_UAT_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_EXPECTED_UAT_SIGNOFF_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_PLANNED_DELIVERY_DATE","dd/MM/yyyy");
				formObject.setSpecificDateFormat("ITT_DEPLOYMENT_DATE","dd/MM/yyyy");

				//Hide Bug Type.
				formObject.setNGVisible("BUG_TYPE",false);
				formObject.setNGVisible("ITT_BUG_TYPE",false);
				formObject.setNGLocked("FRAME_REMARKS",true);
				//Disable the Remark Field.
				formObject.setNGVisible("ITT_REMARK",false);

				//Enable History Button
				formObject.setNGVisible("Btn_History",true);
				formObject.setNGVisible("Label50",false);
				formObject.setNGLocked("Btn_History",true);
				
				//formObject.setNGControlHeight("FRAME_REMARKS",formObject.getNGControlHeight("FRAME_REMARKS")-50);
			}
			paintFrame();
		}
		catch(NullPointerException nex)
		{
			nex.printStackTrace();
			bError=true;
			return "0";
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			bError=true;
			return "0";
		}

        return null;
    }

	public String chkUserGroup()
	{
		String sGroupName="";
		String sGroupNamesAry[];

		String sQuery="SELECT pg.groupname FROM PDBGROUP pg, PDBUSER pu, PDBGROUPMEMBER pgm WHERE pg.groupindex=pgm.groupindex AND pu.userindex=pgm.userindex AND pu.userindex="+formObject.getUserId()+"";
		//System.out.println(sQuery);
		sInputxml = icicicmn.WMTestSelect(sQuery, "1");
		sOutputxml = icicicmn.executeXmlGeneric(sInputxml);

		if(!sOutputxml.equals(""))
		{
			//System.out.println("\n\nITT:chkUserGroup: sInputxml "+sInputxml);
			//System.out.println("\n\nITT:chkUserGroup: sOutputxml "+sOutputxml);
			
			xmlParser.setInputXML(sOutputxml);
			sMainCode = xmlParser.getValueOf("MainCode");

			if(sMainCode.equals("0"))
			{
				if(!sOutputxml.substring(sOutputxml.indexOf("<Results>")+9,sOutputxml.indexOf("</Results>")).equals(""))
				{
					WFXmlResponse xmlResponse = new WFXmlResponse(sOutputxml);
					WFXmlList RecordList;

					for (RecordList =  xmlResponse.createList("Results", "Result");RecordList.hasMoreElements();RecordList.skip())
					{
						sResult=sResult+RecordList.getVal("Result");
						
						sResult=sResult+'~';									
					}
						
					sResult=sResult.substring(0,sResult.length()-1);

					//System.out.println("ITT:chkUserGroup: sResult "+sResult);

					sRowsAry=split(sResult,'~');

					if(sRowsAry.length>0)
					{
						for(int i=0;i<sRowsAry.length;i++)
						{									
							sColAry=split(sRowsAry[i],'|');
							sGroupName=sGroupName+sColAry[1]+",";								
						}

						sGroupName=sGroupName.substring(0,sGroupName.length()-1);												

						//System.out.println("\nITT:chkUserGroup: sGroupName "+sGroupName);

						sGroupNamesAry=split(sGroupName,',');

						for(int j=0;j<sGroupNamesAry.length;j++)
						{
							if(sGroupNamesAry[j].equalsIgnoreCase("Business"))
								isBusinessUser=true;
							else if(sGroupNamesAry[j].equalsIgnoreCase("Technical"))
								isTechnicalUser=true;
						}								
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null,"User Name does not exist in Master for the user logged in.");
					bBrncInfoErr=true;
					return "0";					
				}
			}
			else if(sMainCode.equals("11"))
			{
					JOptionPane.showMessageDialog(null,"Session Expired.");
					bError=true;
					return "0";								
			}
			else
			{
					JOptionPane.showMessageDialog(null,"Error at server end.");
					bError=true;
					return "0";						
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null,"Session Expired.");
			bError=true;
			return "0";				
		}

		return "1";
	}

	//This Code is use to direct call from DB to get value of BusinessGroup.
	public String fillBusinessGroup()
	{
		String sGroupName="";
		String sGroupNamesAry[];

		//System.out.println("Control inside fillBusinessGroup");

		String sQuery="SELECT DISTINCT BUSINESSGROUP FROM ng_business_group_master order by BUSINESSGROUP";
		sInputxml = icicicmn.WMTestSelect(sQuery, "1");
		sOutputxml = icicicmn.executeXmlGeneric(sInputxml);

		if(!sOutputxml.equals(""))
		{
			//System.out.println("\n\nITT:FillBusinessGroup: sInputxml "+sInputxml);
			//System.out.println("\nITT:FillBusinessGroup: sOutputxml "+sOutputxml);
			
			xmlParser.setInputXML(sOutputxml);
			sMainCode = xmlParser.getValueOf("MainCode");

			if(sMainCode.equals("0"))
			{
				if(!sOutputxml.substring(sOutputxml.indexOf("<Results>")+9,sOutputxml.indexOf("</Results>")).equals(""))
				{
					WFXmlResponse xmlResponse = new WFXmlResponse(sOutputxml);
					WFXmlList RecordList;

					sResult="";

					for (RecordList =  xmlResponse.createList("Results", "Result");RecordList.hasMoreElements();RecordList.skip())
					{
						sResult=sResult+RecordList.getVal("Result");
						
						sResult=sResult+'~';									
					}
						
					sResult=sResult.substring(0,sResult.length()-1);

					//System.out.println("ITT: Result Data	->"+sResult);

					sRowsAry=split(sResult,'~');

					if(sRowsAry.length>0)
					{
						for(int i=0;i<sRowsAry.length;i++)
						{									
							sColAry=split(sRowsAry[i],'|');
							sGroupName=sGroupName+sColAry[1]+",";								
						}

						sGroupName=sGroupName.substring(0,sGroupName.length()-1);												

						//System.out.println("\nITT:GroupName		->"+sGroupName);

						sGroupNamesAry=split(sGroupName,',');

						for(int j=0;j<sGroupNamesAry.length;j++)
						{
							//System.out.println("Case"+j+"	->"+sGroupNamesAry[j]+"\n");
							formObject.NGAddItem("ITT_BUSINESS_GROUP", sGroupNamesAry[j]);	
						}
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Unable to fetch data from Master.");
					bBrncInfoErr=true;
					return "0";					
				}
			}
			else if(sMainCode.equals("11"))
			{
					JOptionPane.showMessageDialog(null,"Session Expired.");
					bError=true;
					return "0";								
			}
			else
			{
					JOptionPane.showMessageDialog(null,"Error at server end.");
					bError=true;
					return "0";						
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null,"Session Expired.");
			bError=true;
			return "0";				
		}
		return "1";
	}
	//This Code is use to direct call from DB to get value of BusinessGroup.
	public String fillVendorNameInternalDevTeam(String sActivityName)
	{
		String sColData="";
		String sColDataAry[];
		String colName="";
		String fieldName="";

		if(sActivityName.equalsIgnoreCase("Internal Feasibility"))
		{
			colName="INTERNAL_DEV_TEAM";
			fieldName="ITT_INTERNAL_DEV_TEAM";
		}
		else if(sActivityName.equalsIgnoreCase("External Feasibility"))
		{
			colName="VENDOR_NAME";
			fieldName="ITT_VENDOR_NAME";
		}

		String sQuery="SELECT DISTINCT "+ colName +" FROM NG_VNDR_INTRNL_DEV_TEAM_MASTER order by "+ colName;
		//System.out.println("sQuery		->"+sQuery);
		sInputxml = icicicmn.WMTestSelect(sQuery, "1");
		sOutputxml = icicicmn.executeXmlGeneric(sInputxml);
		
		if(!sOutputxml.equals(""))
		{
			//System.out.println("\n\nITT:FillBusinessGroup: sInputxml "+sInputxml);
			//System.out.println("\nITT:FillBusinessGroup: sOutputxml "+sOutputxml);
			
			xmlParser.setInputXML(sOutputxml);
			sMainCode = xmlParser.getValueOf("MainCode");

			if(sMainCode.equals("0"))
			{
				if(!sOutputxml.substring(sOutputxml.indexOf("<Results>")+9,sOutputxml.indexOf("</Results>")).equals(""))
				{
					WFXmlResponse xmlResponse = new WFXmlResponse(sOutputxml);
					WFXmlList RecordList;

					sResult="";

					for (RecordList =  xmlResponse.createList("Results", "Result");RecordList.hasMoreElements();RecordList.skip())
					{
						sResult=sResult+RecordList.getVal("Result");
						
						sResult=sResult+'~';									
					}
						
					sResult=sResult.substring(0,sResult.length()-1);

					//System.out.println("ITT: Result Data	->"+sResult);

					sRowsAry=split(sResult,'~');

					if(sRowsAry.length>0)
					{
						for(int i=0;i<sRowsAry.length;i++)
						{									
							sColAry=split(sRowsAry[i],'|');
							sColData=sColData+sColAry[1]+",";								
						}

						sColData=sColData.substring(0,sColData.length()-1);												

						//System.out.println("\nITT:sColData		->"+sColData);

						sColDataAry=split(sColData,',');

						for(int j=0;j<sColDataAry.length;j++)
						{
							//System.out.println("Case"+j+"	->"+sColDataAry[j]+"\n");
							formObject.NGAddItem(fieldName,sColDataAry[j]);	
						}
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Unable to fetch data from Master.");
					bBrncInfoErr=true;
					return "0";					
				}
			}
			else if(sMainCode.equals("11"))
			{
					JOptionPane.showMessageDialog(null,"Session Expired.");
					bError=true;
					return "0";								
			}
			else
			{
					JOptionPane.showMessageDialog(null,"Error at server end.");
					bError=true;
					return "0";						
			}
		}

		else
		{
			JOptionPane.showMessageDialog(null,"Session Expired.");
			bError=true;
			return "0";				
		}
		return "1";
	}
	//This Code is use fill value in Business User head and Application.
	public String fillBusinessUserHeadandApplication(String bHvalue)
	{
		String sGroupName="";
		String sGroupNamesAry[];
		String sApplicationAry[];
		String sBusinessuhAry[];
		String strApp="";
		String strBuh="";

		String sQuery="SELECT APPLICATION,BUSINESSUSERHEAD FROM ng_business_group_master where BUSINESSGROUP='"+bHvalue+"'";
		sInputxml = icicicmn.WMTestSelect(sQuery, "2");
		sOutputxml = icicicmn.executeXmlGeneric(sInputxml);

		if(!sOutputxml.equals(""))
		{
			//System.out.println("fillBusinessUserHeadandApplication "+sInputxml);
			//System.out.println("fillBusinessUserHeadandApplication "+sOutputxml);
			
			xmlParser.setInputXML(sOutputxml);
			sMainCode = xmlParser.getValueOf("MainCode");
			if(sMainCode.equals("0"))
			{
				if(!sOutputxml.substring(sOutputxml.indexOf("<Results>")+9,sOutputxml.indexOf("</Results>")).equals(""))
				{
					WFXmlResponse xmlResponse = new WFXmlResponse(sOutputxml);
					WFXmlList RecordList;
					sResult="";
					for (RecordList =  xmlResponse.createList("Results", "Result");RecordList.hasMoreElements();RecordList.skip())
					{
						sResult=sResult+RecordList.getVal("Result");
						sResult=sResult+'~';
					}
					//System.out.println("Result	fillBusinessUserHeadandApplication->"+sResult);

					sResult=sResult.substring(0,sResult.length()-1);
					sRowsAry=split(sResult,'~');
					if(sRowsAry.length>0)
					{
						for(int i=0;i<sRowsAry.length;i++)
						{
							sColAry=split(sRowsAry[i],'|');
							strApp=strApp+sColAry[1]+",";
							strBuh=strBuh+sColAry[2]+",";
						}
						strApp=strApp.substring(0,strApp.length()-1);
						strBuh=strBuh.substring(0,strBuh.length()-1);
						
						sApplicationAry=split(strApp,',');
						sBusinessuhAry=split(strBuh,',');

						formObject.NGClear("ITT_APPLICATION");	
						formObject.setNGListIndex("ITT_APPLICATION",0);		

						List<String> list = Arrays.asList(sApplicationAry); 
						Set<String> mySet = new HashSet<String>(list); 
						String[] inputArray = new String[mySet.size()]; 
						mySet.toArray(inputArray); 

						for(int j=0;j<inputArray.length;j++)
						{
							//System.out.println("inputArray[j] "+inputArray[j]);
							formObject.NGAddItem("ITT_APPLICATION",inputArray[j]);
						}
						formObject.NGClear("ITT_BUSINESS_USER_HEAD");		
						formObject.setNGListIndex("ITT_BUSINESS_USER_HEAD",0);		

						List<String> listBuh = Arrays.asList(sBusinessuhAry); 
						Set<String> mySetBuh = new HashSet<String>(listBuh); 
						String[] inputArrayBuh = new String[mySetBuh.size()]; 
						mySetBuh.toArray(inputArrayBuh); 

						for(int k=0;k<inputArrayBuh.length;k++)
						{
							//System.out.println("inputArrayBuh[k] "+inputArrayBuh[k]);
							formObject.NGAddItem("ITT_BUSINESS_USER_HEAD",inputArrayBuh[k]);
						}
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Unable to fetch data from Master for the user logged in.");
					bBrncInfoErr=true;
					return "0";
				}
			}
			else if(sMainCode.equals("11"))
			{
					JOptionPane.showMessageDialog(null,"Session expired.");
					bError=true;
					return "0";
					
			}
			else
			{
					JOptionPane.showMessageDialog(null,"Error at server end.");
					bError=true;
					return "0";
			}
		}
		return "1";
	}

	/*
	public String getDataBGroupBuhApp()
	{
		String sGroupName="";
		String sGroupNamesAry[];
		String sApplicationAry[];
		String sBusinessuhAry[];
		String strApp="";
		String strBuh="";

		System.out.println("Control inside getDataBGroupBuhApp");

		String sQuery="SELECT BUSINESSGROUP,APPLICATION,BUSINESSUSERHEAD FROM ng_business_group_master";
		sInputxml = icicicmn.WMTestSelect(sQuery, "3");
		sOutputxml = icicicmn.executeXmlGeneric(sInputxml);

		if(!sOutputxml.equals(""))
		{
			//System.out.println("getDataBGroupBuhApp "+sInputxml);
			//System.out.println("getDataBGroupBuhApp "+sOutputxml);
			
			xmlParser.setInputXML(sOutputxml);
			sMainCode = xmlParser.getValueOf("MainCode");
			if(sMainCode.equals("0"))
			{
				if(!sOutputxml.substring(sOutputxml.indexOf("<Results>")+9,sOutputxml.indexOf("</Results>")).equals(""))
				{
					WFXmlResponse xmlResponse = new WFXmlResponse(sOutputxml);
					WFXmlList RecordList;
					sResult="";
					for (RecordList =  xmlResponse.createList("Results", "Result");RecordList.hasMoreElements();RecordList.skip())
					{
						sResult=sResult+RecordList.getVal("Result");
						sResult=sResult+'~';
					}
					//System.out.println("Result getDataBGroupBuhApp	->"+sResult);

					sResult=sResult.substring(0,sResult.length()-1);
					sRowsAry=split(sResult,'~');
					if(sRowsAry.length>0)
					{
						for(int i=0;i<sRowsAry.length;i++)
						{
							//System.out.println("sRowsAry	->"+i+"	-)	"+sRowsAry[i]);
							sColAry=split(sRowsAry[i],'|');
							sGroupName=sGroupName+sColAry[1]+",";
							strApp=strApp+sColAry[2]+",";
							strBuh=strBuh+sColAry[3]+",";
						}
						//System.out.println("sGroupName	"+sGroupName);
						//System.out.println("strApp	"+strApp);
						//System.out.println("strBuh	"+strBuh);

						sGroupName=sGroupName.substring(0,sGroupName.length()-1);
						strApp=strApp.substring(0,strApp.length()-1);
						strBuh=strBuh.substring(0,strBuh.length()-1);

						sGroupNamesAry=split(sGroupName,',');
						//sApplicationAry=split(strApp,',');
						//sBusinessuhAry=split(strBuh,',');

						for(int j=0;j<sGroupNamesAry.length;j++)
						{
							formObject.NGAddItem("ITT_BUSINESS_GROUP",sGroupNamesAry[j]);
							//businessGroup.add(j,sGroupNamesAry[j]);
							//application.put(sGroupNamesAry[j],strApp);
							//businessUserH.put(sGroupNamesAry[j],strBuh);
						}

						for(int count=0;count<sGroupNamesAry.length;count++)
						{
							for(int counter=count+1;counter<sGroupNamesAry.length;counter++)
							{
								businessGroup.add(count,sGroupNamesAry[count]);

								if(!sGroupNamesAry[count].equalsIgnoreCase(sGroupNamesAry[counter]))
								{
									application.put(sGroupNamesAry[count],strApp);
									businessUserH.put(sGroupNamesAry[count],strBuh);
								}
							}
						}

						//Application.
						Set set = application.entrySet();
						Iterator l = set.iterator();

						while(l.hasNext())
						{
							Map.Entry me = (Map.Entry)l.next();
							System.out.println("\n"+me.getKey() + " <-:-> " + me.getValue());
						}
						//BUH
						Set sett = businessUserH.entrySet();
						Iterator m = sett.iterator();

						while(m.hasNext())
						{
							Map.Entry mee = (Map.Entry)m.next();
							System.out.println("\n"+mee.getKey() + " <-::-> " + mee.getValue());
						}

					}
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Unable to fetch data from Master for the user logged in.");
					bBrncInfoErr=true;
					return "0";
				}
			}
			else if(sMainCode.equals("11"))
			{
					JOptionPane.showMessageDialog(null,"Session expired.");
					bError=true;
					return "0";
					
			}
			else
			{
					JOptionPane.showMessageDialog(null,"Error at server end.");
					bError=true;
					return "0";
			}
		}
		return "1";
	}
	*/


	public void showPage(String url)
	{
			
		str[0]=url;
		obj=js.call("OpenURL_ITT",str);
		
	}

    public void NGF_EventHandler(String pEventName, String pControlName, String pControlValue, String pReserved)
    {
		//System.out.println("NGF_EventHandler Called  "+pEventName+"    "+pControlName+"    "+pControlValue+"  "+pReserved);

		String lstrProcessName = formObject.getWFProcessName();
		String lstrActivityName = formObject.getWFActivityName();		
		if(pEventName.equalsIgnoreCase("CLICK") || pEventName.equalsIgnoreCase("DBCLICK"))
		{
			if(pControlName.equalsIgnoreCase("Btn_History"))
			{
				url="ShowHistory.jsp?Process=ITT&ActivityName="+lstrActivityName+"&Pid="+xmlParserGenData.getValueOf("ProcessInstanceId");
				showPage(url);
			}
			if(pControlName.equalsIgnoreCase("ITT_BUSINESS_GROUP"))
			{
				try
				{
					if(!pControlValue.equals(""))
					{
						String strBusinessHeadApplication = fillBusinessUserHeadandApplication(pControlValue);
						//System.out.println("Return Value	-->>"+strBusinessHeadApplication);
					}
					if(pControlValue.equals(""))
					{
						formObject.NGClear("ITT_APPLICATION");		
						formObject.setNGListIndex("ITT_APPLICATION",0);	
						formObject.NGClear("ITT_BUSINESS_USER_HEAD");		
						formObject.setNGListIndex("ITT_BUSINESS_USER_HEAD",0);		
					}
				}
				catch(NullPointerException e)
				{
					System.out.println("Clearing Combo Box");
					bError=true;
				}
			}
			/*
			//This Code is use fill value in Business User head and Application. Using Hash Map
			try{
				if(pControlName.equalsIgnoreCase("ITT_BUSINESS_GROUP"))
				{
					//System.out.println("Return pControlValue	-->>"+pControlValue);
					//System.out.println("Return pControlName	-->>"+pControlName);

					if(pControlValue.equals(""))
					{
						formObject.NGClear("ITT_APPLICATION");		
						formObject.setNGListIndex("ITT_APPLICATION",0);	
						formObject.NGClear("ITT_BUSINESS_USER_HEAD");		
						formObject.setNGListIndex("ITT_BUSINESS_USER_HEAD",0);		
					}
					//Application.
					String strApplication = application.get(pControlValue);
					System.out.println("HM_Application	-->>"+strApplication);
					ApplicationAr=split(strApplication,',');
					formObject.NGClear("ITT_APPLICATION");		
					formObject.setNGListIndex("ITT_APPLICATION",0);		
					for(int k=0;k<ApplicationAr.length;k++)
					{
						formObject.NGAddItem("ITT_APPLICATION",ApplicationAr[k]);
					}
					//BUH
					String strBHead = businessUserH.get(pControlValue);
					BuheadAr=split(strBHead,',');
					System.out.println("HM_Buh	-->>"+strBHead);
					formObject.NGClear("ITT_BUSINESS_USER_HEAD");		
					formObject.setNGListIndex("ITT_BUSINESS_USER_HEAD",0);										
					for(int l=0;l<BuheadAr.length;l++)
					{
						formObject.NGAddItem("ITT_BUSINESS_USER_HEAD",BuheadAr[l]);
					}
				}
			}
			catch(NullPointerException e)
			{
				System.out.println("Clearing Combo Box");
				bError=true;
			}
			*/
		}
		else if(pEventName.equalsIgnoreCase("CHANGE"))
		{
			if(pControlName.equalsIgnoreCase("ITT_PLANNED_UAT_DATE"))
			{
				//System.out.println("Old Planned_Uat_Date	:"+formObject.getNGValue("Planned_Uat_Date"));
				//System.out.println("Current Planned_Uat_Date	:"+formObject.getNGValue("ITT_PLANNED_UAT_DATE"));
				try
				{
					if((!formObject.getNGValue("ITT_PLANNED_UAT_DATE").equalsIgnoreCase("")) && (!formObject.getNGValue("Planned_Uat_Date").equalsIgnoreCase("")))
					{
						System.out.println("Setisify Condition");
						int compResult = datesCompare(formObject.getNGValue("ITT_PLANNED_UAT_DATE"),formObject.getNGValue("Planned_Uat_Date"));
						System.out.println("compResult "+compResult);
						if((compResult == 1) || (compResult == 2))
						{
							bUATDate=true;
							formObject.setNGLocked("ITT_REASON_FOR_CHANGE",true);
						}
						else
						{
							bUATDate=false;
							formObject.setNGLocked("ITT_REASON_FOR_CHANGE",false);
						}
					}
				}
				catch(NullPointerException e)
				{
				   System.out.println("Dealing with planned uat date");
				}
			}
		}
	}

    public String NGF_GetMsgString(String s)
    {
        return null;
    }

    public int NGF_SaveNGFData(String ActivityName,String pEventName)
    {
		js =formObject.getJSObject();

		if(bError)
		{
			JOptionPane.showMessageDialog(null,"Can't Submit the Workitem, Error at the Server End.");
			return 0;
		}
		else if(ActivityName.equalsIgnoreCase("Work Introduction1"))
	    {
			return WorkIntroduction(pEventName,ActivityName);				
	    }
		else if(ActivityName.equalsIgnoreCase("Feasibility Study"))
		{
			return FeasibilityStudy(pEventName,ActivityName);
		}
		else if((ActivityName.equalsIgnoreCase("Internal Feasibility")) || (ActivityName.equalsIgnoreCase("External Feasibility")))
		{
			return InternalExternalFeasibility(pEventName,ActivityName);
		}
		else if(ActivityName.equalsIgnoreCase("BA Team"))
		{
			return BATeam(pEventName,ActivityName);
		}
		else if(ActivityName.equalsIgnoreCase("CR Exceptions"))
		{
			return CrException(pEventName,ActivityName);
		}
		else if(ActivityName.equalsIgnoreCase("Project Manager"))
		{
			return projectManager(pEventName,ActivityName);
		}
		else if(ActivityName.equalsIgnoreCase("Vndr Costing Team"))
		{
			return VndrCostingTeam(pEventName,ActivityName);
		}
		else if(ActivityName.equalsIgnoreCase("Vndr Development"))
		{
			return VndrDevelopment(pEventName,ActivityName);
		}
		else if(ActivityName.equalsIgnoreCase("Business Head"))
		{
			return BusinessHead(pEventName,ActivityName);
		}
		else if(ActivityName.equalsIgnoreCase("Technology Head"))
		{
			return TechnologyHead(pEventName,ActivityName);
		}
		else if((ActivityName.equalsIgnoreCase("BA Testing Team")) || (ActivityName.equalsIgnoreCase("Issue Resolution")) || (ActivityName.equalsIgnoreCase("Busines Testing Team")))
		{
			return BATestingTeam(pEventName,ActivityName);
		}
		else if(ActivityName.equalsIgnoreCase("Infra Team"))
		{
			return InfraTeam(pEventName,ActivityName);
		}
		else if(ActivityName.equalsIgnoreCase("PAT"))
		{
			return PAT(pEventName,ActivityName);
		}

		return 1;
    }
	public int PostSaveNGFData(String ActivityName,String pEventName)
    {
		
		js =formObject.getJSObject();
		if(bError)
		{
			JOptionPane.showMessageDialog(null,"Can't Submit the Workitem, Error at the Server End.");
			return 0;
		}
		if(pEventName.equalsIgnoreCase("S")){

			  return 1;
		}
		if(pEventName.equalsIgnoreCase("D"))
		{
			formObject.setNGValue("ITT_LASTPROCESSEDBY",Integer.toString(formObject.getUserId()));
			return 1;
		}
		try
		{
			
		}
		catch(NullPointerException nex)
		{
			nex.printStackTrace();
			bError=true;
			return 0;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			bError=true;
			return 0;
		}
	    return 1;
    }

	private void ngfUser_Validation(NGTextComponent ngtextcomponent) throws Exception
	{
	}

	private void ngfUser_Validation(Vector vector)
	{
	}
		
	private int WorkIntroduction(String pEventName,String ActivityName)
	{
		try{
			if(pEventName.equalsIgnoreCase("D"))
			{
				if(WorkIntroduction("S","")==0)
				{
					return 0;				
				}

				if(CheckMandatoryCombo("ITT_BUSINESS_GROUP","Business Group")==0)
					return 0;
				if(CheckMandatoryCombo("ITT_APPLICATION","Application")==0)
					return 0;
				/*
				if(CheckMandatoryTextBox("ITT_COMPANY","Company")==0)
					return 0;
				*/
				if(CheckMandatoryTextBox("ITT_TECHNOLOGY_GROUP","Technology Group")==0)
					return 0;
				if(CheckMandatoryTextBox("ITT_BENEFICIARY_ENTITIES","Beneficiary Entities")==0)
					return 0;
				if(CheckMandatoryCombo("ITT_BUSINESS_USER_HEAD","Business User Head")==0)
					return 0;
				if(CheckMandatoryCombo("ITT_CR_TYPE","CR Type")==0)
					return 0;
				//if(formObject.isNGLocked("ITT_CR_TYPE_TECHNICAL") && CheckMandatoryCombo("ITT_CR_TYPE_TECHNICAL","CR Type(Technical)")==0)
					//return 0;
				if(CheckMandatoryCombo("ITT_CR_PRIORITY","CR Priority")==0)
					return 0;
				if(CheckMandatoryCombo("ITT_REQUEST_TYPE","Request Type")==0)
					return 0;
				if(CheckMandatoryCombo("ITT_STRATEGIC_CHANGE","Strategic Change")==0)
					return 0;
				if(CheckMandatoryDate("ITT_ESTIMATED_BRS_SIGNOFF_DATE","Estimated BRS Sign Off Date")==0)
					return 0;
				if(CheckMandatoryDate("ITT_REQUESTED_GO_LIVE_DATE","Requested Go Live Date")==0)
					return 0;
				// Check for BRS Document
				if(CheckMandatoryDocType("BRS_Doc","BRS Document")==0)
					return 0;
				// Check for Test Case Scenario Document
				if(CheckMandatoryDocType("TestCaseScenario_Doc","Test Case Scenario Documents")==0)
					return 0;
			}
			else if(pEventName.equalsIgnoreCase("S"))			
			{
				/*
				if(!ICICIval.validateText(formObject.getNGValue("ITT_COMPANY"),"AlphaWithSpace"))
				{
					JOptionPane.showMessageDialog(null,"Please enter valid Company");
					formObject.NGFocus("ITT_COMPANY");
					return 0;
				}
				*/
				if(!ICICIval.validateText(formObject.getNGValue("ITT_TECHNOLOGY_GROUP"),"AlphaWithSpace"))
				{
					JOptionPane.showMessageDialog(null,"Please enter valid Technology Group");
					formObject.NGFocus("ITT_TECHNOLOGY_GROUP");
					return 0;
				}
				if(!ICICIval.validateText(formObject.getNGValue("ITT_BENEFICIARY_ENTITIES"),"AlphaWithSpace"))
				{
					JOptionPane.showMessageDialog(null,"Please enter valid Beneficiary Entities");
					formObject.NGFocus("ITT_BENEFICIARY_ENTITIES");
					return 0;
				}
			}		
		}
		catch(NullPointerException nex)
		{
		   nex.printStackTrace();
		   bError=true;
		   return 0;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			bError=true;
			return 0;
		}

		return 1;
	}

	private int FeasibilityStudy(String pEventName,String ActivityName)
	{
		try{
			if(pEventName.equalsIgnoreCase("D"))
			{
				if(FeasibilityStudy("S","")==0)
				{
					return 0;				
				}

				if(CheckMandatoryCombo("ITT_FEASIBILITY_TEAM_DECISION","Feasibility Team Decision")==0)
					return 0;				
				if((formObject.getNGValue("ITT_FEASIBILITY_TEAM_DECISION").equalsIgnoreCase("Reject"))&&(formObject.getNGValue("ITT_REMARK").trim().equals("")))
				{
					JOptionPane.showMessageDialog(null,"Remark is mandatory in case Feasibility Team Decision is Reject.");
					formObject.NGFocus("ITT_REMARK");
					return 0;
				}
				// Check for BRS Document
				if(CheckMandatoryDocType("BRS_Doc","BRS Document")==0)
					return 0;
			}
			else if(pEventName.equalsIgnoreCase("S"))			
			{
				return 1;
			}		
		}
		catch(NullPointerException nex)
		{
		   nex.printStackTrace();
		   bError=true;
		   return 0;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			bError=true;
			return 0;
		}

		return 1;
	}

	private int InternalExternalFeasibility(String pEventName,String ActivityName)
	{
		try{
			if(pEventName.equalsIgnoreCase("D"))
			{
				if(InternalExternalFeasibility("S","")==0)
				{
					return 0;				
				}

				if(ActivityName.equalsIgnoreCase("Internal Feasibility"))
				{
					if(CheckMandatoryCombo("ITT_INTERNAL_DEV_TEAM","Internal Development Team")==0)
						return 0;	
				}
				if(ActivityName.equalsIgnoreCase("External Feasibility"))
				{
					if(CheckMandatoryCombo("ITT_VENDOR_NAME","Vendor Name")==0)
						return 0;	
				}

				if(CheckMandatoryCombo("ITT_VENDOR_TEAM_DECSION","Vendor Team Decision")==0)
					return 0;	
				
				if((formObject.getNGValue("ITT_VENDOR_TEAM_DECSION").equalsIgnoreCase("Not Feasible"))&&(formObject.getNGValue("ITT_REMARK").trim().equals("")))
				{
					JOptionPane.showMessageDialog(null,"Remark is mandatory in case Decision is Not Feasible.");
					formObject.NGFocus("ITT_REMARK");
					return 0;
				}				

				//Check for Test Impact Analysis Document
				if(CheckMandatoryDocType("ImpactAnalysis_Doc","Impact Analysis Documents")==0)
					return 0;
			}
			else if(pEventName.equalsIgnoreCase("S"))			
			{
				return 1;
			}		
		}
		catch(NullPointerException nex)
		{
		   nex.printStackTrace();
		   bError=true;
		   return 0;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			bError=true;
			return 0;
		}

		return 1;
	}

	private int BATeam(String pEventName,String ActivityName)
	{
		try
		{
			if(pEventName.equalsIgnoreCase("D"))
			{
				if(BATeam("S","")==0)
				{
					return 0;				
				}
     		    //Bug No: 007 and 018 and 031 System Testing
				str[0]="BATeam";
				obj=js.call("ITT_JSCheckExceptionStatus",str);
				if(!obj.toString().equalsIgnoreCase("R"))
				{
					if(CheckMandatoryCombo("ITT_BA_DECISION","BA Decision")==0)
						return 0;
					//Check for Test Case Scenario Document
					if(CheckMandatoryDocType("TestCaseScenario_Doc","Test Case Scenario Documents")==0)
						return 0;
					if(CheckMandatoryDocType("Others_Doc","Others Documents")==0)
						return 0;	
					if(CheckMandatoryTextBox("ITT_REMARK","Remarks")==0)
						return 0;
					if((formObject.getNGValue("ITT_BA_DECISION").trim().equalsIgnoreCase("Reject")) && (!obj.toString().equalsIgnoreCase("R")))
					{					
						JOptionPane.showMessageDialog(null,"You must raise exception in case BA Decision is Reject");					
						return 0;
					}
					strC[0]="ChkExpClear";
					objC=js.call("ITT_JSCheckExceptionStatus",strC);

					if((formObject.getNGValue("ITT_PREV_WRK_STEP").trim().equalsIgnoreCase("Project Manager")) && (objC.toString().equalsIgnoreCase("R")))
					{
						JOptionPane.showMessageDialog(null,"Please clear and commit exceptions before proceed");					
						return 0;
					}
				}
				else
				{
					//ICICI_ITT_007:System Testing
					if((obj.toString().equalsIgnoreCase("R")) && !(formObject.getNGValue("ITT_BA_DECISION").trim().equalsIgnoreCase("Reject")))
					{					
						JOptionPane.showMessageDialog(null,"You must select BA Decision as Reject, if exception is Raised.");				
						return 0;
					}
				}				
			}
			else if(pEventName.equalsIgnoreCase("S"))			
			{
				return 1;
			}		
		}
		catch(NullPointerException nex)
		{
		   nex.printStackTrace();
		   bError=true;
		   return 0;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			bError=true;
			return 0;
		}
		return 1;
	}

	private int CrException(String pEventName,String ActivityName)
	{
		try{
			if(pEventName.equalsIgnoreCase("D"))
			{
				if(CrException("S","")==0)
				{
					return 0;				
				}
				/*
				As per requirment( Based on discussion B/W Vivek and Dinesh) if Dicision is Reject and WI going to Discard
				in this case He/She must clear and commit exception before proceed.
				*/
				//Bug No: 24 System Testing		
				if(CheckMandatoryCombo("ITT_CR_DECSION","CR Decision")==0)
					return 0;
				
				str[0]="ChkExpClear";
				obj=js.call("ITT_JSCheckExceptionStatus",str);

				/*
				if((!formObject.getNGValue("ITT_CR_DECSION").trim().equalsIgnoreCase("Reject")) && (obj.toString().equalsIgnoreCase("R")))
				{	
					//Bug No: 29 System Testing					
					JOptionPane.showMessageDialog(null,"Please clear and commit exception or select CR Decision as Reject.");				
					return 0;
				}*/
				if(obj.toString().equalsIgnoreCase("R"))
				{	
					//Bug No: 29 System Testing					
					JOptionPane.showMessageDialog(null,"Please clear and commit exception");				
					return 0;
				}
			}
			else if(pEventName.equalsIgnoreCase("S"))			
			{
				return 1;
			}		
		}
		catch(NullPointerException nex)
		{
		   nex.printStackTrace();
		   bError=true;
		   return 0;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			bError=true;
			return 0;
		}

		return 1;
	}
	private int projectManager(String pEventName,String ActivityName)
	{
		try{
			System.out.println("pEventName	->"+pEventName+"\n");
			if(pEventName.equalsIgnoreCase("D"))
			{
				if(projectManager("S","")==0)
				{
				   return 0;				
				}
				//Bug No: 28 System Testing
				/*
				String arry[]=split(obj.toString(),'~');
				String slocalExpStatus="";
				String sPrevExpStatus="";
				System.out.println("Return Obj "+obj.toString());
				System.out.println("Array Length "+arry.length);
				
				if(arry.length==1)
					slocalExpStatus=arry[0];
				if(arry.length==2)
				{	
					slocalExpStatus=arry[0];
					sPrevExpStatus=arry[1];
				}

				System.out.println("slocalExpStatus "+slocalExpStatus);
				System.out.println("sPrevExpStatus "+sPrevExpStatus);
				*/
				str[0]="Project ManagerD";
				obj=js.call("ITT_JSCheckExceptionStatus",str);

				if(!obj.toString().equalsIgnoreCase("R"))
				{
					if(!formObject.getNGValue("ITT_INTERNAL_DEV_TEAM").equalsIgnoreCase(""))
					{
						System.out.println("ITT_INTERNAL_DEV_TEAM"+formObject.getNGValue("ITT_INTERNAL_DEV_TEAM"));
						System.out.println("ITT_VENDOR_NAME"+formObject.getNGValue("ITT_VENDOR_NAME"));
						if(CheckMandatoryTextBox("ITT_PROJECT_COST","Cost")==0)
							return 0;
						if(CheckMandatoryTextBox("ITT_EFFORT","Effort")==0)
							return 0;
						if(CheckMandatoryTextBox("ITT_RESOURCES","Resources")==0)
							return 0;
						if(CheckMandatoryDate("ITT_EXPECTED_UAT_SIGNOFF_DATE","Expected UAT Sign off Date")==0)
							return 0;
						if(CheckMandatoryDate("ITT_PLANNED_DELIVERY_DATE","Planned Delivery Date")==0)
							return 0;
					}
					if(CheckMandatoryDate("ITT_PLANNED_UAT_DATE","Planned UAT Date")==0)
						return 0;
					if(formObject.getNGValue("ITT_PREV_WRK_STEP").equalsIgnoreCase("Vndr Costing Team"))
					{
						if(CheckMandatoryDate("ITT_EXPECTED_UAT_SIGNOFF_DATE","Expected UAT Sign off Date")==0)
							return 0;
					}
					if(CheckMandatoryCombo("ITT_PROJECT_MANAGER_DECISION","Project Manager Decision")==0)
						return 0;	
					if(formObject.isNGLocked("ITT_REASON_FOR_CHANGE"))
					{
						if(CheckMandatoryTextBox("ITT_REASON_FOR_CHANGE","Reason for Change")==0)
							return 0;	
					}

					if((formObject.getNGValue("ITT_PROJECT_MANAGER_DECISION").trim().equalsIgnoreCase("Reject")) && (!obj.toString().equalsIgnoreCase("R")))
					{					
						JOptionPane.showMessageDialog(null,"You must Raise exception in case Project Manager Decision is Reject");					
						return 0;
					}
					//Check if workitem come from Technical Head and Exception is not clear.
					strC[0]="ChkExpClear";
					objC=js.call("ITT_JSCheckExceptionStatus",strC);

					if((formObject.getNGValue("ITT_PREV_WRK_STEP").trim().equalsIgnoreCase("Technology Head")) && (objC.toString().equalsIgnoreCase("R")))
					{
						JOptionPane.showMessageDialog(null,"Please clear and commit exceptions OR Select decision as Reject");
						return 0;
					}
					if((formObject.getNGValue("ITT_PREV_WRK_STEP").trim().equalsIgnoreCase("Business Head")) && (objC.toString().equalsIgnoreCase("R")))
					{
						JOptionPane.showMessageDialog(null,"Please clear and commit exceptions OR Select decision as Reject");
						return 0;
					}
				}
				else
				{
					if(obj.toString().equals("false"))
					{
						return 0;
					}

					if(CheckMandatoryDate("ITT_PLANNED_UAT_DATE","Planned UAT Date")==0)
						return 0;

					if((obj.toString().equalsIgnoreCase("R")) && !(formObject.getNGValue("ITT_PROJECT_MANAGER_DECISION").trim().equalsIgnoreCase("Reject")))
					{					
						JOptionPane.showMessageDialog(null,"You must select Project Manager Decision as Reject, if exception is Raised.");				
						return 0;
					}
					if(((obj.toString().equalsIgnoreCase("R")) && !(formObject.getNGValue("ITT_PREV_WRK_STEP").trim().equalsIgnoreCase("BA Team"))) && ((formObject.getNGValue("ITT_FLAG_BOUNCE_BACK_TO_BA_TEAM").trim().equalsIgnoreCase("Y")) || (formObject.getNGValue("ITT_FLAG_BOUNCE_BACK_TO_BU").trim().equalsIgnoreCase("Y"))))
					{					
						JOptionPane.showMessageDialog(null,"Bounce Back to BA Team Exception or Bounce Back to BU Exception can raise only when WI come from BA Team");				
						return 0;
					}
					if((obj.toString().equalsIgnoreCase("R")) && !(formObject.getNGValue("ITT_PREV_WRK_STEP").trim().equalsIgnoreCase("BA Testing Team")) && (formObject.getNGValue("ITT_FLAG_BOUNCE_BACK_TO_BA_TESTEAM").trim().equalsIgnoreCase("Y")))
					{					
						JOptionPane.showMessageDialog(null,"Bounce Back to BA Testing Team Exception can raise only when WI come from BA Testing Team, Please Undo the Exception");				
						//JOptionPane.showMessageDialog(null,"Please close and reopen the WI");		
						return 0;
					}
					if((obj.toString().equalsIgnoreCase("R")) && (formObject.getNGValue("ITT_PREV_WRK_STEP").trim().equalsIgnoreCase("BA Testing Team")) && (formObject.getNGValue("ITT_FLAG_BOUNCE_BACK_TO_BA_TESTEAM").trim().equalsIgnoreCase("N")))
					{					
						JOptionPane.showMessageDialog(null,"You can raise only Bounce Back to BA Testing Team Exception at this WS");				
						return 0;
					}
					if((obj.toString().equalsIgnoreCase("R")) && (formObject.getNGValue("ITT_PREV_WRK_STEP").trim().equalsIgnoreCase("BA Team")) && (formObject.getNGValue("ITT_FLAG_BOUNCE_BACK_TO_BA_TEAM").trim().equalsIgnoreCase("Y")) && (formObject.getNGValue("ITT_FLAG_BOUNCE_BACK_TO_BU").trim().equalsIgnoreCase("Y")))
					{
						JOptionPane.showMessageDialog(null,"You can either raise Bounce Back to BA Team Exception or Bounce Back to BU Exception");				
						return 0;
					}
				}
			}
			else if(pEventName.equalsIgnoreCase("S"))
			{
				System.out.println("Inside S event\n");
                str[0]="Project ManagerS";
				obj=js.call("ITT_JSCheckExceptionStatus",str);
				if(obj.toString().equals("false"))
				{
					return 0;
				}
				System.out.println("ITT_FLAG_BOUNCE_BACK_TO_BA_TEAM	->"+formObject.getNGValue("ITT_FLAG_BOUNCE_BACK_TO_BA_TEAM"));
				System.out.println("ITT_FLAG_BOUNCE_BACK_TO_BA_TESTEAM	->"+formObject.getNGValue("ITT_FLAG_BOUNCE_BACK_TO_BA_TESTEAM"));
				System.out.println("ITT_FLAG_BOUNCE_BACK_TO_BU	->"+formObject.getNGValue("ITT_FLAG_BOUNCE_BACK_TO_BU"));


				if(((obj.toString().equalsIgnoreCase("Bounce Back to BA Team") || obj.toString().equalsIgnoreCase("Bounce Back to BU") ) && !(formObject.getNGValue("ITT_PREV_WRK_STEP").trim().equalsIgnoreCase("BA Team"))))
				{					
					JOptionPane.showMessageDialog(null,"Bounce Back to BA Team Exception or Bounce Back to BU Exception can raise only when WI come from BA Team");				
					return 0;
				}
				if((obj.toString().equalsIgnoreCase("Bounce Back to BA Testing Team")) && !(formObject.getNGValue("ITT_PREV_WRK_STEP").trim().equalsIgnoreCase("BA Testing Team")))
				{					
					JOptionPane.showMessageDialog(null,"Bounce Back to BA Testing Team Exception can raise only when WI come from BA Testing Team");				
					return 0;
				}
				if((!obj.toString().equalsIgnoreCase("Bounce Back to BA Testing Team")) && (formObject.getNGValue("ITT_PREV_WRK_STEP").trim().equalsIgnoreCase("BA Testing Team")) && (formObject.getNGValue("ITT_PROJECT_MANAGER_DECISION").trim().equalsIgnoreCase("Reject")))
				{					
					JOptionPane.showMessageDialog(null,"You can raise only Bounce Back to BA Testing Team Exception");				
					return 0;
				}
				if(!formObject.getNGValue("ITT_INTERNAL_DEV_TEAM").equalsIgnoreCase(""))
				{
					System.out.println("Project Manager WS for Validation");
					if(!ICICIval.validateText(formObject.getNGValue("ITT_PROJECT_COST"),"Number"))
					{
						JOptionPane.showMessageDialog(null,"Please enter valid Cost");
						formObject.NGFocus("ITT_PROJECT_COST");
						return 0;
					}
					if(!ICICIval.validateText(formObject.getNGValue("ITT_EFFORT"),"AlphanumericWithSpace"))
					{
						JOptionPane.showMessageDialog(null,"Please enter valid Effort");
						formObject.NGFocus("ITT_EFFORT");
						return 0;
					}
					if(!ICICIval.validateText(formObject.getNGValue("ITT_RESOURCES"),"Number"))
					{
						JOptionPane.showMessageDialog(null,"Please enter valid Resources");
						formObject.NGFocus("ITT_RESOURCES");
						return 0;
					}
				}
				System.out.println("Returning from S event\n");
				return 1;
			}
		}
		catch(NullPointerException nex)
		{
		   nex.printStackTrace();
		   bError=true;
		   return 0;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			bError=true;
			return 0;
		}

		return 1;
	}
	private int VndrCostingTeam(String pEventName,String ActivityName)
	{
		try{
			if(pEventName.equalsIgnoreCase("D"))
			{
				if(VndrCostingTeam("S","")==0)
				{
					return 0;				
				}
				//System.out.println("case A");
				if(CheckMandatoryTextBox("ITT_PROJECT_COST","Cost")==0)
					return 0;
				if(CheckMandatoryTextBox("ITT_EFFORT","Effort")==0)
					return 0;
				if(CheckMandatoryTextBox("ITT_RESOURCES","Resources")==0)
					return 0;
				if(CheckMandatoryTextBox("ITT_REMARK","Remarks")==0)
					return 0;
				if(CheckMandatoryDate("ITT_PLANNED_DELIVERY_DATE","Planned Delivery Date")==0)
					return 0;
			}
			else if(pEventName.equalsIgnoreCase("S"))			
			{
				//System.out.println("case B");
				if(!ICICIval.validateText(formObject.getNGValue("ITT_PROJECT_COST"),"Number"))
				{
					JOptionPane.showMessageDialog(null,"Please enter valid Cost");
					formObject.NGFocus("ITT_PROJECT_COST");
					return 0;
				}
				//if(!ICICIval.validateText(formObject.getNGValue("ITT_EFFORT"),"AlphaWithSpace"))
				//Bug No. 3 UAT Testing.
				if(!ICICIval.validateText(formObject.getNGValue("ITT_EFFORT"),"AlphanumericWithSpace"))
				{
					JOptionPane.showMessageDialog(null,"Please enter valid Effort");
					formObject.NGFocus("ITT_EFFORT");
					return 0;
				}
				if(!ICICIval.validateText(formObject.getNGValue("ITT_RESOURCES"),"Number"))
				{
					JOptionPane.showMessageDialog(null,"Please enter valid Resources");
					formObject.NGFocus("ITT_RESOURCES");
					return 0;
				}
			}
			else if(pEventName.equalsIgnoreCase("S"))			
			{
				return 1;
			}		
		}
		catch(NullPointerException nex)
		{
		   nex.printStackTrace();
		   bError=true;
		   return 0;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			bError=true;
			return 0;
		}

		return 1;
	}

	private int VndrDevelopment(String pEventName,String ActivityName)
	{
		try{
			if(pEventName.equalsIgnoreCase("D"))
			{
				if(VndrDevelopment("S","")==0)
				{
					return 0;				
				}
				if(CheckMandatoryTextBox("ITT_REMARK","Remarks")==0)
					return 0;
				if(CheckMandatoryDocType("DeploymentNote_Doc","Deployment Note Documents")==0)
					return 0;	
			}
			else if(pEventName.equalsIgnoreCase("S"))			
			{
				return 1;
			}		
		}
		catch(NullPointerException nex)
		{
		   nex.printStackTrace();
		   bError=true;
		   return 0;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			bError=true;
			return 0;
		}

		return 1;
	}

	private int BusinessHead(String pEventName,String ActivityName)
	{
		try{
			if(pEventName.equalsIgnoreCase("D"))
			{
				if(BusinessHead("S","")==0)
				{
					return 0;				
				}

				str[0]="ChkExpRaise";
				obj=js.call("ITT_JSCheckExceptionStatus",str);
				if(!obj.toString().equalsIgnoreCase("R"))
				{
					if(CheckMandatoryCombo("ITT_BUH_DECISION","BUH Decision")==0)
						return 0;		
					if(CheckMandatoryTextBox("ITT_REMARK","Remarks")==0)
						return 0;

					//Bug No. 5, UAT Testing
					/*
					#Other Document is not mandatory at this WS so below code is commented.
					// Check for Others Document
					if(CheckMandatoryDocType("Others_Doc","Others Documents")==0)
						return 0;	
					*/

					if((formObject.getNGValue("ITT_BUH_DECISION").trim().equalsIgnoreCase("Reject")) && (!obj.toString().equalsIgnoreCase("R")))
					{
						JOptionPane.showMessageDialog(null,"You must raise exception in case BUH Decision is Reject");					
						return 0;
					}
				}
				else
				{
					if(!(formObject.getNGValue("ITT_BUH_DECISION").trim().equalsIgnoreCase("Reject")) && (obj.toString().equalsIgnoreCase("R")))
					{					
						JOptionPane.showMessageDialog(null,"You must select BUH Decision as Reject, if exception is Raised.");				
						return 0;
					}
				}
				
			}
			else if(pEventName.equalsIgnoreCase("S"))			
			{
				return 1;
			}		
		}
		catch(NullPointerException nex)
		{
		   nex.printStackTrace();
		   bError=true;
		   return 0;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			bError=true;
			return 0;
		}

		return 1;
	}

	private int TechnologyHead(String pEventName,String ActivityName)
	{
		try{
			if(pEventName.equalsIgnoreCase("D"))
			{
				if(TechnologyHead("S","")==0)
				{
					return 0;				
				}

				str[0]="ChkExpRaise";
				obj=js.call("ITT_JSCheckExceptionStatus",str);
				if(!obj.toString().equalsIgnoreCase("R"))
				{
					if(CheckMandatoryCombo("ITT_TECHNICAL_HEAD_DECISION","Technical Head Decision")==0)
						return 0;		
					if(CheckMandatoryTextBox("ITT_REMARK","Remarks")==0)
						return 0;
					/*
					// Check for Others Document
					if(CheckMandatoryDocType("Others_Doc","Others Documents")==0)
						return 0;
					*/
				}
				else if((obj.toString().equalsIgnoreCase("R")) && (!formObject.getNGValue("ITT_TECHNICAL_HEAD_DECISION").trim().equalsIgnoreCase("Reject")))
				{
					JOptionPane.showMessageDialog(null,"You must select Technical Head Decision as Reject, if exception is Raised.");					
					return 0;
				}
			}
			else if(pEventName.equalsIgnoreCase("S"))			
			{
				return 1;
			}		
		}
		catch(NullPointerException nex)
		{
		   nex.printStackTrace();
		   bError=true;
		   return 0;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			bError=true;
			return 0;
		}
		return 1;
	}

	private int BATestingTeam(String pEventName,String ActivityName)
	{
		try{
			if(pEventName.equalsIgnoreCase("D"))
			{
				if(BATestingTeam("S","")==0)
				{
					return 0;				
				}

				// Check for Test Case Scenario Document
				if(CheckMandatoryDocType("TestCaseScenario_Doc","Test Case Scenario Documents")==0)
					return 0;

				if(ActivityName.equalsIgnoreCase("BA Testing Team"))
				{
					if(CheckMandatoryCombo("ITT_BA_TESTING_TEAM_DECISION","BA Testing Team Decision")==0)
					return 0;
					/*
					// Mandatory Check for Grid data
					if(CheckMandatoryTextBox("TESTCASENO","Test Case No")==0)
						return 0;						

					if(CheckMandatoryCombo("BUGSTATUS","Bug Status")==0)
						return 0;

					if(CheckMandatoryCombo("BUGSEVERITY","Bug Severity")==0)
						return 0;

					//if(CheckMandatoryTextBox("BUGDESCRIPTION","Bug Description")==0)
						//return 0;
					*/
					if(CheckMandatoryTextBox("ITT_REMARK","Remarks")==0)
						return 0;
					
					//Check if workitem come from Project Manager and Exception is not clear.
					strC[0]="ChkExpClear";
					objC=js.call("ITT_JSCheckExceptionStatus",strC);

					//System.out.println("Clear Status	->"+(objC.toString().equalsIgnoreCase("R")));

					if((formObject.getNGValue("ITT_PREV_WRK_STEP").trim().equalsIgnoreCase("Project Manager")) && (objC.toString().equalsIgnoreCase("R")))
					{
						JOptionPane.showMessageDialog(null,"Please clear and commit exceptions.");
						return 0;
					}					
						
				}
				if(ActivityName.equalsIgnoreCase("Issue Resolution"))
				{
					if(CheckMandatoryCombo("ITT_BUG_TYPE","Bug Type")==0)
						return 0;		
				}
				if(ActivityName.equalsIgnoreCase("Busines Testing Team"))
				{
					//System.out.println("Business Testing Team Decision");
					if(CheckMandatoryCombo("ITT_BUSINESS_TESTING_TEAM_DECISION","Business Testing Team Decision")==0)
						return 0;		
				}
			}
			else if(pEventName.equalsIgnoreCase("S"))		
			{
				/*
				if((!ICICIval.validateText(formObject.getNGValue("TESTCASENO"),"AlphanumericWithSpace")) && formObject.isNGLocked("TESTCASENO"))
				{
					JOptionPane.showMessageDialog(null,"Please enter valid Test Case No.");
					formObject.NGFocus("TESTCASENO");
					return 0;
				}
				*/
				return 1;
			}		
		}
		catch(NullPointerException nex)
		{
		   nex.printStackTrace();
		   bError=true;
		   return 0;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			bError=true;
			return 0;
		}

		return 1;
	}

	private int InfraTeam(String pEventName,String ActivityName)
	{
		try{
			if(pEventName.equalsIgnoreCase("D"))
			{
				if(InfraTeam("S","")==0)
				{
					return 0;				
				}
				if(CheckMandatoryCombo("ITT_INFRA_TEAM_DECISION","Infra Team Decision")==0)
					return 0;		
				//if(CheckMandatoryDate("ITT_DEPLOYMENT_DATE","Deployment Date")==0)
					//return 0;
				if(CheckMandatoryTextBox("ITT_REMARK","Remarks")==0)
					return 0;	
			}
			else if(pEventName.equalsIgnoreCase("S"))			
			{
				if(CheckMandatoryDate("ITT_DEPLOYMENT_DATE","Deployment Date")==0)
					return 0;
				System.out.println("current date "+dateformat1.format(new Date()));
				int flag = datesCompare(formObject.getNGValue("ITT_DEPLOYMENT_DATE"),dateformat1.format(new Date()));
				System.out.println("flag "+flag);
				if((flag == 2) || (flag == 0))
				{
					JOptionPane.showMessageDialog(null,"Deployment Date can not be past Date.");
					formObject.NGFocus("ITT_DEPLOYMENT_DATE");
					return 0;
				}
			   	return 1;
			}		
		}
		catch(NullPointerException nex)
		{
		   nex.printStackTrace();
		   bError=true;
		   return 0;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			bError=true;
			return 0;
		}
		return 1;
	}

	private int PAT(String pEventName,String ActivityName)
	{
		try{
			if(pEventName.equalsIgnoreCase("D"))
			{
				if(PAT("S","")==0)
				{
					return 0;				
				}
				if(CheckMandatoryCombo("ITT_PAT_TEAM_DECSION","PAT Team Decision")==0)
					return 0;	
				// Check for Test Case Scenario Document
				if(CheckMandatoryDocType("TestCaseScenario_Doc","Test Case Scenario Documents")==0)
					return 0;				
			}
			else if(pEventName.equalsIgnoreCase("S"))			
			{
				return 1;
			}		
		}
		catch(NullPointerException nex)
		{
		   nex.printStackTrace();
		   bError=true;
		   return 0;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			bError=true;
			return 0;
		}
		return 1;
	}


	public static String[] split(String s, char c)
	{
			Vector vector = new Vector();
			String s1 = new String();
			for(int i = 0; i < s.length(); i++)
				if(s.charAt(i) == c)
				{
					vector.add(s1);
					s1 = new String();
				} else
				{
					s1 = s1 + s.charAt(i);
				}
			vector.add(s1);
			String as[] = new String[vector.size()];
			for(int j = 0; j < as.length; j++)
				 as[j] = new String((String)vector.elementAt(j));
			return as;
	}
		
	public boolean check_Numeric(String str,int iprefix,int ipostfix)
	{
			if(!ICICIval.validateText(str,"Amount"))
			{
				  return false;
			}
			if(str.trim().equals(".") || ((str.indexOf(".")>0) && str.indexOf(".")==(str.length()-1)) || (str.indexOf(".")!=str.lastIndexOf(".")))
			{
				System.out.println(2);
				return false;
			}
			else if(str.indexOf(".")!=-1)
			{
				if((str.substring(0,str.indexOf("."))).length()>iprefix)
				{
				   System.out.println(3);
				   return false;
				}
				else if((str.substring(str.indexOf(".")+1,str.length())).length()>ipostfix)
				{
					 System.out.println(4);
					 return false;
				}
			}
			else if(str.length()>iprefix)
			{
				 return false;
		 
			}
		 
		 return true;
	 
	}

	private int CheckMandatoryCombo(String ControlName,String CaptionName){
		 
	   if((formObject.isNGLocked(ControlName)) && ( formObject.getNGValue(ControlName).trim().equalsIgnoreCase("--Select--") || formObject.getNGValue(ControlName).trim().equalsIgnoreCase("") ) )
	   {
		  JOptionPane.showMessageDialog(null,"Please Select "+CaptionName+" .");
		  formObject.NGFocus(ControlName);
		  return 0;
	   }
		return 1;
	 }

	private int CheckMandatoryTextBox(String ControlName,String CaptionName){
	   if((formObject.isNGLocked(ControlName)) && formObject.getNGValue(ControlName).trim().equalsIgnoreCase(""))
	   {
		  JOptionPane.showMessageDialog(null,"Please enter "+CaptionName+" .");
		  formObject.NGFocus(ControlName);
		  return 0;
	   }
	   return 1;
	}

	private int CheckMandatoryDate(String ControlName,String CaptionName){
	   if((formObject.isNGEnable(ControlName)) && formObject.getNGValue(ControlName).trim().equalsIgnoreCase(""))
	   {
		  JOptionPane.showMessageDialog(null,"Please select "+CaptionName+" .");
		  formObject.NGFocus(ControlName);
		  return 0;
	   }
	   return 1;
	}

	private int CheckMandatoryDocType(String DocType,String DocCaptionName){
		str[0] = DocType;
		js =formObject.getJSObject();
		obj=js.call("ILombard_ITT_MandDocumentCheck",str);
		//System.out.println("return value "+obj.toString());
		if(obj.toString().equals("false"))
		{
			JOptionPane.showMessageDialog(null,"Please Import "+DocCaptionName+" .");					
			return 0;
		}
	   return 1;
	}

		/*
		#This is simple date comparision function, Function is written by Yogendra Kaushik(20/May/2010).
		#Both the parameter should be in "dd/MM/yyyy" format.
		#Function return the flag value 0 -> both date are equals,1->first date is greater,2->Second date is greater.

		Function Limitation:
		#This function can campare date only defined format.
		*/

	public int datesCompare(String sDate1,String sDate2)   
	{
		int iYear1 = Integer.parseInt(sDate1.substring(7,10));
		int iYear2 = Integer.parseInt(sDate2.substring(7,10));

		if(iYear1 > iYear2)
			return 1;
		else if (iYear1 < iYear2)
			return 2;
		else
		{
			int iMonth1 = Integer.parseInt(sDate1.substring(3,5));
			int iMonth2 = Integer.parseInt(sDate2.substring(3,5));

			if(iMonth1 > iMonth2)
				return 1;
			else if (iMonth1 < iMonth2)
				return 2;
			else
			{
				int iDay1 = Integer.parseInt(sDate1.substring(0,2));
				int iDay2 = Integer.parseInt(sDate2.substring(0,2));
			
				if(iDay1 >= iDay2)
					return 1;
				else //if (iDay1 < iDay2)		// Commented by Dinesh to include current day
					return 2;
				//else
				//	return 0;
			}
		}
	}//End of function.
}//End of Class.

