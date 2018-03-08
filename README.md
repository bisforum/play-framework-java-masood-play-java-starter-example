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

Example of Load Test with Apache Benchmark (make a post_loc.txt file with JSON insude)
```
ab -p post_loc.txt -T application/json -c 10 -n 200 http://localhost:9000/add
```
```
ab -T application/json -c 10 -n 200 http://localhost:9000/statistics
```

  ## To Do:
  - Get timeWindow from Configuration
  - Dequeue the queue by a job so it won't get long or use other queue like circular queue
  - There is no logging for debug purpose
