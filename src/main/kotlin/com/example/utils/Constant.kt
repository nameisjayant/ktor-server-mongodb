package com.example.utils


object Constant {

    const val DATABASE_NAME = "user_table"
    const val USERNAME = "username"
    const val PASSWORD = "password"
    const val USER = "/user"
    const val EMAIL_PATTERN =
        "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@" + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?" + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\." + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?" + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|" + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
    const val PLEASE_ENTER_EMAIL = "Please enter email";
    const val PLEASE_ENTER_PASSWORD = "Please enter password"
    const val EMAIL_IS_NOT_VALID = "Email is not valid"
    const val EMAIL_ALREADY_EXITS = "Email already exits"
    const val PASSWORD_SHOULD_NOT_EMPTY = "Password should not empty"
    const val USER_REGISTER_SUCCESSFULLY = "User Register Successfully"
    const val EMAIL_SHOULD_NOT_EMPTY = "Email should not empty"
    const val USER_DELETED_SUCCESSFULLY = "User Deleted Successfully"
    const val USER_NOT_FOUND = "User Not Found"
    const val ZERO = "0"
    const val LOGGED_SUCCESSFULLY = "Logged Successfully"
    const val EMAIL_PASSWORD_DOES_NOT_MATCHED = "Email and Password does not Matched"
    const val UPDATE_SUCCESSFULLY = "Updated Successfully"
    const val USER_ID_MISMATCHED = "User ID Mismatched"
    const val EMAIL_DID_NOT_CHANGED = "Email and Password did not changed"
    const val NOTE_ADDED_SUCCESSFULLY = "Note Added Successfully"
    const val INVALID_TOKEN = "Invalid Token"
    const val PASS_TOKEN_IN_THE_HEADER =  "Please Pass Token In The Header"
    const val NOTE_DELETED_SUCCESSFULLY = "Note Deleted Successfully"
    const val NOTE_ID_NOT_FOUND = "Note Id Not Found"
    const val NOTE_UPDATE_SUCCESSFULLY = "Note Updated Successfully"
}