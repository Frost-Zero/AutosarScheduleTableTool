package service;

import po.EPPO;
import po.STPO;
import vo.EPVO;
import vo.STVO;

import java.util.*;

/**
 * Created by Frost-D on 18/3/10.
 */
public class STService {

    private int maxSTId = 1;
    private int maxEPId = 1;
    private List<STPO> STs = new ArrayList<>();

    public void createST(STVO vo) {
        STPO po = STVOToSTPO(vo);
        po.setId(maxSTId);
        po.setDuration(10);
        po.setStart_time(0);

        //  random ep * 3
//        List<EPPO> eppos = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            Random random = new Random();
//            EPPO eppo = new EPPO();
//            eppo.setId(i+1);
//            eppo.setOffset(random.nextInt(10));
//            eppos.add(eppo);
//            maxEPId++;
//        }
//        po.setEPs(eppos);

        maxSTId++;
        STs.add(po);
    }


    public EPVO createEPInST(int STId, EPVO vo) {

        EPPO po = EPVOToEPPO(vo);

        Random random = new Random();
        po.setId(maxEPId);
        po.setOffset(random.nextInt(10));


        //TODO some useless tasks
        List<Integer> taskids = new ArrayList<>();

        taskids.add(1);

        po.setTaskIds(taskids);

        maxEPId++;

        STs.get(findSTIndexById(STId)).getEPs().add(po);
        EPVO epvo = EPPOToEPVO(po);
        epvo.stId =STId;
        return epvo;
    }

    public void removeST(int id) {
        // find index by id
        int index = findSTIndexById(id);
        if (index > -1 && index < STs.size()) {
            STs.remove(index);
        }
    }


    public void removeEPinST(int STId ,int id) {
        int index = -1;
        List<EPPO> EPs = STs.get(findSTIndexById(STId)).getEPs();
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

    public EPVO findEPinSTById(int STId,int id) {
        int index = -1;
        List<EPPO> EPs = STs.get(findSTIndexById(STId)).getEPs();
        for (int i = 0; i<EPs.size(); i++) {
            EPPO po = EPs.get(i);
            if (po.getId() == id) {
                index = i;
                break;
            }
        }

        if (index > -1 && index < EPs.size()) {
            EPPO po = EPs.get(index);
            EPVO vo = EPPOToEPVO(po);
            vo.stId = STId;
            return vo;
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


    public List<EPVO> findEPsInST(int STId) {
        List<EPVO> vos = new ArrayList<>();
        List<EPPO> EPs = STs.get(findSTIndexById(STId)).getEPs();
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

    public void updateEPById(int STId,int id,int offset){
        int index = -1;
        int STIndex = findSTIndexById(STId);
        for (int i = 0; i < STs.get(STIndex).getEPs().size(); i++) {
            if (STs.get(STIndex).getEPs().get(i).getId() == id) {
                STs.get(STIndex).getEPs().get(i).setOffset(offset);
                break;
            }
        }
    }

    public void addTaskIdInEPs(int STId, int EPId) {
        // find index by id
        int index = -1;
        int STIndex = findSTIndexById(STId);
        for (int i = 0; i < STs.get(STIndex).getEPs().size(); i++) {
            EPPO po = STs.get(STIndex).getEPs().get(i);
            if (po.getId() == EPId) {
                index = i;
                break;
            }
        }
        if (index > -1 && index < STs.get(STIndex).getEPs().size()) {
            STs.get(STIndex).getEPs().get(index).getTaskIds().add(1);
        }

    }

    public void removeTaskIdInEPs(int STId,int EPId, int taskIdIndex) {
        int index = -1;
        int STIndex = findSTIndexById(STId);
        for (int i = 0; i < STs.get(STIndex).getEPs().size(); i++) {
            EPPO po = STs.get(STIndex).getEPs().get(i);
            if (po.getId() == EPId) {
                index = i;
                break;
            }
        }
        if (index > -1 && index < STs.get(STIndex).getEPs().size()) {
            STs.get(STIndex).getEPs().get(index).getTaskIds().remove(taskIdIndex);
        }
    }

    public void updateTaskIdInEPs(int STId, int EPId, int taskIdIndex, int taskId) {
        int index = -1;
        int STIndex = findSTIndexById(STId);
        for (int i = 0; i < STs.get(STIndex).getEPs().size(); i++) {
            EPPO po = STs.get(STIndex).getEPs().get(i);
            if (po.getId() == EPId) {
                index = i;
                break;
            }
        }
        if (index > -1 && index < STs.get(STIndex).getEPs().size()) {
            STs.get(STIndex).getEPs().get(index).getTaskIds().set(taskIdIndex,taskId);
        }
    }

    public int findSTIndexById(int id) {
        for (int i = 0; i < STs.size(); i++) {
            STPO po = STs.get(i);
            if (po.getId() == id) {
                return i;
            }
        }
        return -1;
    }


    public void updateStartTimeBySTId(int stid, int startTime) {

        for (STPO po:STs) {
            if (po.getId() == stid) {
                po.setStart_time(startTime);
                break;
            }
        }
    }

    public int findStartTimeBySTId(int stid) {
        for (STPO po:STs) {
            if (po.getId() == stid) {
                return po.getStart_time();
            }
        }
        return -1;
    }

    /////

    private STPO STVOToSTPO(STVO vo) {
        STPO po = new STPO();
        po.setId(vo.id);
        po.setDuration(vo.duration);
        po.setStart_time(vo.startTime);

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
        vo.startTime = po.getStart_time();

        List<EPVO> epvos = new ArrayList<>();
        for (EPPO eppo:po.getEPs()) {
            EPVO epvo = EPPOToEPVO(eppo);
            epvo.stId = po.getId();
            epvos.add(epvo);
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
        return vo;
    }
}
