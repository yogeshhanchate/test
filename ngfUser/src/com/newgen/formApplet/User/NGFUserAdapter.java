//----------------------------------------------------------------------------------------------------
//		NEWGEN SOFTWARE TECHNOLOGIES LIMITED
//Group						: Application - Products
//Product / Project			: Form Builder
//Module					: FormApplet
//File Name					: NGFUserAdapter.java
//Author					: Ashish Mangla
//Date written (DD/MM/YYYY)	: 20/06/2005
//Description				: For execution of user commands
//----------------------------------------------------------------------------------------------------
//			CHANGE HISTORY
//----------------------------------------------------------------------------------------------------
// Date				Change By		Change Description (Bug No. (If Any))
// (DD/MM/YYYY)
// 27/09/2005		Manish Kumar	Added for integration eith work Flow
// 12/05/2006		Ashish Mangla	fieldValueBagSet should be invoked after fieldValueBagset is called FBD_6.0.14_005
// 26/06/2006		Ashish Mangla	Support for script execution on events FBD_6.0.15_003
// 02/05/2007		Shweta Mangal	Support for ngfUser_Validation() (FBD_6.0.22)
// 06/12/2007		Vikas Tyagi		FBD_7.1.2 Changed the signature of NGF_SaveNGFData()
// 21/12/2007		Vikas Tyagi		FBD_7.1.3.1 Added a new polymorfic signature of NGF_SaveNGFData()
// 22/01/2008		Vikas Tyagi		FBD_7.1.5.2 Added function NGF_GetMsgString()
// 29/07/2008		Sadhana Sharma  FBD_7.1.10.1 Added function NGF_EventHandler();
//----------------------------------------------------------------------------------------------------
package com.newgen.formApplet.User;
import com.newgen.formApplet.Controls.InputVerifier.NGTextComponent;

public class NGFUserAdapter implements ngfUserInterface {

	public NGFPropInterface formObject = null;
	public Object appletObject = null;
	public boolean breadOnly;

	public	String executeCommand(String strInputXml){
		System.out.println("Inside executeCommand ");
		return "";
	}
	public	void executeEvent(String strInputXml){
		System.out.println("Inside executeEvent ");
		return;
	}

	public	void executeScript(String strInputXml)
	{
		System.out.println("Inside executeScript ");
		System.out.println("strInputXml  " + strInputXml);
		return;
	}

	public	void setAppletObject(Object appletObject)
	{
		this.appletObject = appletObject;
	}

	public	void setFormObject(NGFPropInterface formObject){
		this.formObject = formObject;
	}

	public void ReadOnly(boolean value){
		breadOnly=value;
	}

	public String fieldValueBagSet(){
		return null;
	}

	//   (FBD_6.0.22)
	public boolean ngfUser_Validation()
	{
		System.out.println("Inside Validating");
		return false;
	}
	public void ngfUser_Validation(NGTextComponent pControl)
	{
	System.out.println("Inside Validating Lost");
	}

	//FBD_7.1.2
	//public void NGF_SaveNGFData()
	public int NGF_SaveNGFData()
	{
		return 1;
	}

	//FBD_7.1.3.1
	public int NGF_SaveNGFData(String pEvent)
	{
		return 1;
	}

	//FBD_7.1.5.2
	public String NGF_GetMsgString(String pMessage)
	{
		return null;
	}

	//FBD_7.1.10.1
	public void NGF_EventHandler(String pEventName, String pControlName, String pControlValue, String pReserved)
	{ 
	}
}