package com.weatherpredict

import java.io._

case class Output(result: Measurement) {

  val config = new ConfigReader
  val filepath = config.filepath
  val output_file = config.output_file

  val fileop = new FileOperation

  //print  result in console
  println(result.toString())

  //write result in text file
  fileop.using(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filepath + output_file, true)))) {

    writer =>

      writer.write(result.toString())
      writer.newLine()
      writer.flush();
  }

  //clean the temp files

  fileop.deleteFile(filepath + "Condition.txt")
  fileop.deleteFile(filepath + "Pressure.txt")
  fileop.deleteFile(filepath + "Humidity.txt")
  fileop.deleteFile(filepath + "Temperature.txt")

}