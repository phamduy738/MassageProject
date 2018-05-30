package com.uni.phamduy.massagefinder.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.uni.phamduy.massagefinder.MoveScreen
import com.uni.phamduy.massagefinder.R
import com.uni.phamduy.massagefinder.module.staff.Staff
import org.xml.sax.XMLReader

/**
 * Created by PhamDuy on 9/13/2017.
 */
class ListStaffAdapter(internal var context: Activity, internal var list: List<Staff>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)

        return MyHolder(inflater.inflate(R.layout.cell_list_staff, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val myHolder = holder as MyHolder
        Glide.with(context).load(list[position].avatar.link)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.placeholder)
                .into(myHolder.avatar)
        myHolder.tvNumber?.text= list[position].number+ " -"
        myHolder.tvName?.text= list[position].nickName
        myHolder.tvHomeTown?.text= list[position].homeTown

        myHolder.tvRating?.text= list[position].averageRating.toString()
        myHolder.tvNumberReview?.text= list[position].numberReview.toString()
        myHolder.tvDate?.text = if(list[position].newestReview!=null){
            list[position].newestReview.reviewDate
        }else{
            ""
        }
        myHolder.tvFirstReview?.text = if( list[position].newestReview!=null){
            Html.fromHtml(list[position].newestReview.reviewContent, null, UlTagHandler())
        }else{
            ""
        }

    }


    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        var avatar: ImageView? = null
        var tvplace:TextView? = null
        var tvNumber:TextView? = null
        var tvName:TextView? = null
        var tvHomeTown: TextView? = null
        var tvRating:TextView? = null
        var tvDate:TextView? = null
        var tvNumberReview: TextView? = null
        var tvFirstReview: TextView?=null
        private var moveScreen: MoveScreen? = null
        init {
            avatar= itemView.findViewById(R.id.avatar)
            tvplace=itemView.findViewById(R.id.tvCheckPlace)
            tvNumber=itemView.findViewById(R.id.tvNumber)
            tvName=itemView.findViewById(R.id.tvName)
            tvHomeTown=itemView.findViewById(R.id.tvHomeTown)
            tvRating = itemView.findViewById(R.id.tvRating)
            tvDate = itemView.findViewById(R.id.tvDate)
            tvNumberReview = itemView.findViewById(R.id.tvNumberReview)
            tvFirstReview = itemView.findViewById(R.id.tvFirstReview)
            itemView.setOnClickListener {
                v->clickListener?.OnItemReviewClick(adapterPosition,v )
            }
            tvplace?.setOnClickListener{
               v-> clickListener?.OnItemClick(adapterPosition,v!!)
            }

        }
    }
    fun setOnItemClickListener(clickListener: ListStaffAdapter.ClickListener) {
        ListStaffAdapter.clickListener = clickListener
    }
    fun setOnItemReviewClickListener(clickListener: ListStaffAdapter.ClickListener) {
        ListStaffAdapter.clickListener = clickListener
    }
    interface ClickListener{
        fun OnItemClick(position : Int, v: View)
        fun OnItemReviewClick(position: Int, v: View)
    }
    companion object {
        private var clickListener:ClickListener? = null
    }
    class UlTagHandler : Html.TagHandler {
        override fun handleTag(b: Boolean, s: String, editable: Editable, xmlReader: XMLReader) {
            if (s == "ul" && !b) editable.append("\n")
            if (s == "li" && b) editable.append("\n\t•")
        }
    }
}