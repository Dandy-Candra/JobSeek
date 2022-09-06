package com.android.tubes_pbp.entity

import com.android.tubes_pbp.R

class lowongan(var name: String, var detail: String, var photo: Int) {
    companion object{
        var listOfLowongan = arrayOf(
            lowongan("Mobile Game Development", "Bisa bekerja dalam shift ,Min. Pendidikan S1 Teknik Informatika", R.drawable.game_development),
            lowongan("Mobile App Development", "Bisa bekerja FUll Time ,Min. Pendidikan S1 Teknik Informatika", R.drawable.mobile_app_developer),
            lowongan("Software Developer", "Bisa bekerja dalam shift ,Min. Pendidikan S1 Teknik Informatika", R.drawable.software_developer),
            lowongan("Web Development", "Bisa bekerja Full Time ,Min. Pendidikan SMA/SMK/Sederajat", R.drawable.web_development),
            lowongan("UI / UX Design ", "Bisa bekerja dalam shift ,Min. Pendidikan S1 Teknik Informatika", R.drawable.ui_ux),
            lowongan("Database Administrator","Bisa menggunakan MS Access, SQL Server, Min. Pendidikan S1 Teknik Informatika", R.drawable.database_administrator),
            lowongan("Security Engineer","Menguasai tentang keamanan sistem, Min. Pendidikan S1 Teknik Informatika", R.drawable.cyber_security_engineer),
            lowongan("System Analyst", "Memiliki analisa dan basic programing yang kuat, Min. Pendidikan S1 Teknik Informatika", R.drawable.system_analyst),
            lowongan("Hardware Engineer", "Memiliki skill dalam arsitektur komputer, Min. Pendidikan SMA/SMK/Sederajat", R.drawable.computer_hardware_engineer),
            lowongan("Network Engineer", "Memahami OSI layer, Min. Pendidikan S1 Teknik Informatika", R.drawable.network_enginer),
            )

    }
}