# Readme #

## General Overview ##
__Backend__  
Simple <a href="http://sparkjava.com/">spark java</a> web application using java8 and a little bit of functional programming (see <a href="http://www.javaslang.io/">JavaSlang</a>).

__Frontend__  
Single Page Application build with <a href="https://www.polymer-project.org/1.0/">Polymer</a>.  
<a href="https://bower.io/">Bower</a> for package management.

## Build the project ##
Please, make sure the correct tooling is in place:
- java 8: **1.8.0_91**
- bower: **1.7.7**
- maven: **3.3.3**


1. Install bower dependencies:
> \> cd $project_folder/www  
> \> bower install

2. Build the project with maven (obs: some tests require nerwork connectivity)
> \> cd $project_folder  
> \> mvn clean package

## Run the Code ##

3. Run the self-contained jar
> \> cd $project_folder  
> \> java -jar target/spark-java-sample-1.0-SNAPSHOT.jar

You should see the log output in your console:

> 22/07/2016 15:56:39 [Thread-1] INFO  o.e.jetty.util.log      Logging initialized @514ms  
> 22/07/2016 15:56:39 [Thread-1] INFO  s.e.j.EmbeddedJettyServer == Spark has ignited ...  
> 22/07/2016 15:56:39 [Thread-1] INFO  s.e.j.EmbeddedJettyServer >> Listening on 0.0.0.0:8000  
> 22/07/2016 15:56:39 [Thread-1] INFO  o.e.j.server.Server     jetty-9.3.z-SNAPSHOT  
> 22/07/2016 15:56:40 [Thread-1] INFO  o.e.j.s.ServerConnector Started ServerConnector@12d11bed{HTTP/1.1,[http/1.1]}{0.0.0.0:8000}  
> 22/07/2016 15:56:40 [Thread-1] INFO  o.e.j.server.Server     Started @566ms

Point your browser to <a href="http://localhost:8000">localhost:8000</a> and start playing, or query the service using curl: **curl -i  http://localhost:8000/analyse?u=http://google.com**.

## Improvements ##
1. Consider replacing jsoup by a lenient sax parser so all document information would be extracted on a single pass.

2. Use a asynchronous implementation of the service to extract the document data and push the results to the client, specially when implementing link checking (`Are there any inaccessible links and how many?`).

3. Handle <a href="http://www.html5rocks.com/en/tutorials/webcomponents/imports/">html imports</a> and other types of client side rendering.

4. <a href="https://github.com/Polymer/vulcanize">Vulcanize</a> the webapp.

## Comments ##
In general, I made some implementation decisions based on time constraints that I have.
1. The current rest endpoint implements only a subset of the assignment. The remaining scope should be simple to implement and would not require substantial "new" knowledge.

2. I've decided to deliver a self-contained/self-hosted web application, not a war archive. The deliverable format might be changed to a war file easily, but again, for the sake of time, I reused a simple template based on <a href="sparkjava.com">spark java</a>.

3. Today, the definition of what constitutes a login-form is not clear. One can assume that a login form is a form with a nested input field of type *password*, but that might be too simple for current/modern web applications.

4. Dns problems (UnknownHostException) are reported as status code -1.
