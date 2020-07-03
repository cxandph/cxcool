package com.cxcool.utils;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.checkerframework.checker.units.qual.A;

import javax.jms.*;
import java.io.IOException;

/**
 * Author: ph
 * Date: 2020/7/3
 * Time: 1:37
 * Description:ActiveMQ的消息消费者(Topic，1对N的消息)
 */
public class JmsConsumerTopic {
    public static final String ACTIVE_MQ_URL = "tcp://10.10.10.2:61616";
    public static final String USER_NAME = "admin";
    public static final String USER_PASSWORD = "admin";
    public static final String TOPIC_NAME = "TOPIC-01";
    public static void main(String[] args) throws JMSException, IOException {

        System.out.println("消费者3号正在监听-----------------------------------------");
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(USER_NAME,USER_PASSWORD,ACTIVE_MQ_URL);

        Connection connection =activeMQConnectionFactory.createConnection();
        connection.start();
        Session session= connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        MessageConsumer messageConsumer = session.createConsumer(topic);

        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if(null!=message&&message instanceof TextMessage){
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println("收到消息:"+textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.in.read();
        messageConsumer.close();
        session.close();
        connection.close();
        System.out.println("消费者接收消息完成==>"+TOPIC_NAME);

    }
}
