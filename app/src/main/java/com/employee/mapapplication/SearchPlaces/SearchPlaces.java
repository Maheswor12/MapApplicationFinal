package com.employee.mapapplication.SearchPlaces;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.employee.mapapplication.R;
import com.employee.mapapplication.model.LatitudeLongitude;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

public class SearchPlaces extends AppCompatActivity {
    private GoogleMap mMap;
    private AutoCompleteTextView etCity;
    private Button btnSearch;
    private List<LatitudeLongitude> latitudeLongitudes;
    Marker markerName;
    CameraUpdate center, zoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_search_places );

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById( R.id.map );
        mapFragment.getMapAsync( (OnMapReadyCallback) this );
        etCity = findViewById( R.id.etCity );
        btnSearch = findViewById( R.id.btnSearch );
        filLArrayListandSetAdapter();
        btnSearch.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty( etCity.getText().toString() )) {
                    etCity.setError( "PLEASE ENTER A PLACE NAME" );
                    return;
                }
//                to get the current location of the place
                int position = SearchArrayList( etCity.getText().toString() );
                if (position > -1)
                    loadMap( position );
                else
                    Toast.makeText( SearchPlaces.this, "location not found by name"
                            + etCity.getText().toString(), Toast.LENGTH_SHORT ).show();

            }
        } );
    }

    private void filLArrayListandSetAdapter() {
        latitudeLongitudes = new ArrayList<>();
        latitudeLongitudes.add( new LatitudeLongitude( 27.706195, 85.3300396, "softwarica college" ) );
        latitudeLongitudes.add( new LatitudeLongitude( 27.7079284, 85.330543, "galaxy college" ) );
        latitudeLongitudes.add( new LatitudeLongitude( 85.3300396, 27.706195, "city center" ) );

        String[] data = new String[latitudeLongitudes.size()];
        for (int i = 0; i < data.length; i++) {
            data[i] = latitudeLongitudes.get( i ).getMarker();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>( SearchPlaces.this, android.R.layout.simple_list_item_1, data );
        etCity.setAdapter( adapter );
        etCity.setThreshold( 1 );
    }

    public int SearchArrayList(String name) {
        for (int i = 0; i < latitudeLongitudes.size(); i++) {
            if (latitudeLongitudes.get( i ).getMarker().contains( name )) {
                return i;
            }
        }
        return -1;
    }

    private void loadMap(int position) {

    }
}
