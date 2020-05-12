package com.example.chat;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText password;
    private Button btn,cancel;
    private ImageView hrt;
    SharedPreferences sp;
    Dialog d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        password = findViewById(R.id.password);
        btn = findViewById(R.id.login);
        hrt = findViewById(R.id.heart);
        d = new Dialog(this);
        d.setContentView(R.layout.password);
        cancel = d.findViewById(R.id.button);

        final CountDownTimer ct = new CountDownTimer(500,500) {
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                Intent i = new Intent(getBaseContext(), Chat.class);
                startActivity(i);
                finish();
            }
        };

        Window win = d.getWindow();
        WindowManager.LayoutParams wlp = win.getAttributes();
        win.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        wlp.gravity = Gravity.CENTER;
        wlp.dimAmount = 0.2f;
        win.setAttributes(wlp);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(btn.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                String s = password.getText().toString();
                sp = getSharedPreferences("password",MODE_PRIVATE);
                String str = sp.getString("password","icecream");
                if(s.equals(str)) {
                    hrt.setImageResource(R.drawable.red);
                    password.setText("");
                    ct.start();
                }
                else{
                    hrt.setImageResource(R.drawable.broken);
                    d.show();
                    d.setCancelable(false);

                    cancel.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            d.dismiss();
                            hrt.setImageResource(R.drawable.yellow);
                            password.setText("");
                        }
                    });
                }
            }
        });
    }
}
