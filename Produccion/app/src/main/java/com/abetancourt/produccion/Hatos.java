package com.abetancourt.produccion;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.zip.Inflater;

public class Hatos extends AppCompatActivity {

    SQLite sql;

    Spinner spinhatos;
    ListView listVacas;
    Button btnNuevaVaca, btnHorarioOrd, btnParams, btnAbajo, btnArriba;
    boolean lab=true, ord3=true;
    static ArrayList<String> hatos;
    private ArrayList<Vaca> vacas;
    private ArrayAdapter<String> adapterSpinner;
    private VacasAdapter adapterVacas;
    private EditText txtBuscarVaca;
    String hatoSeleccionado = null;
    int ordVialPos = 0;
    String i1,i2,i3,f1,f2,f3;

    boolean captura1=false,captura2=false,captura3=false; // Permite verificar que se haya capturado al menos una vaca

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hatos);
        init();
    }

    private void init(){
        sql = new SQLite(this);
        try {
            hatos = sql.obtenerHatos();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (hatos!=null && hatos.size()>1) {

            spinhatos = (Spinner) findViewById(R.id.spinnerHatos);
            listVacas = (ListView) findViewById(R.id.listVacas);
            btnNuevaVaca = (Button) findViewById(R.id.btnAgregarVaca);
            btnNuevaVaca.setEnabled(false);
            btnHorarioOrd = (Button) findViewById(R.id.btnOrdenas);
            btnParams = (Button) findViewById(R.id.btnParams);
            btnAbajo = (Button) findViewById(R.id.btnAbajo);
            btnArriba = (Button) findViewById(R.id.btnArriba);
            txtBuscarVaca = (EditText) findViewById(R.id.txtBuscarVaca);
            adapterSpinner = new ArrayAdapter<String>(this, R.layout.spinner_item, hatos);
            adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinhatos.setAdapter(adapterSpinner);

            btnAbajo.setEnabled(false);
            btnArriba.setEnabled(false);

            spinhatos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position > 0) {
                        hatoSeleccionado = spinhatos.getSelectedItem().toString();
                        llenar();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            btnAbajo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listVacas.setSelection(listVacas.getCount() - 1);
                }
            });

            btnArriba.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listVacas.setSelection(0);
                }
            });

            btnNuevaVaca.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(Hatos.this);

                    alert.setTitle("Agregar Vaca");
                    alert.setMessage("Inserte el número de vaca o medalla.");

                    final EditText input = new EditText(Hatos.this);
                    input.setInputType(InputType.TYPE_CLASS_NUMBER);
                    alert.setView(input);

                    alert.setPositiveButton("Agregar Vaca", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String value = input.getText().toString();
                            if (value.isEmpty())
                                Toast.makeText(Hatos.this, "No se ha agregado la vaca porque no tien número de medalla", Toast.LENGTH_SHORT).show();
                            else {
                                Boolean existe = false;
                                for (Vaca v : vacas) {
                                    if (v.getNoVaca().trim().equals(value)) {
                                        Toast.makeText(Hatos.this, "La medalla ya existe en este listado.", Toast.LENGTH_SHORT).show();
                                        existe = true;
                                        break;
                                    }
                                }
                                if (!existe) {
                                    int id = sql.minimoId();
                                    if (id != 0) {
                                        if (id > 0) id = -1;
                                        else id = id - 1;
                                        Vaca v = new Vaca(id, hatoSeleccionado.split(" - ")[0], value.trim(), 0, sql.fechaActual(), "1", hatoSeleccionado.split(" - ")[1]);
                                        sql.AbrirBD();
                                        Object ins = sql.insertarVaca(v);
                                        sql.CerrarBD();
                                        if (ins instanceof Boolean && (Boolean) ins) {
                                            Toast.makeText(Hatos.this, "Vaca " + value.trim() + " agregada con ID=" + id + ".", Toast.LENGTH_SHORT).show();
                                            llenar();
                                        } else if (ins instanceof Boolean && !(Boolean) ins)
                                            Toast.makeText(Hatos.this, "El ID=" + id + " no está disponible.", Toast.LENGTH_SHORT).show();
                                        else
                                            Toast.makeText(Hatos.this, (String) ins, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Hatos.this, "No se ha agregado la vaca por un error interno.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });

                    alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Canceled.
                        }
                    });

                    alert.show();
                    // Intent i = new Intent(Hatos.this, AgregarVaca.class);
                    // i.putExtra("HATO", hatoSeleccionado);
                    // startActivity(i);
                }
            });

            btnHorarioOrd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (hatoSeleccionado != null) {
                        LayoutInflater inflater = Hatos.this.getLayoutInflater();
                        View vistaDialog = inflater.inflate(R.layout.ordenas_alert_view, null);
                        final Button btnI1, btnF1, btnI2, btnF2, btnI3, btnF3;
                        TextView lbl;

                        btnI1 = (Button) vistaDialog.findViewById(R.id.btnI1);
                        btnI2 = (Button) vistaDialog.findViewById(R.id.btnI2);
                        btnI3 = (Button) vistaDialog.findViewById(R.id.btnI3);
                        btnF1 = (Button) vistaDialog.findViewById(R.id.btnF1);
                        btnF2 = (Button) vistaDialog.findViewById(R.id.btnF2);
                        btnF3 = (Button) vistaDialog.findViewById(R.id.btnF3);
                        lbl = (TextView) vistaDialog.findViewById(R.id.lbl3ord);

                        btnI2.setEnabled(false);
                        btnI3.setEnabled(false);
                        btnF1.setEnabled(false);
                        btnF2.setEnabled(false);
                        btnF3.setEnabled(false);

                        if (!ord3){
                            lbl.setVisibility(View.GONE);
                            btnI3.setVisibility(View.GONE);
                            btnF3.setVisibility(View.GONE);
                        }

                        if (i1 != null) {
                            btnI1.setEnabled(false);
                            btnI1.setText(i1);
                            btnI1.setBackgroundColor(getResources().getColor(R.color.accent));
                            if(captura1)btnF1.setEnabled(true);
                        }
                        if (f1 != null) {
                            btnF1.setEnabled(false);
                            btnF1.setText(f1);
                            btnF1.setBackgroundColor(getResources().getColor(R.color.accent));
                            btnI2.setEnabled(true);
                        }
                        if (i2 != null) {
                            btnI2.setEnabled(false);
                            btnI2.setText(i2);
                            btnI2.setBackgroundColor(getResources().getColor(R.color.accent));
                            if(captura2)btnF2.setEnabled(true);
                        }
                        if (f2 != null) {
                            btnF2.setEnabled(false);
                            btnF2.setText(f2);
                            btnF2.setBackgroundColor(getResources().getColor(R.color.accent));
                            btnI3.setEnabled(true);
                        }
                        if (i3 != null) {
                            btnI3.setEnabled(false);
                            btnI3.setText(i3);
                            btnI3.setBackgroundColor(getResources().getColor(R.color.accent));
                            if(captura3)btnF3.setEnabled(true);
                        }
                        if (f3 != null) {
                            btnF3.setEnabled(false);
                            btnF3.setText(f3);
                            btnF3.setBackgroundColor(getResources().getColor(R.color.accent));
                        }



                        btnI1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                i1 = sql.vacaI1(hatoSeleccionado);
                                btnI1.setText(i1);
                                btnI1.setBackgroundColor(getResources().getColor(R.color.accent));
                            }
                        });
                        btnI2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                i2 = sql.vacaI2(hatoSeleccionado);
                                btnI2.setText(i2);
                                btnI2.setBackgroundColor(getResources().getColor(R.color.accent));
                            }
                        });
                        btnI3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                i3 = sql.vacaI3(hatoSeleccionado);
                                btnI3.setText(i3);
                                btnI3.setBackgroundColor(getResources().getColor(R.color.accent));
                            }
                        });
                        btnF1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                f1 = sql.vacaF1(hatoSeleccionado);
                                btnF1.setText(f1);
                                btnF1.setBackgroundColor(getResources().getColor(R.color.accent));
                                btnI2.setEnabled(true);
                            }
                        });
                        btnF2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                f2 = sql.vacaF2(hatoSeleccionado);
                                btnF2.setText(f2);
                                btnF2.setBackgroundColor(getResources().getColor(R.color.accent));
                                btnI3.setEnabled(true);
                            }
                        });
                        btnF3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                f3 = sql.vacaF3(hatoSeleccionado);
                                btnF3.setText(f3);
                                btnF3.setBackgroundColor(getResources().getColor(R.color.accent));
                            }
                        });
                        new AlertDialog.Builder(Hatos.this)
                                .setView(vistaDialog)
                                .setTitle("Horario de Ordeñas para Hato " + hatoSeleccionado)
                                .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        llenar();
                                        dialogInterface.cancel();
                                        dialogInterface.dismiss();
                                    }
                                }).create().show();
                    }
                }
            });

            btnParams.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    LayoutInflater inflater = Hatos.this.getLayoutInflater();
                    View vistaDialog = inflater.inflate(R.layout.ordenas_param_alert_view, null);
                    final Switch switchLab = (Switch) vistaDialog.findViewById(R.id.switchLab),
                            switch3Ord = (Switch) vistaDialog.findViewById(R.id.switch3Ord);
                    final Spinner spinOrdVial;
                    spinOrdVial = (Spinner) vistaDialog.findViewById(R.id.spinOrdVial);

                    if(!ord3)switch3Ord.setChecked(false);
                    if(!lab) {
                        switchLab.setChecked(false);
                        spinOrdVial.setVisibility(View.INVISIBLE);
                    }

                    String[] ords = new String[]{"EN QUÉ ORDEÑA DEBE CAPTURAR EL VIAL?","Vial en 1° ordeña","Vial en 2° ordeña","Vial en 3° Ordeña"};

                    if(!ord3){
                        ords = new String[]{"EN QUÉ ORDEÑA DEBE CAPTURAR EL VIAL?","Vial en 1° ordeña","Vial en 2° ordeña"};
                    }

                    adapterSpinner = new ArrayAdapter<String>(Hatos.this, R.layout.spinner_item, ords);
                    adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinOrdVial.setAdapter(adapterSpinner);
                    if (ordVialPos<3 || (ordVialPos==3 && ord3))
                        spinOrdVial.setSelection(ordVialPos);

                    switch3Ord.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            String[] ords;
                            if (isChecked || ordVialPos > 2)
                                ords = new String[]{"EN QUÉ ORDEÑA DEBE CAPTURAR EL VIAL?", "Vial en 1° ordeña", "Vial en 2° ordeña", "Vial en 3° Ordeña"};
                            else
                                ords = new String[]{"EN QUÉ ORDEÑA DEBE CAPTURAR EL VIAL?", "Vial en 1° ordeña", "Vial en 2° ordeña"};

                            adapterSpinner = new ArrayAdapter<String>(Hatos.this, R.layout.spinner_item, ords);
                            adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinOrdVial.setAdapter(adapterSpinner);
                            spinOrdVial.setSelection(ordVialPos);

                            if (isChecked)
                                ord3 = true;
                            else
                                ord3 = false;
                        }
                    });

                    switchLab.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                spinOrdVial.setVisibility(View.VISIBLE);
                                lab = true;
                            }
                            else {
                                spinOrdVial.setVisibility(View.INVISIBLE);
                                lab=false;
                            }
                        }
                    });

                    spinOrdVial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position > 0) {
                                ordVialPos = position;
                                if (position == 1) sql.ordVial(hatoSeleccionado, "1ra");
                                else if (position == 2)
                                    sql.ordVial(hatoSeleccionado, "2da");
                                else if (position == 3)
                                    sql.ordVial(hatoSeleccionado, "3ra");
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    new AlertDialog.Builder(Hatos.this)
                            .setView(vistaDialog)
                            .setTitle("Parámetros para Hato " + hatoSeleccionado)
                            .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                    dialogInterface.dismiss();
                                }
                            }).create().show();
                }
            });
        }
        else{
            new AlertDialog.Builder(Hatos.this)
                    .setTitle("No hay información")
                    .setMessage("No hay vacas descargadas para calificar.")
                    .setCancelable(true)
                    .setPositiveButton("Regresar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Hatos.this.finish();
                        }
                    })
                    .create()
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_hatos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_reporte_vacas) {
            if (vacas!=null && vacas.size()>0) {
                new AlertDialog.Builder(Hatos.this)
                        .setTitle("Crear Reporte Vacas PDF para hato " + hatoSeleccionado)
                        .setMessage("El proceso de creación de reportes de vacas calificadas en PDF " +
                                "podría tardar unos minutos, dependiendo de la cantidad de vacas " +
                                "calificadas.\n\nPresione \"Iniciar\" para comenzar el proceso.")
                        .setCancelable(true)
                        .setPositiveButton("Iniciar / Start", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new PDFMaker().execute();
                            }
                        })
                        .setNegativeButton("Cancelar / Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .create()
                        .show();
            }
            else Toast.makeText(Hatos.this, "No hay vacas", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (hatoSeleccionado!=null) {
            txtBuscarVaca.setText("");
            llenar();
        }
    }

    private void llenar(){
        listVacas.setAdapter(null);
        btnNuevaVaca.setEnabled(true);
        String hato = hatoSeleccionado.split(" - ")[0];
        String fecha = hatoSeleccionado.split(" - ")[1];
        i1=null;i2=null;i3=null;f1=null;f2=null;f3=null;
        ordVialPos=0;
        try {
            vacas = sql.obtenerVacas(hato,fecha);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (vacas != null) {
            for(Vaca v : vacas){
                if (v.getPeso1()>0)captura1=true;
                if (v.getPeso2()>0)captura2=true;
                if (v.getPeso3()>0)captura3=true;
                if(captura1&&captura2&&captura3)break;
            }
            btnArriba.setEnabled(true);
            btnAbajo.setEnabled(true);
            Vaca v = vacas.get(0);
            if (v.getHora1Ini()!=null && !v.getHora1Ini().isEmpty())i1=v.getHora1Ini();
            if (v.getHora2Ini()!=null && !v.getHora2Ini().isEmpty())i2=v.getHora2Ini();
            if (v.getHora3Ini()!=null && !v.getHora3Ini().isEmpty())i3=v.getHora2Ini();

            if (v.getHora1Fin()!=null && !v.getHora1Fin().isEmpty())f1=v.getHora1Fin();
            if (v.getHora2Fin()!=null && !v.getHora2Fin().isEmpty())f2=v.getHora2Fin();
            if (v.getHora3Fin()!=null && !v.getHora3Fin().isEmpty())f3=v.getHora3Fin();

            if(i3==null){
                if(i2==null){
                    if(i1!=null){
                        Collections.sort(vacas, new Comparator<Vaca>() {
                            @Override
                            public int compare(Vaca v1, Vaca v2) {
                                int v1p = 0,v2p=0;
                                if(v1.getPeso1()>0)v1p=1;
                                if(v2.getPeso1()>0)v2p=1;
                                return v1p-v2p;
                            }
                        });
                    }
                }
                else{
                    Collections.sort(vacas, new Comparator<Vaca>() {
                        @Override
                        public int compare(Vaca v1, Vaca v2) {
                            int v1p = 0,v2p=0;
                            if(v1.getPeso2()>0)v1p=1;
                            if(v2.getPeso2()>0)v2p=1;
                            return v1p-v2p;
                        }
                    });
                }
            }
            else{
                Collections.sort(vacas, new Comparator<Vaca>() {
                    @Override
                    public int compare(Vaca v1, Vaca v2) {
                        int v1p = 0,v2p=0;
                        if(v1.getPeso3()>0)v1p=1;
                        if(v2.getPeso3()>0)v2p=1;
                        return v1p-v2p;
                    }
                });
            }


            adapterVacas = new VacasAdapter(this, 0, vacas);
            adapterVacas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            listVacas.setAdapter(adapterVacas);
            txtBuscarVaca.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    Hatos.this.adapterVacas.getFilter().filter(charSequence);
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });

            /** Inicia Dialog **/
            String ordvial = vacas.get(0).getOrdVial();
            if (ordvial!=null){
                if (ordvial.equals("1ra"))
                    ordVialPos=1;
                else if (ordvial.equals("2da"))
                    ordVialPos=2;
                else if (ordvial.equals("3ra"))
                    ordVialPos=3;
            }
            i1 = vacas.get(0).getHora1Ini();
            i2 = vacas.get(0).getHora2Ini();
            i3 = vacas.get(0).getHora3Ini();

            f1 = vacas.get(0).getHora1Fin();
            f2 = vacas.get(0).getHora2Fin();
            f3 = vacas.get(0).getHora3Fin();
            /** Finaliza Dialog **/

        }

    }

    private class VacasAdapter extends ArrayAdapter<Vaca> implements Filterable {

        ArrayList<Vaca> objects = null;

        public VacasAdapter(Context context, int resource, ArrayList<Vaca> objects) {
            super(context, resource, objects);
            this.objects=objects;
        }

        @Override
        public int getCount() {
            return objects.size();
        }

        @Override
        public Vaca getItem(int position) {
            return objects.get(position);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public Filter getFilter() {
            return myFilter;
        }

        /********* Create a holder Class to contain inflated xml file elements *********/
        public class ViewHolder{
            public RelativeLayout layout;
            public TextView text;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View vi = convertView;
            final ViewHolder holder;

            if(convertView==null){
                LayoutInflater inflater = getLayoutInflater();
                /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
                vi = inflater.inflate(R.layout.hatos_row, null);

                /****** View Holder Object to contain file elements ******/

                holder = new ViewHolder();
                holder.layout = (RelativeLayout)vi.findViewById(R.id.layoutHatosRow);
                holder.text = (TextView) vi.findViewById(R.id.btnHatosRow);

                /************  Set holder with LayoutInflater ************/
                vi.setTag( holder );
            }
            else
                holder=(ViewHolder)vi.getTag();
            String texto = "Medalla:" + objects.get(position).getNoVaca().trim();
            if (objects.get(position).getNoVial()!=null)
                texto+=" | Vial: "+objects.get(position).getNoVial();
            if (objects.get(position).getPeso1()>0) {
                texto += " | " + (double) objects.get(position).getPeso1() / 10;
            }
            if (objects.get(position).getPeso2()>0) {
                texto += " | " + (double) objects.get(position).getPeso2() / 10;
            }
            if (objects.get(position).getPeso3()>0) {
                texto += " | " + (double) objects.get(position).getPeso3() / 10;
            }
            holder.text.setText(texto);

            if (objects.get(position).getPeso1()>0){
                holder.layout.setBackgroundColor(getResources().getColor(R.color.ok1));
                if (objects.get(position).getPeso2()>0){
                    holder.layout.setBackgroundColor(getResources().getColor(R.color.ok2));
                    if (objects.get(position).getPeso3()>0){
                        holder.layout.setBackgroundColor(getResources().getColor(R.color.ok3));
                    }
                }
            }
            else if(objects.get(position).getPeso1()<=0 && (objects.get(position).getPeso2()>0 || objects.get(position).getPeso3()>0)){
                holder.layout.setBackgroundColor(getResources().getColor(R.color.mal));
            }
            else
                holder.layout.setBackgroundColor(Color.TRANSPARENT);

            if (objects.get(position).getCodEst().equals("3") ||
                    objects.get(position).getCodEst().equals("6"))
                 holder.text.setTextColor(Color.GRAY);
            else if (objects.get(position).getCodEst().equals("7") ||
                    objects.get(position).getCodEst().equals("8") ||
                    objects.get(position).getCodEst().equals("9"))
                holder.text.setTextColor(Color.RED);
            else holder.text.setTextColor(Color.BLACK);

            final int pos = position;
            holder.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Vaca sel = objects.get(pos);
                    Intent i = new Intent(Hatos.this, CapturaProduccion.class);
                    i.putExtra("ID", sel.getId());
                    if(lab)i.putExtra("LAB",true);
                    else i.putExtra("LAB", false);
                    if(ord3)i.putExtra("ORD",true);
                    else i.putExtra("ORD", false);
                    i.putExtra("I1",i1);
                    i.putExtra("I2",i2);
                    i.putExtra("I3",i3);
                    i.putExtra("F1",f1);
                    i.putExtra("F2",f2);
                    i.putExtra("F3",f3);
                    i.putExtra("VIAL",ordVialPos);
                    startActivity(i);

                }
            });

            holder.text.setClickable(true);
            return vi;
        }

        Filter myFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                ArrayList<Vaca> tempList=new ArrayList<Vaca>();
                //constraint is the result from text you want to filter against.
                //objects is your data set you will filter from
                if(constraint != null && objects!=null) {
                    int length=vacas.size();
                    int i=0;
                    while(i<length){
                        Vaca item=vacas.get(i);
                        //do whatever you wanna do here
                        //adding result set output array
                        if (item.getNoVaca().contains(constraint) || (item.getCurv()+"").contains(constraint))
                            tempList.add(item);
                        i++;
                    }
                    //following two lines is very important
                    //as publish result can only take FilterResults objects
                    filterResults.values = tempList;
                    filterResults.count = tempList.size();
                }
                return filterResults;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence contraint, FilterResults results) {
                objects = (ArrayList<Vaca>) results.values;
                if (results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }

    private class PDFMaker extends AsyncTask<Void,String,Object> {

        android.support.v7.app.AlertDialog.Builder diag=null;
        Chronometer cron = new Chronometer(Hatos.this);

        private void abrirArchivo(File file){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

            diag = new android.support.v7.app.AlertDialog.Builder(Hatos.this);
            diag.setTitle("Creando PDF...");
            diag.setView(cron);
            diag.setCancelable(false);
            diag.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                }
            });
            diag.create().show();
            cron.setTextSize(40);
            cron.start();
        }

        @Override
        protected void onPostExecute(Object s) {
            super.onPostExecute(s);
            cron.stop();
            diag.setTitle("PDF Creado!");
            if (s != null && s instanceof String){
                Toast.makeText(Hatos.this, (String) s, Toast.LENGTH_LONG).show();
            }
            else if (s instanceof File){
                cron.setText(cron.getText()+" guardado en: "+((File) s).getAbsolutePath());
                abrirArchivo((File)s);
            }
            else if (s instanceof Exception){
                Toast.makeText(Hatos.this, "ERROR: "+((Exception) s).getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected Object doInBackground(Void... voids) {
            try{
                PDF pdf = new PDF(Hatos.this);
                ArrayList<Vaca> calificadas = new ArrayList<Vaca>();
                for (Vaca v : vacas)
                    if (v.getFechaPrueba()!=null) calificadas.add(v);
                if (calificadas.size()>0)
                    return pdf.createPDF("VACAS_"+sql.fechaActual()+".pdf",Hatos.this,calificadas);
                else
                    return "No hay vacas calificadas";
            }catch(Exception e){
                return e.getMessage();
            }

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
