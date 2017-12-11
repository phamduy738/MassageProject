package com.uni.phamduy.massagefinder.ui

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.Html
import android.view.*
import com.uni.phamduy.massagefinder.R
import com.uni.phamduy.massagefinder.adapter.ListReviewAdapter
import kotlinx.android.synthetic.main.dialog_describe.*

/**
 * Created by PhamDuy on 10/30/2017.
 */
class ListReviewActivity: AppCompatActivity() {
    var rvListReview:RecyclerView?=null
    var listReviewAdapter:ListReviewAdapter? = null
    var listReview:MutableList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_review)
        var toolbar: Toolbar = findViewById(R.id.toolbar1)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        tvClose.setOnClickListener {
            val intent = Intent(this, PlaceDetailActivity::class.java)
            startActivity(intent)
        }

        rvListReview = findViewById(R.id.rvListReview)
        listReview.clear()
        addList()
        listReviewAdapter = ListReviewAdapter(this, listReview)
//        val gridlayout = GridLayoutManager(activity, 2)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvListReview?.adapter = listReviewAdapter
        rvListReview?.layoutManager = linearLayoutManager
        listReviewAdapter?.setOnItemClickListener(object : ListReviewAdapter.ClickListener {
            override fun OnItemClick(position: Int, v: View) {
                val menuDialog = Dialog(this@ListReviewActivity)

                menuDialog.window.setFlags(
                        WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
                val window: Window = menuDialog.window
                window.setGravity(Gravity.TOP)
                menuDialog.setContentView(R.layout.dialog_describe)

                menuDialog.window.attributes.windowAnimations = R.style.DialogAnimation
                menuDialog.window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
                menuDialog.window.setBackgroundDrawable(ColorDrawable(Color.WHITE))
                menuDialog.setCancelable(true)

                menuDialog.tvClose.setOnClickListener {
                    menuDialog.dismiss()
                }
                menuDialog.tvDescribe.text = Html.fromHtml("<ul>\r\n\t<li style=\"text-align:justify\">Tọa lạc tại một con hẻm 606/71 Đường 3 Th&aacute;ng 2, Phường 14, Quận 10, <strong>Hana Chicken</strong> l&agrave; điểm hẹn ăn uống l&yacute; tưởng d&agrave;nh cho mọi người, đến đ&acirc;y bạn được kh&aacute;m ph&aacute;, thưởng thức những m&oacute;n ăn được chế biến c&ocirc;ng phu, cầu kỳ, vị thơm ngon, tinh tế.</li>\r\n\t<li style=\"text-align:justify\"><strong>Hana Chicken</strong> sở hữu thực đơn ăn uống đa dạng, phong ph&uacute;, đ&aacute;p ứng cho nhu cầu, sở th&iacute;ch của từng người: G&agrave; ph&ocirc; mai, tokpokki, soup kim chi v&agrave; nhiều m&oacute;n ăn k&egrave;m: Cơm rong biển, khoai đ&uacute;t l&ograve;, khoai t&acirc;y chi&ecirc;n, salad&hellip;</li>\r\n\t<li style=\"text-align:justify\">G&agrave; r&aacute;n 4 loại sốt l&agrave; một trong những m&oacute;n ăn hiện &ldquo;l&agrave;m mưa l&agrave;m gi&oacute;&rdquo; tại qu&aacute;n, được nhiều người ưa th&iacute;ch. G&agrave; được tẩm gia vị đầy đủ rồi chi&ecirc;n với lớp bột cũng được tẩm vị đậm đ&agrave;, g&agrave; r&aacute;n với độ vừa phải, vỏ gi&ograve;n, thịt kh&ocirc;ng bở m&agrave; mềm ăn k&egrave;m với 4 loại sốt như ti&ecirc;u đen, ph&ocirc; mai, bơ tỏi, mật ong, chanh d&acirc;y.</li>\r\n\t<li style=\"text-align:justify\">Tất cả những m&oacute;n ăn tại <strong>Hana Chicken</strong> đều được l&agrave;m từ nguy&ecirc;n liệu sạch, đảm bảo vệ sinh an to&agrave;n thực phẩm v&agrave; được đầu bếp tay nghề cao, d&agrave;y dạn kinh nghiệm chế biến.</li>\r\n\t<li style=\"text-align:justify\"><strong>Hana Chicken</strong> với kh&ocirc;ng gian kh&ocirc;ng cầu kỳ, sang trọng nhưng mọi thứ đều được thiết kế, b&agrave;y tr&iacute; một c&aacute;ch h&agrave;i h&ograve;a v&agrave; đội ngũ nh&acirc;n vi&ecirc;n phục vụ chuy&ecirc;n nghiệp, chu đ&aacute;o, th&acirc;n thiện, sẽ mang đến cho bạn những ph&uacute;t gi&acirc;y ăn uống thỏa m&atilde;n nhất.</li>\r\n</ul>", null, PlaceDetailActivity.UlTagHandler())
                menuDialog.show()
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    fun addList() {
        for (i in 0..20) {
            listReview.add(i.toString())
        }
    }
}