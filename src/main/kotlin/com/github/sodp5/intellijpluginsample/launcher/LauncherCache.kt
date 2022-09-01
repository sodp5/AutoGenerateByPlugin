package com.github.sodp5.intellijpluginsample.launcher

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiPackage
import org.jetbrains.kotlin.idea.gradleTooling.get

open class LauncherCache(
    val project: Project,
) {
    fun getClasses(): List<PsiClass> {
        return LauncherClassGenerator.getLauncher(project)?.let {
            listOf(it)
        } ?: emptyList()
    }

    fun getPackages(): List<PsiPackage> {
        return listOf(LauncherPackageGenerator.createLauncherPackage(project))
    }
}