package ui;

import model.Task;
import service.TaskService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {
    private TaskService service;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField inputField;
    
    public MainFrame(TaskService service) {
        this.service = service;
        setTitle("To-Do List");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
        loadTasks();
    }
    
    private void initComponents() {
        JPanel inputPanel = new JPanel();
        inputField = new JTextField(25);
        JButton addButton = new JButton("Adicionar");
        
        addButton.addActionListener(e -> {
            String desc = inputField.getText().trim();
            if (!desc.isEmpty()) {
                service.addTask(desc);
                inputField.setText("");
                loadTasks();
            }
        });
        
        inputPanel.add(inputField);
        inputPanel.add(addButton);
        add(inputPanel, BorderLayout.NORTH);
        
        tableModel = new DefaultTableModel(new Object[]{"ID", "Descrição", "Concluída"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        JButton toggleButton = new JButton("Marcar/Desmarcar");
        JButton removeButton = new JButton("Remover");
        
        toggleButton.addActionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected >= 0) {
                int id = (int) tableModel.getValueAt(selected, 0);
                service.toggleTask(id);
                loadTasks();
            }
        });
        
        removeButton.addActionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected >= 0) {
                int id = (int) tableModel.getValueAt(selected, 0);
                service.removeTask(id);
                loadTasks();
            }
        });
        
        buttonPanel.add(toggleButton);
        buttonPanel.add(removeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void loadTasks() {
        tableModel.setRowCount(0);
        List<Task> tasks = service.getTasks();
        for (Task t : tasks) {
            tableModel.addRow(new Object[]{
                t.getId(),
                t.getDescription(),
                t.isCompleted() ? "Sim" : "Não"
            });
        }
    }
}
