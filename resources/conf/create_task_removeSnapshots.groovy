import groovy.json.JsonSlurper
import org.sonatype.nexus.scheduling.TaskConfiguration
import org.sonatype.nexus.scheduling.TaskInfo
import org.sonatype.nexus.scheduling.TaskScheduler
import org.sonatype.nexus.scheduling.schedule.Schedule
import org.sonatype.nexus.scheduling.schedule.Daily

parsed_args = new JsonSlurper().parseText(args)

// schedule a task to remove old snapshots from the maven-snapshots repository.
// see https://github.com/sonatype/nexus-public/blob/555cc59e7fa659c0a1a4fbc881bf3fcef0e9a5b6/components/nexus-scheduling/src/main/java/org/sonatype/nexus/scheduling/TaskScheduler.java
// see https://github.com/sonatype/nexus-public/blob/555cc59e7fa659c0a1a4fbc881bf3fcef0e9a5b6/plugins/nexus-coreui-plugin/src/main/java/org/sonatype/nexus/coreui/TaskComponent.groovy
taskScheduler = (TaskScheduler)container.lookup(TaskScheduler.class.name)
taskConfiguration = taskScheduler.createTaskConfigurationInstance("repository.maven.remove-snapshots")
taskConfiguration.name = "remove old snapshots from the maven-snapshots repository"
// NB to see the available properties uncomment the tasksDescriptors property from JsonOutput.toJson at the end of this script.
taskConfiguration.setString("repositoryName", "maven-snapshots")
taskConfiguration.setString("minimumRetained", "1")
taskConfiguration.setString("snapshotRetentionDays", "30")
// TODO taskConfiguration.setAlertEmail("TODO")
taskScheduler.scheduleTask(taskConfiguration, new Daily(new Date().clearTime().next()))
