package dev.tci.registroactividades.Modelos;

public class Registro {
    private String Origenactividad;
    private String latitud;
    private String longitud;
    private String ultimaactividad;
    private String ultimafecha;
    private String ultimahora;

    public Registro() {
    }

    public String getOrigenactividad() {
        return Origenactividad;
    }

    public void setOrigenactividad(String origenactividad) {
        Origenactividad = origenactividad;
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
