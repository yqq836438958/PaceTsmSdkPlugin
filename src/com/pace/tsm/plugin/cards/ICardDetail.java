
package com.pace.tsm.plugin.cards;

import com.pace.tsm.plugin.bean.CardTransactionBean;

import java.util.List;

public interface ICardDetail {
    public List<String> reqApdu(String tag);

    public String rspApdu(String tag, List<String> rspData);

    public List<String> reqTransaction();

    public List<CardTransactionBean> rspTransaction(List<String> rspData);

    public List<String> getSupportTags();
}
