
package com.pace.tsm.plugin.cards;

import android.text.TextUtils;

import com.pace.tsm.plugin.bean.CardTransactionBean;
import com.pace.tsm.plugin.utils.ByteUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BJTCardDetail extends BaseCardDetail {
    private final List<String> mPreSetApduList = Arrays.asList("");
    private final List<String> mCardBalanceApduList = Arrays.asList("00A40008915600001401000100",
            "00A40000023F00", "00B0840000");
    private final List<String> mCardNumApduList = Arrays.asList("");
    private boolean mHasInit = false;

    public BJTCardDetail() {
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

    @Override
    public List<CardTransactionBean> rspTransaction(List<String> rspData) {
        List<CardTransactionBean> targeList = new ArrayList<CardTransactionBean>();
        for (String rsp : rspData) {
            if (!rsp.endsWith("9000") || rsp.length() != 50) {
                continue;
            }
            CardTransactionBean bean = new CardTransactionBean();
            bean.setTransaction_time(formatDateForTransaction(rsp.substring(16, 22)));
            int money = Integer.parseInt(rsp.substring(0, 6), 16);
            int balance = Integer.parseInt(rsp.substring(6, 12), 16);
            bean.setTransaction_amount(String.format("%d", new Object[] {
                    Integer.valueOf(balance - money)
            }));
            bean.setTransaction_type("1");
            targeList.add(bean);
        }
        return targeList;
    }
}
