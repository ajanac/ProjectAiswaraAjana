package twitter


import org.apache.spark.deploy.SparkSubmit

object SparkSubmitWrapper {
  def main(args: Array[String]): Unit = {
    //launching application with spark submitt
    SparkSubmit.main(args)
  }
}
