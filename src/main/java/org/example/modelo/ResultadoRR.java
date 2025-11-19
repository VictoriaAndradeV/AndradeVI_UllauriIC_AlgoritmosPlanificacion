package org.example.modelo;

import java.util.List;

public class ResultadoRR {

    private List<Proceso> procesos;
    private List<DGanttSegmento> segmentos;
    private double tiempoEsperaPromedio;

    public List<Proceso> getProcesos() {
        return procesos;
    }

    public void setProcesos(List<Proceso> procesos) {
        this.procesos = procesos;
    }

    public List<DGanttSegmento> getSegmentos() {
        return segmentos;
    }

    public void setSegmentos(List<DGanttSegmento> segmentos) {
        this.segmentos = segmentos;
    }

    public double getTiempoEsperaPromedio() {
        return tiempoEsperaPromedio;
    }

    public void setTiempoEsperaPromedio(double tiempoEsperaPromedio) {
        this.tiempoEsperaPromedio = tiempoEsperaPromedio;
    }
}
