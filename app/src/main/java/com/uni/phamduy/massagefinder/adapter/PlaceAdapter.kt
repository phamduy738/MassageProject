package com.uni.phamduy.massagefinder.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.uni.phamduy.massagefinder.R
import com.uni.phamduy.massagefinder.module.district.District
import com.uni.phamduy.massagefinder.module.place.Place
import java.util.*

/**
 * Created by PhamDuy on 9/13/2017.
 */
class PlaceAdapter(var context: Context, private var list: List<Place>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val RANDOM = Random()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)

        return MyHolder(inflater.inflate(R.layout.cell_place, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val myHolder = holder as MyHolder
        myHolder.tvStoreName?.text = list[position].name
        myHolder.tvAddress?.text= list[position].address?.street
        myHolder.tvReview?.text = Html.fromHtml(list[position].review)
        myHolder.tvDistance?.text = list[position].distance
        myHolder.ratingBar?.rating = (list[position].rating!! /2).toFloat()
        myHolder.ratingBar?.isClickable = false
//        myHolder.ratingBar.isIndicator = true
        myHolder.ratingBar?.isActivated = false
        Glide.with(context).load(list[position].coverImage?.link).into(myHolder.thumbnail)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
        override fun onClick(v: View) {
            clickListener?.OnItemClick(adapterPosition, v)
        }

        var tvStoreName: TextView?= null
         var cardView: CardView?= null
        var thumbnail: ImageView?= null
        var tvAddress: TextView?= null
        var tvReview: TextView?= null
        var tvDistance: TextView?= null
        var ratingBar: RatingBar?= null


        init {
            thumbnail= itemView.findViewById(R.id.thumbnail_place)
            tvStoreName = itemView.findViewById<TextView>(R.id.tvStoreName)
            cardView = itemView.findViewById(R.id.cardView)
            tvAddress=itemView.findViewById(R.id.tvAddress)
            tvReview = itemView.findViewById(R.id.tvReview)
            tvDistance= itemView.findViewById(R.id.tvDistance)
            ratingBar = itemView.findViewById(R.id.ratingBar)
            itemView.setOnClickListener(this)

        }
    }
    fun setOnItemClickListener(clickListener: PlaceAdapter.ClickListener) {
        PlaceAdapter.clickListener = clickListener
    }
    interface ClickListener{
        fun OnItemClick(position : Int, v: View)
    }
    companion object {
        var clickListener:ClickListener? = null
    }
}