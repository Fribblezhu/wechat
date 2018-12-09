package com.zwj.manager;

import com.zwj.Base.BaseManager;
import com.zwj.Base.BaseWorker;
import com.zwj.wx.message.BaseMessage;
import javafx.concurrent.Worker;

import java.util.List;

/**
 * @author: zwj
 * @Date: 12/5/18
 * @Time: 10:53 PM
 * @description:
 */
public abstract  class AbstractManager implements BaseManager {


    protected List<BaseWorker>  workers;

    @Override
    public boolean isDuty(String context) {
        return false;
    }

    public abstract BaseMessage dispatch(String context);

    @Override
    public BaseWorker getWork(String context){
        for(BaseWorker worker: this.workers) {
            if(worker.isDuty(context))
                return worker;
        }
        return null;
    }
}
