package github.groovy.xml.templates

import spock.lang.Specification
import groovy.text.XmlTemplateEngine
import groovyx.gbench.Benchmark

/**
 * Take from Gapi "Template engine for use in templating scenarios where both the template source and the expected output are intended to be XML".
 * I've created some examples to test the functionality around groovy.text.XmlTemplateEngine
 *
 * Resources:
 * 
 * <ul>
 *   <li>Groovy API: http://groovy.codehaus.org/gapi/</li>
 *   <li>Mr Haki Blog: http://mrhaki.blogspot.ie/2009/10/groovy-goodness-using-template-engines.html</li>
 * </ul>
**/
class XmlTemplateEngineSpec extends Specification{

	/**
	 * IMPORTANT: Notice I'm using simple quotes. I don't want Groovy
	 * to evaluate those varilables at the moment.
	 *
	 * Also notice that for making gsp tags to work you must include the
	 * namespace in your document.
	**/
	def tpl = ''' 
		<response xmlns:gsp='http://groovy.codehaus.org/2005/gsp' version-api="2.0">
			<value>
				<books>
					<gsp:scriptlet>books.eachWithIndex{book,index-></gsp:scriptlet>
						<book id="${index}">
							<!-- You can use GString expressions -->
							<title>${book.title}</title>
							<author id="${index}">
								<!-- Or you can use expression tags as well -->
								<gsp:expression>book.author</gsp:expression>
							</author>
						</book>
					<gsp:scriptlet>}</gsp:scriptlet>
				</books>
			</value>
		</response>
	'''	

	@Benchmark
	def "Creating a new xml using the template and some bindings"(){
		setup: "Building some data"
			def books = (1..10).collect{
				[title:"Book${it}",author:"Author${it}"]
			}
		when: "Creating the engine instance and compiling the template"
			def engine = new XmlTemplateEngine()
			def templ = engine.createTemplate(tpl)
		and: "Binding the values with the template"
			def bindings = [books:books]
		and: "Parsing the template with the included bindings"
			def writable = templ.make(bindings)
		 /* I want to see the output in the test report */
			println writable
		and: "Parsing the result to check the outcoming xml"
			def response = new XmlSlurper().parseText(writable.toString())
		then: "We should have a document with 10 books"
			response.value.books.book.size() == 10
	}


}
