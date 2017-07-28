
package com.abetancourt.holsteincalificador;

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
import java.util.concurrent.TimeUnit;

public class SQLite {

	private static final int VERSION_BD = 2; //Version de la base de datos
	private static final String N_BD = "hlstn_calif"; //NOMBRE DE BASE DE DATOS
    public static final String ID = "id"; //ID para todas las tablas
    public static final String SYNC = "sincronizado"; //ID para todas las tablas

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    ////*** TABLA CALIFVACA ***////
    public static final String TAB_V = "CalifVaca";
    public static final String HATO = "NoHato";  // Número de hato.
    public static final String FECHA = "Fecha";   // Fecha cuando se genera el reporte
    public static final String V_CURV = "Curv";       // Identificador único
    public static final String V_NOCP = "NoCP";    // No. de Vaca o NoCP
    public static final String NOREG = "NoRegistro";// No. de registro de la toro
    public static final String REGP = "RegPadre";// No. de registro del padre
    public static final String REGM = "RegMadre";// No. de registro de la madre
    public static final String FECHANAC = "FechaNac";// Fecha de nacimiento
    public static final String V_NOLAC = "NoLactancia";// Número de parto
    public static final String V_FECHAPAR = "FechaParto";// Fecha de parto.
    public static final String CALIFANT = "CalifAnt";   // Calificación anterior, son los puntos.
    public static final String CLASEANT = "ClaseAnt";// Clase anterior.
    public static final String V_TIPOPAR = "TipoParto";// A char(1) N=Normal, A=Anormal
    public static final String V_LACNOM = "LacNorm";    // Número de lactancias normales.
    public static final String V_MESESAB = "MesesAborto";// Meses al aborto.
    //FORTALEZA LECHERA
    public static final String V_C1 = "c1";     // Estatura
    public static final String V_C2 = "c2";     // AltCruz
    public static final String V_C3 = "c3";     // Tamaño
    public static final String V_C4 = "c4";     // PechoAncho
    public static final String V_C5 = "c5";     // Profundidad
    public static final String V_C24 = "c24";    // Angularidad
    //ANCA
    public static final String V_C6 = "c6";     // Lomo
    public static final String V_C7 = "c7";     // Punta
    public static final String V_C8 = "c8";     // Anchura
    public static final String V_C8A = "c8a";    // Colocacion
    //PATAS Y PEZUÑAS
    public static final String V_C9 = "c9";     // Angulo
    public static final String V_C11 = "c11";    // ProfTalón
    public static final String V_C12 = "c12";    // CalHuesos
    public static final String V_C13 = "c13";    // Aplomo
    public static final String V_C14 = "c14";    // VPosterior
    //SISTEMA MAMARIO
    public static final String V_C15 = "c15";    // SMProfundidad
    public static final String V_C16 = "c16";    // Textura
    public static final String V_C17 = "c17";    // LMS
    public static final String V_C18 = "c18";    // Inserción Del
    public static final String V_C19 = "c19";    // Posición Del
    public static final String V_C20 = "c20";    // LongPezones Pos
    public static final String V_C21 = "c21";    // UPAltura
    public static final String V_C22 = "c22";    // UPAnchura
    public static final String V_C23 = "c23";    // UPPosicion

    public static final String V_PTOS = "Puntos";         // Puntos finales que otorga la aplicación.
    public static final String V_CLSE = "Clase";          // Clase final que otorga la aplicación.
    public static final String PUNTCAL = "PuntosCalif"; // Puntos finales que otorga el calificador.
    public static final String CLSECAL = "ClaseCalif";  // Clase final que otorga el calificador.
    public static final String FECHCAL = "FechaCalif";  // Fecha de calificación
    public static final String FECHCALANT = "FechaCalifAnt";  // Fecha de calificación
    // DEFECTOS //
    public static final String V_D10 = "Def10";
    public static final String V_D11 = "Def11";
    public static final String V_D12 = "Def12";
    public static final String V_D22 = "Def22";
    public static final String V_D23 = "Def23";
    public static final String V_D24 = "Def24";
    public static final String V_D25 = "Def25";
    public static final String V_D26 = "Def26";
    public static final String V_D27 = "Def27";
    public static final String V_D28 = "Def28";
    public static final String V_D29 = "Def29";
    public static final String V_D30 = "Def30";
    public static final String V_D31 = "Def31";
    public static final String V_D32 = "Def32";
    public static final String V_D34 = "Def34";
    public static final String V_D35 = "Def35";
    public static final String V_D36 = "Def36";
    public static final String V_D40 = "Def40";
    public static final String V_D42 = "Def42";
    public static final String V_D43 = "Def43";
    public static final String V_D44 = "Def44";
    public static final String V_D45 = "Def45";
    public static final String V_D46 = "Def46";
    public static final String V_D47 = "Def47";
    public static final String V_D48 = "Def48";
    // CLASES //
    public static final String V_CSE_1 = "ss1";
    public static final String V_CSE_2 = "ss2";
    public static final String V_CSE_3 = "ss3";
    public static final String V_CSE_4 = "ss4";

    public static final String V_SS1P = "ss1puntos";
    public static final String V_SS2P = "ss2puntos";
    public static final String V_SS3P = "ss3puntos";
    public static final String V_SS4P = "ss4puntos";



    ///*** TABLA CalifToros ***///
    public static final String TAB_T = "CalifToro";
    public static final String T_NOMB = "Nombre";               // Nombre del toro                                                 varchar(30)
    public static final String T_C1 = "c1";                   // valor de 1 a 9 para Inclinación del anca.                       tinyint
    public static final String T_C2 = "c2";                   // valor de 1 a 9 para Anchura de anca.                            tinyint
    public static final String T_C3 = "c3";                   // valor de 1 a 9 para Fortaleza lomo.                             tinyint
    public static final String T_C4 = "c4";                   // valor de 1 a 9 para Colocación de la cadera.                    tinyint
    public static final String T_SS1 = "ss1";                  // Clase que otorga el calificador al sistema1 Anca.               varchar(3)
    public static final String T_SS1P = "ss1puntos";            // Puntos que otorga el calificador al sistema1 Anca.              tinyint
    public static final String T_C5 = "c5";                   // valor de 1 a 9 para Estatura.                                   tinyint
    public static final String T_C6 = "c6";                   // valor de 1 a 9 para Altura a la cruz.                           tinyint
    public static final String T_C7 = "c7";                   // valor de 1 a 9 para Anchura de pecho.                           tinyint
    public static final String T_C8 = "c8";                   // valor de 1 a 9 para Profundidad corporal.                       tinyint
    public static final String T_C9 = "c9";                   // valor de 1 a 9 para Angularidad.                                tinyint
    public static final String T_C10 = "c10";                  // valor de 1 a 9 para Tamaño.                                     tinyint
    public static final String T_SS2 = "ss2";                  // Clase que otorga el calificador al sistema2 Fortaleza lechera.  varchar(3)
    public static final String T_SS2P = "ss2puntos";            // Puntos que otorga el calificador al sistema2 Fortaleza lechera. tinyint
    public static final String T_C11 = "c11";                  // valor de 1 a 9 para Ángulo pezuña.                              tinyint
    public static final String T_C12 = "c12";                  // valor de 1 a 9 para Profundidad de talón.                       tinyint
    public static final String T_C13 = "c13";                  // valor de 1 a 9 para Calidad de huesos.                          tinyint
    public static final String T_C14 = "c14";                  // valor de 1 a 9 para Aplomo o Patas traseras vista lateral       tinyint
    public static final String T_C15 = "c15";                  // valor de 1 a 9 para patas posteriores vista trasera.            tinyint
    public static final String T_SS3 = "ss3";                  // Clase que otorga el calificador al sistema3 Patas y pezuñas.    varchar(3)
    public static final String T_SS3P = "ss3puntos";            // Puntos que otorga el calificador al sistema3 Patas y pezuñas.   tinyint
    public static final String T_D10 = "Def10";                // valor 1 para indicar defecto                                    bit
    public static final String T_D11 = "Def11";                // valor 1 para indicar defecto                                    bit
    public static final String T_D40 = "Def40";                // valor 1 para indicar defecto                                    bit
    public static final String T_D42 = "Def42";                // valor 1 para indicar defecto                                    bit
    public static final String T_D43 = "Def43";                // valor 1 para indicar defecto                                    bit
    public static final String T_D44 = "Def44";                // valor 1 para indicar defecto                                    bit
    public static final String T_D45 = "Def45";                // valor 1 para indicar defecto                                    bit
    public static final String T_D30 = "Def30";                // valor 1 para indicar defecto                                    bit
    public static final String T_D31 = "Def31";                // valor 1 para indicar defecto                                    bit
    public static final String T_D32 = "Def32";                // valor 1 para indicar defecto                                    bit
    public static final String T_D34 = "Def34";                // valor 1 para indicar defecto                                    bit
    public static final String T_D35 = "Def35";                // valor 1 para indicar defecto                                    bit
    public static final String T_D36 = "Def36";                // valor 1 para indicar defecto                                    bit
    public static final String COMEN = "Comentarios";          // Comentarios                                                     varchar(50)

    public static final String TAB_S = "Socios";
    public static final String S_NOHATO = "NoHato";
    public static final String S_NOM = "Nombre";



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
     * Get a diff between two dates
     * @param date1 the oldest date
     * @param date2 the newest date
     * @param timeUnit the unit in which you want the diff
     * @return the diff value, in the provided unit
     */
    public long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
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
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		return formato.format(new Date());
	}

//	public static class SDHelper extends SQLiteOpenHelper{
//
//		public SDHelper(Context context) {
//			super(context, "/sdcard/SQLite/firesys.db" , null, 2);
//		}
//
//		@Override
//		public void onCreate(SQLiteDatabase arg0) {
//		}
//
//		@Override
//		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		}
//
//	}

	/**
	 * Clase que permite el manejo de la BD de SQLite
	 * @author Alex
	 *
	 */
	public class BDHelper extends SQLiteOpenHelper{

		public BDHelper(Context context){
			super(context,N_BD,null,VERSION_BD);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE [" + TAB_V + "] (" +
                "    [" + ID + "] integer NOT NULL PRIMARY KEY," +
                "    [" + HATO + "] TEXT NOT NULL," +
                "    [" + FECHA + "] TEXT ," +
                "    [" + V_CURV + "] TEXT," +
                "    [" + V_NOCP + "] TEXT ," +
                "    [" + NOREG + "] TEXT ," +
                "    [" + REGP + "] TEXT ," +
                "    [" + REGM + "] TEXT ," +
                "    [" + FECHANAC + "] TEXT ," +
                "    [" + V_NOLAC + "] TEXT," +
                "    [" + V_FECHAPAR + "] TEXT ," +
                "    [" + CALIFANT + "] TEXT ," +
                "    [" + CLASEANT + "] TEXT ," +
                "    [" + FECHCALANT + "] TEXT ," +
                "    [" + V_TIPOPAR + "] TEXT," +
                "    [" + V_LACNOM + "] TEXT," +
                "    [" + V_MESESAB + "] TEXT," +

                "    [" + V_C1 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_C2 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_C3 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_C4 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_C5 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_C24 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_C6 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_C7 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_C8 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_C8A + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_C9 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_C11 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_C12 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_C13 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_C14 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_C15 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_C16 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_C17 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_C18 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_C19 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_C20 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_C21 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_C22 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_C23 + "] INTEGER NOT NULL DEFAULT 0," +

                "    [" + V_D10 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_D11 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_D12 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_D22 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_D23 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_D24 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_D25 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_D26 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_D27 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_D28 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_D29 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_D30 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_D31 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_D32 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_D34 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_D35 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_D36 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_D40 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_D42 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_D43 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_D44 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_D45 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_D46 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_D47 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_D48 + "] INTEGER NOT NULL DEFAULT 0," +

                "    [" + V_CSE_1 + "] TEXT ," +
                "    [" + V_CSE_2 + "] TEXT ," +
                "    [" + V_CSE_3 + "] TEXT ," +
                "    [" + V_CSE_4 + "] TEXT ," +
                "    [" + V_SS1P + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_SS2P + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_SS3P + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_SS4P + "] INTEGER NOT NULL DEFAULT 0," +

                "    [" + V_PTOS + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + V_CLSE + "] TEXT ," +
                "    [" + PUNTCAL + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + CLSECAL + "] TEXT ," +
                "    [" + FECHCAL + "] TEXT," +
                "    [" + COMEN + "] TEXT," +
                "    [" + SYNC + "] INTEGER NOT NULL DEFAULT 0" +
                ")");

            db.execSQL("CREATE TABLE ["+TAB_T+"] (" +
                "    [" + ID + "] integer NOT NULL PRIMARY KEY," +
                "    [" + T_NOMB + "] TEXT," +
                "    [" + HATO + "] TEXT NOT NULL," +
                "    [" + FECHA + "] TEXT ," +
                "    [" + NOREG + "] TEXT ," +
                "    [" + REGP + "] TEXT ," +
                "    [" + REGM + "] TEXT ," +
                "    [" + FECHANAC + "] TEXT ," +
                "    [" + CALIFANT + "] TEXT ," +
                "    [" + CLASEANT + "] TEXT ," +
                "    [" + T_C1 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + T_C2 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + T_C3 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + T_C4 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + T_C5 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + T_C6 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + T_C7 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + T_C8 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + T_C9 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + T_C10 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + T_C11 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + T_C12 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + T_C13 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + T_C14 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + T_C15 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + T_SS1 + "] TEXT ," +
                "    [" + T_SS2 + "] TEXT ," +
                "    [" + T_SS3 + "] TEXT ," +
                "    [" + T_SS1P + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + T_SS2P + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + T_SS3P + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + T_D10 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + T_D11 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + T_D40 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + T_D42 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + T_D43 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + T_D44 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + T_D45 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + T_D30 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + T_D31 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + T_D32 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + T_D34 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + T_D35 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + T_D36 + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + PUNTCAL + "] INTEGER NOT NULL DEFAULT 0," +
                "    [" + CLSECAL + "] TEXT ," +
                "    [" + FECHCAL + "] TEXT," +
                "    [" + COMEN + "] TEXT," +
                "    [" + SYNC + "] INTEGER NOT NULL DEFAULT 0" +
                ")");
            db.execSQL("CREATE TABLE ["+TAB_S+"] (" +
                "    [" + ID + "] integer NOT NULL PRIMARY KEY," +
                "    [" + S_NOHATO + "] TEXT," +
                "    [" + S_NOM + "] TEXT" +
                ")");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (oldVersion==1){
                db.execSQL("ALTER TABLE "+TAB_V+" ADD COLUMN "+V_D12+" INTEGER NOT NULL DEFAULT 0;");
                db.execSQL("ALTER TABLE "+TAB_V+" ADD COLUMN "+V_D46+" INTEGER NOT NULL DEFAULT 0;");
                db.execSQL("ALTER TABLE "+TAB_V+" ADD COLUMN "+V_D47+" INTEGER NOT NULL DEFAULT 0;");
                db.execSQL("ALTER TABLE "+TAB_V+" ADD COLUMN "+V_D48+" INTEGER NOT NULL DEFAULT 0;");
                db.execSQL("ALTER TABLE "+TAB_V+" ADD COLUMN "+FECHCALANT+" TEXT ;");
                db.execSQL("CREATE TABLE ["+TAB_S+"] (" +
                        "    [" + ID + "] integer NOT NULL PRIMARY KEY," +
                        "    [" + S_NOHATO + "] TEXT," +
                        "    [" + S_NOM + "] TEXT" +
                        ")");
            }


		}
	}

	/**
	 * Método que ejecuta una sentencia sin esperar un resultado
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
	//Metodo para obtener datos solicitados desde la base de datos
	public Cursor getDatos(String TableName, String [] Columnas, String Where,String OrderBy, String groupBy) throws Exception {
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

    public Object insertarCalifVaca(Vaca e){
        AbrirBD();
        try{
            String[] cols = {V_NOCP};
            Cursor c = getDatos(TAB_V,cols,HATO+"='"+e.getNoHato()+"' AND "+V_NOCP+"='"+e.getNoCP()+"'",null,null);
            if (c.moveToFirst()){
                c.close();
                return false;
            } else {
                ContentValues values = new ContentValues();
                values.put(ID, e.getId());
                values.put(HATO, e.getNoHato());
                values.put(FECHA, e.getFecha());
                values.put(V_CURV, e.getCurv());
                values.put(V_NOCP, e.getNoCP());
                values.put(NOREG, e.getNoRegistro());
                values.put(REGM, e.getRegmadre());
                values.put(REGP, e.getRegPadre());
                values.put(FECHANAC, e.getFechaNac());
                values.put(V_NOLAC, e.getNoLactancia());
                values.put(V_FECHAPAR, e.getFechaParto());
                values.put(CALIFANT, e.getCalifAnt());
                values.put(CLASEANT, e.getClaseAnt());
                values.put(V_TIPOPAR, e.getTipoParto());
                values.put(V_LACNOM, e.getLacNorm());
                values.put(V_MESESAB, e.getMesesAborto());
                values.put(FECHCALANT,e.getFechaCalifAnt());
                c.close();
                insertaDatos(TAB_V, values);
                return true;
            }
        }catch(Exception ex){ex.printStackTrace();return ex.getMessage();}
        finally{CerrarBD();}
    }

    public void iniciarTransaccion(){
        nBD.beginTransaction();
    }

    public void finalizarTransaccion(){
        nBD.setTransactionSuccessful();
        nBD.endTransaction();
    }
    public Object bulkInsertarCalifVaca(Vaca e){
        try{
            String[] cols = {V_NOCP};
            Cursor c = getDatos(TAB_V,cols,HATO+"='"+e.getNoHato()+"' AND "+V_NOCP+"='"+e.getNoCP()+"'",null,null);
            if (c.moveToFirst()){
                c.close();
                return false;
            }
            else {
                ContentValues values = new ContentValues();
                values.put(ID, e.getId());
                values.put(HATO, e.getNoHato());
                values.put(FECHA, e.getFecha());
                values.put(V_CURV, e.getCurv());
                values.put(V_NOCP, e.getNoCP());
                values.put(NOREG, e.getNoRegistro());
                values.put(REGM, e.getRegmadre());
                values.put(REGP, e.getRegPadre());
                values.put(FECHANAC, e.getFechaNac());
                values.put(V_NOLAC, e.getNoLactancia());
                values.put(V_FECHAPAR, e.getFechaParto());
                values.put(CALIFANT, e.getCalifAnt());
                values.put(CLASEANT, e.getClaseAnt());
                values.put(V_TIPOPAR, e.getTipoParto());
                values.put(V_LACNOM, e.getLacNorm());
                values.put(V_MESESAB, e.getMesesAborto());
                values.put(FECHCALANT,e.getFechaCalifAnt());
                c.close();
                insertaDatos(TAB_V, values);
                return true;
            }
        }catch(Exception ex){ex.printStackTrace();return ex.getMessage();}
    }

    public Object limpiaSocios(){
        try {
         deleteDatos(TAB_S,null);
            return true;
        }catch(Exception e){e.printStackTrace();return e.getMessage();}

    }

    public Object bulkInsertarSocio(Socio s){
        try{
            ContentValues values = new ContentValues();
            values.put(ID, s.getId());
            values.put(S_NOHATO, s.getNoHato());
            values.put(S_NOM, s.getNombre());
            insertaDatos(TAB_S, values);
            return true;
        }catch(Exception ex){ex.printStackTrace();return ex.getMessage();}
    }

    public Object insertarCalifToro(Toro e){
        AbrirBD();
        try{
            ContentValues values = new ContentValues();
            values.put(ID, e.getId());
            values.put(HATO,e.getNoHato());
            values.put(FECHA, e.getFecha());
            values.put(NOREG, e.getNoRegistro());
            values.put(REGM, e.getRegMadre());
            values.put(REGP, e.getRegPadre());
            values.put(FECHANAC, e.getFechaNac());
            values.put(CALIFANT, e.getCalifAnt());
            values.put(CLASEANT, e.getClaseAnt());
            values.put(T_NOMB , e.getNombre());
            insertaDatos(TAB_T,values);
            return true;
        }catch(Exception ex){ex.printStackTrace();return ex.getMessage();}
        finally{CerrarBD();}
    }

    public Object bulkInsertarCalifToro(Toro e){
        try{
            ContentValues values = new ContentValues();
            values.put(ID, e.getId());
            values.put(HATO,e.getNoHato());
            values.put(FECHA, e.getFecha());
            values.put(NOREG, e.getNoRegistro());
            values.put(REGM, e.getRegMadre());
            values.put(REGP, e.getRegPadre());
            values.put(FECHANAC, e.getFechaNac());
            values.put(CALIFANT, e.getCalifAnt());
            values.put(CLASEANT, e.getClaseAnt());
            values.put(T_NOMB , e.getNombre());
            insertaDatos(TAB_T,values);
            return true;
        }catch(Exception ex){ex.printStackTrace();return ex.getMessage();}
    }

    public Object actualizarCalifVaca(Vaca e){
        AbrirBD();
        try{
            ContentValues values = new ContentValues();
            values.put(V_C1 , e.getC1());
            values.put(V_C2 , e.getC2());
            values.put(V_C3 , e.getC3());
            values.put(V_C4 , e.getC4());
            values.put(V_C5 , e.getC5());
            values.put(V_C24, e.getC24());
            values.put(V_C6 , e.getC6());
            values.put(V_C7 , e.getC7());
            values.put(V_C8 , e.getC8());
            values.put(V_C8A, e.getC8a());
            values.put(V_C9 , e.getC9());
            values.put(V_C11, e.getC11());
            values.put(V_C12, e.getC12());
            values.put(V_C13, e.getC13());
            values.put(V_C14, e.getC14());
            values.put(V_C15, e.getC15());
            values.put(V_C16, e.getC16());
            values.put(V_C17, e.getC17());
            values.put(V_C18, e.getC18());
            values.put(V_C19, e.getC19());
            values.put(V_C20, e.getC20());
            values.put(V_C21, e.getC21());
            values.put(V_C22, e.getC22());
            values.put(V_C23, e.getC23());
            values.put(V_D10, e.getDef10());
            values.put(V_D11, e.getDef11());
            values.put(V_D12, e.getDef12());
            values.put(V_D22, e.getDef22());
            values.put(V_D23, e.getDef23());
            values.put(V_D24, e.getDef24());
            values.put(V_D25, e.getDef25());
            values.put(V_D26, e.getDef26());
            values.put(V_D27, e.getDef27());
            values.put(V_D28, e.getDef28());
            values.put(V_D29, e.getDef29());
            values.put(V_D30, e.getDef30());
            values.put(V_D31, e.getDef31());
            values.put(V_D32, e.getDef32());
            values.put(V_D34, e.getDef34());
            values.put(V_D35, e.getDef35());
            values.put(V_D36, e.getDef36());
            values.put(V_D40, e.getDef40());
            values.put(V_D42, e.getDef42());
            values.put(V_D43, e.getDef43());
            values.put(V_D44, e.getDef44());
            values.put(V_D45, e.getDef45());
            values.put(V_D46, e.getDef46());
            values.put(V_D47, e.getDef47());
            values.put(V_D48, e.getDef48());
            values.put(V_CSE_1, e.getCss1());
            values.put(V_CSE_2, e.getCss2());
            values.put(V_CSE_3, e.getCss3());
            values.put(V_CSE_4, e.getCss4());
            values.put(V_PTOS, e.getPuntos());
            values.put(V_CLSE, e.getClase());
            values.put(PUNTCAL, e.getPuntosCalif());
            values.put(CLSECAL, e.getClaseCalif());
            values.put(FECHCAL, e.getFechaCalif());
            values.put(V_SS1P, e.getCss1puntos());
            values.put(V_SS2P, e.getCss2puntos());
            values.put(V_SS3P, e.getCss3puntos());
            values.put(V_SS4P, e.getCss4puntos());
            values.put(COMEN, e.getComentario());

            updateDatos(TAB_V, values, ID + "=" + e.getId());
            return true;
        }catch(Exception ex){ex.printStackTrace();return ex.getMessage();}
        finally{CerrarBD();}
    }

    public Object actualizarCalifToro(Toro e){
        AbrirBD();
        try{
            ContentValues values = new ContentValues();
            values.put(T_C1 , e.getC1());
            values.put(T_C2 , e.getC2());
            values.put(T_C3 , e.getC3());
            values.put(T_C4 , e.getC4());
            values.put(T_SS1 , e.getSs1());
            values.put(T_SS1P, e.getSs1puntos());
            values.put(T_C5 , e.getC5());
            values.put(T_C6 , e.getC6());
            values.put(T_C7 , e.getC7());
            values.put(T_C8 , e.getC8());
            values.put(T_C9 , e.getC9());
            values.put(T_C10 , e.getC10());
            values.put(T_SS2 , e.getSs2());
            values.put(T_SS2P, e.getSs2puntos());
            values.put(T_C11 , e.getC11());
            values.put(T_C12 , e.getC12());
            values.put(T_C13 , e.getC13());
            values.put(T_C14 , e.getC14());
            values.put(T_C15 , e.getC15());
            values.put(T_SS3 , e.getSs3());
            values.put(T_SS3P, e.getSs3puntos());
            values.put(T_D10 , e.getDef10());
            values.put(T_D11 , e.getDef11());
            values.put(T_D40 , e.getDef40());
            values.put(T_D42 , e.getDef42());
            values.put(T_D43 , e.getDef43());
            values.put(T_D44 , e.getDef44());
            values.put(T_D45 , e.getDef45());
            values.put(T_D30 , e.getDef30());
            values.put(T_D31 , e.getDef31());
            values.put(T_D32 , e.getDef32());
            values.put(T_D34 , e.getDef34());
            values.put(T_D35 , e.getDef35());
            values.put(T_D36 , e.getDef36());
            values.put(COMEN , e.getComentarios());
            values.put(PUNTCAL, e.getPuntosCalif());
            values.put(CLSECAL, e.getClaseCalif());
            values.put(FECHCAL, e.getFechaCalif());

            updateDatos(TAB_T, values, ID + "=" + e.getId());
            return true;
        }catch(Exception ex){ex.printStackTrace();return ex.getMessage();}
        finally{CerrarBD();}
    }

    public Vaca obtenerVaca(int id){
        AbrirBD();
        try{
            String cols[] = {ID, HATO, FECHA,V_CURV,V_NOCP, NOREG, REGP, REGM, FECHANAC,
                    V_NOLAC,V_FECHAPAR, CALIFANT, CLASEANT,V_TIPOPAR,V_LACNOM,V_MESESAB, //16

                    V_C1,V_C2,V_C3,V_C4,V_C5,V_C24,V_C6,V_C7,V_C8,V_C8A,V_C9,V_C11,V_C12,V_C13,
                    V_C14,V_C15,V_C16,V_C17,V_C18,V_C19,V_C20,V_C21,V_C22,V_C23,//24

                    V_D10,V_D11,V_D12,V_D22,V_D23,V_D24,V_D25,V_D26,V_D27,V_D28,V_D29,V_D30,V_D31,V_D32,
                    V_D34,V_D35,V_D36,V_D40,V_D42,V_D43,V_D44,V_D45,V_D46,V_D47,V_D48, //25

                    V_CSE_1,V_CSE_2,V_CSE_3,V_CSE_4,  V_SS1P,V_SS2P,V_SS3P,V_SS4P,
                    V_PTOS,V_CLSE, PUNTCAL, CLSECAL, FECHCAL, COMEN, //14
                    SYNC,FECHCALANT
            };


            Cursor c = getDatos(TAB_V,cols,ID+"="+id,null,null);
            if (c.moveToFirst()){
                Log.d("ObtenerVACA","Se obtuvo una toro");
                Vaca vaca = new Vaca(
                        c.getInt(0),
                        c.getString(1),
                        c.getString(2),
                        c.getInt(3),
                        c.getString(4),
                        c.getString(5),
                        c.getString(6),
                        c.getString(7),
                        c.getString(8),
                        c.getInt(9),
                        c.getString(10),
                        c.getInt(11),
                        c.getString(12),
                        c.getString(13),
                        c.getInt(14),
                        c.getInt(15),

                        c.getInt(16),
                        c.getInt(17),
                        c.getInt(18),
                        c.getInt(19),
                        c.getInt(20),
                        c.getInt(21),
                        c.getInt(22),
                        c.getInt(23),
                        c.getInt(24),
                        c.getInt(25),
                        c.getInt(26),
                        c.getInt(27),
                        c.getInt(28),
                        c.getInt(29),
                        c.getInt(30),
                        c.getInt(31),
                        c.getInt(32),
                        c.getInt(33),
                        c.getInt(34),
                        c.getInt(35),
                        c.getInt(36),
                        c.getInt(37),
                        c.getInt(38),
                        c.getInt(39),

                        c.getInt(40),
                        c.getInt(41),
                        c.getInt(42),
                        c.getInt(43),
                        c.getInt(44),
                        c.getInt(45),
                        c.getInt(46),
                        c.getInt(47),
                        c.getInt(48),
                        c.getInt(49),
                        c.getInt(50),
                        c.getInt(51),
                        c.getInt(52),
                        c.getInt(53),
                        c.getInt(54),
                        c.getInt(55),
                        c.getInt(56),
                        c.getInt(57),
                        c.getInt(58),
                        c.getInt(59),
                        c.getInt(60),
                        c.getInt(61),
                        c.getInt(62),
                        c.getInt(63),
                        c.getInt(64),

                        c.getString(65),
                        c.getString(66),
                        c.getString(67),
                        c.getString(68),
                        c.getInt(69),
                        c.getInt(70),
                        c.getInt(71),
                        c.getInt(72),
                        c.getInt(73),
                        c.getString(74),
                        c.getInt(75),
                        c.getString(76),
                        c.getString(77),
                        c.getString(78),
                        c.getString(80)
                );
                if (c.getInt(79)==1)vaca.setSync(true);else vaca.setSync(false);
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

    public Toro obtenerToro(int id){
        AbrirBD();
        try{
            String cols[] = {ID, HATO, FECHA, NOREG, REGP, REGM, FECHANAC,
                    CALIFANT, CLASEANT,T_NOMB, //10

                    T_C1,T_C2,T_C3,T_C4,T_SS1,T_SS1P,
                    T_C5,T_C6,T_C7,T_C8,T_C9,T_C10,T_SS2,T_SS2P,
                    T_C11,T_C12,T_C13,T_C14,T_C15,T_SS3,T_SS3P, //21

                    T_D10,T_D11,T_D40,T_D42,T_D43,T_D44,T_D45,T_D30,T_D31,T_D32,T_D34,T_D35,T_D36,
                    PUNTCAL, CLSECAL, FECHCAL,COMEN, //17
                    SYNC
            };


            Cursor c = getDatos(TAB_T,cols,ID+"="+id,null,null);
            if (c.moveToFirst()){
                Toro toro = new Toro(
                        c.getInt(0),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        c.getString(4),
                        c.getString(5),
                        c.getString(6),
                        c.getString(7),
                        c.getString(8),
                        c.getString(9),

                        c.getInt(10),
                        c.getInt(11),
                        c.getInt(12),
                        c.getInt(13),
                        c.getString(14),
                        c.getInt(15),

                        c.getInt(16),
                        c.getInt(17),
                        c.getInt(18),
                        c.getInt(19),
                        c.getInt(20),
                        c.getInt(21),
                        c.getString(22),
                        c.getInt(23),

                        c.getInt(24),
                        c.getInt(25),
                        c.getInt(26),
                        c.getInt(27),
                        c.getInt(28),
                        c.getString(29),
                        c.getInt(30),
                        c.getInt(31),
                        c.getInt(32),
                        c.getInt(33),
                        c.getInt(34),
                        c.getInt(35),
                        c.getInt(36),
                        c.getInt(37),
                        c.getInt(38),
                        c.getInt(39),
                        c.getInt(40),
                        c.getInt(41),
                        c.getInt(42),
                        c.getInt(43),
                        c.getInt(44),
                        c.getString(45),
                        c.getString(46),
                        c.getString(47)

                );
                if (c.getInt(48)==1)toro.setSync(true);else toro.setSync(false);
                c.close();
                return toro;
            }
            else {
                c.close();
                return null;
            }
        }catch(Exception e){e.printStackTrace(); return null;}
        finally{CerrarBD();}
    }

    public Socio obtenerSocio(String hato){
        AbrirBD();
        try{
            String cols[] = {ID, S_NOHATO,S_NOM
            };


            Cursor c = getDatos(TAB_S,cols,S_NOHATO+"='"+hato+"'",null,null);
            if (c.moveToFirst()){
                Socio s = new Socio(c.getInt(0),c.getString(1),c.getString(2));
                c.close();
                return s;
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
            String cols[] = {ID, HATO, FECHA,V_CURV,V_NOCP, NOREG, REGP, REGM, FECHANAC,
                    V_NOLAC,V_FECHAPAR, CALIFANT, CLASEANT,V_TIPOPAR,V_LACNOM,V_MESESAB, //16

                    V_C1,V_C2,V_C3,V_C4,V_C5,V_C24,V_C6,V_C7,V_C8,V_C8A,V_C9,V_C11,V_C12,V_C13,
                    V_C14,V_C15,V_C16,V_C17,V_C18,V_C19,V_C20,V_C21,V_C22,V_C23,//24

                    V_D10,V_D11,V_D12,V_D22,V_D23,V_D24,V_D25,V_D26,V_D27,V_D28,V_D29,V_D30,V_D31,V_D32,
                    V_D34,V_D35,V_D36,V_D40,V_D42,V_D43,V_D44,V_D45,V_D46,V_D47,V_D48, //25

                    V_CSE_1,V_CSE_2,V_CSE_3,V_CSE_4,  V_SS1P,V_SS2P,V_SS3P,V_SS4P,
                    V_PTOS,V_CLSE, PUNTCAL, CLSECAL, FECHCAL, COMEN, //14
                    SYNC, FECHCALANT
            };

            Cursor c = getDatos(TAB_V,cols, HATO +"='"+hto+"' AND "+ FECHA +"='"+fecha+"'",V_NOCP,null);
            if (c.moveToFirst()){
                ArrayList<Vaca> ret = new ArrayList<Vaca>();
                do {
                    Vaca vaca = new Vaca(
                            c.getInt(0),
                            c.getString(1),
                            c.getString(2),
                            c.getInt(3),
                            c.getString(4),
                            c.getString(5),
                            c.getString(6),
                            c.getString(7),
                            c.getString(8),
                            c.getInt(9),
                            c.getString(10),
                            c.getInt(11),
                            c.getString(12),
                            c.getString(13),
                            c.getInt(14),
                            c.getInt(15),

                            c.getInt(16),
                            c.getInt(17),
                            c.getInt(18),
                            c.getInt(19),
                            c.getInt(20),
                            c.getInt(21),
                            c.getInt(22),
                            c.getInt(23),
                            c.getInt(24),
                            c.getInt(25),
                            c.getInt(26),
                            c.getInt(27),
                            c.getInt(28),
                            c.getInt(29),
                            c.getInt(30),
                            c.getInt(31),
                            c.getInt(32),
                            c.getInt(33),
                            c.getInt(34),
                            c.getInt(35),
                            c.getInt(36),
                            c.getInt(37),
                            c.getInt(38),
                            c.getInt(39),

                            c.getInt(40),
                            c.getInt(41),
                            c.getInt(42),
                            c.getInt(43),
                            c.getInt(44),
                            c.getInt(45),
                            c.getInt(46),
                            c.getInt(47),
                            c.getInt(48),
                            c.getInt(49),
                            c.getInt(50),
                            c.getInt(51),
                            c.getInt(52),
                            c.getInt(53),
                            c.getInt(54),
                            c.getInt(55),
                            c.getInt(56),
                            c.getInt(57),
                            c.getInt(58),
                            c.getInt(59),
                            c.getInt(60),
                            c.getInt(61),
                            c.getInt(62),
                            c.getInt(63),
                            c.getInt(64),

                            c.getString(65),
                            c.getString(66),
                            c.getString(67),
                            c.getString(68),
                            c.getInt(69),
                            c.getInt(70),
                            c.getInt(71),
                            c.getInt(72),
                            c.getInt(73),
                            c.getString(74),
                            c.getInt(75),
                            c.getString(76),
                            c.getString(77),
                            c.getString(78),
                            c.getString(80)
                    );
                    if (c.getInt(79)==1)vaca.setSync(true);else vaca.setSync(false);
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

    public ArrayList<Toro> obtenerToros(String hto,String fecha) throws Exception {
        AbrirBD();
        try{
            String cols[] = {ID, HATO, FECHA, NOREG, REGP, REGM, FECHANAC,
                    CALIFANT, CLASEANT,T_NOMB, //10

                    T_C1,T_C2,T_C3,T_C4,T_SS1,T_SS1P,
                    T_C5,T_C6,T_C7,T_C8,T_C9,T_C10,T_SS2,T_SS2P,
                    T_C11,T_C12,T_C13,T_C14,T_C15,T_SS3,T_SS3P, //21

                    T_D10,T_D11,T_D40,T_D42,T_D43,T_D44,T_D45,T_D30,T_D31,T_D32,T_D34,T_D35,T_D36,
                    PUNTCAL, CLSECAL, FECHCAL,COMEN, //17
                    SYNC
            };

            Cursor c = getDatos(TAB_T,cols, HATO +"='"+hto+"' AND "+ FECHA +"='"+fecha+"'",NOREG,null);
            if (c.moveToFirst()){
                ArrayList<Toro> ret = new ArrayList<Toro>();
                do {
                    Toro toro = new Toro(
                            c.getInt(0),
                            c.getString(1),
                            c.getString(2),
                            c.getString(3),
                            c.getString(4),
                            c.getString(5),
                            c.getString(6),
                            c.getString(7),
                            c.getString(8),
                            c.getString(9),

                            c.getInt(10),
                            c.getInt(11),
                            c.getInt(12),
                            c.getInt(13),
                            c.getString(14),
                            c.getInt(15),

                            c.getInt(16),
                            c.getInt(17),
                            c.getInt(18),
                            c.getInt(19),
                            c.getInt(20),
                            c.getInt(21),
                            c.getString(22),
                            c.getInt(23),

                            c.getInt(24),
                            c.getInt(25),
                            c.getInt(26),
                            c.getInt(27),
                            c.getInt(28),
                            c.getString(29),
                            c.getInt(30),
                            c.getInt(31),
                            c.getInt(32),
                            c.getInt(33),
                            c.getInt(34),
                            c.getInt(35),
                            c.getInt(36),
                            c.getInt(37),
                            c.getInt(38),
                            c.getInt(39),
                            c.getInt(40),
                            c.getInt(41),
                            c.getInt(42),
                            c.getInt(43),
                            c.getInt(44),
                            c.getString(45),
                            c.getString(46),
                            c.getString(47)
                    );
                    if (c.getInt(48)==0)toro.setSync(false);
                    else toro.setSync(true);
                    ret.add(toro);
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
            String cols[] = {" distinct V."+HATO, "V."+FECHA};
            Cursor c = getDatos(TAB_V+" V LEFT JOIN "+TAB_T+" T ON V."+HATO+"=T."+HATO,cols,null,null, "V."+HATO +", V."+ FECHA);
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

    public String torosIds() throws Exception {
        AbrirBD();
        try{
            String ids = "";
            String cols[] = {ID};
            Cursor c = getDatos(TAB_T,cols,null,null,null);
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
            String cols[] = {ID, HATO, FECHA,V_CURV,V_NOCP, NOREG, REGP, REGM, FECHANAC,
                    V_NOLAC,V_FECHAPAR, CALIFANT, CLASEANT,V_TIPOPAR,V_LACNOM,V_MESESAB, //16

                    V_C1,V_C2,V_C3,V_C4,V_C5,V_C24,V_C6,V_C7,V_C8,V_C8A,V_C9,V_C11,V_C12,V_C13,
                    V_C14,V_C15,V_C16,V_C17,V_C18,V_C19,V_C20,V_C21,V_C22,V_C23,//24

                    V_D10,V_D11,V_D12,V_D22,V_D23,V_D24,V_D25,V_D26,V_D27,V_D28,V_D29,V_D30,V_D31,V_D32,
                    V_D34,V_D35,V_D36,V_D40,V_D42,V_D43,V_D44,V_D45,V_D46,V_D47,V_D48, //25

                    V_CSE_1,V_CSE_2,V_CSE_3,V_CSE_4,  V_SS1P,V_SS2P,V_SS3P,V_SS4P,
                    V_PTOS,V_CLSE, PUNTCAL, CLSECAL, FECHCAL, COMEN, //14
                    SYNC, FECHCALANT
            };


            Cursor c = getDatos(TAB_V,cols,SYNC+"=0 and "+ FECHCAL +" is not null",null,null);
            if (c.moveToFirst()){
                ArrayList<Vaca> vacas = new ArrayList<Vaca>();
                do {
                    Vaca vaca = new Vaca(
                            c.getInt(0),
                            c.getString(1),
                            c.getString(2),
                            c.getInt(3),
                            c.getString(4),
                            c.getString(5),
                            c.getString(6),
                            c.getString(7),
                            c.getString(8),
                            c.getInt(9),
                            c.getString(10),
                            c.getInt(11),
                            c.getString(12),
                            c.getString(13),
                            c.getInt(14),
                            c.getInt(15),

                            c.getInt(16),
                            c.getInt(17),
                            c.getInt(18),
                            c.getInt(19),
                            c.getInt(20),
                            c.getInt(21),
                            c.getInt(22),
                            c.getInt(23),
                            c.getInt(24),
                            c.getInt(25),
                            c.getInt(26),
                            c.getInt(27),
                            c.getInt(28),
                            c.getInt(29),
                            c.getInt(30),
                            c.getInt(31),
                            c.getInt(32),
                            c.getInt(33),
                            c.getInt(34),
                            c.getInt(35),
                            c.getInt(36),
                            c.getInt(37),
                            c.getInt(38),
                            c.getInt(39),

                            c.getInt(40),
                            c.getInt(41),
                            c.getInt(42),
                            c.getInt(43),
                            c.getInt(44),
                            c.getInt(45),
                            c.getInt(46),
                            c.getInt(47),
                            c.getInt(48),
                            c.getInt(49),
                            c.getInt(50),
                            c.getInt(51),
                            c.getInt(52),
                            c.getInt(53),
                            c.getInt(54),
                            c.getInt(55),
                            c.getInt(56),
                            c.getInt(57),
                            c.getInt(58),
                            c.getInt(59),
                            c.getInt(60),
                            c.getInt(61),
                            c.getInt(62),
                            c.getInt(63),
                            c.getInt(64),

                            c.getString(65),
                            c.getString(66),
                            c.getString(67),
                            c.getString(68),
                            c.getInt(69),
                            c.getInt(70),
                            c.getInt(71),
                            c.getInt(72),
                            c.getInt(73),
                            c.getString(74),
                            c.getInt(75),
                            c.getString(76),
                            c.getString(77),
                            c.getString(78),
                            c.getString(80)
                    );
                    if (c.getInt(79)==1)vaca.setSync(true);else vaca.setSync(false);
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

    public ArrayList<Toro> obtenerTorosParaEnvio(){
        AbrirBD();
        try{
            String cols[] = {ID, HATO, FECHA, NOREG, REGP, REGM, FECHANAC, //7
                    CALIFANT, CLASEANT,T_NOMB, //3

                    T_C1,T_C2,T_C3,T_C4,T_SS1,T_SS1P, //6
                    T_C5,T_C6,T_C7,T_C8,T_C9,T_C10,T_SS2,T_SS2P, //8
                    T_C11,T_C12,T_C13,T_C14,T_C15,T_SS3,T_SS3P, //7

                    T_D10,T_D11,T_D40,T_D42,T_D43,T_D44,T_D45,T_D30,T_D31,T_D32,T_D34,T_D35,T_D36,//13
                    PUNTCAL, CLSECAL, FECHCAL,COMEN,SYNC //5
            };


            Cursor c = getDatos(TAB_T,cols,SYNC+"=0 and "+ FECHCAL +" is not null",null,null);
            if (c.moveToFirst()){
                ArrayList<Toro> toros = new ArrayList<Toro>();
                do {
                    Toro toro = new Toro(
                            c.getInt(0),
                            c.getString(1),
                            c.getString(2),
                            c.getString(3),
                            c.getString(4),
                            c.getString(5),
                            c.getString(6),
                            c.getString(7),
                            c.getString(8),
                            c.getString(9),
                            c.getInt(10),
                            c.getInt(11),
                            c.getInt(12),
                            c.getInt(13),
                            c.getString(14),
                            c.getInt(15),
                            c.getInt(16),
                            c.getInt(17),
                            c.getInt(18),
                            c.getInt(19),
                            c.getInt(20),
                            c.getInt(21),
                            c.getString(22),
                            c.getInt(23),
                            c.getInt(24),
                            c.getInt(25),
                            c.getInt(26),
                            c.getInt(27),
                            c.getInt(28),
                            c.getString(29),
                            c.getInt(30),
                            c.getInt(31),
                            c.getInt(32),
                            c.getInt(33),
                            c.getInt(34),
                            c.getInt(35),
                            c.getInt(36),
                            c.getInt(37),
                            c.getInt(38),
                            c.getInt(39),
                            c.getInt(40),
                            c.getInt(41),
                            c.getInt(42),
                            c.getInt(43),
                            c.getInt(44),
                            c.getString(45),
                            c.getString(46),
                            c.getString(47)

                    );
                    if(c.getInt(48)==1)toro.setSync(true);else toro.setSync(false);
                    toros.add(toro);
                }while(c.moveToNext());
                c.close();
                return toros;
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

    public Object sincronizadoToros(ArrayList<Toro> toros){
        AbrirBD();
        try{
            for (Toro t : toros) {
                ContentValues values = new ContentValues();
                values.put(SYNC, 1);
                updateDatos(TAB_T, values, ID + "=" + t.getId());
            }
            return true;
        }catch(Exception ex){ex.printStackTrace();return ex.getMessage();}
        finally{CerrarBD();}
    }
}
