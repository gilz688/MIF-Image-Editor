package com.github.gilz688.mifeditor.mif;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map.Entry;

public class MIFImageWriter {
	private MIFImage image;

	public MIFImageWriter(MIFImage image) {
		this.image = image;
	}

	public void write(File file) throws IOException {
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		for (Entry<String, String> entry : image.getVariables().entrySet()) {
			bw.write(entry.getKey() + " = " + entry.getValue() + ";\n");
		}
		bw.write("Content\nBEGIN\n");
		bw.write("\t[0.." + String.valueOf(image.size() - 1) + "] : 000000;\n");
		int pWidth = image.getpWidth();
		int rows = image.size() / pWidth;
		for (int i = rows - 1; i > -1; i--) {
			bw.write("\t" + i * pWidth + " : ");
			for (int j = 0; j < pWidth; j++) {
				String binStr = Integer.toBinaryString(image.getData(i * pWidth
						+ j));

				for (int k = 0; k < 6 - binStr.length(); k++)
					bw.write("0");

				bw.write(binStr);
				if (j < pWidth - 1)
					bw.write(" ");
				else
					bw.write(";\n");
			}
		}
		bw.write("End;\n");
		bw.close();
	}
}
