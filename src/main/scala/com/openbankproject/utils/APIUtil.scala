package com.openbankproject.utils

object APIUtil {
  /*
   Return the git commit. If we can't for some reason (not a git root etc) then log and return ""
  */
  def gitCommit: String = {
    val commit = try {
      val properties = new java.util.Properties()
      properties.load(getClass().getClassLoader().getResourceAsStream("git.properties"))
      properties.getProperty("git.commit.id", "")
    } catch {
      case e: Throwable => { // Return empty string
        ""
      }
    }
    commit
  }
}
