package com.github.sodp5.intellijpluginsample.launcher

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiClass

open class LauncherCache(
    val project: Project,
) {
    private val classes = mutableListOf<PsiClass>().apply {
        LauncherClassGenerator.getLauncher(project)?.let(::add)
    }

    fun getClasses(): List<PsiClass> {
        return classes
    }
}