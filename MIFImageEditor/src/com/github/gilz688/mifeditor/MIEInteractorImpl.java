package com.github.gilz688.mifeditor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import com.github.gilz688.mifeditor.mif.MIFImage;
import com.github.gilz688.mifeditor.mif.MIFImageReader;
import com.github.gilz688.mifeditor.mif.MIFImageWriter;
import com.github.gilz688.mifeditor.proto.MIEInteractor;
import com.github.gilz688.mifeditor.utils.ColorConverter;
import com.github.gilz688.mifeditor.utils.ColorConverter.InvalidBitrateException;

public class MIEInteractorImpl implements MIEInteractor {

	@Override
	public MIFImage openMIFImage(File file) throws FileNotFoundException,
			IOException {
		MIFImageReader reader = new MIFImageReader();
		return reader.read(file);
	}

	@Override
	public void saveMIFImage(MIFImage mifImage, File file)
			throws FileNotFoundException, IOException {
		MIFImageWriter writer = new MIFImageWriter(mifImage);
		writer.write(file);
	}

	@Override
	public void saveImage(MIFImage mifImage, String format, File file)
			throws FileNotFoundException, IOException {
		int w = mifImage.getWidth();
		int h = mifImage.getHeight();
		Image image = viewImage(mifImage, w, h);
		BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
		ImageIO.write(bImage, format, file);
	}

	@Override
	public Image viewImage(MIFImage mifImage, int width, int height) {
		WritableImage image = new WritableImage(width, height);
		PixelWriter writer = image.getPixelWriter();
		try {
			ColorConverter converter = new ColorConverter(6, 24);
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					int color = mifImage.getData(y * width + x);

					color = converter.convertRGBColor(color) | 0xff000000;
					writer.setArgb(x, y, color);
				}
			}
		} catch (InvalidBitrateException e) {
			e.printStackTrace();
		}
		return image;
	}

	@Override
	public MIFImage convertToMIF(Image image) {
		PixelReader reader = image.getPixelReader();
		int height = (int) image.getHeight();
		int width = (int) image.getWidth();
		MIFImage mifImage = new MIFImage(width, height);

		try {
			ColorConverter converter = new ColorConverter(24, 6);
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					int color = (reader.getArgb(x, y)) & 0x00ffffff;
					color = converter.convertRGBColor(color);
					mifImage.putData(y * width + x, color);
				}
			}
		} catch (InvalidBitrateException e) {
			e.printStackTrace();
		}
		return mifImage;
	}

	@Override
	public MIFImage drawPixel(MIFImage mifImage, double rawX, double rawY, double red, double green, double blue) {
		int width = (int) mifImage.getWidth();
		int height = mifImage.getHeight();
		int x = (int) (rawX * width);
		int y = (int) (rawY * height);
		ColorConverter converter;
		try {
			converter = new ColorConverter(24,6);
			mifImage.putData(y * width + x, converter.convertRGBColor(red*255, green*255, blue*255));
		} catch (InvalidBitrateException e) {
			e.printStackTrace();
		}
		return mifImage;
	}

}
