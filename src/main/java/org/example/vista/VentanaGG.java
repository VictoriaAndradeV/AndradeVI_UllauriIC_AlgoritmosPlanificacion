package org.example.vista;

import org.example.modelo.ResultadoRR;

import javax.swing.*;
import java.awt.*;

public class VentanaGG extends JFrame {
    public VentanaGG(ResultadoRR resultado) {
        setTitle("Diagrama de Gantt - Round Robin");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        PanelGrafica panelGantt = new PanelGrafica(resultado);
        JScrollPane scroll = new JScrollPane(panelGantt);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scroll, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }
}
