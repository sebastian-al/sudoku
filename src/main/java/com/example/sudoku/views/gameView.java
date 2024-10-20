package com.example.sudoku.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class gameView extends Stage {
    public gameView() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/sudoku/game-view.fxml")
        );
        Parent root = loader.load();
        this.setTitle("Sudoku");
        getIcons().add(
                new Image(
                        String.valueOf(getClass().getResource("/com/example/sudoku/images/favIcon.png"))));

        Scene scene = new Scene(root);
        this.setScene(scene);

        this.show();
    }

    public static gameView getInstance() throws IOException {
        if (gameViewHolder.INSTANCE == null) {
            return gameViewHolder.INSTANCE = new gameView();
        } else {
            return gameViewHolder.INSTANCE;
        }
    }

    private static class gameViewHolder {
        private static gameView INSTANCE;
    }
}