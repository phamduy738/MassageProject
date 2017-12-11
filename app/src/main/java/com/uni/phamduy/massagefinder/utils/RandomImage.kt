package com.uni.phamduy.massagefinder.utils

import com.uni.phamduy.massagefinder.R
import java.util.*

/**
 * Created by PhamDuy on 9/25/2017.
 */
class RandomImage {
     val RANDOM = Random()

    fun getRandomCheeseDrawable(): Int {
        when (RANDOM.nextInt(5)) {
            0 -> return R.drawable.q1
            1 -> return R.drawable.q2
            2 -> return R.drawable.q3
            3 -> return R.drawable.q4
            4 -> return R.drawable.q5
            5 -> return R.drawable.q6
            6 -> return R.drawable.q1
            7 -> return R.drawable.q2
            8 -> return R.drawable.q3
            9 -> return R.drawable.q4
            10 -> return R.drawable.q5
            11 -> return R.drawable.q6
            12 -> return R.drawable.q7
        }
        return 0
    }


}