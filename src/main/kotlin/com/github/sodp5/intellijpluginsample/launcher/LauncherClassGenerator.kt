package com.github.sodp5.intellijpluginsample.launcher

import com.intellij.ide.highlighter.JavaFileType
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiJavaFile

object LauncherClassGenerator {
    fun getLauncher(project: Project): PsiClass? {
        val file = PsiFileFactory.getInstance(project)
            .createFileFromText(
                "ChannelLauncher.java",
                JavaFileType.INSTANCE,
                "public class ChannelLauncher {}"
            ) as? PsiJavaFile

        file?.packageName = "launcher"

        return file?.classes?.firstOrNull()
    }
}