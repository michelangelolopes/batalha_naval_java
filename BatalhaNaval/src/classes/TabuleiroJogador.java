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
public final class TabuleiroJogador extends Tabuleiro{
    private boolean etapaMontar;

    public TabuleiroJogador()
    {
        super();
        etapaMontar = true;
        for(int l=0; l<=9; l++)
        {
            for(int c=0; c<=9;c++)
            {
                getTabuleiroTela()[l][c].setBackground(new java.awt.Color(216, 235, 240)); 
            }
        }
        atualizarToolTip(getTabuleiro(),getTabuleiroTela());
    }
    
    @Override
    public void atualizarToolTip(int[][] tabuleiro, BlocoLabel[][] tabuleiroTela)
    {
        for(int l = 0; l <= 9; l++)
        {
            for(int c = 0; c <= 9; c++)
            {
                //etapaMontar = true = tela MontarTabuleiro
                if(etapaMontar && c == 9 && tabuleiro[l][c] == 0)
                {
                    tabuleiroTela[l][c].setToolTipText("Nao e possivel começar objetos aqui");
                }
                else
                {
                    switch (tabuleiro[l][c])
                    {
                        case 0:
                            if(etapaMontar)
                            {
                                tabuleiroTela[l][c].setToolTipText("Livre");
                            }
                            else
                            {
                                tabuleiroTela[l][c].setToolTipText(null);
                            }
                            break;
                        case 1:
                            tabuleiroTela[l][c].setToolTipText("Porta-Avioes");
                            break;
                        case 2:
                            tabuleiroTela[l][c].setToolTipText("Navio de Escolta");
                            break;
                        case 3:
                            tabuleiroTela[l][c].setToolTipText("Submarino");
                            break;
                        case 4:
                            tabuleiroTela[l][c].setToolTipText("Caca");
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }
    
    public boolean isEtapaMontar() {
        return etapaMontar;
    }

    public void setEtapaMontar(boolean etapaMontar) {
        this.etapaMontar = etapaMontar;
    }
}
