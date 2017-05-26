
package com.pace.cardquery;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class BaseCardDetail implements ICardDetail {
    private HandlerList mHandlerList;
    protected HashMap<String, List<String>> mReqApduList = new HashMap<String, List<String>>();
    protected List<String> mSupportTagList = new ArrayList<String>();

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
}
