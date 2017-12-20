/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

/**
 *
 * @author mayla
 */
public final class BlocoLabel extends JLabel {
    private int tipo;//representa o numero do obj q esta ali
    private int[] pos =  new int[2]; //0=l 1=c
    private boolean levouTiro = false;

    public BlocoLabel(int l, int c)
    {
                pos[0] = l;
                pos[1] = c;       
                setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
                setOpaque(true);
                setPreferredSize(new java.awt.Dimension(40, 40));
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                addMouseListener(new MouseAdapter(){   

                    @Override
                    public void mousePressed(MouseEvent e)   
                    {   
                        if(!levouTiro) setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));      
                    }   
                });
                addMouseListener(new MouseAdapter(){   

                    @Override
                    public void mouseReleased(MouseEvent e)   
                    {   
                         if(!levouTiro) setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));      
                    }   
                });
              
    }

    public boolean isLevouTiro() {
        return levouTiro;
    }

    public void setLevouTiro(boolean levouTiro) {
        this.levouTiro = levouTiro;
    }
}
