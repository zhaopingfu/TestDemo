### 1、热修复原理

一般我们自己写的类都是由 `PathClassLoader` 加载的, 

类加载器在加载某个类的时候是先去父加载器那里找，

父加载器再去爷爷加载器那里找，一直往上，如果都找不到，才自己去找，

这就是java的**双亲委托机制**

![](./resources/classLoaderLoadClass.jpg)

通过 `ClassLoader.loadClass` 的时候会先走 `parent` 的 `loadClass`, 如果没找到会自己去找。

调用 `findClass` 方法，具体实现是在 `PathClassLoader` 的父类, 也就是 `BaseDexClassLoader` 里面。

而 `BaseDexClassLoader` 中的 `findClass` 会调用 `pathList.findClass`，代码又来到了 `DexPathList.java`

![](./resources/dexPathListFindClass.png)

在这里可以发现，是通过遍历 `dexElements` 数组来加载类的，而 `dexElements` 是通过 `makePathElements`得到的。

如果我们把有bug的类打成一个 `dex` 包，然后通过反射放到 `dexElements` 的最前面，那不就达到修复的效果了吗。

### 2、获取补丁包
 
1. 生成 `class` 文件

```
选中app模块, 点击 Build -> Make Modules 'app'
```

2. 获取 `jar` 或者 `dex`

```
这里 --output=patch.jar 也可以是 --output=patch.dex
E:\software\AndroidStudio\SDK\build-tools\29.0.0\dx.bat --dex --output=patch.jar com/example/testhotfix/TestBug.class
```

3. 将获取到的 patch.jar 放入到自己写的路径下

```
最好放到私有目录吧，这样不用申请权限

override fun attachBaseContext(base: Context?) {
    super.attachBaseContext(base)
    FixUtil.fix(this, cacheDir.absolutePath + "/patch.jar")
}
```

### 3、问题

1. 为什么java要使用双亲委托这种机制？

```
1. 避免重复查找。一个类只需要加载一次就可以了，没必要加载多次，浪费资源。
2. 避免修改系统代码。如果不用双亲委托，那么每次都要自己查找，如果自己写一个String.java，然后交给ClassLoader去加载，那么别的使用String的地方就很可能会出现问题。
```

2. `Class.forName 和 ClassLoader.loadClass 有什么区别`

```
Class.forName 不仅会加载类，还会走初始化流程。比如在类里面有静态代码块，也是会执行到的。

ClassLoader.loadClass 只是把类加载到类加载器，而不会走类的初始化流程。
```

3. 版本兼容问题

需要兼容的就是反射调用的方法，各版本可能不同

[Tinker兼容代码](https://github.com/Tencent/tinker/blob/dev/tinker-android/tinker-android-loader/src/main/java/com/tencent/tinker/loader/SystemClassLoaderAdder.java)

```
9.0: private static Element[] makeDexElements(List<File> files, File optimizedDirectory,List<IOException> suppressedExceptions, ClassLoader loader) 

8.0: private static Element[] makeDexElements(List<File> files, File optimizedDirectory,List<IOException> suppressedExceptions, ClassLoader loader) 

7.0: private static Element[] makeDexElements(List<File> files, File optimizedDirectory,List<IOException> suppressedExceptions,ClassLoader loader) 

6.0: private static Element[] makePathElements(List<File> files, File optimizedDirectory,List<IOException> suppressedExceptions) 

5.0: private static Element[] makeDexElements(ArrayList<File> files, File optimizedDirectory,ArrayList<IOException> suppressedExceptions) 

4.4: private static Element[] makeDexElements(ArrayList<File> files, File optimizedDirectory,ArrayList<IOException> suppressedExceptions) 

4.3:  private static Element[] makeDexElements(ArrayList<File> files,File optimizedDirectory) {

4.2: private static Element[] makeDexElements(ArrayList<File> files,File optimizedDirectory) 

4.1: private static Element[] makeDexElements(ArrayList<File> files,File optimizedDirectory) 
```

4. `java.lang.IllegalAccessError: Class ref in pre-verified class resolved to unexpected implementation`

[Demo](https://github.com/zhaopingfu/TestDemo/tree/master/TestHotFix)

在5.0以下机型会报这个错误，具体原因下面这个链接里面写的很详细了，我就不再重复了

[错误产生原因](https://mp.weixin.qq.com/s?__biz=MzI1MTA1MzM2Nw==&mid=400118620&idx=1&sn=b4fdd5055731290eef12ad0d17f39d4a)

解决方法，避免让类打上 `CLASS_ISPREVERIFIED`

```
1. 生成一个新的单独的dex
    1.1、 创建一个 JavaLibrary hack，新建一个空的类 com/example/hack/AntilazyLoad.java
    1.2、 make module 会在 /build/libs/ 生成 hack.jar
    1.3、 将 jar 转为 dex: E:\software\AndroidStudio\SDK\build-tools\29.0.0\dx.bat --dex --output=hack.dex hack.jar
    1.4、 将 hack.dex 放入 app/src/main/assets/
    
2. 在 app/build.gradle 中添加以下代码

    dependencies {
        // https://mvnrepository.com/artifact/org.ow2.asm/asm
        implementation 'org.ow2.asm:asm:7.2'
    }
    
    afterEvaluate {
        android.applicationVariants.all { variant ->
            // 获得: debug/release
            String variantName = variant.getName()
            // 首字母大写
            String capitalizeName = variantName.capitalize()
            println capitalizeName
            // 获取打包时jar和class打包成dex 的任务
            Task dexTask = project.tasks.findByName("transformClassesWithDexBuilderFor$capitalizeName")
            // 在打包之前插桩
            dexTask.doFirst {
                // 任务的输入，也就是 class 和 jar
                Set<File> files = dexTask.inputs.files.files
                for (File file : files) {
                    String filePath = file.absolutePath
                    if (filePath.endsWith(".jar")) {
                        processJar(file)
                    } else if (filePath.endsWith(".class")) {
                        // processClass(variant.getDirName(), file)
                        processClass("classes", file)
                    }
                }
            }
        }
    }
    
    static boolean isAndroidClass(String filePath) {
        return filePath.startsWith("android") ||
                filePath.startsWith("androidx")
    }
    
    import java.util.jar.JarEntry
    import java.util.jar.JarFile
    import java.util.jar.JarOutputStream
    import org.objectweb.asm.ClassReader
    import org.objectweb.asm.ClassWriter
    import org.objectweb.asm.ClassVisitor
    import org.objectweb.asm.MethodVisitor
    import org.objectweb.asm.Opcodes
    import org.objectweb.asm.Type
    
    static byte[] referHackWhenInit(InputStream inputStream) throws IOException {
        ClassReader cr = new ClassReader(inputStream)
        ClassWriter cw = new ClassWriter(cr, 0)
        ClassVisitor cv = new ClassVisitor(Opcodes.ASM5, cw) {
            @Override
            MethodVisitor visitMethod(int access, final String name, String desc,
                                      String signature, String[] exceptions) {
                MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions)
                mv = new MethodVisitor(Opcodes.ASM5, mv) {
                    @Override
                    void visitInsn(int opcode) {
                        // 在构造方法中插入 AntilazyLoad 引用
                        if ("<init>".equals(name) && opcode == Opcodes.RETURN) {
                            super.visitLdcInsn(Type.getType("Lcom/example/hack/AntilazyLoad;"))
                        }
                        super.visitInsn(opcode)
                    }
                }
                return mv
            }
        }
        cr.accept(cv, 0)
        return cw.toByteArray()
    }
    
    static void processClass(String dirName, File file) {
        String filePath = file.absolutePath
        // 这里的filePath包含了目录+包名+类名，所以去掉目录
        String className = filePath.split(dirName)[1].substring(1)
        // application 或者android support不管
        if (isAndroidClass(className) ||
                className.startsWith("com\\example\\testhotfix\\MyApplication") ||
                className.startsWith("com\\example\\testhotfix\\fixmethod")) {
            return
        }
        try {
            FileInputStream fis = new FileInputStream(filePath)
            // 执行插桩
            byte[] byteCode = referHackWhenInit(fis)
            fis.close()
    
            FileOutputStream fos = new FileOutputStream(filePath)
            fos.write(byteCode)
            fos.close()
        } catch (Exception e) {
            e.printStackTrace()
        }
    }
    
    static void processJar(File file) {
        try {
            File bakJar = new File(file.parent, "${file.name}.bak")
            JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(bakJar))
    
            JarFile jarFile = new JarFile(file)
            Enumeration<JarEntry> entries = jarFile.entries()
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement()
    
                jarOutputStream.putNextEntry(new JarEntry(jarEntry.name))
                InputStream is = jarFile.getInputStream(jarEntry)
    
                String className = jarEntry.getName()
                if (className.endsWith(".class")
                        && !className.startsWith("com/example/testhotfix/MyApplication")
                        && !isAndroidClass(className)
                        && !className.startsWith("com/example/testhotfix/fixmethod")) {
    
                }
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
    }
    
3、在 application 中加载 hack.dex, 避免 ClassNotFoundException
    
    static File getHackDex(Context context) {
        File hackDir = context.getDir("hack", Context.MODE_PRIVATE);
        File hackFile = new File(hackDir, "hack.dex");
        if (hackFile.exists()) {
            return hackFile;
        }
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(context.getAssets().open("hack.dex"));
            bos = new BufferedOutputStream(new FileOutputStream(hackFile));
            byte[] bytes = new byte[1024];
            int len;
            while ((len = bis.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != bos) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != bis) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return hackFile;
    }
```

经过测试: 4.4, 5.0, 6.0, 9.0 都可以正常修复