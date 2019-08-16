package com.meta.optimize_plugin

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import com.android.build.gradle.api.BaseVariant
import org.gradle.api.Plugin
import org.gradle.api.Project

class OptimizePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println "=========optimize plugin ============= ${project.name}"
        if (!project.plugins.hasPlugin(AppPlugin) && !project.plugins.hasPlugin(LibraryPlugin)) {
            return
        }
        // 添加扩展
        project.extensions.create('optimizePicture', OptimizeExtension)
        // 添加任务
        project.afterEvaluate {
            // application
            if (project.plugins.hasPlugin(AppPlugin)) {
                project.android.applicationVariants.all { BaseVariant variant -> addTask(project, variant) }
                // addTask(project, project.android.applicationVariants[0])
            }
            // android library
            else if (project.plugins.hasPlugin(LibraryPlugin)) {
                project.android.libraryVariants.all { BaseVariant variant -> addTask(project, variant) }
                // addTask(project, project.android.libraryVariants[0])
            }
        }
        println "=========optimize plugin end ============="
    }

    private void addTask(Project project, BaseVariant variant) {
        def lastTask = project.tasks.findByName('optimizePicture')
        if (null != lastTask) {
            return
        }
        // 任务
        def task = project.tasks.create('optimizePicture', OptimizeTask) {
            assetsFolderFile = variant.sourceSets.first().assetsDirectories.first()
            resFolderFile = variant.sourceSets.first().resDirectories.first()
        }
        // 可以在这里添加任务的依赖关系，这里不放到编译过程中执行，就不添加了
    }
}