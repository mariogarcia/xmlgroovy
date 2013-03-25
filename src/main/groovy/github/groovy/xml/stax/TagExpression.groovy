package github.groovy.xml.stax

import javax.xml.stream.events.XMLEvent
import javax.xml.stream.XMLStreamConstants

class TagExpression extends DefaultExpression {

	String tagName
	Closure innerCriteria

	TagExpression(String tag){
		tagName = tag
	}

	TagExpression(String tag,Closure fullCriteria){
		this(tag)
		innerCriteria = fullCriteria
	}

	boolean evaluate(XMLEvent event){
		def isTag = event.eventType == XMLStreamConstants.START_ELEMENT && event.name.localPart == tagName
		def nestedConditions = new AttributeExpressionBuilder().build(innerCriteria ?: { evalTrue()}) 

		isTag && nestedConditions.evaluate(event)
	}

}
