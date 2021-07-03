package com.kc.lmsng.config;

import com.kc.lmsng.Entity.MyListener;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.jboss.ejb3.annotation.ResourceAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.support.destination.JndiDestinationResolver;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.stereotype.Component;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.*;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import org.jboss.ejb3.annotation.ResourceAdapter;
import javax.naming.Context;
import javax.naming.NamingException;
import java.util.Properties;

//@Configuration
//@AllArgsConstructor
//@NoArgsConstructor
@MessageDriven(name="JmsConfig", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType",propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "useJNDI", propertyValue = "false"),
        @ActivationConfigProperty(propertyName = "hostName", propertyValue = "localhost"),
        @ActivationConfigProperty(propertyName = "port", propertyValue = "1414"),
        @ActivationConfigProperty(propertyName = "channel", propertyValue = "DEV.APP.SVRCONN"),
        @ActivationConfigProperty(propertyName = "queueManager", propertyValue = "QM1"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "DEV.QUEUE.1"),
        @ActivationConfigProperty(propertyName = "transportType", propertyValue = "CLIENT")
})
@ResourceAdapter(value = "wmq.jmsra.rar")
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class JmsConfig implements MessageListener {

//        private static final Logger LOGGER = Logger.getLogger(WebSphereMQMDB.class.toString());
//
    /**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message rcvMessage) {
        TextMessage msg = null;
        if (rcvMessage instanceof TextMessage) {
            msg = (TextMessage) rcvMessage;
            System.out.println("msg is " + msg);
//                    LOGGER.info("Received Message from queue: " + msg.getText());
        } else {
//                    LOGGER.warning("Message of wrong type: " + rcvMessage.getClass().getName());
        }
    }
}

//    private static final String INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
//    private static final String INITIAL_CONTEXT_FACTORY = "org.wildfly.naming.client.WildFlyInitialContextFactory";
//    private static final String CONNECTION_FACTORY = "jboss/myconnection";
//
//    @Bean
//    public ConnectionFactory connectionFactory() {
//        try {
//            System.out.println("Retrieving JMS queue with JNDI name: " + CONNECTION_FACTORY);
//            JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
//            jndiObjectFactoryBean.setJndiName(CONNECTION_FACTORY);
//
//            jndiObjectFactoryBean.setJndiEnvironment(getEnvProperties());
//            jndiObjectFactoryBean.afterPropertiesSet();
//
//            return (QueueConnectionFactory) jndiObjectFactoryBean.getObject();
//
//        } catch (NamingException e) {
//            System.out.println("Error while retrieving JMS queue with JNDI name: [" + CONNECTION_FACTORY + "]");
//        } catch (Exception ex) {
//            System.out.println("Error");
//        }
//        return null;
//    }
//
//    Properties getEnvProperties() {
//        Properties env = new Properties();
//        env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
////        env.put(Context.PROVIDER_URL, brokerUrl);
//        return env;
//    }
//
//    @Bean
//    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory) {
//
//        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//        JndiDestinationResolver jndiDestinationResolver = new JndiDestinationResolver();
//
//        jndiDestinationResolver.setJndiEnvironment(getEnvProperties());
//        factory.setDestinationResolver(jndiDestinationResolver);
//        return factory;
//    }

//    @Bean
//    public DefaultMessageListenerContainer orderMessageListenerContainer(ConnectionFactory connectionFactory) {
//        DefaultMessageListenerContainer endpoint = new DefaultMessageListenerContainer();
//        endpoint.setMessageListener(new MyListener());
//        endpoint.setDestination("DEV.QUEUE.1");
//        endpoint.setConnectionFactory(connectionFactory);
//        return orderDefaultJmsListenerContainerFactory().createListenerContainer(endpoint);
//    }
//}
