package com.example.jang3let.scarnesdice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //r stands for resource

    static int userOverallScore, userTurnScore, cpuOverallScore, cpuTurnScore;
    static final int GAME_POINT = 100;
    TextView mScoreTv;
    ImageView mDiceFaceIv;
    Button mRollBtn, mHoldBtn, mResetBtn;
    int[] diceFaces ={
            R.drawable.dice1,
            R.drawable.dice2,
            R.drawable.dice3,
            R.drawable.dice4,
            R.drawable.dice5,
            R.drawable.dice6
    };

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable(){
        @Override
        public void run() {
            timerHandler.postDelayed(this, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScoreTv = (TextView)findViewById(R.id.tv_score);
        mDiceFaceIv = (ImageView)findViewById(R.id.iv_dice);
        mRollBtn = (Button)findViewById(R.id.btn_roll);
        mHoldBtn = (Button)findViewById(R.id.btn_hold);
        mResetBtn = (Button)findViewById(R.id.btn_reset);

        mRollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickRoll();
            }
        });

        mResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickReset();
            }
        });

        mHoldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickHold();
            }
        });
    }

    void clickRoll(){
        int diceValue = rollDice();
        if(diceValue == 1) {
            userTurnScore = 0;
            updatePlayerLabel();
            clickHold();
        }else{
            userTurnScore += diceValue;
            updatePlayerLabel();
        }
    }

    void clickReset(){
        userOverallScore = userTurnScore = cpuOverallScore = cpuTurnScore = 0;
        updatePlayerLabel();
    }

    void clickHold(){
        userOverallScore += userTurnScore;
        userTurnScore = 0;
        updatePlayerLabel();
        if(userOverallScore >= GAME_POINT){
            mScoreTv.setText("You win!");
        }else {
            computerTurn();
        }

    }

    void updatePlayerLabel(){
        mScoreTv.setText("Your Score: " + userOverallScore +
        "\nYour Turn Score: " + userTurnScore);
    }

    void updateCpuLabel(int n){
        if(n == 1) {
            mScoreTv.setText("Computer rolled a 1. Your turn!");
        }else if(n > 1){
            mScoreTv.setText("CPU Score: " + cpuOverallScore +
                    "\nCPU Turn Score: " + cpuTurnScore);
        }else{
            mScoreTv.setText("Computer holds.");
        }
    }

    int rollDice(){
        Random rand = new Random();
        int randFace = rand.nextInt(6);
        mDiceFaceIv.setImageResource(diceFaces[randFace]);
        return randFace + 1;
    }

    void computerTurn(){
        mRollBtn.setEnabled(false);
        mHoldBtn.setEnabled(false);
//        timerHandler.postDelayed(timerRunnable, 5000);
//        while(cpuTurnScore < 20){
        int cpuDice = rollDice();
        if(cpuDice == 1){
            cpuTurnScore = 0;
            updateCpuLabel(cpuDice);
//            mRollBtn.setEnabled(true);
//            mHoldBtn.setEnabled(true);
//            return;
//            break;
//        }else if(cpuOverallScore < 20){
//            timerHandler.postDelayed(timerRunnable, 0);
//            cpuTurnScore += cpuDice;
//            updateCpuLabel(cpuDice);
//            cpuDice = rollDice();
        }else{
//            mRollBtn.setEnabled(true);
//            mHoldBtn.setEnabled(true);
//            cpuOverallScore += cpuTurnScore;
            cpuTurnScore += cpuDice;
            updateCpuLabel(cpuDice);
        }
//        }
//        mRollBtn.setEnabled(true);
//        mHoldBtn.setEnabled(true);
//        cpuTurnScore = 0;
        cpuOverallScore += cpuTurnScore;
        cpuTurnScore = 0;
        if(cpuOverallScore >= GAME_POINT){
            mScoreTv.setText("You lose!");
        }else{
            updateCpuLabel(2);
            mRollBtn.setEnabled(true);
            mHoldBtn.setEnabled(true);
        }
        //        updateCpuLabel(cpuDice);
    }


}
