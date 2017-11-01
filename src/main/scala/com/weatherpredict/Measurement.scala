package com.weatherpredict

import com.weatherpredict.Condition.Condition

case class Measurement(
    position: Position, localTime: LocalTime,
    location: Option[String] = Option("Unknown"),
    condition: Option[Condition] = Option(Condition.Unknown),
    temperature: Option[Temperature] = Option(Temperature()),
    pressure: Option[Pressure] = Option(Pressure()),
    humidity: Option[Humidity] = Option(Humidity())) {
  measurement =>

  def emit(): Measurement = {
    require(measurement.condition.get.equals(Condition.Unknown), "Measurement has weather data already")
    require(measurement.temperature.get.value.isNaN, "Measurement has weather data already")
    require(measurement.pressure.get.value.isNaN, "Measurement has weather data already")
    require(measurement.humidity.get.value.isNaN, "Measurement has weather data already")

    val sample = Predictor.generateSample(localTime, position)
    val condition = Option(Condition.apply(sample("Condition").toInt))
    val temperature = Option(Temperature(sample("Temperature")))
    val pressure = Option(Pressure(sample("Pressure")))
    val humidity = Option(Humidity(sample("Humidity")))

    val mappedLocation = LocationPosition.map.map(_.swap).get(position).orElse(location)
    Measurement(position, localTime, mappedLocation, condition, temperature, pressure, humidity)
  }

  override def toString = {
    "%s|%s|%s|%s|%+.2f|%.2f|%.2f".format(location.get, position.toString, localTime.timeStamp,
      condition.get, temperature.get.value, pressure.get.value, humidity.get.value)
  }

}
