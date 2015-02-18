package com.github.gilz688.mifeditor.mif;

public class MIFImage extends MIF{
	private int pWidth = -1;

	public MIFImage(int width, int height) {
		int pixels = width * height;
		putVariable("Depth", String.valueOf(width * height));
		putVariable("Width", "6");
		putVariable("Address_radix", "dec");
		this.putVariable("Data_radix", "bin");
		for (int i = 0; i < pixels; i++) {
			putData(i, 0);
		}
		pWidth = width;
	}


	public MIFImage() {

	}


	public int getpWidth() {
		return pWidth;
	}

	public int getWidth() {
		return Integer.parseInt(getVariable("Width"));
	}

	public void setpWidth(int pWidth) {
		this.pWidth = pWidth;
	}
}
