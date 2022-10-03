package com.android.tubes_pbp.user

import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM user")
    suspend fun getUsers(): List<User>


    @Query("SELECT * FROM user WHERE id =:userId")
    suspend fun getUser(userId: Int): User?

    @Query("SELECT * FROM user WHERE username =:username")
    suspend fun getUserByUsername(username: String): User?

    @Query("SELECT * FROM user WHERE username =:username and password =:password")
    suspend fun getUserLogin(username: String, password: String): User?
}