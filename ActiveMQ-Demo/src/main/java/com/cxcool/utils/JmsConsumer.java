package com.cxcool.utils;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * Author: ph
 * Date: 2020/7/1
 * Time: 16:34
 * Description:ActiveMQ的消息消费者(Queue，1对1的消息)
 */
public class JmsConsumer {


    public static final String ACTIVE_MQ_URL = "tcp://10.10.10.2:61616";
    public static final String USER_NAME = "admin";
    public static final String USER_PASSWORD = "admin";
    public static final String QUEUE_NAME = "queue-cxcool-01";

    public static void main(String[] args) throws JMSException, IOException {

        System.out.println("我是3号消费者...");
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

        //5、创建消费者
        MessageConsumer messageConsumer = session.createConsumer(queue);
        /*同步阻塞方式(receive())
           订阅者或接收者调用MessageConsumer的receive()方法来接收消息,receive方法
           能够在接收消息之前(或超时之前)将一直阻塞
        while (true){
//            TextMessage textMessage = (TextMessage)messageConsumer.receive();
            TextMessage textMessage = (TextMessage)messageConsumer.receive(5000L);
            if(null!=textMessage){
                System.out.println("=====>消费者接收到消息:"+textMessage.getText());
            }else{
                break;
            }
        }

         */

        //通过监听的方式来接收消息
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (null != message && message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println("====》消费者接收到消息:" + textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        //用System.in.read()来保证messageConsumer有足够的时间监听到消息
        System.in.read();
        messageConsumer.close();
        session.close();
        connection.close();
        System.out.println("消息消费完成...");
    }


}
