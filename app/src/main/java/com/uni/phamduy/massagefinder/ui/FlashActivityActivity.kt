package com.uni.phamduy.massagefinder

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.flash_activity.*

class FlashActivityActivity : AppCompatActivity(), View.OnClickListener {
    var ACESSLOCATION = 123
    override fun onClick(p0: View?) {
        if (p0 != null) {
            when (p0.id) {
                R.id.btnFind -> {
                    this.startActivity(Intent(this@FlashActivityActivity, MainActivity::class.java))
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.flash_activity)
        btnFind.setOnClickListener(this)
        checkPermission()
    }

    fun checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), ACESSLOCATION)
            }
        }
//        Toast.makeText(this, "ready use location", Toast.LENGTH_SHORT).show()
    }
}
