package shsato.tk.watchplant.ui.activity

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_camera2.*
import shsato.tk.watchplant.Constants
import shsato.tk.watchplant.R
import shsato.tk.watchplant.interfaces.CameraControl
import shsato.tk.watchplant.ui.fragment.Camera2Fragment

class Camera2Activity : AppCompatActivity() {

	private var mCameraControl: CameraControl? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_camera2)
		init(savedInstanceState)
	}

	override fun onResume() {
		super.onResume()
		if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), Constants.REQUEST_CAMERA)
		} else {
			startCamera()
		}
	}

	override fun onOptionsItemSelected(item: MenuItem?): Boolean {
		item?.let {
			when (it.itemId) {
				android.R.id.home -> {
					back()
				}
			}
		}
		return super.onOptionsItemSelected(item)
	}

	override fun onBackPressed() {
		back()
	}

	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
		when (requestCode) {
			Constants.REQUEST_CAMERA -> {
				if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					startCamera()
				} else {
					back()
				}
			}
		}
	}

	private fun startCamera() {
		mCameraControl?.start()
	}

	private fun init(savedInstanceState: Bundle?) {
		val f = if (savedInstanceState == null) {
			val f = Camera2Fragment.newInstance()
			supportFragmentManager.beginTransaction()
					.replace(R.id.container, f)
					.commit()
			f
		} else {
			supportFragmentManager.findFragmentById(R.id.container)
		}
		if (f is CameraControl) {
			mCameraControl = f
		}

		setSupportActionBar(toolbar)
		supportActionBar?.let {
			it.setDisplayHomeAsUpEnabled(true)
			it.title = ""
		}
	}

	private fun back() {
		mCameraControl?.release()
		setResult(Activity.RESULT_CANCELED)
		finish()
	}
}
