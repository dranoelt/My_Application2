package com.example.myapplication2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication2.database.Meeting

class myRecyclerAdapter(val items: List<Meeting>): RecyclerView.Adapter<myRecyclerAdapter.itemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemViewHolder {
        val tv = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return itemViewHolder(tv)
    }

    override fun onBindViewHolder(holder: itemViewHolder, position: Int) {
        val meeting: Meeting = items[position]
        holder.itemTitle.text = meeting.title
        holder.itemDate.text = meeting.tgl
        holder.itemDesc.text = meeting.desc
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class itemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.tv_title)
        val itemDate: TextView = itemView.findViewById(R.id.tv_tgl)
        val itemDesc: TextView = itemView.findViewById(R.id.tv_desc)
    }


}