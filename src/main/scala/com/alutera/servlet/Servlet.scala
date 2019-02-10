package com.alutera.servlet

import java.time.{Instant, LocalDateTime}
import java.time.format.DateTimeFormatter

import com.typesafe.config.{Config, ConfigFactory, ConfigObject}
import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}
import org.slf4j.LoggerFactory

class Servlet extends HttpServlet {
  private val logger = LoggerFactory.getLogger(getClass)
  private val DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
  val config: Config = ConfigFactory.load().getConfig("interactionType")

  override def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
    val interactionId = req.getParameter("interactionId")
    val operatorId = req.getParameter("operatorId").toInt
    val interactionType = convert(req.getParameter("interactionType"))
    //val interactionDate =LocalDateTime.parse(req.getParameter("interactionDate"), DATE_TIME_FORMATTER)
    val interactionDate =Instant.from(DATE_TIME_FORMATTER.parse(req.getParameter("interactionDate")))
    logger.debug(s"request param:\ninteractionId=$interactionId\n" +
      s"operatorId=$operatorId\ninteractionType=$interactionType\ninteractionDate=$interactionDate")
  }

  def convert(interactionType: String): Int = {
//    Settings.getSetting(interactionType)
    interactionType match {
      case "first" => 1
      case "second" => 2
    }
  }

  override def doPost(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
    println("POST: " + this.getClass.getName)
  }

}
