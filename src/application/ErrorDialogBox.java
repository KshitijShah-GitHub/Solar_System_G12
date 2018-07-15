package application;

import javafx.geometry.Pos;
import javafx.stage.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class ErrorDialogBox {
	
	static final double EBOX_HEIGHT = 200;
	static final double EBOX_WIDTH = 550;
	
	public static void displayError (){
		//Makes new stage to display error in new window
		Stage errorWindow = new Stage();
		
		errorWindow.getIcons().add(new Image ("PlanetWindowIcon.png"));
		errorWindow.setTitle("An Error or Exception Occured");
		errorWindow.initModality(Modality.APPLICATION_MODAL);
		errorWindow.setHeight (EBOX_HEIGHT);
		errorWindow.setWidth(EBOX_WIDTH);
		errorWindow.setAlwaysOnTop(true);
		errorWindow.setResizable(false);
		
		Label errorMessage = new Label ("An error ocurred in the execution of the program, sorry. Tell Kshitij what you did so he can try to fix it");
		errorMessage.setWrapText(true);
		errorMessage.setMaxWidth(EBOX_WIDTH-50);
		errorMessage.setTextAlignment(TextAlignment.CENTER);
		errorMessage.setAlignment(Pos.CENTER);
		
		Button closeButton = new Button ("Close");
		closeButton.setOnAction(e -> errorWindow.close());
		
		VBox layout = new VBox (20);
		layout.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(errorMessage, closeButton);
		
		Scene scene = new Scene (layout, EBOX_WIDTH, EBOX_HEIGHT);
		
		errorWindow.setScene(scene);
		errorWindow.showAndWait();
	}
	
	public static void displayError(String title, String message) {
		//Makes new stage to display error in new window
		Stage errorWindow = new Stage();
		
		errorWindow.getIcons().add(new Image ("PlanetWindowIcon.png"));
		errorWindow.setTitle(title);
		errorWindow.initModality(Modality.APPLICATION_MODAL);
		errorWindow.setHeight (EBOX_HEIGHT);
		errorWindow.setWidth(EBOX_WIDTH);
		errorWindow.setAlwaysOnTop(true);
		errorWindow.setResizable(false);
		
		Label errorMessage = new Label (message);
		errorMessage.setWrapText(true);
		errorMessage.setMaxWidth(EBOX_WIDTH-50);
		errorMessage.setTextAlignment(TextAlignment.CENTER);
		errorMessage.setAlignment(Pos.CENTER);
		
		Button closeButton = new Button ("Close");
		closeButton.setOnAction(e -> errorWindow.close());
		
		VBox layout = new VBox (20);
		layout.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(errorMessage, closeButton);
		
		Scene scene = new Scene (layout, EBOX_WIDTH, EBOX_HEIGHT);
		
		errorWindow.setScene(scene);
		errorWindow.showAndWait();
	}

}
