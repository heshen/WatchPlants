package shsato.tk.watchplant.util

import android.graphics.ImageFormat
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.hardware.camera2.params.StreamConfigurationMap
import android.media.ImageReader
import android.util.Size
import shsato.tk.watchplant.Logger
import shsato.tk.watchplant.camera.CompareSizesByArea
import java.util.*



class CameraUtil {
	companion object {
		fun selectCameraId(cameraManager: CameraManager, facing: Int): String? {
			cameraManager.cameraIdList.forEach { cameraId ->
				val characteristics = cameraManager.getCameraCharacteristics(cameraId)
				if (characteristics.get(CameraCharacteristics.LENS_FACING) == facing) {
					return cameraId
				}
			}
			return null
		}

		fun createImageReader(imageFormat: Int, size: Size): ImageReader {
			return ImageReader.newInstance(
					size.width, size.height, imageFormat, 1
			)
		}

		fun chooseLargestSize(map: StreamConfigurationMap, imageFormat: Int): Size {
			return Collections.max(
					map.getOutputSizes(imageFormat).toList(),
					CompareSizesByArea()
			)
		}

		fun chooseOptimalSize(
				choices: Array<Size>,
				textureViewWidth: Int,
				textureViewHeight: Int,
				maxWidth: Int,
				maxHeight: Int,
				aspectRatio: Size
		): Size {

			// Collect the supported resolutions that are at least as big as the preview Surface
			val bigEnough = ArrayList<Size>()
			// Collect the supported resolutions that are smaller than the preview Surface
			val notBigEnough = ArrayList<Size>()
			val w = aspectRatio.width
			val h = aspectRatio.height
			for (option in choices) {
				if (option.width <= maxWidth && option.height <= maxHeight &&
						option.height == option.width * h / w) {
					if (option.width >= textureViewWidth && option.height >= textureViewHeight) {
						bigEnough.add(option)
					} else {
						notBigEnough.add(option)
					}
				}
			}

			// Pick the smallest of those big enough. If there is no one big enough, pick the
			// largest of those not big enough.
			return when {
				bigEnough.size > 0    -> Collections.min(bigEnough, CompareSizesByArea())
				notBigEnough.size > 0 -> Collections.max(notBigEnough, CompareSizesByArea())
				else -> choices[0]
			}
		}

	}
}