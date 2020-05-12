package com.example.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MarginLayoutParamsCompat;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
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

public class Chat extends AppCompatActivity {

    private Button loc,reset,sos,img,name,add,oops,thanks,loc_d,chat_d,sos_dd,img_d;
    Dialog sos_d,add_d,pic;
    private ImageView img23,img5,on;
    private TextView msg,num,con;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chat);

        loc = findViewById(R.id.loc_b);
        sos = findViewById(R.id.sos_b);
        reset = findViewById(R.id.reset_b);
        img = findViewById(R.id.pic);
        name = findViewById(R.id.name);
        add = findViewById(R.id.add);
        sos_d = new Dialog(this);
        sos_d.setContentView(R.layout.sos);
        thanks = sos_d.findViewById(R.id.button);
        add_d = new Dialog(this);
        add_d.setContentView(R.layout.message);
        oops = add_d.findViewById(R.id.button);
        pic = new Dialog(this);
        pic.setContentView(R.layout.pic);
        loc_d = pic.findViewById(R.id.loc);
        chat_d = pic.findViewById(R.id.chat);
        sos_dd = pic.findViewById(R.id.sos_d);
        img_d = pic.findViewById(R.id.img);
        img23 = findViewById(R.id.imageView3);
        img23.setImageResource(R.drawable.img);
        img5 = findViewById(R.id.imageView5);
        img5.setImageResource(R.drawable.add);
        msg = findViewById(R.id.textView8);
        on = findViewById(R.id.imageView12);
        num = findViewById(R.id.textView9);
        con = findViewById(R.id.contact);

        myRef.child("status_abhi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String status = dataSnapshot.getValue(String.class);
                if(status.equals("online")){
                    on.setImageResource(R.drawable.online);
                }
                else{
                    on.setImageResource(R.color.colorPrimaryDark);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        myRef.child("abhishek").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                String message = dataSnapshot.getValue(String.class);
                if(!message.equals("")){
                    setMargins(on,110,7,0);
                    con.setGravity(Gravity.TOP);
                    String str[] = message.split("\n");
                    msg.setText(str[str.length-1]);
                    num.setText(str.length+"");
                    num.setBackgroundColor(Color.rgb(255,68,68));
                }
                else{
                    con.setGravity(Gravity.CENTER_VERTICAL);
                    msg.setText("");
                    num.setText("");
                    num.setBackgroundColor(Color.TRANSPARENT);
                    setMargins(on,300,15,0);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });


        Window win = add_d.getWindow();
        WindowManager.LayoutParams wlp = win.getAttributes();
        win.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        wlp.gravity = Gravity.CENTER;
        win.setAttributes(wlp);

        Window win3 = pic.getWindow();
        WindowManager.LayoutParams wlp3 = win3.getAttributes();
        win3.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        wlp3.x = -1000;
        wlp3.y = -730;
        win3.setAttributes(wlp3);

        Window win2 = sos_d.getWindow();
        WindowManager.LayoutParams wlp1 = win2.getAttributes();
        win2.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        wlp1.gravity = Gravity.CENTER;
        win2.setAttributes(wlp1);

        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),Loc.class);
                startActivity(i);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),Reset.class);
                startActivity(i);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_d.show();
                oops.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        add_d.dismiss();
                    }
                });
            }
        });

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),Message.class);
                startActivity(i);
            }
        });

        sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sos_d.show();
                thanks.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sos_d.dismiss();
                        Toast.makeText(Chat.this,"Mention Not",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pic.show();
                img_d.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pic.dismiss();
                        Intent i = new Intent(getBaseContext(),Pic.class);
                        startActivity(i);
                    }
                });
                chat_d.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pic.dismiss();
                        Intent i = new Intent(getBaseContext(),Message.class);
                        startActivity(i);
                    }
                });
                loc_d.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pic.dismiss();
                        Intent i = new Intent(getBaseContext(),Loc.class);
                        startActivity(i);
                    }
                });
                sos_dd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pic.dismiss();
                        sos_d.show();
                        thanks.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sos_d.dismiss();
                                Toast.makeText(Chat.this,"Mention Not",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }
        });

    }
    private void setMargins (View view, int left, int top, int bottom) {
        int leftD = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, left, getResources()
                        .getDisplayMetrics());
        int topD = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, top, getResources()
                        .getDisplayMetrics());
        int bottomD = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, bottom, getResources()
                        .getDisplayMetrics());
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.topMargin=topD;
            p.bottomMargin=bottomD;
            p.leftMargin=leftD;
            view.requestLayout();
        }
    }
}
