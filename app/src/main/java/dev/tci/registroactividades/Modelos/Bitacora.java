package dev.tci.registroactividades.Modelos;

public class Bitacora {
    private String huerta;
    private String productor;
    private long telefono;
    private long ton_prox;
    private String municipio;

    public Bitacora() {
    }

    public String getHuerta() {
        return huerta;
    }

    public void setHuerta(String huerta) {
        this.huerta = huerta;
    }

    public String getProductor() {
        return productor;
    }

    public void setProductor(String productor) {
        this.productor = productor;
    }

    public long getTelefono() {
        return telefono;
    }

    public void setTelefono(long telefono) {
        this.telefono = telefono;
    }

    public long getTon_prox() {
        return ton_prox;
    }

    public void setTon_prox(long ton_prox) {
        this.ton_prox = ton_prox;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }
}
