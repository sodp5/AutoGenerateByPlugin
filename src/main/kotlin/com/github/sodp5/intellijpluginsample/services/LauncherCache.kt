package com.github.sodp5.intellijpluginsample.services

import com.github.sodp5.intellijpluginsample.psi.LightLauncherClass
import com.github.sodp5.intellijpluginsample.util.AnnotationSearchUtil
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
        val annotationFqn = "com.munny.dummyproject.annotations.Launcher"
        val annotationClasses = AnnotationSearchUtil.getAnnotatedElements(project, annotationFqn)

        annotationClasses.forEach { ktDeclaration ->
            val name = ktDeclaration.name ?: return@forEach
            println(ktDeclaration)
            println(ktDeclaration.name)
        }

        return classes
    }
}