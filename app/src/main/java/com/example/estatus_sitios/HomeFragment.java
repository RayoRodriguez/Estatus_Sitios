package com.example.estatus_sitios;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HomeFragment extends Fragment {

    Button btn;
    TextView txtConsutla;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.app_name);

        progressBar = (ProgressBar) view.findViewById(R.id.progresbar);
        progressBar.setVisibility(View.INVISIBLE);

        txtConsutla = (TextView) view.findViewById(R.id.txtPrueba);
        txtConsutla.setMovementMethod(new ScrollingMovementMethod());

        btn = (Button) view.findViewById(R.id.btnPrueba);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                for(int i=0; i<=100; i++){
//                    cargarDatos("Numero: "+i);
//                }
                //ObtenerDatos();
                MyTask task = new MyTask();
                task.execute();
            }
        });
    }

    public void cargarDatos(String datos) {
        txtConsutla.append(datos + "\n");
    }

    public boolean isOnline(){
        return true;
    }

    private class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cargarDatos("Inicio de carga");
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            for (int i = 0; i <= 10; i++) {
                publishProgress("Numero: "+i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Terminamos";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            cargarDatos(s);
            progressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            cargarDatos(values[0]);
        }
    }

    public boolean ObtenerDatos() {
        String datosConsultado = "";
        String datosConsultado2 = "";
        try {
            //Se obtiene la conexión
            Connection connect = ConnectSQL.ConnectionHelper();
            //Se genera la consulta
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery("select latitud,longitud from sitio where id='1'");
            while (rs.next()) {
                //Se extraen los datos
                datosConsultado = rs.getString("latitud");
                datosConsultado2 = rs.getString("longitud");
            }
            //Se cierra la conexión
            connect.close();
            //Mostramos los datos obtenidos
            Toast.makeText(getActivity(),
                    datosConsultado + " -- " + datosConsultado2, Toast.LENGTH_SHORT).show();
            return true;
        } catch (SQLException e) {
            //Mostramos el error en caso de no conectarse
            Toast.makeText(getActivity(),
                    e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
