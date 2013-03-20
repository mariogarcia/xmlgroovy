package github.groovy.xml.jaxb

import javax.xml.bind.*
import github.groovy.xml.util.ResourcesUtil

/**
 * This class helps us to handle marshalling and unmarshalling of JAXB objects
**/
class JaxbUtils extends ResourcesUtil{

	def object2MarshalType
	def source2Unmarshall 
	
	def marshal(object){
		object2MarshalType = object.getClass()
		this
	}

	def to(File file){
		throwIfNull(object2Marshal)
		throwIfNull(file)		

		buildMarshaller(object2MarshalType).marshal(object2MarshalType,file)
	}

	def unmarshal(source){
		source2Unmarshall = source 
		this
	}

	def to(Class<?> anyType){
		throwIfNull(source2Unmarshall)
		throwIfNull(anyType)

		buildUnmarshaller(anyType).unmarshal(source2Unmarshall)
	}

	def buildMarshaller(Class<?> type){
		def jaxbContext= JAXBContext.newInstance(type)
		def marshaller = jaxbContext.createMarshaller()

		marshaller
	}

	def buildUnmarshaller(Class<?> type){
		def jaxbContext= JAXBContext.newInstance(type)
		def unmarshaller = jaxbContext.createUnmarshaller()

		unmarshaller
	}


	def throwIfNull(value,message="You should have provided any value"){
		if (!value){
			throw new Exception(message)
		}
	}

}
