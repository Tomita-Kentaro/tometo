import twitter4j.*
import java.sql.DriverManager.println

fun main() {
    val twitter = Twitter.newBuilder()
        .oAuthConsumer("2hbsBTxCbVtQdpWSMvtI7lomu", "AgOLkQqRnZB10ZWjLMgEN3R7Iy1OkgFf7REKofKYjasDS24Bp9")
        .oAuthAccessToken("1643042925033705472-ezdP2kGAr1NqImIqErktm9mWePvquk", "4xYUTK9EdzCK60bHiUxKQJgixVJUA5mIwIG2wfsfjbVcQ")
        .build()
    val result = twitter.v1().tweets().updateStatus("Hello, Twitter!")
    println("Successfully updated the status to ${result.text}")
}
