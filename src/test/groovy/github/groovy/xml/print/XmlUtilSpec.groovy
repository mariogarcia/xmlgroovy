package github.groovy.xml.print

import groovy.xml.XmlUtil
import groovyx.gbench.Benchmark

import spock.lang.Specification

import github.groovy.xml.util.ResourcesUtil

/**
 * The XmlUtil class is used for printing xml. You can whether print
 * a whole document or just a fragment
 * 
 * I didn't know this till I read it from the Groovy mailing list
**/
@Mixin(ResourcesUtil)
class XmlUtilSpec extends Specification{

	@Benchmark
	def "Get the xml of a given GPath query"(){
		setup: "Getting and parsing the xml"
			def parser = new XmlParser()
			def response = parser.parse(xmlFile)
		when: "Getting a specific node"
			def nodeToSerialize = response.'**'.find{it.name() == 'author'}
			def nodeAsText = XmlUtil.serialize(nodeToSerialize).readLines().find{it.contains("author")}
		then: "We should be seeing the xml fragment"
			nodeAsText.contains('<author id="1">Manuel De Cervantes</author>')
	}
}
