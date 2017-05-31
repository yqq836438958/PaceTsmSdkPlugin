
package com.pace.tsm.plugin;

import com.pace.tsm.plugin.bean.CardTransactionBean;

import java.util.List;

public interface ICardPluginService {
    public List<String> fetchDetailReq(String aid, String tag);

    public String parseDetailRsp(String aid, String tag, List<String> rsp);

    public List<String> fetchTransactionReq(String aid);

    public List<CardTransactionBean> parseTransactionRsp(String aid, List<String> rsp);

    public List<String> getSupportAidList();

    public List<String> getTagListByAid(String aid);
}
