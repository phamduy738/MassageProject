package com.uni.phamduy.massagefinder.adapter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.uni.phamduy.massagefinder.R


/**
 * Created by PhamDuy on 7/27/2017.
 */

class SearchAdapter(private var context: Context, private var list: List<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)

        return MyHolder(inflater.inflate(R.layout.cell_search, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val myHolder = holder as MyHolder
        myHolder.tvCategory.text = list[position]
        if (list[position].equals("Quận", ignoreCase = true)) {
            Glide.with(context).load(R.drawable.ic_ward).into(myHolder.imgCategory)
        }
     /*   if (list[position].equals("Rating", ignoreCase = true)) {
            Glide.with(context).load(R.drawable.ic_star).into(myHolder.imgCategory)
        }
        if (list[position].equals("Khoảng cách", ignoreCase = true)) {
            Glide.with(context).load(R.drawable.ic_directions_walk).into(myHolder.imgCategory)
        }*/
        if (list[position].equals("Sort", ignoreCase = true)) {
            Glide.with(context).load(R.drawable.ic_sort).into(myHolder.imgCategory)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(p0: View?) {
            clickListener!!.onItemClick(adapterPosition, p0)
        }


        internal var tvCategory: TextView
        internal var imgCategory: ImageView

        internal  var lnCategory: ConstraintLayout

        init {

            tvCategory = itemView.findViewById<View>(R.id.tvCategory) as TextView
            imgCategory = itemView.findViewById<View>(R.id.imgCategory) as ImageView
            lnCategory = itemView.findViewById<View>(R.id.lnCategory) as ConstraintLayout
            lnCategory.setOnClickListener(this)
        }
    }

   fun setOnItemClickListener(clickListener: ClickListener) {
        SearchAdapter.clickListener = clickListener
    }

    interface ClickListener {
        fun onItemClick(position: Int, v: View?)
    }

    companion object {
        private var clickListener: ClickListener? = null
    }
}
