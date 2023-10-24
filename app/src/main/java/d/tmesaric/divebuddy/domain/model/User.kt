package d.tmesaric.divebuddy.domain.model

import android.location.Location

data class User(
    val id: Int = 1,
    val name: String = "Test",
    val email: String = "Test",
    val country: String = "Test",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    var lastKnownPosition: Location? = Location("Test"),
    val chats: MutableList<Chat>? = null,
    val depth: Int,
    val dynamic: Int,
    val staticMinutes: Int,
    val staticSeconds: Int
)

fun getLastKnownLocation(lat: Double, lng: Double): Location {
    val lastKnownPosition = Location("Location").also {
        it.latitude = lat
        it.longitude = lng
    }
    return lastKnownPosition;
}
