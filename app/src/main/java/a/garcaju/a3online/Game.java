package a.garcaju.a3online;

import java.util.Random;

public class Game {
    public final int dificulty;
    public int player;
    private int [] boxes;
    private final int[][] COMBINATIONS = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    public Game(int dificulty){
        this.dificulty = dificulty;
        player = 1;
        boxes = new int[9];

        for (int  i = 0 ; i < 9 ; i++) boxes[i] = 0;

    }

    public int twoOk (int playerPlaying){
        int box = -1;
        int howManyWins = 0;

        for (int i=0; i < COMBINATIONS.length ; i++){
            for (int pos: COMBINATIONS[i]) {
                if (boxes[pos] == playerPlaying) howManyWins++;
                if (boxes[pos] == 0) box = pos;
            }
            if (howManyWins == 2 && box != -1) return box;
            box = -1;
            howManyWins = 0;
        }
        return -1;
    }

    public int ai(){
        int box;

        //The machine or player2 may wins at next turn if player1 is get lost. Level Normal.
        box = twoOk(2);
        if (box != -1) return box;
        if (dificulty > 0){
            box = twoOk(1);
            if (box != -1) return box;
        }

        if (dificulty == 2){
            if (boxes[0] == 0) return 0;
            if (boxes[0] == 2) return 2;
            if (boxes[0] == 6) return 6;
            if (boxes[0] == 8) return 8;
        }


        //Machine choose a box ramdomly and don't look if player1 will win. Level Easy.
        Random boxRandom = new Random();
        box = boxRandom.nextInt(9);
        return box;
    }

    public int gameTurn(){

        boolean tie = true;
        boolean lastMov = true;

        for (int i=0; i < COMBINATIONS.length ; i++){
            for (int pos: COMBINATIONS[i]) {
                //System.out.println("Position value: " + pos + " " + boxes[pos]);
                if (boxes[pos] != player) lastMov = false;
                if (boxes[pos] == 0) tie = false;
            }
            //System.out.println("______________________");
            if (lastMov == true) return player;
            lastMov = true;
        }

        if (tie) return 3;
        player++;
        if (player>2) player = 1;

        return 0;
    }

    public boolean testBox(int box){
        if (boxes[box]!= 0) return false;
        else boxes[box] = player;

        return true;
    }
}
