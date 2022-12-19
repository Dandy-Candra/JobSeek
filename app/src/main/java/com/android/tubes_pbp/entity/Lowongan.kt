package com.android.tubes_pbp.entity

import com.android.tubes_pbp.R

class Lowongan(var perusahaan: String, var name: String, var detail: String, var photo: Int) {
    companion object{
        var listOfLowongan = arrayOf(
            Lowongan("PT Serba Bisa","Mobile Game Development", "Bisa bekerja dalam shift ,Min. Pendidikan S1 Teknik Informatika", R.drawable.game_development ),
            Lowongan("PT Serba Ga Bisa","Mobile App Development", "Bisa bekerja FUll Time ,Min. Pendidikan S1 Teknik Informatika", R.drawable.mobile_app_developer),
            Lowongan("PT Jalan terus","Software Developer", "Bisa bekerja dalam shift ,Min. Pendidikan S1 Teknik Informatika", R.drawable.software_developer),
            Lowongan("PT Mukti","Web Development", "Bisa bekerja Full Time ,Min. Pendidikan SMA/SMK/Sederajat", R.drawable.web_development),
            Lowongan("PT CBA","UI / UX Design ", "Bisa bekerja dalam shift ,Min. Pendidikan S1 Teknik Informatika", R.drawable.ui_ux),
            Lowongan("PT Bank Ya","Database Administrator","Bisa menggunakan MS Access, SQL Server, Min. Pendidikan S1 Teknik Informatika", R.drawable.database_administrator),
            Lowongan("PT Ya Gimana","Security Engineer","Menguasai tentang keamanan sistem, Min. Pendidikan S1 Teknik Informatika", R.drawable.cyber_security_engineer),
            Lowongan("PT Engga Dulu","System Analyst", "Memiliki analisa dan basic programing yang kuat, Min. Pendidikan S1 Teknik Informatika", R.drawable.system_analyst),
            Lowongan("PT Ashiapp","Hardware Engineer", "Memiliki skill dalam arsitektur komputer, Min. Pendidikan SMA/SMK/Sederajat", R.drawable.computer_hardware_engineer),
            Lowongan("PT Ya gitu","Network Engineer", "Memahami OSI layer, Min. Pendidikan S1 Teknik Informatika", R.drawable.network_enginer)
            )
    }
}