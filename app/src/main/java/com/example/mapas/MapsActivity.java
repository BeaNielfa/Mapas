package com.example.mapas;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    Marker marker;
    Circle circle;
    LatLng posAnterior;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        marker = mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

        Marker central =mMap.addMarker(new MarkerOptions().position(new LatLng(38.6899915,-4.1082936))
                .title("CIFP Virgen de Gracia").snippet("Teléfono: 926 75 50 17")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .draggable(true));

        posAnterior = new LatLng(central.getPosition().latitude, central.getPosition().longitude);

        mMap.addMarker(new MarkerOptions().position(new LatLng(38.6945623,-4.1120585))
                .title("IES Leonardo Da Vinci").snippet("Teléfono: 926 75 50 17")
                .draggable(true)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));;
        mMap.moveCamera(CameraUpdateFactory.newLatLng(central.getPosition()));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //mMap.setOnMarkerClickListener(this);
        // mMap.setOnMarkerDragListener(this);
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);

        //DEFINICION CIRCULO EN MAPA
        CircleOptions circleOptions = new CircleOptions()
                .center(new LatLng(central.getPosition().latitude, central.getPosition().longitude))
                .radius(1000)
                .fillColor(ContextCompat.getColor(this,R.color.colorPrimary))
                .strokeColor(ContextCompat.getColor(this,R.color.colorAccent))
                .strokeWidth(10);
        circle = mMap.addCircle(circleOptions);
    }

   /* @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(this, "Has hecho click en marker: "+marker.getTitle(), Toast.LENGTH_SHORT).show();
        return false;
    }


    @Override
    public void onMarkerDragStart(Marker marker) {
        Toast.makeText(this, "Se comienza a arrastrar el marker "+ marker.getTitle(), Toast.LENGTH_SHORT).show();
        marker.hideInfoWindow();
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        Log.i("MARKER","MARKER"+marker.getPosition().latitude+" , "+marker.getPosition().longitude);
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        Toast.makeText(this, "Se ha soltado el marker: "+marker.getTitle(), Toast.LENGTH_SHORT).show();
        marker.showInfoWindow();
    }*/



    @Override
    public void onMapClick(LatLng latLng) {
        Toast.makeText(this, "Has hecho click en: "+latLng.latitude+","+latLng.longitude, Toast.LENGTH_SHORT).show();
        //marker.setPosition(latLng);
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("Nuevo marker")
        );
        posAnterior = latLng;

        PolylineOptions rectOptions= new PolylineOptions()
                .add(posAnterior)
                .add(latLng);
        Polyline polylineOptions = mMap.addPolyline(rectOptions);

        //mueve la camara bruscamente
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        //solo mueve la camara
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        //mueve la camara y cambia el zoom
        // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,18));
        circle.setCenter(latLng);//MOVEMOS EL CIRCULO
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Toast.makeText(this, "Has hecho long click en: "+latLng.latitude+","+latLng.longitude, Toast.LENGTH_SHORT).show();
        //marker.setPosition(latLng);
    }
}
