package com.example.user.okhttp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView progress;
    ProgressBar progressbar;
    List<MyAsyncTask> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress = (TextView) findViewById(R.id.progresstext);
        progressbar = (ProgressBar) findViewById(R.id.progressBar);
        progressbar.setVisibility(View.INVISIBLE);
        tasks = new ArrayList<>();
        Button btn = (Button) findViewById(R.id.dotaskbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline()) {
                    Toast.makeText(MainActivity.this, "Online", Toast.LENGTH_LONG).show();
                    MakeServiceCall();
                } else {
                    Toast.makeText(MainActivity.this, "Offline", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void UpdateView(String param) {
        progress.append("\n" + param);
    }

    protected boolean isOnline() {
        ConnectivityManager con_manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net_info = con_manager.getActiveNetworkInfo();
        return net_info != null && net_info.isConnectedOrConnecting();
    }

    public void MakeServiceCall() {
        MyAsyncTask task = new MyAsyncTask();
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "Parameter 1", "Parameter 2", "Parameter 3");
    }

    private class MyAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            UpdateView("Task Started!");

            if (tasks.size() == 0) {
                progressbar.setVisibility(View.VISIBLE);
            }
            tasks.add(this);
        }

        @Override
        protected String doInBackground(String... params) {
            for (String param : params) {
                publishProgress(param);
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return "Task Completed!";
        }


        @Override
        protected void onProgressUpdate(String... values) {
            UpdateView(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            UpdateView(result);

            tasks.remove(this);
            if (tasks.size() == 0) {
                progressbar.setVisibility(View.INVISIBLE);
            }
        }
    }
}
