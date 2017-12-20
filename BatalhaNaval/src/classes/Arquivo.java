/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Michelangelo
 */
public final class Arquivo 
{
    private int[] linha;
    private int[] coluna;
    private int[] tamanho;
    private int arquivoSelecionado;
    private int qtdFinal;
    private int qtdCorreta;
    private int qtdValida;
    private boolean podeMontar = true;
    private JFileChooser procurarArquivo;
    private TabuleiroJogador tabuleiroJogador;
    private final FileFilter filtro;
    private String texto;
    private String nome;
    private ImageIcon[] icons;
    
    public Arquivo() 
    {   
        nome = "";
        filtro = new FileNameExtensionFilter("TXT", "txt");
        procurarArquivo = new JFileChooser();        
        arquivoSelecionado = abrirDiretorio(procurarArquivo);
        File arquivo;
        texto = " ";
        
        if (arquivoSelecionado == JFileChooser.APPROVE_OPTION) 
        {
            arquivo = procurarArquivo.getSelectedFile();
            texto = LerArquivo(arquivo.getPath());
            tabuleiroJogador = new TabuleiroJogador();
            nome = arquivo.getName();
        } 
    }
        
    public TabuleiroJogador gerarTabuleiro()
    {   
        preencherBlocoTabuleiro();
        
        return this.tabuleiroJogador;
    }
    
    public void podeMontarTabuleiro()
    {
        linha = new int[texto.length()];
        coluna = new int[texto.length()];
        tamanho = new int[texto.length()];
        qtdValida = 0;
        qtdFinal = 0;
        qtdCorreta = 0;

        receberValoresArquivo();        
        ignorarValoresIncorretos();
        permitirValoresSelecionados(tamanho, linha, coluna, qtdCorreta, qtdValida);
        
        if(qtdCorreta < 4)
        {
            podeMontar = false;
        }
        
        simularBlocoTabuleiro();
        
        if(qtdFinal >= 4)
        {
            permitirValoresSelecionados(tamanho, linha, coluna, qtdFinal, qtdCorreta);            
            posicionarVetoresCorretamente();
        }
        
        else
        {
            podeMontar = false;
        }
    }
    
    private void preencherBlocoTabuleiro()
    {
        int tipo = 0;
        
        for(int i = 0; i < 4; i++)
        {
            icons = new ImageIcon[4];
            tipo = i + 1;
            
            switch(tipo)
            {
                case 1:
                    icons = tabuleiroJogador.getPorta();
                    break;
                case 2:
                    icons = tabuleiroJogador.getNavio();
                    break;
                case 3:
                    icons = tabuleiroJogador.getSubmarino();
                    break;
                case 4:
                    icons = tabuleiroJogador.getCaca();
                    break;
            }
            
            for(int v = 0; v < tamanho[i]; v++)
            {
                tabuleiroJogador.setBlocoTabuleiro(linha[i], coluna[i] + v, tipo);
                tabuleiroJogador.getBlocoTabuleiroTela(linha[i], coluna[i] + v).setIcon(icons[v]);
            }
        }
    }
    
    private void posicionarVetoresCorretamente()
    {
        int[][] trocar = new int[3][4];
        int cont = 0;

        for(int i = 0; i < qtdFinal; i++)
        {
            switch(tamanho[i])
            {
                case 4:
                    trocar[0][0] = tamanho[i];
                    trocar[1][0] = linha[i];
                    trocar[2][0] = coluna[i];
                    break;

                case 3:
                    trocar[0][1] = tamanho[i];
                    trocar[1][1] = linha[i];
                    trocar[2][1] = coluna[i];
                    break;

                case 2:
                    if(cont == 0)
                    {
                        trocar[0][2] = tamanho[i];
                        trocar[1][2] = linha[i];
                        trocar[2][2] = coluna[i];
                        cont++;
                    }
                    else
                    {
                        trocar[0][3] = tamanho[i];
                        trocar[1][3] = linha[i];
                        trocar[2][3] = coluna[i];
                    }
                    break;
            }
        }
        
        permitirValoresSelecionados(trocar[0], trocar[1], trocar[2], qtdFinal, qtdFinal);
    }
    
    private void simularBlocoTabuleiro()
    {
        Tabuleiro jogador = new TabuleiroJogador();
        boolean podeLugar;
        boolean naoSobreposto;
        boolean podeEmbarcar;
        int tipo;
        
        for(int i = 0; i < qtdCorreta; i++)
        {
            podeLugar = true;
        
            if(coluna[i] + tamanho[i] > 10)
            {
               podeLugar = false;
               negativarVetores(i);
            }

            if(podeLugar)
            {
                tipo = 0;
                podeEmbarcar = true;

                switch(tamanho[i])
                {
                    case 2:
                        if(verificarDisponibilidadeVeiculo(jogador, 3))
                        {
                            tipo = 3;
                        }

                        else
                        {
                            tipo = 4;
                        }
                        
                        podeEmbarcar = verificarDisponibilidadeVeiculo(jogador, 3) || verificarDisponibilidadeVeiculo(jogador, 4);
                        break;

                    case 3:
                        tipo = 2; 
                        podeEmbarcar = verificarDisponibilidadeVeiculo(jogador, tipo);
                        break;

                    case 4:
                        tipo = 1;
                        podeEmbarcar = verificarDisponibilidadeVeiculo(jogador, tipo);
                        break;
                }

                if(podeEmbarcar)
                {      
                    naoSobreposto = verificarSobreposicao(jogador, tamanho[i], linha[i], coluna[i]);

                    if(naoSobreposto)
                    {
                        for(int v = 0; v < tamanho[i]; v++)
                        {
                            jogador.setBlocoTabuleiro(linha[i], coluna[i] + v, tipo);
                        }
                            
                        qtdFinal++;
                    }
                    else
                    {
                        negativarVetores(i);
                    }
                }
                else
                {
                    negativarVetores(i);
                }
            }
        }
    }
    
    private void receberValoresArquivo()
    {
        for(int i = 0, contEspaco = 0, contColuna = 0, contLinha = 0, contTamanho = 0; i < texto.length(); i++)
        {
            tamanho[i] = -1;
            coluna[i] = -1;
            linha[i] = -1;

            if(texto.charAt(i) == ' ')
            {
                contEspaco = 1;
            }

            if(texto.charAt(i) == '\n')
            {
                contEspaco = 0;
                contColuna = 0;
                contLinha = 0;
                contTamanho = 0;
            }
            
            if(contEspaco == 1 && contColuna == 0)
            {
                if(texto.charAt(i) >= 'a' && texto.charAt(i) <= 'z')
                {
                    linha[qtdValida] = texto.charAt(i) - 'a';

                    if(contLinha == 1)
                    {
                        linha[qtdValida] = -1;
                    }
                }

                if(texto.charAt(i) >= 'A' && texto.charAt(i) <= 'Z')
                {
                    linha[qtdValida] = texto.charAt(i) - 'A';

                    if(contLinha == 1)
                    {
                        linha[qtdValida] = -1;
                    }
                }
            }
            
            if(texto.charAt(i) >= '0' && texto.charAt(i) <= '9')
            {
                if(contEspaco == 0)
                {
                    tamanho[qtdValida] = Character.getNumericValue(texto.charAt(i));
                    
                    if(contTamanho == 1)
                    {
                        tamanho[qtdValida] = -1;
                    }
                }

                if(contEspaco == 1 && contColuna == 0)
                {
                    coluna[qtdValida] = Character.getNumericValue(texto.charAt(i));
                    qtdValida++;
                    contColuna++;
                }
                else if(contEspaco == 1 && contColuna > 0)
                {
                    coluna[qtdValida - 1] *= 10;
                    coluna[qtdValida - 1] += Character.getNumericValue(texto.charAt(i));
                }
            }
        }

        for(int j = 0; j < qtdValida; j++)
        {
            coluna[j] --;
        }
    }
    
    private void permitirValoresSelecionados(int[] tamanho, int[] linha, int[] coluna, int tamDepois, int tamAntes)
    {
        this.linha = new int[tamDepois];
        this.coluna = new int[tamDepois];
        this.tamanho = new int[tamDepois];
        
        for(int i = 0, j = 0; i < tamAntes; i++)
        {   
            if(linha[i] != -1 && coluna[i] != -1 && tamanho[i] != -1)
            {
                this.linha[j] = linha[i];
                this.coluna[j] = coluna[i];
                this.tamanho[j] = tamanho[i];
                j++;
            }
        }
    }
    
    private void ignorarValoresIncorretos()
    {   
        for(int j = 0; j < qtdValida; j++)
        {
            if((linha[j] > 9) || (coluna[j] > 9) || (tamanho[j] != 2 && tamanho[j] != 3 && tamanho[j] != 4))
            {
                negativarVetores(j);
            }
        
            if(linha[j] != -1 && coluna[j] != -1 && tamanho[j] != -1)
            {
                qtdCorreta++;
            }
        }
    }
    
    private boolean verificarDisponibilidadeVeiculo(Tabuleiro jogador, int tipo)
    {
        for(int i = 0; i < 10; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                if(jogador.getBlocoTabuleiro(i, j) == tipo)
                {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    private boolean verificarSobreposicao(Tabuleiro jogador, int tamanho, int linha, int coluna)
    {
        for(int v = 0; v <= tamanho - 1; v++)
        {
            if(!(jogador.getBlocoTabuleiro(linha, coluna + v) == 0))
            {
                return false;
            }
        }
        
        return true;
    }
    
    private int abrirDiretorio(JFileChooser procurarArquivo)
    {
        procurarArquivo.setDialogType(JFileChooser.OPEN_DIALOG);
        procurarArquivo.setDialogTitle("Abrir Arquivo"); 
        procurarArquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
        procurarArquivo.setAcceptAllFileFilterUsed(false);
        procurarArquivo.setFileFilter(filtro);
        
        return procurarArquivo.showOpenDialog(null);
    }
    
    private void negativarVetores(int i)
    {
        tamanho[i] = -1;
        linha[i] = -1;
        coluna[i] = -1;
    }
    
    private String LerArquivo(String caminho)
    {
        String texto = "";
        
        try
        {
            FileReader arq = new FileReader(caminho);
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = "";
            
            try
            {
              linha = lerArq.readLine();
              
              while(linha != null)
              {
                  texto += linha+"\n";
                  linha = lerArq.readLine();
              }
              
            }
            catch(IOException ex)
            {
                texto = "Erro: Nao foi possivel ler o arquivo";
            }
        }
        catch(FileNotFoundException ex)
        {
            texto = "Erro: Arquivo nao encontrado";
        }
        
        if(texto.contains("Erro"))
        {
            return " ";
        }
        else
        {
            return texto;
        }
    }

    public boolean isPodeMontar(){
        return podeMontar;
    }
    
    public String getNome() {
        return nome;
    }
}
