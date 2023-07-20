package d.tmesaric.divebuddy.domain.location

import android.location.Location
import d.tmesaric.divebuddy.domain.model.User
import kotlinx.coroutines.flow.Flow

interface LocationClient {
    fun getLocation(): Location
    fun saveLocation(user: User)
    class LocationException(message: String) : Exception()
}