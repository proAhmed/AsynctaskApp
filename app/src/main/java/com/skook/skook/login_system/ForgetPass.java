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
import com.skook.skook.model.NormalResponse;
import com.skook.skook.network.OnLoadCompleteListener;
import com.skook.skook.network.PostForgetPass;
import com.skook.skook.utilities.Utility;

public class ForgetPass extends AppCompatActivity implements View.OnClickListener {

    protected EditText etMobile;
    protected Button btnLogin;
    LinearLayout lin;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_forget_pass);
        initView();
    }

    @Override
    public void onClick(View view) {
        if (Utility.isNetworkConnected(this)) {

            if (view.getId() == R.id.btnLogin) {
            if (!etMobile.getText().toString().isEmpty()) {
                progress.setVisibility(View.VISIBLE);
                btnLogin.setEnabled(false);
                forgetPassAction(etMobile.getText().toString());
            } else {
                Toast.makeText(this, getResources().getString(R.string.insert_phone), Toast.LENGTH_SHORT).show();
            }
        }
    }else {
        Utility.showValidateDialog(
                getResources().getString(R.string.no_internet),
                this);
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
        etMobile = (EditText) findViewById(R.id.etMobile);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(ForgetPass.this);
    }

    private void forgetPassAction(String phone) {
        OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
            @Override
            public boolean onSuccess(Object result) {
                btnLogin.setEnabled(true);
                progress.setVisibility(View.GONE);
                NormalResponse normalResponse = (NormalResponse) result;
                if(normalResponse.getCode()==1){
                    finish();
                    Intent intent = new Intent(ForgetPass.this,Login.class);
                    startActivity(intent);
                }
                Toast.makeText(ForgetPass.this,normalResponse.getMessage(),Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onFailure() {
                btnLogin.setEnabled(true);
                progress.setVisibility(View.GONE);
                return false;
            }
        };
        new PostForgetPass(this, onLoadCompleteListener).execute(phone);
    }
}
