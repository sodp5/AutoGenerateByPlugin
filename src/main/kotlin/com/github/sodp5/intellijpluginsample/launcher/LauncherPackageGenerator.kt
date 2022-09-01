package com.github.sodp5.intellijpluginsample.launcher

import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import com.intellij.openapi.vfs.VfsUtilCore
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiPackage
import com.intellij.psi.impl.file.PsiDirectoryFactory
import com.intellij.psi.impl.file.PsiDirectoryImpl
import com.intellij.psi.impl.file.PsiPackageImpl
import com.intellij.testFramework.LightVirtualFileBase
import org.jetbrains.kotlin.idea.core.util.toPsiDirectory
import java.io.File

object LauncherPackageGenerator {
    fun createLauncherPackage(project: Project): PsiPackage {
        val projectDir = PsiDirectoryFactory.getInstance(project)
            .createDirectory(project.guessProjectDir()!!)

        val io = VfsUtilCore.virtualToIoFile(projectDir.subdirectories.first().virtualFile)
        val fakeDir = FakeDirectory(File(io, "launcher"))

        return object : PsiPackageImpl(PsiManager.getInstance(project), "launcher") {
            override fun isValid() = true
            override fun getDirectories(): Array<PsiDirectory> {
                return arrayOf(PsiDirectoryFactory.getInstance(project).createDirectory(fakeDir))
            }
        }
    }

    private class FakeDirectory(file: File) : LightVirtualFileBase(file.absolutePath, null, -1) {
        override fun getOutputStream(requestor: Any?, newModificationStamp: Long, newTimeStamp: Long) = throw NotImplementedError()
        override fun getInputStream() = throw NotImplementedError()
        override fun contentsToByteArray() = ByteArray(0)
    }
}