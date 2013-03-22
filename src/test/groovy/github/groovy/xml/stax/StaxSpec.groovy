package github.groovy.xml.stax

import spock.lang.Specification

import groovyx.gbench.Benchmark

import javax.xml.stream.*
import javax.xml.namespace.QName
import javax.xml.transform.stream.*

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
		when: "Traversing the document"
			def value
			while(reader.hasNext()){
				def xmlEvent = reader.nextEvent()
				if (xmlEvent.eventType == XMLStreamConstants.START_ELEMENT && xmlEvent.name.localPart == "author"){
					value = xmlEvent.getAttributeByName(new QName("id")).value
				}	
			}
		then: "The result should be Manuel De Cervantes"
			value == "1"
	}


}
