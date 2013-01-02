import org.stringtemplate.v4.*;
public class Example1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ST hello = new ST("Hello, <name>");
		hello.add("name", "World");


	}

}
