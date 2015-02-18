package com.github.gilz688.mifeditor;

import java.io.File;

import com.github.gilz688.mifeditor.proto.MIEPresenter;
import com.github.gilz688.mifeditor.proto.MIEView;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;

public class MIEController implements MIEView {
	@FXML
	ColorPicker colorPicker;
	
	@FXML
	ImageView imageView;

	@FXML
	Slider zoomSlider;

	@FXML
	ScrollPane scrollPane;

	private Scene scene;
	private Scale scale;
	private MIEPresenter presenter;
	private final FileChooser fileChooser = new FileChooser();;

	@FXML
	private void initialize() {
		presenter = new MIEPresenterImpl(this, new MIEInteractorImpl());
		configureFileChooser();
		scale = new Scale(1, 1, 0, 0);
		imageView.getTransforms().add(scale);
		zoomSlider.valueProperty().addListener(
				(observable, oldValue, newValue) -> {
					presenter.onZoom(newValue.intValue());
				});
	}

	private void configureFileChooser() {
		fileChooser.setTitle("Open Image");
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("MIF files", "*.mif"),
				new FileChooser.ExtensionFilter("All Images", "*.bmp", "*.png",
						"*.gif"));
	}

	@FXML
	public void onAbout() {
		presenter.onAbout();
	}

	@FXML
	public void onNew() {
		presenter.onNew();
	}

	@FXML
	public void onOpen() {
		presenter.onOpen();
	}

	@FXML
	public void onSave() {
		presenter.onSave();
	}

	@FXML
	public void onSaveAs() {
		presenter.onSaveAs();
	}

	@FXML
	public void onQuit() {
		presenter.onQuit();
	}

	@FXML
	public void onMousePressed(MouseEvent event) {
		final double rawX = event.getX();
		final double rawY = event.getY();
		int x, y;
		x = (int) (rawX / imageView.getFitWidth());
		y = (int) (rawY / imageView.getFitHeight());
		presenter.onMousePressed(x, y);
	}

	@Override
	public void showAboutDialog() {

	}

	@Override
	public File showOpenDialog() {
		return fileChooser.showOpenDialog(scene.getWindow());
	}

	@Override
	public void showErrorDialog(String localizedMessage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setScene(Scene scene) {
		this.scene = scene;
	}

	@Override
	public File showSaveDialog() {
		return fileChooser.showSaveDialog(scene.getWindow());
	}

	@Override
	public void showImage(Image image) {
		imageView.setImage(image);
		zoomSlider.setValue(100);
		scale.setX(1);
		scale.setY(1);
	}

	@Override
	public void scaleImage(double scaleFactor) {
		scale.setX(scaleFactor);
		scale.setY(scaleFactor);
	}
}
