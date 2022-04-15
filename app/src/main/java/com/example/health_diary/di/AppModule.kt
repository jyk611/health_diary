package com.example.health_diary.di

import android.content.Context
import androidx.room.Room
import com.example.health_diary.database.FoodDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  @Singleton
  @Provides
  fun provideDatabase(
    @ApplicationContext context: Context
  ) = Room.databaseBuilder(
    context,
    FoodDb::class.java,
    "food.db"
  )
    .fallbackToDestructiveMigration()
    .build()

  @Singleton
  @Provides
  fun provideFoodDao(db: FoodDb) = db.foodDao()
}