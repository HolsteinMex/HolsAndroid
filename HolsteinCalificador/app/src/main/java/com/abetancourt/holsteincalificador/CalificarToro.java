package com.abetancourt.holsteincalificador;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CalificarToro extends AppCompatActivity {

    SQLite sql;
    ExpandableListView explist;
    Button btnCancelar, btnGuardar;
    LinearLayout scroll;
    Toro toro;

    ArrayList<RadioGroup> radios;
    ArrayList<CheckBox> checks;
    ArrayList<String> clases;
    RadioGroup.OnCheckedChangeListener radListener;
    TextWatcher textWatcher;
    View.OnFocusChangeListener focusChangeListener;
    InputMethodManager inputMethodManager;
    AdapterView.OnItemSelectedListener spinnerListener;

    TextView
            lblNombre,
            lblNoReg,
            lblRegPadre,
            lblRegMadre,
            lblFechaNac,
            lblCalifAnt,
            lblClaseAnt,
            lblSocio;


    RadioGroup
            radToroAncaInclinacion,
            radToroAncaAnchura,
            radToroAncaFortaleza,
            radToroAncaColocacion,

    radToroFortEstatura,
            radToroFortCruz,
            radToroFortPecho,
            radToroFortProfund,
            radToroFortAngul,
            radToroFortTamano,

    radToroPatasAngulo,
            radToroPatasProfund,
            radToroPatasHueso,
            radToroPatasPTraseras,
            radToroPatasPPost;

    CheckBox
            def10,
            def11,
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
            def45;

    EditText
            txtToroCalif,
            txtAncaCalif,
            txtPatasCalif,
            txtFortalezaCalif;

    Spinner
            spinToroClase,
            spinToroCalifAnca,
            spinToroCalifFortaleza,
            spinToroCalifPatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calificar_toro);
        init();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void init(){
        inputMethodManager = (InputMethodManager) CalificarToro.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        Bundle reicieveParams = getIntent().getExtras();
        int toro_id = reicieveParams.getInt("ID");
        sql = new SQLite(this);
        toro = sql.obtenerToro(toro_id);

        radios = new ArrayList<RadioGroup>();
        checks = new ArrayList<CheckBox>();
        /*explist = (ExpandableListView)findViewById(R.id.explistToro);
        adapter = new ExpListAdapter(this);
        explist.setAdapter(adapter);
        */
        scroll = (LinearLayout)findViewById(R.id.scrollCalificacion);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v1 = inflater.inflate(R.layout.toro_anca, null);
        v1.setPadding(30, 30, 0, 30);
        View v3 = inflater.inflate(R.layout.toro_fortaleza_lechera, null);
        v3.setPadding(30, 30, 0, 30);
        View v4 = inflater.inflate(R.layout.toro_patas_pezunas, null);
        v4.setPadding(30, 30, 0, 30);
        View v5 = inflater.inflate(R.layout.toro_guardar, null);
        v4.setPadding(30, 30, 0, 30);
        scroll.addView(v1);
        scroll.addView(v3);
        scroll.addView(v4);
        scroll.addView(v5);

        btnGuardar = (Button)findViewById(R.id.btnToroGuardarCalif);
        btnCancelar = (Button)findViewById(R.id.btnToroCancelar);

        if(toro.isSync()){
            btnGuardar.setEnabled(false);
            new AlertDialog.Builder(CalificarToro.this)
                    .setTitle("Calificaciones enviadas ")
                    .setMessage("Las calificaciones de éste toro ya fueron enviadas, desea permanecer en la vista?")
                    .setCancelable(true)
                    .setPositiveButton("Salir / Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            CalificarToro.this.finish();
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
                        CalificarToro.this.getCurrentFocus().setBackgroundColor(Color.TRANSPARENT);
                    }catch(Exception e){e.printStackTrace();}
                }
                if (charSequence.length()>=2){
                    try {
                        CalificarToro.this.getCurrentFocus().clearFocus();
                        inputMethodManager.hideSoftInputFromWindow(CalificarToro.this.getCurrentFocus().getWindowToken(), 0);
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
                    spinToroCalifAnca.setSelection(clases.indexOf(calcularClase(Integer.parseInt(txtAncaCalif.getText().toString()))));
                } else if (view == txtFortalezaCalif && txtFortalezaCalif.getText().toString().length() > 0) {
                    spinToroCalifFortaleza.setSelection(clases.indexOf(calcularClase(Integer.parseInt(txtFortalezaCalif.getText().toString()))));
                } else if (view == txtPatasCalif && txtPatasCalif.getText().toString().length() > 0) {
                    spinToroCalifPatas.setSelection(clases.indexOf(calcularClase(Integer.parseInt(txtPatasCalif.getText().toString()))));
                } else if (view == txtToroCalif && txtToroCalif.getText().toString().length() > 0) {
                    spinToroClase.setSelection(clases.indexOf(calcularClase(Integer.parseInt(txtToroCalif.getText().toString()))));
                }
            }
        };

        spinnerListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> view, View view2, int i, long l) {
                if (view == spinToroCalifAnca && txtAncaCalif.getText().toString().length() > 0) {
                    if (!spinToroCalifAnca.getSelectedItem().toString().equals(calcularClase(Integer.parseInt(txtAncaCalif.getText().toString()))))
                        Toast.makeText(CalificarToro.this, "La Clase no coincide con los puntos", Toast.LENGTH_LONG).show();
                }
                else if (view == spinToroCalifFortaleza && txtFortalezaCalif.getText().toString().length() > 0) {
                    if (!spinToroCalifFortaleza.getSelectedItem().toString().equals(calcularClase(Integer.parseInt(txtFortalezaCalif.getText().toString()))))
                        Toast.makeText(CalificarToro.this, "La Clase no coincide con los puntos", Toast.LENGTH_LONG).show();
                }
                else if (view == spinToroCalifPatas && txtPatasCalif.getText().toString().length() > 0) {
                    if (!spinToroCalifPatas.getSelectedItem().toString().equals(calcularClase(Integer.parseInt(txtPatasCalif.getText().toString()))))
                        Toast.makeText(CalificarToro.this, "La Clase no coincide con los puntos", Toast.LENGTH_LONG).show();
                }
                else if (view == spinToroClase && txtToroCalif.getText().toString().length() > 0) {
                    if (!spinToroClase.getSelectedItem().toString().equals(calcularClase(Integer.parseInt(txtToroCalif.getText().toString()))))
                        Toast.makeText(CalificarToro.this, "La Clase no coincide con los puntos", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };

        lblNombre = (TextView)findViewById(R.id.lblToroNombre);
        lblNoReg = (TextView)findViewById(R.id.lblToroNoReg);
        lblRegPadre = (TextView)findViewById(R.id.lblToroRegPadre);
        lblRegMadre = (TextView)findViewById(R.id.lblToroRegMadre);
        lblFechaNac = (TextView)findViewById(R.id.lblToroFechaNac);
        lblCalifAnt = (TextView)findViewById(R.id.lblToroCalifAnt);
        lblClaseAnt = (TextView)findViewById(R.id.lblToroClaseAnt);
        lblSocio =  (TextView)findViewById(R.id.lblToroSocio);

        if (toro !=null) {
            Socio s = sql.obtenerSocio(toro.getNoHato());
            lblSocio.setText("Socio: "+s.getNombre());
            lblNombre.setText("Nombre: " + toro.getNombre());
            lblNoReg.setText("No. Registro: " + toro.getNoRegistro());
            lblRegPadre.setText("Reg. Padre: " + toro.getRegPadre());
            lblRegMadre.setText("Reg. Madre: " + toro.getRegMadre());
            lblFechaNac.setText("Fecha Nac.: " + toro.getFechaNac());

            lblCalifAnt.setText("Calif. Anterior: " + toro.getCalifAnt());
            lblClaseAnt.setText("Clase Anterior: " + toro.getClaseAnt());
        }



        radToroAncaInclinacion = (RadioGroup)findViewById(R.id.radToroAncaInclinacion);
        radToroAncaAnchura = (RadioGroup)findViewById(R.id.radToroAncaAnchura);
        radToroAncaFortaleza = (RadioGroup)findViewById(R.id.radToroAncaFortaleza);
        radToroAncaColocacion = (RadioGroup)findViewById(R.id.radToroAncaColocacion);
        radToroFortEstatura = (RadioGroup)findViewById(R.id.radToroFortEstatura);
        radToroFortCruz = (RadioGroup)findViewById(R.id.radToroFortCruz);
        radToroFortPecho = (RadioGroup)findViewById(R.id.radToroFortPecho);
        radToroFortProfund = (RadioGroup)findViewById(R.id.radToroFortProfund);
        radToroFortAngul = (RadioGroup)findViewById(R.id.radToroFortAngul);
        radToroFortTamano = (RadioGroup)findViewById(R.id.radToroFortTamano);
        radToroPatasAngulo = (RadioGroup)findViewById(R.id.radToroPatasAngulo);
        radToroPatasProfund = (RadioGroup)findViewById(R.id.radToroPatasProfund);
        radToroPatasHueso = (RadioGroup)findViewById(R.id.radToroPatasHueso);
        radToroPatasPTraseras = (RadioGroup)findViewById(R.id.radToroPatasPTraseras);
        radToroPatasPPost = (RadioGroup)findViewById(R.id.radToroPatasPPost);

        def10 = (CheckBox)findViewById(R.id.def10);
        def11 = (CheckBox)findViewById(R.id.def11);
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

        clases = new ArrayList<String>();
        clases.add("FA");
        clases.add("GO");
        clases.add("GP");
        clases.add("VG");
        clases.add("EX");
        ArrayAdapter<String> claseAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, clases);
        claseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinToroCalifAnca = (Spinner)findViewById(R.id.spinToroCalifAnca);
        spinToroCalifFortaleza = (Spinner)findViewById(R.id.spinToroCalifFortaleza);
        spinToroCalifPatas = (Spinner)findViewById(R.id.spinToroCalifPatas);
        spinToroClase = (Spinner)findViewById(R.id.spinnerToroClase);

        spinToroCalifAnca.setAdapter(claseAdapter);
        spinToroCalifFortaleza.setAdapter(claseAdapter);
        spinToroCalifPatas.setAdapter(claseAdapter);
        spinToroClase.setAdapter(claseAdapter);

        spinToroCalifAnca.setOnItemSelectedListener(spinnerListener);
        spinToroCalifFortaleza.setOnItemSelectedListener(spinnerListener);
        spinToroCalifPatas.setOnItemSelectedListener(spinnerListener);
        spinToroClase.setOnItemSelectedListener(spinnerListener);

        txtToroCalif = (EditText)findViewById(R.id.txtToroCalif);
        txtAncaCalif = (EditText)findViewById(R.id.txtToroCalifAnca);
        txtFortalezaCalif = (EditText)findViewById(R.id.txtToroCalifFortaleza);
        txtPatasCalif = (EditText)findViewById(R.id.txtToroCalifPatas);

        txtAncaCalif.addTextChangedListener(textWatcher);
        txtToroCalif.addTextChangedListener(textWatcher);
        txtPatasCalif.addTextChangedListener(textWatcher);
        txtFortalezaCalif.addTextChangedListener(textWatcher);


        txtAncaCalif.setOnFocusChangeListener(focusChangeListener);
        txtPatasCalif.setOnFocusChangeListener(focusChangeListener);
        txtFortalezaCalif.setOnFocusChangeListener(focusChangeListener);
        txtToroCalif.setOnFocusChangeListener(focusChangeListener);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarCalificaciones();
            }
        });
        btnCancelar = (Button) findViewById(R.id.btnToroCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalificarToro.this.finish();
            }
        });

        if (toro.getFechaCalif()!=null && !toro.getFechaCalif().equals(""))establecerActual();

        txtToroCalif.clearFocus();
    }

    public String calcularClase(int puntos){
        if (puntos>=60 && puntos <=69) return "FA";
        else if (puntos>=70 && puntos <=79) return "GO";
        else if (puntos>=80 && puntos <=84) return "GP";
        else if (puntos>=85 && puntos <=89) return "VG";
        else if (puntos>=90) return "EX";
        else return "FA";
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(CalificarToro.this)
                .setTitle("Botón Atrás Presionado ")
                .setMessage("Está seguro de abandonar la vista sin guardar las calificaciones?")
                .setCancelable(true)
                .setPositiveButton("Salir / Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                        CalificarToro.this.finish();
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
        r = (RadioButton)radToroAncaInclinacion.getChildAt(toro.getC1() - 1);r.setChecked(true);
        r = (RadioButton)radToroAncaAnchura.getChildAt(toro.getC2() - 1);r.setChecked(true);
        r = (RadioButton)radToroAncaFortaleza.getChildAt(toro.getC3() - 1);r.setChecked(true);
        r = (RadioButton)radToroAncaColocacion.getChildAt(toro.getC4() - 1);r.setChecked(true);
        r = (RadioButton)radToroFortEstatura.getChildAt(toro.getC5() - 1);r.setChecked(true);
        r = (RadioButton)radToroFortCruz.getChildAt(toro.getC6() - 1);r.setChecked(true);
        r = (RadioButton)radToroFortPecho.getChildAt(toro.getC7() - 1);r.setChecked(true);
        r = (RadioButton)radToroFortProfund.getChildAt(toro.getC8() - 1);r.setChecked(true);
        r = (RadioButton)radToroFortAngul.getChildAt(toro.getC9() - 1);r.setChecked(true);
        r = (RadioButton)radToroFortTamano.getChildAt(toro.getC10() - 1);r.setChecked(true);
        r = (RadioButton)radToroPatasAngulo.getChildAt(toro.getC11() - 1);r.setChecked(true);
        r = (RadioButton)radToroPatasProfund.getChildAt(toro.getC12() - 1);r.setChecked(true);
        r = (RadioButton)radToroPatasHueso.getChildAt(toro.getC13() - 1);r.setChecked(true);
        r = (RadioButton)radToroPatasPTraseras.getChildAt(toro.getC14() - 1);r.setChecked(true);
        r = (RadioButton)radToroPatasPPost.getChildAt(toro.getC15() - 1);r.setChecked(true);

        if(toro.getDef10()>0)def10.setChecked(true);
        if(toro.getDef11()>0)def11.setChecked(true);
        if(toro.getDef30()>0)def30.setChecked(true);
        if(toro.getDef31()>0)def31.setChecked(true);
        if(toro.getDef32()>0)def32.setChecked(true);
        if(toro.getDef34()>0)def34.setChecked(true);
        if(toro.getDef35()>0)def35.setChecked(true);
        if(toro.getDef36()>0)def36.setChecked(true);
        if(toro.getDef40()>0)def40.setChecked(true);
        if(toro.getDef42()>0)def42.setChecked(true);
        if(toro.getDef43()>0)def43.setChecked(true);
        if(toro.getDef44()>0)def44.setChecked(true);
        if(toro.getDef45()>0)def45.setChecked(true);

        spinToroCalifAnca.setSelection(clases.indexOf(toro.getSs1()));
        spinToroCalifFortaleza.setSelection(clases.indexOf(toro.getSs2()));
        spinToroCalifPatas.setSelection(clases.indexOf(toro.getSs3()));
        spinToroClase.setSelection(clases.indexOf(toro.getClaseCalif()));

        txtToroCalif.setText(toro.getPuntosCalif() + "");
        txtAncaCalif.setText(toro.getSs1puntos() + "");
        txtFortalezaCalif.setText(toro.getSs2puntos() + "");
        txtPatasCalif.setText(toro.getSs3puntos() + "");
    }

    private boolean verificarCampos(){
        if(radToroAncaInclinacion.getCheckedRadioButtonId()==-1)radToroAncaInclinacion.setBackgroundColor(Color.RED);
        if(radToroAncaAnchura.getCheckedRadioButtonId()==-1)radToroAncaAnchura.setBackgroundColor(Color.RED);
        if(radToroAncaFortaleza.getCheckedRadioButtonId()==-1)radToroAncaFortaleza.setBackgroundColor(Color.RED);
        if(radToroAncaColocacion.getCheckedRadioButtonId()==-1)radToroAncaColocacion.setBackgroundColor(Color.RED);
        if(radToroFortEstatura.getCheckedRadioButtonId()==-1)radToroFortEstatura.setBackgroundColor(Color.RED);
        if(radToroFortCruz.getCheckedRadioButtonId()==-1)radToroFortCruz.setBackgroundColor(Color.RED);
        if(radToroFortPecho.getCheckedRadioButtonId()==-1)radToroFortPecho.setBackgroundColor(Color.RED);
        if(radToroFortProfund.getCheckedRadioButtonId()==-1)radToroFortProfund.setBackgroundColor(Color.RED);
        if(radToroFortAngul.getCheckedRadioButtonId()==-1)radToroFortAngul.setBackgroundColor(Color.RED);
        if(radToroFortTamano.getCheckedRadioButtonId()==-1)radToroFortTamano.setBackgroundColor(Color.RED);
        if(radToroPatasAngulo.getCheckedRadioButtonId()==-1)radToroPatasAngulo.setBackgroundColor(Color.RED);
        if(radToroPatasProfund.getCheckedRadioButtonId()==-1)radToroPatasProfund.setBackgroundColor(Color.RED);
        if(radToroPatasHueso.getCheckedRadioButtonId()==-1)radToroPatasHueso.setBackgroundColor(Color.RED);
        if(radToroPatasPTraseras.getCheckedRadioButtonId()==-1)radToroPatasPTraseras.setBackgroundColor(Color.RED);
        if(radToroPatasPPost.getCheckedRadioButtonId()==-1)radToroPatasPPost.setBackgroundColor(Color.RED);

        if(txtAncaCalif.getText().toString().isEmpty())txtAncaCalif.setBackgroundColor(Color.RED);
        if(txtPatasCalif.getText().toString().isEmpty())txtPatasCalif.setBackgroundColor(Color.RED);
        if(txtFortalezaCalif.getText().toString().isEmpty())txtFortalezaCalif.setBackgroundColor(Color.RED);
        
        if(
            radToroAncaInclinacion.getCheckedRadioButtonId()==-1 ||
            radToroAncaAnchura.getCheckedRadioButtonId()==-1 ||
            radToroAncaFortaleza.getCheckedRadioButtonId()==-1 ||
            radToroAncaColocacion.getCheckedRadioButtonId()==-1 ||
            radToroFortEstatura.getCheckedRadioButtonId()==-1 ||
            radToroFortCruz.getCheckedRadioButtonId()==-1 ||
            radToroFortPecho.getCheckedRadioButtonId()==-1 ||
            radToroFortProfund.getCheckedRadioButtonId()==-1 ||
            radToroFortAngul.getCheckedRadioButtonId()==-1 ||
            radToroFortTamano.getCheckedRadioButtonId()==-1 ||
            radToroPatasAngulo.getCheckedRadioButtonId()==-1 ||
            radToroPatasProfund.getCheckedRadioButtonId()==-1 ||
            radToroPatasHueso.getCheckedRadioButtonId()==-1 ||
            radToroPatasPTraseras.getCheckedRadioButtonId()==-1 ||
            radToroPatasPPost.getCheckedRadioButtonId()==-1 ||
            txtAncaCalif.getText().toString().isEmpty() ||
            txtPatasCalif.getText().toString().isEmpty() ||
            txtFortalezaCalif.getText().toString().isEmpty()
            ){
            radToroAncaInclinacion.setOnCheckedChangeListener(radListener);
            radToroAncaAnchura.setOnCheckedChangeListener(radListener);
            radToroAncaFortaleza.setOnCheckedChangeListener(radListener);
            radToroAncaColocacion.setOnCheckedChangeListener(radListener);
            radToroFortEstatura.setOnCheckedChangeListener(radListener);
            radToroFortCruz.setOnCheckedChangeListener(radListener);
            radToroFortPecho.setOnCheckedChangeListener(radListener);
            radToroFortProfund.setOnCheckedChangeListener(radListener);
            radToroFortAngul.setOnCheckedChangeListener(radListener);
            radToroFortTamano.setOnCheckedChangeListener(radListener);
            radToroPatasAngulo.setOnCheckedChangeListener(radListener);
            radToroPatasProfund.setOnCheckedChangeListener(radListener);
            radToroPatasHueso.setOnCheckedChangeListener(radListener);
            radToroPatasPTraseras.setOnCheckedChangeListener(radListener);
            radToroPatasPPost.setOnCheckedChangeListener(radListener);
            return false;
        }
        else return true;
    } 
    
    
    private boolean guardarVariable(){
        if (verificarCampos()) {

            int selected;
            RadioButton radio;

            ///*** ANCA ***///
            //c3 - Lomo
            selected = radToroAncaFortaleza.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            toro.setC3(Integer.parseInt(radio.getText().toString()));
            //c1 - Punta
            selected = radToroAncaInclinacion.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            toro.setC1(Integer.parseInt(radio.getText().toString()));
            //c2 - Anchura
            selected = radToroAncaAnchura.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            toro.setC2(Integer.parseInt(radio.getText().toString()));
            //c4 - Colocacion
            selected = radToroAncaColocacion.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            toro.setC4(Integer.parseInt(radio.getText().toString()));


            ///*** FORTALEZA LECHERA ***///
            //c5 - Estatura
            selected = radToroFortEstatura.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            toro.setC5(Integer.parseInt(radio.getText().toString()));
            //c6 - AltCruz
            selected = radToroFortCruz.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            toro.setC6(Integer.parseInt(radio.getText().toString()));
            //c10 - Tamaño
            selected = radToroFortTamano.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            toro.setC10(Integer.parseInt(radio.getText().toString()));
            //c7 - PechoAncho
            selected = radToroFortPecho.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            toro.setC7(Integer.parseInt(radio.getText().toString()));
            //c8 - Profundidad
            selected = radToroFortProfund.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            toro.setC8(Integer.parseInt(radio.getText().toString()));
            //c9 - Angularidad
            selected = radToroFortAngul.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            toro.setC9(Integer.parseInt(radio.getText().toString()));

            ///*** PATAS Y PEZUÑAS ***///
            //c11 - Angulo
            selected = radToroPatasAngulo.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            toro.setC11(Integer.parseInt(radio.getText().toString()));
            //c12 - ProfTalón
            selected = radToroPatasProfund.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            toro.setC12(Integer.parseInt(radio.getText().toString()));
            //c13 - calHuesos
            selected = radToroPatasHueso.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            toro.setC13(Integer.parseInt(radio.getText().toString()));
            //c14 - Aplomo
            selected = radToroPatasPTraseras.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            toro.setC14(Integer.parseInt(radio.getText().toString()));
            //c15 - VPosterior
            selected = radToroPatasPPost.getCheckedRadioButtonId();
            radio = (RadioButton) findViewById(selected);
            toro.setC15(Integer.parseInt(radio.getText().toString()));


            // DEFECTOS
            if (def10.isChecked()) toro.setDef10(1);
            else toro.setDef10(0);
            if (def11.isChecked()) toro.setDef11(1);
            else toro.setDef11(0);
            if (def30.isChecked()) toro.setDef30(1);
            else toro.setDef30(0);
            if (def31.isChecked()) toro.setDef31(1);
            else toro.setDef31(0);
            if (def32.isChecked()) toro.setDef32(1);
            else toro.setDef32(0);
            if (def34.isChecked()) toro.setDef34(1);
            else toro.setDef34(0);
            if (def35.isChecked()) toro.setDef35(1);
            else toro.setDef35(0);
            if (def36.isChecked()) toro.setDef36(1);
            else toro.setDef36(0);
            if (def40.isChecked()) toro.setDef40(1);
            else toro.setDef40(0);
            if (def42.isChecked()) toro.setDef42(1);
            else toro.setDef42(0);
            if (def43.isChecked()) toro.setDef43(1);
            else toro.setDef43(0);
            if (def44.isChecked()) toro.setDef44(1);
            else toro.setDef44(0);
            if (def45.isChecked()) toro.setDef45(1);
            else toro.setDef45(0);

            toro.setSs2(spinToroCalifFortaleza.getSelectedItem().toString());
            toro.setSs1(spinToroCalifAnca.getSelectedItem().toString());
            toro.setSs3(spinToroCalifPatas.getSelectedItem().toString());

            if (!txtFortalezaCalif.getText().toString().isEmpty())
                toro.setSs2puntos(Integer.parseInt(txtFortalezaCalif.getText().toString()));
            else toro.setSs2puntos(0);
            if (!txtAncaCalif.getText().toString().isEmpty())
                toro.setSs1puntos(Integer.parseInt(txtAncaCalif.getText().toString()));
            else toro.setSs1puntos(0);
            if (!txtPatasCalif.getText().toString().isEmpty())
                toro.setSs3puntos(Integer.parseInt(txtPatasCalif.getText().toString()));
            else toro.setSs3puntos(0);
            return true;
        }
        else{
            Toast.makeText(CalificarToro.this, "No se han completado todos los campos de calificación", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void guardarCalificaciones(){
        if (txtToroCalif.getText().toString().isEmpty() || spinToroClase.getSelectedItem()==null){
            Toast.makeText(this,"Confirme las calificaciones finales e intente de nuevo",Toast.LENGTH_LONG).show();
        }
        else {
            new AlertDialog.Builder(this)
                    .setTitle("Confirmar")
                    .setMessage("Está seguro de guardar las calificaciones?\nAre you sure to save the qualifications?")
                    .setCancelable(true)
                    .setPositiveButton("SI / YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (guardarVariable()) {
                                toro.setPuntosCalif(Integer.parseInt(txtToroCalif.getText().toString()));
                                toro.setClaseCalif(spinToroClase.getSelectedItem().toString());
                                toro.setFechaCalif(sql.fechaActual());
                                sql.actualizarCalifToro(toro);
                                Toast.makeText(CalificarToro.this, "Calificaciones de toro guardadas.\nQualifications were saved.", Toast.LENGTH_LONG).show();
                                new AlertDialog.Builder(CalificarToro.this)
                                        .setTitle("Calificaciones Guardadas")
                                        .setMessage("Calificaciones de toro guardadas.\n" +
                                                "  Qualifications were saved\n\n" +
                                                "Desea permanecer en la vista o salir?\n" +
                                                "Do you want to stay in this view or exit?")
                                        .setCancelable(true)
                                        .setPositiveButton("Salir / Exit", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                CalificarToro.this.finish();
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
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
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
