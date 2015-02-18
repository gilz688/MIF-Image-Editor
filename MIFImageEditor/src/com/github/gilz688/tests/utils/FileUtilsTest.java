package com.github.gilz688.tests.utils;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import com.github.gilz688.mifeditor.utils.FileUtils;

public class FileUtilsTest {

	@Test
	public void testGetExtension() {
		File file = new File("test.mif");
		String expected = "mif";
		String actual = FileUtils.getExtension(file);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetExtensionDoubleExtension() {
		File file = new File("test.mif.bak");
		String expected = "bak";
		String actual = FileUtils.getExtension(file);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetExtensionNoExtension() {
		File file = new File("test");
		String expected = "";
		String actual = FileUtils.getExtension(file);
		assertEquals(expected, actual);
	}
}
