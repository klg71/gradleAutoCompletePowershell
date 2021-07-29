package net.mayope.gradlelisttasks

import org.gradle.tooling.GradleConnector
import org.gradle.tooling.model.GradleProject
import java.io.File

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        error("Please specify project directory")
    }

    val connector = GradleConnector.newConnector().run {
        useBuildDistribution()
        forProjectDirectory(File(args[0]))
        connect()
    }
    connector.run {
        collectTasks(model(GradleProject::class.java).get())
    }.forEach {
        println(it)
    }
    connector.close()
}

private fun collectTasks(project: GradleProject): Set<String> {
    return project.children.flatMap {
        collectTasks(it)
    }.union(project.tasks.map { it.name })
}
