package com.becomedigital.sdk.identity.becomedigitalsdk.callback;


import com.becomedigital.sdk.identity.becomedigitalsdk.models.ResponseIV;

/**
 * Response interface to the container application from the SDK.
 */
public interface BecomeInterfaseCallback {
    /**
     * Method onSuccess
     * @param responseIV Object containing the response from the SDK.
     */
    void onSuccess(ResponseIV responseIV);
    /**
     * Method onCancel
     * Returns a response when the user cancels the transaction
     */
    void onCancel();

    /**
     * onError
     * @param pLoginError Object containing the error response from the SDK.
     */
    void onError(LoginError pLoginError);
}
