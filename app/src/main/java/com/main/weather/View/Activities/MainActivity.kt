package com.main.weather.View.Activities
import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.main.weather.R
import com.main.weather.Utils.LocationPermission
import com.main.weather.Utils.PERMISSIONS_LOCATION_REQUEST_CODE
import com.main.weather.View.Fragments.CitiesListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var permission :LocationPermission
    private lateinit var weatherFragment : CitiesListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkForConnection()
        permission= LocationPermission(this)
        if(!permission.checkForPermission(Manifest.permission.ACCESS_FINE_LOCATION)||
            !permission.checkForPermission( Manifest.permission.ACCESS_COARSE_LOCATION)){
                permission.requestForLocation()

        }else{
            addCitiesWeatherFragment()
        }
    }

    private fun checkForConnection(){
        val flag= checkForInternet()
        if(!flag){
            val builder = AlertDialog.Builder(this)
            builder.setMessage("NO INTERNET CONNECTION! \nPLEASE CHECK YORE CONNECTION")
                .setNeutralButton("OK",
                    DialogInterface.OnClickListener { dialog, id ->
                        val f = checkForInternet()
                        Log.d("pttt", "onStart: "+f)
                        if (f)
                            dialog.dismiss()
                    })
            val dialog: AlertDialog? = builder?.create()
            dialog?.show()
            dialog?.setCancelable(false)
            dialog?.setCanceledOnTouchOutside(false)
        }
    }
    private fun addCitiesWeatherFragment(){
        weatherFragment= CitiesListFragment()
        val manager = supportFragmentManager
        var transaction = manager.beginTransaction()
        transaction.replace(R.id.activityMain_fragment_container_view,weatherFragment).commit()
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>, grantResults: IntArray ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            PERMISSIONS_LOCATION_REQUEST_CODE->{
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        addCitiesWeatherFragment()
                        return;
                }else {
                    val locationFlag=permission.requestPermissionWithRationaleCheck(Manifest.permission.ACCESS_FINE_LOCATION,PERMISSIONS_LOCATION_REQUEST_CODE);
                    if(!locationFlag) {
                        permission.openPermissionSettingDialog();
                        return;
                    }
                }
                return;
            }
        }
    }
    private fun checkForInternet(): Boolean {

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            val network = connectivityManager.activeNetwork ?: return false

            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if((!permission.checkForFineLocationPermission()||!permission.checkForCoarseLocationPermission()))
            permission.openPermissionSettingDialog();
        else
            addCitiesWeatherFragment()
    }


}