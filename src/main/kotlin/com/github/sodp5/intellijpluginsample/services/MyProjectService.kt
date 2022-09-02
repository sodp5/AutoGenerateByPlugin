package com.github.sodp5.intellijpluginsample.services

import com.android.tools.idea.gradle.model.IdeArtifactName
import com.android.tools.idea.gradle.project.sync.idea.ModuleUtil
import com.android.tools.idea.projectsystem.getModuleSystem
import com.android.tools.idea.util.CommonAndroidUtil
import com.android.tools.idea.util.toIoFile
import com.intellij.openapi.project.Project
import com.github.sodp5.intellijpluginsample.MyBundle
import com.github.sodp5.intellijpluginsample.launcher.LauncherCache
import com.github.sodp5.intellijpluginsample.launcher.LauncherPackageGenerator
import com.intellij.facet.ProjectFacetManager
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.externalSystem.util.ExternalSystemApiUtil
import com.intellij.openapi.project.guessProjectDir
import com.intellij.psi.PsiManager
import com.intellij.psi.impl.file.PsiDirectoryFactory
import org.jetbrains.android.facet.AndroidFacet
import org.jetbrains.android.facet.SourceProviderManager
import org.jetbrains.kotlin.android.model.isAndroidModule
import org.jetbrains.kotlin.idea.core.util.toPsiDirectory
import org.jetbrains.kotlin.idea.util.projectStructure.getModule


class MyProjectService(project: Project) : LauncherCache(project) {
    init {
        println(MyBundle.message("projectService", project.name))

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


//        val file = project.projectFile!!
//
//        file.getModule(project)
//
//        val facet = AndroidFacet.getInstance(file, project)!!
//        SourceProviderManager.getInstance(facet)
//            .sources
//            .javaDirectories
//            .firstOrNull()
//            ?.toIoFile()
//            .also {
//                println("sourceDir: ${it?.absolutePath}")
//            }

        System.getenv("CI")
    }

    companion object {
        fun getInstance(project: Project): MyProjectService {
            return project.service()
        }
    }
}
