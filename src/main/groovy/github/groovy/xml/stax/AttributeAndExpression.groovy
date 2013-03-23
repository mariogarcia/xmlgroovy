package github.groovy.xml.stax

import javax.xml.stream.events.XMLEvent

class AttributeAndExpression extends DefaultExpression{

	def attrExpressions = []

	boolean evaluate(XMLEvent event){
		attrExpressions.every{it.evaluate(event)}
	}
}
