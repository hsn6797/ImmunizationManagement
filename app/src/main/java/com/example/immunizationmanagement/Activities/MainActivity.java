package com.example.immunizationmanagement.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.immunizationmanagement.R;
import com.example.immunizationmanagement.Utills.Permission.RunTimePermission;

public class MainActivity extends AppCompatActivity {

    Button babyPress;
    private RunTimePermission rp;



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        rp.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        babyPress  = (Button) findViewById(R.id.babyB);


        Intent i = new Intent( MainActivity.this, Main2Activity.class);
        startActivity(i);
        finish();


        babyPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( MainActivity.this, Main2Activity.class);
                startActivity(i);
            }
        });


    }


}
