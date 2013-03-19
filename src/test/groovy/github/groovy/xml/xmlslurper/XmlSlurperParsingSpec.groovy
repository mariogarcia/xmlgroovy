package github.groovy.xml.xmlslurper

import groovyx.gbench.Benchmark
import spock.lang.Specification
import github.groovy.xml.util.ResourcesUtil

/**
 * This specification shows the different ways of using the XmlSlurper class to parse
 * an xml from a variery of sources
 *
 * @author mario
 *
**/
@Mixin(ResourcesUtil)
class XmlSlurperParsingSpec extends Specification{

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
	def "Parsing xml from a String"(){
		setup: "Creating an instanceof XmlSlurper"
			def parser = new XmlSlurper()
		when: "Parsing the xml as text"
			def responseNode = parser.parseText(xml)
		then: "You can check the xml's content"
			responseNode.value.books.book[0].title.text() == "Don Xijote"
	}

	@Benchmark
	def "Parsing an xml from a file"(){
		setup: "The parser"
			def parser = new XmlSlurper()
		when: "Parsing the xml from the resources' file"
			def response = parser.parse(xmlFile)
		then: "Checking the xml's content"
			response.value.books.book[0].title.text() == "Don Xijote"
	}
}
