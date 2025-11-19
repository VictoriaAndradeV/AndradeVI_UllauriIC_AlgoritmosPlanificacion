package org.example.modelo;

import java.util.ArrayList;
import java.util.List;

public class PlanificadorModelo {
    private List<Proceso> procesos;
    private int totalProcesos;
    private int quantum;

    public PlanificadorModelo() {
        procesos = new ArrayList<Proceso>();
        totalProcesos = 0;
        quantum = 0;
    }

    public void setTotalProcesos(int totalProcesos) {
        this.totalProcesos = totalProcesos;
    }

    public int getTotalProcesos() {
        return totalProcesos;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public int getQuantum() {
        return quantum;
    }

    public void agregarProceso(Proceso proceso) {
        if (procesos.size() < totalProcesos) {
            procesos.add(proceso);
        }
    }

    public int getCantidadActualProcesos() {
        return procesos.size();
    }

    public boolean estaCompleto() {
        return totalProcesos > 0 && procesos.size() == totalProcesos;
    }

    public List<Proceso> getProcesos() {
        return procesos;
    }

    public ResultadoRR ejecutarRoundRobin() {
        AlgoritmoRR rr = new AlgoritmoRR();
        return rr.ejecutar(procesos, quantum);
    }

    public void limpiar() {
        procesos.clear();
        totalProcesos = 0;
        quantum = 0;
    }
}
