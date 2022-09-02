package com.github.sodp5.intellijpluginsample.psi

import com.android.tools.idea.projectsystem.getModuleSystem
import com.intellij.facet.ProjectFacetManager
import com.intellij.ide.highlighter.JavaFileType
import com.intellij.lang.java.JavaLanguage
import com.intellij.psi.*
import com.intellij.psi.impl.light.LightFieldBuilder
import com.intellij.psi.impl.light.LightMethodBuilder
import org.jetbrains.android.augment.AndroidLightClassBase
import org.jetbrains.android.facet.AndroidFacet

class LightLauncherClass(
    private val psiManager: PsiManager,
) : AndroidLightClassBase(psiManager, listOf(PsiModifier.FINAL, PsiModifier.PUBLIC)) {
    private val packageName by lazy {
        val facet = ProjectFacetManager.getInstance(project)
            .getFacets(AndroidFacet.ID).firstOrNull {
                it.module.name.substringAfterLast(".").equals("main", ignoreCase = true)
            }

        (facet?.getModuleSystem()?.getPackageName() ?: "") + ".launcher"
    }
    private val className = "ChannelLauncher"

    private val backingFile = PsiFileFactory.getInstance(project)
        .createFileFromText(
            "$className.java",
            JavaFileType.INSTANCE,
            "// create by plugin"
        )
        .let { it as PsiJavaFile }
        .also {
            it.packageName = packageName
        }

    override fun getContainingClass(): PsiClass? {
        return null
    }

    override fun getQualifiedName(): String {
        return "$packageName.$className"
    }

    override fun getContainingFile(): PsiFile {
        return backingFile
    }

    override fun getConstructors(): Array<PsiMethod> {
        val method = LightMethodBuilder(this, JavaLanguage.INSTANCE)
            .setConstructor(true)
            .setModifiers(PsiModifier.PUBLIC)

        return arrayOf(method)
    }

    override fun getFields(): Array<PsiField> {
        val field = LightFieldBuilder(psiManager, "abc", PsiType.INT)
            .setModifiers(PsiModifier.PUBLIC)

        return arrayOf(field)
    }

    override fun getName(): String {
        return className
    }
}