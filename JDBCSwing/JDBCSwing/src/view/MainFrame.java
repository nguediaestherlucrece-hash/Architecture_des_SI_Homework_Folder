package view;

import dao.UtilisateurDAO;
import dao.UtilisateurDAOImpl;
import models.Utilisateur;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

/**
 * Fenêtre principale (JFrame) générée avec le GUI Builder de NetBeans.
 * Le fichier associé MainFrame.form décrit la mise en page (GroupLayout)
 * reconnue par l'éditeur graphique de NetBeans.
 */
public class MainFrame extends javax.swing.JFrame {

    private final UtilisateurDAO utilisateurDAO = new UtilisateurDAOImpl();
    private DefaultTableModel tableModel;

    /**
     * Constructeur : initialise les composants puis charge les données.
     */
    public MainFrame() {
        initComponents();
        configurerTable();
        chargerUtilisateurs();
    }

    private void configurerTable() {
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nom", "Email"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable1.setModel(tableModel);

        jTable1.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && jTable1.getSelectedRow() != -1) {
                int row = jTable1.getSelectedRow();
                jTextFieldId.setText(tableModel.getValueAt(row, 0).toString());
                jTextFieldNom.setText(tableModel.getValueAt(row, 1).toString());
                jTextFieldEmail.setText(tableModel.getValueAt(row, 2).toString());
            }
        });
    }

    private void chargerUtilisateurs() {
        tableModel.setRowCount(0);
        List<Utilisateur> liste = utilisateurDAO.findAll();
        for (Utilisateur u : liste) {
            tableModel.addRow(new Object[]{u.getId(), u.getNom(), u.getEmail()});
        }
    }

    private void viderChamps() {
        jTextFieldId.setText("");
        jTextFieldNom.setText("");
        jTextFieldEmail.setText("");
        jTable1.clearSelection();
    }

    // ------------------------------------------------------------------
    // Actions des boutons
    // ------------------------------------------------------------------

    private void jButtonRefreshActionPerformed(java.awt.event.ActionEvent evt) {
        chargerUtilisateurs();
        viderChamps();
    }

    private void jButtonCreateActionPerformed(java.awt.event.ActionEvent evt) {
        String nom = jTextFieldNom.getText().trim();
        String email = jTextFieldEmail.getText().trim();
        if (nom.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nom et email sont obligatoires.",
                    "Champs manquants", JOptionPane.WARNING_MESSAGE);
            return;
        }
        utilisateurDAO.create(new Utilisateur(nom, email));
        chargerUtilisateurs();
        viderChamps();
        JOptionPane.showMessageDialog(this, "Utilisateur créé avec succès !");
    }

    private void jButtonUpdateActionPerformed(java.awt.event.ActionEvent evt) {
        String idText = jTextFieldId.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Sélectionnez d'abord un utilisateur dans la table.",
                    "Aucune sélection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Utilisateur u = new Utilisateur(Integer.parseInt(idText),
                jTextFieldNom.getText().trim(), jTextFieldEmail.getText().trim());
        utilisateurDAO.update(u);
        chargerUtilisateurs();
        viderChamps();
        JOptionPane.showMessageDialog(this, "Mise à jour effectuée.");
    }

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        String idText = jTextFieldId.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Sélectionnez d'abord un utilisateur dans la table.",
                    "Aucune sélection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "Supprimer l'utilisateur sélectionné ?", "Confirmation",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            utilisateurDAO.delete(Integer.parseInt(idText));
            chargerUtilisateurs();
            viderChamps();
        }
    }

    private void jButtonFindByIdActionPerformed(java.awt.event.ActionEvent evt) {
        String idText = JOptionPane.showInputDialog(this, "ID à rechercher :");
        if (idText == null || idText.trim().isEmpty()) {
            return;
        }
        try {
            Utilisateur u = utilisateurDAO.findById(Integer.parseInt(idText.trim()));
            if (u != null) {
                jTextFieldId.setText(String.valueOf(u.getId()));
                jTextFieldNom.setText(u.getNom());
                jTextFieldEmail.setText(u.getEmail());
            } else {
                JOptionPane.showMessageDialog(this, "Aucun utilisateur avec cet ID.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur SQL : " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ------------------------------------------------------------------
    // Code généré par le GUI Builder (synchronisé avec MainFrame.form)
    // ------------------------------------------------------------------
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabelId = new javax.swing.JLabel();
        jLabelNom = new javax.swing.JLabel();
        jLabelEmail = new javax.swing.JLabel();
        jTextFieldId = new javax.swing.JTextField();
        jTextFieldNom = new javax.swing.JTextField();
        jTextFieldEmail = new javax.swing.JTextField();
        jButtonRefresh = new javax.swing.JButton();
        jButtonCreate = new javax.swing.JButton();
        jButtonUpdate = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jButtonFindById = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestion des Utilisateurs - JDBC Swing");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"ID", "Nom", "Email"}
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabelId.setText("ID :");
        jLabelNom.setText("Nom :");
        jLabelEmail.setText("Email :");

        jTextFieldId.setEditable(false);

        jButtonRefresh.setText("Actualiser");
        jButtonRefresh.addActionListener(this::jButtonRefreshActionPerformed);

        jButtonCreate.setText("Créer");
        jButtonCreate.addActionListener(this::jButtonCreateActionPerformed);

        jButtonUpdate.setText("Modifier");
        jButtonUpdate.addActionListener(this::jButtonUpdateActionPerformed);

        jButtonDelete.setText("Supprimer");
        jButtonDelete.addActionListener(this::jButtonDeleteActionPerformed);

        jButtonFindById.setText("Rechercher par ID");
        jButtonFindById.addActionListener(this::jButtonFindByIdActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelId)
                            .addComponent(jLabelNom)
                            .addComponent(jLabelEmail))
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldId)
                            .addComponent(jTextFieldNom)
                            .addComponent(jTextFieldEmail)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonRefresh)
                        .addGap(6, 6, 6)
                        .addComponent(jButtonFindById)
                        .addGap(6, 6, 6)
                        .addComponent(jButtonCreate)
                        .addGap(6, 6, 6)
                        .addComponent(jButtonUpdate)
                        .addGap(6, 6, 6)
                        .addComponent(jButtonDelete)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelId)
                    .addComponent(jTextFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNom)
                    .addComponent(jTextFieldNom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelEmail)
                    .addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonRefresh)
                    .addComponent(jButtonFindById)
                    .addComponent(jButtonCreate)
                    .addComponent(jButtonUpdate)
                    .addComponent(jButtonDelete))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>

    // Variables declaration - do not modify
    private javax.swing.JButton jButtonCreate;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonFindById;
    private javax.swing.JButton jButtonRefresh;
    private javax.swing.JButton jButtonUpdate;
    private javax.swing.JLabel jLabelEmail;
    private javax.swing.JLabel jLabelId;
    private javax.swing.JLabel jLabelNom;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldEmail;
    private javax.swing.JTextField jTextFieldId;
    private javax.swing.JTextField jTextFieldNom;
    // End of variables declaration
}
