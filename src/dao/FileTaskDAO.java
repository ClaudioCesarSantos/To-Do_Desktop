package dao;

import java.io.*;
import java.util.*;
import model.Task;

public class FileTaskDAO implements TaskDAO {
    private String filename;
    
    public FileTaskDAO(String filename) {
        this.filename = filename;
    }
    
    @Override
    public List<Task> loadAll() {
        List<Task> tasks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0]);
                    String desc = parts[1];
                    boolean completed = Boolean.parseBoolean(parts[2]);
                    tasks.add(new Task(id, desc, completed));
                }
            }
        } catch (IOException e) {
            System.out.println("Arquivo não encontrado, criando novo.");
        }
        return tasks;
    }
    
    @Override
    public void saveAll(List<Task> tasks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Task t : tasks) {
                String line = t.getId() + ";" + t.getDescription() + ";" + t.isCompleted();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar tarefas: " + e.getMessage());
        }
    }
}
