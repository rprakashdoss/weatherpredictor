package com.weatherpredict

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.tree.RandomForest
import org.apache.spark.mllib.tree.model.RandomForestModel
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.{ SparkConf, SparkContext }

import probability_monad.Distribution._

sealed trait TrainingData {

  val config = new ConfigReader
  val ROOT = config.filepath

}

sealed trait SparkBase {
  private val master = "local[*]"
  private val appName = this.getClass.getSimpleName

  val conf = new SparkConf().setMaster(master).setAppName(appName)
}

sealed trait Model {
  def build(training: RDD[LabeledPoint]): RandomForestModel
}

object Model {

  private class Condition extends Model {

    override def build(training: RDD[LabeledPoint]): RandomForestModel = {

      val numClasses = Condition.maxId
      val categoricalFeaturesInfo = Map[Int, Int]()
      val numTrees = 32
      val featureSubsetStrategy = "auto"
      val impurity = "gini"
      val maxDepth = 64
      val maxBins = 100
      val model = RandomForest.trainClassifier(training, numClasses, categoricalFeaturesInfo,
        numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins)

      model
    }
  }

  private class Sensor extends Model {

    override def build(training: RDD[LabeledPoint]): RandomForestModel = {

      val categoricalFeaturesInfo = Map[Int, Int]()
      val numTrees = 32
      val featureSubsetStrategy = "auto"
      val impurity = "variance"
      val maxDepth = 64
      val maxBins = 100
      val model = RandomForest.trainRegressor(training, categoricalFeaturesInfo,
        numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins)

      model
    }

  }

  def apply(modelName: String): Model = {
    if (modelName == "Condition") new Condition
    else new Sensor
  }

}

object Predictor extends SparkBase with TrainingData {
  val models = buildModels()

  def loadTrainingData(sc: SparkContext, fileName: String): RDD[LabeledPoint] = {
    val path = ROOT + fileName + ".txt"
    val data = MLUtils.loadLibSVMFile(sc, path)
    val splits = data.randomSplit(Array(0.8, 0.2), seed = 123L)
    val (trainingData, _) = (splits(0), splits(1))
    trainingData
  }

  def buildModels(): Map[String, RandomForestModel] = {
    val sc = new SparkContext(conf)
    val models = List(Temperature, Pressure, Humidity, Condition)
      .map(v => v.toString).map(m => m -> Model.apply(m).build(loadTrainingData(sc, m))).toMap
    sc.stop()
    models
  }

  def generateSample(localTime: LocalTime, position: Position): Map[String, Double] = {
    val noise = discreteUniform(-1 to 1).sample(1).head
    val dayOfWeek = localTime.localDateTime.getDayOfWeek.getValue + noise
    val newPoint = Vectors.dense(position.latitude, position.longitude, position.elevation, dayOfWeek)
    val predictions = models.map { case (k, v) => (k, v.predict(newPoint)) }
    predictions
  }

}
