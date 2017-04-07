
package com.pace.plugin;

import java.util.List;

public interface ICardPluginService {
    public List<String> fetchDetailReq(String aid, String tag);

    public String parseDetailRsp(String aid, String tag, List<String> rsp);
}
