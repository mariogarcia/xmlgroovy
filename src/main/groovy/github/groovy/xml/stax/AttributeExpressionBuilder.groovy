package github.groovy.xml.stax

class AttributeExpressionBuilder extends AttributeAndExpression{

	def eq(String property,Object value){
		attrExpressions << new AttributeEqExpression(property,value)	
	}

	def gt(String property,Object value){
		attrExpressions << new AttributeGreaterThanExpression(property,value)
	}

	def lt(String property,Object value){
		attrExpressions << new AttributeLessThanExpression(property,value)
	}

	def evalTrue(){
		attrExpressions << new AttributeEvalTrueExpression()	
	}

  /** 
   	* If there is a missing method is because we will be using nested tags such as
	*
   	* def bookList = station.findAllByTag("book"){
	*		author{
	*			lt("id",1)
	*			gt("available",2)
	*		}
	*	}
	*
	**/
	def methodMissing(String name,args){
		def innerExpression = args?.getAt(0)
		if (!innerExpression || !(innerExpression instanceof Closure)){
			throw new Exception("Error trying to create a nested expression")
		}
	 /* Should be evaluated for the children not for the current event (PEEK) */
		innerTagsExpressions << new TagExpression(name,args[0])
	}

	def build(Closure cl){
		this.with(cl)
	 /* Returning this instance */
		this
	}
}
