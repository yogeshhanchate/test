//----------------------------------------------------------------------------------------------------
//		NEWGEN SOFTWARE TECHNOLOGIES LIMITED
//Group						: Application - Products
//Product / Project			: Form Builder
//Module					: FormApplet
//File Name					: NGFPropInterface.java
//Author					: Ashish Mangla
//Date written (DD/MM/YYYY)	: 15/06/2005
//Description				: For calling functions from ngfrmbld like NGValue 
//----------------------------------------------------------------------------------------------------
//			CHANGE HISTORY
//----------------------------------------------------------------------------------------------------
// Date			 Change By			Change Description (Bug No. (If Any))
// (DD/MM/YYYY)
// 27/10/2005   Manish Kumar		Added for FBD_6.0.4_006
// 12/05/2006	Ashish Mangla		Added function for FBD_6.0.14_003
// 01/05/2007   Shweta Mangal       Added function for FBD_6.0.22 
// 30/07/2007	Vikas Tyagi			Added function RaiseEvent(FBD_6.0.26_02)
// 22/08/2007	Vikas Tyagi			Added function NGLocalFloatFormat(),NGGlobalFloatFormat()(FBD_6.0.27_04)
// 30/8/2007    Shweta Mangal       Added Function  NGModifyListViewAt(), getNGListView()  (FBD_6.0.29)
// 04/10/2007   Vikas Tyagi			Added function  getNGLVWSelectedRows() setNGLVWSelectedRows()(FBD_6.0.33)
// 25/10/2007	Vikas Tyagi			Added function SetNGTextHorizontalAlignment(),NGApplyDigitGrouping()(FBD_6.0.36)
// 16/11/2007   Vikas Tyagi			FBD_6.0.37_04 Added Function getJSObject(), NGModifyColumnHeader() for modifying the header of the Listview columns and getFieldValueBag() for fieldvaluebag.
// 21/12/2007	Vikas Tyagi			FBD_7.1.3.1 Changes for enabling sorting in Listview
// 27/12/2007	Vikas Tyagi			FBD_7.1.4.1 Changes in the setCustomSort
// 09/01/2008	Vikas Tyagi			FBD_7.1.5.0 Added function ExecuteExternalCommand()
// 21/01/2008	Vikas Tyagi			FBD_7.1.5.2	Added function setNGAutoClear()
// 04/02/2008	Vikas Tyagi			FBD_7.1.5.4 Added function NGLabelHyperLink() for supporting hyperlinks.
// 29/02/2008	Vikas Tyagi			FBD_7.1.7.0 Added function setNGMultilineTab()
// 08/05/2008   Shweta Mangal       FBD_7.1.7.5 Added function ShowImage.
// 30/05/2008	Sadhana Sharma      FBD_7.1.9.0 For Listview formatting
// 05/06/2008	Sadhana Sharma		FBD_7.1.9.1 For making form Readonly at Readonly work-item
// 24/10/2008	Vikas Tyagi			FND_7.2.1 Added new methods print() and CreateTiff();
// 14/11/2008	Vikas Tyagi			FBD_7.1.10.4 Added method setNGControlFont()
// 28/01/2009	Vikas Tyagi			FBD_7.1.10.7 Added methods NGCreateZone(),NGMapZone(),NGRemoveTabSheet()
// 10/02/2009	Vikas Tyagi			FBD_7.2.1.1 Added new method setNGTabSelectionColor().
// 27/02/2009	Vikas Tyagi			FBD_7.2.1.2 Added new methods setNGDisableRoundOff(),removeNGTooltip(),getNGValue(),getNGDataFromDataSource() and setNGTabSelectionColor()
// 30/03/2009	Vikas Tyagi			FBD_7.2.1.3 Added new Methods setNGForeColor(),setNGBackColor(),setNGToolTip(),saveNGDataIntoDataSource(),getNGTextComponent(),getNGDataFromFile()
//									getWFBatchSize(),setNGTabVisible(),setNGColumnTotal(),setNGCharCase(),getNGSelectedDataFromDataSource()
// 14/04/2009	Vikas Tyagi			FBD_7.2.2.0 Added new methods getNGDataFromDataSource(),getNGPickList()
// 12/06/2009	Shweta Mangal		FBD_7.2.3.0 Added new method NGClearSelection and getNGDataFromDataCache()
// 17/07/2009	Vikas Tyagi 		FBD_7.2.4.0 Added new method setNGTabReadOnly(),printTemplate(),getNGSubForm(),setNGColumnWidth(),getWFAssignmentType() and setNGComboEditable()
//----------------------------------------------------------------------------------------------------
package com.newgen.formApplet.User;

import java.util.*;
import java.awt.Color;
//FBD_6.0.37_04
import netscape.javascript.JSObject;
import com.newgen.formApplet.Controls.NGRepeater;
import com.newgen.formApplet.Controls.InputVerifier.NGTextComponent;
//FBD_7.2.2.0
import com.newgen.formApplet.*;
import java.awt.LayoutManager;

public interface NGFPropInterface
{
        public String getWFServerUrl();
        public String getWFSessionId();
        public String getWFEngineName();
        public String getWFDBType();
        public String getWFActivityId();
        public void sortNGListview(String pControl,int pColIndex,boolean pAscending);
        public void sortNGListview(String pControl,String pColumn,boolean pAscending);
        public void setNGSelectionMode(String pControl,char pSelMode);
        public void printScreen();        
        public void printNGContainer(String pControl);
        public void printNGContainer(String pControl,boolean pSelectedTab);
        public void setNGInputPattern(String pCtrlName,String pPattern);
        public void fetchFragment(String pFrameName,String pFragmentName,String pField);
        public void setNGMandatoryControl(String pControlName,boolean pValue);
        public void setNGFrameLayout(String pFameName,LayoutManager pLayout);
        public void exportAsPDF();
        public String getFieldValueBagEx();
	//FBD_7.2.4.1
	public void setNGFocusTraversalKeys(int pDirection,int pKeyCode, int pModifiers);
	public void setNGSelectedIndices(String pControlName, int[] pIndices);
	public NGRepeater getNGRepeater(String pCtrlName);
	public String getNGItemText(String pCtrlName, int pIndex);
	public String getNGSelectedItemText(String pCtrlName);
	public void NGAddItem(String pCtrlName, String pItemText, String pItemValue);	
	public void setGlobalDateFormat(String pDateFormat);
        public void NGAddDefaultItem(String sControlName, String value);
	public int[] getNGSelectedIndices(String pControlName);
	public String[] getNGSelectedValues(String pControlName);
	//FBD_7.2.4.0
	public void setNGTabReadOnly(String pControlName, int pTabSheet, boolean pValue);
	public void setNGComboEditable(String pControlName, boolean pValue);
	public void printTemplate(String strTemplatePath, String pParamCtrlMap);
	public void printTemplate(String strTemplatePath, String pParamCtrlMap, String pParams);
	public SubForm getNGSubForm(String pFolderName, String pFileName);
	public SubForm getNGSubForm(String pFolderName, String pFileName, String pFormData);
	public void setNGColumnWidth(String pControlName, int pColIndex, int pWidth);
	public String getWFAssignmentType();
	//FBD_7.2.3.0
	public void NGClearSelection(String pControlName);
	public ArrayList getNGDataFromDataCache(String pQuery, int pColumnCount);
	public void getNGDataFromDataCache(String pQuery, int pColumnCount, String pControls);
	public void getNGDataFromDataCache(String pQuery, int pColumnCount, String pListView, String pColSequence);
	//FBD_7.2.2.0
	public ArrayList getNGDataFromDataSource(String pQuery, int pColumnCount);
	public NGPickList getNGPickList(String pParentCtrl, String pHeaders, boolean pBatchingReq);
	//FBD_7.2.1.3
	public void setNGForeColor(String controlName, Color pColor);
	public void setNGBackColor(String controlName, Color pColor);
	public void setNGToolTip(String pListView, int pRow, int pCol, String pText);
	public boolean saveNGDataIntoDataSource(String pQuery);
	public boolean saveNGDataIntoDataSource(ArrayList pQueries);
	public boolean saveNGDataIntoDataSource(String pControls, String pTable, String pColumns, String pCondition);
	public NGTextComponent getNGTextComponent(String pControl);
	public void getNGDataFromFile(String pControls);
	public int getWFBatchSize();
	public void setNGTabVisible(String controlName, int tabSheet, boolean value);
	public void setNGColumnTotal(String pListview, int pColumn, String pControl);
	public void setNGCharCase(String pControl, char pCase);
	public void getNGSelectedDataFromDataSource(String pQuery, int pColumnCount, String pColumn, String pHeaders, String pControls);
	//FBD_7.2.1.2
	public String getNGValue(String pListview, int pRow, int pCol);
	public void getNGDataFromDataSource(String pQuery, int pColumnCount, String pListView, String pColSequence);
	public void getNGDataFromDataSource(String pQuery, int pColumnCount, String pControls);
	public void setNGDisableRoundOff(boolean pValue);
	public void removeNGTooltip();
	public void setNGTabSelectionColor(String controlName, Color pBackCol,Color pForeCol);
	//FBD_7.2.1.1
	public void setNGTabSelectionColor(String controlName, Color pBackCol);
	//FBD_7.1.10.7
	public void NGRemoveTabSheet(String sControlName, String sListofSheetsToHide);
	public void NGMapZones(String pContrls, String pZones);
	public void NGCreateZone(String pZoneName, String pCords);
	public void NGCreateZone(String pZoneName, int pLeft, int pTop, int pWidth, int pHeight);
	//FBD_7.2.1
	public void print();
	//FBD_7.2.1
	public void CreateTiff(String pFileName);	
	//FBD_7.1.10.5
	public void setNGControlCaption(String sControlName, String sCaption);
	//FBD_7.1.10.5
	public String CurrentControlName();
	//FBD_7.1.10.4
	public void setNGFont(String pFontName);
	//FBD_7.1.10.4
	public void setNGFont(String pFontName, int pFontSize);
	//FBD_7.1.10.4
	public void setNGFont(String pFontName, int pFontSize, boolean pBold, boolean pItalic);
	//FBD_7.1.10.4
	public void setNGControlFont(String pCtrlName, String pFontName, int pFontSize);
	//FBD_7.1.10.4
	public void setNGControlFont(String pCtrlName, String pFontName, int pFontSize, boolean pBold, boolean pItalic);
    //FBD_7.1.9.1
    public void NGMakeFormReadOnly();
    //FBD_7.1.9.0
    public void setNGLVWRowHeight(String sControlName, int pHeight);
    //FBD_7.1.9.0
    public int getNGLVWRowHeight(String sControlName );
	//FBD_7.1.7.5
	public void ShowImage(String sControlName,String sPicturePath);
    //FBD_7.1.7.0
    public void setNGMultilineTab(String pCtrlName, boolean pValue);
    //FBD_7.1.5.4
    public void NGLabelHyperLink(String pCtrlName, String pLinkedUrl);
    //FBD_7.1.5.4
    public void NGLabelHyperLink(String pCtrlName, String pLinkedUrl, String pMethodType);
    //FBD_7.1.5.2
	public void setNGAutoClear(boolean pClear);
	//FBD_7.1.5.0
	public void ExecuteExternalCommand(String pCommandName, String pControlName);
	//FBD_7.1.4.1
	public void setNGLVWCustomSort(String pCtrlName, boolean pValue, String pColName, String pOrder);
	//FBD_7.1.3.1
	public void setNGLVWCustomSort(String pCtrlName, boolean pValue);
	//FBD_7.1.3.1
	public void setNGLVWAutoSort(String pCtrlName, boolean pValue);
	//FBD_6.0.37_04
	public JSObject getJSObject();
	//FBD_6.0.37_04
	public String getFieldValueBag();
	//FBD_6.0.37_04
	public void NGModifyColumnHeader(String pCtrl, int iColumnIndex, String strNewName);
	//FBD_6.0.36
	public void SetNGTextHorizontalAlignment(String pControlName, String pAlignment);
	//FBD_6.0.36
	public void NGApplyDigitGrouping(String pControlName, boolean pValue);
	//FBD_6.0.33
	public int[] getNGLVWSelectedRows(String sControlName);
	//FBD_6.0.33
	public void setNGLVWSelectedRows(String sControlName, int[] value);
	//FBD_6.0.29
	public void NGModifyListViewAt(String sControlName,int index,String sXMLString);
	//FBD_6.0.29
	public String getNGListView(String sControlName);
	//FBD_6.0.27_04
	public void NGLocalFloatFormat(String controlName, int iTotalDigitsInDec, int iDigitAfterDec);
	//FBD_6.0.27_04
	public void NGGlobalFloatFormat(int iTotalDigitsInDec, int iDigitAfterDec);

	//FBD_6.0.26_02
	public void RaiseEvent(String pEvent);
	//FBD_6.0.23_02
	public void setNGToolTip(String controlName, String value);
	//FBD_6.0.23_02
	public void setSpecificDateFormat(String controlName, String value);

	public void setNGValue(String controlName, String value);
	public String getNGValue(String controlName);
	public String getNGValueAt(String controlName, int index);

	public void setNGEnable(String controlName, boolean value);
	public boolean isNGEnable(String controlName);

	public void setNGVisible(String controlName, boolean value);
	public boolean isNGVisible(String controlName);

	public void setNGTabCaption(String controlName, int tabSheet, String value);
	public String getNGTabCaption(String controlName, int tabSheet);


	public void setNGSelectedTab(String controlName, int value);
	public int getNGSelectedTab(String controlName);

	public void setNGListIndex(String controlName, int value);
	public int getNGListIndex(String controlName);

	public int getNGListCount(String controlName);
	public void NGAddColumnHeader(String controlName, String ColumnText);
	public void NGAddColumnHeader(String controlName, String ColumnText, int width);
	public void NGAddColumnHeader(String controlName, String ColumnText, int width,int iTextAlign);
	public void NGAddListItem(String controlName, String sListData);



	public void setNGLocked(String controlName, boolean value);
	public boolean isNGLocked(String sControlName);

	public void setNGBackColor(String controlName, int value);
	public int getNGBackColor(String controlName);

	public void setNGForeColor(String controlName, int value);
	public int getNGForeColor(String controlName);	

	public void setNGTabEnable(String controlName, int tabSheet, boolean value);
	public boolean isNGTabEnable(String controlName, int tabSheet);

	public void NGAddItem(String controlName, String Item);
	public void NGRemoveItemAt(String controlName, int index);
	public void NGClear(String controlName);

	public void NGClearColumnHeader(String controlName);
	public void NGFocus(String controlName);

	public Vector getNGColumnHeaderNames(String sControlName);     //Added for FBD_6.0.4_006

	public String getUserName();
	public String getWindowTitle();
	public int getUserId();

	public String getWFProcessName();
	public String getWFActivityName();
	public String getWFGeneralData();
	public String getDateFormat();
	public int getWFFolderId();

	//(FBD_6.0.22)Added for setting control's height,width,top,left coordinate from NGHfUser.java file.  
	public int getNGControlLeft(String sControlName);
	public void setNGControlLeft(String sControlName,int value);
	public int getNGControlTop(String sControlName);
	public void setNGControlTop(String sControlName,int value);
	public int getNGControlWidth(String sControlName);
	public void setNGControlWidth(String sControlName,int value);
	public int getNGControlHeight(String sControlName);
	public void setNGControlHeight(String sControlName,int value);
	public void setNGFormWidth(int value);
	public void setNGFormHeight(int value);
	public int getNGFormHeight();
	public int getNGFormWidth();

	public void setFieldValueBag(String fieldValueBag);
	public void setSpecificTimeFormat(String controlName, String value);
	public void NGAddBoolItem(String strCtrlName,String[] strListItemArray);
	
	//*********** SUB FORM ********************  by onkar
	
	//public String getFieldValueBagEx(); 
	//public void setFieldValueBag(String fieldValueBag); 
	//public NGFPropInterface getParentFormObject();
	//public SubForm getNGSubForm(String pFolderName,String pFileName,String pFormData);
	//public SubForm getNGSubForm(String pFolderName, String pFileName); 
	public SubForm getNGSubForm(); 
	//*********** SUB FORM ********************  
}