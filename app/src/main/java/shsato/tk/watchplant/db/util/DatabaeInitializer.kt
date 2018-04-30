package shsato.tk.watchplant.db.util

import android.os.AsyncTask
import shsato.tk.watchplant.db.AppDatabase
import shsato.tk.watchplant.db.Plant
import java.util.*

class DatabaseInitializer() {
	companion object {
		private const val DELAY_MILLIS = 500

		fun populateAsync(db: AppDatabase) {
			val task = PopulateDbAsync(db, listOf("", ""))
			task.execute()
		}

		fun populateWithTestData(db: AppDatabase, plantImages: List<String>) {
			db.plantModel().deleteAll()
			plantImages.forEachIndexed { index, imgUrl ->

				val plantId = UUID.randomUUID().toString()
				val plant = Plant(
						id = index.toString(),
						plantId = plantId,
						count = index,
						imageUrl = imgUrl,
						updatedAt = Date(),
						createdAt = Date())
				db.plantModel().insertPlant(plant)
			}
		}
	}

	class PopulateDbAsync(private val mDb: AppDatabase, private val mPlants: List<String>) : AsyncTask<Void, Void, Void>() {

		override fun doInBackground(vararg p0: Void?): Void? {
			populateWithTestData(mDb, mPlants)
			return null
		}
	}
}