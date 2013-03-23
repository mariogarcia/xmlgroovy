package github.groovy.xml.stax

import javax.xml.stream.events.XMLEvent
import javax.xml.stream.XMLStreamConstants

class TagExpression extends DefaultExpression {

	String tagName

	TagExpression(String tag){
		tagName = tag
	}

	boolean evaluate(XMLEvent event){
		event.eventType == XMLStreamConstants.START_ELEMENT && 
			event.name.localPart == tagName
	}

}
