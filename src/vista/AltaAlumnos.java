/**
 *
 * @author hernanbiondini
 */
package vista;

import Modelo.Alumno;
import Modelo.Persona;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import servicios.Servicios;

/**
 *
 * @author hernanbiondini
 */
public class AltaAlumnos extends javax.swing.JDialog {

    /**
     * Creates new form AltaAlumnos
     */
    public AltaAlumnos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.jLabel9.setVisible(false);
        this.jLabel10.setVisible(false);
    }

    public AltaAlumnos(java.awt.Frame parent, boolean modal, Persona p) {
        super(parent, modal);
        initComponents();
        this.jLabel9.setVisible(false);
        this.jLabel10.setVisible(false);
        this.jTextFieldApellido.setText(p.getApellido());
        this.jTextFieldNombre.setText(p.getNombre());
        this.jTextFieldDireccion.setText(p.getDireccion());
        this.jTextFieldDocumento.setText(String.valueOf(p.getDocumento()));
        this.jFormattedTextFieldFecNac.setText(fromDateToString2(p.getFechanac()));
        String tipoDoc = p.getTipodoc();
        if (tipoDoc.equals("DNI")) {
            this.jComboBox1.setSelectedIndex(0);
        } else {
            this.jComboBox1.setSelectedIndex(1);
        }
        this.jTextFieldLegajo.setText(String.valueOf(p.getAlumno().getLegajo()));
        this.setPersona1(p);
        System.out.println(" persona1.getAlumno().getLegajo()ALTA: " + persona1.getAlumno().getLegajo());
        this.jButtonAceptar.setText("Actualizar");
        this.setTitle("Actualizar Alumno");
        this.setActualiza(true);
        System.out.println("IdPersona: " + p.getIdentificador());
    }

    private boolean actualiza;

    public boolean isActualiza() {
        return actualiza;
    }

    public void setActualiza(boolean actualiza) {
        this.actualiza = actualiza;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        persona1 = new Modelo.Persona();
        alumno1 = new Modelo.Alumno();
        jPanel1 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextFieldDocumento = new javax.swing.JTextField();
        jTextFieldNombre = new javax.swing.JTextField();
        jTextFieldApellido = new javax.swing.JTextField();
        jTextFieldDireccion = new javax.swing.JTextField();
        jTextFieldLegajo = new javax.swing.JTextField();
        jFormattedTextFieldFecNac = new javax.swing.JFormattedTextField();
        jButtonAceptar = new javax.swing.JButton();
        jButtonCancelar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de Alumno"));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DNI", "LIBRETA CIVICA" }));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, persona1, org.jdesktop.beansbinding.ELProperty.create("${documento}"), jTextFieldDocumento, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, persona1, org.jdesktop.beansbinding.ELProperty.create("${nombre}"), jTextFieldNombre, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, persona1, org.jdesktop.beansbinding.ELProperty.create("${apellido}"), jTextFieldApellido, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, persona1, org.jdesktop.beansbinding.ELProperty.create("${direccion}"), jTextFieldDireccion, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, alumno1, org.jdesktop.beansbinding.ELProperty.create("${legajo}"), jTextFieldLegajo, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jFormattedTextFieldFecNac.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.MEDIUM))));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, persona1, org.jdesktop.beansbinding.ELProperty.create("${fechanac}"), jFormattedTextFieldFecNac, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        jButtonAceptar.setText("Aceptar");
        jButtonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAceptarActionPerformed(evt);
            }
        });

        jButtonCancelar.setText("Cancelar");
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarActionPerformed(evt);
            }
        });

        jLabel1.setText("Tipo documento");

        jLabel2.setText("Documento");

        jLabel3.setText("Nombre");

        jLabel4.setText("Apellido");

        jLabel5.setText("Dirección");

        jLabel6.setText("Fecha de nacimiento");

        jLabel7.setText("Legajo");

        jLabel8.setText("Formato dd/mm/aaaa");

        jLabel9.setFont(new java.awt.Font("Ubuntu", 0, 10)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 0, 0));
        jLabel9.setText("Ingrese un número válido !");

        jLabel10.setFont(new java.awt.Font("Ubuntu", 0, 10)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 0, 0));
        jLabel10.setText("Ingrese un número válido !");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(167, 167, 167)
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                .addGap(12, 12, 12))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonAceptar))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jTextFieldLegajo, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jFormattedTextFieldFecNac, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldDireccion, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldApellido, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldNombre, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldDocumento, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFormattedTextFieldFecNac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(1, 1, 1)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldLegajo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(62, 62, 62)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAceptar)
                    .addComponent(jButtonCancelar))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButtonCancelarActionPerformed

    private void jButtonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAceptarActionPerformed
        System.out.println("jButtonAceptarActionPerformed");
        this.jLabel9.setVisible(false);
        this.jLabel10.setVisible(false);
        boolean ex1 = false;
        boolean ex2 = false;
        boolean ex3 = false;
        // validar dni
        try {
            int dni = Integer.parseInt(this.jTextFieldDocumento.getText());
        } catch (Exception e) {
            this.jLabel10.setVisible(true);
            jTextFieldDocumento.requestFocusInWindow();
            ex1 = true;
            JOptionPane.showMessageDialog(this, "Ingrese un número de documento válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
        // validar legajo
        try {
            int legajo = Integer.parseInt(this.jTextFieldLegajo.getText());
        } catch (Exception e) {
            this.jLabel9.setVisible(true);
            jTextFieldLegajo.requestFocusInWindow();
            ex2 = true;
            JOptionPane.showMessageDialog(this, "Ingrese un número de legajo válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
        // validad fecha
        try {
            String fechaStr = this.jFormattedTextFieldFecNac.getText();
            Date fecha = fromStringToDate2(fechaStr);
        } catch (Exception e) {
            //this.jLabel9.setVisible(true);
            jFormattedTextFieldFecNac.requestFocusInWindow();
            ex3 = true;
            JOptionPane.showMessageDialog(this, "Ingrese una fecha válida", "Error", JOptionPane.ERROR_MESSAGE);
        }
        if ((ex1 != true) && (ex2 != true) && (ex3 != true)) {
            Servicios s = new Servicios();
            String tipoDoc = this.jComboBox1.getSelectedItem().toString();
            this.persona1.setTipodoc(tipoDoc);
            try {
                if (this.actualiza) {
                    this.persona1.setDocumento(Integer.parseInt(this.jTextFieldDocumento.getText()));
                    this.persona1.setNombre(this.jTextFieldNombre.getText());
                    this.persona1.setApellido(this.jTextFieldApellido.getText());
                    this.persona1.setDireccion(this.jTextFieldDireccion.getText());
                    this.persona1.getAlumno().setLegajo(Integer.parseInt(this.jTextFieldLegajo.getText()));
                    s.editarPersona(persona1);
                    JOptionPane.showMessageDialog(this, "El registro se actualizo exitosamente", "Actualizar", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    resultado = s.guardarAlunoPersona(persona1, alumno1);
                    JOptionPane.showMessageDialog(this, "El registro se guardo exitosamente", "Guardar", JOptionPane.INFORMATION_MESSAGE);
                }
                limpiar();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al efectuar la operación", "Error:" + e.getMessage(), JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonAceptarActionPerformed

    private void limpiar() {
        this.jTextFieldApellido.setText("");
        this.jTextFieldNombre.setText("");
        this.jTextFieldDireccion.setText("");
        this.jTextFieldDocumento.setText("");
        this.jFormattedTextFieldFecNac.setText("");
        this.jTextFieldLegajo.setText("");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AltaAlumnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AltaAlumnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AltaAlumnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AltaAlumnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AltaAlumnos dialog = new AltaAlumnos(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Modelo.Alumno alumno1;
    private javax.swing.JButton jButtonAceptar;
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JFormattedTextField jFormattedTextFieldFecNac;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextFieldApellido;
    private javax.swing.JTextField jTextFieldDireccion;
    private javax.swing.JTextField jTextFieldDocumento;
    private javax.swing.JTextField jTextFieldLegajo;
    private javax.swing.JTextField jTextFieldNombre;
    private Modelo.Persona persona1;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
    private String resultado = "";

    public Alumno getAlumno1() {
        return alumno1;
    }

    public void setAlumno1(Alumno alumno1) {
        this.alumno1 = alumno1;
    }

    public Persona getPersona1() {
        return persona1;
    }

    public void setPersona1(Persona persona1) {
        this.persona1 = persona1;
    }

    public String fromDateToString2(Date miFecha) {
        String res = "";
        int dia = 0, mes = 0, anio = 0;
        String sep = "/";

        if (miFecha != null) {
            Calendar gc = new GregorianCalendar();
            gc.setTime(miFecha);
            dia = gc.get(Calendar.DAY_OF_MONTH);
            mes = gc.get(Calendar.MONTH);
            mes++;
            anio = gc.get(Calendar.YEAR);
            res = String.format("%02d", dia) + sep + String.format("%02d", mes) + sep + String.format("%04d", anio);
        } else {
            res = "";
        }
        return res;

    }

    public Date fromStringToDate2(String miFecha) throws Exception {
        Date d = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        d = sdf.parse(miFecha);
        return d;
    }

}
