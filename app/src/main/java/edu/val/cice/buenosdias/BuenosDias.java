package edu.val.cice.buenosdias;

/**
 * Created by Quique on 26/05/2017.
 */

public class BuenosDias {
    //la fecha
    private int anio;
    private int mes;
    private int dia;

    //la foto codificada en BASE64
    private String foto;



    public BuenosDias (int anio, int mes, int dia, String foto)
    {
        this.anio = anio;
        this.mes = mes;
        this.dia = dia;
        this.foto = foto;

    }

    @Override
    public String toString() {
        String straux = null;

        straux = "AÑO "+ this.anio + " MES " + this.mes + " DIA " + this.dia + " FOTO " + this.foto;

        return straux;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
