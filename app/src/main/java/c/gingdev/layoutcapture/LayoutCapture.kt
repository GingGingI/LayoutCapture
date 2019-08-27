package c.gingdev.layoutcapture

import android.content.Context
import android.graphics.Bitmap
import android.view.View

class LayoutCapture(private val view: View,
                   private val context: Context) {
	fun convertLayoutToBitmap(): Bitmap {
		view.apply {
			isDrawingCacheEnabled = true
			measure(
					View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
					View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
			layout(0, 0,
					measuredWidth,
					measuredHeight)
			drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
			buildDrawingCache(true)
		}
		return Bitmap.createBitmap(view.drawingCache)
	}
}