package com.github.sodp5.intellijpluginsample.services

import com.github.sodp5.intellijpluginsample.psi.LightLauncherClass
import com.github.sodp5.intellijpluginsample.psi.LightLauncherClassConfig
import com.github.sodp5.intellijpluginsample.util.AnnotationSearchUtil
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiJavaFile
import com.intellij.psi.PsiManager
import org.jetbrains.kotlin.psi.KtFile

class LauncherProjectService(private val project: Project) {
    fun getClasses(): List<PsiClass> {
        val annotationFqn = "com.munny.dummyproject.annotations.Launcher"
        val annotationClasses = AnnotationSearchUtil.getAnnotatedElements(project, annotationFqn)
        val psiManager = PsiManager.getInstance(project)

        return annotationClasses.mapNotNull { ktDeclaration ->
            val name = ktDeclaration.name ?: return@mapNotNull null
            val packageName = when (val file = ktDeclaration.containingFile) {
                is KtFile -> file.packageFqName.asString()
                is PsiJavaFile -> file.packageName
                else -> return@mapNotNull null
            }

            val config = LightLauncherClassConfig(
                className = "${name}Launcher",
                packageName = "$packageName.launcher",
                psiFile = ktDeclaration.containingFile
            )

            LightLauncherClass(psiManager, config)
        }.toList()
    }

    companion object {
        fun getInstance(project: Project): LauncherProjectService {
            return project.service()
        }
    }
}
