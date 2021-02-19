package com.simple_weather.ui

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.simple_weather.R
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

private const val MY_PERMISSION_ACCESS_FINE_LOCATION = 1
class MainActivity : AppCompatActivity(),KodeinAware {

    override val kodein by closestKodein()
    private val fusedLocationProviderClient: FusedLocationProviderClient by instance()

    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult?) {
            super.onLocationResult(p0)
        }
    }

    private lateinit var navController:NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        bottom_nav.setupWithNavController(navController)

        requestLocationPermission()

        if (hasLocationPermission()) {
            bindLocationManager()

        } else requestLocationPermission()





        //NavigationUI.setupActionBarWithNavController(this,navController)


    }

    private fun bindLocationManager(){
        LifeCycleBoundLocationManager(this,
        fusedLocationProviderClient,
        locationCallback)

    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    }

    private fun requestLocationPermission() {
       ActivityCompat. requestPermissions(this,
               arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
               MY_PERMISSION_ACCESS_FINE_LOCATION)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
         if (requestCode == MY_PERMISSION_ACCESS_FINE_LOCATION){
            if (grantResults.isNotEmpty()&&grantResults[0] == PackageManager.PERMISSION_GRANTED) bindLocationManager()

             else Toast.makeText(this,"Пожалйста включите доступ к местоположению вручную", Toast.LENGTH_SHORT).show()

        }
    }



    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,null)
    }
}