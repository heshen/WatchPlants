package shsato.tk.watchplant.db

import android.arch.persistence.room.TypeConverter
import java.util.*

class DataConverter {

	companion object {
		@TypeConverter
		fun toDate(timestamp: Long?): Date? =
				if (timestamp == null) {
					null
				} else {
					Date(timestamp)
				}

		@TypeConverter
		fun toTimestamp(date: Date?): Long? =
				date?.time
	}
}