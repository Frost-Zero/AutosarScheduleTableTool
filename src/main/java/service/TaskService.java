package service;

import com.sun.javafx.tk.Toolkit;
import po.EPPO;
import po.STPO;
import po.TaskPO;
import vo.EPVO;
import vo.STVO;
import vo.TaskVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frost-D on 18/3/14.
 */
public class TaskService {

    private int maxTaskId = 1;
    private List<TaskPO> Tasks = new ArrayList<>();

    public void createTask(TaskVO vo) {
        TaskPO po = TaskVOToTaskPO(vo);
        po.setId(maxTaskId);
        po.setDeadline(vo.deadline);
        po.setExecution(vo.execution);
        po.setPriority(vo.priority);

        maxTaskId++;
        Tasks.add(po);
    }

    public void removeTask(int id) {
        // find index by id
        int index = -1;
        for (int i = 0; i < Tasks.size(); i++) {
            TaskPO po = Tasks.get(i);
            if (po.getId() == id) {
                index = i;
                break;
            }
        }
        if (index > -1 && index < Tasks.size()) {
            Tasks.remove(index);
        }
    }

    public TaskVO findTaskById(int id) {
        int index = -1;
        for (int i = 0; i < Tasks.size(); i++) {
            TaskPO po = Tasks.get(i);
            if (po.getId() == id) {
                index = i;
                break;
            }
        }

        if (index > -1 && index < Tasks.size()) {
            TaskPO po = Tasks.get(index);
            return TaskPOToTaskVO(po);
        }
        return null;
    }

    public List<TaskVO> findTasks() {
        List<TaskVO> vos = new ArrayList<>();
        for (TaskPO po:Tasks) {
            vos.add(TaskPOToTaskVO(po));
        }
        return vos;
    }

    public void updateTaskById(int id, TaskVO taskvo){
        int index = -1;
        for (int i = 0; i < Tasks.size(); i++) {
            TaskPO po = Tasks.get(i);
            if (po.getId() == id) {
                index = i;
                break;
            }
        }

        if (index > -1 && index < Tasks.size()) {
            Tasks.set(index, TaskVOToTaskPO(taskvo));
        }

    }
    ////

    private TaskPO TaskVOToTaskPO(TaskVO vo) {
        TaskPO po = new TaskPO();
        po.setId(vo.id);
        po.setPriority(vo.priority);
        po.setExecution(vo.execution);
        po.setDeadline(vo.deadline);

        return po;
    }

    private TaskVO TaskPOToTaskVO(TaskPO po) {
        TaskVO vo = new TaskVO();
        vo.id = po.getId();
        vo.priority = po.getPriority();
        vo.execution = po.getExecution();
        vo.deadline = po.getDeadline();

        return vo;
    }
}
