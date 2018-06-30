package com.skook.skook.utilities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Base64;
import android.view.Gravity;
import android.widget.Toast;

import com.skook.skook.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by ahmed radwan on 11/14/2017.
 */

public class Utility {

    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imageEncoded = Base64.encodeToString(byteFormat, Base64.DEFAULT);

        System.out.print("images:  " + imageEncoded);
        return imageEncoded;
    }

    public void sendNotification(Activity activity, String content) {
        Toast toast= Toast.makeText(activity,
                content, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(activity)
                        .setSmallIcon(R.mipmap.icon_launcher)
                        .setContentTitle(content)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(content))
                        .setContentText(content)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)//to show content in lock screen
                        .setPriority(Notification.PRIORITY_MAX);
        NotificationManager notificationManager =
                (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, mBuilder.build());


//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(activity)
//                .setSmallIcon(R.mipmap.icon_launcher)
//                .setContentText(content)
//                .setAutoCancel(true);
//
//
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    public void sendEmail(Activity activity, String emailAddress) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, emailAddress);
        intent.putExtra(Intent.EXTRA_SUBJECT, "");

        activity.startActivity(Intent.createChooser(intent, "Email via..."));
    }

    public void makeCall(Activity activity, String phoneNumber) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        activity.startActivity(callIntent);

     }

    public static void showValidateDialog(String message, Context mContext) {

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext);

        alertDialogBuilder.setMessage(message).setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        alertDialogBuilder.setCancelable(true);
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static void showExsitDialog(final Activity activity, String message, Context mContext) {

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext);

        alertDialogBuilder.setMessage(message).setCancelable(false)
                .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        activity.finishAffinity();
                    }
                }).setNegativeButton("لا", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                alertDialogBuilder.setCancelable(true);
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;
    }

}
