import groovy.json.JsonSlurper
import org.sonatype.nexus.repository.config.Configuration

parsed_args = new JsonSlurper().parseText(args)

repository.createMavenGroup("aem-group-repo", ["maven-central", "aem"], "default")
