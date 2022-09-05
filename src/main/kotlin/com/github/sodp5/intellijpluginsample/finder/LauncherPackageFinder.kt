package com.github.sodp5.intellijpluginsample.finder

import com.github.sodp5.intellijpluginsample.psi.LauncherPackageGenerator
import com.github.sodp5.intellijpluginsample.services.LauncherProjectService
import com.github.sodp5.intellijpluginsample.tracker.LauncherModificationTracker
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElementFinder
import com.intellij.psi.PsiPackage
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.CachedValue
import com.intellij.psi.util.CachedValueProvider
import com.intellij.psi.util.CachedValuesManager

class LauncherPackageFinder(project: Project) : PsiElementFinder() {
    private val launcherService = LauncherProjectService.getInstance(project)
    private val modificationTracker = LauncherModificationTracker.getInstance(project)

    private val packageCached: CachedValue<Map<String, PsiPackage>>

    init {
        val cachedValuesManager = CachedValuesManager.getManager(project)

        packageCached = cachedValuesManager.createCachedValue {
            val packages = launcherService.getClasses()
                .map { psiClass ->
                    val packageName = psiClass.qualifiedName?.substringBeforeLast(".") ?: ""
                    LauncherPackageGenerator.createLauncherPackage(project, packageName)
                }
                .associateBy { psiPackage -> psiPackage.qualifiedName }

            CachedValueProvider.Result.create(packages, modificationTracker)
        }
    }

    override fun findClass(qualifiedName: String, scope: GlobalSearchScope): PsiClass? {
        return null
    }

    override fun findClasses(qualifiedName: String, scope: GlobalSearchScope): Array<PsiClass> {
        return PsiClass.EMPTY_ARRAY
    }

    override fun findPackage(qualifiedName: String): PsiPackage? {
        return packageCached.value[qualifiedName]
    }
}