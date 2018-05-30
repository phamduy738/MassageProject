package com.uni.phamduy.massagefinder.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.uni.phamduy.massagefinder.MoveScreen
import com.uni.phamduy.massagefinder.R
import com.uni.phamduy.massagefinder.module.district.District
import com.uni.phamduy.massagefinder.ui.Place.PlaceFragment
import java.util.*

/**
 * Created by PhamDuy on 9/13/2017.
 */
class ListWardAdapter(internal var context: Context, internal var list: List<District>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val RANDOM = Random()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)

        return MyHolder(inflater.inflate(R.layout.cell_list_ward, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val myHolder = holder as MyHolder
        myHolder.tvStoreName.text = list[position].districtName
        Glide.with(context).load(list[position].image?.link).into(myHolder. thumbnail)

    }



    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(p0: View?) {
          clickListener?.OnItemClick(adapterPosition, p0!!)
        }

        var tvStoreName: TextView
         var cardView: CardView
        var thumbnail: ImageView

        init {
            thumbnail= itemView.findViewById(R.id.thumbnail)
            tvStoreName = itemView.findViewById<TextView>(R.id.tvStoreName)
            cardView = itemView.findViewById(R.id.cardView)
            itemView.setOnClickListener (this)
        }
    }
    fun setOnItemClickListener(clickListener: ListWardAdapter.ClickListener) {
        ListWardAdapter.clickListener = clickListener
    }
    interface ClickListener{
        fun OnItemClick(position : Int, v: View)
    }
    companion object {
        private var clickListener:ClickListener? = null
    }
}
