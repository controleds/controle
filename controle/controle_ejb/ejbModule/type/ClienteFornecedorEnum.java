package type;




public enum ClienteFornecedorEnum {
	
	CLIENTE("CLI"),
	FORNECEDOR("FOR");
	
	
	
	String value;
	
	ClienteFornecedorEnum(String val){
		value = val;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
