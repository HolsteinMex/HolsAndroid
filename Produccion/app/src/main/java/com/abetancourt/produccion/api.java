package com.abetancourt.produccion;

/**
 * Created by Alex on 25/07/2015.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.Build;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;

import com.goebl.david.Response;
import com.goebl.david.Webb;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

/**
 * Created by alex on 16/06/15.
 */
public class api {

    private static String IPSQL = "sqlip"; // IP DEL SERVIDOR SQL (DEBE SER IP, NO UN NOMBRE, YA QUE
    // NO SE PUEDE RESOLVER UN HOSTNAME)
    private static final String SUSRID = "susrid"; //Session User Id
    private static final String SINST = "sinst"; //Installer ID


    private Context context;
    ProgressDialog pDialog;
    static String response = null;
    public final static int GET = 1;
    public final static int POST = 2;
    private SharedPreferences prefs;
    private String serverIP;
    private String usuario_sap, id_instalador,imei;
    int androidAPI;
    private String androidCodename;
    TelephonyManager telephonyManager;

    public api(Context c) {
        androidAPI = Build.VERSION.SDK_INT;
        androidCodename = Build.VERSION.CODENAME;
        this.context = c;
        telephonyManager=(TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
        prefs = PreferenceManager.getDefaultSharedPreferences(c);
        serverIP = prefs.getString("servidor","http://holsteinsis.ddns.net:8000");
        serverIP += "/API/";
    }


    public Object obtenerVacas(String usuario, Main.BuscarTask task) throws SocketTimeoutException,
            ConnectException, JSONException, ParseException {
        Webb webb = Webb.create();
        SQLite sq = new SQLite(context);
        String ids = "0";
        try {
            ids += sq.vacasIds();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Response<JSONArray> response = webb.post(serverIP + "produccion/")
                .param("usuario", usuario)
                .param("ids", ids)
                .asJsonArray();
        if (response.getStatusCode() == 200) {
            try {
                sq.AbrirBD();
                sq.nBD.beginTransaction();
                // Getting JSON Array node
                JSONArray reportes = response.getBody();
                if (reportes.length() > 0) {
                    for (int i = 0; i < reportes.length(); i++) {
                        task.publish("Guardando "+(i+1)+" de "+reportes.length());
                        JSONObject v = reportes.getJSONObject(i);
                        Vaca est = new Vaca(
                                v.getInt(sq.ID),
                                v.getString(sq.V_HATO),
                                v.getString(sq.V_NVACA),
                                v.getInt(sq.V_CURV),
                                v.getString(sq.V_FECHAEST),
                                v.getString(sq.V_CODEST),
                                v.getString(sq.V_FECHA)
                        );
                        Object insert = sq.insertarVaca(est);
                        if (insert instanceof String)
                            return insert;
                    }
                    sq.nBD.setTransactionSuccessful();
                    return reportes.length();
                } else return null;
            } catch (Exception e) {
                throw e;
            } finally {
                sq.nBD.endTransaction();
                sq.CerrarBD();
            }
        } else {
            if (response.getStatusCode() == 400)
                return response.getErrorBody();
            else
                return response.getResponseMessage();
        }
    }

    public Object enviarVacas(ArrayList<Vaca> vacas) throws Exception {
        JSONArray json = new JSONArray();
        for (Vaca v : vacas){
            Gson gson = new Gson();
            String j = gson.toJson(v);
            JSONObject jsonObject = new JSONObject(j);
            json.put(jsonObject);
        }
        Webb webb = Webb.create();

        Response<Void> result = webb.post(serverIP+"enviar_vacasproduccion/")
                .body(json)
                .header(Webb.HDR_CONTENT_TYPE, "application/json")
                .connectTimeout(10000)
                .readTimeout(10000)
                .asVoid();
        if (result.getStatusCode()==404) {
                return "Imposible conectar con los servidores de Holstein, intente nuevamente.";

        }
        else if (result.getStatusCode()!=200)
            return result.getErrorBody();
        else return true;
    }


}