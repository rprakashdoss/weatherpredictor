package com.weatherpredict

import java.io.File
import com.typesafe.config.ConfigFactory
import scala.util.Properties

class ConfigReader {
  val config = ConfigFactory.parseFile(new File("src/main/resources/weatherpredict.conf"))
  val filepath = config.getString("myapp.weather.filepath")
  val output_file = config.getString("myapp.weather.output_file")
  val input_file = config.getString("myapp.weather.input_file")
  val numOfLocations = config.getString("myapp.weather.numOfLocations")

}