package github.groovy.xml.stax

import javax.xml.stream.events.XMLEvent

/**
 * This is the common base for those expression evaluating the value of
 * a given attribute.
**/
class SimpleAttributeExpression implements Expression{

	String attributeName
	Object attributeValue 

	/**
	 * The default behavior receives the attribute name and the attribute
	 * value we want to compare
	**/
	SimpleAttributeExpression(String attrName,Object attrValue){
		attributeName = attrName
		attributeValue= attrValue
	}

	/**
	 * By default it returns false
	**/
	boolean evaluate(XMLEvent event){
		false
	}

}
