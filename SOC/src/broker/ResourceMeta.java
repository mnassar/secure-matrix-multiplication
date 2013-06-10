package broker;

public class ResourceMeta {

	public ResourceMeta(String type) {
		super();
		this.type = type;
	}

	public ResourceMeta() {
		super();

	}
	private String type;

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
}
