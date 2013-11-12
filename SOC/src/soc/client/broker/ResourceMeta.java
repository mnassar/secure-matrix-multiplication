package soc.client.broker;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;
/*
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.WRAPPER_OBJECT, property="type")
@JsonSubTypes({

      @JsonSubTypes.Type(value=MatrixMeta.class, name="matrix")

  })  
*/
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

