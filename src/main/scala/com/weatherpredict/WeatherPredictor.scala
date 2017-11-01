package com.weatherpredict

import java.time.LocalDateTime
import java.time.format.{ DateTimeFormatter, DateTimeParseException }

import util.Random.nextInt
import scala.collection.mutable.LinkedHashSet

object WeatherPredictor {

  def main(args: Array[String]): Unit = {

    val config = new ConfigReader

    val numOfLocations = config.numOfLocations

    System.setProperty("hadoop.home.dir", "C:\\hadoop-common-2.2.0-bin-master")

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    val startDate = LocalDateTime.now()

    println(" Random Weather predictor --> random locations for next few days!!!")

    val randList = LinkedHashSet(numOfLocations.toInt)
    for (x <- 0 until numOfLocations.toInt) yield randList.add(nextInt(LocationPosition.map.size))

    val locationIndex = randList.toList

    val locations = locationIndex.map(i => LocationPosition.map.toIndexedSeq.apply(i))

    DataClean();

    locations.indices.foreach(i => {
      val timeStamp = startDate.plusDays(i)
      val formattedDateTime = timeStamp.format(formatter)
      val position = locations.apply(i)._2
      val result = Measurement(position, LocalTime(formattedDateTime)).emit();
      Output(result)

    })

  }

}
