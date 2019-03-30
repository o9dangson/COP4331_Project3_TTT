package course.oop.view;

import javafx.animation.RotateTransition;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import course.oop.main.TTTDriver;
import course.oop.classes.*;
import course.oop.controller.*;
import javafx.scene.effect.*;
import java.io.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;

public class MainView {
	private BorderPane root;
	private Scene scene;
	private Text statusNode;
    private final int windowWidth = 1000;
    private final int windowHeight = 600;
    private Text gameMode = new Text("Tic-Tac-Toe");
    private TTTControllerImpl interfaceA;
    private int numPlayers = 1;
    private int currentPlayerTurn = 1;
    private String p1Username = "P1";
    private String p2Username = "P2";
    private String p1marker = "X";
    private String p2marker = "O";
    private int delay = 1000;
    private int period = 1000;
    private String emojiType = "sleep";
    private String emojiType2 = "think";
    private String fileName = "users.ser";
    
    //Testing Code
    /*
     * //TESTING
				Media plonkSound = new Media(new File("src/victory.mp3").toURI().toString());
				MediaPlayer mp = new MediaPlayer(plonkSound);
				mp.play();
     */
    
	public MainView() {
		this.root = new BorderPane();
		this.scene = new Scene(root, windowWidth, windowHeight);
		
		this.scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
		gameMode.setId("gameMode");
		gameMode.getStyleClass().add("orangeButton");
		this.statusNode = new Text("no status");
		this.root.setTop(this.buildTopPane());
		this.root.setBottom(this.buildHomePane());
		
		//Build initial save file
		createFile();
		
	}
	
	public Scene getMainScene() {
		return this.scene;
	}
	
	//Done
	public GridPane buildBlankPane() {
		GridPane g = new GridPane();
		g.setMaxSize(windowWidth*3/4, (int) windowHeight/2);
		return g;
	}
	
	//Done
	public GridPane buildTopPane() {
		GridPane topPane = new GridPane();
		topPane.setMaxSize(windowWidth, (int) windowHeight/8);
		topPane.setPadding(new Insets(5, 5, 5, 5));
		topPane.setVgap(5); 
		topPane.setHgap(5);       
		topPane.setAlignment(Pos.CENTER); 
		
		topPane.add(gameMode, 0, 0);
		return topPane;
	}
	
	//Done
	public GridPane buildTopPane(String text) {
		GridPane topPane = new GridPane();
		topPane.setMinSize(windowWidth, (int) windowHeight/8);
		topPane.setPadding(new Insets(5, 5, 5, 5));
		topPane.setVgap(5); 
		topPane.setHgap(5);       
		topPane.setAlignment(Pos.CENTER); 
		
		gameMode = new Text(text);
		gameMode.setId("gameMode");
		topPane.add(gameMode, 0, 0);
		return topPane;
	}
	
	//Done
	public GridPane buildBotPane() {
		GridPane botPane = new GridPane();
		//Setting Dimensions
		botPane.setMaxSize(windowWidth, (int) windowHeight/4);
		botPane.setPadding(new Insets(10, 10, 10, 10));
		botPane.setVgap(5); 
		botPane.setHgap(15);       
		botPane.setAlignment(Pos.CENTER); 
		
		Button button1 = new Button("Home");
		button1.getStyleClass().add("greenButton");
		Button button3 = new Button("Options");
		button3.getStyleClass().add("image");
		Image image = new Image("/gear.jpg");
		ImageView imageView = new ImageView(image);
		//Setting up imageView
		imageView.setFitWidth(40);
		imageView.setFitHeight(40);
		imageView.setPreserveRatio(true);  
		
		EventHandler<MouseEvent> b1_handler = new EventHandler<MouseEvent>() { 
			@Override 
	        public void handle(MouseEvent e) { 
				if(TTTDriver.isCounting)
					TTTDriver.timer.cancel();
				//Set LeftPane as a blank slate
				root.setLeft(buildBlankPane());
				root.setRight(buildRightPane());
	            //Go back to HomePane
				root.setBottom(buildHomePane());
	        } 
	        
		};
		
		
		
		EventHandler<MouseEvent> b3_handler = new EventHandler<MouseEvent>() { 
			@Override 
	        public void handle(MouseEvent e) { 
				root.setLeft(buildOptionPane(0));
				root.setRight(buildBlankPane());
	        } 
	        
		};
		
		//Registering the event filter 
	    button1.addEventFilter(MouseEvent.MOUSE_CLICKED, b1_handler);
	    
	    button3.addEventFilter(MouseEvent.MOUSE_CLICKED, b3_handler);
	    
	    botPane.add(button1, 0, 0);
	    Text blank = new Text("                      ");
	    botPane.add(blank,  1,  0);
	    botPane.add(imageView, 2, 0);
	    botPane.add(button3, 3, 0);
	    
		return botPane;
	}
	
	//Done
	public GridPane buildHomePane() {
		GridPane botPane = new GridPane();
		//Setting Dimensions
		botPane.setMinSize(windowWidth, (int) windowHeight/4);
		botPane.setPadding(new Insets(10, 10, 10, 10));
		botPane.setVgap(5); 
		botPane.setHgap(15);       
		botPane.setAlignment(Pos.CENTER);
		
		Button button1 = new Button("3x3");
		button1.getStyleClass().add("greenButton");
		Button button2 = new Button("Territory Game");
		button2.getStyleClass().add("redButton");
		//Temp for Project 3
		button2.setDisable(true);
		Button button3 = new Button("Help/Game Rules");
		button3.getStyleClass().add("orangeButton");
		
		EventHandler<MouseEvent> b1_handler = new EventHandler<MouseEvent>() { 
			@Override 
	        public void handle(MouseEvent e) { 
				//Set GameMode to 3x3
				root.setTop(buildTopPane("Tic-Tac-Toe"));
				//Set botPane to Menu
				root.setBottom(buildBotPane());
				//Get Player(s) Details
				root.setLeft(buildOptionPane(0));
				root.setRight(buildBlankPane());
	        } 
	        
		};
		
		EventHandler<MouseEvent> b2_handler = new EventHandler<MouseEvent>() { 
			@Override 
	        public void handle(MouseEvent e) { 
				//Set GameMode to Territory.
				root.setTop(buildTopPane("Territory Game"));
				//Set botPane to Menu
				root.setBottom(buildBotPane());
				//Get Player Details
				//root.setLeft(buildTerritoryOptionPane());
	        } 
	        
		};
		
		EventHandler<MouseEvent> b3_handler = new EventHandler<MouseEvent>() { 
			@Override 
	        public void handle(MouseEvent e) { 
				//Help Screen
				root.setLeft(buildHelpPane());
				root.setRight(buildBlankPane());
	        } 
	        
		};
		
		
		//Registering the event filter 
	    button1.addEventFilter(MouseEvent.MOUSE_CLICKED, b1_handler);
	    button2.addEventFilter(MouseEvent.MOUSE_CLICKED, b2_handler);
	    button3.addEventFilter(MouseEvent.MOUSE_CLICKED, b3_handler);
	    
	    botPane.add(button1, 0, 0);
	    botPane.add(button2, 2, 0);
	    botPane.add(button3, 3, 0);
	    
		return botPane;
	}
	
	public GridPane buildOptionPane(int pNum) {
		root.setRight(buildRightPane());
		GridPane optionPane = new GridPane();
		//Setting Dimensions
		optionPane.setMaxSize(windowWidth*3/4, (int) windowHeight/2);
		optionPane.setPadding(new Insets(10, 10, 10, 10));
		optionPane.setVgap(5); 
		optionPane.setHgap(15);       
		optionPane.setAlignment(Pos.TOP_LEFT);
		
		//Players
		Text numPlayerText = new Text("Number of Players: ");
		ToggleGroup groupPlayer = new ToggleGroup();
		RadioButton r1 = new RadioButton("1");
		RadioButton r2 = new RadioButton("2");
		r1.setToggleGroup(groupPlayer);
		r1.setSelected(true);
		r2.setToggleGroup(groupPlayer);
		
		Text player1Name = new Text("Player 1: ");
		Text player2Name = new Text("Player 2: ");
		TextField p1Name = new TextField("P1");
		TextField p2Name = new TextField("P2");
		
		//Marker
		ToggleGroup groupMarker_1 = new ToggleGroup();
		RadioButton r3 = new RadioButton("X");
		RadioButton r4 = new RadioButton("O");
		RadioButton r5 = new RadioButton("Emoji");
		RadioButton r6 = new RadioButton("Unique String");
		r3.setToggleGroup(groupMarker_1);
		r3.setSelected(true);
		r4.setToggleGroup(groupMarker_1);
		r5.setToggleGroup(groupMarker_1);
		r6.setToggleGroup(groupMarker_1);
		
		
		ToggleGroup groupMarker_2 = new ToggleGroup();
		RadioButton r7 = new RadioButton("X");
		RadioButton r8 = new RadioButton("O");
		RadioButton r9 = new RadioButton("Emoji");
		RadioButton r10 = new RadioButton("Unique String");
		r7.setToggleGroup(groupMarker_2);
		r8.setToggleGroup(groupMarker_2);
		r8.setSelected(true);
		r9.setToggleGroup(groupMarker_2);
		r10.setToggleGroup(groupMarker_2);
		
		
		TextField p1String = new TextField();
		p1String.setDisable(true);
		TextField p2String = new TextField();
		p2String.setDisable(true);
		
		//Existing Player
		Button importPlayer1 = new Button("Import User");
		Button importPlayer2 = new Button("Import User");
		
		//Timer
		Text timerText = new Text("Turn Duration: ");
		TextField tDuration = new TextField("0");
		
		//Adjust values accordingly if values were imported
		if(pNum != 0) {
			tDuration.setText(Integer.toString(TTTDriver.timeDuration));
			if(numPlayers == 2)
				r2.setSelected(true);
			if (pNum == 1) {
				p1Name.setText(p1Username);
				if(p1marker.equals("X"))
					r3.setSelected(true);
				else if(p1marker.equals("O"))
					r4.setSelected(true);
				else if(p1marker.contains("emoji"))
					r5.setSelected(true);
				else
				{
					r6.setSelected(true);
					p1String.setText(p1marker);
				}
			}else {
				r2.setSelected(true);
				p2Name.setText(p2Username);
				if(p2marker.equals("X"))
					r7.setSelected(true);
				else if(p2marker.equals("O"))
					r8.setSelected(true);
				else if(p2marker.contains("emoji"))
					r9.setSelected(true);
				else
				{
					r10.setSelected(true);
					p2String.setText(p1marker);
				}
			}
		}
		
		optionPane.add(numPlayerText, 1, 1);
		optionPane.add(r1, 2, 1);
		optionPane.add(r2, 3, 1);
		optionPane.add(player1Name, 1, 2);
		optionPane.add(p1Name, 2, 2);
		optionPane.add(player2Name, 3, 2);
		optionPane.add(p2Name, 4, 2);
		optionPane.add(r3, 1, 3);
		optionPane.add(r4, 1, 4);
		optionPane.add(r5, 1, 5);
		optionPane.add(r6, 1, 6);
		optionPane.add(r7, 3, 3);
		optionPane.add(r8, 3, 4);
		optionPane.add(r9, 3, 5);
		optionPane.add(r10, 3, 6);
		optionPane.add(p1String, 1, 7);
		optionPane.add(p2String, 3, 7);
		optionPane.add(importPlayer1, 1, 9);
		optionPane.add(importPlayer2, 3, 9);
		optionPane.add(timerText, 1, 10);
		optionPane.add(tDuration, 2, 10);

		EventHandler<MouseEvent> r1_handler = new EventHandler<MouseEvent>() { 
			@Override 
	        public void handle(MouseEvent e) { 
				numPlayers = 1;
	        } 
	        
		};
		
		EventHandler<MouseEvent> r2_handler = new EventHandler<MouseEvent>() { 
			@Override 
	        public void handle(MouseEvent e) { 
				numPlayers = 2;
	        } 
	        
		};

		EventHandler<MouseEvent> r3_handler = new EventHandler<MouseEvent>() { 
			@Override 
	        public void handle(MouseEvent e) { 
				p1String.setDisable(true);
	        } 
	        
		};

		EventHandler<MouseEvent> r4_handler = new EventHandler<MouseEvent>() { 
			@Override 
	        public void handle(MouseEvent e) { 
				p1String.setDisable(true);
	        } 
	        
		};

		EventHandler<MouseEvent> r5_handler = new EventHandler<MouseEvent>() { 
			@Override 
	        public void handle(MouseEvent e) { 
				p1String.setDisable(true);
				root.setRight(buildEmojiPane(1));
	        } 
	        
		};
		
		EventHandler<MouseEvent> r6_handler = new EventHandler<MouseEvent>() { 
			@Override 
	        public void handle(MouseEvent e) { 
				p1String.setDisable(false);
	        } 
	        
		};
		
		EventHandler<MouseEvent> r7_handler = new EventHandler<MouseEvent>() { 
			@Override 
	        public void handle(MouseEvent e) { 
				p2String.setDisable(true);
	        } 
	        
		};
		
		EventHandler<MouseEvent> r8_handler = new EventHandler<MouseEvent>() { 
			@Override 
	        public void handle(MouseEvent e) { 
				p2String.setDisable(true);
	        } 
	        
		};

		EventHandler<MouseEvent> r9_handler = new EventHandler<MouseEvent>() { 
			@Override 
	        public void handle(MouseEvent e) { 
				p2String.setDisable(true);
				root.setRight(buildEmojiPane(2));
	        } 
	        
		};
		
		EventHandler<MouseEvent> r10_handler = new EventHandler<MouseEvent>() { 
			@Override 
	        public void handle(MouseEvent e) { 
				p2String.setDisable(false);
	        } 
	        
		};

		r1.addEventFilter(MouseEvent.MOUSE_CLICKED, r1_handler);
		r2.addEventFilter(MouseEvent.MOUSE_CLICKED, r2_handler);
		r3.addEventFilter(MouseEvent.MOUSE_CLICKED, r3_handler);
		r4.addEventFilter(MouseEvent.MOUSE_CLICKED, r4_handler);
		r5.addEventFilter(MouseEvent.MOUSE_CLICKED, r5_handler);
		r6.addEventFilter(MouseEvent.MOUSE_CLICKED, r6_handler);
		r7.addEventFilter(MouseEvent.MOUSE_CLICKED, r7_handler);
		r8.addEventFilter(MouseEvent.MOUSE_CLICKED, r8_handler);
		r9.addEventFilter(MouseEvent.MOUSE_CLICKED, r9_handler);
		r10.addEventFilter(MouseEvent.MOUSE_CLICKED, r10_handler);
		
		Button startButton = new Button("Start");
		startButton.getStyleClass().add("greenButton");
		Button resetButton = new Button("Reset");
		resetButton.getStyleClass().add("orangeButton");
		Button quitButton = new Button("Quit");
		quitButton.getStyleClass().add("redButton");
		
		//TODO::Implement handler for importing players
		EventHandler<MouseEvent> import1_handler = new EventHandler<MouseEvent>() { 
			@Override 
	        public void handle(MouseEvent e) { 
				if(r2.isSelected())
					numPlayers = 2;
				else
					numPlayers = 1;
				TTTDriver.interval = Integer.parseInt(tDuration.getText());
				TTTDriver.timeDuration = TTTDriver.interval;
				p1Username = p1Name.getText();
				p2Username = p2Name.getText();
				
				if(r3.isSelected())
					p1marker = "X";
				else if(r4.isSelected())
					p1marker = "O";
				else if(r5.isSelected()) 
					p1marker = "emoji_" + emojiType;	//emojiType decided in another pane
				else if(r6.isSelected())
					p1marker = p1String.getText();
				
				if(r7.isSelected())
					p2marker = "X";
				else if(r8.isSelected())
					p2marker = "O";
				else if(r9.isSelected()) 
					p2marker = "emoji_" + emojiType2;	//emojiType decided in another pane
				else if(r10.isSelected())
					p2marker = p1String.getText();
				root.setRight(buildPlayerPane(1));
	        } 
		};
		
		EventHandler<MouseEvent> import2_handler = new EventHandler<MouseEvent>() { 
			@Override 
	        public void handle(MouseEvent e) { 
				if(r2.isSelected())
					numPlayers = 2;
				else
					numPlayers = 1;
				TTTDriver.interval = Integer.parseInt(tDuration.getText());
				TTTDriver.timeDuration = TTTDriver.interval;
				p1Username = p1Name.getText();
				p2Username = p2Name.getText();
				
				if(r3.isSelected())
					p1marker = "X";
				else if(r4.isSelected())
					p1marker = "O";
				else if(r5.isSelected()) 
					p1marker = "emoji_" + emojiType;	//emojiType decided in another pane
				else if(r6.isSelected())
					p1marker = p1String.getText();
				
				if(r7.isSelected())
					p2marker = "X";
				else if(r8.isSelected())
					p2marker = "O";
				else if(r9.isSelected()) 
					p2marker = "emoji_" + emojiType2;	//emojiType decided in another pane
				else if(r10.isSelected())
					p2marker = p1String.getText();
				root.setRight(buildPlayerPane(2));
	        } 
		};

		importPlayer1.addEventFilter(MouseEvent.MOUSE_CLICKED, import1_handler);
		importPlayer2.addEventFilter(MouseEvent.MOUSE_CLICKED, import2_handler);
		
		//TODO::FInish
		EventHandler<MouseEvent> start_handler = new EventHandler<MouseEvent>() { 
			@Override 
	        public void handle(MouseEvent e) {
				//Build the game
				interfaceA = new TTTControllerImpl();
				currentPlayerTurn = 1;
				if(r2.isSelected())
					numPlayers = 2;
				else
					numPlayers = 1;
				int temp = Integer.parseInt(tDuration.getText());
				if(temp < 0)
					temp = 0;
				TTTDriver.interval = temp;
				TTTDriver.timeDuration = TTTDriver.interval;
				p1Username = p1Name.getText();
				p2Username = p2Name.getText();
				
				if(r3.isSelected())
					p1marker = "X";
				else if(r4.isSelected())
					p1marker = "O";
				else if(r5.isSelected()) 
					p1marker = "emoji_" + emojiType;	//emojiType decided in another pane
				else if(r6.isSelected())
					p1marker = p1String.getText();
				
				if(r7.isSelected())
					p2marker = "X";
				else if(r8.isSelected())
					p2marker = "O";
				else if(r9.isSelected()) 
					p2marker = "emoji_" + emojiType2;	//emojiType decided in another pane
				else if(r10.isSelected())
					p2marker = p1String.getText();
			    
				//Create game
			    interfaceA.startNewGame(numPlayers, TTTDriver.timeDuration);
			    interfaceA.getGame().resetBoard();
			    interfaceA.createPlayer(p1Username, p1marker, 1);
			    interfaceA.createPlayer(p2Username, p2marker, 2);
			    
			    System.out.println(p1marker + " " + p2marker);
			    if(numPlayers > 1)
			    	interfaceA.getGame().getPlayer(2).setAi(true);
				//Save Players to file
				TTTPlayer p = interfaceA.getGame().getPlayer(1);
				saveFile(p);
				p = interfaceA.getGame().getPlayer(2);
				saveFile(p);
				//Start the game
				TTTDriver.isCounting = true;
				TTTDriver.timer = new Timer();
				if(interfaceA.getGame().getDoesTimerMatter()) {
					TTTDriver.timer.schedule(new TimerTask() {
						public void run() {
							root.setLeft(buildLeftPane());
							root.setLeft(buildGamePane());
						}
					}, delay, period);
				}
				root.setLeft(buildLeftPane());
				root.setRight(buildGamePane());
	        } 
		};
		
		EventHandler<MouseEvent> reset_handler = new EventHandler<MouseEvent>() { 
			@Override 
	        public void handle(MouseEvent e) { 
				//Reset Board
				interfaceA.getGame().resetBoard();
				//Restart the game
				root.setLeft(buildLeftPane());
				root.setRight(buildGamePane());
	        } 
		};
		
		EventHandler<MouseEvent> quit_handler = new EventHandler<MouseEvent>() { 
			@Override 
	        public void handle(MouseEvent e) { 
				root.setBottom(buildHomePane());
				root.setLeft(buildBlankPane());
				root.setRight(buildBlankPane());
				TTTDriver.timer.cancel();
	        } 
		};

		startButton.addEventFilter(MouseEvent.MOUSE_CLICKED, start_handler);
		resetButton.addEventFilter(MouseEvent.MOUSE_CLICKED, reset_handler);
		quitButton.addEventFilter(MouseEvent.MOUSE_CLICKED, quit_handler);
		
		optionPane.add(startButton, 1, 11);
		optionPane.add(resetButton, 2, 11);
		optionPane.add(quitButton, 3, 11);
		
		return optionPane;
	}
	
	//Display Board here
	public GridPane buildLeftPane() {
		GridPane leftPane = new GridPane();
		Group group = new Group();
		//Setting Dimensions
		leftPane.setMinSize(windowWidth*3/4, (int) windowHeight/2);
		leftPane.setPadding(new Insets(10, 10, 10, 10));
		leftPane.setVgap(5); 
		leftPane.setHgap(15); 
		leftPane.setAlignment(Pos.TOP_LEFT);
		
		//Variables
		int [][] board = interfaceA.getGame().getBoard();
		String p1_marker = determineImageURL(p1marker);
		String p2_marker = determineImageURL(p2marker);
		boolean isP1MarkerString = false;
		boolean isP2MarkerString = false;
		if(!p1_marker.contains(".png") && !p1_marker.contains(".jpg"))
			isP1MarkerString = true;
		if(!p2_marker.contains(".png") && !p2_marker.contains(".jpg"))
			isP2MarkerString = true;
		
		//Audio
		Media victorySound = new Media(new File("src/victory.mp3").toURI().toString());
		Media loserSound = new Media(new File("src/Looser-sound-effect.mp3").toURI().toString());
		MediaPlayer mp1 = new MediaPlayer(victorySound);
		MediaPlayer mp2 = new MediaPlayer(loserSound);
		
		
		//Default Image
		Image p1Image = new Image("/x_icon.png");
		Image p2Image = new Image("/o_icon.jpg");
		if(!isP1MarkerString)
			p1Image = new Image(p1_marker);
		if(!isP2MarkerString)
			p2Image = new Image(p2_marker);
		
		List<ImageView> imgViewList = new ArrayList<ImageView>();
		List<Text> textList = new ArrayList<Text>();
		for(int i=0;i<9;i++) {
			imgViewList.add(new ImageView(new Image("/x_icon.png")));
			textList.add(new Text("Temp"));
		}
		
		//Initialize Elements
		Line line_v1 = new Line();
		Line line_v2 = new Line();
		Line line_h1 = new Line();
		Line line_h2 = new Line();
		
		line_h1.setStartX(100.0); 	line_h2.setStartX(100);	line_v1.setStartX(200.0); 	line_v2.setStartX(300);
		line_h1.setStartY(200.0); 	line_h2.setStartY(300);	line_v1.setStartY(100.0); 	line_v2.setStartY(100);
		line_h1.setEndX(400.0);		line_h2.setEndX(400);	line_v1.setEndX(200.0);		line_v2.setEndX(300);	
		line_h1.setEndY(200.0); 	line_h2.setEndY(300);	line_v1.setEndY(400.0); 	line_v2.setEndY(400);	
		
		Text turnCounter = new Text("Timer: " + Integer.toString(TTTDriver.interval));
		turnCounter.setLayoutX(450); turnCounter.setLayoutY(200);
		
		Text winnerText = new Text();
		if(interfaceA.determineWinner() == 0)
			winnerText = new Text("Game in Progress..");
		else if(interfaceA.determineWinner() == 1) {
			winnerText = new Text(p1Username + " wins!");
			mp1.play();
		}
		else if(interfaceA.determineWinner() == 2) {
			winnerText = new Text(p2Username + " wins!");
			if(numPlayers == 1)
				mp2.play();
			else
				mp1.play();
		}
		else {
			winnerText = new Text("Tie!");
			mp2.play();
		}
		
		winnerText.setLayoutX(450); winnerText.setLayoutY(400);
		
		//Iterate Through board
		for(int i=0;i<board.length;i++) {
			for(int j=0;j<board[0].length;j++) {
				ImageView iv;
				Text str;
				//Create ImgView or Text field for each Spot
				if(board[i][j] == 1) {
					iv = new ImageView(p1Image);
					iv.setX(110+(100*j)); iv.setY(110+(100*i));
					iv.setFitWidth(75);
					iv.setFitHeight(75);
					iv.setPreserveRatio(true); 
					str = new Text(p2_marker);
					imgViewList.set((i*3 + j), iv);
					textList.set((i*3 + j), str);
				}else if(board[i][j] == 2) {
					iv = new ImageView(p2Image);
					iv.setX(110+(100*j)); iv.setY(110+(100*i));
					iv.setFitWidth(75);
					iv.setFitHeight(75);
					iv.setPreserveRatio(true);
					str = new Text(p2_marker);
					imgViewList.set((i*3 + j), iv);;
					textList.set((i*3 + j), str);
				}
			}
		}
		
		//Add elements to group and GridPane
		for(int i=0;i<board.length;i++) {
			for(int j=0;j<board.length;j++) {
				if(board[i][j] == 1) {
					if(isP1MarkerString)
						group.getChildren().add(textList.get(i*3 + j));
					else
						group.getChildren().add(imgViewList.get(i*3 + j));
				}
				else if (board[i][j] == 2) {
					if(isP2MarkerString)
						group.getChildren().add(textList.get(i*3 + j));
					else
						group.getChildren().add(imgViewList.get(i*3 + j));
				}
			}
		}
		
		group.getChildren().add(line_h1);
		group.getChildren().add(line_h2);
		group.getChildren().add(line_v1);
		group.getChildren().add(line_v2);
		group.getChildren().add(turnCounter);
		group.getChildren().add(winnerText);
		leftPane.add(group, 0, 0);
		return leftPane;
	}

	//Display User Controls
	public ScrollPane buildGamePane() {
		ScrollPane sp = new ScrollPane();
		GridPane pane = new GridPane();
		//Setting Dimensions
		pane.setMaxSize(windowWidth/4, (int) windowHeight/2);
		pane.setPadding(new Insets(5, 5, 5, 5));
		pane.setVgap(5); 
		pane.setHgap(5);       
		pane.setAlignment(Pos.TOP_LEFT);
		sp.setMaxWidth(windowWidth/4);
		
		// Prompt
		Text promptText = new Text();
		String temp = "";
		if(interfaceA.getGame().getCurrentTurn())
			temp = p1Username;
		else
			temp = p2Username;
		promptText.setText(temp);
		
		// Buttons
		Button b1 = new Button("1");
		Button b2 = new Button("2");
		Button b3 = new Button("3");
		Button b4 = new Button("4");
		Button b5 = new Button("5");
		Button b6 = new Button("6");
		Button b7 = new Button("7");
		Button b8 = new Button("8");
		Button b9 = new Button("9");
		
		// Event Handlers
		EventHandler<MouseEvent> b1_handler = new EventHandler<MouseEvent>() { 
			@Override 
	        public void handle(MouseEvent e) { 
				if(interfaceA.getGame().getCurrentTurn())
					currentPlayerTurn = 1;
				else
					currentPlayerTurn = 2;
				
				//Make Human player move
				if(TTTDriver.isCounting) {
					boolean isChoiceValid = interfaceA.setSelection(0, 0, currentPlayerTurn);
				}
				else {
					interfaceA.getGame().randomizeMove();
					if(interfaceA.getGame().getDoesTimerMatter())
						TTTDriver.timer.cancel();
				}
				
				//Update Game board
				root.setLeft(buildLeftPane());
				//Check winner
				if(interfaceA.determineWinner() != 0) {
					root.setLeft(buildLeftPane());
					root.setRight(buildRightPane()); //To prevent further additions to the board
				}else {
					if(interfaceA.getGame().getCurrentTurn())
						currentPlayerTurn = 1;
					else
						currentPlayerTurn = 2;
					//Check if next turn is AI
					if(numPlayers == 1 && currentPlayerTurn == 2) {
						interfaceA.getGame().makeMove();
						//Update Game board
						root.setLeft(buildLeftPane());
					}
				}
				if(interfaceA.determineWinner() != 0) {
					root.setLeft(buildLeftPane());
					root.setRight(buildRightPane()); //To prevent further additions to the board
				}else
					root.setRight(buildGamePane());
	        } 
		};

		EventHandler<MouseEvent> b2_handler = new EventHandler<MouseEvent>() { 
			@Override 
	        public void handle(MouseEvent e) { 
				if(interfaceA.getGame().getCurrentTurn())
					currentPlayerTurn = 1;
				else
					currentPlayerTurn = 2;
				
				//Make Human player move
				if(TTTDriver.isCounting) {
					boolean isChoiceValid = interfaceA.setSelection(0, 1, currentPlayerTurn);
				}
				else {
					interfaceA.getGame().randomizeMove();
					if(interfaceA.getGame().getDoesTimerMatter())
						TTTDriver.timer.cancel();
				}
				
				//Update Game board
				root.setLeft(buildLeftPane());
				//Check winner
				if(interfaceA.determineWinner() != 0) {
					root.setLeft(buildLeftPane());
					root.setRight(buildRightPane()); //To prevent further additions to the board
				}else {
					if(interfaceA.getGame().getCurrentTurn())
						currentPlayerTurn = 1;
					else
						currentPlayerTurn = 2;
					//Check if next turn is AI
					if(numPlayers == 1 && currentPlayerTurn == 2) {
						interfaceA.getGame().makeMove();
						//Update Game board
						root.setLeft(buildLeftPane());
					}
				}
				if(interfaceA.determineWinner() != 0) {
					root.setLeft(buildLeftPane());
					root.setRight(buildRightPane()); //To prevent further additions to the board
				}else
					root.setRight(buildGamePane());
	        } 
		};
		
		EventHandler<MouseEvent> b3_handler = new EventHandler<MouseEvent>() { 
			@Override 
	        public void handle(MouseEvent e) { 
				if(interfaceA.getGame().getCurrentTurn())
					currentPlayerTurn = 1;
				else
					currentPlayerTurn = 2;
				
				//Make Human player move
				if(TTTDriver.isCounting) {
					boolean isChoiceValid = interfaceA.setSelection(0, 2, currentPlayerTurn);
				}
				else {
					interfaceA.getGame().randomizeMove();
					if(interfaceA.getGame().getDoesTimerMatter())
						TTTDriver.timer.cancel();
				}
				
				//Update Game board
				root.setLeft(buildLeftPane());
				//Check winner
				if(interfaceA.determineWinner() != 0) {
					root.setLeft(buildLeftPane());
					root.setRight(buildRightPane()); //To prevent further additions to the board
				}else {
					if(interfaceA.getGame().getCurrentTurn())
						currentPlayerTurn = 1;
					else
						currentPlayerTurn = 2;
					//Check if next turn is AI
					if(numPlayers == 1 && currentPlayerTurn == 2) {
						interfaceA.getGame().makeMove();
						//Update Game board
						root.setLeft(buildLeftPane());
					}
				}
				if(interfaceA.determineWinner() != 0) {
					root.setLeft(buildLeftPane());
					root.setRight(buildRightPane()); //To prevent further additions to the board
				}else
					root.setRight(buildGamePane());
	        } 
		};
		
		EventHandler<MouseEvent> b4_handler = new EventHandler<MouseEvent>() { 
			@Override 
	        public void handle(MouseEvent e) { 
				if(interfaceA.getGame().getCurrentTurn())
					currentPlayerTurn = 1;
				else
					currentPlayerTurn = 2;
				
				//Make Human player move
				if(TTTDriver.isCounting) {
					boolean isChoiceValid = interfaceA.setSelection(1, 0, currentPlayerTurn);
				}
				else {
					interfaceA.getGame().randomizeMove();
					if(interfaceA.getGame().getDoesTimerMatter())
						TTTDriver.timer.cancel();
				}
				
				//Update Game board
				root.setLeft(buildLeftPane());
				//Check winner
				if(interfaceA.determineWinner() != 0) {
					root.setLeft(buildLeftPane());
					root.setRight(buildRightPane()); //To prevent further additions to the board
				}else {
					if(interfaceA.getGame().getCurrentTurn())
						currentPlayerTurn = 1;
					else
						currentPlayerTurn = 2;
					//Check if next turn is AI
					if(numPlayers == 1 && currentPlayerTurn == 2) {
						interfaceA.getGame().makeMove();
						//Update Game board
						root.setLeft(buildLeftPane());
					}
				}
				if(interfaceA.determineWinner() != 0) {
					root.setLeft(buildLeftPane());
					root.setRight(buildRightPane()); //To prevent further additions to the board
				}else
					root.setRight(buildGamePane());
	        } 
		};
		
		EventHandler<MouseEvent> b5_handler = new EventHandler<MouseEvent>() { 
			@Override 
	        public void handle(MouseEvent e) { 
				if(interfaceA.getGame().getCurrentTurn())
					currentPlayerTurn = 1;
				else
					currentPlayerTurn = 2;
				
				//Make Human player move
				if(TTTDriver.isCounting) {
					boolean isChoiceValid = interfaceA.setSelection(1, 1, currentPlayerTurn);
				}
				else {
					interfaceA.getGame().randomizeMove();
					if(interfaceA.getGame().getDoesTimerMatter())
						TTTDriver.timer.cancel();
				}
				
				//Update Game board
				root.setLeft(buildLeftPane());
				//Check winner
				if(interfaceA.determineWinner() != 0) {
					root.setLeft(buildLeftPane());
					root.setRight(buildRightPane()); //To prevent further additions to the board
				}else {
					if(interfaceA.getGame().getCurrentTurn())
						currentPlayerTurn = 1;
					else
						currentPlayerTurn = 2;
					//Check if next turn is AI
					if(numPlayers == 1 && currentPlayerTurn == 2) {
						interfaceA.getGame().makeMove();
						//Update Game board
						root.setLeft(buildLeftPane());
					}
				}
				if(interfaceA.determineWinner() != 0) {
					root.setLeft(buildLeftPane());
					root.setRight(buildRightPane()); //To prevent further additions to the board
				}else
					root.setRight(buildGamePane());
	        } 
		};
		
		EventHandler<MouseEvent> b6_handler = new EventHandler<MouseEvent>() { 
			@Override 
	        public void handle(MouseEvent e) { 
				if(interfaceA.getGame().getCurrentTurn())
					currentPlayerTurn = 1;
				else
					currentPlayerTurn = 2;
				
				//Make Human player move
				if(TTTDriver.isCounting) {
					boolean isChoiceValid = interfaceA.setSelection(1, 2, currentPlayerTurn);
				}
				else {
					interfaceA.getGame().randomizeMove();
					if(interfaceA.getGame().getDoesTimerMatter())
						TTTDriver.timer.cancel();
				}
				
				//Update Game board
				root.setLeft(buildLeftPane());
				//Check winner
				if(interfaceA.determineWinner() != 0) {
					root.setLeft(buildLeftPane());
					root.setRight(buildRightPane()); //To prevent further additions to the board
				}else {
					if(interfaceA.getGame().getCurrentTurn())
						currentPlayerTurn = 1;
					else
						currentPlayerTurn = 2;
					//Check if next turn is AI
					if(numPlayers == 1 && currentPlayerTurn == 2) {
						interfaceA.getGame().makeMove();
						//Update Game board
						root.setLeft(buildLeftPane());
					}
				}
				if(interfaceA.determineWinner() != 0) {
					root.setLeft(buildLeftPane());
					root.setRight(buildRightPane()); //To prevent further additions to the board
				}else
					root.setRight(buildGamePane());
	        } 
		};
		
		EventHandler<MouseEvent> b7_handler = new EventHandler<MouseEvent>() { 
			@Override 
	        public void handle(MouseEvent e) { 
				if(interfaceA.getGame().getCurrentTurn())
					currentPlayerTurn = 1;
				else
					currentPlayerTurn = 2;
				
				//Make Human player move
				if(TTTDriver.isCounting) {
					boolean isChoiceValid = interfaceA.setSelection(2, 0, currentPlayerTurn);
				}
				else {
					interfaceA.getGame().randomizeMove();
					if(interfaceA.getGame().getDoesTimerMatter())
						TTTDriver.timer.cancel();
				}
				
				//Update Game board
				root.setLeft(buildLeftPane());
				//Check winner
				if(interfaceA.determineWinner() != 0) {
					root.setLeft(buildLeftPane());
					root.setRight(buildRightPane()); //To prevent further additions to the board
				}else {
					if(interfaceA.getGame().getCurrentTurn())
						currentPlayerTurn = 1;
					else
						currentPlayerTurn = 2;
					//Check if next turn is AI
					if(numPlayers == 1 && currentPlayerTurn == 2) {
						interfaceA.getGame().makeMove();
						//Update Game board
						root.setLeft(buildLeftPane());
					}
				}
				if(interfaceA.determineWinner() != 0) {
					root.setLeft(buildLeftPane());
					root.setRight(buildRightPane()); //To prevent further additions to the board
				}else
					root.setRight(buildGamePane());
	        } 
		};
		
		EventHandler<MouseEvent> b8_handler = new EventHandler<MouseEvent>() { 
			@Override 
	        public void handle(MouseEvent e) { 
				if(interfaceA.getGame().getCurrentTurn())
					currentPlayerTurn = 1;
				else
					currentPlayerTurn = 2;
				
				//Make Human player move
				if(TTTDriver.isCounting) {
					boolean isChoiceValid = interfaceA.setSelection(2, 1, currentPlayerTurn);
				}
				else {
					interfaceA.getGame().randomizeMove();
					if(interfaceA.getGame().getDoesTimerMatter())
						TTTDriver.timer.cancel();
				}
				
				//Update Game board
				root.setLeft(buildLeftPane());
				//Check winner
				if(interfaceA.determineWinner() != 0) {
					root.setLeft(buildLeftPane());
					root.setRight(buildRightPane()); //To prevent further additions to the board
				}else {
					if(interfaceA.getGame().getCurrentTurn())
						currentPlayerTurn = 1;
					else
						currentPlayerTurn = 2;
					//Check if next turn is AI
					if(numPlayers == 1 && currentPlayerTurn == 2) {
						interfaceA.getGame().makeMove();
						//Update Game board
						root.setLeft(buildLeftPane());
					}
				}
				if(interfaceA.determineWinner() != 0) {
					root.setLeft(buildLeftPane());
					root.setRight(buildRightPane()); //To prevent further additions to the board
				}else
					root.setRight(buildGamePane());
	        } 
		};
		
		EventHandler<MouseEvent> b9_handler = new EventHandler<MouseEvent>() { 
			@Override 
	        public void handle(MouseEvent e) { 
				if(interfaceA.getGame().getCurrentTurn())
					currentPlayerTurn = 1;
				else
					currentPlayerTurn = 2;
				
				//Make Human player move
				if(TTTDriver.isCounting) {
					boolean isChoiceValid = interfaceA.setSelection(2, 2, currentPlayerTurn);
				}
				else {
					interfaceA.getGame().randomizeMove();
					if(interfaceA.getGame().getDoesTimerMatter())
						TTTDriver.timer.cancel();
				}
				
				//Update Game board
				root.setLeft(buildLeftPane());
				//Check winner
				if(interfaceA.determineWinner() != 0) {
					root.setLeft(buildLeftPane());
					root.setRight(buildRightPane()); //To prevent further additions to the board
				}else {
					if(interfaceA.getGame().getCurrentTurn())
						currentPlayerTurn = 1;
					else
						currentPlayerTurn = 2;
					//Check if next turn is AI
					if(numPlayers == 1 && currentPlayerTurn == 2) {
						interfaceA.getGame().makeMove();
						//Update Game board
						root.setLeft(buildLeftPane());
					}
				}
				if(interfaceA.determineWinner() != 0) {
					root.setLeft(buildLeftPane());
					root.setRight(buildRightPane()); //To prevent further additions to the board
				}else
					root.setRight(buildGamePane());
	        } 
		};
		// Attack EventHandlers
		b1.addEventFilter(MouseEvent.MOUSE_CLICKED, b1_handler);
		b2.addEventFilter(MouseEvent.MOUSE_CLICKED, b2_handler);
		b3.addEventFilter(MouseEvent.MOUSE_CLICKED, b3_handler);
		b4.addEventFilter(MouseEvent.MOUSE_CLICKED, b4_handler);
		b5.addEventFilter(MouseEvent.MOUSE_CLICKED, b5_handler);
		b6.addEventFilter(MouseEvent.MOUSE_CLICKED, b6_handler);
		b7.addEventFilter(MouseEvent.MOUSE_CLICKED, b7_handler);
		b8.addEventFilter(MouseEvent.MOUSE_CLICKED, b8_handler);
		b9.addEventFilter(MouseEvent.MOUSE_CLICKED, b9_handler);
		
		// Set content for GridPane
		pane.add(promptText, 1, 1, 3, 1);
		pane.add(b1, 1, 2);
		pane.add(b2, 2, 2);
		pane.add(b3, 3, 2);
		pane.add(b4, 1, 3);
		pane.add(b5, 2, 3);
		pane.add(b6, 3, 3);
		pane.add(b7, 1, 4);
		pane.add(b8, 2, 4);
		pane.add(b9, 3, 4);
		
		// Set content for ScrollPane
        sp.setContent(pane);
 
        // Always show vertical scroll bar
        sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        
        // Horizontal scroll bar is only displayed when needed
        sp.setHbarPolicy(ScrollBarPolicy.ALWAYS);
        
        return sp;
	}
	//Done
	
	//Placeholder Done
	public GridPane buildRightPane() {
		GridPane rightPane = new GridPane();
		//Setting Dimensions
		rightPane.setMaxSize(windowWidth/4, (int) windowHeight/2);
		rightPane.setPadding(new Insets(10, 10, 10, 10));
		rightPane.setVgap(5); 
		rightPane.setHgap(15); 
		rightPane.setAlignment(Pos.CENTER);
		
		
		return rightPane;
	}
	
	public ScrollPane buildPlayerPane(int pNum) {
		GridPane pane = new GridPane();
		//Setting Dimensions
		pane.setMaxSize(windowWidth/4, (int) windowHeight/2);
		pane.setPadding(new Insets(5, 5, 5, 5));
		pane.setVgap(5); 
		pane.setHgap(5);       
		pane.setAlignment(Pos.TOP_LEFT);
		
		List <TTTPlayer> objects = new ArrayList<TTTPlayer>();
		objects = getList();
        
        Text importText = new Text("Username: ");
        TextField importField = new TextField();
        importText.getStyleClass().add("tableHeader");
        importField.getStyleClass().add("table");
        
        Button importButton = new Button("Import");
        
        EventHandler<MouseEvent> import_handler = new EventHandler<MouseEvent>() { 
			@Override 
	        public void handle(MouseEvent e) { 
				//Check if User exists
				if(doesUserExist(importField.getText())) {
					TTTPlayer p;
					List <TTTPlayer> objList = getList();
					int i=0;
					int j=0;
					for(i=0;i<objList.size();i++ ) {
						if((objList.get(i).getName()).equals(importField.getText()))
							j=i;
					}
					p = objList.get(j);
					
					if(pNum == 1) {
						p1Username = p.getName();
						p1marker = p.getMarker();
						
					}else {
						p2Username = p.getName();
						p2marker = p.getMarker();
					}
					//Rebuild Options Pane
					root.setLeft(buildOptionPane(pNum));
				}
	        } 
	        
		};
		
		importButton.addEventHandler(MouseEvent.MOUSE_CLICKED, import_handler);
        
        Line line = new Line();
        line.setStartX((float) windowWidth*3/4); 
        line.setStartY(0.0f);         
        line.setEndX((float) windowWidth); 
        line.setEndY(0.0f);
        Line line2 = new Line();
        line2.setStartX((float) windowWidth*3/4); 
        line2.setStartY(0.0f);         
        line2.setEndX((float) windowWidth); 
        line2.setEndY(0.0f);
        
        Text pName = new Text("Username");
        Text pMarker = new Text("Marker");
        Text pWin = new Text("Win");
        Text pLost = new Text("Lost");
        pName.getStyleClass().add("tableHeader");
        pMarker.getStyleClass().add("tableHeader");
        pWin.getStyleClass().add("tableHeader");
        pLost.getStyleClass().add("tableHeader");
        
        pane.add(importText, 1, 1);
        pane.add(importField, 2, 1);
        pane.add(importButton, 3, 1, 2, 1);
        pane.add(line, 1, 2, 4, 1);
        pane.add(pName, 1, 3);
        pane.add(pMarker, 2, 3);
        pane.add(pWin, 3, 3);
        pane.add(pLost, 4, 3);
        pane.add(line2, 1, 4, 4, 1);
        
        //Show all objects
        for (int i = 0; i < objects.size(); i++) {
			Text tUser = new Text(objects.get(i).getName());
			Text tMarker = new Text(objects.get(i).getMarker());
			Text tWin = new Text(Integer.toString(objects.get(i).getRecordWin()));
			Text tLost = new Text(Integer.toString(objects.get(i).getRecordLost()));
			pane.add(tUser, 1, 5+i);
			pane.add(tMarker, 2, 5+i);
			pane.add(tWin, 3, 5+i);
			pane.add(tLost, 4, 5+i);
		}
        
        ScrollPane sp = new ScrollPane();
        sp.setMaxWidth(windowWidth/4);
		// Set content for ScrollPane
        sp.setContent(pane);
 
        // Always show vertical scroll bar
        sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        
        // Horizontal scroll bar is only displayed when needed
        sp.setHbarPolicy(ScrollBarPolicy.ALWAYS);
        
        return sp;
	}

	//Done
	public ScrollPane buildEmojiPane(int pNum) {
		GridPane pane = new GridPane();
		//Setting Dimensions
		pane.setMaxSize(windowWidth/4, (int) windowHeight/2);
		pane.setPadding(new Insets(5, 5, 5, 5));
		pane.setVgap(5); 
		pane.setHgap(5);       
		pane.setAlignment(Pos.TOP_LEFT);
		
		//Initialize Elements
		String str  = "Select Emoji";
		Text textPrompt = new Text(str);
		textPrompt.getStyleClass().add("tableHeader");
		
		RadioButton r1 = new RadioButton();
		RadioButton r2 = new RadioButton();
		RadioButton r3 = new RadioButton();
		ToggleGroup rGroup = new ToggleGroup();
		r1.setToggleGroup(rGroup); r2.setToggleGroup(rGroup); r3.setToggleGroup(rGroup);
		r1.setSelected(true);
		if(pNum == 2)
			r3.setSelected(true);
		
		Image eSleepImg = new Image("/emoji_sleep.png");
		Image eSmileImg = new Image("/emoji_smile.png");
		Image eThinkImg = new Image("/emoji_think.png");
		ImageView eSleepImgView = new ImageView(eSleepImg);
		ImageView eSmileImgView = new ImageView(eSmileImg);
		ImageView eThinkImgView = new ImageView(eThinkImg);
		//Setting up imageView(s)
		eSleepImgView.setX(0); eSmileImgView.setX(0); eThinkImgView.setX(0);
		eSleepImgView.setY(0); eSmileImgView.setY(0); eThinkImgView.setY(0);
		eSleepImgView.setFitWidth(20); eSmileImgView.setFitWidth(20); eThinkImgView.setFitWidth(20);
		eSleepImgView.setFitHeight(20);eSmileImgView.setFitHeight(20); eThinkImgView.setFitWidth(20);
		eSleepImgView.setPreserveRatio(true); eSmileImgView.setPreserveRatio(true); eThinkImgView.setPreserveRatio(true);
        
		//Event Handlers
        EventHandler<MouseEvent> eSleep_handler = new EventHandler<MouseEvent>() { 
			@Override 
	        public void handle(MouseEvent e) { 
				if(pNum == 1)
					emojiType = "sleep";
				else
					emojiType2= "sleep";
	        } 
		};
		
		EventHandler<MouseEvent> eSmile_handler = new EventHandler<MouseEvent>() { 
			@Override 
	        public void handle(MouseEvent e) { 
				if(pNum == 1)
					emojiType = "smile";
				else
					emojiType2= "smile";
	        } 
		};

		EventHandler<MouseEvent> eThink_handler = new EventHandler<MouseEvent>() { 
			@Override 
	        public void handle(MouseEvent e) { 
				if(pNum == 1)
					emojiType = "think";
				else
					emojiType2= "think";
	        } 
		};
		
		r1.addEventHandler(MouseEvent.MOUSE_CLICKED, eSleep_handler);	
		r2.addEventHandler(MouseEvent.MOUSE_CLICKED, eSmile_handler);	
		r3.addEventHandler(MouseEvent.MOUSE_CLICKED, eThink_handler);

        
        
        //Show all objects
        pane.add(textPrompt, 1, 1, 3, 1);
        pane.add(r1, 1, 2);	pane.add(eSleepImgView, 2, 2);
        pane.add(r2, 1, 3);	pane.add(eSmileImgView, 2, 3);
        pane.add(r3, 1, 4);	pane.add(eThinkImgView, 2, 4);
        
        ScrollPane sp = new ScrollPane();
        sp.setMaxWidth(windowWidth/4);
		// Set content for ScrollPane
        sp.setContent(pane);
 
        // Always show vertical scroll bar
        sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        
        // Horizontal scroll bar is only displayed when needed
        sp.setHbarPolicy(ScrollBarPolicy.ALWAYS);
        
        return sp;
	}
	
	//Done
	public GridPane buildHelpPane() {
		GridPane botPane = new GridPane();
		//Setting Dimensions
		botPane.setMinSize(windowWidth, (int) windowHeight/4);
		botPane.setPadding(new Insets(10, 10, 10, 10));
		botPane.setVgap(5); 
		botPane.setHgap(15);       
		botPane.setAlignment(Pos.TOP_LEFT);
		
		Text title1 = new Text("Rules: ");
		title1.setId("help");
		Text rules = new Text("- each player takes turns choosing a spot on the board to mark.\n"
				+ "- each spot chosen must be an unclaimed spot.\n"
				+ "- each turn has a set amount of time for the player to make their move.\n"
				+ "\t- in the case the time runs out, a random, valid move is chosen.\n"
				+ "- if either player matches 3 of their marker in-a-row, they win.\n"
				+ "\t- in the case their is no spots left to choose and neither player "
				+ "has won, the result is a tie.");
		Text title2 = new Text("Territory Game: ");
		title2.setId("help");
		Text rules2 = new Text("- board is now 5x5 instead of 3x3.\n"
				+ "- each player will be give a choice to place a NEUTRAL and UNPICKABLE spot.\n"
				+ "\t- NEUTRAL spots count toward both players.\n"
				+ "\t- UNPICKABLE spots count toward neither players.");
		
		
		botPane.add(title1, 1, 1);
		botPane.add(rules, 1, 2);
		botPane.add(title2, 1, 3);
		botPane.add(rules2, 1, 4);
		return botPane;
	}
	
	//Done
	public void createFile() {
		try {
			FileOutputStream fos = new FileOutputStream("users.ser");
			ObjectOutputStream out = new ObjectOutputStream(fos);
			List<TTTPlayer> objects = new ArrayList<TTTPlayer>();
			out.writeObject(objects);
	        out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	//Done
	public void saveFile(TTTPlayer p) {
		//Get List of Objects
		List <TTTPlayer> objects = new ArrayList<TTTPlayer>();
		objects = getList();
        
		//Check if TTTPlayer p already exists, update if necessary
        if(doesUserExist(p.getName())) {
        	int i=0;
            for(i=0;i<objects.size();i++) {
            	if(objects.get(i).getName().equals(p.getName())) {
            		objects.set(i, p);
            		//System.out.println(p.getName() + " does exist");
            	}
            }
        }else {
        	objects.add(p);
        	//System.out.println(p.getName() + " added");
        }
		
		//Save objects to file
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(fos);
	        
			out.writeObject(objects);
	        out.close();
	        fos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	//Done
	public boolean doesUserExist(String user) {
		boolean doesExist = false;
		List <TTTPlayer> objects = getList();
		int i=0;
        for(i=0;i<objects.size();i++) {
        	if(objects.get(i).getName().equals(user))
        		doesExist = true;
        }
        //System.out.println("User: " + p.getName());
		return doesExist;
	}
	
	//Helper method
	public List<TTTPlayer> getList(){
		FileInputStream fis = null;
        ObjectInputStream in = null;
        List <TTTPlayer> objects = new ArrayList<TTTPlayer>();
        try {
            fis = new FileInputStream(fileName);
            in = new ObjectInputStream(fis);
            objects = (ArrayList<TTTPlayer>) in.readObject();
            in.close();
            fis.close();
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        return objects;
	}
	
	public String determineImageURL(String s) {
		String marker = "";
		if(s.equals("X"))
			marker = "/x_icon.png";
		else if(s.equals("O"))
			marker = "/o_icon.jpg";
		else if(s.contains("emoji"))
			marker = "/" + s + ".png";
		
		return marker;
	}
	
	public void gameLogic() {
		
	}
}
