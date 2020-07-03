package com.cxcool.utils;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Author: ph
 * Date: 2020/7/1
 * Time: 15:49
 * Description:ActiveMQ的消息生产者(Queue，1对1的消息)
 */

public class JmsProducer {


    public static final String ACTIVE_MQ_URL = "tcp://10.10.10.2:61616";
    public static final String USER_NAME = "admin";
    public static final String USER_PASSWORD = "admin";
    public static final String QUEUE_NAME = "queue-cxcool-01";

    public static void main(String[] args) throws JMSException {

        System.out.println("我是消息生产者...");
        //1、创建连接工厂，按照给定的url地址，采用用户名、密码登录，若不传用户名、密码
        //需要activemq的默认登录用户名、密码没有改过，依然是admin/admin
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(USER_NAME, USER_PASSWORD, ACTIVE_MQ_URL);
        //2、通过连接工厂，获取connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //3、创建会话session，两个参数：
        //3.1、事务 3.2、签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4、创建目的地（具体是队列还是topic）
        Queue queue = session.createQueue(QUEUE_NAME);
        //5、创建消息的生产者
        MessageProducer messageProducer = session.createProducer(queue);
        //6、通过使用jmessageProducer生产6条消息发送到MQ的队列里面
        for (int i = 1; i <= 10; i++) {
            //7、创建消息
            TextMessage textMessage = session.createTextMessage("messagelistening---" + i);
            //8.通过messageProducer发送给mq
            messageProducer.send(textMessage);
        }

        //9关闭资源
        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("============》消息发布到ActiveMQ完成::" + ACTIVE_MQ_URL + "::" + QUEUE_NAME);

    }
}
