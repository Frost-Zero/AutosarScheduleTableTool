package service;

import po.VeriEPPO;
import vo.EPVO;
import vo.STVO;
import vo.VeriEPVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frost-D on 18/3/29.
 */
public class VeriEPService {

    private List<VeriEPPO> veriEPPOs = new ArrayList<>();

    private STService stService = ServiceFactory.STService();

    private int firstTodo = 50000;

    public void clearVeriEPPOs() {
        veriEPPOs.clear();
    }

    public void dataCalc(List<STVO> STs) {
        for (STVO stvo:STs) {
            for (EPVO epvo:stvo.EPs) {
                VeriEPPO veriEPPO = new VeriEPPO();
                int startingEPOffset = stService.findEPinSTById(stvo.id, stvo.startingEPId).offset;
                veriEPPO.setEpId(epvo.id);
                if (epvo.offset > startingEPOffset) {
                    veriEPPO.setTodo(epvo.offset - startingEPOffset);
                } else if (epvo.offset <= startingEPOffset) {
                    veriEPPO.setTodo(epvo.offset - startingEPOffset + stvo.duration);
                }
                veriEPPO.setDuration(stvo.duration);
                veriEPPOs.add(veriEPPO);
            }
        }
    }

    public VeriEPVO init() {
        List<Integer> ids = new ArrayList<>();
        for (VeriEPPO po:veriEPPOs) {
            if (po.getTodo() == po.getDuration()) {
                ids.add(po.getEpId());
            }
        }
        VeriEPVO vo = new VeriEPVO();
        vo.next = 0;
        vo.epIds = ids;
        return vo;
    }

    public VeriEPVO execute() {
        firstTodo = 50000;
        List<Integer> ids = new ArrayList<>();
        for (VeriEPPO po:veriEPPOs) {
            if (po.getTodo() < firstTodo) {
                firstTodo = po.getTodo();
                ids.clear();
                ids.add(po.getEpId());
            } else if (po.getTodo() == firstTodo) {
                ids.add(po.getEpId());
            }
        }
        VeriEPVO vo = new VeriEPVO();
        vo.next = firstTodo;
        vo.epIds = ids;
        afterExecute();
        return vo;
    }

    public void afterExecute() {
        for (VeriEPPO po:veriEPPOs) {
            po.setTodo(po.getTodo() - firstTodo);
            if (po.getTodo() == 0) {
                po.setTodo(po.getDuration());
            }
        }
    }
}
