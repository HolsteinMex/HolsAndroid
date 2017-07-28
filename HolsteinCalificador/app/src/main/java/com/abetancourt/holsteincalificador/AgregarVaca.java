package com.abetancourt.holsteincalificador;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class AgregarVaca extends AppCompatActivity {

    String hato,fecha;
    private int mYear;
    private int mMonth;
    private int mDay;
    private String dayOfWeek;
    private ArrayList<String> clases;
    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
    SQLite sql;
    EditText txtNoCP,txtNoReg,txtFechaNac,txtRegPadre,txtRegMadre,txtNoParto,txtFechaParto,
    txtCalifAnt,txtNoLact,txtMesesAb;
    Spinner spinClaseAnt;
    RadioButton radioNormal, radioAnormal;
    Button btnCancelar, btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_vaca);
        Bundle reicieveParams = getIntent().getExtras();
        String hato_y_fecha = reicieveParams.getString("HATO");
        hato = hato_y_fecha.split(" - ")[0];
        fecha = hato_y_fecha.split(" - ")[1];
        init();
        listeners();
    }

    private void init(){
        sql = new SQLite(this);
        txtNoCP = (EditText)findViewById(R.id.txtAgrVacaCP);
        txtNoReg = (EditText)findViewById(R.id.txtAgrVacaNoReg);
        txtFechaNac = (EditText)findViewById(R.id.txtAgrVacaFechaNac);
        txtRegPadre = (EditText)findViewById(R.id.txtAgrVacaRegPadre);
        txtRegMadre = (EditText)findViewById(R.id.txtAgrVacaRegMadre);
        txtNoParto = (EditText)findViewById(R.id.txtAgrVacaNoParto);
        txtFechaParto = (EditText)findViewById(R.id.txtAgrVacaFechaParto);
        txtCalifAnt = (EditText)findViewById(R.id.txtAgrVacaCalifAnt);
        txtNoLact = (EditText)findViewById(R.id.txtAgrVacaNoLact);
        txtMesesAb = (EditText)findViewById(R.id.txtAgrVacaMesesAb);
        spinClaseAnt = (Spinner)findViewById(R.id.spinAgrVacaClaseAnt);
        radioNormal = (RadioButton)findViewById(R.id.radioAgrVacaNormal);
        radioAnormal = (RadioButton)findViewById(R.id.radioAgrVacaAnormal);
        btnCancelar = (Button)findViewById(R.id.btnAgrVacaCancelar);
        btnGuardar = (Button)findViewById(R.id.btnAgrVacaAgregar);
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

                new DatePickerDialog(AgregarVaca.this,
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

        txtFechaParto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                new DatePickerDialog(AgregarVaca.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                Calendar c = Calendar.getInstance();
                                c.set(year, monthOfYear, dayOfMonth);

                                dayOfWeek = formato.format(c.getTimeInMillis());
                                // display the current date (this method is below)
                                txtFechaParto.setText(dayOfWeek);
                            }
                        },
                        mYear, mMonth, mDay).show();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AgregarVaca.this.finish();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (//!txtCURV.getText().toString().isEmpty() &&
                !txtNoCP.getText().toString().isEmpty() &&
//                !txtNoReg.getText().toString().isEmpty() &&
//                !txtFechaNac.getText().toString().isEmpty() &&
//                !txtRegPadre.getText().toString().isEmpty() &&
//                !txtRegMadre.getText().toString().isEmpty() &&
                !txtNoParto.getText().toString().isEmpty()
//                !txtFechaParto.getText().toString().isEmpty() &&
//                !txtCalifAnt.getText().toString().isEmpty() &&
//                !txtNoLact.getText().toString().isEmpty() &&
//                !txtMesesAb.getText().toString().isEmpty()
                        ){
                    String tipopar = "N";
                    if (radioAnormal.isChecked())tipopar="A";
                    Vaca est = new Vaca();
                    est.setId((int) (System.currentTimeMillis() / 1000));

                    est.setNoRegistro(txtNoReg.getText().toString());
                    est.setNoHato(hato);
                    est.setFecha(fecha);
                    est.setCurv(0);
                    est.setNoCP(txtNoCP.getText().toString());
                    est.setRegmadre(txtRegMadre.getText().toString());
                    est.setRegPadre(txtRegPadre.getText().toString());
                    est.setFechaNac(txtFechaNac.getText().toString());
                    if (!txtNoLact.getText().toString().isEmpty())
                        est.setNoLactancia(Integer.parseInt(txtNoLact.getText().toString()));
                    est.setFechaParto(txtFechaParto.getText().toString());
                    if (!txtCalifAnt.getText().toString().isEmpty()) {
                        est.setCalifAnt(Integer.parseInt(txtCalifAnt.getText().toString()));
                        est.setClaseAnt(spinClaseAnt.getSelectedItem().toString());
                    }
                    est.setTipoParto(tipopar);
                    if (!txtMesesAb.getText().toString().isEmpty())
                        est.setMesesAborto(Integer.parseInt(txtMesesAb.getText().toString()));

                    Object insert = sql.insertarCalifVaca(est);
                    if (insert instanceof Boolean && (Boolean)insert) {
                        Toast.makeText(AgregarVaca.this, "Vaca Agregada con Éxito", Toast.LENGTH_LONG).show();
                        AgregarVaca.this.finish();
                    }
                    else if (insert instanceof Boolean && (Boolean)insert==false) {
                        Toast.makeText(AgregarVaca.this, "Ya existe un registro de ésta vaca en éste Hato.", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(AgregarVaca.this, (String) insert, Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(AgregarVaca.this, "Llene el campo de Medalla y Número de partos", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
