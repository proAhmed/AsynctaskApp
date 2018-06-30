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
import android.widget.Toast;

import com.skook.skook.R;
import com.skook.skook.model.SignInModel;
import com.skook.skook.model.SignUpInput;
import com.skook.skook.network.OnLoadCompleteListener;
import com.skook.skook.network.PostSignUp;
import com.skook.skook.start.NavigationActivity;
import com.skook.skook.utilities.StoreData;
import com.skook.skook.utilities.Utility;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    protected EditText etUserName;
    protected EditText etPassword;
    protected EditText etEmail;
    protected EditText etPhone;
    protected Button btnRegister;
    StoreData storeData;
    LinearLayout lin;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_register);
        initView();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnRegister) {
            if (!etEmail.getText().toString().isEmpty()) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
                    if (!etPassword.getText().toString().isEmpty()) {
                        if (!etUserName.getText().toString().isEmpty()) {
                            if (!etPhone.getText().toString().isEmpty()) {
                                SignUpInput signUpInput = new SignUpInput(etEmail.getText().toString(),
                                        etPassword.getText().toString(), etUserName.getText().toString(),
                                        etPhone.getText().toString());
                                if (Utility.isNetworkConnected(this)) {
                                    progress.setVisibility(View.VISIBLE);
                                    btnRegister.setEnabled(false);
                                    postData(signUpInput);
                            }else {
                                Utility.showValidateDialog(
                                        getResources().getString(R.string.no_internet),
                                        this);
                            }
                            } else {
                                Toast.makeText(this, getResources().getString(R.string.insert_phone), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, getResources().getString(R.string.insert_userName), Toast.LENGTH_SHORT).show();
                         }
                    } else {
                        Toast.makeText(this, getResources().getString(R.string.insert_password), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, getResources().getString(R.string.insert_correct_email), Toast.LENGTH_SHORT).show();
                       }
            } else {
                Toast.makeText(this, getResources().getString(R.string.insert_email), Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void initView() {
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
        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPhone = (EditText) findViewById(R.id.etPhone);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(RegisterActivity.this);
        storeData = new StoreData(this);
    }

    private void postData(SignUpInput signUpInput) {
        OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
            @Override
            public boolean onSuccess(Object result) {
                progress.setVisibility(View.GONE);
                btnRegister.setEnabled(true);
                SignInModel signInModel = (SignInModel) result;
                if (signInModel.getCode() == 1) {
                    storeData.setToken(signInModel.getToken());
                    storeData.setPicture(signInModel.getImage());
                    storeData.setPhone(signInModel.getPhone());
                    storeData.saveEmail(signInModel.getEmail());
                    storeData.setUserName(signInModel.getUsername());
                    storeData.setUserId(signInModel.getUserID());
                    Utility utility = new Utility();
                    utility.sendNotification(RegisterActivity.this,"تم تسجيل الاشتراك بنجاح");
                    finishAffinity();
                    Intent intent = new Intent(RegisterActivity.this, NavigationActivity.class);
                    startActivity(intent);
                } else {
                     Toast.makeText(RegisterActivity.this, signInModel.getMessage(), Toast.LENGTH_SHORT).show();
                }

                return false;
            }

            @Override
            public boolean onFailure() {
                btnRegister.setEnabled(true);
                progress.setVisibility(View.GONE);
                return false;
            }

        };
        new PostSignUp(this, onLoadCompleteListener).execute(signUpInput);
    }
}
