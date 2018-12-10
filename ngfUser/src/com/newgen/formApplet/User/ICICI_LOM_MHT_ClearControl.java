/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.newgen.formApplet.User;

/**
 *
 * @author ILOM09138
 */
public class ICICI_LOM_MHT_ClearControl
{

    private NGFPropInterface formObject;

    public ICICI_LOM_MHT_ClearControl(NGFPropInterface formObject)
    {
        this.formObject = formObject;

    }

    public boolean clear_all_control()
    {
        ////System.out.println("ICICI_LOM_MHT_ClearControl.clear_all_control(): started");
        
        formObject.setNGValue("MHT_PRIMARY_VERTICAL","");
        formObject.setNGValue("MHT_SUB_VERTICAL","");
        formObject.setNGValue("MHT_PRODUCT_NAME","");
		formObject.setNGListIndex("MHT_TRANSACTION_TYPE",0);
        //formObject.setNGValue("MHT_TRANSACTION_TYPE","--Select--");
        //formObject.setNGValue("MHT_COVER_NOTE_TYPE","--Select--");
        /********Start CR-OMHT-1314-02 Omniflow_development for ILLocation Changes******/
		/*formObject.setNGValue("MHT_IL_LOCATION","");
        formObject.setNGValue("MHT_IL_LOCATION_CODE",""); */
		/********End CR-OMHT-1314-02 Omniflow_development for ILLocation Changes*******/
        formObject.setNGValue("MHT_PREVIOUS_POLICY_NO","");
        formObject.setNGValue("MHT_ENDORSEMENT_NO","");
        formObject.setNGValue("MHT_IPARTNER_PROPOSAL_NO","");
        formObject.setNGValue("MHT_PATHFINDER_PROPOSAL_NO","");
        formObject.setNGValue("MHT_CUSTOMER_NAME","");
        formObject.setNGValue("MHT_CUSTOMER_ID","");
        formObject.setNGValue("MHT_SOURCE_BUSINESS","");
        formObject.setNGValue("MHT_CHANNEL_SOURCE","");
        formObject.setNGValue("MHT_BANK_BRANCH_NAME","");
        formObject.setNGValue("MHT_EMPCODE_CSO","");
		formObject.setNGValue("MHT_SP_CODE","");
		/**************************Start MHT SP Code CR-8127-69652 ******************************/
		formObject.setNGValue("MHT_DEAL_IL_LOCATION","");
		formObject.setNGValue("MHT_SP_PAN","");
		/**************************End MHT SP Code CR-8127-69652 ******************************/
        formObject.setNGValue("MHT_CHANNEL_EMP_INFO","");
        formObject.setNGValue("MHT_BRANCH_ID_UBO_NAME","");
        formObject.setNGValue("MHT_BSM_ID","");
        formObject.setNGValue("MHT_BCM_ID","");
        formObject.setNGValue("MHT_RO_DSA_COUNSELOR_ID","");
        formObject.setNGValue("MHT_CENTER_CODE_NAME","");
        formObject.setNGValue("MHT_RM_EMPLOYEE","");
		formObject.setNGValue("MHT_PREMIUM_AMOUNT","");
		formObject.setNGValue("MHT_MANUAL_CN_STATUS","");
		formObject.setNGValue("MHT_PRODUCT_TYPE","");
		formObject.setNGValue("MHT_PF_PAYMENT_ID_NO","");
		formObject.setNGValue("MHT_IAS_ID","");
		formObject.setNGValue("MHT_BREAK_IN_ID","");
		formObject.setNGValue("MHT_SM_NAME","");
		formObject.setNGValue("MHT_SM_ID","");
		//formObject.setNGValue("MHT_COVER_NOTE_TYPE","");
		formObject.setNGValue("MHT_ALTERNATE_POLICY_NUMBER","");


        formObject.setNGValue("MHT_FINAL_QUOTE_NO","");
        formObject.setNGValue("MHT_REMARKS","");
        formObject.setNGValue("MHT_PRODUCT_CODE","");
        formObject.setNGValue("MHT_DEAL_STATUS","");
		/*************************** MHT-PID Process Integration ****************************/
        /*formObject.setNGListIndex("MHT_PAYMENT_TYPE1",0);
		formObject.setNGListIndex("MHT_PAYMENT_TYPE2",0);
		formObject.setNGListIndex("MHT_PAYMENT_TYPE3",0);*/
		formObject.NGClear("MHT_PAYMENT_TYPE1");
        formObject.NGClear("MHT_PAYMENT_TYPE2");
        formObject.NGClear("MHT_PAYMENT_TYPE3");
		/************************* End MHT-PID Process Integration **************************/

        formObject.setNGValue("MHT_PAYMENT_TYPE1_NO","");
        formObject.setNGValue("MHT_PAYMENT_TYPE2_NO","");
        formObject.setNGValue("MHT_PAYMENT_TYPE3_NO","");

        formObject.setNGValue("MHT_PAYMENT_TYPE1_DATE","");
        formObject.setNGValue("MHT_PAYMENT_TYPE2_DATE","");
        formObject.setNGValue("MHT_PAYMENT_TYPE3_DATE","");

        formObject.setNGValue("MHT_PAYMENT_TYPE1_AMOUNT","");
        formObject.setNGValue("MHT_PAYMENT_TYPE2_AMOUNT","");
        formObject.setNGValue("MHT_PAYMENT_TYPE3_AMOUNT","");

        formObject.setNGValue("MHT_PAYMENT_TYPE1_BANKNAME","");
        formObject.setNGValue("MHT_PAYMENT_TYPE2_BANKNAME","");
        formObject.setNGValue("MHT_PAYMENT_TYPE3_BANKNAME","");

        formObject.setNGValue("MHT_PAYMENT_TYPE1_EXPIRYDATE","");
        formObject.setNGValue("MHT_PAYMENT_TYPE2_EXPIRYDATE","");
        formObject.setNGValue("MHT_PAYMENT_TYPE3_EXPIRYDATE","");
        return true;
    }

}
