import java.math.BigInteger;

import Jama.Matrix;

/* This class implements matrix multiplication  
 * using Homomorphic encryption protocol
 * This protocol only works with integer matrix (all values of matrix are integers)
 *  * TODO: put REFERENCE
 */
public class HomomorphicMultiplication {
	
	/**
	 * @param args
	 * n, size of matrix is n*n   
	 */
	static int n=1000; 
	public static void main(String[] args) {
		Paillier paillier = new Paillier();
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
		// split B into B1 and B2 
		Matrix B1= Matrix.random(n,  n).times(200);
		B1.minusEquals(constant);
		// client choose R1 and R2 and computes R1(.)R2
		Matrix R1= Matrix.random(n, n).times(200); 
		R1.minusEquals(constant); 
		Matrix R2= Matrix.random(n, n).times(200); 
		R2.minusEquals(constant);
		// convert to integers 
		for (int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				A.set(i, j, (int)A.get(i, j));
				B.set(i, j, (int)B.get(i, j));
				A1.set(i, j, (int)A1.get(i, j));
				B1.set(i, j, (int)B1.get(i, j));
				R1.set(i, j, (int)R1.get(i, j));
				R2.set(i, j, (int)R2.get(i, j));
			}
		}
		Matrix A2= A.minus(A1);
		//A.print (3, 1);
		//A1.print(3, 1);
		//A2.print(3, 1);
		Matrix B2= B.minus(B1);
		Matrix R=R1.arrayTimes(R2);
		long stop = System.currentTimeMillis();
		// store A1 and B1 at S1
		// store A2 and B2 at S2
		// output: storage time 
		long startp = System.currentTimeMillis();
		// multiply A1B1 and A2B2
		Matrix A1B1=A1.times(B1);
		Matrix A2B2=A2.times(B2);

		// S1 encrypt A1 and B1 
		BigInteger [][] encA1 = new BigInteger[n][n];
		BigInteger [][] encB1 = new BigInteger[n][n];
		BigInteger [][] encR1 = new BigInteger[n][n];
		for (int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				encA1[i][j]=paillier.Encryption(new BigInteger(String.valueOf((int)A1.get(i, j))));
				encB1[i][j]=paillier.Encryption(new BigInteger(String.valueOf((int)B1.get(i, j))));
				encR1[i][j]=paillier.Encryption(new BigInteger(String.valueOf((int)R1.get(i, j))));
			}
		}
		// send E(A1), E(B1) and E(R1) to S2
		// S2 computes E(A1B2 + A2B1 +R)
		BigInteger [][] encA1B2= new BigInteger [n][n];
		BigInteger [][] encA2B1= new BigInteger [n][n];
		BigInteger [][] encR= new BigInteger [n][n];
		BigInteger [][] encA1B2plusA2B1plusR= new BigInteger [n][n];
		for (int i=0; i<n; i++){
			for (int j=0; j<n; j++){
				encA1B2[i][j]=BigInteger.ONE;
				encA2B1[i][j]=BigInteger.ONE;
				for (int k=0;k<n; k++){
					encA1B2[i][j]=
						encA1B2[i][j].multiply(
							encA1[i][k].modPow(
								new BigInteger(String.valueOf((int)B2.get(k, j))), paillier.nsquare)).mod(paillier.nsquare);
					encA2B1[i][j]=
						encA2B1[i][j].multiply(
							encB1[k][j].modPow(
								new BigInteger(String.valueOf((int)A2.get(i, k))), paillier.nsquare)).mod(paillier.nsquare);
				}
				encR[i][j]= encR1[i][j].modPow(
						new BigInteger(String.valueOf((int)R2.get(i, j))), paillier.nsquare);
				encA1B2plusA2B1plusR [i][j]= encA1B2[i][j].multiply(encA2B1[i][j]).mod(paillier.nsquare)
						.multiply(encR[i][j]).mod(paillier.nsquare);
			}
		}
		// S1 decrypts A1B2 +A2B1 +R and send it to C 
		BigInteger [][] A1B2plusA2B1plusR= new BigInteger [n][n];
		BigInteger maxInt = new BigInteger(String.valueOf(Integer.MAX_VALUE));
		for (int i=0; i<n; i++){
			for (int j=0; j<n; j++){
				A1B2plusA2B1plusR[i][j] =paillier.Decryption(encA1B2plusA2B1plusR [i][j]);
				if (!A1B2plusA2B1plusR[i][j].max(maxInt).equals(maxInt)){ // it means we have a negative number 
					A1B2plusA2B1plusR[i][j]=BigInteger.ZERO.subtract(paillier.n.subtract(A1B2plusA2B1plusR[i][j]));
				}
				//System.out.println(A1B2plusA2B1plusR[i][j]);
			}
		}
		// s1 sends the result to the client 
		// Client add A1B2+A2B1+A1B1+A2B2 +R-R 
		BigInteger [][] AB = new BigInteger [n][n];
		for (int i=0; i<n; i++){
			for (int j=0; j<n; j++){
				AB[i][j] = A1B2plusA2B1plusR[i][j].add( 
						new BigInteger(String.valueOf((int)(A1B1.get(i, j) + A2B2.get(i, j) - R.get(i, j))))) ;
				System.out.print(AB[i][j]+" ");
			}
			System.out.println();
		}
		
		// output: protocol time 
		long stopp = System.currentTimeMillis();
		A.times(B).print(3, 1); 
		System.out.println("n "+n);
		System.out.println("Storage time(ms): "+(stop-start));
		System.out.println("Servers Time (ms): " + (stopp-startp));
	}
}
