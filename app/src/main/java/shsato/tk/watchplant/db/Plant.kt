package shsato.tk.watchplant.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity
data class Plant(
		@PrimaryKey
		val id: String,
		val plantId :String,
		val count: Int,
		val imageUrl: String,
		val updatedAt: Date,
		val createdAt: Date
)
