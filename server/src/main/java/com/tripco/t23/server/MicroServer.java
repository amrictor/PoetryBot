package com.tripco.t23.server;
import com.tripco.t23.planner.Bot;
import com.tripco.t23.planner.Write;
import spark.Request;
import spark.Response;

import spark.Spark;
import static spark.Spark.*;


/** A simple micro-server for the web.  Just what we need, nothing more.
 *
 */
public class MicroServer {

  private int    port;
  private String name;
  private String path = "/public/";
  public Bot b = new Bot();

  /** Creates a micro-server to load static files and provide REST APIs.
   *
   * @param port Which port to start the server on
   * @param name Name of the server
   */
  MicroServer(int port, String name) {
    this.port = port;
    this.name = name;

    port(port);

    // serve the static files: index.html and bundle.js
    Spark.staticFileLocation(this.path);
    get("/", (req, res) -> {
      res.redirect("index.html");
      return null;
    });

    // register all micro-services and the function that services them.
    // start with HTTP GET

    get("/echo", this::echo);
    // client is sending data, so a HTTP POST is used instead of a GET
    get("/write", this::write); //writes a poem

    System.out.println("\n\nServer running on port: " + this.port + "\n\n");
  }


  /** A REST API that echos the client request.
   *
   * @param request input tffi
   * @param response output tffi
   * @return returns an echo
   */
  private String echo(Request request, Response response) {

    response.type("application/json");
    response.header("Access-Control-Allow-Origin", "*");

    return HTTP.echoRequest(request);
  }

  /** A REST API to support trip planning.
   *
   * @param request input tffi
   * @param response output tffi
   * @return returns trip object
   */
  private String write(Request request, Response response) {
    response.type("application/json");
    response.header("Access-Control-Allow-Origin", "*");

    return new Write(request, b).getPoem();
  }

}