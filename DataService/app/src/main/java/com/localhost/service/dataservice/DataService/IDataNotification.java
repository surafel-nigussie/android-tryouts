package com.localhost.service.dataservice.DataService;

import org.json.JSONArray;
import org.json.JSONObject;

public interface IDataNotification {
    void dataObjectReceived(JSONObject jsonObject, int requestCode);

    void dataReceived(JSONArray jsonArray, int requestCode);

    void handelNotification(String message);

    void handelProgressDialog(Boolean showProgress, String Message);

    void handelProgressDialog(Boolean showProgress);

    void readFromDatabase(int requestCode);
}
