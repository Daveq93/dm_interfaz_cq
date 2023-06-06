package com.uce.edu.logic.validator

import com.uce.edu.entity.LoginUser

class LoginValidator {
    fun checkLogin(email:String, pass:String):Boolean{
        val admin = LoginUser()
        return (admin.email==email && admin.pass==pass)

    }
}