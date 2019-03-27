package com.example.haritam;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.SoundPool;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.xml.transform.Source;
                                                                                    //kullanıcı bi yere uzun tıklama
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback , GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    //defult gelen kodlar
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
/*
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);//enlem ve boylam Latlng--yer belirten obje

        //marker kırmıı işaret
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

        // enlem ve boylamları kullanara haritada yer bulmayı yaptık
      /*  LatLng mevlana = new LatLng(37.870712, 32.504965);
        mMap.addMarker(new MarkerOptions().position(mevlana).title("MEVLANA"));*/
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(mevlana));

        //zoomlanmış olarak baslamak  için
       /* mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mevlana, 15));*/

        //önemli ipucu astudio ile ilgil  //eğer kodda herhangi biseyin üstüne  gelip contol-Q ye  ile ilgigi dökumantasyonu göstericek---önemliipucu

        //kullanıcının yerini bulmak için
        /* locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);*/


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        //locatiomanagere yardımcı sınıf
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {//yer degirse adresi alır
/*
                //geoder sınıfı cografik kodlama bir adresi enlem boylama dönüstürme demektir.
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                try {
                    List<Address> addressesList = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                    if(addressesList != null && addressesList.size()>0){
                        System.out.println("adress"+addressesList.get(0).toString());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }*/

/*
                //harita üzerinde her şeyi temizler
                mMap.clear();
                //zoom
                LatLng userLocation = new LatLng(location.getLatitude(),location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(userLocation).title("Konumun"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,15));
*/

                /* //yeri değiştiğinde ne yapılıcak
                //location.toString();//kullanıcın yeri değiştikten sonraki yeri
                System.out.println("location" + location.toString());*/
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                //kullanıcı gps i bulunamadıysa
            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

      if(Build.VERSION.SDK_INT >= 23){
          if (checkSelfPermission( Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions( new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
          }else {
              locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, locationListener);
              Location lastLocation =  locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
              System.out.println("lastLocation"+lastLocation);
              LatLng userLastLocatoin = new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
              mMap.addMarker(new MarkerOptions().title("Konumunuz").position(userLastLocatoin));
              mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLastLocatoin,15));
                        }
          }
          else {
                locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, locationListener);
                Location lastLocation =  locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                System.out.println("lastLocation"+lastLocation);
                LatLng userLastLocatoin = new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
                mMap.addMarker(new MarkerOptions().title("Konumunuz").position(userLastLocatoin));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLastLocatoin,15));
          }

          mMap.setOnMapLongClickListener(this);



       /*
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        //izin yoksa yapılcak
        else {
            //kullanıcının yerini al   //eğer 0 0 olursa her saniye location bulmaya çalışır kullanıcınn pilini yer ///0,0,locationListener
            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 5000, 500, locationListener);

            //son bilinen lokasyonu alma
            Location lastLocation =  locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            System.out.println("lastLocation"+lastLocation);
            LatLng userLastLocatoin = new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
            mMap.addMarker(new MarkerOptions().title("Konumunuz").position(userLastLocatoin));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLastLocatoin,15));
        }*/

    }

    //kullanıcı izinleri ilk defa verdiğinde yapılcaklar
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //bir sonuç geldiyse
        if(grantResults.length > 0){
            //birden çok izin durumu olabilr,o yüzden hangisi olduğu kontrol ediyoruz
            if(requestCode == 1){
                //izni verdi mi
                if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,500,locationListener);
                }
            }

        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

        mMap.clear();
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        String adress ="";
        try {
            List<Address> addressList = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
            if (addressList != null && addressList.size()> 0){
                if (addressList.get(0).getThoroughfare() != null ){
                    adress += addressList.get(0).getThoroughfare();

                    if (addressList.get(0).getSubThoroughfare() != null){
                        adress += addressList.get(0).getSubThoroughfare();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (adress.matches("")){
            adress = "Adres Yok";
        }

        mMap.addMarker(new MarkerOptions().position(latLng).title(adress));
    }
}
