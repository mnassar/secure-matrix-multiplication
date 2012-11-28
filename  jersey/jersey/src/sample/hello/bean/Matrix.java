package sample.hello.bean;

import java.util.Random;

import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
public class Matrix{
	private String id;
	private int [] content;
	private int rows; 
	private int columns;
	public static Random random_generator = new Random(); 
	
	public Matrix() {
		
	}
	public Matrix(String id, int rows, int columns, int[] content) {
		this.setId(id);
		this.setRows(rows); 
		this.setColumns(columns);
		this.setContent(content);
	}
	public Matrix(String id, String rows, String columns, String content) {
		this.setId(id);
		this.setRows(Integer.parseInt(rows)); 
		this.setColumns(Integer.parseInt(columns));
		String[] stri= content.trim().split(" ");
		int[] contentsi=new int[stri.length];
		for (int i=0;i<stri.length;i++)
			contentsi[i]=new Integer(stri[i]).intValue();
		this.setContent(contentsi);
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	
	public int[] getContent() {
		return content;
	}

	public void setContent(int[] content) {
		this.content = content;
	}
	
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public int getColumns() {
		return columns;
	}
	public void setColumns(int columns) {
		this.columns = columns;
	}
	public String toString(){
		String toStr=id +"\n";
		toStr+="rows: "+rows+"\n";
		toStr+="columns: "+columns+"\n";
		for (int i=0; i<rows; i++){
			for(int j=0;j<columns;j++){
				toStr+=content[i*columns+j]+" ";
			}
		toStr+=("\n");
		}
		return toStr;
	}
	
	
}
