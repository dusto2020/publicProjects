package TicTacToe;

import ExternClasses.T;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Arrays;

import static TicTacToe.program.checkWinner;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    static String player = "X";
    Label message = new Label("X has to play!");
    public String[][] board = new String[3][3];
    String winner = "";
    BorderPane borderPane = new BorderPane();
    GridPane pane = new GridPane();
    String style = "-fx-border-style: solid; " +
            "-fx-border-width: 2px;-fx-border-color: black; -fx-border-radius: 5px; -fx-padding: 5px;";
    Image img = new Image(getClass().getResourceAsStream("img/tictactoe.png"), 300, 300, false, false);
    String mode = "";
    boolean compStarts = false;

    @Override
    public void start(Stage stage) {

        HBox hBox = new HBox();

        Label topLabel = new Label("Let's Play some Tic Tac Toe!");
        pane.setAlignment(Pos.CENTER);

        BorderPane.setMargin(topLabel, new Insets(12, 12, 12, 12));
        BorderPane.setAlignment(hBox, Pos.CENTER);
        BorderPane.setAlignment(topLabel, Pos.CENTER);

        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(15);

        borderPane.setTop(topLabel);
        BorderPane.setMargin(hBox, new Insets(12, 12, 12, 12));
        borderPane.setBottom(hBox);
        borderPane.setStyle("-fx-font-size: 20px;");

        changeMode(hBox);

        Scene scene = new Scene(borderPane, 500, 500);
        stage.setResizable(false);
        stage.getIcons().add(img);
        stage.setTitle("Tic Tac Toe");
        stage.setScene(scene);
        stage.show();
    }

    public void changeMode(HBox hBox) {
        Button pvp = new Button("Player vs Player");
        Button pvc = new Button("Player vs Computer");
        ImageView view = new ImageView(img);
        hBox.getChildren().clear();
        borderPane.setCenter(view);
        compStarts = false;
        pvp.setOnAction(e -> {
            mode = "pvp";
            borderPane.setCenter(pane);
            newGame(hBox);
        });

        pvc.setOnAction(e -> {
            mode = "pvc";
            chooseStartP(hBox);
        });

        hBox.getChildren().addAll(pvp, pvc);
    }

    public void chooseStartP(HBox hBox) {
        Button player = new Button("Player");
        Button computer = new Button("Computer");
        Label l = new Label("Choose who has to start");
        borderPane.setCenter(l);
        hBox.getChildren().clear();
        player.setOnAction(e -> {
            borderPane.setCenter(pane);
            newGame(hBox);
        });
        computer.setOnAction(e -> {
            borderPane.setCenter(pane);
            compStarts = true;
            newGame(hBox);
        });
        hBox.getChildren().addAll(player, computer);
    }

    public void newGame(HBox hBox) {
        player = "X";
        for (String[] row : board)
            Arrays.fill(row, " ");
        if (compStarts)
            computerMove();
        message.setText(player + " has to play!");
        winner = "";
        hBox.getChildren().clear();
        Button playAgain = new Button("New Game");
        Button backToScreen = new Button("Change Mode");
        backToScreen.setOnAction(e -> changeMode(hBox));
        playAgain.setOnAction(e -> newGame(hBox));
        hBox.getChildren().addAll(backToScreen, message, playAgain);
        message.setStyle(style);
        playAgain.setStyle(style);
        startGame(pane);
    }

    public void startGame(GridPane pane) {
        pane.getChildren().clear();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Tile tile = new Tile(i, j);
                pane.add(tile, i, j);
            }
        }
    }

    public void computerMove() {
        T<Integer, Integer> move = program.aiMove(board, player);
        board[move.getZeile()][move.getSpalte()] = player;
        player = player.equals("X") ? "O" : "X";
    }

    private class Tile extends StackPane {

        public Tile(int i, int j) {
            Rectangle rec = new Rectangle(100, 100, Color.WHITE);
            rec.setStroke(Color.BLACK);
            rec.setStrokeWidth(2);
            setAlignment(Pos.CENTER);
            Text t = new Text();
            t.setText(board[i][j]);
            t.setStyle("-fx-font-size: 75px");
            setOnMouseClicked(e -> {
                if (t.getText().equals(" ") && winner.equals("")) {
                    board[i][j] = player;
                    winner = checkWinner(board);
                    if ("".equals(winner)) {
                        player = player.equals("X") ? "O" : "X";
                        message.setText(player + " has to play!");
                        if ("pvc".equals(mode)) {
                            computerMove();
                            winner = checkWinner(board);
                            if (winner.equals("")) {
                                message.setText(player + " has to play!");
                            } else message.setText(winner);
                        }
                    } else message.setText(winner);

                }
                startGame(pane);
            });
            getChildren().addAll(rec, t);
        }
    }
}

