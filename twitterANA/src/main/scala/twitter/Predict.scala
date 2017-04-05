package twitter


import org.apache.spark.SparkConf
import org.apache.spark.mllib.classification.NaiveBayesModel
import org.apache.spark.streaming.twitter._
import org.apache.spark.streaming.{Seconds, StreamingContext}

/*
Object Predict Pulls live tweets and predicts the sentiment.*/

object Predict {
  def main(args: Array[String]) {
    if (args.length < 1) {
      System.err.println("Usage: " + this.getClass.getSimpleName + " <modelDirectory> ")
      System.exit(1)// Abnormal execution o
    }

    val Array(modelFile) =
      Utils.parseCommandLineWithTwitterCredentials(args)

    println("Initializing Streaming Spark Context...")
    //Configuration for a Spark application
    val conf = new SparkConf().setAppName(this.getClass.getSimpleName)
    //accepting and processing high throughput data streams
    val ssc = new StreamingContext(conf, Seconds(5))

    println("Initializing Twitter stream...")
      //Create a input stream that returns tweets received from Twitter.
    val tweets = TwitterUtils.createStream(ssc, Utils.getAuth)
    // Filter the tweets based on the language english
    val statuses = tweets.filter(_.getLang == "en").map(_.getText)

    println("Initalizaing the Naive Bayes model...")
    //Model for Naive Bayes Classifiers.
    val model = NaiveBayesModel.load(ssc.sparkContext, modelFile.toString)

    val labeled_statuses = statuses
      .map(t => (t, model.predict(Utils.featurize(t))))

    labeled_statuses.print()

    // Start the streaming process
    println("Initialization complete.")
    ssc.start()
    ssc.awaitTermination()
  }
}
