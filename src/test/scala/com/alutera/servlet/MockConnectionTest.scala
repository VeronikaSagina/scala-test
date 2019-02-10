package com.alutera.servlet

import java.sql.{Connection, DriverManager, Statement}
import java.time.Instant

import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, FunSuite}

class MockConnectionTest extends FunSuite with BeforeAndAfter with BeforeAndAfterAll {
  var connection: Connection = _

  override protected def beforeAll(): Unit = Class.forName("org.postgresql.Driver")

  before {
    connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gen", "postgres", "postgres")
    connection.setAutoCommit(false)
  }

  after {
    if (connection != null) {
      connection.rollback()
      connection.close()
    }
  }

  test("MockConnection.insertPreparedStatement") {
    val stm = connection.createStatement()
    val countRows = rowCount(stm)
    MockConnection.insertPreparedStatement(
      connection, "test", 2, 2, Instant.parse("2007-12-03T10:15:30.00Z")
    )
    val result = rowCount(stm)
    assert(result == countRows + 1)
  }

  private def rowCount(stm: Statement): Int = {
    val rows = stm.executeQuery("select count(*) from wwe_logging_test")
    rows.next()
    rows.getInt(1)
  }

  test("test row inserted") {

    val stm = connection.createStatement()
    MockConnection.insertPreparedStatement(
      connection, "Another test", 2, 2, Instant.parse("2007-12-03T10:15:30.00Z")
    )
    val rows = stm.executeQuery("select * from wwe_logging_test where interaction_id = 'Another test'")
    rows.next()
    assert(rows.getString("interaction_id") == "Another test")
    assert(rows.getInt("operator_id") == 2)
    assert(rows.getInt("interaction_type") == 2)
    assert(rows.getTimestamp("interaction_date").toInstant == Instant.parse("2007-12-03T10:15:30.00Z"))
  }
}
