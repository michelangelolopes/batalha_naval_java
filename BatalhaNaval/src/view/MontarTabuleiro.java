/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import classes.Aleatorio;
import javax.swing.JOptionPane;
import classes.Tabuleiro;
import classes.TabuleiroComputador;
import classes.TabuleiroJogador;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;


/**
 *
 * @author mayla
 */
public class MontarTabuleiro extends javax.swing.JFrame {
    private TabuleiroJogador tabuleiroJogador;
    private int opcao = 0;

    /**
     * Creates new form MontarTabuleiro
     */
    public MontarTabuleiro() {
        initComponents();
        barraProgresso.setMinimum(0);
        barraProgresso.setMaximum(100);
        painelFerramentas.setVisible(true);
        tabuleiroJogador  = new TabuleiroJogador();
        lblArquivo.setVisible(false);
        lblConcluido.setVisible(false);
        btnJogar.setEnabled(false);
        btnLimparTabuleiro.setEnabled(false);
        this.setIconImage(new ImageIcon (getClass().getResource("/images/icon_batalhaNaval.png")).getImage());

        for(int l=0; l<=9; l++)
        {
            for(int c=0; c<=9;c++)
            {
                //constantes necessarios porque o java reclama de l e c na classe anonima
                //classe anonima é o que faço no MouseClicked
                final int linha = l;
                final int coluna = c;
                panTabuleiroJogador.add(tabuleiroJogador.getBlocoTabuleiroTela(l, c));
                criarMouseClicked(linha,coluna); //chama o metodo q cria o MouseClicked 
            }
        }
    }
   
    
    public void criarMouseClicked(int linha,int coluna)
    {
        //cria um evento de MouseClicked para um BlocoLabel
        tabuleiroJogador.getBlocoTabuleiroTela(linha, coluna).addMouseListener(new MouseAdapter(){   

                    //sobreescreve o metodo mouseClicked da classe JLabel
                    @Override
                    public void mouseClicked(MouseEvent e)   
                    {  
                        // vetor de icons que ira receber o vetor de icons dependendo do tipo
                        ImageIcon[] icons = new javax.swing.ImageIcon[4];
                        //variavel logica que diz se pode ser adicionada a embarcacao ou nao
                        boolean podeLugar = true;
                        //variavel que recebe a quantidades de bloco q a embarcacao selecionada ocupa
                        int ocupa = 0;
                        //variavel que diz quais colunas cada embarcacao n pode 
                        //ser posta porque se for nao cabera ate o final da matriz
                        //inicializada com um valor aleatorio maior que os possiveis
                        int colunasNao = 10;
                        //epreenchimento das variaveis de acordo com a embarcacao selecionada
                        switch(opcao)
                        {
                            case 0:
                                ocupa = 0;
                                podeLugar = false;
                            case 1:
                                ocupa = 4;
                                colunasNao = 7;
                                break;
                            case 2:
                                ocupa = 3;
                                colunasNao = 8;
                                break;
                            case 3:
                                ocupa = 2;
                                colunasNao = 9;
                                break;
                            case 4:
                                ocupa = 2;
                                colunasNao = 9;
                                break;
                        }
                        if(opcao != 0)
                        {
                            //testa se a coluna é permitida de acordo com o valor de colunas nao permitidas da embarcacao
                            if(coluna >= colunasNao )
                            {
                               tabuleiroJogador.getBlocoTabuleiroTela(linha,coluna).setToolTipText("Esse objeto não pode ser posto aqui!");
                               podeLugar = false;
                            }
                            else
                            {
                                //caso a condição de colunas seja satisfeita, ele testa se todos os blocos a serem preenchidos estao vazios
                               for(int o = 0; o <= ocupa-1; o++)
                                {
                                    if(tabuleiroJogador.getBlocoTabuleiro(linha,coluna+o) != 0)
                                    {
                                        //setToolTipText muda o texto que aparece no label
                                        tabuleiroJogador.getBlocoTabuleiroTela(linha,coluna).setToolTipText("Esse objeto não pode ser posto aqui!");
                                        podeLugar = false;
                                        break;
                                    }
                                }
                            }
                        }
                        //caso nada impeça de por o bloco, esse if o faz
                        if(podeLugar)
                        {
                            //seta as variaveis de acordo com o tipo de embarcacao
                            switch(opcao)
                            {
                                case 1:
                                    icons = tabuleiroJogador.getPorta();
                                    btnPortaAvioes.setEnabled(false); //deixa o botao nao clicavel
                                    break; 

                                case 2:icons = tabuleiroJogador.getNavio();
                                    btnNavio.setEnabled(false);
                                    break;

                                case 3:icons = tabuleiroJogador.getSubmarino();
                                    btnSubmarino.setEnabled(false);
                                    break;

                                case 4:
                                    icons = tabuleiroJogador.getCaca();
                                    btnCaca.setEnabled(false);
                                    break;

                            }
                            //percorre todos os labels que a embarcacao vai ocupar mudando eles
                            for(int v = 0; v <= ocupa-1; v++)
                            {
                                //muda o icone pro icone correspondente 
                                tabuleiroJogador.getBlocoTabuleiroTela(linha,coluna+v).setIcon(icons[v]);
                                //muda a variavel de inteiros preenchendo ela com o numero corerspondente ao tipo de embarcacao
                                tabuleiroJogador.setBlocoTabuleiro(linha,coluna+v,opcao);
                            } 
                            //muda o texto do label dizendo o nome da embarcacao que tem ali agora
                            tabuleiroJogador.atualizarToolTip(tabuleiroJogador.getTabuleiro(),tabuleiroJogador.getTabuleiroTela());
                            //aumenta a barra de progresso em 25(ja q sao 4 tipos de embarcacao)
                            barraProgresso.setValue(barraProgresso.getValue() + 25);
                            //caso a barra esteja completa ele habilita o botao de jogar
                            if(barraProgresso.getValue() == 100)
                            {
                                btnJogar.setEnabled(true);
                                lblConcluido.setVisible(false);
                            }
                           //muda a variavel de opcao pra 0, fazendo assim 
                           //que não possa ser adicionado outra embarcacao do mesmo tipo
                           //e so seja adicionada outra embarcacao quando clicado no seu respectivo botao 
                           opcao = 0;  
                           btnLimparTabuleiro.setEnabled(true);
                        }
                        
                    }
                });
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
        panBarraTarefas = new javax.swing.JPanel();
        painelFerramentas = new javax.swing.JPanel();
        btnFechar = new javax.swing.JLabel();
        btnMinimizar = new javax.swing.JLabel();
        panMenuMontar = new javax.swing.JPanel();
        btnPortaAvioes = new javax.swing.JButton();
        btnSubmarino = new javax.swing.JButton();
        btnNavio = new javax.swing.JButton();
        btnCaca = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        btnArquivo = new javax.swing.JButton();
        lblArquivo = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        lblConcluido = new javax.swing.JLabel();
        btnJogar = new javax.swing.JButton();
        barraProgresso = new javax.swing.JProgressBar();
        btnLimparTabuleiro = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
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
        panTabuleiroJogador = new javax.swing.JPanel();
        lbl11 = new javax.swing.JLabel();
        btnMenu = new javax.swing.JButton();
        lblInformacoes1 = new javax.swing.JLabel();
        lblFundoFInal = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(getPreferredSize());
        setMinimumSize(getPreferredSize());
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1200, 700));
        setResizable(false);
        getContentPane().setLayout(null);

        panJogo.setBackground(java.awt.Color.white);
        panJogo.setPreferredSize(new java.awt.Dimension(1200, 700));
        panJogo.setLayout(null);

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
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnFecharMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnFecharMouseEntered(evt);
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

        panJogo.add(panBarraTarefas);
        panBarraTarefas.setBounds(0, 0, 1200, 30);

        panMenuMontar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 20));
        panMenuMontar.setLayout(null);

        btnPortaAvioes.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        btnPortaAvioes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/PortaOk.png"))); // NOI18N
        btnPortaAvioes.setText("Porta-Avioes");
        btnPortaAvioes.setFocusable(false);
        btnPortaAvioes.setPreferredSize(new java.awt.Dimension(80, 40));
        btnPortaAvioes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPortaAvioesActionPerformed(evt);
            }
        });
        panMenuMontar.add(btnPortaAvioes);
        btnPortaAvioes.setBounds(35, 80, 330, 45);

        btnSubmarino.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        btnSubmarino.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/SubmarinoOK.png"))); // NOI18N
        btnSubmarino.setText("Submarino");
        btnSubmarino.setFocusable(false);
        btnSubmarino.setPreferredSize(new java.awt.Dimension(80, 40));
        btnSubmarino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmarinoActionPerformed(evt);
            }
        });
        panMenuMontar.add(btnSubmarino);
        btnSubmarino.setBounds(35, 180, 330, 45);

        btnNavio.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        btnNavio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/NavioOk.png"))); // NOI18N
        btnNavio.setText("Navio de Escolta");
        btnNavio.setFocusable(false);
        btnNavio.setPreferredSize(new java.awt.Dimension(80, 40));
        btnNavio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNavioActionPerformed(evt);
            }
        });
        panMenuMontar.add(btnNavio);
        btnNavio.setBounds(35, 130, 330, 45);

        btnCaca.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        btnCaca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/CacaOk.png"))); // NOI18N
        btnCaca.setText("Caca");
        btnCaca.setFocusable(false);
        btnCaca.setPreferredSize(new java.awt.Dimension(80, 40));
        btnCaca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCacaActionPerformed(evt);
            }
        });
        panMenuMontar.add(btnCaca);
        btnCaca.setBounds(35, 230, 330, 45);

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Escolha o veiculo");
        panMenuMontar.add(jLabel2);
        jLabel2.setBounds(20, 21, 360, 60);
        panMenuMontar.add(jSeparator2);
        jSeparator2.setBounds(20, 350, 360, 10);

        btnArquivo.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        btnArquivo.setText("Selecionar Arquivo");
        btnArquivo.setFocusable(false);
        btnArquivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArquivoActionPerformed(evt);
            }
        });
        panMenuMontar.add(btnArquivo);
        btnArquivo.setBounds(35, 360, 330, 50);

        lblArquivo.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        lblArquivo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblArquivo.setText("Arquivo.txt");
        panMenuMontar.add(lblArquivo);
        lblArquivo.setBounds(35, 420, 330, 24);
        panMenuMontar.add(jSeparator3);
        jSeparator3.setBounds(20, 456, 360, 10);

        lblConcluido.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblConcluido.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblConcluido.setText("Concluido");
        panMenuMontar.add(lblConcluido);
        lblConcluido.setBounds(35, 530, 330, 32);

        btnJogar.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        btnJogar.setText("Jogar");
        btnJogar.setFocusable(false);
        btnJogar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJogarActionPerformed(evt);
            }
        });
        panMenuMontar.add(btnJogar);
        btnJogar.setBounds(35, 570, 330, 50);

        barraProgresso.setForeground(new java.awt.Color(255, 0, 0));
        panMenuMontar.add(barraProgresso);
        barraProgresso.setBounds(35, 480, 330, 40);

        btnLimparTabuleiro.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        btnLimparTabuleiro.setText("Limpar Tabuleiro");
        btnLimparTabuleiro.setFocusable(false);
        btnLimparTabuleiro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparTabuleiroActionPerformed(evt);
            }
        });
        panMenuMontar.add(btnLimparTabuleiro);
        btnLimparTabuleiro.setBounds(35, 292, 330, 50);
        panMenuMontar.add(jSeparator4);
        jSeparator4.setBounds(20, 280, 360, 10);

        panJogo.add(panMenuMontar);
        panMenuMontar.setBounds(765, 40, 400, 650);

        panJogador.setBackground(java.awt.Color.white);
        panJogador.setOpaque(false);
        panJogador.setPreferredSize(new java.awt.Dimension(500, 550));
        panJogador.setLayout(null);

        lblA.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblA.setForeground(java.awt.Color.white);
        lblA.setText("A");
        lblA.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lblA);
        lblA.setBounds(30, 70, 30, 30);

        lblB.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblB.setForeground(java.awt.Color.white);
        lblB.setText("B");
        lblB.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lblB);
        lblB.setBounds(30, 110, 30, 30);

        lblC.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblC.setForeground(java.awt.Color.white);
        lblC.setText("C");
        lblC.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lblC);
        lblC.setBounds(30, 150, 30, 30);

        lblD.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblD.setForeground(java.awt.Color.white);
        lblD.setText("D");
        lblD.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lblD);
        lblD.setBounds(30, 190, 30, 30);

        lblE.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblE.setForeground(java.awt.Color.white);
        lblE.setText("E");
        lblE.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lblE);
        lblE.setBounds(30, 230, 30, 30);

        lblF.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblF.setForeground(java.awt.Color.white);
        lblF.setText("F");
        lblF.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lblF);
        lblF.setBounds(30, 270, 30, 30);

        lblG.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblG.setForeground(java.awt.Color.white);
        lblG.setText("G");
        lblG.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lblG);
        lblG.setBounds(30, 310, 30, 30);

        lblH.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblH.setForeground(java.awt.Color.white);
        lblH.setText("H");
        lblH.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lblH);
        lblH.setBounds(30, 350, 30, 30);

        lblI.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblI.setForeground(java.awt.Color.white);
        lblI.setText("I");
        lblI.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lblI);
        lblI.setBounds(30, 390, 30, 30);

        lblJ.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblJ.setForeground(java.awt.Color.white);
        lblJ.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblJ.setText("J");
        lblJ.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lblJ);
        lblJ.setBounds(30, 430, 30, 30);

        lbl1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lbl1.setForeground(java.awt.Color.white);
        lbl1.setText("1");
        lbl1.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lbl1);
        lbl1.setBounds(75, 30, 30, 30);

        lbl2.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lbl2.setForeground(java.awt.Color.white);
        lbl2.setText("2");
        lbl2.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lbl2);
        lbl2.setBounds(115, 30, 30, 30);

        lbl3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lbl3.setForeground(java.awt.Color.white);
        lbl3.setText("3");
        lbl3.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lbl3);
        lbl3.setBounds(155, 30, 30, 30);

        lbl4.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lbl4.setForeground(java.awt.Color.white);
        lbl4.setText("4");
        lbl4.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lbl4);
        lbl4.setBounds(195, 30, 30, 30);

        lbl5.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lbl5.setForeground(java.awt.Color.white);
        lbl5.setText("5");
        lbl5.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lbl5);
        lbl5.setBounds(235, 30, 30, 30);

        lbl6.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lbl6.setForeground(java.awt.Color.white);
        lbl6.setText("6");
        lbl6.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lbl6);
        lbl6.setBounds(275, 30, 30, 30);

        lbl7.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lbl7.setForeground(java.awt.Color.white);
        lbl7.setText("7");
        lbl7.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lbl7);
        lbl7.setBounds(315, 30, 30, 30);

        lbl8.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lbl8.setForeground(java.awt.Color.white);
        lbl8.setText("8");
        lbl8.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lbl8);
        lbl8.setBounds(355, 30, 30, 30);

        lbl9.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lbl9.setForeground(java.awt.Color.white);
        lbl9.setText("9");
        lbl9.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lbl9);
        lbl9.setBounds(395, 30, 30, 30);

        lbl10.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lbl10.setForeground(java.awt.Color.white);
        lbl10.setText("10");
        lbl10.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogador.add(lbl10);
        lbl10.setBounds(425, 30, 30, 30);

        panTabuleiroJogador.setOpaque(false);
        panTabuleiroJogador.setLayout(new java.awt.GridLayout(10, 10));
        panJogador.add(panTabuleiroJogador);
        panTabuleiroJogador.setBounds(60, 60, 400, 400);

        panJogo.add(panJogador);
        panJogador.setBounds(60, 115, 500, 500);

        lbl11.setFont(new java.awt.Font("SansSerif", 1, 100)); // NOI18N
        lbl11.setForeground(java.awt.Color.white);
        lbl11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl11.setText("Montar Jogo");
        lbl11.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogo.add(lbl11);
        lbl11.setBounds(0, 30, 770, 120);

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

        lblInformacoes1.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        lblInformacoes1.setForeground(java.awt.Color.white);
        lblInformacoes1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInformacoes1.setText("<html>Selecione um veiculo e clique no bloco desejado  para posiciona-lo<html/> ");
        lblInformacoes1.setPreferredSize(new java.awt.Dimension(30, 30));
        panJogo.add(lblInformacoes1);
        lblInformacoes1.setBounds(150, 570, 610, 110);

        lblFundoFInal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/image_fundo_montar.png"))); // NOI18N
        lblFundoFInal.setPreferredSize(new java.awt.Dimension(1200, 700));
        panJogo.add(lblFundoFInal);
        lblFundoFInal.setBounds(0, 30, 1200, 700);

        getContentPane().add(panJogo);
        panJogo.setBounds(0, 0, 1200, 700);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnPortaAvioesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPortaAvioesActionPerformed
        // TODO add your handling code here:
        opcao = 1;
    }//GEN-LAST:event_btnPortaAvioesActionPerformed

    private void btnNavioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavioActionPerformed
        // TODO add your handling code here:
        opcao = 2;
    }//GEN-LAST:event_btnNavioActionPerformed

    private void btnSubmarinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmarinoActionPerformed
        // TODO add your handling code here:
        opcao = 3;
    }//GEN-LAST:event_btnSubmarinoActionPerformed

    private void btnCacaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCacaActionPerformed
        // TODO add your handling code here:
        opcao = 4;
    }//GEN-LAST:event_btnCacaActionPerformed

    private void btnArquivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArquivoActionPerformed
        // TODO add your handling code here:
        classes.Arquivo a = new classes.Arquivo();
        a.podeMontarTabuleiro();
        if(a.isPodeMontar())
        {
            opcao = 0;
            panTabuleiroJogador.removeAll();
            tabuleiroJogador = a.gerarTabuleiro();
            for(int l=0; l<=9; l++)
            {
                for(int c=0; c<=9;c++)
                {
                    panTabuleiroJogador.add(tabuleiroJogador.getBlocoTabuleiroTela(l, c));
                }
            }
            tabuleiroJogador.setEtapaMontar(false);
            tabuleiroJogador.atualizarToolTip(tabuleiroJogador.getTabuleiro(),tabuleiroJogador.getTabuleiroTela());

            barraProgresso.setValue(100);
            btnJogar.setEnabled(true);
            btnPortaAvioes.setEnabled(false);
            btnNavio.setEnabled(false);
            btnSubmarino.setEnabled(false);
            btnCaca.setEnabled(false);
            btnArquivo.setEnabled(false);
            lblArquivo.setText(a.getNome());
            lblArquivo.setVisible(true);
            lblConcluido.setVisible(true);
            btnLimparTabuleiro.setEnabled(true);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "A montagem por arquivo falhou!\nPossiveis razoes:\n1) Arquivo pode estar incompleto\n2) Arquivo pode nao conter posicoes validas suficientes\n3) Arquivo pode nao conter informacoes necessarias para a montagem\n");
        }
    }//GEN-LAST:event_btnArquivoActionPerformed

    private void btnLimparTabuleiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparTabuleiroActionPerformed
        // TODO add your handling code here:

        panTabuleiroJogador.removeAll();
        panTabuleiroJogador.revalidate(); //valida e atualiza
        panTabuleiroJogador.repaint(); //redesenha

        tabuleiroJogador = new TabuleiroJogador();
        for(int l=0; l<=9; l++)
        {
            for(int c=0; c<=9;c++)
            {
                final int linha = l;
                final int coluna = c;
                panTabuleiroJogador.add(tabuleiroJogador.getBlocoTabuleiroTela(l, c));
                criarMouseClicked(linha,coluna);
            }
        }
        btnPortaAvioes.setEnabled(true);
        btnNavio.setEnabled(true);
        btnSubmarino.setEnabled(true);
        btnCaca.setEnabled(true);
        barraProgresso.setValue(0);
        lblArquivo.setVisible(false);
        lblConcluido.setVisible(false);
        btnJogar.setEnabled(false);
        btnArquivo.setEnabled(true);
        btnLimparTabuleiro.setEnabled(false);
        tabuleiroJogador.setEtapaMontar(true);
        tabuleiroJogador.atualizarToolTip(tabuleiroJogador.getTabuleiro(),tabuleiroJogador.getTabuleiroTela());
    }//GEN-LAST:event_btnLimparTabuleiroActionPerformed

    private void btnJogarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJogarActionPerformed
        // TODO add your handling code here:
        TabuleiroComputador tabuleiroComputador = new TabuleiroComputador();
        tabuleiroJogador.setEtapaMontar(false);
        Jogo jogo = new Jogo(tabuleiroJogador, Aleatorio.gerarTabuleiro(tabuleiroComputador));
        jogo.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnJogarActionPerformed

    private void btnMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuActionPerformed
        // TODO add your handling code here:
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog (null, "Gostaria mesmo de Voltar ao Menu? \nSeu jogo sera perdido","Menu", dialogButton);
        if(dialogResult == JOptionPane.YES_OPTION){
            Menu menu = new Menu();
            menu.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_btnMenuActionPerformed

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barraProgresso;
    private javax.swing.JButton btnArquivo;
    private javax.swing.JButton btnCaca;
    private javax.swing.JLabel btnFechar;
    private javax.swing.JButton btnJogar;
    private javax.swing.JButton btnLimparTabuleiro;
    private javax.swing.JButton btnMenu;
    private javax.swing.JLabel btnMinimizar;
    private javax.swing.JButton btnNavio;
    private javax.swing.JButton btnPortaAvioes;
    private javax.swing.JButton btnSubmarino;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lbl1;
    private javax.swing.JLabel lbl10;
    private javax.swing.JLabel lbl11;
    private javax.swing.JLabel lbl2;
    private javax.swing.JLabel lbl3;
    private javax.swing.JLabel lbl4;
    private javax.swing.JLabel lbl5;
    private javax.swing.JLabel lbl6;
    private javax.swing.JLabel lbl7;
    private javax.swing.JLabel lbl8;
    private javax.swing.JLabel lbl9;
    private javax.swing.JLabel lblA;
    private javax.swing.JLabel lblArquivo;
    private javax.swing.JLabel lblB;
    private javax.swing.JLabel lblC;
    private javax.swing.JLabel lblConcluido;
    private javax.swing.JLabel lblD;
    private javax.swing.JLabel lblE;
    private javax.swing.JLabel lblF;
    private javax.swing.JLabel lblFundoFInal;
    private javax.swing.JLabel lblG;
    private javax.swing.JLabel lblH;
    private javax.swing.JLabel lblI;
    private javax.swing.JLabel lblInformacoes1;
    private javax.swing.JLabel lblJ;
    private javax.swing.JPanel painelFerramentas;
    private javax.swing.JPanel panBarraTarefas;
    private javax.swing.JPanel panJogador;
    private javax.swing.JPanel panJogo;
    private javax.swing.JPanel panMenuMontar;
    private javax.swing.JPanel panTabuleiroJogador;
    // End of variables declaration//GEN-END:variables
}
