package org.example.vista;

import org.example.modelo.Proceso;
import org.example.modelo.ResultadoRR;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaResultado extends JFrame {

    public VentanaResultado(ResultadoRR resultado) {
        setTitle("Resultados - Round Robin");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        List<Proceso> procesos = resultado.getProcesos();

        // ===== Tabla de TIEMPO DE ESPERA =====
        DefaultTableModel modeloEspera = new DefaultTableModel(
                new Object[]{"Proceso", "Tiempo de espera"}, 0
        );

        double sumaEspera = 0;
        for (Proceso p : procesos) {
            Object[] fila = new Object[]{
                    p.getNombre(),
                    p.getTiempoEspera()
            };
            modeloEspera.addRow(fila);
            sumaEspera += p.getTiempoEspera();
        }

        // Fila TOTAL
        modeloEspera.addRow(new Object[]{"TOTAL", (int) sumaEspera});

        JTable tablaEspera = new JTable(modeloEspera);
        JScrollPane scrollEspera = new JScrollPane(tablaEspera);
        scrollEspera.setBorder(BorderFactory.createTitledBorder("TIEMPO DE ESPERA"));

        // ===== Tabla de TIEMPO DE RETORNO =====
        DefaultTableModel modeloRetorno = new DefaultTableModel(
                new Object[]{"Proceso", "Tiempo de retorno"}, 0
        );

        for (Proceso p : procesos) {
            Object[] fila = new Object[]{
                    p.getNombre(),
                    p.getTiempoRetorno()
            };
            modeloRetorno.addRow(fila);
        }

        JTable tablaRetorno = new JTable(modeloRetorno);
        JScrollPane scrollRetorno = new JScrollPane(tablaRetorno);
        scrollRetorno.setBorder(BorderFactory.createTitledBorder("TIEMPO DE RETORNO"));

        // Panel central con las dos tablas
        JPanel panelCentro = new JPanel(new GridLayout(1, 2, 10, 10));
        panelCentro.add(scrollEspera);
        panelCentro.add(scrollRetorno);

        // Etiqueta con el promedio de tiempo de espera
        double promedio = resultado.getTiempoEsperaPromedio();
        JLabel lblPromedio = new JLabel("Tiempo de espera promedio --> " + promedio);
        lblPromedio.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        getContentPane().setLayout(new BorderLayout(10, 10));
        getContentPane().add(panelCentro, BorderLayout.CENTER);
        getContentPane().add(lblPromedio, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }
}
