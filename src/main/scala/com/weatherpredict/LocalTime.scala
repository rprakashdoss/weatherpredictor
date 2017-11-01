package com.weatherpredict

import java.time.LocalDate.parse
import java.time.format.DateTimeFormatter

case class LocalTime(timeStamp: String) {
  val localDateTime = parse(timeStamp, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
}
