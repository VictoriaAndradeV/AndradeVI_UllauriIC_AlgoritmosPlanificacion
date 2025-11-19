package org.example.controlador;

import org.example.modelo.PlanificadorModelo;
import org.example.modelo.Proceso;
import org.example.modelo.ResultadoRR;
import org.example.vista.VentanaGG;
import org.example.vista.VentanaPrincipal;
import org.example.vista.VentanaResultado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controlador implements ActionListener {

    private PlanificadorModelo modelo;
    private VentanaPrincipal vista;

    public Controlador(PlanificadorModelo modelo, VentanaPrincipal vista) {
        this.modelo = modelo;
        this.vista = vista;

        this.vista.getBtnAceptar().addActionListener(this);
        this.vista.getBtnGraficar().addActionListener(this);
        this.vista.getBtnMostrarResultados().addActionListener(this);
        this.vista.getBtnLimpiar().addActionListener(this);
    }

    public void iniciar() {
        vista.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnAceptar()) {
            manejarAceptar();
        } else if (e.getSource() == vista.getBtnGraficar()) {
            manejarGraficar();
        } else if (e.getSource() == vista.getBtnMostrarResultados()) {
            manejarMostrarResultados();
        } else if (e.getSource() == vista.getBtnLimpiar()) {
            manejarLimpiar();
        }
    }

    private void manejarAceptar() {

        // 1) Total de procesos
        if (modelo.getTotalProcesos() == 0) {
            String textoTotal = vista.getTextoTotalProcesos();
            try {
                int total = Integer.parseInt(textoTotal);
                if (total <= 0) {
                    vista.mostrarMensaje("El total de procesos debe ser mayor que cero.");
                    return;
                }
                modelo.setTotalProcesos(total);
            } catch (NumberFormatException ex) {
                vista.mostrarMensaje("Ingrese un número válido en 'Total de procesos'.");
                return;
            }
        }

        // 2) Quantum
        if (modelo.getQuantum() == 0) {
            String textoQuantum = vista.getTextoQuantum();
            try {
                int quantum = Integer.parseInt(textoQuantum);
                if (quantum <= 0) {
                    vista.mostrarMensaje("El cuanto debe ser mayor que cero.");
                    return;
                }
                modelo.setQuantum(quantum);
            } catch (NumberFormatException ex) {
                vista.mostrarMensaje("Ingrese un número válido en 'Valor del cuanto'.");
                return;
            }
        }

        // 3) Verificar si ya se ingresaron todos
        if (modelo.estaCompleto()) {
            vista.mostrarMensaje("Ya se ingresaron todos los procesos.");
            return;
        }

        // 4) Leer datos del proceso
        String nombre = vista.getNombreProceso();
        String textoTiempo = vista.getTextoTiempoEjecucion();
        String textoLlegada = vista.getTextoTiempoLlegada();
        String textoPrioridad = vista.getTextoPrioridad();

        if (nombre == null || nombre.trim().isEmpty()) {
            vista.mostrarMensaje("Ingrese el nombre del proceso.");
            return;
        }

        int tiempo;
        int llegada;
        int prioridad;

        try {
            tiempo = Integer.parseInt(textoTiempo);
            llegada = Integer.parseInt(textoLlegada);
            prioridad = Integer.parseInt(textoPrioridad);
        } catch (NumberFormatException ex) {
            vista.mostrarMensaje("Tiempo de ejecución, tiempo de llegada y prioridad deben ser enteros.");
            return;
        }

        if (tiempo <= 0) {
            vista.mostrarMensaje("El tiempo de ejecución debe ser mayor que cero.");
            return;
        }
        if (llegada < 0) {
            vista.mostrarMensaje("El tiempo de llegada no puede ser negativo.");
            return;
        }

        // 5) Crear y agregar proceso
        Proceso p = new Proceso(nombre.trim(), tiempo, prioridad, llegada);
        modelo.agregarProceso(p);

        // 6) Actualizar contador y limpiar campos del proceso
        vista.actualizarContador(modelo.getCantidadActualProcesos(), modelo.getTotalProcesos());
        vista.limpiarCamposProceso();

        // 7) Si ya están todos, habilitamos los otros botones
        if (modelo.estaCompleto()) {
            vista.mostrarMensaje("Se ingresaron todos los procesos. Ahora puede graficar y ver resultados.");
            vista.getBtnGraficar().setEnabled(true);
            vista.getBtnMostrarResultados().setEnabled(true);
            vista.getBtnLimpiar().setEnabled(true);
        }
    }

    private void manejarGraficar() {
        if (!modelo.estaCompleto()) {
            vista.mostrarMensaje("Primero debe ingresar todos los procesos.");
            return;
        }

        String algoritmo = vista.getAlgoritmoSeleccionado();
        if (!"Round Robin".equals(algoritmo)) {
            vista.mostrarMensaje("Por ahora solo se puede graficar Round Robin.");
            return;
        }

        ResultadoRR resultado = modelo.ejecutarRoundRobin();
        VentanaGG ventanaGantt = new VentanaGG(resultado);
        ventanaGantt.setVisible(true);
    }

    private void manejarMostrarResultados() {
        if (!modelo.estaCompleto()) {
            vista.mostrarMensaje("Primero debe ingresar todos los procesos.");
            return;
        }

        String algoritmo = vista.getAlgoritmoSeleccionado();
        if (!"Round Robin".equals(algoritmo)) {
            vista.mostrarMensaje("Por ahora solo se pueden mostrar resultados de Round Robin.");
            return;
        }

        ResultadoRR resultado = modelo.ejecutarRoundRobin();
        VentanaResultado ventanaResultados = new VentanaResultado(resultado);
        ventanaResultados.setVisible(true);
    }

    private void manejarLimpiar() {
        modelo.limpiar();
        vista.limpiarTodo();
        vista.mostrarMensaje("Se limpiaron todos los datos. Puede ingresar nuevos procesos.");
    }

}
