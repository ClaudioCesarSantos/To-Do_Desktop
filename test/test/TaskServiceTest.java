package test;

import model.Task;
import dao.TaskDAO;
import service.TaskService;
import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import org.junit.Test;

public class TaskServiceTest {
    
    @Test
    public void testAddTask() {
        TaskDAO mockDAO = new TaskDAO() {
            private List<Task> tasks = new ArrayList<>();
            
            @Override
            public List<Task> loadAll(){
                return tasks;
            }
            
            @Override
            public void saveAll(List<Task> tasks) {
                this.tasks = new ArrayList<>(tasks);
            }
        };
        
        TaskService service = new TaskService(mockDAO);
        service.addTask("Testar JUnit");
        
        List<Task> result = service.getTasks();
        assertEquals(1, result.size());
        assertEquals("Testar JUnit", result.get(0).getDescription());
        assertFalse(result.get(0).isCompleted());
    }
}
