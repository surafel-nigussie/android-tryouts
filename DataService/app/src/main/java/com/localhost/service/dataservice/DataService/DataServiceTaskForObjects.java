package com.localhost.service.dataservice.DataService;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.localhost.service.dataservice.Helper.Constants;
import com.localhost.service.dataservice.Helper.GlobalVariables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class DataServiceTaskForObjects extends AsyncTask<String, Void, Boolean> {

    IDataNotification dataNotification;
    Boolean isApiCallFailed = false;
    Boolean post = false;
    String jsonParameter = "";
    int requestStatusCode = 500;
    JSONArray jsonArray;
    JSONObject jsonObject;
    Boolean noData = false;
    Boolean returnFullData = false;
    Boolean showProgress = true;
    Constants constants;
    private int requestCode = 5;

    public DataServiceTaskForObjects(IDataNotification dataNotification, String jsonParameter, int requestCode) {
        this.dataNotification = dataNotification;
        this.jsonParameter = jsonParameter;
        post = true;
        this.requestCode = requestCode;
    }

    public DataServiceTaskForObjects(IDataNotification dataNotification, String jsonParameter, int requestCode, Boolean showProgress) {
        this.dataNotification = dataNotification;
        this.jsonParameter = jsonParameter;
        post = true;
        this.requestCode = requestCode;
        this.showProgress = showProgress;
    }

    public DataServiceTaskForObjects(IDataNotification dataNotification, String jsonParameter) {
        this.dataNotification = dataNotification;
        this.jsonParameter = jsonParameter;
        post = true;
    }

    public AsyncTask<String, Void, Boolean> setPostRequest() {
        this.post = true;
        return this;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (showProgress)
            dataNotification.handelProgressDialog(showProgress, "Connecting to Server");
    }

    @Override
    protected Boolean doInBackground(String... params) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            Log.d("do in background", String.valueOf(i));
            if (isBackgroundtaskRunsSuccessfuly(params[0], params[1]) && count < 3 && requestStatusCode == 200)
                break;
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
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            urlConnection.setRequestProperty("Authorization", "Basic " + (!TextUtils.isEmpty(GlobalVariables.getToken()) ? GlobalVariables.getToken() : Constants.DefaultToken));
        } catch (IOException e) {
            e.printStackTrace();
            status = false;
        }

        try {
            if (jsonParameter.length() != 0) {
                OutputStream os = urlConnection.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                osw.write(jsonParameter);
                osw.flush();
                osw.close();
            }
            requestStatusCode = urlConnection.getResponseCode();
            result.append("");
        } catch (Exception e) {
            e.printStackTrace();
            isApiCallFailed = true;
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
        }

        urlConnection.disconnect();

        try {
            jsonObject = new JSONObject(result.toString());

            if (!returnFullData) {
                jsonArray = jsonObject.getJSONArray("Data");
                if (jsonArray.length() == 0 && !returnFullData) noData = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        urlConnection.disconnect();
        return status;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        constants = new Constants();
        if (showProgress)
            dataNotification.handelProgressDialog(false, "Connecting to Server");
        if (requestStatusCode == 200) {
            if (jsonArray != null) {
                dataNotification.dataReceived(jsonArray, requestCode);
            } else {
                dataNotification.dataObjectReceived(jsonObject, requestCode);
            }
        }
        dataNotification.handelNotification(constants.getErrorMessage(requestStatusCode));
    }
}


