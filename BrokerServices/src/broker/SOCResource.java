package broker;


public class SOCResource {
	
	private StorageProtocol storage_protocol;
	private String user_token;
	private String file_path;
	private ResourceMeta resource_meta;
	
	public ResourceMeta getResource_meta() {
		return resource_meta;
	}
	public void setResource_meta(ResourceMeta resource_meta) {
		if(resource_meta.getType().equals("matrix"))
			this.resource_meta =  new broker.MatrixMeta(resource_meta);
		else
			this.resource_meta =  new ResourceMeta(resource_meta.getType());
		
	}
	
	
	public void setResource_meta(broker.MatrixMeta resource_meta) {
		this.resource_meta = new broker.MatrixMeta(resource_meta);
		this.resource_meta.setType("matrix");
	}
	
	public String getUser_token() {
		return user_token;
	}
	public void setUser_token(String user_token) {
		this.user_token = user_token;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	
	/**
	 * @return the storage_protocol
	 */
	public StorageProtocol getStorage_protocol() {
		return storage_protocol;
	}
	/**
	 * @param storage_protocol the storage_protocol to set
	 */
	public void setStorage_protocol(StorageProtocol storage_protocol) {
		this.storage_protocol = storage_protocol;
	}
	public SOCResource(StorageProtocol storage_protocol, String user_token,
			String file_path) {
		super();
		this.setStorage_protocol(storage_protocol);
		this.user_token = user_token;
		this.file_path = file_path;
	}
	
	public SOCResource(StorageProtocol storage_protocol)
	{
		super();
		this.setStorage_protocol(storage_protocol);
		
	}
	
	public SOCResource(StorageProtocol storage_protocol, String user_token) {
		super();
		this.setStorage_protocol(storage_protocol);
		this.user_token = user_token;
		
	}
	public SOCResource()
	{
		
	}
	

}
