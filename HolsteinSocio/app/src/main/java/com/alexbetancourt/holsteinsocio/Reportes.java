package com.alexbetancourt.holsteinsocio;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Reportes extends AppCompatActivity {

    ListView listReportes;
    ArrayList<String[]> reportes;
    SQLite sql;
    Adapter adapter;
    Button btnUltimo, btnHistorial;
    TextView titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);
        sql = new SQLite(this);
        listReportes = (ListView)findViewById(R.id.lvReportes);
        btnUltimo = (Button)findViewById(R.id.btnRepUltimo);
        titulo = (TextView)findViewById(R.id.tvReportes);
        btnHistorial = (Button)findViewById(R.id.btnRepHistorial);
        try {
            reportes = sql.obtenerListaReportes();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
        if (reportes != null && reportes.size()>0){
            adapter = new Adapter(this);
            listReportes.setAdapter(adapter);
            listReportes.setVisibility(View.INVISIBLE);
            btnHistorial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (btnHistorial.getText().equals("Ocultar Historial")){
                        btnHistorial.setText("Mostrar Historial");
                        listReportes.setVisibility(View.INVISIBLE);
                    }else{
                        btnHistorial.setText("Ocultar Historial");
                        listReportes.setVisibility(View.VISIBLE);
                    }
                }
            });
            try {
                final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                final Date fechaProceso = format.parse(sql.ultimoProceso());
                final SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy' a las 'HH:mm:ss");
                final SimpleDateFormat format_sh = new SimpleDateFormat("dd/MM/yyyy");
                titulo.setText("Último proceso de Holstein: "+format2.format(fechaProceso));
                btnUltimo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Intent i = new Intent(Reportes.this, ReporteDetalles.class);
                            i.putExtra("ID", reportes.get(0)[2]);
                            Date fecha = format.parse(reportes.get(0)[1]);
                            i.putExtra("FECHA", "Visita: " + format_sh.format(fecha));
                            i.putExtra("FECHAP", "Visita:" + format_sh.format(fecha)+" | Último Proceso:" + format2.format(fechaProceso));
                            startActivity(i);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }catch(Exception e){e.printStackTrace();}
        }else{
            btnHistorial.setEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reportes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    class Adapter extends ArrayAdapter<String[]>{
        Context context;

        public Adapter(Context context) {
            super(context,0);
            this.context = context;
        }

        @Override
        public int getCount() {
            return reportes.size();
        }

        @Override
        public String[] getItem(int position) {
            return reportes.get(position);
        }

        @Override
        public int getPosition(String[] item) {
            return super.getPosition(item);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            try {
                LinearLayout.LayoutParams txtParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                txtParams1.setMargins(0, 10, 0, 10);
                LinearLayout.LayoutParams txtParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                txtParams2.setMargins(10, 0, 0, 0);
                LinearLayout Layout = new LinearLayout(context);
                // specifying vertical orientation
                Layout.setOrientation(LinearLayout.VERTICAL);
                AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT,AbsListView.LayoutParams.WRAP_CONTENT,1);
                Layout.setLayoutParams(params);
                TextView tv = new TextView(context);
                tv.setTextSize(18);
                tv.setTextColor(Color.BLACK);
                tv.setLayoutParams(txtParams1);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                final Date fecha = format.parse(reportes.get(position)[1]);
                final SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy' a las 'HH:mm:ss");
                final SimpleDateFormat format_sh = new SimpleDateFormat("dd/MM/yyyy");
                tv.setText("Reporte del " +format_sh.format(fecha));
                Layout.addView(tv);
                TextView tv2 = new TextView(context);
                tv2.setTextSize(15);
                tv2.setTextColor(Color.GRAY);
                tv2.setLayoutParams(txtParams2);
                tv2.setText("Hato: " + reportes.get(position)[0]);
                Layout.addView(tv2);
                final int pos = position;
                Layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                            Date fechaProceso = format.parse(sql.ultimoProceso());
                            SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy' a las 'HH:mm:ss");
                            SimpleDateFormat format_sh = new SimpleDateFormat("dd/MM/yyyy");
                            Intent i = new Intent(Reportes.this, ReporteDetalles.class);
                            i.putExtra("ID", reportes.get(pos)[2]);
                            Date fecha = format.parse(reportes.get(pos)[1]);
                            i.putExtra("FECHA", "Reporte del " + format_sh.format(fecha));
                            i.putExtra("FECHAP", "Visita:" + format_sh.format(fecha)+" | Último Proceso:" + format2.format(fechaProceso));
                            startActivity(i);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                return Layout;
            }catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }

    }

}
