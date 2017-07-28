package com.abetancourt.holsteincalificador;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.goebl.david.WebbException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.util.ArrayList;



public class Main extends AppCompatActivity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private ProgressBar mRegistrationProgressBar;
    private TextView mInformationTextView;
    SharedPreferences sharedPreferences;
    Button btnRegistrar, btnDescargar, btnEnviar, btnHatos, btnConfig;
    private ProgressDialog pDialog;
    api API;
    SQLite sql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        API = new api(this);
        sql = new SQLite(this);
        init();
        listeners();
        sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        mRegistrationProgressBar = (ProgressBar) findViewById(R.id.registrationProgressBar);
        mRegistrationProgressBar.setVisibility(ProgressBar.GONE);
        if (sharedPreferences.getString("usuario","").equals("")){
            Intent i = new Intent(this, SettingsActivity.class);
            startActivityForResult(i, 99);
        }
        /*
        else {
            pDialog = new ProgressDialog(Main.this);
            pDialog.setMessage("Verificando servicios de Notificación...");
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setCancelable(true);
            pDialog.setIcon(R.drawable.logo180x180);
            pDialog.show();
            mRegistrationBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    //mRegistrationProgressBar.setVisibility(ProgressBar.GONE);
                    pDialog.dismiss();
                    sharedPreferences =
                            PreferenceManager.getDefaultSharedPreferences(context);
                    boolean sentToken = sharedPreferences
                            .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                    if (sentToken) {
                        mInformationTextView.setText(getString(R.string.gcm_send_message));
                        if (sharedPreferences.getBoolean(QuickstartPreferences.REGISTRADO,false)) {
                            btnRegistrar.setVisibility(View.GONE);
                            btnDescargar.setEnabled(true);
                            btnHatos.setEnabled(true);
                            btnEnviar.setEnabled(true);
                            mInformationTextView.setText("Registrado con Holstein Mexico");
                        }
                    } else {
                        mInformationTextView.setText(getString(R.string.token_error_message));
                    }
                }
            };
            mInformationTextView = (TextView) findViewById(R.id.informationTextView);

            if (checkPlayServices()) {
                // Start IntentService to register this application with GCM.
                Intent intent = new Intent(this, HCRegistrationIntentService.class);
                startService(intent);
            }
        }*/

    }

    private void init(){
        btnRegistrar = (Button)findViewById(R.id.btnRegistrar);
        btnDescargar = (Button)findViewById(R.id.btnMainDescargar);
        btnHatos = (Button)findViewById(R.id.btnMainHatos);
        btnConfig = (Button)findViewById(R.id.btnMainConfig);
        btnEnviar = (Button)findViewById(R.id.btnMainEnviar);
        /*
        btnDescargar.setEnabled(false);
        btnHatos.setEnabled(false);
        btnEnviar.setEnabled(false);
        */
        btnRegistrar.setVisibility(View.GONE);
    }

    private void listeners(){
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrar();
            }
        });
        btnDescargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BuscarTask().execute();
            }
        });
        btnHatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Main.this, Hatos.class);
                startActivity(i);
            }
        });
        btnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.getString("usuario", "").equals("")) {
                    //mRegistrationProgressBar.setVisibility(ProgressBar.VISIBLE);
                    Intent i = new Intent(Main.this, SettingsActivity.class);
                    startActivityForResult(i, 99);
                } else {
                    Intent i = new Intent(Main.this, SettingsActivity.class);
                    startActivity(i);
                }
            }
        });
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarVacasYToros();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        API = new api(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        /*LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
        */
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99){
            if (sharedPreferences.getString("usuario","").equals("")){
                //mRegistrationProgressBar.setVisibility(ProgressBar.INVISIBLE);
                Toast.makeText(this, "Es necesario configurar el usuario y contraseña para continuar.", Toast.LENGTH_LONG).show();
            }
            /*
            else {
                API = new api(this);
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

                pDialog = new ProgressDialog(Main.this);
                pDialog.setMessage("Verificando servicio de Notificación...");
                pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pDialog.setCancelable(false);
                pDialog.setIcon(R.drawable.logo180x180);
                pDialog.show();
                mRegistrationBroadcastReceiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        pDialog.dismiss();
                        //mRegistrationProgressBar.setVisibility(ProgressBar.GONE);
                        sharedPreferences =
                                PreferenceManager.getDefaultSharedPreferences(context);
                        boolean sentToken = sharedPreferences
                                .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                        if (sentToken) {
                            mInformationTextView.setText(getString(R.string.gcm_send_message));
                            Toast.makeText(Main.this,"Ahora puede registrarse con holstein.",Toast.LENGTH_LONG).show();
                        } else {
                            mInformationTextView.setText(getString(R.string.token_error_message));
                        }
                    }
                };
                mInformationTextView = (TextView) findViewById(R.id.informationTextView);

                if (checkPlayServices()) {
                    // Start IntentService to register this application with GCM.
                    Intent intent = new Intent(this, HCRegistrationIntentService.class);
                    startService(intent);
                }
            }*/
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            if (sharedPreferences.getString("usuario","").equals("")){
                //mRegistrationProgressBar.setVisibility(ProgressBar.VISIBLE);
                Intent i = new Intent(Main.this, SettingsActivity.class);
                startActivityForResult(i, 99);
            }
            else {
                Intent i = new Intent(Main.this, SettingsActivity.class);
                startActivity(i);
            }
        }
        else if(id == R.id.action_testpdf){
            PDF pdf = new PDF();
        }

        return super.onOptionsItemSelected(item);
    }


    private void registrar(){
        new AsyncTask<Void,Void,String>(){

            @Override
            protected void onPreExecute() {
                pDialog = new ProgressDialog(Main.this);
                pDialog.setMessage("Registrando con Holstein...");
                pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pDialog.setCancelable(true);
                pDialog.setIcon(R.drawable.logo180x180);
                pDialog.show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                String usuario = sharedPreferences.getString("usuario",null);
                String clave = sharedPreferences.getString("clave",null);
                String token = sharedPreferences.getString("token",null);
                String modelo = Build.MODEL;
                if (usuario!=null && clave!=null && token!=null) {
                    try {
                        Object respuesta = API.registrar(usuario, clave, token, modelo,0);
                        if (respuesta instanceof String)
                            return (String)respuesta;
                        else {
                            sharedPreferences.edit().putBoolean(QuickstartPreferences.REGISTRADO, true).apply();
                            return "Dispositivo Registrado con Holstein";
                        }
                    } catch (WebbException ex){
                        return "No es posible conectar con los servidores de Holstein, intente nuevamente.";
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        return e.getMessage();
                    }
                }else{
                    return "No se puede registrar con Holstein verifique sus configuraciones";
                }
            }

            @Override
            protected void onPostExecute(String result) {
                pDialog.dismiss();
                if (result.equals("Dispositivo Registrado con Holstein")) {
                    btnRegistrar.setVisibility(View.GONE);
                    btnDescargar.setEnabled(true);
                    btnHatos.setEnabled(true);
                    btnEnviar.setEnabled(true);
                    mInformationTextView.setText("Registrado con Holstein Mexico");
                }
                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                Toast.makeText(Main.this, "Cancelado el proceso de registro con el servidor.", Toast.LENGTH_SHORT).show();
            }
        }.execute();

    }

    private void enviarVacasYToros(){
        new AsyncTask<Void,Void,String>(){

            @Override
            protected void onPreExecute() {
                pDialog = new ProgressDialog(Main.this);
                pDialog.setMessage("Enviando Calificaciones a Holstein...");
                pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pDialog.setCancelable(false);
                pDialog.setIcon(R.drawable.logo180x180);
                pDialog.show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                ArrayList<Vaca> vacas = sql.obtenerVacasParaEnvio();
                ArrayList<Toro> toros = sql.obtenerTorosParaEnvio();
                if (vacas == null && toros == null) {
                    return "No hay datos para envío";
                } else {
                    try {
                        String ret = "";
                        if (vacas!=null) {
                            Object envio = API.enviarVacas(vacas);
                            if (envio instanceof String) {
                                ret += (String) envio;
                            } else if (envio instanceof Boolean && (Boolean) envio) {
                                ret += "Enviadas " + vacas.size() + " vacas.";
                            Object sync = sql.sincronizadoVacas(vacas);
                            if (sync instanceof String) ret+= (String)sync;
                            else if (sync instanceof Exception)ret += ((Exception) sync).getMessage();
                            } else {
                                ret += "No hay vacas para envío";
                            }
                        }
                        if (toros!=null) {
                            Object envio2 = API.enviarToros(toros);
                            if (envio2 instanceof String) {
                                ret += (String) envio2;
                            } else if (envio2 instanceof Boolean && (Boolean) envio2) {
                                ret += "Enviados " + toros.size() + " toros.";
                            Object sync = sql.sincronizadoToros(toros);
                            if (sync instanceof String) ret+= (String)sync;
                            else if (sync instanceof Exception)ret += ((Exception) sync).getMessage();
                            } else {
                                ret += "No hay toros para envío";
                            }
                        }
                        return ret;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return e.getMessage();
                    }
                }
            }

            @Override
            protected void onPostExecute(String result) {
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
            }
        }.execute();
    }

    public class BuscarTask extends AsyncTask<Void, String, String> {


        public void  publish(String s){
            publishProgress(s);
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {

                String result = "";
                publish("Buscando Vacas...");
                Object vacas = API.obtenerVacas(sharedPreferences.getString("usuario", ""), this);
                if (vacas instanceof String)
                    result += (String)vacas;
                else if (vacas instanceof Integer)
                    result += "Se han descargado "+vacas+" Vacas.";
                else
                    result += "No se han descargado vacas.";
                result += "\n";
                publish("Buscando Toros...");
                Object toros = API.obtenerToros(sharedPreferences.getString("usuario", ""), this);
                if (toros instanceof String)
                    result += (String)toros;
                else if (toros instanceof Integer)
                    result += "Se han descargado "+toros+" Toros.";
                else
                    result += "No se han descargado toros.";
                result += "\n";
                publish("Buscando Socios...");
                Object socios = API.obtenerSocios(this);
                if (socios instanceof String)
                    result += (String)socios;
                else if (socios instanceof Integer)
                    result += "Se han descargado "+socios+" Socios.";
                else
                    result += "No se han descargado socios.";
                return result;
            }catch (com.goebl.david.WebbException to){
                return "No es posible conectar con los servidores de Holstein, intente nuevamente.";
            }catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(Main.this);
            pDialog.setMessage("Buscando Vacas y Toros para Calificar...");
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setCancelable(false);
            pDialog.setIcon(R.drawable.logo180x180);
            pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    BuscarTask.this.cancel(true);
                    pDialog.dismiss();
                }
            });
            pDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.dismiss();
            Toast.makeText(Main.this,s,Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            pDialog.setMessage(values[0]);
        }
    }
}
