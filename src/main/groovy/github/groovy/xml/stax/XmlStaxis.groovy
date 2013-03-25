package github.groovy.xml.stax

import javax.xml.transform.stream.*

import javax.xml.stream.*
import javax.xml.stream.XMLStreamConstants

/**
 * This class acts as a read-only xml parser based on Stax
 *
 * @author mario
**/
class XmlStaxis {

	def xmlInputFactory

	/**
	 * This class holds the query methods you can use to query your xml
	**/
	class Station {

	  /**
		* All queries, constraints...etc is based on the XMLEventReader object 
		**/
		@Delegate XMLEventReader reader

		/**
		 * A single algorithm that evaluates tags and its attributes depending on the expressions
		 * provided to the closure. The closure gets the tag value and all its attributes and build
		 * a map with all of them. Now it's only helpful with simple tags.
		**/
		def findByTagClosure = {readerDelegate,tagExpression,event-> 
			tagExpression.evaluate(event) ? [
				text : readerDelegate.next()?.data
			] << event.attributes.collectEntries{at-> [(at.name.toString()):at.value]} : null
		}

		/**
		* The expression should have the tag and the closure with the criteria
		* If a node matches the criteria all its inner estructure should be 
		* returned using nested maps. 
		*
	    * def bookList = station.findAllByTag("book"){
		*		author{
		*			lt("id",1)
		*			gt("available",2)
	    *		}
		*	}
		*
		**/
		//def findByTagClosure = {readerDelegate,expression,event->
		 /* If the event matches the expression we collect its nested values in nested maps */
			//expression.evaluate(event) ? collector.collectTree(readerDelegate) : null
		//}

		/**
	     * looks for the first xmlevent instance that contents a tag with the name passed
		 * as parameter
		 * 
		 * @param tagname
		 * @return an map with the tag attributes/values and the value of the tag if it was a text tag
		**/
		def findByTag(String tagName){
			findByTag(tagName,null)
		}

		/**
	     * looks for the first xmlevent instance that contents a tag with the name passed
		 * as parameter
		 * 
		 * @param tagname
		 * @param closure A closure with constraints about the tag's attributes
		 * @return an map with the tag attributes/values and the value of the tag if it was a text tag
		**/
		def findByTag(String tagName,Closure closure){
			def query = createQuery(tagName,closure)
			reader.findResult(query)
		}

		/**
		 * Looks for all items matching the tag name passed as parameter
		 *
		 * @param tagName the name of the elements we are looking for
		 * @return a list of maps
		**/
		def findAllByTag(String tagName){
			findAllByTag(tagName,null)	
		}

		/**
		 * Looks for all items matching the tag name passed as parameter. It also
		 * could reveive a closure with some constraints related to the element's
		 * attributes
		 *
		 * @param tagName the name of the elements we are looking for
		 * @param closure the closure with the attributes' constraints
		 * @return a list of maps
		**/
		def findAllByTag(String tagName,Closure closure){
			def query = createQuery(tagName,closure)
			reader.collect(query).findAll{it}
		}

		/**
		 * This method builds the expression used to find the items inside the xml
		 * 
		 * @param tagName the tagName
		 * @param closure
		**/
		def createQuery(String tagName,Closure closure){
			def tagExpression = new TagExpression(tagName,closure)
			def query = findByTagClosure.curry(reader).curry(tagExpression)
		 /* returning the query */
			query
		}

		/**
		 * The constructor receives an instance of XMLStreamReader
		 *
		 * @param re and instance of XMLEventReader 
		**/
		def Station(re){
			reader = re
		}
	}

	/**
	 * Creating a read-only Stax factory
	**/
	def XmlStaxis(){
		xmlInputFactory = XMLInputFactory.newFactory()
	}

	/**
	 * Parsing an xml from a given xml
	 * 
	 * @param xml a String with the xml
	 * @return an instance of XmlStaxis.Station
	**/
	def parseText(String xml){
		new Station(xmlInputFactory.createXMLEventReader(new StringReader(xml)))
	}

	/**
	 * Parsing an xml from a given xml
	 * 
	 * @param xml a File with the xml
	 * @return an instance of XmlStaxis.Station
	**/
	def parse(File xmlFile){
		new Station(xmlInputFactory.createXMLEventReader(new FileReader(xmlFile)))
	}
}
