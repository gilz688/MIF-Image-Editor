package com.github.gilz688.mifeditor.mif;

public class MIFImage extends MIF{
	private int pWidth = -1;

	public MIFImage(int width, int height) {
		super();
		int pixels = width * height;
		putVariable("Depth", String.valueOf(width * height));
		for (int i = 0; i < pixels; i++) {
			putData(i, 0);
		}
		pWidth = width;
	}
	
	public MIFImage() {
		super();
	}
	
	public int getWidth() {
		return pWidth;
	}

	public int getHeight() {
		return size()/pWidth;
	}
	
	public void setWidth(int width) {
		pWidth = width;
	}
}
