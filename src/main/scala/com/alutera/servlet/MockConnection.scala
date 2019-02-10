package com.alutera.servlet

import java.sql.{Connection, DriverManager, PreparedStatement, SQLException, Statement, Timestamp}
import java.time.Instant

import org.slf4j.LoggerFactory

object MockConnection {
  private val JDBC_DRIVER = "org.postgresql.Driver"
  private val logger = LoggerFactory.getLogger(getClass)

  @throws[ClassNotFoundException]
  def main(args: Array[String]): Unit = {
    Class.forName(JDBC_DRIVER)
    val connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gen", "postgres", "postgres")
    try {
      insert(connection, "interId", 43, 32, Instant.now())
      insertPreparedStatement(connection, "interId", 43, 32, Instant.now())
    } catch {
      case e: Exception =>
        e.printStackTrace()
    } finally if (connection != null) connection.close()
  }


  @throws[SQLException]
   def insert(connection: Connection): Unit = {
    val statement: Statement = connection.createStatement
    try {
      statement.executeUpdate(
        "insert into WWE_LOGGING_TEST (INTERACTION_ID, OPERATOR_ID, INTERACTION_TYPE, INTERACTION_DATE)" +
          "VALUES ('someId', 123, 123, CURRENT_DATE)"
      )
    } finally {
      if (statement != null) statement.close()
    }
  }

  @throws[SQLException]
  def insert(connection: Connection, interactionId: String, operatorId: Int, interactionType: Int, interactionDate: Instant): Unit = {
    logger.debug(s"request param:\ninteractionId=$interactionId\n" +
      s"operatorId=$operatorId\ninteractionType=$interactionType\ninteractionDate=$interactionDate")
    val statement: Statement = connection.createStatement
    val str = "insert into WWE_LOGGING_TEST (INTERACTION_ID, OPERATOR_ID, INTERACTION_TYPE, INTERACTION_DATE) " +
      s"VALUES ('$interactionId', $operatorId, $interactionType, '${Timestamp.from(interactionDate)}')"
    println(str)
    try {
      statement.executeUpdate(str)
    } finally {
      if (statement != null) statement.close()
    }
  }

  @throws[SQLException]
  def insertPreparedStatement(connection: Connection, interactionId: String, operatorId: Int, interactionType: Int, interactionDate: Instant): Int = {
    logger.debug(s"request param:\ninteractionId=$interactionId\n" +
      s"operatorId=$operatorId\ninteractionType=$interactionType\ninteractionDate=$interactionDate")

    val statement: PreparedStatement = connection.prepareStatement("insert into WWE_LOGGING_TEST (INTERACTION_ID, OPERATOR_ID, INTERACTION_TYPE, INTERACTION_DATE) VALUES(?,?,?,?)")
    try {
      statement.setString(1, interactionId)
      statement.setInt(2, operatorId)
      statement.setInt(3, interactionType)
      statement.setTimestamp(4, Timestamp.from(interactionDate))
      statement.executeUpdate()
    } finally {
      if (statement != null) statement.close()
    }
  }

}
