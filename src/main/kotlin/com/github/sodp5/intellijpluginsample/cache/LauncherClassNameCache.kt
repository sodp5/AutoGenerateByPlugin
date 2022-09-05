package com.github.sodp5.intellijpluginsample.cache

import com.github.sodp5.intellijpluginsample.services.LauncherProjectService
import com.github.sodp5.intellijpluginsample.tracker.LauncherModificationTracker
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiField
import com.intellij.psi.PsiMethod
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.PsiShortNamesCache
import com.intellij.psi.util.CachedValueProvider
import com.intellij.psi.util.CachedValuesManager
import com.intellij.util.Processor

class LauncherClassNameCache(project: Project) : PsiShortNamesCache() {
    private val launcherService = LauncherProjectService.getInstance(project)
    private val modificationTracker = LauncherModificationTracker.getInstance(project)

    private val launcherClassCachedValue = CachedValuesManager.getManager(project)
        .createCachedValue {
            val classes = launcherService.getClasses()
            val shortName = classes.groupBy { it.name ?: "ToT" }
            CachedValueProvider.Result.create(shortName, modificationTracker)
        }

    private val launcherMethodCached = CachedValuesManager.getManager(project)
        .createCachedValue {
            val allMethods = launcherService.getClasses()
                .flatMap { psiClass -> psiClass.methods.asIterable() }
                .groupBy { method -> method.name }

            CachedValueProvider.Result.create(allMethods, modificationTracker)
        }


    override fun getClassesByName(name: String, scope: GlobalSearchScope): Array<PsiClass> {
        modificationTracker.incModificationCount()

        return launcherClassCachedValue.value[name]?.toTypedArray() ?: PsiClass.EMPTY_ARRAY
    }

    override fun getAllClassNames(): Array<String> {
        return launcherClassCachedValue.value.keys.toTypedArray()
    }

    override fun getMethodsByName(name: String, scope: GlobalSearchScope): Array<PsiMethod> {
        val methods = launcherMethodCached.value[name] ?: return PsiMethod.EMPTY_ARRAY

        return methods.toTypedArray()
    }

    override fun getMethodsByNameIfNotMoreThan(
        name: String,
        scope: GlobalSearchScope,
        maxCount: Int
    ): Array<PsiMethod> {
        return getMethodsByName(name, scope).take(maxCount).toTypedArray()
    }

    override fun processMethodsWithName(
        name: String,
        scope: GlobalSearchScope,
        processor: Processor<in PsiMethod>
    ): Boolean {
        for (method in getMethodsByName(name, scope)) {
            if (!processor.process(method)) {
                return false
            }
        }
        return true
    }

    override fun getAllMethodNames(): Array<String> {
        return launcherMethodCached.value.keys.toTypedArray()
    }

    override fun getAllFieldNames(): Array<String> = emptyArray()

    override fun getFieldsByNameIfNotMoreThan(name: String, scope: GlobalSearchScope, maxCount: Int): Array<PsiField> =
        PsiField.EMPTY_ARRAY

    override fun getFieldsByName(name: String, scope: GlobalSearchScope): Array<PsiField> = PsiField.EMPTY_ARRAY
}