package d.tmesaric.divebuddy.domain.model

import android.location.Location

data class User(
    val id: String,
    val name: String,
    val email: String,
    val country: Country
    //val lastKnownPosition: LatLng,
) {

    fun getLocation(){
        // This will calculate position which will be used to calculate distance between users
        // TODO
    }
}