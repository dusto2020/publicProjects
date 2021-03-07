package TicTacToe;

import ExternClasses.T;

import java.util.Arrays;
import java.util.HashMap;

public class program {

    public static String checkWinner(String[][] game) {
        if (!rowWinner(game).equals(""))
            return rowWinner(game);
        else if (!colWinner(game).equals(""))
            return colWinner(game);
        else if (!diagWinner(game).equals(""))
            return diagWinner(game);
        else if (Draw(game) == 9)
            return "Draw";
        return "";
    }

    private static String rowWinner(String[][] game) {
        for (int i = 0; i < 3; i++) {
            if (!game[i][0].equals(" ")) {
                if (game[i][0].equals(game[i][1]) && game[i][1].equals(game[i][2]))
                    return game[i][i] + " wins";
            }
        }
        return "";
    }

    private static String colWinner(String[][] game) {
        for (int i = 0; i < 3; i++) {
            if (!game[0][i].equals(" ")) {
                if (game[0][i].equals(game[1][i]) && game[1][i].equals(game[2][i]))
                    return game[i][i] + " wins";
            }
        }
        return "";
    }

    private static String diagWinner(String[][] game) {
        if (!game[0][0].equals(" ") && game[0][0].equals(game[1][1]) && game[1][1].equals(game[2][2]))
            return game[1][1] + " wins";
        else if (!game[0][2].equals(" ") && game[0][2].equals(game[1][1]) && game[1][1].equals(game[2][0]))
            return game[1][1] + " wins";
        return "";
    }

    private static int Draw(String[][] game) {
        return (int) Arrays.stream(game).flatMap(Arrays::stream).filter(tile -> tile.equals("O") || tile.equals("X")).count();
    }

    public static T<Integer, Integer> aiMove(String[][] board, String player) {
        int best = Integer.MIN_VALUE;
        T<Integer, Integer> move = new T<>(0, 0);
        String enemy = player.equals("X") ? "O" : "X";
        setScores(player, enemy);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].equals(" ")) {
                    board[i][j] = player;
                    int score = bestMove(board, false, enemy, player);
                    board[i][j] = " ";
                    if (score > best) {
                        best = score;
                        move.setZeile(i);
                        move.setSpalte(j);
                    }
                }
            }
        }
        return move;
    }

    static HashMap<String, Integer> scores = new HashMap<>();

    public static void setScores(String player, String enemy) {
        scores.put(player + " wins", 1);
        scores.put(enemy + " wins", -1);
        scores.put("Draw", 0);
    }

    public static int bestMove(String[][] board, boolean isMax, String enemy, String player) {
        String result = checkWinner(board);
        if (!result.equals("")) {
            return scores.get(result);
        }
        int bestScore;
        if (isMax) {
            bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j].equals(" ")) {
                        board[i][j] = player;
                        int score = bestMove(board, false, enemy, player);
                        board[i][j] = " ";
                        bestScore = Math.max(bestScore, score);
                    }
                }
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j].equals(" ")) {
                        board[i][j] = enemy;
                        int score = bestMove(board, true, enemy, player);
                        board[i][j] = " ";
                        bestScore = Math.min(bestScore, score);
                    }
                }
            }
        }
        return bestScore;
    }
}