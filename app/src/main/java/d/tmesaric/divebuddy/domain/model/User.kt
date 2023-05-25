package d.tmesaric.divebuddy.domain.model

import android.location.Location

data class User(
    val id: String,
    val name: String,
    val email: String,
    //val lastKnownPosition: LatLng,
    // Other user-related properties...
) {
//    fun calculateDistanceToUser(user: User): Float {
//        val results = FloatArray(1)
//        Location.distanceBetween(
//            lastKnownPosition.latitude,
//            lastKnownPosition.longitude,
//            user.lastKnownPosition.latitude,
//            user.lastKnownPosition.longitude,
//            results
//        )
//        return results[0]
//    }
}