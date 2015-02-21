package com.github.gilz688.mifeditor.utils;

import java.io.File;

public class FileUtils {
	public static String getExtension(File file){
		String extension = "";
		String fileName = file.getName();
		int i = fileName.lastIndexOf('.');
		if (i > 0) {
		    extension = fileName.substring(i+1);
		}
		return extension;
	}
}
