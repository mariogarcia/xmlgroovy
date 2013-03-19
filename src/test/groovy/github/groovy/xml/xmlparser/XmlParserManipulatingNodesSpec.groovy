package github.groovy.xml.xmlparser

import groovy.xml.QName
import spock.lang.Specification

/**
 * This test shows some samples about using groovy.util.XmlParser and
 * also its parter in crime groovy.util.Node
**/
class XmlParserManipulatingNodesSpec extends Specification{

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

	def "Adding a new tag to a node"(){
		setup: "Building an instance of XmlParser"
			def parser = new XmlParser()
		and: "Parsing the xml"
			def response = parser.parseText(xml)
		when: "Adding a tag to response"
			def numberOfResults = parser.createNode(
				response,
				new QName("numberOfResults"),
				[:]
			)	
		and: "Setting the node's value"
			numberOfResults.value = "1"
		then: "We should be able to find the new node"
			response.numberOfResults.text() == "1"
	}

	def "Adding a new tag to a node with the node instance"(){
		setup: "Building an instance of XmlParser"
			def parser = new XmlParser()
		and: "Parsing the xml"
			def response = parser.parseText(xml)
		when: "Appending the tag to the current node"
			response.appendNode(
				new QName("numberOfResults"),
				[:],
				"1"
			)
		then: "We should be able to find it"	
			response.numberOfResults.text() == "1"
	}

	def "Replacing a node"(){
		setup: "Building the parser and parsing the xml"
			def response = new XmlParser().parseText(xml)
		when: "Replacing the book 'Don Xijote' to 'To Kill a Mockingbird'"
		 /* Use the same syntax as groovy.xml.MarkupBuilder */
			response.value.books.book[0].replaceNode{
				book(id:"3"){
					title("To Kill a Mockingbird")
					author(id:"3","Harper Lee")
				}
			}
		and: "Looking for the new node"
			def newNode = response.value.books.book[0]
		then: "Checking the result"
			newNode.name() == "book"
			newNode.@id == "3"
			newNode.title.text() == "To Kill a Mockingbird"
			newNode.author.text() == "Harper Lee"
		 /* Don't know why I have to look for the first id */
			newNode.author.@id.first() == "3"
	}

	def "Adding a new attribute to a node"(){
		setup: "Building an instance of XmlParser"
			def parser = new XmlParser()
		and: "Parsing the xml"
			def response = parser.parseText(xml)
		when: "Adding an attribute to response"
			response.@numberOfResults = "1"
		then: "We should be able to see the new attribute"
			response.@numberOfResults == "1"

	}
}
