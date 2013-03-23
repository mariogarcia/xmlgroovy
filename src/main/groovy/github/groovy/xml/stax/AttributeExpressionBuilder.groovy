package github.groovy.xml.stax

class AttributeExpressionBuilder extends AttributeAndExpression{

	def eq(String property,Object value){
		attrExpressions << new AttributeEqExpression(property,value)	
	}

	def evalTrue(){
		new AttributeEvalTrueExpression()	
	}

	def build(Closure cl){
		this.with(cl)
		this
	}
}
