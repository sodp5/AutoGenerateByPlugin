package com.github.sodp5.intellijpluginsample.launcher

import com.github.sodp5.intellijpluginsample.services.MyProjectService
import com.intellij.openapi.project.Project
import com.intellij.psi.*
import com.intellij.psi.impl.file.PsiPackageImpl
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.CachedValue
import com.intellij.psi.util.CachedValueProvider
import com.intellij.psi.util.CachedValuesManager
import com.intellij.util.Processor

class LauncherClassNameCache(project: Project) : ShortClassNamesCache() {
    private val launcherCache = MyProjectService.getInstance(project)
    private val modificationTracker = LauncherModificationTracker.getInstance(project)

    private val launcherClassCachedValue = CachedValuesManager.getManager(launcherCache.project)
        .createCachedValue {
            val classes = launcherCache.getClasses()
            val shortName = classes.groupBy { it.name ?: "ToT" }
            CachedValueProvider.Result.create(shortName, modificationTracker)
        }

    private val launcherMethodCached = CachedValuesManager.getManager(launcherCache.project)
        .createCachedValue {
            val allMethods = launcherCache.getClasses()
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
}