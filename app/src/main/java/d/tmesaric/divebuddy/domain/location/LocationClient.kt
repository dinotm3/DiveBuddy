package d.tmesaric.divebuddy.domain.location

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationClient {
    fun getLocation(): Location

    class LocationException(message: String): Exception()
}