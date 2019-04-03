package com.example.estatus_sitios;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.Spinner;


public class DialogBox extends DialogFragment {

    private Spinner spEstatus;
    private Button btnCancelar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog, null);
        spEstatus = view.findViewById(R.id.spEstatus);
        btnCancelar =view.findViewById(R.id.btnCancel);
        return view;
    }
}
