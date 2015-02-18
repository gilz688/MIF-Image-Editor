package com.github.gilz688.mifeditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import com.github.gilz688.mifeditor.mif.MIFImage;
import com.github.gilz688.mifeditor.mif.MIFImageReader;
import com.github.gilz688.mifeditor.mif.MIFImageWriter;
import com.github.gilz688.mifeditor.proto.MIEInteractor;

public class MIEInteractorImpl implements MIEInteractor {

	@Override
	public MIFImage openMIFImage(File file) throws FileNotFoundException,
			IOException {
		MIFImageReader reader = new MIFImageReader();
		return reader.read(file);
	}

	@Override
	public void saveMIFImage(MIFImage image, File file)
			throws FileNotFoundException, IOException {
		MIFImageWriter writer = new MIFImageWriter(image);
		writer.write(file);
	}

	@Override
	public Image viewImage(MIFImage mif, int width, int height) {
		WritableImage image = new WritableImage(width, height);
		PixelWriter writer = image.getPixelWriter();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int color = mif.getData(y * width + x);
				int r = Math.round(((color & 0b110000) >> 4) * 63.75f);
				int g = Math.round(((color & 0b001100) >> 2) * 63.75f);
				int b = Math.round((color & 0b000011) * 63.75f);
				writer.setColor(x, y, Color.rgb(r, g, b));
			}
		}
		return image;
	}
	
	public MIFImage convertToMIF(Image image){
		PixelReader reader = image.getPixelReader();
		int height = (int) image.getHeight();
		int width = (int) image.getWidth();
		MIFImage mifImage = new MIFImage(width,height);
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {	
				int c, r, g, b;
				c = reader.getArgb(x, y);
				c = ((c >> 24) & 0x000000FF) | ((c & 0x00FF0000) >> 8) | ((c & 0x0000FF00) << 8) | ((c & 0x000000FF) << 24);
				c >>= 8;
				b = (c & 0x00FF0000) >> 16;
				g = (c & 0x0000FF00) >>  8;
				r = (c & 0x000000FF);
				c = r + g + b;
				c /= 6;
				mifImage.putData(y * width + x, c);
			}
		}
		return mifImage;
	}
}
