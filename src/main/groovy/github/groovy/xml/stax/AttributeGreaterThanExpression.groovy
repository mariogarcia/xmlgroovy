package github.groovy.xml.stax

import groovy.transform.InheritConstructors

import javax.xml.namespace.QName
import javax.xml.stream.events.XMLEvent

@InheritConstructors
class AttributeGreaterThanExpression extends SimpleAttributeExpression{
	
	boolean evaluate(XMLEvent event){
		def attribute = event?.getAttributeByName(new QName(attributeName))
		def isGreaterThan = attribute?.value > attributeValue

		isGreaterThan
	}
}
