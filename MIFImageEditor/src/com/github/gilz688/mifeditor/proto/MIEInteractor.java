package com.github.gilz688.mifeditor.proto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.scene.image.Image;

import com.github.gilz688.mifeditor.mif.MIFImage;

public interface MIEInteractor {

	public MIFImage openMIFImage(File file)
			throws FileNotFoundException, IOException;

	public void saveMIFImage(MIFImage image, File file)
			throws FileNotFoundException, IOException;

	public Image viewImage(MIFImage mif, int width, int height);

}