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

You can always run rabbitmq on an AWS instance and connect to that remotely by:

Create a new AWS EC2 instance.

Add a security group with ssh port 22 and http port 80 open

SSH in to instance when running using:
ssh -i ~/.ssh/MyKeyPairName.pem ec2-user@ec2-xx-xx-xx-xx.eu-west-2.compute.amazonaws.com //this will be the public IP address given when instance is up and running in the AWS dashboard.

Run commands:
sudo yum update -y

Install docker
sudo yum install -y docker

Start the Docker service.
sudo service docker start

Pull latest rabbitmq docker image
sudo docker pull rabbitmq

run rabbitmq broker image using:
sudo docker run -d -p 80:5672 --hostname=my-broker rabbitmq //-d detatches docker container, -p exposes port 5672 inside the continer to 80 outside.  This makes the rabbitmq broker accessible by using host - ec2-xx-xx-xx-xx.eu-west-2.compute.amazonaws.com, and port 80 whch utilises the security group http port 80 we added earlier.




