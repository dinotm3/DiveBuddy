package d.tmesaric.divebuddy.utils

fun redirect(userLoggedIn: Boolean, ): String {
    var startDestination = "sign_in"
    if (userLoggedIn) {
        startDestination = "profile"
    }
    return startDestination;
}