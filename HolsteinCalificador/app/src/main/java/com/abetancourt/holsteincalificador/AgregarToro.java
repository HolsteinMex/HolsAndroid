package com.abetancourt.holsteincalificador;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class AgregarToro extends ActionBarActivity {

    String hato,fecha;
    private int mYear;
    private int mMonth;
    private int mDay;
    private String dayOfWeek;
    private ArrayList<String> clases;
    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
    SQLite sql;
    EditText txtNoReg,txtFechaNac,txtRegPadre,txtRegMadre,
            txtCalifAnt,txtNombre;
    Spinner spinClaseAnt;
    Button btnCancelar, btnGuardar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agrear_toro);
        Bundle reicieveParams = getIntent().getExtras();
        String hato_y_fecha = reicieveParams.getString("HATO");
        hato = hato_y_fecha.split(" - ")[0];
        fecha = hato_y_fecha.split(" - ")[1];
        init();
        listeners();
    }

    private void init(){
        sql = new SQLite(this);
        txtNoReg = (EditText)findViewById(R.id.txtAgrToroNoReg);
        txtFechaNac = (EditText)findViewById(R.id.txtAgrToroFechaNac);
        txtRegPadre = (EditText)findViewById(R.id.txtAgrToroRegPadre);
        txtRegMadre = (EditText)findViewById(R.id.txtAgrToroRegMadre);
        txtCalifAnt = (EditText)findViewById(R.id.txtAgrToroCalifAnt);
        txtNombre = (EditText)findViewById(R.id.txtAgrToroNombre);
        spinClaseAnt = (Spinner)findViewById(R.id.spinAgrToroClaseAnt);
        btnCancelar = (Button)findViewById(R.id.btnAgrToroCancelar);
        btnGuardar = (Button)findViewById(R.id.btnAgrToroAgregar);

        clases = new ArrayList<String>();
        clases.add("SC");
        clases.add("FA");
        clases.add("GO");
        clases.add("GP");
        clases.add("VG");
        clases.add("EX");
        ArrayAdapter<String> claseAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, clases);
        claseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinClaseAnt.setAdapter(claseAdapter);
    }

    private void listeners(){
        txtFechaNac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                new DatePickerDialog(AgregarToro.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                Calendar c = Calendar.getInstance();
                                c.set(year, monthOfYear, dayOfMonth);

                                dayOfWeek = formato.format(c.getTimeInMillis());
                                // display the current date (this method is below)
                                txtFechaNac.setText(dayOfWeek);
                            }
                        },
                        mYear, mMonth, mDay).show();
            }
        });

        txtFechaNac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                new DatePickerDialog(AgregarToro.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                Calendar c = Calendar.getInstance();
                                c.set(year, monthOfYear, dayOfMonth);

                                dayOfWeek = formato.format(c.getTimeInMillis());
                                // display the current date (this method is below)
                                txtFechaNac.setText(dayOfWeek);
                            }
                        },
                        mYear, mMonth, mDay).show();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AgregarToro.this.finish();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if(!txtFechaNac.getText().toString().isEmpty()){
                        formato.parse(txtFechaNac.getText().toString());
                    }
                    if (
                            !txtNoReg.getText().toString().isEmpty()
//                !txtFechaNac.getText().toString().isEmpty() &&
//                !txtRegPadre.getText().toString().isEmpty() &&
//                !txtRegMadre.getText().toString().isEmpty() &&
//                !txtFechaParto.getText().toString().isEmpty() &&
//                !txtCalifAnt.getText().toString().isEmpty() &&
                            ) {
                        Toro est = new Toro();
                        est.setId((int) (System.currentTimeMillis() / 1000));

                        est.setNoRegistro(txtNoReg.getText().toString());
                        est.setNoHato(hato);
                        est.setFecha(fecha);
                        est.setNombre(txtNombre.getText().toString());
                        est.setRegMadre(txtRegMadre.getText().toString());
                        est.setRegPadre(txtRegPadre.getText().toString());
                        est.setFechaNac(txtFechaNac.getText().toString());

                        if (!txtCalifAnt.getText().toString().isEmpty()) {
                            est.setCalifAnt(txtCalifAnt.getText().toString());
                        } else {
                            est.setCalifAnt("0");
                        }
                        est.setClaseAnt(spinClaseAnt.getSelectedItem().toString());


                        Object insert = sql.insertarCalifToro(est);
                        if (insert instanceof Boolean && (Boolean) insert) {
                            Toast.makeText(AgregarToro.this, "Toro Agregado con Éxito", Toast.LENGTH_LONG).show();
                            AgregarToro.this.finish();
                        } else if (insert instanceof Boolean && (Boolean) insert == false) {
                            Toast.makeText(AgregarToro.this, "Ya existe un registro de éste toro en éste Hato.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(AgregarToro.this, (String) insert, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(AgregarToro.this, "Llene el campo de Registro", Toast.LENGTH_LONG).show();
                    }
                } catch (ParseException e) {
                    Toast.makeText(AgregarToro.this, "El formato de la fecha es incorrecto.\nPresione 2 veces el campo para mostrar el calendario.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
