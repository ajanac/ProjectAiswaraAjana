# README #

This repository is for the Scala implementation of analysis of tweets using Spark. This project predicts the sentiment of input tweets using a classifier and prints out the accuracy of the classifier.

### What is this repository for? ###

About the project: We retrieve the tweets from twitter and analyse the sentiment of each tweets by using machine learning algorithms by building classifiers. We can use any classification algorithms like Naive Bayes, Decision tree Induction, etc.
Here, a set of tweets are fetched into the classifier to do the sentiment analysis, This is the training phase. In the testing phase, new twitter tweets are learned based on the classification done on training tweets.
In this project, we have used Spark for streaming the twitter data and Spark has some Machine learning libraries to do analysis on the data, specifically MlLib. Some natural language processing libraries like Stanford NLP are also used for analysing the tweets. It makes use og LingPipe API for text classification.

### How do I get set up? ###

* Summary of set up

1. First download HDP Sandbox 2.5 from Hortonworks for VMWare and import this into VMWare player. This is the single node instance of Spark cluster.
2. We start the sandbox and then we are ready to work in Spark on top of Hadoop.
3. We worked in IntelliJ IDE for Scala project with Maven build. All dependencies are written in pom.xml file
4. Copy the tweets to HDFS in Sandbox
5. Also, we need to convert the scala program to .jar files wich need to be run in SPark submit command.
6. We need to get the credentials for Twitter OAuth: https://apps.twitter.com/

* Configuration

We referred to the website for configurations: http://hortonworks.com/hadoop-tutorial/a-lap-around-apache-spark/


* Dependencies
Pom.xml gives all the dependencies used.

* How to run tests
1.Copy the data to spark cluster 
hadoop fs -put tweets.csv /tweets
tweets.csv is the training tweets, we have provided the file here

2.twitterANA
mvn clean package 
We can create jar file of ou scala program using Cygwin
 This creates .jar file including all the dependencies in the target folder of project.

3.spark-submit --master yarn-client \
               --driver-memory 1g \
               --executor-memory 2g \
               target/mavenscala-1.0-SNAPSHOT-jar-with-dependencies.jar \
               hdfs//tweets.csv
               trainedModel

4. spark-submit \
    --class twitter.Predict \
    --master yarn-client \
    --num-executors 2 \
    --executor-memory 512m \
    --executor-cores 2 \
    target/mavenscala-1.0-SNAPSHOT-jar-with-dependencies.jar \
    trainedModel \ 
    --consumerKey {your Twitter consumer key} \
    --consumerSecret {your Twitter consumer secret} \
    --accessToken {your Twitter access token} \
    --accessTokenSecret {your Twitter access token secret}

By this command, it logs in to twitter with the credentials and fetch live tweets.
### Who do I talk to? ###

* Aishwarya Vijayan, Ajana Sathian
