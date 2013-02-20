import java.util.Random;
/*
* note that we do not use modular arithmetic here, 
* TODO use Finite Field arithmetic
* xs and t are chosen small enough to not have number limit excess 
*/
public class ShamirSecretSharing {

	static Random r= new Random();
	public static long [][] storeShares(int a, int b, int t){
		long [] sharesx = new long[2*t+1];
		long [] sharesay = new long[2*t+1] ;
		long [] sharesby = new long[2*t+1] ;
		//System.out.println("t "+t);
		//System.out.println("a " +a);
		//System.out.println("b " +b);
		// choose t random coefficients 
		int [] coeffsa = new int[t];
		int [] coeffsb = new int[t];
		//System.out.print("coefa:");
		for (int i=0;i<t ; i++){
			coeffsa[i] = r.nextInt(100);
			//System.out.print(coeffsa[i]+" ");
		}
		//System.out.println();
		//System.out.print("coefb:");
		for (int i=0;i<t ; i++){
			coeffsb[i] = r.nextInt(100);
			//System.out.print(coeffsb[i]+" ");
		}
		//System.out.println();
		// choose 2t+1 points  
		for (int i=0; i<(2*t+1); i++){
			int rn= (int)r.nextInt(100);
			//System.out.println(rn);
			//System.out.println(Math.pow(rn,2*t));
			//System.out.println(Long.MAX_VALUE);
			// ensure it does not exist previously in the list 
			boolean exist = true;
			while (exist){
				exist=false;
				for (int u=0;u<i;u++)
					if (sharesx[u]==rn){
						exist = true;
						rn= (int)r.nextInt(10);
					}
			}
			sharesx[i]=rn;
			//System.out.print("x"+i+" "+ sharesx[i]);
			sharesay[i]= a; 
			for (int j=0; j<t; j++){
				sharesay[i]+=coeffsa[j]*Math.pow(sharesx[i],j+1);
			}
			//System.out.print(" ya "+ sharesay[i]);
			sharesby[i]= b;
			for (int j=0; j<t; j++){
				sharesby[i]+=coeffsb[j]*Math.pow(sharesx[i],j+1);
			}
			//System.out.println(" yb "+ sharesby[i]);
		}
		//System.out.println();
		// now the client distributes sharesx , sharesay and sharesby to the servers
		return new long [][] {sharesx, sharesay, sharesby}; 
	}
	
	static long[] multiplyShares(long[] sharesx, long[] sharesay, long[] sharesby, int t){
		long [] sharesaby = new long[2*t+1];
		for (int i=0; i<(2*t+1); i++){
			sharesaby[i]=sharesay[i]*sharesby[i];
			//System.out.println("x"+i+" "+sharesx[i] +" yab "+sharesaby[i]);
		}
		return sharesaby;
	}
	
	//this function does interpolation 
	static long interpolation(long[] sharesx, long[] sharesaby , int t){
		// the client receives the multiplication results and compute the Lagrange Basis Polynomials
		// in order to recover the answer 
		double [] lbp = new double[2*t+1];
		for (int j=0; j<(2*t+1); j++){
			lbp[j]=1;
			for (int i=0; i<(2*t+1); i++){
				if (i!=j){
					lbp[j]*=-sharesx[i]/(1.0*(sharesx[j]-sharesx[i]));
				}
			}
			
			//System.out.println("lbp"+j+": "+lbp[j]);
		}
		double ab=0;
		for (int j=0; j<(2*t+1); j++){
			ab+=lbp[j]*sharesaby[j];
		}
		//System.out.println("ab "+Math.round(ab));
		return (Math.round(ab));
	}
	
	
	
	public static void main(String [] args){
		int a=r.nextInt(100)-50; 
		int b=r.nextInt(100)-50;
		int t=2;
		System.out.println("t "+t);
		long[][] x_ya_yb =storeShares(a,b,t);
		long[] aby = multiplyShares(x_ya_yb[0], x_ya_yb[1], x_ya_yb[2],t);
		System.out.println("a*b "+a*b);
		System.out.print("ab  "+
		interpolation(x_ya_yb[0],aby,t));

	}
}
