# graalvm-poc
Test app with Grapes -> Groovy -> GraalVM -> docker !

### check your dgm config file
If you are using a version of groovy other than 2.5.5, make sure that you update your dgm.json.  You can do this by running:

> `groovy src/dgm.groovy > src/dgm.json`

### compiling the bytecode
Here we make use of the compiler config file
> `groovyc --configscript=src/compiler.groovy src/CountLinks.groovy`

### building the image
format:
> `native-image -Dgroovy.grape.enable=false --enable-url-protocols=https --allow-incomplete-classpath -H:+AllowVMInspection -H:+ReportUnsupportedElementsAtRuntime -H:ReflectionConfigurationFiles=src/dgm.json,src/countlinks.json --no-server -cp ".:{path-to-groovy-jar}:{path-to-jsoup-jar}" CountLinks`

Example:
> `native-image -Dgroovy.grape.enable=false --enable-url-protocols=https --allow-incomplete-classpath -H:+AllowVMInspection -H:+ReportUnsupportedElementsAtRuntime -H:ReflectionConfigurationFiles=src/dgm.json,src/countlinks.json --no-server -cp ".:$HOME/.gradle/caches/modules-2/files-2.1/org.codehaus.groovy/groovy/2.5.5/2388f952b369f6b11cde8e15ec872e9ca3e0ff3e/groovy-2.5.5.jar:$HOME/.gradle/caches/modules-2/files-2.1/org.jsoup/jsoup/1.11.3/36da09a8f68484523fa2aaa100399d612b247d67/jsoup-1.11.3.jar" CountLinks`

### running the image
Format:
> `./countlinks -Djava.library.path={path-to-jre/lib} {website}`

Example:
> `./countlinks -Djava.library.path=$HOME/.sdkman/candidates/java/1.0.0-rc-11-grl/jre/lib https://stackoverflow.com`


## Speed test results
Dynamic groovy:
> `9.80s user 0.66s system 346% cpu 3.022 total`

Compiled static groovy:
> `2.68s user 0.34s system 294% cpu 1.024 total`

GraalVm:
> `0.04s user 0.03s system 23% cpu 0.295 total`

245 times faster!!
