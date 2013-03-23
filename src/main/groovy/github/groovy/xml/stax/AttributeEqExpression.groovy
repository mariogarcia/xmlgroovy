package github.groovy.xml.stax

import javax.xml.namespace.QName
import javax.xml.stream.events.XMLEvent

class AttributeEqExpression extends DefaultExpression{

	String attributeName
	Object attributeValue 

	AttributeEqExpression(String attrName,Object attrValue){
		attributeName = attrName
		attributeValue= attrValue
	}

	boolean evaluate(XMLEvent event){
		def attribute = event?.getAttributeByName(new QName(attributeName))
		def equals = attribute?.value == attributeValue
	
		equals
	}


}
