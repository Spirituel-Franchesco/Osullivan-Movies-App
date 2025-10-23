package com.example.osullivanmoviesapp.Vue;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
public class MapActivity extends SupportMapFragment implements OnMapReadyCallback
{
    private GoogleMap googleMap;
    public MapActivity() {
        getMapAsync(this);
    }
    @Override
    public void onMapReady(final GoogleMap gmap) {
        this.googleMap = gmap;
        //Setter les coordonnées GPS de notre college
        LatLng Osullivan=new LatLng(46.810339, -71.219795);
        //Ajouter un marqueur textuel
        this.googleMap.addMarker(new
                MarkerOptions().position(Osullivan).title("College Osullivan de Quebec"));
        //Déplacer la caméra vers notre emplacement
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(Osullivan));
        this.googleMap.setMinZoomPreference(14);
        this.googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(latLng.latitude + " : "+
                        latLng.longitude);
                googleMap.clear();
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,
                        10));
                googleMap.addMarker(markerOptions);
            }
        });
    }
}
