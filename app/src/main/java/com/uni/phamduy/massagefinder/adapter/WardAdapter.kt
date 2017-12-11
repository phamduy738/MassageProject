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
import android.Manifest.permission
import android.Manifest.permission.ACCESS_FINE_LOCATION
import com.uni.phamduy.massagefinder.MainActivity
import android.support.v4.app.ActivityCompat
import android.content.DialogInterface
import android.R.string.ok




/**
 * Created by PhamDuy on 3/14/2017.
 */

class WardAdapter(internal var context: Context, internal var items: List<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)

        return MyHolder(inflater.inflate(R.layout.cell_ward, parent, false))

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, p: Int) {

        val myHolder = holder as MyHolder
        myHolder.tvCode.text = items[p].toString()


    }

    override fun getItemCount(): Int {
        return items.size
    }


    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        internal var tvCode: TextView = itemView.findViewById<View>(R.id.tvCode) as TextView
        private var rldetail: RelativeLayout
        private var imgChecked: ImageView

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
        WardAdapter.clickListener = clickListener
    }

    interface ClickListener {
        fun onItemClick(position: Int, v: View)
    }

    companion object {
        private var clickListener: ClickListener? = null
    }
}
