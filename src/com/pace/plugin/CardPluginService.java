
package com.pace.plugin;

import com.pace.cardquery.CardDetailFactory;
import com.pace.cardquery.Constants;
import com.pace.cardquery.ICardDetail;

import java.util.ArrayList;
import java.util.List;

public class CardPluginService implements ICardPluginService {

    @Override
    public List<String> fetchDetailReq(String aid, String tag) {
        ICardDetail cardDetail = CardDetailFactory.getInstance().getCard(aid);
        if (cardDetail == null) {
            return null;
        }
        return cardDetail.reqApdu(tag);
    }

    @Override
    public String parseDetailRsp(String aid, String tag, List<String> rsp) {
        ICardDetail cardDetail = CardDetailFactory.getInstance().getCard(aid);
        if (cardDetail == null) {
            return null;
        }
        return cardDetail.rspApdu(tag, rsp);
    }

    @Override
    public List<String> getSupportAidList() {
        List<String> aidConfigList = new ArrayList<String>();
        aidConfigList.add(Constants.AID_BJT);
        aidConfigList.add(Constants.AID_SZT);
        aidConfigList.add(Constants.AID_LNT);
        return aidConfigList;
    }

    @Override
    public List<String> getTagListByAid(String aid) {
        ICardDetail cardDetail = CardDetailFactory.getInstance().getCard(aid);
        if (cardDetail == null) {
            return null;
        }
        return cardDetail.getSupportTags();
    }

}
