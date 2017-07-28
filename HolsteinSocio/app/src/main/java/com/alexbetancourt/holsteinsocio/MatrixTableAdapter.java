package com.alexbetancourt.holsteinsocio;

/**
 * Created by Alex on 01/07/2015.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MatrixTableAdapter<T> extends BaseTableAdapter {

    private final static int WIDTH_DIP = 55;
    private final static int HEIGHT_DIP = 45;

    private Context context;

    private T[][] table;

    public int width;
    public int height;
    private int color;
    private SharedPreferences sharedPreferences;
    private String sombrear;

    public MatrixTableAdapter(Context context) {
        this(context, null,0,null);
    }

    public MatrixTableAdapter(Context context, T[][] table, int colorEncabezado, String sombrear) {
        this.context = context;
        this.color = colorEncabezado;
        this.sombrear = sombrear;
        Resources r = context.getResources();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        width = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, Integer.parseInt(sharedPreferences.getString("tabla_width", "80")), r.getDisplayMetrics()));
        //width = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 110, r.getDisplayMetrics()));
        height = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, Integer.parseInt(sharedPreferences.getString("tabla_height", "32")), r.getDisplayMetrics()));

        setInformation(table);
    }

    public void setInformation(T[][] table) {
        this.table = table;
    }

    @Override
    public int getRowCount() {
        return table.length - 1;
    }

    @Override
    public int getColumnCount() {
        return table[0].length - 1;
    }

    @Override
    public View getView(int row, int column, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new TextView(context);
            ((TextView) convertView).setGravity(Gravity.CENTER_VERTICAL);
        }
        ((TextView) convertView).setText(table[row + 1][column + 1].toString());

        ((TextView) convertView).setTextSize(Integer.parseInt(sharedPreferences.getString("tabla_texto", "16")));
        String texto = table[row+1][0].toString();
        if (texto.substring(texto.length() - 1, texto.length()).equals(sombrear) || row==0 || row==1) {
            convertView.setBackgroundColor(Color.rgb(204, 255, 153));
            //Log.d("Tabla","VERDE - ROW/COLUMN "+row+"-"+column+" | VALOR:"+table[row + 1][column + 1].toString());
        }
        else if (row == -1 || column == -1) {
            //Log.d("Tabla","TABLA - ROW/COLUMN "+row+"-"+column+" | VALOR:"+table[row + 1][column + 1].toString());
            convertView.setBackgroundColor(color);
            if (texto.length() > 18)
                ((TextView) convertView).setTextSize((Integer.parseInt(sharedPreferences.getString("tabla_texto", "16")))-4);
        }
        else
            convertView.setBackgroundColor(Color.TRANSPARENT);

        return convertView;
    }

    @Override
    public int getHeight(int row) {
        if(row==-1)return (int)(height*1.8);
        return height;
    }

    @Override
    public int getWidth(int column) {
        return width;
    }

    @Override
    public int getItemViewType(int row, int column) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }
}
