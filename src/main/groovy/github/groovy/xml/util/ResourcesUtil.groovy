package github.groovy.xml.util

class ResourcesUtil{

	def resources_root = "src/test/resources"

	/**
	 * This method returns the xml related to the current test. Looks for a file
	 * called books.xml located in the default package in the resources
	 * folder
	 * 
	 * @return an instance of type File representing an xml
	**/
	def getXmlFile(){
		new File("${resources_root}/books.xml")
	}
}
