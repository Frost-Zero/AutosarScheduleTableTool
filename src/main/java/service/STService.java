package service;

import po.EPPO;
import po.STPO;
import po.TaskPO;
import vo.EPVO;
import vo.STVO;
import vo.TaskVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Frost-D on 18/3/10.
 */
public class STService {

    private int maxSTId = 1;
    private int maxEPId = 1;
    private List<STPO> STs = new ArrayList<>();
    private List<EPPO> EPs = new ArrayList<>();

    public void createST(STVO vo) {
        STPO po = STVOToSTPO(vo);
        po.setId(maxSTId);
        po.setDuration(10);

        // TODO random ep
        List<EPPO> eppos = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Random random = new Random();
            EPPO eppo = new EPPO();
            eppo.setId(i);
            eppo.setOffset(random.nextInt(10));
            eppos.add(eppo);
        }
        po.setEPs(eppos);

        maxSTId++;
        STs.add(po);
    }

    public void createEP(EPVO vo) {
        EPPO po = EPVOToEPPO(vo);

        Random random = new Random();
        po.setId(maxEPId);
        po.setOffset(random.nextInt(10));

        //TODO some useless tasks
        List<TaskPO> taskpos = new ArrayList<>();
        List<Integer> taskids = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            TaskPO taskpo = new TaskPO();
            taskpo.setId(i);
            taskids.add(i);
            taskpo.setDeadline(random.nextInt(10));
            taskpo.setExecution(random.nextInt(5));
            taskpo.setPriority(random.nextInt(3));
            taskpos.add(taskpo);
        }
        po.setTaskIds(taskids);

        //TODO print offset
//        System.out.println(po.getOffset());

        maxEPId++;
        EPs.add(po);
    }

    public void removeST(int id) {
        // find index by id
        int index = -1;
        for (int i = 0; i < STs.size(); i++) {
            STPO po = STs.get(i);
            if (po.getId() == id) {
                index = i;
                break;
            }
        }
        if (index > -1 && index < STs.size()) {
            STs.remove(index);
        }
    }

    public void removeEP(int id) {
        int index = -1;
        for (int i = 0; i < EPs.size(); i++) {
            EPPO po = EPs.get(i);
            if (po.getId() == id) {
                index = i;
                break;
            }
        }
        if (index > -1 && index < EPs.size()){
            EPs.remove(index);
        }
    }

    public STVO findSTById(int id) {
        int index = -1;
        for (int i = 0; i < STs.size(); i++) {
            STPO po = STs.get(i);
            if (po.getId() == id) {
                index = i;
                break;
            }
        }

        if (index > -1 && index < STs.size()) {
            STPO po = STs.get(index);
            return STPOToSTVO(po);
        }
        return null;
    }

    public EPVO findEPById(int id) {
        int index = -1;
        for (int i = 0; i<EPs.size(); i++) {
            EPPO po = EPs.get(i);
            if (po.getId() == id) {
                index = i;
                break;
            }
        }

        if (index > -1 && index < EPs.size()) {
            EPPO po = EPs.get(index);
            return EPPOToEPVO(po);
        }
        return null;
    }

    public List<STVO> findSTs() {
        List<STVO> vos = new ArrayList<>();
        for (STPO po:STs) {
            vos.add(STPOToSTVO(po));
        }
        return vos;
    }

    public List<EPVO> findEPs() {
        List<EPVO> vos = new ArrayList<>();
        for (EPPO po:EPs) {
            vos.add(EPPOToEPVO(po));
        }
        return vos;
    }

    public void updateSTById(int id, STVO stvo){
        int index = -1;
        for (int i = 0; i < STs.size(); i++) {
            STPO po = STs.get(i);
            if (po.getId() == id) {
                index = i;
                break;
            }
        }
        int max = -1;
        for (EPVO epvo:stvo.EPs) {
            max = epvo.offset > max ? epvo.offset : max;
        }
        if (stvo.duration < max) {
            stvo.duration = max;
        }
        if (index > -1 && index < STs.size()) {
            STs.set(index, STVOToSTPO(stvo));
        }

    }

    /////

    private STPO STVOToSTPO(STVO vo) {
        STPO po = new STPO();
        po.setId(vo.id);
        po.setDuration(vo.duration);

        List<EPPO> eppos = new ArrayList<>();
        for (EPVO epvo:vo.EPs) {
            eppos.add(EPVOToEPPO(epvo));
        }
        po.setEPs(eppos);

        return po;
    }

    private STVO STPOToSTVO(STPO po) {
        STVO vo = new STVO();
        vo.id = po.getId();
        vo.duration = po.getDuration();

        List<EPVO> epvos = new ArrayList<>();
        for (EPPO eppo:po.getEPs()) {
            epvos.add(EPPOToEPVO(eppo));
        }
        vo.EPs = epvos;

        return vo;
    }

    private EPPO EPVOToEPPO(EPVO vo) {
        EPPO po = new EPPO();
        po.setId(vo.id);
        po.setOffset(vo.offset);

        List<Integer> taskidsclone = new ArrayList<>();
        for (Integer taskid:vo.TaskIds) {
            taskidsclone.add(taskid);
        }
        po.setTaskIds(taskidsclone);
        // TODO task
        return po;
    }

    private EPVO EPPOToEPVO(EPPO po) {
        EPVO vo = new EPVO();
        vo.id = po.getId();
        vo.offset = po.getOffset();

        List<Integer> taskidsclone = new ArrayList<>();
        for (Integer taskid:po.getTaskIds()){
            taskidsclone.add(taskid);
        }
        vo.TaskIds = taskidsclone;
        // TODO task
        return vo;
    }
}
