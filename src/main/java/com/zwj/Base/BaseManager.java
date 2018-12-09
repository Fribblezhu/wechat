package com.zwj.Base;

/**
 * @author: zwj
 * @Date: 12/5/18
 * @Time: 10:42 PM
 * @description:
 */
public interface BaseManager extends BaseWorker{

    BaseWorker getWork(String context);


}
