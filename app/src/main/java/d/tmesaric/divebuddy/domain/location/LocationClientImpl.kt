package d.tmesaric.divebuddy.domain.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.location.LocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import d.tmesaric.divebuddy.domain.model.User
import kotlinx.coroutines.CompletableDeferred
import javax.inject.Inject

class LocationClientImpl @Inject constructor(
    private val context: Context,
    private val client: FusedLocationProviderClient,
) : LocationClient {

    @SuppressLint("MissingPermission")
    override suspend fun getLocation(): Location {
        var userLocation = Location("UserLocation")
        val userLocationDeferred = CompletableDeferred<Location>()

        if(checkAllPermissions(context)){
            client.getCurrentLocation(
                LocationRequest.QUALITY_HIGH_ACCURACY,
                object : CancellationToken() {
                    override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                        CancellationTokenSource().token
                    override fun isCancellationRequested() = false
                }
            ).addOnSuccessListener { location: Location? ->
                if (location == null)
                    userLocationDeferred.completeExceptionally(
                        LocationClient.LocationException("Cannot get location")
                    )
                else {
                    // fake location so it works in emulator,
                    // comment when testing with real device
                    location.latitude = 43.38090
                    location.longitude = 16.56144
                    userLocation.latitude = location.latitude
                    userLocation.longitude = location.longitude
                    userLocationDeferred.complete(location)
                }
            }
        }
        userLocation = userLocationDeferred.await()
        return userLocation;
    }

    private fun checkAllPermissions(context: Context): Boolean {
        try {
            if (!LocationHelper().checkLocationPermission(context)) {
                if(!LocationHelper().askForLocationPermissions())
                    return false
            }
            val locationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) &&
                !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            ) {
                return false
            }
            return true
        } catch (e: SecurityException) {
            e.printStackTrace()
            return false
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }


    override suspend fun updateLocation(user: User) {
        val userPosition = getLocation()
        user.lastKnownPosition = userPosition
    }
}

