package tn.esprit.mylast.dao

import tn.esprit.mylast.data.User
import androidx.room.*
@Dao
interface UserDAO {
    @Insert
    fun insert(educ: User)

    @Update
    fun update(educ: User)

    @Delete
    fun delete(educ: User)

    @Query("SELECT * FROM user")
    fun getAllChamps(): List<User>
}