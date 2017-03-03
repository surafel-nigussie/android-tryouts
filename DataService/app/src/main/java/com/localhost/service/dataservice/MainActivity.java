package com.localhost.service.dataservice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.localhost.service.dataservice.DataService.DataServiceTask;
import com.localhost.service.dataservice.DataService.IDataNotification;
import com.localhost.service.dataservice.Model.StudentModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IDataNotification {
    Button bt_callService;
    TextView tv_resultText;
    ArrayList<StudentModel> student_model_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_resultText = (TextView) findViewById(R.id.tv_resultText);
        bt_callService = (Button) findViewById(R.id.bt_callService);
        bt_callService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_resultText.getVisibility() == View.GONE) {
                    tv_resultText.setVisibility(View.VISIBLE);
                    tv_resultText.setText("VISIBLE");
                } else {
                    tv_resultText.setVisibility(View.GONE);
                    tv_resultText.setText("GONE");
                }
            }
        });

        new DataServiceTask(this, 1, false, false).execute("StudentCtrl/students", "");
    }

    @Override
    public void dataObjectReceived(JSONObject jsonObject, int requestCode) {

    }

    @Override
    public void dataReceived(JSONArray jsonArray, int requestCode) {
        if (requestCode == 1) {
            try {
                student_model_list = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    StudentModel student_model = new StudentModel();
                    JSONObject jo = jsonArray.getJSONObject(i);
                    student_model.setStudent_ID(jo.getInt("ID"));
                    student_model.setStudent_FirstName(jo.getString("FirstName"));
                    student_model.setStudent_LastName(jo.getString("LastName"));
                    student_model.setStudent_ProfilePicture(jo.getBoolean("Profile_Picture"));
                    student_model.setStudent_Apikey(jo.getString("Apikey"));
                    student_model_list.add(student_model);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void handelNotification(String message) {

    }

    @Override
    public void handelProgressDialog(Boolean showProgress, String Message) {

    }

    @Override
    public void handelProgressDialog(Boolean showProgress) {

    }

    @Override
    public void readFromDatabase(int requestCode) {

    }
}
