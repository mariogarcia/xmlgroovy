package github.groovy.xml.jaxb

import javax.xml.bind.annotation.*

@XmlRootElement(name="author")
@XmlAccessorType(XmlAccessType.FIELD)
class Author{

	@XmlAttribute Long id
	@XmlElement String name

}
