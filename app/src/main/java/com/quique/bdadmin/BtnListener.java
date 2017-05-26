package com.quique.bdadmin;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Base64;
import android.view.View;
import android.widget.DatePicker;

import java.io.ByteArrayOutputStream;

import edu.val.cice.buenosdias.BuenosDias;

/**
 * Created by Quique on 26/05/2017.
 */

public class BtnListener implements View.OnClickListener {
    private Activity datosActividad;
    private Bitmap imagen;

    public BtnListener(Activity valorActividad, Bitmap valorImagen){
        datosActividad = valorActividad;
        imagen = valorImagen;
    }

    @Override
    public void onClick(View v) {
        DatePicker calendario = (DatePicker) datosActividad.findViewById(R.id.calendario);
        BuenosDias objBD = new BuenosDias(calendario.getYear(), calendario.getMonth(), calendario.getDayOfMonth(), bitmapTOBase64(imagen));

        new AsyncTask(datosActividad).execute(objBD);
    }
    private String bitmapTOBase64(Bitmap imagen){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imagen.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imagenBytes = baos.toByteArray();
        return Base64.encodeToString(imagenBytes,Base64.DEFAULT);
    }
}
