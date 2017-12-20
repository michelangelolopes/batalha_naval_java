/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;
import java.util.Random;
import javax.swing.ImageIcon;
/**
 *
 * @author Michelangelo
 */
public final class Aleatorio 
{  
    public static Tabuleiro gerarTabuleiro(Tabuleiro tabuleiro) 
    {
        Random random = new Random();
        int linha;
        int coluna;
        
        for(int opcao = 1; opcao <= 4; opcao++)
        {
            ImageIcon[] icons = new javax.swing.ImageIcon[4];
            int ocupa = 0;
            boolean podeLugar = true;
            boolean teste = true;
            
            linha = random.nextInt(10);
            coluna = random.nextInt(10);
            
            switch(opcao)
            {
                case 1:
                    ocupa = 4;
                    break;
                case 2:
                    ocupa = 3;
                    break;
                case 3:
                    ocupa = 2;
                    break;
                case 4:
                    ocupa = 2;
                    break;
            }
            
            if(coluna + ocupa > 10)
            {
               podeLugar = false;
               opcao--;
            }
            
            if(podeLugar)
            {
                switch(opcao)
                {
                    case 1:
                        icons = tabuleiro.getPorta();
                        break;
                    case 2:
                        icons = tabuleiro.getNavio();
                        break;
                    case 3:
                        icons = tabuleiro.getSubmarino();
                        break;
                    case 4:
                        icons = tabuleiro.getCaca();
                        break;
                }
                
                for(int v = 0; v <= ocupa - 1; v++)
                {
                    teste = (tabuleiro.getBlocoTabuleiro(linha, coluna + v) == 0);
                    
                    if(!teste)
                    {
                        break;
                    }
                }
                
                if(!teste)
                {
                    opcao--;
                }
                
                else
                {
                    for(int v = 0; v <= ocupa - 1; v++)
                    {
                        if(tabuleiro instanceof TabuleiroJogador)
                        {
                            tabuleiro.getBlocoTabuleiroTela(linha, coluna + v).setIcon(icons[v]);
                        }
                        tabuleiro.setBlocoTabuleiro(linha, coluna + v, opcao);

                    }
                } 
            }
        }
        
        return tabuleiro;
    }
}
