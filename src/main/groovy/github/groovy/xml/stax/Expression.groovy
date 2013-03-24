package github.groovy.xml.stax

import javax.xml.stream.events.XMLEvent

/**
 * All expressions must follow this interface to evaluate the xml events in order to know
 * if the given event matches the query 
**/
interface Expression{
  /**
	* If the element matches the query it should return true otherwise
	* it should return false
	*
	* @param event The event we are going to evaluate to know whether if it matches the query or not
	* @return true if the event content matches the query or false otherwise
	**/
	boolean evaluate(XMLEvent event)
}
