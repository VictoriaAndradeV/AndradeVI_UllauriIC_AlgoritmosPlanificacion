package org.example.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AlgoritmoPrioridad {

    public ResultadoRR ejecutar(List<Proceso> procesosOriginales) {

        // 1) copiamos para no tocar la lista original
        List<Proceso> procesos = new ArrayList<Proceso>();
        for (Proceso pOriginal : procesosOriginales) {
            Proceso copia = new Proceso(
                    pOriginal.getNombre(),
                    pOriginal.getRafaga(),
                    pOriginal.getPrioridad(),
                    pOriginal.getTiempoLlegada()
            );
            procesos.add(copia);
        }

        // 2) ordenamos: primero por tiempo de llegada, luego por prioridad
        //    (menor prioridad = más importante)
        Collections.sort(procesos, new Comparator<Proceso>() {
            @Override
            public int compare(Proceso a, Proceso b) {
                if (a.getTiempoLlegada() != b.getTiempoLlegada()) {
                    return Integer.compare(a.getTiempoLlegada(), b.getTiempoLlegada());
                }
                return Integer.compare(a.getPrioridad(), b.getPrioridad());
            }
        });

        List<DGanttSegmento> segmentos = new ArrayList<DGanttSegmento>();

        int tiempoActual = 0;

        // 3) ejecutamos SIN desalojo, en el orden calculado
        for (Proceso p : procesos) {

            // si aún no ha llegado, adelantamos el reloj hasta su llegada
            if (tiempoActual < p.getTiempoLlegada()) {
                tiempoActual = p.getTiempoLlegada();
            }

            int inicio = tiempoActual;
            int fin = inicio + p.getRafaga();
            tiempoActual = fin;

            // segmento de Gantt
            segmentos.add(new DGanttSegmento(inicio, fin, p.getNombre()));

            int tiempoRetorno = tiempoActual - p.getTiempoLlegada();
            p.setTiempoRetorno(tiempoRetorno);
        }

        // 4) tiempos de espera y promedio
        double sumaEspera = 0.0;
        for (Proceso p : procesos) {
            int tiempoEspera = p.getTiempoRetorno() - p.getRafaga();
            p.setTiempoEspera(tiempoEspera);
            sumaEspera += tiempoEspera;
        }

        double promedioEspera = 0.0;
        if (!procesos.isEmpty()) {
            promedioEspera = sumaEspera / procesos.size();
        }

        // 5) armamos resultado
        ResultadoRR resultado = new ResultadoRR();
        resultado.setProcesos(procesos);
        resultado.setSegmentos(segmentos);
        resultado.setTiempoEsperaPromedio(promedioEspera);

        return resultado;
    }
}
