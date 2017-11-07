package com.perrin.tony;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;

/**
 * Created by tonyperrin on 05/11/2017.
 */
public class MainClass {
    private static final Random random = new Random();
    private static final String HOST = "ec2-xx-xx-xx-xx.eu-west-2.compute.amazonaws.com";
    private static final int PORT = 80;

    public static void main(String[] args) throws Exception {
        Connection connection = initMarketQuoter();
        Stream.of(args).forEach(t -> {
            generateStockQuotes(t, connection);
        });
    }

    private static Connection initMarketQuoter() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setPort(PORT);
        return factory.newConnection();
    }

    private static void generateStockQuotes(final String queueName, Connection connection) {
        //create new thread to publish new quotes
        //create random start price.
        final double price = (random.nextInt(80_000) + 1000) / 100;
        try {
            final Channel channel = connection.createChannel();
            channel.queueDeclare(queueName, false, false, false, null);
            new Thread(() -> {
                double p = price;
                while(true) {
                    try {
                        //generate random sleep time in milliseconds
                        Thread.sleep(generateNextSleep());
                        //set price
                        p = generateNextPrice(p);
                        //set message
                        String message = String.format("%s : %.2f", queueName, p);
                        //send message to correct queue
                        channel.basicPublish("", queueName, null, message.getBytes());
                        System.out.println(Thread.currentThread().getName() + " sent '" + message + "'");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long generateNextSleep() {
        return random.nextInt(2000);  //random Thread sleep time until next price update.
    }

    private static double generateNextPrice(double price) {
        boolean isUp = random.nextBoolean(); //true or false - next price either + 0.1 or + -0.1
        return price + (isUp ? 0.1 : -0.1);
    }
}
