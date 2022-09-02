package com.github.sodp5.intellijpluginsample.launcher

import com.android.tools.idea.util.toIoFile
import com.google.common.collect.Maps
import com.intellij.facet.ProjectFacetManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import com.intellij.openapi.vfs.VfsUtilCore
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiPackage
import com.intellij.psi.impl.file.PsiDirectoryFactory
import com.intellij.psi.impl.file.PsiPackageImpl
import com.intellij.testFramework.LightVirtualFileBase
import org.jetbrains.android.facet.AndroidFacet
import org.jetbrains.android.facet.SourceProviderManager
import java.io.File

object LauncherPackageGenerator {
    private val layoutBindingPsiPackages = Maps.newConcurrentMap<String, PsiPackage>()

    fun createLauncherPackage(project: Project, packageName: String): PsiPackage {
        val facet = ProjectFacetManager.getInstance(project)
            .getFacets(AndroidFacet.ID)
            .filter {
                it.module.name.substringAfterLast(".").equals("main", ignoreCase = true)
            }
            .firstOrNull()

        SourceProviderManager.getInstance(facet!!)
            .sources
            .javaDirectories
            .firstOrNull()



        return layoutBindingPsiPackages.computeIfAbsent(packageName) {
            object : PsiPackageImpl(PsiManager.getInstance(project), packageName) {
                override fun isValid() = true
                override fun getDirectories(): Array<PsiDirectory> {
                    val io = SourceProviderManager.getInstance(facet)
                        .sources
                        .javaDirectories
                        .firstOrNull()
                        ?.toIoFile()

                    val fakeDir = FakeDirectory(File(io, packageName.replace('.', '/')))

                    return arrayOf(PsiDirectoryFactory.getInstance(project).createDirectory(fakeDir))
                }
            }
        }
    }

    private class FakeDirectory(file: File) : LightVirtualFileBase(file.absolutePath, null, -1) {
        override fun getOutputStream(requestor: Any?, newModificationStamp: Long, newTimeStamp: Long) = throw NotImplementedError()
        override fun getInputStream() = throw NotImplementedError()
        override fun contentsToByteArray() = ByteArray(0)
    }
}