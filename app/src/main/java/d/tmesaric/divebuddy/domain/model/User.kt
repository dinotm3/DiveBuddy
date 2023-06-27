package d.tmesaric.divebuddy.domain.model

import android.location.Location
import com.google.android.gms.location.LocationServices

data class User(
    val id: String,
    val name: String,
    val email: String,
    val country: Country,
    val lastKnownPosition: Location
) {

    fun calculateLastKnownPosition(){
        // This will calculate position which will be used to calculate distance between users
        // TODO

    }
}