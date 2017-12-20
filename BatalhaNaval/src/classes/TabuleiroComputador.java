/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 *
 * @author mayla
 */
public final class TabuleiroComputador extends Tabuleiro{
    
    public TabuleiroComputador()
    {
        for(int l = 0; l <= 9; l++)
        {
            for(int c = 0; c <= 9; c++)
            {
               getTabuleiroTela()[l][c].setBackground(new java.awt.Color(240, 216, 216));
            }
        }
    }
    
    @Override
    public void atualizarToolTip(int[][] tabuleiro, BlocoLabel[][] tabuleiroTela)
    {
        for(int l = 0; l <= 9; l++)
        {
            for(int c = 0; c <= 9; c++)
            {
                if(!tabuleiroTela[l][c].isLevouTiro())
                {
                   tabuleiroTela[l][c].setToolTipText("Atirar"); 
                }
                else
                {
                    switch (tabuleiro[l][c])
                    {
                        case 0:
                            tabuleiroTela[l][c].setToolTipText("Água"); 
                            break;
                        case 1:
                            tabuleiroTela[l][c].setToolTipText("Porta-Aviões");
                            break;
                        case 2:
                            tabuleiroTela[l][c].setToolTipText("Navio de Escolta");
                            break;
                        case 3:
                            tabuleiroTela[l][c].setToolTipText("Submarino");
                            break;
                        case 4:
                            tabuleiroTela[l][c].setToolTipText("Caça");
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }
}
