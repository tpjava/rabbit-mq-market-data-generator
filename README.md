# rabbit-mq-market-data-generator

Simple random market data generator which creates channels for each provided stock name and randomly generates prices.

Build from root folder by running:

mvn clean install

Run with

mvn exec:java -Dexec.args="ERICSSON VODAFONE ASTRA BT"

where exec.args is a list of stocks separated by spaces that you want to create channels and prices for

The receiver program can then subscribe to one or many of these channes to receive the prices.

https://github.com/tpjava/rabbit-mq-market-data-receiver

You will need to have a rabbit-mq broker running to facilitate all messaging.  The easiest way to do this is using Docker.

Simply with docker running, run command:

docker pull rabbitmq

This will now appear in your docker images, check by running command: 

docker images

To run the image use:

docker run -d -p 5672:5672 --hostname=my-broker rabbitmq

-d detaches the process
-p exposes the port 5672 outside of the docker container, making it accessible to the rabbit-mq-market-data-generator above

You can also install rabbit-mq broker on to your machine but docker takes away a lot of the grunt work.
