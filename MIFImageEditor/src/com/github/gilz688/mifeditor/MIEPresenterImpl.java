package com.github.gilz688.mifeditor;

import java.io.File;
import java.io.IOException;

import javafx.application.Platform;
import javafx.scene.image.Image;

import com.github.gilz688.mifeditor.mif.MIFImage;
import com.github.gilz688.mifeditor.proto.MIEInteractor;
import com.github.gilz688.mifeditor.proto.MIEPresenter;
import com.github.gilz688.mifeditor.proto.MIEView;
import com.github.gilz688.mifeditor.utils.FileUtils;

public class MIEPresenterImpl implements MIEPresenter {
	private MIEInteractor interactor;
	private MIFImage mifImage;
	private MIEView view;
	private File currentFile;
	private double red, blue, green;

	public MIEPresenterImpl(MIEView view, MIEInteractor interactor) {
		this.view = view;
		this.interactor = interactor;
		currentFile = null;
	}

	@Override
	public void onAbout() {
		view.showAboutDialog();
	}

	@Override
	public void onNew() {
		mifImage = new MIFImage(100,100);
		updateImage();
		view.setTitle("Untitled Image");
	}

	@Override
	public void onOpen() {
		File file = view.showOpenDialog();
		if (file != null){
			try {
				if (FileUtils.getExtension(file).equalsIgnoreCase("mif")) {
					mifImage = interactor.openMIFImage(file);
				} else {
					String path = file.toURI().toString();
					Image image = new Image(path);
					mifImage = interactor.convertToMIF(image);
				}
				updateImage();
				currentFile = file;
				view.setTitle(file.getAbsolutePath());
			} catch (IOException e) {
				view.showErrorDialog(e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void onSave() {
		if (currentFile == null)
			onSaveAs();
		else
			save();
	}

	@Override
	public void onSaveAs() {
		currentFile = view.showSaveDialog();
		if (currentFile != null)
			save();
	}

	private void save() {
		String extension = FileUtils.getExtension(currentFile);

		switch (extension) {
		case "":
			currentFile = new File(currentFile.getAbsolutePath()+".mif");
		case "mif":
			try {
				interactor.saveMIFImage(mifImage, currentFile);
				view.setTitle(currentFile.getAbsolutePath());
			} catch (IOException e) {
				view.showErrorDialog(e.getLocalizedMessage());
			}
			break;
		default:
			try {
				interactor.saveImage(mifImage, extension, currentFile);
				view.setTitle(currentFile.getAbsolutePath());
			} catch (IOException e) {
				view.showErrorDialog(e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void onQuit() {
		 Platform.exit();
	}

	@Override
	public void onMousePressed(double x, double y) {
		mifImage = interactor.drawPixel(mifImage, x, y, red, green, blue);
		updateImage();
	}

	public void updateImage() {
		int w = mifImage.getWidth();
		int h = mifImage.size() / w;
		Image actualImage = interactor.viewImage(mifImage, w, h);
		view.showImage(actualImage);
	}

	@Override
	public void onZoom(int zoom) {
		view.scaleImage(zoom / 100.0f);
	}

	@Override
	public void onColorPick(double red, double blue, double green) {
		this.red = red;
		this.blue = blue;
		this.green = green;
	}

}
