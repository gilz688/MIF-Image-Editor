package com.github.gilz688.mifeditor.proto;

import java.io.File;

import javafx.scene.image.Image;
import javafx.stage.Stage;

public interface MIEView {

	public void showAboutDialog();

	public File showOpenDialog();

	public void showErrorDialog(String localizedMessage);

	public abstract File showSaveDialog();

	public abstract void showImage(Image image);

	public abstract void scaleImage(double scaleFactor);

	public void setTitle(String path);

	public void setStage(Stage stage);

	public void resetScale();

	public int showConfirmationDialog(String header, String content);

	public static final int RESULT_CANCEL = 0;	
	public static final int RESULT_YES = 1;
	public static final int RESULT_NO = 2;
	public void setColorPickerValue(int color);

}