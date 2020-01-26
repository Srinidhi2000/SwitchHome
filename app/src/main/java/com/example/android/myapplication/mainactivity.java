package com.example.android.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class mainactivity extends AppCompatActivity implements View.OnClickListener {
    Button add, submit;
    RelativeLayout layout1;
    MyView view1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = findViewById(R.id.addRoom);
       add.setOnClickListener(this);
        submit = findViewById(R.id.submitRoom);
        submit.setOnClickListener(this);
         view1 = new MyView(this);
        layout1=findViewById(R.id.layout1);
        layout1.addView(view1);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.addRoom:
            {   if(view1.currentpt.x1==0)
            {   Toast.makeText(this,"ready" , Toast.LENGTH_SHORT).show();
                view1.setIsend(false);
                view1.invalidate();
            }
            }
             case R.id.submitRoom :if(view1.isend)
            {
            view1.onSubmit();
            }
        }
    }
}