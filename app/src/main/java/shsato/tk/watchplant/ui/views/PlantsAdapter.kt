package shsato.tk.watchplant.ui.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import shsato.tk.watchplant.R
import shsato.tk.watchplant.db.Plant
import java.util.*

class PlantsAdapter(context: Context) : RecyclerView.Adapter<PlantsAdapter.ViewHolder>() {

	companion object {
		fun convert(plants: List<Plant>): List<Item> {
			val items = mutableMapOf<String, Item>()
			plants.forEach {
				val item = Item(
						plantId = it.plantId, imageUrl = it.imageUrl
				)
				items[it.plantId] = item
			}
			val itemList = mutableListOf<Item>()
			items.forEach {
				itemList.add(it.value)
			}
			return itemList
		}
	}

	private val mInflater = LayoutInflater.from(context)
	private val mItems: MutableList<Item> = mutableListOf()

	fun updateAll(plants: List<Plant>) {
		val items = convert(plants)
		mItems.clear()
		mItems.addAll(items)
		notifyDataSetChanged()
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = mInflater.inflate(R.layout.item_plants, parent, false)
		return ViewHolder(view)
	}

	override fun getItemId(position: Int): Long {
		return position.hashCode().toLong()
	}

	override fun getItemCount(): Int = mItems.size

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
	}

	class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
	}

	data class Item(val plantId: String, val imageUrl: String)
}