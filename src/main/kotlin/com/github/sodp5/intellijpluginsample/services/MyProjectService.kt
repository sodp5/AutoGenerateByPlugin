package com.github.sodp5.intellijpluginsample.services

import com.intellij.openapi.project.Project
import com.github.sodp5.intellijpluginsample.MyBundle
import com.github.sodp5.intellijpluginsample.launcher.LauncherCache
import com.github.sodp5.intellijpluginsample.launcher.LauncherPackageGenerator
import com.intellij.openapi.components.service
import com.intellij.openapi.project.guessProjectDir
import com.intellij.psi.impl.file.PsiDirectoryFactory
import org.jetbrains.kotlin.idea.core.util.toPsiDirectory

class MyProjectService(project: Project): LauncherCache(project) {

    init {
        println(MyBundle.message("projectService", project.name))

        val projectDir = PsiDirectoryFactory.getInstance(project)
            .createDirectory(project.guessProjectDir()!!)

        println(projectDir)
        println(projectDir.subdirectories.toList())

        System.getenv("CI")
    }

    companion object {
        fun getInstance(project: Project): MyProjectService {
            return project.service()
        }
    }
}
