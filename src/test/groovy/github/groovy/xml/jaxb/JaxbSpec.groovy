package github.groovy.xml.jaxb

import groovy.xml.XmlUtil
import groovyx.gbench.Benchmark
import spock.lang.Specification
import github.groovy.xml.util.ResourcesUtil

@Mixin(JaxbUtils)
@Mixin(ResourcesUtil)
class JaxbSpec extends Specification{

	@Benchmark
	def "Unmarshalling the first book"(){
		setup: "Parsing the document"
			def response = new XmlSlurper().parse(xmlFile)
		and: "Getting only the first book"
			def firstBook = response.'**'.find{it.name() =='book'}
			def jaxbSource= XmlUtil.serialize(firstBook)
		when: "Convert to Jaxb object"
			def newXmlSource = new StringReader(jaxbSource)
			def jaxbBook = unmarshal(newXmlSource).to(Book)
		then: "We make sure the conversion took place"
			jaxbBook instanceof Book
		and: "Checking Book properties"
			jaxbBook.title
			jaxbBook.author
			jaxbBook.author.id
	}

}
