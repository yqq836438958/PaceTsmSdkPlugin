
package com.pace.cardquery;

import java.util.List;

public interface ICardDetail {
    public List<String> reqApdu(String tag);

    public String rspApdu(String tag, List<String> rspData);
}
