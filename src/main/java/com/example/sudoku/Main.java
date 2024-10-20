package com.example.sudoku;

import com.example.sudoku.views.gameView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        gameView.getInstance();
    }


    public static void main(String[] args) {
        launch();
    }
}