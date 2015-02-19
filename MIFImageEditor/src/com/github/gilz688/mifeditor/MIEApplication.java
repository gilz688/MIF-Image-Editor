package com.github.gilz688.mifeditor;

import com.github.gilz688.mifeditor.proto.MIEView;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MIEApplication extends Application {
	private Stage primaryStage;
	private AnchorPane rootLayout;
	public static final String APPLICATION_NAME = "MIF Image Editor";

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MIEApplication.class
					.getResource("view/MIE.fxml"));
			rootLayout = (AnchorPane) loader.load();
			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			scene.getStylesheets().add(
					getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle(APPLICATION_NAME);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			final MIEView view = (MIEView) loader.getController();
			view.setStage(primaryStage);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
