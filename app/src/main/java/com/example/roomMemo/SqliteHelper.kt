package com.example.roomMemo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.Serializable

class SqliteHelper(context: Context, name: String, version: Int): SQLiteOpenHelper(context, name, null, version) {
    override fun onCreate(db: SQLiteDatabase?) {
        val create = "create table memo (" +
                "no integer primary key," +
                "title text," +
                "content text," +
                "datetime integer" +
                ")"
        db?.execSQL(create)
    }
    
    //삽입 메서드
    fun insertMemo(memo: Memo){
        //map과 사용법이 비슷함. 내부 작동 코드만 다름
        val values =  ContentValues()

        //Memo 클래스의 데이터를 key-value 형태로 저장
        values.put("title", memo.title)
        values.put("content", memo.content)
        values.put("datetime", memo.datetime)

        val wd = writableDatabase
        wd.insert("memo", null, values)
        wd.close()
    }

    //조회 메서드
    fun selectMemo(): MutableList<Memo>{
        val list = mutableListOf<Memo>()

        val select = "select * from memo"

        val rd = readableDatabase

        val cursor = rd.rawQuery(select, null)

        while (cursor.moveToNext()) {
            val no = cursor.getInt(cursor.getColumnIndex("no"))
            val title = cursor.getString(cursor.getColumnIndex("title"))
            val content = cursor.getString(cursor.getColumnIndex("content"))
            val datetime = cursor.getLong(cursor.getColumnIndex("datetime"))
            list.add(Memo(no, title, content, datetime))
        }

        cursor.close()
        rd.close()

        return list
    }

    //수정 메서드
    fun updateMemo(memo: Memo){
        val values = ContentValues()
        values.put("title", memo.title)
        values.put("content", memo.content)
        values.put("datetime", memo.datetime)

        //update(테이블 명, 수정할 값, 수정할 조건, null)
        val wd = writableDatabase
        wd.update("memo", values, "no = ${memo.no}", null)
        wd.close()
    }

    //삭제 메서드
    fun deleteMemo(memo: Memo){
        val delete = "delete from memo where no = ${memo.no}"
        val db = writableDatabase

        db.execSQL(delete)
        db.close()
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) { }

}

data class Memo(var no: Int?, var title: String, var content: String, var datetime: Long): Serializable {
    constructor(title: String, content: String, datetime: Long): this(null, title, content, datetime)
}