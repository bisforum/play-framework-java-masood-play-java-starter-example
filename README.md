[<img src="https://img.shields.io/travis/playframework/play-java-starter-example.svg"/>](https://travis-ci.org/playframework/play-java-starter-example)

# play-java-starter-example

This is a starter application that shows how Play works.  Please see the documentation at https://www.playframework.com/documentation/latest/Home for more details.

## Running

Run this using [sbt](http://www.scala-sbt.org/).  If you downloaded this project from http://www.playframework.com/download then you'll find a prepackaged version of sbt in the project directory:

```
sbt run
```

And then go to http://localhost:9000 to see the running web application.

Example of curl calls are:
```
curl -X GET -H 'Content-Type: application/json' -i http://localhost:9000/statistics
```
and
```
curl -X POST -H 'Content-Type: application/json' -i http://localhost:9000/add --data '{"sales_amount":"2"}'
```

Example of Load Test with Apache Benchmark
```
ab -p post_loc.txt -T application/json -c 10 -n 200 http://localhost:9000/add
```
```
ab -T application/json -c 10 -n 200 http://localhost:9000/statistics
```
## Controllers

There are several demonstration files available in this template.

- HomeController.java:

  Shows how to handle simple HTTP requests.

- AsyncController.java:

  Shows how to do asynchronous programming when handling a request.

- CountController.java:

  Shows how to inject a component into a controller and use the component when
  handling requests.

## Components

- Module.java:

  Shows how to use Guice to bind all the components needed by your application.

- Counter.java:

  An example of a component that contains state, in this case a simple order.

- ApplicationTimer.java:

  An example of a component that starts when the application starts and stops
  when the application stops.

## Filters

- ExampleFilter.java

  A simple filter that adds a header to every response.
