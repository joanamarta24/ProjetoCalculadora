import javax.swing.*;

import java.awt.*;

import static java.awt.AWTEventMulticaster.add;
import static jdk.internal.jimage.decompressor.CompressedResourceHeader.getSize;botaoDiv.addActionListener(handlerAcao);

// Configuração das restrições do layout (GridBagConstraints)
constraints.anchor = GridBagConstraints.WEST;
constraints.weightx = 0;
constraints.weightx = 1; // Sobrescreve o anterior
constraints.fill = GridBagConstraints.BOTH;

// Adicionando os componentes à grade (Componente, linha, coluna, largura, altura)
addComponent(scroll, 0, 0, 5, 1);
addComponent(visor, 1, 0, 5, 1);

addComponent(botao2dig, 2, 0, 1, 1);
addComponent(botao4dig, 2, 1, 1, 1);
addComponent(botao6dig, 2, 2, 3, 1);

constraints.weightx = 0;
constraints.weightx = 5;

// Botões de controle e operações
addComponent(botaoPerc, 3, 0, 1, 1);
addComponent(botaoCE, 3, 4, 1, 1);
addComponent(botaoC, 4, 4, 1, 1);
addComponent(botaoBackspace, 5, 4, 1, 1);

addComponent(botaoDiv, 3, 1, 1, 1);
addComponent(botaoMultip, 3, 2, 1, 1);
addComponent(botaoMenos, 3, 3, 1, 1);

// Números e operadores
addComponent(botao7, 4, 0, 1, 1);
addComponent(botao8, 4, 1, 1, 1);
addComponent(botao9, 4, 2, 1, 1);
addComponent(botaoMais, 4, 3, 1, 1);

addComponent(botao4, 5, 0, 1, 1);
addComponent(botao5, 5, 1, 1, 1);
addComponent(botao6, 5, 2, 1, 1);
addComponent(botaoVirgula, 5, 3, 1, 1);

addComponent(botao1, 6, 0, 1, 1);
addComponent(botao2, 6, 1, 1, 1);
addComponent(botao3, 6, 2, 1, 1);
addComponent(botao0, 6, 3, 1, 1);
addComponent(botaoTotal, 6, 4, 1, 1);

// Configuração final dos eventos de ação
trataEventosAcao handlerAcao = new trataEventosAcao();
        botaoC.addActionListener(handlerAcao);
        botaoCE.addActionListener(handlerAcao);
        botaoPerc.addActionListener(handlerAcao);



        botaoMultip.addActionListener(handlerAcao);
        botaoMenos.addActionListener(handlerAcao);
        botaoMais.addActionListener(handlerAcao);
        botaoTotal.addActionListener(handlerAcao);
        botaoVirgula.addActionListener(handlerAcao);
        botao1.addActionListener(handlerAcao);
        botao2.addActionListener(handlerAcao);
        botao3.addActionListener(handlerAcao);
        botao4.addActionListener(handlerAcao);
        botao5.addActionListener(handlerAcao);
        botao6.addActionListener(handlerAcao);
        botao7.addActionListener(handlerAcao);
        botao8.addActionListener(handlerAcao);
        botao9.addActionListener(handlerAcao);
        botao0.addActionListener(handlerAcao);
        botaoBackspace.addActionListener(handlerAcao);
private void addComponent(Component c, int row, int col, int width, int height) {
        constraints.gridy = row;
        constraints.gridx = col;
        constraints.gridwidth = width;
        constraints.gridheight = height;
        layout.setConstraints(c, constraints);
        add(c);
}

// Instanciação dos handlers de eventos
trataEventosFocus handlerFocus = new trataEventosFocus();
trataEventosRadio handlerRadio = new trataEventosRadio();

// Configuração de ItemListeners e KeyListeners
        botao2dig.addItemListener(handlerRadio);
        botao4dig.addItemListener(handlerRadio);
        botao6dig.addItemListener(handlerRadio);
        visor.addKeyListener(this);

// Configurações de dimensionamento e posicionamento da janela
setSize(285, 480);
Dimension resVideo = Toolkit.getDefaultToolkit().getScreenSize();
Dimension tamForm = getSize();

// Centraliza a janela na tela
setLocation(
            (resVideo.width - tamForm.width) / 2,
        (resVideo.height - tamForm.height) / 2
        );

setResizable(false);
setVisible(true);
    }

public static void main(String[] args) {
    // Inicialização do projeto
    ProjetoCalculadora app = new ProjetoCalculadora();
    app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}
