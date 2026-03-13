import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class ProjetoCalculadoraCientifica extends JFrame implements KeyListener {

    private JButton botaoC, botaoCE, botaoDiv, botaoMultip, botaoMenos, botaoMais,
            botaoVirgula, botaoTotal, botaoPerc, botaoBackspace;

    // Novos botões científicos
    private JButton botaoRaiz, botaoPotencia, botaoSin, botaoCos, botaoLog, botaoPI;

    private final Map<String, JButton> botoesNumericos = new HashMap<>();
    private JTextArea roloPapel;
    private JLabel visor;
    private JScrollPane scroll;
    private JRadioButton botao2dig, botao4dig, botao6dig;

    private double soma = 0;
    private String valor = "0", operador = "";
    private boolean novaOperacao = true;
    private int casasDecimais = 2;

    public ProjetoCalculadoraCientifica() {
        super("Calculadora Científica 7.0");
        configurarJanela();
        inicializarComponentes();
        configurarLayout();
        setVisible(true);
    }

    private void configurarJanela() {
        setSize(400, 600); // Aumentei a largura para caber as funções
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridBagLayout());
    }

    private void inicializarComponentes() {
        for (int i = 0; i <= 9; i++) {
            botoesNumericos.put(String.valueOf(i), new JButton(String.valueOf(i)));
        }

        // Botões padrão
        botaoC = new JButton("C");
        botaoCE = new JButton("CE");
        botaoDiv = new JButton("/");
        botaoMultip = new JButton("*");
        botaoMenos = new JButton("-");
        botaoMais = new JButton("+");
        botaoVirgula = new JButton(",");
        botaoTotal = new JButton("=");
        botaoPerc = new JButton("%");
        botaoBackspace = new JButton("<-");

        // Botões Científicos
        botaoRaiz = new JButton("√");
        botaoPotencia = new JButton("x²");
        botaoSin = new JButton("sin");
        botaoCos = new JButton("cos");
        botaoLog = new JButton("log");
        botaoPI = new JButton("π");

        visor = new JLabel("0,00");
        visor.setHorizontalAlignment(SwingConstants.RIGHT);
        visor.setFont(new Font("Monospaced", Font.BOLD, 25));
        visor.setForeground(new Color(0, 100, 0)); // Verde escuro para parecer científica
        visor.setBorder(BorderFactory.createLoweredBevelBorder());

        roloPapel = new JTextArea(8, 20);
        roloPapel.setEditable(false);
        scroll = new JScrollPane(roloPapel);

        botao2dig = new JRadioButton("2", true);
        botao4dig = new JRadioButton("4");
        botao6dig = new JRadioButton("6");
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(botao2dig); grupo.add(botao4dig); grupo.add(botao6dig);

        // Registro de eventos
        trataEventosAcao handler = new trataEventosAcao();
        botoesNumericos.values().forEach(b -> b.addActionListener(handler));

        JButton[] todosBotoes = {botaoC, botaoCE, botaoDiv, botaoMultip, botaoMenos, botaoMais,
                botaoVirgula, botaoTotal, botaoPerc, botaoBackspace,
                botaoRaiz, botaoPotencia, botaoSin, botaoCos, botaoLog, botaoPI};

        for (JButton b : todosBotoes) b.addActionListener(handler);

        this.setFocusable(true);
        this.addKeyListener(this);
    }

    private void configurarLayout() {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(3, 3, 3, 3);
        c.weightx = 1.0;

        c.gridwidth = 5; addComponent(scroll, 0, 0, c);
        c.gridwidth = 5; addComponent(visor, 1, 0, c);

        // Linha de Funções Científicas 1
        c.gridwidth = 1;
        addComponent(botaoSin, 2, 0, c);
        addComponent(botaoCos, 2, 1, c);
        addComponent(botaoLog, 2, 2, c);
        addComponent(botaoPI, 2, 3, c);
        addComponent(botaoPerc, 2, 4, c);

        // Linha de Funções Científicas 2
        addComponent(botaoRaiz, 3, 0, c);
        addComponent(botaoPotencia, 3, 1, c);
        addComponent(botaoDiv, 3, 2, c);
        addComponent(botaoMultip, 3, 3, c);
        addComponent(botaoCE, 3, 4, c);

        // Teclado Numérico e Operações Básicas
        addComponent(botoesNumericos.get("7"), 4, 0, c);
        addComponent(botoesNumericos.get("8"), 4, 1, c);
        addComponent(botoesNumericos.get("9"), 4, 2, c);
        addComponent(botaoMenos, 4, 3, c);
        addComponent(botaoC, 4, 4, c);

        addComponent(botoesNumericos.get("4"), 5, 0, c);
        addComponent(botoesNumericos.get("5"), 5, 1, c);
        addComponent(botoesNumericos.get("6"), 5, 2, c);
        addComponent(botaoMais, 5, 3, c);
        addComponent(botaoBackspace, 5, 4, c);

        addComponent(botoesNumericos.get("1"), 6, 0, c);
        addComponent(botoesNumericos.get("2"), 6, 1, c);
        addComponent(botoesNumericos.get("3"), 6, 2, c);
        c.gridheight = 2; addComponent(botaoTotal, 6, 3, c);
        c.gridheight = 1; addComponent(botaoVirgula, 7, 2, c);

        c.gridwidth = 2; addComponent(botoesNumericos.get("0"), 7, 0, c);
    }

    private void addComponent(Component comp, int row, int col, GridBagConstraints c) {
        c.gridy = row;
        c.gridx = col;
        add(comp, c);
    }

    private void atualizarVisor() {
        try {
            double d = Double.parseDouble(valor.replace(",", "."));
            visor.setText(formatar(d));
        } catch (Exception e) { visor.setText("Erro"); }
    }

    private String formatar(double n) {
        DecimalFormat df = new DecimalFormat("#,##0", new DecimalFormatSymbols(new Locale("pt", "BR")));
        df.setMinimumFractionDigits(casasDecimais);
        df.setMaximumFractionDigits(casasDecimais);
        return df.format(n);
    }

    private class trataEventosAcao implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();
            double numAtual = Double.parseDouble(valor.replace(",", "."));

            if ("0123456789".contains(cmd)) {
                if (novaOperacao) { valor = cmd; novaOperacao = false; }
                else valor += cmd;
                atualizarVisor();
            }
            else if (cmd.equals("sin")) { calcularCientifico(Math.sin(Math.toRadians(numAtual)), "sin"); }
            else if (cmd.equals("cos")) { calcularCientifico(Math.cos(Math.toRadians(numAtual)), "cos"); }
            else if (cmd.equals("√")) { calcularCientifico(Math.sqrt(numAtual), "sqrt"); }
            else if (cmd.equals("x²")) { calcularCientifico(Math.pow(numAtual, 2), "pow2"); }
            else if (cmd.equals("log")) { calcularCientifico(Math.log10(numAtual), "log"); }
            else if (cmd.equals("π")) { valor = String.valueOf(Math.PI); atualizarVisor(); }
            else if ("+-*/".contains(cmd)) realizarOperacaoBasica(cmd);
            else if (cmd.equals("=")) finalizarCalculo();
            else if (cmd.equals("C")) limparTudo();
        }

        private void calcularCientifico(double resultado, String opNome) {
            roloPapel.append(opNome + "(" + valor + ") = " + formatar(resultado) + "\n");
            valor = String.valueOf(resultado);
            atualizarVisor();
            novaOperacao = true;
        }

        private void realizarOperacaoBasica(String proxOp) {
            soma = Double.parseDouble(valor.replace(",", "."));
            operador = proxOp;
            roloPapel.append(formatar(soma) + " " + operador + "\n");
            novaOperacao = true;
        }

        private void finalizarCalculo() {
            double val2 = Double.parseDouble(valor.replace(",", "."));
            switch (operador) {
                case "+" -> soma += val2;
                case "-" -> soma -= val2;
                case "*" -> soma *= val2;
                case "/" -> soma /= val2;
            }
            roloPapel.append(formatar(val2) + "\n----------\n" + formatar(soma) + "\n\n");
            valor = String.valueOf(soma);
            operador = "";
            atualizarVisor();
            novaOperacao = true;
        }

        private void limparTudo() {
            valor = "0"; soma = 0; operador = ""; roloPapel.setText(""); atualizarVisor();
        }
    }

    // KeyListener (Básico)
    public void keyPressed(KeyEvent e) { if (e.getKeyCode() == KeyEvent.VK_ESCAPE) System.exit(0); }
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProjetoCalculadoraCientifica::new);
    }
}