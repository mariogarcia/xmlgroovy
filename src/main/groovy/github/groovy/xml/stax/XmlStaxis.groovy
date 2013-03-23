package github.groovy.xml.stax

import javax.xml.transform.stream.*

import javax.xml.stream.*
import javax.xml.stream.XMLStreamConstants

class XmlStaxis {

	def xmlInputFactory

	class Station {
		@Delegate
		XMLEventReader reader

		def findByTagClosure = {readerDelegate,tagExpression,attrExpression = null,event-> 
			tagExpression.evaluate(event) && attrExpression.evaluate(event) ? [
				text : readerDelegate.next()?.data
			] << event.attributes.collectEntries{at-> [(at.name.toString()):at.value]} : null
		}

		def findByTag(String tagName){
			findByTag(tagName,null)
		}

		def findByTag(String tagName,Closure closure){
			def tagExpression = new TagExpression(tagName)
			def attributeExpression = new AttributeExpressionBuilder().build(closure ?: { evalTrue() } )
			def query = findByTagClosure.curry(reader).curry(tagExpression).curry(attributeExpression)

			reader.findResult(query)
		}

		def Station(re){
			reader = re
		}
	}

	def XmlStaxis(){
		xmlInputFactory = XMLInputFactory.newFactory()
	}

	def parseText(String xml){
		new Station(xmlInputFactory.createXMLEventReader(new StringReader(xml)))
	}

	def parse(File xmlFile){
		new Station(xmlInputFactory.createXMLEventReader(new FileReader(xmlFile)))
	}
}
