

import Jama.*; 



import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Random; 

public class Broker {

	/**
	 * @param args
	 */
	
	static Server [] sv;
	static Matrix R; //current result for protocol
	static ArrayList<Thread> threads = new ArrayList<Thread>(); 
	Random r;
	HashMap<String, HashMap<String, HashSet<Server>>> decTable; 
	
	public static long calculate_average (long[] array){
		long average=0;
		for (long l:array)
			average+=l; 
		return average/(array.length);
	}
	public static void main(String[] args) {

		System.out.println("Hello World");
		
		int [] sizes = {3000};
		//int [] sizes = {3};
		long[] avg_times_single=new long[sizes.length];
		long[] avg_times_protocol=new long[sizes.length];
		int s=0; 
		int nb_trials=1; 
		for (int size: sizes){
			long []times_protocol=new long[nb_trials];
			long []times_single=new long[nb_trials];
			for (int i=0; i<nb_trials; i++){
				Broker br = new Broker();
				Matrix A= Matrix.random(size, size).times(br.r.nextInt(100));
				Matrix B= Matrix.random(size, size).times(br.r.nextInt(100));
				br.storeMatrix("A",A);
			    br.storeMatrix("B",B);
			  //  long start = System.currentTimeMillis();
			   // br.multiplyTwoMatrices("A","B", size);
			   // long end = System.currentTimeMillis();
			   // times_protocol[i]=end-start;
			    System.out.println(size+"/protocol:"+times_protocol[i]);
			    //A.times(B).print(1, 1);
			    long start1 = System.currentTimeMillis();
			    A.times(B);
			    long end1 = System.currentTimeMillis();
			    times_single[i]=end1-start1;
			    System.out.println(size+"/single:"+times_single[i]);
			}
			avg_times_single[s]=calculate_average(times_single);
			avg_times_protocol[s]=calculate_average(times_protocol); 
			System.out.print(size+"/single:");
			for (long t:times_single)
				System.out.print(t+",");
			System.out.println(avg_times_single[s]);
			System.out.print(size+"/protocol:");
			for (long t:times_protocol)
				System.out.print(t+",");
			System.out.println(avg_times_protocol[s]);
			s++;
		}
		// Multiplication of two matrices
		/*double[][] vals = {{1.,2.,3},{4.,5.,6.},{7.,8.,10.}};
	    Matrix A = new Matrix(vals);
	    
	    double[][] vals2 = {{1.,2.,3},{4.,5.,6.},{7.,9.,10.}};
	    Matrix B = new Matrix(vals2);
	    Broker br = new Broker();
	    br.storeMatrix("A",A);
	    br.storeMatrix("B",B);
	   	br.multiplyTwoMatrices("A","B",3);
	   	//R.print(1, 1);
	    //A.times(B).print(1, 1); 
	    */
	   /*// print out of the hash table at the broker 
	    * for (String s: br.decTable.keySet()){
	    	HashMap<String, HashSet<Server>> ht = br.decTable.get(s);
	    	for (String s1: ht.keySet()){
	    		HashSet<Server> hs = ht.get(s1);
	    		for (Server sv: hs)
	    			System.out.println(s + " " +s1 + " " + sv.toString() );
	    	}
	    }*/

	}
	
	Broker (){
		r = new Random(); 
		decTable = new HashMap<String,HashMap<String,HashSet<Server>>>();
		Server sv1 = new Server("sv1");
		Server sv2 = new Server("sv2"); 
		Server sv3 = new Server("sv3"); 
		Server sv4 = new Server("sv4");
		sv = new Server[]{sv1,sv2,sv3,sv4};
	}
	
	void storeMatrix (String id, Matrix A){
		Matrix A1= Matrix.random(A.getRowDimension(), A.getColumnDimension()).times(r.nextInt(100));
	    Matrix A2=A.minus(A1); 
	    //A.print(1, 1);
	    //A1.print(1, 1);
	    //A2.print(1, 1);
	    //System.out.println(A);
	    //choose randomly two servers
	    int i= r.nextInt(sv.length);
	    //System.out.print(i);
	    int j= i;
	    while (j==i)
	    	j=r.nextInt(sv.length);
	    //System.out.print(j);
	    //store the first share at the chosen servers 
	    sendShare(id +"1", A1,sv[i]);
	    sendShare(id +"1", A1,sv[j]);
	    // store the second share at the other two servers
	    int k =j; 
	    while (k==i || k==j)
	    	k=r.nextInt(sv.length);
	    //System.out.print(k);
	    int l =k; 
	    while (l==i || l==j || l==k)
	    	l=r.nextInt(sv.length);
	    //System.out.println(l);
	    sendShare(id+"2", A2, sv[k]);
	    sendShare(id+"2", A2, sv[l]);
	    //put the matrix identifier and the decomposition schema in the decTable
	    HashSet<Server> hs = new HashSet<Server>();
	    hs.add(sv[i]);
	    hs.add(sv[j]);
	    HashMap<String,HashSet<Server>> ht = new HashMap<String, HashSet<Server>>();
	    ht.put(id+"1", hs);
	    HashSet<Server> hs1 = new HashSet<Server>();
	    hs1.add(sv[k]);
	    hs1.add(sv[l]);
	    ht.put(id+"2", hs1);
	    decTable.put(id, ht);
	}
	
	void sendShare(String id, Matrix A, Server sv){
		sv.receiveShare(id, A);
	}
	
	void multiplyTwoMatrices(String A, String B, int size){

		R = new Matrix(size,size, 0 ); // ToDo: store the size of the matrices along with their identifiers
		HashMap<String, HashSet<Server>> ht1 = decTable.get(A);
		HashMap<String, HashSet<Server>> ht2 = decTable.get(B);
		for (String X1: ht1.keySet())
			for (String X2: ht2.keySet()){
				// if the two shares have one server in common, use it 
				HashSet<Server> set1= ht1.get(X1);
				HashSet<Server> set2= ht2.get(X2);
				boolean found = false; 
				for (Server sv: set1)
					if (set2.contains (sv)){
						found=true;
						sv.multiplyTwoMatrices(X1, X2);
						break;
					}
				if (!found){
					//System.out.println("not found");
					//share X1 
					((Server)set1.toArray()[0]).shareMatrix(X1,set2);
					 ((Server)set2.toArray()[0]).multiplyTwoMatrices(X1+"1", X2);
					 ((Server)set2.toArray()[1]).multiplyTwoMatrices(X1+"2", X2);
				}
					 
					
			}
		try {
			for (Thread t: threads)
				t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//R.print(1, 1);
		//return R;
	}
	
	static class Server{
		String id;
		HashMap<String,Matrix> mt; 
		Server (String id){
			this.id = id;
			mt = new HashMap<String,Matrix>();
		}
		void receiveShare(String id, Matrix A){
			mt.put(id,A);
		}
		void shareMatrix(String id, HashSet<Server> set){
			Matrix A = mt.get(id);
			Matrix A1= Matrix.random(A.getRowDimension(), A.getColumnDimension());
		    Matrix A2=A.minus(A1);
		    ((Server)set.toArray()[0]).receiveShare(id+"1", A1);
		    ((Server)set.toArray()[1]).receiveShare(id+"2", A2);
		}
		
		void multiplyTwoMatrices(final String A, final String B){
			Thread t = new Thread() {public void run() { 
				Matrix M= mt.get(A).times(mt.get(B)); 
				synchronized(sv) {
					R.plusEquals(M); 
			//		R.print(1, 1);
					}
				}
			};
			threads.add(t);
			t.start();
			
		}
		public String toString(){
			return id;
		}
	}
}
	