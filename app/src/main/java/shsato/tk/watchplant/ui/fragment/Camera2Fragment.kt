package shsato.tk.watchplant.ui.fragment


import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.SurfaceTexture
import android.media.ImageReader
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_camera2.*
import shsato.tk.watchplant.Constants
import shsato.tk.watchplant.Logger
import shsato.tk.watchplant.R
import shsato.tk.watchplant.camera.Camera2
import shsato.tk.watchplant.interfaces.CameraControl

/**
 * Camera
 *
 */
class Camera2Fragment : Fragment(), CameraControl, ActivityCompat.OnRequestPermissionsResultCallback {

	companion object {
		@JvmStatic
		fun newInstance(): Fragment = Camera2Fragment()

	}
	private val mTextureListener = object : TextureView.SurfaceTextureListener {
		override fun onSurfaceTextureSizeChanged(texture: SurfaceTexture?, width: Int, height: Int) {
			mCamera?.configureTransform(width, height)
		}

		override fun onSurfaceTextureUpdated(texture: SurfaceTexture?) = Unit

		override fun onSurfaceTextureDestroyed(texture: SurfaceTexture?): Boolean = true

		override fun onSurfaceTextureAvailable(texture: SurfaceTexture?, width: Int, height: Int) {
			openCamera(width, height)
		}

	}

	private var mCamera: Camera2? = null

	private var backgroundThread: HandlerThread? = null
	private var backgroundHandler: Handler? = null

	override fun takePicture(backgroundCallback: (imageReader: ImageReader?) -> Unit) {
		mCamera?.takePicture(backgroundCallback)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
	                          savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_camera2, container, false)
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)

	}

	override fun onResume() {
		super.onResume()
		context?.let {
			if (mCamera == null) {
				mCamera = Camera2(it, Handler(Looper.getMainLooper()))
			}
		}
		startBackgroundThread()
		if (preview.isAvailable) {
			openCamera(preview.width, preview.height)
		} else {
			preview.surfaceTextureListener = mTextureListener
		}
	}

	override fun onPause() {
		closeCamera()
		stopBackgroundThread()
		super.onPause()
	}

	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
		when (requestCode) {
			Constants.REQUEST_CAMERA -> {
				if (grantResults.size != 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
					activity?.setResult(Activity.RESULT_CANCELED)
					activity?.finish()
				}
			}
		}
	}


	private fun closeCamera() {
		mCamera?.release()
	}

	private fun openCamera(width: Int, height: Int) {
		val permission = ContextCompat.checkSelfPermission(activity!!, Manifest.permission.CAMERA)
		if (permission != PackageManager.PERMISSION_GRANTED) {
			requestPermissions(arrayOf(android.Manifest.permission.CAMERA), Constants.REQUEST_CAMERA)
			return
		}
		mCamera?.setTexture(preview.surfaceTexture)
		mCamera?.setup(width, height)
		preview.setTransform(mCamera?.configureTransform(width, height))
		mCamera?.open(backgroundHandler)
	}

	private fun startBackgroundThread() {
		backgroundThread = HandlerThread("CameraBackground").also { it.start() }
		backgroundHandler = Handler(backgroundThread?.looper)
	}

	private fun stopBackgroundThread() {
		backgroundThread?.quitSafely()
		try {
			backgroundThread?.join()
			backgroundThread = null
			backgroundHandler = null
		} catch (e: InterruptedException) {
			Logger.e(e.toString())
		}
	}
}
