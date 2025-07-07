package dao;

import java.util.List;
import model.Task;

public interface TaskDAO {
    List<Task> loadAll();
    void saveAll(List<Task> tasks);
}
