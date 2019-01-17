package dev.tci.registroactividades.Modelos;

import java.io.Serializable;

public class FormatoCalidad implements Serializable {

    private int bano;
    private int cal32;
    private int cal36;
    private int cal40;
    private int cal48;
    private int cal60;
    private int cal70;
    private int cal84;
    private int cal96;
    private int canica;
    private int lacrado;
    private String comedor;
    private String fecha;
    private String hora;
    private double latitud;
    private double longitud;
    private int ncuadrillas;
    private String huerta;
    private String productor;
    private long telefono;
    private long ton_prox;
    private String municipio;
    private int rona;
    private int rosado;
    private int trips;
    private int quemado;
    private int viruela;
    private int varicela;
    private String url;
    private String baseurl;
    private String conceptoBitacora;
    private String campoBitacora;
    private int positionMun;
    private String record;
    private int status;
    private int subido;
    private String contacto;
    private long contacTele;
    private int noComedor;
    private boolean checkBanio;
    private String floracion;
    private String tipoHuerta;
    private int Nofloracion;
    private int NotipoHuerta;

    public FormatoCalidad() {
    }

    public int getNofloracion() {
        return Nofloracion;
    }

    public void setNofloracion(int nofloracion) {
        Nofloracion = nofloracion;
    }

    public int getNotipoHuerta() {
        return NotipoHuerta;
    }

    public void setNotipoHuerta(int notipoHuerta) {
        NotipoHuerta = notipoHuerta;
    }

    public String getFloracion() {
        return floracion;
    }

    public void setFloracion(String floracion) {
        this.floracion = floracion;
    }

    public String getTipoHuerta() {
        return tipoHuerta;
    }

    public void setTipoHuerta(String tipoHuerta) {
        this.tipoHuerta = tipoHuerta;
    }

    public boolean isCheckBanio() {
        return checkBanio;
    }

    public void setCheckBanio(boolean checkBanio) {
        this.checkBanio = checkBanio;
    }

    public int getNoComedor() {
        return noComedor;
    }

    public void setNoComedor(int noComedor) {
        this.noComedor = noComedor;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public long getContacTele() {
        return contacTele;
    }

    public void setContacTele(long contacTele) {
        this.contacTele = contacTele;
    }

    public int getSubido() {
        return subido;
    }

    public void setSubido(int subido) {
        this.subido = subido;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBaseurl() {
        return baseurl;
    }

    public void setBaseurl(String baseurl) {
        this.baseurl = baseurl;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public int getPositionMun() {
        return positionMun;
    }

    public String getCampoBitacora() {
        return campoBitacora;
    }

    public void setCampoBitacora(String campoBitacora) {
        this.campoBitacora = campoBitacora;
    }

    public void setPositionMun(int positionMun) {
        this.positionMun = positionMun;
    }

    public String getConcepto() {
        return conceptoBitacora;
    }

    public void setConcepto(String concepto) {
        this.conceptoBitacora = concepto;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getBano() {
        return bano;
    }

    public void setBano(int bano) {
        this.bano = bano;
    }

    public int getCal32() {
        return cal32;
    }

    public void setCal32(int cal32) {
        this.cal32 = cal32;
    }

    public int getCal36() {
        return cal36;
    }

    public void setCal36(int cal36) {
        this.cal36 = cal36;
    }

    public int getCal40() {
        return cal40;
    }

    public void setCal40(int cal40) {
        this.cal40 = cal40;
    }

    public int getCal48() {
        return cal48;
    }

    public void setCal48(int cal48) {
        this.cal48 = cal48;
    }

    public int getCal60() {
        return cal60;
    }

    public void setCal60(int cal60) {
        this.cal60 = cal60;
    }

    public int getCal70() {
        return cal70;
    }

    public void setCal70(int cal70) {
        this.cal70 = cal70;
    }

    public int getCal84() {
        return cal84;
    }

    public void setCal84(int cal84) {
        this.cal84 = cal84;
    }

    public int getCal96() {
        return cal96;
    }

    public void setCal96(int cal96) {
        this.cal96 = cal96;
    }

    public int getCanica() {
        return canica;
    }

    public void setCanica(int canica) {
        this.canica = canica;
    }

    public int getLacrado() {
        return lacrado;
    }

    public void setLacrado(int lacrado) {
        this.lacrado = lacrado;
    }

    public String getComedor() {
        return comedor;
    }

    public void setComedor(String comedor) {
        this.comedor = comedor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public int getNcuadrillas() {
        return ncuadrillas;
    }

    public void setNcuadrillas(int ncuadrillas) {
        this.ncuadrillas = ncuadrillas;
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

    public int getRona() {
        return rona;
    }

    public void setRona(int rona) {
        this.rona = rona;
    }

    public int getRosado() {
        return rosado;
    }

    public void setRosado(int rosado) {
        this.rosado = rosado;
    }

    public int getTrips() {
        return trips;
    }

    public void setTrips(int trips) {
        this.trips = trips;
    }

    public int getQuemado() {
        return quemado;
    }

    public void setQuemado(int quemado) {
        this.quemado = quemado;
    }

    public int getViruela() {
        return viruela;
    }

    public void setViruela(int viruela) {
        this.viruela = viruela;
    }

    public int getVaricela() {
        return varicela;
    }

    public void setVaricela(int varicela) {
        this.varicela = varicela;
    }
}
