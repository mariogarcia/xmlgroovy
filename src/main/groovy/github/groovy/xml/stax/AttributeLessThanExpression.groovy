package github.groovy.xml.stax

import groovy.transform.InheritConstructors

import javax.xml.namespace.QName
import javax.xml.stream.events.XMLEvent

@InheritConstructors
class AttributeLessThanExpression extends SimpleAttributeExpression{
	
	boolean evaluate(XMLEvent event){
		def attribute = event?.getAttributeByName(new QName(attributeName))
		def isLessThan = attribute?.value < attributeValue

		isLessThan	
	}
}
