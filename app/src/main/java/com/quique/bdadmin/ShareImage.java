package com.quique.bdadmin;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static android.R.attr.path;

public class ShareImage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_image);

        ActivityCompat.requestPermissions(this, new String []{Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {


            Intent datosIntent = getIntent();
            if (Intent.ACTION_SEND.equals(datosIntent.getAction())) {
                Log.d("MENSAJE", "DETECTADO");
                Uri uriImagen = datosIntent.getParcelableExtra(Intent.EXTRA_STREAM);

                try{
                    String codigoImagen="";
                    Log.d("MENSAJE", "GENERAMOS EL ARCHIVO BITMAP");
                    Bitmap datosImagen = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriImagen);
                    Log.d("MENSAJE", "RECUPERAMOS LA RUTA DE LA IMAGEN");
                    String rutaImagen = obtenerPathURI(uriImagen);
                    if(null != rutaImagen) {
                        Log.d("MENSAJE", "LA IMAGEN SE ENCUENTRA EN LA MEMORIA DEL TELEFONO");

                        Log.d("MENSAJE", "RECUPERAMOS LOS DATOS EXIF: " + rutaImagen);
                        ExifInterface exif = new ExifInterface(rutaImagen);
                        Log.d("MENSAJE", "RECUPERAMOS LA ORIENTACION");
                        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
                        Log.d("MENSAJE", "ROTAMOS LA IMAGEN");
                        datosImagen = rotarImagen(datosImagen, orientation);
                        Log.d("MENSAJE", "CARGAMOS IMAGEN ROTADA");
                        ((ImageView) findViewById(R.id.imageShared)).setImageBitmap(datosImagen);
                    } else {
                        Log.d("MENSAJE", "LA IMAGEN ES COMPARTIDA DESDE FUERA DE LA GALERIA");

                        Log.d("MENSAJE", "CARGAMOS IMAGEN SELECCIONADA");
                        ((ImageView) findViewById(R.id.imageShared)).setImageURI(uriImagen);
                        Log.d("MENSAJE", "GENERAMOS LA IMAGEN DINÁMICAMENTE");
                        InputStream imagenStream = getContentResolver().openInputStream(uriImagen);
                        datosImagen = BitmapFactory.decodeStream(imagenStream);
                    }

                    if(datosImagen.getHeight() > 100 || datosImagen.getWidth() > 100)
                        datosImagen = resizeImagen(datosImagen, 100, 100);

                    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnFABEnviar);
                    fab.setOnClickListener(new FABListener(datosImagen));

                } catch (Exception e) {
                    Toast.makeText(this, "Error en la recuperación de la imagen: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            Toast.makeText(this, "No se disponen de acceder a la galería de imágenes", Toast.LENGTH_LONG).show();
        }
    }
    private String obtenerPathURI(Uri datosURI){
        Cursor cursor = getContentResolver().query(datosURI, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
    private Bitmap rotarImagen(Bitmap imagen, int orientacion){
        Matrix matrix = new Matrix();
        switch (orientacion) {
            case ExifInterface.ORIENTATION_NORMAL:
                return imagen;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return imagen;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(imagen, 0, 0, imagen.getWidth(), imagen.getHeight(), matrix, true);
            imagen.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }
    private Bitmap resizeImagen(Bitmap image, int maxWidth, int maxHeight) {
        int width = image.getWidth();
        int height = image.getHeight();
        float ratioBitmap = (float) width / (float) height;
        float ratioMax = (float) maxWidth / (float) maxHeight;

        int finalWidth = maxWidth;
        int finalHeight = maxHeight;
        if (ratioMax > 1) {
            finalWidth = (int) ((float)maxHeight * ratioBitmap);
        } else {
            finalHeight = (int) ((float)maxWidth / ratioBitmap);
        }
        image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
        return image;
    }
}
