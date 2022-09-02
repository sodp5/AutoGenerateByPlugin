package com.github.sodp5.intellijpluginsample.launcher

import com.android.tools.idea.util.androidFacet
import com.android.tools.idea.util.toIoFile
import com.github.sodp5.intellijpluginsample.services.MyProjectService
import com.intellij.openapi.module.ModuleUtil
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.ResolveScopeEnlarger
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.SearchScope
import com.intellij.psi.util.CachedValueProvider
import com.intellij.psi.util.PsiModificationTracker
import org.jetbrains.android.facet.AndroidFacet
import org.jetbrains.android.facet.SourceProviderManager

class LauncherScopeEnlarger : ResolveScopeEnlarger() {
    override fun getAdditionalResolveScope(file: VirtualFile, project: Project): SearchScope? {
//        val module = ModuleUtil.findModuleForFile(file, project)!!
//        val facet = AndroidFacet.getInstance(module)!!
//
//        SourceProviderManager.getInstance(facet)
//            .sources
//            .javaDirectories
//            .firstOrNull()
//            ?.toIoFile()
//            .also {
//                println("sourceDir: ${it?.absolutePath}")
//            }

        return getLocalBindingScope(project)
    }

    private fun getLocalBindingScope(project: Project): GlobalSearchScope {
        val cache = MyProjectService.getInstance(project)
        val classes = cache.getClasses()

        val virtualFiles = classes.map { it.containingFile!!.viewProvider.virtualFile }
        val localScope = GlobalSearchScope.filesWithoutLibrariesScope(project, virtualFiles)
        val cacheScope = CachedValueProvider.Result.create(localScope, PsiModificationTracker.MODIFICATION_COUNT)

        return cacheScope.value
    }
}
