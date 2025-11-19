package org.example.vista;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private JComboBox<String> comboAlgoritmo;
    private JTextField txtTotalProcesos;
    private JTextField txtNombre;
    private JTextField txtTiempo;
    private JTextField txtLlegada;
    private JTextField txtPrioridad;
    private JTextField txtQuantum;

    private JButton btnAceptar;
    private JButton btnGraficar;
    private JButton btnMostrarResultados;
    private JButton btnLimpiar;

    private JLabel lblContador;

    public VentanaPrincipal() {
        setTitle("Algoritmos de Planificación");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(560, 380);
        setLocationRelativeTo(null);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitulo = new JLabel("Algoritmos de Planificación", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new GridLayout(0, 2, 5, 5));

        panelCentro.add(new JLabel("Algoritmo:"));
        comboAlgoritmo = new JComboBox<String>(
                new String[]{"Round Robin", "Prioridad (no expropiativo)"}
        );
        panelCentro.add(comboAlgoritmo);

        panelCentro.add(new JLabel("Total de procesos:"));
        txtTotalProcesos = new JTextField();
        panelCentro.add(txtTotalProcesos);

        panelCentro.add(new JLabel("Nombre del proceso:"));
        txtNombre = new JTextField();
        panelCentro.add(txtNombre);

        panelCentro.add(new JLabel("Tiempo de ejecución:"));
        txtTiempo = new JTextField();
        panelCentro.add(txtTiempo);

        // NUEVO CAMPO: TIEMPO DE LLEGADA
        panelCentro.add(new JLabel("Tiempo de llegada:"));
        txtLlegada = new JTextField();
        panelCentro.add(txtLlegada);

        panelCentro.add(new JLabel("Prioridad (Linux):"));
        txtPrioridad = new JTextField();
        panelCentro.add(txtPrioridad);

        panelCentro.add(new JLabel("Valor del cuanto:"));
        txtQuantum = new JTextField();
        panelCentro.add(txtQuantum);

        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        JPanel panelSur = new JPanel();
        panelSur.setLayout(new BorderLayout());

        JPanel panelBotones = new JPanel();

        btnAceptar = new JButton("Aceptar");
        btnGraficar = new JButton("Graficar");
        btnMostrarResultados = new JButton("Mostrar resultados");
        btnLimpiar = new JButton("Limpiar");

        panelBotones.add(btnAceptar);
        panelBotones.add(btnGraficar);
        panelBotones.add(btnMostrarResultados);
        panelBotones.add(btnLimpiar);

        btnGraficar.setEnabled(false);
        btnMostrarResultados.setEnabled(false);
        btnLimpiar.setEnabled(false);

        panelSur.add(panelBotones, BorderLayout.NORTH);

        lblContador = new JLabel("Procesos ingresados: 0 / 0");
        panelSur.add(lblContador, BorderLayout.SOUTH);

        panelPrincipal.add(panelSur, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);
    }

    // ==== GETTERS para el controlador ====

    public String getAlgoritmoSeleccionado() {
        return (String) comboAlgoritmo.getSelectedItem();
    }

    public String getTextoTotalProcesos() {
        return txtTotalProcesos.getText();
    }

    public String getNombreProceso() {
        return txtNombre.getText();
    }

    public String getTextoTiempoEjecucion() {
        return txtTiempo.getText();
    }

    public String getTextoTiempoLlegada() {   // NUEVO
        return txtLlegada.getText();
    }

    public String getTextoPrioridad() {
        return txtPrioridad.getText();
    }

    public String getTextoQuantum() {
        return txtQuantum.getText();
    }

    public void limpiarCamposProceso() {
        txtNombre.setText("");
        txtTiempo.setText("");
        txtLlegada.setText("");
        txtPrioridad.setText("");
        txtNombre.requestFocus();
    }

    public void actualizarContador(int actual, int total) {
        lblContador.setText("Procesos ingresados: " + actual + " / " + total);
    }

    public JButton getBtnAceptar() {
        return btnAceptar;
    }

    public JButton getBtnGraficar() {
        return btnGraficar;
    }

    public JButton getBtnMostrarResultados() {
        return btnMostrarResultados;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void limpiarTodo() {
        txtTotalProcesos.setText("");
        txtNombre.setText("");
        txtTiempo.setText("");
        txtLlegada.setText("");
        txtPrioridad.setText("");
        txtQuantum.setText("");

        comboAlgoritmo.setSelectedIndex(0);
        actualizarContador(0, 0);

        btnGraficar.setEnabled(false);
        btnMostrarResultados.setEnabled(false);
        btnLimpiar.setEnabled(false);
    }

}
