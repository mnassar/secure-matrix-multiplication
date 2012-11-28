package sample.hello.storage;

import java.util.HashMap;
import java.util.Map;

import sample.hello.bean.Matrix;


public class MatrixStore {
	private static Map<String,Matrix> store;
	private static MatrixStore instance = null;
	
	private MatrixStore() {
		store = new HashMap<String,Matrix>();
		initOneMatrix();
	}
	
	public static Map<String,Matrix> getStore() {
		if(instance==null) {
			instance = new MatrixStore();
		}
		return store;
	}
	
	private static void initOneMatrix() {
		int [] list = {1,2,3,4,5,6};
		Matrix mat = new Matrix("A",2,3, list);
		store.put(mat.getId(), mat);
	}
}