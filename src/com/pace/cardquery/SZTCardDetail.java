
package com.pace.cardquery;

import android.text.TextUtils;

import com.pace.utils.ByteUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SZTCardDetail extends BaseCardDetail {
    private final List<String> mPreSetApduList = Arrays.asList(
            "00A404000E535A542E57414C4C45542E454E5600",
            "00A40000021001", "00B0950000");
    private final List<String> mCardBalanceApduList = Arrays.asList("805C000204");
    private final List<String> mCardNumApduList = Arrays.asList("");
    private boolean mHasInit = false;

    public SZTCardDetail() {
        super();
        init();
    }

    @Override
    public List<String> onReqApdu(String tag) {
        List<String> targetList = new ArrayList<String>();
        if (mHasInit) {
            targetList.addAll(mPreSetApduList);
        }
        targetList.addAll(mReqApduList.get(tag));
        return targetList;
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
        addHandler(Constants.TAG_MONEY, mCardBalanceApduList, new BaseCardDetail.Handler() {

            @Override
            protected String onCall(List<String> rspData) {
                return parseMoneyInternal(rspData.get(rspData.size() - 1));
            }
        });
        addHandler(Constants.TAG_CARDNUM, mCardNumApduList, new BaseCardDetail.Handler() {

            @Override
            protected String onCall(List<String> rspData) {
                return parseCardNumInternal(rspData.get(rspData.size() - 1));
            }
        });
    }

}
