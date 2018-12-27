package dev.tci.registroactividades.Modelos;

public class AgendaVisitas {
    private String Fecha;
    private String huerta;
    private String latitud;
    private String longitud;
    private String productor;
    private String record;
    private long telefono;

    public AgendaVisitas() {
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        this.Fecha = fecha;
    }

    public String getHuerta() {
        return huerta;
    }

    public void setHuerta(String huerta) {
        this.huerta = huerta;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getProductor() {
        return productor;
    }

    public void setProductor(String productor) {
        this.productor = productor;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

}
