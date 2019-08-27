package c.gingdev.layoutcapture.files

import android.graphics.Bitmap
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.lang.Exception

class fileUtil {
	private fun Any?.notNull(f: ()-> Unit) {
		if (this != null) f()
	}
	private fun Any?.isNull(f: ()-> Unit) {
		if (this == null) f()
	}

	constructor(bitmap: Bitmap, filePath: String, fileName: String, listener: fileListener) {
		this.bitmap = bitmap
		this.filePath = filePath
		this.fileName = fileName
		this.listener = listener

		makeImageFile()
	}

	private val bitmap: Bitmap?
	private val filePath: String
	private val fileName: String
	private val listener: fileListener

	private val BITMAP_QUALITY: Int = 100


	private fun makeImageFile() {
		val file = File(filePath)

		file.isNull {
			file.mkdir()
		}

		val fileCacheItem = File(filePath + fileName)
		var out: OutputStream? = null

		try {
			fileCacheItem.createNewFile()
			out = FileOutputStream(fileCacheItem)

			bitmap.notNull {
				bitmap!!.compress(Bitmap.CompressFormat.PNG, BITMAP_QUALITY, out)
				listener.successToWork()
			}
		} catch (e: Exception) {
			Log.e("err", "${e.message} at -> $filePath$fileName")
			listener.failedToWork()
		} finally {
			try {
				out?.close()
			} catch (ioe: IOException) {
				ioe.printStackTrace()
			}
		}
	}

	interface fileListener {
		fun successToWork()
		fun failedToWork()
	}
}