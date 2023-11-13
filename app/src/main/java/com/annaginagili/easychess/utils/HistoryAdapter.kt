package com.annaginagili.easychess.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.annaginagili.easychess.R
import com.annaginagili.easychess.databinding.HistoryLayoutBinding
import com.annaginagili.easychess.model.History

class HistoryAdapter(private val context: Context, private val itemList: List<History>):
    RecyclerView.Adapter<HistoryAdapter.ItemHolder>() {

    class ItemHolder(private val binding: HistoryLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun setData(item: History) {
            binding.username.text = item.username
            if (item.win) {
                binding.win.setImageResource(R.drawable.add)
            } else {
                binding.win.setBackgroundResource(R.drawable.remove)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding = HistoryLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(itemList[position])
    }
}