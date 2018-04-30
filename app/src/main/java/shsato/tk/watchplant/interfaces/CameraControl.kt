package shsato.tk.watchplant.interfaces

import android.media.ImageReader

interface CameraControl {
	fun start()
	fun release()
	fun takePicture(callback: (imageReader: ImageReader) -> Unit)
}