package com.github.sodp5.intellijpluginsample.psi

import com.intellij.psi.PsiFile

data class LightLauncherClassConfig(
    val className: String,
    val packageName: String,
    val originalFile: PsiFile,
)
