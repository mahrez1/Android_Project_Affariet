package tn.esprit.mylast.data
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "user")
data class UserLocal(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "Pic")
    val EduPic: Int,
    @ColumnInfo(name = "Name")
    val EduName : String,
    @ColumnInfo(name = "Password")
    val EduLoc : String,
    @ColumnInfo(name = "Email")
    val EduFirstDate : String,

)
