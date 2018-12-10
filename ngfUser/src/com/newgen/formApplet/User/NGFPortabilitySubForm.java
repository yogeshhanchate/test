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
// import to perform operation with  Sub Form
import com.newgen.formApplet.SubForm;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class NGFPortabilitySubForm{


int gridRows;
String strDataXml="";
SubForm objSubForm = null;
private NGFPropInterface formObject = null;
public NGFPortabilitySubForm(NGFPropInterface formObject)//formObject Mandatory
	{
		this.formObject = formObject;
	}


public void portabilityMatrixSubForm(String pEventName, String pControlName, String pControlValue, String pReserved,String ActivityName)
{

		try{
			System.out.println("inside portabilityMatrixSubForm method :");
			String wsName = ActivityName;
			System.out.println("wsName :"+wsName);
			System.out.println("pEventName :"+pEventName);
			System.out.println("pControlName :"+pControlName);
			System.out.println("pControlValue :"+pControlValue);
			System.out.println("pReserved :"+pReserved);
			
			// Counting No of Rows in Grid ICICILOMBARD_HT_qGRIDVar
			gridRows = formObject.getNGListCount("ICICILOMBARD_HT_qGRIDVar");
			System.out.println("No of Rows in Grid Control in main Form :"+gridRows);
			
			
			if(wsName.equals("BSG_DataEntry") )
			{
			
				formObject.setNGEnable("BTN_PRT_POPUP", true); // portability button is visible for transction type = "Rollover"
				if(gridRows == 0)
				{
					if( pControlName.equalsIgnoreCase("BTN_PRT_POPUP") && pEventName.equalsIgnoreCase("click"))
					{		
						
					    /*Create SubForm with providing folder name and form name with data xml.
						 *According to parameters,physical location of this subform is;
						 * webdesktop.war/webtop/applet/SubForms/NGFPortabilitySubForm.ngf
						 */
						 final NGFPropInterface objParFormObject = formObject;						 
							objSubForm = formObject.getNGSubForm("SubForms", "NGFPortabilitySubForm.ngf");
							System.out.println("objSubForm ::"+objSubForm);
							objSubForm.setModal(false);
							final SubForm objSub = objSubForm ;
							
							
							//formObject.setFieldValueBag(objSubForm.getFieldValueBag());  // setting Sub form grid data on main form grid 
							objSubForm.addWindowListener(new WindowAdapter()
							{
									public void windowClosing(WindowEvent e)
									{
										System.out.println("Grid data in sub form "+objSub.getFieldValueBag());
										objParFormObject.setFieldValueBag(objSub.getFieldValueBag());
									}
							});
							objSubForm.setVisible(true); 		// sub form is visible
					
					}
				}
				else
				{
					System.out.println("NUM Rows in  else condition ::"+gridRows);
					if((gridRows > 0) && pControlName.equalsIgnoreCase("BTN_PRT_POPUP") && pEventName.equalsIgnoreCase("click"))
					{ 
						//Get Curent Data from main form.
						strDataXml = formObject.getFieldValueBagEx();
						System.out.println("strDataXml  in else part ::"+strDataXml);
						
						/*Create SubForm with providing folder name and form name with data xml.
						 *According to parameters,physical location of this subform is;
						 * webdesktop.war/webtop/applet/SubForms/NGFPortabilitySubForm.ngf
						 */
						 	
						 final NGFPropInterface objParFormObject = formObject;		
							objSubForm = formObject.getNGSubForm("SubForms", "NGFPortabilitySubForm.ngf",strDataXml);	
							objSubForm.setModal(false);
							final SubForm objSub = objSubForm ;
							
							 objSubForm.addWindowListener(new WindowAdapter()
							{
									public void windowClosing(WindowEvent e)
									{
										//System.out.println("Grid data in sub form "+objSub.getFieldValueBag());
										objParFormObject.setFieldValueBag(objSub.getFieldValueBag());
									}
							});
							objSubForm.setVisible(true); 		// sub form is visible
					}
				}
				
			}	
			
			
			
			if(wsName.equals("BSG_DataEntry_QC")|| wsName.equals("Exception")||wsName.equals("COPS_Team")||wsName.equals("COPS_QC"))   //||wsName.equals("RMT") //CR25_Masters of KRG,Removal of fields & CR26 RMT Bucket
			{
			  
				
				if(gridRows == 0)
				{
					if( pControlName.equalsIgnoreCase("BTN_PRT_POPUP") && pEventName.equalsIgnoreCase("click"))
					{		
						System.out.println(" Frame disable");
					    /*Create SubForm with providing folder name and form name with data xml.
						 *According to parameters,physical location of this subform is;
						 * webdesktop.war/webtop/applet/SubForms/NGFPortabilitySubForm.ngf
						 */
						 
							objSubForm = formObject.getNGSubForm("SubForms", "NGFPortabilitySubForm.ngf");
							System.out.println("objSubForm ::"+objSubForm);
			//				formObject.setNGEnable("FRM_GRID_CONTROLS",false); // grid controls disable
			//				formObject.setNGEnable("FRM_GRID_VIEW",false); // grid listvies frame disabled
			
			
							objSubForm.setVisible(true); 			// sub form is visible					
					}
				}
				else
				{
					System.out.println("NUM Rows in  else condition ::"+gridRows);
					if((gridRows > 0) && pControlName.equalsIgnoreCase("BTN_PRT_POPUP") && pEventName.equalsIgnoreCase("click"))
					{
						System.out.println(" Frame disable in else part");

						//Get Curent Data from main form.
						strDataXml = formObject.getFieldValueBagEx();
						System.out.println("strDataXml  in else part ::"+strDataXml);
						
						/*Create SubForm with providing folder name and form name with data xml.
						 *According to parameters,physical location of this subform is;
						 * webdesktop.war/webtop/applet/SubForms/NGFPortabilitySubForm.ngf
						 */
						
						objSubForm = formObject.getNGSubForm("SubForms", "NGFPortabilitySubForm.ngf",strDataXml);
				//		formObject.setNGEnable("FRM_GRID_CONTROLS",false);  // grid controls disable
				//		formObject.setNGEnable("FRM_GRID_VIEW",false); // grid listvies frame disabled
				
						objSubForm.setVisible(true);				// sub form is visible
						
						
					}
				}
				
			}
		
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
}


} 