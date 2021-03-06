/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import classes.Tabuleiro;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author mayla
 */
public class Final extends javax.swing.JFrame {
    private Tabuleiro tabuleiroJogador;
    private Tabuleiro tabuleiroComputador;
    /**
     * Creates new form Final
     */
    public Final(boolean ganhou) {
        initComponents();
        this.setIconImage(new ImageIcon (getClass().getResource("/images/icon_batalhaNaval.png")).getImage());

        if(ganhou)
        {
           lblResultado.setText("Ganhou");
           lblResultado.setForeground(Color.green);
           lblFundoFinal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/image_fundo_final_ganhou.png")));

        }else 
        {
            lblResultado.setText("Perdeu");
            lblResultado.setForeground(Color.red);
            lblFundoFinal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/image_fundo_final_perdeu.png")));

        }
        //receber no construtor o status de 1 ou 0 pra printar a palavra certa
    }
    
    public Tabuleiro getTabuleiroJogador() {
        return tabuleiroJogador;
    }

    public void setTabuleiroJogador(Tabuleiro tabuleiroJogador) {
        
        tabuleiroJogador.setTirosAtingidos(0);
        tabuleiroJogador.getCadaVeiculo(0).setVida(4);
        tabuleiroJogador.getCadaVeiculo(1).setVida(3);
        tabuleiroJogador.getCadaVeiculo(2).setVida(2);
        tabuleiroJogador.getCadaVeiculo(3).setVida(2);
        
        for(int i = 0; i < 10; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                tabuleiroJogador.getBlocoTabuleiroTela(i, j).setBackground(new java.awt.Color(216, 235, 240));
                tabuleiroJogador.getBlocoTabuleiroTela(i, j).setText("");
                tabuleiroJogador.getBlocoTabuleiroTela(i, j).setLevouTiro(false);
            }
        }
        
        this.tabuleiroJogador = tabuleiroJogador;
    }

    public Tabuleiro getTabuleiroComputador() {
        return tabuleiroComputador;
    }

    public void setTabuleiroComputador(Tabuleiro tabuleiroComputador) {
        
        tabuleiroComputador.setTirosAtingidos(0);
        tabuleiroComputador.getCadaVeiculo(0).setVida(4);
        tabuleiroComputador.getCadaVeiculo(1).setVida(3);
        tabuleiroComputador.getCadaVeiculo(2).setVida(2);
        tabuleiroComputador.getCadaVeiculo(3).setVida(2);
        
        for(int i = 0; i < 10; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                tabuleiroComputador.getBlocoTabuleiroTela(i, j).setBackground(new java.awt.Color(240, 216, 216));
                tabuleiroComputador.getBlocoTabuleiroTela(i, j).setText("");
                tabuleiroComputador.getBlocoTabuleiroTela(i, j).setLevouTiro(false);
            }
        }
        
        this.tabuleiroComputador = tabuleiroComputador;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFinal = new javax.swing.JPanel();
        btnNovoJogo = new javax.swing.JButton();
        btnReiniciarJogo = new javax.swing.JButton();
        lblFimdeJogo = new javax.swing.JLabel();
        lblResultado = new javax.swing.JLabel();
        lblFundoFinal = new javax.swing.JLabel();
        panBarraTarefas = new javax.swing.JPanel();
        painelFerramentas = new javax.swing.JPanel();
        btnFechar = new javax.swing.JLabel();
        btnMinimizar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(java.awt.Color.lightGray);
        setMaximumSize(getPreferredSize());
        setMinimumSize(getPreferredSize());
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1200, 700));
        setResizable(false);
        getContentPane().setLayout(null);

        panFinal.setBackground(java.awt.Color.white);
        panFinal.setPreferredSize(new java.awt.Dimension(1200, 700));
        panFinal.setLayout(null);

        btnNovoJogo.setFont(new java.awt.Font("SansSerif", 1, 30)); // NOI18N
        btnNovoJogo.setForeground(new java.awt.Color(1, 13, 58));
        btnNovoJogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon_novo_jogo.png"))); // NOI18N
        btnNovoJogo.setText("      Novo Jogo");
        btnNovoJogo.setFocusable(false);
        btnNovoJogo.setPreferredSize(new java.awt.Dimension(300, 80));
        btnNovoJogo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoJogoActionPerformed(evt);
            }
        });
        panFinal.add(btnNovoJogo);
        btnNovoJogo.setBounds(450, 570, 350, 100);

        btnReiniciarJogo.setFont(new java.awt.Font("SansSerif", 1, 30)); // NOI18N
        btnReiniciarJogo.setForeground(new java.awt.Color(1, 13, 58));
        btnReiniciarJogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon_reiniciar_jogo.png"))); // NOI18N
        btnReiniciarJogo.setText("Reiniciar Jogo");
        btnReiniciarJogo.setFocusable(false);
        btnReiniciarJogo.setPreferredSize(new java.awt.Dimension(300, 80));
        btnReiniciarJogo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReiniciarJogoActionPerformed(evt);
            }
        });
        panFinal.add(btnReiniciarJogo);
        btnReiniciarJogo.setBounds(450, 440, 350, 100);

        lblFimdeJogo.setFont(new java.awt.Font("SansSerif", 1, 150)); // NOI18N
        lblFimdeJogo.setForeground(java.awt.Color.white);
        lblFimdeJogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFimdeJogo.setText("Fim de Jogo");
        lblFimdeJogo.setPreferredSize(new java.awt.Dimension(30, 30));
        panFinal.add(lblFimdeJogo);
        lblFimdeJogo.setBounds(0, 30, 1200, 180);

        lblResultado.setFont(new java.awt.Font("SansSerif", 1, 150)); // NOI18N
        lblResultado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblResultado.setText("Perdeu");
        lblResultado.setPreferredSize(new java.awt.Dimension(30, 30));
        panFinal.add(lblResultado);
        lblResultado.setBounds(0, 220, 1200, 170);

        lblFundoFinal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/image_fundo_final_perdeu.png"))); // NOI18N
        lblFundoFinal.setPreferredSize(new java.awt.Dimension(1200, 700));
        panFinal.add(lblFundoFinal);
        lblFundoFinal.setBounds(0, 30, 1200, 700);

        panBarraTarefas.setBackground(new java.awt.Color(0, 0, 0));
        panBarraTarefas.setPreferredSize(new java.awt.Dimension(1200, 30));
        panBarraTarefas.setLayout(null);

        painelFerramentas.setBackground(new java.awt.Color(0, 0, 0));
        painelFerramentas.setPreferredSize(new java.awt.Dimension(97, 18));
        painelFerramentas.setLayout(null);

        btnFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon_close_not_high.png"))); // NOI18N
        btnFechar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFechar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnFecharMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnFecharMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnFecharMouseExited(evt);
            }
        });
        painelFerramentas.add(btnFechar);
        btnFechar.setBounds(52, 0, 45, 18);

        btnMinimizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon_min_not_high.png"))); // NOI18N
        btnMinimizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMinimizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMinimizarMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnMinimizarMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnMinimizarMouseEntered(evt);
            }
        });
        painelFerramentas.add(btnMinimizar);
        btnMinimizar.setBounds(21, 0, 25, 18);

        panBarraTarefas.add(painelFerramentas);
        painelFerramentas.setBounds(1104, 0, 97, 18);

        panFinal.add(panBarraTarefas);
        panBarraTarefas.setBounds(0, 0, 1200, 30);

        getContentPane().add(panFinal);
        panFinal.setBounds(0, 0, 1200, 700);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnReiniciarJogoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReiniciarJogoActionPerformed
        // TODO add your handling code here:
        Jogo reiniciar = new Jogo(tabuleiroJogador, tabuleiroComputador);
        reiniciar.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnReiniciarJogoActionPerformed

    private void btnFecharMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFecharMouseClicked
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog (null, "Gostaria mesmo de encerrar?","Fechar", dialogButton);
        if(dialogResult == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }//GEN-LAST:event_btnFecharMouseClicked

    private void btnFecharMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFecharMouseEntered
        btnFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon_close_high.png")));
    }//GEN-LAST:event_btnFecharMouseEntered

    private void btnFecharMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFecharMouseExited
        btnFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon_close_not_high.png")));
    }//GEN-LAST:event_btnFecharMouseExited

    private void btnMinimizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimizarMouseClicked
        setExtendedState(ICONIFIED);
    }//GEN-LAST:event_btnMinimizarMouseClicked

    private void btnMinimizarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimizarMouseExited
        btnMinimizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon_min_high.png")));
    }//GEN-LAST:event_btnMinimizarMouseExited

    private void btnMinimizarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMinimizarMouseEntered
        btnMinimizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon_min_not_high.png")));
    }//GEN-LAST:event_btnMinimizarMouseEntered

    private void btnNovoJogoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoJogoActionPerformed
        // TODO add your handling code here:
        Menu menu = new Menu();
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnNovoJogoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnFechar;
    private javax.swing.JLabel btnMinimizar;
    private javax.swing.JButton btnNovoJogo;
    private javax.swing.JButton btnReiniciarJogo;
    private javax.swing.JLabel lblFimdeJogo;
    private javax.swing.JLabel lblFundoFinal;
    private javax.swing.JLabel lblResultado;
    private javax.swing.JPanel painelFerramentas;
    private javax.swing.JPanel panBarraTarefas;
    private javax.swing.JPanel panFinal;
    // End of variables declaration//GEN-END:variables
}
