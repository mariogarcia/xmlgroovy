package github.groovy.xml.gpath

import spock.lang.Specification

class GPathSpec extends Specification{

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

	def "Getting a node using POJOs notation a.b.c"(){
		setup: "Parsing the document"
			def response = new XmlSlurper().parseText(xml)
		when: "Trying to get a given node using the a.b.c notation"
			def authorNode = response.value.books.book[0].author
		then: "We can check the author's value"
			authorNode.text() == 'Manuel De Cervantes'
	}

	def "Getting an attribute's value using POJOs notation a.b.c"(){
		setup: "Parsing the document"
			def response = new XmlSlurper().parseText(xml)
		when: "Trying to get a given node using the a.b.c notation"
			def authorIdNode = response.value.books.book[0].author.@id
		then: "Getting the id's value"
			authorIdNode.text() == "1"

	}


}
