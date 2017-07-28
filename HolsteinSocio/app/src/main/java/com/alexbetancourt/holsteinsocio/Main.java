package com.alexbetancourt.holsteinsocio;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;


public class Main extends AppCompatActivity {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private ProgressBar mRegistrationProgressBar;
    private TextView mInformationTextView;
    SharedPreferences sharedPreferences;
    Button btnRegistrar, btnDescargar, btnReportes, btnConfig;
    private ProgressDialog pDialog;
    api API;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        API = new api(this);
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
        else {
            pDialog = new ProgressDialog(Main.this);
            pDialog.setMessage("Verificando servicios de Notificación...");
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setCancelable(false);
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
                            btnReportes.setEnabled(true);
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
                Intent intent = new Intent(this, RegistrationIntentService.class);
                startService(intent);
            }
        }
    }

    private void init(){
        btnRegistrar = (Button)findViewById(R.id.btnRegistrar);
        btnDescargar = (Button)findViewById(R.id.btnMainDescargar);
        btnReportes = (Button)findViewById(R.id.btnMainReportes);
        btnConfig = (Button)findViewById(R.id.btnMainConfig);
        btnDescargar.setEnabled(false);
        btnReportes.setEnabled(false);
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
        btnReportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Main.this, Reportes.class);
                startActivity(i);
            }
        });
        btnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        API = new api(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));

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
                Toast.makeText(this,"Es necesario configurar el usuario y contraseña para continuar.",Toast.LENGTH_LONG).show();
            }
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
                    Intent intent = new Intent(this, RegistrationIntentService.class);
                    startService(intent);
                }
            }
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
        else if(id == R.id.action_register){
            registrar();
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
                pDialog.setCancelable(false);
                pDialog.setIcon(R.drawable.logo180x180);
                pDialog.show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                String usuario = sharedPreferences.getString("usuario",null);
                String clave = sharedPreferences.getString("clave",null);
                String token = sharedPreferences.getString("token",null);
                String modelo = android.os.Build.MODEL;
                if (usuario!=null && clave!=null && token!=null) {
                    try {
                        Object respuesta = API.registrar(usuario, clave, token, modelo,0);
                        if (respuesta instanceof String)
                            return (String)respuesta;
                        else {
                            sharedPreferences.edit().putBoolean(QuickstartPreferences.REGISTRADO, true).apply();
                            return "Dispositivo Registrado con Holstein";
                        }
                    } catch (com.goebl.david.WebbException ex){
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
                    btnReportes.setEnabled(true);
                    mInformationTextView.setText("Registrado con Holstein Mexico");
                }
                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
            }
        }.execute();

    }

    private class BuscarTask extends AsyncTask<Void, String, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                Object generales = API.obtenerEstGenerales();
                if (generales instanceof String)
                    return (String)generales;
                else
                {
                    Object reportes = API.obtenerEstadisticos(sharedPreferences.getString("usuario", ""));
                    if (reportes instanceof String)
                        return (String)reportes;
                    else if (reportes instanceof Integer) {
                        if ((Integer)reportes==1)
                            return reportes + " nuevo estadístico de su Hato recibido.";
                        else
                            return reportes + " nuevos estadísticos recibidos.";
                    }
                    else
                        return "Se han actualizado los reportes.";
                }
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
            pDialog.setMessage("Buscando Reportes y Descargando");
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setCancelable(true);
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
    }

}