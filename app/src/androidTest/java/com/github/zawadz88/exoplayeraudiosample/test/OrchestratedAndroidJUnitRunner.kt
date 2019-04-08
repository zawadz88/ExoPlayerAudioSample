package com.github.zawadz88.exoplayeraudiosample.test

import android.os.Bundle
import androidx.test.runner.AndroidJUnitRunner

class OrchestratedAndroidJUnitRunner : AndroidJUnitRunner() {

    private var startArguments: Bundle? = null

    override fun onCreate(arguments: Bundle?) {
        startArguments = arguments
        println("OrchestratedAndroidJUnitRunner onCreate: ${arguments?.keySet()?.joinToString(",")}")
        arguments?.keySet()?.onEach {
            println("key: $it, value: ${arguments.get(it)}")
        }
//        if (isOrchestratedJUnit5TestMethodExecution(arguments)) {
//            convertJUnit5ClassToJunit4(arguments!!)
//        }

        //Debug.waitForDebugger()
        super.onCreate(arguments)
    }

    private fun convertJUnit5ClassToJunit4(arguments: Bundle) {
        val originalClassArgument = arguments.getString("class")!!
        val originalTestClass = originalClassArgument.substringBefore("#")
        val fixedTestClass = findRealTestClass(originalTestClass)
        val fixedTestMethod = originalClassArgument.substringAfter("#").substringBefore("()")
        arguments.putString("class", "$fixedTestClass#$fixedTestMethod")
    }

    private fun findRealTestClass(originalTestClass: String) =
        "com.github.zawadz88.exoplayeraudiosample.test.$originalTestClass"

    private fun isOrchestratedJUnit5TestMethodExecution(arguments: Bundle?) = arguments?.get("listTestsForOrchestrator")?.toString() != true.toString() &&
        arguments?.get("orchestratorService") != null &&
        arguments.getString("class")?.endsWith("()") == true
}
