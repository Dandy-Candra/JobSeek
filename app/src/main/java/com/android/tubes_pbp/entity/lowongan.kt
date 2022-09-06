package com.android.tubes_pbp.entity

import com.android.tubes_pbp.R

class lowongan(var name: String, var detail: String, var photo: Int) {
    companion object{
        var listOfLowongan = arrayOf(
            lowongan("Mobile Game Development", "Bisa bekerja dalam shift ,Min. Pendidikan S1 Teknik Informatika", R.drawable.game_development),

        )

    }
}