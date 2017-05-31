
package com.pace.tsm.plugin.cards;

import android.text.TextUtils;

import com.pace.tsm.plugin.bean.CardTransactionBean;
import com.pace.tsm.plugin.utils.ByteUtil;

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
        mTransactionApduCmds.clear();
        mTransactionApduCmds.add("00A404000E535A542E57414C4C45542E454E5600");
        mTransactionApduCmds.add("00A40000021001");
        for (int i = 0; i < 10; i++) {
            mTransactionApduCmds.add("00B2#C400".replace("#", ByteUtil.toHex(i)));
        }
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

    @Override
    public List<CardTransactionBean> rspTransaction(List<String> rspData) {
        List<CardTransactionBean> targeList = new ArrayList<CardTransactionBean>();
        for (String rsp : rspData) {
            if (!rsp.endsWith("9000") || rsp.length() != 50) {
                continue;
            }
            CardTransactionBean bean = new CardTransactionBean();
            bean.setTransaction_time(
                    formatDatetimeForTransaction(rsp.substring(32, 46)));
            bean.setTransaction_amount(String.format("%d", new Object[] {
                    Integer.valueOf(Integer.parseInt(rsp.substring(10, 18), 16))
            }));
            String type = rsp.substring(18, 20);
            if (type.matches("10|02|01")) {
                type = "1";
            } else if (type.matches("11|09|06|05")) {
                type = "2";
            }
            bean.setTransaction_type(type);
            targeList.add(bean);
        }
        return targeList;
    }

}
