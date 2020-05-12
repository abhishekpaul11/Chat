package com.example.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Desc extends AppCompatActivity {

    private Button back,sangabhi;
    private ImageView img;
    private TextView txt;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_desc);

        back = findViewById(R.id.button);
        sangabhi = findViewById(R.id.button3);
        img = findViewById(R.id.pic);
        txt = findViewById(R.id.textView10);
        img.setImageResource(R.drawable.img);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sangabhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setComponent(new ComponentName("com.example.sangabhi", "com.example.sangabhi.MainActivity"));
                if (i != null) {
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(Desc.this,"App not found",Toast.LENGTH_SHORT).show();
                }
            }
        });

        myRef.child("time_abhi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long last = dataSnapshot.getValue(Long.class);
                if(last!=0) {
                    String s = convertTime(last);
                    String[] arr = s.split(" ");
                    txt.setText("last seen " + arr[2] + "." + arr[1] + "." + arr[0] + " " + arr[3]);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        myRef.child("status_abhi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String status = dataSnapshot.getValue(String.class);
                if(status.equals("online")) {
                    txt.setText("online");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    public String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        return format.format(date);
    }
}
