package controllers.swagger

import com.google.inject.Inject
import com.iheart.playSwagger.SwaggerSpecGenerator
import play.api.mvc.InjectedController

import scala.concurrent.{ExecutionContext, Future}

class ApiSpecs @Inject()() (implicit ctx: ExecutionContext) extends InjectedController {

  implicit val cl = getClass.getClassLoader

  private lazy val generator = SwaggerSpecGenerator(false, "forms", "models")

  def specs = Action.async { _ =>
      Future.fromTry(generator.generate()).map(Ok(_))
  }

}