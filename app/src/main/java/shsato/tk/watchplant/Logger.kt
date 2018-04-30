package shsato.tk.watchplant

import android.util.Log

class Logger {
	companion object {
		private const val TAG = "WatchPlants"

		fun d(message: String) {
			if (BuildConfig.DEBUG) {
				Log.d(TAG, message)
			}
		}

		fun e(message: String) {
			Log.e(TAG, message)
		}
	}
}