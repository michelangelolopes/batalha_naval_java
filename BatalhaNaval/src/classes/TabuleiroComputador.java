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
    
}
