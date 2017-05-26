package com.quique.bdadmin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

/**
 * Created by Quique on 26/05/2017.
 */

public class FABListener implements View.OnClickListener {
    private Bitmap imagen;

    public FABListener(Bitmap valorImagen){
        imagen = valorImagen;
    }

    @Override
    public void onClick(View v) {
        Log.d("MENSAJE", "DETECTADO CLICK EN ENVIAR - " +imagen.getWidth() + " / " + imagen.getHeight() );
        Intent datosIntent = new Intent(v.getContext(), SendImage.class);
        datosIntent.putExtra("imagen", imagen);
        Log.d("MENSAJE", "CARGAMOS CODIGO EN INTENT Y REDIRIGIMOS FLUJO");
        v.getContext().startActivity(datosIntent);
    }
}
