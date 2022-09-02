package com.github.sodp5.intellijpluginsample.cache

import com.intellij.psi.PsiField
import com.intellij.psi.PsiMethod
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.PsiShortNamesCache
import com.intellij.util.Processor

abstract class ShortClassNamesCache : PsiShortNamesCache() {
    override fun getAllFieldNames(): Array<String> = emptyArray()

    override fun getFieldsByNameIfNotMoreThan(name: String, scope: GlobalSearchScope, maxCount: Int): Array<PsiField> =
        PsiField.EMPTY_ARRAY

    override fun getFieldsByName(name: String, scope: GlobalSearchScope): Array<PsiField> = PsiField.EMPTY_ARRAY
}