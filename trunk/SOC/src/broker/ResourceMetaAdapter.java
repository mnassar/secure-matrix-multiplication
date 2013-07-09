package broker;



import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonDeserializer;
import java.lang.reflect.Type;


public class ResourceMetaAdapter implements JsonSerializer<ResourceMeta>, JsonDeserializer<ResourceMeta> 
{

	private static final String CLASSNAME = "CLASSNAME";
	private static final String INSTANCE  = "INSTANCE";

	@Override
	public JsonElement serialize(ResourceMeta src, Type typeOfSrc,
	        JsonSerializationContext context) {

	    JsonObject retValue = new JsonObject();
	    String className = src.getClass().getCanonicalName();
	    retValue.addProperty(CLASSNAME, className);
	    JsonElement elem = context.serialize(src); 
	    retValue.add(INSTANCE, elem);
	    return retValue;
	}

	@Override
	public ResourceMeta deserialize(JsonElement json, Type typeOfT,
	        JsonDeserializationContext context) throws JsonParseException  {
	 
		JsonObject jsonObject =  json.getAsJsonObject();

		//JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
	//    ResourceMeta result

		String className = "broker.ResourceMeta";
		if(jsonObject.get("type").getAsString().equals("matrix"))
		{
			className ="broker.MatrixMeta";
			return context.deserialize(json, MatrixMeta.class);
		}
		else
			return context.deserialize(json, ResourceMeta.class);
	
		/*Class<?> klass = null;
	    try {
	        klass = Class.forName(className);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	        throw new JsonParseException(e.getMessage());
	    }
	    return context.deserialize(jsonObject.get(INSTANCE), klass);
	    */
	}
	 

}
