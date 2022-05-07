package com.becomedigital.sdk.identity.becomedigitalsdk.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 *  BDIVConfig
 *  Configuration of the SDK, the respective credentials and parameters are assigned for its operation.
 */
public class BDIVConfig implements Parcelable, Serializable {


    private boolean isFirstTransaction;
    private String token;
    private String contractId;
    private String userId;
    private String frontImagePath;

    /**
     * Constructor BDIVConfig
     * @param token:              Bearer toquen for HTTPS security.
     * @param contractId:         Identifier of the acquired contract.
     * @param userId:             Transaction identifier.
     * @param isFirstTransaction: Defines whether the transaction is going to return an image, or is going to upload the document for validation.
     * @param frontImagePath             required for validation on the server.
     * @apiNote `IsFirstTransaction = true`, Means that the captured images of the document will be returned for preview, otherwise it will load document images in full frame for validation.
     */
    public BDIVConfig(boolean isFirstTransaction, String token, String contractId, String userId, String frontImagePath) {
        this.isFirstTransaction = isFirstTransaction;
        this.token = token;
        this.contractId = contractId;
        this.userId = userId;
        this.frontImagePath = frontImagePath;
    }

    public BDIVConfig(boolean isFirstTransaction, String token, String contractId, String userId) {
        this.isFirstTransaction = isFirstTransaction;
        this.token = token;
        this.contractId = contractId;
        this.userId = userId;
        this.frontImagePath = frontImagePath;
    }

    protected BDIVConfig(Parcel in) {
        isFirstTransaction = in.readByte() != 0;
        token = in.readString();
        contractId = in.readString();
        userId = in.readString();
        frontImagePath = in.readString();
    }

    public static final Creator<BDIVConfig> CREATOR = new Creator<BDIVConfig>() {
        @Override
        public BDIVConfig createFromParcel(Parcel in) {
            return new BDIVConfig(in);
        }

        @Override
        public BDIVConfig[] newArray(int size) {
            return new BDIVConfig[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isFirstTransaction ? 1 : 0));
        dest.writeString(token);
        dest.writeString(contractId);
        dest.writeString(userId);
        dest.writeString(frontImagePath);
    }

    public boolean isFirstTransaction() {
        return isFirstTransaction;
    }

    /**
     * Method setFirstTransaction
     * @param firstTransaction Defines whether the transaction is going to return an image, or is going to upload the document for validation.
     *                         `IsFirstTransaction = true`, Means that the captured images of the document will be returned for preview, otherwise it will load document images in full frame for validation.
     */
    public void setFirstTransaction(boolean firstTransaction) {
        isFirstTransaction = firstTransaction;
    }

    public String getToken() {
        return token;
    }

    /**
     * Method setFirstTransaction
     * @param token Bearer toquen for HTTPS security.
     */
    public void setToken(String token) {
        this.token = token;
    }

    public String getContractId() {
        return contractId;
    }

    /**
     * Method setContractId
     * @param contractId Identifier of the acquired contract.
     */
    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getUserId() {
        return userId;
    }

    /**
     * Method setUserId
     * @param userId Transaction identifier.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFrontImagePath() {
        return frontImagePath;
    }

    /**
     * Method setImgData
     * @param frontImagePath Path front image, required for validation on the server.
     */
    public void setFrontImagePath(String frontImagePath) {
        this.frontImagePath = frontImagePath;
    }
}
