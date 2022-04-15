package com.example.health_diary

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import kotlin.text.Typography.dagger

@HiltAndroidApp
class MainApplication: Application() {
  override fun onCreate() {
    super.onCreate()

    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
  }
}