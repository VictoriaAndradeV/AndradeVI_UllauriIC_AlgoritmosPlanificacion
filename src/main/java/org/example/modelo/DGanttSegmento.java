package org.example.modelo;

public class DGanttSegmento {

    private int inicio;
    private int fin;
    private String nombreProceso;

    public DGanttSegmento(int inicio, int fin, String nombreProceso) {
        this.inicio = inicio;
        this.fin = fin;
        this.nombreProceso = nombreProceso;
    }

    public int getInicio() {
        return inicio;
    }

    public void setInicio(int inicio) {
        this.inicio = inicio;
    }

    public int getFin() {
        return fin;
    }

    public void setFin(int fin) {
        this.fin = fin;
    }

    public String getNombreProceso() {
        return nombreProceso;
    }

    public void setNombreProceso(String nombreProceso) {
        this.nombreProceso = nombreProceso;
    }
}
