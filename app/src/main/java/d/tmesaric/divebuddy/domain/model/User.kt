package d.tmesaric.divebuddy.domain.model

import android.location.Location
import com.google.android.gms.location.LocationServices

data class User(
    val id: String = "Test",
    val name: String = "Test",
    val email: String = "Test",
    val country: String = "Test",
    var lastKnownPosition: Location = Location("Test")
)

fun getLastKnownPosition(lat: Double, lng: Double): Location {
    val lastKnownPosition = Location("Location")
    lastKnownPosition.latitude = lat
    lastKnownPosition.longitude = lng
    return lastKnownPosition;
}
