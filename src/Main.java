package projetocalculadora;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class ProjetoCalculadora extends JFrame implements KeyListener {

    private JButton botao1, botao2, botao3, botao4, botao5, botao6, botao7, botao8,
            botao9, botao0, botaoC, botaoCE, botaoDiv, botaoMultip, botaoMenos, botaoMais,
            botaoVirgula, botaoTotal, botaoPerc, botaoBackspace;

    private JTextArea roloPapel;
    private JLabel visor, keyCodigo;
    private JScrollPane scroll;

    private double soma = 0;
    private String valor = "", operador = "", valorAnterior = "";

    private Container container;
    private GridBagLayout layout;
    private GridBagConstraints constraints;

    private JRadioButton botao2dig, botao4dig, botao6dig;
    private ButtonGroup botaoDecimais;

    private int casasDecimais = 2;

    private class trataEventosAcao implements ActionListener {

        public void actionPerformed(ActionEvent event) {

            if (event.getSource() == botaoC) teclouC();
            else if (event.getSource() == botaoCE) teclouCE();
            else if (event.getSource() == botaoPerc) teclouP();
            else if (event.getSource() == botaoVirgula) teclouVirgula();
            else if (event.getSource() == botaoBackspace) teclouBackspace();
            else if (event.getSource() == botao1) teclouNumeros("1");
            else if (event.getSource() == botao2) teclouNumeros("2");
            else if (event.getSource() == botao3) teclouNumeros("3");
            else if (event.getSource() == botao4) teclouNumeros("4");
            else if (event.getSource() == botao5) teclouNumeros("5");
            else if (event.getSource() == botao6) teclouNumeros("6");
            else if (event.getSource() == botao7) teclouNumeros("7");
            else if (event.getSource() == botao8) teclouNumeros("8");
            else if (event.getSource() == botao9) teclouNumeros("9");
            else if (event.getSource() == botao0) teclouNumeros("0");
            else if (event.getSource() == botaoMais) teclouMais();
            else if (event.getSource() == botaoMenos) teclouMenos();
            else if (event.getSource() == botaoDiv) teclouDiv();
            else if (event.getSource() == botaoMultip) teclouMultip();
            else if (event.getSource() == botaoTotal) teclouEnter();
        }
    }

    private class trataEventosFocus implements FocusListener {

        public void focusGained(FocusEvent event) {

        }

        public void focusLost(FocusEvent event) {

        }
    }

    private class trataEventosRadio implements ItemListener {

        public void itemStateChanged(ItemEvent event) {

            if (event.getSource() == botao2dig) {
                casasDecimais = 2;
            }

            if (event.getSource() == botao4dig) {
                casasDecimais = 4;
            }

            if (event.getSource() == botao6dig) {
                casasDecimais = 6;
            }
        }
    }

    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == e.VK_ESCAPE) {
            int selectedOption = JOptionPane.showConfirmDialog(this, "Deseja Sair mesmo???");

            if (selectedOption == JOptionPane.YES_NO_OPTION) {
                dispose();
                System.exit(0);
            }
        }

        if (e.getKeyCode() == 67) teclouC();
        if (e.getKeyCode() == 69) teclouCE();
        if (e.getKeyCode() == 8) teclouBackspace();
        if (e.getKeyCode() == 110) teclouVirgula();

        if ((e.getKeyCode() >= e.VK_0 && e.getKeyCode() <= e.VK_9) ||
                (e.getKeyCode() >= e.VK_NUMPAD0 && e.getKeyCode() <= e.VK_NUMPAD9)) {
            teclouNumeros("" + e.getKeyChar());
        }

        if ((e.getKeyCode() == 61) || (e.getKeyCode() == 107)) teclouMais();
        if ((e.getKeyCode() == 45) || (e.getKeyCode() == 109)) teclouMenos();
        if ((e.getKeyCode() == 59) || (e.getKeyCode() == 111)) teclouDiv();
        if (e.getKeyCode() == 106) teclouMultip();
        if (e.getKeyCode() == 80) teclouP();
        if (e.getKeyCode() == 10) teclouEnter();
    }

    public void keyReleased(KeyEvent e) {

    }

    public void keyTyped(KeyEvent e) {

    }

    public void teclouC() {

        valor = "0";
        visor.setText(formatStrNumerica(valor, casasDecimais));
    }

    public void teclouCE() {

        valor = "0";
        soma = 0;
        visor.setText(formatStrNumerica(valor, casasDecimais));
        roloPapel.append(alinhaStringDir("--------------------" + "\n"));
        roloPapel.append(alinhaStringDir("" + "\n"));
    }

    public void teclouP() {

        roloPapel.append(alinhaStringDir(formatStrNumerica(valor, casasDecimais) + "%" + "\n"));
        roloPapel.append(alinhaStringDir("--------------------" + "\n"));

        if (operador == "*")
            soma = soma * (Double.parseDouble(valor) / 100);
        else if (operador == "/")
            soma = soma / (Double.parseDouble(valor) / 100);

        roloPapel.append(alinhaStringDir(formatStrNumerica("" + soma, casasDecimais) + "\n"));
        roloPapel.append(alinhaStringDir("" + "\n"));

        visor.setText(formatStrNumerica("" + soma, casasDecimais));
        valorAnterior = "" + soma;
        soma = 0;
        valor = "";
    }

    public void teclouVirgula() {

        if (valor.lastIndexOf('.') == -1)
            valor = valor + ".";

        visor.setText(valor);
    }

    public void teclouBackspace() {

        if (valor.length() <= 1)
            valor = "0";
        else
            valor = valor.substring(0, valor.length() - 1);

        visor.setText(valor);
    }

    public void teclouNumeros(String s) {

        if (valor.length() <= 14) {

            if (valor == "0")
                valor = "";

            valor = valor + s;
            visor.setText(valor);
        }
    }

    public void teclouMais() {

        if (valor == "")
            valor = valorAnterior;

        roloPapel.append(alinhaStringDir(formatStrNumerica(valor, casasDecimais) + "+" + "\n"));

        soma = soma + Double.parseDouble(valor);
        visor.setText(formatStrNumerica("" + soma, casasDecimais));

        valorAnterior = valor;
        valor = "";
        operador = "+";
    }

    public void teclouMenos() {

        if (valor == "")
            valor = valorAnterior;

        roloPapel.append(alinhaStringDir(formatStrNumerica(valor, casasDecimais) + "-" + "\n"));

        soma = soma - Double.parseDouble(valor);
        visor.setText(formatStrNumerica("" + soma, casasDecimais));

        valorAnterior = valor;
        valor = "";
        operador = "-";
    }

    public void teclouDiv() {

        if (valor == "")
            valor = valorAnterior;

        roloPapel.append(alinhaStringDir("=======================" + "\n"));
        roloPapel.append((alinhaStringDir(""+ "\n")));
        roloPapel.append((alinhaStringDir(formatStrNumerica(valor, casasDecimais) + "/" +  "\n")));
        soma = Double.parseDouble(valor);
        visor.setText(formatStrNumerica("" + soma, casasDecimais));
        valor = "";
        operador = "/";
    }

    public void teclaMultip(){
        if (valor == "") valorAnterior;

        roloPapel.append(alinhaStringDir("----------------------------" + "\n"));
        roloPapel.append(alinhaStringDir("" + "\n"));
        roloPapel.append(alinhaStringDir(formatStrNumerica(valor, casasDecimais) + "*"
                + "\n"));

        soma = Double.parseDouble(valor);
        visor.setText(formatStrNumerica("" + soma, casasDecimais));
        valor = "";
        operador = "*";
    }

    public void teclaEnter(){
        if ((operador == "+") || (operador == "-")){
            roloPapel.append(alinhaStringDir("----------------------------" + "\n"));
            roloPapel.append(alinhaStringDir(formatStrNumerica("" +
                    soma, casasDecimais) + "+" + "\n"));
        }

        if ((operador == "/") && (soma != 0)){
            roloPapel.append(alinhaStringDir(formatStrNumerica(valor, casasDecimais)
                    + " " + "\n"));

            roloPapel.append(alinhaStringDir("----------------------------" + "\n"));
            soma = soma / Double.parseDouble(valor);
            roloPapel.append(alinhaStringDir(formatStrNumerica("" +
                    soma, casasDecimais) + "+" + "\n"));
        }

        if (operador == "*"){
            roloPapel.append(alinhaStringDir(formatStrNumerica(valor, casasDecimais)
                    + "*" + "\n"));

            roloPapel.append(alinhaStringDir("----------------------------" + "\n"));
            soma = soma * Double.parseDouble(valor);
            roloPapel.append(alinhaStringDir(formatStrNumerica("" +
                    soma, casasDecimais) + "+" + "\n"));
        }

        roloPapel.append(alinhaStringDir(" " + "\n"));
        visor.setText(formatStrNumerica("" + soma, casasDecimais));
        valorAnterior = "" + soma;
        soma = 0;
        valor = "";
    }

    public String alinhaStringDir(String s){
        String alinhador = "";
        int i;
        for (i = 0; i < (36 - s.length()); i++){
            alinhador = alinhador + " ";
        }
        return alinhador + s;
    }

    public String formatStrNumerica(String s, int dig){
        DecimalFormat decimal = new DecimalFormat();
        decimal.setMinimumFractionDigits(dig);
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setGroupingSeparator('.');
        simbolos.setDecimalSeparator(',');
        decimal.setDecimalFormatSymbols(simbolos);
    }

}


