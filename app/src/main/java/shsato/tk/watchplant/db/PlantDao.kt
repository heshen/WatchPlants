package shsato.tk.watchplant.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.IGNORE
import android.arch.persistence.room.OnConflictStrategy.REPLACE

@Dao
@TypeConverters(DataConverter::class)
interface PlantDao {

	@Query("SELECT * FROM Plant WHERE plantId = :plantId AND count <= :count")
	fun selectPlantsByPlantId(plantId: Long, count: Int): LiveData<List<Plant>>

	@Insert(onConflict = IGNORE)
	fun insertPlant(plant: Plant)

	@Update(onConflict = REPLACE)
	fun updatePlant(plant: Plant)

	@Query("DELETE FROM Plant WHERE plantId = :plantId")
	fun deleteByPlantId(plantId: Int)

	@Query("DELETE FROM Plant")
	fun deleteAll()
}