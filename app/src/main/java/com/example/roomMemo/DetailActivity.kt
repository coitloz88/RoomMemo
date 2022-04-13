package com.example.roomMemo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.roomMemo.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    lateinit var binding:ActivityDetailBinding
    var title = ""
    var content = ""
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

//        val editTitle = findViewById<EditText>(R.id.editTitle)
//        val editContent = findViewById<EditText>(R.id.editContent)
//        val imageSave = findViewById<ImageButton>(R.id.imageSave)
//        val imageDelete = findViewById<ImageButton>(R.id.imageDelete)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.activity = this

        var memo = intent.getSerializableExtra("memo") as RoomMemo

//        binding.editTitle.setText(memo.title)
//        binding.editContent.setText(memo.content)

        title = memo.title
        content = memo.content

        binding.imageSave.setOnClickListener {
            //제목과 내용이 둘 다 있을 때 main으로 돌아가기
            if (title.isNotEmpty() && content.isNotEmpty()){
                memo.title = title
                memo.content = content
                
                val returnIntent = Intent()
                returnIntent.putExtra("returnMemo", memo)
                
                //결과값 설정
                setResult(Activity.RESULT_OK, returnIntent)
                
                //액티비티 종료
                finish()
            }
            else if (title.isNotEmpty()){
                Toast.makeText(this, "내용이 비어있습니다.", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "제목이 비어있습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imageDelete.setOnClickListener {
            setResult(Activity.RESULT_CANCELED, Intent().putExtra("returnMemo", memo))
            finish()
        }
    }
}