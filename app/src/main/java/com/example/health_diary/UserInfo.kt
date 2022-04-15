package com.example.health_diary

data class UserInfo(var username : String? = null,
                    var age : Int? = null,
                    var gender : String? = null,
                    var weight : Double? = null,
                    var height : Double? = null,
                    var bmi : Double? = null)