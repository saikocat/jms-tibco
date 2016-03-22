# Set up local repo with Tibco JMS jar

We don't put `tibjms.jar` here cos it is proprietary and non-maven jar.
Neither we will put `./repo` path in the source tree since it is generated as below.

```
$ mvn deploy:deploy-file -Durl=file://<path>/<to_this_repo>/jms-tibco/repo/ -Dfile=tibjms.jar -DgroupId=com.tibco -DartifactId=tibjms -Dpackaging=jar -Dversion=8.2.1_v4
```
