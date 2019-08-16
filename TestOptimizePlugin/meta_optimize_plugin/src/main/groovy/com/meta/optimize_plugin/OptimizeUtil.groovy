package com.meta.optimize_plugin

import org.gradle.api.Project

import javax.imageio.ImageIO
import org.apache.tools.ant.taskdefs.condition.Os

class OptimizeUtil {

    static def final PNG = ".png"
    static def final PNG9 = ".9.png"
    static def final JPG = ".jpg"
    static def final JPEG = ".jpeg"

    /**
     * 获取工具
     * @param name 参数只能是 cwebp/guetzli/pngcrush
     */
    static def getTool(Project project, String name) {
        String toolName
        if (Os.isFamily(Os.FAMILY_WINDOWS)) {
            toolName = "${name}_win.exe"
        } else if (Os.isFamily(Os.FAMILY_MAC)) {
            toolName = "${name}_darwin"
        } else {
            toolName = "${name}_linux"
        }

        def outToolName = "${project.buildDir}/tools/$toolName"
        def file = new File(outToolName)
        if (!file.exists()) {
            file.parentFile.mkdirs()
            new FileOutputStream(file).withStream {
                def is = OptimizeUtil.class.getResourceAsStream("/$name/$toolName")
                it.write(is.bytes)
                is.close()
            }
        }
        if (file.exists() && file.setExecutable(true)) {
            return file.absolutePath
        }
        throw new RuntimeException("$toolName 不存在或者无法执行")
    }

    static def isImageFolder(File dir) {
        return dir.name.startsWith("drawable") || dir.name.startsWith("mipmap")
    }

    static def isPreOptimizeJpg(File file) {
        return file.name.endsWith(JPG) || file.name.endsWith(JPG.toUpperCase()) ||
                file.name.endsWith(JPEG) || file.name.endsWith(JPEG.toUpperCase())
    }

    def static isPreOptimizePng(File file) {
        return (file.name.endsWith(PNG) || file.name.endsWith(PNG.toUpperCase())) &&
                !file.name.endsWith(PNG9) && !file.name.endsWith(PNG9.toUpperCase())
    }

    /**
     * 判断是否存在透明度通道
     */
    static def isTransparent(File file) {
        def bufferedImage = ImageIO.read(file)
        return bufferedImage.colorModel.hasAlpha()
    }

    /**
     * 是否是启动图标
     */
    static def isLauncher(File file) {
        String name = file.name
        return name == "ic_launcher.png" || name == "ic_launcher_round.png"
    }

    /**
     * 压缩png 图片
     */
    static void compressPics(List<File> files, def pngTool, def jpgTool, boolean isPng) {
        if (files.isEmpty()) {
            return
        }

        def compressErrors = []
        def compressFail = []
        files.each { file ->
            // 因为是压缩,压缩后和压缩前名字一样,所以先弄一个临时文件,压缩过后把源文件删除,再把临时文件的名字修改
            def output = new File(file.parent, "temp-preoptimizer-${file.name}")
            def result
            if (isPng) {
                result = "$pngTool -brute -rem alla -reduce -q ${file.absolutePath} ${output.absolutePath}".execute()
            } else {
                result = "$jpgTool --quality 85  ${file.absolutePath} ${output.absolutePath}".execute()
            }
            // 等待命令执行完成
            result.waitForProcessOutput()
            // 压缩成功
            if (result.exitValue() == 0) {
                def rawLen = file.length()
                def outLen = output.length()
                // 压缩后文件确实小了
                if (outLen < rawLen) {
                    println "---> compress ${file.absolutePath} success"
                    // 删除原图片
                    file.delete()
                    // 将压缩后的图片重命名为原图片
                    output.renameTo(file)
                } else {
                    compressFail.add(file.absolutePath)
                    println "---> compress ${file.absolutePath} bigger than raw"
                    output.delete()
                }
            } else {
                compressErrors.add(file.absolutePath)
                println "---> compress ${file.absolutePath} error"
            }
        }
    }

    /**
     * 转换为webp
     */
    static boolean convertToWebp(File file, String webpTool) {
        String name = file.name
        name = name.substring(0, name.lastIndexOf("."))
        def output = new File(file.parent, "${name}.webp")
        def result = "$webpTool -q 75 ${file.absolutePath} -o ${output.absolutePath}".execute()
        result.waitForProcessOutput()
        if (result.exitValue() == 0) {
            def rawLen = file.length()
            def outLen = output.length()
            // 压缩后文件确实小了
            if (outLen < rawLen) {
                println "---> convert webp ${file.absolutePath} success"
                // 删除原图片
                file.delete()
                return true
            } else {
                println "---> convert webp ${file.absolutePath} bigger than raw"
                output.delete()
            }
        } else {
            println "---> convert webp ${file.absolutePath} error"
        }
        return false
    }
}