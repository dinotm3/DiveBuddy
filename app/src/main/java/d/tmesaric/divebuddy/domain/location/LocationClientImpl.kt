package d.tmesaric.divebuddy.domain.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.location.LocationRequest
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import d.tmesaric.divebuddy.domain.model.User

class LocationClientImpl(
    private val context: Context,
    private val client: FusedLocationProviderClient
) : LocationClient {

    @SuppressLint("MissingPermission")
    override fun getLocation(): Location {
        if (!context.hasLocationPermission()) {
            throw LocationClient.LocationException("Missing location permission")
        }

        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled =
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (!isGpsEnabled && !isNetworkEnabled) {
            throw LocationClient.LocationException("GPS is disabled")
        }

        val userLocation = Location("UserLocation")
        client.getCurrentLocation(
            LocationRequest.QUALITY_HIGH_ACCURACY,
            object : CancellationToken() {
                override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                    CancellationTokenSource().token

                override fun isCancellationRequested() = false
            }).addOnSuccessListener { location: Location? ->
            if (location == null)
                Toast.makeText(context, "cannot get location", Toast.LENGTH_SHORT)
                    .show()
            else {
                userLocation.latitude = location.latitude
                userLocation.longitude = location.longitude
/*                Toast.makeText(context, "User lng is ${location.longitude}, ltd is ${location.latitude}", Toast.LENGTH_LONG)
                    .show()*/
            }
        }
        return userLocation;
    }

    override fun saveLocation(user: User) {
        user.lastKnownPosition = getLocation()
    }
}

