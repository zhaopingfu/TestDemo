package com.meta.optimize_plugin

import com.tinify.Source
import com.tinify.Tinify
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
     * 获取指定目录下的所有符合条件的文件
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

    /**
     * 采用 tiny 压缩png
     */
    static boolean compressPngTiny(File file) {
        // 因为是压缩,压缩后和压缩前名字一样,所以先弄一个临时文件,压缩过后把源文件删除,再把临时文件的名字修改
        def output = new File(file.parent, "temp-preoptimizer-${file.name}")
        try {
            Tinify.setKey("47tGYp7yc759xPLJtByP4DdJY4f98vqM")
            Source source = Tinify.fromFile(file.getAbsolutePath())
            source.toFile(output.getAbsolutePath())

            def rawLen = file.length()
            def outLen = output.length()
            if (outLen < rawLen) {
                double percent = (double) (rawLen - outLen) / rawLen * 100F
                String format = String.format("%.2f%%", percent)
                println "---> compress pngTiny ${file.absolutePath} success 压缩了 $format"
                // 删除原图片
                file.delete()
                // 将压缩后的图片重命名为原图片
                output.renameTo(file)
                return true
            } else {
                output.delete()
                println "---> compress pngTiny ${file.absolutePath} bigger than raw"
            }
        } catch (Exception e) {
            output.delete()
            println "---> compress pngTiny ${file.absolutePath} error: ${e.message}"
        }
        return false
    }

    /**
     * 用 pngCrush 压缩png
     */
    static boolean compressPngCrush(def pngTool, File file) {
        // 因为是压缩,压缩后和压缩前名字一样,所以先弄一个临时文件,压缩过后把源文件删除,再把临时文件的名字修改
        def output = new File(file.parent, "temp-preoptimizer-${file.name}")
        def result = "$pngTool -brute -rem alla -reduce -q ${file.absolutePath} ${output.absolutePath}".execute()
        // 等待命令执行完成
        result.waitForProcessOutput()
        // 压缩成功
        if (result.exitValue() == 0) {
            def rawLen = file.length()
            def outLen = output.length()
            // 压缩后文件确实小了
            if (outLen < rawLen) {
                double percent = (double) (rawLen - outLen) / rawLen * 100F
                String format = String.format("%.2f%%", percent)
                println "---> compress pngCrush ${file.absolutePath} success 压缩了: $format"
                // 删除原图片
                file.delete()
                // 将压缩后的图片重命名为原图片
                output.renameTo(file)
                return true
            } else {
                output.delete()
                println "---> compress pngCrush ${file.absolutePath} bigger than raw"
            }
        } else {
            output.delete()
            println "---> compress pngCrush ${file.absolutePath} error"
        }
        return false
    }

    /**
     * 压缩 jpg
     */
    static boolean compressJpg(def jpgTool, File file) {
        // 因为是压缩,压缩后和压缩前名字一样,所以先弄一个临时文件,压缩过后把源文件删除,再把临时文件的名字修改
        def output = new File(file.parent, "temp-preoptimizer-${file.name}")
        def result = "$jpgTool --quality 85  ${file.absolutePath} ${output.absolutePath}".execute()
        // 等待命令执行完成
        result.waitForProcessOutput()
        // 压缩成功
        if (result.exitValue() == 0) {
            def rawLen = file.length()
            def outLen = output.length()
            // 压缩后文件确实小了
            if (outLen < rawLen) {
                double percent = (double) (rawLen - outLen) / rawLen * 100F
                String format = String.format("%.2f%%", percent)
                println "---> compress jpg ${file.absolutePath} success 压缩了: $format"
                // 删除原图片
                file.delete()
                // 将压缩后的图片重命名为原图片
                output.renameTo(file)
                return true
            } else {
                output.delete()
                println "---> compress jpg ${file.absolutePath} bigger than raw"
            }
        } else {
            output.delete()
            println "---> compress jpg ${file.absolutePath} error"
        }
        return false
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