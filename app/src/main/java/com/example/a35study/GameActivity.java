package com.example.a35study;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    private Button start_button;
    private TextView score_view;
    private Button stop_button;
    private Button left_button;
    private Button up_button;
    private Button down_button;
    private Button right_button;

    GameEngine gameEngine;
    SnakeGameView snakeGameView;

    private Handler handler = new Handler();
    private long update_delay = 500;
    private Runnable gameTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        start_button = findViewById(R.id.start_button);
        score_view = findViewById(R.id.score_text);
        stop_button = findViewById(R.id.stop_button);

        left_button = findViewById(R.id.left_button);
        up_button = findViewById(R.id.up_button);
        down_button = findViewById(R.id.down_button);
        right_button = findViewById(R.id.right_button);

        gameEngine = new GameEngine();
        gameEngine.init_game();
        snakeGameView = findViewById(R.id.snake_view);
        snakeGameView.setGameMap(gameEngine.getBoard());

        start_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                gameEngine.init_game();
                startHandler();
            }
        });

        stop_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                gameEngine.finish_game();
            }
        });

        left_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                gameEngine.snakeChangeDirection('l');

            }
        });

        up_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                gameEngine.snakeChangeDirection('u');

            }
        });

        down_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                gameEngine.snakeChangeDirection('d');

            }
        });

        right_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                gameEngine.snakeChangeDirection('r');

            }
        });
    }
    private void startHandler(){
        gameTask = new Runnable() {
            @Override
            public void run() {
                gameEngine.run_game();

                if (gameEngine.getGameStatus() == 'o') {
                    handler.postDelayed(gameTask, update_delay);
                } else {
                    gameEngine.finish_game();
                }
                snakeGameView.setGameMap(gameEngine.getBoard());
                score_view.setText("Score: "+Integer.toString(gameEngine.getScore()));
                snakeGameView.invalidate();
            }
        };
        gameTask.run();
    }
}