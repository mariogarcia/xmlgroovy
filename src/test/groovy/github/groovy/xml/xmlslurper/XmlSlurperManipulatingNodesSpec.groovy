package github.groovy.xml.xmlslurper

import spock.lang.Specification
import groovyx.gbench.Benchmark

import groovy.xml.XmlUtil
import groovy.xml.StreamingMarkupBuilder

/**
 * When using XmlSlurper we will be using mostly GPathResult instances to manipulate
 * the document.
 *
 * The only problem with manipulation using XmlSlurper is that all evaluations are lazy
 * so it seems you have to rebuild the document to see the new values and nodes.
 *
**/
class XmlSlurperManipulatingNodesSpec extends Specification{

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
	def "Adding a new tag to a node with the node instance"(){
		setup: "Parsing the document"
			def response = new XmlSlurper().parseText(xml)
		when: "Creating the new node and its value"
		 /* Use the same syntax as groovy.xml.MarkupBuilder */
			response.appendNode{
				numberOfResults("1")
			}
		and: "Asserting the lazyness"
			assert !response.numberOfResults.text()
		and: "Rebuild the document"
	 	 /* That mkp is a special namespace used to escape away from the normal building mode 
			of the builder and get access to helper markup methods 
			'yield', 'pi', 'comment', 'out', 'namespaces', 'xmlDeclaration' and 
			'yieldUnescaped' */
			def result = new StreamingMarkupBuilder().bind{mkp.yield response}.toString()
			def changedResponse = new XmlSlurper().parseText(result) 
		then: "We will find it when evaluating the whole document"
			changedResponse.numberOfResults.text() == "1"
	}


	@Benchmark
	def "Replacing a node"(){
		when: "Running this spec"
		then: "An exception will be throw"
			thrown(Exception)

	}

	@Benchmark
	def "Adding a new attribute to a node"(){
		when: "Running this spec"
		then: "An exception will be throw"
			thrown(Exception)
	}

}


