package ru.napoleonit.testnapoleon2.base

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.napoleonit.testnapoleon2.di.ViewModelFactory
import javax.inject.Inject
import kotlin.random.Random


abstract class BaseActivity<T : ViewModel> : AppCompatActivity() {
    @Inject
    protected lateinit var viewModelFactory: ViewModelFactory

    val viewModel by lazy { ViewModelProvider(this, viewModelFactory).get(viewModelClass) }

    private val permissionMap = mutableMapOf<Int, PermissionHandler>()

    abstract val viewModelClass: Class<T>

    fun withPermission(
        permission: String,
        onSuccessPermRequest: () -> Unit,
        onFailedPermRequest: () -> Unit
    ) {
        if (ActivityCompat.checkSelfPermission(this, permission) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            onSuccessPermRequest()
        } else {
            val requestCode = unusedPermRequestCode()
            permissionMap[requestCode] = PermissionHandler(
                onSuccess = onSuccessPermRequest,
                onError = onFailedPermRequest
            )
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        val permissionHandler = permissionMap[requestCode] ?: return
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            permissionHandler.onSuccess()
        } else {
            permissionHandler.onError()
        }
    }

    fun unusedPermRequestCode(): Int {
        var requestCode = Random.nextInt() and 0xFFFF
        while (permissionMap.containsKey(requestCode)) {
            requestCode = Random.nextInt() and 0xFFFF
        }
        return requestCode
    }

    private class PermissionHandler(
        val onSuccess: () -> Unit,
        val onError: () -> Unit
    )
}