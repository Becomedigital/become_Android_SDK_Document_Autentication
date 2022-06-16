package com.becomedigital.sdk.identity.becomedigitalsdk.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Arrays;

public class ResponseIV implements Parcelable, Serializable {

    public static final int ERROR = 2;
    public static final int SUCCES = 0;

    private String firstName;
    private String lastName;
    private String documentNumber;
    private String dateOfExpiry;
    private Integer age;
    private String dateOfBirth;
    private String mrzText;
    private String sex;
    private String barcodeResult;
    private byte[] barcodeResultData;
    private String isoAlpha2CountryCode;
    private String isoAlpha3CountryCode;
    private String isoNumericCountryCode;
    private String countryName;
    private String type;
    private String frontImagePath;
    private String backImagePath;
    private String fullFronImagePath;
    private String fullBackImagePath;
    private String documentValidation;
    private String registryInformation;
    private Integer responseStatus;
    private String message;
    private Boolean IsFirstTransaction;

    protected ResponseIV(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        documentNumber = in.readString();
        dateOfExpiry = in.readString();
        if (in.readByte() == 0) {
            age = null;
        } else {
            age = in.readInt();
        }
        dateOfBirth = in.readString();
        mrzText = in.readString();
        sex = in.readString();
        barcodeResult = in.readString();
        barcodeResultData = in.createByteArray();
        isoAlpha2CountryCode = in.readString();
        isoAlpha3CountryCode = in.readString();
        isoNumericCountryCode = in.readString();
        countryName = in.readString();
        type = in.readString();
        frontImagePath = in.readString();
        backImagePath = in.readString();
        fullFronImagePath = in.readString();
        fullBackImagePath = in.readString();
        documentValidation = in.readString();
        registryInformation = in.readString();
        if (in.readByte() == 0) {
            responseStatus = null;
        } else {
            responseStatus = in.readInt();
        }
        message = in.readString();
        byte tmpIsFirstTransaction = in.readByte();
        IsFirstTransaction = tmpIsFirstTransaction == 0 ? null : tmpIsFirstTransaction == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(documentNumber);
        dest.writeString(dateOfExpiry);
        if (age == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(age);
        }
        dest.writeString(dateOfBirth);
        dest.writeString(mrzText);
        dest.writeString(sex);
        dest.writeString(barcodeResult);
        dest.writeByteArray(barcodeResultData);
        dest.writeString(isoAlpha2CountryCode);
        dest.writeString(isoAlpha3CountryCode);
        dest.writeString(isoNumericCountryCode);
        dest.writeString(countryName);
        dest.writeString(type);
        dest.writeString(frontImagePath);
        dest.writeString(backImagePath);
        dest.writeString(fullFronImagePath);
        dest.writeString(fullBackImagePath);
        dest.writeString(documentValidation);
        dest.writeString(registryInformation);
        if (responseStatus == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(responseStatus);
        }
        dest.writeString(message);
        dest.writeByte((byte) (IsFirstTransaction == null ? 0 : IsFirstTransaction ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ResponseIV> CREATOR = new Creator<ResponseIV>() {
        @Override
        public ResponseIV createFromParcel(Parcel in) {
            return new ResponseIV(in);
        }

        @Override
        public ResponseIV[] newArray(int size) {
            return new ResponseIV[size];
        }
    };

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfExpiry() {
        return dateOfExpiry;
    }

    public void setDateOfExpiry(String dateOfExpiry) {
        this.dateOfExpiry = dateOfExpiry;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getMrzText() {
        return mrzText;
    }

    public void setMrzText(String mrzText) {
        this.mrzText = mrzText;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBarcodeResult() {
        return barcodeResult;
    }

    public void setBarcodeResult(String barcodeResult) {
        this.barcodeResult = barcodeResult;
    }

    public byte[] getBarcodeResultData() {
        return barcodeResultData;
    }

    public void setBarcodeResultData(byte[] barcodeResultData) {
        this.barcodeResultData = barcodeResultData;
    }

    public String getFrontImagePath() {
        return frontImagePath;
    }

    public void setFrontImagePath(String frontImagePath) {
        this.frontImagePath = frontImagePath;
    }

    public String getBackImagePath() {
        return backImagePath;
    }

    public void setBackImagePath(String backImagePath) {
        this.backImagePath = backImagePath;
    }

    public String getFullFronImagePath() {
        return fullFronImagePath;
    }

    public void setFullFronImagePath(String fullFronImagePath) {
        this.fullFronImagePath = fullFronImagePath;
    }

    public String getFullBackImagePath() {
        return fullBackImagePath;
    }

    public void setFullBackImagePath(String fullBackImagePath) {
        this.fullBackImagePath = fullBackImagePath;
    }

    public String getDocumentValidation() {
        return documentValidation;
    }

    public void setDocumentValidation(String documentValidation) {
        this.documentValidation = documentValidation;
    }

    public String getRegistryInformation() {
        return registryInformation;
    }

    public void setRegistryInformation(String registryInformation) {
        this.registryInformation = registryInformation;
    }

    public Integer getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(Integer responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getFirstTransaction() {
        return IsFirstTransaction;
    }

    public void setFirstTransaction(Boolean firstTransaction) {
        IsFirstTransaction = firstTransaction;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getIsoAlpha2CountryCode() {
        return isoAlpha2CountryCode;
    }

    public void setIsoAlpha2CountryCode(String isoAlpha2CountryCode) {
        this.isoAlpha2CountryCode = isoAlpha2CountryCode;
    }

    public String getIsoAlpha3CountryCode() {
        return isoAlpha3CountryCode;
    }

    public void setIsoAlpha3CountryCode(String isoAlpha3CountryCode) {
        this.isoAlpha3CountryCode = isoAlpha3CountryCode;
    }

    public String getIsoNumericCountryCode() {
        return isoNumericCountryCode;
    }

    public void setIsoNumericCountryCode(String isoNumericCountryCode) {
        this.isoNumericCountryCode = isoNumericCountryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ResponseIV(String firstName, String lastName, String documentNumber, String dateOfExpiry, Integer age, String dateOfBirth, String mrzText, String sex, String barcodeResult, byte[] barcodeResultData, String isoAlpha2CountryCode, String isoAlpha3CountryCode, String isoNumericCountryCode, String countryName, String type, String frontImagePath, String backImagePath, String fullFronImagePath, String fullBackImagePath, String documentValidation, String registryInformation, Integer responseStatus, String message, Boolean isFirstTransaction) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.documentNumber = documentNumber;
        this.dateOfExpiry = dateOfExpiry;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.mrzText = mrzText;
        this.sex = sex;
        this.barcodeResult = barcodeResult;
        this.barcodeResultData = barcodeResultData;
        this.isoAlpha2CountryCode = isoAlpha2CountryCode;
        this.isoAlpha3CountryCode = isoAlpha3CountryCode;
        this.isoNumericCountryCode = isoNumericCountryCode;
        this.countryName = countryName;
        this.type = type;
        this.frontImagePath = frontImagePath;
        this.backImagePath = backImagePath;
        this.fullFronImagePath = fullFronImagePath;
        this.fullBackImagePath = fullBackImagePath;
        this.documentValidation = documentValidation;
        this.registryInformation = registryInformation;
        this.responseStatus = responseStatus;
        this.message = message;
        IsFirstTransaction = isFirstTransaction;
    }


    public ResponseIV(Integer responseStatus, String message) {
        this.responseStatus = responseStatus;
        this.message = message;
    }

    public ResponseIV(Integer responseStatus, Boolean IsFirstTransaction, String documentValidation) {
        this.responseStatus = responseStatus;
        this.IsFirstTransaction = IsFirstTransaction;
        this.documentValidation = documentValidation;
    }

    public ResponseIV(){
    }

    @Override
    public String toString() {
        return "ResponseIV{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", documentNumber='" + documentNumber + '\'' +
                ", dateOfExpiry='" + dateOfExpiry + '\'' +
                ", age=" + age +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", mrzText='" + mrzText + '\'' +
                ", sex='" + sex + '\'' +
                ", barcodeResult='" + barcodeResult + '\'' +
                ", barcodeResultData=" + Arrays.toString(barcodeResultData) +
                ", isoAlpha2CountryCode='" + isoAlpha2CountryCode + '\'' +
                ", isoAlpha3CountryCode='" + isoAlpha3CountryCode + '\'' +
                ", isoNumericCountryCode='" + isoNumericCountryCode + '\'' +
                ", countryName='" + countryName + '\'' +
                ", type='" + type + '\'' +
                ", frontImagePath='" + frontImagePath + '\'' +
                ", backImagePath='" + backImagePath + '\'' +
                ", fullFronImagePath='" + fullFronImagePath + '\'' +
                ", fullBackImagePath='" + fullBackImagePath + '\'' +
                ", documentValidation='" + documentValidation + '\'' +
                ", registryInformation='" + registryInformation + '\'' +
                ", responseStatus=" + responseStatus +
                ", message='" + message + '\'' +
                ", IsFirstTransaction=" + IsFirstTransaction +
                '}';
    }
}
