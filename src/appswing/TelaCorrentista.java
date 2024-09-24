package appswing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import modelo.Conta;
import modelo.Correntista;
import regras_negocio.Fachada;

public class TelaCorrentista {
    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JLabel label;
    private JTextField textField_nome;
    private JTextField textField_cpf;
    private JTextField textField_senha;
    private JButton button_criar;
    private JButton button_listar;
    private JButton button_contas;

    public TelaCorrentista() {
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JDialog();
        frame.setModal(true);
        frame.setResizable(false);
        frame.setTitle("Gerenciamento de Correntistas");
        frame.setBounds(100, 100, 800, 450);
        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(Color.WHITE);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                listagem();
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Tabela
        scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(750, 150));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        frame.add(scrollPane, gbc);

        table = new JTable();
        table.setGridColor(Color.BLACK);
        table.setFocusable(false);
        table.setBackground(Color.WHITE);
        table.setFillsViewportHeight(true);
        table.setRowSelectionAllowed(true);
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        scrollPane.setViewportView(table);
        table.setBorder(new LineBorder(new Color(0, 0, 0)));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Mensagem
        label = new JLabel("");
        label.setForeground(Color.BLUE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        frame.add(label, gbc);

        // Labels e Campos de Texto
        gbc.gridwidth = 1;

        JLabel label_nome = new JLabel("Nome:");
        label_nome.setFont(new Font("Tahoma", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(label_nome, gbc);

        textField_nome = new JTextField();
        textField_nome.setFont(new Font("Dialog", Font.PLAIN, 12));
        gbc.gridx = 1;
        frame.add(textField_nome, gbc);

        JLabel label_cpf = new JLabel("CPF:");
        label_cpf.setFont(new Font("Tahoma", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(label_cpf, gbc);

        textField_cpf = new JTextField();
        textField_cpf.setFont(new Font("Dialog", Font.PLAIN, 12));
        gbc.gridx = 1;
        frame.add(textField_cpf, gbc);

        JLabel label_senha = new JLabel("Senha:");
        label_senha.setFont(new Font("Tahoma", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 4;
        frame.add(label_senha, gbc);

        textField_senha = new JTextField();
        textField_senha.setFont(new Font("Dialog", Font.PLAIN, 12));
        gbc.gridx = 1;
        frame.add(textField_senha, gbc);

        // Botões
        button_criar = new JButton("Criar");
        button_criar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (textField_nome.getText().isEmpty() || textField_cpf.getText().isEmpty()
                            || textField_senha.getText().isEmpty()) {
                        label.setText("Preencha todos os campos");
                        return;
                    }
                    String nome = textField_nome.getText();
                    String cpf = textField_cpf.getText();
                    String senha = textField_senha.getText();

                    Fachada.criarCorrentista(cpf, nome, senha);
                    label.setText("Correntista criado com sucesso!");
                    listagem();
                } catch (Exception ex) {
                    label.setText(ex.getMessage());
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 5;
        frame.add(button_criar, gbc);

        button_listar = new JButton("Listar Correntistas");
        button_listar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listagem();
            }
        });
        gbc.gridx = 1;
        frame.add(button_listar, gbc);

        button_contas = new JButton("Listar Contas");
        button_contas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    String cpfSelecionado = (String) table.getValueAt(selectedRow, 0);
                    try {
                        List<Correntista> lista = Fachada.listarCorrentista();
                        DefaultTableModel model = new DefaultTableModel();
                        model.addColumn("ID da Conta");
                        model.addColumn("Saldo");
                        
						Correntista c = Fachada.localizarCorrentista(cpfSelecionado);
                        if (c != null) {
                            List<Conta> listaConta = c.getContas();
                            for (Conta co : listaConta) {
                                model.addRow(new Object[]{co.getId(), co.getSaldo()});
                            }
                        }

                        // Atualiza a tabela para exibir as contas do correntista
                        table.setModel(model);
                        label.setText("Contas do correntista de CPF: " + cpfSelecionado);
                    } catch (Exception ex) {
                        label.setText("Erro ao listar contas: " + ex.getMessage());
                    }
                } else {
                    label.setText("Selecione um correntista da tabela.");
                }
            }
        });
        gbc.gridx = 2;
        frame.add(button_contas, gbc);
    }

    public void listagem() {
        try {
            List<Correntista> lista = Fachada.listarCorrentista();
            DefaultTableModel model = new DefaultTableModel();

            model.addColumn("CPF");
            model.addColumn("Nome");

            for (Correntista c : lista) {
                model.addRow(new Object[]{c.getCpf(), c.getNome()});
            }

            table.setModel(model);
            label.setText("Resultados: " + lista.size() + " correntista(s) encontrado(s)");
        } catch (Exception erro) {
            label.setText(erro.getMessage());
        }
    }
}
