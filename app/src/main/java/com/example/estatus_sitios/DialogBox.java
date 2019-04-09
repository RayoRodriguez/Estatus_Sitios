package com.example.estatus_sitios;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DialogBox extends DialogFragment {

    private Spinner spPais;
    private Button btnCancelar;
    ArrayList listaPaises;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog, null);
        spPais = view.findViewById(R.id.spPais);

        btnCancelar =view.findViewById(R.id.btnCancel);
        return view;
    }

    public boolean spinerPais() {
        String nombre="";
        try {
            //Se obtiene la conexión
            Connection connect = ConnectSQL.ConnectionHelper();
            //Se genera la consulta
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery("SELECT Descripcion" +
                    "FROM Pais");
            while (rs.next()) {
                //Se extraen los datos
                nombre = rs.getString("Descripcion");
                listaPaises.add(nombre);
                //SimpleCursorAdapter adaptador = new SimpleCursorAdapter(getContext(),android.R.layout.simple_spinner_item,dataSource.getAllGeneres(),new String[]{DatabaseScript.GenereColumns.});
                //spPais.setAdapter(adaptador);
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
