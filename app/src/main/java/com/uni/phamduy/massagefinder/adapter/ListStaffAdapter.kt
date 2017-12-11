package com.uni.phamduy.massagefinder.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.uni.phamduy.massagefinder.MoveScreen
import com.uni.phamduy.massagefinder.R
import com.uni.phamduy.massagefinder.ui.PlaceDetailActivity
import java.util.*

/**
 * Created by PhamDuy on 9/13/2017.
 */
class ListStaffAdapter(internal var context: Context, internal var list: List<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val RANDOM = Random()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)

        return MyHolder(inflater.inflate(R.layout.cell_list_staff, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val myHolder = holder as MyHolder
        Glide.with(context).load(getRandomCheeseDrawable()).into(myHolder.avatar)

    }
    fun getRandomCheeseDrawable(): Int {
        when (RANDOM.nextInt(3)) {
            0 -> return R.drawable.hinh1
            1 -> return R.drawable.hinh2
            2 -> return R.drawable.hinh3
            3 -> return R.drawable.hinh4
        }
        return 0
    }


    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(v: View?) {
           clickListener?.OnItemClick(adapterPosition,v!!)
        }

        var avatar: ImageView
        var tvplace:TextView
        private var moveScreen: MoveScreen? = null
        init {
            avatar= itemView.findViewById(R.id.avatar)
            tvplace=itemView.findViewById(R.id.tvplace)
            itemView.setOnClickListener(this)
            tvplace.setOnClickListener {
                val intent = Intent(context, PlaceDetailActivity::class.java)
                context.startActivity(intent)
            }
        }
    }
    fun setOnItemClickListener(clickListener: ListStaffAdapter.ClickListener) {
        ListStaffAdapter.clickListener = clickListener
    }
    interface ClickListener{
        fun OnItemClick(position : Int, v: View)
    }
    companion object {
        private var clickListener:ClickListener? = null
    }
}