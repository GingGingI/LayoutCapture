package c.gingdev.layoutcapture.permissions

import android.app.Activity
import c.gingdev.layoutcapture.permissions.utils.permissionListener
import c.gingdev.layoutcapture.permissions.utils.permissionVerifier

class permissionChecker: permissionVerifier {
	private fun Any?.notNull(f: ()-> Unit) {
		if (this != null) f()
	}
	private fun Boolean?.isFalse(f: ()-> Unit) {
		if (this == false) f()
	}
	private fun Boolean?.isTrue(f: ()-> Unit) {
		if (this == true) f()
	}

	private var permissions: Array<String>? = null

	constructor(activity: Activity,
	            permissions: Array<String>,
	            listener: permissionListener) {
		setActivity(activity)
		setListener(listener)
		this.permissions = permissions
	}

	fun requestPermission() {
		permissions.notNull {
			/* When need request to permissions */
			needRequestRuntimePermission().isTrue { requestUnGranted(permissions!!) }
			/* When not need to request permissions */
			needRequestRuntimePermission().isFalse { permissionGranted() }
		}
	}

	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)
		requestPermissionResult(permissions, grantResults) }
}