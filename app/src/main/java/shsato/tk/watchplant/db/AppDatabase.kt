package shsato.tk.watchplant.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [(Plant::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {
	companion object {
		private var INSTANCE: AppDatabase? = null

		fun getInMemoryDatabase(context: Context): AppDatabase {
			if (INSTANCE == null) {
				INSTANCE = Room.inMemoryDatabaseBuilder(
						context.applicationContext,
						AppDatabase::class.java).allowMainThreadQueries().build()
			}
			return INSTANCE!!
		}

		fun destroyInstance() {
			INSTANCE = null
		}
	}

	abstract fun plantModel(): PlantDao
}