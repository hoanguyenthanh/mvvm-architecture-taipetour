package com.hoant.taipeitour.view.attractions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.hoant.taipeitour.base.BaseAdapter
import com.hoant.taipeitour.databinding.ItemAttractionBinding
import com.hoant.taipeitour.repository.model.Attraction
import com.hoant.taipeitour.util.Utils

class AttractionsAdapter(onItemClick: OnItemClick<Attraction>): BaseAdapter<Attraction, ItemAttractionBinding>(onItemClick) {

    override var variableId: Int? = BR.attraction

    override fun getBinding(parent: ViewGroup, viewType: Int): ItemAttractionBinding {
        return ItemAttractionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun getItem(position: Int): Attraction {
        return differ.currentList[position]
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        with(differ.currentList[position]) {
            if (this.images.isNotEmpty()) {
                Utils.loadImage(holder.itemView.context, this.images[0].src, binding.ivImage)
            }
        }
    }



    override fun getItemCount() =  differ.currentList.size

    private val differCallback = object : DiffUtil.ItemCallback<Attraction>() {
        override fun areItemsTheSame(oldItem: Attraction, newItem: Attraction): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Attraction, newItem: Attraction): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}