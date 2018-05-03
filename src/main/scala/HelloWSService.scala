import akka.actor._
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Source, Flow}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.ws.{TextMessage, Message}

trait HelloWSService {

  implicit val system: ActorSystem
  implicit val materializer: ActorMaterializer

  val helloWS =
    Flow[Message]
      .collect {
        case tm: TextMessage =>
          TextMessage(Source.single("Hello ") ++ tm.textStream)
      }

  val route =
    path("ws") {
      get {
        handleWebSocketMessages(helloWS)
      }
    }
}
