package mayton.compression;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.concurrent.TimeoutException;

import static mayton.compression.Constants.*;

public class Transmitter {

    static Logger logger = LoggerFactory.getLogger(Transmitter.class);

    public static void main(String[] args) throws IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {

        logger.info("START!");

        ConnectionFactory factory = new ConnectionFactory();

        logger.debug("Connection factory created");

        //factory.setUri("amqp://guest:guest@localhost:5672/virtualHost");



        try(Connection conn = factory.newConnection()) {
            logger.debug("Connection {} created", conn);

            Channel channel1 = conn.createChannel();
            Channel channel2 = conn.createChannel();

            logger.debug("Channel {} created", channel1);
            //logger.debug("Channel {} created", channel2);

            AMQP.Exchange.DeclareOk exchangeDeclareResult = channel1.exchangeDeclare(EXCHANGE_NAME, "direct", true);

            logger.debug("Exchange {} declared with result = {}", EXCHANGE_NAME, exchangeDeclareResult.toString());

            AMQP.Queue.DeclareOk queueDeclareResult = channel1.queueDeclare(QUEUE_NAME, false, true, true, Collections.EMPTY_MAP);

            logger.debug("Queue {} declared with result = {}", QUEUE_NAME, queueDeclareResult);

            AMQP.Queue.BindOk bindingRes = channel1.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

            logger.debug("Queue name = {} binded to exchange {} with routing key {}", QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

            for (int i = 0; i < 10; i++) {
                String messageBodyBytes = String.format("%d -> %d", i, i + 1);
                channel2.basicPublish(
                        EXCHANGE_NAME,
                        ROUTING_KEY,
                        new AMQP.BasicProperties.Builder()
                                .contentType("text/plain")
                                //.expiration("60_000")
                                //.deliveryMode(2)
                                //.priority(1)
                                //.userId("bob")
                                .build(),
                        messageBodyBytes.getBytes());
                logger.info("Message sent {}", messageBodyBytes);
            }

            channel1.close();
            channel2.close();

            logger.debug("Channels closed");

        } catch (Exception ex) {
            logger.error("General exception",ex);
        }

        logger.info("FINISH!");
    }
}
