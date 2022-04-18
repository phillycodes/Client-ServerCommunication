package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class MyServer extends Application {
	public void start(Stage primaryStage) {
		TextArea text = new TextArea(); //where information is shown from the server
		
		
		// Creates the scene and put it on the stage
		Scene scene = new Scene(new ScrollPane(text));
		primaryStage.setTitle("Server Chat");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		new Thread(() -> {
			try {
			//create sever socket
			ServerSocket serverSocket = new ServerSocket(8000);
			Platform.runLater(() ->
				text.appendText("Started Server at " + new Date() + "\n\n"));
			
			//Listen for a connection request from a client
			Socket socket = serverSocket.accept();
			
			//Create data in and out streams
			DataInputStream clientInput = new DataInputStream(socket.getInputStream());
			DataOutputStream clientOutput = new DataOutputStream(socket.getOutputStream());
			
			while(true){ // I do not completely understand this part while "what" is true
				//receive the interest, number of years, and loan amount to place a constructor
				double interest = clientInput.readDouble();
				int numYears = clientInput.readInt();
				double loanAmount = clientInput.readDouble();
				
				//create loan object
				Loan l = new Loan(interest, numYears, loanAmount);
				
				//take the number from client and cube it
				double monthly = l.getMonthlyPayment();
				double totalPay = l.getTotalPayment();
				
				// send back the result
				clientOutput.writeInt(numYears);
				clientOutput.writeDouble(interest);
				clientOutput.writeDouble(loanAmount);
				
				Platform.runLater(() -> {
					text.appendText("Annual Interest Rate: " + interest + "\n");
					text.appendText("Number of Years: " + numYears + "\n");
					text.appendText("Loan Amount: " + loanAmount + "\n");
					text.appendText("Monthly Payment: " + monthly + "\n");
					text.appendText("Total Payment: " + totalPay + "\n");
					
				});// close platform run later
			}// end of infinite while loop
			}catch(IOException e) {
				e.printStackTrace();
			}// end catch
		}).start(); // end thread		
	}//end start method
	
	public static void main(String[] args) {
		launch(args);
	}// main method

}//end MyServer class
