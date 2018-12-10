//----------------------------------------------------------------------------
//			NEWGEN SOFTWARE TECHNOLOGIES LIMITED
//	Group				: Application - Products
//	Product / Project	: Form Builder
//	Module				: Form Builde rApplet
//	File Name			: XMLParser.java
//	Author				: Ashish Mangla
//	Date written		: 03/06/2003
//	Description			: Parses a XML for values.
//	----------------------------------------------------------------------------
//		CHANGE HISTORY
//	----------------------------------------------------------------------------
//	Date		Change By		Change Description (Bug No. If Any)
//	----------------------------------------------------------------------------

package com.newgen.formApplet;

public class XMLParser {
    private String parseString;
    private String copyString;
    private int IndexOfPrevSrch;

    public XMLParser() {}
	public XMLParser(String parseThisString) {
        copyString = new String(parseThisString);
		parseString = toUpperCase(copyString,0,0);
    }

// ----------------------------------------------------------------
// Function Name 		: setInputXML
// Date Written			: 27/02/2002
// Author				: Prashant
// Input Parameters		: String ParseThisString
// Output Parameters	: None
// Return Values		: void
// Description			: sets the XML for parsing
// ----------------------------------------------------------------
    public void setInputXML( String ParseThisString) {
        if (ParseThisString != null){
			copyString = new String(ParseThisString);
			parseString = toUpperCase(copyString,0,0);
			IndexOfPrevSrch = 0;
		}
		else{
			parseString = null;
			copyString = null;
			IndexOfPrevSrch = 0;
		}
	}

// ----------------------------------------------------------------
// Function Name 		: getServiceName
// Date Written			: 27/02/2002
// Author				: Prashant
// Input Parameters		: None
// Output Parameters	: None
// Return Values		: String
// Description			: gets the value of Option Tag
// ----------------------------------------------------------------
    public String getServiceName(){
		try{
			return new String(copyString.substring(parseString.indexOf(toUpperCase("<Option>",0,0))+ (new String(toUpperCase("<Option>",0,0))).length(),parseString.indexOf(toUpperCase("</Option>",0,0))));
		}
		catch(StringIndexOutOfBoundsException e){
			throw e;
		}
    }

// ----------------------------------------------------------------
// Function Name 		: getServiceName
// Date Written			: 27/02/2002
// Author				: Prashant
// Input Parameters		: char
// Output Parameters	: None
// Return Values		: String
// Description			: gets the value of Admin Tag
// ----------------------------------------------------------------
	public String getServiceName(char chr){
		try{
			if(chr == 'A')
				return new String(copyString.substring(parseString.indexOf("<AdminOption>".toUpperCase())+ (new String("<AdminOption>".toUpperCase())).length(),parseString.indexOf("</AdminOption>".toUpperCase())));
			return "";
		}
		catch(StringIndexOutOfBoundsException e){
			return "NoServiceFound";
		}
    }


// ----------------------------------------------------------------
// Function Name 		: validateXML
// Date Written			: 27/02/2002
// Author				: Prashant
// Input Parameters		: None
// Output Parameters	: None
// Return Values		: boolean
// Description			: validates the XML
// ----------------------------------------------------------------
    public boolean validateXML(){
		try{
			if(parseString.indexOf("<?xml version=\"1.0\"?>".toUpperCase()) == -1)
				return false;
			return true;
		}
		catch(StringIndexOutOfBoundsException e){
			return false;
		}
    }

// ----------------------------------------------------------------
// Function Name 		: getValueOf
// Date Written			: 27/02/2002
// Author				: Prashant
// Input Parameters		: String
// Output Parameters	: None
// Return Values		: String
// Description			: parse the entire XML for the tag
// ----------------------------------------------------------------
    public String getValueOf(String valueOf) {
        try{
        	return new String( copyString.substring( parseString.indexOf("<" + toUpperCase(valueOf,0,0) + ">")+ valueOf.length() + 2 ,parseString.indexOf("</" + toUpperCase(valueOf,0,0) + ">") ) );
        }
        catch(StringIndexOutOfBoundsException e){
        	return "";
        }
    }

// ----------------------------------------------------------------
// Function Name 		: getValueOf
// Date Written			: 27/02/2002
// Author				: Prashant
// Input Parameters		: String , String
// Output Parameters	: None
// Return Values		: String
// Description			: parse the entire XML for the tag without upperCase
// ----------------------------------------------------------------
    public String getValueOf(String valueOf,String type) {
		try{
			if(type.equalsIgnoreCase("Binary")){
				int startPos = copyString.indexOf("<"+valueOf+">");
				if(startPos == -1)
					return "";
				int endPos = copyString.lastIndexOf("</"+valueOf +">");
				startPos += new String("<"+valueOf +">").length();
				return copyString.substring(startPos,endPos);
			}
			else return "";
		}
		catch(StringIndexOutOfBoundsException e){
			return "";
		}
    }

// ----------------------------------------------------------------
// Function Name 		: getValueOf
// Date Written			: 27/02/2002
// Author				: Prashant
// Input Parameters		: String , boolean
// Output Parameters	: None
// Return Values		: String
// Description			: returns the last value of the tag
// ----------------------------------------------------------------
	public String getValueOf(String valueOf, boolean fromlast) {
		try{
			if(fromlast)
				return new String( copyString.substring( parseString.indexOf("<" + toUpperCase(valueOf,0,0) + ">")+ valueOf.length() + 2 , parseString.lastIndexOf("</" + toUpperCase(valueOf,0,0) + ">") ) );
			else
	        	return new String( copyString.substring( parseString.indexOf("<" + toUpperCase(valueOf,0,0) + ">")+ valueOf.length() + 2 , parseString.indexOf("</" + toUpperCase(valueOf,0,0) + ">") ) );
		}
		catch(StringIndexOutOfBoundsException e){
			return "";
		}
	}

// ----------------------------------------------------------------
// Function Name 		: getValueOf
// Date Written			: 27/02/2002
// Author				: Prashant
// Input Parameters		: String , int , int
// Output Parameters	: None
// Return Values		: String
// Description			: parse the XML from Start Position till the end
// ----------------------------------------------------------------
   public String getValueOf(String valueOf, int start, int end ) {
		try{
			if (start >= 0){
				int endIndex = parseString.indexOf("</" + toUpperCase(valueOf,0,0) + ">", start);
	        	if ( endIndex > start &&  ( end == 0 || end >= endIndex ) )
					return new String(copyString.substring(parseString.indexOf("<" + toUpperCase(valueOf,0,0) + ">", start) + valueOf.length() + 2, endIndex));
			}
			return "";
		}
        catch(StringIndexOutOfBoundsException e){
        	return "";
        }
    }

// ----------------------------------------------------------------
// Function Name 		: getstartIndex
// Date Written			: 27/02/2002
// Author				: Prashant
// Input Parameters		: String , int , int
// Output Parameters	: None
// Return Values		: int
// Description			: gets the Starting index of the tag between Start , End
// ----------------------------------------------------------------
    public int getStartIndex(String tag, int start, int end ) {
        try{
			if (start >= 0){
				int startIndex = parseString.indexOf("<" + toUpperCase(tag,0,0) + ">", start);
				if ( startIndex >= start &&  ( end == 0 || end >= startIndex ) )
		    		return startIndex+tag.length()+2;
			}
			return -1;
		}
        catch(StringIndexOutOfBoundsException e){
        	return -1;
        }
    }

// ----------------------------------------------------------------
// Function Name 		: getendIndex
// Date Written			: 27/02/2002
// Author				: Prashant
// Input Parameters		: String , int , int
// Output Parameters	: None
// Return Values		: int
// Description			: gets the starting index of the closing tag between Start , End
// ----------------------------------------------------------------
   public int getEndIndex(String tag, int start, int end ) {
        try	{
			if (start >= 0){
				int endIndex = parseString.indexOf("</" + toUpperCase(tag,0,0) + ">", start);
	        	if ( endIndex > start &&  ( end == 0 || end >= endIndex ))
		    		return endIndex;
			}
			return -1;
		}
        catch(StringIndexOutOfBoundsException e){
        	return -1;
        }
    }

// ----------------------------------------------------------------
// Function Name 		: getTagstartIndex
// Date Written			: 27/02/2002
// Author				: Prashant
// Input Parameters		: String , int , int
// Output Parameters	: None
// Return Values		: int
// Description			: gets the starting index of the opening tag between Start , End
// ----------------------------------------------------------------
    public int getTagStartIndex(String tag, int start, int end ) {
        try{
			if (start >= 0){
				int startIndex = parseString.indexOf("<" + toUpperCase(tag,0,0) + ">", start);
				if ( startIndex >= start &&  ( end == 0 || end >= startIndex ) )
		    		return startIndex;
			}
			return -1;
		}
        catch(StringIndexOutOfBoundsException e){
        	return -1;
        }
    }

// ----------------------------------------------------------------
// Function Name 		: getTagEndIndex
// Date Written			: 27/02/2002
// Author				: Prashant
// Input Parameters		: String , int , int
// Output Parameters	: None
// Return Values		: int
// Description			: gets the ending index of the closing tag between Start , End
// ----------------------------------------------------------------
    public int getTagEndIndex(String tag, int start, int end ) {
        try	{
			if (start >= 0){
				int endIndex = parseString.indexOf("</" + toUpperCase(tag,0,0) + ">", start);
	        	if ( endIndex > start &&  ( end == 0 || end >= endIndex ))
		    		return endIndex + tag.length() + 3;
			}
			return -1;
		}
        catch(StringIndexOutOfBoundsException e){
        	return -1;
        }
    }

// ----------------------------------------------------------------
// Function Name 		: getFirstValueOf
// Date Written			: 27/02/2002
// Author				: Prashant
// Input Parameters		: String
// Output Parameters	: None
// Return Values		: String
// Description			: gets the first value of the tag
// ----------------------------------------------------------------
    public String getFirstValueOf(String valueOf){
        try{
			IndexOfPrevSrch = parseString.indexOf("<"+ toUpperCase(valueOf,0,0) +">");
			return new String(copyString.substring(IndexOfPrevSrch + valueOf.length() +2 ,parseString.indexOf("</" + toUpperCase(valueOf,0,0) + ">")));
        }
        catch(StringIndexOutOfBoundsException e){
        	return "";
        }
    }

// ----------------------------------------------------------------
// Function Name 		: getFirstValueOf
// Date Written			: 27/02/2002
// Author				: Prashant
// Input Parameters		: String
// Output Parameters	: None
// Return Values		: String
// Description			: gets the first value of the tag from the Start index
// ----------------------------------------------------------------
    public String getFirstValueOf(String valueOf , int start){
        try{
			IndexOfPrevSrch = parseString.indexOf("<" + toUpperCase(valueOf,0,0) + ">" , start);
			return new String(copyString.substring(IndexOfPrevSrch +  valueOf.length() +2 ,parseString.indexOf("</" + toUpperCase(valueOf,0,0) + ">",start )));
        }
        catch(StringIndexOutOfBoundsException e){
        	return "";
        }
    }

// ----------------------------------------------------------------
// Function Name 		: getNextValueOf
// Date Written			: 27/02/2002
// Author				: Prashant
// Input Parameters		: String
// Output Parameters	: None
// Return Values		: String
// Description			: gets the next value of the tag from the previous index
// ----------------------------------------------------------------
    public String getNextValueOf(String valueOf) {
        try{
			IndexOfPrevSrch = parseString.indexOf(("<" + toUpperCase(valueOf,0,0) + ">"), IndexOfPrevSrch + valueOf.length() + 2 );
			return new String(copyString.substring(IndexOfPrevSrch + valueOf.length() +2 ,parseString.indexOf("</" + toUpperCase(valueOf,0,0) + ">",IndexOfPrevSrch)));
        }
        catch(StringIndexOutOfBoundsException e){
        	return "";
        }
    }

// ----------------------------------------------------------------
// Function Name 		: getNoOfFields
// Date Written			: 27/02/2002
// Author				: Prashant
// Input Parameters		: String
// Output Parameters	: None
// Return Values		: int
// Description			: gets the no of the given tag in the XML
// ----------------------------------------------------------------
    public int getNoOfFields(String tag) {
    	int noOfFields = 0;
    	int beginPos = 0;
    	try {
			tag = toUpperCase(tag,0,0) + ">";
    		while(parseString.indexOf("<" +tag, beginPos) != -1){
    			noOfFields++;
    			beginPos = parseString.indexOf("</"+tag, beginPos) ;
				if (beginPos == -1 )
					break;
				beginPos	+= tag.length() + 2;
			}
    	}
    	catch (StringIndexOutOfBoundsException e){}
    	return noOfFields;
    }

// ----------------------------------------------------------------
// Function Name 		: getNoOfFields
// Date Written			: 27/02/2002
// Author				: Prashant
// Input Parameters		: String , int ,end
// Output Parameters	: None
// Return Values		: int
// Description			: gets the no of the given tag in the XML between Start and end
// ----------------------------------------------------------------
    public int getNoOfFields(String tag, int startPos ,int endPos) {
    	int noOfFields = 0;
    	int beginPos = startPos;
    	try {
			tag = toUpperCase(tag,0,0) + ">";
    		while((parseString.indexOf("<"+tag, beginPos) != -1) && (beginPos < endPos || endPos == 0) ){
    			beginPos = parseString.indexOf("</"+tag, beginPos)+ tag.length() + 2;
    			if(beginPos != -1 && (beginPos <= endPos || endPos == 0))
    				noOfFields++;
    		}
    	}
    	catch (StringIndexOutOfBoundsException e){}
    	return noOfFields;
    }

// ----------------------------------------------------------------
// Function Name 		: convertToSQLString
// Date Written			: 27/02/2002
// Author				: Prashant
// Input Parameters		: String
// Output Parameters	: None
// Return Values		: String
// Description			: Converts the given String to SQL wildcard format
// ----------------------------------------------------------------
	public String convertToSQLString(String strName){
        try{
            int count = strName.indexOf("[");
            while(count != -1){
                strName = strName.substring(0,count)+"[[]"+strName.substring(count+1,strName.length());
                count = strName.indexOf("[",count+2);
            }
        }
        catch(Exception e){
        }
        try{
            int count = strName.indexOf("_");
            while(count != -1){
                strName = strName.substring(0,count)+"[_]"+strName.substring(count+1,strName.length());
                count = strName.indexOf("_",count+2);
            }
        }
        catch(Exception e){
        }
        try{
            int count = strName.indexOf("%");
            while(count != -1){
                strName = strName.substring(0,count)+"[%]"+strName.substring(count+1,strName.length());
                count = strName.indexOf("%",count+2);
            }
        }
        catch(Exception e){
        }
        strName = strName.replace('?','_');
 	 	return strName;
	}

// ----------------------------------------------------------------
// Function Name 		: getValueOf
// Date Written			: 16/01/2002
// Author				: Prashant
// Input Parameters		: String , String
// Output Parameters	: None
// Return Values		: String
// Description			: parse the entire XML for the tag without upperCase
// ----------------------------------------------------------------
    public String getValueOf(String valueOf,String type,int from , int end) {
		try{
			if(type.equalsIgnoreCase("Binary")){
				int startPos = copyString.indexOf("<"+valueOf+">",from);
				if(startPos == -1)
					return "";
				int endPos = copyString.indexOf("</"+valueOf +">",from);
				if (endPos > end)
					return "";
				startPos += new String("<"+valueOf +">").length();
				return copyString.substring(startPos,endPos);
			}
			else return "";
		}
		catch(StringIndexOutOfBoundsException e){
			return "";
		}
    }

    public String toUpperCase(String valueOf, int begin, int end) throws StringIndexOutOfBoundsException{
		//if(begin > end )
		//	throw new StringIndexOutOfBoundsException();
		String returnStr = "";
		try{
			int count = valueOf.length();
			char[] strChar = new char[count];
			valueOf.getChars(0,count,strChar,0);
			while(count-- > 0)
				strChar[count] = Character.toUpperCase(strChar[count]);
			returnStr = new String(strChar);
		}
		catch(ArrayIndexOutOfBoundsException e){
		}
		return returnStr;
    }

// ----------------------------------------------------------------
// Function Name 		: changeValue
// Date Written			: 15/11/2002
// Author						: Prashant
// Input Parameters		: String , String , String
// Output Parameters	: None
// Return Values		: String
// Description			: parses the entire XML for the tag and changes its value
// ----------------------------------------------------------------
	public String changeValue(String ParseString, String TagName, String NewValue)
	{
		try
		{
			String ParseStringTmp = ParseString.toUpperCase();
			String StrTag = (new String("<"+TagName+">")).toUpperCase();

			int StartIndex = ParseStringTmp.indexOf( StrTag) + StrTag.length();
			int EndIndex = ParseStringTmp.indexOf((new String("</"+TagName+">")).toUpperCase());

			String RetStr = ParseString.substring(0, StartIndex);
			RetStr = RetStr + NewValue + ParseString.substring(EndIndex);
			return RetStr;
		}
    catch(Exception e){
        	return "";
    }
  }

// ----------------------------------------------------------------
// Function Name 		: changeValue
// Date Written			: 15/11/2002
// Author						: Prashant
// Input Parameters		: String , String , String
// Output Parameters	: None
// Return Values		: String
// Description			: parses the entire XML for the tag and changes its value
// ----------------------------------------------------------------
  public void changeValue(String TagName, String NewValue)
  {
    try
    {
      String StrTag = ("<"+TagName+">").toUpperCase();

      int StartIndex = parseString.indexOf( StrTag) ;
      if ( StartIndex > -1 )
      {
        StartIndex += StrTag.length();
        int EndIndex = parseString.indexOf(("</"+TagName+">").toUpperCase());

        String RetStr = copyString.substring(0, StartIndex);
        copyString = RetStr + NewValue + copyString.substring(EndIndex);
      }else
      {
        int EndIndex=StartIndex=parseString.lastIndexOf("</");
        String RetStr = copyString.substring(0, StartIndex);
        copyString = RetStr + "<"+TagName+">"+NewValue+"</"+TagName+">" + copyString.substring(EndIndex);
      }
      parseString = toUpperCase(copyString,0,0);
    }
    catch(Exception e)
    {
    }
  }

  public String toString()
  {
    return copyString;
  }
}//end of Class XMLParser
