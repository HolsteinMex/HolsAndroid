
package com.alexbetancourt.holsteinsocio;

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
	private static final String N_BD = "hlstn"; //NOMBRE DE BASE DE DATOS
    public static final String ID = "id"; //ID para todas las tablas

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");


    //// TABLA ESTADISTICOS ////
    public static final String TAB_E = "Estadisticos";
    public static final String E_HTO = "NoHato";
    public static final String E_FECH_VSTA = "FechaVisita";
    public static final String E_REG = "Region";
    public static final String E_EST = "Estado";
    public static final String E_VACAHATO = "VacasHato";
    public static final String E_LECHEKGH = "LechekgH";
    public static final String E_LECHEEM = "LecheEM";
    public static final String E_DIASLECHE = "DiasLeche";
    public static final String E_PVACASC = "PVacasCarga";
    public static final String E_DIASP = "DiasPico";
    public static final String E_LECHEP = "LechePico";
    public static final String E_PDESH = "PDesecho";
    public static final String E_EDADMM = "EdadMM";
    public static final String E_DIAS1S = "Dias1Serv";
    public static final String E_PFERTIL1 = "PFertil1Serv";
    public static final String E_INTERS = "InterServ";
    public static final String E_SERVX = "ServxConc";
    public static final String E_DIASAB = "DiasAb";
    public static final String E_INTERPAR = "InterPartos";
    public static final String E_PABORT = "PAbortos";
    public static final String E_PGRASA = "PGrasa";
    public static final String E_PPROT = "PProteina";
    public static final String E_PSOLID = "PSolidos";
    public static final String E_NITROG = "NitrogU";
    public static final String E_CCMIL = "CCSMiles";

    //// TABLA EST_GEN ////
    public static final String TAB_G = "Estad_NacReg";
    public static final String G_TIPO = "Tipo";
    public static final String G_FPROC = "FechaProceso";

    public static final String G_NAC = "NAC";
    public static final String G_R1 = "RG1";
    public static final String G_R2 = "RG2";
    public static final String G_R3 = "RG3";
    public static final String G_R4 = "RG4";
    public static final String G_R5 = "RG5";

    //// TABLA REGIONES ////
    public static final String TAB_R = "Regiones";
    public static final String R_REG = "Region";
    public static final String R_NOM = "Nombre";

    //// TABLA ESTADOS ////
    public static final String TAB_S = "Estados";
    public static final String S_CVE = "ClaveEdo";
    public static final String S_ABREV = "Abrev";


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
	 * Crea un Cursor con una funci�n Gen�rica
	 * @param table Tabla a consultar
	 * @param columns Array de los Nombres de columas que se desea consultar
	 * @param where Cl�usula WHERE, escriba null si no existe
	 * @param groupBy Agrupamientos GROUPBY, escriba null si no existe
	 * @param having Cl�usula HAVING, escriba null si no existe
	 * @param orderBy Ordenamiento por nombre de columna, escriba null si no existe
	 * @param limit limite, escriba null si no existe
	 * @return Cursor que contiene la informaci�n obtenida
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
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd k:m:s.S");
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
            db.execSQL("CREATE TABLE "+TAB_E+" (" +
                "    ["+ID+"] integer NOT NULL PRIMARY KEY," +
                "    ["+E_HTO+"] TEXT NOT NULL," +
                "    ["+E_FECH_VSTA+"] datetime NOT NULL," +
                "    ["+E_REG+"] integer NOT NULL," +
                "    ["+E_EST+"] integer NOT NULL," +
                "    ["+E_VACAHATO+"] integer NOT NULL," +
                "    ["+E_LECHEKGH+"] decimal NOT NULL," +
                "    ["+E_LECHEEM+"] decimal NOT NULL," +
                "    ["+E_DIASLECHE+"] decimal NOT NULL," +
                "    ["+E_PVACASC+"] decimal NOT NULL," +
                "    ["+E_DIASP+"] decimal NOT NULL," +
                "    ["+E_LECHEP+"] decimal NOT NULL," +
                "    ["+E_PDESH+"] integer NOT NULL," +
                "    ["+E_EDADMM+"] decimal NOT NULL," +
                "    ["+E_DIAS1S+"] integer NOT NULL," +
                "    ["+E_PFERTIL1+"] integer NOT NULL," +
                "    ["+E_INTERS+"] decimal NOT NULL," +
                "    ["+E_SERVX+"] decimal NOT NULL," +
                "    ["+E_DIASAB+"] integer NOT NULL," +
                "    ["+E_INTERPAR+"] decimal NOT NULL," +
                "    ["+E_PABORT+"] integer NOT NULL," +
                "    ["+E_PGRASA+"] decimal NOT NULL," +
                "    ["+E_PPROT+"] decimal NOT NULL," +
                "    ["+E_PSOLID+"] decimal NOT NULL," +
                "    ["+E_NITROG+"] decimal NOT NULL," +
                "    ["+E_CCMIL+"] integer NOT NULL" +
                ")");
            db.execSQL("CREATE TABLE ["+TAB_G+"] (" +
                    "    ["+ID+"] integer NOT NULL PRIMARY KEY," +
                    "    ["+G_TIPO+"] TEXT NOT NULL," +
                    "    ["+G_FPROC+"] datetime NOT NULL," +
                    "    ["+E_VACAHATO+"] integer NOT NULL," +
                    "    ["+E_LECHEKGH+"] decimal NOT NULL," +
                    "    ["+E_LECHEEM+"] decimal NOT NULL," +
                    "    ["+E_DIASLECHE+"] decimal NOT NULL," +
                    "    ["+E_PVACASC+"] decimal NOT NULL," +
                    "    ["+E_DIASP+"] decimal NOT NULL," +
                    "    ["+E_LECHEP+"] decimal NOT NULL," +
                    "    ["+E_PDESH+"] integer NOT NULL," +
                    "    ["+E_EDADMM+"] decimal NOT NULL," +
                    "    ["+E_DIAS1S+"] integer NOT NULL," +
                    "    ["+E_PFERTIL1+"] integer NOT NULL," +
                    "    ["+E_INTERS+"] decimal NOT NULL," +
                    "    ["+E_SERVX+"] decimal NOT NULL," +
                    "    ["+E_DIASAB+"] integer NOT NULL," +
                    "    ["+E_INTERPAR+"] decimal NOT NULL," +
                    "    ["+E_PABORT+"] integer NOT NULL," +
                    "    ["+E_PGRASA+"] decimal NOT NULL," +
                    "    ["+E_PPROT+"] decimal NOT NULL," +
                    "    ["+E_PSOLID+"] decimal NOT NULL," +
                    "    ["+E_NITROG+"] decimal NOT NULL," +
                    "    ["+E_CCMIL+"] integer NOT NULL" +
                    ")");
            db.execSQL("CREATE TABLE "+TAB_R+" (" +
                    "    ["+R_REG+"] integer NOT NULL," +
                    "    ["+R_NOM+"] varchar(50) NOT NULL" +
                    ")");
            db.execSQL("CREATE TABLE "+TAB_S+" (" +
                    "    ["+S_CVE+"] integer NOT NULL," +
                    "    ["+S_ABREV+"] varchar(6) NOT NULL" +
                    ")");

            ///*** INSERTS PARA ESTADOS ***///
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(1,'AGS.')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(2,'B.C.N')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(3,'B.C.S.')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(4,'CAMP.')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(5,'COAH.')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(6,'COL.')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(7,'CHIS.')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(8,'CHIH.')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(9,'D.F.')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(10,'DGO.')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(11,'GTO.')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(12,'GRO.')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(13,'HGO.')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(14,'JAL.')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(15,'EDOMEX')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(16,'MICH.')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(17,'MOR.')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(18,'NAY.')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(19,'N.L.')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(20,'OAX.')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(21,'PUE.')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(22,'QRO.')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(23,'Q.R.')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(24,'S.L.P.')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(25,'SIN.')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(26,'SON.')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(27,'TAB.')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(28,'TAMPS.')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(29,'TLAX.')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(30,'VER.')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(31,'YUC.')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(32,'ZAC.')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(40,'N.P.S.')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(41,'INVE.')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(50,'NO SOC')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(51,'USA')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(52,'CAN')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(53,'HOL')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(54,'FRA')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(55,'ITA')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(56,'ESP')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(57,'ALE')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(58,'BRAZIL')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(59,'CHILE')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(60,'MEMBRE')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(61,'POSTA')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(62,'BOLIV')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(63,'PERU')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(64,'HONDU')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(65,'COPROP')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(66,'COLOMB')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(67,'ECU')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(68,'ClteRe')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(69,'SUSCR')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(70,'L.T.E.')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(71,'GUAT')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(72,'PANAM')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(73,'C.RICA')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(74,'ARG')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(75,'AUS')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(80,'L.P.S.')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(90,'JERSEY')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(91,'CAPRIN')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(92,'ROMOSI')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(95,'COADYU')");
            db.execSQL("INSERT INTO ["+TAB_S+"] (["+S_CVE+"],["+S_ABREV+"])VALUES(99,'SINED')");

            ///*** INSERTS REGIONES ***///
            db.execSQL("INSERT INTO ["+TAB_R+"] (["+R_REG+"],["+R_NOM+"])VALUES(1,'B.C., Coah, Chih, Dgo., Son.')");
            db.execSQL("INSERT INTO ["+TAB_R+"] (["+R_REG+"],["+R_NOM+"])VALUES(2,'Gto., Hgo., Mex., Qro., SLP')");
            db.execSQL("INSERT INTO ["+TAB_R+"] (["+R_REG+"],["+R_NOM+"])VALUES(3,'Col., Jal., Mich., Nay., Sin.')");
            db.execSQL("INSERT INTO ["+TAB_R+"] (["+R_REG+"],["+R_NOM+"])VALUES(4,'Ags., Zac.')");
            db.execSQL("INSERT INTO ["+TAB_R+"] (["+R_REG+"],["+R_NOM+"])VALUES(5,'Chis., Gro., Mor., Pue., Tlax., Ver.')");
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
	public Cursor getDatos(String TableName, String [] Columnas, String Where,String OrderBy) throws Exception {
        Cursor resultado = nBD.query(TableName, Columnas, Where, null, null, null, OrderBy);
        return resultado;
	}

	/**
	 * Elimina los datos de una tabla dentro de la base de datos
	 * @param TableName - Nombre de la tabla de donde se eliminar�n los datos
	 * @param Where - Condici�n utilizada para eliminar los datos
	 * @return
	 */
	//Elimina los datos de una tabla
	public int deleteDatos (String TableName, String Where) {
		int result = nBD.delete(TableName, Where, null);
		return result;
	}

    public Object insertarEstadistico(Estadistico e){
        AbrirBD();
        try{
            ContentValues values = new ContentValues();
            values.put(ID, e.getId());
            values.put(E_HTO ,e.getNoHato());
            values.put(E_FECH_VSTA, format.format(e.getFechaVisita()));
            values.put(E_REG, e.getRegion());
            values.put(E_EST, e.getEstado());
            values.put(E_VACAHATO, e.getVacasHato());
            values.put(E_LECHEKGH, e.getLechekgH());
            values.put(E_LECHEEM, e.getLecheEM());
            values.put(E_DIASLECHE, e.getDiasLeche());
            values.put(E_PVACASC, e.getpVacasCarga());
            values.put(E_DIASP, e.getDiasPico());
            values.put(E_LECHEP, e.getLechePico());
            values.put(E_PDESH, e.getpDesecho());
            values.put(E_EDADMM, e.getEdadMM());
            values.put(E_DIAS1S, e.getDias1Serv());
            values.put(E_PFERTIL1, e.getpFertil1Serv());
            values.put(E_INTERS, e.getInterServ());
            values.put(E_SERVX, e.getServxConc());
            values.put(E_DIASAB, e.getDiasAb());
            values.put(E_INTERPAR, e.getInterPartos());
            values.put(E_PABORT, e.getpAbortos());
            values.put(E_PGRASA, e.getpGrasa());
            values.put(E_PPROT, e.getpProteina());
            values.put(E_PSOLID, e.getpSolidos());
            values.put(E_NITROG, e.getNitrogU());
            values.put(E_CCMIL, e.getcCSMiles());

            insertaDatos(TAB_E,values);
            return true;
        }catch(Exception ex){ex.printStackTrace();return ex.getMessage();}
        finally{CerrarBD();}
    }

    public void limpiaEstGeneral(){
        try{
            AbrirBD();
            nBD.execSQL("DELETE FROM "+TAB_G+" ");
        }catch(Exception e){e.printStackTrace();}
        finally{CerrarBD();}
    }

    public Object insertarEstGeneral(ArrayList<EstGeneral> est){
        AbrirBD();
        try{
            for (EstGeneral e:est) {
                ContentValues values = new ContentValues();
                values.put(ID, e.getId());
                values.put(G_TIPO, e.getTipo());
                values.put(G_FPROC, e.getFechaProceso());
                values.put(E_VACAHATO, e.getVacasHato());
                values.put(E_LECHEKGH, e.getLechekgH());
                values.put(E_LECHEEM, e.getLecheEM());
                values.put(E_DIASLECHE, e.getDiasLeche());
                values.put(E_PVACASC, e.getpVacasCarga());
                values.put(E_DIASP, e.getDiasPico());
                values.put(E_LECHEP, e.getLechePico());
                values.put(E_PDESH, e.getpDesecho());
                values.put(E_EDADMM, e.getEdadMM());
                values.put(E_DIAS1S, e.getDias1Serv());
                values.put(E_PFERTIL1, e.getpFertil1Serv());
                values.put(E_INTERS, e.getInterServ());
                values.put(E_SERVX, e.getServxConc());
                values.put(E_DIASAB, e.getDiasAb());
                values.put(E_INTERPAR, e.getInterPartos());
                values.put(E_PABORT, e.getpAbortos());
                values.put(E_PGRASA, e.getpGrasa());
                values.put(E_PPROT, e.getpProteina());
                values.put(E_PSOLID, e.getpSolidos());
                values.put(E_NITROG, e.getNitrogU());
                values.put(E_CCMIL, e.getcCSMiles());
                insertaDatos(TAB_G, values);
            }
            return true;
        }catch(Exception ex){ex.printStackTrace();return ex.getMessage();}
        finally{CerrarBD();}
    }

    public Estadistico obtenerEstadistico(String id){
        AbrirBD();
        try{
            String cols[] = {ID,E_HTO,E_FECH_VSTA,E_REG,E_EST,E_VACAHATO,E_LECHEKGH,E_LECHEEM,
                    E_DIASLECHE,E_PVACASC,E_DIASP,E_LECHEP,E_PDESH,E_EDADMM,E_DIAS1S,
                    E_PFERTIL1,E_INTERS,E_SERVX,E_DIASAB,E_INTERPAR,E_PABORT,E_PGRASA,E_PPROT,
                    E_PSOLID,E_NITROG,E_CCMIL};
            Cursor c = getDatos(TAB_E,cols,ID+"="+id,null);
            if (c.moveToFirst()){
                Estadistico est = new Estadistico(
                        c.getInt(0),
                        c.getString(1),
                        format.parse(c.getString(2)),
                        c.getString(3),
                        c.getString(4),
                        c.getInt(5),
                        c.getDouble(6),
                        c.getDouble(7),
                        c.getDouble(8),
                        c.getDouble(9),
                        c.getDouble(10),
                        c.getDouble(11),
                        c.getInt(12),
                        c.getDouble(13),
                        c.getInt(14),
                        c.getInt(15),
                        c.getDouble(16),
                        c.getDouble(17),
                        c.getInt(18),
                        c.getDouble(19),
                        c.getDouble(20),
                        c.getDouble(21),
                        c.getDouble(22),
                        c.getDouble(23),
                        c.getDouble(24),
                        c.getInt(25)
                );
                return est;
            }
            else return null;
        }catch(Exception e){e.printStackTrace(); return null;}
        finally{CerrarBD();}
    }

    public ArrayList<EstGeneral> obtenerEstGeneral(String Estado) throws Exception {
        Log.d("Estado","Estado:'"+Estado+"'");
        AbrirBD();
        try{
            String cols[] = {ID,G_TIPO,G_FPROC,E_VACAHATO,E_LECHEKGH,E_LECHEEM,
                    E_DIASLECHE,E_PVACASC,E_DIASP,E_LECHEP,E_PDESH,E_EDADMM,E_DIAS1S,
                    E_PFERTIL1,E_INTERS,E_SERVX,E_DIASAB,E_INTERPAR,E_PABORT,E_PGRASA,E_PPROT,
                    E_PSOLID,E_NITROG,E_CCMIL};
            String where = G_TIPO+"='"+G_NAC+"' OR "+
                    G_TIPO+"='"+G_R1+"' OR "+
                    G_TIPO+"='"+G_R2+"' OR "+
                    G_TIPO+"='"+G_R3+"' OR "+
                    G_TIPO+"='"+G_R4+"' OR "+
                    G_TIPO+"='"+G_R5+"' OR "+
                    G_TIPO+"='"+Estado+"' ";

            Cursor c = getDatos(TAB_G,cols,where,G_TIPO);
            if (c.moveToFirst()){
                ArrayList<EstGeneral> ret = new ArrayList<EstGeneral>();
                do {
                    EstGeneral est = new EstGeneral(
                            c.getInt(0),
                            c.getString(1),
                            c.getString(2),
                            c.getInt(3),
                            c.getDouble(4),
                            c.getInt(5),
                            c.getDouble(6),
                            c.getDouble(7),
                            c.getDouble(8),
                            c.getDouble(9),
                            c.getInt(10),
                            c.getDouble(11),
                            c.getInt(12),
                            c.getInt(13),
                            c.getInt(14),
                            c.getInt(15),
                            c.getInt(16),
                            c.getDouble(17),
                            c.getInt(18),
                            c.getDouble(19),
                            c.getDouble(20),
                            c.getDouble(21),
                            c.getDouble(22),
                            c.getInt(23)
                    );
                    try{
                        int estado = Integer.parseInt(c.getString(1));
                        est.setTipo(obtenerEstado(estado));
                    }catch(Exception ex){}
                    ret.add(est);
                }while (c.moveToNext());
                return ret;
            }
            else return null;
        }catch(Exception e){e.printStackTrace(); throw e;}
        finally{CerrarBD();}
    }


    public String idsEstadisticos() throws Exception {
        AbrirBD();
        try{
            Cursor c = getDatos(TAB_E,new String[]{ID},null,null);
            if (c.moveToFirst()){
                String ret = "";
                do{
                    ret+=c.getString(0)+",";
                }while(c.moveToNext());
                return ret;
            }
            return "0,";
        }catch (Exception e){throw e;}
        finally{CerrarBD();}
    }

    public ArrayList<String[]> obtenerListaReportes() throws Exception {
        AbrirBD();
        try{
            Cursor c = getDatos(TAB_E,new String[]{E_HTO,E_FECH_VSTA,ID},null,E_FECH_VSTA+" DESC");
            if (c.moveToFirst()){
                ArrayList<String[]> reportes = new ArrayList<String[]>();
                do{
                    String s[] = new String[3];
                    s[0] = c.getString(0);
                    s[1] = c.getString(1);
                    s[2] = c.getString(2);
                    reportes.add(s);
                }while(c.moveToNext());
                return reportes;
            }
            return null;
        }catch (Exception e){throw e;}
        finally{CerrarBD();}
    }

    public String ultimoProceso()throws Exception{
        AbrirBD();
        try{
            Cursor c = getDatos(TAB_G,new String[]{G_FPROC},G_TIPO+"='NAC'",null); //TODO Verificar NAC en tabla Holstein
            if (c.moveToFirst()){
                return c.getString(0);
            }
            return null;
        }catch (Exception e){throw e;}
        finally{CerrarBD();}
    }

    public String obtenerEstado(int clave)throws Exception{
        AbrirBD();
        try{
            Cursor c = getDatos(TAB_S,new String[]{S_ABREV},S_CVE+"='"+clave+"'",null); //TODO Verificar NAC en tabla Holstein
            if (c.moveToFirst()){
                return c.getString(0);
            }
            return null;
        }catch (Exception e){throw e;}
        finally{CerrarBD();}
    }

}
