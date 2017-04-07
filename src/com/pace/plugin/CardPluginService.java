
package com.pace.plugin;

import com.pace.cardquery.CardDetailFactory;
import com.pace.cardquery.ICardDetail;

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

}
