package com.seyeong.databaseexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.seyeong.databaseexample.databinding.ActivityMainBinding
import com.seyeong.databaseexample.databinding.ActivityMainBinding.inflate

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val helper = SqliteHelper(this, "memo", 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = RecyclerAdapter()
        adapter.listDate.addAll(helper.selectMemo()) // addAll?. adapter의 listDate에 데이터베이스에서 가져온 데이터를 세팅합니다.

        binding.recyclerMemo.adapter = adapter // 레이아웃.xml의 리사이클러뷰 위젯에 adapter를 연결하고 레이아웃 매니저를 설정한다.
        binding.recyclerMemo.layoutManager = LinearLayoutManager(this)

        binding.buttonSave.setOnClickListener { // 버튼을 클릭하면.
            if (binding.editMemo.text.toString().isNotEmpty()) { // 텍스트가 공백이 아니라면
                val memo = Memo(null, binding.editMemo.text.toString(), System.currentTimeMillis()) // Memo 타입으로 번호에는 null값을, 그리고 메모값과 현재 시각을 넘겨준다.

                helper.insertMemo(memo) // helper 클래스의 insertMemo() 메서드에 앞에서 생성한 memo를 전달해 데이터베이스에 저장합니다.
                adapter.listDate.clear() // 어댑터의 데이터를 모두 초기화합니다.

                binding.editMemo.setText("") // EditText의 값을 초기화합니다.
            }
        }
    }
}