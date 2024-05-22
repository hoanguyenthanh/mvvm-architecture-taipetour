package com.hoant.taipeitour.view.languages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.hoant.taipeitour.base.BaseAdapter
import com.hoant.taipeitour.databinding.ItemLanguageBinding

class LanguagesAdapter(onItemClick: OnItemClick<String>): BaseAdapter<String, ItemLanguageBinding>(onItemClick) {

    override var variableId: Int? = BR.language

    override fun getBinding(parent: ViewGroup, viewType: Int): ItemLanguageBinding {
        return ItemLanguageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun getItem(position: Int): String {
        return differ.currentList[position]
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
    }

    override fun getItemCount() =  differ.currentList.size

    private val differCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}