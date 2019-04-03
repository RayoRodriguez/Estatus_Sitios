package com.example.estatus_sitios;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AvailabilityFragment extends Fragment
        implements OnMapReadyCallback {

    private GoogleMap ngoogleMap;
    private MapView mapView;
    private Marker marcador;
    private Button btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_availability, null);
        btn = view.findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogBox dialogBox = new DialogBox();
                dialogBox.show(getFragmentManager(),"Dialogo");
            }
        });
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.titleAvailability);
        mapView = view.findViewById(R.id.mapView);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng Mexico = new LatLng(23.3134142,-111.6559662);
        MapsInitializer.initialize(getContext());
        ngoogleMap = googleMap;
        //ObtenerDatos();
        Estatus();
        ngoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Mexico,5));
        ngoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                marker.getPosition();
                Toast.makeText(getActivity(),marker.getPosition().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean Estatus() {
        double latitud, longitud;
        String NumSitio="";
        try {
            //Se obtiene la conexión
            Connection connect = ConnectSQL.ConnectionHelper();
            //Se genera la consulta
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery("SELECT sitio.latitud,sitio.longitud,sitio.NumeroSitio, sitio.EstatusID, estatus.Nombre, estatus.ID " +
                    "FROM sitio INNER JOIN estatus ON sitio.EstatusID = estatus.ID " +
                    "WHERE estatus.Nombre = 'En incidente'  ");
            while (rs.next()) {
                //Se extraen los datos
                latitud = rs.getDouble("latitud");
                longitud = rs.getDouble("longitud");
                NumSitio= rs.getString("NumeroSitio");
                LatLng coordenadas = new LatLng(latitud, longitud);
                marcador = ngoogleMap.addMarker(new MarkerOptions()
                        .position(coordenadas)
                        .title(NumSitio)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            }
            //Se cierra la conexión
            connect.close();
            return  true;
        } catch (SQLException e) {
            //Mostramos el error en caso de no conectarse
            Toast.makeText(getActivity(),
                    e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    public boolean ObtenerDatos() {
        double latitud, longitud;
        String NumSitio="";
        try {
            //Se obtiene la conexión
            Connection connect = ConnectSQL.ConnectionHelper();
            //Se genera la consulta
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery("select latitud,longitud,NumeroSitio  from sitio");
            while (rs.next()) {
                //Se extraen los datos
                latitud = rs.getDouble("latitud");
                longitud = rs.getDouble("longitud");
                NumSitio= rs.getString("NumeroSitio");
                LatLng coordenadas = new LatLng(latitud, longitud);
                marcador = ngoogleMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title(NumSitio));
            }
            //Se cierra la conexión
            connect.close();
            return  true;
        } catch (SQLException e) {
            //Mostramos el error en caso de no conectarse
            Toast.makeText(getActivity(),
                    e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
