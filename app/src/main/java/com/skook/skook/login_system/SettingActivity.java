package com.skook.skook.login_system;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skook.skook.R;
import com.skook.skook.model.NormalResponse;
import com.skook.skook.model.SignUpInput;
import com.skook.skook.network.DeleteUserImage;
import com.skook.skook.network.OnLoadCompleteListener;
import com.skook.skook.network.UpdateUserData;
import com.skook.skook.network.UpdateUserImage;
import com.skook.skook.utilities.StoreData;
import com.skook.skook.utilities.Utility;
import com.squareup.picasso.Picasso;

import java.io.File;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ahmed radwan on 11/22/2017.
 */

public class SettingActivity extends Fragment implements View.OnClickListener {

    protected TextView txtDelete;
    protected TextView txtChange;
    protected EditText etUserName;
    protected EditText etPassword;
    protected EditText etEmail;
    protected EditText etPhone;
    protected Button btnSave;
    protected ProgressBar progressBarMain;
    protected TextView txtUserName;
    protected ProgressBar progressBar;
    ImageView imgUser;
    private static int LOAD_IMAGE_RESULTS = 1;
    Utility utility;
    String imgBase64 = "";
    private static final int PERMISSION_REQUEST_CODE = 200;
    StoreData storeData;
    RelativeLayout rel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_fragment, container, false);
        initView(view);
        return view;

    }




    @Override
    public void onClick(View view) {
        if (Utility.isNetworkConnected(getContext())) {

            if (view.getId() == R.id.txtDelete) {
            deleteImage();
             imgUser.setImageResource(0);
        } else if (view.getId() == R.id.imgUser) {
            if (Build.VERSION.SDK_INT >= 21) {
                if (!checkPermission()) {
                    requestPermission();
                } else {
                    Intent i = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(i, LOAD_IMAGE_RESULTS);
                }
            } else {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, LOAD_IMAGE_RESULTS);
            }


        } else if (view.getId() == R.id.txtChange) {
            if (Build.VERSION.SDK_INT >= 21) {
                if (!checkPermission()) {
                    requestPermission();
                } else {
                    if (!imgBase64.isEmpty()) {
                        updateImage(imgBase64);
                    } else {
                        Intent i = new Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                        startActivityForResult(i, LOAD_IMAGE_RESULTS);
                    }
                }
            } else {
                if (!imgBase64.isEmpty()) {
                    updateImage(imgBase64);
                } else {
                    Intent i = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(i, LOAD_IMAGE_RESULTS);
                }
            }


        } else if (view.getId() == R.id.btnSave) {
            SignUpInput signUpInput = new SignUpInput();
                signUpInput.setEmail(etEmail.getText().toString());
                if (!etUserName.getText().toString().isEmpty()) {
                signUpInput.setUserName(etUserName.getText().toString());
                if (!etPhone.getText().toString().isEmpty()) {
                    signUpInput.setPhone(etPhone.getText().toString());
                    if (!etPassword.getText().toString().isEmpty()) {
                        signUpInput.setPassword(etPassword.getText().toString());
                        progressBar.setVisibility(View.VISIBLE);
                        btnSave.setEnabled(false);
                        updateData(signUpInput);
                    } else {
                        progressBar.setVisibility(View.VISIBLE);
                        btnSave.setEnabled(false);
                        updateData(signUpInput);
                    }

                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.insert_phone), Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getActivity(), getResources().getString(R.string.insert_userName), Toast.LENGTH_SHORT).show();
            }


        }
        }else {
            Utility.showValidateDialog(
                    getResources().getString(R.string.no_internet),
                    getActivity());
        }
    }

    private void initView(View view) {
        utility = new Utility();
        storeData = new StoreData(getActivity());
        rel =  view.findViewById(R.id.rel);
        rel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });
        txtDelete = view.findViewById(R.id.txtDelete);
        txtDelete.setOnClickListener(SettingActivity.this);
        txtChange = view.findViewById(R.id.txtChange);
        txtChange.setOnClickListener(SettingActivity.this);
        etUserName = view.findViewById(R.id.etUserName);
        etPassword = view.findViewById(R.id.etPassword);
        txtUserName = view.findViewById(R.id.txtUserName);
        etEmail = view.findViewById(R.id.etEmail);
        imgUser = view.findViewById(R.id.imgUser);
        imgUser.setOnClickListener(SettingActivity.this);

        etPhone = view.findViewById(R.id.etPhone);
        btnSave = view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(SettingActivity.this);
        progressBarMain = view.findViewById(R.id.progressBarMain);
        progressBar = view.findViewById(R.id.progressBar);

        etPhone.setText(storeData.getPhone());
        txtUserName.setText(storeData.getUserName());
        etUserName.setText(storeData.getUserName());
        etEmail.setText(storeData.getEmail());
        etEmail.setEnabled(false);
        if (storeData.getPicture().contains("/storage")) {
            File imgFile = new File(storeData.getPicture());

            if (imgFile.exists()) {

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imgUser.setImageBitmap(myBitmap);
            }
        } else {
            Picasso.with(getContext()).load(storeData.getPicture()).into(imgUser);
        }

    }

    private void updateData(final SignUpInput signUpInput) {
        OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
            @Override
            public boolean onSuccess(Object result) {
                progressBar.setVisibility(View.GONE);
                btnSave.setEnabled(true);

                NormalResponse normalResponse = (NormalResponse) result;
                if(normalResponse.getCode()==1){
                    if(!signUpInput.getPhone().isEmpty()) {
                        storeData.setPhone(signUpInput.getPhone());
                    }
                    if(!signUpInput.getUserName().isEmpty()) {
                        storeData.setUserName(signUpInput.getUserName());
                    }
                    if(signUpInput.getPassword()!=null&&!signUpInput.getPassword().isEmpty()) {
                        storeData.setPassword(signUpInput.getPassword());
                    }

                }
                Toast.makeText(getActivity(), normalResponse.getMessage(), Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onFailure() {
                progressBar.setVisibility(View.GONE);
                btnSave.setEnabled(true);
                return false;
            }
        };
        new UpdateUserData(getActivity(), onLoadCompleteListener).execute(signUpInput);
    }

    private void updateImage(String image) {
        OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
            @Override
            public boolean onSuccess(Object result) {

                NormalResponse normalResponse = (NormalResponse) result;
                Toast.makeText(getActivity(), normalResponse.getMessage(), Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onFailure() {
                return false;
            }
        };
        new UpdateUserImage(getContext(), onLoadCompleteListener).execute(image);
    }

    private void deleteImage() {
        OnLoadCompleteListener onLoadCompleteListener = new OnLoadCompleteListener() {
            @Override
            public boolean onSuccess(Object result) {

                NormalResponse normalResponse = (NormalResponse) result;
                storeData.setPicture("");
                Toast.makeText(getActivity(), normalResponse.getMessage(), Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onFailure() {
                return false;
            }
        };
        new DeleteUserImage(getContext(), onLoadCompleteListener).execute();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOAD_IMAGE_RESULTS && resultCode == RESULT_OK
                && data != null) {
            // Let's read picked image data - its URI
            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(pickedImage, filePath,
                    null, null, null);
            assert cursor != null;
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor
                    .getColumnIndex(filePath[0]));
            SharedPreferences sh = getActivity().getSharedPreferences("imagePathPhoto",
                    MODE_PRIVATE);
            SharedPreferences.Editor prefEditor = sh.edit();
            prefEditor.putString("image", imagePath);
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            storeData.setPicture(imagePath);
            imgUser.setImageBitmap(bitmap);
            imgBase64 = utility.getEncoded64ImageStringFromBitmap(bitmap);

            prefEditor.apply();
            // Now we need to set the GUI ImageView data with data read from the
            // picked file.

            // At the end remember to close the cursor or you will end with the
            // RuntimeException!
            cursor.close();

        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getContext(), WRITE_EXTERNAL_STORAGE);

        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (storageAccepted) {
                        Intent i = new Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                        startActivityForResult(i, LOAD_IMAGE_RESULTS);
                    } else {
                   //     Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("You need to allow access to the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION, CAMERA},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

}
