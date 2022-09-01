package com.github.sodp5.intellijpluginsample.listeners

import com.github.sodp5.intellijpluginsample.launcher.LauncherModificationTracker
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import com.github.sodp5.intellijpluginsample.services.MyProjectService

internal class MyProjectManagerListener : ProjectManagerListener {
    override fun projectOpened(project: Project) {
        project.service<MyProjectService>()
        project.service<LauncherModificationTracker>()

        System.getenv("CI")
    }
}
