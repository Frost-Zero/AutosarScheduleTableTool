package service;

import po.CalcDataPO;
import vo.CalcDataVO;
import vo.EPVO;
import vo.STVO;
import vo.TaskVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frost-D on 18/3/21.
 */
public class DataCalcService {

    private int currentTime = 0;

    private List<CalcDataPO> calcDataPOs = new ArrayList<>();

    private List<CalcDataPO> readyQueue = new ArrayList<>();

    private TaskService taskService = ServiceFactory.taskService();

    private CalcDataPO execpo = new CalcDataPO();

    private int priority = 255;
    private int releaseTime = 50000;

    public void init() {
        currentTime = 0;
        calcDataPOs.clear();
        readyQueue.clear();
    }


    public void dataCalc(List<STVO> STs) {
        for (STVO stvo:STs) {
            for (EPVO epvo:stvo.EPs) {
                for (int taskid:epvo.TaskIds) {
                    CalcDataPO po = new CalcDataPO();
                    po.setStId(stvo.id);                                                        //1
                    po.setDuration(stvo.duration);                                              //2
                    po.setDelayed(false);                                                       //3
                    po.setReleaseTime(0);                                                       //4
                    po.setEpId(epvo.id);                                                        //5
                    if(epvo.offset >= stvo.startTime) {
                        po.setTodo(epvo.offset - stvo.startTime);                               //6.1
                    } else {
                        po.setTodo(stvo.duration + epvo.offset - stvo.startTime);               //6.2
                    }
                    po.setTaskId(taskid);                                                       //7
                    TaskVO taskvo = taskService.findTaskById(taskid);
                    po.setExecution(taskvo.execution);                                          //8
                    po.setDeadline(taskvo.deadline);                                            //9
                    po.setPriority(taskvo.priority);                                            //10
                    calcDataPOs.add(po);
                }
            }
        }
    }

    public void execution() {

        addToReadyQue();

        findExecuteTask();

    }


    public void addToReadyQue() {
        //add to ready queue
        for (CalcDataPO po:calcDataPOs) {
            if(po.getTodo() == 0) {
                po.setTodo(po.getDuration());
                po.setReleaseTime(currentTime);
                CalcDataPO newpo = new CalcDataPO(po.getStId(),po.getEpId(),po.getTaskId(),po.getDuration(),po.getTodo(),po.getExecution(),po.getDeadline(),po.getPriority(),po.isDelayed(),po.getReleaseTime());
                readyQueue.add(newpo);
            }
        }
    }

    public void findExecuteTask() {
        //find the highest priority task
        priority = 255;

        if(readyQueue.size()>0){
            for (CalcDataPO po:readyQueue) {
                if (po.getPriority() < priority) {
                    priority = po.getPriority();
                    releaseTime = po.getReleaseTime();
                    execpo = po;
                } else if (po.getPriority() == priority && po.getReleaseTime() < releaseTime) {
                    releaseTime = po.getReleaseTime();
                    execpo = po;
                }
            }
            //execute the chosen po
            execute();
        } else {
            execpo = null;
            for (CalcDataPO po:readyQueue) {
                po.setDeadline(po.getDeadline() - 1);
            }
            for (CalcDataPO po:calcDataPOs) {
                po.setTodo(po.getTodo() - 1);
            }
            currentTime++;
        }
    }

    public void execute() {
        //excute the found task
        execpo.setExecution(execpo.getExecution() - 1);
//        execpo.setDeadline(execpo.getDeadline() - 1);
        for (CalcDataPO po:readyQueue) {
            po.setDeadline(po.getDeadline() - 1);
        }

        if (execpo.getDeadline() < execpo.getExecution()) {
            CalcDataPO po = findCalcDataPOByExecPO();
            po.setDelayed(true);
        }
    }

    public CalcDataPO findCalcDataPOByExecPO() {
        for (CalcDataPO po:readyQueue) {
            if (po.getStId() == execpo.getStId() && po.getEpId() == execpo.getEpId() && po.getTaskId() == execpo.getTaskId() && po.getReleaseTime() == execpo.getReleaseTime()) {
                return po;
            }
        }
        return null;
    }

    public void afterExecute() {
        if (execpo.getExecution() == 0) {
            CalcDataPO po = findCalcDataPOByExecPO();
            readyQueue.remove(po);
        }

        for (CalcDataPO po:calcDataPOs) {
            po.setTodo(po.getTodo() - 1);
        }

        currentTime++;
    }

    public CalcDataVO dataListVO() {
        CalcDataVO vo = new CalcDataVO();
        vo.stId = execpo.getStId();
        vo.epId = execpo.getEpId();
        vo.taskId = execpo.getTaskId();
        vo.exec = currentTime;
        vo.isDelayed = execpo.isDelayed();
        vo.substain = 1;
        return vo;
    }

    public boolean isReadyQueueEmpty() {
        if(execpo == null) {
            return true;
        } else {
            return false;
        }
    }


}
