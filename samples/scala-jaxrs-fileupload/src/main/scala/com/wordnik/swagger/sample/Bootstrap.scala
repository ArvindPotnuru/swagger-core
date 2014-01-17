package com.wordnik.swagger.sample

import com.wordnik.swagger.jaxrs._
import com.wordnik.swagger.config._
import com.wordnik.swagger.model._

import javax.servlet.http.HttpServlet

class Bootstrap extends HttpServlet {
  val oauth = OAuth(
    List(
      AuthorizationScope("email", "Access to your email address"),
      AuthorizationScope("pets", "Access to your pets")),
    List(
      ImplicitGrant(
        LoginEndpoint("http://localhost:8002/oauth/dialog"),
        "access_code"
      ),
      AuthorizationCodeGrant(
        TokenRequestEndpoint("http://localhost:8002/oauth/requestToken",
          "client_id",
          "client_secret"),
        TokenEndpoint("http://localhost:8002/oauth/token",
          "access_code"
        )
    )
  ))
  val apikey = ApiKey("api_key", "header")
  ConfigFactory.config.authorizations = List(oauth, apikey, BasicAuth())

  val info = ApiInfo(
    title = "Swagger Sample App",
    description = """This is a sample server Petstore server.  You can find out more about Swagger 
    at <a href="http://swagger.wordnik.com">http://swagger.wordnik.com</a> or on irc.freenode.net, #swagger.  For this sample,
    you can use the api key "special-key" to test the authorization filters""", 
    termsOfServiceUrl = "http://helloreverb.com/terms/",
    contact = "apiteam@wordnik.com", 
    license = "Apache 2.0", 
    licenseUrl = "http://www.apache.org/licenses/LICENSE-2.0.html")

  ConfigFactory.config.info = Some(info)
}
