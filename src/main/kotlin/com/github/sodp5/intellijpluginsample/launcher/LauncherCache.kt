package com.github.sodp5.intellijpluginsample.launcher

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiClass

open class LauncherCache(
    val project: Project,
) {
    fun getClasses(): List<PsiClass> {
        return LauncherClassGenerator.getLauncher(project)?.let {
            listOf(it)
        } ?: emptyList()
    }
}