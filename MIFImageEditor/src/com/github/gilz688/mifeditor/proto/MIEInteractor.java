package com.github.gilz688.mifeditor.proto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.scene.image.Image;

import com.github.gilz688.mifeditor.mif.MIFImage;

public interface MIEInteractor {

	public MIFImage openMIFImage(File file)
			throws FileNotFoundException, IOException;

	public void saveMIFImage(MIFImage mifImage, File file)
			throws FileNotFoundException, IOException;

	public Image viewImage(MIFImage mifImage, int width, int height);

	public MIFImage convertToMIF(Image image);

	public void saveImage(MIFImage mifImage, String format, File file)
			throws FileNotFoundException, IOException;

	public MIFImage drawPixel(MIFImage mifImage, double x, double y,
			double red, double green, double blue);
}