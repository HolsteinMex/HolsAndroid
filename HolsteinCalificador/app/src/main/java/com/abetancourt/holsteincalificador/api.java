package com.abetancourt.holsteincalificador;

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
        telephonyManager=( TelephonyManager ) c.getSystemService(Context.TELEPHONY_SERVICE);
        prefs = PreferenceManager.getDefaultSharedPreferences(c);
        serverIP = prefs.getString("servidor","http://holsteinsis.ddns.net:8000");
        serverIP += "/API/";
    }



    public Object registrar(String usuario, String clave, String reg, String modelo,int intento) throws Exception {
        Webb webb = Webb.create();

        Response<Void> result = webb.post(serverIP+"registrar/")
                .param("usuario",usuario.trim())
                .param("clave",clave.trim())
                .param("reg",reg.trim())
                .param("modelo",modelo)
                .compress()
                .connectTimeout(10000)
                .readTimeout(10000)
                .asVoid();
        if (result.getStatusCode()==404) {
            if (intento < 3) {
                return registrar(usuario, clave, reg, modelo, intento + 1);
            } else {
                return "Imposible conectar con los servidores de Holstein, intente nuevamente.";
            }
        }
        else if (result.getStatusCode()!=200)
            return result.getErrorBody();
        else return true;
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
        Response<JSONArray> response = webb.post(serverIP + "calificaciones/")
                .param("usuario", usuario)
                .param("ids", ids)
                .asJsonArray();
        if (response.getStatusCode() == 200) {
            try {
                // Getting JSON Array node
                JSONArray reportes = response.getBody();
                if (reportes.length() > 0) {
                    sq.AbrirBD();
                    sq.iniciarTransaccion();
                    for (int i = 0; i < reportes.length(); i++) {
                        task.publish("Guardando "+(i+1)+" de "+reportes.length()+" Vacas recibidas");
                        JSONObject v = reportes.getJSONObject(i);
                        Vaca est = new Vaca(
                                v.getInt(sq.ID),
                                v.getString(sq.HATO),
                                v.getString(sq.FECHA),
                                v.getInt(sq.V_CURV),
                                v.getString(sq.V_NOCP),
                                v.getString(sq.NOREG),
                                v.getString(sq.REGP),
                                v.getString(sq.REGM),
                                v.getString(sq.FECHANAC),
                                v.getInt(sq.V_NOLAC),
                                v.getString(sq.V_FECHAPAR),
                                v.getInt(sq.CALIFANT),
                                v.getString(sq.CLASEANT),
                                v.getString(sq.V_TIPOPAR),
                                v.getInt(sq.V_LACNOM),
                                v.getInt(sq.V_MESESAB)
                        );
                        est.setFechaCalifAnt(v.getString(sq.FECHCALANT));
                        Object insert = sq.bulkInsertarCalifVaca(est);
                        if (insert instanceof String)
                            return insert;
                    }
                    sq.finalizarTransaccion();
                    sq.CerrarBD();
                    return reportes.length();
                } else return null;
            } catch (Exception e) {
                return e.getMessage();
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

        Response<JSONArray> result = webb.post(serverIP+"enviar_vacas/")
                .body(json)
                .header(Webb.HDR_CONTENT_TYPE, "application/json")
                .connectTimeout(300000)
                .readTimeout(300000)
                .asJsonArray();
        if (result.getStatusCode()==404) {
                return "Imposible conectar con los servidores de Holstein, intente nuevamente.";

        }
        else if (result.getStatusCode()!=200)
            return result.getErrorBody();
        else return true;
    }

    public Object enviarToros(ArrayList<Toro> toros) throws Exception {
        JSONArray json = new JSONArray();
        for (Toro v : toros){
            Gson gson = new Gson();
            String j = gson.toJson(v);
            JSONObject jsonObject = new JSONObject(j);
            json.put(jsonObject);
        }
        Webb webb = Webb.create();

        Response<Void> result = webb.post(serverIP+"enviar_toros/")
                .body(json)
                .header(Webb.HDR_CONTENT_TYPE, "application/json")
                .connectTimeout(300000)
                .readTimeout(300000)
                .asVoid();
        if (result.getStatusCode()==404) {
            return "Imposible conectar con los servidores de Holstein, intente nuevamente.";

        }
        else if (result.getStatusCode()!=200)
            return result.getErrorBody();
        else return true;
    }

    public Object obtenerToros(String usuario, Main.BuscarTask task ) throws SocketTimeoutException,
            ConnectException, JSONException, ParseException {
        Webb webb = Webb.create();
        SQLite sq = new SQLite(context);
        String ids = "0";
        try {
            ids += sq.torosIds();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Response<JSONArray> response = webb.post(serverIP + "calificacionestoro/")
                .param("usuario", usuario)
                .param("ids", ids)
                .asJsonArray();
        if (response.getStatusCode() == 200) {
            try {
                // Getting JSON Array node
                JSONArray reportes = response.getBody();
                if (reportes.length() > 0) {
                    sq.AbrirBD();
                    sq.iniciarTransaccion();
                    for (int i = 0; i < reportes.length(); i++) {
                        task.publish("Guardando "+(i+1)+" de "+reportes.length()+" Toros recibidos");
                        JSONObject v = reportes.getJSONObject(i);

                        Toro est = new Toro(
                                v.getInt(sq.ID),
                                v.getString(sq.HATO),
                                v.getString(sq.FECHA),
                                v.getString(sq.NOREG),
                                v.getString(sq.REGP),
                                v.getString(sq.REGM),
                                v.getString(sq.FECHANAC),
                                v.getString(sq.CALIFANT),
                                v.getString(sq.CLASEANT),
                                v.getString(sq.T_NOMB)
                        );
                        Object insert = sq.bulkInsertarCalifToro(est);
                        if (insert instanceof String)
                            return insert;
                    }
                    sq.finalizarTransaccion();
                    sq.CerrarBD();
                    return reportes.length();
                } else return null;
            } catch (Exception e) {
                return e.getMessage();
            }
        } else {
            if (response.getStatusCode() == 400)
                return response.getErrorBody();
            else
                return response.getResponseMessage();
        }
    }

    public Object obtenerSocios(Main.BuscarTask task) throws SocketTimeoutException,
            ConnectException, JSONException, ParseException {
        Webb webb = Webb.create();
        SQLite sq = new SQLite(context);
        Response<JSONArray> response = webb.post(serverIP + "socios/")
                .asJsonArray();
        if (response.getStatusCode() == 200) {
            try {
                // Getting JSON Array node
                JSONArray reportes = response.getBody();
                if (reportes.length() > 0) {
                    sq.AbrirBD();
                    sq.iniciarTransaccion();
                    sq.limpiaSocios();
                    for (int i = 0; i < reportes.length(); i++) {
                        task.publish("Guardando "+(i+1)+" de "+reportes.length()+" Socios recibidos");
                        JSONObject v = reportes.getJSONObject(i);
                        Socio s = new Socio(
                                v.getInt(sq.ID),
                                v.getString(sq.S_NOHATO),
                                v.getString(sq.S_NOM)
                                );
                        Object insert = sq.bulkInsertarSocio(s);
                        if (insert instanceof String)
                            return insert;

                    }
                    sq.finalizarTransaccion();
                    sq.CerrarBD();
                    return reportes.length();
                } else return null;
            } catch (Exception e) {
                return e.getMessage();
            }
        } else {
            if (response.getStatusCode() == 400)
                return response.getErrorBody();
            else
                return response.getResponseMessage();
        }
    }

/*


    public Object obtenerEstGenerales() throws SocketTimeoutException,
            ConnectException, JSONException, ParseException  {
        Webb webb = Webb.create();
        SQLite sq = new SQLite(context);
        Response<JSONArray> response = webb.get(serverIP + "estgenerales/")
                .asJsonArray();
        if (response.getStatusCode()==200) {
            try {
                // Getting JSON Array node
                JSONArray reportes = response.getBody();
                if (reportes.length() > 0) {
                    sq.limpiaEstGeneral();
                    ArrayList<EstGeneral> insertar = new ArrayList<EstGeneral>();
                    for (int i = 0; i < reportes.length(); i++) {
                        JSONObject r = reportes.getJSONObject(i);
                        EstGeneral est = new EstGeneral(
                                r.getInt(sq.ID),
                                r.getString(sq.G_TIPO),
                                r.getString(sq.G_FPROC),
                                r.getInt(sq.E_VACAHATO),
                                r.getDouble(sq.E_LECHEKGH),
                                r.getDouble(sq.E_LECHEEM),
                                r.getDouble(sq.E_DIASLECHE),
                                r.getDouble(sq.E_PVACASC),
                                r.getDouble(sq.E_DIASP),
                                r.getDouble(sq.E_LECHEP),
                                r.getInt(sq.E_PDESH),
                                r.getDouble(sq.E_EDADMM),
                                r.getInt(sq.E_DIAS1S),
                                r.getInt(sq.E_PFERTIL1),
                                r.getDouble(sq.E_INTERS),
                                r.getDouble(sq.E_SERVX),
                                r.getInt(sq.E_DIASAB),
                                r.getDouble(sq.E_INTERPAR),
                                r.getDouble(sq.E_PABORT),
                                r.getDouble(sq.E_PGRASA),
                                r.getDouble(sq.E_PPROT),
                                r.getDouble(sq.E_PSOLID),
                                r.getDouble(sq.E_NITROG),
                                r.getInt(sq.E_CCMIL)
                        );
                        insertar.add(est);
                    }
                    Object insert = sq.insertarEstGeneral(insertar);
                    if (insert instanceof String)
                        return insert;
                    return reportes.length();
                }
                else return null;
            } catch (Exception e) {
                throw e;
            }
        } else {
            if (response.getStatusCode()==400)
                return response.getErrorBody();
            else
                return response.getResponseMessage();
        }
    }

/*
    public Object obtenerCatalogoMateriales(){
        Webb webb = Webb.create();
        Response<JSONArray> response = webb.get(serverIP+"catalogo_materiales/")
                .asJsonArray();
        if (response.getStatusCode()==200) {
            mexikey.clases.SQLite sqlite = new mexikey.clases.SQLite(context);
            try {
                // Getting JSON Array node
                JSONArray materiales = response.getBody();
                sqlite.AbrirBD();
                if (materiales.length() > 0) {
                    sqlite.limpiaCat?logoMateriales();
                    for (int i = 0; i < materiales.length(); i++) {
                        JSONObject m = materiales.getJSONObject(i);
                        Material mat = new Material(m.getInt("id"),m.getString("no_parte"),
                                m.getString("no_parte_sap"),m.getString("descripcion"),m.getString("unidad"));
                        sqlite.insertarMaterial(mat);
                    }
                    return true;
                }
                else return null;
            } catch (Exception e) {
                return e.getMessage();
            }finally{sqlite.CerrarBD();}
        } else {
            if (response.getStatusCode()==400)
                return response.getBody().toString();
            else
                return response.getResponseMessage();
        }
    }

    public Object verificarNuevasOrdenes(){
        Webb webb = Webb.create();
        Response<Void> response = webb.get(serverIP+"verificar_ordenes/?"+url_complemento)
                .asVoid();
        if (response.getStatusCode()==200) {
            try {
                // Getting JSON Array node
                String body = response.getBody().toString();
                return Integer.parseInt(body);
            } catch (Exception e) {
                return e.getMessage();
            }
        } else {
            if (response.getStatusCode()==400)
                return response.getBody().toString();
            else
                return response.getResponseMessage();
        }
    }

    public Object obtenerOrdenes() throws IOException {
        Webb webb = Webb.create();
        Response<JSONArray> response = webb.get(serverIP + "nuevasordenes/?" + url_complemento)
                .asJsonArray();

        if (response.getStatusCode()==200) {
            try {
                // Getting JSON Array node
                JSONArray ordenes = response.getBody();
                if (ordenes.length()>0) {
                    ArrayList<ServiceOrder> tr = new ArrayList<ServiceOrder>();

                    // looping through All Contacts
                    for (int i = 0; i < ordenes.length(); i++) {
                        JSONObject ord = ordenes.getJSONObject(i);

                        ServiceOrder s = new ServiceOrder();
                        s.setServiceOrdersId(ord.getInt("id"));
                        s.setSONumber(ord.getString("fsdoc"));
                        s.setClientSAPKey(Integer.parseInt(ord.getString("cliente_sap")));
                        s.setClientName(ord.getString("cliente"));
                        s.setMiningUnit(Integer.parseInt(ord.getString("unidad_minera_no")));
                        s.setMiningUnitName(ord.getString("unidad_minera"));
                        s.setTechnician(ord.getString("tecnico"));
                        s.setInstallerId(ord.getInt("instaldor"));
                        s.setNextService("");
                        s.setWorkPerformedBy(ord.getString("instalador__first_name") + " " + ord.getString("instaldor__last_name"));
                        s.setBunkerQuotation(ord.getString("sucursal_cotiza"));
                        s.setClientPO(ord.getString("orden_compra"));
                        s.setComments(ord.getString("comentarios"));
                        s.setUserId(ord.getInt("usuario_creador"));
                        s.setFSDate(ord.getString("fecha_creacion"));
                        if (ord.isNull("sincronizado_hh")) {
                            s.setSynchronized(false);
                            s.setFSDateSynchronized(null);
                        }
                        else {
                            s.setSynchronized(true);
                            s.setFSDateSynchronized(ord.getString("ultima_sincronizacion"));
                        }
                        s.setSelected(false);
                        ArrayList<Truck> dets = new ArrayList<Truck>();
                        JSONArray det = new JSONArray("detalles");
                        for (int j=0;j<det.length();j++) {
                            JSONObject detalle = ordenes.getJSONObject(j);
                            Truck t = new Truck();
                            t.setServiceOrdersDetailsId(detalle.getInt("id"));
                            t.setServiceOrdersId(s.getServiceOrdersId());
                            t.setSONumber(s.getSONumber());
                            t.setWorkType(detalle.getString("tipo"));
                            t.setEquipmentNumber(detalle.getString("camion__no_economico"));
                            t.setCapacity(detalle.getString("camion__capacidad"));
                            t.setModel(detalle.getString("camion__modelo"));
                            t.setSupressionSystemtype(detalle.getString("camion__tipo_supresion"));
                            t.setActivationSystemType(detalle.getString("camion__tipo_activacion"));
                            t.setTanksQuantity(detalle.getDouble("camion__tanques"));
                            t.setActivatorsQuantity(detalle.getDouble("camion__activadores"));
                            t.setNozzlesQuantity(detalle.getDouble("camion__boquillas"));
                            t.setSelected(false);
                            ArrayList<Integer> checklist = new ArrayList<Integer>();
                            JSONArray chks = new JSONArray("checklist");
                            if (chks.length()>0) {
                                for (int k = 0; k < chks.length(); k++) {
                                    JSONObject check = chks.getJSONObject(k);
                                    checklist.add(check.getInt(""));
                                }
                            }
                            t.setChks(checklist);
                            dets.add(t);
                        }
                        s.setTrucks(dets);
                        tr.add(s);
                    }
                    return tr;
                }
                else{
                    return null;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return e.getMessage();
            }
        } else {
            if (response.getStatusCode()==400)
                return response.getBody().toString();
            else
                return response.getResponseMessage();
        }
    }


    public Object confirmaSincronizarOrdenes(ArrayList<Integer> ids) throws JSONException {
        JSONObject msg = new JSONObject();
        JSONArray array = new JSONArray();
        for (Integer i : ids){
            array.put(i);
        }
        msg.put("id",array);

        Webb webb = Webb.create();

        Response<Void> result = webb.post(serverIP+"confirma_recibir_ordenes/")
                .body(msg)
                .compress()
                .connectTimeout(10000)
                .readTimeout(10000)
                .asVoid();
        if (result.getStatusCode()!=200)
            return result.getBody().toString();
        else return true;
    }

    public Object enviaSincronizacion(String json) throws JSONException {
        Webb webb = Webb.create();
        Response<Void> result = webb.post(serverIP+"subir_orden/")
                .body(json)
                .compress()
                .connectTimeout(10000)
                .readTimeout(10000)
                .asVoid();
        if (result.getStatusCode()!=200)
            return result.getBody().toString();
        else return true;
    }

    public Object imprimirEtiquetas(String json) throws JSONException {
        Webb webb = Webb.create();
        Response<Void> result = webb.post(serverIP+"imprimir_etiquetas_hh/")
                .body(json)
                .compress()
                .connectTimeout(10000)
                .readTimeout(10000)
                .asVoid();
        if (result.getStatusCode()!=200)
            return result.getBody().toString();
        else return true;
    }

*/
}