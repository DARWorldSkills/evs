package com.davidpopayan.sena.responsiveimage;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.widget.ShareDialog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;

public class MainActivity extends AppCompatActivity {
    Button btnCompartirFace;


    CallbackManager callbackManager;
    ShareDialog shareDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        btnCompartirFace = findViewById(R.id.btnCompartirFace);

        KeyHash();

        compartir();


    }

    private void compartir() {

        btnCompartirFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_PROCESS_TEXT, "Hola Mundo");
                startActivity(Intent.createChooser(intent,"send"));
            }
        });
    }


   // YOZcV4HxCcMbvLRxgKglHTXUEE4=
    private void KeyHash() {

        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.davidpopayan.sena.responsiveimage", getPackageManager().GET_SIGNATURES);

            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


    }
}
