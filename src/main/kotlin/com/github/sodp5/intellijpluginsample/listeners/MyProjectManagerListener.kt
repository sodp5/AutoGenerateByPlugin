package com.github.sodp5.intellijpluginsample.listeners

import com.github.sodp5.intellijpluginsample.services.MyProjectService
import com.github.sodp5.intellijpluginsample.util.AnnotationSearchUtil
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener

internal class MyProjectManagerListener : ProjectManagerListener {
    override fun projectOpened(project: Project) {
        project.service<MyProjectService>()
    }
}
