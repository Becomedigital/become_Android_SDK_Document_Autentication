package com.becomedigital.sdk.identity.becomedigitalsdk.callback;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.webkit.WebView;

import com.becomedigital.sdk.identity.becomedigitalsdk.MainBDIV;
import com.becomedigital.sdk.identity.becomedigitalsdk.R;
import com.becomedigital.sdk.identity.becomedigitalsdk.StartActivity;
import com.becomedigital.sdk.identity.becomedigitalsdk.models.BDIVConfig;
import com.becomedigital.sdk.identity.becomedigitalsdk.models.ResponseIV;
import com.becomedigital.sdk.identity.becomedigitalsdk.services.ValidateStatusRest;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Objects;

/**
 * Class BecomeResponseManager
 * Delegate interface response from parent to child
 */
public class BecomeResponseManager implements AsynchronousTask {
    private static final int REQUEST_CODE = 7778;
    private static WeakReference<BecomeCallBackManager> mCallbackManagerWeakReference = new WeakReference<>(null);
    private static BecomeResponseManager sBecomeManager;
    private WeakReference<Activity> mActivityWeakReference = new WeakReference<>(null);

    public static BecomeInterfaseCallback getCallback() {
        BecomeCallBackManager becomeCallBackManager = (BecomeCallBackManager) mCallbackManagerWeakReference.get();
        if (becomeCallBackManager == null) {
            return null;
        }
        return becomeCallBackManager.getCallback();
    }

    /**
     * Method startAutentication
     * Starts the SDK authentication process and connects to the method displayed by the GUI..
     *
     * @param activity   Container application activity.
     * @param bDIVConfig Object with SDK configurations.
     */
    public void startAutentication(Activity activity, BDIVConfig bDIVConfig) {
        if (bDIVConfig.isFirstTransaction()) {
            Intent intent = new Intent(activity, StartActivity.class);
            intent.putExtra("BDIVConfig", (Parcelable) bDIVConfig);
            activity.startActivity(intent);
        } else {
            Log.i("BECOME_IV_SDK", "Enviando información al servidor...");
            String ua = new WebView(activity).getSettings().getUserAgentString();
            ValidateStatusRest validateStatusRest = new ValidateStatusRest();
            validateStatusRest.addDataServer(activity,
                    bDIVConfig,
                    ua,
                    this
            );

        }
    }

    /**
     * Method login
     * This method creates the new {@link WeakReference} and launches the SDK graphing activity..
     *
     * @param activity   Container application activity.
     * @param bDIVConfig Object with SDK configurations.
     */
    public void login(Activity activity, BDIVConfig bDIVConfig) {
        this.mActivityWeakReference = new WeakReference<>(activity);
        startActivity(MainBDIV.class, bDIVConfig);
    }

    /**
     * Method registerCallback
     * Register the return response from the SDK to the containing activity.
     *
     * @param becomeCallBackManager   SDK return response manager.
     * @param becomeInterfaseCallback SDK response interface.
     */
    public void registerCallback(BecomeCallBackManager becomeCallBackManager, BecomeInterfaseCallback becomeInterfaseCallback) {
        becomeCallBackManager.setCallback(becomeInterfaseCallback);
        mCallbackManagerWeakReference = new WeakReference<>(becomeCallBackManager);
    }

    public static synchronized BecomeResponseManager getInstance() {
        BecomeResponseManager becomeResponseManager;
        synchronized (BecomeResponseManager.class) {
            if (sBecomeManager == null) {
                sBecomeManager = new BecomeResponseManager();
            }
            becomeResponseManager = sBecomeManager;
        }
        return becomeResponseManager;
    }

    /* access modifiers changed from: 0000 */
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            BecomeInterfaseCallback callback = getCallback();
            if (callback != null) {
                if (resultCode == RESULT_OK) {
                    callback.onSuccess((ResponseIV) data.getSerializableExtra("ResponseIV"));
                } else if (resultCode == RESULT_CANCELED) {
                    callback.onCancel();
                } else {
                    LoginError loginError = new LoginError();
                    if (data.hasExtra(LoginError.class.getSimpleName()))
                        loginError.setMessage(data.getStringExtra(LoginError.class.getSimpleName()));
                    callback.onError(loginError);
                }
                return true;
            }
        }
        return false;
    }

    private <T extends BaseActivityBecome> void startActivity(Class<MainBDIV> pClass, BDIVConfig bDIVConfig) {
        Activity activity = (Activity) this.mActivityWeakReference.get();
        if (activity != null) {
            Intent intent = new Intent(activity, pClass);
            intent.putExtra("BDIVConfig", (Parcelable) bDIVConfig);
            activity.startActivityForResult(intent, REQUEST_CODE);
            activity.overridePendingTransition(R.anim.slide_in_from_bottom, 0);
        }
    }

    @Override
    public void onReceiveResultsTransaction(ResponseIV responseIV, int transactionId) {

        Objects.requireNonNull(getCallback()).onSuccess(responseIV);
    }

    @Override
    public void onReceiveResultsTransactionDictionary(Map<String, Object> map, int responseStatus, int transactionId) {

    }

    @Override
    public void onErrorTransaction(String errorMsn) {
        LoginError loginError = new LoginError();
        loginError.setMessage(errorMsn);
        Objects.requireNonNull(getCallback()).onError(loginError);
    }
}
