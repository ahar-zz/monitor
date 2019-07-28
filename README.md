**Monitoring Service**

### Instructions
If you don't have gradle installed on your server, replace ```gradle``` with ```./gradlew``` to use gradle wrapper

_**Straight run**_
- From the project root:<br /> ```gradle bootRun``` <br/>
_**Building jar**_
- From the project root:<br /> ```gradle build``` <br/>
```java -jar build/libs/monitor-0.0.1-SNAPSHOT.jar ```<br/>
- API docs:<br /> ```http://127.0.0.1:8000/swagger-ui.html```<br/>
_**Run tests**_
- From the project root:<br />
```gradle test```

**Technologies**
- Spring-boot (2.1.6.RELEASE)
- Quartz Scheduler (http://www.quartz-scheduler.org/)

**Tips**
- I didn't use anything like lombok, by a reason :)
- There is a possibility to chose different implementation for scheduler(TriggerTask, etc.). 
However, Quartz Scheduler was chosen by me because I have never worked with it and was curious about it. Looks good to me. 
- Created minimal test coverage just to let you understand, that there is no problem for me. 
- I didn't include checkstyle, as long as this is just test task :) Usually, I use it