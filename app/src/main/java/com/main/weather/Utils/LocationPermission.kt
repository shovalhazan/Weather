package com.main.weather.Utils

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

const val MANUALLY_PERMISSION_REQUEST_CODE :Int = 111
const val PERMISSIONS_LOCATION_REQUEST_CODE :Int = 112
const val PERMISSIONS_BACKGROUND_LOCATION_REQUEST_CODE :Int = 113

class LocationPermission(context: Context) {
    lateinit var context : Context

    init {
        this.context=context
    }
    private fun checkForBackgroundLocationPermission() :Boolean{
        if ( ContextCompat.checkSelfPermission( context, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    fun checkForFineLocationPermission(): Boolean {
        if ( ContextCompat.checkSelfPermission( context, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }
    fun  checkForCoarseLocationPermission(): Boolean {
        if ( ContextCompat.checkSelfPermission( context, Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }
    fun checkForPermission(permission:String ): Boolean{
        if ( ContextCompat.checkSelfPermission( context, permission)
            == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    fun  requestForLocation() {
        val per1:Boolean = checkForCoarseLocationPermission();
        val per2 :Boolean= checkForFineLocationPermission();
        val per3:Boolean = android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.Q || checkForBackgroundLocationPermission();

        if (!per1  ||  !per2) {
            // if i can ask for permission
            requestFirstLocationPermission();
        } else if (!per3) {
            // if i can ask for permission
            requestSecondLocationPermission();
        } else {
            Log.d("pttt", "requestForLocation: ");
        }
    }
    private fun requestSecondLocationPermission() {
        // Background location permissions
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_BACKGROUND_LOCATION), PERMISSIONS_BACKGROUND_LOCATION_REQUEST_CODE)
    }
    private fun requestFirstLocationPermission() {
        // Regular location permissions
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSIONS_LOCATION_REQUEST_CODE)
    }

    fun requestPermissionWithRationaleCheck( permission:String, code :Int):Boolean{
        if (ActivityCompat.shouldShowRequestPermissionRationale(context as Activity,permission)) {
            Log.d("pttt", "shouldShowRequestPermissionRationale = true");
            // Show user description for what we need the permission
            ActivityCompat.requestPermissions(context as Activity,
                arrayOf(permission),
                code);
            return true;
        } else {
            return false;
        }
    }
    fun  openPermissionSettingDialog() {
         val message:String = "We must your permission to CAMERA ,LOCATION and STORAGE.\nPlease add the missing permission in the settings screen.";
         val alertDialog : AlertDialog = AlertDialog.Builder((context))
            .setMessage(message)
            .setPositiveButton((context as Activity).getString(android.R.string.ok),
                object: DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        val intent: Intent = Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        val uri:Uri = Uri.fromParts("package", (context).getPackageName(), null);
                        intent.setData(uri);
                        (context as Activity).startActivityForResult(intent, MANUALLY_PERMISSION_REQUEST_CODE);
                        if (dialog != null) {
                            dialog.cancel()
                        }
                    }
                }).show();
        alertDialog.setCanceledOnTouchOutside(false);
    }
}