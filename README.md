## Running

Run this using [sbt](http://www.scala-sbt.org/).  If you downloaded this project from http://www.playframework.com/download then you'll find a prepackaged version of sbt in the project directory:
Run the service:
```
sbt run
```

Run the tests:
```
sbt test
```

And then go to http://localhost:9000 to see the running web application.

Example of curl calls are:
```
curl -X GET -i http://localhost:9000/statistics
```
and
```
curl -d "sales_amount=2" -H "Content-Type: application/x-www-form-urlencoded" -X POST http://localhost:9000/sales -i
```
