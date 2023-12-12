package com.example.clientjava;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;

public class MainApp extends Application {

    private ChoiceBox<String> typeChoiceBox;
    private Button nextButton;
    private Stage primaryStage;
    TextField valueTextField;


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        typeChoiceBox = new ChoiceBox<>();
        typeChoiceBox.getItems().addAll("Integer", "Double", "String");
        typeChoiceBox.setValue("Integer");

        nextButton = new Button("Next");
        nextButton.setOnAction(event -> {
            try {
                handleNext();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(typeChoiceBox, nextButton);

        Scene scene = new Scene(vbox, 300, 200);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Tree Type Selection");
        primaryStage.show();
    }
    private <T extends Comparable<T>> void showMethodSelectionWindow(String sel, TextField textField) throws IOException, ClassNotFoundException {
        TextField commandTextField = new TextField();
        commandTextField.setPromptText("Enter command");
        MethodSelectionWindow ms = new MethodSelectionWindow(sel);


        Button handleButton = new Button("Handle");
        handleButton.setOnAction(event ->
        {
            try
            {
                ms.handleCommand(sel, commandTextField.getText().trim().toLowerCase(), textField.getText().trim(), textField);
            }
            catch (IOException | ClassNotFoundException | InterruptedException e)
            {
                throw new RuntimeException();
            }
        });

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(commandTextField, textField, handleButton);

        Scene scene = new Scene(vbox, 300, 200);

        Stage methodSelectionStage = new Stage();
        methodSelectionStage.setScene(scene);
        methodSelectionStage.setTitle("Method Selection");
        methodSelectionStage.show();
    }


    private void handleNext() throws IOException, ClassNotFoundException {
        String selectedType = typeChoiceBox.getValue();
        valueTextField = new TextField();
        showMethodSelectionWindow(selectedType,valueTextField);
    }
    public static void main(String[] args) {
        launch(args);
    }
}