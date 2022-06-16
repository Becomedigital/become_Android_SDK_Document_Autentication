package com.becomedigital.sdk.identity.becomedigitalsdk;

import static androidx.navigation.Navigation.findNavController;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.becomedigital.sdk.identity.becomedigitalsdk.callback.AsynchronousTask;
import com.becomedigital.sdk.identity.becomedigitalsdk.callback.BecomeCallBackManager;
import com.becomedigital.sdk.identity.becomedigitalsdk.models.BDIVConfig;
import com.becomedigital.sdk.identity.becomedigitalsdk.models.ResponseIV;
import com.becomedigital.sdk.identity.becomedigitalsdk.services.ValidateStatusRest;
import com.bumptech.glide.Glide;
import com.microblink.MicroblinkSDK;
import com.microblink.entities.recognizers.RecognizerBundle;
import com.microblink.entities.recognizers.blinkid.generic.BlinkIdCombinedRecognizer;
import com.microblink.entities.recognizers.blinkid.generic.classinfo.Country;
import com.microblink.entities.recognizers.blinkid.generic.classinfo.Type;
import com.microblink.image.Image;
import com.microblink.intent.IntentDataTransferMode;
import com.microblink.uisettings.ActivityRunner;
import com.microblink.uisettings.BlinkIdUISettings;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Class MainBDIV
 * Wrapper class for comment validation and general transactions.
 */
public class MainBDIV extends AppCompatActivity implements AsynchronousTask {
    public static final String KEY_ERROR = "ErrorMessage";
    private static ValidateStatusRest autService;
    private BDIVConfig config;
    public Intent mData = new Intent();
    public androidx.appcompat.widget.Toolbar toolbar;
    private FrameLayout frameInit;
    private final BecomeCallBackManager mCallbackManager = BecomeCallBackManager.createNew();
    private BlinkIdCombinedRecognizer recognizer;
    private RecognizerBundle recognizerBundle;
    public static final int MY_BLINKID_REQUEST_CODE = 123;
    private ResponseIV responseIV = new ResponseIV();

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main_bdiv);
        frameInit = findViewById(R.id.frameLoaderInit);
        autService = new ValidateStatusRest();
        ImageView imgLoader = findViewById(R.id.imgLoader);// loader inicial
        Glide.with(this)
                .load(R.drawable.load_init)
                .into(imgLoader);
        getExtrasAndValidateConfig();
        toolbar = findViewById(R.id.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        MicroblinkSDK.setLicenseFile("com.become.mb.key", this);
        MicroblinkSDK.setIntentDataTransferMode(IntentDataTransferMode.PERSISTED_OPTIMISED);
        recognizer = new BlinkIdCombinedRecognizer();
        recognizer.setFullDocumentImageDpi(400);
        recognizer.setSaveCameraFrames(true);
        recognizer.setReturnFullDocumentImage(true);
        recognizerBundle = new RecognizerBundle(recognizer);
    }

    public void setConfig(BDIVConfig config) {
        this.config = config;
    }

    private void getExtrasAndValidateConfig() { // valida data input user
        if (getIntent().getExtras() != null) {
            setConfig((BDIVConfig) getIntent().getSerializableExtra("BDIVConfig"));
            if (config != null) {
                if (this.config.getToken().isEmpty()) {
                    setResulLoginError("Token parameters cannot be empty");
                    return;
                } else if (this.config.getContractId().isEmpty()) {
                    setResulLoginError("ContractId parameters cannot be empty");
                    return;
                } else if (this.config.getUserId().isEmpty()) {
                    setResulLoginError("UserId parameters cannot be empty");
                    return;
                }
                // autenticarse
                getContract();
            }
        }
    }


    private void getContract() {
        autService.getContract(this.config.getContractId(), this.config.getToken(), this, this);
    }

    //region server transactions

    private void enableAppRemoveSpinner() {
        runOnUiThread(() -> {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            Animation animFadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
            frameInit.startAnimation(animFadeOut);
            frameInit.setVisibility(View.GONE);
        });
    }

    @Override
    public void onReceiveResultsTransaction(String response, String error, int resposeEstatus, int transactionId) {

    }

    @Override
    public void onReceiveResultsTransactionDictionary(Map<String, Object> map, int responseStatus, int transactionId) {
        runOnUiThread(() -> {
            if (transactionId == ValidateStatusRest.GETCONTRACT) {
                if (responseStatus == ResponseIV.ERROR) {
                    setResultError((String) map.get("mensaje"));
                } else {
                    enableAppRemoveSpinner();
                    scan();
                }
            }
        });
    }

    @Override
    public void onErrorTransaction(String errorMsn) {
        runOnUiThread(() -> {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            setResultError(errorMsn);
        });
    }
    //endregion

    //region app response
    private void returnResultSucces(ResponseIV responseIV) {
        enableAppRemoveSpinner();
        mData.putExtra("ResponseIV", (Parcelable) responseIV);
        setResult(RESULT_OK, mData);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != MY_BLINKID_REQUEST_CODE) {
            this.mCallbackManager.onActivityResult(requestCode, resultCode, data);
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
        if (resultCode == Activity.RESULT_OK) {
            // OK result code means scan was successful
            onScanSuccess(data);
        } else {
            // user probably pressed Back button and cancelled scanning
            onScanCanceled();
        }
    }

    private void setResulLoginError(String msnErr) {
        mData.putExtra(KEY_ERROR, msnErr);
        setResult(RESULT_FIRST_USER, mData);
        finish();
    }

    public void setResultError(String msnErr) {
        mData.putExtra(KEY_ERROR, msnErr);
        setResult(RESULT_FIRST_USER, mData);
        finish();
    }

    public void setResulUserCanceled() {
        setResult(RESULT_CANCELED, mData);
        finish();
    }


    //endregion

    // region microblikn

    public void scan() {
        // use default UI for scanning documents
        BlinkIdUISettings uiSettings = new BlinkIdUISettings(recognizerBundle);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        // start scan activity based on UI settings
        ActivityRunner.startActivityForResult(MainBDIV.this, MY_BLINKID_REQUEST_CODE, uiSettings);
    }

    private void onScanSuccess(Intent data) {
        // update recogni zer results with scanned data
        recognizerBundle.loadFromIntent(data);

        Map<String, Image> map = new HashMap<String, Image>();
        // you can now extract any scanned data from result, we'll just get primary id
        BlinkIdCombinedRecognizer.Result result = recognizer.getResult();

        String firstName = result.getFirstName();
        String lastName = result.getLastName();
        String dateOfExpiry = result.getDateOfExpiry().getOriginalDateString();
        int age = result.getAge();
        String dateOfBirth = result.getDateOfBirth().getOriginalDateString();
        String mrzText = result.getMrzResult().getMrzText();
        String sex = result.getSex();
        String barcodeResult = result.getBarcodeResult().getStringData();
        byte[] barcodeResultData = result.getBarcodeResult().getRawData();
        String countryName = result.getClassInfo().getCountryName();
        String isoAlpha2CountryCode = result.getClassInfo().getIsoAlpha2CountryCode();
        String isoAlpha3CountryCode = result.getClassInfo().getIsoAlpha3CountryCode();
        String isoNumericCountryCode = result.getClassInfo().getIsoNumericCountryCode();
        String typeName = result.getClassInfo().getType().name();
        String documentNumber = result.getDocumentNumber();

        responseIV.setFirstName(firstName);
        responseIV.setLastName(lastName);
        responseIV.setDateOfExpiry(dateOfExpiry);
        responseIV.setAge(age);
        responseIV.setDateOfBirth(dateOfBirth);
        responseIV.setMrzText(mrzText);
        responseIV.setSex(sex);
        responseIV.setBarcodeResult(barcodeResult);
        responseIV.setBarcodeResultData(barcodeResultData);
        responseIV.setCountryName(countryName);
        responseIV.setIsoAlpha2CountryCode(isoAlpha2CountryCode);
        responseIV.setIsoAlpha3CountryCode(isoAlpha3CountryCode);
        responseIV.setIsoNumericCountryCode(isoNumericCountryCode);
        responseIV.setType(typeName);
        responseIV.setDocumentNumber(documentNumber);

        map.put("imgFront", Objects.requireNonNull(result.getFullDocumentFrontImage()));
        map.put("imgBack", Objects.requireNonNull(result.getFullDocumentBackImage()));
        map.put("imgFrontFull", Objects.requireNonNull(result.getFrontCameraFrame()));
        map.put("imgBackFull", Objects.requireNonNull(result.getBackCameraFrame()));

        for (Map.Entry<String, Image> image : map.entrySet()) {
            run(image);
        }
        responseIV.setResponseStatus(ResponseIV.SUCCES);
        responseIV.setFirstTransaction(true);
        returnResultSucces(responseIV);
    }

    private void onScanCanceled() {
        setResulUserCanceled();
    }

    public void run(Map.Entry<String, Image> image) {
        File mFile = new File(getExternalFilesDir(null), image.getKey() + ".jpg");
        Bitmap b = image.getValue().convertToBitmap();
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(mFile);
            assert b != null;
            boolean successF = b.compress(Bitmap.CompressFormat.JPEG, 100, output);
            b.compress(Bitmap.CompressFormat.JPEG, 100, output);

            if (!successF) {
                com.microblink.util.Log.e(this, "Failed to compress bitmap!");
                try {
                    output.close();
                } catch (IOException ignored) {
                } finally {
                    output = null;
                }
                boolean deleteSuccess = new File(image.getKey()).delete();
                if (!deleteSuccess) {
                    com.microblink.util.Log.e(this, "Failed to delete {}", deleteSuccess);
                }
            }

            switch (image.getKey()) {
                case "imgFront":
                    responseIV.setFrontImagePath(mFile.getPath());
                    break;
                case "imgBack":
                    responseIV.setBackImagePath(mFile.getPath());
                    break;
                case "imgFrontFull":
                    responseIV.setFullFronImagePath(mFile.getPath());
                    break;
                case "imgBackFull":
                    responseIV.setFullBackImagePath(mFile.getPath());
                    break;
            }

        } catch (IOException e) {
            setResultError(e.getLocalizedMessage());
            // e.printStackTrace(); ( );
        } finally {
            if (null != output) {
                try {
                    output.close();
                } catch (IOException e) {
                    setResultError(e.getLocalizedMessage());
                    // e.printStackTrace(); ( );
                }
            }
        }
    }

    // endregion

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
