/*
 * Copyright (C) 2009-2014 Typesafe Inc. <http://www.typesafe.com>
 */

package akka.http.server.japi.directives

import akka.http.server.japi.impl.RouteStructure
import akka.http.server.japi.{ Coder, Directive, Directives, Route }

import scala.annotation.varargs

trait CodingDirectives {
  /**
   * Wraps the inner routes with encoding support. The response will be encoded
   * using one of the predefined coders, `Gzip`, `Deflate`, or `NoCoding` depending on
   * a potential [[akka.http.model.japi.headers.AcceptEncoding]] header from the client.
   */
  @varargs def encodeResponse(innerRoutes: Route*): Route =
    // FIXME: make sure this list stays synchronized with the Scala one
    RouteStructure.EncodeResponse(List(Coder.NoCoding, Coder.Gzip, Coder.Deflate), innerRoutes.toVector)

  /**
   * A directive that Wraps its inner routes with encoding support.
   * The response will be encoded using one of the given coders with the precedence given
   * by the order of the coders in this call.
   *
   * In any case, a potential [[akka.http.model.japi.headers.AcceptEncoding]] header from the client
   * will be respected (or otherwise, if no matching .
   */
  @varargs def encodeResponse(coders: Coder*): Directive =
    Directives.custom(RouteStructure.EncodeResponse(coders.toList, _))
}