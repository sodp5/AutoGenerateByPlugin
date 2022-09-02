package com.github.sodp5.intellijpluginsample.psi

import com.android.tools.idea.projectsystem.getModuleSystem
import com.intellij.facet.ProjectFacetManager
import com.intellij.ide.highlighter.JavaFileType
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiJavaFile
import org.jetbrains.android.facet.AndroidFacet

object LauncherClassGenerator {
    fun getLauncher(project: Project): PsiClass? {
        val facet = ProjectFacetManager.getInstance(project)
            .getFacets(AndroidFacet.ID)
            .filter {
                it.module.name.substringAfterLast(".").equals("main", ignoreCase = true)
            }
            .firstOrNull()

        val file = PsiFileFactory.getInstance(project)
            .createFileFromText(
                "ChannelLauncher.java",
                JavaFileType.INSTANCE,
                "public class ChannelLauncher {}"
            ) as? PsiJavaFile

        file?.packageName = (facet?.getModuleSystem()?.getPackageName() ?: "") + ".launcher"

        return file?.classes?.firstOrNull()
    }
}
