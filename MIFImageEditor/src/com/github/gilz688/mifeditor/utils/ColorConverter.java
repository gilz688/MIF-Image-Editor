package com.github.gilz688.mifeditor.utils;

public class ColorConverter {
	private int sourceChannel;
	private int targetChannel;
	private int redMask;
	private int greenMask;
	private int blueMask;
	private int targetBlueMask;
	private float factor;

	
	public ColorConverter(int sourceBitrate, int targetBitrate) throws InvalidBitrateException{
		if(sourceBitrate % 3 != 0 || targetBitrate % 3 != 0)
			throw new InvalidBitrateException("Color bitrate should be a multiple of 3.");
		if(sourceBitrate == 0 || targetBitrate ==0)
			throw new InvalidBitrateException("Color bitrate should not be zero.");
		
		sourceChannel = sourceBitrate / 3;
		targetChannel = targetBitrate / 3;
		generateMasks();
		factor = ((float) targetBlueMask)/blueMask;
	}
	
	private void generateMasks() {
		int sourceMask = generateMask(sourceChannel);
		int targetMask = generateMask(targetChannel);
		
		redMask = sourceMask << (sourceChannel * 2);
		greenMask = sourceMask << sourceChannel;
		blueMask = sourceMask;
		targetBlueMask = targetMask;
	}

	public int generateMask(int bits){
		return (int) (Math.pow(2,bits)-1);
	}
	
	public int convertRGBColor(int color) throws InvalidBitrateException {
		int r = (color & redMask) >> (2 * sourceChannel);
		int g = (color & greenMask) >> sourceChannel;
		int b = (color & blueMask);
		
		int tr = Math.round(r * factor);
		int tg = Math.round(g * factor);
		int tb = Math.round(b * factor);
		
		int outputColor = (tr << (2 * targetChannel) ) | (tg << (targetChannel) ) | tb;
		return outputColor;
	}
	
	public int convertRGBColor(double r, double g, double b) throws InvalidBitrateException {
		int tr = (int) Math.round(r * factor);
		int tg = (int) Math.round(g * factor);
		int tb = (int) Math.round(b * factor);
		
		int outputColor = (tr << (2 * targetChannel) ) | (tg << (targetChannel) ) | tb;
		return outputColor;
	}

	/*
	 *	Exception thrown when color bit rate is not a multiple of 3. 
	 */
	public class InvalidBitrateException extends Exception{
		public InvalidBitrateException(String string) {
			super(string);
		}

		private static final long serialVersionUID = 8557727606689592638L;
	}
}
