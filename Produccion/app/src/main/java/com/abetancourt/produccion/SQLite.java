
package com.abetancourt.produccion;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SQLite {

	private static final int VERSION_BD = 1; //Version de la base de datos
	private static final String N_BD = "hlstn_prod"; //NOMBRE DE BASE DE DATOS
    public static final String ID = "id"; //ID para todas las tablas
    public static final String SYNC = "sincronizado"; //ID para todas las tablas

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    ////*** TABLA CALIFVACA ***////
    public static final String TAB_V = "Vacas";
    public static final String V_HATO = "NoHato";
    public static final String V_NVACA = "NoVaca";
    public static final String V_CURV = "Curv";
    public static final String V_NUMORD = "NumOrd";
    public static final String V_LAB = "Lab";
    public static final String V_FECHAEST = "FechaEst";
    public static final String V_CODEST = "CodEst";
    public static final String V_FECHPRUEBA = "FechaPrueba";
    public static final String V_H1INI = "Hora1Ini";
    public static final String V_H1FIN = "Hora1Fin";
    public static final String V_H2INI = "Hora2Ini";
    public static final String V_H2FIN = "Hora2Fin";
    public static final String V_H3INI = "Hora3Ini";
    public static final String V_H3FIN = "Hora3Fin";
    public static final String V_P1 = "Peso1";
    public static final String V_P2 = "Peso2";
    public static final String V_P3 = "Peso3";
    public static final String V_P1HR = "Peso1Hr";
    public static final String V_P2HR = "Peso2Hr";
    public static final String V_P3HR = "Peso3Hr";
    public static final String V_LOTET = "LoteT";
    public static final String V_NOVIAL = "NoVial";
    public static final String V_ORDVIAL = "OrdVial";
    public static final String V_FECHA = "Fecha";

    private BDHelper nHelper = null;
	private Context nContexto;
	public SQLiteDatabase nBD=null;

	/**
	 * Constructor de la clase
	 * @param c Contexto
	 */
	public SQLite(Context c){
		nContexto=c;
	}

	/**
	 * Se encarga de abrir la conexi�n con SQLite
	 * @return Conexi�n
	 * @throws Exception
	 */
	public SQLite AbrirBD(){
		nHelper = new BDHelper(nContexto);
		nBD = nHelper.getWritableDatabase();
		return this;
	}

	/**
	 * Cierra la conexi�n con la BD
	 */
	public void CerrarBD() {
		if(!(nHelper==null))nHelper.close();
//		if(!(sdHelper==null))sdHelper.close();
	}

	/**
	 * Crea un Cursor con una función Genérica
	 * @param table Tabla a consultar
	 * @param columns Array de los Nombres de columas que se desea consultar
	 * @param where Cláusula WHERE, escriba null si no existe
	 * @param groupBy Agrupamientos GROUPBY, escriba null si no existe
	 * @param having Cláusula HAVING, escriba null si no existe
	 * @param orderBy Ordenamiento por nombre de columna, escriba null si no existe
	 * @param limit limite, escriba null si no existe
	 * @return Cursor que contiene la informacián obtenida
	 */
	public Cursor GenericSELECT(String table,String[] columns,String where, String groupBy,String having,String orderBy,String limit){
		Cursor c = nBD.query(table, columns, where, null, groupBy, having, orderBy, limit);
		return c;
	}
	
	public Cursor GenericSELECT(String RAWQuery) throws Exception {
        try {
            AbrirBD();
            Cursor c = nBD.rawQuery(RAWQuery, null);
            return c;
        }catch (Exception e){throw e;}
        finally{CerrarBD();}
	}

	@SuppressLint("SimpleDateFormat")
	public String fechaActual(){
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		return formato.format(new Date());
	}

    @SuppressLint("SimpleDateFormat")
    public String horaActual(){
        SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
        return formato.format(new Date());
    }

	/**
	 * Clase que permite el manejo de la BD de SQLite
	 * @author Alex
	 *
	 */
	public class BDHelper extends SQLiteOpenHelper {

		public BDHelper(Context context){
			super(context,N_BD,null,VERSION_BD);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE [" + TAB_V + "] (" +
                "    [" + ID + "] integer NOT NULL PRIMARY KEY," +
                "    [" + V_HATO + "] TEXT NOT NULL," +
                "    [" + V_NVACA + "] TEXT ," +
                "    [" + V_CURV + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_NUMORD + "] INTEGER NOT NULL DEFAULT 0 ," +
                "    [" + V_LAB + "] INTEGER NOT NULL DEFAULT 0 ," +
                "    [" + V_FECHAEST + "] TEXT ," +
                "    [" + V_CODEST + "] TEXT ," +
                "    [" + V_FECHPRUEBA + "] TEXT ," +
                "    [" + V_H1INI + "] TEXT," +
                "    [" + V_H1FIN + "] TEXT ," +
                "    [" + V_H2INI + "] TEXT ," +
                "    [" + V_H2FIN + "] TEXT ," +
                "    [" + V_H3INI + "] TEXT," +
                "    [" + V_H3FIN + "] TEXT," +
                "    [" + V_P1 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_P2 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_P3 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_LOTET + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_NOVIAL + "] TEXT," +
                "    [" + V_FECHA + "] TEXT," +
                "    [" + SYNC + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_ORDVIAL + "] TEXT ," +
                "    [" + V_P1HR + "] TEXT ," +
                "    [" + V_P2HR + "] TEXT," +
                "    [" + V_P3HR + "] TEXT" +
                ")");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}
	}

	/**
	 * M�todo que ejecuta una sentencia sin esperar un resultado
	 * @param exec Sentencia SQL para SQLite
	 */
	public void execSQLite(String exec){
		nBD.execSQL(exec);
	}

	/**
	 * Inserta datos dentro de la tabla que se indique
	 * @param TableName - Nombre de la tabla en donde se almacenr�n los datos
	 * @param Values - ContentValue con los valores que se van a ingresar en la tabla especificada
	 * @return
	 */
	public long insertaDatos (String TableName, ContentValues Values)
	{
		long result;

		result = nBD.insert(TableName, null, Values);

		return result;
	}

	public int updateDatos (String TableName, ContentValues Values, String Where)
	{
		int result = nBD.update(TableName, Values, Where, null);

		return result;
	}

	public long insertaImagen (String TableName, ContentValues cvImagePre)
	{
        long res = nBD.insert(TableName, null, cvImagePre);

		return res;
	}

	/**
	 * Obtiene los datos de una consulta y los guarda en un Cursor
	 * @param TableName - Nombre de la tabla de donde se tomar�n los datos
	 * @param Columnas - Arreglo de strings de las columnas que se desean obtener
	 * @param Where - Condici�n utilizada para la consulta
	 * @return Cursor: Retorna los datos solicitados dentro de un Cursor
	 */
	//M�todo para obtener datos solicitados desde la base de datos
	public Cursor getDatos(String TableName, String[] Columnas, String Where,String OrderBy, String groupBy) throws Exception {
        Cursor resultado = nBD.query(TableName, Columnas, Where, null, groupBy, null, OrderBy);
        return resultado;
	}

	/**
	 * Elimina los datos de una tabla dentro de la base de datos
	 * @param TableName - Nombre de la tabla de donde se eliminaron los datos
	 * @param Where - Condición utilizada para eliminar los datos
	 */
	//Elimina los datos de una tabla
	public int deleteDatos (String TableName, String Where) {
		int result = nBD.delete(TableName, Where, null);
		return result;
	}

    public int minimoId(){
        try {
            AbrirBD();
            Cursor c = getDatos(TAB_V, new String[]{"min(" + ID + ")"},null,null,null);
            c.moveToFirst();
            int rowID = c.getInt(0);
            return rowID;
        }catch (Exception e){return 0;}
        finally{CerrarBD();}
    }

    public Object insertarVaca(Vaca e){
        try{
            String[] cols = {ID};
            Cursor c = getDatos(TAB_V,cols,ID+"='"+e.getId()+"'",null,null);
            if (c.moveToFirst()){
                c.close();
                return false;
            }
            else {
                ContentValues values = new ContentValues();
                values.put(ID, e.getId());
                values.put(V_HATO, e.getNoHato());
                values.put(V_NVACA, e.getNoVaca());
                values.put(V_CURV, e.getCurv());
                values.put(V_FECHAEST, e.getFechaEst());
                values.put(V_CODEST, e.getCodEst());
                values.put(V_FECHA, e.getFecha());

                c.close();
                insertaDatos(TAB_V, values);
                return true;
            }
        }catch(Exception ex){ex.printStackTrace();return ex.getMessage();}
    }



    public Object actualizarVaca(Vaca e){
        AbrirBD();
        try{
            ContentValues values = new ContentValues();
            values.put(V_NUMORD , e.getNumOrd());
            values.put(V_LAB , e.getLab());
            values.put(V_FECHPRUEBA , fechaActual());
            values.put(V_H1INI , e.getHora1Ini());
            values.put(V_H1FIN , e.getHora1Fin());
            values.put(V_H2INI , e.getHora2Ini());
            values.put(V_H2FIN , e.getHora2Fin());
            values.put(V_H3INI , e.getHora3Ini());
            values.put(V_H3FIN , e.getHora3Fin());
            values.put(V_P1 , e.getPeso1());
            values.put(V_P2 , e.getPeso2());
            values.put(V_P3 , e.getPeso3());
            values.put(V_LOTET , e.getLoteT());
            values.put(V_NOVIAL , e.getNoVial());
            updateDatos(TAB_V, values, ID + "=" + e.getId());
            return true;
        }catch(Exception ex){ex.printStackTrace();return ex.getMessage();}
        finally{CerrarBD();}
    }

    public String actualizarHorarioCaptura(int id, int produccion){
        AbrirBD();
        try{
            String ahora = horaActual();
            ContentValues values = new ContentValues();
            if (produccion==1)
                values.put(V_P1HR, ahora);
            else if (produccion == 2)
                values.put(V_P2HR, ahora);
            else if (produccion == 3)
                values.put(V_P3HR, ahora);
            updateDatos(TAB_V, values, ID+"='"+id+"'");
            return ahora;
        }catch(Exception ex){return ex.getMessage();}
        finally{CerrarBD();}
    }

    public String ordVial(String hatoSeleccionado, String ord){
        AbrirBD();
        try{
            String hato = hatoSeleccionado.split(" - ")[0];
            String fecha = hatoSeleccionado.split(" - ")[1];
            ContentValues values = new ContentValues();
            values.put(V_ORDVIAL , ord);
            updateDatos(TAB_V, values, V_HATO + "='" + hato+"' AND "+V_FECHA+"='"+fecha+"'");
            return ord;
        }catch(Exception ex){return ex.getMessage();}
        finally{CerrarBD();}
    }

    public String vacaI1(String hatoSeleccionado){
        AbrirBD();
        try{
            String hato = hatoSeleccionado.split(" - ")[0];
            String fecha = hatoSeleccionado.split(" - ")[1];
            String ahora = horaActual();
            ContentValues values = new ContentValues();
            values.put(V_H1INI , ahora);
            updateDatos(TAB_V, values, V_HATO + "='" + hato+"' AND "+V_FECHA+"='"+fecha+"'");
            return ahora;
        }catch(Exception ex){return ex.getMessage();}
        finally{CerrarBD();}
    }
    public String vacaI2(String hatoSeleccionado){
        AbrirBD();
        try{
            String hato = hatoSeleccionado.split(" - ")[0];
            String fecha = hatoSeleccionado.split(" - ")[1];
            String ahora = horaActual();
            ContentValues values = new ContentValues();
            values.put(V_H2INI , ahora);
            updateDatos(TAB_V, values, V_HATO + "='" + hato+"' AND "+V_FECHA+"='"+fecha+"'");
            return ahora;
        }catch(Exception ex){return ex.getMessage();}
        finally{CerrarBD();}
    }
    public String vacaI3(String hatoSeleccionado){
        AbrirBD();
        try{
            String hato = hatoSeleccionado.split(" - ")[0];
            String fecha = hatoSeleccionado.split(" - ")[1];
            String ahora = horaActual();
            ContentValues values = new ContentValues();
            values.put(V_H3INI , ahora);
            updateDatos(TAB_V, values, V_HATO + "='" + hato+"' AND "+V_FECHA+"='"+fecha+"'");
            return ahora;
        }catch(Exception ex){return ex.getMessage();}
        finally{CerrarBD();}
    }


    public String vacaF1(String hatoSeleccionado){
        AbrirBD();
        try{
            String hato = hatoSeleccionado.split(" - ")[0];
            String fecha = hatoSeleccionado.split(" - ")[1];
            String ahora = horaActual();
            ContentValues values = new ContentValues();
            values.put(V_H1FIN , ahora);
            updateDatos(TAB_V, values, V_HATO + "='" + hato+"' AND "+V_FECHA+"='"+fecha+"'");
            return ahora;
        }catch(Exception ex){return ex.getMessage();}
        finally{CerrarBD();}
    }
    public String vacaF2(String hatoSeleccionado){
        AbrirBD();
        try{
            String hato = hatoSeleccionado.split(" - ")[0];
            String fecha = hatoSeleccionado.split(" - ")[1];
            String ahora = horaActual();
            ContentValues values = new ContentValues();
            values.put(V_H2FIN , ahora);
            updateDatos(TAB_V, values, V_HATO + "='" + hato+"' AND "+V_FECHA+"='"+fecha+"'");
            return ahora;
        }catch(Exception ex){return ex.getMessage();}
        finally{CerrarBD();}
    }
    public String vacaF3(String hatoSeleccionado){
        AbrirBD();
        try{
            String hato = hatoSeleccionado.split(" - ")[0];
            String fecha = hatoSeleccionado.split(" - ")[1];
            String ahora = horaActual();
            ContentValues values = new ContentValues();
            values.put(V_H3FIN , ahora);
            updateDatos(TAB_V, values, V_HATO + "='" + hato+"' AND "+V_FECHA+"='"+fecha+"'");
            return ahora;
        }catch(Exception ex){return ex.getMessage();}
        finally{CerrarBD();}
    }


    public Vaca obtenerVaca(int id){
        AbrirBD();
        try{
            String cols[] = {ID,V_HATO,V_NVACA,V_CURV,V_NUMORD,V_LAB,V_FECHAEST,V_CODEST,
                    V_FECHPRUEBA,V_H1INI,V_H1FIN,V_H2INI,V_H2FIN,V_H3INI,V_H3FIN,
                    V_P1,V_P2,V_P3,V_LOTET,V_NOVIAL,V_FECHA,SYNC,V_ORDVIAL,V_P1HR,V_P2HR,V_P3HR};

            Cursor c = getDatos(TAB_V,cols,ID+"="+id,null,null);
            if (c.moveToFirst()){
                Log.d("ObtenerVACA", "Se obtuvo una toro");
                Vaca vaca = new Vaca(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getInt(3),
                    c.getInt(4),
                    c.getInt(5),
                    c.getString(6),
                    c.getString(7),
                    c.getString(8),
                    c.getString(9),
                    c.getString(10),
                    c.getString(11),
                    c.getString(12),
                    c.getString(13),
                    c.getString(14),
                    c.getInt(15),
                    c.getInt(16),
                    c.getInt(17),
                    c.getInt(18),
                    c.getString(19),
                    c.getString(20),
                    c.getString(22),
                    c.getString(23),
                    c.getString(24),
                    c.getString(25)
                );
                if (c.getInt(21)==1)vaca.setSync(true);else vaca.setSync(false);
                c.close();
                return vaca;
            }
            else {
                c.close();
                return null;
            }
        }catch(Exception e){e.printStackTrace(); return null;}
        finally{CerrarBD();}
    }

    public ArrayList<Vaca> obtenerVacas(String hto,String fecha) throws Exception {
        AbrirBD();
        try{
            String cols[] = {ID,V_HATO,V_NVACA,V_CURV,V_NUMORD,V_LAB,V_FECHAEST,V_CODEST,V_FECHPRUEBA,V_H1INI,
                    V_H1FIN,V_H2INI,V_H2FIN,V_H3INI,V_H3FIN,V_P1,V_P2,V_P3,V_LOTET,V_NOVIAL,
                    V_FECHA,SYNC,V_ORDVIAL,V_P1HR,V_P2HR,V_P3HR};

            Cursor c = getDatos(TAB_V,cols, V_HATO +"='"+hto+"' AND "+ V_FECHA +"='"+fecha+"'",V_NVACA,null);
            if (c.moveToFirst()){
                ArrayList<Vaca> ret = new ArrayList<Vaca>();
                do {
                    Vaca vaca = new Vaca(
                            c.getInt(0),
                            c.getString(1),
                            c.getString(2),
                            c.getInt(3),
                            c.getInt(4),
                            c.getInt(5),
                            c.getString(6),
                            c.getString(7),
                            c.getString(8),
                            c.getString(9),
                            c.getString(10),
                            c.getString(11),
                            c.getString(12),
                            c.getString(13),
                            c.getString(14),
                            c.getInt(15),
                            c.getInt(16),
                            c.getInt(17),
                            c.getInt(18),
                            c.getString(19),
                            c.getString(20),
                            c.getString(22),
                            c.getString(23),
                            c.getString(24),
                            c.getString(25)
                    );
                    if (c.getInt(21)==1)vaca.setSync(true);else vaca.setSync(false);
                    ret.add(vaca);
                }while (c.moveToNext());
                c.close();
                return ret;
            }
            else {
                c.close();
                return null;
            }
        }catch(Exception e){e.printStackTrace(); throw e;}
        finally{CerrarBD();}
    }


    public ArrayList<String> obtenerHatos() throws Exception {
        AbrirBD();
        try{
            ArrayList<String> ret = new ArrayList<String>();
            ret.add("-- Seleccionar Hato --");
            String cols[] = {" distinct "+V_HATO, V_FECHA};
            Cursor c = getDatos(TAB_V,cols,null,null, V_HATO +","+ V_FECHA);
            if (c.moveToFirst()){
                do {
                    ret.add(c.getString(0)+" - "+c.getString(1));
                }while (c.moveToNext());
            }
            c.close();
            return ret;
        }catch(Exception e){e.printStackTrace(); throw e;}
        finally{CerrarBD();}
    }

    public String vacasIds() throws Exception {
        AbrirBD();
        try{
            String ids = "";
            String cols[] = {ID};
            Cursor c = getDatos(TAB_V,cols,null,null,null);
            if (c.moveToFirst()){
                do {
                    ids+=","+c.getInt(0);
                }while (c.moveToNext());

            }
            c.close();
            return ids;
        }catch(Exception e){e.printStackTrace(); throw e;}
        finally{CerrarBD();}
    }


    public ArrayList<Vaca> obtenerVacasParaEnvio(){
        AbrirBD();
        try{
            String cols[] = {ID,V_HATO,V_NVACA,V_CURV,V_NUMORD,V_LAB,V_FECHAEST,V_CODEST,
                    V_FECHPRUEBA,V_H1INI,V_H1FIN,V_H2INI,V_H2FIN,V_H3INI,V_H3FIN,
                    V_P1,V_P2,V_P3,V_LOTET,V_NOVIAL,V_FECHA,SYNC,V_ORDVIAL,V_P1HR,V_P2HR,V_P3HR
            };


            Cursor c = getDatos(TAB_V,cols,SYNC+"=0 and "+ V_FECHPRUEBA +" is not null",null,null);
            if (c.moveToFirst()){
                ArrayList<Vaca> vacas = new ArrayList<Vaca>();
                do {
                    Vaca vaca = new Vaca(
                            c.getInt(0),
                            c.getString(1),
                            c.getString(2),
                            c.getInt(3),
                            c.getInt(4),
                            c.getInt(5),
                            c.getString(6),
                            c.getString(7),
                            c.getString(8),
                            c.getString(9),
                            c.getString(10),
                            c.getString(11),
                            c.getString(12),
                            c.getString(13),
                            c.getString(14),
                            c.getInt(15),
                            c.getInt(16),
                            c.getInt(17),
                            c.getInt(18),
                            c.getString(19),
                            c.getString(20),
                            c.getString(22),
                            c.getString(23),
                            c.getString(24),
                            c.getString(25)
                    );
                    if (c.getInt(21)==1)vaca.setSync(true);else vaca.setSync(false);
                    vacas.add(vaca);
                }while(c.moveToNext());
                c.close();
                return vacas;
            }
            else {
                c.close();
                return null;
            }
        }catch(Exception e){e.printStackTrace(); return null;}
        finally{CerrarBD();}
    }

    public String verificarVialRepetido(String vial, String hato, String fecha){
        AbrirBD();
        try{
            String cols[] = {V_NVACA};

            Cursor c = getDatos(TAB_V,cols,V_NOVIAL+"='"+vial+"' AND "+V_HATO+"='"+hato+"' AND "+V_FECHA+"='"+fecha+"'",null,null);
            if (c.moveToFirst()){
                String novaca = c.getString(0);
                c.close();
                return novaca;
            }
            else {
                c.close();
                return null;
            }
        }catch(Exception e){e.printStackTrace(); return null;}
        finally{CerrarBD();}
    }

    public Object sincronizadoVacas(ArrayList<Vaca> vacas){
        AbrirBD();
        try{
            for (Vaca v : vacas) {
                ContentValues values = new ContentValues();
                values.put(SYNC, 1);
                updateDatos(TAB_V, values, ID + "=" + v.getId());
            }
            return true;
        }catch(Exception ex){ex.printStackTrace();return ex.getMessage();}
        finally{CerrarBD();}
    }


}
