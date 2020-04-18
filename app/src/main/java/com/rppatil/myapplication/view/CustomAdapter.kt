package com.rppatil.myapplication.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rppatil.myapplication.R
import com.rppatil.myapplication.model.JsonData

class CustomAdapter(private var jsonData: List<JsonData>) :
    RecyclerView.Adapter<CustomAdapter.MViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.data_row, parent, false)
        return MViewHolder(view)
    }

    override fun onBindViewHolder(vh: MViewHolder, position: Int) {
        val jsonData = jsonData[position]

        //render
        vh.textViewTitle.text = jsonData.title
        vh.textViewDesc.text = jsonData.description
        Glide.with(vh.imageView.context).load(jsonData.imageHref).error(R.drawable.no_images)
            .into(vh.imageView)
    }

    override fun getItemCount(): Int {
        return jsonData.size
    }

    fun update(data: List<JsonData>) {
        jsonData = data
        notifyDataSetChanged()
    }

    class MViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewTitle: TextView = view.findViewById(R.id.textViewTitle)
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val textViewDesc: TextView = view.findViewById(R.id.textViewDesc)
    }
}