package com.abetancourt.holsteincalificador;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.usb.UsbManager;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.FontProvider;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Alex on 26/07/2015.
 */
public class PDF {

    String sSDpath;
    File directory;

    public PDF(){
        /*
        String sSDpath = null;

        File   fileCur = null;
        //for( String sPathCur : Arrays.asList("ext_card", "external_sd", "ext_sd", "external", "extSdCard", "externalSdCard", "usbdisk", "")) // external sdcard
        for( String sPathCur : Arrays.asList("usbdisk", "UsbDriveA", "UsbDriveB")) // external sdcard
        {
            fileCur = new File( "/storage/", sPathCur);
            if( fileCur.isDirectory() && fileCur.canWrite())
            {
                sSDpath = fileCur.getAbsolutePath();
                break;
            }
        }
        if( sSDpath == null)  sSDpath = Environment.getExternalStorageDirectory().getAbsolutePath();
        */

        File storageDir = new File("/storage");
        File files[] = storageDir.listFiles();

        directory = null;
        if (files != null && files.length > 0) {
            for (File f : files) {
                if (f.isDirectory() && f.getPath().toLowerCase().contains("usb") && f.canRead()) {
                    directory = f;
                    break;
                }
            }
        }
        if (directory != null && directory.canRead() && directory.canWrite()) {
            File path = new File(directory, "Holstein");
            if (!path.exists()) {
                path.mkdir();
            }
            /*
            File file[] = directory.listFiles();
            if (file != null && file.length > 0) {
                for (int i = 0; i < file.length; i++) {
                    String filePath = file[i].getPath();
                    Log.d("FILE",filePath);
                }
            }
            */
        }
        else directory = Environment.getExternalStorageDirectory();
    }


    public Object createPDFVaca(String fileName, Context context, ArrayList<Vaca> vacas) {
        String HTML_VACA = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n" +
                "        \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"en\" xml:lang=\"en\">\n" +
                "<head>\n" +
                "    <meta name=\"generator\" content=\"HTML\" />\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\" />\n" +
                "    <title></title>\n" +
                "    <style type=\"text/css\">\n" +
                "        html {\n" +
                "            -ms-text-size-adjust: 100%;\n" +
                "            -webkit-text-size-adjust: 100%;\n" +
                "        }\n" +
                "        body {\n" +
                "            margin: 0;\n" +
                "            margin-top: 20px;\n" +
                "        }\n" +
                "        table {\n" +
                "            border-collapse: collapse;\n" +
                "            border-spacing: 0;\n" +
                "        }\n" +
                "        * {\n" +
                "            font-size: 9px;\n" +
                "            -webkit-box-sizing: border-box;\n" +
                "            -moz-box-sizing: border-box;\n" +
                "            box-sizing: border-box;\n" +
                "        }\n" +
                "        *:before,\n" +
                "        *:after {\n" +
                "            -webkit-box-sizing: border-box;\n" +
                "            -moz-box-sizing: border-box;\n" +
                "            box-sizing: border-box;\n" +
                "        }\n" +
                "        html {\n" +
                "            font-size: 9px;\n" +
                "        }\n" +
                "        body {\n" +
                "            font-size: 9px;\n" +
                "            color: #333333;\n" +
                "        }\n" +
                "        @media (min-width: 768px) {\n" +
                "            .container {\n" +
                "                width: 750px;\n" +
                "            }\n" +
                "        }\n" +
                "        @media (min-width: 992px) {\n" +
                "            .container {\n" +
                "                width: 970px;\n" +
                "            }\n" +
                "        }\n" +
                "        @media (min-width: 1200px) {\n" +
                "            .container {\n" +
                "                width: 1170px;\n" +
                "            }\n" +
                "        }\n" +
                "        .row {\n" +
                "            margin-left: -15px;\n" +
                "            margin-right: -15px;\n" +
                "        }\n" +
                "        .col-md-12 {\n" +
                "            position: relative;\n" +
                "            min-height: 1px;\n" +
                "            padding-left: 5px;\n" +
                "            padding-right: 5px;\n" +
                "        }\n" +
                "\n" +
                "        @media (min-width: 992px) {\n" +
                "            .col-md-12 {\n" +
                "                float: left;\n" +
                "            }\n" +
                "            .col-md-12 {\n" +
                "                width: 100%;\n" +
                "            }\n" +
                "\n" +
                "        }\n" +
                "        .clearfix:before,\n" +
                "        .clearfix:after,\n" +
                "        .container:before,\n" +
                "        .container:after,\n" +
                "        .container-fluid:before,\n" +
                "        .container-fluid:after,\n" +
                "        .row:before,\n" +
                "        .row:after {\n" +
                "            content: \" \";\n" +
                "            display: table;\n" +
                "        }\n" +
                "        .clearfix:after,\n" +
                "        .container:after,\n" +
                "        .container-fluid:after,\n" +
                "        .row:after {\n" +
                "            clear: both;\n" +
                "        }\n" +
                "        .ideal{\n" +
                "            color: #269abc;\n" +
                "        }\n" +
                "        table .col-md-12{\n" +
                "            text-align: center !important;\n" +
                "        }\n" +
                "        table .col-md-6{\n" +
                "            font-size: smaller;\n" +
                "        }\n" +
                "        td{\n" +
                "            padding-top: 5px;\n" +
                "            padding-bottom: 5px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"container\">\n" +
                "    <div class=\"row\">\n" +
                "        <div class=\"col-md-12\" style=\"text-align: center\"><h4>ANÁLISIS DE CONFORMACIÓN HEMBRAS - HOLSTEIN</h4></div>\n" +
                "    </div>\n" +
                "    <div class=\"row\">\n" +
                "        <table border=\"1\" style=\"width: 100%\">\n" +
                "            <tr>\n" +
                "                <td style=\"width: 20%\">Hato:<b>*HATO*</b></td>\n" +
                "                <td style=\"width: 27%\">Medalla o No.Vaca:<b>*MEDALLA*</b></td>\n" +
                "                <td style=\"width: 28%\">No. Registro:<b>*NOREG*</b></td>\n" +
                "                <td style=\"width: 25%\">Fecha:<b>*FECHA*</b></td>\n" +
                "            </tr>\n" +
                "            </table>\n" +
                "        <table border=\"1\" style=\"width: 100%\">\n" +
                "            <tr>\n" +
                "                <td style=\"width: 25%\">No. Registro Madre:<b>*REGMADRE*</b></td>\n" +
                "                <td style=\"width: 25%\">No. Registro Padre:<b>*REGPADRE*</b></td>\n" +
                "                <td style=\"width: 16%\">Fecha de Nac:<b>*FECHANAC*</b></td>\n" +
                "                <td style=\"width: 16%\">Fecha de Parto:<b>*FECHAPARTO*</b></td>\n" +
                "                <td style=\"width: 16%\">No. de Lactancia:<b>*NOLACT*</b></td>\n" +
                "            </tr>\n" +
                "        </table>\n" +
                "        <table border=\"1\" style=\"width: 100%\">\n" +
                "            <tr>\n" +
                "                <td style=\"width: 10.33%\">Sección</td>\n" +
                "                <td style=\"width: 6.33%\">Calif.</td>\n" +
                "                <td style=\"width: 50%\">\n" +
                "                    <table style=\"width: 100%;margin: 10px;\">\n" +
                "                        <tr><td style=\"width: 60%\">Características</td><td style=\"width: 20%\">Calif.</td><td style=\"width: 20%\">Ideal</td></tr>\n" +
                "                    </table>\n" +
                "                </td>\n" +
                "                <td style=\"width: 33.33%\">Defectos</td>\n" +
                "            </tr>\n" +
                "        </table>\n" +
                "        <table><tr><td>&nbsp;</td></tr></table>\n" +
                "        <table border=\"1\" style=\"width: 100%\">\n" +
                "            <tr>\n" +
                "                <td style=\"width: 10.33%\"><div class=\"col-md-12\"><h4>Anca (10%)</h4></div></td>\n" +
                "                <td style=\"width: 6.33%\"><div class=\"col-md-12\"><h4>*CANCA*</h4></div></td>\n" +
                "                <td style=\"width: 50%\">\n" +
                "                    <table style=\"width: 100%;margin: 10px;\">\n" +
                "                        <tr><td style=\"width: 60%\">Inclinación del Anca (34%)</td><td style=\"width: 20%\">*C7*</td><td style=\"width: 20%\">5</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Anchura Anca (21%)</td><td style=\"width: 20%\">*C8*</td><td style=\"width: 20%\">9</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Fortaleza Lomo (32%)</td><td style=\"width: 20%\">*C6*</td><td style=\"width: 20%\">9</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Colocación de la Cadera (13%)</td><td style=\"width: 20%\">*C8A*</td><td style=\"width: 20%\">6</td></tr>\n" +
                "                    </table>\n" +
                "                </td>\n" +
                "                <td style=\"width: 33.33%\">\n" +
                "                    <table style=\"width: 100%\">\n" +
                "                        <tr><td>*D10*</td></tr>\n" +
                "                        <tr><td>*D11*</td></tr>\n" +
                "                        <tr><td>*D12*</td></tr>\n" +
                "                    </table>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "        </table>\n" +
                "        <table><tr><td>&nbsp;</td></tr></table>\n" +
                "        <table border=\"1\" style=\"width: 100%\">\n" +
                "            <tr>\n" +
                "                <td style=\"width: 10.33%\"><div class=\"col-md-12\"><h4>Sistema Mamario (42%)</h4></div></td>\n" +
                "                <td style=\"width: 6.33%\"><div class=\"col-md-12\"><h4>*CMAMARIO*</h4></div></td>\n" +
                "                <td style=\"width: 50%\">\n" +
                "                    <table style=\"width: 100%;margin: 10px;\">\n" +
                "                        <tr><td style=\"width: 60%\">Profundidad Ubre (16%)</td><td style=\"width: 20%\">*C15*</td><td style=\"width: 20%\">5</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Textura Ubre (14%)</td><td style=\"width: 20%\">*C16*</td><td style=\"width: 20%\">9</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Ligamento Medio (14%)</td><td style=\"width: 20%\">*C17*</td><td style=\"width: 20%\">9</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Inserción delantera(18%)</td><td style=\"width: 20%\">*C18*</td><td style=\"width: 20%\">9</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Posc. Tetas Delanteras (7%)</td><td style=\"width: 20%\">*C19*</td><td style=\"width: 20%\">6</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Altura Ubre Trasera (12%)</td><td style=\"width: 20%\">*C21*</td><td style=\"width: 20%\">9</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Anchura Ubre Trasera(10%)</td><td style=\"width: 20%\">*C22*</td><td style=\"width: 20%\">9</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Posc.Pezones posteriores(7%)</td><td style=\"width: 20%\">*C23*</td><td style=\"width: 20%\">5</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Longitud de Pezones (2%)</td><td style=\"width: 20%\">*C20*</td><td style=\"width: 20%\">5</td></tr>\n" +
                "                    </table>\n" +
                "                </td>\n" +
                "                <td style=\"width: 33.33%\">\n" +
                "                    <table style=\"width: 100%\">\n" +
                "                        <tr><td>*D22*</td></tr>\n" +
                "                        <tr><td>*D23*</td></tr>\n" +
                "                        <tr><td>*D24*</td></tr>\n" +
                "                        <tr><td>*D25*</td></tr>\n" +
                "                        <tr><td>*D26*</td></tr>\n" +
                "                        <tr><td>*D27*</td></tr>\n" +
                "                        <tr><td>*D28*</td></tr>\n" +
                "                        <tr><td>*D29*</td></tr>\n" +
                "                        <tr><td>*D46*</td></tr>\n" +
                "                        <tr><td>*D47*</td></tr>\n" +
                "                        <tr><td>*D48*</td></tr>\n" +
                "                    </table>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "        </table>\n" +
                "        <table><tr><td>&nbsp;</td></tr></table>\n" +
                "        <table border=\"1\" style=\"width: 100%\">\n" +
                "            <tr>\n" +
                "                <td style=\"width: 10.33%\"><div class=\"col-md-12\"><h4>Fortaleza Lechera (20%)</h4></div></td>\n" +
                "                <td style=\"width: 6.33%\"><div class=\"col-md-12\"><h4>*CFORTALEZA*</h4></div></td>\n" +
                "                <td style=\"width: 50%\">\n" +
                "                    <table style=\"width: 100%;margin: 10px;\">\n" +
                "                        <tr><td style=\"width: 60%\">Estatura (12%)</td><td style=\"width: 20%\">*C1*</td><td style=\"width: 20%\">7</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Altura a la Cruz (3%)</td><td style=\"width: 20%\">*C2*</td><td style=\"width: 20%\">6</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Anchura de Pecho (23%)</td><td style=\"width: 20%\">*C4*</td><td style=\"width: 20%\">7</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Prof. Corporal(17%)</td><td style=\"width: 20%\">*C5*</td><td style=\"width: 20%\">7</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Angularidad (28%)</td><td style=\"width: 20%\">*C24*</td><td style=\"width: 20%\">9</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Tamaño (17%)</td><td style=\"width: 20%\">*C3*</td><td style=\"width: 20%\">7</td></tr>\n" +
                "                    </table>\n" +
                "                </td>\n" +
                "                <td style=\"width: 33.33%\">\n" +
                "                    <table style=\"width: 100%\">\n" +
                "                        <tr><td>*D40*</td></tr>\n" +
                "                        <tr><td>*D42*</td></tr>\n" +
                "                        <tr><td>*D43*</td></tr>\n" +
                "                        <tr><td>*D44*</td></tr>\n" +
                "                        <tr><td>*D45*</td></tr>\n" +
                "                    </table>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "        </table>\n" +
                "        <table><tr><td>&nbsp;</td></tr></table>\n" +
                "        <table border=\"1\" style=\"width: 100%\">\n" +
                "            <tr>\n" +
                "                <td style=\"width: 10.33%\"><div class=\"col-md-12\"><h4>Patas y Pezuñas (28%)</h4></div></td>\n" +
                "                <td style=\"width: 6.33%\"><div class=\"col-md-12\"><h4>*CPATAS*</h4></div></td>\n" +
                "                <td style=\"width: 50%\">\n" +
                "                    <table style=\"width: 100%;margin: 10px;\">\n" +
                "                        <tr><td style=\"width: 60%\">Ángulo Pezuña (18%)</td><td style=\"width: 20%\">*C9*</td><td style=\"width: 20%\">7</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Prof. del Talón (22%)</td><td style=\"width: 20%\">*C11*</td><td style=\"width: 20%\">7</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Calidad del hueso (12%)</td><td style=\"width: 20%\">*C12*</td><td style=\"width: 20%\">9</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Patas Traseras Vista Lateral (17%)</td><td style=\"width: 20%\">*C13*</td><td style=\"width: 20%\">5</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Patas Post. Vista trasera (31%)</td><td style=\"width: 20%\">*C14*</td><td style=\"width: 20%\">9</td></tr>\n" +
                "                    </table>\n" +
                "                </td>\n" +
                "                <td style=\"width: 33.33%\">\n" +
                "                    <table style=\"width: 100%\">\n" +
                "                        <tr><td>*D30*</td></tr>\n" +
                "                        <tr><td>*D31*</td></tr>\n" +
                "                        <tr><td>*D32*</td></tr>\n" +
                "                        <tr><td>*D34*</td></tr>\n" +
                "                        <tr><td>*D35*</td></tr>\n" +
                "                        <tr><td>*D36*</td></tr>\n" +
                "                    </table>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "        </table>\n" +
                "        <table><tr><td>&nbsp;</td></tr></table>\n" +
                "        <table border=\"1\" style=\"width: 100%\">\n" +
                "            <tr>\n" +
                "                <td style=\"width: 10.33%\"><div class=\"col-md-12\"><h4>Clase: <b>*CLASE*</b></h4></div></td>\n" +
                "                <td style=\"width: 6.33%\"><div class=\"col-md-12\"><h4>Puntos: <b>*PUNTOS*</b></h4></div></td>\n" +
                "                <td style=\"width: 83.34%\">\n" +
                "                    <h4>Comentarios:<b>*COMENT*</b></h4>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "        </table>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
        File path = new File(directory, "Holstein");
        if (!path.exists()) {
            path.mkdir();
        }
        File file = new File(path, fileName);
        try {
            String tempHTML = HTML_VACA;
            Document document = new Document(PageSize.LETTER);
            ByteArrayInputStream in = null;
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            for (Vaca v : vacas) {
                ///*** MODIFICAR HEADER ***///
                tempHTML = tempHTML.replace("*HATO*",v.getNoHato()+"");
                tempHTML = tempHTML.replace("*MEDALLA*",v.getNoCP());
                tempHTML = tempHTML.replace("*FECHA*",v.getFechaCalif()+"");
                tempHTML = tempHTML.replace("*NOREG*",v.getNoRegistro()+"");
                tempHTML = tempHTML.replace("*REGPADRE*",v.getRegPadre()+"");
                tempHTML = tempHTML.replace("*REGMADRE*",v.getRegmadre()+"");
                tempHTML = tempHTML.replace("*FECHANAC*",v.getFechaNac()+"");
                tempHTML = tempHTML.replace("*FECHAPARTO*",v.getFechaParto()+"");
                tempHTML = tempHTML.replace("*NOLACT*",v.getNoLactancia()+"");

                tempHTML = tempHTML.replace("*CLASE*",v.getClaseCalif());
                tempHTML = tempHTML.replace("*PUNTOS*",v.getPuntosCalif()+"");
                tempHTML = tempHTML.replace("*COMENT*",v.getComentario()+"");

                ///*** REEMPLAZAR CALIFICACIONES ***///
                // FORTALEZA LECHERA
                tempHTML = tempHTML.replace("*C1*",""+v.getC1());    // Estatura
                tempHTML = tempHTML.replace("*C2*",""+v.getC2());    // AltCruz
                tempHTML = tempHTML.replace("*C3*",""+v.getC3());    // Tamaño
                tempHTML = tempHTML.replace("*C4*",""+v.getC4());    // PechoAncho
                tempHTML = tempHTML.replace("*C5*",""+v.getC5());    // Profundidad
                tempHTML = tempHTML.replace("*C24*",""+v.getC24());    // Angularidad

                // ANCA
                tempHTML = tempHTML.replace("*C6*",""+v.getC6());    // Lomo
                tempHTML = tempHTML.replace("*C7*",""+v.getC7());    // Punta
                tempHTML = tempHTML.replace("*C8*",""+v.getC8());      // Anchura
                tempHTML = tempHTML.replace("*C8A*",""+v.getC8a());    // Colocacion

                // PATAS Y PEZUÑAS
                tempHTML = tempHTML.replace("*C9*",""+v.getC9());      // Angulo
                tempHTML = tempHTML.replace("*C11*",""+v.getC11());    // ProfTalón
                tempHTML = tempHTML.replace("*C12*",""+v.getC12());    // CalHuesos
                tempHTML = tempHTML.replace("*C13*",""+v.getC13());    // Aplomo
                tempHTML = tempHTML.replace("*C14*",""+v.getC14());    // VPosterior

                //SISTEMA MAMARIO
                tempHTML = tempHTML.replace("*C15*",""+v.getC15());    // SMProfundidad
                tempHTML = tempHTML.replace("*C16*",""+v.getC16());    // Textura
                tempHTML = tempHTML.replace("*C17*",""+v.getC17());    // LMS
                tempHTML = tempHTML.replace("*C18*",""+v.getC18());    // Inserción Del
                tempHTML = tempHTML.replace("*C19*",""+v.getC19());    // Posición Del
                tempHTML = tempHTML.replace("*C20*",""+v.getC20());    // LongPezones Pos
                tempHTML = tempHTML.replace("*C21*",""+v.getC21());    // UPAltura
                tempHTML = tempHTML.replace("*C22*",""+v.getC22());    // UPAnchura
                tempHTML = tempHTML.replace("*C23*",""+v.getC23());    // UPPosicion

                tempHTML = tempHTML.replace("*CANCA*",""+v.getCss2()+" "+v.getCss2puntos());
                tempHTML = tempHTML.replace("*CMAMARIO*",""+v.getCss4()+" "+v.getCss4puntos());
                tempHTML = tempHTML.replace("*CFORTALEZA*",""+v.getCss1()+" "+v.getCss1puntos());
                tempHTML = tempHTML.replace("*CPATAS*",""+v.getCss3()+" "+v.getCss3puntos());

                if(v.getDef10()>0)tempHTML = tempHTML.replace("*D10*", "<span style=\"background-color: #d43f3a\">10</span> Ano Inclinado / Andvanced anus");
                else tempHTML = tempHTML.replace("*D10*","");
                if(v.getDef11()>0)tempHTML = tempHTML.replace("*D11*", "<span style=\"background-color: #d43f3a\">11</span> Cola Sumida / Advanced Tailhead");
                else tempHTML = tempHTML.replace("*D11*","");
                if(v.getDef12()>0)tempHTML = tempHTML.replace("*D12*", "<span style=\"background-color: #d43f3a\">12</span> Cola Alta / High Tailhead");
                else tempHTML = tempHTML.replace("*D12*","");
                if(v.getDef22()>0)tempHTML = tempHTML.replace("*D22*", "<span style=\"background-color: #d43f3a\">22</span> Inserción delantera Corta / Short Fore");
                else tempHTML = tempHTML.replace("*D22*","");
                if(v.getDef23()>0)tempHTML = tempHTML.replace("*D23*", "<span style=\"background-color: #d43f3a\">23</span> Inserción trasera Corta / Short Rear");
                else tempHTML = tempHTML.replace("*D23*","");
                if(v.getDef24()>0)tempHTML = tempHTML.replace("*D24*", "<span style=\"background-color: #d43f3a\">24</span> Ubre sin forma definida / Lacks Udder Shape");
                else tempHTML = tempHTML.replace("*D24*","");
                if(v.getDef25()>0)tempHTML = tempHTML.replace("*D25*", "<span style=\"background-color: #d43f3a\">25</span> Cuarto Desbalanceado / Unbalanced Quarter");
                else tempHTML = tempHTML.replace("*D25*","");
                if(v.getDef26()>0)tempHTML = tempHTML.replace("*D26*", "<span style=\"background-color: #d43f3a\">26</span> Cuarto Ciego / Blind Quarter");
                else tempHTML = tempHTML.replace("*D26*","");
                if(v.getDef27()>0)tempHTML = tempHTML.replace("*D27*", "<span style=\"background-color: #d43f3a\">27</span> Teta Palmípeda / Webbed Teat");
                else tempHTML = tempHTML.replace("*D27*","");
                if(v.getDef28()>0)tempHTML = tempHTML.replace("*D28*", "<span style=\"background-color: #d43f3a\">28</span> Tetas delanteras hacia atrás / Front teats back");
                else tempHTML = tempHTML.replace("*D28*","");
                if(v.getDef29()>0)tempHTML = tempHTML.replace("*D29*", "<span style=\"background-color: #d43f3a\">29</span> Tetas traseras hacia atrás / Rear teats back");
                else tempHTML = tempHTML.replace("*D29*","");
                if(v.getDef30()>0)tempHTML = tempHTML.replace("*D30*", "<span style=\"background-color: #d43f3a\">30</span> Pezuña Anormal / Abnormal Claw");
                else tempHTML = tempHTML.replace("*D30*","");
                if(v.getDef31()>0)tempHTML = tempHTML.replace("*D31*", "<span style=\"background-color: #d43f3a\">31</span> Cuartillas Débiles / Weak Pastems");
                else tempHTML = tempHTML.replace("*D31*","");
                if(v.getDef32()>0)tempHTML = tempHTML.replace("*D32*", "<span style=\"background-color: #d43f3a\">32</span> Corvejones Toscos / Boggy Hocks");
                else tempHTML = tempHTML.replace("*D32*","");
                if(v.getDef34()>0)tempHTML = tempHTML.replace("*D34*", "<span style=\"background-color: #d43f3a\">34</span> Calambres / Crampy");
                else tempHTML = tempHTML.replace("*D34*","");
                if(v.getDef35()>0)tempHTML = tempHTML.replace("*D35*", "<span style=\"background-color: #d43f3a\">35</span> Patas traseras hacia atrás / Rear legs back");
                else tempHTML = tempHTML.replace("*D35*","");
                if(v.getDef36()>0)tempHTML = tempHTML.replace("*D36*", "<span style=\"background-color: #d43f3a\">36</span> Pezuñas hacia afuera / Toes out front");
                else tempHTML = tempHTML.replace("*D36*","");
                if(v.getDef40()>0)tempHTML = tempHTML.replace("*D40*", "<span style=\"background-color: #d43f3a\">40</span> Cara Chueca / Wry Face");
                else tempHTML = tempHTML.replace("*D40*","");
                if(v.getDef42()>0)tempHTML = tempHTML.replace("*D42*", "<span style=\"background-color: #d43f3a\">42</span> Costillar Anterior Angosto / Shalow Fore Rib");
                else tempHTML = tempHTML.replace("*D42*","");
                if(v.getDef43()>0)tempHTML = tempHTML.replace("*D43*", "<span style=\"background-color: #d43f3a\">43</span> Relleno de Paleta / Weak Crops");
                else tempHTML = tempHTML.replace("*D43*","");
                if(v.getDef44()>0)tempHTML = tempHTML.replace("*D44*", "<span style=\"background-color: #d43f3a\">44</span> Lomo Débil / Weak Back");
                else tempHTML = tempHTML.replace("*D44*","");
                if(v.getDef45()>0)tempHTML = tempHTML.replace("*D45*", "<span style=\"background-color: #d43f3a\">45</span> Costilla Cerrada / Not Well Sprung");
                else tempHTML = tempHTML.replace("*D45*","");

                if(v.getDef46()>0)tempHTML = tempHTML.replace("*D46*", "<span style=\"background-color: #d43f3a\">46</span> Ubre Pendulosa Contraria / Contrary Pendulous Udder");
                else tempHTML = tempHTML.replace("*D46*","");
                if(v.getDef47()>0)tempHTML = tempHTML.replace("*D47*", "<span style=\"background-color: #d43f3a\">47</span> Ubre Pendulosa Delantera / Pendulous Udder Front");
                else tempHTML = tempHTML.replace("*D47*","");
                if(v.getDef48()>0)tempHTML = tempHTML.replace("*D48*", "<span style=\"background-color: #d43f3a\">48</span> Ubre Aglobada / Bulgy Udder");
                else tempHTML = tempHTML.replace("*D48*","");

                in = new ByteArrayInputStream(tempHTML.getBytes());

                XMLWorkerHelper.getInstance().parseXHtml(writer, document, in);

                PdfContentByte canvas = writer.getDirectContentUnder();
                Drawable d = context.getResources().getDrawable(R.drawable.logo180x180);
                Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] bitmapData = stream.toByteArray();
                Image image = Image.getInstance(bitmapData);
                image.setAbsolutePosition(PageSize.LETTER.getWidth()/2-200, PageSize.LETTER.getHeight()/2-100);
                canvas.saveState();
                PdfGState state = new PdfGState();
                state.setFillOpacity(0.2f);
                canvas.setGState(state);
                canvas.addImage(image);
                canvas.restoreState();

                document.newPage();
                tempHTML = HTML_VACA;
            }
            in.close();
            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        }
    }

    public Object createPDFToro(String fileName, Context context, ArrayList<Toro> toros) {
        String HTML_TORO = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n" +
                "        \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"en\" xml:lang=\"en\">\n" +
                "<head>\n" +
                "    <meta name=\"generator\" content=\"HTML\" />\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\" />\n" +
                "    <title></title>\n" +
                "    <style type=\"text/css\">\n" +
                "        html {\n" +
                "            -ms-text-size-adjust: 100%;\n" +
                "            -webkit-text-size-adjust: 100%;\n" +
                "        }\n" +
                "        body {\n" +
                "            margin: 0;\n" +
                "            margin-top: 20px;\n" +
                "        }\n" +
                "        table {\n" +
                "            border-collapse: collapse;\n" +
                "            border-spacing: 0;\n" +
                "        }\n" +
                "        * {\n" +
                "            font-size: 9px;\n" +
                "            -webkit-box-sizing: border-box;\n" +
                "            -moz-box-sizing: border-box;\n" +
                "            box-sizing: border-box;\n" +
                "        }\n" +
                "        *:before,\n" +
                "        *:after {\n" +
                "            -webkit-box-sizing: border-box;\n" +
                "            -moz-box-sizing: border-box;\n" +
                "            box-sizing: border-box;\n" +
                "        }\n" +
                "        html {\n" +
                "            font-size: 9px;\n" +
                "        }\n" +
                "        body {\n" +
                "            font-size: 9px;\n" +
                "            color: #333333;\n" +
                "        }\n" +
                "        @media (min-width: 768px) {\n" +
                "            .container {\n" +
                "                width: 750px;\n" +
                "            }\n" +
                "        }\n" +
                "        @media (min-width: 992px) {\n" +
                "            .container {\n" +
                "                width: 970px;\n" +
                "            }\n" +
                "        }\n" +
                "        @media (min-width: 1200px) {\n" +
                "            .container {\n" +
                "                width: 1170px;\n" +
                "            }\n" +
                "        }\n" +
                "        .row {\n" +
                "            margin-left: -15px;\n" +
                "            margin-right: -15px;\n" +
                "        }\n" +
                "        .col-md-12 {\n" +
                "            position: relative;\n" +
                "            min-height: 1px;\n" +
                "            padding-left: 5px;\n" +
                "            padding-right: 5px;\n" +
                "        }\n" +
                "\n" +
                "        @media (min-width: 992px) {\n" +
                "            .col-md-12 {\n" +
                "                float: left;\n" +
                "            }\n" +
                "            .col-md-12 {\n" +
                "                width: 100%;\n" +
                "            }\n" +
                "\n" +
                "        }\n" +
                "        .clearfix:before,\n" +
                "        .clearfix:after,\n" +
                "        .container:before,\n" +
                "        .container:after,\n" +
                "        .container-fluid:before,\n" +
                "        .container-fluid:after,\n" +
                "        .row:before,\n" +
                "        .row:after {\n" +
                "            content: \" \";\n" +
                "            display: table;\n" +
                "        }\n" +
                "        .clearfix:after,\n" +
                "        .container:after,\n" +
                "        .container-fluid:after,\n" +
                "        .row:after {\n" +
                "            clear: both;\n" +
                "        }\n" +
                "        .ideal{\n" +
                "            color: #269abc;\n" +
                "        }\n" +
                "        table .col-md-12{\n" +
                "            text-align: center !important;\n" +
                "        }\n" +
                "        table .col-md-6{\n" +
                "            font-size: smaller;\n" +
                "        }\n" +
                "        td{\n" +
                "            padding-top: 5px;\n" +
                "            padding-bottom: 5px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"container\">\n" +
                "    <div class=\"row\">\n" +
                "        <div class=\"col-md-12\" style=\"text-align: center\"><h4>ANÁLISIS DE CONFORMACIÓN MACHOS - HOLSTEIN</h4></div>\n" +
                "    </div>\n" +
                "    <div class=\"row\">\n" +
                "        <table border=\"1\" style=\"width: 100%\">\n" +
                "            <tr>\n" +
                "                <td style=\"width: 25%\">Hato:<b>*HATO*</b></td>\n" +
                "                <td style=\"width: 25%\">No. Registro:<b>*NOREG*</b></td>\n" +
                "                <td style=\"width: 25%\">Nombre:<b>*NOMBRE*</b></td>\n" +
                "                <td style=\"width: 25%\">Fecha:<b>*FECHA*</b></td>\n" +
                "            </tr>\n" +
                "            </table>\n" +
                "        <table border=\"1\" style=\"width: 100%\">\n" +
                "            <tr>\n" +
                "                <td style=\"width: 33%\">No. Registro Madre:<b>*REGMADRE*</b></td>\n" +
                "                <td style=\"width: 33%\">No. Registro Padre:<b>*REGPADRE*</b></td>\n" +
                "                <td style=\"width: 34%\">Fecha de Nac:<b>*FECHANAC*</b></td>\n" +
                "            </tr>\n" +
                "        </table>\n" +
                "        <table border=\"1\" style=\"width: 100%\">\n" +
                "            <tr>\n" +
                "                <td style=\"width: 10.33%\">Sección</td>\n" +
                "                <td style=\"width: 6.33%\">Calif.</td>\n" +
                "                <td style=\"width: 50%\">\n" +
                "                    <table style=\"width: 100%;margin: 10px;\">\n" +
                "                        <tr><td style=\"width: 60%\">Características</td><td style=\"width: 20%\">Calif.</td><td style=\"width: 20%\">Ideal</td></tr>\n" +
                "                    </table>\n" +
                "                </td>\n" +
                "                <td style=\"width: 33.33%\">Defectos</td>\n" +
                "            </tr>\n" +
                "        </table>\n" +
                "        <table><tr><td>&nbsp;</td></tr></table>\n" +
                "        <table border=\"1\" style=\"width: 100%\">\n" +
                "            <tr>\n" +
                "                <td style=\"width: 10.33%\"><div class=\"col-md-12\"><h4>Anca (20%)</h4></div></td>\n" +
                "                <td style=\"width: 6.33%\"><div class=\"col-md-12\"><h4>*CANCA*</h4></div></td>\n" +
                "                <td style=\"width: 50%\">\n" +
                "                    <table style=\"width: 100%;margin: 10px;\">\n" +
                "                        <tr><td style=\"width: 60%\">Inclinación del Anca (34%)</td><td style=\"width: 20%\">*C7*</td><td style=\"width: 20%\">5</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Anchura Anca (21%)</td><td style=\"width: 20%\">*C8*</td><td style=\"width: 20%\">9</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Fortaleza Lomo (32%)</td><td style=\"width: 20%\">*C6*</td><td style=\"width: 20%\">9</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Colocación de la Cadera (13%)</td><td style=\"width: 20%\">*C8A*</td><td style=\"width: 20%\">6</td></tr>\n" +
                "                    </table>\n" +
                "                </td>\n" +
                "                <td style=\"width: 33.33%\">\n" +
                "                    <table style=\"width: 100%\">\n" +
                "                        <tr><td>*D10*</td></tr>\n" +
                "                        <tr><td>*D11*</td></tr>\n" +
                "                    </table>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "        </table>\n" +
                "        <table><tr><td>&nbsp;</td></tr></table>\n" +

                "        <table border=\"1\" style=\"width: 100%\">\n" +
                "            <tr>\n" +
                "                <td style=\"width: 10.33%\"><div class=\"col-md-12\"><h4>Fortaleza Lechera (45%)</h4></div></td>\n" +
                "                <td style=\"width: 6.33%\"><div class=\"col-md-12\"><h4>*CFORTALEZA*</h4></div></td>\n" +
                "                <td style=\"width: 50%\">\n" +
                "                    <table style=\"width: 100%;margin: 10px;\">\n" +
                "                        <tr><td style=\"width: 60%\">Estatura (12%)</td><td style=\"width: 20%\">*C1*</td><td style=\"width: 20%\">9</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Altura a la Cruz (3%)</td><td style=\"width: 20%\">*C2*</td><td style=\"width: 20%\">7</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Anchura de Pecho (23%)</td><td style=\"width: 20%\">*C4*</td><td style=\"width: 20%\">9</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Prof. Corporal(17%)</td><td style=\"width: 20%\">*C5*</td><td style=\"width: 20%\">7</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Angularidad (28%)</td><td style=\"width: 20%\">*C24*</td><td style=\"width: 20%\">9</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Tamaño (17%)</td><td style=\"width: 20%\">*C3*</td><td style=\"width: 20%\">9</td></tr>\n" +
                "                    </table>\n" +
                "                </td>\n" +
                "                <td style=\"width: 33.33%\">\n" +
                "                    <table style=\"width: 100%\">\n" +
                "                        <tr><td>*D40*</td></tr>\n" +
                "                        <tr><td>*D42*</td></tr>\n" +
                "                        <tr><td>*D43*</td></tr>\n" +
                "                        <tr><td>*D44*</td></tr>\n" +
                "                        <tr><td>*D45*</td></tr>\n" +
                "                    </table>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "        </table>\n" +
                "        <table><tr><td>&nbsp;</td></tr></table>\n" +
                "        <table border=\"1\" style=\"width: 100%\">\n" +
                "            <tr>\n" +
                "                <td style=\"width: 10.33%\"><div class=\"col-md-12\"><h4>Patas y Pezuñas (35%)</h4></div></td>\n" +
                "                <td style=\"width: 6.33%\"><div class=\"col-md-12\"><h4>*CPATAS*</h4></div></td>\n" +
                "                <td style=\"width: 50%\">\n" +
                "                    <table style=\"width: 100%;margin: 10px;\">\n" +
                "                        <tr><td style=\"width: 60%\">Ángulo Pezuña (18%)</td><td style=\"width: 20%\">*C9*</td><td style=\"width: 20%\">7</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Prof. del Talón (22%)</td><td style=\"width: 20%\">*C11*</td><td style=\"width: 20%\">9</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Calidad del hueso (12%)</td><td style=\"width: 20%\">*C12*</td><td style=\"width: 20%\">9</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Patas Traseras Vista Lateral (17%)</td><td style=\"width: 20%\">*C13*</td><td style=\"width: 20%\">5</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Patas Post. Vista trasera (31%)</td><td style=\"width: 20%\">*C14*</td><td style=\"width: 20%\">9</td></tr>\n" +
                "                    </table>\n" +
                "                </td>\n" +
                "                <td style=\"width: 33.33%\">\n" +
                "                    <table style=\"width: 100%\">\n" +
                "                        <tr><td>*D30*</td></tr>\n" +
                "                        <tr><td>*D31*</td></tr>\n" +
                "                        <tr><td>*D32*</td></tr>\n" +
                "                        <tr><td>*D34*</td></tr>\n" +
                "                        <tr><td>*D35*</td></tr>\n" +
                "                        <tr><td>*D36*</td></tr>\n" +
                "                    </table>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "        </table>\n" +
                "        <table><tr><td>&nbsp;</td></tr></table>\n" +
                "        <table border=\"1\" style=\"width: 100%\">\n" +
                "            <tr>\n" +
                "                <td style=\"width: 10.33%\"><div class=\"col-md-12\"><h4>Clase: <b>*CLASE*</b></h4></div></td>\n" +
                "                <td style=\"width: 6.33%\"><div class=\"col-md-12\"><h4>Puntos: <b>*PUNTOS*</b></h4></div></td>\n" +
                "                <td style=\"width: 83.34%\">\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "        </table>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
        File path = new File(directory, "Holstein");
        if (!path.exists()) {
            path.mkdir();
        }
        File file = new File(path, fileName);
        try {
            String tempHTML = HTML_TORO;
            Document document = new Document(PageSize.LETTER);
            ByteArrayInputStream in = null;
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            for (Toro t : toros) {
                ///*** MODIFICAR HEADER ***///
                tempHTML = tempHTML.replace("*HATO*",t.getNoHato()+"");
                tempHTML = tempHTML.replace("*NOMBRE*",t.getNombre().toUpperCase());
                tempHTML = tempHTML.replace("*FECHA*",t.getFechaCalif()+"");
                tempHTML = tempHTML.replace("*NOREG*",t.getNoRegistro()+"");
                tempHTML = tempHTML.replace("*REGPADRE*",t.getRegPadre()+"");
                tempHTML = tempHTML.replace("*REGMADRE*",t.getRegMadre()+"");
                tempHTML = tempHTML.replace("*FECHANAC*",t.getFechaNac()+"");

                tempHTML = tempHTML.replace("*CLASE*",t.getClaseCalif());
                tempHTML = tempHTML.replace("*PUNTOS*",t.getPuntosCalif()+"");

                ///*** REEMPLAZAR CALIFICACIONES ***///
                //FORTALEZA LECHERA
                tempHTML = tempHTML.replace("*C1*",""+t.getC5());    // Estatura
                tempHTML = tempHTML.replace("*C2*",""+t.getC6());    // AltCruz
                tempHTML = tempHTML.replace("*C3*",""+t.getC10());    // Tamaño
                tempHTML = tempHTML.replace("*C4*",""+t.getC7());    // PechoAncho
                tempHTML = tempHTML.replace("*C5*",""+t.getC8());    // Profundidad
                tempHTML = tempHTML.replace("*C24*",""+t.getC9());    // Angularidad

                //ANCA
                tempHTML = tempHTML.replace("*C6*",""+t.getC3());    // Lomo
                tempHTML = tempHTML.replace("*C7*",""+t.getC1());    // Punta
                tempHTML = tempHTML.replace("*C8*",""+t.getC2());    // Anchura
                tempHTML = tempHTML.replace("*C8A*",""+t.getC4());    // Colocacion

                //PATAS Y PEZUÑAS
                tempHTML = tempHTML.replace("*C9*",""+t.getC11());    // Angulo
                tempHTML = tempHTML.replace("*C11*",""+t.getC12());    // ProfTalón
                tempHTML = tempHTML.replace("*C12*",""+t.getC13());    // CalHuesos
                tempHTML = tempHTML.replace("*C13*",""+t.getC14());    // Aplomo
                tempHTML = tempHTML.replace("*C14*",""+t.getC15());    // VPosterior

                tempHTML = tempHTML.replace("*CANCA*",t.getSs1()+" "+t.getSs1puntos());
                tempHTML = tempHTML.replace("*CFORTALEZA*",t.getSs2()+" "+t.getSs2puntos());
                tempHTML = tempHTML.replace("*CPATAS*",t.getSs3()+" "+t.getSs3puntos());

                if(t.getDef10()>0)tempHTML = tempHTML.replace("*D10*", "<span style=\"background-color: #d43f3a\">10</span> Ano Inclinado / Andvanced anus");
                else tempHTML = tempHTML.replace("*D10*","");
                if(t.getDef11()>0)tempHTML = tempHTML.replace("*D11*", "<span style=\"background-color: #d43f3a\">11</span> Cola Sumida / Advanced Tailhead");
                else tempHTML = tempHTML.replace("*D11*","");
                if(t.getDef30()>0)tempHTML = tempHTML.replace("*D30*", "<span style=\"background-color: #d43f3a\">30</span> Pezuña Anormal / Abnormal Claw");
                else tempHTML = tempHTML.replace("*D30*","");
                if(t.getDef31()>0)tempHTML = tempHTML.replace("*D31*", "<span style=\"background-color: #d43f3a\">31</span> Cuartillas Débiles / Weak Pastems");
                else tempHTML = tempHTML.replace("*D31*","");
                if(t.getDef32()>0)tempHTML = tempHTML.replace("*D32*", "<span style=\"background-color: #d43f3a\">32</span> Corvejones Toscos / Boggy Hocks");
                else tempHTML = tempHTML.replace("*D32*","");
                if(t.getDef34()>0)tempHTML = tempHTML.replace("*D34*", "<span style=\"background-color: #d43f3a\">34</span> Calambres / Crampy");
                else tempHTML = tempHTML.replace("*D34*","");
                if(t.getDef35()>0)tempHTML = tempHTML.replace("*D35*", "<span style=\"background-color: #d43f3a\">35</span> Patas traseras hacia atrás / Rear legs back");
                else tempHTML = tempHTML.replace("*D35*","");
                if(t.getDef36()>0)tempHTML = tempHTML.replace("*D36*", "<span style=\"background-color: #d43f3a\">36</span> Pezuñas hacia afuera / Toes out front");
                else tempHTML = tempHTML.replace("*D36*","");
                if(t.getDef40()>0)tempHTML = tempHTML.replace("*D40*", "<span style=\"background-color: #d43f3a\">40</span> Cara Chueca / Wry Face");
                else tempHTML = tempHTML.replace("*D40*","");
                if(t.getDef42()>0)tempHTML = tempHTML.replace("*D42*", "<span style=\"background-color: #d43f3a\">42</span> Costillar Anterior Angosto / Shalow Fore Rib");
                else tempHTML = tempHTML.replace("*D42*","");
                if(t.getDef43()>0)tempHTML = tempHTML.replace("*D43*", "<span style=\"background-color: #d43f3a\">43</span> Relleno de Paleta / Weak Crops");
                else tempHTML = tempHTML.replace("*D43*","");
                if(t.getDef44()>0)tempHTML = tempHTML.replace("*D44*", "<span style=\"background-color: #d43f3a\">44</span> Lomo Débil / Weak Back");
                else tempHTML = tempHTML.replace("*D44*","");
                if(t.getDef45()>0)tempHTML = tempHTML.replace("*D45*", "<span style=\"background-color: #d43f3a\">45</span> Costilla Cerrada / Not Well Sprung");
                else tempHTML = tempHTML.replace("*D45*","");

                in = new ByteArrayInputStream(tempHTML.getBytes());

                XMLWorkerHelper.getInstance().parseXHtml(writer, document, in);

                PdfContentByte canvas = writer.getDirectContentUnder();
                Drawable d = context.getResources().getDrawable(R.drawable.logo180x180);
                Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] bitmapData = stream.toByteArray();
                Image image = Image.getInstance(bitmapData);
                image.setAbsolutePosition(PageSize.LETTER.getWidth()/2-200, PageSize.LETTER.getHeight()/2-100);
                canvas.saveState();
                PdfGState state = new PdfGState();
                state.setFillOpacity(0.2f);
                canvas.setGState(state);
                canvas.addImage(image);
                canvas.restoreState();

                document.newPage();
                tempHTML = HTML_TORO;
            }
            in.close();
            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        }
    }

    public String getStoragepath() {
        String finalpath= null;
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            String line;
            String[] patharray = new String[10];
            int i = 0;
            int available = 0;


            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                String mount = new String();
                if (line.contains("secure"))
                    continue;
                if (line.contains("asec"))
                    continue;

                if (line.contains("fat")) {// TF card
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        mount = mount.concat(columns[1] + "/requiredfiles");

                        patharray[i] = mount;
                        i++;

                        // check directory is exist or not
                        File dir = new File(mount);
                        if (dir.exists() && dir.isDirectory()) {
                            // do something here

                            available = 1;
                            finalpath = mount;
                            break;
                        } else {

                        }
                    }
                }
            }
            if (available == 1) {

            } else if (available == 0) {
                finalpath = patharray[0];
            }

        } catch (Exception e) {

        }
        return finalpath;
    }
}



