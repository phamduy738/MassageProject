package com.uni.phamduy.massagefinder.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.uni.phamduy.massagefinder.R
import com.uni.phamduy.massagefinder.module.place.Place
import java.util.*



/**
 * Created by PhamDuy on 9/13/2017.
 */
class ListImageAdapter(var context: Context, private var list: List<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val RANDOM = Random()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)

        return MyHolder(inflater.inflate(R.layout.cell_list_image, parent, false))
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val myHolder = holder as MyHolder
//        myHolder.tvStoreName.text = list[position]
        Glide.with(context).load(list[position])
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.placeholder)
                .into(myHolder.img)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView = itemView.findViewById(R.id.img)

    }
}