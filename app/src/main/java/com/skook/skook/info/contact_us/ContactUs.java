package com.skook.skook.info.contact_us;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.skook.skook.R;
import com.skook.skook.model.ContactUsInput;
import com.skook.skook.model.ContactUsModel;
import com.skook.skook.model.NormalResponse;
import com.skook.skook.network.GetContactUS;
import com.skook.skook.network.OnLoadCompleteListener;
import com.skook.skook.network.PostContactUS;

/**
 * Created by ahmed radwan on 11/14/2017.
 */

public class ContactUs extends Fragment implements View.OnClickListener {

    protected EditText etName;
    protected EditText etEmail;
    protected EditText etMessage;
    protected Button btnContactUs;
    protected TextView tvPhone;
    protected TextView tvEmail;
    protected TextView tvMobile;
    protected TextView tvFax;
    LinearLayout lin;
    ProgressBar progress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_us_fragment, container, false);
        initView(view);
        return view;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnContactUs) {

            if (!etName.getText().toString().isEmpty()) {
                if (!etEmail.getText().toString().isEmpty()) {
                    if (!etMessage.getText().toString().isEmpty()) {
                        ContactUsInput contactUsInput = new ContactUsInput(etName.getText().toString()
                                , etEmail.getText().toString(), etMessage.getText().toString());
                        progress.setVisibility(View.VISIBLE);
                        btnContactUs.setEnabled(false);
                        postData(contactUsInput);

                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.insert_message), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.insert_email), Toast.LENGTH_SHORT).show();

                }
            } else {
                Toast.makeText(getActivity(), getResources().getString(R.string.insert_name), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initView(View rootView) {
        lin = rootView.findViewById(R.id.lin);
        progress = rootView.findViewById(R.id.progress);
        lin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });
        etName = rootView.findViewById(R.id.etName);
        etEmail = rootView.findViewById(R.id.etEmail);
        etMessage = rootView.findViewById(R.id.etMessage);
        btnContactUs = rootView.findViewById(R.id.btnContactUs);
        btnContactUs.setOnClickListener(ContactUs.this);
        tvPhone = (TextView) rootView.findViewById(R.id.tvPhone);
        tvEmail = (TextView) rootView.findViewById(R.id.tvEmail);
        tvMobile = (TextView) rootView.findViewById(R.id.tvMobile);
        tvFax = (TextView) rootView.findViewById(R.id.tvFax);
        loadData();
    }

    private void postData(ContactUsInput contactUsInput) {
        OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
            @Override
            public boolean onSuccess(Object result) {
                NormalResponse normalResponse = (NormalResponse) result;
                Toast.makeText(getActivity(), normalResponse.getMessage(), Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.GONE);
                btnContactUs.setEnabled(true);

                return false;
            }

            @Override
            public boolean onFailure() {
                progress.setVisibility(View.GONE);
                btnContactUs.setEnabled(true);
                return false;
            }
        };
        new PostContactUS(getActivity(), onLoadCompleteListener).execute(contactUsInput);
    }

    private void loadData() {
        OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
            @Override
            public boolean onSuccess(Object result) {

                ContactUsModel aboutUsModel = (ContactUsModel) result;
                tvPhone.setText(aboutUsModel.getData().getPhone());
                tvMobile.setText(aboutUsModel.getData().getMobile());
                tvEmail.setText(aboutUsModel.getData().getEmail());
                tvFax.setText(aboutUsModel.getData().getFax());




                return false;
            }

            @Override
            public boolean onFailure() {
                return false;
            }
        };
        new GetContactUS(getActivity(), onLoadCompleteListener).execute();
    }


}
