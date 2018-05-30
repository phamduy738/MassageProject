package com.uni.phamduy.massagefinder.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.uni.phamduy.massagefinder.R

/**
 * Created by PhamDuy on 3/14/2017.
 */

class SortAdapter(internal var context: Context, internal var items: List<String>,internal var pos: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)

        return MyHolder(inflater.inflate(R.layout.cell_ward, parent, false))

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, p: Int) {

        val myHolder = holder as MyHolder
        myHolder.tvCode.text = items[p].toString()
        if(p==pos){
            myHolder.imgChecked.visibility = View.VISIBLE
        }else{
            myHolder.imgChecked.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        internal var tvCode: TextView = itemView.findViewById<View>(R.id.tvCode) as TextView
         var rldetail: RelativeLayout
        var imgChecked: ImageView

        init {
            rldetail = itemView.findViewById<View>(R.id.rldetail) as RelativeLayout
            imgChecked = itemView.findViewById<View>(R.id.imgChecked) as ImageView
            rldetail.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            clickListener?.onItemClick(adapterPosition, view)
        }


    }

    fun setOnItemClickListener(clickListener: ClickListener) {
        SortAdapter.clickListener = clickListener
    }

    interface ClickListener {
        fun onItemClick(position: Int, v: View)
    }

    companion object {
        private var clickListener: ClickListener? = null
    }
}