package tn.esprit.mylast.dao

import tn.esprit.mylast.data.UserLocal
import androidx.room.*
@Dao
interface UserDAO {
    @Insert
    fun insert(educ: UserLocal)

    @Update
    fun update(educ: UserLocal)

    @Delete
    fun delete(educ: UserLocal)

    @Query("SELECT * FROM user")
    fun getAllChamps(): List<UserLocal>
}