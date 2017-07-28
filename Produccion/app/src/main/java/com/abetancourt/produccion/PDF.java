package com.abetancourt.produccion;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;

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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by Alex on 26/07/2015.
 */
public class PDF {

    String HTML;

    public PDF(Context context){
        HTML = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\"\n" +
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
                "            padding-left: 15px;\n" +
                "            padding-right: 15px;\n" +
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
                "                <td style=\"width: 40%\">Socio:<b>*SOCIO*</b></td>\n" +
                "                <td style=\"width: 40%\">Fecha:<b>*FECHA*</b></td>\n" +
                "            </tr>\n" +
                "            </table>\n" +
                "        <table border=\"1\" style=\"width: 100%\">\n" +
                "            <tr>\n" +
                "                <td style=\"width: 25%\">No. Registro:<b>*NOREG*</b></td>\n" +
                "                <td style=\"width: 25%\">No. Registro Padre:<b>*REGPADRE*</b></td>\n" +
                "                <td style=\"width: 16%\">Fecha de Nac:<b>*FECHANAC*</b></td>\n" +
                "                <td style=\"width: 16%\">Fecha de Parto:<b>*FECHAPARTO*</b></td>\n" +
                "                <td style=\"width: 16%\">No. de Lactancia:<b>*NOLACT*</b></td>\n" +
                "            </tr>\n" +
                "        </table>\n" +
                "        <table border=\"1\" style=\"width: 100%\">\n" +
                "            <tr>\n" +
                "                <td style=\"width: 8.33%\">Sección</td>\n" +
                "                <td style=\"width: 8.33%\">Calif.</td>\n" +
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
                "                <td style=\"width: 8.33%\"><div class=\"col-md-12\"><h4>Anca (10%)</h4></div></td>\n" +
                "                <td style=\"width: 8.33%\"><div class=\"col-md-12\"><h4>*CANCA*</h4></div></td>\n" +
                "                <td style=\"width: 50%\">\n" +
                "                    <table style=\"width: 100%;margin: 10px;\">\n" +
                "                        <tr><td style=\"width: 60%\">Inclinación del Anca (34%)</td><td style=\"width: 20%\">*C7*</td><td style=\"width: 20%\">6</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Anchura Anca (21%)</td><td style=\"width: 20%\">*C8*</td><td style=\"width: 20%\">8</td></tr>\n" +
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
                "                <td style=\"width: 8.33%\"><div class=\"col-md-12\"><h4>Sistema Mamario (42%)</h4></div></td>\n" +
                "                <td style=\"width: 8.33%\"><div class=\"col-md-12\"><h4>*CMAMARIO*</h4></div></td>\n" +
                "                <td style=\"width: 50%\">\n" +
                "                    <table style=\"width: 100%;margin: 10px;\">\n" +
                "                        <tr><td style=\"width: 60%\">Profundidad Ubre (16%)</td><td style=\"width: 20%\">*C15*</td><td style=\"width: 20%\">6</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Textura Ubre (14%)</td><td style=\"width: 20%\">*C16*</td><td style=\"width: 20%\">8</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Ligamento Medio (14%)</td><td style=\"width: 20%\">*C17*</td><td style=\"width: 20%\">9</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Inserción delantera(18%)</td><td style=\"width: 20%\">*C18*</td><td style=\"width: 20%\">6</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Posc. Tetas Delanteras (7%)</td><td style=\"width: 20%\">*C19*</td><td style=\"width: 20%\">6</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Altura Ubre Trasera (12%)</td><td style=\"width: 20%\">*C21*</td><td style=\"width: 20%\">8</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Anchura Ubre Trasera(10%)</td><td style=\"width: 20%\">*C22*</td><td style=\"width: 20%\">9</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Posc.Pezones posteriores(7%)</td><td style=\"width: 20%\">*C23*</td><td style=\"width: 20%\">6</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Longitud de Pezones (2%)</td><td style=\"width: 20%\">*C20*</td><td style=\"width: 20%\">6</td></tr>\n" +
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
                "                    </table>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "        </table>\n" +
                "        <table><tr><td>&nbsp;</td></tr></table>\n" +
                "        <table border=\"1\" style=\"width: 100%\">\n" +
                "            <tr>\n" +
                "                <td style=\"width: 8.33%\"><div class=\"col-md-12\"><h4>Fortaleza Lechera (20%)</h4></div></td>\n" +
                "                <td style=\"width: 8.33%\"><div class=\"col-md-12\"><h4>*CFORTALEZA*</h4></div></td>\n" +
                "                <td style=\"width: 50%\">\n" +
                "                    <table style=\"width: 100%;margin: 10px;\">\n" +
                "                        <tr><td style=\"width: 60%\">Estatura (12%)</td><td style=\"width: 20%\">*C1*</td><td style=\"width: 20%\">6</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Altura a la Cruz (3%)</td><td style=\"width: 20%\">*C2*</td><td style=\"width: 20%\">8</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Anchura de Pecho (23%)</td><td style=\"width: 20%\">*C4*</td><td style=\"width: 20%\">9</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Prof. Corporal(17%)</td><td style=\"width: 20%\">*C5*</td><td style=\"width: 20%\">6</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Angularidad (28%)</td><td style=\"width: 20%\">*C24*</td><td style=\"width: 20%\">6</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Tamaño (17%)</td><td style=\"width: 20%\">*C3*</td><td style=\"width: 20%\">8</td></tr>\n" +
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
                "                <td style=\"width: 8.33%\"><div class=\"col-md-12\"><h4>Patas y Pezuñas (28%)</h4></div></td>\n" +
                "                <td style=\"width: 8.33%\"><div class=\"col-md-12\"><h4>*CPATAS*</h4></div></td>\n" +
                "                <td style=\"width: 50%\">\n" +
                "                    <table style=\"width: 100%;margin: 10px;\">\n" +
                "                        <tr><td style=\"width: 60%\">Ángulo Pezuña (18%)</td><td style=\"width: 20%\">*C9*</td><td style=\"width: 20%\">6</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Prof. del Talón (22%)</td><td style=\"width: 20%\">*C11*</td><td style=\"width: 20%\">8</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Calidad del hueso (12%)</td><td style=\"width: 20%\">*C12*</td><td style=\"width: 20%\">9</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Patas Traseras Vista Lateral (17%)</td><td style=\"width: 20%\">*C13*</td><td style=\"width: 20%\">6</td></tr>\n" +
                "                        <tr><td style=\"width: 60%\">Patas Post. Vista trasera (31%)</td><td style=\"width: 20%\">*C14*</td><td style=\"width: 20%\">6</td></tr>\n" +
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
                "                <td style=\"width: 8.33%\"><div class=\"col-md-12\"><h4>Clase:<b>*CLASE*</b></h4></div></td>\n" +
                "                <td style=\"width: 8.33%\"><div class=\"col-md-12\"><h4>Puntos:<b>*PUNTOS*</b></h4></div></td>\n" +
                "                <td style=\"width: 83.34%\">\n" +
                "                    <h4>Comentarios:<b>*COMENT*</b></h4>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "        </table>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";

    }


    public Object createPDF(String fileName, Context context, ArrayList<Vaca> vacas) {
        File path = new File(Environment.getExternalStorageDirectory(), "HolsteinCalificador");
        if (!path.exists()) {
            path.mkdir();
        }
        File file = new File(path, fileName);
        try {
            String tempHTML = HTML;
            Document document = new Document(PageSize.LETTER);
            ByteArrayInputStream in = null;
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            for (Vaca v : vacas) {
                ///*** MODIFICAR HEADER ***///
                tempHTML = tempHTML.replace("*HATO*",v.getNoHato()+"");

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
                tempHTML = HTML;
            }
            in.close();
            document.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        }
    }


    public class MyFont implements FontProvider {
        private static final String FONT_PATH = "/system/fonts/DroidSans.ttf";
        private static final String FONT_ALIAS = "my_font";

        public MyFont(){ FontFactory.register(FONT_PATH, FONT_ALIAS); }

        @Override
        public Font getFont(String fontname, String encoding, boolean embedded,
                            float size, int style, BaseColor color){

            return FontFactory.getFont(FONT_ALIAS, BaseFont.IDENTITY_H,
                    BaseFont.EMBEDDED, size, style, color);
        }

        @Override
        public boolean isRegistered(String name) { return name.equals( FONT_ALIAS ); }
    }
}



