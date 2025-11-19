package org.example.vista;
import org.example.modelo.Proceso;
import org.example.modelo.DGanttSegmento;
import org.example.modelo.ResultadoRR;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class PanelGrafica extends JPanel {

    private ResultadoRR resultado;
    private Map<String, Color> coloresProcesos;

    private int anchoUnidad = 40;      // píxeles por unidad de tiempo
    private int margenIzquierdo = 40;
    private int margenSuperior = 30;
    private int altoBarra = 30;

    public PanelGrafica(ResultadoRR resultado) {
        this.resultado = resultado;
        this.coloresProcesos = generarColores();

        int tiempoMax = calcularTiempoMaximo();
        int ancho = margenIzquierdo + tiempoMax * anchoUnidad + 60;
        int alto = 120;
        setPreferredSize(new Dimension(ancho, alto));
    }

    private int calcularTiempoMaximo() {
        int max = 0;
        if (resultado != null && resultado.getSegmentos() != null) {
            for (DGanttSegmento seg : resultado.getSegmentos()) {
                if (seg.getFin() > max) {
                    max = seg.getFin();
                }
            }
        }
        return max;
    }

    private Map<String, Color> generarColores() {
        Map<String, Color> mapa = new HashMap<String, Color>();

        // Paleta de colores (puedes cambiarla si quieres)
        Color[] paleta = new Color[]{
                new Color(255, 153, 204), // rosa
                new Color(153, 255, 153), // verde claro
                new Color(255, 204, 102), // amarillo/naranja
                new Color(102, 204, 255), // celeste
                new Color(153, 153, 255), // azul violeta
                new Color(255, 204, 204), // rosa claro
                new Color(204, 255, 204), // verde muy claro
                new Color(255, 255, 153)  // amarillo claro
        };

        int indice = 0;
        if (resultado != null && resultado.getProcesos() != null) {
            for (Proceso p : resultado.getProcesos()) {   // ya sin planificadorcpu.modelo
                String nombre = p.getNombre();
                if (!mapa.containsKey(nombre)) {
                    mapa.put(nombre, paleta[indice % paleta.length]);
                    indice++;
                }
            }
        }

        return mapa;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (resultado == null || resultado.getSegmentos() == null) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g;

        // Dibujar las barras (segmentos)
        List<DGanttSegmento> segmentos = resultado.getSegmentos();
        for (DGanttSegmento seg : segmentos) {
            String nombre = seg.getNombreProceso();
            Color color = coloresProcesos.get(nombre);
            if (color == null) {
                color = Color.LIGHT_GRAY;
            }

            int inicio = seg.getInicio();
            int fin = seg.getFin();
            int duracion = fin - inicio;

            int x = margenIzquierdo + inicio * anchoUnidad;
            int y = margenSuperior;
            int ancho = duracion * anchoUnidad;

            g2.setColor(color);
            g2.fillRect(x, y, ancho, altoBarra);

            g2.setColor(Color.BLACK);
            g2.drawRect(x, y, ancho, altoBarra);

            // Escribir el nombre del proceso dentro de la barra (centrado)
            FontMetrics fm = g2.getFontMetrics();
            int textoAncho = fm.stringWidth(nombre);
            int textoX = x + (ancho - textoAncho) / 2;
            int textoY = y + (altoBarra + fm.getAscent()) / 2 - 2;
            g2.drawString(nombre, textoX, textoY);
        }

        // Dibujar la línea de tiempos (marcas 0,1,2,...)
        int tiempoMax = calcularTiempoMaximo();
        g2.setColor(Color.BLACK);

        int baseY = margenSuperior + altoBarra;

        for (int t = 0; t <= tiempoMax; t++) {
            int x = margenIzquierdo + t * anchoUnidad;
            // pequeña línea vertical
            g2.drawLine(x, baseY, x, baseY + 5);

            String txt = String.valueOf(t);
            int w = g2.getFontMetrics().stringWidth(txt);
            g2.drawString(txt, x - w / 2, baseY + 20);
        }
    }
}
