package github.groovy.xml.stax

import javax.xml.stream.events.XMLEvent

interface Expression{
	boolean evaluate(XMLEvent event)
}
