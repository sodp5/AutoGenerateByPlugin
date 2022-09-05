package com.github.sodp5.intellijpluginsample.finder

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

class LauncherClassFinder(project: Project) : PsiElementFinder() {
    private val launcherService = LauncherProjectService.getInstance(project)
    private val modificationTracker = LauncherModificationTracker.getInstance(project)

    private val classCached: CachedValue<List<PsiClass>>

    private val fqnClassCached: CachedValue<Map<String, List<PsiClass>>>
    private val packageCached: CachedValue<Map<String, List<PsiClass>>>

    init {
        val cachedValuesManager = CachedValuesManager.getManager(project)

        classCached = cachedValuesManager.createCachedValue {
            val classes = launcherService.getClasses()
            CachedValueProvider.Result.create(classes, modificationTracker)
        }

        fqnClassCached = cachedValuesManager.createCachedValue {
            val map = classCached.value.groupBy { it.qualifiedName ?: "ToT" }
            CachedValueProvider.Result.create(map, modificationTracker)
        }

        packageCached = cachedValuesManager.createCachedValue {
            val map = classCached.value.groupBy { it.qualifiedName?.substringBeforeLast('.') ?: "ToT" }
            CachedValueProvider.Result.create(map, modificationTracker)
        }
    }

    override fun findClass(qualifiedName: String, scope: GlobalSearchScope): PsiClass? {
        return fqnClassCached.value[qualifiedName]?.firstOrNull()
    }

    override fun findClasses(qualifiedName: String, scope: GlobalSearchScope): Array<PsiClass> {
        val c = findClass(qualifiedName, scope) ?: return PsiClass.EMPTY_ARRAY
        return arrayOf(c)
    }

    override fun getClasses(psiPackage: PsiPackage, scope: GlobalSearchScope): Array<PsiClass> {
        val packages = packageCached.value[psiPackage.qualifiedName]

        return packages?.toTypedArray() ?: PsiClass.EMPTY_ARRAY
    }

    override fun findPackage(qualifiedName: String): PsiPackage? {
        return null
    }
}