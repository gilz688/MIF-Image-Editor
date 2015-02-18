package com.github.gilz688.mifeditor.proto;

import java.io.File;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public interface MIEView {

	public void showAboutDialog();

	public File showOpenDialog();

	public void showErrorDialog(String localizedMessage);

	public void setScene(Scene scene);

	public abstract File showSaveDialog();

	public abstract void showImage(Image image);

	public abstract void scaleImage(double scaleFactor);

}