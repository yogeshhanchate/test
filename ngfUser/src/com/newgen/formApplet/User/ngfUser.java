//----------------------------------------------------------------------------------------------------
//		NEWGEN SOFTWARE TECHNOLOGIES LIMITED
//Group						: APPPROJ
//Product / Project			: LnS
//Module					: NGFUSER
//File Name					: ngfUser.java
//Author					: Ravi Shankar Tiwari
//Date written (DD/MM/YYYY)	: 10/09/2010
//Description				:Main file.
//----------------------------------------------------------------------------------------------------
//			CHANGE HISTORY
//----------------------------------------------------------------------------------------------------
// Date			 Change By	 Change Description (Bug No. (If Any))
// (DD/MM/YYYY)
//----------------------------------------------------------------------------------------------------

package com.newgen.formApplet.User;
import com.newgen.formApplet.SubForm;

import com.newgen.formApplet.Controls.InputVerifier.NGTextComponent;
import com.newgen.formApplet.XMLParser;
import java.io.PrintStream;
import java.util.Vector;

 //added for HT
 import com.newgen.formApplet.*;
import com.newgen.formApplet.User.*;
import netscape.javascript.*;

import com.newgen.formApplet.Controls.InputVerifier.NGTextComponent;
//Import this package for using JSobject
import netscape.javascript.JSObject;
import org.apache.commons.httpclient.*;
import java.applet.*;

import org.apache.commons.httpclient.methods.GetMethod;
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


// Referenced classes of package com.newgen.formApplet.User:
//            NGFUserAdapter

public class ngfUser extends NGFUserAdapter
{
	//code added for health tracker process
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
	String sCommandName=null;
	XMLParser generalDataParser=new XMLParser();
	int debug=0;  // for enabling/disabling the debug mode.. 0-disable; 1-enable
	//changes end here

	NGFPortabilitySubForm ngfPortabilitySubForm =null;

	ICICI_LOMBARD iciciLombard=new ICICI_LOMBARD();
	ICICI_ITT iciciItt=new ICICI_ITT();
	boolean _bEventFired;

	public ngfUser()
    {
        this._bEventFired = false;
	}
//added for HT(health tracker process)
   //setting the applet object
	/*public void setAppletObject(Object appletObject)
	{

		formApplet = (FormApplet)appletObject;
		appletContext = formApplet.getAppletContext();
		if (formApplet.getParameter("ENCODING") != null && !formApplet.getParameter("ENCODING").equals(""))
		{
			sEncoding = formApplet.getParameter("ENCODING");
		}
	}

	//chnanges end here
	*/
    public String executeCommand(String strInputXml)
    {
        String lstrProcessName = formObject.getWFProcessName();
		String lstrActivityName = formObject.getWFActivityName();

        XMLParser localXMLParser = new XMLParser(strInputXml);
		sCommandName = localXMLParser.getValueOf("CommandName");

//////////// Sub Form Code
	
	   ////System.out.println("Command name*******"+sCommandName);
	  // ////System.out.println("TTTT:"+formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL"));
	 if((formObject.getNGSubForm()!=null) && (lstrProcessName.equalsIgnoreCase("HT")) && (lstrActivityName.equalsIgnoreCase("BSG_DATAENTRY")))
		{
			if(sCommandName.equalsIgnoreCase("Add_row"))
			{
					////System.out.println("NGAddRow");
					if(formObject.getNGValue("ICICILOMBARD_HT_CURRENT_PORTABILITY_SI").trim().length()==0)
					{
							JOptionPane.showMessageDialog(null,"Please Eneter Current Portability SI ");
					}
					else if(formObject.getNGValue("SubForm_Claim").trim().equalsIgnoreCase("--select--"))
					{
							JOptionPane.showMessageDialog(null,"select Claim  ");
					}
					else if(formObject.getNGValue("SubForm_Claim").trim().equalsIgnoreCase("Yes") && (formObject.getNGValue("SubForm_Nature_of_Illness").trim().length()==0))
					{
							JOptionPane.showMessageDialog(null,"Please enter Nature of illness ");
					}
					else
					{
					formObject.ExecuteExternalCommand("NGAddRow","ICICILOMBARD_HT_qGRIDVar");
					}
			}
			if(sCommandName.equalsIgnoreCase("Modify_row"))
			{
			 ////System.out.println("NGModifyRow");
			 	if(formObject.getNGValue("ICICILOMBARD_HT_CURRENT_PORTABILITY_SI").trim().length()==0)
					{
							JOptionPane.showMessageDialog(null,"Please Eneter Current Portability SI ");
					}
					else if(formObject.getNGValue("SubForm_Claim").trim().equalsIgnoreCase("--Select--"))
					{
							JOptionPane.showMessageDialog(null,"select Claim  ");
					}
					else if(formObject.getNGValue("SubForm_Claim").trim().equalsIgnoreCase("Yes") && (formObject.getNGValue("SubForm_Nature_of_Illness").trim().length()==0))
					{
							JOptionPane.showMessageDialog(null,"Please enter Nature of illness ");
					}
					else
					{
					formObject.ExecuteExternalCommand("NGModifyRow","ICICILOMBARD_HT_qGRIDVar");
					}
			}
			if(sCommandName.equalsIgnoreCase("Delete_row"))
			{
					////System.out.println("NGDeleteRow");
					formObject.ExecuteExternalCommand("NGDeleteRow","ICICILOMBARD_HT_qGRIDVar");
			}
		}
 // SubForm code
		if(lstrProcessName.equalsIgnoreCase("CPI"))
		{
			iciciLombard.set_CPI(formObject);
			return iciciLombard.executeCommand(strInputXml);
		}
		if(lstrProcessName.equalsIgnoreCase("ITT"))
		{
			iciciItt.set_ITT(formObject);
			return iciciItt.executeCommand(strInputXml);
		}
	//code added for HT process

		generalDataParser.setInputXML(formObject.getWFGeneralData());
		XMLParser xmlParser = new XMLParser(strInputXml);
		sCommandName = xmlParser.getValueOf("CommandName");
		////System.out.println("UserIndex---"+formObject.getWFGeneralData());
		////System.out.println("UserID---"+formObject.getUserId());
		if((lstrProcessName.equalsIgnoreCase("HT")))
		{
			if(lstrActivityName.equalsIgnoreCase("BSG_DATAENTRY") || lstrActivityName.equalsIgnoreCase("BSG_DATAENTRY_QC") || lstrActivityName.equalsIgnoreCase("exception"))
			{
				if(debug==1)
				{
					////System.out.println("in daya entey load");
				}

				ObjHealthTracker objHealthTracker = new ObjHealthTracker(formObject);
				if(debug==1)
				{
					////System.out.println("objHealthTracker"+objHealthTracker);
				}
				objHealthTracker.executeHTCommand(generalDataParser,strInputXml,sCommandName,appletContext);
				if(debug==1)
				{
					////System.out.println("sCommandName"+sCommandName);
				}
			}
		}

        /************************** CR-OF-MHT-1314-01 MHTProcess Implementaion Start***********/
        if(lstrProcessName.equalsIgnoreCase("MHT"))
        {
            if(!(lstrActivityName.equalsIgnoreCase("IPartner_PFSync") || lstrActivityName.equalsIgnoreCase("Complete_SYNC")))
            {
                if(debug == 1)
                {
                    ////System.out.println("executeCommand(): MHT Process Dataload");
                }
                ICICI_LOMBARD_MHT icici_lombard_mht = new ICICI_LOMBARD_MHT(formObject);
                if(debug==1)
                {
                    ////System.out.println("executeCommand(): MHT Process Object creation:"+icici_lombard_mht);
                }
                icici_lombard_mht.executeMHTCommand(generalDataParser, strInputXml, sCommandName, appletContext);
                if(debug == 1)
                {
                    ////System.out.println("executeCommand():MHT Process sCommandName:"+sCommandName);
                }
            }
        }

        /************************** CR-OF-MHT-1314-01 MHTProcess Implementaion End***********/


		//change ends here

		return "";

    }

    public void executeEvent(String strInputXml)
    {
	}

    public String fieldValueBagSet()
    {
		String lstrProcessName = formObject.getWFProcessName();
		String lstrActivityName = formObject.getWFActivityName();
		if(lstrProcessName.equalsIgnoreCase("CPI"))
		{

			return iciciLombard.fieldValueBagSet(lstrActivityName);

		}
		if(lstrProcessName.equalsIgnoreCase("ITT"))
		{

			return iciciItt.fieldValueBagSet(lstrActivityName);

		}

	 	//added for HT
		if(debug==1)
		{
			////System.out.println("fieldValueBagSet");
		}

		//////System.out.println("getWFGeneralData"+formObject.getWFGeneralData());
		generalDataParser.setInputXML(formObject.getWFGeneralData());

		//added for HT
		if(formObject.getNGSubForm()!=null)
		{
			////System.out.println("Subform in ng event handler");

			if((this.formObject.getWFActivityName().equalsIgnoreCase("BSG_DataEntry")))
			{

				formObject.setNGEnable("SubForm_Nature_of_Illness",false); // grid controls disable

			}
			if((this.formObject.getWFActivityName().equalsIgnoreCase("BSG_DataEntry_QC")|| this.formObject.		getWFActivityName().equalsIgnoreCase("Exception")||this.formObject.getWFActivityName().equalsIgnoreCase("COPS_Team")||this.formObject.getWFActivityName().equalsIgnoreCase("COPS_QC") //||this.formObject.getWFActivityName().equalsIgnoreCase("RMT") //CR25_Masters of KRG,Removal of fields & CR26 RMT Bucket
			) )
			{
					formObject.setNGEnable("FRM_GRID_CONTROLS",false); // grid controls disable
					formObject.setNGEnable("FRM_GRID_VIEW",false); // grid listvies frame disabled
			}
		}
		else
		{
			if(lstrProcessName.equalsIgnoreCase("HT"))
			{
					if(debug==1)
					{
							////System.out.println("fieldValueBagSet2");
					}

					ObjHealthTracker objHealthTracker= new ObjHealthTracker(formObject);
					objHealthTracker.executeHTOnload(generalDataParser);
			}
		}

		/************************** CR-OF-MHT-1314-01 MHTProcess Implementaion Start***********/
		if(lstrProcessName.equalsIgnoreCase("MHT"))
		{
			if(debug==1)
			{
				////System.out.println("ngfUser.fieldValueBagSet():MHT Process");
			}

		   ICICI_LOMBARD_MHT icici_lombard_mht = new ICICI_LOMBARD_MHT(formObject);
		   icici_lombard_mht.executeMHTOnload(generalDataParser);
		}
		/************************** CR-OF-MHT-1314-01 MHTProcess Implementaion End***********/
//changesd end here

        return null;
    }

    public void NGF_EventHandler(String pEventName, String pControlName, String pControlValue, String pReserved)
    {
		String lstrProcessName = formObject.getWFProcessName();
		if(formObject.getNGSubForm()!=null)
		{
		
		//	////System.out.println("Subform in ng event handler");
			if((this.formObject.getWFActivityName().equalsIgnoreCase("BSG_DataEntry")))
			{
					////System.out.println("1");
					////System.out.println(" paramString1::::"+pEventName);
					////System.out.println(" claim Value ::::"+formObject.getNGValue("SubForm_Claim"));
					/*if(paramString1.equals("LOAD"))
					{
							////System.out.println(" claim Value ::::"+formObject.getNGValue("SubForm_Claim"));
							////System.out.println("2");
							formObject.setNGEnable("SubForm_Nature_of_Illness",false); // grid controls disable
							////System.out.println("3");

					}*/

					/*if(paramString1.equals("CLICK") && formObject.getNGValue("SubForm_Claim").trim().equals("Yes"))
					{	////System.out.println("7");
							formObject.setNGEnable("SubForm_Nature_of_Illness",true);
							////System.out.println("8");

					}
					else
					{
					formObject.setNGEnable("SubForm_Nature_of_Illness",false);
					}*/
			//	if(paramString1.equals("GOTFOCUS") || paramString1.equals("CLICK") )
					//{	////System.out.println("6");
							/*if(paramString1.equals("CLICK") && formObject.getNGValue("SubForm_Claim").trim().equals("Yes"))
							{
									////System.out.println("7");
									formObject.setNGEnable("SubForm_Nature_of_Illness",true);
									////System.out.println("8");
							}
							else
							{
							formObject.setNGEnable("SubForm_Nature_of_Illness",false);
							}*/
					//}
					//else
					//{
					//formObject.setNGEnable("SubForm_Nature_of_Illness",false);
					//}


			}

			if((this.formObject.getWFActivityName().equalsIgnoreCase("BSG_DataEntry_QC")|| this.formObject.getWFActivityName().equalsIgnoreCase("Exception")||this.formObject.getWFActivityName().equalsIgnoreCase("COPS_Team")||this.formObject.getWFActivityName().equalsIgnoreCase("COPS_QC") //||this.formObject.getWFActivityName().equalsIgnoreCase("RMT") //CR25_Masters of KRG,Removal of fields & CR26 RMT Bucket 
			) )
			{
			formObject.setNGEnable("FRM_GRID_CONTROLS",false); // grid controls disable
			formObject.setNGEnable("FRM_GRID_VIEW",false); // grid listvies frame disabled
			}
		}
		else
		{
		//////System.out.println("Sub Form Object ----"+formObject.getNGSubForm());
		if(lstrProcessName.equalsIgnoreCase("HT"))
		{
                    generalDataParser.setInputXML(formObject.getWFGeneralData());
                    if(formObject.getNGSubForm() == null )
                    {
                        ObjHealthTracker objHealthTracker= new ObjHealthTracker(formObject);
                        String result=objHealthTracker.executeHTEvent(pEventName, pControlName, pControlValue, pReserved);
                    }
		}
		/************************** CR-OF-MHT-1314-01 MHTProcess Implementaion Start***********/
		if(lstrProcessName.equalsIgnoreCase("MHT"))
		{
			////System.out.println("ngfUser.NGF_EventHandler(-,-,-,-): eventname "+pEventName);
			generalDataParser.setInputXML(formObject.getWFGeneralData());
			ICICI_LOMBARD_MHT icici_lombard_mht = new ICICI_LOMBARD_MHT(formObject);
			String resultMHT= icici_lombard_mht.executeMHTEvent(pEventName, pControlName, pControlValue, pReserved);
		}
		/************************** CR-OF-MHT-1314-01 MHTProcess Implementaion End***********/
		if(lstrProcessName.equalsIgnoreCase("CPI"))
		{
			iciciLombard.set_CPI(formObject);
			iciciLombard.NGF_EventHandler(pEventName,pControlName,pControlValue,pReserved);

		}
		if(lstrProcessName.equalsIgnoreCase("ITT"))
		{
			iciciItt.set_ITT(formObject);
			iciciItt.NGF_EventHandler(pEventName,pControlName,pControlValue,pReserved);

		}

		}
    }

    public String NGF_GetMsgString(String s)
    {
        return null;
    }

    public int NGF_SaveNGFData(String pEventName)
    {
		////System.out.println("NGF_SaveNGFData CPI in ngfUser "+pEventName);
		String ActivityName = formObject.getWFActivityName();
		////System.out.println("ProcessName "+formObject.getWFProcessName());
		if (formObject.getWFProcessName().equalsIgnoreCase("CPI"))
		{
			////System.out.println("ProcessName "+formObject.getWFProcessName());
			if(iciciLombard.PostSaveNGFData(ActivityName,pEventName)==1)
				return 1;
			else
				return 0;
		}
		if (formObject.getWFProcessName().equalsIgnoreCase("ITT"))
		{

			if(iciciItt.PostSaveNGFData(ActivityName,pEventName)==1)
				return 1;
			else
				return 0;
		}
		//added for HT
		int result=0;
		if(debug==1)
		{
			////System.out.println("NGF_SaveNGFData in ngfUser  " + pEventName);
			////System.out.println("ProcessName " + this.formObject.getWFProcessName());
		}
		////System.out.println("ProcessName 1 "+formObject.getWFProcessName());
		//added for HT
		if(this.formObject.getWFProcessName().equalsIgnoreCase("HT"))
		{
		////System.out.println("ProcessName 2 "+formObject.getWFProcessName());
			Validation v=new Validation(formObject);
			generalDataParser.setInputXML(formObject.getWFGeneralData());
			wsName = generalDataParser.getValueOf("ActivityName").toUpperCase();
			wiName = generalDataParser.getValueOf("ProcessInstanceId").toUpperCase();

			result=v.MandatoryControls(pEventName,wsName,wiName);
			if(debug==1)
			{
				////System.out.println("Inside Validating Save/Done");
			}
			////System.out.println("ProcessName 3 "+formObject.getWFProcessName());
			return result;
			
		}
		/************************** CR-OF-MHT-1314-01 MHTProcess Implementaion Start***********/
		if(formObject.getWFProcessName().equalsIgnoreCase("MHT"))
		{
			////System.out.println("ngfUser.NGF_SaveNGFData():ProcessName "+formObject.getWFProcessName());
			ICICI_LOM_MHT_Validation icici_Mht_Validation = new ICICI_LOM_MHT_Validation(formObject);

			generalDataParser.setInputXML(formObject.getWFGeneralData());
			wsName = generalDataParser.getValueOf("ActivityName").toUpperCase();
			wiName = generalDataParser.getValueOf("ProcessInstanceId").toUpperCase();

			result = icici_Mht_Validation.mandatoryControls(pEventName, wsName, wiName);
			if(debug==1)
			{
				////System.out.println("ngfUser.NGF_SaveNGFData(): Save/Done");
			}
			return result;
		}
		/************************** CR-OF-MHT-1314-01 MHTProcess Implementaion End***********/
		//changes end here

	    return 1;
    }

	 /*  public void ngfUser_Validation(NGTextComponent paramNGTextComponent)
        throws Exception
    {

	String CName=paramNGTextComponent.getName();
	String CValue=paramNGTextComponent.getText() ;

	processName=this.formObject.getWFProcessName();

		if(this.formObject.getWFProcessName().equalsIgnoreCase("HT"))
		{
			Validation v=new Validation(formObject);

			v.ValidateTextControl(CName,CValue);
			if(debug==1)
			{
				////System.out.println("Inside Validating Lost Focus");
			}
		}
	}
	*/
	//method MODIFIED FOR HT PROCESS
   public void ngfUser_Validation(NGTextComponent paramNGTextComponent)
    {
		String CName=paramNGTextComponent.getName();
		String CValue=paramNGTextComponent.getText() ;

		processName=this.formObject.getWFProcessName();

		if(this.formObject.getWFProcessName().equalsIgnoreCase("HT"))
		{
			Validation v=new Validation(formObject);

			v.ValidateTextControl(CName,CValue);
			if(debug==1)
			{
				////System.out.println("Inside Validating Lost Focus");
			}
		}
		/************************** CR-OF-MHT-1314-01 MHTProcess Implementaion Start***********/
		if(formObject.getWFProcessName().equalsIgnoreCase("MHT"))
		{
			////System.out.println("ngfUser.ngfUser_Validation(paramNGTextComponent):ProcessName "+formObject.getWFProcessName());
			ICICI_LOM_MHT_Validation icici_Mht_Validation = new ICICI_LOM_MHT_Validation(formObject);

			//icici_Mht_Validation.validateTextControl(CName,CValue);
			if(debug==1)
			{
				////System.out.println("ngfUser.ngfUser_Validation(paramNGTextComponent): validateTextControl");
			}
			
		}
		/************************** CR-OF-MHT-1314-01 MHTProcess Implementaion End***********/
		
    }

    public boolean ngfUser_Validation(String pEvent)
    {
            //////System.out.println("ProcessName ngfUser_Validation "+formObject.getWFProcessName());
            String ActivityName = formObject.getWFActivityName();

            if (formObject.getWFProcessName().equalsIgnoreCase("CPI"))
            {
					////System.out.println("before calling my PostSaveNGFData ngfUser_Validation ");
					int m =iciciLombard.PostSaveNGFData(ActivityName,pEvent);
					
                    if(iciciLombard.NGF_SaveNGFData(ActivityName,pEvent)==1)
                            return true;
                    else
                            return false;
            }
            if (formObject.getWFProcessName().equalsIgnoreCase("ITT"))
            {
                    if(iciciItt.NGF_SaveNGFData(ActivityName,pEvent)==1)
                            return true;
                    else
                            return false;
            }
            //added for HT  code added by manoj for resolving mandatory popup issue
            if(this.formObject.getWFProcessName().equalsIgnoreCase("HT"))
            {
                    Validation v=new Validation(formObject);
                    generalDataParser.setInputXML(formObject.getWFGeneralData());
                    wsName = generalDataParser.getValueOf("ActivityName").toUpperCase();
                    wiName = generalDataParser.getValueOf("ProcessInstanceId").toUpperCase();

                    int result=v.MandatoryControls(pEvent,wsName,wiName);
                    if(debug==1)
                    {
                            ////System.out.println("Inside Validating Save/Done");
                    }
            if(result!=1)
            return false;

            }
            /************************** CR-OF-MHT-1314-01 MHTProcess Implementaion Start***********/
            if(formObject.getWFProcessName().equalsIgnoreCase("MHT"))
            {
                ////System.out.println("ngfUser.ngfUser_Validation(pEvent):ProcessName "+formObject.getWFProcessName());
                ICICI_LOM_MHT_Validation icici_Mht_Validation = new ICICI_LOM_MHT_Validation(formObject);

                 generalDataParser.setInputXML(formObject.getWFGeneralData());
                 wsName = generalDataParser.getValueOf("ActivityName").toUpperCase();
                 wiName = generalDataParser.getValueOf("ProcessInstanceId").toUpperCase();

                int resultMHT = icici_Mht_Validation.mandatoryControls(pEvent,wsName,wiName);
                if(debug==1)
                {
                    ////System.out.println("ngfUser.ngfUser_Validation(pEvent): validateTextControl");
                }
                ////System.out.println("ngfUser.ngfUser_Validation(pEvent): resultMHT"+resultMHT);
                if(resultMHT!=1)
                {
                    return false;
                }
            }
            /************************** CR-OF-MHT-1314-01 MHTProcess Implementaion End***********/
		
            //changes end here

            return true;

    }

    public void ngfUser_Validation(Vector vector)
    {
    }

}