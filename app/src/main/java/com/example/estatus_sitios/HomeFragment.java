package com.example.estatus_sitios;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HomeFragment extends Fragment {

    Button btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,null);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn = (Button) view.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObtenerDatos();
            }
        });
    }

    public boolean ObtenerDatos() {
        String datosConsultado = "";
        try {
            //Se obtiene la conexión
            Connection connect = ConnectSQL.ConnectionHelper();
            //Se genera la consulta
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery("select latitud from dbo.sitios");
            while (rs.next()) {
                //Se extraen los datos
                datosConsultado = rs.getString("latitud");
            }
            //Se cierra la conexión
            connect.close();
            //Mostramos los datos obtenidos
            Toast.makeText(getActivity(),
                    datosConsultado, Toast.LENGTH_SHORT).show();
            return  true;
        } catch (SQLException e) {
            //Mostramos el error en caso de no conectarse
            Toast.makeText(getActivity(),
                    e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
