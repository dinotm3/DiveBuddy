package d.tmesaric.divebuddy.data

import d.tmesaric.divebuddy.domain.model.User
import retrofit2.http.GET
import retrofit2.http.POST

interface UsersApi {
    @GET("/user")
    suspend fun getUser(id: String): User

    @GET("/users")
    suspend fun getUsers(): List<User>

    @POST("/user/update/location")
    suspend fun updateUserPosition(id: Int, lat: Double, lng: Double)
}