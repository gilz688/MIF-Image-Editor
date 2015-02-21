package com.github.gilz688.mifeditor.mif;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MIFImageReader {
	public MIFImage read(File file) throws FileNotFoundException, IOException {
		MIFImage image = new MIFImage();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		Pattern initVar = Pattern.compile("\\s*(\\w+)\\s*=\\s*(\\w+);\\s*$");
		while ((line = br.readLine()) != null) {
			Matcher dMatcher = initVar.matcher(line);
			if (dMatcher.matches()) {
				String variable = dMatcher.group(1);
				String value = dMatcher.group(2);
				image.putVariable(variable, value);
			} else if (line.trim().equalsIgnoreCase("BEGIN")) {
				Pattern memRange = Pattern
						.compile("\\s*\\x5b(\\d+)\\x2e{2}(\\d+)\\x5d\\s*:\\s+(.*);\\s*$");
				Pattern memLoc = Pattern
						.compile("\\s*(\\d+)\\s*:\\s+(.*);\\s*$");

				while ((line = br.readLine()) != null) {
					Matcher mrMatcher = memRange.matcher(line);
					if (mrMatcher.matches()) {
						int start = Integer.parseInt(mrMatcher.group(1));
						int end = Integer.parseInt(mrMatcher.group(2));
						int value = Integer.parseInt(mrMatcher.group(3), 2);
						for (int i = start; i <= end; i++)
							image.putData(i, value);
					} else {
						Matcher mlMatcher = memLoc.matcher(line);
						if (mlMatcher.matches()) {
							int address = Integer.parseInt(mlMatcher.group(1));
							String[] values = mlMatcher.group(2).split(" ");
							if (image.getWidth() == -1)
								image.setWidth(values.length);
							for (String str : values) {
								int value = Integer.parseInt(str, 2);
								image.putData(address, value);
								address++;
							}
						}
					}
					if (line.trim().equalsIgnoreCase("End;")) {
						if (image.size() == 19200) {
							image.setWidth(160);
						}
						break;
					}
				}
			}
		}
		br.close();
		return image;
	}
}
