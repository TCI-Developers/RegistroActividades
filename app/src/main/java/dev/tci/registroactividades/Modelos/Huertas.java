package dev.tci.registroactividades.Modelos;

public class Huertas {
    private String nombreHuerta;
    private String nombreProductor;
    private String Fecha;

    public Huertas() {
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getNombreHuerta() {
        return nombreHuerta;
    }

    public void setNombreHuerta(String nombreHuerta) {
        this.nombreHuerta = nombreHuerta;
    }

    public String getNombreProductor() {
        return nombreProductor;
    }

    public void setNombreProductor(String nombreProductor) {
        this.nombreProductor = nombreProductor;
    }
}

