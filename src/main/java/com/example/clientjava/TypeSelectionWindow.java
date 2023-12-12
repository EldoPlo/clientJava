package com.example.clientjava;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class TypeSelectionWindow extends Application {

    private Stage primarystage;
    private ChoiceBox<String> typeChoiceBox;

    @Override
    public void start(Stage primaryStage) {
        this.primarystage = primaryStage;
        typeChoiceBox = new ChoiceBox<>();
        typeChoiceBox.getItems().addAll("Integer", "Double", "String");
        typeChoiceBox.setValue("Integer");

        Button nextButton = new Button("Next");
        nextButton.setOnAction(event -> {
            try {
                handleNext(primaryStage);

            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(typeChoiceBox, nextButton);

        Scene scene = new Scene(vbox, 300, 200);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Tree Type Selection");
        primaryStage.show();
    }

    private void handleNext(Stage primaryStage) throws IOException, ClassNotFoundException {
        String selectedType = typeChoiceBox.getValue();
        MethodSelectionWindow methodSelectionWindow;
        primaryStage.close();
        methodSelectionWindow = new MethodSelectionWindow(selectedType);
        methodSelectionWindow.show(primaryStage);
    }
}