/***************************************************************************
*	Product		: OmniFlow
*	Application	: OutputXml parser for OmniFlow
*	Module		: JavaBeans
*	File		: WFXmlList.java
*
*	Purpose:
*       Implementation of list class to traverse the list returned in the output XML
*		from the JTS server.
*
*   Change History:
*	Date		FunctionName	Problem No
*	---------	-----------		-----------------------
***************************************************************************/


package com.newgen.formApplet.User;

public class WFXmlList
{
	public String xmlString = null;
	public String listTag = null;
	int startPos = 0;
	int endPos = 0;
	int elementStart = 0;
	int elementEnd = 0;
	private String strUpperCaseXmlString;
	private String strUpperCaseListTag;

	public WFXmlList()
	{

	}

/*
	*****************************************************************************************************
	FUNCTION NAME	   : WFXmlList::WFXmlList
	AUTHOR      	   : Krishan Dutt Dixit
	PURPOSE     	   : This is the constructor for initializing the WFXmlList object.
	INPUT PARAMETERS   : String outXml - Xml to be parsed.
						 String tag - Parent Tag name.
						 int startPos - Start position of the child tag.
						 int endPos - End position of the child tag.

	OUTPUT PARAMETERS  : None.
	*****************************************************************************************************
*/

	public WFXmlList(String outXml, String tag, int startPos, int endPos)
	{
		this.xmlString = outXml;
		this.listTag = tag;

		//strUpperCaseXmlString= xmlString.toUpperCase();
		strUpperCaseXmlString= xmlString;
		//strUpperCaseListTag= tag.toUpperCase();
		strUpperCaseListTag= tag;

		this.startPos = startPos;
		this.endPos = endPos;
		this.elementStart = this.startPos;
		this.elementEnd = strUpperCaseXmlString.indexOf("</"+ strUpperCaseListTag + ">", elementStart);
		if(this.elementEnd != -1)
			this.elementEnd += listTag.length() + 3;
		
	}

/*
	*****************************************************************************************************
	FUNCTION NAME	   : WFXmlList::reInitialize
	AUTHOR      	   : Krishan Dutt Dixit
	PURPOSE     	   : Reinitialize the list to begin traversing the list again.
	INPUT PARAMETERS   : None

	OUTPUT PARAMETERS  : None.
	*****************************************************************************************************
*/

	public void reInitialize()
	{
		this.elementStart = this.startPos;
		this.elementEnd = strUpperCaseXmlString.indexOf("</"+ strUpperCaseListTag  + ">", elementStart);
		if(this.elementEnd != -1)
			this.elementEnd += strUpperCaseListTag.length() + 3;
	}
	
/*
	*****************************************************************************************************
	FUNCTION NAME	   : WFXmlList::reInitialize
	AUTHOR      	   : Krishan Dutt Dixit
	PURPOSE     	   : Reinitialize the list to begin traversing the list again in the ascending order or 
						 descending order.

	INPUT PARAMETERS   : Boolean isAscending - true or folse depending on whether the list is to be traversed in 
						 ascending or descending order.

	OUTPUT PARAMETERS  : None.
	*****************************************************************************************************
*/

	public void reInitialize(boolean isAscending)
	{
		if(isAscending)
		{
			this.elementStart = this.startPos;
			this.elementEnd = strUpperCaseXmlString.indexOf("</"+ strUpperCaseListTag + ">", elementStart);
			if(this.elementEnd != -1)
				this.elementEnd += strUpperCaseListTag.length() + 3;
		}
		else
			reInitializeDesc();
	}

/*
	*****************************************************************************************************
	FUNCTION NAME	   : WFXmlList::reInitializeDesc
	AUTHOR      	   : Krishan Dutt Dixit
	PURPOSE     	   : Initializes the list to begin traversing the list from the end.

	INPUT PARAMETERS   : None

	OUTPUT PARAMETERS  : bolean - returns whether it was reinitialized properly or not in descending order.
	*****************************************************************************************************
*/


	boolean reInitializeDesc()
	{
		this.elementEnd = strUpperCaseXmlString.lastIndexOf("</"+ strUpperCaseListTag + ">", this.endPos);
		if(this.elementEnd == -1)
			return false;
		this.elementEnd += strUpperCaseListTag.length() + 3;
		this.elementStart = strUpperCaseXmlString.lastIndexOf("<"+ strUpperCaseListTag + ">", elementEnd);
		return true;
	}

/*
	*****************************************************************************************************
	FUNCTION NAME	   : WFXmlList::createList
	AUTHOR      	   : Krishan Dutt Dixit
	PURPOSE     	   : Create a list object from a XML within the start and end of a specified tag.
						 This list is separated on some specified tag.

	INPUT PARAMETERS   : String listStartTag  - The tag from where the list starts and its end tag marks the end of 
									            the list.
						 String elementTag  - The tag by which the list elements are separated.


	OUTPUT PARAMETERS  : WFXmlList  = WFXmlList object to traverse through the list.
	*****************************************************************************************************
*/

	public WFXmlList createList(String listStartTag, String elementTag)
	{
		int initPos = strUpperCaseXmlString.indexOf("<" + listStartTag+ ">", this.elementStart);
		if(initPos == -1)
			return (new WFXmlList("", elementTag, 0, 0));
		initPos += (2 + listStartTag.length());
		int lastPos = strUpperCaseXmlString.indexOf("</" + listStartTag + ">", initPos);

		return (new WFXmlList(xmlString, elementTag, initPos, lastPos));
	}

/*
	*****************************************************************************************************
	FUNCTION NAME	   : WFXmlList::hasMoreElements
	AUTHOR      	   : Krishan Dutt Dixit
	PURPOSE     	   : Returns whether the list contains some more elements or not.

	INPUT PARAMETERS   : None

	OUTPUT PARAMETERS  : boolean - True or False depending on whether list has more elements or not.
	*****************************************************************************************************
*/


	public boolean hasMoreElements()
	{
		int index1 = strUpperCaseXmlString.indexOf("<"+ strUpperCaseListTag + ">", elementStart);
		int index2 = strUpperCaseXmlString.indexOf("</"+ strUpperCaseListTag + ">", elementStart);

		return (index2!=-1 && index1 != -1  && index2>index1 && index2<this.endPos );
	}
	

/*
	*****************************************************************************************************
	FUNCTION NAME	   : WFXmlList::hasMoreElements
	AUTHOR      	   : Krishan Dutt Dixit
	PURPOSE     	   : Returns whether the list contains some more elements or not.

	INPUT PARAMETERS   : isAscending - True if the list is being traversed in ascending order. False otherwise.

	OUTPUT PARAMETERS  : boolean - True or False depending on whether list has more elements or not.
	*****************************************************************************************************
*/

	public boolean hasMoreElements(boolean isAscending)
	{
		if(this.elementEnd == -1)
			return false;
		if(isAscending)
		{
			int index = strUpperCaseXmlString.indexOf("<"+ strUpperCaseListTag + ">", elementStart);
			return ((index != -1) && (index < this.endPos));
		}
		else
			return hasMoreElementsDesc();
	}

/*
	*****************************************************************************************************
	FUNCTION NAME	   : WFXmlList::hasMoreElementsDesc
	AUTHOR      	   : Krishan Dutt Dixit
	PURPOSE     	   : Returns whether the list contains some more elements or not.

	INPUT PARAMETERS   : None

	OUTPUT PARAMETERS  : boolean - True or False depending on whether list has more elements or not.
	*****************************************************************************************************
*/

	boolean hasMoreElementsDesc()
	{
		int index = strUpperCaseXmlString.lastIndexOf("<"+ strUpperCaseListTag + ">", this.elementStart);
		return ((index != -1) && (index >= this.startPos));
	}

/*
	*****************************************************************************************************
	FUNCTION NAME	   : WFXmlList::skip
	AUTHOR      	   : Krishan Dutt Dixit
	PURPOSE     	   : Skips the current element of the list.

	INPUT PARAMETERS   : None

	OUTPUT PARAMETERS  : None
	*****************************************************************************************************
*/


	public void skip()
	{
		this.elementStart = this.elementEnd;
		this.elementEnd = strUpperCaseXmlString.indexOf("</"+ strUpperCaseListTag + ">", elementStart) + strUpperCaseListTag.length() + 3;
	}
	
/*
	*****************************************************************************************************
	FUNCTION NAME	   : WFXmlList::skip
	AUTHOR      	   : Krishan Dutt Dixit
	PURPOSE     	   : Skips the current element of the list.

	INPUT PARAMETERS   : boolean IsAscending - tells whether list should be skipped in ascending or descending 
						 order.

	OUTPUT PARAMETERS  : None
	*****************************************************************************************************
*/

	public void skip(boolean isAscending)
	{
		if(isAscending)
		{
			this.elementStart = this.elementEnd;
			this.elementEnd = strUpperCaseXmlString.indexOf("</"+ strUpperCaseListTag + ">", elementStart);
			if(this.elementEnd != -1)
				this.elementEnd += strUpperCaseListTag.length() + 3;
		}
		else
			skipDesc();
	}

/*
	*****************************************************************************************************
	FUNCTION NAME	   : WFXmlList::skipDesc
	AUTHOR      	   : Krishan Dutt Dixit
	PURPOSE     	   : Skips the current element of the list.

	INPUT PARAMETERS   : None

	OUTPUT PARAMETERS  : None
	*****************************************************************************************************
*/


	void skipDesc()
	{
		this.elementEnd = this.elementStart;
		this.elementStart = strUpperCaseXmlString.lastIndexOf("<"+ strUpperCaseListTag + ">", elementEnd-1);
		if(this.elementStart == -1)
			this.elementEnd = -1;
	}
/*
	*****************************************************************************************************
	FUNCTION NAME	   : WFXmlList::getVal
	AUTHOR      	   : Krishan Dutt Dixit
	PURPOSE     	   : Returns the value within the start and end of a specified tag.

	INPUT PARAMETERS   : String tag  - The tag within which the value is to be returned.

	OUTPUT PARAMETERS  : None
	*****************************************************************************************************
*/

	public String getVal(String tag)
	{
		if(elementStart == -1 || elementEnd == -1)
		{
			System.out.println("Problem in call sequence.");
			return "";
		}
		int startIndex = strUpperCaseXmlString.indexOf("<" + tag + ">", elementStart);
		if(startIndex == -1 || startIndex >= elementEnd)
			return "";
		return (xmlString.substring((startIndex + tag.length() + 2), strUpperCaseXmlString.indexOf("</" + tag+ ">", elementStart)));
	}
/*
	*****************************************************************************************************
	FUNCTION NAME	   : WFXmlList::toString
	AUTHOR      	   : Manish Handa
	PURPOSE     	   : This function returns the String representation of the given WFXmlList object.

	INPUT PARAMETERS   : None

	OUTPUT PARAMETERS  : String- String representation of the given WFXmlList object.
	*****************************************************************************************************
*/

	public String toString()
	{
		if(xmlString==null)
			return "";
		else
			return xmlString;
	}
/*
	*****************************************************************************************************
	FUNCTION NAME	   : WFXmlList::getCurrentXml
	AUTHOR      	   : Manish Handa
	PURPOSE     	   : This function returns the current XML part that is being parsed.

	INPUT PARAMETERS   : None

	OUTPUT PARAMETERS  : 
	*****************************************************************************************************
*/

	public String getCurrentXml()
	{
		if(xmlString==null)
			return "";
		else
			return xmlString.substring(elementStart,elementEnd);
	}

	
}