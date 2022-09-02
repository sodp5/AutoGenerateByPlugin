package com.github.sodp5.intellijpluginsample.services

import com.android.tools.idea.projectsystem.getModuleSystem
import com.intellij.facet.ProjectFacetManager
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import com.intellij.psi.impl.file.PsiDirectoryFactory
import org.jetbrains.android.facet.AndroidFacet
import org.jetbrains.android.facet.SourceProviderManager


class MyProjectService(project: Project) : LauncherCache(project) {
    init {
        val projectDir = PsiDirectoryFactory.getInstance(project)
            .createDirectory(project.guessProjectDir()!!)

        println(projectDir)
        println(projectDir.subdirectories.toList())

        val facet = ProjectFacetManager.getInstance(project)
            .getFacets(AndroidFacet.ID)
            .filter {
                it.module.name.substringAfterLast(".").equals("main", ignoreCase = true)
            }
            .firstOrNull()

        SourceProviderManager.getInstance(facet!!)
            .sources
            .javaDirectories
            .firstOrNull()
            .let { file ->
                println(file)
            }

        println(facet.getModuleSystem().getPackageName())
    }

    companion object {
        fun getInstance(project: Project): MyProjectService {
            return project.service()
        }
    }
}
