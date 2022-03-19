# java-minimal-api-server

Groovy script for running a minimal REST api server

## Description

This groovy script responds to the "/" endpoint and parses GET and POST parameters and Request BODY.

It is trivial to modify to handle new contexts and new HTTP options.

## Dependencies

```
sudo apt install openjdk-8-jdk groovy npm
sudo npm install -g loadtest

```

## Usage

```
#Start Service

groovy HttpServerMinimal.groovy

#Test GET without Parameters
curl http://localhost:8080
{"app":"Minimal Api server","time":"2022-03-19T08:39:11+0000","params":{},"body":null}

#Test GET with query parameter
curl "http://localhost:8080?test=one&test2=two"
{"app":"Minimal Api server","time":"2022-03-19T08:38:41+0000","params":{"test":"one","test2":"two"},"body":null}

#Test POST
curl -X POST -d "test3=three&test4=four" "http://localhost:8080?test=one&test2=two"
{"app":"Minimal Api server","time":"2022-03-19T08:40:13+0000","params":{"test":"one","test2":"two","test3":"three","test4":"four"},"body":null}

#Test POST with body
curl -H "Content-Type:application/data" -X POST -d '{"mydata1":"value1","mydata2":"value2"}' "http://localhost:8080?test=one&test2=two"
{"app":"Minimal Api server","time":"2022-03-19T08:41:52+0000","params":{"test":"one","test2":"two"},"body":"{\"mydata1\":\"value1\",\"mydata2\":\"value2\"}"}

#Test Performance
loadtest -c 100 -n 500 -H "Content-Type:application/data" -m POST --data '{"mydata1":"value1","mydata2":"value2"}' "http://localhost:8080?test=one&test2=two"
headers: object, {"host":"localhost:8080","user-agent":"loadtest/5.1.2","accept":"*/*","content-type":"application/data"}
[Sat Mar 19 2022 11:45:10 GMT+0300 (East Africa Time)] INFO Requests: 0 (0%), requests per second: 0, mean latency: 0 ms
[Sat Mar 19 2022 11:45:11 GMT+0300 (East Africa Time)] INFO 
[Sat Mar 19 2022 11:45:11 GMT+0300 (East Africa Time)] INFO Target URL:          http://localhost:8080?test=one&test2=two
[Sat Mar 19 2022 11:45:11 GMT+0300 (East Africa Time)] INFO Max requests:        500
[Sat Mar 19 2022 11:45:11 GMT+0300 (East Africa Time)] INFO Concurrency level:   100
[Sat Mar 19 2022 11:45:11 GMT+0300 (East Africa Time)] INFO Agent:               none
[Sat Mar 19 2022 11:45:11 GMT+0300 (East Africa Time)] INFO 
[Sat Mar 19 2022 11:45:11 GMT+0300 (East Africa Time)] INFO Completed requests:  500
[Sat Mar 19 2022 11:45:11 GMT+0300 (East Africa Time)] INFO Total errors:        0
[Sat Mar 19 2022 11:45:11 GMT+0300 (East Africa Time)] INFO Total time:          0.8190091469999999 s
[Sat Mar 19 2022 11:45:11 GMT+0300 (East Africa Time)] INFO Requests per second: 610
[Sat Mar 19 2022 11:45:11 GMT+0300 (East Africa Time)] INFO Mean latency:        150.6 ms
[Sat Mar 19 2022 11:45:11 GMT+0300 (East Africa Time)] INFO 
[Sat Mar 19 2022 11:45:11 GMT+0300 (East Africa Time)] INFO Percentage of the requests served within a certain time
[Sat Mar 19 2022 11:45:11 GMT+0300 (East Africa Time)] INFO   50%      148 ms
[Sat Mar 19 2022 11:45:11 GMT+0300 (East Africa Time)] INFO   90%      195 ms
[Sat Mar 19 2022 11:45:11 GMT+0300 (East Africa Time)] INFO   95%      205 ms
[Sat Mar 19 2022 11:45:11 GMT+0300 (East Africa Time)] INFO   99%      224 ms
[Sat Mar 19 2022 11:45:11 GMT+0300 (East Africa Time)] INFO  100%      243 ms (longest request)


```

