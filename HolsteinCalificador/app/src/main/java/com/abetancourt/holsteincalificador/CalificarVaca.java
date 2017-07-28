package com.abetancourt.holsteincalificador;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class CalificarVaca extends AppCompatActivity {

    SQLite sql;
    Button btnCancelar, btnGuardar, btnCalcular;
    LinearLayout scroll;
    Vaca vaca;

    ArrayList<RadioGroup> radios;
    ArrayList<CheckBox> checks;
    ArrayList<String> clases;
    RadioGroup.OnCheckedChangeListener radListener;
    TextWatcher textWatcher;
    View.OnFocusChangeListener focusChangeListener;
    InputMethodManager inputMethodManager;
    AdapterView.OnItemSelectedListener spinnerListener;

    RelativeLayout vacaLayout;

    TextView
            lblNoVaca,
            lblNoReg,
            lblRegPadre,
            lblRegMadre,
            lblFechaNac,
            lblNoParto,
            lblFechaPar,
            lblCalifAnt,
            lblClaseAnt,
            lblTipoPar,
            lblNoLac,
            lblMesesAb,
            lblMesesPario,
            lblCalifAnca,
            lblCalifFLechera,
            lblCalifPatasYP,
            lblCalifSistemaM,
            lblFechaCalifAnt,
            lblSocio;

    RadioGroup
            radVacaAncaInclinacion,
            radVacaAncaAnchura,
            radVacaAncaFortaleza,
            radVacaAncaColocacion,

            radVacaMamProfund,
            radVacaMamTextura,
            radVacaMamLigam,
            radVacaMamInser,
            radVacaMamTetas,
            radVacaMamAlUbrTra,
            radVacaMamAncUbrTra,
            radVacaMamPoscPezo,
            radVacaMamLongPezo,

            radVacaFortEstatura,
            radVacaFortCruz,
            radVacaFortPecho,
            radVacaFortProfund,
            radVacaFortAngul,
            radVacaFortTamano,

            radVacaPatasAngulo,
            radVacaPatasProfund,
            radVacaPatasHueso,
            radVacaPatasPTraseras,
            radVacaPatasPPost;

    CheckBox
            def10,
            def11,
            def12,
            def22,
            def23,
            def24,
            def25,
            def26,
            def27,
            def28,
            def29,
            def30,
            def31,
            def32,
            def34,
            def35,
            def36,
            def40,
            def42,
            def43,
            def44,
            def45,
            def46,
            def47,
            def48;

    EditText
            txtVacaCalif,
            txtAncaCalif,
            txtMamarioCalif,
            txtPatasCalif,
            txtFortalezaCalif,
            txtComentario;

    Spinner
            spinVacaClase,
            spinVacaCalifAnca,
            spinVacaCalifFortaleza,
            spinVacaCalifPatas,
            spinVacaCalifMamario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calificar_vaca);
        init();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void init(){
        inputMethodManager = (InputMethodManager) CalificarVaca.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        Bundle reicieveParams = getIntent().getExtras();
        int vaca_id = reicieveParams.getInt("ID");
        sql = new SQLite(this);
        vaca = sql.obtenerVaca(vaca_id);

        radios = new ArrayList<RadioGroup>();
        checks = new ArrayList<CheckBox>();
        /*explist = (ExpandableListView)findViewById(R.id.explistVaca);
        adapter = new ExpListAdapter(this);
        explist.setAdapter(adapter);
        */
        vacaLayout = (RelativeLayout)findViewById(R.id.VacaLayout);
        scroll = (LinearLayout)findViewById(R.id.scrollCalificacion);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v1 = inflater.inflate(R.layout.vaca_anca, null);
        v1.setPadding(30, 30, 0, 30);
        View v2 = inflater.inflate(R.layout.vaca_sistema_mamario, null);
        v2.setPadding(30, 30, 0, 30);
        View v3 = inflater.inflate(R.layout.vaca_fortaleza_lechera, null);
        v3.setPadding(30, 30, 0, 30);
        View v4 = inflater.inflate(R.layout.vaca_patas_pezunas, null);
        v4.setPadding(30, 30, 0, 30);
        View v5 = inflater.inflate(R.layout.vaca_guardar, null);
        v4.setPadding(30, 30, 0, 30);

        scroll.addView(v1);
        scroll.addView(v2);
        scroll.addView(v3);
        scroll.addView(v4);
        scroll.addView(v5);

        btnGuardar = (Button)findViewById(R.id.btnVacaGuardarCalif);
        btnCancelar = (Button)findViewById(R.id.btnVacaCancelar);

        if(vaca.isSync()){
            btnGuardar.setEnabled(false);
            new AlertDialog.Builder(CalificarVaca.this)
                    .setTitle("Calificaciones enviadas ")
                    .setMessage("Las calificaciones de ésta vaca ya fueron enviadas, desea permanecer en la vista?")
                    .setCancelable(true)
                    .setPositiveButton("Salir / Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            CalificarVaca.this.finish();
                        }
                    })
                    .setNegativeButton("Permanecer / Stay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    })
                    .create()
                    .show();
        }
        radListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioGroup.setBackgroundColor(Color.TRANSPARENT);
            }
        };
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length()>1) {
                    try {
                        CalificarVaca.this.getCurrentFocus().setBackgroundColor(Color.TRANSPARENT);
                    }catch(Exception e){e.printStackTrace();}
                }
                if (charSequence.length()>=2){
                    try {
                        CalificarVaca.this.getCurrentFocus().clearFocus();
                        inputMethodManager.hideSoftInputFromWindow(CalificarVaca.this.getCurrentFocus().getWindowToken(), 0);
                    }catch(Exception e){e.printStackTrace();}
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };

        focusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (view == txtAncaCalif && txtAncaCalif.getText().toString().length() > 0) {
                    spinVacaCalifAnca.setSelection(clases.indexOf(calcularClase(Integer.parseInt(txtAncaCalif.getText().toString()))));
                    actualizaResumen(1);
                } else if (view == txtFortalezaCalif && txtFortalezaCalif.getText().toString().length() > 0) {
                    spinVacaCalifFortaleza.setSelection(clases.indexOf(calcularClase(Integer.parseInt(txtFortalezaCalif.getText().toString()))));
                    actualizaResumen(2);
                } else if (view == txtMamarioCalif && txtMamarioCalif.getText().toString().length() > 0) {
                    spinVacaCalifMamario.setSelection(clases.indexOf(calcularClase(Integer.parseInt(txtMamarioCalif.getText().toString()))));
                    actualizaResumen(4);
                } else if (view == txtPatasCalif && txtPatasCalif.getText().toString().length() > 0) {
                    spinVacaCalifPatas.setSelection(clases.indexOf(calcularClase(Integer.parseInt(txtPatasCalif.getText().toString()))));
                    actualizaResumen(3);
                } else if (view == txtVacaCalif && txtVacaCalif.getText().toString().length() > 0) {
                    spinVacaClase.setSelection(clases.indexOf(calcularClase(Integer.parseInt(txtVacaCalif.getText().toString()))));
                }
            }
        };

        spinnerListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> view, View view2, int i, long l) {
                if (view == spinVacaCalifAnca && txtAncaCalif.getText().toString().length() > 0) {
                    if (!spinVacaCalifAnca.getSelectedItem().toString().equals(calcularClase(Integer.parseInt(txtAncaCalif.getText().toString()))))
                        Toast.makeText(CalificarVaca.this, "La Clase no coincide con los puntos", Toast.LENGTH_LONG).show();
                    actualizaResumen(1);
                }
                else if (view == spinVacaCalifFortaleza && txtFortalezaCalif.getText().toString().length() > 0) {
                    if (!spinVacaCalifFortaleza.getSelectedItem().toString().equals(calcularClase(Integer.parseInt(txtFortalezaCalif.getText().toString()))))
                        Toast.makeText(CalificarVaca.this, "La Clase no coincide con los puntos", Toast.LENGTH_LONG).show();
                    actualizaResumen(2);
                }
                else if (view == spinVacaCalifMamario && txtMamarioCalif.getText().toString().length() > 0) {
                    if (!spinVacaCalifMamario.getSelectedItem().toString().equals(calcularClase(Integer.parseInt(txtMamarioCalif.getText().toString()))))
                        Toast.makeText(CalificarVaca.this, "La Clase no coincide con los puntos", Toast.LENGTH_LONG).show();
                    actualizaResumen(4);
                }
                else if (view == spinVacaCalifPatas && txtPatasCalif.getText().toString().length() > 0) {
                    if (!spinVacaCalifPatas.getSelectedItem().toString().equals(calcularClase(Integer.parseInt(txtPatasCalif.getText().toString()))))
                        Toast.makeText(CalificarVaca.this, "La Clase no coincide con los puntos", Toast.LENGTH_LONG).show();
                    actualizaResumen(3);
                }
                else if (view == spinVacaClase && txtVacaCalif.getText().toString().length() > 0) {
                    if (!spinVacaClase.getSelectedItem().toString().equals(calcularClase(Integer.parseInt(txtVacaCalif.getText().toString()))))
                        Toast.makeText(CalificarVaca.this, "La Clase no coincide con los puntos", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };

        lblNoVaca = (TextView)findViewById(R.id.lblVacaNoVaca);
        lblNoReg = (TextView)findViewById(R.id.lblVacaNoReg);
        lblRegPadre = (TextView)findViewById(R.id.lblVacaRegPadre);
        lblRegMadre = (TextView)findViewById(R.id.lblVacaRegMadre);
        lblFechaNac = (TextView)findViewById(R.id.lblVacaFechaNac);
        lblNoParto = (TextView)findViewById(R.id.lblVacaNoParto);
        lblFechaPar = (TextView)findViewById(R.id.lblVacaFechaParto);
        lblCalifAnt = (TextView)findViewById(R.id.lblVacaCalifAnt);
        lblClaseAnt = (TextView)findViewById(R.id.lblVacaClaseAnt);
        lblTipoPar = (TextView)findViewById(R.id.lblVacaTipoParto);
        lblNoLac = (TextView)findViewById(R.id.lblVacaNoLactNorm);
        lblMesesAb = (TextView)findViewById(R.id.lblVacaMesesAborto);
        lblMesesPario = (TextView)findViewById(R.id.lblVacaMesesPario);
        lblFechaCalifAnt = (TextView)findViewById(R.id.lblVacaFechaCalifAnt);
        lblSocio = (TextView)findViewById(R.id.lblVacaSocio);

        lblCalifAnca = (TextView)findViewById(R.id.lblVacaCalifAnca);
        lblCalifFLechera = (TextView)findViewById(R.id.lblVacaCalifFLechera);
        lblCalifPatasYP = (TextView)findViewById(R.id.lblVacaCalifPatasYP);
        lblCalifSistemaM = (TextView)findViewById(R.id.lblVacaCalifSistemaM);

        if (vaca!=null) {
            Socio s = sql.obtenerSocio(vaca.getNoHato());
            lblSocio.setText("Socio: "+s.getNombre());

            lblNoVaca.setText("No. Vaca: " + vaca.getNoCP());
            lblNoReg.setText("No. Registro: " + vaca.getNoRegistro());
            lblRegPadre.setText("Reg. Padre: " + vaca.getRegPadre());
            lblRegMadre.setText("Reg. Madre: " + vaca.getRegmadre());
            lblFechaNac.setText("Fecha Nac.: " + vaca.getFechaNac());
            lblNoParto.setText("No. Parto: " + vaca.getNoLactancia());
            lblFechaPar.setText("Fecha Par.: " + vaca.getFechaParto());
            lblCalifAnt.setText("Calif. Anterior: " + vaca.getCalifAnt());
            lblClaseAnt.setText("Clase Anterior: " + vaca.getClaseAnt());
            lblTipoPar.setText("Tipo Parto: " + vaca.getTipoParto());
            lblNoLac.setText("No. Lact. Norm.: " + vaca.getLacNorm());
            lblMesesAb.setText("Meses Aborto: " + vaca.getMesesAborto());
            if (vaca.getFechaCalifAnt()!=null && !vaca.getFechaCalifAnt().isEmpty())
                lblFechaCalifAnt.setText("F.Calf. Ant.: "+vaca.getFechaCalifAnt());
            else
                lblFechaCalifAnt.setText("F.Calf. Ant.: SC ");
            String pario = "";
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                if (vaca.getFechaParto()!=null && vaca.getFechaNac()!=null) {
                    Date parto = format.parse(vaca.getFechaParto());
                    Date nac = format.parse(vaca.getFechaNac());
                    Long meses = sql.getDateDiff(parto, nac, TimeUnit.DAYS);
                    double mesesF = Math.ceil((meses / 30.0));
                    pario = "Parió "+Math.abs(mesesF)+" meses de edad";
                }
            }catch(Exception e){}
            lblMesesPario.setText(pario);
            if (vaca.getNoLactancia()<=1) {
                vacaLayout.setBackgroundColor(Color.rgb(236, 249, 255));
                lblNoParto.setTextColor(Color.BLUE);
            }
            else
                vacaLayout.setBackgroundColor(Color.WHITE);
        }

        radVacaAncaInclinacion = (RadioGroup)findViewById(R.id.radVacaAncaInclinacion);
        radVacaAncaAnchura = (RadioGroup)findViewById(R.id.radVacaAncaAnchura);
        radVacaAncaFortaleza = (RadioGroup)findViewById(R.id.radVacaAncaFortaleza);
        radVacaAncaColocacion = (RadioGroup)findViewById(R.id.radVacaAncaColocacion);
        radVacaMamProfund = (RadioGroup)findViewById(R.id.radVacaMamProfund);
        radVacaMamTextura = (RadioGroup)findViewById(R.id.radVacaMamTextura);
        radVacaMamLigam = (RadioGroup)findViewById(R.id.radVacaMamLigam);
        radVacaMamInser = (RadioGroup)findViewById(R.id.radVacaMamInser);
        radVacaMamTetas = (RadioGroup)findViewById(R.id.radVacaMamTetas);
        radVacaMamAlUbrTra = (RadioGroup)findViewById(R.id.radVacaMamAlUbrTra);
        radVacaMamAncUbrTra = (RadioGroup)findViewById(R.id.radVacaMamAncUbrTra);
        radVacaMamPoscPezo = (RadioGroup)findViewById(R.id.radVacaMamPoscPezo);
        radVacaMamLongPezo = (RadioGroup)findViewById(R.id.radVacaMamLongPezo);
        radVacaFortEstatura = (RadioGroup)findViewById(R.id.radVacaFortEstatura);
        radVacaFortCruz = (RadioGroup)findViewById(R.id.radVacaFortCruz);
        radVacaFortPecho = (RadioGroup)findViewById(R.id.radVacaFortPecho);
        radVacaFortProfund = (RadioGroup)findViewById(R.id.radVacaFortProfund);
        radVacaFortAngul = (RadioGroup)findViewById(R.id.radVacaFortAngul);
        radVacaFortTamano = (RadioGroup)findViewById(R.id.radVacaFortTamano);
        radVacaPatasAngulo = (RadioGroup)findViewById(R.id.radVacaPatasAngulo);
        radVacaPatasProfund = (RadioGroup)findViewById(R.id.radVacaPatasProfund);
        radVacaPatasHueso = (RadioGroup)findViewById(R.id.radVacaPatasHueso);
        radVacaPatasPTraseras = (RadioGroup)findViewById(R.id.radVacaPatasPTraseras);
        radVacaPatasPPost = (RadioGroup)findViewById(R.id.radVacaPatasPPost);

        def10 = (CheckBox)findViewById(R.id.def10);
        def11 = (CheckBox)findViewById(R.id.def11);
        def12 = (CheckBox)findViewById(R.id.def12);
        def22 = (CheckBox)findViewById(R.id.def22);
        def23 = (CheckBox)findViewById(R.id.def23);
        def24 = (CheckBox)findViewById(R.id.def24);
        def25 = (CheckBox)findViewById(R.id.def25);
        def26 = (CheckBox)findViewById(R.id.def26);
        def27 = (CheckBox)findViewById(R.id.def27);
        def28 = (CheckBox)findViewById(R.id.def28);
        def29 = (CheckBox)findViewById(R.id.def29);
        def30 = (CheckBox)findViewById(R.id.def30);
        def31 = (CheckBox)findViewById(R.id.def31);
        def32 = (CheckBox)findViewById(R.id.def32);
        def34 = (CheckBox)findViewById(R.id.def34);
        def35 = (CheckBox)findViewById(R.id.def35);
        def36 = (CheckBox)findViewById(R.id.def36);
        def40 = (CheckBox)findViewById(R.id.def40);
        def42 = (CheckBox)findViewById(R.id.def42);
        def43 = (CheckBox)findViewById(R.id.def43);
        def44 = (CheckBox)findViewById(R.id.def44);
        def45 = (CheckBox)findViewById(R.id.def45);
        def46 = (CheckBox)findViewById(R.id.def46);
        def47 = (CheckBox)findViewById(R.id.def47);
        def48 = (CheckBox)findViewById(R.id.def48);

        clases = new ArrayList<String>();
        clases.add("FA");
        clases.add("GO");
        clases.add("GP");
        clases.add("VG");
        clases.add("EX");
        ArrayAdapter<String> claseAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, clases);
        claseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinVacaCalifAnca = (Spinner)findViewById(R.id.spinVacaCalifAnca);
        spinVacaCalifFortaleza = (Spinner)findViewById(R.id.spinVacaCalifFortaleza);
        spinVacaCalifPatas = (Spinner)findViewById(R.id.spinVacaCalifPatas);
        spinVacaCalifMamario = (Spinner)findViewById(R.id.spinVacaCalifMamario);
        spinVacaClase = (Spinner)findViewById(R.id.spinnerVacaClase);

        spinVacaCalifAnca.setAdapter(claseAdapter);
        spinVacaCalifFortaleza.setAdapter(claseAdapter);
        spinVacaCalifPatas.setAdapter(claseAdapter);
        spinVacaCalifMamario.setAdapter(claseAdapter);
        spinVacaClase.setAdapter(claseAdapter);

        spinVacaCalifAnca.setOnItemSelectedListener(spinnerListener);
        spinVacaCalifFortaleza.setOnItemSelectedListener(spinnerListener);
        spinVacaCalifPatas.setOnItemSelectedListener(spinnerListener);
        spinVacaCalifMamario.setOnItemSelectedListener(spinnerListener);
        spinVacaClase.setOnItemSelectedListener(spinnerListener);

        txtVacaCalif = (EditText)findViewById(R.id.txtVacaCalif);
        txtAncaCalif = (EditText)findViewById(R.id.txtVacaCalifAnca);
        txtFortalezaCalif = (EditText)findViewById(R.id.txtVacaCalifFortaleza);
        txtMamarioCalif = (EditText)findViewById(R.id.txtVacaCalifMamario);
        txtPatasCalif = (EditText)findViewById(R.id.txtVacaCalifPatas);

        txtAncaCalif.addTextChangedListener(textWatcher);
        txtVacaCalif.addTextChangedListener(textWatcher);
        txtMamarioCalif.addTextChangedListener(textWatcher);
        txtPatasCalif.addTextChangedListener(textWatcher);
        txtFortalezaCalif.addTextChangedListener(textWatcher);

        txtAncaCalif.setOnFocusChangeListener(focusChangeListener);
        txtMamarioCalif.setOnFocusChangeListener(focusChangeListener);
        txtPatasCalif.setOnFocusChangeListener(focusChangeListener);
        txtFortalezaCalif.setOnFocusChangeListener(focusChangeListener);
        txtVacaCalif.setOnFocusChangeListener(focusChangeListener);

        txtComentario = (EditText) findViewById(R.id.txtVacaComent);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarCalificaciones();
            }
        });
        btnCalcular = (Button)findViewById(R.id.btnVacaCalcular);
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calcular(true);
            }
        });
        btnCancelar = (Button) findViewById(R.id.btnVacaCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalificarVaca.this.finish();
            }
        });

        if (vaca.getFechaCalif()!=null && !vaca.getFechaCalif().equals(""))establecerActual();

        txtVacaCalif.clearFocus();
    }

    public String calcularClase(int puntos){
        if (puntos>=60 && puntos <=69) return "FA";
        else if (puntos>=70 && puntos <=79) return "GO";
        else if (puntos>=80 && puntos <=84) return "GP";
        else if (puntos>=85 && puntos <=89) return "VG";
        else if (puntos>=90) return "EX";
        else return "FA";
    }

    private void actualizaResumen(int clase){
        String calif = "", puntos="";
        switch(clase) {
            case 1: // Anca
                calif = spinVacaCalifAnca.getSelectedItem().toString();
                puntos = txtAncaCalif.getText().toString();
                lblCalifAnca.setText("Anca: "+calif+"/"+puntos);
                break;
            case 2: // FLechera
                calif = spinVacaCalifFortaleza.getSelectedItem().toString();
                puntos = txtFortalezaCalif.getText().toString();
                lblCalifFLechera.setText("F.Lechera: "+calif+"/"+puntos);
                break;
            case 3: //Patas
                calif = spinVacaCalifPatas.getSelectedItem().toString();
                puntos = txtPatasCalif.getText().toString();
                lblCalifPatasYP.setText("Patas y Pezuñas: "+calif+"/"+puntos);
                break;
            case 4: // SMamario
                calif = spinVacaCalifMamario.getSelectedItem().toString();
                puntos = txtMamarioCalif.getText().toString();
                lblCalifSistemaM.setText("Sistema Mamario: "+calif+"/"+puntos);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(CalificarVaca.this)
                .setTitle("Botón Atrás Presionado ")
                .setMessage("Está seguro de abandonar la vista sin guardar las calificaciones?")
                .setCancelable(true)
                .setPositiveButton("Salir / Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                        CalificarVaca.this.finish();
                    }
                })
                .setNegativeButton("Permanecer / Stay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .create()
                .show();
    }

    private void establecerActual(){
        RadioButton r;
        r = (RadioButton)radVacaAncaInclinacion.getChildAt(vaca.getC7() - 1);r.setChecked(true);
        r = (RadioButton)radVacaAncaAnchura.getChildAt(vaca.getC8() - 1);r.setChecked(true);
        r = (RadioButton)radVacaAncaFortaleza.getChildAt(vaca.getC6() - 1);r.setChecked(true);
        r = (RadioButton)radVacaAncaColocacion.getChildAt(vaca.getC8a() - 1);r.setChecked(true);
        r = (RadioButton)radVacaMamProfund.getChildAt(vaca.getC15() - 1);r.setChecked(true);
        r = (RadioButton)radVacaMamTextura.getChildAt(vaca.getC16() - 1);r.setChecked(true);
        r = (RadioButton)radVacaMamLigam.getChildAt(vaca.getC17() - 1);r.setChecked(true);
        r = (RadioButton)radVacaMamInser.getChildAt(vaca.getC18() - 1);r.setChecked(true);
        r = (RadioButton)radVacaMamTetas.getChildAt(vaca.getC19() - 1);r.setChecked(true);
        r = (RadioButton)radVacaMamAlUbrTra.getChildAt(vaca.getC21() - 1);r.setChecked(true);
        r = (RadioButton)radVacaMamAncUbrTra.getChildAt(vaca.getC22() - 1);r.setChecked(true);
        r = (RadioButton)radVacaMamPoscPezo.getChildAt(vaca.getC23() - 1);r.setChecked(true);
        r = (RadioButton)radVacaMamLongPezo.getChildAt(vaca.getC20() - 1);r.setChecked(true);
        r = (RadioButton)radVacaFortEstatura.getChildAt(vaca.getC1() - 1);r.setChecked(true);
        r = (RadioButton)radVacaFortCruz.getChildAt(vaca.getC2() - 1);r.setChecked(true);
        r = (RadioButton)radVacaFortPecho.getChildAt(vaca.getC4() - 1);r.setChecked(true);
        r = (RadioButton)radVacaFortProfund.getChildAt(vaca.getC5() - 1);r.setChecked(true);
        r = (RadioButton)radVacaFortAngul.getChildAt(vaca.getC24() - 1);r.setChecked(true);
        r = (RadioButton)radVacaFortTamano.getChildAt(vaca.getC3() - 1);r.setChecked(true);
        r = (RadioButton)radVacaPatasAngulo.getChildAt(vaca.getC9() - 1);r.setChecked(true);
        r = (RadioButton)radVacaPatasProfund.getChildAt(vaca.getC11() - 1);r.setChecked(true);
        r = (RadioButton)radVacaPatasHueso.getChildAt(vaca.getC12() - 1);r.setChecked(true);
        r = (RadioButton)radVacaPatasPTraseras.getChildAt(vaca.getC13() - 1);r.setChecked(true);
        r = (RadioButton)radVacaPatasPPost.getChildAt(vaca.getC14() - 1);r.setChecked(true);

        if(vaca.getDef10()>0)def10.setChecked(true);
        if(vaca.getDef11()>0)def11.setChecked(true);
        if(vaca.getDef12()>0)def12.setChecked(true);
        if(vaca.getDef22()>0)def22.setChecked(true);
        if(vaca.getDef23()>0)def23.setChecked(true);
        if(vaca.getDef24()>0)def24.setChecked(true);
        if(vaca.getDef25()>0)def25.setChecked(true);
        if(vaca.getDef26()>0)def26.setChecked(true);
        if(vaca.getDef27()>0)def27.setChecked(true);
        if(vaca.getDef28()>0)def28.setChecked(true);
        if(vaca.getDef29()>0)def29.setChecked(true);
        if(vaca.getDef30()>0)def30.setChecked(true);
        if(vaca.getDef31()>0)def31.setChecked(true);
        if(vaca.getDef32()>0)def32.setChecked(true);
        if(vaca.getDef34()>0)def34.setChecked(true);
        if(vaca.getDef35()>0)def35.setChecked(true);
        if(vaca.getDef36()>0)def36.setChecked(true);
        if(vaca.getDef40()>0)def40.setChecked(true);
        if(vaca.getDef42()>0)def42.setChecked(true);
        if(vaca.getDef43()>0)def43.setChecked(true);
        if(vaca.getDef44()>0)def44.setChecked(true);
        if(vaca.getDef45()>0)def45.setChecked(true);
        if(vaca.getDef46()>0)def46.setChecked(true);
        if(vaca.getDef47()>0)def47.setChecked(true);
        if(vaca.getDef48()>0)def48.setChecked(true);

        spinVacaCalifAnca.setSelection(clases.indexOf(vaca.getCss2()));
        spinVacaCalifFortaleza.setSelection(clases.indexOf(vaca.getCss1()));
        spinVacaCalifPatas.setSelection(clases.indexOf(vaca.getCss3()));
        spinVacaCalifMamario.setSelection(clases.indexOf(vaca.getCss4()));
        spinVacaClase.setSelection(clases.indexOf(vaca.getClaseCalif()));

        txtVacaCalif.setText(vaca.getPuntosCalif() + "");
        txtAncaCalif.setText(vaca.getCss2puntos() + "");
        txtFortalezaCalif.setText(vaca.getCss1puntos() + "");
        txtPatasCalif.setText(vaca.getCss3puntos() + "");
        txtMamarioCalif.setText(vaca.getCss4puntos()+"");
        txtComentario.setText(vaca.getComentario()+"");
    }


    private boolean verificarCampos(){
        if(radVacaAncaInclinacion.getCheckedRadioButtonId()==-1)radVacaAncaInclinacion.setBackgroundColor(Color.RED);
        if(radVacaAncaAnchura.getCheckedRadioButtonId()==-1)radVacaAncaAnchura.setBackgroundColor(Color.RED);
        if(radVacaAncaFortaleza.getCheckedRadioButtonId()==-1)radVacaAncaFortaleza.setBackgroundColor(Color.RED);
        if(radVacaAncaColocacion.getCheckedRadioButtonId()==-1)radVacaAncaColocacion.setBackgroundColor(Color.RED);
        if(radVacaMamProfund.getCheckedRadioButtonId()==-1)radVacaMamProfund.setBackgroundColor(Color.RED);
        if(radVacaMamTextura.getCheckedRadioButtonId()==-1)radVacaMamTextura.setBackgroundColor(Color.RED);
        if(radVacaMamLigam.getCheckedRadioButtonId()==-1)radVacaMamLigam.setBackgroundColor(Color.RED);
        if(radVacaMamInser.getCheckedRadioButtonId()==-1)radVacaMamInser.setBackgroundColor(Color.RED);
        if(radVacaMamTetas.getCheckedRadioButtonId()==-1)radVacaMamTetas.setBackgroundColor(Color.RED);
        if(radVacaMamAlUbrTra.getCheckedRadioButtonId()==-1)radVacaMamAlUbrTra.setBackgroundColor(Color.RED);
        if(radVacaMamAncUbrTra.getCheckedRadioButtonId()==-1)radVacaMamAncUbrTra.setBackgroundColor(Color.RED);
        if(radVacaMamPoscPezo.getCheckedRadioButtonId()==-1)radVacaMamPoscPezo.setBackgroundColor(Color.RED);
        if(radVacaMamLongPezo.getCheckedRadioButtonId()==-1)radVacaMamLongPezo.setBackgroundColor(Color.RED);
        if(radVacaFortEstatura.getCheckedRadioButtonId()==-1)radVacaFortEstatura.setBackgroundColor(Color.RED);
        if(radVacaFortCruz.getCheckedRadioButtonId()==-1)radVacaFortCruz.setBackgroundColor(Color.RED);
        if(radVacaFortPecho.getCheckedRadioButtonId()==-1)radVacaFortPecho.setBackgroundColor(Color.RED);
        if(radVacaFortProfund.getCheckedRadioButtonId()==-1)radVacaFortProfund.setBackgroundColor(Color.RED);
        if(radVacaFortAngul.getCheckedRadioButtonId()==-1)radVacaFortAngul.setBackgroundColor(Color.RED);
        if(radVacaFortTamano.getCheckedRadioButtonId()==-1)radVacaFortTamano.setBackgroundColor(Color.RED);
        if(radVacaPatasAngulo.getCheckedRadioButtonId()==-1)radVacaPatasAngulo.setBackgroundColor(Color.RED);
        if(radVacaPatasProfund.getCheckedRadioButtonId()==-1)radVacaPatasProfund.setBackgroundColor(Color.RED);
        if(radVacaPatasHueso.getCheckedRadioButtonId()==-1)radVacaPatasHueso.setBackgroundColor(Color.RED);
        if(radVacaPatasPTraseras.getCheckedRadioButtonId()==-1)radVacaPatasPTraseras.setBackgroundColor(Color.RED);
        if(radVacaPatasPPost.getCheckedRadioButtonId()==-1)radVacaPatasPPost.setBackgroundColor(Color.RED);

        if(txtAncaCalif.getText().toString().isEmpty())txtAncaCalif.setBackgroundColor(Color.RED);
        if(txtMamarioCalif.getText().toString().isEmpty())txtMamarioCalif.setBackgroundColor(Color.RED);
        if(txtPatasCalif.getText().toString().isEmpty())txtPatasCalif.setBackgroundColor(Color.RED);
        if(txtFortalezaCalif.getText().toString().isEmpty())txtFortalezaCalif.setBackgroundColor(Color.RED);

        if(
            radVacaAncaInclinacion.getCheckedRadioButtonId()==-1 ||
            radVacaAncaAnchura.getCheckedRadioButtonId()==-1 ||
            radVacaAncaFortaleza.getCheckedRadioButtonId()==-1 ||
            radVacaAncaColocacion.getCheckedRadioButtonId()==-1 ||
            radVacaMamProfund.getCheckedRadioButtonId()==-1 ||
            radVacaMamTextura.getCheckedRadioButtonId()==-1 ||
            radVacaMamLigam.getCheckedRadioButtonId()==-1 ||
            radVacaMamInser.getCheckedRadioButtonId()==-1 ||
            radVacaMamTetas.getCheckedRadioButtonId()==-1 ||
            radVacaMamAlUbrTra.getCheckedRadioButtonId()==-1 ||
            radVacaMamAncUbrTra.getCheckedRadioButtonId()==-1 ||
            radVacaMamPoscPezo.getCheckedRadioButtonId()==-1 ||
            radVacaMamLongPezo.getCheckedRadioButtonId()==-1 ||
            radVacaFortEstatura.getCheckedRadioButtonId()==-1 ||
            radVacaFortCruz.getCheckedRadioButtonId()==-1 ||
            radVacaFortPecho.getCheckedRadioButtonId()==-1 ||
            radVacaFortProfund.getCheckedRadioButtonId()==-1 ||
            radVacaFortAngul.getCheckedRadioButtonId()==-1 ||
            radVacaFortTamano.getCheckedRadioButtonId()==-1 ||
            radVacaPatasAngulo.getCheckedRadioButtonId()==-1 ||
            radVacaPatasProfund.getCheckedRadioButtonId()==-1 ||
            radVacaPatasHueso.getCheckedRadioButtonId()==-1 ||
            radVacaPatasPTraseras.getCheckedRadioButtonId()==-1 ||
            radVacaPatasPPost.getCheckedRadioButtonId()==-1 ||
            txtAncaCalif.getText().toString().isEmpty() ||
            txtMamarioCalif.getText().toString().isEmpty() ||
            txtPatasCalif.getText().toString().isEmpty() ||
            txtFortalezaCalif.getText().toString().isEmpty()
            ){

            radVacaAncaInclinacion.setOnCheckedChangeListener(radListener);
            radVacaAncaAnchura.setOnCheckedChangeListener(radListener);
            radVacaAncaFortaleza.setOnCheckedChangeListener(radListener);
            radVacaAncaColocacion.setOnCheckedChangeListener(radListener);
            radVacaMamProfund.setOnCheckedChangeListener(radListener);
            radVacaMamTextura.setOnCheckedChangeListener(radListener);
            radVacaMamLigam.setOnCheckedChangeListener(radListener);
            radVacaMamInser.setOnCheckedChangeListener(radListener);
            radVacaMamTetas.setOnCheckedChangeListener(radListener);
            radVacaMamAlUbrTra.setOnCheckedChangeListener(radListener);
            radVacaMamAncUbrTra.setOnCheckedChangeListener(radListener);
            radVacaMamPoscPezo.setOnCheckedChangeListener(radListener);
            radVacaMamLongPezo.setOnCheckedChangeListener(radListener);
            radVacaFortEstatura.setOnCheckedChangeListener(radListener);
            radVacaFortCruz.setOnCheckedChangeListener(radListener);
            radVacaFortPecho.setOnCheckedChangeListener(radListener);
            radVacaFortProfund.setOnCheckedChangeListener(radListener);
            radVacaFortAngul.setOnCheckedChangeListener(radListener);
            radVacaFortTamano.setOnCheckedChangeListener(radListener);
            radVacaPatasAngulo.setOnCheckedChangeListener(radListener);
            radVacaPatasProfund.setOnCheckedChangeListener(radListener);
            radVacaPatasHueso.setOnCheckedChangeListener(radListener);
            radVacaPatasPTraseras.setOnCheckedChangeListener(radListener);
            radVacaPatasPPost.setOnCheckedChangeListener(radListener);
            return false;
        }
        else return true;
    }


    private boolean calcular(boolean califDefault){

        if (verificarCampos()) {

            int selected;
            RadioButton radio;

            ///*** FORTALEZA LECHERA ***///
            //c1 - Estatura
            selected = radVacaFortEstatura.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            vaca.setC1(Integer.parseInt(radio.getText().toString()));
            //c2 - AltCruz
            selected = radVacaFortCruz.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            vaca.setC2(Integer.parseInt(radio.getText().toString()));
            //c3 - Tamaño
            selected = radVacaFortTamano.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            vaca.setC3(Integer.parseInt(radio.getText().toString()));
            //c4 - PechoAncho
            selected = radVacaFortPecho.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            vaca.setC4(Integer.parseInt(radio.getText().toString()));
            //c5 - Profundidad
            selected = radVacaFortProfund.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            vaca.setC5(Integer.parseInt(radio.getText().toString()));
            //c24 - Angularidad
            selected = radVacaFortAngul.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            vaca.setC24(Integer.parseInt(radio.getText().toString()));

            ///*** ANCA ***///
            //c6 - Lomo
            selected = radVacaAncaFortaleza.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            vaca.setC6(Integer.parseInt(radio.getText().toString()));
            //c7 - Punta
            selected = radVacaAncaInclinacion.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            vaca.setC7(Integer.parseInt(radio.getText().toString()));
            //c8 - Anchura
            selected = radVacaAncaAnchura.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            vaca.setC8(Integer.parseInt(radio.getText().toString()));
            //c8a - Colocacion
            selected = radVacaAncaColocacion.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            vaca.setC8a(Integer.parseInt(radio.getText().toString()));

            ///*** PATAS Y PEZUÑAS ***///
            //c9 - Angulo
            selected = radVacaPatasAngulo.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            vaca.setC9(Integer.parseInt(radio.getText().toString()));
            //c11 - ProfTalón
            selected = radVacaPatasProfund.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            vaca.setC11(Integer.parseInt(radio.getText().toString()));
            //c12 - calHuesos
            selected = radVacaPatasHueso.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            vaca.setC12(Integer.parseInt(radio.getText().toString()));
            //c13 - Aplomo
            selected = radVacaPatasPTraseras.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            vaca.setC13(Integer.parseInt(radio.getText().toString()));
            //c14 - VPosterior
            selected = radVacaPatasPPost.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            vaca.setC14(Integer.parseInt(radio.getText().toString()));

            ///*** SISTEMA MAMARIO ***///
            //c15 - SMProfundidad
            selected = radVacaMamProfund.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            vaca.setC15(Integer.parseInt(radio.getText().toString()));
            //c16 - Testura
            selected = radVacaMamTextura.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            vaca.setC16(Integer.parseInt(radio.getText().toString()));
            //c17 - LMS
            selected = radVacaMamLigam.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            vaca.setC17(Integer.parseInt(radio.getText().toString()));
            //c18 - Insercion Del
            selected = radVacaMamInser.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            vaca.setC18(Integer.parseInt(radio.getText().toString()));
            //c19 - Posicion Del
            selected = radVacaMamTetas.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            vaca.setC19(Integer.parseInt(radio.getText().toString()));
            //c20 - Long Pezones
            selected = radVacaMamLongPezo.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            vaca.setC20(Integer.parseInt(radio.getText().toString()));
            //c21 - UPAltura
            selected = radVacaMamAlUbrTra.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            vaca.setC21(Integer.parseInt(radio.getText().toString()));
            //c22 - UPAnchura
            selected = radVacaMamAncUbrTra.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            vaca.setC22(Integer.parseInt(radio.getText().toString()));
            //c23 - UPosicion
            selected = radVacaMamPoscPezo.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            vaca.setC23(Integer.parseInt(radio.getText().toString()));

            // DEFECTOS
            if (def10.isChecked()) vaca.setDef10(1);
            else vaca.setDef10(0);
            if (def11.isChecked()) vaca.setDef11(1);
            else vaca.setDef11(0);
            if (def12.isChecked()) vaca.setDef12(1);
            else vaca.setDef12(0);
            if (def22.isChecked()) vaca.setDef22(1);
            else vaca.setDef22(0);
            if (def23.isChecked()) vaca.setDef23(1);
            else vaca.setDef23(0);
            if (def24.isChecked()) vaca.setDef24(1);
            else vaca.setDef24(0);
            if (def25.isChecked()) vaca.setDef25(1);
            else vaca.setDef25(0);
            if (def26.isChecked()) vaca.setDef26(1);
            else vaca.setDef26(0);
            if (def27.isChecked()) vaca.setDef27(1);
            else vaca.setDef27(0);
            if (def28.isChecked()) vaca.setDef28(1);
            else vaca.setDef28(0);
            if (def29.isChecked()) vaca.setDef29(1);
            else vaca.setDef29(0);
            if (def30.isChecked()) vaca.setDef30(1);
            else vaca.setDef30(0);
            if (def31.isChecked()) vaca.setDef31(1);
            else vaca.setDef31(0);
            if (def32.isChecked()) vaca.setDef32(1);
            else vaca.setDef32(0);
            if (def34.isChecked()) vaca.setDef34(1);
            else vaca.setDef34(0);
            if (def35.isChecked()) vaca.setDef35(1);
            else vaca.setDef35(0);
            if (def36.isChecked()) vaca.setDef36(1);
            else vaca.setDef36(0);
            if (def40.isChecked()) vaca.setDef40(1);
            else vaca.setDef40(0);
            if (def42.isChecked()) vaca.setDef42(1);
            else vaca.setDef42(0);
            if (def43.isChecked()) vaca.setDef43(1);
            else vaca.setDef43(0);
            if (def44.isChecked()) vaca.setDef44(1);
            else vaca.setDef44(0);
            if (def45.isChecked()) vaca.setDef45(1);
            else vaca.setDef45(0);
            if (def46.isChecked()) vaca.setDef46(1);
            else vaca.setDef46(0);
            if (def47.isChecked()) vaca.setDef47(1);
            else vaca.setDef47(0);
            if (def48.isChecked()) vaca.setDef48(1);
            else vaca.setDef48(0);

            vaca.setCss1(spinVacaCalifFortaleza.getSelectedItem().toString());
            vaca.setCss2(spinVacaCalifAnca.getSelectedItem().toString());
            vaca.setCss3(spinVacaCalifPatas.getSelectedItem().toString());
            vaca.setCss4(spinVacaCalifMamario.getSelectedItem().toString());

            if (!txtFortalezaCalif.getText().toString().isEmpty())
                vaca.setCss1puntos(Integer.parseInt(txtFortalezaCalif.getText().toString()));
            else vaca.setCss1puntos(0);
            if (!txtAncaCalif.getText().toString().isEmpty())
                vaca.setCss2puntos(Integer.parseInt(txtAncaCalif.getText().toString()));
            else vaca.setCss2puntos(0);
            if (!txtPatasCalif.getText().toString().isEmpty())
                vaca.setCss3puntos(Integer.parseInt(txtPatasCalif.getText().toString()));
            else vaca.setCss3puntos(0);
            if (!txtMamarioCalif.getText().toString().isEmpty())
                vaca.setCss4puntos(Integer.parseInt(txtMamarioCalif.getText().toString()));
            else vaca.setCss4puntos(0);

            int calif = vaca.calcularCalificacion();
            String clase = vaca.calcularClase();
            Toast.makeText(CalificarVaca.this, "Calificación: " + calif + "\nClase: " + clase, Toast.LENGTH_SHORT).show();
            vaca.setPuntos(calif);
            vaca.setClase(clase);
            if (califDefault) {
                txtVacaCalif.setText(calif + "");
                spinVacaClase.setSelection(clases.indexOf(clase));
            } else {
                if (txtVacaCalif.getText().toString().equals("")) {
                    txtVacaCalif.setText(calif + "");
                    spinVacaClase.setSelection(clases.indexOf(clase));
                }
            }
            vaca.setComentario(txtComentario.getText().toString());
            return true;
        }
        else{
            Toast.makeText(CalificarVaca.this, "No se han completado todos los campos de calificación", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private void guardarCalificaciones(){
        if (txtVacaCalif.getText().toString().isEmpty() || spinVacaClase.getSelectedItem()==null){
            Toast.makeText(this,"Confirme las calificaciones finales e intente de nuevo",Toast.LENGTH_LONG).show();
        }
        else {
            new AlertDialog.Builder(
                CalificarVaca.this)
                .setCancelable(false)
                .setMessage("Está seguro de guardar las calificaciones para la vaca?")
                .setPositiveButton("Guardar / Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (calcular(false)) {
                            vaca.setPuntosCalif(Integer.parseInt(txtVacaCalif.getText().toString()));
                            vaca.setClaseCalif(spinVacaClase.getSelectedItem().toString());
                            vaca.setFechaCalif(sql.fechaActual());
                            sql.actualizarCalifVaca(vaca);
                            Toast.makeText(CalificarVaca.this, "Calificaciones de vaca guardadas.\nCow qualifications were saved.", Toast.LENGTH_LONG).show();
                            new AlertDialog.Builder(CalificarVaca.this)
                                    .setTitle("Calificaciones Guardadas")
                                    .setMessage("Calificaciones de vaca guardadas.\n" +
                                            "  Cow qualifications were saved\n\n" +
                                            "Desea permanecer en la vista o salir?\n" +
                                            "Do you want to stay in this view or exit?")
                                    .setCancelable(true)
                                    .setPositiveButton("Salir / Exit", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            CalificarVaca.this.finish();
                                        }
                                    })
                                    .setNegativeButton("Permanecer / Stay", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    })
                                    .create()
                                    .show();
                        }
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

    }

}
