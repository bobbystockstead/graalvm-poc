#!groovy
@Grab(group='org.jsoup', module='jsoup', version='1.11.3')

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

final String url = args[0]
final Document doc = Jsoup.connect(url).get()
final int links = doc.select("a").size()

println "Website $url contains $links links."
