package com.github.sodp5.intellijpluginsample.tracker

import com.intellij.psi.PsiTreeChangeAdapter
import com.intellij.psi.PsiTreeChangeEvent

abstract class SimpleChangeListener : PsiTreeChangeAdapter() {
    override fun childAdded(event: PsiTreeChangeEvent) {
        onChange()
    }

    override fun childRemoved(event: PsiTreeChangeEvent) {
        onChange()
    }

    override fun childReplaced(event: PsiTreeChangeEvent) {
        onChange()
    }

    override fun childMoved(event: PsiTreeChangeEvent) {
        onChange()
    }

    override fun childrenChanged(event: PsiTreeChangeEvent) {
        onChange()
    }

    override fun propertyChanged(event: PsiTreeChangeEvent) {
        onChange()
    }

    abstract fun onChange()
}