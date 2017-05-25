
package com.pace.cardquery;

import android.text.TextUtils;

import com.pace.utils.ByteUtil;

import java.util.Arrays;
import java.util.List;

public class LNTCardDetail extends BaseCardDetail {

    public LNTCardDetail() {
        super();
        // 00A4000E535A542E57414C4C45542E454E5600
        reqApduList.put(Constants.TAG_MONEY, Arrays.asList("", ""));
        reqApduList.put(Constants.TAG_CARDNUM,
                Arrays.asList("00A4000E535A542E57414C4C45542E454E5600", "00A40000023F00",
                        "00B0840000"));
    }

    @Override
    public List<String> reqApdu(String tag) {
        return reqApduList.get(tag);
    }

    private String parseCardNumInternal(String result) {
        if (TextUtils.isEmpty(result) || !result.endsWith("9000")) {
            return "null";
        }
        byte[] card = ByteUtil.toByteArray(result);
        byte[] bResult = new byte[8];
        System.arraycopy(card, 0, bResult, 0, 8);
        return ByteUtil.toHexString(bResult);
    }

    private String parseMoneyInternal(String money) {
        if (TextUtils.isEmpty(money) || !money.endsWith("9000")) {
            return "";
        }
        byte[] tmp = ByteUtil.toByteArray(money);
        int len = tmp.length;
        byte[] bResult = new byte[len - 2];
        System.arraycopy(tmp, 0, bResult, 0, len - 2);
        return ByteUtil.toInt(bResult, 0, 4) + "";
    }

    @Override
    protected void onPrepareHandlers() {
        addHandler(Constants.TAG_MONEY, new BaseCardDetail.Handler() {

            @Override
            protected String onCall(List<String> rspData) {
                return parseMoneyInternal(rspData.get(rspData.size() - 1));
            }
        });
        addHandler(Constants.TAG_CARDNUM, new BaseCardDetail.Handler() {

            @Override
            protected String onCall(List<String> rspData) {
                return parseCardNumInternal(rspData.get(rspData.size() - 1));
            }
        });
    }

}
