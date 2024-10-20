package com.example.sudoku.controllers;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class gameController {
    private List<TextField> textFields = new ArrayList<>();
    private int[][] solution;
    private int remainingHelps = 3; // Número de ayudas disponibles

    @FXML
    private Label mostrarAyuda;

    @FXML
    private Button buttonForHelp;

    @FXML
    private TextField textField1, textField2, textField3, textField4, textField5, textField6;
    @FXML
    private TextField textField7, textField8, textField9, textField10, textField11, textField12;
    @FXML
    private TextField textField13, textField14, textField15, textField16, textField17, textField18;
    @FXML
    private TextField textField19, textField20, textField21, textField22, textField23, textField24;
    @FXML
    private TextField textField25, textField26, textField27, textField28, textField29, textField30;
    @FXML
    private TextField textField31, textField32, textField33, textField34, textField35, textField36;

    @FXML
    public void initialize() {
        textFields.add(textField1);
        textFields.add(textField2);
        textFields.add(textField3);
        textFields.add(textField4);
        textFields.add(textField5);
        textFields.add(textField6);
        textFields.add(textField7);
        textFields.add(textField8);
        textFields.add(textField9);
        textFields.add(textField10);
        textFields.add(textField11);
        textFields.add(textField12);
        textFields.add(textField13);
        textFields.add(textField14);
        textFields.add(textField15);
        textFields.add(textField16);
        textFields.add(textField17);
        textFields.add(textField18);
        textFields.add(textField19);
        textFields.add(textField20);
        textFields.add(textField21);
        textFields.add(textField22);
        textFields.add(textField23);
        textFields.add(textField24);
        textFields.add(textField25);
        textFields.add(textField26);
        textFields.add(textField27);
        textFields.add(textField28);
        textFields.add(textField29);
        textFields.add(textField30);
        textFields.add(textField31);
        textFields.add(textField32);
        textFields.add(textField33);
        textFields.add(textField34);
        textFields.add(textField35);
        textFields.add(textField36);

        // Event handler setup
        for (int i = 0; i < textFields.size(); i++) {
            int index = i;
            textFields.get(i).setOnKeyReleased(event -> handleInput(textFields.get(index), index));
        }

        // Inicializamos el label de ayudas
        mostrarAyuda.setText("Ayudas disponibles: " + remainingHelps);
    }

    @FXML
    void buttonToStart(ActionEvent event) {
        clearBoard();
        generateSudokuBoard();
        remainingHelps = 3; // Reiniciar las ayudas al comenzar un nuevo juego
        buttonForHelp.setDisable(false); // Rehabilitar el botón de ayuda si estaba deshabilitado
        mostrarAyuda.setText("Ayudas disponibles: " + remainingHelps);
    }

    private void clearBoard() {
        for (TextField textField : textFields) {
            textField.setText("");
            markAsValid(textField);
        }
    }

    private void generateSudokuBoard() {
        solution = generateValidSudoku();
        Random random = new Random();

        // For each block of 2x3, reveal 2 numbers
        for (int blockRow = 0; blockRow < 2; blockRow++) {
            for (int blockCol = 0; blockCol < 3; blockCol++) {
                HashSet<Integer> positions = new HashSet<>();
                while (positions.size() < 2) {
                    int pos = random.nextInt(6);
                    positions.add(pos + blockRow * 3 * 6 + blockCol * 2);
                }

                for (int index : positions) {
                    textFields.get(index).setText(Integer.toString(solution[index / 6][index % 6]));
                }
            }
        }
    }

    private int[][] generateValidSudoku() {
        int[][] board = new int[6][6];
        fillSudoku(board);
        return board;
    }

    private boolean fillSudoku(int[][] board) {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= 6; num++) {
                        if (isSafe(board, row, col, num)) {
                            board[row][col] = num;
                            if (fillSudoku(board)) {
                                return true;
                            }
                            board[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isSafe(int[][] board, int row, int col, int num) {
        for (int x = 0; x < 6; x++) {
            if (board[row][x] == num || board[x][col] == num) {
                return false;
            }
        }
        return true;
    }

    private void handleInput(TextField textField, int index) {
        String input = textField.getText();
        if (input.matches("[1-6]")) {
            int row = index / 6;
            int col = index % 6;
            if (!isCorrectNumber(row, col, Integer.parseInt(input))) {
                markAsInvalid(textField);
                showError("Incorrect number.");
            } else {
                markAsValid(textField);
            }
        } else {
            if (!input.isEmpty()) {
                markAsInvalid(textField);
                showError("Only numbers between 1 and 6 are allowed.");
                textField.setText("");
            } else {
                markAsValid(textField);
            }
        }
    }

    private boolean isCorrectNumber(int row, int col, int value) {
        for (int c = 0; c < 6; c++) {
            if (c != col && solution[row][c] == value) {
                return false;
            }
        }
        for (int r = 0; r < 6; r++) {
            if (r != row && solution[r][col] == value) {
                return false;
            }
        }
        return solution[row][col] == value;
    }

    private void markAsValid(TextField textField) {
        textField.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        textField.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, null)));
    }

    private void markAsInvalid(TextField textField) {
        textField.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Implementación del botón de ayuda con límite de 3
    @FXML
    void buttonForHelp(ActionEvent event) {
        if (remainingHelps > 0) {
            for (int i = 0; i < textFields.size(); i++) {
                if (textFields.get(i).getText().isEmpty()) {
                    int row = i / 6;
                    int col = i % 6;
                    textFields.get(i).setText(Integer.toString(solution[row][col]));
                    remainingHelps--; // Decrementar el número de ayudas disponibles
                    mostrarAyuda.setText("Ayudas disponibles: " + remainingHelps);
                    if (remainingHelps == 0) {
                        buttonForHelp.setDisable(true); // Deshabilitar el botón si no hay más ayudas
                    }
                    break;
                }
            }
        }
    }
}



















