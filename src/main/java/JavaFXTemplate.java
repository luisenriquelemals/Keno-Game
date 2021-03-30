import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Optional;
import java.util.Random;

import java.util.HashMap;
import java.util.Vector;

public class JavaFXTemplate extends Application {

		/*stage*/
		private Stage mainWindow;

		/*Buttons*/
		private Button bRulesBack, bPrizesBack, bNewLookBack;
		private Button b2Play, quickPick, nextDollar, nextDraws,
				      nextSpots, reselectNumbers, startDrawing, nextDrawing,
						theme1, theme2, theme3;

		private BorderPane root, leftPaneQuestions, prizePane, rulesPane;
		private VBox newLookpane, questionsBox ;
		private Text labelQuick;



		/*Menu Bar*/
		private MenuBar menu;
		private Menu varMenu;
		private MenuItem iRules;
		private MenuItem iPrizes;
		private MenuItem iNewLook;
		private MenuItem iExit;

		/*HasMap*/
		HashMap<String, Scene> sceneMap;

		/*CheckBox*/
		private RadioButton radio1, radio2, radio3, radio4, radio5;
		private RadioButton radioDraw1, radioDraw2, radioDraw3, radioDraw4;
		private RadioButton radioSpot1, radioSpot4, radioSpot8, radioSpot10;

	    /*boolean*/
		private boolean isInPlayScene = false;

		/*options of questions*/
		private int dollars, draws, spots;
		private int increment;

		/*TextArea*/
		private TextArea displayInformation;

		/*Grid*/
		private GridPane grid;



		/*Prizes*/
		private Vector<Integer> tenSpot, eightSpot, fourSpot;
		private TextField showPrize;
		private int accumulatePrize;

		/*random Numbers*/
		private Vector<Node> randomTwenty;
		private Vector<Node> randomSpots;
		private StartGame newDraw;


		/*Handler for the Buttons*/
	EventHandler<ActionEvent> myHandler = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent e) {
			quickPick.setDisable(true);
			if (increment < spots ) {
				Button b1 = (Button)e.getSource();
				int number = Integer.parseInt(b1.getText());
				int row = grid.getRowIndex(b1);
				int col = grid.getColumnIndex(b1);
				Node newNode = new Node(number,row, col);
				randomSpots.add(newNode); // add numbers to list
				updateTextArea(b1.getText(), true);
				b1.setDisable(true);
				increment++;
			}

			if (increment  == spots) {
				lockGrid();
				printMessageStartGame();
				reselectNumbers.setDisable(false);
				nextDrawing.setDisable(true);
				startDrawing.setDisable(false);
			}

		}
	};


	//private int row,col;
	/*Event Handler for Button quickPick*/
	EventHandler<ActionEvent> quickSelection = new EventHandler<ActionEvent>() {

		public void handle(ActionEvent e) {

			quickPick.setDisable(true);
			reselectNumbers.setDisable(false);
			startDrawing.setDisable(true);

			/*get random numbers spots 1,4,8,10*/
			randomSpots = newDraw.getRandomNumbers(spots);

			int time = 500;

			int index = 0;
			while (index < spots) {

				int row = randomSpots.get(index).getRow();
				int col = randomSpots.get(index).getColumn();
				Button butt = (Button) grid.getChildren().get(row*10+col);
				PauseTransition newPause = new PauseTransition(Duration.millis(time));
					newPause.setOnFinished(m->{
						butt.setStyle(" -fx-background-radius: 10em;" +
								"-fx-min-width: 40px; " +
								"-fx-min-height: 40px; " +
								"-fx-max-width: 40px; " +
								"-fx-max-height: 40px;"+
								"-fx-background-color: #FF0000;");
					});
				updateTextArea(butt.getText(), true);
				index++;
				newPause.play();
				time += 500;//
			}// end of while

			/*other operations*/
			lockGrid();
			printMessageStartGame();


			PauseTransition newPause = new PauseTransition(Duration.millis(time));
			newPause.setOnFinished(x->{
				startDrawing.setDisable(false);
			});
			newPause.play();


		}

	};


	/*Event Handler for start Drawing*/
	EventHandler<ActionEvent> startDraw = new EventHandler<ActionEvent>() {
		Vector<Node> matchNumbers;


		public void handle(ActionEvent e) {
			quickPick.setDisable(true);
			reselectNumbers.setDisable(true);
			nextDrawing.setDisable(true);
			int time = 500;

			if (draws > 0) {

				startDrawing.setDisable(true);
				//nextDrawing.setDisable(true);
				/*juts to set the color where they were before*/
				unlockGrid();
				lockGrid();

				/*get random twenty numbers*/
				randomTwenty = newDraw.getRandomNumbers(20);
				int i = 0;


				while (i < 20) {
					PauseTransition newPause = new PauseTransition(Duration.millis(time));
					Node number = randomTwenty.get(i);
					int row  = number.getRow();
					int col = number.getColumn();
					Button b1 = (Button) grid.getChildren().get(row*10+col);

					newPause.setOnFinished(m->{
						b1.setStyle(" -fx-background-radius: 10em;" +
								"-fx-min-width: 40px; " +
								"-fx-min-height: 40px; " +
								"-fx-max-width: 40px; " +
								"-fx-max-height: 40px;"+
								"-fx-background-color: #AFD275;");
					});

					newPause.play();

					time += 500;//
					i++;
				}// end of while


				matchNumbers = newDraw.getMatchNumbers(randomTwenty, randomSpots);

				/*check if the vector is not empty */
				if (!matchNumbers.isEmpty()) {
					i = 0;
					int size = matchNumbers.size();
					while (i < size) {
						Node number = matchNumbers.get(i);
						int row  = number.getRow();
						int col = number.getColumn();
						Button b1 = (Button) grid.getChildren().get(row*10+col);
						PauseTransition newPause = new PauseTransition(Duration.millis(time));

						newPause.setOnFinished(m->{
							b1.setStyle(" -fx-background-radius: 10em;" +
									"-fx-min-width: 40px; " +
									"-fx-min-height: 40px; " +
									"-fx-max-width: 40px; " +
									"-fx-max-height: 40px;"+
									"-fx-background-color: #DF44A9;");

						});

						newPause.play();
						i++;
						time += 500;
					}

					/*show prizes*/
					PauseTransition prizePause = new PauseTransition(Duration.millis(time));
					prizePause.setOnFinished(x->{
						setPrizeMoney(matchNumbers.size());
					});
					prizePause.play();


				}


				draws--;

				PauseTransition nextDrawPause = new PauseTransition(Duration.millis(time));
				nextDrawPause.setOnFinished(x->{
					nextDrawing.setDisable(false);
				});
				nextDrawPause.play();

			}

			/*end of the game*/
			if(draws == 0) {
				/*no more draws*/
				time += 500;
				PauseTransition nextFinalPause = new PauseTransition(Duration.millis(time));
				nextFinalPause.setOnFinished(x->{
					//finalMessage = true;
					nextDrawing.setDisable(true);
					finalMessageGame();
				});
				nextFinalPause.play();



			}// end of main if



		}// end of handle

	};


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		mainWindow = primaryStage;
		mainWindow.setTitle("Keno Game");

		sceneMap = new HashMap<String,Scene>();
		randomSpots = new Vector<Node>();
		newDraw = new StartGame();


		/*Buttons*/
		bRulesBack = new Button("go Back");
		bPrizesBack = new Button("go Back");
		bNewLookBack = new Button("go Back");
		b2Play = new Button("Play");
		nextDollar = new Button("Next");
		nextDraws = new Button("Next");
		nextSpots = new Button("Next");
		reselectNumbers = new Button("Reselect Numbers");
		startDrawing = new Button("Start Game");
		nextDrawing = new Button("Next Drawing");
		theme1 = new Button("Theme 1");
		theme2 = new Button("Theme 2");
		theme3 = new Button("Theme 3");

		/*Menu Items*/
		menu = new MenuBar();
		menu.setPrefSize(50,50);
		menu.setStyle(getBackgroundEffect(1));
		varMenu = new Menu("Menu");
		iRules = new MenuItem("Rules"); //menu items go inside a menu
		iPrizes = new MenuItem("Prizes"); //menu items go inside a menu
		iExit = new MenuItem("Exit Game"); //menu items go inside a menu
		iNewLook = new MenuItem("New Look");
		varMenu.getItems().addAll(iRules, iPrizes, iExit);
		menu.getMenus().add(varMenu);
		BorderPane welcomePane = welcomeScene();

		/*check boxes*/
		/*for question 1*/
		radio1 = new RadioButton("$1");
		radio2 = new RadioButton("$2");
		radio3 = new RadioButton("$3");
		radio4 = new RadioButton("$5");
		radio5 = new RadioButton("$10");

		/*for question 2*/
		radioDraw1 = new RadioButton("1");
		radioDraw2 = new RadioButton("2");
		radioDraw3 = new RadioButton("3");
		radioDraw4 = new RadioButton("4");

		/*for question 3*/
		radioSpot1 = new RadioButton("1");
		radioSpot4 = new RadioButton("4");
		radioSpot8 = new RadioButton("8");
		radioSpot10 = new RadioButton("10");

		/*quick button*/
		quickPick = new Button("Go");

		/*Grid of Buttons*/
		grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		addGrid(); //populate the GridPane with buttons
		grid.setHgap(20);
		grid.setVgap(20);

		/*Text Area*/
		displayInformation = new TextArea();

		//sceneMap.put("welcome", welcomeScene());
		sceneMap.put("rules", rulesScene());
		sceneMap.put("prize", prizesScene());
		sceneMap.put("newLook", newLookScene());

		/*Go back to Welcome Scene*/
		EventHandler<ActionEvent> myHandlerBack = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if(isInPlayScene) {
					mainWindow.setScene(sceneMap.get("play"));
				}else {
					Scene welcoScene = new Scene(new VBox(20,menu, welcomePane), 850,750);
					mainWindow.setScene(welcoScene);
				}

			}
		};

		bRulesBack.setOnAction(myHandlerBack);
		bPrizesBack.setOnAction(myHandlerBack);
		bNewLookBack.setOnAction(myHandlerBack);

		/*go to rules scene*/
		iRules.setOnAction(e->{
			mainWindow.setScene(sceneMap.get("rules"));

		});

		/*go to prizes scene*/
		iPrizes.setOnAction(e->{
			mainWindow.setScene(sceneMap.get("prize"));

		});

		iNewLook.setOnAction(e-> {

			mainWindow.setScene(sceneMap.get("newLook"));
		});

		/*exit the program*/
		iExit.setOnAction(e->{
			Platform.exit();
			System.exit(0);

		});

		/*go to play scene*/
		b2Play.setOnAction(e-> {
			isInPlayScene = true;
			sceneMap.put("play", playScene());
			mainWindow.setScene(sceneMap.get("play"));
		});

		/*go next In questions of pick money, draws, spots*/
		nextDollar.setOnAction(e->{
			dollars = getSelectedOptionMoney();
			if (dollars == 0) {
				printMessageWarning();
			}


		});

		nextDraws.setOnAction(e->{
			draws = getSelectedOptionDraws();
			if (draws == 0) {
				printMessageWarning();
			}

		});

		nextSpots.setOnAction(e->{
			spots = getSelectedOptionSpots();
			if (spots == 0) {
				printMessageWarning();
			}else {
				printMessageSelectNumbers();
				updateTextArea("",false);
				unlockGrid();
			}

		});

		reselectNumbers.setOnAction( e-> {
			quickPick.setDisable(false);
			startDrawing.setDisable(true);
			reselectNumbers.setDisable(true);

			unlockGrid();
			increment = 0;
			displayInformation.clear();// remove all the information in text Area
			randomSpots.clear(); // delete all elements of the vector
			updateTextArea("",false);

		});


		quickPick.setOnAction(quickSelection);

		startDrawing.setOnAction( startDraw );

		nextDrawing.setOnAction(startDraw);

		theme1.setOnAction(e->{
			theme1.setStyle(getBackgroundEffect(2));
			theme2.setStyle(getBackgroundEffect(2));
			theme3.setStyle(getBackgroundEffect(2));
			menu.setStyle(getBackgroundEffect(2));
			grid.setStyle(getBackgroundEffect(2)+ "-fx-font: 15px Verdana" );
			root.setStyle(getBackgroundEffect(2));
			leftPaneQuestions.setStyle(getBackgroundEffect(2));
			displayInformation.setFont(Font.font(15));
			newLookpane.setStyle(getBackgroundEffect(2));
			bNewLookBack.setStyle(getBackgroundEffect(2));
			questionsBox.setStyle(getBackgroundEffect(2));
			prizePane.setStyle(getBackgroundEffect(2));
			bPrizesBack.setStyle(getBackgroundEffect(2));
			bRulesBack.setStyle(getBackgroundEffect(2));
			rulesPane.setStyle(getBackgroundEffect(2));

		});

		theme2.setOnAction(e->{
			theme1.setStyle(getBackgroundEffect(3));
			theme2.setStyle(getBackgroundEffect(3));
			theme3.setStyle(getBackgroundEffect(3));
			menu.setStyle(getBackgroundEffect(3));
			grid.setStyle(getBackgroundEffect(3)+ "-fx-font: 15px Verdana" );
			root.setStyle(getBackgroundEffect(3));
			leftPaneQuestions.setStyle(getBackgroundEffect(3));
			displayInformation.setFont(Font.font(15));
			newLookpane.setStyle(getBackgroundEffect(3));
			bNewLookBack.setStyle(getBackgroundEffect(3));
			questionsBox.setStyle(getBackgroundEffect(3));
			prizePane.setStyle(getBackgroundEffect(3));
			bPrizesBack.setStyle(getBackgroundEffect(3));
			bRulesBack.setStyle(getBackgroundEffect(3));
			rulesPane.setStyle(getBackgroundEffect(3));

		});

		theme3.setOnAction(e->{
			theme1.setStyle(getBackgroundEffect(4));
			theme2.setStyle(getBackgroundEffect(4));
			theme3.setStyle(getBackgroundEffect(4));
			menu.setStyle(getBackgroundEffect(4));
			grid.setStyle(getBackgroundEffect(4)+ "-fx-font: 15px Verdana" );
			root.setStyle(getBackgroundEffect(4));
			leftPaneQuestions.setStyle(getBackgroundEffect(4));
			displayInformation.setFont(Font.font(15));
			newLookpane.setStyle(getBackgroundEffect(4));
			bNewLookBack.setStyle(getBackgroundEffect(4));
			questionsBox.setStyle(getBackgroundEffect(4));
			prizePane.setStyle(getBackgroundEffect(4));
			bPrizesBack.setStyle(getBackgroundEffect(4));
			bRulesBack.setStyle(getBackgroundEffect(4));
			rulesPane.setStyle(getBackgroundEffect(4));

		});

		/*welcome scene by default*/
		VBox mainBox = new VBox(30,menu, welcomePane);
		Scene scene = new Scene(mainBox, 850,750);
		mainWindow.setScene(scene); //set the scene in the stage
		mainWindow.setResizable(false);
		mainWindow.show(); //make visible to the user

	}// end of primary stage


	public BorderPane welcomeScene() {

		Text textWelcome = new Text("Welcome to Keno game! ☺ ");
		textWelcome.setFont(Font.font ("Verdana", 30));
		textWelcome.setFill(Color.RED);
		textWelcome.setStyle("-fx-font: 60px Tahoma;" +
				"    -fx-fill: linear-gradient(from 0% 0% to 50% 100%, repeat, aqua 0%, blue 50%);" +
				"    -fx-stroke: black;" +
				"    -fx-stroke-width: 2;");


		b2Play.setStyle(getBackgroundEffect(1));
		b2Play.setPrefSize(100,60);
		b2Play.setAlignment(Pos.CENTER);

		BorderPane pane =  new BorderPane();
		pane.setPrefSize(700,700);
		pane.setStyle(getBackgroundEffect(1));
		pane.setPadding(new Insets(30));
		BorderPane.setMargin(textWelcome,new Insets(50));
		BorderPane.setMargin(b2Play,new Insets(50));
		BorderPane.setAlignment(b2Play, Pos.BOTTOM_CENTER);
		BorderPane.setAlignment(textWelcome, Pos.CENTER);
		pane.setTop(null);
		pane.setCenter(textWelcome);
		pane.setBottom(b2Play);

		return pane;

	}// end of welcome sene

	public Scene rulesScene() {

		Text header = new Text ("Rules of the Game\n");
		Text text1 = new Text("Carolina Keno is a fast-paced lottery draw-style game " +
				"that's easy to play, with a chance to win great cash prizes every 4 minutes. " +
				"For each Keno drawing, 20 numbers out of 80 will be selected as winning numbers. " +
				"You can decide how many of these numbers (called Spots) and exactly which numbers " +
				"you will try to match. There are more than 300 drawings daily so you will never " +
				"need to wait long until the next Keno drawing.");

		//text1.setStyle(getBackgroundEffect(1));
		text1.setFont(Font.font("Verdana",38));
		text1.setTextAlignment(TextAlignment.JUSTIFY);
		//header.setStyle(getBackgroundEffect(1));
		header.setFill(Color.TOMATO);
		header.setFont(Font.font("Verdana",45));
		header.setTextAlignment(TextAlignment.CENTER);


		TextFlow textFlow = new TextFlow( header, text1);
		//textFlow.setTextAlignment(TextAlignment.JUSTIFY);
		bRulesBack.setStyle(getBackgroundEffect(1));

		BorderPane.setAlignment(bRulesBack, Pos.CENTER_LEFT);
		BorderPane.setAlignment(textFlow, Pos.CENTER);
		BorderPane.setMargin(textFlow, new Insets(10));

		rulesPane = new BorderPane();
		rulesPane.setStyle(getBackgroundEffect(1));
		rulesPane.setPrefSize(700,800);
		rulesPane.setPadding(new Insets(30));
		rulesPane.setTop(bRulesBack);
		rulesPane.setCenter(textFlow);


		VBox paneCenter = new VBox(20, rulesPane);

		return new Scene(paneCenter, 850, 750);
	}

	public Scene prizesScene ()  {
		Text spot1 = new Text ("1 Spot game ");
		Text content1 = new Text (" Match         Prize \n" +
									 "   1            $2   \n\n");

		Text spot4 = new Text (" 4 Spot game ");
		Text content4 = new Text (" Match         Prize \n" +
									 "   4            $75   \n"+
									 "   3            $5   \n"+
									 "   2            $1   \n");

		Text spot8 = new Text (" 8 Spot game ");
		Text content8 = new Text (" Match         Prize \n" +
									"   8          $10,000 \n"+
									"   7             $750 \n"+
									"   6              $50 \n"+
									"   5              $12 \n"+
									"   4               $2 \n\n");

		Text spot10 = new Text ("10 Spot game ");
		Text content10 = new Text (" Match         Prize \n" +
									"   10         $100,000 \n"+
									"    9           $4,200 \n"+
									"    8             $450 \n"+
									"    7              $40 \n"+
									"    6              $15 \n"+
									"    5               $2 \n"+
									"    0               $5 \n");

		spot1.setFont(Font.font("Verdana",30));
		spot4.setFont(Font.font("Verdana",30));
		spot8.setFont(Font.font("Verdana",30));
		spot10.setFont(Font.font("Verdana",30));
		spot1.setFill(Color.TOMATO);
		spot4.setFill(Color.TOMATO);
		spot8.setFill(Color.TOMATO);
		spot10.setFill(Color.TOMATO);

		content1.setFont(Font.font("Verdana",30));
		content4.setFont(Font.font("Verdana",30));
		content8.setFont(Font.font("Verdana",30));
		content10.setFont(Font.font("Verdana",30));

		bPrizesBack.setStyle(getBackgroundEffect(1));


		VBox textFlow1 = new VBox( 5, spot1, content1, spot8, content8);
		VBox textFlow2 =  new VBox( 0, spot4, content4, spot10, content10);
		textFlow1.setAlignment(Pos.TOP_CENTER);
		textFlow2.setAlignment(Pos.TOP_CENTER);


		prizePane = new BorderPane();
		prizePane.setStyle(getBackgroundEffect(1));
		prizePane.setPrefSize(700,800);
		prizePane.setTop(bPrizesBack);
		prizePane.setLeft(textFlow1);
		prizePane.setRight(textFlow2);
		BorderPane.setAlignment(textFlow1, Pos.TOP_CENTER);
		BorderPane.setMargin(textFlow1, new Insets(50));
		BorderPane.setAlignment(textFlow2, Pos.TOP_CENTER);
		BorderPane.setMargin(textFlow2, new Insets(50));
		prizePane.setPadding(new Insets(20));

		return new Scene(prizePane, 850, 750);

	}// end of prizesScene

	public Scene playScene () {

		varMenu.getItems().clear();
		menu.getMenus().clear();
		varMenu.getItems().addAll(iRules, iPrizes, iNewLook, iExit);
		menu.getMenus().add(varMenu);

		accumulatePrize = 0;

		/*creating the questions*/
		Text q1 = new Text("How much do you want to play per draw?");
		Text q2 = new Text("How many consecutive draws do you want to play ?");
		Text q3 = new Text("How many number (spots) do you want to play ?");

		q1.setFill(Color.RED);
		q1.setFont(Font.font("Verdana",20));
		q2.setFill(Color.RED);
		q2.setFont(Font.font("Verdana",20));
		q3.setFill(Color.RED);
		q3.setFont(Font.font("Verdana",20));


		/*use ToggleGroup to just choose one option*/
		ToggleGroup options1 = new ToggleGroup();
		radio1.setToggleGroup(options1);
		radio2.setToggleGroup(options1);
		radio3.setToggleGroup(options1);
		radio4.setToggleGroup(options1);
		radio5.setToggleGroup(options1);

		ToggleGroup options2 = new ToggleGroup();
		radioDraw1.setToggleGroup(options2);
		radioDraw2.setToggleGroup(options2);
		radioDraw3.setToggleGroup(options2);
		radioDraw4.setToggleGroup(options2);

		ToggleGroup options3 = new ToggleGroup();
		radioSpot1.setToggleGroup(options3);
		radioSpot4.setToggleGroup(options3);
		radioSpot8.setToggleGroup(options3);
		radioSpot10.setToggleGroup(options3);

		/*Putting the checkBoxes in a Horizontal Box*/
		HBox boxes1 = new HBox(50);
		boxes1.setPadding(new Insets(10,10,10,10));
		boxes1.getChildren().addAll(radio1, radio2, radio3, radio4, radio5);
		boxes1.setAlignment(Pos.CENTER);

		HBox boxes2 = new HBox(60);
		boxes2.setPadding(new Insets(10,10,10,10));
		boxes2.setAlignment(Pos.CENTER);
		boxes2.getChildren().addAll(radioDraw1, radioDraw2, radioDraw3, radioDraw4);

		HBox boxes3 = new HBox(60);
		boxes3.setPadding(new Insets(10,10,10,10));
		boxes3.setAlignment(Pos.CENTER);
		boxes3.getChildren().addAll(radioSpot1, radioSpot4, radioSpot8, radioSpot10);

		/*disable radioButtons of the last two questions*/
		/* radioButtons draws*/
		radioDraw1.setDisable(true);
		radioDraw2.setDisable(true);
		radioDraw3.setDisable(true);
		radioDraw4.setDisable(true);
		nextDraws.setDisable(true);
		/* radioButtons spots*/
		radioSpot1.setDisable(true);
		radioSpot4.setDisable(true);
		radioSpot8.setDisable(true);
		radioSpot10.setDisable(true);
		nextSpots.setDisable(true);

		/* layout for questions and boxes*/
		questionsBox = new VBox(10);
		questionsBox.setStyle(getBackgroundEffect(1));
		questionsBox.setPadding(new Insets(10,10,10,10));
		questionsBox.setAlignment(Pos.CENTER);
		questionsBox.getChildren().addAll(q1, boxes1, nextDollar, q2, boxes2, nextDraws, q3, boxes3, nextSpots);


		nextDollar.setFont(Font.font("Verdana",14));
		nextDollar.setPrefSize(60,30);
		nextDraws.setFont(Font.font("Verdana",14));
		nextDraws.setPrefSize(60,30);
		nextSpots.setFont(Font.font("Verdana",14));
		nextSpots.setPrefSize(60,30);

		/*quick label and button*/
		labelQuick = new Text("Random \nNumbers");
		labelQuick.setFill(Color.RED);
		labelQuick.setFont(Font.font("Verdana",20));
		VBox quickSelection = new VBox(10);
		quickSelection.setAlignment(Pos.BOTTOM_CENTER);
		quickSelection.setPadding(new Insets(10,10,10,10));
		quickSelection.getChildren().addAll(labelQuick, quickPick);
		quickPick.setDisable(true);

		/*Text Area*/
		Text info = new Text("Selected Options");
		info.setFill(Color.RED);
		info.setFont(Font.font("Verdana",20));
		displayInformation.setPrefSize(80,80);
		VBox textInfo = new VBox(5,info, displayInformation);
		displayInformation.setEditable(false);

		/*left border pane*/
		leftPaneQuestions = new BorderPane();
		leftPaneQuestions.setStyle(getBackgroundEffect(1));
		leftPaneQuestions.setPadding(new Insets(20));
		leftPaneQuestions.setLeft(questionsBox);
		leftPaneQuestions.setRight(quickSelection);
		leftPaneQuestions.setBottom(textInfo);

		/*Label Prize*/
		showPrize = new TextField("$ 0");
		showPrize.setMinWidth(10);
		showPrize.setMinHeight(10);
		showPrize.setEditable(false);

		/*right Borderpane*/
		HBox bottomPart = new HBox(50);
		bottomPart.setAlignment(Pos.CENTER_RIGHT);
		bottomPart.setPadding(new Insets(10,10,10,10));
		bottomPart.getChildren().addAll(showPrize,reselectNumbers, startDrawing, nextDrawing);
		reselectNumbers.setDisable(true);
		startDrawing.setDisable(true);
		nextDrawing.setDisable(true);

		/*main border pane*/
		root = new BorderPane();
		BorderPane.setAlignment(grid, Pos.CENTER);
		//root.setStyle("-fx-background-color: #557A95;");
		root.setStyle(getBackgroundEffect(1));
		BorderPane.setMargin(grid, new Insets(10));
		//grid.setStyle("-fx-background-color: #272727;");
		grid.setStyle(getBackgroundEffect(1) + "-fx-font: 15px Tahoma" );
		root.setPadding(new Insets(10));
		root.setLeft(leftPaneQuestions);
		root.setRight(grid);
		root.setBottom(bottomPart);
		displayInformation.setFont(Font.font(15));



		VBox playScene = new VBox(20, menu,root);

		return new Scene(playScene, 1400, 750);

	}// end of playScene

	public Scene newLookScene () {

		bNewLookBack.setStyle(getBackgroundEffect(1));
		VBox buttonsPane = new VBox(30, theme1, theme2, theme3);
		buttonsPane.setAlignment(Pos.CENTER);
		theme1.setStyle(getBackgroundEffect(1));
		theme2.setStyle(getBackgroundEffect(1));
		theme3.setStyle(getBackgroundEffect(1));


		newLookpane = new VBox(20, bNewLookBack, buttonsPane);
		newLookpane.setPadding(new Insets(30));
		newLookpane.setStyle(getBackgroundEffect(1));

		return new Scene(newLookpane, 850, 750);

	}//  end of newLookScene

	/*
	 * method to populate a GridPane with buttons and attach a handler to each button
	 */
	public void addGrid() {

		int number = 1;
		for(int x = 0; x<10; x++) {
			for(int i = 0; i<8; i++) {
				Button b1 = new Button(Integer.toString(number));
				b1.setOnAction(myHandler);
				b1.setStyle(" -fx-background-radius: 10em;" +
						"-fx-min-width: 40px; " +
						"-fx-min-height: 40px; " +
						"-fx-max-width: 40px; " +
						"-fx-max-height: 40px;"+
						"-fx-background-color: #7B68EE;" );
				grid.add(b1, i, x);
				 number ++;
				 b1.setDisable(true);
			}
		}
	}// end of addGrid

	public void unlockGrid () {
		for(int x = 0; x<8; x++) {
			for(int i = 0; i<10; i++) {
				Button b1 = (Button) grid.getChildren().get(x*10+i);
				b1.setStyle(" -fx-background-radius: 10em;" +
						"-fx-min-width: 40px; " +
						"-fx-min-height: 40px; " +
						"-fx-max-width: 40px; " +
						"-fx-max-height: 40px;"+
						"-fx-background-color: #7B68EE;");
				b1.setDisable(false);
			}
		}
	}// end of unlockGrid

	public void lockGrid () {
		for(int x = 0; x<8; x++) {
			for(int i = 0; i<10; i++) {
				Button b1 = (Button) grid.getChildren().get(x*10+i);
				b1.setDisable(true);
			}
		}

	}// end of disableGrid



	public int getSelectedOptionMoney () {
		int returnVal = 0;

		if (radio1.isSelected()) {
			returnVal = 1;
		}else if (radio2.isSelected()) {
			returnVal = 2;
		}else if (radio3.isSelected()) {
			returnVal = 3;
		}else if (radio4.isSelected()) {
			returnVal = 5;
		}else if (radio5.isSelected()) {
			returnVal = 10;
		}

		if(returnVal != 0) {
			/*disable the first question options*/
			radio1.setDisable(true);
			radio2.setDisable(true);
			radio3.setDisable(true);
			radio4.setDisable(true);
			radio5.setDisable(true);
			nextDollar.setDisable(true);
			/*enable second question options*/
			radioDraw1.setDisable(false);
			radioDraw2.setDisable(false);
			radioDraw3.setDisable(false);
			radioDraw4.setDisable(false);
			nextDraws.setDisable(false);
		}

		return returnVal;
	}// end of getSelectedOption

	public int getSelectedOptionDraws () {
		int returnVal = 0;

		if (radioDraw1.isSelected()) {
			returnVal = 1;
		}else if (radioDraw2.isSelected()) {
			returnVal = 2;
		}else if (radioDraw3.isSelected()) {
			returnVal = 3;
		}else if (radioDraw4.isSelected()) {
			returnVal = 4;
		}

		if(returnVal != 0) {
			/*disable second question options*/
			radioDraw1.setDisable(true);
			radioDraw2.setDisable(true);
			radioDraw3.setDisable(true);
			radioDraw4.setDisable(true);
			nextDraws.setDisable(true);
			/*enable third question options*/
			radioSpot1.setDisable(false);
			radioSpot4.setDisable(false);
			radioSpot8.setDisable(false);
			radioSpot10.setDisable(false);
			nextSpots.setDisable(false);
		}

		return returnVal;
	}// end of getSelectedOption

	public int getSelectedOptionSpots () {
		int returnVal = 0;

		if (radioSpot1.isSelected()) {
			returnVal = 1;
		}else if (radioSpot4.isSelected()) {
			returnVal = 4;
		}else if (radioSpot8.isSelected()) {
			returnVal = 8;
		}else if (radioSpot10.isSelected()) {
			returnVal = 10;
		}

		if(returnVal != 0) {
			radioSpot1.setDisable(true);
			radioSpot4.setDisable(true);
			radioSpot8.setDisable(true);
			radioSpot10.setDisable(true);
			nextSpots.setDisable(true);
			quickPick.setDisable(false);
		}

		return returnVal;
	}// end of getSelectedOption

	public void printMessageWarning () {
		Alert a = new Alert(Alert.AlertType.INFORMATION);
		a.setTitle("Message");
		a.setHeaderText("");
		a.setContentText("Before Continue Select one Option");
		a.show();
	}// end of printMessageWarning

	public void printMessageSelectNumbers () {
		Alert a = new Alert(Alert.AlertType.INFORMATION);
		a.setTitle("Message");
		a.setHeaderText("");
		a.setContentText("Now Select your "+Integer.toString(spots)+" numbers");
		a.show();
	}// end of printMessageWarning

	public void printMessageStartGame () {
		Alert a = new Alert(Alert.AlertType.INFORMATION);
		a.setTitle("Message");
		a.setHeaderText("");
		a.setContentText("You can reselect your "+Integer.toString(spots)+" numbers or start the Game.");
		a.show();
	}// end of printMessageWarning


	public void finalMessageGame() {
		Stage finalMessage = new Stage();
		finalMessage.setTitle("Keno Game");
		Button yes = new Button("Yes");
		Button exit = new Button("Exit");
		Text message = new Text("Thank you for Play Keno,\n do you want to play again ?");
		message.setTextAlignment(TextAlignment.JUSTIFY);
		HBox hLayout = new HBox(30, yes, exit);
		VBox layaout = new VBox(20, message, hLayout);
		layaout.setAlignment(Pos.CENTER);
		hLayout.setAlignment(Pos.CENTER);
		yes.setStyle(getBackgroundEffect(1));
		exit.setStyle(getBackgroundEffect(1));
		layaout.setStyle(getBackgroundEffect(1));
		finalMessage.setResizable(false);


		yes.setOnAction(e-> {
			resetGame();
			finalMessage.close();

		});
		exit.setOnAction(e->{
			/*exit the program*/
			Platform.exit();
			System.exit(0);
		});

		Scene messageScene = new Scene(layaout, 500, 300);
		finalMessage.setScene(messageScene);
		finalMessage.show();
	}// end of finalMessageGame

	public void resetGame() {
		dollars = 0;
		spots = 0;
		draws = 0;

		/*unselect radioButtons*/
		radio1.setSelected(false);
		radio2.setSelected(false);
		radio3.setSelected(false);
		radio4.setSelected(false);
		radio5.setSelected(false);

		radioDraw1.setSelected(false);
		radioDraw2.setSelected(false);
		radioDraw3.setSelected(false);
		radioDraw4.setSelected(false);

		radioSpot1.setSelected(false);
		radioSpot4.setSelected(false);
		radioSpot8.setSelected(false);
		radioSpot10.setSelected(false);

		radio1.setDisable(false);
		radio2.setDisable(false);
		radio3.setDisable(false);
		radio4.setDisable(false);
		radio5.setDisable(false);

		nextDollar.setDisable(false);

		displayInformation.clear();
		showPrize.setText("$ 0");

		unlockGrid();
		lockGrid();



	}// end of resetGame


	public void updateTextArea (String text, boolean numbersOfGrid) {

		if (numbersOfGrid) {
			displayInformation.appendText(text + " - ");

		}else {
			displayInformation.appendText("Money to Play: "+Integer.toString(dollars) + "\n");
			displayInformation.appendText("Consecutive Draws: "+Integer.toString(draws) + "\n");
			displayInformation.appendText("Number of Spots: "+ Integer.toString(spots) + "\n");
			displayInformation.appendText("Numbers Selected : - ");
		}

	}// end of updateTextArea


	/*IMPORTANT NOTE:
	* the prizes are set to be according to the Carolina Keno game, but it was a little hard get the player win
	* some money when he had a situation like choose to pick play 4 spots and get just one match, the player would
	* not have won any money according to the Carolina Keno. So I decided to let the player win some money when at least
	* he have one match */
	public void setPrizeMoney (int matches) {


		if (spots == 1){
			if (matches == 1) {
				accumulatePrize = accumulatePrize + 2;
			}
		}else if (spots == 4) {
				/*Prizes for spot 4*/
				if (matches == 2 || matches == 1) {
					accumulatePrize = accumulatePrize + 1;
				}else if (matches == 3) {
					accumulatePrize = accumulatePrize + 5;
				}else if (matches == 4) {
					accumulatePrize = accumulatePrize + 75;
				}

		}else if (spots == 8) {
				/*Prizes for spot 8*/
				if (matches == 4 || matches == 3 || matches == 2 || matches == 1) {
					accumulatePrize = accumulatePrize + 2;
				}else if (matches == 5) {
					accumulatePrize = accumulatePrize + 12;
				}else if (matches == 6) {
					accumulatePrize = accumulatePrize + 50;
				}else if (matches == 7) {
					accumulatePrize = accumulatePrize + 750;
				}else if (matches == 8) {
					accumulatePrize = accumulatePrize + 10000;
				}

		}else if (spots == 10) {
				/*Prizes for spot 10*/
				if (matches == 0 ||matches == 4 || matches == 3 || matches == 2 || matches == 1 ) {
					accumulatePrize = accumulatePrize + 5;
				}else if (matches == 5) {
					accumulatePrize = accumulatePrize + 2;
				}else if (matches == 6) {
					accumulatePrize = accumulatePrize + 15;
				}else if (matches == 7) {
					accumulatePrize = accumulatePrize + 40;
				}else if (matches == 8) {
					accumulatePrize = accumulatePrize + 450;
				}else if (matches == 9) {
					accumulatePrize = accumulatePrize + 4250;
				}else if (matches == 10) {
					accumulatePrize = accumulatePrize + 100000;
				}
		}


		/*set the Label to the actual prize*/
		showPrize.setText("$ "+Integer.toString(accumulatePrize));

	}// end of setPrizeMoney

	public String getBackgroundEffect (int num) {
		String newStyle = "";

		if (num == 1) {
			newStyle = "-fx-font: 25px Tahoma;" +
					"-fx-text-fill: white;"+
					"    -fx-stroke-width: 5;"+
					" -fx-background-insets: 0,1,4,5,6;" +
					" -fx-background-radius: 9,8,5,4,3;"+
					"-fx-background-color:" +
					" linear-gradient(#000000, #000000)," +
					" radial-gradient(center 50% -40%, radius 200%, #0489B1 45%, #81F7F3 50%);";
		}else if (num == 2) {
			newStyle = "-fx-font: 25px Verdana;" +
					"-fx-text-fill: blue;"+
					" -fx-stroke-width: 5;"+
					" -fx-background-insets: 0,1,4,5,6;" +
					" -fx-background-radius: 9,8,5,4,3;"+
					"-fx-background-color:" +
					" linear-gradient(#000000, #000000)," +
					" radial-gradient(center 50% -40%, radius 100%, #0B610B 45%, #088A08 50%);";
		}else if (num == 3) {
			newStyle = "-fx-font: 25px Verdana;" +
					"-fx-text-fill: blue;"+
					" -fx-stroke-width: 5;"+
					" -fx-background-insets: 0,1,4,5,6;" +
					" -fx-background-radius: 9,8,5,4,3;"+
					"-fx-background-color:" +
					" linear-gradient(#000000, #000000)," +
					" radial-gradient(center 50% -40%, radius 100%, #FF8000 45%, #8A4B08 50%);";
		}else if (num == 4) {
			newStyle = "-fx-font: 25px Verdana;" +
					"-fx-text-fill: blue;"+
					" -fx-stroke-width: 5;"+
					" -fx-background-insets: 0,1,4,5,6;" +
					" -fx-background-radius: 9,8,5,4,3;"+
					"-fx-background-color:" +
					" linear-gradient(#000000, #000000)," +
					" radial-gradient(center 50% -40%, radius 100%, #8A0829 45%, #DF0174 50%);";
		}

		return newStyle;

	}// end of getBackgroundEffect

}
