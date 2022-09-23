package com.android.tubes_pbp.user

import androidx.room.*

@Dao
interface ExperienceDao {
    @Insert
    suspend fun addExperience(experience: Experience)

    @Update
    suspend fun updateExperience(experience: Experience)

    @Delete
    suspend fun deleteExperience(experience: Experience)

    @Query("SELECT * FROM experience")
    suspend fun getExperiences() : List<Experience>

    @Query("SELECT * FROM experience WHERE idUser =:user_id")
    suspend fun getExperiencesById(user_id: Int) : List<Experience>

    @Query("SELECT * FROM experience WHERE id =:experience_id")
    suspend fun getUser(experience_id: Int) : List<Experience>
}