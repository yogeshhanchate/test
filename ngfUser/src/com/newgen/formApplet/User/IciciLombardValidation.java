package com.newgen.formApplet.User;
	
import java.applet.AppletContext;
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
import java.text.DateFormat;
import java.awt.Color;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import netscape.javascript.*;
import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import netscape.javascript.JSObject;
import java.text.ParseException;
import com.newgen.formApplet.User.*;
import com.newgen.formApplet.*;

import java.math.BigDecimal;
import java.math.MathContext;
	
	public class IciciLombardValidation{
	
	Validation validation = null;
	List<String[]> listValuesArr = null;
	List<String[]> mandatoryValuesListArr=null;
	int flag;
	int flag1;
	int flag2;	
	private NGFPropInterface formObject = null;
	public IciciLombardValidation(NGFPropInterface formObject) //formObject Mandatory
	{
		this.formObject = formObject;
	}
	
		public int validationIciciLombardHT(String pEvent,String ActivityName,String WorkItemName)
		{
			// System.out.println("Event ::"+pEvent);
			// System.out.println("ActivityName ::"+ActivityName);
			// System.out.println("WorkItemName ::"+WorkItemName);
			
			try{
					if(pEvent.equals("S"))
					{
						if(ActivityName.equalsIgnoreCase("BSG_DATAENTRY")||ActivityName.equalsIgnoreCase("BSG_DATAENTRY_QC")||ActivityName.equalsIgnoreCase("EXCEPTION")||ActivityName.equalsIgnoreCase("COPS_QC")||ActivityName.equalsIgnoreCase("COPS_TEAM")||ActivityName.equalsIgnoreCase("RMT"))
						{
													
							return 1;
						
						}
					
					}
					
					
					if(pEvent.equals("D"))
					{
						if(ActivityName.equalsIgnoreCase("BSG_DATAENTRY"))
						{
							// System.out.println("in data entry");
							flag1 = getValuesBSG_DataEntry();
							flag2 = mandatoryFields();
							
							if(flag1==1&& flag2==1)
							{
								flag=1;
							}else 
							{
								flag=0;
							}
						}
					}
					
					return flag;
				}catch(Exception ex){
					ex.printStackTrace();
					return 0;
			}
		
			
		}
			
			public int getValuesBSG_DataEntry(){
					validation = new Validation(formObject);
					int flag1=0; // check returning value 0/1 again
					int flag2=0;
					int flag3=0;
					// System.out.println("in get valuier");
					listValuesArr =getValuesDataEntryWorkStep();
					// System.out.println("size of list------------"+listValuesArr.size());
					if(listValuesArr!=null && listValuesArr.size() >0)
					{  
						for(int i=0;i<listValuesArr.size();i++){
							
							// System.out.println("fieldName "+listValuesArr.get(i)[0]);
							// System.out.println("fieldValue "+listValuesArr.get(i)[1]);
							// System.out.println("ValidationType "+listValuesArr.get(i)[2]);
							// System.out.println("dispaly Name  "+listValuesArr.get(i)[3]);
							flag2=	validation.emptyCheckValidation(listValuesArr.get(i)[0],listValuesArr.get(i)[1],listValuesArr.get(i)[2],listValuesArr.get(i)[3]);
							if(flag2==0)
							{	
								flag1 = validation.validateType(listValuesArr.get(i)[0],listValuesArr.get(i)[1],listValuesArr.get(i)[2],listValuesArr.get(i)[3]);
							}
							// System.out.println("flag1 "+flag1+" ----"+"flag2 "+flag2);
							flag3 = ((flag1 ==1) &&(flag2 ==1)) ? 1: 0;
								break;
						
						}
						//return flag1;
					}
				return flag3;	
			}
		
		
		
		/* ********* Retreiving values from  BSG_DATANTRY  ****************** */
		
		public List<String[]>  getValuesDataEntryWorkStep()
		 {			
		 // System.out.println("inside getValuesDataEntryWorkStep method ");
					
					List<String[]> listArr = new ArrayList<String[]>();
					
					
					String smIdFieldValue = formObject.getNGValue("ICICILOMBARD_HT_SM_ID");
					if(smIdFieldValue!=null)
					{	String smIdFieldName ="ICICILOMBARD_HT_SM_ID";
						//String smIdFieldValue="TEST";
						String smIdValidateType ="OnlyNumeric";
						String smIdDisplayName ="SM ID";
						String strArrSMID []={smIdFieldName,smIdFieldValue,smIdValidateType,smIdDisplayName};
						listArr.add(strArrSMID);
					}
					// System.out.println("inside getValuesDataEntryWorkStep method 11");
					
					String contact1FieldValue = formObject.getNGValue("ICICILOMBARD_HT_CONTACT1");
					// System.out.println("contact1FieldValue  "+contact1FieldValue);
					if(contact1FieldValue!=null)
					{   
						String contact1FieldName ="ICICILOMBARD_HT_CONTACT1";
						String contact1ValidateType ="OnlyNumeric";
						String contact1DisplayName ="Contact1";
						String strArrContact1 []={contact1FieldName,contact1FieldValue,contact1ValidateType,contact1DisplayName};
						listArr.add(strArrContact1);
					}	
					
					
					String contact2FieldValue = formObject.getNGValue("ICICILOMBARD_HT_CONTACT2");
					if(contact2FieldValue!=null)
					{
						String contact2FieldName ="ICICILOMBARD_HT_CONTACT2";
						String contact2ValidateType ="OnlyNumeric";
						String contact2DisplayName ="Contact2";
						
						String strArrContact2 []={contact2FieldName,contact2FieldValue,contact2ValidateType,contact2DisplayName};
						listArr.add(strArrContact2);
					}
					
					// System.out.println("before trainee id");
					
					
					String traineeIdFieldValue = formObject.getNGValue("ICICILOMBARD_HT_TRAINEE_ID");
					if(traineeIdFieldValue!=null)
					{
						String traineeIdFieldName ="ICICILOMBARD_HT_TRAINEE_ID";
						String traineeIdValidateType ="OnlyNumeric";
						String traineeIdDisplayName ="Trainee ID";
						String strArrtraineeId []={traineeIdFieldName,traineeIdFieldValue,traineeIdValidateType,traineeIdDisplayName};
						listArr.add(strArrtraineeId);
					}
					// System.out.println("before premium");
					
					
					
					String premiumAmountFieldValue = formObject.getNGValue("ICICILOMBARD_HT_PREMIUM_AMOUNT");
					if(premiumAmountFieldValue!=null)
					{	
						String premiumAmountFieldName ="ICICILOMBARD_HT_PREMIUM_AMOUNT";
						String premiumAmountValidateType ="OnlyNumeric";
						String premiumAmountDisplayName ="Premium Amount";
						
						String strArrPremiumAmount []={premiumAmountFieldName,premiumAmountFieldValue,premiumAmountValidateType,premiumAmountDisplayName};
						listArr.add(strArrPremiumAmount);
					}
					// System.out.println("before lan");
					
					
					String lanNoFieldValue = formObject.getNGValue("ICICILOMBARD_HT_LAN");
					if(lanNoFieldValue!=null)
					{   
						String lanNoFieldName ="ICICILOMBARD_HT_LAN";
						String lanNoValidateType ="OnlyNumeric";
						String lanNoDisplayName ="LAN NO";
						String strArrLanNo []={lanNoFieldName,lanNoFieldValue,lanNoValidateType,lanNoDisplayName};
						listArr.add(strArrLanNo);
					}					
					//// System.out.println("List Object "+listArr
					
					String product = formObject.getNGValue("ICICILOMBARD_HT_PRODUCT");
					if(product!=null && product.length()>0)
					{	if(product.equalsIgnoreCase("HSP")){
							// System.out.println("in hsp---- ");
							
							String sumInsuredFieldValue = formObject.getNGValue("ICICILOMBARD_HT_SUM_INSURED");
							if(sumInsuredFieldValue!=null)
							{	
								String sumInsuredFieldName="ICICILOMBARD_HT_SUM_INSURED";
								String sumInsuredValidationType ="OnlyNumeric";
								String sumInsuredDisplayName = "Sum Insured";
								String strArrsumInsured [] = {sumInsuredFieldName,sumInsuredFieldValue,sumInsuredValidationType,sumInsuredDisplayName};
								listArr.add(strArrsumInsured);
							}	
						}
					}
					String paymentMode = formObject.getNGValue("ICICILOMBARD_HT_PAYMENT_MODE");
					// System.out.println("in --- "+paymentMode);
					if(paymentMode!=null && paymentMode.length()>0)
					{
						if(paymentMode.equalsIgnoreCase("Cheque")||paymentMode.equalsIgnoreCase("Demand Draft")){
							
							String instrumentNoFieldValue= formObject.getNGValue("ICICILOMBARD_HT_INSTRUMENT_NO");
							if(instrumentNoFieldValue!=null)
							{
								String instrumentNoFieldName="ICICILOMBARD_HT_INSTRUMENT_NO";
								String instrumentNoValidationType="OnlyNumeric";
								String instrumentNoDisplayName="Instrument NO";
								
								String strArrInstrumentNo[] = {instrumentNoFieldName,instrumentNoFieldValue,instrumentNoValidationType,instrumentNoDisplayName};
								listArr.add(strArrInstrumentNo);
							}
							
							
							String instrumentDateFieldValue= formObject.getNGValue("ICICILOMBARD_HT_INSTRUMENT_DATE");
							if(instrumentDateFieldValue!=null)
							{
								String instrumentDateFieldName="ICICILOMBARD_HT_INSTRUMENT_DATE";
								String instrumentDateValidationType="dateFormat(dd/mm/yyyy)";
								String instrumentDateDisplayName="Instrument Date";
								String strArrInstrumentDate[] = {instrumentDateFieldName,instrumentDateFieldValue,instrumentDateValidationType,instrumentDateDisplayName};
								listArr.add(strArrInstrumentDate);
							}
						}else if(paymentMode.equalsIgnoreCase("Electronic Fund Transfer")){
					
							
							String savingsAccNofieldValue= formObject.getNGValue("ICICILOMBARD_HT_SAVINGS_ACCOUNT_NO");
							if(savingsAccNofieldValue!=null)
							{
								String savingsAccNofieldName="ICICILOMBARD_HT_SAVINGS_ACCOUNT_NO";
								String savingsAccNovalidationType="OnlyNumeric";
								String savingsAccNodisplayName="Savings Account NO";
								String strArrSavingaccNo[] = {savingsAccNofieldName,savingsAccNofieldValue,savingsAccNovalidationType,savingsAccNodisplayName};
								listArr.add(strArrSavingaccNo);
							}
						}else if(paymentMode.equalsIgnoreCase("Credit Card")){
					
							
							String creditCardFieldValue = formObject.getNGValue("ICICILOMBARD_HT_CREDIT_CARD_NO");
							if(creditCardFieldValue!=null)
							{
								String creditCardFieldName="ICICILOMBARD_HT_CREDIT_CARD_NO";
								String creditCardValidationType ="OnlyNumeric";
								String creditCardDisplayName ="Credit Card No";
								String strArrCreditCard[] = {creditCardFieldName,creditCardFieldValue,creditCardValidationType,creditCardDisplayName};
								listArr.add(strArrCreditCard);
							}
							
							
							String creditCardExpDateFieldValue = formObject.getNGValue("ICICILOMBARD_HT_CREDITCARD_EXP_DATE");
							if(creditCardExpDateFieldValue!=null)
							{
								String creditCardExpDateFieldName = "ICICILOMBARD_HT_CREDITCARD_EXP_DATE";
								String creditCardExpDateValidationType="dateFormat(dd/mm/yyyy)";
								String creditCardExpDateDisplayName="Credit Card Expiry Date";
								String strArrCreditCardExpDate[] = {creditCardExpDateFieldName,creditCardExpDateFieldValue,creditCardExpDateValidationType,creditCardExpDateDisplayName};
								listArr.add(strArrCreditCardExpDate);
							}
							
							String authorizationCodeFieldValue = formObject.getNGValue("ICICILOMBARD_HT_AUTHORIZATION_CODE");
							if(authorizationCodeFieldValue!=null)
							{
								String authorizationCodeFieldName = "ICICILOMBARD_HT_AUTHORIZATION_CODE";
								String authorizationCodeValidationType="OnlyNumeric";
								String authorizationCodeDisplayName="Authorization Code";
								String strArrAuthorizationCode[] = {authorizationCodeFieldName,authorizationCodeFieldValue,authorizationCodeValidationType,authorizationCodeDisplayName};
								listArr.add(strArrAuthorizationCode);

							}
							
							String authorizationDateFieldName = "ICICILOMBARD_HT_AUTHORIZATION_DATE";
							String authorizationDateFieldValue = formObject.getNGValue("ICICILOMBARD_HT_AUTHORIZATION_DATE");
							if(authorizationDateFieldValue!=null)
							{
								String authorizationDateValidationType="dateFormat(dd/mm/yyyy)";
								String authorizationDateDisplayName="Authorization Date";
								String strArrAuthorizationDate[] = {authorizationDateFieldName,authorizationDateFieldValue,authorizationDateValidationType,authorizationDateDisplayName};
								listArr.add(strArrAuthorizationDate);
							}
				
						}else if(paymentMode.equalsIgnoreCase("CDBG")){
					
							
							String pidNoFieldValue =formObject.getNGValue("ICICILOMBARD_HT_PID_NO");
							if(pidNoFieldValue!=null)
							{
								String pidNoFieldName ="ICICILOMBARD_HT_PID_NO";
								String pidNoValidationType ="onlyAlphaNumeric";
								String pidNoDisplayName = "PID NO"; 
								String strArrPIDNO[]={pidNoFieldName,pidNoFieldValue,pidNoValidationType,pidNoDisplayName};
								listArr.add(strArrPIDNO);
							}
						}
					}	
						
					
					return listArr;
		 
		 }
		 
		 
		 //************** Mandatory Data Validation *************
		 public int mandatoryFields(){
		 validation = new Validation(formObject);
		 int flag =0;
		 
			mandatoryValuesListArr = getMandatoryValuesBSG_DATAENTRY();
			// System.out.println("mandatory list size"+mandatoryValuesListArr.size());
			if(mandatoryValuesListArr!=null && mandatoryValuesListArr.size()>0)
			{	for(int i=0;i<mandatoryValuesListArr.size();i++)
				{		
					// System.out.println("fieldName "+mandatoryValuesListArr.get(i)[0]);
					// System.out.println("fieldValue "+mandatoryValuesListArr.get(i)[1]);
					// System.out.println("ValidationType "+mandatoryValuesListArr.get(i)[2]);
					// System.out.println("dispaly Name  "+mandatoryValuesListArr.get(i)[3]);
					
					flag = validation.emptyCheckValidation(mandatoryValuesListArr.get(i)[0],mandatoryValuesListArr.get(i)[1],mandatoryValuesListArr.get(i)[2],mandatoryValuesListArr.get(i)[3]);
				}
			
			}
		return flag;	
		}
		
	
//****** Retreiving Mandatory Values and setting in List *****
	public List<String[]> getMandatoryValuesBSG_DATAENTRY(){
			List<String[]> mandatoryFieldsList = new ArrayList<String[]>();	
					
					if((formObject.getNGValue("ICICILOMBARD_HT_AGENT_NAME"))!=null)
					{
						String agentNameFieldName ="ICICILOMBARD_HT_AGENT_NAME";
						String agentNameFieldValue = formObject.getNGValue("ICICILOMBARD_HT_AGENT_NAME");
						String agentNameDisplayName ="Agent Name";
						String agentNameValidationType="emptyCheck";
						String strArrAgentName [] = {agentNameFieldName,agentNameFieldValue,agentNameValidationType,agentNameDisplayName};
						mandatoryFieldsList.add(strArrAgentName);
						
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_AGENT_CODE"))!=null)
					{
						String agentCodeFieldName ="ICICILOMBARD_HT_AGENT_CODE";
						String agentCodeFieldValue = formObject.getNGValue("ICICILOMBARD_HT_AGENT_CODE");
						String agentCodeValidationType="emptyCheck";
						String agentCodeDisplayName ="Agent Code";
						String strArrAgentCode [] = {agentCodeFieldName,agentCodeFieldValue,agentCodeValidationType,agentCodeDisplayName};
						mandatoryFieldsList.add(strArrAgentCode);

					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_DEAL_NO"))!=null)
					{
						String dealNoFieldName ="ICICILOMBARD_HT_DEAL_NO";
						String dealNoFieldValue = formObject.getNGValue("ICICILOMBARD_HT_DEAL_NO");
						String dealNoValidationType="emptyCheck";
						String dealNoDisplayName ="Deal No";
						String strArrDealNo [] = {dealNoFieldName,dealNoFieldValue,dealNoValidationType,dealNoDisplayName};
						mandatoryFieldsList.add(strArrDealNo);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL"))!=null)
					{
						String subVerticalFieldName ="ICICILOMBARD_HT_SUB_VERTICAL";
						String subVerticalFieldValue = formObject.getNGValue("ICICILOMBARD_HT_SUB_VERTICAL");
						String subVerticalValidationType="emptyCheck";
						String subVerticalDisplayName ="Sub Vertical";
						String strArrSubVertical [] = {subVerticalFieldName,subVerticalFieldValue,subVerticalValidationType,subVerticalDisplayName};
						
						mandatoryFieldsList.add(strArrSubVertical);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_VERTICAL"))!=null)
					{
						String verticalFieldName ="ICICILOMBARD_HT_VERTICAL";
						String verticalFieldValue = formObject.getNGValue("ICICILOMBARD_HT_VERTICAL");
						String verticalValidationType="emptyCheck";
						String verticalDisplayName ="Vertical";
						String strArrVertical [] = {verticalFieldName,verticalFieldValue,verticalValidationType,verticalDisplayName};
						
						mandatoryFieldsList.add(strArrVertical);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_PRODUCT_ABBR_CODE"))!=null)
					{
						String productAbbrCodeFieldName ="ICICILOMBARD_HT_PRODUCT_ABBR_CODE";
						String productAbbrCodeFieldValue = formObject.getNGValue("ICICILOMBARD_HT_PRODUCT_ABBR_CODE");
						String  productAbbrCodeValidationType="emptyCheck";
						String productAbbrCodeDisplayName ="Product Abbr Code";
						String strArrProductAbbrCode [] = {productAbbrCodeFieldName,productAbbrCodeFieldValue,productAbbrCodeValidationType,productAbbrCodeDisplayName};
						
						mandatoryFieldsList.add(strArrProductAbbrCode);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_SUB_PRODUCT"))!=null)
					{
						String subProductFieldName ="ICICILOMBARD_HT_SUB_PRODUCT";
						String subProductFieldValue = formObject.getNGValue("ICICILOMBARD_HT_SUB_PRODUCT");
						String subProductValidationType="emptyCheck";
						String subProductDisplayName ="Sub Product";
						String strArrSubProduct [] = {subProductFieldName,subProductFieldValue,subProductValidationType,subProductDisplayName};
						
						mandatoryFieldsList.add(strArrSubProduct);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_TRANSACTION_TYPE"))!=null)
					{
						String transctionTypeFieldName ="ICICILOMBARD_HT_TRANSACTION_TYPE";
						String transctionTypeFieldValue = formObject.getNGValue("ICICILOMBARD_HT_TRANSACTION_TYPE");
						String transctionTypeValidationType="emptyCheck";
						String transctionTypeDisplayName ="Transction Type";
						String strArrTransctionType [] = {transctionTypeFieldName,transctionTypeFieldValue,transctionTypeValidationType,transctionTypeDisplayName};
						
						mandatoryFieldsList.add(strArrTransctionType);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_PREVIOUS_POLICY_NO"))!=null)
					{
						String previousPolicyFieldName ="ICICILOMBARD_HT_PREVIOUS_POLICY_NO";
						String previousPolicyFieldValue = formObject.getNGValue("ICICILOMBARD_HT_PREVIOUS_POLICY_NO");
						String previousPolicyValidationType="emptyCheck";
						String previousPolicyDisplayName ="Previous Policy No";
						String strArrPreviousPolicy [] = {previousPolicyFieldName,previousPolicyFieldValue,previousPolicyValidationType,previousPolicyDisplayName};
						
						mandatoryFieldsList.add(strArrPreviousPolicy);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_IAGENT"))!=null)
					{
						String iAgentFieldName ="ICICILOMBARD_HT_IAGENT";
						String iAgentFieldValue = formObject.getNGValue("ICICILOMBARD_HT_IAGENT");
						String iAgentValidationType="emptyCheck";
						String iAgentDisplayName ="I Agent";
						String strArrIAgent [] = {iAgentFieldName,iAgentFieldValue,iAgentValidationType,iAgentDisplayName};
						
						mandatoryFieldsList.add(strArrIAgent);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_PROPOSAL_NO"))!=null)
					{
						String proposalNoFieldName ="ICICILOMBARD_HT_PROPOSAL_NO";
						String proposalNoFieldValue = formObject.getNGValue("ICICILOMBARD_HT_PROPOSAL_NO");
						String proposalNoValidationType="emptyCheck";
						String proposalNoDisplayName ="Proposal No";
						String strArrProposalNo [] = {proposalNoFieldName,proposalNoFieldValue,proposalNoValidationType,proposalNoDisplayName};
						
						mandatoryFieldsList.add(strArrProposalNo);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_CONFIRM_PROPOSAL_NO"))!=null)
					{
						String confirmProposalNoFieldName ="ICICILOMBARD_HT_CONFIRM_PROPOSAL_NO";
						String confirmProposalNoFieldValue = formObject.getNGValue("ICICILOMBARD_HT_CONFIRM_PROPOSAL_NO");
						String confirmProposalNoValidationType="emptyCheck";
						String confirmProposalNoDisplayName ="Confirm Proposal No";
						String strArrConfirmProposalNo [] = {confirmProposalNoFieldName,confirmProposalNoFieldValue,confirmProposalNoValidationType,confirmProposalNoDisplayName};
						
						mandatoryFieldsList.add(strArrConfirmProposalNo);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_BANK_NAME"))!=null)
					{
						String bankNameFieldName ="ICICILOMBARD_HT_BANK_NAME";
						String bankNameFieldValue = formObject.getNGValue("ICICILOMBARD_HT_BANK_NAME");
						String bankNameValidationType="emptyCheck";
						String bankNameDisplayName ="Bank Name";
						String strArrBankName [] = {bankNameFieldName,bankNameFieldValue,bankNameValidationType,bankNameDisplayName};
						
						mandatoryFieldsList.add(strArrBankName);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_BANK_CODE"))!=null)
					{
						String bankCodeFieldName ="ICICILOMBARD_HT_BANK_CODE";
						String bankCodeFieldValue = formObject.getNGValue("ICICILOMBARD_HT_BANK_CODE");
						String bankCodeValidationType="emptyCheck";
						String bankCodeDisplayName ="Bank Code";
						String strArrBankCode [] = {bankCodeFieldName,bankCodeFieldValue,bankCodeValidationType,bankCodeDisplayName};
						
						mandatoryFieldsList.add(strArrBankCode);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_SM_NAME"))!=null)
					{
						String smNameFieldName ="ICICILOMBARD_HT_SM_NAME";
						String smNameFieldValue = formObject.getNGValue("ICICILOMBARD_HT_SM_NAME");
						String smNameValidationType="emptyCheck";
						String smNameDisplayName ="SM Name";
						String strArrSmName [] = {smNameFieldName,smNameFieldValue,smNameValidationType,smNameDisplayName};
						
						mandatoryFieldsList.add(strArrSmName);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_CUSTOMER_NAME"))!=null)
					{
						String customerNameFieldName ="ICICILOMBARD_HT_CUSTOMER_NAME";
						String customerNameFieldValue = formObject.getNGValue("ICICILOMBARD_HT_CUSTOMER_NAME");
						String customerNameValidationType="emptyCheck";
						String customerNameDisplayName ="Customer Name";
						String strArrCustomerName [] = {customerNameFieldName,customerNameFieldValue,customerNameValidationType,customerNameDisplayName};
						
						mandatoryFieldsList.add(strArrCustomerName);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_APPLICANT_NO"))!=null)
					{
						String applicantNoFieldName ="ICICILOMBARD_HT_APPLICANT_NO";
						String applicantNoFieldValue = formObject.getNGValue("ICICILOMBARD_HT_APPLICANT_NO");
						String applicantNoValidationType="emptyCheck";
						String applicantNoDisplayName ="ArrApplicant No";
						String strArrApplicantNo [] = {applicantNoFieldName,applicantNoFieldValue,applicantNoValidationType,applicantNoDisplayName};
						
						mandatoryFieldsList.add(strArrApplicantNo);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_HSP_VERTICAL"))!=null)
					{
						String hspVerticalFieldName ="ICICILOMBARD_HT_HSP_VERTICAL";
						String hspVerticalFieldValue = formObject.getNGValue("ICICILOMBARD_HT_HSP_VERTICAL");
						String hspVerticalValidationType="emptyCheck";
						String hspVerticalDisplayName ="HSP Vertical";
						String strArrHspVertical [] = {hspVerticalFieldName,hspVerticalFieldValue,hspVerticalValidationType,hspVerticalDisplayName};
						
						mandatoryFieldsList.add(strArrHspVertical);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_DOB"))!=null)
					{
						String dobFieldName ="ICICILOMBARD_HT_DOB";
						String dobFieldValue = formObject.getNGValue("ICICILOMBARD_HT_DOB");
						String dobValidationType="emptyCheck";
						String dobDisplayName ="DOB";
						String strArrDOB [] = {dobFieldName,dobFieldValue,dobValidationType,dobDisplayName};
						
						mandatoryFieldsList.add(strArrDOB);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_SANCTION_DATE"))!=null)
					{
						String sanctionDateFieldName ="ICICILOMBARD_HT_SANCTION_DATE";
						String sanctionDateFieldValue = formObject.getNGValue("ICICILOMBARD_HT_SANCTION_DATE");
						String sanctionDateValidationType="emptyCheck";
						String sanctionDateDisplayName ="Sanction Date";
						String strArrSanctionDate [] = {sanctionDateFieldName,sanctionDateFieldValue,sanctionDateValidationType,sanctionDateDisplayName};
						
						mandatoryFieldsList.add(strArrSanctionDate);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_DISBURSAL_DATE"))!=null)
					{
						String disbursalDateFieldName ="ICICILOMBARD_HT_DISBURSAL_DATE";
						String disbursalDateFieldValue = formObject.getNGValue("ICICILOMBARD_HT_DISBURSAL_DATE");
						String disbursalDateValidationType="emptyCheck";
						String disbursalDateDisplayName ="Disbursal Date";
						String strArrDisbursalDate [] = {disbursalDateFieldName,disbursalDateFieldValue,disbursalDateValidationType,disbursalDateDisplayName};
						
						mandatoryFieldsList.add(strArrDisbursalDate);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_OCCUPATION"))!=null)
					{
						String occupationFieldName ="ICICILOMBARD_HT_OCCUPATION";
						String occupationFieldValue = formObject.getNGValue("ICICILOMBARD_HT_OCCUPATION");
						String occupationValidationType="emptyCheck";
						String occupationDisplayName ="Occupation";
						String strArrOccupation [] = {occupationFieldName,occupationFieldValue,occupationValidationType,occupationDisplayName};
						
						mandatoryFieldsList.add(strArrOccupation);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_PAN"))!=null)
					{
						String panFieldName ="ICICILOMBARD_HT_PAN";
						String panFieldValue = formObject.getNGValue("ICICILOMBARD_HT_PAN");
						String panValidationType="emptyCheck";
						String panDisplayName ="PAN";
						String strArrPAN [] = {panFieldName,panFieldValue,panValidationType,panDisplayName};
						
						mandatoryFieldsList.add(strArrPAN);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_SOURCE_BUSINESS"))!=null)
					{
						String sourceBusinessFieldName ="ICICILOMBARD_HT_SOURCE_BUSINESS";
						String sourceBusinessFieldValue = formObject.getNGValue("ICICILOMBARD_HT_SOURCE_BUSINESS");
						String sourceBusinessValidationType="emptyCheck";
						String sourceBusinessDisplayName ="Source Business";
						String strArrSourceBusiness [] = {sourceBusinessFieldName,sourceBusinessFieldValue,sourceBusinessValidationType,sourceBusinessDisplayName};
						
						mandatoryFieldsList.add(strArrSourceBusiness);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_SECONDARY_DEAL_NO"))!=null)
					{
						String secondarydealNoFieldName ="ICICILOMBARD_HT_SECONDARY_DEAL_NO";
						String secondarydealNoFieldValue = formObject.getNGValue("ICICILOMBARD_HT_SECONDARY_DEAL_NO");
						String secondarydealNoValidationType="emptyCheck";
						String secondarydealNoDisplayName ="Secondary Deal No";
						String strArrSecondarydealNo [] = {secondarydealNoFieldName,secondarydealNoFieldValue,secondarydealNoValidationType,secondarydealNoDisplayName};
						
						mandatoryFieldsList.add(strArrSecondarydealNo);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_PRIMARY_VERTICAL"))!=null)
					{
						String primaryVerticalFieldName ="ICICILOMBARD_HT_PRIMARY_VERTICAL";
						String primaryVerticalFieldValue = formObject.getNGValue("ICICILOMBARD_HT_PRIMARY_VERTICAL");
						String primaryVerticalValidationType="emptyCheck";
						String primaryVerticalDisplayName ="Primary Vertical";
						String strArrPrimaryVertical [] = {primaryVerticalFieldName,primaryVerticalFieldValue,primaryVerticalValidationType,primaryVerticalDisplayName};
						
						mandatoryFieldsList.add(strArrPrimaryVertical);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_SECONDARY_VERTICAL"))!=null)
					{
						String secondaryVerticalFieldName ="ICICILOMBARD_HT_SECONDARY_VERTICAL";
						String secondaryVerticalFieldValue = formObject.getNGValue("ICICILOMBARD_HT_SECONDARY_VERTICAL");
						String secondaryVerticalValidationType="emptyCheck";
						String secondaryVerticalDisplayName ="Secondary Vertical";
						String strArrSecondaryVertical [] = {secondaryVerticalFieldName,secondaryVerticalFieldValue,secondaryVerticalValidationType,secondaryVerticalDisplayName};
						
						mandatoryFieldsList.add(strArrSecondaryVertical);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_REFERENCE1"))!=null)
					{
						String reference1FieldName ="ICICILOMBARD_HT_REFERENCE1";
						String reference1FieldValue = formObject.getNGValue("ICICILOMBARD_HT_REFERENCE1");
						String reference1ValidationType="emptyCheck";
						String reference1DisplayName ="Reference1";
						String strArrReference1 [] = {reference1FieldName,reference1FieldValue,reference1ValidationType,reference1DisplayName};
						
						mandatoryFieldsList.add(strArrReference1);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_REFERENCE2"))!=null)
					{
						String reference2FieldName ="ICICILOMBARD_HT_REFERENCE1";
						String reference2FieldValue = formObject.getNGValue("ICICILOMBARD_HT_REFERENCE1");
						String reference2ValidationType="emptyCheck";
						String reference2DisplayName ="Reference2";
						String strArrReference2 [] = {reference2FieldName,reference2FieldValue,reference2ValidationType,reference2DisplayName};
						
						mandatoryFieldsList.add(strArrReference2);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_IL_LOCATION"))!=null)
					{
						String ilLOcationFieldName ="ICICILOMBARD_HT_IL_LOCATION";
						String ilLOcationFieldValue = formObject.getNGValue("ICICILOMBARD_HT_IL_LOCATION");
						String ilLOcationValidationType="emptyCheck";
						String ilLOcationDisplayName ="IL LOcation";
						String strArrIlLOcation [] = {ilLOcationFieldName,ilLOcationFieldValue,ilLOcationValidationType,ilLOcationDisplayName};
						
						mandatoryFieldsList.add(strArrIlLOcation);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_IL_LOCATION_CODE"))!=null)
					{
						String ilLOcationCodeFieldName ="ICICILOMBARD_HT_IL_LOCATION_CODE";
						String ilLOcationCodeFieldValue = formObject.getNGValue("ICICILOMBARD_HT_IL_LOCATION_CODE");
						String ilLOcationCodeValidationType="emptyCheck";
						String ilLOcationCodeDisplayName ="IL LOcation Code";
						String strArrIlLOcationCode [] = {ilLOcationCodeFieldName,ilLOcationCodeFieldValue,ilLOcationCodeValidationType,ilLOcationCodeDisplayName};
						
						mandatoryFieldsList.add(strArrIlLOcationCode);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_CHANNEL_SOURCE"))!=null)
					{
						String channelSourceFieldName ="ICICILOMBARD_HT_CHANNEL_SOURCE";
						String channelSourceFieldValue = formObject.getNGValue("ICICILOMBARD_HT_CHANNEL_SOURCE");
						String channelSourceValidationType="emptyCheck";
						String channelSourceDisplayName ="Channel Source";
						String strArrChannelSource [] = {channelSourceFieldName,channelSourceFieldValue,channelSourceValidationType,channelSourceDisplayName};
						
						mandatoryFieldsList.add(strArrChannelSource);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_BANK_BRANCH_NAME"))!=null)
					{
						String bankBranchNameFieldName ="ICICILOMBARD_HT_BANK_BRANCH_NAME";
						String bankBranchNameFieldValue = formObject.getNGValue("ICICILOMBARD_HT_BANK_BRANCH_NAME");
						String bankBranchNameValidationType="emptyCheck";
						String bankBranchNameDisplayName ="Bank Branch Name";
						String strArrBankBranchName [] = {bankBranchNameFieldName,bankBranchNameFieldValue,bankBranchNameValidationType,bankBranchNameDisplayName};
						
						mandatoryFieldsList.add(strArrBankBranchName);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_EMPCODE_CSO"))!=null)
					{
						String empoyeeCSOFieldName ="ICICILOMBARD_HT_EMPCODE_CSO";
						String empoyeeCSOFieldValue = formObject.getNGValue("ICICILOMBARD_HT_EMPCODE_CSO");
						String empoyeeCSOValidationType="emptyCheck";
						String empoyeeCSODisplayName ="Empoyee CSO";
						String strArrEmpoyeeCSO [] = {empoyeeCSOFieldName,empoyeeCSOFieldValue,empoyeeCSOValidationType,empoyeeCSODisplayName};
						
						mandatoryFieldsList.add(strArrEmpoyeeCSO);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_PRIVILEGE_BANKER_CODE"))!=null)
					{
						String priviledgeBankerCodeFieldName ="ICICILOMBARD_HT_PRIVILEGE_BANKER_CODE";
						String priviledgeBankerCodeFieldValue = formObject.getNGValue("ICICILOMBARD_HT_PRIVILEGE_BANKER_CODE");
						String priviledgeBankerCodeValidationType="emptyCheck";
						String priviledgeBankerCodeDisplayName ="Priviledge Banker Code";
						
						String strArrPriviledgeBankerCode [] = {priviledgeBankerCodeFieldName,priviledgeBankerCodeValidationType,priviledgeBankerCodeValidationType,priviledgeBankerCodeDisplayName};
						
						mandatoryFieldsList.add(strArrPriviledgeBankerCode);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_WRM"))!=null)
					{
						String wrmFieldName ="ICICILOMBARD_HT_WRM";
						String wrmFieldValue = formObject.getNGValue("ICICILOMBARD_HT_WRM");
						String wrmValidationType="emptyCheck";
						String wrmDisplayName ="WRM";
						
						String strArrWRM [] = {wrmFieldName,wrmFieldValue,wrmValidationType,wrmDisplayName};
						
						mandatoryFieldsList.add(strArrWRM);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_WRE"))!=null)
					{
						String wreFieldName ="ICICILOMBARD_HT_WRE";
						String wreFieldValue = formObject.getNGValue("ICICILOMBARD_HT_WRE");
						String wreValidationType="emptyCheck";
						String wreDisplayName ="WRE";
						
						String strArrWRE [] = {wreFieldName,wreFieldValue,wreValidationType,wreDisplayName};
						
						mandatoryFieldsList.add(strArrWRE);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_CHANNEL_EMP_INFO"))!=null)
					{
						String channelEmpInfoFieldName ="ICICILOMBARD_HT_CHANNEL_EMP_INFO";
						String channelEmpInfoFieldValue = formObject.getNGValue("ICICILOMBARD_HT_CHANNEL_EMP_INFO");
						String channelEmpInfoValidationType="emptyCheck";
						String channelEmpInfoDisplayName ="Channel Emp Info";
						
						String strArrChannelEmpInfo [] = {channelEmpInfoFieldName,channelEmpInfoFieldValue,channelEmpInfoValidationType,channelEmpInfoDisplayName};
						
						mandatoryFieldsList.add(strArrChannelEmpInfo);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_BRANCH_ID_UBO_NAME"))!=null)
					{
						String branchIdUBONameFieldName ="ICICILOMBARD_HT_BRANCH_ID_UBO_NAME";
						String branchIdUBONameFieldValue = formObject.getNGValue("ICICILOMBARD_HT_BRANCH_ID_UBO_NAME");
						String branchIdUBONameValidationType="emptyCheck";
						String branchIdUBONameDisplayName ="Branch ID UBO Name";
						
						String strArrBranchIdUBOName [] = {branchIdUBONameFieldName,branchIdUBONameFieldValue,branchIdUBONameValidationType,branchIdUBONameDisplayName};
						
						mandatoryFieldsList.add(strArrBranchIdUBOName);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_BSM_ID"))!=null)
					{
						String bsmIdFieldName ="ICICILOMBARD_HT_BSM_ID";
						String bsmIdFieldValue = formObject.getNGValue("ICICILOMBARD_HT_BSM_ID");
						String bsmIdValidationType="emptyCheck";
						String bsmIdDisplayName ="BSM ID";
						
						String strArrBsmId [] = {bsmIdFieldName,bsmIdFieldValue,bsmIdValidationType,bsmIdDisplayName};
						
						mandatoryFieldsList.add(strArrBsmId);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_BCM_ID"))!=null)
					{
						String bcmIdFieldName ="ICICILOMBARD_HT_BCM_ID";
						String bcmIdFieldValue = formObject.getNGValue("ICICILOMBARD_HT_BCM_ID");
						String bcmIdValidationType="emptyCheck";
						String bcmIdDisplayName ="BCM ID";
						
						String strArrBcmId [] = {bcmIdFieldName,bcmIdFieldValue,bcmIdValidationType,bcmIdDisplayName};
						
						mandatoryFieldsList.add(strArrBcmId);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_RO_DSA_COUNSELOR_ID"))!=null)
					{
						String roDSACounselorIdFieldName ="ICICILOMBARD_HT_RO_DSA_COUNSELOR_ID";
						String roDSACounselorIdIdFieldValue = formObject.getNGValue("ICICILOMBARD_HT_RO_DSA_COUNSELOR_ID");
						String roDSACounselorIdValidationType="emptyCheck";
						String roDSACounselorIdDisplayName ="RO DSA Counselor Id";
						
						String strArrRoDSACounselorId [] = {roDSACounselorIdFieldName,roDSACounselorIdIdFieldValue,roDSACounselorIdValidationType,roDSACounselorIdDisplayName};
						
						mandatoryFieldsList.add(strArrRoDSACounselorId);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_TRAINEE_ID"))!=null)
					{
						String traineeIdFieldName ="ICICILOMBARD_HT_TRAINEE_ID";
						String traineeIdFieldValue = formObject.getNGValue("ICICILOMBARD_HT_TRAINEE_ID");
						String traineeIdValidationType="emptyCheck";
						String traineeIdDisplayName ="trainee ID";
						
						String strArrTraineeId [] = {traineeIdFieldName,traineeIdFieldValue,traineeIdValidationType,traineeIdDisplayName};
						
						mandatoryFieldsList.add(strArrTraineeId);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_TA_CODE"))!=null)
					{
						String taCodeFieldName ="ICICILOMBARD_HT_TA_CODE";
						String taCodeFieldValue = formObject.getNGValue("ICICILOMBARD_HT_TA_CODE");
						String taCodeValidationType="emptyCheck";
						String taCodeDisplayName ="TA Code";
						
						String strArrTACode [] = {taCodeFieldName,taCodeFieldValue,taCodeValidationType,taCodeDisplayName};
						
						mandatoryFieldsList.add(strArrTACode);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_CENTER_CODE_NAME"))!=null)
					{
						String centerCodeNameFieldName ="ICICILOMBARD_HT_CENTER_CODE_NAME";
						String centerCodeNameFieldValue = formObject.getNGValue("ICICILOMBARD_HT_CENTER_CODE_NAME");
						String centerCodeNameValidationType="emptyCheck";
						String centerCodeNameDisplayName ="Center Code Name";
						
						String strArrCenterCodeName [] = {centerCodeNameFieldName,centerCodeNameFieldValue,centerCodeNameValidationType,centerCodeNameDisplayName};
						
						mandatoryFieldsList.add(strArrCenterCodeName);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_RM_EMPLOYEE"))!=null)
					{
						String rmEmployeeFieldName ="ICICILOMBARD_HT_RM_EMPLOYEE";
						String rmEmployeeFieldValue = formObject.getNGValue("ICICILOMBARD_HT_RM_EMPLOYEE");
						String rmEmployeeValidationType="emptyCheck";
						String rmEmployeeDisplayName ="RM Employee";
						
						String strArrRmEmployee [] = {rmEmployeeFieldName,rmEmployeeFieldValue,rmEmployeeValidationType,rmEmployeeDisplayName};
						
						mandatoryFieldsList.add(strArrRmEmployee);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_SUB_BROKER_CODE"))!=null)
					{
						String subBrokerCodeFieldName ="ICICILOMBARD_HT_SUB_BROKER_CODE";
						String subBrokerCodeFieldValue = formObject.getNGValue("ICICILOMBARD_HT_SUB_BROKER_CODE");
						String subBrokerCodeValidationType="emptyCheck";
						String subBrokerCodeDisplayName ="Suh Broker Code";
						
						String strArrSubBrokerCode [] = {subBrokerCodeFieldName,subBrokerCodeFieldValue,subBrokerCodeValidationType,subBrokerCodeDisplayName};
						
						mandatoryFieldsList.add(strArrSubBrokerCode);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_TERRITORY_MANAGER"))!=null)
					{
						String territoryManagerFieldName ="ICICILOMBARD_HT_TERRITORY_MANAGER";
						String territoryManagerFieldValue = formObject.getNGValue("ICICILOMBARD_HT_TERRITORY_MANAGER");
						String territoryManagerValidationType="emptyCheck";
						String territoryManagerDisplayName ="Territory Manager";
						
						String strArrTerritoryManager [] = {territoryManagerFieldName,territoryManagerFieldValue,territoryManagerValidationType,territoryManagerDisplayName};
						
						mandatoryFieldsList.add(strArrTerritoryManager);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_SUB_BROKER"))!=null)
					{
						String subBrokerFieldName ="ICICILOMBARD_HT_SUB_BROKER";
						String subBrokerFieldValue = formObject.getNGValue("ICICILOMBARD_HT_SUB_BROKER");
						String subBrokerValidationType="emptyCheck";
						String subBrokerDisplayName ="Sub sBroker";
						
						String strArrSubBroker [] = {subBrokerFieldName,subBrokerFieldValue,subBrokerValidationType,subBrokerDisplayName};
						
						mandatoryFieldsList.add(strArrSubBroker);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_ECS_MANDATE"))!=null)
					{
						String ecsMandateFieldName ="ICICILOMBARD_HT_ECS_MANDATE";
						String ecsMandateFieldValue = formObject.getNGValue("ICICILOMBARD_HT_ECS_MANDATE");
						String ecsMandateValidationType="emptyCheck";
						String ecsMandateDisplayName ="ECS Mandate";
						
						String strArrEcsMandate [] = {ecsMandateFieldName,ecsMandateFieldValue,ecsMandateValidationType,ecsMandateDisplayName};
						
						mandatoryFieldsList.add(strArrEcsMandate);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_PAYMENT_ID"))!=null)
					{
						String paymentIdFieldName ="ICICILOMBARD_HT_PAYMENT_ID";
						String paymentIdFieldValue = formObject.getNGValue("ICICILOMBARD_HT_PAYMENT_ID");
						String paymentIdValidationType="emptyCheck";
						String paymentIdDisplayName ="PaymentId ";
						
						String strArrPaymentId [] = {paymentIdFieldName,paymentIdFieldValue,paymentIdValidationType,paymentIdDisplayName};
						
						mandatoryFieldsList.add(strArrPaymentId);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_CUSTOMER_ID"))!=null)
					{
						String customerIdFieldName ="ICICILOMBARD_HT_CUSTOMER_ID";
						String customerIdFieldValue = formObject.getNGValue("ICICILOMBARD_HT_CUSTOMER_ID");
						String customerIdValidationType="emptyCheck";
						String customerIdDisplayName =" Customer Id ";
						
						String strArrCustomerId [] = {customerIdFieldName,customerIdFieldValue,customerIdValidationType,customerIdDisplayName};
						
						mandatoryFieldsList.add(strArrCustomerId);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_IS_EMI"))!=null)
					{
						String isEMIFieldName ="ICICILOMBARD_HT_IS_EMI";
						String isEMIFieldValue = formObject.getNGValue("ICICILOMBARD_HT_IS_EMI");
						String isEMIValidationType="emptyCheck";
						String isEMIDisplayName =" IS EMI ";
						
						String strArrIsEMI [] = {isEMIFieldName,isEMIFieldValue,isEMIValidationType,isEMIDisplayName};
						
						mandatoryFieldsList.add(strArrIsEMI);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_UNIQUE_ID"))!=null)
					{
						String uniqueIdFieldName ="ICICILOMBARD_HT_UNIQUE_ID";
						String uniqueIdFieldValue = formObject.getNGValue("ICICILOMBARD_HT_UNIQUE_ID");
						String uniqueIdValidationType="emptyCheck";
						String uniqueIdDisplayName =" Unique ID ";
						
						String strArrUniqueId [] = {uniqueIdFieldName,uniqueIdFieldValue,uniqueIdValidationType,uniqueIdDisplayName};
						
						mandatoryFieldsList.add(strArrUniqueId);
					}
					else if((formObject.getNGValue("ICICILOMBARD_HT_REMARKS"))!=null)
					{
						String remarksFieldName ="ICICILOMBARD_HT_REMARKS";
						String remarksFieldValue = formObject.getNGValue("ICICILOMBARD_HT_REMARKS");
						String remarksValidationType="emptyCheck";
						String remarksDisplayName =" Remarks";
						
						String strArrRemarks [] = {remarksFieldName,remarksFieldValue,remarksValidationType,remarksDisplayName};
						
						mandatoryFieldsList.add(strArrRemarks);
					}
				
				return 	mandatoryFieldsList;	
		}
		 
		 
	
	
	}