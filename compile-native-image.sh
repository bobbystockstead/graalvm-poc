#!/bin/bash

if [ -z ${GROOVY_HOME+x} ]; then
    echo "ERROR: environment variable GROOVY_HOME is not set..."
    exit 1
fi

GROOVY_VERSION=`groovy -version | awk '{print $3}'`

CLASSPATH=".:$GROOVY_HOME/lib/groovy-$GROOVY_VERSION.jar:$HOME/.groovy/grapes/org.jsoup/jsoup/jars/jsoup-1.11.3.jar"

if [ ! -f ./CountLinks.class ]; then
    echo "--------  ./ ----------"
    ls
    echo "Compiling the script..."
    groovyc --configscript=config/compiler.groovy src/CountLinks.groovy

    echo "--------  ~/ ----------"
    ls -al ~/
    echo "--------  /root/ ----------"
    ls -la /root/
fi

native-image -Dgroovy.grape.enable=false \
    --enable-url-protocols=https \
    --allow-incomplete-classpath \
    -H:+AllowVMInspection \
    -H:+ReportUnsupportedElementsAtRuntime \
    -H:ReflectionConfigurationFiles=config/dgm.json,config/countlinks.json \
    --no-server \
    -cp $CLASSPATH \
    CountLinks