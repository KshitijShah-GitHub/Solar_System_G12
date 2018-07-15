package application;

import java.io.BufferedReader; //Import everthing
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class QuizQ { //Class to diaplay questions from quiz

	private static int answerIndex = -1; //Make private variable for correct answer index (which button). Init to invalid number 4 future validation
	private static int selection = 0; //Private var for the index (button #) user selected. Init to invalid number 4 future validation
	private final static int NUM_OF_LINES = 7; //Num of lines in MC textfiles
	private static String answerText; //Post answer submission reponse text from txt file
	private static String[] mc = new String[NUM_OF_LINES]; //Make an array to contain the contents of the txt file

	//returns the pane that contains UI to answer questions to the main class. Deals with display and collecting answer and validating
	public static Pane getMCQ(int qNum, int NUM_OF_QUESTIONS, ArrayList<Integer> rng, Button submitAnswer) throws IOException {
		
		mc = mcReader(rng.get(qNum - 1)); //call the reader to read text file for question number as dictated by an index in the array of generated question order
		Pane mcPane = new Pane(); //make pane to house 
		System.out.println(mc); //for debug purposes

		Label questionText = new Label(mc[0]); // label contains question txt (first line of txt file)
		questionText.setWrapText(true); //make it wrap if its too long
		questionText.setFont(new Font("Gotham", 25)); //make it big and pretty
		questionText.setTextAlignment(TextAlignment.CENTER); //center it
		questionText.setAlignment(Pos.TOP_CENTER); //center it
		questionText.setMaxWidth(600); //make it wrap after this length

		Button op1 = new Button(mc[1]); //Make 4 buttons for each of the options
		op1.setPrefWidth(500); //Make it nice and big
		op1.setPrefHeight(50);
		Button op2 = new Button(mc[2]); //Make 4 buttons for each of the options
		op2.setPrefWidth(500); //Make it nice and big
		op1.setPrefHeight(50);
		Button op3 = new Button(mc[3]); //Make 4 buttons for each of the options
		op3.setPrefWidth(500); //Make it nice and big
		op1.setPrefHeight(50);
		Button op4 = new Button(mc[4]); //Make 4 buttons for each of the options
		op4.setPrefWidth(500); //Make it nice and big
		op1.setPrefHeight(50);
		
		op1.setOnAction(e -> {  //If user clicks a button. make it much bigger to signify selection 
			op1.setPrefHeight(100); //make all the other buttons small but clickable
			op2.setPrefHeight(20); 
			op3.setPrefHeight(20);
			op4.setPrefHeight(20);
			selection = 1; //set selection index to first button
			submitAnswer.setDisable(false); //make submittable in main class
		});

		op2.setOnAction(e -> { //If user clicks a button. make it much bigger to signify selection 
			op1.setPrefHeight(20);
			op2.setPrefHeight(100); //make all the other buttons small but clickable
			op3.setPrefHeight(20);
			op4.setPrefHeight(20);
			selection = 2; //set selection index to second button
			submitAnswer.setDisable(false); //make submittable in main class
		});

		op3.setOnAction(e -> { //If user clicks a button. make it much bigger to signify selection 
			op1.setPrefHeight(20);
			op2.setPrefHeight(20);
			op3.setPrefHeight(100); //make all the other buttons small but clickable
			op4.setPrefHeight(20);
			selection = 3; //set selection index to third button
			submitAnswer.setDisable(false); //make submittable in main class
		}); 

		op4.setOnAction(e -> { //If user clicks a button. make it much bigger to signify selection 
			op1.setPrefHeight(20); 
			op2.setPrefHeight(20);
			op3.setPrefHeight(20);
			op4.setPrefHeight(100); //make all the other buttons small but clickable
			selection = 4; //set selection index to fourth button
			submitAnswer.setDisable(false); //make submittable in main class
		});

		Image img = imageGetter(rng.get(qNum - 1)); //get image corresponding to loaded question

		ImageView questionImage = new ImageView(img); //make an imageview for the image
		questionImage.setFitWidth(800); //make it quite large
		questionImage.setFitHeight(800); //all the images are aquare
		//questionImage.setPreserveRatio(true);
		questionImage.isSmooth(); //Make it look nice
		
		VBox answers = new VBox (10); //A vbox to house the answer buttons
		answers.getChildren().addAll(op1, op2, op3, op4); //add answer buttons to it
		answers.setAlignment(Pos.TOP_CENTER); //make it centered
		answers.setPadding(new Insets (40,40,40,40)); //make it spaced out
		
		
		VBox question = new VBox(20); //a vbox for the question label
		question.getChildren().addAll(questionText, answers); //add the question and answer vbox to it
		question.setAlignment(Pos.CENTER); //make it centered
		//question.setPadding(new Insets (40,40,40,40));
		
		HBox qWithImage = new HBox (20); //make hbox for the image and question and answers
		qWithImage.setAlignment(Pos.CENTER); //center it
		qWithImage.setPadding(new Insets (40,40,40,40)); //space it out
		qWithImage.getChildren().addAll(questionImage, question); //add everything to the layout
		
		answerIndex = Integer.parseInt(mc[5]); //get the answwer index from the txt file
		answerText = mc[6]; //get the post submission response from the txt file
		
		
		mcPane.getChildren().addAll(qWithImage); //addd everything to the main pane layout
		return mcPane; //return this pane object to main class
	}
	
	public static boolean isSelected () { //check if anything is selected
		if (selection == 0) { //if selection value is at default invalid number, nothing selected
			return false; //return not selected
		} else { //other wise, it must be selected
			return true; //return that something is selected
		}
	}
	
	private static Image imageGetter(int qImgNum) { //private method to get the relevant img for the question
		Image qImg = null; //initlaize img 
		try {
			qImg = new Image("Q" + qImgNum + ".jpg"); //try to get the relevant image
		} catch (Exception e) {
			ErrorDialogBox.displayError("Question Image Error",
					"We couldn't find the appropriate image for this question. Sorry"); //if couldnt get, return a null image
		}
		
		return qImg; //return back to method
	}

	public static VBox getAnswerText() { //returns a label to main class containing post submit response
		
		String response; //make a string to house it
		if (selection == answerIndex) {
			response = ("CORRECT!\n\n" + answerText);  //if user answered correctly, add a correct note to response
		}		else {
			response = ("INCORRECT :(\n\n" + answerText); //else add incorrect note to response
		}
		
		Label qResponse = new Label (response); //make the label with response string
		qResponse.setMaxWidth(1000);  //make it long and make it wrap
		qResponse.setWrapText(true);
		qResponse.setTextAlignment(TextAlignment.CENTER); //center it all
		qResponse.setAlignment(Pos.CENTER); //center EVERYTHING
		qResponse.setPadding(new Insets (40,40,500,40)); //make it spaced out
		qResponse.setFont(new Font ("Gotham", 30)); //Make it big and pretty
		
		VBox qRBox = new VBox (10); //add a layout to house the label
		qRBox.setAlignment(Pos.CENTER); //center it
		qRBox.getChildren().addAll(qResponse); //add label to layout
	
		return qRBox; //return this layout to main class
	}

	private static String[] mcReader(int qFileNum) throws IOException { //reader file for the mc question
		BufferedReader reader = new BufferedReader(new FileReader("Q" + qFileNum + ".txt")); //reader for relevant question
		for (int i = 0; i < NUM_OF_LINES ; i++) { //read all the lines
			mc[i] = reader.readLine(); //add everything to string array
		}
		reader.close(); //closer reader
		return mc; //return the string array
	}

	public static boolean isCorrect() { //check if correct answer and pass boolean to main class
		if (selection == answerIndex) { //if selection index = correct answer index, answer it right
			return true; //return true
		} else {
			return false; //else answer is wrong, return false
		}

	}

}
