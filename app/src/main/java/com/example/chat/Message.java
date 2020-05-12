package com.example.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message extends AppCompatActivity {

    private Button img,loc,sos,thanks,send,back,b2,bg,b1,b22,b3,b4,b5,b6,b7,b8,b9;
    private EditText msg;
    private ImageView i,onl;
    private int c=5,d=0,e=0,b=0;
    private String text="",status="";
    private long last;
    private ScrollView scrollView;
    private TextView txt,txt1,ls;
    Dialog sos_d,con,wp;
    CountDownTimer t;

    SharedPreferences sp;
    SharedPreferences.Editor edit;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_message);
        myRef.child("status_sanga").setValue("online");
        myRef.child("time_sanga").setValue(0);
        sp = getSharedPreferences("wallpaper",MODE_PRIVATE);
        int w = sp.getInt("wallpaper",1);
        final ConstraintLayout layout = findViewById(R.id.wallpaper);

        switch (w){
            case 1:
                layout.setBackgroundResource(R.drawable.chatbg);
                break;
            case 2:
                layout.setBackgroundResource(R.drawable.both);
                break;
            case 3:
                layout.setBackgroundResource(R.drawable.abhishek);
                break;
            case 4:
                layout.setBackgroundResource(R.drawable.sanga);
                break;
            case 5:
                layout.setBackgroundColor(Color.rgb(153,204,0));
                break;
            case 6:
                layout.setBackgroundColor(Color.rgb(170,102,204));
                break;
            case 7:
                layout.setBackgroundColor(Color.rgb(255,187,51));
                break;
            case 8:
                layout.setBackgroundColor(Color.rgb(255,136,0));
                break;
            case 9:
                layout.setBackgroundColor(Color.rgb(0,221,255));

        }

        i = findViewById(R.id.imageView);
        img = findViewById(R.id.img);
        loc = findViewById(R.id.loc_b);
        onl = findViewById(R.id.imageView11);
        sos = findViewById(R.id.sos_b);
        msg = findViewById(R.id.msg);
        ls = findViewById(R.id.textView5);
        back = findViewById(R.id.button2);
        scrollView = findViewById(R.id.scrollView);
        send = findViewById(R.id.send);
        txt = findViewById(R.id.textView1);
        txt1 = findViewById(R.id.textView);
        i.setImageResource(R.drawable.img);
        sos_d = new Dialog(this);
        con = new Dialog(this);
        sos_d.setContentView(R.layout.sos);
        con.setContentView(R.layout.connect);
        b2 = con.findViewById(R.id.button4);
        thanks = sos_d.findViewById(R.id.button);
        bg = findViewById(R.id.button5);
        wp = new Dialog(this);
        wp.setContentView(R.layout.wallpaper);

        b1 = wp.findViewById(R.id.button1);
        b22 = wp.findViewById(R.id.button2);
        b3 = wp.findViewById(R.id.button3);
        b4 = wp.findViewById(R.id.button4);
        b5 = wp.findViewById(R.id.button5);
        b6 = wp.findViewById(R.id.button6);
        b7 = wp.findViewById(R.id.button7);
        b8 = wp.findViewById(R.id.button8);
        b9 = wp.findViewById(R.id.button9);

        txt.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        txt.setGravity(Gravity.BOTTOM);
        txt1.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        txt1.setGravity(Gravity.BOTTOM);

        Window win3 = wp.getWindow();
        WindowManager.LayoutParams wlp3 = win3.getAttributes();
        win3.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        wlp3.gravity = Gravity.CENTER;
        win3.setAttributes(wlp3);

        final CountDownTimer ct = new CountDownTimer(300,300) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                b=0;
            }
        };

        bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            b++;
            if(b==1){
                ct.start();
            }
            else{
                wp.show();
                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        layout.setBackgroundResource(R.drawable.chatbg);
                        Toast.makeText(Message.this,"Wallpaper set",Toast.LENGTH_SHORT).show();
                        wp.dismiss();
                        edit = sp.edit();
                        edit.putInt("wallpaper",1);
                        edit.apply();
                    }
                });
                b22.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        layout.setBackgroundResource(R.drawable.both);
                        Toast.makeText(Message.this,"Wallpaper set",Toast.LENGTH_SHORT).show();
                        wp.dismiss();
                        edit = sp.edit();
                        edit.putInt("wallpaper",2);
                        edit.apply();
                    }
                });
                b3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        layout.setBackgroundResource(R.drawable.abhishek);
                        Toast.makeText(Message.this,"Wallpaper set",Toast.LENGTH_SHORT).show();
                        wp.dismiss();
                        edit = sp.edit();
                        edit.putInt("wallpaper",3);
                        edit.apply();
                    }
                });
                b4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        layout.setBackgroundResource(R.drawable.sanga);
                        Toast.makeText(Message.this,"Wallpaper set",Toast.LENGTH_SHORT).show();
                        wp.dismiss();
                        edit = sp.edit();
                        edit.putInt("wallpaper",4);
                        edit.apply();
                    }
                });
                b5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        layout.setBackgroundColor(Color.rgb(153,204,0));
                        Toast.makeText(Message.this,"Wallpaper set",Toast.LENGTH_SHORT).show();
                        wp.dismiss();
                        edit = sp.edit();
                        edit.putInt("wallpaper",5);
                        edit.apply();
                    }
                });
                b6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        layout.setBackgroundColor(Color.rgb(170,102,204));
                        Toast.makeText(Message.this,"Wallpaper set",Toast.LENGTH_SHORT).show();
                        wp.dismiss();
                        edit = sp.edit();
                        edit.putInt("wallpaper",6);
                        edit.apply();
                    }
                });
                b7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        layout.setBackgroundColor(Color.rgb(255,187,51));
                        Toast.makeText(Message.this,"Wallpaper set",Toast.LENGTH_SHORT).show();
                        wp.dismiss();
                        edit = sp.edit();
                        edit.putInt("wallpaper",7);
                        edit.apply();
                    }
                });
                b8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        layout.setBackgroundColor(Color.rgb(255,136,0));
                        Toast.makeText(Message.this,"Wallpaper set",Toast.LENGTH_SHORT).show();
                        wp.dismiss();
                        edit = sp.edit();
                        edit.putInt("wallpaper",8);
                        edit.apply();
                    }
                });
                b9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        layout.setBackgroundColor(Color.rgb(0,221,255));
                        Toast.makeText(Message.this,"Wallpaper set",Toast.LENGTH_SHORT).show();
                        wp.dismiss();
                        edit = sp.edit();
                        edit.putInt("wallpaper",9);
                        edit.apply();
                    }
                });
            }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                t.cancel();
            }
        });

        myRef.child("time_abhi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                last = dataSnapshot.getValue(Long.class);
                if(last!=0) {
                    String s = convertTime(last);
                    String[] arr = s.split(" ");
                    ls.setText("last seen " + arr[2] + "." + arr[1] + "." + arr[0] + " " + arr[3]);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        myRef.child("abhishek").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                if(!s.equals("")) {
                    text="";
                    c=3;
                    txt1.append(TextViewUtils.getColoredString((s + "\n"), ContextCompat.getColor(getApplication(), R.color.colorPrimary)));
                    txt.append(TextViewUtils.getColoredString((s + "\n"), ContextCompat.getColor(getApplication(), R.color.colorPrimaryDark)));
                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.fullScroll(View.FOCUS_DOWN);
                        }
                    });
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
                status = dataSnapshot.getValue(String.class);
                if(status.equals("online")){
                    onl.setImageResource(R.drawable.online);
                    ls.setText("online");
                    c=4;
                }
                else{
                    onl.setImageResource(R.color.colorPrimaryDark);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        myRef.child("sanga").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                con.dismiss();
                e=1;
                if(d==0) {
                    text = dataSnapshot.getValue(String.class);
                    if(!text.equals("")) {
                        txt.append(TextViewUtils.getColoredString((text), ContextCompat.getColor(getApplication(), R.color.colorPrimary)));
                        txt1.append(TextViewUtils.getColoredString((text), ContextCompat.getColor(getApplication(), R.color.colorPrimaryDark)));
                    }
                    d=1;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(send.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                String s = msg.getText().toString();
                if(!s.equals("")) {
                    if(status.equals("online")) {
                        c=1;
                        msg.setText("");
                        myRef.child("sanga").setValue(s);
                        txt.append(TextViewUtils.getColoredString((s + "\n"), ContextCompat.getColor(getApplication(), R.color.colorPrimary)));
                        txt1.append(TextViewUtils.getColoredString((s + "\n"), ContextCompat.getColor(getApplication(), R.color.colorPrimaryDark)));
                        scrollView.post(new Runnable() {
                            @Override
                            public void run() {
                                scrollView.fullScroll(View.FOCUS_DOWN);
                            }
                        });
                    }
                    else if(status.equals("offline")){
                        c=0;
                        msg.setText("");
                        text+=s+"\n";
                        myRef.child("sanga").setValue(text);
                        txt.append(TextViewUtils.getColoredString(s+"\n", ContextCompat.getColor(getApplication(), R.color.colorPrimary)));
                        txt1.append(TextViewUtils.getColoredString(s+"\n", ContextCompat.getColor(getApplication(), R.color.colorPrimaryDark)));
                        scrollView.post(new Runnable() {
                            @Override
                            public void run() {
                                scrollView.fullScroll(View.FOCUS_DOWN);
                            }
                        });
                    }
                }
            }
        });

        Window win2 = sos_d.getWindow();
        WindowManager.LayoutParams wlp1 = win2.getAttributes();
        win2.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        wlp1.gravity = Gravity.CENTER;
        win2.setAttributes(wlp1);

        Window win = con.getWindow();
        WindowManager.LayoutParams wlp = win.getAttributes();
        win.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        wlp.gravity = Gravity.CENTER;
        win.setAttributes(wlp);
        con.setCancelable(false);

        t = new CountDownTimer(360000,100) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(!haveNetworkConnection()){
                    con.show();
                }
                else {
                    if(e==1)con.dismiss();
                }
            }

            @Override
            public void onFinish() {
            }
        }.start();

        if(c==5) con.show();

        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(getBaseContext(),Loc.class);
                startActivity(j);
                t.cancel();
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
                        Toast.makeText(Message.this,"Mention Not",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(getBaseContext(),Desc.class);
                startActivity(j);
                t.cancel();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                t.cancel();
            }
        });
    }

    public String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        return format.format(date);
    }

    public void onDestroy(){
        super.onDestroy();
        if(c==1 || c==3 || c==4) myRef.child("sanga").setValue("");
        myRef.child("abhishek").setValue("");
        myRef.child("status_sanga").setValue("offline");
        myRef.child("time_sanga").setValue(ServerValue.TIMESTAMP);
        t.cancel();
    }

    public void onPause(){
        super.onPause();
        if(c==1 || c==3 || c==4) myRef.child("sanga").setValue("");
        myRef.child("abhishek").setValue("");
        myRef.child("status_sanga").setValue("offline");
        myRef.child("time_sanga").setValue(ServerValue.TIMESTAMP);
        finish();
    }

    public void onResume(){
        super.onResume();
        myRef.child("status_sanga").setValue("online");
        myRef.child("time_sanga").setValue(0);
    }
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected()) {
                    haveConnectedWifi = true;
                }
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
class TextViewUtils{
    public static Spannable getColoredString(String mString, int colorId) {
        Spannable spannable = new SpannableString(mString);
        spannable.setSpan(new ForegroundColorSpan(colorId), 0, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }
}
