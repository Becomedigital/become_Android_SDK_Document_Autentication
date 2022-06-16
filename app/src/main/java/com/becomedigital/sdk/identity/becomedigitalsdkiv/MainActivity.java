package com.becomedigital.sdk.identity.becomedigitalsdkiv;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.becomedigital.sdk.identity.becomedigitalsdk.callback.BecomeCallBackManager;
import com.becomedigital.sdk.identity.becomedigitalsdk.callback.BecomeInterfaseCallback;
import com.becomedigital.sdk.identity.becomedigitalsdk.callback.BecomeResponseManager;
import com.becomedigital.sdk.identity.becomedigitalsdk.callback.LoginError;
import com.becomedigital.sdk.identity.becomedigitalsdk.models.BDIVConfig;
import com.becomedigital.sdk.identity.becomedigitalsdk.models.ResponseIV;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private final BecomeCallBackManager mCallbackManager = BecomeCallBackManager.createNew();
    private String contractId = "";
    private String token = "";
    private String userId = "";
    private TextView textResponse;
    private String fullFronImagePath = "";
    private TextView textValidation;

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textResponse = findViewById(R.id.textReponse);
        textValidation = findViewById(R.id.textDocumentValidation);
        EditText textContractId = findViewById(R.id.ContractIdText);
        EditText textToken = findViewById(R.id.textToken);
        ImageView imgFront = findViewById(R.id.imgFront);
        ImageView imgFrontFull = findViewById(R.id.imgFrontFull);
        ImageView imgBack = findViewById(R.id.imgBack);
        ImageView imgBackFull = findViewById(R.id.imgBackFull);
        Button btnAut = findViewById(R.id.btnAuth);
        Button btnSecond = findViewById(R.id.btnSecond);

        btnSecond.setOnClickListener(view -> {
            textValidation.setText("Enviando segunda petición...");
            secondTransacion(fullFronImagePath);
        });
        btnAut.setOnClickListener(view -> {
            textValidation.setText("");
            token = textToken.getText().toString();
            contractId = textContractId.getText().toString().isEmpty() ? "2" : textContractId.getText().toString();
            Date currentTime = Calendar.getInstance().getTime();
            SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault());
            userId = format1.format(currentTime);

            BecomeResponseManager.getInstance().startAutentication(MainActivity.this,
                    new BDIVConfig(true,
                            token,
                            contractId,
                            userId));

            BecomeResponseManager.getInstance().registerCallback(mCallbackManager, new BecomeInterfaseCallback() {
                @Override
                public void onSuccess(final ResponseIV responseIV) {
                    runOnUiThread(() -> {
                        if (responseIV.getFullFronImagePath() != null) {
                            File imgFileFullFront = new File(responseIV.getFullFronImagePath());
                            if (imgFileFullFront.exists()) {
                                fullFronImagePath = responseIV.getFullFronImagePath();
                                Bitmap myBitmap = BitmapFactory.decodeFile(imgFileFullFront.getAbsolutePath());
                                imgFrontFull.setImageBitmap(myBitmap);
                            }
                        }
                        if (responseIV.getFrontImagePath() != null) {
                            File imgFile = new File(responseIV.getFrontImagePath());
                            if (imgFile.exists()) {
                                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                                imgFront.setImageBitmap(myBitmap);
                            }
                        }
                        if (responseIV.getBackImagePath() != null) {
                            File imgFile = new File(responseIV.getBackImagePath());
                            if (imgFile.exists()) {
                                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                                imgBack.setImageBitmap(myBitmap);
                            }
                        }
                        if (responseIV.getFullBackImagePath() != null) {
                            File imgFile = new File(responseIV.getFullBackImagePath());
                            if (imgFile.exists()) {
                                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                                imgBackFull.setImageBitmap(myBitmap);
                            }
                        }
                        textResponse.setText(responseIV.toString());
                    });
                }

                @Override
                public void onCancel() {
                    textResponse.setText("Cancelado por el usuario ");

                }

                @Override
                public void onError(LoginError pLoginError) {
                    textResponse.setText(pLoginError.getMessage());
                }

            });
        });

    }

    private void secondTransacion(String frontImagePath) {

        BecomeResponseManager.getInstance().startAutentication(MainActivity.this,
                new BDIVConfig(false,
                        token,
                        contractId,
                        userId,
                        frontImagePath
                ));

        BecomeResponseManager.getInstance().registerCallback(mCallbackManager, new BecomeInterfaseCallback() {
            @Override
            public void onSuccess(final ResponseIV responseIV) {
                runOnUiThread(() -> {
                    textValidation.setText(responseIV.getDocumentValidation());
                });
            }

            @Override
            public void onCancel() {
                runOnUiThread(() -> {
                    textValidation.setText("Cancelado por el usuario ");
                });
            }

            @Override
            public void onError(LoginError pLoginError) {
                runOnUiThread(() -> {
                    textValidation.setText(pLoginError.getMessage());
                });
            }

        });
    }
}
