import java.util.Random;

import Jama.Matrix;

/* This class implements matrix multiplication  
 * using additive splitting protocol
 */
public class AdditiveSplittingMultiplication {

	/**
	 * @param args
	 * n where size of square matrix is n*n 
	 */
	static int n= 1000; 
	static Random r = new Random(); 
	public static void main(String[] args) {
		// Generate two random matrices A and B
		Matrix constant = new Matrix(n,n,100); 
		Matrix A= Matrix.random(n, n).times(200);
		A.minusEquals(constant); // values of A are between -100 and +100 
		Matrix B= Matrix.random(n, n).times(200); 
		B.minusEquals(constant); // values of B are between -100 and +100 
		// split A into A1 and A2 
		long start = System.currentTimeMillis();
		Matrix A1= Matrix.random(n,  n).times(200);
		A1.minusEquals(constant);
		Matrix A2= A.minus(A1);
		//A.print (3, 1);
		//A1.print(3, 1);
		//A2.print(3, 1);
		// split B into B1 and B2 
		Matrix B1= Matrix.random(n,  n).times(200);
		B1.minusEquals(constant);
		Matrix B2= B.minus(B1);
		long stop = System.currentTimeMillis();

		// store A1 and B1 at S1
		// store A1 and B2 at S2
		// store A2 and B1 at S3
		// store A2 and B2 at S4
		// output: storage time 
		// multiply A1B1, A2B2, A1B2, A2B1 
		long startm = System.currentTimeMillis();
		Matrix A1B1= A1.times(B1);
		Matrix A1B2= A1.times(B2); 
		Matrix A2B1= A2.times(B1); 
		Matrix A2B2= A2.times(B2); 
		long stopm = System.currentTimeMillis();
		// add 
		Matrix R = A1B1.plus(A1B2).plus(A2B2).plus(A2B1); 
		R.print(3, 1);
		
		A.times(B).print(3, 1); 
		System.out.println("n "+n);
		System.out.println("Storage time(ms): "+(stop-start));
		System.out.println("Servers Time (ms): " + (stopm-startm));
		// output: protocol time 
	}

}
