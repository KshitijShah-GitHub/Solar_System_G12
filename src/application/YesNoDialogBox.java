package application;

import javafx.geometry.Pos;
import javafx.stage.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

public class YesNoDialogBox {

	static boolean answer;	
	static final double YNBOX_HEIGHT = 200;
	static final double YNBOX_WIDTH = 600;
	
	public static boolean displayYNBox(String title, String message) {
		Stage yesNoBox= new Stage();
		
		//Adds the cool planet icon because its cool and unifies the relationship to main application
		yesNoBox.getIcons().add(new Image("PlanetWindowIcon.png"));
		
		//Makes it so user HAS to interact with error before continuing interaction with other windows
		yesNoBox.initModality(Modality.APPLICATION_MODAL);
		yesNoBox.initStyle(StageStyle.UNIFIED);
		
		//Set title to whatever title passed from elsewhere to display
		yesNoBox.setTitle(title);
		
		//Set size of dialog Box and window properties
		yesNoBox.setHeight(YNBOX_HEIGHT);
		yesNoBox.setWidth(YNBOX_WIDTH);
		yesNoBox.setAlwaysOnTop(true);
		yesNoBox.setResizable(false);
		
		//new label for the main message on the YN box
		Label YNmessage = new Label (message);
		
		//the buttons (true and false)
		Button yesButton = new Button ("Yes");
		Button noButton = new Button ("No");
		
		//lambda expression for button response
		yesButton.setOnAction(e -> {
			answer = true;
			yesNoBox.close();
		});
		
		noButton.setOnAction(e -> {
			answer = false;
			yesNoBox.close();
		});
			
		//new layout to vertically organize objects in window, 10px buffer
		VBox layout = new VBox (20);
		layout.getChildren().addAll(YNmessage, yesButton, noButton); //add label and button to this layout format
		layout.setAlignment(Pos.CENTER);
		
		//Make new scene to display layout, small rectangle
		Scene scene = new Scene (layout, YNBOX_WIDTH, YNBOX_HEIGHT);
		yesNoBox.setScene(scene); //add complete scene to window stage
		
		yesNoBox.showAndWait(); //show and wait for user to interact without ignoring
		
		return answer;
	}
	
	public static boolean displayYNBox(String title, String message, String buttonText1, String buttonText2) {
		Stage yesNoBox= new Stage();
		
		//Adds the cool planet icon because its cool and unifies the relationship to main application
		yesNoBox.getIcons().add(new Image("PlanetWindowIcon.png"));
		
		//Makes it so user HAS to interact with error before continuing interaction with other windows
		yesNoBox.initModality(Modality.APPLICATION_MODAL);
		
		//Set title to whatever title passed from elsewhere to display
		yesNoBox.setTitle(title);
		
		//Set size of dialog Box and window properties
		yesNoBox.setMinHeight(YNBOX_HEIGHT);
		yesNoBox.setMinWidth(YNBOX_WIDTH);
		yesNoBox.setAlwaysOnTop(true);
		yesNoBox.setResizable(false);
		
		//message to display
		Label YNmessage = new Label (message);
		
		//make 2 buttons
		Button noButton = new Button (buttonText1);
		Button yesButton = new Button (buttonText2);
		
		//lambda expressions to change value as a result of which button is clicked
		yesButton.setOnAction(e -> {
			answer = true;
			yesNoBox.close();
		});
		
		noButton.setOnAction(e -> {
			answer = false;
			yesNoBox.close();
		});
			
		//new layout to vertically organize objects in window, 10px buffer
		VBox layout = new VBox (20);
		layout.getChildren().addAll(YNmessage, yesButton, noButton); //add label and button to this layout format
		layout.setAlignment(Pos.CENTER);
		
		//Make new scene to display layout, small rectangle
		Scene scene = new Scene (layout, YNBOX_WIDTH, YNBOX_HEIGHT);
		yesNoBox.setScene(scene); //add complete scene to window stage
		
		yesNoBox.showAndWait(); //show and wait for user to interact without ignoring
		
		return answer;
	}

}
