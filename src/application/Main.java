package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;

/*--------------------------------------------------------------------------------------*/
/*  SolarSystem.java  -  The program is intended to be a study guide/aid for sixth grade*/
/*           students in Ontario, currently learning or interested in learning about the*/
/*           Solar System. The program is to give the student a cursory overview of the */
/*           topics that will be covered in their Unit 2 of Science and Technology      */
/*           class. It is not intended to replace in class learning, but rather to      */
/*           supplement it. It will give text based lessons featuring diagrams and      */
/*           images and a way to test the student’s retention using different question  */
/*           formats. The topics covered will be the properties of the Solar System,    */
/*           including scale, size, masses, temperatures, habitability and relative     */
/*           location of all 8 planets, information about the demotion of Pluto as a    */
/*           planet, information about the function of the sun, and most importantly    */
/*           the features of Earth that allow it to support life.                       */
/*                                                                                      */
/*--------------------------------------------------------------------------------------*/
/*  Author:  Kshitij Shah                                                               */
/*  Date:    May 25 - June 14 2017                                                      */
/*--------------------------------------------------------------------------------------*/
/*  Input:   Mouse (Buttons, menus)                                                     */
/*           Keyboard (search bar, arrow keys for listview, enterkey for searchbar)     */
/*  Output: Visual response to inputs such as error dialogs for errors, changing screen */
/*          going to next page in lessons, next question, response if question answer is*/
/*          right or wrong, etc.                                                        */
/*--------------------------------------------------------------------------------------*/

public class Main extends Application {

	// Declares important variables as global so they can be accessed from anywhere (only variables that are relevant to all parts of program)
	static Stage window; // make a new stage name because its more descriptive
	static double screenW = getScreenWidth(); // get values for current monitor resolution
	static double screenH = getScreenHeight();

	static TabPane navTabs = new TabPane(); // Make a tab pane to house all the tabs
	static Tab mainMenuTab = new Tab("Main Menu"); // Make tabs to fill the tab pane
	static Tab lessonsTab = new Tab("Lessons");
	static Tab quizTab = new Tab("Quiz");

	//Add some important constants so some major things can be changed really quickly
	static final int NUM_OF_LESSONS = 13;
	static final int NUM_OF_PLANETS = 11;
	static final int NUM_OF_QUESTIONS = 11;
	//static DecimalFormat df = new DecimalFormat("####.##"); //Add a general decimal format for anything that will need to display score

	//qNum, Score, and rngNumbers are here to get around listeners needing final values
	static int qNum = 0;
	static int score;
	static ArrayList<Integer> rngNumbers = new ArrayList<Integer>();
	static int answer;

	@Override
	public void start(Stage primaryStage) throws IOException {
		try {
			// Make new Stage called window for easier retrieval
			window = primaryStage;
			setStage(window); // Call Method to set up window properties

			// Call method to set up properties of tab pane
			setTabs();

			// Calls Method to set up the main menu tab content
			setMainMenuTab();

			// Calls Method to set up the lesson tab content
			setLessonTab();

			// Calls method to set up the quiz
			setQuizTab();

			// make new default scene to hold tab pane at size of recorded width and height
			Scene scene = new Scene(navTabs, screenH, screenW);
			window.setScene(scene); //add the scene to the stage (window)

		} catch (Exception e) {
			e.printStackTrace(); //If anything unsxpected goes wrong, its something I dont know about so just close program
			window.setOpacity(0.5); //Dim window
			ErrorDialogBox.displayError();
			window.close();
		}
	}

	public static void setQuizTab() throws IOException { //set up quiz tab
		
		BorderPane quizTabBP = new BorderPane(); //Add a borderpane to house major nodees 

		String[] planetNames = new String[NUM_OF_PLANETS + 1]; //make a string to house the planet names 
		planetNames = getPlanetNames(); //get from method

		Planet[] planets = new Planet[NUM_OF_PLANETS + 1]; //make planet object using planet names 
		for (int i = 0; i < NUM_OF_PLANETS; i++) { //make objects for all the planets
			planets[i] = new Planet();
			planets[i].setProperties(planetNames[i]); //add properties contained in txt files
			System.out.println(planets[i].getName() + " " + planets[i].getNum() + " " + planets[i].getPlanetNum()); //DEBUG ONLY
		}

		Label qTitle = new Label("CLICK START QUIZ"); //Title of the whole quiz tab
		qTitle.setFont(new Font("Gotham Bold", 45)); //make it big and pretty
		qTitle.setTextAlignment(TextAlignment.CENTER); //center it
		qTitle.setWrapText(true); //make it wrap if too long
		qTitle.setAlignment(Pos.CENTER); //center

		Label scoreTitle = new Label("Start the Quiz!"); //title for score pane
		scoreTitle.setFont(new Font("Gotham Bold", 20)); //big and pretty
		scoreTitle.setTextAlignment(TextAlignment.CENTER); //center
		scoreTitle.setWrapText(true); //redundant unless screen is too small
		scoreTitle.setAlignment(Pos.CENTER); //center everything

		Label scoreText = new Label("Your score will be displayed here"); //label for current score and percent, default text
		scoreText.setFont(new Font("Gotham", 15)); //big and pretty
		scoreText.setTextAlignment(TextAlignment.CENTER); //center everything
		scoreText.setWrapText(true); //wrap
		scoreText.setAlignment(Pos.CENTER); //center

		HBox scoreTitleHBox = new HBox(); //make layout for the score title
		scoreTitleHBox.getChildren().addAll(scoreTitle); //add title to layout
		scoreTitleHBox.setAlignment(Pos.TOP_CENTER); 
		scoreTitleHBox.setPadding(new Insets(40, 40, 40, 20));

		HBox scoreTextHBox = new HBox(); //for the score and percent
		scoreTextHBox.setAlignment(Pos.TOP_CENTER); //center
		scoreTextHBox.setPadding(new Insets(10, 40, 10, 20));
		scoreTextHBox.getChildren().addAll(scoreText);

		HBox titleBox = new HBox(); //for the title
		titleBox.setAlignment(Pos.CENTER); //center
		titleBox.setPadding(new Insets(10, 10, 10, 10));
		titleBox.getChildren().addAll(qTitle);

		Button startQ = new Button("Start Quiz!"); //button to start quiz
		startQ.setPrefSize(screenW * 0.20, 30);

		Button submitAnswer = new Button("Submit Response"); //button to submit answer
		submitAnswer.setPrefSize(screenW * 0.2, 30);
		submitAnswer.setVisible(false); //make it invisible until quiz started
		submitAnswer.setDisable(true);

		Button nextQ = new Button("Next Question"); //button to go to next question
		nextQ.setPrefSize(screenW * 0.2, 30); 
		nextQ.setVisible(false); //make it invisible until quiz started
		nextQ.setDisable(true);

		VBox scoreStartVBox = new VBox(10); //to hold score and buttons
		scoreStartVBox.getChildren().addAll(scoreTextHBox, startQ, submitAnswer, nextQ);
		scoreStartVBox.setPadding(new Insets(10, 40, screenH * 0.60, 20));

		GridPane qCheckPane = new GridPane(); //pane to hold progress list
		qCheckPane.setVgap(20); //make it spread out
		qCheckPane.setHgap(20); //spaced out
		qCheckPane.setPadding(new Insets(0, 40, 40, 40));
		qCheckPane.setAlignment(Pos.CENTER_LEFT);

		BorderPane qLeftBP = new BorderPane(); //borderpane to house score panes
		qLeftBP.setTop(scoreTitleHBox); //add relevant score panes to bp
		qLeftBP.setCenter(qCheckPane);
		qLeftBP.setBottom(scoreStartVBox);
		qLeftBP.setPrefWidth(screenW * 0.15); //make it big

		BorderPane qCenterBP = new BorderPane(); //bp for actual questions
		qCenterBP.setTop(titleBox); //add title to the top of bp
		qCenterBP.setPadding(new Insets(40, 40, 40, 40));

		fillGPane(qCheckPane); //fill gpane initally with list of questions

		startQ.setOnAction(e -> {
			qNum = 0; //reset values incase restart
			score = 0;
			startQ.setText("Start Quiz!"); //set text to start quiz
			quizInitSetup(startQ, nextQ, submitAnswer, scoreTitle, qCheckPane, scoreText); //call method to setup initial states of buttons
			fillGPane(qCheckPane); //fill gpane if emptied on restart
			rngNumbers.clear(); //clear stored question order
			rngNumbers = rngRunner(rngNumbers, NUM_OF_QUESTIONS, 1); //generate new question order
			startQ.setDisable(true); //disable start button
			nextQ.fire(); //launch first question
		});

		nextQ.setOnAction(e -> { //everytime new question is requested
			if (qNum != NUM_OF_QUESTIONS) { //if not done questions
				qNum++; //increment question number whne new question is loaded
				System.out.print(qNum + " " + rngNumbers.get(qNum - 1)); //DEBUG PURPOSES ONLY
				if (qNum == NUM_OF_QUESTIONS) { //if last question. change next q button to load final score 
					nextQ.setText("End Quiz");
				}
				if (qNum <= NUM_OF_QUESTIONS) { //if not last q
					qTitle.setText("QUESTION " + qNum); //set title to question number
					try {
						qCenterBP.setCenter(QuizQ.getMCQ(qNum, NUM_OF_QUESTIONS, rngNumbers, submitAnswer)); //try to fill bp with pane from quiz class
					} catch (IOException e1) {
						ErrorDialogBox.displayError("Question Display Error", //if something doesnt work, throw error and show error message 
								"Something went wrong in displaying the question, Sorry");
					}
					ImageView checkIcon = new ImageView(); //make imagview for the progress icons
					checkIcon.setFitHeight(15); //make them small 
					checkIcon.setFitWidth(15);
					submitAnswer.setDisable(true); //setup button states
					nextQ.setDisable(true); 
					submitAnswer.setVisible(true);
					submitAnswer.setOnAction(f -> { //when user asks to check answer
						boolean correct = QuizQ.isCorrect(); //call quiz class to see if correct answer was selected
						submitAnswer.setDisable(true); //button state change
						if (correct) { //if user was correct
							score++; //increment score
							checkIcon.setImage(new Image("QCorrecticonSmall.png")); //progress icon is check mark
							qCheckPane.add(checkIcon, 1, qNum - 1); //add icon to grid

						} else {
							checkIcon.setImage(new Image("QWrongiconSmall.png")); ///if user was wrong, add x mark icon
							qCheckPane.add(checkIcon, 1, qNum - 1); //add to grid
						}
						qCenterBP.setCenter(QuizQ.getAnswerText()); //get post submit response layout and display it
						int scorePercent = (int) Math.round(((score * 100) / (qNum))); //calculate percent score (rounded)
						scoreText.setText("Current Score: " + score + "/" + (qNum) + " \nMark: " + scorePercent + "%"); //show user statement in progress pane

						nextQ.setDisable(false); //button states
					});
				}
			} else { //if done questions, 
				QuizFin.scoreWindow(score, NUM_OF_QUESTIONS); // call class to show final score window and video
				qNum = 0; //reset data
				score = 0;
				submitAnswer.setVisible(false); //reset button stated
				startQ.setDisable(false);
				startQ.setText("Restart Quiz"); //make label restart rather than start
				nextQ.setVisible(false); //reset button states
				nextQ.setDisable(true);
			}

		});

		quizTabBP.setLeft(qLeftBP); //add layouts to main tab layout
		quizTabBP.setCenter(qCenterBP);
		quizTab.setContent(quizTabBP); //add main tab layout to tab
	}

	public static void quizInitSetup(Button startQ, Button nextQ, Button submitAnswer, Label scoreTitle,
			GridPane qCheckPane, Label scoreText) {
		qNum = 0; //set question num to 0 for starting or restarting
		score = 0; //set score to 0
		startQ.setDisable(true); //make start button disabled
		nextQ.setVisible(true); //make nextq button visible
		nextQ.setDisable(false); //make next q button enabled
		nextQ.setText("Next Question"); //reset the text from end quiz prompt
		submitAnswer.setDisable(true); //make answer submission impossible if no quiz started
		submitAnswer.setVisible(false); //make it invisible as an option
		scoreTitle.setText("SCORE: "); //set the title to score
		scoreText.setText("You score will be displayed here");
		qCheckPane.getChildren().clear(); //clear the gridpane or previous feedback
	}

	public static ArrayList<Integer> rngRunner(ArrayList<Integer> rngNumbers, int numToGen, int adder) {
		Random randomGenerator = new Random(); //generate random ints, no repeats
		while (rngNumbers.size() < numToGen) { //generate question number of numbers
			int random = randomGenerator.nextInt(numToGen) + adder; //add to random int
			if (!rngNumbers.contains(random)) { //check if arraylist already contains number, gen again
				rngNumbers.add(random); //else add to arraylist
			}
		}
		System.out.println(rngNumbers);
		return rngNumbers; //return the arraylist
	}
	

	public static void fillGPane(GridPane qCheckPane) {
		for (int i = 0; i < NUM_OF_QUESTIONS; i++) { //fill the gridpane with a list of questions to show progress
			Label questionCheck = new Label();
			questionCheck.setFont(new Font("Gotham", 14)); //add questions till it reaches the constant
			questionCheck.setText("Question " + (i + 1) + ": ");
			qCheckPane.add(questionCheck, 0, i);
		}
	}

	public static String[] getPlanetNames() throws IOException {
		String[] planetNames = new String[NUM_OF_PLANETS + 1]; //Make a string array for planets
		BufferedReader reader = new BufferedReader(new FileReader("GetPlanetNames.txt")); //make reader to read from txt file
		for (int i = 0; i < NUM_OF_PLANETS; i++) {
			planetNames[i] = reader.readLine(); //fill array from txt file
		}
		reader.close(); //close reader
		return planetNames; //return the string array
	}

	public static void setMainMenuTab() throws IOException { //method to setup main menu tab
		BorderPane mainBorderPane = new BorderPane(); //main bp for tab
		mainBorderPane.setPadding(new Insets(0, 0, 0, 0)); //edge to edge display yo

		Image img = new Image("MainMenuIMG.png"); //get image to display

		ImageView mmIMG = new ImageView(img); //add image to imagview
		mmIMG.setPreserveRatio(true);
		mmIMG.setFitWidth(1890); //set a width, let preserve ratio figure out proportional height

		ScrollPane mainScrollPane = new ScrollPane(); //make scrollpane 
		mainScrollPane.setContent(mmIMG); //add long ass image to it
		mainScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); //no horizontal scrolling
		mainScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); //dont need it but hey, might as well
		mainScrollPane.setStyle("-fx-background-color: black"); //just in case

		Button toLesson = new Button("Go to Lessons!"); //add button to go to lesson
		toLesson.setPrefHeight(30);
		toLesson.setPrefWidth(150);

		Button toQuiz = new Button("Go to Quiz"); //add button to go to quiz
		toQuiz.setPrefHeight(30);
		toQuiz.setPrefWidth(150);

		HBox lessonQuizButtonHBox = new HBox(screenW * 0.1); //add both buttons to hbox layout
		lessonQuizButtonHBox.setPadding(new Insets(10, 10, 10, 10)); //space it out
		lessonQuizButtonHBox.getChildren().addAll(toLesson, toQuiz);  
		lessonQuizButtonHBox.setAlignment(Pos.CENTER); //center

		mainBorderPane.setBottom(lessonQuizButtonHBox); //add layout to main bp

		toLesson.setOnAction(e -> {
			navTabs.getSelectionModel().select(lessonsTab); ///open lesson tab when user clicks to to lesson button
		});

		toQuiz.setOnAction(e -> { 
			boolean answer = YesNoDialogBox.displayYNBox("Tab changing", //ask user if they dont want to do lesson first
					"It's recommended you review the lessons before doing the test, What would you like to do",
					"I want to take the quiz", "Take me to the lessons");
			if (answer) { 
				navTabs.getSelectionModel().select(lessonsTab); //if they say yes to lesson, take them to lessons
			} else { 
				navTabs.getSelectionModel().select(quizTab); //if they say no, go to quiz tab
			}
		});

		mainBorderPane.setCenter(mainScrollPane); //add scrollpane with image to center

		mainMenuTab.setContent(mainBorderPane); //add bp to tab
	}

	public static void setLessonTab() throws IOException {
		BorderPane lessonBorderPane = new BorderPane(); //Make a borderpane to house everything in the whole tab
		BorderPane lessonLeftBorderPane = new BorderPane(); //Make a border pane for the browsing contents
		lessonLeftBorderPane.setPadding(new Insets(0, 10, 0, 10)); //Add a padding so its spaced out a bit

		String[] lessonTitles = new String[NUM_OF_LESSONS]; //Make a string for the lessons that exist
		lessonTitles = lessonTitleReader("LessonTitles.txt"); // Fill string string from method

		ListView<String> listView = new ListView<>(); //Make a listView browser
		listView.getItems().addAll(lessonTitles); //Add titles to list view
		listView.getSelectionModel().select(0); //set default selection to first lesson
		listView.setPrefWidth(screenW * 0.15); //Set a preffered width

		ImageView planetIcon = new ImageView(); //Make a imageview to contain the planet icon
		planetIcon.setImage(getPlanetIcon("PlanetWindow")); //Get the default icon for no selected lesson by calling the get icon method
		planetIcon.setFitWidth(screenW * 0.15); //Make it fit the square in the selection BP bottom
		planetIcon.setFitHeight(screenW * 0.15);
		planetIcon.isPreserveRatio(); //Make it look good
		planetIcon.isSmooth(); //make it look good

		Button listViewSelectButton = new Button("Select Lesson"); //Make a button to select from listview
		listViewSelectButton.setPrefWidth(listView.getPrefWidth()); //Make it fit the space
		listViewSelectButton.setPrefHeight(40);

		Button goToQuizButton = new Button("Go To Quiz"); //Make a button to go to quiz
		goToQuizButton.setPrefWidth(listView.getPrefWidth()); //make it fit space

		VBox listViewSelect = new VBox(10); //Make a VBox to hold the icon and the select buttom
		listViewSelect.setAlignment(Pos.CENTER); //Make it all centered
		listViewSelect.setPadding(new Insets(10, 0, 10, 0)); // Make sure its not all cramped
		listViewSelect.getChildren().addAll(listViewSelectButton, planetIcon, goToQuizButton); //Add the icon and button to the VBox

		TextField lessonSearchBar = new TextField(); //Make a textfield for the lesson search
		lessonSearchBar.setPromptText("Search for a Lesson"); //Set a greyed our prompt text that tells user what to do

		ImageView lessonDiagram = new ImageView(); //Make an imageview for the lesson diagram
		lessonDiagram.isPreserveRatio(); //Make it look good
		lessonDiagram.isSmooth();

		ScrollPane lessonCenterText = new ScrollPane(); //Make a scrollable container for the main lesson
		lessonCenterText.setFitToWidth(true); //Make it fit available space
		lessonCenterText.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); //I dont want to make user scroll sideways, I make label fit so dont clutter
		lessonCenterText.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Lesson will probably be longer than available space, make it scrollable
		lessonCenterText.setPadding(new Insets(40, 0, 0, screenW * 0.02)); // dont make it crowded

		Label title = new Label("Select a Lesson"); //Make the label for the title, set a default for when no lesson selected
		title.setFont(new Font("Gotham Bold", 75)); //Make it big and pretty

		HBox lessonTextTitleHBox = new HBox(); //HBox for lesson title 		
		lessonTextTitleHBox.getChildren().addAll(title); //Add title to the HBox
		lessonTextTitleHBox.setAlignment(Pos.CENTER); //Make it centered
		lessonTextTitleHBox.setPadding(new Insets(10, 10, 10, 10)); //make it not ugly

		Button nextLesson = new Button("Next Lesson"); // Add buttons for next and previous lessons
		Button previousLesson = new Button("Previous Lesson");

		HBox lessonButtonsHBox = new HBox(screenW * 0.06); //Make HBox for containing the prev/next buttons
		lessonButtonsHBox.setPadding(new Insets(0, 0, 0, 0)); //Make buttons go to the edges
		lessonButtonsHBox.setAlignment(Pos.CENTER); //Make it all centered
		lessonButtonsHBox.getChildren().addAll(previousLesson, nextLesson); //Add all the buttons to the HBox

		BorderPane lessonTextBorderPane = new BorderPane(); //Make a borderpane to contain the actual lesson
		lessonTextBorderPane.setCenter(lessonCenterText); //to the middle add the scrollpane containing diagram and the lesson
		lessonTextBorderPane.setTop(lessonTextTitleHBox); //Add the title to the top

		VBox searchVBox = new VBox(10); //A new Vbox to contain prev, next buttons and search bar
		searchVBox.setPadding(new Insets(10, 0, 10, 0));
		searchVBox.getChildren().addAll(lessonButtonsHBox, lessonSearchBar); //Add all the stuff to vbox

		goToQuizButton.setOnAction(e -> navTabs.getSelectionModel().select(quizTab)); //When go to quiz button clicked, open quiz Tab

		listViewSelectButton.setOnAction(e -> { //On a selection, add this listener
			Label lessontext = new Label(""); //Make a blank label
			boolean loadContinue = false; //Make a variable to see if lesson should be actually loaded or to throw an error
			try { //Tru to get the lesson text from the method, pass contents of listview and searchbar
				lessontext = lessonBrowse(lessonSearchBar, listView);
				loadContinue = true; //if can be loaded, set this to true
			} catch (IOException e1) {
				e1.printStackTrace();
				ErrorDialogBox.displayError("Error", "The lesson you were looking for does not exist"); //diaply error since lesson couldnt be found
			}
			if (loadContinue) { //if lesson was found, continue with rest of loading
				try {
					title.setText(listView.getSelectionModel().getSelectedItem().toUpperCase()); //Make the title the thing on selected lesson
					planetIcon.setImage(getPlanetIcon(listView.getSelectionModel().getSelectedItem())); //get icon for selected lesson

					Image diagram = getLessonDiagram(listView.getSelectionModel().getSelectedItem()); //get diagram for selected lesson
					lessonDiagram.setImage(diagram); //add diagram to imageview
					lessonDiagram.setFitWidth(diagram.getWidth()); //make it original image size
					lessonDiagram.setFitHeight(diagram.getHeight());

					VBox lessonTextVBox = new VBox(40); //make a VBox for the actual lesson 
					lessonTextVBox.setPadding(new Insets(10, 40, 40, 40));
					lessonTextVBox.setAlignment(Pos.TOP_CENTER); //make it centered
					lessonTextVBox.getChildren().addAll(lessonDiagram, lessontext); //add the diagram and the lesson text to the VBox
					lessonCenterText.setContent(lessonTextVBox); //add vbox to lesson main BP
					lessonCenterText.setVvalue(0); //make it scroll to top for switching lessons
				} catch (Exception e2) {
					ErrorDialogBox.displayError("Error", "The lesson you are trying to access isn't found, Sorry"); //error if lesson wasnt found
				}
			}
		});

		nextLesson.setOnAction(e -> {
			if (listView.getSelectionModel().getSelectedIndex() < NUM_OF_LESSONS - 1) { //if your on the last lesson, give and error, othwerwise move to next
				listView.getSelectionModel().select(listView.getSelectionModel().getSelectedIndex() + 1); //get the current selection, select next selection
				listViewSelectButton.fire(); //fire the select button automatically to run that listener
			} else {
				ErrorDialogBox.displayError("Next Lesson", "This is the last lesson, can't go further forward"); //throw error if last lesson
				listView.getSelectionModel().select(12); //reset selection to last one
			}
		});

		previousLesson.setOnAction(e -> {
			if (listView.getSelectionModel().getSelectedIndex() > 0) { //if first, give error, else move to previous lesson
				listView.getSelectionModel().select(listView.getSelectionModel().getSelectedIndex() - 1); // get current, select current minus 1
				listViewSelectButton.fire(); //fire selection button to run that listener
			} else {
				ErrorDialogBox.displayError("Previous Lesson", "This is the first lesson, can't go further back"); //if first lesson, tell user
				listView.getSelectionModel().select(0); //reset to 1st lesson
			}
		});

		lessonSearchBar.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) { //If user uses return key in search bar, get text and fire selection button
				if (ke.getCode().equals(KeyCode.ENTER)) {
					listView.getSelectionModel().select(lessonSearchBar.getText());
					listViewSelectButton.fire();
				}
			}
		});

		lessonLeftBorderPane.setTop(searchVBox); //For selection bp, add button and searchbar vbox
		lessonLeftBorderPane.setCenter(listView); //ass the listview
		lessonLeftBorderPane.setBottom(listViewSelect); //add the button and icon

		lessonBorderPane.setCenter(lessonTextBorderPane); //to the main bp, add the lesson text bp
		lessonBorderPane.setLeft(lessonLeftBorderPane);//add the search bp

		lessonsTab.setContent(lessonBorderPane); // add the main bp to the lesson tab
	}
	

	public static Image getLessonDiagram(String lessonTitle) {
		try {
			Image diagram = new Image(lessonTitle + "Diagram.jpg"); //Try to get diagram for selected lesson if possible
			return diagram; //if found, return it
		} catch (Exception e1) {
			e1.printStackTrace();
			ErrorDialogBox.displayError("Error Getting Diagram",
					"We couldn't find the relevant diagram for this lesson, sorry"); //If the diagram cant be found, tell user in error box
			return new Image("errorDiagram.jpg"); //return a template image
		}

	}

	public static Image getPlanetIcon(String planetName) {
		Image planetIcon = new Image(planetName + "Icon.png"); //Get image file for selected planet
		planetIcon.isPreserveRatio(); //Make it look good
		planetIcon.isSmooth();
		return planetIcon; //return the image
	}

	public static Label lessonBrowse(TextField lessonSearchBar, ListView<String> listView) throws IOException {
		//Take input from search bar and listview to search for the topic they selected
		String lessonSelection; //make a string for the lesson they search for
		if (lessonSearchBar.getText().isEmpty()) { //if the searchbar is blank, call the lessontext method with lesson selected in listview
			lessonSelection = listView.getSelectionModel().getSelectedItem();
		} else {
			lessonSelection = lessonSearchBar.getText(); //if its not empty, search for lesson from searchbar
			listView.getSelectionModel().select(lessonSearchBar.getText());
			lessonSearchBar.setText(""); //set it to blank so it doesnt get in the way anymore
		}
		Label lessontext = new Label(); //make lesson text a new label
		lessontext = lessonFileReader(lessonSelection); //get lesson text from lesson reader method
		return lessontext; //return this label

	}

	public static Label lessonFileReader(String lessonSelection) throws IOException {
		Label lessontext = new Label(); //Make a label to contain the actual lesson text
		BufferedReader reader = new BufferedReader(new FileReader(lessonSelection + ".txt")); //make a reader to read the file for accessed lesson
		try { //Try incase file not found
			lessontext.setText(reader.readLine()); //Lesson is on one line, add it all to the label
			reader.close(); //close reader

			lessontext.setMaxWidth(screenW * 0.7); //Set properties of the label 
			lessontext.setFont(new Font("Gotham", 22)); //Set font of label
			lessontext.setWrapText(true); //Make label wrap into new lines if too long
			lessontext.setLineSpacing(10);
			lessontext.setAlignment(Pos.CENTER); //Make it in the center
			lessontext.setTextAlignment(TextAlignment.CENTER); //Make text justification centered too
			return (lessontext); //return this label
		} catch (Exception e1) {
			ErrorDialogBox.displayError("Search Error", "We couldn't find the lesson you searched for"); //if lesson wasn't found, display an error
			return new Label(""); //if not found, return a blank label
		}
	}

	public static String[] lessonTitleReader(String fileName) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("LessonTitles.txt")); //Read the lesson titles text file
		String[] lessonTitles = new String[NUM_OF_LESSONS]; //Make new array to contain all lesson titles

		for (int i = 0; i < NUM_OF_LESSONS; i++) { //fill array with contents of text file
			lessonTitles[i] = (reader.readLine()); // use constants if # of lessons changes
		}
		reader.close(); //close reader
		return lessonTitles; //return the array of titles
	}

	public static void setTabs() {
		navTabs.getTabs().addAll(mainMenuTab, lessonsTab, quizTab); //Add tabs to tabpane
		navTabs.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE); //Dont let user close the tabs, theyre important
	}

	public static void setStage(Stage window) {
		// Setup properties of window including icons, titles and size (depends on monitor resolution)
		window.getIcons().add(new Image("PlanetWindowIcon.png")); //set window icon

		window.setTitle("Solar System JavaFX Application by Kshitij Shah"); //Set window title
		window.setWidth(screenW); //set window size
		window.setHeight(screenH);
		window.setMinHeight(screenH); //dont let user make window smaller 
		window.setMinWidth(screenW);
		window.setResizable(true); //they can make it bigger though

		// Show window
		window.show();
		window.setOnCloseRequest(e -> {
			e.consume(); //if they try to close window, kill the request, call the close program method
			closeProgram();
		});

	}

	public static void closeProgram() {
		//If the user decides to close the program, call the boolean box class to get response if they really want to close the program
		boolean answer = YesNoDialogBox.displayYNBox("EXIT?", "PLEASE DON'T LEAVE I'LL MISS YOU", "STAY", "LEAVE");
		if (answer) { //IF they actually want to close, close the main window, else do nothing
			window.close();
		}
	}

	public static double getScreenWidth() {
		Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds(); //Get and return the width of the primary monitor the user is using
		double screenW = visualBounds.getWidth(); //get width of rectangke (the screen)
		return screenW;
	}

	public static double getScreenHeight() {
		Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds(); //Get and return the height of the primary monitor of user
		double screenH = visualBounds.getHeight();	//get height of rectangle
		return screenH;
	}

	public static void main(String[] args) {
		launch(args); //lauch the application from the main stage
	}
}