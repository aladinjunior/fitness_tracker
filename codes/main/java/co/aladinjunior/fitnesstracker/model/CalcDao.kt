package co.aladinjunior.fitnesstracker.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface CalcDao {

    @Insert
    fun insert(calc: Calc)

    @Query("SELECT * FROM Calc WHERE type = :type")
    fun getValuesByType(type: String) : List<Calc>
}