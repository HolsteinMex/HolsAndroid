package com.alexbetancourt.holsteinsocio;

/**
 * Created by Alex on 20/06/2015.
 */
public class EstGeneral {

    private int id;
    private String tipo;
    private String fechaProceso;
    private int vacasHato;
    private double lechekgH;
    private double lecheEM;
    private double diasLeche;
    private double pVacasCarga;
    private double diasPico;
    private double lechePico;
    private int pDesecho;
    private double edadMM;
    private int dias1Serv;
    private int pFertil1Serv;
    private double interServ;
    private double servxConc;
    private int diasAb;
    private double interPartos;
    private double pAbortos;
    private double pGrasa;
    private double pProteina;
    private double pSolidos;
    private double nitrogU;
    private int cCSMiles;

    public EstGeneral(int id, String tipo, String fechaProceso, int vacasHato, double lechekgH, double lecheEM, double diasLeche, double pVacasCarga, double diasPico, double lechePico, int pDesecho, double edadMM, int dias1Serv, int pFertil1Serv, double interServ, double servxConc, int diasAb, double interPartos, double pAbortos, double pGrasa, double pProteina, double pSolidos, double nitrogU, int cCSMiles) {
        this.id = id;
        this.tipo = tipo;
        this.fechaProceso = fechaProceso;
        this.vacasHato = vacasHato;
        this.lechekgH = lechekgH;
        this.lecheEM = lecheEM;
        this.diasLeche = diasLeche;
        this.pVacasCarga = pVacasCarga;
        this.diasPico = diasPico;
        this.lechePico = lechePico;
        this.pDesecho = pDesecho;
        this.edadMM = edadMM;
        this.dias1Serv = dias1Serv;
        this.pFertil1Serv = pFertil1Serv;
        this.interServ = interServ;
        this.servxConc = servxConc;
        this.diasAb = diasAb;
        this.interPartos = interPartos;
        this.pAbortos = pAbortos;
        this.pGrasa = pGrasa;
        this.pProteina = pProteina;
        this.pSolidos = pSolidos;
        this.nitrogU = nitrogU;
        this.cCSMiles = cCSMiles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFechaProceso() {
        return fechaProceso;
    }

    public void setFechaProceso(String fechaProceso) {
        this.fechaProceso = fechaProceso;
    }

    public int getVacasHato() {
        return vacasHato;
    }

    public void setVacasHato(int vacasHato) {
        this.vacasHato = vacasHato;
    }

    public double getLechekgH() {
        return lechekgH;
    }

    public void setLechekgH(double lechekgH) {
        this.lechekgH = lechekgH;
    }

    public double getLecheEM() {
        return lecheEM;
    }

    public void setLecheEM(double lecheEM) {
        this.lecheEM = lecheEM;
    }

    public double getDiasLeche() {
        return diasLeche;
    }

    public void setDiasLeche(double diasLeche) {
        this.diasLeche = diasLeche;
    }

    public double getpVacasCarga() {
        return pVacasCarga;
    }

    public void setpVacasCarga(double pVacasCarga) {
        this.pVacasCarga = pVacasCarga;
    }

    public double getDiasPico() {
        return diasPico;
    }

    public void setDiasPico(double diasPico) {
        this.diasPico = diasPico;
    }

    public double getLechePico() {
        return lechePico;
    }

    public void setLechePico(double lechePico) {
        this.lechePico = lechePico;
    }

    public int getpDesecho() {
        return pDesecho;
    }

    public void setpDesecho(int pDesecho) {
        this.pDesecho = pDesecho;
    }

    public double getEdadMM() {
        return edadMM;
    }

    public void setEdadMM(double edadMM) {
        this.edadMM = edadMM;
    }

    public int getDias1Serv() {
        return dias1Serv;
    }

    public void setDias1Serv(int dias1Serv) {
        this.dias1Serv = dias1Serv;
    }

    public int getpFertil1Serv() {
        return pFertil1Serv;
    }

    public void setpFertil1Serv(int pFertil1Serv) {
        this.pFertil1Serv = pFertil1Serv;
    }

    public double getInterServ() {
        return interServ;
    }

    public void setInterServ(double interServ) {
        this.interServ = interServ;
    }

    public double getServxConc() {
        return servxConc;
    }

    public void setServxConc(double servxConc) {
        this.servxConc = servxConc;
    }

    public int getDiasAb() {
        return diasAb;
    }

    public void setDiasAb(int diasAb) {
        this.diasAb = diasAb;
    }

    public double getInterPartos() {
        return interPartos;
    }

    public void setInterPartos(double interPartos) {
        this.interPartos = interPartos;
    }

    public double getpAbortos() {
        return pAbortos;
    }

    public void setpAbortos(double pAbortos) {
        this.pAbortos = pAbortos;
    }

    public double getpGrasa() {
        return pGrasa;
    }

    public void setpGrasa(double pGrasa) {
        this.pGrasa = pGrasa;
    }

    public double getpProteina() {
        return pProteina;
    }

    public void setpProteina(double pProteina) {
        this.pProteina = pProteina;
    }

    public double getpSolidos() {
        return pSolidos;
    }

    public void setpSolidos(double pSolidos) {
        this.pSolidos = pSolidos;
    }

    public double getNitrogU() {
        return nitrogU;
    }

    public void setNitrogU(double nitrogU) {
        this.nitrogU = nitrogU;
    }

    public int getcCSMiles() {
        return cCSMiles;
    }

    public void setcCSMiles(int cCSMiles) {
        this.cCSMiles = cCSMiles;
    }
}