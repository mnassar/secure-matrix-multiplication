import java.util.Random;

import Jama.Matrix;

/* This class implements matrix multiplication  
 * using a Shamir secret sharing protocol 
 * TODO: put REFERENCE
 * xs and t are chosen small enough to not have number limit excess 
 * TODO: use finite field arithmetic 
 */
public class ShamirMultiplication {

	/**
	 * @param args
	 * t = polynomial degree
	 * n = size of the matrix is n*n  
	 */
	static int t = 2; 
	static int n = 1000;
	static long [] sharesx; // 2t+1 x are chosen for evaluating all the polynomials in A and B 
	static long [][][] sharesay ; // This is basically A1, A2, ...
	static long [][][] sharesby ; // This is basically B1, B2 , ..
	static long [][][] sharesaby; // This is A1B1, A2B2, ... 
	static Random r;
	public static void main(String[] args) {
		r = new Random(); 
		Matrix constant = new Matrix(n,n,5); 
		Matrix A= Matrix.random(n, n).times(10);
		Matrix B= Matrix.random(n, n).times(10);
		A.minusEquals(constant); // values of A are between -100 and +100 
		B.minusEquals(constant); // values of B are between -100 and +100 
		// convert to integers 
		for (int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				A.set(i, j, (int)A.get(i, j));
				B.set(i, j, (int)B.get(i, j));
			}
		}
		A.print(3, 1);
		B.print(3, 1);
		
		long start = System.currentTimeMillis();
		// choose 2t+1 DIFFERENT random xs, 
		sharesx =  new long[2*t+1];
		for (int i=0; i<(2*t+1); i++){
			int rn= (int)r.nextInt(100);
			// ensure it does not exist previously in the list 
			boolean exist = true;
			while (exist){
				exist=false;
				for (int u=0;u<i;u++)
					if (sharesx[u]==rn){
						exist = true;
						rn= (int)r.nextInt(100);
					}
			}
			sharesx[i]=rn;
		}
		sharesay = new long[n][n][2*t+1];
		sharesby = new long[n][n][2*t+1];
		for (int i=0; i<n; i++){
			for (int j=0; j<n; j++){
				// choose t random coefficients for each (i,j) in A and each (i,j) in B
				int [] coeffsa = new int[t];
				int [] coeffsb = new int[t];
				for (int c=0;c<t ; c++){
					coeffsa[c] = r.nextInt(10);
					coeffsb[c] = r.nextInt(10);
				}
				// compute 2t+1 values out of the polynomials based on the chosen xs
				for (int c=0; c<(2*t+1); c++){
					sharesay[i][j][c]= (int)A.get(i,j); 
					for (int c1=0; c1<t; c1++){
						sharesay[i][j][c]+=coeffsa[c1]*Math.pow(sharesx[c],c1+1);
					}
					sharesby[i][j][c]= (int)B.get(i,j);;
					for (int c1=0; c1<t; c1++){
						sharesby[i][j][c]+=coeffsb[c1]*Math.pow(sharesx[c],c1+1);
					}
					//System.out.println(sharesx[c]+" "+sharesay[i][j][c]+" "+sharesby[i][j][c]);
				}
				//System.out.println();
			}
		}
		long stop = System.currentTimeMillis();
		
		
		long starts = System.currentTimeMillis();
		sharesaby = new long[n][n][2*t+1];
		// compute matrix multiplication for each share 
		for (int c=0; c<(2*t+1); c++){
			for (int i=0; i<n; i++){
				for (int j=0; j<n; j++){
					sharesaby[i][j][c]=0;
					for (int k=0; k<n; k++){
						sharesaby[i][j][c]+=sharesay[i][k][c]*sharesby[k][j][c];
					}
					//System.out.print(sharesx[c]+" "+sharesaby[i][j][c]+" ");
				}
			}
			//System.out.println();
		}
		long stops = System.currentTimeMillis();
		System.out.println();
		
		A.times(B).print(3, 0);
		long starti = System.currentTimeMillis();
		for (int i=0; i<n; i++){
			for (int j=0; j<n; j++){
				System.out.print("  "+
				ShamirSecretSharing.interpolation(sharesx,sharesaby[i][j],t));
			}
			System.out.println();
		}
		long stopi = System.currentTimeMillis();
		System.out.println("t "+t);
		System.out.println("n "+n);
		System.out.println("CLIENT storage time(ms)/ computing the shares: "+ (stop-start));
		System.out.println("SERVERS Multiplication time: "+ (stops-starts));
		System.out.println("CLIENT Interpolation time: "+ (stopi-starti));
	}
}
