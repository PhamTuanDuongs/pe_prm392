package com.example.pe_prm392;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ((Button)findViewById(R.id.btn_go_to_image)).setOnClickListener(this);

        ((Button)findViewById(R.id.btn_read_from_file)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_go_to_image) {
                Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivity(intent);
        }
        if(v.getId() == R.id.btn_read_from_file) {
            Intent intent = new Intent(MainActivity.this, ReadFileActivity.class);
            startActivity(intent);
        }
    }
}

