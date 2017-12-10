package com.example.user.taiwaintourplay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class gameover extends AppCompatActivity {

    Bundle myBundle;
    TextView tv_allcount;
    Button btn_end,btn_again;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);

        tv_allcount= (TextView) findViewById(R.id.tv_allcount);
        myBundle = getIntent().getExtras();
        tv_allcount.setText("\n\n共答對  "+ myBundle.getString("sAllcount")+" 題");

        btn_again= (Button) findViewById(R.id.btn_again);
        btn_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(gameover.this,game.class);
                startActivity(intent);
            }
        });
        btn_end= (Button) findViewById(R.id.btn_end);
        btn_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(gameover.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
