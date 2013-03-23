package github.groovy.xml.stax

import spock.lang.Specification
import groovyx.gbench.Benchmark
import github.groovy.xml.util.ResourcesUtil

@Mixin(ResourcesUtil)
class XmlStaxisSpec extends Specification{

	@Benchmark
	def "Searching first result by tag"(){
		setup: "Parsing document"
			def station = new XmlStaxis().parse(xmlFile)
		when: "Looking for the tag"
			def author = station.findByTag("author")
		then: "Checking the name"
			author.id == "1"
			author.text == "Manuel De Cervantes"
	}

	@Benchmark
	def "Seatching first result by tag and attributes"(){
		setup: "Parsing document"
			def station = new XmlStaxis().parse(xmlFile)
		when: "Looking for the tag"
			def author = station.findByTag("author"){
				eq("id","2")
			}
		then: "Checking the name"
			author
			author.id == "2"
			author.text == "JD Salinger"
	}

}
