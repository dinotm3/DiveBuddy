package d.tmesaric.divebuddy.domain.model

import android.location.Location
import com.google.android.gms.location.LocationServices

data class User(
    val id: String = "Test",
    val name: String = "Test",
    val email: String = "Test",
    val country: String = "Test",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    var lastKnownPosition: Location = Location("Test")
)

fun getLastKnownLocation(lat: Double, lng: Double): Location {
    val lastKnownPosition = Location("Location")
    lastKnownPosition.latitude = lat
    lastKnownPosition.longitude = lng
    return lastKnownPosition;
}
