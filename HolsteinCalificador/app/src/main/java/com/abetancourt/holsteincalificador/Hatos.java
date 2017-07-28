package com.abetancourt.holsteincalificador;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class Hatos extends AppCompatActivity {

    SQLite sql;

    Spinner spinhatos;
    ListView listVacas, listToros;
    Button btnNuevaVaca, btnNuevoToro;
    static ArrayList<String> hatos;
    private ArrayList<Vaca> vacas;
    private ArrayList<Toro> toros;
    private ArrayAdapter<String> adapterSpinner;
    private TorosAdapter adapterToros;
    private VacasAdapter adapterVacas;
    private EditText txtBuscarVaca, txtBuscarToro;
    private Switch hatosSwitch;
    String hatoSeleccionado = null;
    TextView lblVacas, lblToros, lblTorosCalif, lblVacasCalif;

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
            listToros = (ListView) findViewById(R.id.listToros);
            btnNuevaVaca = (Button) findViewById(R.id.btnAgregarVaca);
            btnNuevaVaca.setEnabled(false);
            btnNuevoToro = (Button) findViewById(R.id.btnAgregarToro);
            btnNuevoToro.setEnabled(false);
            txtBuscarVaca = (EditText) findViewById(R.id.txtBuscarVaca);
            txtBuscarToro = (EditText) findViewById(R.id.txtBuscarToro);
            hatosSwitch = (Switch) findViewById(R.id.switchHatos);
            lblVacas = (TextView) findViewById(R.id.lblHatosVaca);
            lblToros = (TextView) findViewById(R.id.lblHatosToro);
            lblTorosCalif = (TextView) findViewById(R.id.lblTorosCalificados);
            lblVacasCalif = (TextView) findViewById(R.id.lblVacasCalificadas);
            lblVacas.setTextColor(getResources().getColor(R.color.accent));
            adapterSpinner = new ArrayAdapter<String>(this, R.layout.spinner_item, hatos);
            adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinhatos.setAdapter(adapterSpinner);

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

            btnNuevaVaca.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Hatos.this, AgregarVaca.class);
                    i.putExtra("HATO", hatoSeleccionado);
                    startActivity(i);
                }
            });

            btnNuevoToro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Hatos.this, AgregarToro.class);
                    i.putExtra("HATO", hatoSeleccionado);
                    startActivity(i);
                }
            });
            hatosSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        listVacas.setVisibility(View.GONE);
                        listToros.setVisibility(View.VISIBLE);
                        lblToros.setTextColor(getResources().getColor(R.color.accent));
                        lblVacas.setTextColor(getResources().getColor(R.color.primary));
                    } else {
                        lblVacas.setTextColor(getResources().getColor(R.color.accent));
                        lblToros.setTextColor(getResources().getColor(R.color.primary));
                        listVacas.setVisibility(View.VISIBLE);
                        listToros.setVisibility(View.GONE);
                    }
                }
            });
            listToros.setVisibility(View.GONE);
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
        getMenuInflater().inflate(R.menu.menu_hatos, menu);
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
                                new PDFVacas().execute();
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
            else Toast.makeText(Hatos.this, "No hay vacas calificadas", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.action_reporte_toros) {
            if (vacas!=null && vacas.size()>0) {
                new AlertDialog.Builder(Hatos.this)
                        .setTitle("Crear Reporte Toros PDF para hato " + hatoSeleccionado)
                        .setMessage("El proceso de creación de reportes de toros en PDF " +
                                "podría tardar unos minutos, dependiendo de la cantidad de toros " +
                                "calificados.\n\nPresione \"Iniciar\" para comenzar el proceso.")
                        .setCancelable(true)
                        .setPositiveButton("Iniciar / Start", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new PDFToros().execute();
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
            else Toast.makeText(Hatos.this, "No hay toros calificados", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (hatoSeleccionado!=null) {
            txtBuscarVaca.setText("");
            txtBuscarToro.setText("");
            llenar();
        }
    }

    private void llenar(){
        listVacas.setAdapter(null);
        listToros.setAdapter(null);
        btnNuevaVaca.setEnabled(true);
        btnNuevoToro.setEnabled(true);

        String hato = hatoSeleccionado.split(" - ")[0];
        String fecha = hatoSeleccionado.split(" - ")[1];
        try {
            vacas = sql.obtenerVacas(hato,fecha);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            toros = sql.obtenerToros(hato, fecha);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (vacas != null) {

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
            int vacascalificadas = 0;
            for (Vaca va : vacas)
                if (va.getPuntosCalif()>0)
                    vacascalificadas+=1;
            lblVacasCalif.setText(vacascalificadas+" vacas calificadas");

        }
        if (toros != null) {

            adapterToros = new TorosAdapter(this, 0, toros);
            adapterToros.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            listToros.setAdapter(adapterToros);
            txtBuscarToro.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    Hatos.this.adapterToros.getFilter().filter(charSequence);
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });

            int toroscalificados = 0;
            for (Toro to : toros)
                if (to.getPuntosCalif()>0)
                    toroscalificados+=1;
            lblTorosCalif.setText(toroscalificados + " toros calificados");

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

            if (objects.get(position).getNoCP()!=null)
                holder.text.setText("Reg: " + objects.get(position).getNoRegistro() + " | Medalla: " + objects.get(position).getNoCP());
            else
                holder.text.setText("Medalla: " + objects.get(position).getNoCP());

            if (objects.get(position).getPuntosCalif()>0){
                if (objects.get(position).getNoCP()!=null)
                    holder.text.setText("✓ Reg: " + objects.get(position).getNoRegistro() + " | Medalla: " + objects.get(position).getNoCP());
                else
                    holder.text.setText("✓ Medalla: " + objects.get(position).getNoCP());
                if (objects.get(position).isSync())holder.text.setTextColor(getResources().getColor(R.color.accent));
                else holder.text.setTextColor(Color.RED);
            }
            else holder.text.setTextColor(Color.BLACK);


            final int pos = position;
            holder.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Vaca sel = objects.get(pos);
                    Intent i = new Intent(Hatos.this, CalificarVaca.class);
                    i.putExtra("ID",sel.getId());
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
                        if (item.getNoCP().contains(constraint) || item.getNoRegistro().contains(constraint) || (item.getCurv()+"").contains(constraint))
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

    private class TorosAdapter extends ArrayAdapter<Toro> implements Filterable {

        ArrayList<Toro> objects = null;

        public TorosAdapter(Context context, int resource, ArrayList<Toro> objects) {
            super(context, resource, objects);
            this.objects=objects;
        }

        @Override
        public int getCount() {
            return objects.size();
        }

        @Override
        public Toro getItem(int position) {
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
            if (objects.get(position).getNombre()!=null && !(objects.get(position).getNombre().equals("null")))
                holder.text.setText("Reg: " + objects.get(position).getNoRegistro()+" | Nom: "+objects.get(position).getNombre());
            else
                holder.text.setText("Reg: " + objects.get(position).getNoRegistro());

            if (objects.get(position).getPuntosCalif()>0){
                if (objects.get(position).getNombre()!=null && !(objects.get(position).getNombre().equals("null")))
                    holder.text.setText("✓ Reg: " + objects.get(position).getNoRegistro() + " | Nom: " + objects.get(position).getNombre());
                else
                    holder.text.setText("✓ Reg: " + objects.get(position).getNoRegistro());
                if (objects.get(position).isSync())holder.text.setTextColor(getResources().getColor(R.color.accent));
                else holder.text.setTextColor(Color.RED);
            }
            else holder.text.setTextColor(Color.BLACK);


            final int pos = position;
            holder.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toro sel = objects.get(pos);
                    Intent i = new Intent(Hatos.this, CalificarToro.class);
                    i.putExtra("ID",sel.getId());
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
                ArrayList<Toro> tempList=new ArrayList<Toro>();
                //constraint is the result from text you want to filter against.
                //objects is your data set you will filter from
                if(constraint != null && objects!=null) {
                    int length=toros.size();
                    int i=0;
                    while(i<length){
                        Toro item=toros.get(i);
                        //do whatever you wanna do here
                        //adding result set output array
                        if (item.getNoRegistro().contains(constraint) || (item.getNombre()).contains(constraint))
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
                objects = (ArrayList<Toro>) results.values;
                if (results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }

    private class PDFVacas extends AsyncTask<Void,String,Object> {

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
                PDF pdf = new PDF();
                ArrayList<Vaca> calificadas = new ArrayList<Vaca>();
                for (Vaca v : vacas)
                    if (v.getPuntosCalif()>0) calificadas.add(v);
                if (calificadas.size()>0)
                    return pdf.createPDFVaca(calificadas.get(0).getNoHato()+"_VACAS_" + sql.fechaActual() + ".pdf", Hatos.this, calificadas);
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

    private class PDFToros extends AsyncTask<Void,String,Object> {

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
                PDF pdf = new PDF();
                ArrayList<Toro> calificadas = new ArrayList<Toro>();
                for (Toro toro : toros)
                    if (toro.getPuntosCalif()>0) calificadas.add(toro);
                if (calificadas.size()>0)
                    return pdf.createPDFToro(calificadas.get(0).getNoHato()+"_TOROS_" + sql.fechaActual() + ".pdf", Hatos.this, calificadas);
                else
                    return "No hay toros calificadas";
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
