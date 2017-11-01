package com.weatherpredict

case class Position(latitude: Double, longitude: Double, elevation: Double) {
  require(latitude >= -90.0 && latitude <= 90.0, "Range of latitude in degrees")
  require(longitude >= -180.0 && latitude <= 180.0, "Range of longitude in degrees")
  require(elevation >= 0.0 && elevation <= 8872.0, "Range of elevation in meters")

  override def toString = "%.2f,%.2f,%.2f".format(latitude, longitude, elevation)

}
