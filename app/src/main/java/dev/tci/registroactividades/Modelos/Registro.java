package dev.tci.registroactividades.Modelos;

public class Registro {
    private String Origenactividad;
    private Double latitud;
    private Double longitud;
    private String ultimaactividad;
    private String ultimafecha;
    private String ultimahora;
    private String productor;

    public Registro() {
    }

    public String getProductor() {
        return productor;
    }

    public void setProductor(String productor) {
        this.productor = productor;
    }

    public String getOrigenactividad() {
        return Origenactividad;
    }

    public void setOrigenactividad(String origenactividad) {
        Origenactividad = origenactividad;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getUltimaactividad() {
        return ultimaactividad;
    }

    public void setUltimaactividad(String ultimaactividad) {
        this.ultimaactividad = ultimaactividad;
    }

    public String getUltimafecha() {
        return ultimafecha;
    }

    public void setUltimafecha(String ultimafecha) {
        this.ultimafecha = ultimafecha;
    }

    public String getUltimahora() {
        return ultimahora;
    }

    public void setUltimahora(String ultimahora) {
        this.ultimahora = ultimahora;
    }
}
