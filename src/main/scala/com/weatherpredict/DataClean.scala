package com.weatherpredict

import scala.io.Source
import java.io._

case class DataClean() {

  val config = new ConfigReader
  val filepath = config.filepath
  val output_file = config.output_file
  val input_file = config.input_file

  val fileop = new FileOperation

  //delete old output file if any
  fileop.deleteFile(filepath + output_file)

  //From input file filter the stale datas and split files to various temp files for analysis
  val bufferedSource = Source.fromFile(filepath + input_file)
  val lines = bufferedSource.getLines.map(row => row.split(",")).filter(cols => cols(3).toFloat < 9999 && cols(4).toFloat < 9999 && cols(5).toFloat < 9999).toList

  fileop.using(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filepath + "Condition.txt")))) {
    writer =>
      for (x <- lines) {

        writer.write(x(14) + " 1:" + x(6) + " 2:" + x(7) + " 3:" + x(8))
        writer.newLine()
        writer.flush();
      }
  }

  fileop.using(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filepath + "Humidity.txt")))) {
    writer =>

      for (x <- lines) {

        writer.write(x(3) + " 1:" + x(6) + " 2:" + x(7) + " 3:" + x(8))
        writer.newLine()
        writer.flush();
      }
  }

  fileop.using(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filepath + "Pressure.txt")))) {
    writer =>

      for (x <- lines) {

        writer.write(x(4) + " 1:" + x(6) + " 2:" + x(7) + " 3:" + x(8))
        writer.newLine()
        writer.flush();
      }
  }

  fileop.using(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filepath + "Temperature.txt")))) {
    writer =>

      for (x <- lines) {

        writer.write(((x(2).toFloat - 32) * 0.55) + " 1:" + x(6) + " 2:" + x(7) + " 3:" + x(8))
        writer.newLine()
        writer.flush();
      }
  }

  bufferedSource.close

}