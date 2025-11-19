package org.example;

import org.example.controlador.Controlador;
import org.example.modelo.PlanificadorModelo;
import org.example.vista.VentanaPrincipal;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                PlanificadorModelo modelo = new PlanificadorModelo();
                VentanaPrincipal vista = new VentanaPrincipal();
                Controlador controlador = new Controlador(modelo, vista);

                controlador.iniciar();
            }
        });
    }


}