package org.example.modelo;

import java.util.*;

public class AlgoritmoRR {

    public ResultadoRR ejecutar(List<Proceso> procesosOriginales, int quantum) {

        // 1) Copiamos los procesos para no modificar la lista original
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

        // 2) Ordenamos por tiempo de llegada
        Collections.sort(procesos, new Comparator<Proceso>() {
            @Override
            public int compare(Proceso a, Proceso b) {
                return Integer.compare(a.getTiempoLlegada(), b.getTiempoLlegada());
            }
        });

        List<DGanttSegmento> segmentos = new ArrayList<DGanttSegmento>();
        Queue<Proceso> colaListos = new LinkedList<Proceso>();

        int tiempoActual = 0;
        int indiceLlegada = 0; // siguiente proceso de la lista "procesos" que aún no se encola
        int n = procesos.size();

        // 3) Bucle principal
        while (indiceLlegada < n || !colaListos.isEmpty()) {

            // 3.1) Encolamos todos los procesos que YA hayan llegado
            while (indiceLlegada < n &&
                    procesos.get(indiceLlegada).getTiempoLlegada() <= tiempoActual) {

                colaListos.offer(procesos.get(indiceLlegada));
                indiceLlegada++;
            }

            // 3.2) Si no hay listos, saltamos al próximo que llegue
            if (colaListos.isEmpty()) {
                Proceso siguiente = procesos.get(indiceLlegada);
                tiempoActual = siguiente.getTiempoLlegada();
                colaListos.offer(siguiente);
                indiceLlegada++;
            }

            // 3.3) Tomamos el primero de la cola
            Proceso actual = colaListos.poll();

            int tiempoAUsar = Math.min(actual.getTiempoRestante(), quantum);

            int inicio = tiempoActual;
            int fin = tiempoActual + tiempoAUsar;
            tiempoActual = fin;

            // 3.4) Registramos el segmento en el diagrama de Gantt
            segmentos.add(new DGanttSegmento(inicio, fin, actual.getNombre()));

            // 3.5) Actualizamos tiempo restante
            actual.setTiempoRestante(actual.getTiempoRestante() - tiempoAUsar);

            // 3.6) Si terminó, calculamos tiempo de retorno; si no, vuelve a la cola
            if (actual.getTiempoRestante() == 0) {
                int tiempoRetorno = tiempoActual - actual.getTiempoLlegada();
                actual.setTiempoRetorno(tiempoRetorno);
            } else {
                // ¡IMPORTANTE!: lo reencolamos AHORA,
                // y los procesos que llegaron durante este trozo
                // se encolarán recién al inicio de la próxima iteración,
                // después de él -> así coincide con el Excel de tu profe.
                colaListos.offer(actual);
            }
        }

        // 4) Cálculo de tiempo de espera y promedio
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

        // 5) Armamos el resultado
        ResultadoRR resultado = new ResultadoRR();
        resultado.setProcesos(procesos);
        resultado.setSegmentos(segmentos);
        resultado.setTiempoEsperaPromedio(promedioEspera);

        return resultado;
    }
}
