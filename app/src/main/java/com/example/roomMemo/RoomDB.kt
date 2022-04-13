package com.example.roomMemo

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(RoomMemo::class), version = 1, exportSchema = false)
abstract class RoomDB: RoomDatabase() {
    abstract fun roomMemoDao(): RoomMemoDao
}