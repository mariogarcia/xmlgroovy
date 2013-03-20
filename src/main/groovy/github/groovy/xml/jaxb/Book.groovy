package github.groovy.xml.jaxb

import javax.xml.bind.annotation.*

@XmlRootElement(name="book")
@XmlAccessorType(XmlAccessType.FIELD)
class Book{

	@XmlAttribute Long id
	@XmlElement String title
	@XmlElement Author author

}
