package com.github.gilz688.mifeditor.mif;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MIF {
	private Map<String, String> variables = new LinkedHashMap<>();
	private Map<Integer, Integer> data = new HashMap<>();
	
	public static final String RADIX_BINARY = "bin";
	public static final String RADIX_OCTAL = "oct";
	public static final String RADIX_DECIMAL = "dec";
	public static final String RADIX_HEXADECIMAL = "hex";
	
	public MIF(){
		setup();
	}
	
	public void setup(){
		putVariable("Width", "6");
		putVariable("Address_radix", RADIX_HEXADECIMAL);
		putVariable("Data_radix", RADIX_BINARY);
		putVariable("Depth", "0");
	}
	
	public Map<String, String> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, String> variables) {
		this.variables = variables;
	}
	
	public String getVariable(String key) {
		return variables.get(key);
	}

	public int getData(int x) {
		return data.get(x);
	}

	public void putData(int key, int value) {
		data.put(key, value);
	}

	public void putVariable(String variable, String value) {
		variables.put(variable, value);
	}

	public void setData(Map<Integer, Integer> data) {
		this.data = data;
	}

	public int size() {
		return data.size();
	}
	
	public int getDepth(){
		return Integer.parseInt(getVariable("Depth"));
	}
	
	public int getBitWidth() {
		return Integer.parseInt(getVariable("Width"));
	}

	public void setBitWidth(int bitWidth) {
		putVariable("Width",Integer.toString(bitWidth));
	}
}
