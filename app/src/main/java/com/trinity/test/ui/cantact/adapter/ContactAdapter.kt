package com.trinity.test.ui.cantact.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.trinity.test.databinding.ItemContactBinding
import com.trinity.test.rest.entity.ContactModel
import java.lang.Boolean.FALSE

@SuppressLint("NotifyDataSetChanged")
class ContactAdapter : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    private var categoryList = mutableListOf<ContactModel>()
    private lateinit var listener: (ContactModel) -> Unit

    override fun getItemCount(): Int = categoryList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemContactBinding.inflate(inflater, parent, FALSE))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }

    fun setData(model: ContactModel) {
        this.categoryList.add(model)
        notifyDataSetChanged()
    }

    fun setData(model: MutableList<ContactModel>) {
        this.categoryList.clear()
        this.categoryList = model
        notifyDataSetChanged()
    }

    fun getAction(listener: (ContactModel) -> Unit) {
        this.listener = listener
    }

    inner class ViewHolder(val bind: ItemContactBinding) : RecyclerView.ViewHolder(bind.root) {

        fun bind(model: ContactModel) {
            bind.contactName.text = model.firstName

            bind.btnRoot.setOnClickListener {
                listener(model)
            }
        }
    }
}