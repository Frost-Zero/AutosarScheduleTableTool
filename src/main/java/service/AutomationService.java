package service;

import po.automationPO;
import vo.EPVO;
import vo.STVO;
import vo.automationVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frost-D on 18/4/2.
 */
public class AutomationService {

    private List<automationPO> automationPOs = new ArrayList<>();

    private List<automationPO> autoPOsInST = new ArrayList<>();

    private int smallestOffset = 10000;

    boolean stopFlag = false;

    public void dataCollect(List<STVO> STs) {
        for (STVO stvo:STs) {
            for (EPVO epvo:stvo.EPs) {
                automationPO po = new automationPO();
                po.setStId(stvo.id);
                po.setEpId(epvo.id);
                po.setOffset(epvo.offset);
                po.setDuration(stvo.duration);
                automationPOs.add(po);
            }
        }
    }

    public List<automationVO> dataCalc(List<STVO> STs) {
        List<automationVO> vos = new ArrayList<>();
        for (int i = 0; i < STs.size(); i++) {
            autoPOsInST.clear();
            for (automationPO po : automationPOs) {
                if (po.getStId() == STs.get(i).id) {
                    autoPOsInST.add(po);
                }
            }
            automationVO vo = createVOByST(autoPOsInST);
            vos.add(vo);
        }
        return vos;
    }

    public automationVO createVOByST(List<automationPO> pos) {
        automationVO vo = new automationVO();
        stopFlag = false;

        //if all offset in pos < 0, stop finding next po
        while(!stopFlag) {
            automationPO po = findNextPO(pos);
            vo.epIds.add(po.getEpId());
            vo.delays.add(po.getOffset());
        }

        vo.stId = pos.get(0).getStId();

        return vo;
    }

    public automationPO findNextPO(List<automationPO> pos) {
        automationPO nextPO = new automationPO(-1,-1,-1,-1);
        smallestOffset = 10000;
        //find the smallestOffset
        for (automationPO po:pos) {
            if (po.getOffset() < smallestOffset && po.getOffset() > 0) {
                smallestOffset = po.getOffset();
                nextPO.setStId(po.getStId());
                nextPO.setDuration(po.getDuration());
                nextPO.setEpId(po.getEpId());
            }
        }
        //minus the smallestOffset
        if(smallestOffset != 10000) {
            for (automationPO po : pos) {
                po.setOffset(po.getOffset() - smallestOffset);
            }
        }

        smallestOffset = 10000;
        //find the nextDelay(new smallest offset)
        for (automationPO po:pos) {
            if (po.getOffset() < smallestOffset && po.getOffset() > 0) {
                smallestOffset = po.getOffset();
                nextPO.setOffset(smallestOffset);
            }
        }

        boolean emptyFlag = false;
        for (automationPO po:pos) {
            if(po.getOffset() > 0) {
                emptyFlag = true;
            }
        }

        //if none of offset of POs fits, find the smallest offset, plus duration and get the final delay
        if (nextPO.getEpId() == -1 || pos.size() == 1 || !emptyFlag) {
            smallestOffset = 10000;
            int duration = pos.get(0).getDuration();
            for (automationPO po:pos) {
                if (po.getOffset() < smallestOffset) {
                    smallestOffset = po.getOffset();
                    if(nextPO.getEpId() == -1) {
                        nextPO.setEpId(po.getEpId());
                    }
                    nextPO.setOffset(po.getOffset() + duration);
                }
            }
            nextPO.setStId(pos.get(0).getStId());
            nextPO.setDuration(duration);

            stopFlag = true;
        }
        return nextPO;
    }
}
