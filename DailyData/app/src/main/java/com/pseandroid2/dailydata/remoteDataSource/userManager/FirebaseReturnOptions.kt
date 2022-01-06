package com.pseandroid2.dailydata.remoteDataSource.userManager

/**
 * @return true: alles ist wie geplant verlaufen (kein Fehler)
 *         false: etwas ist schief gelaufen
 */
enum class FirebaseReturnOptions (val success: Boolean){
    REGISTERED(true),
    REGISTRATION_FAILED(false),
    LOGIN(true),
    WRONG_LOGIN_VALUES(false),
    SINGED_IN(true),
    SIGN_IN_FAILED(false),
    SINGED_OUT(true),

    NOT_PROCCESSED(false) // Wenn die Firebaseanfrage noch nicht fertig ist
}