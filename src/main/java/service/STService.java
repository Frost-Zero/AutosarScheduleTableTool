package service;

import po.STPO;
import vo.STVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frost-D on 18/3/10.
 */
public class STService {

    private int maxId = 1;
    private List<STPO> STs = new ArrayList<>();

    public void createST(STVO vo) {
        STPO po = STVOToSTPO(vo);
        po.setId(maxId);
        maxId++;
        STs.add(po);
    }

    public void removeST(int id) {
        // find index by id
        int index = -1;
        for (STPO po: STs) {
            if (po.getId() == id) {
                index = id;
                break;
            }
        }
        if (index > -1 && index < STs.size()) {
            STs.remove(index);
        }
    }

    public STVO findSTById(int id) {
        int index = -1;
        for (STPO po: STs) {
            if (po.getId() == id) {
                index = id;
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

    private STPO STVOToSTPO(STVO vo) {
        STPO po = new STPO();
        po.setId(vo.id);
        // TODO EPVO to  EPPO
        return po;
    }

    private STVO STPOToSTVO(STPO po) {
        STVO vo = new STVO();
        vo.id = po.getId();
        // TODO EPVO to  EPPO
        return vo;
    }
}
