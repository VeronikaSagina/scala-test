package com.alutera.servlet

import java.util.Map.Entry

import com.typesafe.config.{Config, ConfigValue}

import scala.collection.JavaConverters._

case class Settings(config: Config) {
  lazy val getSetting : Map[String, Int] = {
    val configObject = config.getObject("interactionType")
    (for {
      entry : Entry[String, ConfigValue] <- configObject.entrySet().asScala
      key = entry.getKey
      number = entry.getValue.unwrapped().asInstanceOf[Int]
    } yield (key, number)).toMap
  }
}
