package com.alexbetancourt.holsteinsocio;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;


public class ReporteDetalles extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    static SQLite sql;
    static String id;
    static ArrayList<EstGeneral> estgenerales;
    static Estadistico hato;
    static String fechap,fechav;
    private static SharedPreferences sharedPreferences;
    private static int tabla_texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_detalles);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        tabla_texto = Integer.parseInt(sharedPreferences.getString("tabla_texto","16"));
        Bundle reicieveParams = getIntent().getExtras();
        id=reicieveParams.getString("ID");
        fechap = reicieveParams.getString("FECHAP");
        fechav = reicieveParams.getString("FECHA");
        this.setTitle("Detalles del "+fechav);
        sql = new SQLite(this);
        try {
            hato = sql.obtenerEstadistico(id);
            estgenerales = sql.obtenerEstGeneral(hato.getEstado());

        }catch(Exception e){e.printStackTrace();}


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        init();
    }

    private void init(){

    }

    private final void salir(){
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reporte_detalles, menu);
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


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return FragmentProductivos.newInstance();
                case 1:
                    return FragmentReproductivos.newInstance();
                case 2:
                    return FragmentLaboratorio.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    static void zoom(int valor, TableLayout tableLayout){
        if (valor > 0) {
            for (int i = 0; i < tableLayout.getChildCount(); i++) {
                View row = tableLayout.getChildAt(i);
                if (row instanceof TableRow) {
                    for (int j = 0; j < ((TableRow) row).getChildCount(); j++) {
                        View text = ((TableRow) row).getChildAt(j);
                        if (text instanceof TextView) {
                            ((TextView) text).setTextSize(16 + (2 * valor));
                        }
                    }
                }
            }
        }
    }

    static void zoom2(int valor, TableFixHeaders tableLayout){
        if (valor > 0) {
            for (int i = 0; i < tableLayout.getChildCount(); i++) {
                View row = tableLayout.getChildAt(i);
                if (row instanceof TableRow) {
                    for (int j = 0; j < ((TableRow) row).getChildCount(); j++) {
                        View text = ((TableRow) row).getChildAt(j);
                        if (text instanceof TextView) {
                            ((TextView) text).setTextSize(tabla_texto + (2 * valor));
                        }
                    }
                }
                else if (row instanceof TextView) {
                    ((TextView) row).setTextSize(tabla_texto+(2 * valor));
                }
            }
        }
    }

    /**
     * A placeholder fragment containing a simple view
     */
    public static class FragmentProductivos extends Fragment{
        SeekBar seekBar;
        TableLayout tableLayout;
        public FragmentProductivos(){}
        public static FragmentProductivos newInstance() {
            FragmentProductivos fragment = new FragmentProductivos();
            return fragment;
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_reporte_productivos, container, false);
            seekBar = (SeekBar)rootView.findViewById(R.id.prodZoomBar);
            TextView proceso = (TextView)rootView.findViewById(R.id.txtProdProc);
            proceso.setText(fechap);
            LinearLayout linearLayout = (LinearLayout)rootView.findViewById(R.id.llProd);
            final TableFixHeaders tableFixHeaders = (TableFixHeaders) rootView.findViewById(R.id.prodTable);
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    zoom2(i, tableFixHeaders);
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });
            String[][] data = new String[estgenerales.size()+2][9];
            for (int i=0;i<9;i++){ //HEADER
                switch(i){
                    case 0:
                        data[0][i] ="Lugar";
                        break;
                    case 1:
                        data[0][i] ="# Vacas\nanual";
                        break;
                    case 2:
                        data[0][i] ="Leche KG\nanual";
                        break;
                    case 3:
                        data[0][i] ="Leche EM";
                        break;
                    case 4:
                        data[0][i] ="Días Leche";
                        break;
                    case 5:
                        data[0][i] ="% Vacas\nCargadas";
                        break;
                    case 6:
                        data[0][i] ="Días Pico\nanual";
                        break;
                    case 7:
                        data[0][i] ="Leche Pico\nanual";
                        break;
                    case 8:
                        data[0][i] ="% Deshecho\nanual";
                        break;
                }
            }
            for (int i=0;i<9;i++){ //HATO
                switch(i){
                    case 0:
                        data[1][i] =hato.getNoHato();
                        break;
                    case 1:
                        data[1][i] = hato.getVacasHato()+"";
                        break;
                    case 2:
                        data[1][i] = hato.getLechekgH()+"";
                        break;
                    case 3:
                        data[1][i] = hato.getLecheEM()+"";
                        break;
                    case 4:
                        data[1][i] = hato.getDiasLeche()+"";
                        break;
                    case 5:
                        data[1][i] = hato.getpVacasCarga()+"%";
                        break;
                    case 6:
                        data[1][i] = hato.getDiasPico()+"";
                        break;
                    case 7:
                        data[1][i] = hato.getLechePico()+"";
                        break;
                    case 8:
                        data[1][i] = hato.getpDesecho()+"%";
                        break;
                }
            }
            int c = 2;
            for (EstGeneral est:estgenerales){
                for (int i=0;i<9;i++){ //HATO
                    switch(i){
                        case 0:
                            data[c][i] = est.getTipo();
                            break;
                        case 1:
                            data[c][i] = est.getVacasHato()+"";
                            break;
                        case 2:
                            data[c][i] = est.getLechekgH()+"";
                            break;
                        case 3:
                            data[c][i] = est.getLecheEM()+"";
                            break;
                        case 4:
                            data[c][i] = est.getDiasLeche()+"";
                            break;
                        case 5:
                            data[c][i] = est.getpVacasCarga()+"%";
                            break;
                        case 6:
                            data[c][i] = est.getDiasPico()+"";
                            break;
                        case 7:
                            data[c][i] = est.getLechePico()+"";
                            break;
                        case 8:
                            data[c][i] = est.getpDesecho()+"%";
                            break;
                    }
                }
                c++;
            }
            MatrixTableAdapter<String> matrixTableAdapter = new MatrixTableAdapter<String>(rootView.getContext(),data, Color.rgb(255, 178, 102),hato.getRegion());
            tableFixHeaders.setAdapter(matrixTableAdapter);
            /*
                TableRow trHato = (TableRow) inflater.inflate(R.layout.productivos_row, tableLayout, false);
                TextView reg, reg2, novacas, lechekg, lecheem, diasleche, pvacascar, diasp, lechep, pdesh;
                reg = (TextView) trHato.findViewById(R.id.prod_tv_region);
                reg2 = (TextView) trHato.findViewById(R.id.prod_tv_region2);
                novacas = (TextView) trHato.findViewById(R.id.prod_tv_novacas);
                lechekg = (TextView) trHato.findViewById(R.id.prod_tv_lechekg);
                lecheem = (TextView) trHato.findViewById(R.id.prod_tv_lecheem);
                diasleche = (TextView) trHato.findViewById(R.id.prod_tv_diasleche);
                pvacascar = (TextView) trHato.findViewById(R.id.prod_tv_pvacascarg);
                diasp = (TextView) trHato.findViewById(R.id.prod_tv_diaspico);
                lechep = (TextView) trHato.findViewById(R.id.prod_tv_lechepico);
                pdesh = (TextView) trHato.findViewById(R.id.prod_tv_pdeshecho);

                reg.setText("HATO");
                reg2.setText("HATO");
                novacas.setText(hato.getVacasHato()+"");
                lechekg.setText(hato.getLechekgH()+"");
                lecheem.setText(hato.getLecheEM()+"");
                diasleche.setText(hato.getDiasLeche()+"");
                pvacascar.setText("%"+hato.getpVacasCarga());
                diasp.setText(hato.getDiasPico()+"");
                lechep.setText(hato.getLechePico()+"");
                pdesh.setText("%"+hato.getpDesecho());

                tableLayout.addView(trHato);

            for (EstGeneral est : estgenerales){
                TableRow tr = (TableRow) inflater.inflate(R.layout.productivos_row, tableLayout, false);
                TextView greg, greg2, gnovacas, glechekg, glecheem, gdiasleche, gpvacascar, gdiasp, glechep, gpdesh;
                greg = (TextView) tr.findViewById(R.id.prod_tv_region);
                greg2 = (TextView) tr.findViewById(R.id.prod_tv_region2);
                gnovacas = (TextView) tr.findViewById(R.id.prod_tv_novacas);
                glechekg = (TextView) tr.findViewById(R.id.prod_tv_lechekg);
                glecheem = (TextView) tr.findViewById(R.id.prod_tv_lecheem);
                gdiasleche = (TextView) tr.findViewById(R.id.prod_tv_diasleche);
                gpvacascar = (TextView) tr.findViewById(R.id.prod_tv_pvacascarg);
                gdiasp = (TextView) tr.findViewById(R.id.prod_tv_diaspico);
                glechep = (TextView) tr.findViewById(R.id.prod_tv_lechepico);
                gpdesh = (TextView) tr.findViewById(R.id.prod_tv_pdeshecho);

                greg.setText(est.getTipo());
                greg2.setText(est.getTipo());
                gnovacas.setText(est.getVacasHato() + "");
                glechekg.setText(est.getLechekgH() + "");
                glecheem.setText(est.getLecheEM() + "");
                gdiasleche.setText(est.getDiasLeche() + "");
                gpvacascar.setText("%" + est.getpVacasCarga());
                gdiasp.setText(est.getDiasPico() + "");
                glechep.setText(est.getLechePico() + "");
                gpdesh.setText("%" + est.getpDesecho());

                tableLayout.addView(tr);
            }*/
            return rootView;
        }
    }

    public static class FragmentReproductivos extends Fragment{
        SeekBar seekBar;
        LinearLayout linearLayout;
        public FragmentReproductivos(){}
        public static FragmentReproductivos newInstance() {
            FragmentReproductivos fragment = new FragmentReproductivos();
            return fragment;
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_reporte_reproductivos, container, false);
            TextView proceso = (TextView)rootView.findViewById(R.id.txtReprProc);
            proceso.setText(fechap);
            seekBar = (SeekBar)rootView.findViewById(R.id.reprodZoomBar);
            linearLayout = (LinearLayout)rootView.findViewById(R.id.llRepr);
            final TableFixHeaders tableFixHeaders = (TableFixHeaders) rootView.findViewById(R.id.repTable);
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    zoom2(i, tableFixHeaders);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            String[][] data = new String[estgenerales.size()+2][9];
            for (int i=0;i<9;i++){ //HEADER
                switch(i){
                    case 0:
                        data[0][i] ="Lugar";
                        break;
                    case 1:
                        data[0][i] ="Edad 1er.\nParto";
                        break;
                    case 2:
                        data[0][i] ="Dias Primer\nServicio anual";
                        break;
                    case 3:
                        data[0][i] ="%Fertilidad \nal 1er Servicio anual";
                        break;
                    case 4:
                        data[0][i] ="Intervalo\nentre servicios";
                        break;
                    case 5:
                        data[0][i] ="Servicios por\nconcepción anual";
                        break;
                    case 6:
                        data[0][i] ="Días Abiertos\nanual";
                        break;
                    case 7:
                        data[0][i] ="Intervalo\nentre Partos";
                        break;
                    case 8:
                        data[0][i] ="% Abortos\nanual";
                        break;
                }
            }
            for (int i=0;i<9;i++){ //HATO
                switch(i){
                    case 0:
                        data[1][i] =hato.getNoHato();
                        break;
                    case 1:
                        data[1][i] =hato.getEdadMM()+"";
                        break;
                    case 2:
                        data[1][i] = hato.getDias1Serv()+"";
                        break;
                    case 3:
                        data[1][i] = hato.getpFertil1Serv()+"%";
                        break;
                    case 4:
                        data[1][i] = hato.getInterServ()+"";
                        break;
                    case 5:
                        data[1][i] = hato.getServxConc()+"";
                        break;
                    case 6:
                        data[1][i] = hato.getDiasAb()+"";
                        break;
                    case 7:
                        data[1][i] = hato.getInterPartos()+"";
                        break;
                    case 8:
                        data[1][i] = hato.getpAbortos()+"%";
                        break;
                }
            }
            int c = 2;
            for (EstGeneral est:estgenerales){
                for (int i=0;i<9;i++){ //HATO
                    switch(i){
                        case 0:
                            data[c][i] = est.getTipo();
                            break;
                        case 1:
                            data[c][i] = est.getEdadMM()+"";
                            break;
                        case 2:
                            data[c][i] = est.getDias1Serv()+"";
                            break;
                        case 3:
                            data[c][i] = est.getpFertil1Serv()+"%";
                            break;
                        case 4:
                            data[c][i] = est.getInterServ()+"";
                            break;
                        case 5:
                            data[c][i] = est.getServxConc()+"";
                            break;
                        case 6:
                            data[c][i] = est.getDiasAb()+"";
                            break;
                        case 7:
                            data[c][i] = est.getInterPartos()+"";
                            break;
                        case 8:
                            data[c][i] = est.getpAbortos()+"%";
                            break;
                    }
                }
                c++;
            }
            MatrixTableAdapter<String> matrixTableAdapter = new MatrixTableAdapter<String>(rootView.getContext(),data, Color.rgb(102, 178, 255),hato.getRegion());
            tableFixHeaders.setAdapter(matrixTableAdapter);
            /*TableRow trHato = (TableRow) inflater.inflate(R.layout.reproductivos_row, tableLayout, false);
            TextView reg,reg2,edadmm,dias1s,pfer1,interserv,servx,diasab,interpar,pabor;
            reg = (TextView) trHato.findViewById(R.id.rep_tv_region);
            reg2 = (TextView) trHato.findViewById(R.id.rep_tv_region2);
            edadmm = (TextView) trHato.findViewById(R.id.rep_tv_edadmm);
            dias1s = (TextView) trHato.findViewById(R.id.rep_tv_dias1serv);
            pfer1 = (TextView) trHato.findViewById(R.id.rep_tv_pfertil1serv);
            interserv = (TextView) trHato.findViewById(R.id.rep_tv_interserv);
            servx = (TextView) trHato.findViewById(R.id.rep_tv_servxconc);
            diasab = (TextView) trHato.findViewById(R.id.rep_tv_diasab);
            interpar = (TextView) trHato.findViewById(R.id.rep_tv_interpartos);
            pabor = (TextView) trHato.findViewById(R.id.rep_tv_pabortos);

            reg.setText("HATO");
            reg2.setText("HATO");
            edadmm.setText(hato.getEdadMM()+"");
            dias1s.setText(hato.getDias1Serv()+"");
            pfer1.setText("%"+hato.getpFertil1Serv());
            interserv.setText(hato.getInterServ()+"");
            servx.setText(hato.getServxConc()+"");
            diasab.setText(hato.getDiasAb()+"");
            interpar.setText(hato.getInterPartos()+"");
            pabor.setText("%" + hato.getpAbortos());

            tableLayout.addView(trHato);

            for (EstGeneral est:estgenerales){
                TableRow tr = (TableRow) inflater.inflate(R.layout.reproductivos_row, tableLayout, false);
                TextView greg,greg2,gedadmm,gdias1s,gpfer1,ginterserv,gservx,gdiasab,ginterpar,gpabor;
                greg = (TextView) tr.findViewById(R.id.rep_tv_region);
                greg2 = (TextView) tr.findViewById(R.id.rep_tv_region2);
                gedadmm = (TextView) tr.findViewById(R.id.rep_tv_edadmm);
                gdias1s = (TextView) tr.findViewById(R.id.rep_tv_dias1serv);
                gpfer1 = (TextView) tr.findViewById(R.id.rep_tv_pfertil1serv);
                ginterserv = (TextView) tr.findViewById(R.id.rep_tv_interserv);
                gservx = (TextView) tr.findViewById(R.id.rep_tv_servxconc);
                gdiasab = (TextView) tr.findViewById(R.id.rep_tv_diasab);
                ginterpar = (TextView) tr.findViewById(R.id.rep_tv_interpartos);
                gpabor = (TextView) tr.findViewById(R.id.rep_tv_pabortos);

                greg.setText(est.getTipo());
                greg2.setText(est.getTipo());
                gedadmm.setText(est.getEdadMM() + "");
                gdias1s.setText(est.getDias1Serv() + "");
                gpfer1.setText("%" + est.getpFertil1Serv());
                ginterserv.setText(est.getInterServ() + "");
                gservx.setText(est.getServxConc() + "");
                gdiasab.setText(est.getDiasAb() + "");
                ginterpar.setText(est.getInterPartos() + "");
                gpabor.setText("%" + est.getpAbortos());

                tableLayout.addView(tr);
            }*/

            return rootView;
        }
    }

    public static class FragmentLaboratorio extends Fragment{
        SeekBar seekBar;
        TableLayout tableLayout;
        public FragmentLaboratorio(){}
        public static FragmentLaboratorio newInstance() {
            FragmentLaboratorio fragment = new FragmentLaboratorio();
            return fragment;
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_reporte_laboratorio, container, false);
            TextView proceso = (TextView)rootView.findViewById(R.id.txtLabProc);
            proceso.setText(fechap);
            seekBar = (SeekBar)rootView.findViewById(R.id.labZoomBar);
            LinearLayout linearLayout = (LinearLayout)rootView.findViewById(R.id.llLab);
            final TableFixHeaders tableFixHeaders = (TableFixHeaders) rootView.findViewById(R.id.labTable);
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    zoom2(i, tableFixHeaders);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            String[][] data = new String[estgenerales.size()+2][6];
            for (int i=0;i<6;i++){ //HEADER
                switch(i){
                    case 0:
                        data[0][i] ="Lugar";
                        break;
                    case 1:
                        data[0][i] =" % Grasa";
                        break;
                    case 2:
                        data[0][i] ="% Proteína";
                        break;
                    case 3:
                        data[0][i] ="% S.N.G.";
                        break;
                    case 4:
                        data[0][i] ="N.U. mg/dl";
                        break;
                    case 5:
                        data[0][i] ="C.C.S.";
                        break;
                }
            }
            for (int i=0;i<6;i++){ //HATO
                switch(i){
                    case 0:
                        data[1][i] =hato.getNoHato();
                        break;
                    case 1:
                        data[1][i] = hato.getpGrasa()+"%";
                        break;
                    case 2:
                        data[1][i] = hato.getpProteina()+"%";
                        break;
                    case 3:
                        data[1][i] = hato.getpSolidos()+"%";
                        break;
                    case 4:
                        data[1][i] = hato.getNitrogU()+"";
                        break;
                    case 5:
                        data[1][i] = hato.getcCSMiles()+"";
                        break;
                }
            }
            int c = 2;
            for (EstGeneral est:estgenerales){
                for (int i=0;i<6;i++){ //HATO
                    switch(i){
                        case 0:
                            data[c][i] = est.getTipo();
                            break;
                        case 1:
                            data[c][i] = est.getpGrasa()+"%";
                            break;
                        case 2:
                            data[c][i] = est.getpProteina()+"%";
                            break;
                        case 3:
                            data[c][i] = est.getpSolidos()+"%";
                            break;
                        case 4:
                            data[c][i] = est.getNitrogU()+"";
                            break;
                        case 5:
                            data[c][i] = est.getcCSMiles()+"";
                            break;
                    }
                }
                c++;
            }
            MatrixTableAdapter<String> matrixTableAdapter = new MatrixTableAdapter<String>(rootView.getContext(),data, Color.rgb(255, 255, 102),hato.getRegion());
            tableFixHeaders.setAdapter(matrixTableAdapter);
            /*TableRow trHato = (TableRow) inflater.inflate(R.layout.laboratorio_row, tableLayout, false);
            TextView reg, reg2, pgrasa,pprot,psng,nu,ccs;
            reg = (TextView) trHato.findViewById(R.id.lab_tv_region);
            reg2 = (TextView) trHato.findViewById(R.id.lab_tv_region2);
            pgrasa = (TextView) trHato.findViewById(R.id.lab_tv_pgrasa);
            pprot = (TextView) trHato.findViewById(R.id.lab_tv_pproteina);
            psng = (TextView) trHato.findViewById(R.id.lab_tv_psng);
            nu = (TextView) trHato.findViewById(R.id.lab_tv_nu);
            ccs = (TextView) trHato.findViewById(R.id.lab_tv_ccs);

            reg.setText("HATO");
            reg2.setText("HATO");
            pgrasa.setText("%"+hato.getpGrasa());
            pprot.setText("%"+hato.getpProteina());
            psng.setText("%"+hato.getpSolidos());
            nu.setText(hato.getNitrogU()+"");
            ccs.setText(hato.getcCSMiles() + "");

            tableLayout.addView(trHato);

            for(EstGeneral est:estgenerales){
                TableRow tr = (TableRow) inflater.inflate(R.layout.laboratorio_row, tableLayout, false);
                TextView greg, greg2, gpgrasa,gpprot,gpsng,gnu,gccs;
                greg = (TextView) tr.findViewById(R.id.lab_tv_region);
                greg2 = (TextView) tr.findViewById(R.id.lab_tv_region2);
                gpgrasa = (TextView) tr.findViewById(R.id.lab_tv_pgrasa);
                gpprot = (TextView) tr.findViewById(R.id.lab_tv_pproteina);
                gpsng = (TextView) tr.findViewById(R.id.lab_tv_psng);
                gnu = (TextView) tr.findViewById(R.id.lab_tv_nu);
                gccs = (TextView) tr.findViewById(R.id.lab_tv_ccs);

                greg.setText(est.getTipo());
                greg2.setText(est.getTipo());
                gpgrasa.setText("%" + est.getpGrasa());
                gpprot.setText("%" + est.getpProteina());
                gpsng.setText("%" + est.getpSolidos());
                gnu.setText(est.getNitrogU() + "");
                gccs.setText(est.getcCSMiles() + "");

                tableLayout.addView(tr);
            }*/

            return rootView;
        }
    }



}
