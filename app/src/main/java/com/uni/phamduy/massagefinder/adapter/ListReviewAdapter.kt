package com.uni.phamduy.massagefinder.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.uni.phamduy.massagefinder.MoveScreen
import com.uni.phamduy.massagefinder.R
import java.util.*

/**
 * Created by PhamDuy on 9/13/2017.
 */
class ListReviewAdapter(internal var context: Context, internal var list: List<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val RANDOM = Random()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)

        return MyHolder(inflater.inflate(R.layout.cell_list_review, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val myHolder = holder as MyHolder

    }



    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(v: View?) {
           clickListener?.OnItemClick(adapterPosition,v!!)
        }

        var tvReadMore: TextView
        private var moveScreen: MoveScreen? = null
        init {
            tvReadMore=itemView.findViewById(R.id.tvReadMore)
            itemView.setOnClickListener(this)
            tvReadMore.setOnClickListener(this)

        }
    }
    fun setOnItemClickListener(clickListener: ListReviewAdapter.ClickListener) {
        ListReviewAdapter.clickListener = clickListener
    }
    interface ClickListener{
        fun OnItemClick(position : Int, v: View)
    }
    companion object {
        private var clickListener:ClickListener? = null
    }
}