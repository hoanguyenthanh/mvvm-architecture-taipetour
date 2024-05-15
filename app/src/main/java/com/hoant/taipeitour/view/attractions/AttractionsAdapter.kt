package com.hoant.taipeitour.view.attractions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.hoant.taipeitour.databinding.ItemAttractionBinding
import com.hoant.taipeitour.repository.model.Attraction
import com.hoant.taipeitour.util.Utils

class AttractionsAdapter : RecyclerView.Adapter<AttractionsAdapter.AttractionViewHolder>() {

    inner class AttractionViewHolder(val binding: ItemAttractionBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttractionViewHolder {
        val binding = ItemAttractionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AttractionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AttractionViewHolder, position: Int) {
        with(holder) {
            with( differ.currentList[position]) {
                binding.tvTitle.text = this.name
                binding.tvDescription.text = this.introduction
                if (this.images.isNotEmpty()) {
                    Utils.loadImage(holder.itemView.context, this.images[0].src, binding.ivImage)
                }
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