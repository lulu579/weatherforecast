package com.example.myapplication6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import static android.transition.Fade.IN;
import static android.transition.Fade.OUT;

public class Main2Activity extends AppCompatActivity {
    private int id=0;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        Button button=(Button)findViewById(R.id.button);
        Button button1=(Button)findViewById(R.id.button1);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.sheng:
                        id=1;
                        break;
                    case R.id.shi:
                        id=2;
                        break; } }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id==1){
                   Intent intent=new Intent();
                    intent.setClass(com.example.myapplication6.Main2Activity.this,Main6Activity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent=new Intent();
                    intent.setClass(com.example.myapplication6.Main2Activity.this,Main4Activity.class);
                    startActivity(intent);
                }
            }
        });
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent();
                    intent.setClass(com.example.myapplication6.Main2Activity.this,Main11Activity.class);
                    startActivity(intent);
                }
            });
    }
}
