package com.skook.skook.login_system;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.skook.skook.R;

public class MainChooseLogin extends AppCompatActivity implements View.OnClickListener {

    protected Button btnLogin;
    protected Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main_choose_login);
        initView();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLogin) {
            Intent intent = new Intent(MainChooseLogin.this,Login.class);
            startActivity(intent);
            finish();

        } else if (view.getId() == R.id.btnRegister) {
            Intent intent = new Intent(MainChooseLogin.this,RegisterActivity.class);
            startActivity(intent);
            finish();

        }
    }

    private void initView() {
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(MainChooseLogin.this);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(MainChooseLogin.this);
    }
}
