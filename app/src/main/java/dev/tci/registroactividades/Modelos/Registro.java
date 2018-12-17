package dev.tci.registroactividades.Modelos;

public class Registro {
    private String huerta;
    private String huerta_quick;
    private int telefono;
    private String toneladas_aprox;
    private String municipio;
    private String IMEI;
    private String fecha_prospeccion;
    private String latitus;
    private String longitud;
    private String nombre_productor;
    private String record_id;

    public Registro() {
    }

    public String getHuerta() {
        return huerta;
    }

    public void setHuerta(String huerta) {
        this.huerta = huerta;
    }

    public String getHuerta_quick() {
        return huerta_quick;
    }

    public void setHuerta_quick(String huerta_quick) {
        this.huerta_quick = huerta_quick;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getToneladas_aprox() {
        return toneladas_aprox;
    }

    public void setToneladas_aprox(String toneladas_aprox) {
        this.toneladas_aprox = toneladas_aprox;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getFecha_prospeccion() {
        return fecha_prospeccion;
    }

    public void setFecha_prospeccion(String fecha_prospeccion) {
        this.fecha_prospeccion = fecha_prospeccion;
    }

    public String getLatitus() {
        return latitus;
    }

    public void setLatitus(String latitus) {
        this.latitus = latitus;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getNombre_productor() {
        return nombre_productor;
    }

    public void setNombre_productor(String nombre_productor) {
        this.nombre_productor = nombre_productor;
    }

    public String getRecord_id() {
        return record_id;
    }

    public void setRecord_id(String record_id) {
        this.record_id = record_id;
    }
}
