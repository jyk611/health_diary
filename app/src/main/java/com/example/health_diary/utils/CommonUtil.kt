package com.example.health_diary.utils

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Context.toast(msg: String) {
  return Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}