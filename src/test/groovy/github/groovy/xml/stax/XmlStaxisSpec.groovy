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
	def "Searching first occurrence by author and id eauals 2"(){
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

	def "Searching first occurrence by author and id greater than 2"(){
		setup: "Parsing document"
			def station = new XmlStaxis().parse(xmlFile)
		when: "Looking for the tag"
			def author = station.findByTag("author"){
				gt("id","2")
			}
		then: "Checking the name"
			author
			author.id == "3"
			author.text == "Lewis Carroll"
	}

	def "Mixin different attribute constraints"(){
		setup: "Parsing document"
			def station = new XmlStaxis().parse(xmlFile)
		when: "Looking for the tag"
			def book = station.findByTag("book"){
				gt("id","2")
				eq("available","5")
			}
		then: "The Only book that matches the criteria is"
			book.id == "4"
			book.available == "5"
	}

}
