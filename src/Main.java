
import dao.FileTaskDAO;
import javax.swing.SwingUtilities;
import service.TaskService;
import ui.MainFrame;

public class Main {
    public static void main(String[] args) {
        FileTaskDAO dao = new FileTaskDAO("tasks.csv");
        TaskService service = new TaskService(dao);
        
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame(service);
            frame.setVisible(true);
        });
    }
}
