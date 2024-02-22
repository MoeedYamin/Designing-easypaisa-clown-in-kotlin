package com.example.task9_designing.fragments.utilsclasses

import android.content.Context
import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.task9_designing.ProjectConstants
import com.example.task9_designing.R

class DialogUtils(context: Context) {

    fun requestCursorPermissionDialog(activity: Activity) {
            AlertDialog.Builder(activity)
                .setTitle(Resources.getSystem().getString(R.string.location_permission_needed))
                .setMessage(Resources.getSystem().getString(R.string.requires_current_location))
                .setPositiveButton(Resources.getSystem().getString(R.string.go_to_settings)) { _, _ ->
                    val intent =
                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts(ProjectConstants.PACKAGE, activity.packageName, null)
                    intent.data = uri
                    activity.startActivity(intent)
                }.setNegativeButton(Resources.getSystem().getString(R.string.cancel)) { dialog, _ ->
                    dialog.dismiss()
                }.show()
    }

    fun requestFinePermissionDialog(activity: Activity) {
        AlertDialog.Builder(activity)
            .setTitle(Resources.getSystem().getString(R.string.location_permission_needed))
            .setMessage(Resources.getSystem().getString(R.string.requires_location_access))
            .setPositiveButton(Resources.getSystem().getString(R.string.go_to_settings)) { _, _ ->
                val intent =
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts(ProjectConstants.PACKAGE, activity.packageName, null)
                intent.data = uri
                activity.startActivity(intent)
            }.setNegativeButton(Resources.getSystem().getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    fun requestCameraPermissionDialog(activity: Activity) {
            AlertDialog.Builder(activity)
                .setTitle(Resources.getSystem().getString(R.string.camera_permission_needed))
                .setMessage(Resources.getSystem().getString(R.string.requires_camera_access))
                .setPositiveButton(Resources.getSystem().getString(R.string.go_to_settings)) { _, _ ->
                    val intent =
                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts(ProjectConstants.PACKAGE, activity.packageName, null)
                    intent.data = uri
                    activity.startActivity(intent)
                }.setNegativeButton(Resources.getSystem().getString(R.string.cancel)) { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }









}