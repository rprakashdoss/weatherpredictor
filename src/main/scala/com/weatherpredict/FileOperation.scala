package com.weatherpredict

import scala.io.Source
import java.io._

class FileOperation {

  def using[T <: Closeable, R](resource: T)(block: T => R): R = {
    try { block(resource) }
    finally { resource.close() }
  }

  def deleteFile(fileName: String) = {
    new File(fileName).delete
  }

}