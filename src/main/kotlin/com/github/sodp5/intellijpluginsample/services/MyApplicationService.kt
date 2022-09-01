package com.github.sodp5.intellijpluginsample.services

import com.github.sodp5.intellijpluginsample.MyBundle

class MyApplicationService {

    init {
        println(MyBundle.message("applicationService"))

        System.getenv("CI")
    }
}
