//----------------------------------------------------------------------------------------------------
//		NEWGEN SOFTWARE TECHNOLOGIES LIMITED
//Group						: Application - Products
//Product / Project			: Form Builder
//Module					: FormApplet
//File Name					: ngfUserInterface.java
//Author					: Ashish Mangla
//Date written (DD/MM/YYYY)	: 02/06/2003
//Description				: For execution of user commands
//----------------------------------------------------------------------------------------------------
//			CHANGE HISTORY
//----------------------------------------------------------------------------------------------------
// Date				Change By		Change Description (Bug No. (If Any))
// (DD/MM/YYYY)
// 	12/05/2006		Ashish Mangla	fieldValueBagSet should be invoked after fieldValueBagset is called FBD_6.0.14_005
//  23/06/2006		Ashish Mangla	Support for script execution on events FBD_6.0.15_003
//  01/05/2007		Shweta Mangal	Added for validating the controls before the click of DONE button. (FBD_6.0.22)
//  28/11/2007		Vikas Tyagi		FBD_7.1.1 added function SaveData() for saving custom data.
//	06/12/2007		Vikas Tyagi		FBD_7.1.2 Changed the signature of NGF_SaveNGFData()
//  21/12/2007		Vikas Tyagi		FBD_7.1.3.1 Added a new polymorfic signature of NGF_SaveNGFData()
//	22/01/2008		Vikas Tyagi		FBD_7.1.5.2 Added function NGF_GetMsgString()
//  29/07/2008		Sadhana Sharma  FBD_7.1.10.1 Added function NGF_EventHandler();
//----------------------------------------------------------------------------------------------------
package com.newgen.formApplet.User;

import com.newgen.formApplet.Controls.InputVerifier.NGTextComponent;
public interface ngfUserInterface
{
	public String executeCommand(String strInputXml);
	public void executeScript(String strScript);		//FBD_6.0.15_003
	public void executeEvent(String strInputXml);

     //   public void ProcessFocusLostEvent(String strLostFocusXML);
	public void setAppletObject(Object appletObject);
	public void setFormObject(NGFPropInterface appletObject);
	public String fieldValueBagSet();
	public boolean ngfUser_Validation(); //(FBD_6.0.22)
	//public void NGF_SaveNGFData();//FBD_7.1.1
	public int NGF_SaveNGFData();//FBD_7.1.2
	public int NGF_SaveNGFData(String pEvent);//FBD_7.1.3.1
	public String NGF_GetMsgString(String pMessage);//FBD_7.1.5.2
	//FBD_7.1.10.1	
	public void NGF_EventHandler(String pEventName,String pControlName,String pControlValue ,String pReserved);
	public void ngfUser_Validation(NGTextComponent pControl); // Added By Manoj Jain
}


