package github.groovy.xml.gpath

import groovyx.gbench.Benchmark
import spock.lang.Specification
import github.groovy.xml.util.ResourcesUtil

/**
 * This class shows the most common ways of using GPath to query your xml.
**/
@Mixin(ResourcesUtil)
class GPathSpec extends Specification{

	@Benchmark
	def "Using POJO notation: Getting a node using POJOs notation a.b.c"(){
		setup: "Parsing the document"
			def response = new XmlSlurper().parse(xmlFile)
		when: "Trying to get a given node using the a.b.c notation"
			def authorNode = response.value.books.book[0].author
		then: "We can check the author's value"
			authorNode.text() == 'Manuel De Cervantes'
	}

	@Benchmark
	def "Using POJO notation: Getting an attribute's value using POJOs notation a.b.c"(){
		setup: "Parsing the document"
			def response = new XmlSlurper().parse(xmlFile)
		when: "Trying to get a given node using the a.b.c notation"
			def firstBook = response.value.books.book[0]
			def firstAuthorIdNode1 = firstBook.author.@id
			def firstAuthorIdNode2 = firstBook.author['@id']
		then: "Getting the id's value"
			firstAuthorIdNode1.toInteger() == 1
			firstAuthorIdNode2.toInteger() == 1
	}

	@Benchmark
	def "Using '*': Getting a node using breadthFirst operator '*'"(){
		setup: "Parsing the document"
			def response = new XmlSlurper().parse(xmlFile)
		when: "Looking for the node having the name 'book'"
		and: "with attribute id equals to 2"
		 /* You can use the breadthFirst operator to look among a group 
			of nodes at the same level */
			def catcherInTheRye = response.value.books.'*'.find{node-> 
			 /* node.@id == 2 could be expressed as node['@id'] == 2 */
				node.name() == 'book' && node.@id == '2'
			}
		then: "Getting the author's value"
			catcherInTheRye.title.text() == 'Catcher in the Rye'
	}

	@Benchmark
	def "Using '**': Getting a node using depthFirst operator '**'"(){
		setup: "parsing the document"
			def response = new XmlSlurper().parse(xmlFile)
		when: "Using the deptFirst operator we can look for something"
		and: "it doesn't matter how deep the node is"
		and: "Let's say we want to look for the book's id of the book written by Lewis Carrol"
		 /* Beware of the name I used for the closure's parameter. It may look like 
			the ** is too smart, but it isn't. It's just that I'm sure only books will 
			match the query. To avoid any confusion I'd rather use 'node' */
			def bookId = response.'**'.find{book-> 
				book.author.text() == 'Lewis Carroll'
			}.@id
		then: "The bookId should be 3"
			bookId == "3"
	}

	@Benchmark
	def "Using '**': Collecting all titles"(){
		setup: "parsing the document"
			def response = new XmlSlurper().parse(xmlFile)
		when: "Looking for all titles within the document"
			def titles = response.'**'.findAll{node-> node.name() == 'title'}*.text()
		then: "There should be only four"
			titles.size() == 4 
	}

	@Benchmark
	def "Using findAll: Collecting all titles"(){
		setup: "parsing the document"
			def response = new XmlSlurper().parse(xmlFile)
		when: "Looking for all titles with an id greater than 2"
			def titles = response.value.books.book.findAll{book->
			 /* You can use toInteger() over the GPathResult object */
				book.@id.toInteger() > 2
			}*.title
		then: "There should be only two"
			titles.size() == 2
	}

}
