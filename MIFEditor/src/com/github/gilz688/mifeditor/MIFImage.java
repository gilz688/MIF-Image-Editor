package com.github.gilz688.mifeditor;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MIFImage {
	private Map<String, String> variables = new LinkedHashMap<>();
	private Map<Integer, Integer> data = new HashMap<>();
	private int pWidth = -1;
	
	public MIFImage(){

	}
	
	public MIFImage(int width, int height){
		int pixels = width*height;
		variables.put("Depth", String.valueOf(width*height));
		variables.put("Width", "6");
		variables.put("Address_radix", "dec");
		variables.put("Data_radix", "bin");
		for(int i=0;i<pixels;i++){
			data.put(i, 0);
		}
		pWidth = width;
	}
	

	public Map<String, String> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, String> variables) {
		this.variables = variables;
	}

	public int getData(int x) {
		return data.get(x);
	}
	
	public void putData(int key, int value){
		data.put(key, value);
	}
	
	public void putVariable(String variable, String value){
		variables.put(variable, value);
	}

	public void setData(Map<Integer, Integer> data) {
		this.data = data;
	}

	public int size(){
		return data.size();
	}
	
	public int getpWidth() {
		return pWidth;
	}
	
	public int getWidth(){
		return Integer.parseInt(variables.get("Width"));
	}
	
	public void setpWidth(int pWidth) {
		this.pWidth = pWidth;
	}
	
	public BufferedImage getImage(int width, int height){
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for(int y=0;y<height;y++){
			for(int x=0;x<width;x++){
				int color = data.get(y*width+x);
				int r = Math.round (((color & 0b110000) >> 4) * 63.75f);
				int g = Math.round (((color & 0b001100) >> 2) * 63.75f);
				int b = Math.round ((color & 0b000011) * 63.75f);
				image.setRGB(x, y, new Color(r, g, b).getRGB());
			}
		}
		return image;
	}
}
