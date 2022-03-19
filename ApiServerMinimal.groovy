import com.sun.net.httpserver.HttpServer
import com.sun.net.httpserver.HttpExchange;
import java.net.InetSocketAddress
import java.util.concurrent.Executors;
import java.net.URLDecoder;
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
 
Map getQueryParams(String url, Boolean isGet=true)
{
    if(isGet){
        if(url.size()==1) return [:]
        url = url.substring(2)
    }
    def queryParams = url?.split('&')
    def r = queryParams.collectEntries { param -> param.split('=').collect { URLDecoder.decode(it) }}
    return r
}

String getBody(HttpExchange exchange)
{
    StringBuilder sb = new StringBuilder();
    InputStream ios = exchange.getRequestBody();
    byte [] data = new byte[1024]
    int read = ios.read(data)
    data = Arrays.copyOf(data,read)

    return new String(data)
}

Map getParams(HttpExchange exchange)
{
    def queryParams = getQueryParams(exchange.getRequestURI().toString());
    
    String requestMethod = exchange.getRequestMethod();
    if (requestMethod.equalsIgnoreCase('POST')) {

       def headers = exchange.getRequestHeaders()
       def contentType = headers.get("Content-Type")

       def body = getBody(exchange)
       if(contentType.contains("application/x-www-form-urlencoded")){
         def postParams = getQueryParams(body,false)
         return queryParams + postParams;
       }
       else 
       { 
           queryParams.put("__body__",body)
           return queryParams 
       }
    }

    return queryParams;
}

String getBody(Map params)
{
    return params.remove("__body__")
}

String toJson(Map body){

    return JsonOutput.toJson(body)
}

def server = HttpServer.create(new InetSocketAddress("0.0.0.0", 8080), 0)
server.setExecutor(Executors.newFixedThreadPool(10));
server.createContext("/",  { HttpExchange exchange ->
    try{

        Map params = getParams(exchange)
        String body = getBody(params)
        
        def responseBody =  [app: "Minimal Api server", 
                    time: new Date(), 
                    params: params,
                    body: body]

        responseBody = toJson(responseBody)

        def responseHeaders = exchange.getResponseHeaders();
        responseHeaders.set('Content-Type', 'application/json');
        exchange.sendResponseHeaders(200, responseBody.length())
        exchange.responseBody.write(responseBody.bytes)
    } catch (Exception e)
    {
        e.printStackTrace()
    }
    exchange.close()
})
server.start()