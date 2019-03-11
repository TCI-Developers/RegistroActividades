package dev.tci.registroactividades.Modelos;

public class Huertas {
    private String nombreHuerta;
    private String nombreProductor;
    private String TelProductor;
    private String Fecha;
    private String Contacto;
    private String TelContacto;
    private String HUE;

    public String getTelProductor() {
        return TelProductor;
    }

    public void setTelProductor(String telProductor) {
        TelProductor = telProductor;
    }

    public String getTelContacto() {
        return TelContacto;
    }

    public void setTelContacto(String telContacto) {
        TelContacto = telContacto;
    }

    public String getHUE() {
        return HUE;
    }

    public void setHUE(String HUE) {
        this.HUE = HUE;
    }

    public Huertas() {
    }

    public String getContacto() {
        return Contacto;
    }

    public void setContacto(String contacto) {
        Contacto = contacto;
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

