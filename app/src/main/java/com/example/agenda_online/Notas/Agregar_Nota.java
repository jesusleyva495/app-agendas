package com.example.agenda_online.Notas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agenda_online.Objetos.Nota;
import com.example.agenda_online.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Agregar_Nota extends AppCompatActivity {

    TextView Uid_Usuario, Correo_usuario, Fecha_hora_actual, Fecha, Estado;
    EditText Titulo, Descripcion;
    Button Btn_Calendario;

    int dia, mes, anio;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    DatabaseReference BD_Firebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_nota);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        InicializarVariables();
        ObtenerDatos();
        Obtener_Fecha_Hora_Actual();

        Btn_Calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar caledario = Calendar.getInstance();

                dia = caledario.get(Calendar.DAY_OF_MONTH);
                mes = caledario.get(Calendar.MONTH);
                anio = caledario.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Agregar_Nota.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int AnioSeleccionado, int MesSeleccionado, int DiaSeleccionado) {

                        String diaFormatiado, mesFormatiado;

                        //Obtener dia
                        if (DiaSeleccionado < 10){
                            diaFormatiado = "0"+String.valueOf(DiaSeleccionado);
                        }else {
                            diaFormatiado = String.valueOf(DiaSeleccionado);
                        }

                        //Obtener el mes
                        int Mes = MesSeleccionado + 1;

                        if (Mes < 10){
                            mesFormatiado = "0"+String.valueOf(Mes);
                        }else {
                            mesFormatiado = String.valueOf(Mes);
                        }

                        //Setear fecha en TextView
                        Fecha.setText(diaFormatiado + "/" + mesFormatiado + "/" + AnioSeleccionado);
                    }
                }
                ,anio,mes,dia);
                datePickerDialog.show();
            }
        });


    }

    private void InicializarVariables(){
        Uid_Usuario = findViewById(R.id.Uid_Usuario);
        Correo_usuario = findViewById(R.id.Correo_usuario);
        Fecha_hora_actual = findViewById(R.id.Fecha_hora_actual);
        Fecha = findViewById(R.id.Fecha);
        Estado = findViewById(R.id.Estado);

        Titulo = findViewById(R.id.Titulo);
        Descripcion = findViewById(R.id.Descripcion);
        Btn_Calendario = findViewById(R.id.Btn_Calendario);

        BD_Firebase = FirebaseDatabase.getInstance().getReference("Usuarios");

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
    }

    private void ObtenerDatos(){
        String uid_recuperado = getIntent().getStringExtra("Uid");
        String correo_recuperado = getIntent().getStringExtra("Correo");

        Uid_Usuario.setText(uid_recuperado);
        Correo_usuario.setText(correo_recuperado);
    }

    private void Obtener_Fecha_Hora_Actual(){
        String Fecha_hora_registro = new SimpleDateFormat("dd-MM-yyyy/HH:mm:ss a",
                Locale.getDefault()).format(System.currentTimeMillis());

        Fecha_hora_actual.setText(Fecha_hora_registro);
    }

    private void Agregar_Nota(){

        //Obtener los datos
        String uid_usuario = Uid_Usuario.getText().toString();
        String correo_usuario = Correo_usuario.getText().toString();
        String fecha_hora_actual = Fecha_hora_actual.getText().toString();
        String titulo = Titulo.getText().toString();
        String descripcion = Descripcion.getText().toString();
        String fecha = Fecha.getText().toString();
        String estado = Estado.getText().toString();
        String id_nota = BD_Firebase.push().getKey();

        //validar datos
        if (!uid_usuario.equals("") && !correo_usuario.equals("") && !fecha_hora_actual.equals("") &&
        !titulo.equals("") && !descripcion.equals("") && !fecha.equals("") && !estado.equals("")){

            Nota nota = new Nota(correo_usuario+"/"+fecha_hora_actual,
                    uid_usuario,
                    correo_usuario,
                    fecha_hora_actual,
                    titulo,
                    descripcion,
                    fecha,
                    estado);
            String Nota_usuario = BD_Firebase.push().getKey();
            //Establecer el nombre de la BD
            String Nombre_BD = "Notas_Publicadas";

            assert id_nota != null;
            BD_Firebase.child(user.getUid()).child(Nombre_BD).child(id_nota).setValue(nota);
            Toast.makeText(this, "Se ha agregado la nota exitosamente", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }else {
            Toast.makeText(this, "Llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_agenda, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Agregar_Nota_BD){
            Agregar_Nota();
        }else {
            Toast.makeText(this,"No se pudo agregar la nota, falla en el programa", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}