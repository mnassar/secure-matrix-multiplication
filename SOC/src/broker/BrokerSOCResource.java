package broker;

import java.util.ArrayList;


public class BrokerSOCResource extends SOCResource{
	
	private String resource_id;
	private ArrayList<Location> locations;
	
	public String getResource_id() {
		return resource_id;
	}
	public void setResource_id(String resource_id) {
		this.resource_id = resource_id;
	}
	public ArrayList<Location> getLocations() {
		return locations;
	}
	public void setLocations(ArrayList<Location> locations) {
		this.locations = locations;
	}
	public void addLocation(Location loc)
	{
		if(locations!=null)
			locations.add(loc);
	}
	public BrokerSOCResource(String resource_id) {
	
		this.resource_id = resource_id;
		this.locations = new ArrayList<Location>();
		//this.setAvailable(false);
	}
	public BrokerSOCResource(String resource_id, ArrayList<Location> locations,
			ResourceMeta resource_meta) {
		this.resource_id = resource_id;
		this.locations = locations;
		this.setResource_meta( resource_meta);
		//this.setAvailable(true);
	}
	
	public BrokerSOCResource(BrokerSOCResource resource) {
		this.resource_id = resource.getResource_id();
		this.locations = resource.getLocations();
		this.setFile_path(resource.getFile_path());
		this.setStorage_protocol(resource.getStorage_protocol());
		this.setUser_token(resource.getUser_token());
		this.setAvailable(resource.getAvailable());
		
		if(resource.getResource_meta().getType().equals("matrix"))
			 this.setResource_meta( new MatrixMeta(((MatrixMeta)resource.getResource_meta()).getnRows(), ((MatrixMeta)resource.getResource_meta()).getnColumns(), ((MatrixMeta)resource.getResource_meta()).getdataType()));
	}
	public BrokerSOCResource(StorageProtocol storage_protocol, String user_token) {
		
		 super(storage_protocol, user_token);
		 locations = new ArrayList<Location>();
	}
	
	public BrokerSOCResource(SOCResource resource) {
		
		 super(resource.getStorage_protocol(), resource.getUser_token());
		 locations = new ArrayList<Location>();
		 if(resource.getResource_meta().getType().equals("matrix"))
			 this.setResource_meta( new MatrixMeta(((MatrixMeta)resource.getResource_meta()).getnRows(), ((MatrixMeta)resource.getResource_meta()).getnColumns(), ((MatrixMeta)resource.getResource_meta()).getdataType()));
	}
	

}
