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

	def build(Closure cl){
		this.with(cl)
	 /* Returning this instance */
		this
	}
}
