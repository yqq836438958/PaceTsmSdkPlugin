
package com.pace.tsm.plugin.cards;

import android.text.TextUtils;

import com.pace.tsm.plugin.utils.DateTimeUtil;
import com.pace.tsm.plugin.utils.ValueUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class BaseCardDetail implements ICardDetail {
    private HandlerList mHandlerList;
    protected HashMap<String, List<String>> mReqApduList = new HashMap<String, List<String>>();
    protected List<String> mSupportTagList = new ArrayList<String>();
    protected List<String> mTransactionApduCmds = new ArrayList<String>();

    protected void init() {
        onPrepareHandlers();
    }

    @Override
    public String rspApdu(String tag, List<String> rspData) {
        return mHandlerList.head.parseTag(tag, rspData);
    }

    @Override
    public List<String> reqApdu(String tag) {
        if (!mSupportTagList.contains(tag)) {
            return null;
        }
        return onReqApdu(tag);
    }

    @Override
    public List<String> getSupportTags() {
        return mSupportTagList;
    }

    @Override
    public List<String> reqTransaction() {
        return mTransactionApduCmds;
    }

    protected final void addHandler(String tag, List<String> cmds, Handler process) {
        if (process == null) {
            return;
        }
        process.setTag(tag);
        if (mHandlerList == null) {
            mHandlerList = new HandlerList(process);
        } else {
            mHandlerList.add(process);
        }
        mSupportTagList.add(tag);
        mReqApduList.clear();
        mReqApduList.put(tag, cmds);
    }

    public abstract class Handler {
        private String mTag = "";

        private void setTag(String tag) {
            mTag = tag;
        }

        /**
         * 持有后继的责任对象
         */
        protected Handler nextHandler;

        public String parseTag(String tag, List<String> rspData) {
            if (!TextUtils.isEmpty(mTag) && mTag.equalsIgnoreCase(tag)) {
                return onCall(rspData);
            }
            return nextHandler.parseTag(tag, rspData);
        }

        protected abstract String onCall(List<String> rspData);

        public Handler getNext() {
            return nextHandler;
        }

        public void setNext(Handler successor) {
            this.nextHandler = successor;
        }

    }

    final class HandlerList {
        Handler head = null;
        Handler curProcess = null;
        int curPos = 0;

        HandlerList(Handler process) {
            head = process;
            curProcess = process;
            curPos++;
        }

        void add(Handler process) {
            curProcess.setNext(process);
            curProcess = process;
            curPos++;
        }
    }

    protected abstract void onPrepareHandlers();

    protected abstract List<String> onReqApdu(String tag);

    public static String formatDatetimeForTransaction(String data) {
        if (ValueUtil.isEmpty(data)) {
            return null;
        }
        String currentYear = DateTimeUtil.currentDateString("yyyy");
        if (data.length() == 10) {
            data = new StringBuilder(String.valueOf(currentYear)).append(data).toString();
        } else if (data.length() <= 12) {
            data = currentYear.substring(0, 2) + data;
        }
        return DateTimeUtil.format(DateTimeUtil.parseDateString(data, "yyyyMMddHHmmss"),
                "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatDateForTransaction(String data) {
        if (ValueUtil.isEmpty(data)) {
            return null;
        }
        String currentYear = DateTimeUtil.currentDateString("yyyy");
        if (data.length() <= 12) {
            data = currentYear.substring(0, 2) + data;
        }
        return DateTimeUtil.format(DateTimeUtil.parseDateString(data, "yyyyMMdd"), "yyyy-MM-dd");
    }

}
