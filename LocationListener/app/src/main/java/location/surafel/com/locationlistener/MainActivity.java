package location.surafel.com.locationlistener;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tv_longitude, tv_latitude, tv_altitude, tv_accuracy, tv_provider, tv_speed, tv_time;
    BroadcastReceiver location_receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_longitude = (TextView) findViewById(R.id.tv_longitude);
        tv_latitude = (TextView) findViewById(R.id.tv_latitude);
        tv_altitude = (TextView) findViewById(R.id.tv_altitude);
        tv_accuracy = (TextView) findViewById(R.id.tv_accuracy);
        tv_provider = (TextView) findViewById(R.id.tv_provider);
        tv_speed = (TextView) findViewById(R.id.tv_speed);
        tv_time = (TextView) findViewById(R.id.tv_time);

        if (!runtime_permissions()) {
            runtime_permissions();
        }

        Intent intent = new Intent(this, LocationCoordinateService.class);
        startService(intent);
    }

    private boolean runtime_permissions() {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (location_receiver == null) {
            location_receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    tv_longitude.setText(intent.getExtras().get("Longitude") + "");
                    tv_latitude.setText(intent.getExtras().get("Latitude") + "");
                    tv_altitude.setText(intent.getExtras().get("Altitude") + "");
                    tv_accuracy.setText(intent.getExtras().get("Accuracy") + "");
                    tv_provider.setText(intent.getExtras().get("Provider") + "");
                    tv_speed.setText(intent.getExtras().get("Speed") + "");
                    tv_time.setText(intent.getExtras().get("Time") + "");
                }
            };
        }
        registerReceiver(location_receiver, new IntentFilter("LocationUpdateFilter"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(location_receiver);
    }
}
