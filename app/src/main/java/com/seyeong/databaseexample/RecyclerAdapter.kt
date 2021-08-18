package com.seyeong.databaseexample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seyeong.databaseexample.databinding.ItemRecyclerBinding
import java.text.SimpleDateFormat

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.Holder>() {
    var helper : SqliteHelper? = null
    var listDate = mutableListOf<Memo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return listDate.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val memo = listDate.get(position)
        holder.setMemo(memo)
    }

    inner class Holder(val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        var mMemo: Memo? = null // 홀더를 재사용하는 구조때문에 클릭하는 시점에 어떤 데이터가 있는지 알기 위해 setMemo() 메서드로 넘어온 Memo를 임시 저장
        init {
            binding.buttonDelete.setOnClickListener {
                helper?.deleteMemo(mMemo!!)
                // .deleteMemo 메서드의 매개변수는 null값을 허용하지 않는데 여기로 넘겨주는 mMemo는 null값이 허용된다.
                // 따라서 null값을 매개변수로 넘겨주는 일을 방지하고자 강제 not null 처리 키워드인 !!로 구현.
                listDate.remove(mMemo)
                notifyDataSetChanged()
            }
        }
        fun setMemo(memo: Memo) {
            binding.textNo.text = "${memo.no}"
            binding.textContent.text = memo.content
            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")
            binding.textDatetime.text = "${sdf.format(memo.datetime)}"
            this.mMemo = memo
        }
    }

}

