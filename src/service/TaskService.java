package service;

import dao.TaskDAO;
import java.util.*;
import model.Task;

public class TaskService {
    private List<Task> tasks;
    private TaskDAO dao;

    public TaskService(TaskDAO dao) {
        this.dao = dao;
        this.tasks = dao.loadAll();
    }
    
    public void addTask(String description) {
        int id = tasks.size() + 1;
        Task newTask = new Task(id, description);
        tasks.add(newTask);
        dao.saveAll(tasks);
    }
    
    public void removeTask(int id) {
        tasks.removeIf(task -> task.getId() == id);
        dao.saveAll(tasks);
    }
    
    public void toggleTask(int id) {
        for (Task t : tasks) {
            if (t.getId() == id) {
                t.setCompleted(!t.isCompleted());
                break;
            }
        }
        
        dao.saveAll(tasks);
    }
    
    public List<Task> getTask() {
        return new ArrayList<>(tasks);
    }
    
    private int generateNextId() {
        return tasks.stream()
                .mapToInt(Task::getId)
                .max()
                .orElse(0) + 1;
    
    }
    
}
