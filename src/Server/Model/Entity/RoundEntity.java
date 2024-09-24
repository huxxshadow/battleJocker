package Server.Model.Entity;

import Server.Controller.PlayerController;

import java.util.ArrayList;

public class RoundEntity {

    public boolean isStarted = false;
    public static final int SIZE = 4;
    int[] board = new int[SIZE * SIZE];

    private int level = 1;

    private boolean gameOver;
    private int score;
    private int combo;
    private int totalMoveCount;
    private int numOfTilesMoved;

    public ArrayList<PlayerController> players = new ArrayList<>();


    public int[] getBoard() {
        return board;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCombo() {
        return combo;
    }

    public void setCombo(int combo) {
        this.combo = combo;
    }

    public int getTotalMoveCount() {
        return totalMoveCount;
    }

    public void setTotalMoveCount(int totalMoveCount) {
        this.totalMoveCount = totalMoveCount;
    }

    public int getNumOfTilesMoved() {
        return numOfTilesMoved;
    }

    public void setNumOfTilesMoved(int numOfTilesMoved) {
        this.numOfTilesMoved = numOfTilesMoved;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void setBoard(int[] board) {
        this.board = board;
    }
}
