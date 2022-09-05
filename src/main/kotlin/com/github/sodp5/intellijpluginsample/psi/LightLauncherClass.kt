package com.github.sodp5.intellijpluginsample.psi

import com.android.tools.idea.kotlin.psiType
import com.github.sodp5.intellijpluginsample.util.AnnotationSearchUtil
import com.intellij.ide.highlighter.JavaFileType
import com.intellij.lang.java.JavaLanguage
import com.intellij.psi.*
import com.intellij.psi.impl.light.LightFieldBuilder
import com.intellij.psi.impl.light.LightMethodBuilder
import com.intellij.psi.search.GlobalSearchScope
import org.jetbrains.android.augment.AndroidLightClassBase
import org.jetbrains.kotlin.j2k.getContainingClass
import org.jetbrains.kotlin.psi.KtProperty

class LightLauncherClass(
    psiManager: PsiManager,
    private val lightLauncherClassConfig: LightLauncherClassConfig,
) : AndroidLightClassBase(psiManager, listOf(PsiModifier.FINAL, PsiModifier.PUBLIC)) {
    private val backingFile = PsiFileFactory.getInstance(project)
        .createFileFromText(
            "${lightLauncherClassConfig.className}.java",
            JavaFileType.INSTANCE,
            "// create by plugin"
        )
        .let { it as PsiJavaFile }
        .also {
            it.packageName = lightLauncherClassConfig.packageName
            navigationElement = lightLauncherClassConfig.originalFile
        }

    override fun getContainingClass(): PsiClass? {
        return null
    }

    override fun getQualifiedName(): String {
        return "${lightLauncherClassConfig.packageName}.${lightLauncherClassConfig.className}"
    }

    override fun getContainingFile(): PsiFile {
        return backingFile
    }

    override fun getConstructors(): Array<PsiMethod> {
        val method = LightMethodBuilder(this, JavaLanguage.INSTANCE)
            .setConstructor(true)
            .setModifiers(PsiModifier.PUBLIC)
            .apply {
                navigationElement = lightLauncherClassConfig.originalFile
            }

        return arrayOf(method)
    }

    override fun getFields(): Array<PsiField> {
        val fqn = "com.munny.dummyproject.annotations.LauncherExtraData"
        val elements = AnnotationSearchUtil.getAnnotatedElements(
            project,
            fqn,
            GlobalSearchScope.fileScope(
                lightLauncherClassConfig.originalFile
            )
        )

        return elements
            .filterIsInstance<KtProperty>()
            .mapNotNull {
                LightFieldBuilder(
                    it.name ?: return@mapNotNull null,
                    it.psiType ?: return@mapNotNull null,
                    it
                )
                    .setModifiers(PsiModifier.PUBLIC)
            }
            .toList()
            .toTypedArray()
    }

    override fun getName(): String {
        return lightLauncherClassConfig.className
    }
}