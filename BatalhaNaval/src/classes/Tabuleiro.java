/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.awt.Color;
import javax.swing.ImageIcon;

/**
 *
 * @author mayla
 */
public abstract class Tabuleiro {
    private final ImageIcon[] porta;
    private final ImageIcon[] navio;
    private final ImageIcon[] submarino;
    private final ImageIcon[] caca;

    private int[][] tabuleiro;
    private BlocoLabel[][] tabuleiroTela;
    private int tirosAtingidos; //tiros q acertaram navios(por quantidade de blocos)
    private final VeiculoMilitar[] veiculos;

    public Tabuleiro()
    {
        porta = new javax.swing.ImageIcon[4];
        navio = new javax.swing.ImageIcon[3];
        submarino = new javax.swing.ImageIcon[2];
        caca = new javax.swing.ImageIcon[2];
        tabuleiro = new int[10][10];
        tabuleiroTela = new BlocoLabel[10][10]; 
        tirosAtingidos = 0;
        //1- Porta //2- Navio //3- Submarino //4-Caça
        veiculos = new VeiculoMilitar[4];
        classes.PortaAvioes portaAvioes = new classes.PortaAvioes();
        veiculos[0] = portaAvioes;
        classes.NavioEscolta navioEscolta = new classes.NavioEscolta();
        veiculos[1] = navioEscolta;
        classes.Submarino sub = new classes.Submarino();
        veiculos[2] = sub;
        classes.Caca jato = new classes.Caca();
        veiculos[3] = jato;
        
        //instancia o vetor de BlocoLabel
        for(int l=0; l<=9; l++)
        {
            for(int c=0; c<=9;c++)
            {
                this.tabuleiroTela[l][c] = new BlocoLabel(l,c);
                this.tabuleiroTela[l][c].setForeground(Color.red);
                this.tabuleiroTela[l][c].setFont(new java.awt.Font("DejaVu Sans Mono", java.awt.Font.BOLD, 45));
                this.tabuleiroTela[l][c].setHorizontalTextPosition(BlocoLabel.CENTER); //traz pra frente
                this.tabuleiroTela[l][c].setHorizontalAlignment(javax.swing.SwingConstants.CENTER); //centraliza
            }
        }
        instanciarVetoresIcons();
    }
    
    private void instanciarVetoresIcons()
    {
        for(int p=0; p <= 3; p++)
        {
            porta[p] = new ImageIcon(getClass().getResource("/images/Porta" + Integer.toString(p) +".png"));
      
        }
        for(int n=0; n <= 2; n++)
        {
            navio[n] = new ImageIcon(getClass().getResource("/images/Navio" + Integer.toString(n) +".png"));
            
        }

        for(int sc=0; sc <= 1; sc++)
        {
           submarino[sc] = new ImageIcon(getClass().getResource("/images/Submarino" + Integer.toString(sc) +".png"));
           caca[sc] = new ImageIcon(getClass().getResource("/images/Caca" + Integer.toString(sc) +".png"));
        }
    }
    
    
    //atualiza a frase que fica no label
    public void atualizarToolTip(int[][] tabuleiro, BlocoLabel[][] tabuleiroTela){}

    //gets e sets
    
    public ImageIcon[] getPorta() {
        return porta;
    }

    public ImageIcon[] getNavio() {
        return navio;
    }

    public ImageIcon[] getSubmarino() {
        return submarino;
    }

    public ImageIcon[] getCaca() {
        return caca;
    }
    
    public BlocoLabel[][] getTabuleiroTela() {
        return tabuleiroTela;
    }

    public void setTabuleiroTela(BlocoLabel[][] tabuleiroTela) {
        this.tabuleiroTela = tabuleiroTela;
    }
   
    public int getTirosAtingidos() {
        return tirosAtingidos;
    }

    public void setTirosAtingidos(int tirosAtingidos) {
        this.tirosAtingidos = tirosAtingidos;
    }

    public int[][] getTabuleiro() {
        return tabuleiro;
    }

    public void setTabuleiro(int[][] tabuleiro) {
        this.tabuleiro = tabuleiro;
    }
    
    public void setBlocoTabuleiro(int l, int c,int valor)
    {
        this.tabuleiro[l][c] = valor;
    }
    public int getBlocoTabuleiro(int l,int c)
    {
        return this.tabuleiro[l][c];
    }
    
    //nao relacionados aos atributos da classe
    public void setBlocoTabuleiroTela(int l, int c,BlocoLabel lbl)
    {
        this.tabuleiroTela[l][c] = lbl;
    }
    public BlocoLabel getBlocoTabuleiroTela(int l,int c)
    {
        return this.tabuleiroTela[l][c];
    }
    
    public VeiculoMilitar getCadaVeiculo(int pos)
    {
        return this.veiculos[pos];
    }
    public void setCadaVeiculo(int pos, VeiculoMilitar embarcacao)
    {
        this.veiculos[pos] = embarcacao;
    }
}
