package com.github.gilz688.mifeditor.mif;

public class MIFImage extends MIF{
	private int pWidth = -1;

	public MIFImage(int width, int height) {
		int pixels = width * height;
		putVariable("Depth", String.valueOf(width * height));
		setup();
		for (int i = 0; i < pixels; i++) {
			putData(i, 0);
		}
		pWidth = width;
	}
	
	public MIFImage() {
		setup();
	}
	
	public void setup(){
		putVariable("Width", "6");
		putVariable("Address_radix", "dec");
		putVariable("Data_radix", "bin");
	}
	
	public int getWidth() {
		return pWidth;
	}

	public int getHeight() {
		return size()/pWidth;
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
	
	public void setWidth(int width) {
		pWidth = width;
	}
}
