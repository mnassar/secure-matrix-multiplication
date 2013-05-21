package org.lsmp.djep.matrixJep.function;

import java.util.Stack;

import org.lsmp.djep.vectorJep.Dimensions;
import org.lsmp.djep.vectorJep.function.BinaryOperatorI;
import org.lsmp.djep.vectorJep.function.UnaryOperatorI;
import org.lsmp.djep.vectorJep.values.MatrixValueI;
import org.lsmp.djep.vectorJep.values.Scaler;
import org.lsmp.djep.vectorJep.values.Tensor;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.Add;
import org.nfunk.jep.function.Divide;
import org.nfunk.jep.function.Subtract;

public class MInverse extends Divide implements UnaryOperatorI {
	
	protected Add add = new Add();
	protected Subtract sub = new Subtract();
	
	public MInverse()
	{
		//add = (Add) Operator.OP_ADD.getPFMC();
		//sub = (Subtract) Operator.OP_SUBTRACT.getPFMC();
		numberOfParameters = 1;
	}

	/**
	 *  Need to redo this as the standard jep version assumes commutivity.
	 */	
	public void run(Stack stack) throws ParseException 
	{
		checkStack(stack); // check the stack
		//if(this.curNumberOfParameters!=2) throw new ParseException("Multiply: should have two children its got "+stack.size());
		//Object param2 = stack.pop();
		Object param1 = stack.pop();
		Object product = inverse(param1) ; //TODO: Add inverse implementation= div(param1, param2);
		stack.push(product);
		return;
	}

	/**
	 * Inverse of a matrix.
	 */
	public Object inverse(Object param1) throws ParseException 
	{
		//TODO: INVERSE of matrix inversion here
	//	if(param1 instanceof MatrixValueI )
		//{
			//return inverse((MatrixValueI) param1);
		//}
	
		return param1;
	}
		
	
	public Dimensions calcDim(Dimensions l,Dimensions r) throws ParseException
	{
		int rrank = r.rank();
		if(rrank!=0) throw new ParseException("MDivide: right hand side must be a scaler. It has dimension "+r.toString());
		return l;
	}

	public MatrixValueI calcValue(MatrixValueI res,MatrixValueI param1,MatrixValueI param2) throws ParseException
	{
		if(param2 instanceof Scaler)
		{
			for(int i=0;i<param1.getDim().numEles();++i)
				res.setEle(i,super.div(param1.getEle(i),param2.getEle(0)));
		}
		else throw new ParseException("MDivide: right hand side must be a scaler. It has dimension "+param2.getDim().toString());
		return res;
	}

	@Override
	public Dimensions calcDim(Dimensions ldim) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MatrixValueI calcValue(MatrixValueI res, MatrixValueI lhs)
			throws ParseException {
		// TODO Auto-generated method stub
		return null;
	}
}