package com.becomedigital.sdk.identity.becomedigitalsdk.callback;

import android.content.Intent;

/**
 * Manager of responses and returns from the SDK.
 * Assigns the activity to which the result will be returned after the process performed by the SDK.
 */
public class BecomeCallBackManager {
    private BecomeInterfaseCallback mBecomeCallBack;

    private BecomeCallBackManager() {
    }

    public static BecomeCallBackManager createNew() {
        return new BecomeCallBackManager();
    }

    /* access modifiers changed from: 0000 */
    public void setCallback(BecomeInterfaseCallback becomeInterfaseCallback) {
        this.mBecomeCallBack = becomeInterfaseCallback;
    }

    /* access modifiers changed from: 0000 */
    public BecomeInterfaseCallback getCallback() {
        return this.mBecomeCallBack;
    }

    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        return BecomeResponseManager.getInstance().onActivityResult(requestCode, resultCode, data);
    }
}
