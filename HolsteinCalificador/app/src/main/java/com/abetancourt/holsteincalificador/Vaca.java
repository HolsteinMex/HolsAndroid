package com.abetancourt.holsteincalificador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Alex on 25/07/2015.
 */
public class Vaca {

    public Vaca(){}

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private boolean sync;
    private int id;
    private String NoHato;  // Número de hato.
    private String Fecha;   // Fecha cuando se genera el reporte
    private int Curv;       // Identificador único
    private String NoCP;    // No. de Vaca o NoCP
    private String NoRegistro;// No. de registro de la toro
    private String RegPadre;// No. de registro del padre
    private String Regmadre;// No. de registro de la madre
    private String FechaNac;// Fecha de nacimiento
    private int NoLactancia;// Número de parto
    private String FechaParto;// Fecha de parto.
    private int CalifAnt;   // Calificación anterior, son los puntos.
    private String ClaseAnt;// Clase anterior.
    private String TipoParto;// A char(1) N=Normal, A=Anormal
    private int LacNorm;    // Número de lactancias normales.
    private int MesesAborto;// Meses al aborto.
    private String Comentario;


    //FORTALEZA LECHERA
    int c1;     // Estatura
    int c2;     // AltCruz
    int c3;     // Tamaño
    int c4;     // PechoAncho
    int c5;     // Profundidad
    int c24;    // Angularidad

    //ANCA
    int c6;     // Lomo
    int c7;     // Punta
    int c8;     // Anchura
    int c8a;    // Colocacion

    //PATAS Y PEZUÑAS
    int c9;     // Angulo
    int c11;    // ProfTalón
    int c12;    // CalHuesos
    int c13;    // Aplomo
    int c14;    // VPosterior

    //SISTEMA MAMARIO
    int c15;    // SMProfundidad
    int c16;    // Textura
    int c17;    // LMS
    int c18;    // Inserción Del
    int c19;    // Posición Del
    int c20;    // LongPezones Pos
    int c21;    // UPAltura
    int c22;    // UPAnchura
    int c23;    // UPPosicion

    // ??
    int c10;    // UPezuna

    int s1;     // EstrucCapac
    int s2;     // Anca
    int s3;     // PatasPezuñas
    int s4;     // SistMamario
    int s5;     // UbreAnterior
    int s6;     // UbrePosterior
    int s7;     // CarLechero
    int ss1;    // SISTEMA Estructura y Capacidad
    int ss2;    // SISTEMA Anca
    int ss3;    // SISTEMA Patas y Pezunas
    int ss4;    // SISTEMA Sistema Mamario;

    String css1;    // SISTEMA Estructura y Capacidad
    String css2;    // SISTEMA Anca
    String css3;    // SISTEMA Patas y Pezunas
    String css4;    // SISTEMA Sistema Mamario;

    int css1puntos;    // SISTEMA Estructura y Capacidad
    int css2puntos;    // SISTEMA Anca
    int css3puntos;    // SISTEMA Patas y Pezunas
    int css4puntos;    // SISTEMA Sistema Mamario;

    ///*** PONDERACIÓN ***///
    double l1_intercept = 93.0867; double l1_ss1 = -4.68768; double l1_ss2 = -4.37352; double l1_ss3 = -1.63239; double l1_ss4 = -8.6474; double l1_ss5 = -.003714915;
    double l2_intercept = 94.4305; double l2_ss1 = -5.18410; double l2_ss2 = -3.37842; double l2_ss3 = -1.93099; double l2_ss4 = -9.2687; double l2_ss5 = -.004305978;
    double l3_intercept = 95.6714; double l3_ss1 = -2.75750; double l3_ss2 = -9.75060; double l3_ss3 = 1.23795; double l3_ss4 = -12.6282; double l3_ss5 = 0.005901708;

    ///*** DEFECTOS ***///
    int def10;
    int def11;
    int def12;
    int def22;
    int def23;
    int def24;
    int def25;
    int def26;
    int def27;
    int def28;
    int def29;
    int def30;
    int def31;
    int def32;
    int def34;
    int def35;
    int def36;
    int def40;
    int def42;
    int def43;
    int def44;
    int def45;
    int def46;
    int def47;
    int def48;


    // PUNTOS
    int Puntos;
    String Clase;
    int PuntosCalif;
    String ClaseCalif;
    String FechaCalif;
    String FechaCalifAnt;

    double CALIF_FINAL = 0;


    public Vaca(int id,String noHato, String fecha, int curv, String noCP, String noRegistro,
                String regPadre, String regmadre, String fechaNac, int noLactancia,
                String fechaParto, int califAnt, String claseAnt, String tipoParto,
                int lacNorm, int mesesAborto) {
        this.id=id;
        NoHato = noHato;
        Fecha = fecha;
        Curv = curv;
        NoCP = noCP;
        NoRegistro = noRegistro;
        RegPadre = regPadre;
        Regmadre = regmadre;
        FechaNac = fechaNac;
        NoLactancia = noLactancia;
        FechaParto = fechaParto;
        CalifAnt = califAnt;
        ClaseAnt = claseAnt;
        TipoParto = tipoParto;
        LacNorm = lacNorm;
        MesesAborto = mesesAborto;
    }

    public Vaca(int id, String noHato, String fecha, int curv, String noCP, String noRegistro,
                String regPadre, String regmadre, String fechaNac, int noLactancia,
                String fechaParto, int califAnt, String claseAnt, String tipoParto, int lacNorm,
                int mesesAborto, //16

                int c1, int c2, int c3, int c4, int c5, int c24, int c6, int c7, int c8, int c8a,
                int c9, int c11, int c12, int c13, int c14, int c15, int c16, int c17, int c18,
                int c19, int c20, int c21, int c22, int c23,

                int def10, int def11, int def12, int def22, int def23, int def24, int def25, int def26,
                int def27, int def28, int def29, int def30, int def31, int def32, int def34,
                int def35, int def36, int def40, int def42, int def43, int def44, int def45,int def46, int def47, int def48,

                String css1, String css2, String css3, String css4,
                int css1puntos, int css2puntos, int css3puntos, int css4puntos,

                int puntos, String clase, int puntosCalif, String claseCalif, String fechaCalif,
                String comentario, String fechaCalifAnt) {

        this.id = id;
        NoHato = noHato;
        Fecha = fecha;
        Curv = curv;
        NoCP = noCP;
        NoRegistro = noRegistro;
        RegPadre = regPadre;
        Regmadre = regmadre;
        FechaNac = fechaNac;
        NoLactancia = noLactancia;
        FechaParto = fechaParto;
        CalifAnt = califAnt;
        ClaseAnt = claseAnt;
        TipoParto = tipoParto;
        LacNorm = lacNorm;
        MesesAborto = mesesAborto;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.c4 = c4;
        this.c5 = c5;
        this.c24 = c24;
        this.c6 = c6;
        this.c7 = c7;
        this.c8 = c8;
        this.c8a = c8a;
        this.c9 = c9;
        this.c11 = c11;
        this.c12 = c12;
        this.c13 = c13;
        this.c14 = c14;
        this.c15 = c15;
        this.c16 = c16;
        this.c17 = c17;
        this.c18 = c18;
        this.c19 = c19;
        this.c20 = c20;
        this.c21 = c21;
        this.c22 = c22;
        this.c23 = c23;
        this.css1 = css1;
        this.css2 = css2;
        this.css3 = css3;
        this.css4 = css4;
        this.css1puntos = css1puntos;
        this.css2puntos = css2puntos;
        this.css3puntos = css3puntos;
        this.css4puntos = css4puntos;
        this.def10 = def10;
        this.def11 = def11;
        this.def12 = def12;
        this.def22 = def22;
        this.def23 = def23;
        this.def24 = def24;
        this.def25 = def25;
        this.def27 = def27;
        this.def26 = def26;
        this.def28 = def28;
        this.def31 = def31;
        this.def30 = def30;
        this.def29 = def29;
        this.def32 = def32;
        this.def34 = def34;
        this.def35 = def35;
        this.def36 = def36;
        this.def40 = def40;
        this.def42 = def42;
        this.def43 = def43;
        this.def44 = def44;
        this.def45 = def45;
        this.def46 = def46;
        this.def47 = def47;
        this.def48 = def48;

        Puntos = puntos;
        Clase = clase;
        PuntosCalif = puntosCalif;
        ClaseCalif = claseCalif;
        FechaCalif = fechaCalif;
        Comentario = comentario;
        FechaCalifAnt = fechaCalifAnt;
    }

    @Override
    public String toString() {
        return "Vaca{" +
                "id=" + id +
                ", \nNoHato='" + NoHato + '\'' +
                ", \nFecha='" + Fecha + '\'' +
                ", \nCurv=" + Curv +
                ", \nNoCP='" + NoCP + '\'' +
                ", \nNoRegistro='" + NoRegistro + '\'' +
                ", \nRegPadre='" + RegPadre + '\'' +
                ", \nRegmadre='" + Regmadre + '\'' +
                ", \nFechaNac='" + FechaNac + '\'' +
                ", \nNoLactancia=" + NoLactancia +
                ", \nFechaParto='" + FechaParto + '\'' +
                ", \nCalifAnt=" + CalifAnt +
                ", \nClaseAnt='" + ClaseAnt + '\'' +
                ", \nTipoParto='" + TipoParto + '\'' +
                ", \nLacNorm=" + LacNorm +
                ", \nMesesAborto=" + MesesAborto +
                ", \nc1=" + c1 +
                ", \nc2=" + c2 +
                ", \nc3=" + c3 +
                ", \nc4=" + c4 +
                ", \nc5=" + c5 +
                ", \nc24=" + c24 +
                ", \nc6=" + c6 +
                ", \nc7=" + c7 +
                ", \nc8=" + c8 +
                ", \nc8a=" + c8a +
                ", \nc9=" + c9 +
                ", \nc11=" + c11 +
                ", \nc12=" + c12 +
                ", \nc13=" + c13 +
                ", \nc14=" + c14 +
                ", \nc15=" + c15 +
                ", \nc16=" + c16 +
                ", \nc17=" + c17 +
                ", \nc18=" + c18 +
                ", \nc19=" + c19 +
                ", \nc20=" + c20 +
                ", \nc21=" + c21 +
                ", \nc22=" + c22 +
                ", \nc23=" + c23 +
                ", \nFechaCalif='" + FechaCalif + '\'' +
                ", \nClaseCalif='" + ClaseCalif + '\'' +
                ", \nPuntosCalif=" + PuntosCalif +
                ", \nClase='" + Clase + '\'' +
                ", Puntos=" + Puntos +
                '}';
    }

    public boolean isSync() {
        return sync;
    }

    public void setSync(boolean sync) {
        this.sync = sync;
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

    public String getFecha() {
        return Fecha;
    }

    public int getCurv() {
        return Curv;
    }

    public String getNoCP() {
        return NoCP;
    }

    public String getNoRegistro() {
        return NoRegistro;
    }

    public String getRegPadre() {
        return RegPadre;
    }

    public String getRegmadre() {
        return Regmadre;
    }

    public String getFechaNac() {
        return FechaNac;
    }

    public int getNoLactancia() {
        return NoLactancia;
    }

    public String getFechaParto() {
        return FechaParto;
    }

    public int getCalifAnt() {
        return CalifAnt;
    }

    public String getClaseAnt() {
        return ClaseAnt;
    }

    public String getTipoParto() {
        return TipoParto;
    }

    public int getLacNorm() {
        return LacNorm;
    }

    public int getMesesAborto() {
        return MesesAborto;
    }

    public void setNoHato(String noHato) {
        NoHato = noHato;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public void setCurv(int curv) {
        Curv = curv;
    }

    public void setNoCP(String noCP) {
        NoCP = noCP;
    }

    public void setNoRegistro(String noRegistro) {
        NoRegistro = noRegistro;
    }

    public void setRegPadre(String regPadre) {
        RegPadre = regPadre;
    }

    public void setRegmadre(String regmadre) {
        Regmadre = regmadre;
    }

    public void setFechaNac(String fechaNac) {
        FechaNac = fechaNac;
    }

    public void setNoLactancia(int noLactancia) {
        NoLactancia = noLactancia;
    }

    public void setFechaParto(String fechaParto) {
        FechaParto = fechaParto;
    }

    public void setCalifAnt(int califAnt) {
        CalifAnt = califAnt;
    }

    public void setClaseAnt(String claseAnt) {
        ClaseAnt = claseAnt;
    }

    public void setTipoParto(String tipoParto) {
        TipoParto = tipoParto;
    }

    public void setLacNorm(int lacNorm) {
        LacNorm = lacNorm;
    }

    public void setMesesAborto(int mesesAborto) {
        MesesAborto = mesesAborto;
    }

    public int getC1() {
        return c1;
    }

    public void setC1(int c1) {
        this.c1 = c1;
    }

    public int getC2() {
        return c2;
    }

    public void setC2(int c2) {
        this.c2 = c2;
    }

    public int getC3() {
        return c3;
    }

    public void setC3(int c3) {
        this.c3 = c3;
    }

    public int getC4() {
        return c4;
    }

    public void setC4(int c4) {
        this.c4 = c4;
    }

    public int getC5() {
        return c5;
    }

    public void setC5(int c5) {
        this.c5 = c5;
    }

    public int getC6() {
        return c6;
    }

    public void setC6(int c6) {
        this.c6 = c6;
    }

    public int getC7() {
        return c7;
    }

    public void setC7(int c7) {
        this.c7 = c7;
    }

    public int getC8() {
        return c8;
    }

    public void setC8(int c8) {
        this.c8 = c8;
    }

    public int getC8a() {
        return c8a;
    }

    public void setC8a(int c8a) {
        this.c8a = c8a;
    }

    public int getC9() {
        return c9;
    }

    public void setC9(int c9) {
        this.c9 = c9;
    }

    public int getC10() {
        return c10;
    }

    public void setC10(int c10) {
        this.c10 = c10;
    }

    public int getC11() {
        return c11;
    }

    public void setC11(int c11) {
        this.c11 = c11;
    }

    public int getC12() {
        return c12;
    }

    public void setC12(int c12) {
        this.c12 = c12;
    }

    public int getC13() {
        return c13;
    }

    public void setC13(int c13) {
        this.c13 = c13;
    }

    public int getC14() {
        return c14;
    }

    public void setC14(int c14) {
        this.c14 = c14;
    }

    public int getC15() {
        return c15;
    }

    public void setC15(int c15) {
        this.c15 = c15;
    }

    public int getC16() {
        return c16;
    }

    public void setC16(int c16) {
        this.c16 = c16;
    }

    public int getC17() {
        return c17;
    }

    public void setC17(int c17) {
        this.c17 = c17;
    }

    public int getC18() {
        return c18;
    }

    public void setC18(int c18) {
        this.c18 = c18;
    }

    public int getC19() {
        return c19;
    }

    public void setC19(int c19) {
        this.c19 = c19;
    }

    public int getC20() {
        return c20;
    }

    public void setC20(int c20) {
        this.c20 = c20;
    }

    public int getC21() {
        return c21;
    }

    public void setC21(int c21) {
        this.c21 = c21;
    }

    public int getC22() {
        return c22;
    }

    public void setC22(int c22) {
        this.c22 = c22;
    }

    public int getC23() {
        return c23;
    }

    public void setC23(int c23) {
        this.c23 = c23;
    }

    public int getC24() {
        return c24;
    }

    public void setC24(int c24) {
        this.c24 = c24;
    }

    public int getDef10() {
        return def10;
    }

    public void setDef10(int def10) {
        this.def10 = def10;
    }

    public int getDef11() {
        return def11;
    }

    public void setDef11(int def11) {
        this.def11 = def11;
    }

    public int getDef22() {
        return def22;
    }

    public void setDef22(int def22) {
        this.def22 = def22;
    }

    public int getDef23() {
        return def23;
    }

    public void setDef23(int def23) {
        this.def23 = def23;
    }

    public int getDef24() {
        return def24;
    }

    public void setDef24(int def24) {
        this.def24 = def24;
    }

    public int getDef25() {
        return def25;
    }

    public void setDef25(int def25) {
        this.def25 = def25;
    }

    public int getDef26() {
        return def26;
    }

    public void setDef26(int def26) {
        this.def26 = def26;
    }

    public int getDef27() {
        return def27;
    }

    public void setDef27(int def27) {
        this.def27 = def27;
    }

    public int getDef28() {
        return def28;
    }

    public void setDef28(int def28) {
        this.def28 = def28;
    }

    public int getDef29() {
        return def29;
    }

    public void setDef29(int def29) {
        this.def29 = def29;
    }

    public int getDef30() {
        return def30;
    }

    public void setDef30(int def30) {
        this.def30 = def30;
    }

    public int getDef31() {
        return def31;
    }

    public void setDef31(int def31) {
        this.def31 = def31;
    }

    public int getDef32() {
        return def32;
    }

    public void setDef32(int def32) {
        this.def32 = def32;
    }

    public int getDef34() {
        return def34;
    }

    public void setDef34(int def34) {
        this.def34 = def34;
    }

    public int getDef35() {
        return def35;
    }

    public void setDef35(int def35) {
        this.def35 = def35;
    }

    public int getDef36() {
        return def36;
    }

    public void setDef36(int def36) {
        this.def36 = def36;
    }

    public int getDef40() {
        return def40;
    }

    public void setDef40(int def40) {
        this.def40 = def40;
    }

    public int getDef42() {
        return def42;
    }

    public void setDef42(int def42) {
        this.def42 = def42;
    }

    public int getDef43() {
        return def43;
    }

    public void setDef43(int def43) {
        this.def43 = def43;
    }

    public int getDef44() {
        return def44;
    }

    public void setDef44(int def44) {
        this.def44 = def44;
    }

    public int getDef45() {
        return def45;
    }

    public void setDef45(int def45) {
        this.def45 = def45;
    }

    public String getCss1() {
        return css1;
    }

    public void setCss1(String css1) {
        this.css1 = css1;
    }

    public String getCss2() {
        return css2;
    }

    public void setCss2(String css2) {
        this.css2 = css2;
    }

    public String getCss3() {
        return css3;
    }

    public void setCss3(String css3) {
        this.css3 = css3;
    }

    public String getCss4() {
        return css4;
    }

    public void setCss4(String css4) {
        this.css4 = css4;
    }

    public String getComentario() {
        return Comentario;
    }

    public void setComentario(String comentario) {
        Comentario = comentario;
    }

    public int getCss1puntos() {
        return css1puntos;
    }

    public void setCss1puntos(int css1puntos) {
        this.css1puntos = css1puntos;
    }

    public int getCss2puntos() {
        return css2puntos;
    }

    public void setCss2puntos(int css2puntos) {
        this.css2puntos = css2puntos;
    }

    public int getCss3puntos() {
        return css3puntos;
    }

    public void setCss3puntos(int css3puntos) {
        this.css3puntos = css3puntos;
    }

    public int getCss4puntos() {
        return css4puntos;
    }

    public void setCss4puntos(int css4puntos) {
        this.css4puntos = css4puntos;
    }

    public int getPuntos() {
        return Puntos;
    }

    public void setPuntos(int puntos) {
        Puntos = puntos;
    }

    public String getClase() {
        return Clase;
    }

    public void setClase(String clase) {
        Clase = clase;
    }

    public int getPuntosCalif() {
        return PuntosCalif;
    }

    public void setPuntosCalif(int puntosCalif) {
        PuntosCalif = puntosCalif;
    }

    public String getClaseCalif() {
        return ClaseCalif;
    }

    public void setClaseCalif(String claseCalif) {
        ClaseCalif = claseCalif;
    }

    public String getFechaCalif() {
        return FechaCalif;
    }

    public void setFechaCalif(String fechaCalif) {
        FechaCalif = fechaCalif;
    }


    public int getDef12() {
        return def12;
    }

    public void setDef12(int def12) {
        this.def12 = def12;
    }

    public int getDef46() {
        return def46;
    }

    public void setDef46(int def46) {
        this.def46 = def46;
    }

    public int getDef47() {
        return def47;
    }

    public void setDef47(int def47) {
        this.def47 = def47;
    }

    public int getDef48() {
        return def48;
    }

    public void setDef48(int def48) {
        this.def48 = def48;
    }

    public String getFechaCalifAnt() {
        return FechaCalifAnt;
    }

    public void setFechaCalifAnt(String fechaCalifAnt) {
        FechaCalifAnt = fechaCalifAnt;
    }

    public int calcularCalificacion(){
        int lact = 0;
        if (LacNorm >= 3)
            lact = 3;
        else
            lact = LacNorm;

        double calif,calif2;

        double cs1=abs(c1-7);
        double cs2=abs(c2-7); /*prueba*/
        double cs3=abs(c3-5); /*prueba*/
        double cs4=abs(c4-9);
        double cs5=abs(c5-7);
        double cs6=abs(c6-9); /*prueba*/
        double cs7=abs(c7-5);
        double cs8=abs(c8-9);
        double cs8a=abs(c8a-6);
        double cs9=abs(c9-7);
        double cs11=abs(c11-9); /*prueba*/
        double cs12=abs(c12-9);
        double cs13=abs(c13-5);
        double cs14=abs(c14-9); /*prueba*/
        double cs15=abs(c15-5);
        double cs16=abs(c16-9); /*prueba*/
        double cs17=abs(c17-9);
        double cs18=abs(c18-9);
        double cs19=abs(c19-5);
        double cs20=abs(c20-5); /*prueba*/
        double cs21=abs(c21-9);
        double cs22=abs(c22-9); /*prueba*/
        double cs23=abs(c23-5); /*prueba*/
        double cs24=abs(c24-9);
        double csa1=(cs1*0.12);
        double csa2=(cs2*0.03);
        double csa3=(cs3*0.17);
        double csa4=(cs4*0.23);
        double csa5=(cs5*0.17);
        double csa6=(cs6*0.32);
        double csa7=(cs7*0.34);
        double csa8=(cs8*0.21);
        double csa8a=(cs8a*0.00);
        double csa9=(cs9*0.18);
        double csa11=(cs11*.22);
        double csa12=(cs12*.12);
        double csa13=(cs13*.17);
        double csa14=(cs14*.31);
        double csa15=(cs15*.16);
        double csa16=(cs16*.14);
        double csa17=(cs17*.14);
        double csa18=(cs18*.18);
        double csa19=(cs19*.07);
        double csa20=(cs20*.02);
        double csa21=(cs21*.12);
        double csa22=(cs22*.10);
        double csa23=(cs23*.07);
        double csa24=(cs24*.28);
        double ss1=((csa1+csa2+csa3+csa4+csa5+csa24)*0.2);
        double ss2=((csa6+csa7+csa8+csa8a)*0.1);
        double ss3=((csa9+csa11+csa12+csa13+csa14)*.28);
        double ss4=((csa15+csa16+csa17+csa18+csa19+csa20+csa21+csa22+csa23)*.42);

        int dim = daysBetween(fechaParto(),fechaCalif());

        if (dim > 305) dim = 305;

        switch(lact){
            case 1:
                CALIF_FINAL = l1_intercept + (ss1*l1_ss1) + (ss2*l1_ss2) + (ss3*l1_ss3) +(ss4*l1_ss4) + (dim*l1_ss5);
                return round(CALIF_FINAL);
            case 2:
                CALIF_FINAL = l2_intercept + (ss1*l2_ss1) + (ss2*l2_ss2) + (ss3*l2_ss3) +(ss4*l2_ss4) + (dim*l2_ss5);
                return round(CALIF_FINAL);
            default:
                CALIF_FINAL = l3_intercept + (ss1*l3_ss1) + (ss2*l3_ss2) + (ss3*l3_ss3) +(ss4*l3_ss4) + (dim*l3_ss5);
                return round(CALIF_FINAL);
        }
    }

    public String calcularClase(){

        if (CALIF_FINAL==0)calcularCalificacion();
        int cfinal = round(CALIF_FINAL);
        if (cfinal>=60 && cfinal <=69) return "FA";
        else if (cfinal>=70 && cfinal <=79) return "GO";
        else if (cfinal>=80 && cfinal <=84) return "GP";
        else if (cfinal>=85 && cfinal <=89) return "VG";
        else if (cfinal>=90) return "EX";
        else return "FA";
    }

    public static float abs(float a) {
        return (a <= 0.0F) ? 0.0F - a : a;
    }

    public static Calendar getDatePart(Date date){
        Calendar cal = Calendar.getInstance();       // get calendar instance
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        cal.set(Calendar.MINUTE, 0);                 // set minute in hour
        cal.set(Calendar.SECOND, 0);                 // set second in minute
        cal.set(Calendar.MILLISECOND, 0);            // set millisecond in second

        return cal;                                  // return the date part
    }

    public static int daysBetween(Date startDate, Date endDate) {
        Calendar sDate = getDatePart(startDate);
        Calendar eDate = getDatePart(endDate);

        int daysBetween = 0;
        while (sDate.before(eDate)) {
            sDate.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }
        return daysBetween;
    }

    private Date fechaCalif(){
        return new Date();
    }

    private Date fechaParto(){
        if (FechaParto!=null && !FechaParto.equals("")){
            try {
                Date d = sdf.parse(FechaParto);
                return d;
            } catch (ParseException e) {
                e.printStackTrace();
                return new Date();
            }
        }else return new Date();
    }

    private int round(double d){
        double dAbs = Math.abs(d);
        int i = (int) dAbs;
        double result = dAbs - (double) i;
        if(result<0.5){
            return d<0 ? -i : i;
        }else{
            return d<0 ? -(i+1) : i+1;
        }
    }
}
