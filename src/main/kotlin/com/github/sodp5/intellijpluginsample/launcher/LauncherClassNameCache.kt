package com.github.sodp5.intellijpluginsample.launcher

import com.github.sodp5.intellijpluginsample.services.MyProjectService
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiPackage
import com.intellij.psi.impl.file.PsiPackageImpl
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.CachedValue
import com.intellij.psi.util.CachedValueProvider
import com.intellij.psi.util.CachedValuesManager

class LauncherClassNameCache(project: Project) : ShortClassNamesCache() {
    private val launcherCache = MyProjectService.getInstance(project)
    private val modificationTracker = LauncherModificationTracker.getInstance(project)

    private val launcherClassCachedValue: CachedValue<Map<String, List<PsiClass>>>
    private val launcherPackageCachedValue: CachedValue<Map<String, List<PsiPackage>>>

    init {
        launcherClassCachedValue = CachedValuesManager.getManager(launcherCache.project)
            .createCachedValue {
                val classes = launcherCache.getClasses()
                val shortName = classes.groupBy { it.name ?: "ToT" }
                CachedValueProvider.Result.create(shortName, modificationTracker)
            }

        launcherPackageCachedValue = CachedValuesManager.getManager(launcherCache.project)
            .createCachedValue {
                val packages = launcherCache.getPackages()
                val shortName = packages.groupBy { it.name ?: "ToT" }

                CachedValueProvider.Result.create(shortName, modificationTracker)
            }
    }

    override fun getClassesByName(name: String, scope: GlobalSearchScope): Array<PsiClass> {
        modificationTracker.incModificationCount()

        return launcherClassCachedValue.value[name]?.toTypedArray() ?: PsiClass.EMPTY_ARRAY
    }

    override fun getAllClassNames(): Array<String> {
        return launcherClassCachedValue.value.keys.toTypedArray()
    }
}