/***************************************************************************
*	Product		: OmniFlow
*	Application	: Monitoring Agent
*	Module		: JavaBeans
*	File		: WFXmlResponse.java
*
*	Purpose:
*       Implementation of response class to parse the output XML  from the WFS server.
*
*   Change History:
*	Date		FunctionName	Problem No
*	---------	-----------		-----------------------
***************************************************************************/

package com.newgen.formApplet.User;

public class WFXmlResponse
{
	public String xmlString = null;
	private String strUpperCaseXmlString;

/*
	*****************************************************************************************************
	FUNCTION NAME	   : WFXmlResponse::WFXmlResponse
	AUTHOR      	   : Aparna Kansal
	PURPOSE     	   : Public zero argument constructor.
	INPUT PARAMETERS   : None
	OUTPUT PARAMETERS  : None
	*****************************************************************************************************
*/

	public WFXmlResponse()
	{
	}


/*
	*****************************************************************************************************
	FUNCTION NAME	   : WFXmlResponse::WFXmlResponse
	AUTHOR      	   : Aparna Kansal
	PURPOSE     	   : Public constructor to create an object of WFXmlResponse object with the given 
						 XML.
	INPUT PARAMETERS   : String responseXml - The XML to initialize the WFXmlResponse object.
	OUTPUT PARAMETERS  : None
	*****************************************************************************************************
*/

	public WFXmlResponse(String responseXml)
	{
		xmlString = responseXml;
		if (xmlString != null)
			strUpperCaseXmlString= xmlString;//strUpperCaseXmlString= xmlString.toUpperCase();
	}

/*
	*****************************************************************************************************
	FUNCTION NAME	   : WFXmlResponse::getXmlString
	AUTHOR      	   : Aparna Kansal
	PURPOSE     	   : Returns the XML attached with the WFXmlResponse object.
	INPUT PARAMETERS   : None
	OUTPUT PARAMETERS  : String - XML attached with the WFXmlResponse object.
	*****************************************************************************************************
*/

	public String getXmlString()
	{
		return xmlString;
	}
	
/*
	*****************************************************************************************************
	FUNCTION NAME	   : WFXmlResponse::setXmlString
	AUTHOR      	   : Aparna Kansal
	PURPOSE     	   : Sets the XML to the WFXmlResponse object.
	INPUT PARAMETERS   : String xmlString - XML string to be attached with the WFXmlResponse object.
	OUTPUT PARAMETERS  : None
	*****************************************************************************************************
*/

	public void setXmlString(String xmlString)
	{
		this.xmlString = xmlString;
		if (xmlString != null)
		//strUpperCaseXmlString= xmlString.toUpperCase();
		strUpperCaseXmlString= xmlString;
	}
	
/*
	*****************************************************************************************************
	FUNCTION NAME	   : WFXmlResponse::getVal
	AUTHOR      	   : Aparna Kansal
	PURPOSE     	   : Returns the actual value within the start and end of a specified tag in the XML.
	INPUT PARAMETERS   : String tag - The tag within which the value is to be returned.
	OUTPUT PARAMETERS  : String - The String value between the starting and ending tag in the attached XML.
	*****************************************************************************************************
*/


	public String getVal(String tag)
	{
		int idTag = strUpperCaseXmlString.indexOf("<" + tag + ">", 0);
		if(idTag == -1)
			return "";
		return (xmlString.substring((idTag + tag.length() + 2), strUpperCaseXmlString.indexOf("</" + tag + ">", 0)));
	}

/*
	*****************************************************************************************************
	FUNCTION NAME	   : WFXmlResponse::createList
	AUTHOR      	   : Aparna Kansal
	PURPOSE     	   : Create a list object from a XML within the start and end of a specified tag.This list is separated on some specified tag.
	INPUT PARAMETERS   : String listStartTag  - The tag from where the list starts and its end tag marks the end of the list.
						 String elementTag  - The tag by which the list elements are separated.
	OUTPUT PARAMETERS  : WFXmlList - WFXmlList object to traverse through the list.
	*****************************************************************************************************
*/

	public WFXmlList createList(String listStartTag, String elementTag)
	{
		int startPos = strUpperCaseXmlString.indexOf("<" + listStartTag + ">");
		if(startPos == -1)
			return (new WFXmlList("", elementTag, 0, 0));
		startPos += (2 + listStartTag.length());
		int endPos = strUpperCaseXmlString.indexOf("</" + listStartTag + ">", startPos);
		return (new WFXmlList(xmlString, elementTag, startPos, endPos));
	}

/*
	*****************************************************************************************************
	FUNCTION NAME	   : WFXmlResponse::toString
	AUTHOR      	   : Aparna Kansal
	PURPOSE     	   : This function returns the String representation of the given WFXmlResponse object.
	INPUT PARAMETERS   : None
	OUTPUT PARAMETERS  : String- String representation of the given WFXmlResponse object.
	*****************************************************************************************************
*/
	public String toString()
	{
		return (xmlString);
	}

	public void insertValue(String strTagName , String strTagValue)
	{
		int nPos = strUpperCaseXmlString.lastIndexOf("</");		
		String strPart1 =xmlString.substring(0,nPos); 
		String strPart2 =xmlString.substring(nPos,strUpperCaseXmlString.length()); 
		String strTempTagXml="<"+ strTagName +">"+strTagValue+"</"+strTagName +">";
		xmlString =strPart1+strTempTagXml+strPart2;
		strUpperCaseXmlString= xmlString;
	}
}