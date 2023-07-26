package d.tmesaric.divebuddy.data

import d.tmesaric.divebuddy.domain.model.User
import retrofit2.http.GET

interface UsersApi {
    @GET("/user")
    suspend fun getUser(): User

    @GET("/users")
    suspend fun getUsers(): List<User>
}