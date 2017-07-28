package com.abetancourt.holsteincalificador;

/**
 * Created by Alex on 12/09/2015.
 */
public class Toro {

    private boolean sync;
    private int id;
    private String NoHato;
    private String Fecha;
    private String NoRegistro;
    private String RegPadre;
    private String RegMadre;
    private String FechaNac;
    private String CalifAnt;
    private String ClaseAnt;
    private String Nombre;
    private int c1;
    private int c2;
    private int c3;
    private int c4;
    private String ss1;
    private int ss1puntos;
    private int c5;
    private int c6;
    private int c7;
    private int c8;
    private int c9;
    private int c10;
    private String ss2;
    private int ss2puntos;
    private int c11;
    private int c12;
    private int c13;
    private int c14;
    private int c15;
    private String ss3;
    private int ss3puntos;
    private int Def10;
    private int Def11;
    private int Def40;
    private int Def42;
    private int Def43;
    private int Def44;
    private int Def45;
    private int Def30;
    private int Def31;
    private int Def32;
    private int Def34;
    private int Def35;
    private int Def36;
    private int PuntosCalif;
    private String ClaseCalif;
    private String FechaCalif;
    private String Comentarios;

    public Toro (){}


    public Toro(int id, String noHato, String fecha, String noRegistro, String regPadre,
                String regMadre, String fechaNac, String califAnt, String claseAnt, String nombre) {
        this.id = id;
        NoHato = noHato;
        Fecha = fecha;
        NoRegistro = noRegistro;
        RegPadre = regPadre;
        RegMadre = regMadre;
        FechaNac = fechaNac;
        CalifAnt = califAnt;
        ClaseAnt = claseAnt;
        Nombre = nombre;
    }

    public Toro(int id, String noHato, String fecha, String noRegistro, String regPadre,
                String regMadre, String fechaNac, String califAnt, String claseAnt, String nombre,
                int c1, int c2, int c3, int c4, String ss1, int ss1puntos, int c5, int c6, int c7,
                int c8, int c9, int c10, String ss2, int ss2puntos, int c11, int c12, int c13,
                int c14, int c15, String ss3, int ss3puntos,
                int def10, int def11, int def40,
                int def42, int def43, int def44, int def45, int def30, int def31, int def32,
                int def34, int def35, int def36,
                int puntosCalif, String claseCalif, String fechaCalif, String comentarios) {
        this.id = id;
        NoHato = noHato;
        Fecha = fecha;
        NoRegistro = noRegistro;
        RegPadre = regPadre;
        RegMadre = regMadre;
        FechaNac = fechaNac;
        CalifAnt = califAnt;
        ClaseAnt = claseAnt;
        Nombre = nombre;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.c4 = c4;
        this.ss1 = ss1;
        this.ss1puntos = ss1puntos;
        this.c5 = c5;
        this.c6 = c6;
        this.c7 = c7;
        this.c8 = c8;
        this.c9 = c9;
        this.c10 = c10;
        this.ss2 = ss2;
        this.ss2puntos = ss2puntos;
        this.c11 = c11;
        this.c12 = c12;
        this.c13 = c13;
        this.c14 = c14;
        this.c15 = c15;
        this.ss3 = ss3;
        this.ss3puntos = ss3puntos;
        Def10 = def10;
        Def11 = def11;
        Def40 = def40;
        Def42 = def42;
        Def43 = def43;
        Def44 = def44;
        Def45 = def45;
        Def30 = def30;
        Def31 = def31;
        Def32 = def32;
        Def34 = def34;
        Def35 = def35;
        Def36 = def36;
        PuntosCalif = puntosCalif;
        ClaseCalif = claseCalif;
        FechaCalif = fechaCalif;
        Comentarios = comentarios;
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

    public void setNoHato(String noHato) {
        NoHato = noHato;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getNoRegistro() {
        return NoRegistro;
    }

    public void setNoRegistro(String noRegistro) {
        NoRegistro = noRegistro;
    }

    public String getRegPadre() {
        return RegPadre;
    }

    public void setRegPadre(String regPadre) {
        RegPadre = regPadre;
    }

    public String getRegMadre() {
        return RegMadre;
    }

    public void setRegMadre(String regMadre) {
        RegMadre = regMadre;
    }

    public String getFechaNac() {
        return FechaNac;
    }

    public void setFechaNac(String fechaNac) {
        FechaNac = fechaNac;
    }

    public String getCalifAnt() {
        return CalifAnt;
    }

    public void setCalifAnt(String califAnt) {
        CalifAnt = califAnt;
    }

    public String getClaseAnt() {
        return ClaseAnt;
    }

    public void setClaseAnt(String claseAnt) {
        ClaseAnt = claseAnt;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
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

    public String getSs1() {
        return ss1;
    }

    public void setSs1(String ss1) {
        this.ss1 = ss1;
    }

    public int getSs1puntos() {
        return ss1puntos;
    }

    public void setSs1puntos(int ss1puntos) {
        this.ss1puntos = ss1puntos;
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

    public String getSs2() {
        return ss2;
    }

    public void setSs2(String ss2) {
        this.ss2 = ss2;
    }

    public int getSs2puntos() {
        return ss2puntos;
    }

    public void setSs2puntos(int ss2puntos) {
        this.ss2puntos = ss2puntos;
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

    public String getSs3() {
        return ss3;
    }

    public void setSs3(String ss3) {
        this.ss3 = ss3;
    }

    public int getSs3puntos() {
        return ss3puntos;
    }

    public void setSs3puntos(int ss3puntos) {
        this.ss3puntos = ss3puntos;
    }

    public int getDef10() {
        return Def10;
    }

    public void setDef10(int def10) {
        Def10 = def10;
    }

    public int getDef11() {
        return Def11;
    }

    public void setDef11(int def11) {
        Def11 = def11;
    }

    public int getDef40() {
        return Def40;
    }

    public void setDef40(int def40) {
        Def40 = def40;
    }

    public int getDef42() {
        return Def42;
    }

    public void setDef42(int def42) {
        Def42 = def42;
    }

    public int getDef43() {
        return Def43;
    }

    public void setDef43(int def43) {
        Def43 = def43;
    }

    public int getDef44() {
        return Def44;
    }

    public void setDef44(int def44) {
        Def44 = def44;
    }

    public int getDef45() {
        return Def45;
    }

    public void setDef45(int def45) {
        Def45 = def45;
    }

    public int getDef30() {
        return Def30;
    }

    public void setDef30(int def30) {
        Def30 = def30;
    }

    public int getDef31() {
        return Def31;
    }

    public void setDef31(int def31) {
        Def31 = def31;
    }

    public int getDef32() {
        return Def32;
    }

    public void setDef32(int def32) {
        Def32 = def32;
    }

    public int getDef34() {
        return Def34;
    }

    public void setDef34(int def34) {
        Def34 = def34;
    }

    public int getDef35() {
        return Def35;
    }

    public void setDef35(int def35) {
        Def35 = def35;
    }

    public int getDef36() {
        return Def36;
    }

    public void setDef36(int def36) {
        Def36 = def36;
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

    public String getComentarios() {
        return Comentarios;
    }

    public void setComentarios(String comentarios) {
        Comentarios = comentarios;
    }
}
