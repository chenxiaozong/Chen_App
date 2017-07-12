package com.example.a05_chen.LogTest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a05_chen.R;

public class LoginActivity extends BaseActivity {
    private EditText ed_input_name;
    private EditText ed_input_passwd;
    private Button bt_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bt_login = (Button)findViewById(R.id.bt_login);

        ed_input_passwd = (EditText)findViewById(R.id.ed_input_passwd);
        ed_input_name = (EditText)findViewById(R.id.ed_input_name);

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Login in", Toast.LENGTH_SHORT).show();

                String name = ed_input_name.getText().toString();
                String passwd = ed_input_passwd.getText().toString();

                if(name.equals("chen")&&passwd.equals("123")) {
                    Intent intent = new Intent(LoginActivity.this,LogSuccessActivity.class);
                    startActivity(intent);

                }

            }
        });


    }
}
