package appswing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import regras_negocio.Fachada;

public class TelaCaixa extends JFrame {
    private JTextField tfCpf, tfSenha, tfValor, tfIdConta, tfIdContaDestino;
    private JButton btnCreditar, btnDebitar, btnTransferir;
    private JLabel lblStatus; // Novo JLabel para status das mensagens

    public TelaCaixa() {
        setTitle("Tela Caixa");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Espaçamento entre os componentes

        // Criando os componentes
        JLabel lblCpf = new JLabel("CPF:");
        tfCpf = new JTextField(15);

        JLabel lblSenha = new JLabel("Senha:");
        tfSenha = new JPasswordField(15);

        JLabel lblIdConta = new JLabel("ID da Conta:");
        tfIdConta = new JTextField(15);

        JLabel lblValor = new JLabel("Valor:");
        tfValor = new JTextField(15);

        JLabel lblIdContaDestino = new JLabel("ID Conta Destino:");
        tfIdContaDestino = new JTextField(15);

        btnCreditar = new JButton("Creditar");
        btnDebitar = new JButton("Debitar");
        btnTransferir = new JButton("Transferir");

        // Adicionando componentes ao painel
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(lblCpf, gbc);
        gbc.gridx = 1;
        panel.add(tfCpf, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(lblSenha, gbc);
        gbc.gridx = 1;
        panel.add(tfSenha, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(lblIdConta, gbc);
        gbc.gridx = 1;
        panel.add(tfIdConta, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(lblValor, gbc);
        gbc.gridx = 1;
        panel.add(tfValor, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(lblIdContaDestino, gbc);
        gbc.gridx = 1;
        panel.add(tfIdContaDestino, gbc);

        // Configurando os botões
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(btnCreditar);
        buttonPanel.add(btnDebitar);
        buttonPanel.add(btnTransferir);

        // JLabel para mensagens de status
        lblStatus = new JLabel();
        lblStatus.setHorizontalAlignment(SwingConstants.CENTER); // Centraliza o texto
        lblStatus.setPreferredSize(new Dimension(0, 30)); // Define uma altura fixa para o JLabel

        // Adicionando o painel de componentes, os botões e o JLabel ao frame
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(lblStatus, BorderLayout.NORTH); // Adiciona o JLabel na parte inferior

        // Configurando os botões
        btnCreditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double valor = Double.parseDouble(tfValor.getText());
                    String cpf = tfCpf.getText();
                    String senha = tfSenha.getText();
                    int idConta = Integer.parseInt(tfIdConta.getText());
                    Fachada.creditarValor(idConta, cpf, senha, valor);
                    lblStatus.setText("Valor creditado com sucesso!");
                } catch (Exception ex) {
                    lblStatus.setText("Erro: " + ex.getMessage());
                }
            }
        });

        btnDebitar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double valor = Double.parseDouble(tfValor.getText());
                    String cpf = tfCpf.getText();
                    String senha = tfSenha.getText();
                    int idConta = Integer.parseInt(tfIdConta.getText());
                    Fachada.debitarValor(idConta, cpf, senha, valor);
                    lblStatus.setText("Valor debitado com sucesso!");
                } catch (Exception ex) {
                    lblStatus.setText("Erro: " + ex.getMessage());
                }
            }
        });

        btnTransferir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double valor = Double.parseDouble(tfValor.getText());
                    String cpf = tfCpf.getText();
                    String senha = tfSenha.getText();
                    int idConta = Integer.parseInt(tfIdConta.getText());
                    int idContaDestino = Integer.parseInt(tfIdContaDestino.getText());
                    Fachada.tranferirValor(idConta, cpf, senha, valor, idContaDestino);
                    lblStatus.setText("Valor transferido com sucesso!");
                } catch (Exception ex) {
                    lblStatus.setText("Erro: " + ex.getMessage());
                }
            }
        });

        // Adicionando um fundo e cor aos botões
        panel.setBackground(Color.LIGHT_GRAY);
        buttonPanel.setBackground(Color.DARK_GRAY);
        btnCreditar.setBackground(Color.GREEN);
        btnDebitar.setBackground(Color.RED);
        btnTransferir.setBackground(Color.BLUE);
        btnCreditar.setForeground(Color.WHITE);
        btnDebitar.setForeground(Color.WHITE);
        btnTransferir.setForeground(Color.WHITE);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaCaixa());
    }
}
