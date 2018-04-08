package nhjkpt.configmanage.service.impl.ladderprice;

public enum PriceTypeEmun {
	WATER(1,"水"),
	ELECTRIC(2,"电");
	
	private Integer type;
	
	private String typeName;
	
	private PriceTypeEmun(Integer type,String typeName) {
		this.type = type;
		this.typeName = typeName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
}
