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

    }
}