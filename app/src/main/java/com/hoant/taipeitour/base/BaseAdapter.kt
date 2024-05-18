package com.hoant.taipeitour.base

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

abstract class BaseAdapter<I, VB : ViewDataBinding>(
    private val onItemClick: OnItemClick<I>? = null
) : RecyclerView.Adapter<BaseAdapter<I, VB>.MyViewHolder>() {

    protected lateinit var binding: VB

    abstract var variableId: Int?
    abstract fun getBinding(parent: ViewGroup, viewType: Int): VB
    abstract fun getItem(position: Int): I


    interface OnItemClick<I> {
        fun onItemCLicked(item: I)
    }

    inner class MyViewHolder(viewDataBinding: VB) : ViewHolder(viewDataBinding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = getBinding(parent, viewType)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item: I = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClick?.onItemCLicked(item)
        }
        variableId?.let {
            binding.setVariable(it, item)
            binding.executePendingBindings()
        }
    }

    override fun onViewDetachedFromWindow(holder: MyViewHolder) {
        super.onViewDetachedFromWindow(holder)
        binding.unbind()
    }
}
