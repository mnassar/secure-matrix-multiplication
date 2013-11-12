package broker;

import java.util.StringTokenizer;

public class Location {

	private String ip;
	private String protocol;
	private String port_no;
	private String url;
	
	public Location()
	{
		super();
	}
	/**
	 * @return the ip
	 */
	public String getIP() {
		return ip;
	}

	/**
	 * @param url the url to set
	 */
	public void setIP(String ip) {
		this.ip = ip;
	}

	public Location(String url) {
		super();
		this.setUrl(url);
		System.out.println(url);
		StringTokenizer tokenizer = new StringTokenizer(url); 
		String prot = tokenizer.nextToken(":");
		System.out.println(prot);
		this.setProtocol(prot);
		String ip = tokenizer.nextToken(":").substring(2);
		System.out.println(ip);
		this.setIP(ip);
		this.setPort_no(tokenizer.nextToken());
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getPort_no() {
		return port_no;
	}

	public void setPort_no(String port_no) {
		this.port_no = port_no;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/*
	public static void main(String[] args)
	{
		Location loc = new Location("hdfs://127.0.0.1:54310");
		System.out.println(loc.getProtocol());
		System.out.println(loc.getIP());
		System.out.println(loc.getPort_no());
	}
	*/
}