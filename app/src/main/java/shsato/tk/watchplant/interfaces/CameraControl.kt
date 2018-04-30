package shsato.tk.watchplant.interfaces

import android.media.ImageReader

interface CameraControl {
	fun takePicture(backgroundCallback: (imageReader: ImageReader?) -> Unit)
}