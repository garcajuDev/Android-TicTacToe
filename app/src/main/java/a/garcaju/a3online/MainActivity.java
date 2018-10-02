package a.garcaju.a3online;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends Activity {

    private int players;
    private int[] BOXES;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initiate BOXES array wich indentificates every box and stores them in the array
        BOXES = new int[9];
        BOXES[0] = R.id.a1;
        BOXES[1] = R.id.a2;
        BOXES[2] = R.id.a3;
        BOXES[3] = R.id.b1;
        BOXES[4] = R.id.b2;
        BOXES[5] = R.id.b3;
        BOXES[6] = R.id.c1;
        BOXES[7] = R.id.c2;
        BOXES[8] = R.id.c3;
    }

    public void toPlay(View view) {
        //Resseting the table game
        ImageView image;
        for (int box : BOXES) {
            image = findViewById(box);
            image.setImageResource(R.drawable.casilla);
        }
        //Enabling play for one or two players
        players = 1;
        if (view.getId() == R.id.btnTwoPlayers) players = 2;

        //Establishing the level of dificulty
        RadioGroup configLevel = findViewById(R.id.radGroupLevel);
        int id = configLevel.getCheckedRadioButtonId();
        int dificulty = 0;
        if (id == R.id.normalRad) dificulty = 1;
        else if (id == R.id.godRad) dificulty = 2;

        game = new Game(dificulty);
        //Disabling the buttons when the game begins
        (findViewById(R.id.btnSingle)).setEnabled(false);
        (findViewById(R.id.radGroupLevel)).setAlpha(0);
        (findViewById(R.id.btnTwoPlayers)).setEnabled(false);
    }

    //touching the boxes
    public void touch(View view) {
        //Detecting touched box
        int box = 0;
        for (int i = 0; i < BOXES.length; i++) {
            if (BOXES[i] == view.getId()) {
                box = i;
                break;
            }
        }

        if (game == null) return;

        if(game.testBox(box) == false) return;
        mark(box);
        int result = game.gameTurn();
        if (result > 0) {gameEnded(result); return;}
        box = game.ai();
        while(game.testBox(box) != true)box = game.ai();
        mark(box);
        result = game.gameTurn();
        if (result > 0) gameEnded(result);
    }

    //End game method
    private void gameEnded(int result){
        String msg;

        if (result == 1) msg = "Crosses Wins!";
        else if (result == 2) msg = "Circles Wins!";
        else msg = "Tie!";

        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        game = null;
        ((Button) findViewById(R.id.btnSingle)).setEnabled(true);
        ((RadioGroup) findViewById(R.id.radGroupLevel)).setAlpha(1);
        ((Button) findViewById(R.id.btnTwoPlayers)).setEnabled(true);
    }

    //Marks the box
    private void mark(int box){
        ImageView image;
        image = findViewById(BOXES[box]);
        if (game.player == 1) image.setImageResource(R.drawable.aspa);
        else image.setImageResource(R.drawable.circulo);
    }
}