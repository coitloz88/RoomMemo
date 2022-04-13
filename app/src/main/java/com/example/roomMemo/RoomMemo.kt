package com.example.roomMemo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "orm_memo")
data class RoomMemo (
    @PrimaryKey(autoGenerate = true) var no: Long?,
    var title: String,
    var content: String,
    var datetime: Long = 0
) : Serializable