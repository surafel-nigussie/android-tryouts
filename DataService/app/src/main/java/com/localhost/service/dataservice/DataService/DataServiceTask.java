package com.localhost.service.dataservice.DataService;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.localhost.service.dataservice.Helper.Constants;
import com.localhost.service.dataservice.Helper.GlobalVariables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class DataServiceTask extends AsyncTask<String, Void, Boolean> {
    JSONArray jsonArray;
    JSONObject jsonObject;
    IDataNotification dataNotification;
    Boolean isApiCallFailed = false;
    Boolean noData = false;
    Boolean showProgressbar = true;
    Boolean returnFullData = false;
    Boolean post = false;
    Constants constant;
    private int requestCode = 0;

    public DataServiceTask(IDataNotification dataNotification) {
        this.dataNotification = dataNotification;
    }

    public DataServiceTask(IDataNotification iDataNotification, int requestCode) {
        this.dataNotification = iDataNotification;
        this.requestCode = requestCode;
    }

    public DataServiceTask(IDataNotification iDataNotification, int requestCode, boolean returnFullData, boolean showProgressbar) {
        this.dataNotification = iDataNotification;
        this.requestCode = requestCode;
        this.showProgressbar = showProgressbar;
    }

    public DataServiceTask(IDataNotification iDataNotification, boolean returnFullData) {
        this.dataNotification = iDataNotification;
        this.returnFullData = returnFullData;
    }

    public DataServiceTask(IDataNotification iDataNotification, int requestCode, boolean returnFullData) {
        this.dataNotification = iDataNotification;
        this.returnFullData = returnFullData;
        this.requestCode = requestCode;
    }

    public AsyncTask<String, Void, Boolean> setPostRequest() {
        this.post = true;
        return this;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        constant = new Constants();
        if (showProgressbar) dataNotification.handelProgressDialog(true, "");
    }

    @Override
    protected Boolean doInBackground(String... params) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            if (isBackgroundtaskRunsSuccessfuly(params[0], params[1]) && count < 3) break;
        }
        return null;
    }

    @SuppressLint("LongLogTag")
    private boolean isBackgroundtaskRunsSuccessfuly(String param, String token) {
        boolean status = true;
        isApiCallFailed = false;
        StringBuilder result = new StringBuilder();
        URL url = null;

        try {
            url = new URL(Constants.BaseUrl + param);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            status = false;
        }

        HttpURLConnection urlConnection = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(post);
            urlConnection.setRequestMethod(post ? "POST" : "GET");
            urlConnection.setRequestProperty("Authorization", "Basic "
                    + (!TextUtils.isEmpty(GlobalVariables.getToken()) ? GlobalVariables.getToken()
                    : Constants.DefaultToken));
        } catch (Exception e) {
            e.printStackTrace();
            status = false;
        }

        InputStream inputStream = null;

        try {
            InflaterInputStream in = new InflaterInputStream((urlConnection.getInputStream()), new Inflater(true));
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            isApiCallFailed = true;
            status = false;
        }

        urlConnection.disconnect();

        try {
            if (!post) {
                jsonObject = new JSONObject(result.toString());
                if (!returnFullData) {
                    try {
                        jsonArray = jsonObject.getJSONArray("Data");
                        if (jsonArray.length() == 0 && !returnFullData) {
                            noData = true;
                        }
                    } catch (Exception e) {
                        noData = true;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            status = false;
        }
        return status;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        if (showProgressbar) dataNotification.handelProgressDialog(false, "");

        if (isApiCallFailed) {
            dataNotification.handelNotification(constant.getErrorMessage(102));
            dataNotification.readFromDatabase(requestCode);
        } else if (noData) dataNotification.handelNotification("No Data");
        else {
            if (returnFullData)
                dataNotification.dataObjectReceived(jsonObject, requestCode);
            else if (jsonArray == null) {
                dataNotification.handelNotification("No Data");
            } else {
                dataNotification.dataReceived(jsonArray, requestCode);
            }
        }
    }
}


