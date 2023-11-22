package com.example.agenda_online.Detalle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.agenda_online.R;

public class Detalle_Nota extends AppCompatActivity {

    TextView ID_nota_Detalle, Uid_Usuario_Detalle, Correo_usuario_Detalle, Titulo_Detalle, Descripcion_Detalle, Fecha_Registro_Detalle, Fecha_Nota_Detalle, Estado_Detalle;

    String id_nota_R, Uid_Usuario_R, Correo_usuario_R, Fecha_registro_R, Fecha_R, Estado_R, Titulo_R, Descripcion_R;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_nota);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Detalle de nota");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        inicializarVistas();
        RecuperarDatos();
        SetearDatosRecuperados();
    }

    private void inicializarVistas(){
        ID_nota_Detalle = findViewById(R.id.ID_nota_Detalle);
        Uid_Usuario_Detalle = findViewById(R.id.Uid_Usuario_Detalle);
        Correo_usuario_Detalle = findViewById(R.id.Correo_usuario_Detalle);
        Titulo_Detalle = findViewById(R.id.Titulo_Detalle);
        Descripcion_Detalle = findViewById(R.id.Descripcion_Detalle);
        Fecha_Registro_Detalle = findViewById(R.id.Fecha_Registro_Detalle);
        Fecha_Nota_Detalle = findViewById(R.id.Fecha_Nota_Detalle);
        Estado_Detalle = findViewById(R.id.Estado_Detalle);
    }
    private void RecuperarDatos() {
        Bundle intent = getIntent().getExtras();
        id_nota_R = intent.getString("id_nota");
        Uid_Usuario_R = intent.getString("uid_usuario");
        Correo_usuario_R = intent.getString("correo_usuario");
        Fecha_registro_R = intent.getString("fecha_registro");
        Titulo_R = intent.getString("titulo");
        Descripcion_R = intent.getString("descripcion");
        Fecha_R = intent.getString("fecha_nota");
        Estado_R = intent.getString("estado");
    }
    private void SetearDatosRecuperados(){
        ID_nota_Detalle.setText(id_nota_R);
        Uid_Usuario_Detalle.setText(Uid_Usuario_R);
        Correo_usuario_Detalle.setText(Correo_usuario_R);
        Fecha_Registro_Detalle.setText(Fecha_registro_R);
        Titulo_Detalle.setText(Titulo_R);
        Descripcion_Detalle.setText(Descripcion_R);
        Fecha_Nota_Detalle.setText(Fecha_R);
        Estado_Detalle.setText(Estado_R);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}