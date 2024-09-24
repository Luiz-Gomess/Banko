package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import modelo.Conta;
import regras_negocio.Fachada;

public class TelaConta {
    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JTextField textFieldCpf;
    private JTextField textFieldLimite; // Campo adicional para limite
    private JLabel labelStatus;
    private JButton buttonCriar;
    private JButton buttonApagar;
    private JButton buttonAdicionarCotitular;
    private JButton buttonRemoverCotitular;

    public TelaConta() {
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JDialog();
        frame.setModal(true);
        frame.setTitle("Gerenciamento de Contas");
        frame.setBounds(100, 100, 912, 451);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        // Configurando o background e as bordas
        frame.getContentPane().setBackground(new Color(230, 230, 250)); // Cor leve

        scrollPane = new JScrollPane();
        scrollPane.setBounds(26, 42, 844, 120);
        frame.getContentPane().add(scrollPane);

        table = new JTable();
        table.setGridColor(Color.BLACK);
        table.setBackground(Color.WHITE);
        table.setFillsViewportHeight(true);
        table.setRowSelectionAllowed(true);
        table.setFont(new Font("Tahoma", Font.PLAIN, 12));
        scrollPane.setViewportView(table);
        table.setBorder(new LineBorder(new Color(0, 0, 0)));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Labels and Text Fields
        JLabel labelCpf = new JLabel("CPF:");
        labelCpf.setFont(new Font("Dialog", Font.BOLD, 12));
        labelCpf.setBounds(26, 170, 71, 14);
        frame.getContentPane().add(labelCpf);

        textFieldCpf = new JTextField();
        textFieldCpf.setFont(new Font("Dialog", Font.PLAIN, 12));
        textFieldCpf.setBounds(105, 170, 200, 20);
        frame.getContentPane().add(textFieldCpf);
        
        JLabel labelLimite = new JLabel("Limite (Conta especial):");
        labelLimite.setFont(new Font("Dialog", Font.BOLD, 12));
        labelLimite.setBounds(26, 200, 150, 14);
        frame.getContentPane().add(labelLimite);

        textFieldLimite = new JTextField();
        textFieldLimite.setFont(new Font("Dialog", Font.PLAIN, 12));
        textFieldLimite.setBounds(170, 200, 135, 20);
        frame.getContentPane().add(textFieldLimite);

        // Buttons
        buttonCriar = new JButton("Criar Conta");
        buttonCriar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                criarConta();
            }
        });
        buttonCriar.setFont(new Font("Tahoma", Font.BOLD, 12));
        buttonCriar.setBounds(350, 170, 130, 23);
        buttonCriar.setBackground(new Color(144, 238, 144)); // Verde claro
        frame.getContentPane().add(buttonCriar);

        buttonApagar = new JButton("Apagar Conta");
        buttonApagar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                apagarConta();
            }
        });
        buttonApagar.setFont(new Font("Tahoma", Font.BOLD, 12));
        buttonApagar.setBounds(350, 200, 130, 23);
        buttonApagar.setBackground(new Color(255, 99, 71)); // Vermelho claro
        frame.getContentPane().add(buttonApagar);

        buttonAdicionarCotitular = new JButton("Adicionar Cotitular");
        buttonAdicionarCotitular.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                adicionarCotitular();
            }
        });
        buttonAdicionarCotitular.setFont(new Font("Tahoma", Font.BOLD, 12));
        buttonAdicionarCotitular.setBounds(350, 230, 130, 23);
        buttonAdicionarCotitular.setBackground(new Color(173, 216, 230)); // Azul claro
        frame.getContentPane().add(buttonAdicionarCotitular);
        
        buttonRemoverCotitular = new JButton("Remover Cotitular");
        buttonRemoverCotitular.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removerCotitular();
            }
        });
        buttonRemoverCotitular.setFont(new Font("Tahoma", Font.BOLD, 12));
        buttonRemoverCotitular.setBounds(350, 260, 130, 23);
        buttonRemoverCotitular.setBackground(new Color(255, 140, 0)); // Laranja
        frame.getContentPane().add(buttonRemoverCotitular);

        labelStatus = new JLabel("");
        labelStatus.setForeground(Color.BLUE);
        labelStatus.setBounds(26, 300, 830, 14);
        frame.getContentPane().add(labelStatus);
        
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                listagem();
            }
        });
    }

    private void criarConta() {
        try {
            String cpf = textFieldCpf.getText();
            String limiteText = textFieldLimite.getText();
            double limite = 0;

            if (cpf.isEmpty()) {
                labelStatus.setText("Campo CPF vazio.");
                return;
            }

            if (!limiteText.isEmpty()) {
                limite = Double.parseDouble(limiteText);
                Fachada.criarContaEspecial(cpf, limite);
            } else {
                Fachada.criarConta(cpf);
            }

            labelStatus.setText("Conta criada com sucesso.");
            listagem();
        } catch (NumberFormatException e) {
            labelStatus.setText("Limite deve ser um número válido.");
        } catch (Exception e) {
            labelStatus.setText(e.getMessage());
        }
    }

    private void apagarConta() {
        try {
            if (table.getSelectedRow() >= 0) {
                int id = (Integer) table.getValueAt(table.getSelectedRow(), 0);
                Fachada.apagarConta(id);
                labelStatus.setText("Conta apagada com sucesso.");
                listagem();
            } else {
                labelStatus.setText("Selecione uma conta.");
            }
        } catch (Exception e) {
            labelStatus.setText(e.getMessage());
        }
    }

    private void adicionarCotitular() {
        try {
            int selectedRow = table.getSelectedRow();
            String cpf = textFieldCpf.getText();
            if (selectedRow >= 0) {
                if (cpf.isEmpty()) {
                    labelStatus.setText("Campo de CPF vazio.");
                    return;
                }
                int id = (Integer) table.getValueAt(selectedRow, 0);
                Fachada.inserirCorrentistaConta(cpf, id);
                labelStatus.setText("Cotitular adicionado.");
                listagem();
            } else {
                labelStatus.setText("Selecione uma conta.");
            }
        } catch (Exception e) {
            labelStatus.setText(e.getMessage());
        }
    }

    private void removerCotitular() {
        try {
            int selectedRow = table.getSelectedRow();
            String cpf = textFieldCpf.getText();
            if (selectedRow >= 0) {
                if (cpf.isEmpty()) {
                    labelStatus.setText("Campo de CPF vazio.");
                    return;
                }
                int id = (Integer) table.getValueAt(selectedRow, 0);
                Fachada.removerCorrentistaConta(cpf, id);
                labelStatus.setText("Cotitular removido.");
                listagem();
            } else {
                labelStatus.setText("Selecione uma conta.");
            }
        } catch (Exception e) {
            labelStatus.setText(e.getMessage());
        }
    }

    public void listagem() {
        try {
            List<Conta> lista = Fachada.listarContas();
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Titular");
            model.addColumn("Saldo");
            model.addColumn("Data");

            for (Conta c : lista) {
                model.addRow(new Object[]{c.getId(), c.getTitular(), c.getSaldo(), c.getData()});
            }

            table.setModel(model); 
        } catch (Exception e) {
            labelStatus.setText(e.getMessage());
        }
    }
}
