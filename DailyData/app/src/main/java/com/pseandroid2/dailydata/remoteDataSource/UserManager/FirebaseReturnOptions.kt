package com.pseandroid2.dailydata.remoteDataSource.UserManager

/**
 * @return true: alles ist wie geplant verlaufen (kein Fehler)
 *         false: etwas ist schief gelaufen
 */
enum class FirebaseReturnOptions (val success: Boolean){
    REGISTERED(true),
    REGISTRATION_FAILED(false),
    LOGIN(true),
    WRONG_LOGIN_VALUES(false),
    NOT_PROCCESSED(false) // Wenn die Firebaseanfrage noch nicht fertig ist
}