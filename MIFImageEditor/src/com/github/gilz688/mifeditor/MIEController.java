package com.github.gilz688.mifeditor;

import java.io.File;

import com.github.gilz688.mifeditor.proto.MIEPresenter;
import com.github.gilz688.mifeditor.proto.MIEView;

import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MIEController implements MIEView {
	
	@FXML
	ColorPicker colorPicker;

	@FXML
	ImageView imageView;

	@FXML
	Slider zoomSlider;

	@FXML
	ScrollPane scrollPane;

	private Scale scale;
	private MIEPresenter presenter;
	private final FileChooser fileChooser = new FileChooser();

	private Stage stage;;

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

		imageView.addEventHandler(MouseEvent.MOUSE_CLICKED,
				(event) -> {
			        double w = imageView.getBoundsInLocal().getWidth();
			        double h = imageView.getBoundsInLocal().getHeight();
					double x = event.getX()/w;
					double y = event.getY()/h;
					presenter.onMousePressed(x, y);
				});
		
		colorPicker.setValue(Color.BLACK);
	}

	private void configureFileChooser() {
		fileChooser.setTitle("Open Image");
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("MIF files", "*.mif"),
				new FileChooser.ExtensionFilter("All Images", "*.mif", "*.bmp", "*.png",
						"*.gif"));
	}

	@FXML
	public void onAbout() {
		presenter.onAbout();
	}

	@FXML
	public void onNew() {
		resetScale();
		presenter.onNew();
	}

	public void resetScale(){
		zoomSlider.setValue(100);
	}
	
	@FXML
	public void onOpen() {
		resetScale();
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
	public void onColorPick() {
		Color color = colorPicker.getValue();
		double red = color.getRed();
		double green = color.getGreen();
		double blue = color.getBlue();
		presenter.onColorPick(red,blue,green);
	}
	
	@Override
	public void showAboutDialog() {

	}

	@Override
	public File showOpenDialog() {
		return fileChooser.showOpenDialog(stage);
	}

	@Override
	public void showErrorDialog(String localizedMessage) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@Override
	public File showSaveDialog() {
		return fileChooser.showSaveDialog(stage);
	}

	@Override
	public void showImage(Image image) {
		imageView.setImage(image);
	}

	@Override
	public void scaleImage(double scaleFactor) {
		scale.setX(scaleFactor);
		scale.setY(scaleFactor);
	}

	@Override
	public void setTitle(String path) {
		stage.setTitle(MIEApplication.APPLICATION_NAME + " - " + path);
	}
}
