package com.github.sodp5.intellijpluginsample.tracker

import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.SimpleModificationTracker

@Service
class LauncherModificationTracker : SimpleModificationTracker() {
    companion object {
        fun getInstance(project: Project): LauncherModificationTracker {
            return project.getService(LauncherModificationTracker::class.java)
        }
    }
}