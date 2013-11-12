package broker;

public class SOCResourceAlias {

	private String resource_id;
	private String alias;
	
	public SOCResourceAlias()
	{
		super();
	}
	public SOCResourceAlias(String resourceID, String alias_name)
	{
		resource_id = new String(resourceID);
		alias = new String(alias_name);
	}
	/**
	 * @return the resource_id
	 */
	public String getResource_id() {
		return resource_id;
	}
	/**
	 * @param resource_id the resource_id to set
	 */
	public void setResource_id(String resource_id) {
		this.resource_id = resource_id;
	}
	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}
	/**
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}
}
