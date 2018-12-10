/************************************************************************************************************************
 * Project          : iBank
 * Application      : OmniFlow Registry System
 * Module           : ngfUser
 * File             : ICICIPickList.java
 * Author           : Alok Sharma
 * Purpose          : Contains the custom code to customize the NGPicklist.
 * Date             : 13/07/2009
 * ---------------------------------------------------------------------------------------------------------------------
 * CHANGE HISTORY
 * ---------------------------------------------------------------------------------------------------------------------
 * Date (DD/MM/YYYY)    Changed By           Description
 * -----------------   ----------------     ----------------------------------------------------------------------------
 ************************************************************************************************************************/
package com.newgen.formApplet.User;

import com.newgen.formApplet.*;
import com.newgen.formApplet.event.*;
import java.util.ArrayList;

public class ICICIPickList extends NGPickListAdapter
{

    int _iBatchNo = 1;
    int _iBatchSize = 10;
    String _strQuery = "";
    int TotalRows;
    String strCtrlID_1 = "";
    String strCtrlID_2 = "";
    String strSearchVal = "";
    String strSource = "";
    XMLParser objProcessData = null;
    String strUserName = "";

    private static final String CLG_USER_LIST = "CLG_USER_LIST";
    private static final String IWF_CITY_LIST = "IWF_CITY_LIST";
    private static final String CLG_COST_CENTER = "CLG_COST_CENTER";
    private static final String NRI_PREF_BRANCH = "NRI_PREF_BRANCH";
    private static final String NRI_ORG_BRANCH = "BRANCH";
	  //-------- For InfoPool Process
    private static final String INFO_CITY_LIST  =    "INFO_CITY_LIST";
    private static final String INFO_STATE_LIST =    "INFO_STATE_LIST";
    private static final String INFO_COUNTRY_LIST =  "INFO_COUNTRY_LIST";
    private static final String TF_BRANCH_LIST =  "TF_BRANCH";
    private static final String  TF_PRODUCT_LIST="TF_PRODUCT";
private static final String TF_CLIENT_LIST =  "TF_CLIENT";

    private NGFPropInterface formObject = null;

    /**
     * Constructor for intializing picklist for suburb/portcode selection
     * @param pObj Insatnce of formObject
     * @param totalRows The total number of rows present for current criteria (Sum of all batches present)
     * @param strSource The Source from where the picklist has been called
     * @param strSuburb The value of the search string for searching suburb/postcode
     * @param strCtrlSuburb The control where the value of Suburb is to be populated on selection
     * @param strCtrlPostcode The control where the value of Postcode is to be populated on selection
     */
    public ICICIPickList(NGFPropInterface pObj, int totalRows, String strSource, String strCtrlFPRID)
    {
        this.formObject = pObj;
        TotalRows = totalRows;
        this.strSource = strSource;
        this.strCtrlID_1 = strCtrlFPRID;
    }
    // New Constructor added for IWF Picklist

    public ICICIPickList(NGFPropInterface pObj, int totalRows, String strSource, String strCtrlID_1, String strCtrlID_2, String strSearchVal)
    {
        this.formObject = pObj;
        TotalRows = totalRows;
        this.strSource = strSource;
        this.strCtrlID_1 = strCtrlID_1.trim();
        this.strCtrlID_2 = strCtrlID_2.trim();
        this.strSearchVal = strSearchVal.trim();
    }
    // New Constructor added for CLG COST CENTER Picklist

    public ICICIPickList(NGFPropInterface pObj, int totalRows, String strSource, String strCtrlID_1, String strSearchVal)
    {
         objProcessData = new XMLParser();

        this.formObject = pObj;
        TotalRows = totalRows;
        this.strSource = strSource;
        this.strCtrlID_1 = strCtrlID_1.trim();
        this.strSearchVal = strSearchVal.trim();
        //	String strCasetype = formObject.getNGValue("CLG_CASETYPE");
    }

    /**
     * Loads the picklist when browse button is clicked.
     * @param e The event captured by the listener.
     */
    public void NGPickList_Loading(NGEvent e)
    {
        NGPickList objPickList = (NGPickList) e.getSource();
		objProcessData = new XMLParser();
        objProcessData.setInputXML(formObject.getWFGeneralData());
        strUserName= objProcessData.getValueOf("UserName");

        if (strSource != null && strSource.equals(CLG_USER_LIST))
        {
            _strQuery = "SELECT FPRID, FPRNAME FROM (SELECT FPRID, FPRNAME FROM CLG_FPRESCALATIONMASTER WHERE STATUS = 'A' ORDER BY FPRID) WHERE ROWNUM <= " + _iBatchSize;

            if (objPickList.populateData(_strQuery, 2))
            {
                if (((_iBatchNo * _iBatchSize) + 1) > TotalRows && TotalRows > ((_iBatchNo - 1) * _iBatchSize))
                    objPickList.enableButton("Next", false);
                else
                    objPickList.enableButton("Next", true);

                objPickList.enableButton("Previous", false);
            }

            if ((_iBatchNo * _iBatchSize) > TotalRows)
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + TotalRows + " of " + TotalRows);
            else
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + ((_iBatchNo * _iBatchSize)) + " of " + TotalRows);
        }
        else if (strSource != null && strSource.equals(IWF_CITY_LIST))
        {
            _strQuery = "SELECT CIT_RECORD_ID,CITY_NAME FROM (SELECT CIT_RECORD_ID,CITY_NAME FROM IWF_MASTER_CITY  WHERE STATUS = 'A' AND UPPER(CITY_NAME) LIKE UPPER('" + strSearchVal + "%') ORDER BY CIT_RECORD_ID) WHERE ROWNUM <= " + _iBatchSize;

            if (objPickList.populateData(_strQuery, 2))
            {
                if (((_iBatchNo * _iBatchSize) + 1) > TotalRows && TotalRows > ((_iBatchNo - 1) * _iBatchSize))
                    objPickList.enableButton("Next", false);
                else
                    objPickList.enableButton("Next", true);

                objPickList.enableButton("Previous", false);
            }

            if ((_iBatchNo * _iBatchSize) > TotalRows)
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + TotalRows + " of " + TotalRows);
            else
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + ((_iBatchNo * _iBatchSize)) + " of " + TotalRows);
        }
//CLG_COST_CENTER
        else if (strSource != null && strSource.equals(CLG_COST_CENTER))
        {
            // _strQuery = "SELECT CIT_RECORD_ID,CITY_NAME FROM (SELECT CIT_RECORD_ID,CITY_NAME FROM IWF_MASTER_CITY  WHERE STATUS = 'A' AND UPPER(CITY_NAME) LIKE UPPER('"+strSearchVal+"%') ORDER BY CIT_RECORD_ID) WHERE ROWNUM <= " + _iBatchSize;
            String strCasetype = formObject.getNGValue("CLG_CASETYPE");
            _strQuery = "SELECT COSTCENTERNAME FROM (SELECT C.COSTCENTERNAME FROM CLG_COSTCENTERMASTER C, CLG_CASETYPEMASTER T  WHERE C.CASETYPE = T.CASECODE AND C.STATUS = 'A' AND T.CASENAME='" + strCasetype + "'  AND UPPER(C.COSTCENTERNAME) LIKE UPPER('" + strSearchVal + "%') ORDER BY C.COSTCENTERNAME) WHERE ROWNUM <=  " + _iBatchSize;
            // System.out.println("********************Load query ************* " + _strQuery);
            if (objPickList.populateData(_strQuery, 1))
            {
                if (((_iBatchNo * _iBatchSize) + 1) > TotalRows && TotalRows > ((_iBatchNo - 1) * _iBatchSize))
                    objPickList.enableButton("Next", false);
                else
                    objPickList.enableButton("Next", true);

                objPickList.enableButton("Previous", false);
            }

            if ((_iBatchNo * _iBatchSize) > TotalRows)
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + TotalRows + " of " + TotalRows);
            else
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + ((_iBatchNo * _iBatchSize)) + " of " + TotalRows);
        }
        else if(strSource != null && strSource.equals(NRI_PREF_BRANCH))
        {
            _strQuery = "SELECT BRANCHCODE,BRANCHNAME FROM (SELECT distinct BRANCHCODE,BRANCHNAME FROM NRI_BRANCHMASTER WHERE STATUS = 'A' AND UPPER(BRANCHNAME) LIKE UPPER('" + strSearchVal + "%') ORDER BY BRANCHNAME) WHERE ROWNUM <= " + _iBatchSize;

            // System.out.println("********************Load query ************* " + _strQuery);
            if (objPickList.populateData(_strQuery, 2))
            {
                if (((_iBatchNo * _iBatchSize) + 1) > TotalRows && TotalRows > ((_iBatchNo - 1) * _iBatchSize))
                    objPickList.enableButton("Next", false);
                else
                    objPickList.enableButton("Next", true);

                objPickList.enableButton("Previous", false);
            }

            if ((_iBatchNo * _iBatchSize) > TotalRows)
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + TotalRows + " of " + TotalRows);
            else
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + ((_iBatchNo * _iBatchSize)) + " of " + TotalRows);
        }
        else if(strSource != null && strSource.equals(NRI_ORG_BRANCH))
        {
            _strQuery = "select branch from NG_CPI_BRANCH_MASTER where PRIMARY_SUB_VERTICAL_ID=1" + " AND UPPER(BRANCH) LIKE UPPER('" + strSearchVal + "%') and ROWNUM <= " + _iBatchSize+"  ORDER BY BRANCH";

            // System.out.println("********************Load query *************: " + _strQuery);
            if (objPickList.populateData(_strQuery, 2))
            {
                if (((_iBatchNo * _iBatchSize) + 1) > TotalRows && TotalRows > ((_iBatchNo - 1) * _iBatchSize))
                    objPickList.enableButton("Next", false);
                else
                    objPickList.enableButton("Next", true);

                objPickList.enableButton("Previous", false);
            }

            if ((_iBatchNo * _iBatchSize) > TotalRows)
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + TotalRows + " of " + TotalRows);
            else
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + ((_iBatchNo * _iBatchSize)) + " of " + TotalRows);
        }
		else if(strSource != null && strSource.equals(INFO_CITY_LIST))
        {
            _strQuery = "SELECT CIT_RECORD_ID,CITY_NAME FROM (SELECT CIT_RECORD_ID,CITY_NAME FROM AOT_MASTER_CITY WHERE STATUS = 'A' AND UPPER(CITY_NAME) LIKE UPPER('" + strSearchVal + "%') ORDER BY CIT_RECORD_ID) WHERE ROWNUM <= " + _iBatchSize;

            // System.out.println("********************Load query ************* " + _strQuery);
            if (objPickList.populateData(_strQuery, 2))
            {
                if (((_iBatchNo * _iBatchSize) + 1) > TotalRows && TotalRows > ((_iBatchNo - 1) * _iBatchSize))
                    objPickList.enableButton("Next", false);
                else
                    objPickList.enableButton("Next", true);

                objPickList.enableButton("Previous", false);
            }

            if ((_iBatchNo * _iBatchSize) > TotalRows)
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + TotalRows + " of " + TotalRows);
            else
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + ((_iBatchNo * _iBatchSize)) + " of " + TotalRows);
        }
         else if(strSource != null && strSource.equals(INFO_STATE_LIST))
        {
            _strQuery = "SELECT STA_RECORD_ID,STATE_NAME FROM (SELECT STA_RECORD_ID,STATE_NAME FROM AOT_MASTER_STATE WHERE STATUS = 'A' AND UPPER(STATE_NAME) LIKE UPPER('" + strSearchVal + "%') ORDER BY STA_RECORD_ID) WHERE ROWNUM <= " + _iBatchSize;

            // System.out.println("********************Load query ************* " + _strQuery);
            if (objPickList.populateData(_strQuery, 2))
            {
                if (((_iBatchNo * _iBatchSize) + 1) > TotalRows && TotalRows > ((_iBatchNo - 1) * _iBatchSize))
                    objPickList.enableButton("Next", false);
                else
                    objPickList.enableButton("Next", true);

                objPickList.enableButton("Previous", false);
            }

            if ((_iBatchNo * _iBatchSize) > TotalRows)
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + TotalRows + " of " + TotalRows);
            else
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + ((_iBatchNo * _iBatchSize)) + " of " + TotalRows);
        }
         else if(strSource != null && strSource.equals(INFO_COUNTRY_LIST))
        {
            _strQuery = "SELECT CM_COUNTRY_CODE,CM_COUNTRY_DESC FROM (SELECT CM_COUNTRY_CODE,CM_COUNTRY_DESC FROM INFO_MASTER_COUNTRY WHERE STATUS = 'A' AND UPPER(CM_COUNTRY_DESC) LIKE UPPER('" + strSearchVal + "%') ORDER BY CM_COUNTRY_CODE) WHERE ROWNUM <= " + _iBatchSize;

            // System.out.println("********************Load query ************* " + _strQuery);
            if (objPickList.populateData(_strQuery, 2))
            {
                if (((_iBatchNo * _iBatchSize) + 1) > TotalRows && TotalRows > ((_iBatchNo - 1) * _iBatchSize))
                    objPickList.enableButton("Next", false);
                else
                    objPickList.enableButton("Next", true);

                objPickList.enableButton("Previous", false);
            }

            if ((_iBatchNo * _iBatchSize) > TotalRows)
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + TotalRows + " of " + TotalRows);
            else
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + ((_iBatchNo * _iBatchSize)) + " of " + TotalRows);
        }
        else if(strSource != null && strSource.equals(TF_BRANCH_LIST))
        {
            _strQuery = "SELECT SOLID,BRANCH_NAME FROM (SELECT SOLID,BRANCH_NAME FROM tffbk_master_branch WHERE STATUS = 'A' AND UPPER(SOLID) LIKE UPPER('" + strSearchVal + "%') ORDER BY SOLID) WHERE ROWNUM <= " + _iBatchSize;

            // System.out.println("********************Load query ************* " + _strQuery);
            if (objPickList.populateData(_strQuery, 2))
            {
                if (((_iBatchNo * _iBatchSize) + 1) > TotalRows && TotalRows > ((_iBatchNo - 1) * _iBatchSize))
                    objPickList.enableButton("Next", false);
                else
                    objPickList.enableButton("Next", true);

                objPickList.enableButton("Previous", false);
            }

            if ((_iBatchNo * _iBatchSize) > TotalRows)
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + TotalRows + " of " + TotalRows);
            else
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + ((_iBatchNo * _iBatchSize)) + " of " + TotalRows);
        }
        else if(strSource != null && strSource.equals(TF_PRODUCT_LIST))
        {
            _strQuery = "SELECT PRODUCT_NAME,PRODUCT_DESC FROM (SELECT PRODUCT_NAME,PRODUCT_DESC FROM tffbk_master_product WHERE STATUS = 'A' AND UPPER(PRODUCT_NAME) LIKE UPPER('" + strSearchVal + "%') ORDER BY PRODUCT_NAME) WHERE ROWNUM <= " + _iBatchSize;

            // System.out.println("********************Load query ************* " + _strQuery);
            if (objPickList.populateData(_strQuery, 2))
            {
                if (((_iBatchNo * _iBatchSize) + 1) > TotalRows && TotalRows > ((_iBatchNo - 1) * _iBatchSize))
                    objPickList.enableButton("Next", false);
                else
                    objPickList.enableButton("Next", true);

                objPickList.enableButton("Previous", false);
            }

            if ((_iBatchNo * _iBatchSize) > TotalRows)
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + TotalRows + " of " + TotalRows);
            else
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + ((_iBatchNo * _iBatchSize)) + " of " + TotalRows);
        }
         else if(strSource != null && strSource.equals(TF_CLIENT_LIST))
        {
            _strQuery = "SELECT KEY_CLIENT_NAME FROM (SELECT KEY_CLIENT_NAME FROM tffbk_master_key_client WHERE STATUS = 'A' AND UPPER(KEY_CLIENT_NAME) LIKE UPPER('" + strSearchVal + "%') AND UPPER(KEY_CLIENT_TYPE) =UPPER('"+formObject.getNGValue("TF_FBK_CLIENT_TYPE").trim()+"') ORDER BY KEY_CLIENT_NAME) WHERE ROWNUM <= " + _iBatchSize;

            // System.out.println("********************Load query ************* " + _strQuery);
            if (objPickList.populateData(_strQuery, 2))
            {
                if (((_iBatchNo * _iBatchSize) + 1) > TotalRows && TotalRows > ((_iBatchNo - 1) * _iBatchSize))
                    objPickList.enableButton("Next", false);
                else
                    objPickList.enableButton("Next", true);

                objPickList.enableButton("Previous", false);
            }

            if ((_iBatchNo * _iBatchSize) > TotalRows)
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + TotalRows + " of " + TotalRows);
            else
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + ((_iBatchNo * _iBatchSize)) + " of " + TotalRows);
        }
    }

    /**
     * Executes the custom code on selection change.
     * @param e The event captured by the listener.
     */
    public void tblPickList_SelectionChanged(NGEvent e)
    {
        // System.out.println("Click");
    }

    /**
     * Executes the custom code on NEXT click.
     * @param e The event captured by the listener.
     */
    public void btnNext_Clicked(NGEvent e)
    {
        NGPickList objPickList = (NGPickList) e.getSource();
        objProcessData.setInputXML(formObject.getWFGeneralData());
        strUserName= objProcessData.getValueOf("UserName");

        _iBatchNo++;

        if (((_iBatchNo * _iBatchSize) + 1) > TotalRows && TotalRows > ((_iBatchNo - 1) * _iBatchSize))
        {
            objPickList.enableButton("Next", false);
        }

        if (strSource != null && strSource.equals(CLG_USER_LIST))
        {
            String strLastUser = objPickList.getValueAt(objPickList.getRecordFetchedInBatch() - 1, 0);

            _strQuery = "SELECT FPRID, FPRNAME FROM (SELECT FPRID, FPRNAME FROM CLG_FPRESCALATIONMASTER WHERE STATUS = 'A' AND FPRID > '" + strLastUser + "' ORDER BY FPRID) WHERE ROWNUM <= " + _iBatchSize;

            if (objPickList.populateData(_strQuery, 2))
            {
                objPickList.enableButton("Previous", true);
            }

            if ((_iBatchNo * _iBatchSize) > TotalRows)
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + TotalRows + " of " + TotalRows);
            else
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + ((_iBatchNo * _iBatchSize)) + " of " + TotalRows);
        }
        else if (strSource != null && strSource.equals(IWF_CITY_LIST))
        {
            String strLastCity_Record_Id = objPickList.getValueAt(objPickList.getRecordFetchedInBatch() - 1, 0);

            _strQuery = "SELECT CIT_RECORD_ID,CITY_NAME FROM (SELECT CIT_RECORD_ID,CITY_NAME FROM IWF_MASTER_CITY WHERE STATUS = 'A' AND CIT_RECORD_ID > '" + strLastCity_Record_Id + "' AND UPPER(CITY_NAME) LIKE UPPER('" + strSearchVal + "%') ORDER BY CIT_RECORD_ID) WHERE ROWNUM <= " + _iBatchSize;

            if (objPickList.populateData(_strQuery, 2))
            {
                objPickList.enableButton("Previous", true);
            }

            if ((_iBatchNo * _iBatchSize) > TotalRows)
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + TotalRows + " of " + TotalRows);
            else
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + ((_iBatchNo * _iBatchSize)) + " of " + TotalRows);
        }
        //CLG_COST_CENTER
        else if (strSource != null && strSource.equals(CLG_COST_CENTER))
        {
            String strLastCostCenter_Record_Id = objPickList.getValueAt(objPickList.getRecordFetchedInBatch() - 1, 0);
            String strCasetype = formObject.getNGValue("CLG_CASETYPE");
            //     _strQuery = "SELECT CIT_RECORD_ID,CITY_NAME FROM (SELECT CIT_RECORD_ID,CITY_NAME FROM IWF_MASTER_CITY WHERE STATUS = 'A' AND CIT_RECORD_ID > '" + strLastCostCenter_Record_Id + "' AND UPPER(CITY_NAME) LIKE UPPER('"+strSearchVal+"%') ORDER BY CIT_RECORD_ID) WHERE ROWNUM <= " + _iBatchSize;

            _strQuery = "SELECT COSTCENTERNAME FROM (SELECT C.COSTCENTERNAME FROM CLG_COSTCENTERMASTER C, CLG_CASETYPEMASTER T  WHERE C.CASETYPE = T.CASECODE AND C.STATUS = 'A' AND T.CASENAME='" + strCasetype + "'   AND C.COSTCENTERNAME > UPPER('" + strLastCostCenter_Record_Id + "')  AND UPPER(C.COSTCENTERNAME) LIKE UPPER('" + strSearchVal + "%') ORDER BY C.COSTCENTERNAME) WHERE ROWNUM <=  " + _iBatchSize;

            // System.out.println("********************Next query ************* " + _strQuery);

            if (objPickList.populateData(_strQuery, 1))
            {
                objPickList.enableButton("Previous", true);
            }

            if ((_iBatchNo * _iBatchSize) > TotalRows)
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + TotalRows + " of " + TotalRows);
            else
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + ((_iBatchNo * _iBatchSize)) + " of " + TotalRows);
        }
         else if (strSource != null && strSource.equals(NRI_PREF_BRANCH))
        {
            String strPrefBrName = objPickList.getValueAt(objPickList.getRecordFetchedInBatch() - 1, 1);

            _strQuery = "SELECT BRANCHCODE,BRANCHNAME FROM (SELECT distinct BRANCHCODE,BRANCHNAME FROM NRI_BRANCHMASTER WHERE STATUS = 'A' AND BRANCHNAME > '" + strPrefBrName + "' AND UPPER(BRANCHNAME) LIKE UPPER('" + strSearchVal + "%') ORDER BY BRANCHNAME) WHERE ROWNUM <= " + _iBatchSize;
            // System.out.println("********************Next query ************* " + _strQuery);

            if (objPickList.populateData(_strQuery, 2))
            {
                objPickList.enableButton("Previous", true);
            }

            if ((_iBatchNo * _iBatchSize) > TotalRows)
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + TotalRows + " of " + TotalRows);
            else
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + ((_iBatchNo * _iBatchSize)) + " of " + TotalRows);
        }
        else if (strSource != null && strSource.equals(NRI_ORG_BRANCH))
        {
            //String strOrgBrName = objPickList.getValueAt(objPickList.getRecordFetchedInBatch() - 1, 1);
			//// System.out.println("strOrgBrName: "+strOrgBrName);
			String strOrgBrName=objPickList.getValueAt(objPickList.getRecordFetchedInBatch() - 1, 0);
			// System.out.println("strOrgBrName: "+strOrgBrName);
			//strOrgBrName = "ACHAMPATHU_6148_RLG";
            _strQuery = "SELECT BRANCH FROM  NG_CPI_BRANCH_MASTER where PRIMARY_SUB_VERTICAL_ID=1  AND BRANCH > '"+strOrgBrName+"' AND UPPER(BRANCH) LIKE UPPER('" + strSearchVal + "%') and ROWNUM <= " + _iBatchSize+"  ORDER BY BRANCH";
//_strQuery = "SELECT BRANCHCODE,BRANCHNAME FROM (SELECT distinct B.BRANCHCODE,B.BRANCHNAME FROM NRI_BRANCHMASTER B,NRI_USERMASTER U WHERE B.CountryName = U.CountryID AND B.STATUS = 'A' AND BRANCH > '"+strOrgBrName+"'AND U.USERID = '" + strUserName + "'AND UPPER(B.BRANCHNAME) LIKE UPPER('" + strSearchVal + "%') ORDER BY B.BRANCHNAME) WHERE ROWNUM <= " + _iBatchSize;			
            
			//_strQuery = "select branch from NG_CPI_BRANCH_MASTER where PRIMARY_SUB_VERTICAL_ID=1" + " AND UPPER(BRANCH) LIKE UPPER('" + strSearchVal + "%') ORDER BY BRANCH) WHERE ROWNUM <= " + _iBatchSize;
			// System.out.println("********************Next query ************* ::" + _strQuery);

            if (objPickList.populateData(_strQuery, 2))
            {
                objPickList.enableButton("Previous", true);
            }

            if ((_iBatchNo * _iBatchSize) > TotalRows)
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + TotalRows + " of " + TotalRows);
            else
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + ((_iBatchNo * _iBatchSize)) + " of " + TotalRows);
        }
		 else if (strSource != null && strSource.equals(INFO_CITY_LIST))
        {
            String strLastCity_Record_Id = objPickList.getValueAt(objPickList.getRecordFetchedInBatch() - 1, 0);

            _strQuery = "SELECT CIT_RECORD_ID,CITY_NAME FROM (SELECT CIT_RECORD_ID,CITY_NAME FROM AOT_MASTER_CITY WHERE STATUS = 'A' AND CIT_RECORD_ID > '" + strLastCity_Record_Id + "' AND UPPER(CITY_NAME) LIKE UPPER('" + strSearchVal + "%') ORDER BY CIT_RECORD_ID) WHERE ROWNUM <= " + _iBatchSize;

            if (objPickList.populateData(_strQuery, 2))
            {
                objPickList.enableButton("Previous", true);
            }

            if ((_iBatchNo * _iBatchSize) > TotalRows)
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + TotalRows + " of " + TotalRows);
            else
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + ((_iBatchNo * _iBatchSize)) + " of " + TotalRows);
        }
         else if (strSource != null && strSource.equals(INFO_STATE_LIST))
        {
            String strLastState_Record_Id = objPickList.getValueAt(objPickList.getRecordFetchedInBatch() - 1, 0);

            _strQuery = "SELECT STA_RECORD_ID,STATE_NAME FROM (SELECT STA_RECORD_ID,STATE_NAME FROM AOT_MASTER_CITY WHERE STATUS = 'A' AND STA_RECORD_ID > '" + strLastState_Record_Id + "' AND UPPER(STATE_NAME) LIKE UPPER('" + strSearchVal + "%') ORDER BY STA_RECORD_ID) WHERE ROWNUM <= " + _iBatchSize;

            if (objPickList.populateData(_strQuery, 2))
            {
                objPickList.enableButton("Previous", true);
            }

            if ((_iBatchNo * _iBatchSize) > TotalRows)
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + TotalRows + " of " + TotalRows);
            else
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + ((_iBatchNo * _iBatchSize)) + " of " + TotalRows);
        }
         else if (strSource != null && strSource.equals(INFO_COUNTRY_LIST))
        {
            String strLastCountry_Record_Id = objPickList.getValueAt(objPickList.getRecordFetchedInBatch() - 1, 0);

            _strQuery = "SELECT CM_COUNTRY_CODE,CM_COUNTRY_DESC FROM (SELECT CM_COUNTRY_CODE,CM_COUNTRY_DESC FROM INFO_MASTER_COUNTRY WHERE STATUS = 'A' AND CM_COUNTRY_CODE > '" + strLastCountry_Record_Id + "' AND UPPER(CM_COUNTRY_DESC) LIKE UPPER('" + strSearchVal + "%') ORDER BY CM_COUNTRY_CODE) WHERE ROWNUM <= " + _iBatchSize;

            if (objPickList.populateData(_strQuery, 2))
            {
                objPickList.enableButton("Previous", true);
            }

            if ((_iBatchNo * _iBatchSize) > TotalRows)
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + TotalRows + " of " + TotalRows);
            else
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + ((_iBatchNo * _iBatchSize)) + " of " + TotalRows);
        }
        else if (strSource != null && strSource.equals(TF_BRANCH_LIST))
        {
            String strPrefBrName = objPickList.getValueAt(objPickList.getRecordFetchedInBatch() - 1, 0);
            _strQuery = "SELECT SOLID,BRANCH_NAME FROM (SELECT SOLID,BRANCH_NAME FROM tffbk_master_branch WHERE STATUS = 'A' AND SOLID > '" + strPrefBrName + "' AND UPPER(SOLID) LIKE UPPER('" + strSearchVal + "%') ORDER BY SOLID) WHERE ROWNUM <= " + _iBatchSize;;
            // System.out.println("********************Next query ************* " + _strQuery);

            if (objPickList.populateData(_strQuery, 2))
            {
                objPickList.enableButton("Previous", true);
            }

            if ((_iBatchNo * _iBatchSize) > TotalRows)
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + TotalRows + " of " + TotalRows);
            else
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + ((_iBatchNo * _iBatchSize)) + " of " + TotalRows);
        }
        else if (strSource != null && strSource.equals(TF_PRODUCT_LIST))
        {
            String strPrefBrName = objPickList.getValueAt(objPickList.getRecordFetchedInBatch() - 1, 0);
            _strQuery = "SELECT PRODUCT_NAME,PRODUCT_DESC FROM (SELECT PRODUCT_NAME,PRODUCT_DESC FROM tffbk_master_product WHERE STATUS = 'A' AND PRODUCT_NAME > '" + strPrefBrName + "' AND UPPER(PRODUCT_NAME) LIKE UPPER('" + strSearchVal + "%') ORDER BY PRODUCT_NAME) WHERE ROWNUM <= " + _iBatchSize;;
            // System.out.println("********************Next query ************* " + _strQuery);

            if (objPickList.populateData(_strQuery, 2))
            {
                objPickList.enableButton("Previous", true);
            }

            if ((_iBatchNo * _iBatchSize) > TotalRows)
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + TotalRows + " of " + TotalRows);
            else
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + ((_iBatchNo * _iBatchSize)) + " of " + TotalRows);
        }
        else if (strSource != null && strSource.equals(TF_CLIENT_LIST))
        {
            String strPrefBrName = objPickList.getValueAt(objPickList.getRecordFetchedInBatch() - 1, 1);
            _strQuery = "SELECT KEY_CLIENT_NAME FROM (SELECT KEY_CLIENT_NAME FROM tffbk_master_key_client WHERE STATUS = 'A' AND KEY_CLIENT_NAME > '" + strPrefBrName + "' AND UPPER(KEY_CLIENT_NAME) LIKE UPPER('" + strSearchVal + "%') AND UPPER(KEY_CLIENT_TYPE)=UPPER('"+formObject.getNGValue("TF_FBK_CLIENT_TYPE").trim()+"') ORDER BY KEY_CLIENT_NAME) WHERE ROWNUM <= " + _iBatchSize;;
            // System.out.println("********************Next query ************* " + _strQuery);

            if (objPickList.populateData(_strQuery, 2))
            {
                objPickList.enableButton("Previous", true);
            }

            if ((_iBatchNo * _iBatchSize) > TotalRows)
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + TotalRows + " of " + TotalRows);
            else
                objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + ((_iBatchNo * _iBatchSize)) + " of " + TotalRows);
        }


    }

    /**
     * Executes the custom code on PREVIOUS click.
     * @param e The event captured by the listener.
     */
    public void btnPrev_Clicked(NGEvent e)
    {
        NGPickList objPickList = (NGPickList) e.getSource();
        objProcessData.setInputXML(formObject.getWFGeneralData());
        strUserName= objProcessData.getValueOf("UserName");

        _iBatchNo--;

        if (_iBatchNo == 1)
        {
            objPickList.enableButton("Previous", false);
        }

        if (strSource != null && strSource.equals(CLG_USER_LIST))
        {
            objPickList.enableButton("Next", true);

            String strFPRUserID = objPickList.getValueAt(0, 0);

        _strQuery = "SELECT FPRID, FPRNAME FROM (SELECT FPRID, FPRNAME FROM CLG_FPRESCALATIONMASTER WHERE STATUS = 'A' AND FPRID < '" + strFPRUserID + "' ORDER BY FPRID DESC) WHERE ROWNUM <= " + _iBatchSize + " ORDER BY FPRID";

            objPickList.populateData(_strQuery, 2);

            objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + ((_iBatchNo * _iBatchSize)) + " of " + TotalRows);
        }
        else if (strSource != null && strSource.equals(IWF_CITY_LIST))
        {
            objPickList.enableButton("Next", true);

            String strLastCity_Record_Id = objPickList.getValueAt(0, 0);

            _strQuery = "SELECT CIT_RECORD_ID,CITY_NAME FROM (SELECT CIT_RECORD_ID,CITY_NAME FROM IWF_MASTER_CITY WHERE STATUS = 'A' AND CIT_RECORD_ID < '" + strLastCity_Record_Id + "' AND UPPER(CITY_NAME) LIKE UPPER('" + strSearchVal + "%') ORDER BY CIT_RECORD_ID DESC) WHERE ROWNUM <= " + _iBatchSize + " ORDER BY CIT_RECORD_ID";

            objPickList.populateData(_strQuery, 2);

            objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + ((_iBatchNo * _iBatchSize)) + " of " + TotalRows);
        }
//CLG_COST_CENTER
        else if (strSource != null && strSource.equals(CLG_COST_CENTER))
        {
            objPickList.enableButton("Next", true);

            String strLastCostCenter_Record_Id = objPickList.getValueAt(0, 0);
            String strCasetype = formObject.getNGValue("CLG_CASETYPE");
//            _strQuery = "SELECT CIT_RECORD_ID,CITY_NAME FROM (SELECT CIT_RECORD_ID,CITY_NAME FROM IWF_MASTER_CITY WHERE STATUS = 'A' AND CIT_RECORD_ID < '" + strLastCostCenter_Record_Id + "' AND UPPER(CITY_NAME) LIKE UPPER('"+strSearchVal+"%') ORDER BY CIT_RECORD_ID DESC) WHERE ROWNUM <= " + _iBatchSize + " ORDER BY CIT_RECORD_ID";

            _strQuery = "SELECT COSTCENTERNAME FROM (SELECT C.COSTCENTERNAME FROM CLG_COSTCENTERMASTER C, CLG_CASETYPEMASTER T  WHERE C.CASETYPE = T.CASECODE AND C.STATUS = 'A' AND T.CASENAME='" + strCasetype + "'   AND UPPER(C.COSTCENTERNAME) < UPPER('" + strLastCostCenter_Record_Id + "')  AND UPPER(C.COSTCENTERNAME) LIKE UPPER('" + strSearchVal + "%') ORDER BY C.COSTCENTERNAME DESC) WHERE ROWNUM <=  " + _iBatchSize + " ORDER BY COSTCENTERNAME";

            // System.out.println("********************Prev query ************* " + _strQuery);
            objPickList.populateData(_strQuery, 1);

            objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + ((_iBatchNo * _iBatchSize)) + " of " + TotalRows);
        }
        else if (strSource != null && strSource.equals(NRI_PREF_BRANCH))
        {
            objPickList.enableButton("Next", true);

            String strPrefBrName = objPickList.getValueAt(0, 1);

        _strQuery = "SELECT BRANCHCODE,BRANCHNAME FROM (SELECT distinct BRANCHCODE,BRANCHNAME FROM NRI_BRANCHMASTER WHERE STATUS = 'A' AND BRANCHNAME < '" + strPrefBrName +  "' AND UPPER(BRANCHNAME) LIKE UPPER('" + strSearchVal + "%') ORDER BY BRANCHNAME DESC) WHERE ROWNUM <= " + _iBatchSize +" ORDER BY BRANCHNAME";

            // System.out.println("********************Prev query ************* " + _strQuery);
            objPickList.populateData(_strQuery, 2);

            objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + ((_iBatchNo * _iBatchSize)) + " of " + TotalRows);
        }
        else if (strSource != null && strSource.equals(NRI_ORG_BRANCH))
        {
            objPickList.enableButton("Next", true);
            String strOrgBrName = objPickList.getValueAt(0, 1);
			// System.out.println("strOrgBrName: "+strOrgBrName);
			strOrgBrName = objPickList.getValueAt(0, 0);
			// System.out.println("strOrgBrName: "+strOrgBrName);
                        _strQuery = "SELECT BRANCH FROM  NG_CPI_BRANCH_MASTER where PRIMARY_SUB_VERTICAL_ID=1 AND BRANCH < '"+strOrgBrName+"' AND UPPER(BRANCH) LIKE UPPER('" + strSearchVal + "%') AND ROWNUM <= " + _iBatchSize+" ORDER BY BRANCH DESC";

						//_strQuery = "SELECT BRANCH FROM from NG_CPI_BRANCH_MASTER where PRIMARY_SUB_VERTICAL_ID=1 AND BRANCH > '"+strOrgBrName+"' AND  UPPER(BRANCH) LIKE UPPER('" + strSearchVal + "%') ORDER BY BRANCH) WHERE ROWNUM <= " + _iBatchSize;
						//_strQuery = "SELECT BRANCHCODE,BRANCHNAME FROM (SELECT distinct B.BRANCHCODE,B.BRANCHNAME FROM NRI_BRANCHMASTER B,NRI_USERMASTER U WHERE B.CountryName = U.CountryID AND B.STATUS = 'A' AND B.BRANCHNAME < '"+strOrgBrName+"'AND U.USERID = '" + strUserName + "'AND UPPER(B.BRANCHNAME) LIKE UPPER('" + strSearchVal + "%') ORDER BY B.BRANCHNAME DESC) WHERE ROWNUM <= " + _iBatchSize+" ORDER BY BRANCHNAME";

      //  _strQuery = "SELECT BRANCHCODE,BRANCHNAME FROM (SELECT BRANCHCODE,BRANCHNAME FROM NRI_BRANCHMASTER WHERE STATUS = 'Active' AND BRANCHNAME < '" + strPrefBrName +  "' AND UPPER(BRANCHNAME) LIKE UPPER('" + strSearchVal + "%') ORDER BY BRANCHNAME DESC) WHERE ROWNUM <= " + _iBatchSize +" ORDER BY BRANCHNAME";

            // System.out.println("********************Prev query ************* " + _strQuery);
            objPickList.populateData(_strQuery, 2);

            objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + ((_iBatchNo * _iBatchSize)) + " of " + TotalRows);

        }
		else if (strSource != null && strSource.equals(INFO_CITY_LIST))
        {
            objPickList.enableButton("Next", true);

            String strLastCity_Record_Id = objPickList.getValueAt(0, 0);

            _strQuery = "SELECT CIT_RECORD_ID,CITY_NAME FROM (SELECT CIT_RECORD_ID,CITY_NAME FROM AOT_MASTER_CITY WHERE STATUS = 'A' AND CIT_RECORD_ID < '" + strLastCity_Record_Id + "' AND UPPER(CITY_NAME) LIKE UPPER('" + strSearchVal + "%') ORDER BY CIT_RECORD_ID DESC) WHERE ROWNUM <= " + _iBatchSize + " ORDER BY CIT_RECORD_ID";

            objPickList.populateData(_strQuery, 2);

            objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + ((_iBatchNo * _iBatchSize)) + " of " + TotalRows);
        }
        else if (strSource != null && strSource.equals(INFO_STATE_LIST))
        {
            objPickList.enableButton("Next", true);

            String strLastCity_Record_Id = objPickList.getValueAt(0, 0);

            _strQuery = "SELECT STA_RECORD_ID,STATE_NAME FROM (SELECT STA_RECORD_ID,STATE_NAME FROM AOT_MASTER_STATE WHERE STATUS = 'A' AND STA_RECORD_ID < '" + strLastCity_Record_Id + "' AND UPPER(STATE_NAME) LIKE UPPER('" + strSearchVal + "%') ORDER BY STA_RECORD_ID DESC) WHERE ROWNUM <= " + _iBatchSize + " ORDER BY STA_RECORD_ID";

            objPickList.populateData(_strQuery, 2);

            objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + ((_iBatchNo * _iBatchSize)) + " of " + TotalRows);
        }
        else if (strSource != null && strSource.equals(INFO_COUNTRY_LIST))
        {
            objPickList.enableButton("Next", true);

            String strLastCity_Record_Id = objPickList.getValueAt(0, 0);

            _strQuery = "SELECT CM_COUNTRY_CODE,CM_COUNTRY_DESC FROM (SELECT CM_COUNTRY_CODE,CM_COUNTRY_DESC FROM INFO_MASTER_COUNTRY WHERE STATUS = 'A' AND CM_COUNTRY_CODE < '" + strLastCity_Record_Id + "' AND UPPER(CM_COUNTRY_DESC) LIKE UPPER('" + strSearchVal + "%') ORDER BY CM_COUNTRY_CODE DESC) WHERE ROWNUM <= " + _iBatchSize + " ORDER BY CM_COUNTRY_CODE ";

            objPickList.populateData(_strQuery, 2);

            objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + ((_iBatchNo * _iBatchSize)) + " of " + TotalRows);
        }
         else if (strSource != null && strSource.equals(TF_BRANCH_LIST))
        {
            objPickList.enableButton("Next", true);

            String strPrefBrName = objPickList.getValueAt(0, 0);

        _strQuery = "SELECT SOLID,BRANCH_NAME FROM (SELECT SOLID,BRANCH_NAME FROM tffbk_master_branch WHERE STATUS = 'A' AND SOLID < '" + strPrefBrName +  "' AND UPPER(SOLID) LIKE UPPER('" + strSearchVal + "%') ORDER BY SOLID DESC) WHERE ROWNUM <= " + _iBatchSize +" ORDER BY SOLID";

            // System.out.println("********************Prev query ************* " + _strQuery);
            objPickList.populateData(_strQuery, 2);

            objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + ((_iBatchNo * _iBatchSize)) + " of " + TotalRows);
        }
        else if (strSource != null && strSource.equals(TF_PRODUCT_LIST))
        {
            objPickList.enableButton("Next", true);

            String strPrefBrName = objPickList.getValueAt(0, 0);

        _strQuery = "SELECT PRODUCT_NAME,PRODUCT_DESC FROM (SELECT PRODUCT_NAME,PRODUCT_DESC FROM tffbk_master_product WHERE STATUS = 'A' AND PRODUCT_NAME < '" + strPrefBrName +  "' AND UPPER(PRODUCT_NAME) LIKE UPPER('" + strSearchVal + "%') ORDER BY PRODUCT_NAME DESC) WHERE ROWNUM <= " + _iBatchSize +" ORDER BY PRODUCT_NAME";

            // System.out.println("********************Prev query ************* " + _strQuery);
            objPickList.populateData(_strQuery, 2);

            objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + ((_iBatchNo * _iBatchSize)) + " of " + TotalRows);
        }
    else if (strSource != null && strSource.equals(TF_CLIENT_LIST))
        {
            objPickList.enableButton("Next", true);

            String strPrefBrName = objPickList.getValueAt(0, 1);

        _strQuery = "SELECT KEY_CLIENT_NAME FROM (SELECT KEY_CLIENT_NAME FROM tffbk_master_key_client WHERE STATUS = 'A' AND KEY_CLIENT_NAME < '" + strPrefBrName +  "' AND UPPER(KEY_CLIENT_NAME) LIKE UPPER('" + strSearchVal + "%') AND UPPER(KEY_CLIENT_TYPE)=UPPER('"+formObject.getNGValue("TF_FBK_CLIENT_TYPE").trim()+"')ORDER BY KEY_CLIENT_NAME DESC) WHERE ROWNUM <= " + _iBatchSize +" ORDER BY BRANCH_NAME";

            // System.out.println("********************Prev query ************* " + _strQuery);
            objPickList.populateData(_strQuery, 2);

            objPickList.setStatus("Showing " + (((_iBatchNo - 1) * _iBatchSize) + 1) + " - " + ((_iBatchNo * _iBatchSize)) + " of " + TotalRows);
        }

    }

    /**
     * Executes the custom code on OK click.
     * @param e The event captured by the listener.
     */
    public void btnOk_Clicked(NGEvent e)
    {

        NGPickList objPickList = (NGPickList) e.getSource();
        ArrayList arrSelectedData = objPickList.getSelectedData();
        ArrayList arrRowData = null;
        // System.out.println("********************btn clicked ************* ");
		// System.out.println("strSource: "+strSource);
        if (strSource != null && strSource.equals(NRI_ORG_BRANCH))
        {
			// System.out.println("strSource1: "+strSource);
            if (arrSelectedData != null && arrSelectedData.size() > 0)
            {
				arrRowData = (ArrayList) arrSelectedData.get(0);
				// System.out.println("strSource 2: "+strSource);
				// System.out.println("arrRowData 2: "+arrRowData);
				// System.out.println("arrRowData 2: "+arrRowData.size());
				//if ((arrRowData != null) && (arrRowData.size() == 2))
                if ((arrRowData != null) && (arrRowData.size() == 1))
                {
					
                    // System.out.println("Value: "+arrRowData.get(0).toString());
					formObject.setNGValue(strCtrlID_1, arrRowData.get(0).toString());
					// System.out.println("strSource 3: "+strSource);
                }
            }
        }
        else if (strSource != null && strSource.equals(IWF_CITY_LIST))
        {
            if (arrSelectedData != null && arrSelectedData.size() > 0)
            {
                arrRowData = (ArrayList) arrSelectedData.get(0);

                if ((arrRowData != null) && (arrRowData.size() == 2))
                {
                    formObject.setNGValue(strCtrlID_1, arrRowData.get(0).toString());
                    formObject.setNGValue(strCtrlID_2, arrRowData.get(1).toString());
                }
            }
        }
//CLG_COST_CENTER
        else if (strSource != null && strSource.equals(CLG_COST_CENTER))
        {
            if (arrSelectedData != null && arrSelectedData.size() > 0)
            {
                arrRowData = (ArrayList) arrSelectedData.get(0);
                if ((arrRowData != null) && (arrRowData.size() == 1))
                {
                    formObject.setNGValue("CLG_COSTCENTER", arrRowData.get(0).toString());
                    //formObject.setNGValue("CLG_COSTCENTER","test");
                    //	formObject.setNGValue(strCtrlID_2,arrRowData.get(1).toString());
                }
            }
        }
         else if (strSource != null && strSource.equals(NRI_PREF_BRANCH))
        {
            if (arrSelectedData != null && arrSelectedData.size() > 0)
            {
                arrRowData = (ArrayList) arrSelectedData.get(0);

                if ((arrRowData != null) && (arrRowData.size() == 2))
                {
                    //formObject.setNGValue(strCtrlID_1, arrRowData.get(1).toString()+"("+arrRowData.get(0).toString()+")");
					formObject.setNGValue(strCtrlID_1, arrRowData.get(0).toString());
                }
            }
        }
          else if (strSource != null && strSource.equals(CLG_USER_LIST))
        {
            if (arrSelectedData != null && arrSelectedData.size() > 0)
            {
                arrRowData = (ArrayList) arrSelectedData.get(0);

                if ((arrRowData != null) && (arrRowData.size() == 2))
                {
                    //formObject.setNGValue(strCtrlID_1, arrRowData.get(1).toString()+"("+arrRowData.get(0).toString()+")");
					formObject.setNGValue(strCtrlID_1, arrRowData.get(0).toString());
                }
            }
        }
		else if (strSource != null && strSource.equals(INFO_CITY_LIST))
        {
            if (arrSelectedData != null && arrSelectedData.size() > 0)
            {
                arrRowData = (ArrayList) arrSelectedData.get(0);

                if ((arrRowData != null) && (arrRowData.size() == 2))
                {
                    //formObject.setNGValue(strCtrlID_1, arrRowData.get(0).toString());
                    formObject.setNGValue(strCtrlID_1, arrRowData.get(1).toString());
                }
            }
        }
        else if (strSource != null && strSource.equals(INFO_STATE_LIST))
        {
            if (arrSelectedData != null && arrSelectedData.size() > 0)
            {
                arrRowData = (ArrayList) arrSelectedData.get(0);

                if ((arrRowData != null) && (arrRowData.size() == 2))
                {
                   // formObject.setNGValue(strCtrlID_1, arrRowData.get(0).toString());
                    formObject.setNGValue(strCtrlID_1, arrRowData.get(1).toString());
                }
            }
        }
        else if (strSource != null && strSource.equals(INFO_COUNTRY_LIST))
        {
            if (arrSelectedData != null && arrSelectedData.size() > 0)
            {
                arrRowData = (ArrayList) arrSelectedData.get(0);

                if ((arrRowData != null) && (arrRowData.size() == 2))
                {
                   // formObject.setNGValue(strCtrlID_1, arrRowData.get(0).toString());
                    formObject.setNGValue(strCtrlID_1, arrRowData.get(1).toString());
					//formObject.setNGValue(str, arrRowData.get(1).toString());
                }
            }
        }
        else if (strSource != null && strSource.equals(TF_BRANCH_LIST))
        {
            if (arrSelectedData != null && arrSelectedData.size() > 0)
            {
                arrRowData = (ArrayList) arrSelectedData.get(0);

                if ((arrRowData != null) && (arrRowData.size() == 2))
                {
                   // formObject.setNGValue(strCtrlID_1, arrRowData.get(0).toString());
                    formObject.setNGValue(strCtrlID_1, arrRowData.get(0).toString());
                    formObject.setNGValue(strCtrlID_2, arrRowData.get(1).toString());
					//formObject.setNGValue(str, arrRowData.get(1).toString());
                }
            }
        }
         else if (strSource != null && strSource.equals(TF_PRODUCT_LIST))
        {
            if (arrSelectedData != null && arrSelectedData.size() > 0)
            {
                arrRowData = (ArrayList) arrSelectedData.get(0);

                if ((arrRowData != null) && (arrRowData.size() == 2))
                {
                   // formObject.setNGValue(strCtrlID_1, arrRowData.get(0).toString());
                    formObject.setNGValue(strCtrlID_1, arrRowData.get(0).toString());
                    formObject.setNGValue(strCtrlID_2, arrRowData.get(1).toString());
					//formObject.setNGValue(str, arrRowData.get(1).toString());
                }
            }
        }
         else if (strSource != null && strSource.equals(TF_CLIENT_LIST))
        {
            if (arrSelectedData != null && arrSelectedData.size() > 0)
            {
                arrRowData = (ArrayList) arrSelectedData.get(0);

                if ((arrRowData != null) && (arrRowData.size() == 1))
                {
                   // formObject.setNGValue(strCtrlID_1, arrRowData.get(0).toString());
                    formObject.setNGValue(strCtrlID_1, arrRowData.get(0).toString());
                    
					//formObject.setNGValue(str, arrRowData.get(1).toString());
                }
            }
        }



        objPickList.setVisible(false);
    }

    /**
     * Executes the custom code on CANCEL click.
     * @param e The event captured by the listener.
     */
    public void btnCancel_Clicked(NGEvent e)
    {
        NGPickList objPickList = (NGPickList) e.getSource();
        objPickList.setVisible(false);
    }
}
