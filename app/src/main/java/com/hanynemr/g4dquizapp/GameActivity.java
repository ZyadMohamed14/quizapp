package com.hanynemr.g4dquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    TextView questionText,questionCount,helloText;
    Spinner answerText;

    Button startButton,nextButton;
    List<Question> questions;
    int skipGame=-1;
//    List<String> countries=Arrays.asList("egypt","usa","france","uk");
//    List<String> countriesCopy= Arrays.asList("egypt","usa","france","uk");
//    List<String> cities=Arrays.asList("cairo","ws","paris","london");


    ArrayList<String> items=new ArrayList<>();
    byte score;
    byte index ;
    MediaPlayer player;

boolean isnextButtonisPressed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questionText=findViewById(R.id.questionText);
        questionCount=findViewById(R.id.questionCount);
        answerText=findViewById(R.id.answerText);
        startButton=findViewById(R.id.startButton);
        nextButton=findViewById(R.id.nextButton);
        helloText=findViewById(R.id.helloText);

        String name = getIntent().getStringExtra("name");
        helloText.setText("hello "+name);

        questions=Arrays.asList(new Question("egypt","cairo"),
                new Question("usa","ws"),
                new Question("france","paris"),
                new Question("uk","london"));

    }

    @Override
    public void onBackPressed() {
        if (player != null) {
            player.stop();
        }
       if(isnextButtonisPressed){
         //
           // Optionally, perform any actions you want to enforce staying in the game
           // For example, you could show a message or dialog to inform the user
           Toast.makeText(this, "You must finish the game!", Toast.LENGTH_LONG).show();
           Log.d("benz", "onBackPressed: "+skipGame);
           return; // This prevents further execution of onBackPressed()
       }else{
           super.onBackPressed();
       }

    }


    public void start(View view) {

        if(skipGame==1){
            startButton.setEnabled(false);
            Toast.makeText(this, "You have finished all your attempts \n ", Toast.LENGTH_LONG).show();
            return;
        }
        if(index != questions.size()&&index!=0) skipGame++;
     //   Toast.makeText(this, "skiped game = "+skipGame, Toast.LENGTH_LONG).show();
        if (player!=null)
            player.stop();

        index=0;
        score=0;
        Collections.shuffle(questions);
        questionText.setText("what is the capital of "+questions.get(index).getName());
        questionCount.setText("Question 1 of "+questions.size());
        nextButton.setEnabled(true);
        answerText.setEnabled(true);

        items.clear();
        Collections.addAll(items,"please select",
                "cairo",
                "damascus",
                "baghdad",
                "ws",
                "toronto",
                "london",
                "khartoum",
                "paris");

        //items->adapter->spinner
        ArrayAdapter adapter=new ArrayAdapter(this
                , android.R.layout.simple_list_item_1,items);
        answerText.setAdapter(adapter);

    }

    public void next(View view) {
        isnextButtonisPressed = true;
        String answer=answerText.getSelectedItem().toString();
        if (answer.equalsIgnoreCase("please select")){
            Toast.makeText(this, "please answer", Toast.LENGTH_SHORT).show();
            return;
        }

        if (answer.equalsIgnoreCase(questions.get(index).getCity())) {
            score++;
            items.remove(answer);
        }

        index++;
        if(index<questions.size()){
            questionText.setText("what is the capital of "+questions.get(index).getName());
            questionCount.setText("Question "+(index+1)+" of "+questions.size());
        }else{
//            Toast.makeText(this, "score="+score, Toast.LENGTH_LONG).show();
            nextButton.setEnabled(false);
            if (score>(questions.size())/2){
                player=MediaPlayer.create(this,R.raw.success);
                player.start();
            }else{
                player=MediaPlayer.create(this,R.raw.fail);
                player.start();
            }

            Intent a=new Intent(this, PlayerActivity.class);
            a.putExtra("score",score);
            setResult(RESULT_OK,a);
            finish();
        }

        answerText.setSelection(0);

//        items.remove(0);
//        Collections.shuffle(items);
//        items.add(0,"please select");

//        Collections.shuffle(items);
//        int index = items.indexOf("please select");
//        Collections.swap(items,0,index);

        Collections.shuffle(items.subList(1,items.size()));



//        answerText.setText("");
    }
}