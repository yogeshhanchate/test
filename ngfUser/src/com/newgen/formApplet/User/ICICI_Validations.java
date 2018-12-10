//----------------------------------------------------------------------------------------------------
//		NEWGEN SOFTWARE TECHNOLOGIES LIMITED
//Group						: APPPROJ
//Product / Project			: LnS
//Module					: NGFUSER
//File Name					: SBI-Validation.java
//Author					: Tarit Gupta
//Date written (DD/MM/YYYY)	: 23/06/2009
//Description				: Main Validation class.
//----------------------------------------------------------------------------------------------------
//			CHANGE HISTORY
//----------------------------------------------------------------------------------------------------
// Date			 Change By	 Change Description (Bug No. (If Any))
// (DD/MM/YYYY)
//----------------------------------------------------------------------------------------------------

package com.newgen.formApplet.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;
public class ICICI_Validations {
	private NGFPropInterface formObject = null;		
	
	
	static String[] M_REG_EXP_NAMES = {
						"Number",
						"Alpha",
						"AlphaWithSpace",
						"Alphanumeric",
						"AlphanumericWithSpace",
						"AlphanumericWithUnderscore",
                        "Amount",
						"NounNamesMaskMand",
						"AlphanumericMand",
						"AlphanumericWithUnderscoreMand",
						"NounNamesMaskMand",
						"GeneralTextMask",
						"TextMandatory",						
						"UM_Desgination"						
	};
	static String[] M_REG_EXP = {
				 "^[0-9]",
				 "^[a-zA-Z]",//Bug No:SBI_003,SBI_008,SBI_014,SBI_016,SBI_018,SBI_025-remove space
				 "^[a-zA-Z ]",// Added by Dinesh on 17082009 to allow space in the parameter of alpha type
				 "^[a-zA-Z0-9]",
				 "^[a-zA-Z0-9 ]",// Added by Dinesh on 15092009 to allow space in the parameter of alphanumeric type
				 "^[a-zA-Z0-9_]",
                 "^[0-9.]",
				 "^['&a-zA-Z0-9 ]",
				 "^[a-zA-Z]{1}[a-zA-Z0-9]",
				 "^[a-zA-Z]{1}[a-zA-Z0-9_]",
				 "^[a-zA-Z]{1}[a-zA-Z0-9 ]",
				 "^[a-zA-Z0-9\\-\\.\\,\\/#()\n\r ]",
                 "^[a-zA-Z]{1}[a-zA-Z0-9_]",
				 "^[a-zA-Z. ]"					
	};
	public static boolean validateText(String pstrValue,String sType) 
	{
		try 
		{	
				if(pstrValue!="" && sType!="")
				{
					for(int i=0;i<M_REG_EXP_NAMES.length;i++)
					{
							if(M_REG_EXP_NAMES[i].equalsIgnoreCase(sType))
							{
									String lstrPattern = M_REG_EXP[i]+ "*";
									Pattern lobjPattern = Pattern.compile(lstrPattern);
									Matcher lobjMatcher = lobjPattern.matcher(pstrValue);
									return lobjMatcher.matches();
								
							}

					}
					//System.out.println("Regular Expression Name not found");
					return false;
				}
				else
				return false;
			
		} 
		catch (Exception e) 
		{
            //System.out.println("Exception occurred in validateText():" +e.toString());
            e.printStackTrace();
        }

		return true;
	}
	
	public static boolean validateTextAgnstExpr(String pstrValue, String pstrPredefinedRegexName){
		try{
			if(pstrPredefinedRegexName!="" && pstrValue!=null){
				for(int i = 0; i < M_REG_EXP_NAMES.length; i++){
					if(pstrPredefinedRegexName==M_REG_EXP_NAMES[i]){
						String lstrPattern = M_REG_EXP[i] + "*$";
						//System.out.println(lstrPattern);
						Pattern lobjPattern = Pattern.compile(lstrPattern);
						Matcher lobjMatcher = lobjPattern.matcher(pstrValue);
						return lobjMatcher.matches();
					}
				}
				//System.out.println("Regular Expression Name not found");
				return false;
			}else{
				return false;
			}
		}
		catch(Exception lExcp){
			//System.out.println( "Exception: " + lExcp.toString());
			return false;
		}
	}
	
	
	public static boolean validateTextAgnstExpr(String pstrValue, String pstrPredefinedRegexName, Integer pintMinLength, Integer pintMaxLength){
		boolean lblnFlag = false;
		try{
			if(pstrPredefinedRegexName!="" && pstrValue!=null){
				for(int i = 0; i < M_REG_EXP_NAMES.length; i++){
					if(pstrPredefinedRegexName==M_REG_EXP_NAMES[i]){
						String lstrPattern = "";
						if(pintMinLength!=null)
							lstrPattern = M_REG_EXP[i] + "{"+pintMinLength+","+pintMaxLength+"}$";
						else
							lstrPattern = M_REG_EXP[i] + "{"+pintMaxLength+"}$";
						//System.out.println("lstrPattern: " + lstrPattern);
						Pattern lobjPattern = Pattern.compile(lstrPattern);
						Matcher lobjMatcher = lobjPattern.matcher(pstrValue);
						lblnFlag = lobjMatcher.matches();
						//System.out.println("lblnFlag1: " + lblnFlag);
						break;
					}
				}
				return lblnFlag;
			}else{
				return false;
			}
		}
		catch(Exception lExcp){
			//System.out.println( "Exception: " + lExcp.toString());
		}
		return lblnFlag;
	}
	
	public static boolean validateEmail(String pstrValue){
		try{
			String lstrPattern = "";
			lstrPattern = "([a-zA-Z0-9_.-])+@(([a-zA-Z0-9-])+.)+([a-zA-Z0-9]{2,4})+";
			if(!lstrPattern.equals("")){
				Pattern lobjPattern = Pattern.compile(lstrPattern);
				Matcher lobjMatcher = lobjPattern.matcher(pstrValue);
				return lobjMatcher.matches();
			}
			else 
				return false;
		}
		catch (Exception lExcp) {
			//System.out.println( "Exception: " + lExcp.toString());
			return false;
		}
	}

	public static boolean validateFloat(String pstrValue){
		try{
			String lstrPattern = "";
			lstrPattern = "^[-+]?[0-9]{1,10}(\\.?[0-9]{1,2})?$";
			if(!lstrPattern.equals("")){
				Pattern lobjPattern = Pattern.compile(lstrPattern);
				Matcher lobjMatcher = lobjPattern.matcher(pstrValue);
				return lobjMatcher.matches();
			}
			else 
				return false;
		}
		catch (Exception lExcp) {
			//System.out.println( "Exception: " + lExcp.toString());
			return false;
		}
	}
	
	public static boolean validateFloat(String pstrValue, Integer pintMin, Integer pintMax){
		try{
			String lstrPattern = "";
			if(pintMin==null)
				lstrPattern = "^[-+]?[0-9]{" + pintMax + "}(\\.?[0-9]{1,2})?$";
			else
				lstrPattern = "^[-+]?[0-9]{" + pintMin + "," + pintMax + "}(\\.?[0-9]{1,2})?$";
			if(!lstrPattern.equals("")){
				Pattern lobjPattern = Pattern.compile(lstrPattern);
				Matcher lobjMatcher = lobjPattern.matcher(pstrValue);
				return lobjMatcher.matches();
			}
			else 
				return false;
		}
		catch (Exception lExcp) {
			//System.out.println( "Exception: " + lExcp.toString());
			return false;
		}
	}
	public int sCompareDates(String sDate1,String sDate2)   // return 1 when Date1 is greater,0 when equal,2 when Date2 is greater
	{
	    System.out.println("sDate1 "+sDate1);
		System.out.println("sDate1 "+sDate2);
		int iYear1 = Integer.parseInt(sDate1.substring(7,11));
		int iYear2 = Integer.parseInt(sDate2.substring(6,10));

		if(iYear1 > iYear2)
			return 1;
		else if (iYear1 < iYear2)
			return 2;
		else
		{
			String sMonth1 = sDate1.substring(3,6);
			//System.out.println("sMonth1 "+sMonth1);
			int iMonth1=Integer.parseInt(retMnth(sMonth1));
			int iMonth2 = Integer.parseInt(sDate2.substring(3,5));
			
			if(iMonth1 > iMonth2)
				return 1;
			else if (iMonth1 < iMonth2)
				return 2;
			else
			{
				int iDay1 = Integer.parseInt(sDate1.substring(0,2));
				int iDay2 = Integer.parseInt(sDate2.substring(0,2));
			
				if(iDay1 > iDay2)
					return 1;
				else if (iDay1 < iDay2)
					return 2;
				else
					return 0;
			}
		}
	}
	public String retMnth(String strtempdate)
	{
		
		if(strtempdate.equalsIgnoreCase("JAN"))
			return "01";
		else if(strtempdate.equalsIgnoreCase("FEB"))
			return "02";
		else if(strtempdate.equalsIgnoreCase("MAR"))
			return "03";
		else if(strtempdate.equalsIgnoreCase("APR"))
			return "04";
		else if(strtempdate.equalsIgnoreCase("MAY"))
			return "05";
		else if(strtempdate.equalsIgnoreCase("JUN"))
			return "06";
		else if(strtempdate.equalsIgnoreCase("JUL"))
			return "07";
		else if(strtempdate.equalsIgnoreCase("AUG"))
			return "08";
		else if(strtempdate.equalsIgnoreCase("SEP"))
			return "09";
		else if(strtempdate.equalsIgnoreCase("OCT"))
			return "10";
		else if(strtempdate.equalsIgnoreCase("NOV"))
			return "11";
		else if(strtempdate.equalsIgnoreCase("DEC"))
			return "12";
		return "";
	}

	public boolean CompareDates(String d1,String d2,String Type){
		try{
			
			System.out.println( "d1"+d1);
			System.out.println( "d2"+d2);
			Calendar cal = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			Date Date1=new Date(d1);
			Date Date2=new Date(d2);
			System.out.println(Date1.compareTo(Date2));
			cal.setTime(Date1);
			cal2.setTime(Date2);
			System.out.println( "cal"+cal.getTimeInMillis());
			System.out.println( "cal2"+cal2.getTimeInMillis());
			if(Type.equals("Before") && (cal.before(cal2)))
			{
				System.out.println( "Before");
				return false;
				
			}
			else if(Type.equals("After") && (cal.after(cal2)))
			{
				System.out.println( "aflter");
				return false;
				
			}
		
		
		
		}
		catch (Exception lExcp) {
			//System.out.println( "Exception: " + lExcp.toString());
			return false;
		}
		return true;
	}

}