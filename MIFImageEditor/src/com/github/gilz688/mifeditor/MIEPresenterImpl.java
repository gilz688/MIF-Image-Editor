package com.github.gilz688.mifeditor;

import java.io.File;
import java.io.IOException;

import javafx.scene.image.Image;

import com.github.gilz688.mifeditor.mif.MIFImage;
import com.github.gilz688.mifeditor.proto.MIEInteractor;
import com.github.gilz688.mifeditor.proto.MIEPresenter;
import com.github.gilz688.mifeditor.proto.MIEView;

public class MIEPresenterImpl implements MIEPresenter {
	private MIEInteractor interactor;
	private MIFImage mifImage;
	private MIEView view;
	private File currentFile;

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
		mifImage = new MIFImage();
	}

	@Override
	public void onOpen() {
		File file = view.showOpenDialog();
		if (file != null)
			try {
				mifImage = interactor.openMIFImage(file);
				int w = mifImage.getpWidth();
				int h = mifImage.size() / w;
				Image actualImage = interactor.viewImage(mifImage, w, h);
				view.showImage(actualImage);
				currentFile = file;
			} catch (IOException e) {
				view.showErrorDialog(e.getLocalizedMessage());
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
		if(currentFile != null)
			save();
	}

	private void save() {
		String extension = FileUtils.getExtension(currentFile);
		
		switch(extension){
			case "mif":
				try {
					interactor.saveMIFImage(mifImage, currentFile);
				} catch (IOException e) {
					view.showErrorDialog(e.getLocalizedMessage());
				}
				break;
			default:
		}
	}

	@Override
	public void onQuit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMousePressed(int x, int y) {
		
	}

	@Override
	public void onZoom(int zoom) {
		view.scaleImage(zoom / 100.0f);
	}

}
