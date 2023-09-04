package d.tmesaric.divebuddy.domain.model

import android.location.Location
import com.google.android.gms.location.LocationServices

data class User(
    val id: Int = 1,
    val name: String = "Test",
    val email: String = "Test",
    val country: String = "Test",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    var lastKnownPosition: Location = Location("Test")
)

fun getLastKnownLocation(lat: Double, lng: Double): Location {
    val lastKnownPosition = Location("Location").also {
        it.latitude = lat
        it.longitude = lng
    }
    return lastKnownPosition;
}
