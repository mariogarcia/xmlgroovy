package github.groovy.xml.xmlparser

import groovyx.gbench.Benchmark
import spock.lang.Specification
import github.groovy.xml.util.ResourcesUtil

/**
 * This specification shows the different ways of using the XmlParser class to parse
 * an xml from a variery of sources
 *
 * @author mario
 *
**/
@Mixin(ResourcesUtil)
class XmlParserParsingSpec extends Specification{

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
	def "Parsing an xml from a String"(){
		setup: "The parser"
			def parser = new XmlParser()
		when: "Parsing the xml"
			def response = parser.parseText(xml)
		then: "Checking the xml's content"
			response.value.books.book[0].title.text() == "Don Xijote"
	}

	@Benchmark
	def "Parsing an xml from a file"(){
		setup: "The parser"
			def parser = new XmlParser()
		when: "Parsing the xml from the resources' file"
			def response = parser.parse(xmlFile)
		then: "Checking the xml's content"
			response.value.books.book[0].title.text() == "Don Xijote"
	}
}
