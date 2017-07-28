package com.abetancourt.produccion;


/**
 * Created by alex on 3/10/15.
 */
public class Vaca {

    private int id;
    private String NoHato;
    private String NoVaca;
    private int Curv;
    private int NumOrd;
    private int Lab;
    private String FechaEst;
    private String CodEst;
    private String FechaPrueba;
    private String Hora1Ini;
    private String Hora1Fin;
    private String Hora2Ini;
    private String Hora2Fin;
    private String Hora3Ini;
    private String Hora3Fin;
    private int Peso1=0;
    private int Peso2=0;
    private int Peso3=0;
    private int LoteT;
    private String NoVial;
    private String Fecha;
    private String OrdVial;
    private String Peso1Hr;
    private String Peso2Hr;
    private String Peso3Hr;

    private boolean sync;

    /**
     * Constructor para el recibo de la Vaca
     * @param id Id de tabla
     * @param noHato Número de Hato
     * @param noVaca Número de vaca o Medalla
     * @param curv Numero interno de Holstein
     * @param fechaEst Fecha del último Estado
     * @param codEst Codigo del último estado
     *               Valores posibles para CodEst
     *               1,2 = parida
     *               3= comprada seca
     *               4= comprada en leche
     *               5= aborto
     *               6= seca
     *               7= vendida
     *               8= rastro
     *               9= muerta
     * @param fecha Fecha en que se genera el reporte
     */
    public Vaca(int id, String noHato, String noVaca, int curv, String fechaEst, String codEst,
                String fecha) {
        this.id = id;
        NoHato = noHato;
        NoVaca = noVaca;
        Curv = curv;
        FechaEst = fechaEst;
        CodEst = codEst;
        Fecha = fecha;
    }

    /**
     * Constructor con todos los valores para Vaca
     * @param id Id de la tabla
     * @param noHato ** X1...X6 varchar(10) Número de hato
     * @param noVaca ** X1...X6 varchar(6) No. de Vaca o NoCP.
     * @param curv ** 999999 int Identificador único.
     * @param numOrd 9 tinyint Número de ordeñas 2 ó 3
     * @param lab 9 tinyint Laboratorio 1= Si, 0= No
     * @param fechaEst ** dd/mm/aaaa datetime Fecha del estado de la vaca.
     * @param codEst ** 9 varchar(1) Código del estado de la vaca.
     * @param fechaPrueba dd/mm/aaaa datetime Fecha cuando se muestrea el hato.
     * @param hora1Ini hh:mm varchar(5) Horario de inicio de la ordeña 1
     * @param hora1Fin hh:mm varchar(5) Horario final de la ordeña 1
     * @param hora2Ini hh:mm varchar(5) Horario de inicio de la ordeña 2
     * @param hora2Fin hh:mm varchar(5) Horario final de la ordeña 2
     * @param hora3Ini hh:mm varchar(5) Horario de inicio de la ordeña 3
     * @param hora3Fin hh:mm varchar(5) Horario final de la ordeña 3
     * @param peso1 999 smallint Peso en kgs. de la ordeña 1 multiplicado *10
     * @param peso2 999 smallint Peso en kgs. de la ordeña 2 multiplicado *10
     * @param peso3 999 smallint Peso en kgs. de la ordeña 3 multiplicado *10
     * @param loteT 9 tinyint Número de lote o corral (0 a 9)
     * @param noVial 999999 varcha(6) Número de vial en el que se toma la muestra de leche.
     * @param fecha Fecha en que se genera el reporte
     * @param ordVial Orden en la que se captura el Vial
     * @param peso1hr Hora de pesaje de la ordeña 1
     * @param peso2hr Hora de pesaje de la ordeña 2
     * @param peso3hr Hora de pesaje de la ordeña 3
     */
    public Vaca(int id, String noHato, String noVaca, int curv, int numOrd, int lab,
                String fechaEst, String codEst, String fechaPrueba, String hora1Ini,
                String hora1Fin, String hora2Ini, String hora2Fin, String hora3Ini,
                String hora3Fin, int peso1, int peso2, int peso3, int loteT, String noVial,
                String fecha, String ordVial, String peso1hr, String peso2hr, String peso3hr) {
        this.id = id;
        NoHato = noHato;
        NoVaca = noVaca;
        Curv = curv;
        NumOrd = numOrd;
        Lab = lab;
        FechaEst = fechaEst;
        CodEst = codEst;
        FechaPrueba = fechaPrueba;
        Hora1Ini = hora1Ini;
        Hora1Fin = hora1Fin;
        Hora2Ini = hora2Ini;
        Hora2Fin = hora2Fin;
        Hora3Ini = hora3Ini;
        Hora3Fin = hora3Fin;
        Peso1 = peso1;
        Peso2 = peso2;
        Peso3 = peso3;
        LoteT = loteT;
        NoVial = noVial;
        Fecha = fecha;
        OrdVial = ordVial;
        Peso1Hr = peso1hr;
        Peso2Hr = peso2hr;
        Peso3Hr = peso3hr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoHato() {
        return NoHato;
    }

    public void setNoHato(String noHato) {
        NoHato = noHato;
    }

    public String getNoVaca() {
        return NoVaca;
    }

    public void setNoVaca(String noVaca) {
        NoVaca = noVaca;
    }

    public int getCurv() {
        return Curv;
    }

    public void setCurv(int curv) {
        Curv = curv;
    }

    public int getNumOrd() {
        return NumOrd;
    }

    public void setNumOrd(int numOrd) {
        NumOrd = numOrd;
    }

    public int getLab() {
        return Lab;
    }

    public void setLab(int lab) {
        Lab = lab;
    }

    public String getFechaEst() {
        return FechaEst;
    }

    public void setFechaEst(String fechaEst) {
        FechaEst = fechaEst;
    }

    public String getCodEst() {
        return CodEst;
    }

    public void setCodEst(String codEst) {
        CodEst = codEst;
    }

    public String getFechaPrueba() {
        return FechaPrueba;
    }

    public void setFechaPrueba(String fechaPrueba) {
        FechaPrueba = fechaPrueba;
    }

    public String getHora1Ini() {
        return Hora1Ini;
    }

    public void setHora1Ini(String hora1Ini) {
        Hora1Ini = hora1Ini;
    }

    public String getHora1Fin() {
        return Hora1Fin;
    }

    public void setHora1Fin(String hora1Fin) {
        Hora1Fin = hora1Fin;
    }

    public String getHora2Ini() {
        return Hora2Ini;
    }

    public void setHora2Ini(String hora2Ini) {
        Hora2Ini = hora2Ini;
    }

    public String getHora2Fin() {
        return Hora2Fin;
    }

    public void setHora2Fin(String hora2Fin) {
        Hora2Fin = hora2Fin;
    }

    public String getHora3Ini() {
        return Hora3Ini;
    }

    public void setHora3Ini(String hora3Ini) {
        Hora3Ini = hora3Ini;
    }

    public String getHora3Fin() {
        return Hora3Fin;
    }

    public void setHora3Fin(String hora3Fin) {
        Hora3Fin = hora3Fin;
    }

    public int getPeso1() {
        return Peso1;
    }

    public void setPeso1(int peso1) {
        Peso1 = peso1;
    }

    public int getPeso2() {
        return Peso2;
    }

    public void setPeso2(int peso2) {
        Peso2 = peso2;
    }

    public int getPeso3() {
        return Peso3;
    }

    public void setPeso3(int peso3) {
        Peso3 = peso3;
    }

    public int getLoteT() {
        return LoteT;
    }

    public void setLoteT(int loteT) {
        LoteT = loteT;
    }

    public String getNoVial() {
        return NoVial;
    }

    public void setNoVial(String noVial) {
        NoVial = noVial;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public boolean isSync() {
        return sync;
    }

    public void setSync(boolean sync) {
        this.sync = sync;
    }

    public String getOrdVial() {
        return OrdVial;
    }

    public void setOrdVial(String ordVial) {
        OrdVial = ordVial;
    }

    public String getPeso1Hr() {
        return Peso1Hr;
    }

    public void setPeso1Hr(String peso1Hr) {
        Peso1Hr = peso1Hr;
    }

    public String getPeso2Hr() {
        return Peso2Hr;
    }

    public void setPeso2Hr(String peso2Hr) {
        Peso2Hr = peso2Hr;
    }

    public String getPeso3Hr() {
        return Peso3Hr;
    }

    public void setPeso3Hr(String peso3Hr) {
        Peso3Hr = peso3Hr;
    }

}
