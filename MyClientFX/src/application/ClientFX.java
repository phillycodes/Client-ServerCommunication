package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ClientFX extends Application {
	//IOStreams
	DataOutputStream toServer = null;
	DataInputStream fromServer = null;
	
	public void start(Stage primaryStage) {
		GridPane textPane = new GridPane();
		textPane.setPadding(new Insets(5,5,5,5));
		textPane.setStyle("-fx-border-color: green");
		textPane.setVgap(8);
		textPane.setHgap(10);
		
		// { forming the GridPane
		//interest Label
		Label interest = new Label("Annual Interest Rate");
		GridPane.setConstraints(interest, 0, 0);

		//interest TextField
		TextField interestField = new TextField();
		GridPane.setConstraints(interestField, 1, 0);
		
		//years label
		Label years = new Label("Number Of Years");
		GridPane.setConstraints(years, 0, 1); //setConstraints(column, row)

		//years textField
		TextField yearsField = new TextField();
		GridPane.setConstraints(yearsField, 1, 1);

		//amount label
		Label amount = new Label("Loan Amount");
		GridPane.setConstraints(amount, 0, 2);
		
		//amount TextField
		TextField amountField = new TextField();
		GridPane.setConstraints(amountField, 1, 2);
				
		// a button
		Button button = new Button("Submit");
		GridPane.setConstraints(button, 2, 1);
		textPane.getChildren().addAll(years, yearsField, amount, amountField, interest, interestField, button);
		// } end GridPane
		
		BorderPane mainPane = new BorderPane();
		
		//Text area to display
		TextArea area = new TextArea();
		mainPane.setCenter(new ScrollPane(area));
		mainPane.setTop(textPane);
		
		
		//Create the scene and put it on the stage
		Scene scene = new Scene(mainPane, 500, 350);
		primaryStage.setTitle("Client Chat"); // title for the stage
		primaryStage.setScene(scene); // put scene in stage
		primaryStage.show(); // display the stage
		
		//Button click action
		button.setOnAction(e -> {
			try {
			double serverInterest = Double.parseDouble(interestField.getText().trim());
			toServer.writeDouble(serverInterest);
			toServer.flush();
			int serverYears = Integer.parseInt(yearsField.getText().trim()); //get number of Years
			
			//send cubeNum to the server
			toServer.writeInt(serverYears);
			toServer.flush();
			double serverAmount = Double.parseDouble(amountField.getText().trim()); //get Loan Amount
			toServer.writeDouble(serverAmount);
			toServer.flush();
			
			//display on area
			area.appendText("Annual Interest Rate: " + serverInterest + "\n");
			area.appendText("Number of Years: " + serverYears + "\n");
			area.appendText("Loan Amount: " + serverAmount + "\n");
			
			}catch (IOException ex) {
				System.err.println(ex);
				System.out.println("IO ISSUE......");
			} // end catch
		});
		
		try {
			//create the socket to connect to the server
			Socket socket = new Socket("localhost", 8000);
			
			// create input stream to get data from the server
			fromServer = new DataInputStream(socket.getInputStream());
			
			//create output stream to send data to the server
			toServer = new DataOutputStream(socket.getOutputStream());
			} catch(IOException ex) {
				area.appendText(ex.toString() + "\n");
				System.out.println("Issue to and from server.\n");
			} // end catch
		}//end start method
	
	public static void main(String[] args) {
		launch(args);
	}//end main method

}//end class
