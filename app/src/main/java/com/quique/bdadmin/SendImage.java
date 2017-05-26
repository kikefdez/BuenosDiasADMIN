package com.quique.bdadmin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;

import java.io.ByteArrayOutputStream;

public class SendImage extends AppCompatActivity {
    private Bitmap datosImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MENSAJE", "INICIO DE ACTIVIDAD DE ENVÍO");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_image);

        Intent datosIntent = getIntent();
        Bundle datosBundle = datosIntent.getExtras();
        datosImagen = datosBundle.getParcelable("imagen");

        Button btnEnviar = (Button) findViewById(R.id.btnFPEnviar);
        btnEnviar.setOnClickListener(new BtnListener(this, datosImagen));
    }

}
