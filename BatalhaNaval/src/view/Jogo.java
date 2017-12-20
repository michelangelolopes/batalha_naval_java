/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import classes.Tabuleiro;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author mayla
 */
public class Jogo extends javax.swing.JFrame {
    private Tabuleiro tabuleiroJogador;
    private Tabuleiro tabuleiroComputador;
    private int dica = 0;
    private int tipoTiro = 0;
    private boolean ganhou;
    private boolean fimJogo = false;
    private boolean vezJogador = true;
    private boolean acertou;
    private int[] posTiroComp = new int[2];

    /**
     * Creates new form Jogo
     */
    public Jogo(){}
    public Jogo(Tabuleiro tabuleiroJogador, Tabuleiro tabuleiroComputador) 
    {
        initComponents();
        this.tabuleiroJogador = tabuleiroJogador;
        this.tabuleiroComputador = tabuleiroComputador;
        this.setIconImage(new ImageIcon (getClass().getResource("/images/icon_batalhaNaval.png")).getImage());
        
        for(int l = 0; l <= 9; l++)
        {
            for(int c = 0; c <= 9; c++)
            {
                final int linha = l;
                final int coluna = c;
                panTabuleiroJogador.add(tabuleiroJogador.getBlocoTabuleiroTela(l, c));
                panTabuleiroComputador.add(tabuleiroComputador.getBlocoTabuleiroTela(l, c));
                criarMouseClickedComputador(linha,coluna);
            }
        }
        
        this.tabuleiroJogador.atualizarToolTip(this.tabuleiroJogador.getTabuleiro(),this.tabuleiroJogador.getTabuleiroTela());
        lblAcertosJogador.setText(tabuleiroJogador.getTirosAtingidos() + " Acerto(s)"); 
        lblAcertosComputador.setText(tabuleiroComputador.getTirosAtingidos()+ " Acerto(s)"); 
        lblVezJogador.setForeground(Color.red);
        lblRespostaDica.setVisible(false);
        lblInformacoes.setVisible(false);
        lblInformacoes2.setVisible(false);
        lblInformacoes3.setVisible(false);
        lblInformacoes4.setVisible(false);
        lblInformacoes5.setVisible(false);
        barraVidaJogador.setMinimum(0);
        barraVidaJogador.setMaximum(11);// 2 + 2 + 3 + 4 =11
        barraVidaJogador.setValue(11);
        barraVidaComputador.setMinimum(0);
        barraVidaComputador.setMaximum(11);// 2 + 2 + 3 + 4 =11
        barraVidaComputador.setValue(11);
       
    }
   
    private void criarMouseClickedComputador(int linha,int coluna)
    {
        //cria um evento de MouseClicked para um BlocoLabel Computador
        tabuleiroComputador.getBlocoTabuleiroTela(linha, coluna).addMouseListener(new MouseAdapter()
        {   
            //sobreescreve o metodo mouseClicked da classe JLabel
            @Override
            public void mouseClicked(MouseEvent e)   
            {  
                boolean cliqueLugarJaAtirado = false;
                acertou = false;
                
                if(vezJogador)
                {
                    if(tipoTiro != 0)
                    {
                        if(!tabuleiroComputador.getBlocoTabuleiroTela(linha, coluna).isLevouTiro())
                        {
                            lblInformacoes.setVisible(false);
                            lblInformacoes1.setVisible(false);
                            atirar(linha,coluna);
                            vezJogador = false;
                            tipoTiro = 0;
                        }
                        else
                        {
                            lblInformacoes.setVisible(true);
                            cliqueLugarJaAtirado = true;
                        }
                        
                        if(!cliqueLugarJaAtirado)
                        {
                            Random random = new Random();

                            do
                            {
                               posTiroComp[0] = random.nextInt(10);
                               posTiroComp[1] = random.nextInt(10); 
                            }
                            while(tabuleiroJogador.getBlocoTabuleiroTela(posTiroComp[0], posTiroComp[1]).isLevouTiro());

                            int[] vidaVeiculo = new int[3];
                            int[] tiroVeiculoVivo = new int[3];
                            tiroVeiculoVivo[0] = 2;
                            tiroVeiculoVivo[1] = 1;
                            tiroVeiculoVivo[2] = 3;
                            for(int i = 0; i < 3; i++)
                            {
                                vidaVeiculo[i] = tabuleiroComputador.getCadaVeiculo(i + 1).getVida(); 

                                if(vidaVeiculo[i] == 0)
                                {
                                    tiroVeiculoVivo[i] = 0;
                                }
                            }
                            if(!(vidaVeiculo[0] + vidaVeiculo[1] + vidaVeiculo[2] == 0))
                            {
                                while(tipoTiro != tiroVeiculoVivo[0] && tipoTiro != tiroVeiculoVivo[1] && tipoTiro != tiroVeiculoVivo[2])
                                {
                                    tipoTiro = 1 + random.nextInt(3);
                                }
                            }
                            atirar(posTiroComp[0], posTiroComp[1]);
                            vezJogador = true;
                            tipoTiro = 0;
                        }
                    }
                }
                
                if(testarFinalVeiculos(tabuleiroComputador))
                {   
                    btnSair.setText("Terminar Jogo\n");
                    boxLetra.setEnabled(false);
                    boxNumero.setEnabled(false);
                    btnTiroCascata.setEnabled(false);
                    btnTiroComum.setEnabled(false);
                    btnTiroEstrela.setEnabled(false);
                    btnDica.setEnabled(false);
                    ganhou = true;
                    fimJogo = true;
                    lblInformacoes3.setVisible(true);
                    
                    if(testarFinalAcertos(tabuleiroComputador))
                    {
                        lblInformacoes3.setVisible(false);
                        lblInformacoes2.setVisible(true);
                    }
                }
                
                if(testarFinalVeiculos(tabuleiroJogador))
                {                    
                    btnSair.setText("Terminar Jogo\n");
                    boxLetra.setEnabled(false);
                    boxNumero.setEnabled(false);
                    btnTiroCascata.setEnabled(false);
                    btnTiroComum.setEnabled(false);
                    btnTiroEstrela.setEnabled(false);
                    btnDica.setEnabled(false);
                    ganhou = false;
                    fimJogo = true;
                    lblInformacoes5.setVisible(true);
                    
                    if(testarFinalAcertos(tabuleiroJogador))
                    {
                        lblInformacoes5.setVisible(false);
                        lblInformacoes4.setVisible(true);
                    }
                }
            }
        });
     }
    
    private void atirar(int line, int column)
    {
        setLabelTiro(line, column);
        
        switch(tipoTiro)
        {
            case 2:
                if(column < 9) setLabelTiro(line, column + 1);
                break;
            case 3:
                if(column < 9) setLabelTiro(line, column + 1);
                if(column > 0) setLabelTiro(line, column - 1);
                if(line < 9) setLabelTiro(line + 1, column);
                if(line > 0) setLabelTiro(line - 1, column);
                break;
        }
    }
        
    private void setLabelTiro(int line, int column)
    {
        Tabuleiro receptor;
        int vida;
        
        if(vezJogador)
        {
            receptor = tabuleiroComputador;
        }
        else
        {     
            receptor = tabuleiroJogador;
        }
        
        if(!(receptor.getBlocoTabuleiroTela(line, column).isLevouTiro()))
        {
            switch(receptor.getBlocoTabuleiro(line, column))
            {
                case 0:
                    receptor.getBlocoTabuleiroTela(line, column).setBackground(new java.awt.Color(138,203,219));
                    break;
                case 1:
                    receptor.getCadaVeiculo(0).setVida(receptor.getCadaVeiculo(0).getVida()-1);
                    break;
                case 2:
                    receptor.getCadaVeiculo(1).setVida(receptor.getCadaVeiculo(1).getVida()-1);
                    break;
                case 3:
                    receptor.getCadaVeiculo(2).setVida(receptor.getCadaVeiculo(2).getVida()-1);
                    break;
                case 4:
                    receptor.getCadaVeiculo(3).setVida(receptor.getCadaVeiculo(3).getVida()-1); 
                    break;
            }
        
            if(receptor.getBlocoTabuleiro(line, column) != 0)
            {
                receptor.getBlocoTabuleiroTela(line, column).setText("X");
                receptor.setTirosAtingidos(receptor.getTirosAtingidos() + 1);
                acertou = true;
            } 
        }
        
        vida = receptor.getCadaVeiculo(0).getVida() 
        + receptor.getCadaVeiculo(1).getVida() 
        + receptor.getCadaVeiculo(2).getVida() 
        + receptor.getCadaVeiculo(3).getVida();
        
        if(vezJogador)
        {
            tabuleiroComputador = receptor;
            
            tabuleiroComputador.getBlocoTabuleiroTela(line, column).setLevouTiro(true);
            barraVidaComputador.setValue(vida);
            btnTiroComum.setEnabled(false);
            btnTiroCascata.setEnabled(false);
            btnTiroEstrela.setEnabled(false);
            btnDica.setEnabled(false);
            boxLetra.setEnabled(false);
            boxNumero.setEnabled(false);
            lblVezComputador.setForeground(Color.red);
            lblVezJogador.setForeground(Color.white);           
        }
        else
        {   
            tabuleiroJogador = receptor;
            
            tabuleiroJogador.getBlocoTabuleiroTela(line, column).setLevouTiro(true);
            barraVidaJogador.setValue(vida);
            btnTiroComum.setEnabled(true);
            btnTiroCascata.setEnabled(true);
            btnTiroEstrela.setEnabled(true);
            btnDica.setEnabled(true);
            boxLetra.setEnabled(true);
            boxNumero.setEnabled(true);
            lblVezComputador.setForeground(Color.white);
            lblVezJogador.setForeground(Color.red);
        }
        
        lblAcertosJogador.setText(tabuleiroJogador.getTirosAtingidos() + " Acerto(s)");
        lblAcertosComputador.setText(tabuleiroComputador.getTirosAtingidos() + " Acerto(s)");
        
        if(tabuleiroJogador.getCadaVeiculo(1).getVida() == 0)
            btnTiroCascata.setEnabled(false);
        if(tabuleiroJogador.getCadaVeiculo(2).getVida() == 0)
            btnTiroComum.setEnabled(false);
        if(tabuleiroJogador.getCadaVeiculo(3).getVida() == 0)
            btnTiroEstrela.setEnabled(false);
        
        if(dica >= 3)
        {
            boxLetra.setEnabled(false);
            boxNumero.setEnabled(false);
            btnDica.setEnabled(false);
        }
    }
    
    private boolean testarFinalAcertos(Tabuleiro ataque)
    {        
        return (ataque.getTirosAtingidos() == 11);
    }
    
    private boolean testarFinalVeiculos(Tabuleiro defesa)
    {
        return (defesa.getCadaVeiculo(1).getVida() + defesa.getCadaVeiculo(2).getVida() + defesa.getCadaVeiculo(3).getVida() == 0);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panJogo = new javax.swing.JPanel();
        panJogador = new javax.swing.JPanel();
        lblA = new javax.swing.JLabel();
        lblB = new javax.swing.JLabel();
        lblC = new javax.swing.JLabel();
        lblD = new javax.swing.JLabel();
        lblE = new javax.swing.JLabel();
        lblF = new javax.swing.JLabel();
        lblG = new javax.swing.JLabel();
        lblH = new javax.swing.JLabel();
        lblI = new javax.swing.JLabel();
        lblJ = new javax.swing.JLabel();
        lbl1 = new javax.swing.JLabel();
        lbl2 = new javax.swing.JLabel();
        lbl3 = new javax.swing.JLabel();
        lbl4 = new javax.swing.JLabel();
        lbl5 = new javax.swing.JLabel();
        lbl6 = new javax.swing.JLabel();
        lbl7 = new javax.swing.JLabel();
        lbl8 = new javax.swing.JLabel();
        lbl9 = new javax.swing.JLabel();
        lbl10 = new javax.swing.JLabel();
        lblAcertosJogador = new javax.swing.JLabel();
        barraVidaJogador = new javax.swing.JProgressBar();
        lblVezJogador = new javax.swing.JLabel();
        panTabuleiroJogador = new javax.swing.JPanel();
        panComputador = new javax.swing.JPanel();
        lblA1 = new javax.swing.JLabel();
        lblB1 = new javax.swing.JLabel();
        lblC1 = new javax.swing.JLabel();
        lblD1 = new javax.swing.JLabel();
        lblE1 = new javax.swing.JLabel();
        lblF1 = new javax.swing.JLabel();
        lblG1 = new javax.swing.JLabel();
        lblH1 = new javax.swing.JLabel();
        lblI1 = new javax.swing.JLabel();
        lblJ1 = new javax.swing.JLabel();
        lbl11 = new javax.swing.JLabel();
        lbl12 = new javax.swing.JLabel();
        lbl13 = new javax.swing.JLabel();
        lbl14 = new javax.swing.JLabel();
        lbl15 = new javax.swing.JLabel();
        lbl16 = new javax.swing.JLabel();
        lbl17 = new javax.swing.JLabel();
        lbl18 = new javax.swing.JLabel();
        lbl19 = new javax.swing.JLabel();
        lbl20 = new javax.swing.JLabel();
        lblAcertosComputador = new javax.swing.JLabel();
        barraVidaComputador = new javax.swing.JProgressBar();
        lblVezComputador = new javax.swing.JLabel();
        panTabuleiroComputador = new javax.swing.JPanel();
        btnMenu = new javax.swing.JButton();
        panBarraTarefas = new javax.swing.JPanel();
        painelFerramentas = new javax.swing.JPanel();
        btnFechar = new javax.swing.JLabel();
        btnMinimizar = new javax.swing.JLabel();
        panMenuJogar = new javax.swing.JPanel();
        lblRespostaDica = new javax.swing.JLabel();
        btnTiroComum = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        btnDica = new javax.swing.JButton();
        btnTiroEstrela = new javax.swing.JButton();
        btnTiroCascata = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        boxNumero = new javax.swing.JComboBox<>();
        boxLetra = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnSair = new javax.swing.JButton();
        lblInformacoes = new javax.swing.JLabel();
        lblInformacoes1 = new javax.swing.JLabel();
        lblInformacoes2 = new javax.swing.JLabel();
        lblInformacoes3 = new javax.swing.JLabel();
        lblInformacoes4 = new javax.swing.JLabel();
        lblInformacoes5 = new javax.swing.JLabel();
        lblFundoFInal = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(getPreferredSize());
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1200, 700));
        setResizable(false);
        getContentPane().setLayout(null);

        panJogo.setBackground(java.awt.Color.white);
        panJogo.setPreferredSize(new java.awt.Dimension(1200, 700));
        panJogo.setLayout(null);

        panJogador.setBackground(java.awt.Color.white);
        panJogador.setMaximumSize(getPreferredSize());
        panJogador.setMinimumSize(getPreferredSize());
        panJogador.setOpaque(false);
        panJogador.setPreferredSize(new java.awt.Dimension(470, 490));
        panJogador.setLayout(null);

        lblA.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblA.setForeground(java.awt.Color.white);
        lblA.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblA.setText("A");
        lblA.setMaximumSize(getPreferredSize());
        lblA.setMinimumSize(getPreferredSize());
        lblA.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lblA);
        lblA.setBounds(30, 70, 30, 30);

        lblB.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblB.setForeground(java.awt.Color.white);
        lblB.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblB.setText("B");
        lblB.setMaximumSize(getPreferredSize());
        lblB.setMinimumSize(getPreferredSize());
        lblB.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lblB);
        lblB.setBounds(30, 110, 30, 30);

        lblC.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblC.setForeground(java.awt.Color.white);
        lblC.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblC.setText("C");
        lblC.setMaximumSize(getPreferredSize());
        lblC.setMinimumSize(getPreferredSize());
        lblC.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lblC);
        lblC.setBounds(30, 150, 30, 30);

        lblD.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblD.setForeground(java.awt.Color.white);
        lblD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblD.setText("D");
        lblD.setMaximumSize(getPreferredSize());
        lblD.setMinimumSize(getPreferredSize());
        lblD.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lblD);
        lblD.setBounds(30, 190, 30, 30);

        lblE.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblE.setForeground(java.awt.Color.white);
        lblE.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblE.setText("E");
        lblE.setMaximumSize(getPreferredSize());
        lblE.setMinimumSize(getPreferredSize());
        lblE.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lblE);
        lblE.setBounds(30, 230, 30, 30);

        lblF.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblF.setForeground(java.awt.Color.white);
        lblF.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblF.setText("F");
        lblF.setMaximumSize(getPreferredSize());
        lblF.setMinimumSize(getPreferredSize());
        lblF.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lblF);
        lblF.setBounds(30, 270, 30, 30);

        lblG.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblG.setForeground(java.awt.Color.white);
        lblG.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblG.setText("G");
        lblG.setMaximumSize(getPreferredSize());
        lblG.setMinimumSize(getPreferredSize());
        lblG.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lblG);
        lblG.setBounds(30, 310, 30, 30);

        lblH.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblH.setForeground(java.awt.Color.white);
        lblH.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblH.setText("H");
        lblH.setMaximumSize(getPreferredSize());
        lblH.setMinimumSize(getPreferredSize());
        lblH.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lblH);
        lblH.setBounds(30, 350, 30, 30);

        lblI.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblI.setForeground(java.awt.Color.white);
        lblI.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblI.setText("I");
        lblI.setMaximumSize(getPreferredSize());
        lblI.setMinimumSize(getPreferredSize());
        lblI.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lblI);
        lblI.setBounds(30, 390, 30, 30);

        lblJ.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblJ.setForeground(java.awt.Color.white);
        lblJ.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblJ.setText("J");
        lblJ.setMaximumSize(getPreferredSize());
        lblJ.setMinimumSize(getPreferredSize());
        lblJ.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lblJ);
        lblJ.setBounds(30, 430, 30, 30);

        lbl1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lbl1.setForeground(java.awt.Color.white);
        lbl1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl1.setText("1");
        lbl1.setMaximumSize(getPreferredSize());
        lbl1.setMinimumSize(getPreferredSize());
        lbl1.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lbl1);
        lbl1.setBounds(75, 30, 30, 30);

        lbl2.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lbl2.setForeground(java.awt.Color.white);
        lbl2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl2.setText("2");
        lbl2.setMaximumSize(getPreferredSize());
        lbl2.setMinimumSize(getPreferredSize());
        lbl2.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lbl2);
        lbl2.setBounds(115, 30, 30, 30);

        lbl3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lbl3.setForeground(java.awt.Color.white);
        lbl3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl3.setText("3");
        lbl3.setMaximumSize(getPreferredSize());
        lbl3.setMinimumSize(getPreferredSize());
        lbl3.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lbl3);
        lbl3.setBounds(155, 30, 30, 30);

        lbl4.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lbl4.setForeground(java.awt.Color.white);
        lbl4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl4.setText("4");
        lbl4.setMaximumSize(getPreferredSize());
        lbl4.setMinimumSize(getPreferredSize());
        lbl4.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lbl4);
        lbl4.setBounds(195, 30, 30, 30);

        lbl5.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lbl5.setForeground(java.awt.Color.white);
        lbl5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl5.setText("5");
        lbl5.setMaximumSize(getPreferredSize());
        lbl5.setMinimumSize(getPreferredSize());
        lbl5.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lbl5);
        lbl5.setBounds(235, 30, 30, 30);

        lbl6.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lbl6.setForeground(java.awt.Color.white);
        lbl6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl6.setText("6");
        lbl6.setMaximumSize(getPreferredSize());
        lbl6.setMinimumSize(getPreferredSize());
        lbl6.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lbl6);
        lbl6.setBounds(275, 30, 30, 30);

        lbl7.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lbl7.setForeground(java.awt.Color.white);
        lbl7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl7.setText("7");
        lbl7.setMaximumSize(getPreferredSize());
        lbl7.setMinimumSize(getPreferredSize());
        lbl7.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lbl7);
        lbl7.setBounds(315, 30, 30, 30);

        lbl8.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lbl8.setForeground(java.awt.Color.white);
        lbl8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl8.setText("8");
        lbl8.setMaximumSize(getPreferredSize());
        lbl8.setMinimumSize(getPreferredSize());
        lbl8.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lbl8);
        lbl8.setBounds(355, 30, 30, 30);

        lbl9.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lbl9.setForeground(java.awt.Color.white);
        lbl9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl9.setText("9");
        lbl9.setMaximumSize(getPreferredSize());
        lbl9.setMinimumSize(getPreferredSize());
        lbl9.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lbl9);
        lbl9.setBounds(395, 30, 30, 30);

        lbl10.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lbl10.setForeground(java.awt.Color.white);
        lbl10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl10.setText("10");
        lbl10.setMaximumSize(getPreferredSize());
        lbl10.setMinimumSize(getPreferredSize());
        lbl10.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lbl10);
        lbl10.setBounds(425, 30, 30, 30);

        lblAcertosJogador.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblAcertosJogador.setForeground(java.awt.Color.white);
        lblAcertosJogador.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAcertosJogador.setText("0 Acerto(s)");
        lblAcertosJogador.setMaximumSize(getPreferredSize());
        lblAcertosJogador.setMinimumSize(getPreferredSize());
        lblAcertosJogador.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lblAcertosJogador);
        lblAcertosJogador.setBounds(60, 460, 400, 30);

        barraVidaJogador.setForeground(new java.awt.Color(255, 0, 0));
        panJogador.add(barraVidaJogador);
        barraVidaJogador.setBounds(170, 0, 300, 20);

        lblVezJogador.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblVezJogador.setForeground(java.awt.Color.white);
        lblVezJogador.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblVezJogador.setText("Jogador");
        lblVezJogador.setMaximumSize(getPreferredSize());
        lblVezJogador.setMinimumSize(getPreferredSize());
        lblVezJogador.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lblVezJogador);
        lblVezJogador.setBounds(0, 0, 170, 30);

        panTabuleiroJogador.setOpaque(false);
        panTabuleiroJogador.setLayout(new java.awt.GridLayout(10, 10));
        panJogador.add(panTabuleiroJogador);
        panTabuleiroJogador.setBounds(60, 60, 400, 400);

        panJogo.add(panJogador);
        panJogador.setBounds(0, 30, 470, 490);

        panComputador.setBackground(java.awt.Color.white);
        panComputador.setMaximumSize(getPreferredSize());
        panComputador.setMinimumSize(getPreferredSize());
        panComputador.setOpaque(false);
        panComputador.setPreferredSize(new java.awt.Dimension(470, 490));
        panComputador.setLayout(null);

        lblA1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblA1.setForeground(java.awt.Color.white);
        lblA1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblA1.setText("A");
        lblA1.setMaximumSize(getPreferredSize());
        lblA1.setMinimumSize(getPreferredSize());
        lblA1.setPreferredSize(new java.awt.Dimension(30, 30));
        panComputador.add(lblA1);
        lblA1.setBounds(30, 70, 30, 30);

        lblB1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblB1.setForeground(java.awt.Color.white);
        lblB1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblB1.setText("B");
        lblB1.setMaximumSize(getPreferredSize());
        lblB1.setMinimumSize(getPreferredSize());
        lblB1.setPreferredSize(new java.awt.Dimension(30, 30));
        panComputador.add(lblB1);
        lblB1.setBounds(30, 110, 30, 30);

        lblC1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblC1.setForeground(java.awt.Color.white);
        lblC1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblC1.setText("C");
        lblC1.setMaximumSize(getPreferredSize());
        lblC1.setMinimumSize(getPreferredSize());
        lblC1.setPreferredSize(new java.awt.Dimension(30, 30));
        panComputador.add(lblC1);
        lblC1.setBounds(30, 150, 30, 30);

        lblD1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblD1.setForeground(java.awt.Color.white);
        lblD1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblD1.setText("D");
        lblD1.setMaximumSize(getPreferredSize());
        lblD1.setMinimumSize(getPreferredSize());
        lblD1.setPreferredSize(new java.awt.Dimension(30, 30));
        panComputador.add(lblD1);
        lblD1.setBounds(30, 190, 30, 30);

        lblE1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblE1.setForeground(java.awt.Color.white);
        lblE1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblE1.setText("E");
        lblE1.setMaximumSize(getPreferredSize());
        lblE1.setMinimumSize(getPreferredSize());
        lblE1.setPreferredSize(new java.awt.Dimension(30, 30));
        panComputador.add(lblE1);
        lblE1.setBounds(30, 230, 30, 30);

        lblF1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblF1.setForeground(java.awt.Color.white);
        lblF1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblF1.setText("F");
        lblF1.setMaximumSize(getPreferredSize());
        lblF1.setMinimumSize(getPreferredSize());
        lblF1.setPreferredSize(new java.awt.Dimension(30, 30));
        panComputador.add(lblF1);
        lblF1.setBounds(30, 270, 30, 30);

        lblG1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblG1.setForeground(java.awt.Color.white);
        lblG1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblG1.setText("G");
        lblG1.setMaximumSize(getPreferredSize());
        lblG1.setMinimumSize(getPreferredSize());
        lblG1.setPreferredSize(new java.awt.Dimension(30, 30));
        panComputador.add(lblG1);
        lblG1.setBounds(30, 310, 30, 30);

        lblH1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblH1.setForeground(java.awt.Color.white);
        lblH1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblH1.setText("H");
        lblH1.setMaximumSize(getPreferredSize());
        lblH1.setMinimumSize(getPreferredSize());
        lblH1.setPreferredSize(new java.awt.Dimension(30, 30));
        panComputador.add(lblH1);
        lblH1.setBounds(30, 350, 30, 30);

        lblI1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblI1.setForeground(java.awt.Color.white);
        lblI1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblI1.setText("I");
        lblI1.setMaximumSize(getPreferredSize());
        lblI1.setMinimumSize(getPreferredSize());
        lblI1.setPreferredSize(new java.awt.Dimension(30, 30));
        panComputador.add(lblI1);
        lblI1.setBounds(30, 390, 30, 30);

        lblJ1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblJ1.setForeground(java.awt.Color.white);
        lblJ1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblJ1.setText("J");
        lblJ1.setMaximumSize(getPreferredSize());
        lblJ1.setMinimumSize(getPreferredSize());
        lblJ1.setPreferredSize(new java.awt.Dimension(30, 30));
        panComputador.add(lblJ1);
        lblJ1.setBounds(30, 430, 30, 30);

        lbl11.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lbl11.setForeground(java.awt.Color.white);
        lbl11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl11.setText("1");
        lbl11.setMaximumSize(getPreferredSize());
        lbl11.setMinimumSize(getPreferredSize());
        lbl11.setPreferredSize(new java.awt.Dimension(30, 30));
        panComputador.add(lbl11);
        lbl11.setBounds(75, 30, 30, 30);

        lbl12.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lbl12.setForeground(java.awt.Color.white);
        lbl12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl12.setText("2");
        lbl12.setMaximumSize(getPreferredSize());
        lbl12.setMinimumSize(getPreferredSize());
        lbl12.setPreferredSize(new java.awt.Dimension(30, 30));
        panComputador.add(lbl12);
        lbl12.setBounds(115, 30, 30, 30);

        lbl13.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lbl13.setForeground(java.awt.Color.white);
        lbl13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl13.setText("3");
        lbl13.setMaximumSize(getPreferredSize());
        lbl13.setMinimumSize(getPreferredSize());
        lbl13.setPreferredSize(new java.awt.Dimension(30, 30));
        panComputador.add(lbl13);
        lbl13.setBounds(155, 30, 30, 30);

        lbl14.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lbl14.setForeground(java.awt.Color.white);
        lbl14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl14.setText("4");
        lbl14.setMaximumSize(getPreferredSize());
        lbl14.setMinimumSize(getPreferredSize());
        lbl14.setPreferredSize(new java.awt.Dimension(30, 30));
        panComputador.add(lbl14);
        lbl14.setBounds(195, 30, 30, 30);

        lbl15.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lbl15.setForeground(java.awt.Color.white);
        lbl15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl15.setText("5");
        lbl15.setMaximumSize(getPreferredSize());
        lbl15.setMinimumSize(getPreferredSize());
        lbl15.setPreferredSize(new java.awt.Dimension(30, 30));
        panComputador.add(lbl15);
        lbl15.setBounds(235, 30, 30, 30);

        lbl16.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lbl16.setForeground(java.awt.Color.white);
        lbl16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl16.setText("6");
        lbl16.setMaximumSize(getPreferredSize());
        lbl16.setMinimumSize(getPreferredSize());
        lbl16.setPreferredSize(new java.awt.Dimension(30, 30));
        panComputador.add(lbl16);
        lbl16.setBounds(275, 30, 30, 30);

        lbl17.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lbl17.setForeground(java.awt.Color.white);
        lbl17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl17.setText("7");
        lbl17.setMaximumSize(getPreferredSize());
        lbl17.setMinimumSize(getPreferredSize());
        lbl17.setPreferredSize(new java.awt.Dimension(30, 30));
        panComputador.add(lbl17);
        lbl17.setBounds(315, 30, 30, 30);

        lbl18.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lbl18.setForeground(java.awt.Color.white);
        lbl18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl18.setText("8");
        lbl18.setMaximumSize(getPreferredSize());
        lbl18.setMinimumSize(getPreferredSize());
        lbl18.setPreferredSize(new java.awt.Dimension(30, 30));
        panComputador.add(lbl18);
        lbl18.setBounds(355, 30, 30, 30);

        lbl19.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lbl19.setForeground(java.awt.Color.white);
        lbl19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl19.setText("9");
        lbl19.setMaximumSize(getPreferredSize());
        lbl19.setMinimumSize(getPreferredSize());
        lbl19.setPreferredSize(new java.awt.Dimension(30, 30));
        panComputador.add(lbl19);
        lbl19.setBounds(395, 30, 30, 30);

        lbl20.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lbl20.setForeground(java.awt.Color.white);
        lbl20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbl20.setText("10");
        lbl20.setMaximumSize(getPreferredSize());
        lbl20.setMinimumSize(getPreferredSize());
        lbl20.setPreferredSize(new java.awt.Dimension(30, 30));
        panComputador.add(lbl20);
        lbl20.setBounds(425, 30, 30, 30);

        lblAcertosComputador.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblAcertosComputador.setForeground(java.awt.Color.white);
        lblAcertosComputador.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAcertosComputador.setText("0 Acerto(s)");
        lblAcertosComputador.setMaximumSize(getPreferredSize());
        lblAcertosComputador.setMinimumSize(getPreferredSize());
        lblAcertosComputador.setPreferredSize(new java.awt.Dimension(30, 30));
        panComputador.add(lblAcertosComputador);
        lblAcertosComputador.setBounds(60, 460, 400, 30);

        barraVidaComputador.setForeground(new java.awt.Color(255, 0, 0));
        panComputador.add(barraVidaComputador);
        barraVidaComputador.setBounds(170, 0, 300, 20);

        lblVezComputador.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblVezComputador.setForeground(java.awt.Color.white);
        lblVezComputador.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblVezComputador.setText("Computador");
        lblVezComputador.setMaximumSize(getPreferredSize());
        lblVezComputador.setMinimumSize(getPreferredSize());
        lblVezComputador.setPreferredSize(new java.awt.Dimension(30, 30));
        panComputador.add(lblVezComputador);
        lblVezComputador.setBounds(0, 0, 170, 30);

        panTabuleiroComputador.setOpaque(false);
        panTabuleiroComputador.setLayout(new java.awt.GridLayout(10, 10));
        panComputador.add(panTabuleiroComputador);
        panTabuleiroComputador.setBounds(60, 60, 400, 400);

        panJogo.add(panComputador);
        panComputador.setBounds(470, 30, 470, 490);

        btnMenu.setFont(new java.awt.Font("SansSerif", 3, 18)); // NOI18N
        btnMenu.setText("Menu");
        btnMenu.setFocusable(false);
        btnMenu.setPreferredSize(new java.awt.Dimension(100, 40));
        btnMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuActionPerformed(evt);
            }
        });
        panJogo.add(btnMenu);
        btnMenu.setBounds(30, 620, 100, 40);

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
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnMinimizarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnMinimizarMouseExited(evt);
            }
        });
        painelFerramentas.add(btnMinimizar);
        btnMinimizar.setBounds(21, 0, 25, 18);

        panBarraTarefas.add(painelFerramentas);
        painelFerramentas.setBounds(1104, 0, 97, 18);

        panJogo.add(panBarraTarefas);
        panBarraTarefas.setBounds(0, 0, 1200, 30);

        panMenuJogar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 20));
        panMenuJogar.setLayout(null);

        lblRespostaDica.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblRespostaDica.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRespostaDica.setText("Sim");
        panMenuJogar.add(lblRespostaDica);
        lblRespostaDica.setBounds(150, 530, 70, 30);

        btnTiroComum.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        btnTiroComum.setText("Comum");
        btnTiroComum.setFocusable(false);
        btnTiroComum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTiroComumActionPerformed(evt);
            }
        });
        panMenuJogar.add(btnTiroComum);
        btnTiroComum.setBounds(20, 110, 200, 50);
        panMenuJogar.add(jSeparator3);
        jSeparator3.setBounds(20, 590, 200, 10);

        btnDica.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        btnDica.setText("Dica");
        btnDica.setFocusable(false);
        btnDica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDicaActionPerformed(evt);
            }
        });
        panMenuJogar.add(btnDica);
        btnDica.setBounds(20, 560, 200, 25);

        btnTiroEstrela.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        btnTiroEstrela.setText(" Estrela");
        btnTiroEstrela.setFocusable(false);
        btnTiroEstrela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTiroEstrelaActionPerformed(evt);
            }
        });
        panMenuJogar.add(btnTiroEstrela);
        btnTiroEstrela.setBounds(20, 365, 200, 50);

        btnTiroCascata.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        btnTiroCascata.setText("Cascata");
        btnTiroCascata.setFocusable(false);
        btnTiroCascata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTiroCascataActionPerformed(evt);
            }
        });
        panMenuJogar.add(btnTiroCascata);
        btnTiroCascata.setBounds(20, 245, 200, 50);

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 30)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Dica");
        panMenuJogar.add(jLabel3);
        jLabel3.setBounds(21, 430, 200, 30);

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("<html><br />Disparado pelo Submarino <br /> Alcance : proprio bloco<html/>");
        panMenuJogar.add(jLabel4);
        jLabel4.setBounds(15, 60, 200, 50);

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("<html><br />Disparado pelo Navio de Escolta <br /> Alcance : proprio bloco e o da direita<html/>");
        jLabel5.setPreferredSize(new java.awt.Dimension(200, 80));
        panMenuJogar.add(jLabel5);
        jLabel5.setBounds(20, 160, 200, 80);
        panMenuJogar.add(jSeparator4);
        jSeparator4.setBounds(20, 420, 200, 10);

        boxNumero.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        boxNumero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        boxNumero.setFocusable(false);
        panMenuJogar.add(boxNumero);
        boxNumero.setBounds(80, 530, 60, 28);

        boxLetra.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        boxLetra.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" }));
        boxLetra.setFocusable(false);
        panMenuJogar.add(boxLetra);
        boxLetra.setBounds(21, 530, 60, 28);

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Escolha o tiro");
        panMenuJogar.add(jLabel6);
        jLabel6.setBounds(20, 30, 200, 30);

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("<html><br />Disparado pelo Caca <br /> Alcance : proprio bloco e todos os adjacentes<html/>");
        jLabel7.setPreferredSize(new java.awt.Dimension(200, 60));
        panMenuJogar.add(jLabel7);
        jLabel7.setBounds(21, 300, 200, 60);

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("<html>Informa se ha algum veiculo na linha / coluna da posicao indicada <br/> Maximo de 3 dicas <html/>");
        jLabel8.setPreferredSize(new java.awt.Dimension(200, 60));
        panMenuJogar.add(jLabel8);
        jLabel8.setBounds(21, 460, 200, 60);

        btnSair.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        btnSair.setText("Sair");
        btnSair.setFocusable(false);
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });
        panMenuJogar.add(btnSair);
        btnSair.setBounds(20, 600, 200, 30);

        panJogo.add(panMenuJogar);
        panMenuJogar.setBounds(945, 40, 240, 650);

        lblInformacoes.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        lblInformacoes.setForeground(java.awt.Color.white);
        lblInformacoes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInformacoes.setText("Voce ja atirou aqui!");
        lblInformacoes.setMaximumSize(getPreferredSize());
        lblInformacoes.setMinimumSize(getPreferredSize());
        lblInformacoes.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogo.add(lblInformacoes);
        lblInformacoes.setBounds(40, 530, 900, 80);

        lblInformacoes1.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblInformacoes1.setForeground(java.awt.Color.white);
        lblInformacoes1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInformacoes1.setText("Para atirar escolha um tiro e clique no bloco desejado.");
        lblInformacoes1.setMaximumSize(getPreferredSize());
        lblInformacoes1.setMinimumSize(getPreferredSize());
        lblInformacoes1.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogo.add(lblInformacoes1);
        lblInformacoes1.setBounds(130, 600, 820, 80);

        lblInformacoes2.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        lblInformacoes2.setForeground(java.awt.Color.white);
        lblInformacoes2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInformacoes2.setText("Voce acertou todos os veiculos!");
        lblInformacoes2.setMaximumSize(getPreferredSize());
        lblInformacoes2.setMinimumSize(getPreferredSize());
        lblInformacoes2.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogo.add(lblInformacoes2);
        lblInformacoes2.setBounds(40, 530, 900, 80);

        lblInformacoes3.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        lblInformacoes3.setForeground(java.awt.Color.white);
        lblInformacoes3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInformacoes3.setText("Voce acertou todos os veiculos armados!");
        lblInformacoes3.setMaximumSize(getPreferredSize());
        lblInformacoes3.setMinimumSize(getPreferredSize());
        lblInformacoes3.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogo.add(lblInformacoes3);
        lblInformacoes3.setBounds(40, 530, 900, 80);

        lblInformacoes4.setFont(new java.awt.Font("SansSerif", 1, 32)); // NOI18N
        lblInformacoes4.setForeground(java.awt.Color.white);
        lblInformacoes4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInformacoes4.setText("O computador acertou todos os seus veiculos!");
        lblInformacoes4.setMaximumSize(getPreferredSize());
        lblInformacoes4.setMinimumSize(getPreferredSize());
        lblInformacoes4.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogo.add(lblInformacoes4);
        lblInformacoes4.setBounds(40, 530, 900, 80);

        lblInformacoes5.setFont(new java.awt.Font("SansSerif", 1, 32)); // NOI18N
        lblInformacoes5.setForeground(java.awt.Color.white);
        lblInformacoes5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInformacoes5.setText("<html>O computador acertou todos os seus veiculos<br/>armados!<html/>");
        lblInformacoes5.setMaximumSize(getPreferredSize());
        lblInformacoes5.setMinimumSize(getPreferredSize());
        lblInformacoes5.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogo.add(lblInformacoes5);
        lblInformacoes5.setBounds(40, 530, 900, 80);

        lblFundoFInal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/image_fundo_jogar.png"))); // NOI18N
        lblFundoFInal.setPreferredSize(new java.awt.Dimension(1200, 700));
        panJogo.add(lblFundoFInal);
        lblFundoFInal.setBounds(0, 30, 1200, 700);

        getContentPane().add(panJogo);
        panJogo.setBounds(0, 0, 1200, 700);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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

    private void btnMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuActionPerformed
        // TODO add your handling code here:
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog (null, "Gostaria mesmo de voltar ao menu? \nSeu jogo sera perdido","Menu", dialogButton);
        if(dialogResult == JOptionPane.YES_OPTION){
            Menu menu = new Menu();
            menu.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_btnMenuActionPerformed

    private void btnTiroComumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTiroComumActionPerformed
        tipoTiro = 1;
    }//GEN-LAST:event_btnTiroComumActionPerformed

    private void btnDicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDicaActionPerformed
        // TODO add your handling code here:
        String letra = boxLetra.getSelectedItem().toString();
        int linha = 11;
        int coluna = Integer.parseInt(boxNumero.getSelectedItem().toString()) - 1;
        switch(letra)
        {
            case "A":
                linha = 0;
                break;
            case "B":
                linha = 1;
                break;
            case "C":
                linha = 2;
                break;
            case "D":
                linha = 3;
                break;
            case "E":
                linha = 4;
                break;
            case "F":
                linha = 5;
                break;
            case "G":
                linha = 6;
                break;
            case "H":
                linha = 7;
                break;
            case "I":
                linha = 8;
                break;
            case "J":
                linha = 9;
                break;
        }
        boolean tem = false;
        for(int l=0; l<=9; l++)
        {
            for(int c=0; c<=9;c++)
            {
                if( (tabuleiroComputador.getBlocoTabuleiro(l, c) != 0) && ( (l == linha) || (c == coluna) ) )
                {
                    tem = true;
                    break;
                }
            }
        }
        if(tem) lblRespostaDica.setText("Sim");
        else lblRespostaDica.setText("Nao"); 
        lblRespostaDica.setVisible(true);
        dica++;
        
        if(dica >= 3)
        {
            boxLetra.setEnabled(false);
            boxNumero.setEnabled(false);
            btnDica.setEnabled(false);
            lblRespostaDica.setEnabled(false);
        }
    }//GEN-LAST:event_btnDicaActionPerformed

    private void btnTiroEstrelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTiroEstrelaActionPerformed
        // TODO add your handling code here:
        tipoTiro = 3;
    }//GEN-LAST:event_btnTiroEstrelaActionPerformed

    private void btnTiroCascataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTiroCascataActionPerformed
        // TODO add your handling code here:
        tipoTiro = 2;
    }//GEN-LAST:event_btnTiroCascataActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        // TODO add your handling code here:        
        if(fimJogo)
        {
            Final fim = new Final(ganhou);
            fim.setTabuleiroJogador(tabuleiroJogador);
            fim.setTabuleiroComputador(tabuleiroComputador);
            fim.setVisible(true);
            this.dispose();
        }
        else
        {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog (null, "Gostaria mesmo de desistir? \nSeu jogo sera perdido","Sair", dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION)
            {
                Final fim = new Final(false);
                fim.setTabuleiroJogador(tabuleiroJogador);
                fim.setTabuleiroComputador(tabuleiroComputador);
                fim.setVisible(true);
                this.dispose();
            }
        }
        
        
                
    }//GEN-LAST:event_btnSairActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barraVidaComputador;
    private javax.swing.JProgressBar barraVidaJogador;
    private javax.swing.JComboBox<String> boxLetra;
    private javax.swing.JComboBox<String> boxNumero;
    private javax.swing.JButton btnDica;
    private javax.swing.JLabel btnFechar;
    private javax.swing.JButton btnMenu;
    private javax.swing.JLabel btnMinimizar;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnTiroCascata;
    private javax.swing.JButton btnTiroComum;
    private javax.swing.JButton btnTiroEstrela;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lbl1;
    private javax.swing.JLabel lbl10;
    private javax.swing.JLabel lbl11;
    private javax.swing.JLabel lbl12;
    private javax.swing.JLabel lbl13;
    private javax.swing.JLabel lbl14;
    private javax.swing.JLabel lbl15;
    private javax.swing.JLabel lbl16;
    private javax.swing.JLabel lbl17;
    private javax.swing.JLabel lbl18;
    private javax.swing.JLabel lbl19;
    private javax.swing.JLabel lbl2;
    private javax.swing.JLabel lbl20;
    private javax.swing.JLabel lbl3;
    private javax.swing.JLabel lbl4;
    private javax.swing.JLabel lbl5;
    private javax.swing.JLabel lbl6;
    private javax.swing.JLabel lbl7;
    private javax.swing.JLabel lbl8;
    private javax.swing.JLabel lbl9;
    private javax.swing.JLabel lblA;
    private javax.swing.JLabel lblA1;
    private javax.swing.JLabel lblAcertosComputador;
    private javax.swing.JLabel lblAcertosJogador;
    private javax.swing.JLabel lblB;
    private javax.swing.JLabel lblB1;
    private javax.swing.JLabel lblC;
    private javax.swing.JLabel lblC1;
    private javax.swing.JLabel lblD;
    private javax.swing.JLabel lblD1;
    private javax.swing.JLabel lblE;
    private javax.swing.JLabel lblE1;
    private javax.swing.JLabel lblF;
    private javax.swing.JLabel lblF1;
    private javax.swing.JLabel lblFundoFInal;
    private javax.swing.JLabel lblG;
    private javax.swing.JLabel lblG1;
    private javax.swing.JLabel lblH;
    private javax.swing.JLabel lblH1;
    private javax.swing.JLabel lblI;
    private javax.swing.JLabel lblI1;
    private javax.swing.JLabel lblInformacoes;
    private javax.swing.JLabel lblInformacoes1;
    private javax.swing.JLabel lblInformacoes2;
    private javax.swing.JLabel lblInformacoes3;
    private javax.swing.JLabel lblInformacoes4;
    private javax.swing.JLabel lblInformacoes5;
    private javax.swing.JLabel lblJ;
    private javax.swing.JLabel lblJ1;
    private javax.swing.JLabel lblRespostaDica;
    private javax.swing.JLabel lblVezComputador;
    private javax.swing.JLabel lblVezJogador;
    private javax.swing.JPanel painelFerramentas;
    private javax.swing.JPanel panBarraTarefas;
    private javax.swing.JPanel panComputador;
    private javax.swing.JPanel panJogador;
    private javax.swing.JPanel panJogo;
    private javax.swing.JPanel panMenuJogar;
    private javax.swing.JPanel panTabuleiroComputador;
    private javax.swing.JPanel panTabuleiroJogador;
    // End of variables declaration//GEN-END:variables
}
