package com.cxcool.utils;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Author: ph
 * Date: 2020/7/3
 * Time: 1:36
 * Description:ActiveMQ的消息生产者(Topic，1对N的消息)
 */
public class JmsProducerTopic {
    public static final String ACTIVE_MQ_URL = "tcp://10.10.10.2:61616";
    public static final String USER_NAME = "admin";
    public static final String USER_PASSWORD = "admin";
    public static final String TOPIC_NAME = "TOPIC-01";
    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(USER_NAME,USER_PASSWORD,ACTIVE_MQ_URL);

        Connection connection =activeMQConnectionFactory.createConnection();
        connection.start();
        Session session= connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        MessageProducer messageProducer =session.createProducer(topic);
        for (int i = 1; i <= 6; i++) {
            TextMessage textMessage = session.createTextMessage("TopicMsg-"+i);
            messageProducer.send(textMessage);
        }
        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("生产者发送消息完成==>"+TOPIC_NAME);

    }
}
