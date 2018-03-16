package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import service.ServiceFactory;
import service.TaskService;
import util.NumberTextField;
import vo.TaskVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frost-D on 18/3/7.
 */
public class TaskConfigController {

    @FXML
    private NumberTextField tfTaskExecution;

    @FXML
    private NumberTextField tfTaskDeadline;

    @FXML
    private NumberTextField tfTaskPriority;

    @FXML
    private Label labelTaskDeadline;

    @FXML
    private Label labelTaskDuration;

    private TaskService taskService = ServiceFactory.taskService();

    private TaskVO taskvo;

    private List<TaskVO> taskvos = new ArrayList<>();

    @FXML
    public void initialize() {

        tfTaskExecution.textProperty().addListener((observable, oldValue, newValue) -> {

        });

        tfTaskDeadline.textProperty().addListener((observable, oldValue, newValue) -> {

        });

        tfTaskPriority.textProperty().addListener((observable, oldValue, newValue) -> {

        });

    }

    public void setTaskVO(TaskVO taskvo) {
        this.taskvo = taskvo;

        setTFTaskExection(taskvo.execution);
        setTFTaskDeadline(taskvo.deadline);
        setTFTaskPriority(taskvo.priority);
    }

    public void setTaskVOID(int id) {
        this.taskvo.id = id;
    }

    public void setTFTaskExection(int execution) {
        tfTaskExecution.setText("" + execution);
    }

    public void setTFTaskDeadline(int deadline) {
        tfTaskDeadline.setText("" + deadline);
    }

    public void setTFTaskPriority(int priority) {
        tfTaskPriority.setText("" + priority);
    }

    @FXML
    public void onTaskExecutionChanged() {

        String string = tfTaskExecution.getText();
        int execution = Integer.parseInt(string);

        System.out.println("Execution:" + execution);
        taskvo.execution = execution;
        taskService.updateTaskById(taskvo.id, taskvo);

    }

    @FXML
    public void onTaskDeadlineChanged() {

        String string = tfTaskDeadline.getText();
        int deadline = Integer.parseInt(string);

        System.out.println("Deadline:" + deadline);
        taskvo.deadline = deadline;
        taskService.updateTaskById(taskvo.id,taskvo);

    }

    @FXML
    public void onTaskPriorityChanged() {

        String string = tfTaskPriority.getText();
        int priority = Integer.parseInt(string);

        System.out.println("Priority:" + priority);
        taskvo.priority = priority;
        taskService.updateTaskById(taskvo.id,taskvo);

    }

    public void updateTasks() {
        taskService.confirmUpdateAllTasks();
    }

    public void cancelUpdateTasks() {
        taskService.rejectUpdateAllTasks();
    }

}
