package com.github.gilz688.mifeditor;

import java.io.File;
import java.util.Optional;

import com.github.gilz688.mifeditor.proto.MIEPresenter;
import com.github.gilz688.mifeditor.proto.MIEView;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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

	private Stage stage;

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
					
					MouseButton button = event.getButton();
					switch(button){
						case PRIMARY:
							presenter.onMousePressed(x, y);
							break;
						case SECONDARY:
							presenter.onEyedropperToolClick(x, y);
							break;
						default:
					}

					
				});
		
		colorPicker.setValue(Color.WHITE);
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
		presenter.onNew();
	}

	public void resetScale(){
		zoomSlider.setValue(100);
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
	public void onColorPick() {
		Color color = colorPicker.getValue();
		double red = color.getRed();
		double green = color.getGreen();
		double blue = color.getBlue();
		presenter.onColorPick(red,blue,green);
	}
	
	@Override
	public void showAboutDialog() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About");
		alert.setHeaderText(null);
		alert.setContentText("Copyright (c) 2015 Gilz688");
		alert.showAndWait();
	}

	@Override
	public File showOpenDialog() {
		return fileChooser.showOpenDialog(stage);
	}

	@Override
	public void showErrorDialog(String localizedMessage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(localizedMessage);
		alert.showAndWait();
	}

	@Override
	public void setStage(Stage stage) {
		this.stage = stage;
		
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	          public void handle(WindowEvent we) {
	              presenter.onQuit();
	              we.consume();
	          }
	      });  
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
	
	@Override
	public int showConfirmationDialog(String header, String content){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText(header);
		alert.setContentText(content);

		ButtonType buttonYes = new ButtonType("Yes");
		ButtonType buttonNo = new ButtonType("No");
		ButtonType buttonCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(buttonYes, buttonNo, buttonCancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonYes){
		    return RESULT_YES;
		} else if (result.get() == buttonNo) {
		    return RESULT_NO;
		} else {
		    // ... user chose CANCEL or closed the dialog
			return RESULT_CANCEL;
		}
	}

	@Override
	public void setColorPickerValue(int raw) {
		int r = ((raw >> 16) & 255);
		int g = ((raw >> 8) & 255);
		int b = ((raw) & 255);
		colorPicker.setValue(Color.rgb(r,g,b));
	}
}
