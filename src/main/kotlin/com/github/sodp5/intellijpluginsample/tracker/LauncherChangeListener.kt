package com.github.sodp5.intellijpluginsample.tracker

class LauncherChangeListener(
    private val tracker: LauncherModificationTracker,
) : SimpleChangeListener() {
    override fun onChange() {
        tracker.incModificationCount()
    }
}