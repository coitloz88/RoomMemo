package com.example.roomMemo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface RoomMemoDao {
    @Query("select * from orm_memo")
    fun getAll(): MutableList<RoomMemo>

    //onConflict 옵션을 이용해서 update를 대신함
    @Insert(onConflict = REPLACE)
    fun insert(memo: RoomMemo)

    @Delete
    fun delete(memo: RoomMemo)
}