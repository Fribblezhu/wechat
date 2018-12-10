package com.zwj.manager;

import com.zwj.Base.BaseRobot;
import com.zwj.Base.BaseWorker;
import com.zwj.express.CheckExpressRobot;
import com.zwj.wx.message.BaseMessage;
import org.springframework.stereotype.Component;

/**
 * @author: zwj
 * @Date: 12/9/18
 * @Time: 10:37 PM
 * @description:
 */
@Component
public class RobotManager extends AbstractManager {

    public RobotManager(CheckExpressRobot checkExpressRobot) {
        this.workers.add(checkExpressRobot);
    }

    @Override
    public boolean isDuty(String context) {
        for(BaseWorker worker: this.workers) {
            if(worker.isDuty(context)){
                return  true;
            }
        }
        return false;
    }

    @Override
    public BaseMessage dispatch(String context) {
        BaseRobot robot = (BaseRobot) this.getWork(context);
        if(robot != null) {
            return robot.execute(context);
        }
        return null;
    }
}
