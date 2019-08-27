package c.gingdev.layoutcapture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import c.gingdev.layoutcapture.files.fileUtil
import c.gingdev.layoutcapture.permissions.permissionChecker
import c.gingdev.layoutcapture.permissions.utils.permissionListener
import c.gingdev.layoutcapture.permissions.values.permissionsList

import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
	private fun Any?.notNull(f: ()-> Unit) {
		if (this != null) f()
	}
	private fun Any?.isNull(f: ()-> Unit) {
		if (this == null) f()
	}

	private val externalStoragePath = "${Environment.getExternalStorageDirectory().absolutePath}/Pictures/"

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		permissionChecker(
				this@MainActivity,
				permissionsList.storagePermissionList,
				object : permissionListener {
					override fun permissionGranted() {
						val bitmap = LayoutCapture(CapturedLayout, this@MainActivity).convertLayoutToBitmap()
						fileUtil(bitmap, externalStoragePath, "${UUID.randomUUID()}.png", object : fileUtil.fileListener {
							override fun successToWork() {
								Toast.makeText(this@MainActivity, "파일생성!", Toast.LENGTH_LONG).show()
							}

							override fun failedToWork() {
								Toast.makeText(this@MainActivity, "파일생성 실패!!!", Toast.LENGTH_LONG).show()
							}
						})
					}

					override fun permissionDenied() {
						Toast.makeText(this@MainActivity, "Permission Denied", Toast.LENGTH_LONG).show()
					}
				}).requestPermission()
	}
}
