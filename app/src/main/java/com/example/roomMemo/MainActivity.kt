package com.example.roomMemo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.roomMemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
//    val helper = SqliteHelper(this, "memo", 1)
    var helper: RoomDB? = null
    val adapter = RecyclerAdapter()

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this

        //databaseBuilder(db가 생성될 액티비티의 context, RoomDatabase를 상송받은 클래스, db의 이름)
        helper = Room.databaseBuilder(this, RoomDB::class.java, "room_memo").allowMainThreadQueries().build()
        adapter.helper = helper

//        val recyclerMemo = findViewById<RecyclerView>(R.id.recyclerMemo)
//        val buttonAdd = findViewById<Button>(R.id.buttonAdd)

//        adapter.listData.addAll(helper.selectMemo())
        adapter.listData = helper?.roomMemoDao()?.getAll() ?: mutableListOf()
        binding.recyclerMemo.adapter = adapter
        binding.recyclerMemo.layoutManager = LinearLayoutManager(this)

        binding.buttonAdd.setOnClickListener {
            //Toast.makeText(this, "Add button", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, DetailActivity::class.java)

            var memo = RoomMemo(null,"", "", System.currentTimeMillis())
            intent.putExtra("memo", memo)
            startActivityForResult(intent,99)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val memo = data?.getSerializableExtra("returnMemo") as RoomMemo

        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                99 -> {
                    memo.datetime = System.currentTimeMillis()
//                    helper.insertMemo(memo)
                    helper?.roomMemoDao()?.insert(memo)
                    adapter.listData.clear()
//                    adapter.listData.addAll(helper.selectMemo())
                    adapter.listData.addAll(helper?.roomMemoDao()?.getAll() ?: mutableListOf())
                    adapter.notifyDataSetChanged()
                }
                100 -> {
                    memo.datetime = System.currentTimeMillis()
                    helper?.roomMemoDao()?.insert(memo)
                    adapter.listData.clear()
                    adapter.listData.addAll(helper?.roomMemoDao()?.getAll() ?: mutableListOf())
                    adapter.notifyDataSetChanged()
                }
            }
        }

        else if(resultCode == Activity.RESULT_CANCELED){
//            helper.deleteMemo(memo)
            helper?.roomMemoDao()?.delete(memo)
            adapter.listData.clear()
            adapter.listData.addAll(helper?.roomMemoDao()?.getAll() ?: mutableListOf())
            adapter.notifyDataSetChanged()
        }
    }
}