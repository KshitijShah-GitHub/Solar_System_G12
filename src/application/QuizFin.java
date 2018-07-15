package application;

import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.*;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.TextAlignment;

public class QuizFin { // New Class to run after user finishes quiz to give
							// feedback

	static final double QBOX_HEIGHT = 850; // Constants for window size
	static final double QBOX_WIDTH = 1280;
	static MediaPlayer mediaPlayer; // Global media player so event listener can access without making it a final variable

	public static void scoreWindow(int score, int NUM_OF_QUESTIONS) {
		Stage scoreWindow = new Stage(); // Make a window for the video
		File link; // Make new file object titled link

		scoreWindow.getIcons().add(new Image("PlanetWindowIcon.png")); // Set window icon, title, and size
		scoreWindow.setTitle("End of Quiz!");
		scoreWindow.setHeight(QBOX_HEIGHT);
		scoreWindow.setWidth(QBOX_WIDTH);
		scoreWindow.setAlwaysOnTop(true); // Make sure it pops up above everything else
		scoreWindow.setResizable(false); // make sure its not resizeable
		scoreWindow.setOnCloseRequest(e -> closeMethod(scoreWindow)); //When user clicks X button, 
		
		int scorePercent = (int) Math.round(((score*100)/NUM_OF_QUESTIONS)); //round it to a percent grade
		Label message = new Label("You got " + score + "/" + NUM_OF_QUESTIONS + " correct, thats " + scorePercent+ "%."); // Label
		message.setWrapText(true); // make it wrap if too long
		message.setMaxWidth(QBOX_WIDTH - 50); // make it not go too close to edge of window
		message.setTextAlignment(TextAlignment.CENTER); // center align
		message.setAlignment(Pos.CENTER);
		message.setFont(Font.font("Gotham", FontPosture.ITALIC, 45));
		message.setTextFill(Color.IVORY); // change color, make white-ish
		message.setPadding(new Insets(20, 40, 10, 40)); // add padding for asthetic purposes
		
		if (scorePercent == 100) { // select video based on the score they got
			link = new File("score10.mp4"); // if they got perfect, congratulations video
		} else if ((scorePercent >= 70) && (scorePercent < 100)) {
			link = new File("score7.mp4"); // if they did good but not perfect, a congratulations video thats less happy
		} else if ((scorePercent >= 50) && (scorePercent < 70)) {
			link = new File("score5.mp4"); // if they barely passed, a semi sad video
		} else {
			link = new File("score0.mp4"); // if they failed, tell them they suck
		}

		try { // Try to get the video from selected link and add it to a mediaplayer 
			mediaPlayer = new MediaPlayer(new Media(link.toURI().toURL().toString())); 
			scoreWindow.show(); //only show this if user will actually see something
		} catch (Exception e) {
			// you should be able to find it becuase its there, but if they cant, then just show a simple box
			ErrorDialogBox.displayError("Quiz Ended", message.getText());
		}

		MediaView mediaView = new MediaView(mediaPlayer); // make new media viewer to view the mediaplayer through
		mediaView.setFitWidth(QBOX_WIDTH); // make it 720x1280 (HD)
		mediaView.setFitHeight(720);
		
		mediaPlayer.play(); // make it play to begin with
		mediaPlayer.setAutoPlay(true); // make it autoplay on loop
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // loop indefinitely until user closes
		
		VBox layout = new VBox(10); //Add layout for containing the label and the video
		layout.setAlignment(Pos.CENTER); //make everything centered
		layout.getChildren().addAll(message, mediaView); //add the label and the video to the layout
		layout.setStyle("-fx-background-color: black"); //make the layout background black for fun

		Scene scene = new Scene(layout, QBOX_WIDTH, QBOX_HEIGHT); //make a scene with the layout with sizes defined by constants

		scoreWindow.setScene(scene); //add the scene to the window (stage)
	}

	private static void closeMethod(Stage scoreWindow) {
		mediaPlayer.stop(); //When user closes, end mediaPlayer content because otherwise audio keeps playing in background indefinitely
		scoreWindow.close(); //close the window
	}
}
