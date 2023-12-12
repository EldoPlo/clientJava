package com.example.clientjava;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import java.io.*;
import java.net.*;
public class MethodSelectionWindow {

    String selection;
    TextField commandTextField = new TextField();
    TextField valueTextField = new TextField();
    Client client;
    private class Client{
        Socket socket;
        BufferedReader input;
        PrintStream out;


        Client(String sel) throws IOException
        {
            try
            {
                socket = new Socket(InetAddress.getLocalHost().getHostName(), 9877);
                out = new PrintStream(socket.getOutputStream());
                displayAlert("Connected");
                out.println(sel);
                out.flush();
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                if (!input.readLine().equals("Success"))
                {
                    throw new IOException("Error");
                }
            }

            catch (ConnectException e)
            {
                displayAlert("Error");
            }
        }
        public void writeToServer(String s) throws IOException
        {
            try
            {
                if (out == null || input == null )
                {
                    displayAlert("NullPointer!!!");
                    return;
                }
                out.println(s);
                out.flush();

                String response = input.readLine();
                displayAlert(response);
                if(response.equals(""))
                {
                    closeConnection();
                    throw new IOException("Server Down");
                }
            }
            catch (SocketException e)
            {
                displayAlert("Error! Please reset the applications and make sure the server is on");
                Platform.exit();
            }
            catch(IOException  e)
            {
                displayAlert("Socket and Server Error");
                throw new IOException();
            }
        }

        public void closeConnection()
        {
            try
            {
                input.close();
                out.close();
                socket.close();
            }
            catch (IOException e) {
                displayAlert("Error");
            }
        }
    }

    public MethodSelectionWindow(String sel) throws IOException {
        this.selection = sel;
        client = new Client(sel);
    }

    public void show(Stage primaryStage) {

        commandTextField.setPromptText("Enter command");

        valueTextField.setPromptText("Enter value");

        Button handleButton = new Button("Handle2");
        handleButton.setOnAction(event ->
        {
            try
            {
                handleCommand(selection, commandTextField.getText().trim().toLowerCase(),
                        valueTextField.getText().trim(), valueTextField);
            }
            catch (IOException | InterruptedException | ClassNotFoundException e)
            {
                throw new RuntimeException(e);
            }
        });

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(commandTextField, valueTextField, handleButton);

        Scene scene = new Scene(vbox, 300, 200);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Method Selection");
        primaryStage.show();
    }

    public void handleCommand(String sel, String command, String value, TextField textField) throws IOException, ClassNotFoundException, InterruptedException,SocketException
    {
        String s = command + ";" + value;
        try
        {
            ToServer(s);
        }
        catch (IOException e)
        {
            displayAlert("Error! Please reset the applications and make sure the server is on");
            Platform.exit();
        }
    }

    public void ToServer(String s) throws  IOException{

        try
        {
            client.writeToServer(s);
        }
        catch (SocketException e)
        {
            displayAlert("Error! Please reset the applications and make sure the server is on");
            throw new SocketException();
        }
    }
    private void displayAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}