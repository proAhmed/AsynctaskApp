package com.skook.skook.login_system;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.skook.skook.R;
import com.skook.skook.model.SignInModel;
import com.skook.skook.network.OnLoadCompleteListener;
import com.skook.skook.network.PostSignIn;
import com.skook.skook.start.NavigationActivity;
import com.skook.skook.utilities.StoreData;
import com.skook.skook.utilities.Utility;

public class Login extends AppCompatActivity implements View.OnClickListener {

    protected EditText etEmail;
    protected EditText etPassword;
    protected Button btnLogin;
    protected TextView txtForgetPass;
    StoreData storeData;
    LinearLayout lin;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_login);

        initView();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLogin) {
            if(!etEmail.getText().toString().isEmpty()){
                if(!etPassword.getText().toString().isEmpty()){
                    if (Utility.isNetworkConnected(this)) {
                        progress.setVisibility(View.VISIBLE);
                        btnLogin.setEnabled(false);
                        postData(etEmail.getText().toString(),etPassword.getText().toString());
                    }else {
                        Utility.showValidateDialog(
                                getResources().getString(R.string.no_internet),
                                Login.this);
                    }
                }else {
                    Toast.makeText(this,"ادخل كلمة السر",Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this,"ادخل بريد الكترونى",Toast.LENGTH_SHORT).show();

            }

        } else if (view.getId() == R.id.txtForgetPass) {

            Intent intent = new Intent(Login.this,ForgetPass.class);
            startActivity(intent);
        }
    }

    private void initView() {

        etEmail = (EditText) findViewById(R.id.etEmail);
        lin = (LinearLayout) findViewById(R.id.lin);
        progress = (ProgressBar) findViewById(R.id.progress);
        lin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(Login.this);
        txtForgetPass = (TextView) findViewById(R.id.txtForgetPass);
        txtForgetPass.setOnClickListener(Login.this);
        storeData = new StoreData(this);
    }

    private void postData(String email,String pass){
        OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
            @Override
            public boolean onSuccess(Object result) {
                progress.setVisibility(View.GONE);
                btnLogin.setEnabled(true);

                SignInModel signInModel = (SignInModel) result;
                if (signInModel.getCode() == 1) {
                    storeData.setToken(signInModel.getToken());
                    storeData.setPicture(signInModel.getImage());
                    storeData.setPhone(signInModel.getPhone());
                    storeData.saveEmail(signInModel.getEmail());
                    storeData.setUserName(signInModel.getUsername());
                    storeData.setUserId(signInModel.getUserID());
                    Utility utility = new Utility();
                    utility.sendNotification(Login.this,"تم تسجيل الدخول بنجاح");
                    finishAffinity();
                    Intent intent = new Intent(Login.this, NavigationActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Login.this, signInModel.getMessage(), Toast.LENGTH_SHORT).show();
                }

//            finish();
//            new StoreData(this).setToken("");
                return false;
            }

            @Override
            public boolean onFailure() {
                progress.setVisibility(View.GONE);
                btnLogin.setEnabled(true);
                return false;
            }

        };  new PostSignIn(this,onLoadCompleteListener).execute(email,pass);
    }
}
