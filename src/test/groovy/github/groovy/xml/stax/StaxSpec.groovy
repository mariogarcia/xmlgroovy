package github.groovy.xml.stax

import spock.lang.Specification

import groovyx.gbench.Benchmark

import javax.xml.stream.*
import javax.xml.namespace.QName
import javax.xml.transform.stream.*

import static javax.xml.stream.XMLStreamConstants.*

class StaxSpec extends Specification{

	def xml = """
		<response version-api="2.0">
			<value>
				<books>
					<book id="2">
						<title>Don Xijote</title>
						<author id="1">Manuel De Cervantes</author>
					</book>
				</books>
			</value>
		</response>
	"""

	@Benchmark
	def "Parsing a document with Stax"(){
		setup: "Parsing the document"
			def reader = XMLInputFactory.newFactory().createXMLEventReader(
				new StringReader(xml)
			)	
		when: "Creating the query"
			def authors = {readerDelegate,event-> 
				def map = [:]
				if (event.eventType == START_ELEMENT && event.name.localPart == "author"){
					map.id = event.getAttributeByName(new QName("id")).value
					map.name = readerDelegate.next()?.data
				}
				map?.id ? map: null
			}.curry(reader).memoize()
		and: "Looking for the first author in the xml document"
			def author = reader.findResult(authors)
		and: "That would be the java execution" /*
			while(reader.hasNext()){
				def xmlEvent = reader.nextEvent()
				if (xmlEvent.eventType == START_ELEMENT && xmlEvent.name.localPart == "author"){
					id = xmlEvent.getAttributeByName(new QName("id")).value
					author = reader.next()?.data
				}	
			} */
		then: "The result should be Manuel De Cervantes"
			author.name == "Manuel De Cervantes"
			author.id == "1"
	}
}
