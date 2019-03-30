package course.oop.application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import course.oop.main.TTTDriver;
import course.oop.view.MainView;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			MainView mView = new MainView();
			Scene scene = mView.getMainScene();
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setTitle("Tic Tac Toe - Andrew Dang");
			
			
			BorderPane root = new BorderPane();
			//Scene scene = new Scene(root,400,400);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			//primaryStage.setTitle("2D Array Example");
			//TwoDArray twoDArr = new TwoDArray(5,8,-1); 
			//Text arrDetails = new Text(twoDArr.getArrayDetails()); 
			//root.setTop(arrDetails);
			//Text arrDisplay = new Text(twoDArr.getArrayDisplay());
			//root.setCenter(arrDisplay);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
