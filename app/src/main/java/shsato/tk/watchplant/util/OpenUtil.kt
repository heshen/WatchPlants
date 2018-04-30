package shsato.tk.watchplant.util

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import shsato.tk.watchplant.ui.activity.Camera2Activity


class OpenUtil {
	companion object {
		fun openCamera(activity: Activity, requestCode: Int) {
			val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//			activity.startActivityForResult(intent, requestCode)
			// 自作したい・・
			activity.startActivity(Intent(activity, Camera2Activity::class.java))
		}
	}
}