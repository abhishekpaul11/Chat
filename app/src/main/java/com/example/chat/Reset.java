package com.example.chat;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Reset extends AppCompatActivity {

    private EditText password;
    private Button btn,ok,oops;
    private ImageView hrt;
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    Dialog d,d1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reset);
        password = findViewById(R.id.password);
        btn = findViewById(R.id.login);
        hrt = findViewById(R.id.heart);
        d = new Dialog(this);
        d.setContentView(R.layout.reset_yes);
        d1 = new Dialog(this);
        d1.setContentView(R.layout.reset_no);
        ok = d.findViewById(R.id.button);
        oops = d1.findViewById(R.id.button);

        Window win = d.getWindow();
        WindowManager.LayoutParams wlp = win.getAttributes();
        win.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        wlp.gravity = Gravity.CENTER;
        wlp.dimAmount = 0.2f;
        win.setAttributes(wlp);

        Window win1 = d1.getWindow();
        WindowManager.LayoutParams wlp1 = win1.getAttributes();
        win1.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        wlp1.gravity = Gravity.CENTER;
        wlp1.dimAmount = 0.2f;
        win1.setAttributes(wlp);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            final String s = password.getText().toString();
            sp = getSharedPreferences("password",MODE_PRIVATE);
            String str = sp.getString("password","icecream");
            if(s.equals(str)) {
                hrt.setImageResource(R.drawable.broken);
                password.setText("");
                d1.show();
                d1.setCancelable(false);
                oops.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d1.dismiss();
                        hrt.setImageResource(R.drawable.yellow);
                    }
                });
            }
            else{
                hrt.setImageResource(R.drawable.red);
                password.setText("");
                edit = sp.edit();
                edit.putString("password",s);
                edit.commit();
                d.show();
                d.setCancelable(false);

                ok.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        finish();
                    }
                });
            }
            }
        });
    }
}
