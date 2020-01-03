package com.zc.study.guardedSuspension;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangchong
 * @CodeReviewer zhangqingan
 * @Description
 */
class MessageQueue {

    private List<Consumer> consumers = new ArrayList<Consumer>();

    void register(Consumer consumer) {
        consumers.add(consumer);
    }

    /**
     * MQ消息返回后会调用该方法,该方法的执行线程不同于发送消息的线程
     *
     * @param msg
     */
    void onMessage(Message msg) {
        //唤醒等待的线程
        GuardedObject.fireEvent(msg.id, msg);
    }


    //处理浏览器发来的请求
    Response handleWebReq() {
        //创建一消息
        final String id = "1";
        Message msg1 = new Message(id, "{...}");
        //创建GuardedObject实例
        GuardedObject<Message> go = GuardedObject.create(id);
        // 发送消息
        send(msg1);
        // 如何等待MQ返回的消息呢？
        Message r = go.get(t -> null != t);
        return new Response(r);
    }

    private void send(Message msg) {

    }
}
