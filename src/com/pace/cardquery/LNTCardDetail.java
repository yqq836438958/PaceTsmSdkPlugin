
package com.pace.cardquery;

import java.util.List;

public class LNTCardDetail extends BaseCardDetail {

    @Override
    public List<String> reqApdu(String tag) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void onPrepareHandlers() {
        addHandler(Constants.TAG_MONEY, new BaseCardDetail.Handler() {

            @Override
            protected String onCall(List<String> rspData) {
                // TODO
                return null;
            }
        });
        addHandler(Constants.TAG_CARDNUM, new BaseCardDetail.Handler() {

            @Override
            protected String onCall(List<String> rspData) {
                // TODO
                return null;
            }
        });
    }

}
