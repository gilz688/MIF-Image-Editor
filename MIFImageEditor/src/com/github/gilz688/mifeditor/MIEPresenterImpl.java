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
	private boolean changes;

	public MIEPresenterImpl(MIEView view, MIEInteractor interactor) {
		this.view = view;
		this.interactor = interactor;
		currentFile = null;
		changes = false;
	}

	@Override
	public void onAbout() {
		view.showAboutDialog();
	}

	@Override
	public void onNew() {
		if (changes) {
			String filename = currentFile == null ? "Untitled Image"
					: currentFile.getName();
			int result = view.showConfirmationDialog("Unsaved Changes in \""
					+ filename + "\"", "Do you want to save these changes?");
			switch (result) {
			case MIEView.RESULT_YES:
				onSave();
				if (!changes)
					break;
			case MIEView.RESULT_CANCEL:
				return;
			default:
			}
		}
		createNewFile();
	}

	private void createNewFile() {
		mifImage = new MIFImage(20, 20);
		red = blue = green = 1;
		view.resetScale();
		updateImage();
		currentFile = null;
		view.setTitle("Untitled Image");
		changes = false;
	}

	@Override
	public void onOpen() {
		File file = view.showOpenDialog();
		if (file != null) {
			view.resetScale();
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
				changes = false;
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
			currentFile = new File(currentFile.getAbsolutePath() + ".mif");
		case "mif":
			try {
				interactor.saveMIFImage(mifImage, currentFile);
				view.setTitle(currentFile.getAbsolutePath());
				changes = false;
			} catch (IOException e) {
				view.showErrorDialog(e.getLocalizedMessage());
			}
			break;
		default:
			try {
				interactor.saveImage(mifImage, extension, currentFile);
				view.setTitle(currentFile.getAbsolutePath());
				changes = false;
			} catch (IOException e) {
				view.showErrorDialog(e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void onQuit() {
		if (changes) {
			String filename = currentFile == null ? "Untitled Image"
					: currentFile.getName();
			int result = view
					.showConfirmationDialog("Unsaved Changes in \"" + filename
							+ "\"",
							"Do you want to save these changes first before closing this app?");
			switch (result) {
			case MIEView.RESULT_YES:
				onSave();
			case MIEView.RESULT_CANCEL:
				return;
			default:
			}
		}
		Platform.exit();
	}

	@Override
	public void onMousePressed(double x, double y) {
		if (mifImage != null) {
			mifImage = interactor.drawPixel(mifImage, x, y, red, green, blue);
			changes = true;
			updateImage();
		}
	}

	public void updateImage() {
		if (mifImage != null) {
			int w = mifImage.getWidth();
			int h = mifImage.size() / w;

			Image actualImage = interactor.viewImage(mifImage, w, h, 10);
			view.showImage(actualImage);
		}
	}

	@Override
	public void onZoom(int zoom) {
		double scaleFactor = zoom / 100.0f;
		view.scaleImage(scaleFactor);
	}

	@Override
	public void onColorPick(double red, double blue, double green) {
		this.red = red;
		this.blue = blue;
		this.green = green;
	}

	@Override
	public void onEyedropperToolClick(double x, double y) {
		if (mifImage != null) {
			int color = interactor.eyedropper(mifImage, x, y);
			red = ((color >> 16) & 255) / 255.0;
			green = ((color >> 8) & 255) / 255.0;
			blue = (color & 255) / 255.0;
			view.setColorPickerValue(color);
		}
	}

}
