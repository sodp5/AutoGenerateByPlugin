package com.github.sodp5.intellijpluginsample.services

import com.github.sodp5.intellijpluginsample.psi.LightLauncherClass
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiManager

open class LauncherCache(
    val project: Project,
) {
    private val classes = mutableListOf<PsiClass>().apply {
        add(LightLauncherClass(PsiManager.getInstance(project)))
    }

    fun getClasses(): List<PsiClass> {
        return classes
    }
}