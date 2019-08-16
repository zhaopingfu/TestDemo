package com.meta.optimize_plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

/**
 * @author zhaopf* @date 2019-08-15
 */
class OptimizeTask extends DefaultTask {

    @Input
    File assetsFolderFile
    @Input
    File resFolderFile

    def pngTool
    def jpgTool
    def webpTool

    OptimizeTask() {
        group "Optimizer"

        pngTool = OptimizeUtil.getTool(project, "pngcrush")
        jpgTool = OptimizeUtil.getTool(project, "guetzli")
        webpTool = OptimizeUtil.getTool(project, "cwebp")
    }

    @TaskAction
    def run() {
        println "=========Optimizer=========="
        println "assetsFolder : $assetsFolderFile.absolutePath"
        println "resFolder : $resFolderFile.absolutePath"

        // 拿到所有要处理的图片
        def pngFileList = []
        def jpgFileList = []
        // 获取 res 下所有能处理的图片
        resFolderFile.eachDir { dir ->
            if (OptimizeUtil.isImageFolder(dir)) {
                dir.eachFile { file ->
                    if (OptimizeUtil.isPreOptimizePng(file) && !OptimizeUtil.isLauncher(file)) {
                        pngFileList << file
                    } else if (OptimizeUtil.isPreOptimizeJpg(file)) {
                        jpgFileList << file
                    }
                }
            }
        }

        // 获取 assets 下的图片
        pngFileList.addAll(getAllFiles(assetsFolderFile, { File file -> OptimizeUtil.isPreOptimizePng(file) }))
        jpgFileList.addAll(getAllFiles(assetsFolderFile, { File file -> OptimizeUtil.isPreOptimizeJpg(file) }))

        // 没有转为 webp 的要压缩
        def preCompressPngList = []
        def preCompressJpgList = []

        // 先将图片都转为webp
        pngFileList.each { file ->
            if (!OptimizeUtil.convertToWebp(file, webpTool)) {
                preCompressPngList << file
            }
        }
        jpgFileList.each { file ->
            if (!OptimizeUtil.convertToWebp(file, webpTool)) {
                preCompressJpgList << file
            }
        }

        // 压缩图片
        OptimizeUtil.compressPics(preCompressPngList, pngTool, jpgTool, true)
        OptimizeUtil.compressPics(preCompressJpgList, pngTool, jpgTool, false)
    }

    /**
     * 获取指定目录下的所有符合条件的文件地址
     *
     * @param dir 指定目录
     * @param closure{ file -> boolean }
     */
    static List<File> getAllFiles(File dir, Closure closure) {
        if (!dir.isDirectory()) {
            return []
        }
        def result = []
        dir.eachFile { file ->
            if (file.isDirectory()) {
                result.addAll(getAllFiles(file, closure))
            } else if (file.isFile() && closure.call(file)) {
                result << file
            }
        }
        return result
    }
}