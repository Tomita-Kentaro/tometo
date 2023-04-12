package twitter4j.examples
import twitter4j.TwitterFactory
import twitter4j.StatusUpdate
import twitter4j.auth.AccessToken
import java.sql.DriverManager.println

fun main() {
    val twitter = TwitterFactory().getInstance()
    twitter.setOAuthConsumer("2hbsBTxCbVtQdpWSMvtI7lomu", "AgOLkQqRnZB10ZWjLMgEN3R7Iy1OkgFf7REKofKYjasDS24Bp9")
    twitter.oAuthAccessToken = AccessToken("1643042925033705472-ezdP2kGAr1NqImIqErktm9mWePvquk", "4xYUTK9EdzCK60bHiUxKQJgixVJUA5mIwIG2wfsfjbVcQ")
    val status = StatusUpdate("Hello, Twitter!")
    val result = twitter.updateStatus(status)
    println("Successfully updated the status to ${result.text}")
}
