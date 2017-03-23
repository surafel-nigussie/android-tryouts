package location.surafel.com.locationlistener;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;

/**
 * Created by user on 3/2/2017.
 */
public class LocationCoordinateService extends Service {
    private LocationListener location_listener;
    private LocationManager location_manager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        location_listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Intent intent = new Intent("LocationUpdateFilter");
                intent.putExtra("Longitude", location.getLongitude());
                intent.putExtra("Latitude", location.getLatitude());
                intent.putExtra("Altitude", location.getAltitude());
                intent.putExtra("Accuracy", location.getAccuracy());
                intent.putExtra("Provider", location.getProvider());
                intent.putExtra("Speed", location.getSpeed());
                intent.putExtra("Time", location.getTime());
                sendBroadcast(intent);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        };
        location_manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        //noinspection MissingPermission
        location_manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, location_listener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (location_manager != null) {
            //noinspection MissingPermission
            location_manager.removeUpdates(location_listener);
        }
    }
}