package d.tmesaric.divebuddy.common

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationRequest
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import d.tmesaric.divebuddy.domain.location.LocationClientImpl
import d.tmesaric.divebuddy.domain.model.User
import d.tmesaric.divebuddy.presentation.finder.FinderViewModel

class LocationHelper {
    fun checkLocationPermission(context: Context): Boolean {
        var hasLocationPermission = false
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            hasLocationPermission = true
        }
        return hasLocationPermission
    }

    fun askForLocationPermissions(): Boolean {
        TODO("Not yet implemented")
    }

    @SuppressLint("MissingPermission")
    fun updateRangeFilter(
        fusedLocationProviderClient: FusedLocationProviderClient,
        context: Context,
        viewModel: FinderViewModel,
        sliderPosition: Float
    ){
        fusedLocationProviderClient.getCurrentLocation(
            LocationRequest.QUALITY_HIGH_ACCURACY,
            @SuppressLint("MissingPermission")
            object : CancellationToken() {
                override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                    CancellationTokenSource().token

                override fun isCancellationRequested() = false
            }
        ).addOnSuccessListener { location: Location? ->
            if (location == null)
                Toast.makeText(
                    context,
                    "Cannot get location",
                    Toast.LENGTH_SHORT
                ).show()
            else {
                // fake location so it works in emulator,
                // comment when testing with real device
                location.latitude = 43.38090
                location.longitude = 16.56144
                viewModel.filterUsersInRange(
                    sliderPosition,
                    location,
                )
            }
        }
    }
}