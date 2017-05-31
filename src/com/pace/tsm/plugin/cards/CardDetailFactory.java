
package com.pace.tsm.plugin.cards;

import java.util.HashMap;

public class CardDetailFactory {
    private static volatile CardDetailFactory sInstance = null;
    private HashMap<String, ICardDetail> mCardDetailsMap = new HashMap<String, ICardDetail>();
    private HashMap<String, Class<?>> mCardClassMap = new HashMap<String, Class<?>>();

    private CardDetailFactory() {
        mCardClassMap.put(Constants.AID_BJT, BJTCardDetail.class);
        mCardClassMap.put(Constants.AID_LNT, LNTCardDetail.class);
        mCardClassMap.put(Constants.AID_SZT, SZTCardDetail.class);
    }

    public static CardDetailFactory getInstance() {
        if (sInstance == null) {
            synchronized (CardDetailFactory.class) {
                if (sInstance == null) {
                    sInstance = new CardDetailFactory();
                }
            }
        }
        return sInstance;
    }

    public ICardDetail getCard(String aid) {
        ICardDetail detail = mCardDetailsMap.get(aid);
        if (detail != null) {
            return detail;
        }
        Class<?> clz = mCardClassMap.get(aid);
        if (clz != null) {
            try {
                detail = (ICardDetail) clz.newInstance();
            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (detail != null) {
            mCardDetailsMap.put(aid, detail);
        }
        return detail;
    }
}
