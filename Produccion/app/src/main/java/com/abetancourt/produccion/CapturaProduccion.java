package com.abetancourt.produccion;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CapturaProduccion extends AppCompatActivity {

    InputMethodManager inputMethodManager;
    SQLite sql;
    Vaca vaca;

    TextView lblNoVaca, lblEstVaca, lblFechaEst, lbl3OrdProd;
    EditText txtP1, txtP2, txtP3, txtNoVial, txtCorral;
    Button btnCerrar, btnGuardar;
    private ArrayAdapter<String> adapterSpinner;
    String i1,i2,i3,f1,f2,f3;
    int ordVialPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captura_produccion);
        init();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void init(){
        inputMethodManager = (InputMethodManager) CapturaProduccion.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        Bundle reicieveParams = getIntent().getExtras();
        int vaca_id = reicieveParams.getInt("ID");

        Boolean lab = reicieveParams.getBoolean("LAB"),
                ord = reicieveParams.getBoolean("ORD");

        i1 = reicieveParams.getString("I1");
        i2 = reicieveParams.getString("I2");
        i3 = reicieveParams.getString("I3");
        f1 = reicieveParams.getString("F1");
        f2 = reicieveParams.getString("F2");
        f3 = reicieveParams.getString("F3");
        ordVialPos = reicieveParams.getInt("VIAL");

        sql = new SQLite(this);
        vaca = sql.obtenerVaca(vaca_id);

        if (vaca.getCodEst().equals("3")||vaca.getCodEst().equals("6")){
            new AlertDialog.Builder(this)
                    .setMessage("La vaca esta seca, revise si la producción corresponde a esta vaca")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create().show();
        }
        if (vaca.getCodEst().equals("7")||vaca.getCodEst().equals("8")||vaca.getCodEst().equals("9")){
            new AlertDialog.Builder(this)
                    .setMessage("La vaca ya fue reportada Vendida / Rastro / Muerta (según corresponda), revise si la producción corresponde a esta vaca")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create().show();
        }
        if (vaca.getCodEst().equals("1")||vaca.getCodEst().equals("2")||vaca.getCodEst().equals("5")) {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date fechaest = formato.parse(vaca.getFechaEst());
                Date ahora = new Date();
                long fe = fechaest.getTime();
                long ah = ahora.getTime();
                long diffDays = (ah - fe) / (24 * 60 * 60 * 1000);
                if (diffDays<=7){
                    new AlertDialog.Builder(this)
                        .setMessage("La vaca esta fresca, han pasado "+diffDays+" días.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        lblNoVaca = (TextView)findViewById(R.id.lblNoVaca);
        lblEstVaca = (TextView)findViewById(R.id.lblEstVaca);
        lblFechaEst = (TextView)findViewById(R.id.lblFechaEst);
        lbl3OrdProd = (TextView) findViewById(R.id.lbl3OrdProd);
        txtNoVial = (EditText)findViewById(R.id.txtNoVial);
        txtCorral = (EditText)findViewById(R.id.txtCorral);
        txtP1 = (EditText)findViewById(R.id.txtP1);
        txtP2 = (EditText)findViewById(R.id.txtP2);
        txtP3 = (EditText)findViewById(R.id.txtP3);
        btnCerrar = (Button)findViewById(R.id.btnCerrarProd);
        btnGuardar = (Button)findViewById(R.id.btnGuardarProd);

        lblNoVaca.setText("No Vaca: "+vaca.getNoVaca());
        lblFechaEst.setText("Fecha Est: "+vaca.getFechaEst());
        if(vaca.getCodEst().equals("1") || vaca.getCodEst().equals("2")) {
            lblEstVaca.setText("Estado de Vaca: Parida");
        }else if(vaca.getCodEst().equals("3")) {
            lblEstVaca.setText("Estado de Vaca: Comprada Seca");
        }else if(vaca.getCodEst().equals("4")) {
            lblEstVaca.setText("Estado de Vaca: Comprada en leche");
        }else if(vaca.getCodEst().equals("5")) {
            lblEstVaca.setText("Estado de Vaca: Aborto");
            lblEstVaca.setTextColor(Color.RED);
        }else if(vaca.getCodEst().equals("6")) {
            lblEstVaca.setText("Estado de Vaca: Seca");
            lblEstVaca.setTextColor(Color.GRAY);
        }else if(vaca.getCodEst().equals("7")) {
            lblEstVaca.setText("Estado de Vaca: Vendida");
            lblEstVaca.setTextColor(Color.RED);
        }else if(vaca.getCodEst().equals("8")) {
            lblEstVaca.setText("Estado de Vaca: Rastro");
            lblEstVaca.setTextColor(Color.RED);
        }else if(vaca.getCodEst().equals("9")) {
            lblEstVaca.setText("Estado de Vaca: Muerta");
            lblEstVaca.setTextColor(Color.RED);
        }
        txtNoVial.setText(vaca.getNoVial());
        if (vaca.getLoteT()>0)txtCorral.setText(vaca.getLoteT()+"");
        if (vaca.getPeso1()>0)txtP1.setText(vaca.getPeso1()+"");
        if (vaca.getPeso2()>0)txtP2.setText(vaca.getPeso2()+"");
        if (vaca.getPeso3()>0)txtP3.setText(vaca.getPeso3()+"");

        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CapturaProduccion.this.finish();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtNoVial.getText().toString().isEmpty())
                    vaca.setNoVial(txtNoVial.getText().toString());
                if (!txtCorral.getText().toString().isEmpty())
                    vaca.setLoteT(Integer.parseInt(txtCorral.getText().toString()));
                if (!txtP1.getText().toString().isEmpty())
                    vaca.setPeso1(Integer.parseInt(txtP1.getText().toString()));
                if (!txtP2.getText().toString().isEmpty())
                    vaca.setPeso2(Integer.parseInt(txtP2.getText().toString()));
                if (!txtP3.getText().toString().isEmpty())
                    vaca.setPeso3(Integer.parseInt(txtP3.getText().toString()));

                Object verifica = sql.verificarVialRepetido(vaca.getNoVial(), vaca.getNoHato(), vaca.getFecha());
                if (verifica == null) {

                    Object act = sql.actualizarVaca(vaca);
                    if (act instanceof Boolean) {
                        Toast.makeText(CapturaProduccion.this, "Datos Guardados", Toast.LENGTH_SHORT).show();
                        CapturaProduccion.this.finish();
                    } else if (act instanceof String)
                        Toast.makeText(CapturaProduccion.this, (String) act, Toast.LENGTH_SHORT).show();

                    if (i1 != null) { // Primera ordeña
                        if (f1 != null & i2 != null) { // segunda Ordeña
                            if (f2 != null && i3 != null) // Tercera Ordeña
                                sql.actualizarHorarioCaptura(vaca.getId(), 3);
                            else
                                sql.actualizarHorarioCaptura(vaca.getId(), 2);
                        } else
                            sql.actualizarHorarioCaptura(vaca.getId(), 1);
                    }
                } else if (verifica instanceof String) {
                    new AlertDialog.Builder(CapturaProduccion.this)
                            .setMessage("La vaca " + (String)verifica + " ya tiene asignado el vial "+vaca.getNoVial()+", No se puede guardar.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).create().show();
                }

            }
        });

        if (i1!=null)txtP1.setEnabled(true);
        if (f1!=null)txtP1.setEnabled(false);
        if (i2!=null) txtP2.setEnabled(true);
        if (f2 !=null) txtP2.setEnabled(false);
        if (i3!=null) txtP3.setEnabled(true);
        if (f3 !=null) txtP3.setEnabled(false);

        if (!lab)txtNoVial.setVisibility(View.INVISIBLE);
        if (!ord){
            txtP3.setVisibility(View.INVISIBLE);
            lbl3OrdProd.setVisibility(View.INVISIBLE);
        }

        if(i1==null || f1!=null)
            txtCorral.setEnabled(false);

        if(ordVialPos==1) {
            if (i1 == null || f1 != null) txtNoVial.setEnabled(false);
        }
        else if (ordVialPos==2) {
            if (i2 == null || f2 != null) txtNoVial.setEnabled(false);
        }
        else if (ordVialPos==3) {
            if (i3 == null || f3 != null) txtNoVial.setEnabled(false);
        }

    }
}
