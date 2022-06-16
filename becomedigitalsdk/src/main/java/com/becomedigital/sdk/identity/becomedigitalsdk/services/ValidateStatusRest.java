package com.becomedigital.sdk.identity.becomedigitalsdk.services;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.becomedigital.sdk.identity.becomedigitalsdk.R;
import com.becomedigital.sdk.identity.becomedigitalsdk.callback.AsynchronousTask;
import com.becomedigital.sdk.identity.becomedigitalsdk.models.BDIVConfig;
import com.becomedigital.sdk.identity.becomedigitalsdk.models.ResponseIV;
import com.becomedigital.sdk.identity.becomedigitalsdk.utils.SharedParameters;
import com.becomedigital.sdk.identity.becomedigitalsdk.utils.UserAgentInterceptor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ValidateStatusRest {
    private final int GETCONTRACT = 5;
    private final int ADDDATARESPONSE = 2;

    public void getContract(String contractId, String access_token, final Activity activity, final AsynchronousTask asynchronousTask) {
        AsyncTask.execute(() -> {
            try {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(activity.getResources().getInteger(R.integer.timeOut), TimeUnit.SECONDS)
                        .readTimeout(activity.getResources().getInteger(R.integer.timeOut), TimeUnit.SECONDS)
                        .writeTimeout(activity.getResources().getInteger(R.integer.timeOut), TimeUnit.SECONDS).build();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity); // get url
                String serverUrl = preferences.getString(SharedParameters.URL_GET_CONTRACT, SharedParameters.url_get_contract);
                String urlContractId = serverUrl + contractId;
                Request request = new Request.Builder()
                        .url(urlContractId)
                        .header("Authorization", "Bearer " + access_token)
                        .get()
                        .build();

                Call call = client.newCall(request);

                call.enqueue(new Callback() {

                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onFailure(Call call, IOException e) {
                        asynchronousTask.onErrorTransaction(e.getLocalizedMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            String jsonData = response.body().string();
                            JSONObject Jobject = new JSONObject(jsonData);
                            Map<String, Object> map = new HashMap<String, Object>();

                            if (Jobject.has("msg")) {
                                map.put("mensaje", Jobject.getString("msg"));
                                asynchronousTask.onReceiveResultsTransactionDictionary(map, ResponseIV.ERROR, GETCONTRACT);
                            } else if (Jobject.has("canUseOnexOne") &&
                                    Jobject.has("countIsOnexOne") &&
                                    Jobject.has("maxIsOnexOne")) {
                                map.put("canUseOnexOne", Jobject.getBoolean("canUseOnexOne"));
                                map.put("countIsOnexOne", Jobject.getInt("countIsOnexOne"));
                                map.put("maxIsOnexOne", Jobject.getInt("maxIsOnexOne"));
                                asynchronousTask.onReceiveResultsTransactionDictionary(map, ResponseIV.SUCCES, GETCONTRACT);
                            } else {
                                map.put("mensaje", activity.getResources().getString(R.string.general_error));
                                asynchronousTask.onReceiveResultsTransactionDictionary(map, ResponseIV.ERROR, GETCONTRACT);
                            }
                        } catch (JSONException e) {
                            asynchronousTask.onErrorTransaction(e.getLocalizedMessage());
                        }
                    }
                });


            } catch (Exception e) {
                asynchronousTask.onErrorTransaction(e.getLocalizedMessage());
            }
        });
    }

    public void addDataServer(final Activity activity,
                              BDIVConfig config,
                              String ua,
                              final AsynchronousTask asynchronousTask) {
        AsyncTask.execute(() -> {
            try {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity); // get url
                String serverUrl = preferences.getString(SharedParameters.URL_ADD_DATA, SharedParameters.url_add_data);
                File document = new File(config.getFrontImagePath());
                Log.i("BECOME_IV_SDK", "userId: " + config.getUserId());
                Log.i("BECOME_IV_SDK", "contractId: " + config.getContractId());
                Log.i("BECOME_IV_SDK", "image file: " + config.getFrontImagePath());
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("contractId", config.getContractId())
                        .addFormDataPart("userId", config.getUserId())
                        .addFormDataPart("file", "file.jpg", RequestBody.create(MediaType.parse("image/jpg"), document))
                        .build();

                OkHttpClient client = new OkHttpClient.Builder()
                        .addInterceptor(new UserAgentInterceptor(ua))
                        .connectTimeout(activity.getResources().getInteger(R.integer.timeOut), TimeUnit.SECONDS)
                        .readTimeout(activity.getResources().getInteger(R.integer.timeOut), TimeUnit.SECONDS)
                        .writeTimeout(activity.getResources().getInteger(R.integer.timeOut), TimeUnit.SECONDS)
                        .build();

                Request request = new Request.Builder()
                        .header("Authorization", "Bearer " + config.getToken())
                        .url(serverUrl)
                        .post(requestBody)
                        .build();

                Call call = client.newCall(request);

                call.enqueue(new Callback() {

                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onFailure(Call call, IOException e) {
                        asynchronousTask.onErrorTransaction(e.getLocalizedMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            Log.i("BECOME_IV_SDK", "El servidor respondió.");
                            String jsonData = response.body().string();
                            JSONObject Jobject = new JSONObject(jsonData);
                            if (Jobject.has("status_code")) {
                                if (Jobject.getString("status_code").equals("OK")) {
                                    asynchronousTask.onReceiveResultsTransaction(new ResponseIV(ResponseIV.SUCCES, false, Jobject.toString()), ADDDATARESPONSE);
                                } else {
                                    asynchronousTask.onReceiveResultsTransaction(new ResponseIV(ResponseIV.ERROR, Jobject.toString()), ADDDATARESPONSE);
                                }
                            } else {
                                if (Jobject.has("msg")) {
                                    asynchronousTask.onReceiveResultsTransaction(new ResponseIV(ResponseIV.ERROR, Jobject.getString("msg")), ADDDATARESPONSE);
                                }
                                if (Jobject.has("error")) {
                                    asynchronousTask.onReceiveResultsTransaction(new ResponseIV(ResponseIV.ERROR, Jobject.getString("error")), ADDDATARESPONSE);
                                }
                            }

                        } catch (JSONException e) {
                            asynchronousTask.onErrorTransaction(e.getLocalizedMessage());
                        }
                    }
                });


            } catch (Exception e) {
                asynchronousTask.onErrorTransaction(e.getLocalizedMessage());
            }
        });
    }


}
