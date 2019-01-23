@Grab(group = "org.reflections", module = "reflections", version = "0.9.11")
@Grab(group = 'org.apache.logging.log4j', module = 'log4j-to-slf4j', version='2.11.1')

import groovy.json.JsonOutput
import org.codehaus.groovy.reflection.GeneratedMetaMethod
import org.reflections.Reflections
import org.reflections.util.ConfigurationBuilder

def reflections = new Reflections(new ConfigurationBuilder().build())

def json = reflections.getSubTypesOf(GeneratedMetaMethod).sort().collect {
    [name: it.name, allDeclaredConstructors: true, allPublicConstructors: true, allDeclaredMethods: true, allPublicMethods: true]
}

Comparator mc = { a, b -> a == b ? 0 : a.name.find(/\d+$/)?.toInteger() < b.name.find(/\d+$/)?.toInteger() ? -1 : 1 }
Collections.sort(json, mc)
println JsonOutput.prettyPrint(JsonOutput.toJson(json))