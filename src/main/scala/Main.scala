import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import com.typesafe.config.ConfigFactory

object Main extends App with HelloWSService {

  override implicit val system = ActorSystem("hello-ws")
  override implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  val config = ConfigFactory.load()

  val host = config.getString("ws.host")
  val port = config.getInt("ws.port")

  val http = Http(system).bindAndHandle(route, host, port)

}
