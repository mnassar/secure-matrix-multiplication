package broker;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
/*
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonSubTypes({
	 
    @JsonSubTypes.Type(value=MatrixMeta.class)

})
*/

//@JsonIgnoreProperties(ignoreUnknown = true)

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="type" )
@JsonSubTypes({

      @JsonSubTypes.Type(value=MatrixMeta.class, name="matrix")

  })
  
//@JsonSubTypes({ @JsonSubTypes.Type(value = MatrixMeta.class, name="matrix")}) 
public class ResourceMeta {

	public ResourceMeta(String type) {
		super();
		this.type = type;
	}

	public ResourceMeta() {
		super();

	}
//	@JsonProperty
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
