package mayton.compression;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static mayton.compression.Constants.QUEUE_NAME;

public class Receiver {

    static Logger logger = LoggerFactory.getLogger(Receiver.class);

    public static void main(String[] args) {
        logger.info("START!");

        ConnectionFactory factory = new ConnectionFactory();

        logger.debug("Connection factory created");

        //factory.setUri("amqp://guest:guest@localhost:5672/virtualHost");


        try(Connection conn = factory.newConnection()) {
            logger.debug("Connection {} created", conn);
            Channel channel1 = conn.createChannel();

            logger.debug("Channel {} created", channel1);

            AMQP.Queue.DeclareOk response = channel1.queueDeclarePassive(QUEUE_NAME);

            /*// returns the number of messages in Ready state in the queue
            int msgCnt = response.getMessageCount();
            logger.info("Messages count = {}", msgCnt);
            // returns the number of consumers the queue has
            int consCnt = response.getConsumerCount();
            logger.info("consumer count = {}", consCnt);*/

            boolean autoAck = false;
            /*GetResponse response = channel1.basicGet(queueName, autoAck);
            if (response == null) {
                // No message retrieved.
            } else {
                AMQP.BasicProperties props = response.getProps();
                byte[] body = response.getBody();
                long deliveryTag = response.getEnvelope().getDeliveryTag();
            }*/

        } catch (TimeoutException e) {
            logger.error("Timeout", e);
        } catch (IOException e) {
            logger.error("IO exception", e);
        }

    }

}
