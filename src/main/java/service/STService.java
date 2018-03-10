package service;

import po.EPPO;
import po.STPO;
import vo.EPVO;
import vo.STVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Frost-D on 18/3/10.
 */
public class STService {

    private int maxId = 1;
    private List<STPO> STs = new ArrayList<>();

    public void createST(STVO vo) {
        STPO po = STVOToSTPO(vo);
        po.setId(maxId);
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

        maxId++;
        STs.add(po);
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

    public List<STVO> findSTs() {
        List<STVO> vos = new ArrayList<>();
        for (STPO po:STs) {
            vos.add(STPOToSTVO(po));
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
        // TODO task
        return po;
    }
    private EPVO EPPOToEPVO(EPPO po) {
        EPVO vo = new EPVO();
        vo.id = po.getId();
        vo.offset = po.getOffset();
        // TODO task
        return vo;
    }


}
