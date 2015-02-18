package com.github.gilz688.tests.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import com.github.gilz688.mifeditor.utils.ColorConverter;
import com.github.gilz688.mifeditor.utils.ColorConverter.InvalidBitrateException;

public class ColorConverterTest {
	@Test
	public void testGenerateMask() throws InvalidBitrateException{
		int sourceBitrate = 24;
		int targetBitrate = 12;
		ColorConverter converter = new ColorConverter(sourceBitrate,targetBitrate);
		int expected = 0b111;
		int actual = converter.generateMask(3);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testConvertRGBColor() throws InvalidBitrateException {
		int sourceBitrate = 24;
		int targetBitrate = 12;
		int color = 0x6699FF;
		int expected = 0x69F;
		ColorConverter converter = new ColorConverter(sourceBitrate,targetBitrate);
		int actual = converter.convertRGBColor(color);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testConvertRGBColorBlack() throws InvalidBitrateException {
		int sourceBitrate = 24;
		int targetBitrate = 12;
		int color = 0xFFFFFF;
		int expected = 0xFFF;
		ColorConverter converter = new ColorConverter(sourceBitrate,targetBitrate);
		int actual = converter.convertRGBColor(color);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testConvertRGBColorWhite() throws InvalidBitrateException {
		int sourceBitrate = 24;
		int targetBitrate = 12;
		int color = 0x000000;
		int expected = 0x000;
		ColorConverter converter = new ColorConverter(sourceBitrate,targetBitrate);
		int actual = converter.convertRGBColor(color);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testConvertRGBColorHigherBitrate() throws InvalidBitrateException {
		int sourceBitrate = 6;
		int targetBitrate = 24;
		
		int color = 0x69F;
		int expected = 0x55FFFF;
		ColorConverter converter = new ColorConverter(sourceBitrate,targetBitrate);
		int actual = converter.convertRGBColor(color);
		assertEquals(expected, actual);
	}
	
	@Test(expected=InvalidBitrateException.class)
	public void testConvertRGBColorInvalidBitrate() throws InvalidBitrateException {
		int sourceBitrate = 12;
		int targetBitrate = 28;
		int color = 0x69F;
		ColorConverter converter = new ColorConverter(sourceBitrate,targetBitrate);
		converter.convertRGBColor(color);
	}
	
	@Test(expected=InvalidBitrateException.class)
	public void testConvertRGBColorZeroBitrate() throws InvalidBitrateException {
		int sourceBitrate = 0;
		int targetBitrate = 0;
		int color = 0;
		ColorConverter converter = new ColorConverter(sourceBitrate,targetBitrate);
		converter.convertRGBColor(color);
	}
}
