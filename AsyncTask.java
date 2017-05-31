package com.quique.bdadmin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.val.cice.buenosdias.BuenosDias;

/**
 * Created by Quique on 26/05/2017.
 */

public class AsyncTask extends android.os.AsyncTask<BuenosDias, Void, String> {
    private final static String urlServer = "http://femxa-ebtm.rhcloud.com/BuenosDiasBebe";
    private Activity datosActividad;
    private ProgressDialog progDailog;

    public AsyncTask(Activity valorActividad){
        this.datosActividad = valorActividad;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progDailog = new ProgressDialog(datosActividad);
        progDailog.setMessage("Enviando datos al servidor...");
        progDailog.setIndeterminate(false);
        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDailog.setCancelable(true);
        progDailog.show();
    }

    @Override
    protected String doInBackground(BuenosDias... parametros) {
        String respuesta = "";

        HttpURLConnection http = null;

        try{
            URL datosUrl = new URL(urlServer);
            http = (HttpURLConnection)datosUrl.openConnection();
            http.setRequestMethod("POST");

            Gson gson = new Gson();
            String infoEnvio = gson.toJson(parametros[0]);

            OutputStream outputStream = http.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(outputStream);
            osw.write(infoEnvio);
            osw.close();

            respuesta = (http.getResponseCode() == HttpURLConnection.HTTP_OK) ? "OK" : null;
        } catch (Exception e) {
            Toast.makeText(datosActividad, "ERROR EN LA PUBLICACIÓN DE LA IMAGEN: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        http.disconnect();

        return respuesta;
    }

    @Override
    protected void onPostExecute(String s) {
        progDailog.dismiss();
        /*
        if(s == "OK")
            Toast.makeText(datosActividad, "PUBLICACIÓN SATISFACTORIA", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(datosActividad, "ERROR EN LA PUBLICACIÓN", Toast.LENGTH_LONG).show();
        */
    }
}
