### 序列化和反序列化

##### 1、什么是序列化和反序列化?

```
比如说，我看到埃菲尔铁塔很漂亮，想把它搬到我家门口，要求搬过去之后还是原来的样子，怎么办呢？

我需要把每一根钢筋都拆下来，做上标记，然后把钢筋都装到飞机上

运到目的地之后，把钢筋都卸下来，然后按照之前的标记，把钢筋组装起来，就变回埃菲尔铁塔了
```

**这就是现实生活中的序列化和反序列化，那么反映到程序里面是怎样的呢?**

```
要把一个对象，写入到文件里，在需要的时候再恢复

需要把这个对象的全类名，所有的属性的类型、属性名、属性值，按照一定的规则都保存起来

在恢复的时候在按照规则读取出来就组成了原来的对象
```

**序列化是一个对象存到文件里的过程**

**反序列化是从文件读取信息恢复到一个对象的过程**

##### 2、在代码中如何实现？

- 2.1 可以实现 `Serializable` 接口，实现持久保存

JDK里面提供了 `Serializable` 接口，里面没有任何方法，只是起到一个标记作用

在写入到文件里的时候如果检测到这个标记会按照一定的规则写入文件

```
// 将两个对象序列化道 test.txt
val user1 = User("Bob", 20)
val user2 = User("Tom", 21)
val oos = ObjectOutputStream(FileOutputStream(File("test.txt")))
oos.writeObject(user1)
oos.writeObject(user2)
oos.close()

// 从 test.txt 反序列化两个对象
val ois = ObjectInputStream(FileInputStream(File("test.txt")))
val user1 = ois.readObject() as User
val user2 = ois.readObject() as User
ois.close()
println(user1)
println(user2)

// 打印结果
User(name=Bob, age=20)
User(name=Tom, age=21)
```

- 2.1.1 为什么实现 Serializable 接口，就能实现序列化呢?

```
在写入文件的时候会检测是否是 Serializable 接口，如果是，就会根据响应的规则写入文件

ObjectOutputStream.java

if (obj instanceof Class) {
    writeClass((Class) obj, unshared);
} else if (obj instanceof ObjectStreamClass) {
    writeClassDesc((ObjectStreamClass) obj, unshared);
    // END Android-changed:  Make Class and ObjectStreamClass replaceable.
} else if (obj instanceof String) {
    writeString((String) obj, unshared);
} else if (cl.isArray()) {
    writeArray(obj, desc, unshared);
} else if (obj instanceof Enum) {
    writeEnum((Enum<?>) obj, desc, unshared);
} else if (obj instanceof Serializable) {
    writeOrdinaryObject(obj, desc, unshared);
} else {
    if (extendedDebugInfo) {
        throw new NotSerializableException(
          cl.getName() + "\n" + debugInfoStack.toString());
    } else {
        throw new NotSerializableException(cl.getName());
    }
}
```

- 2.2 可以实现 `Parcelable` 接口，序列化到内存

```
data class User(
    val name: String,
    val age: Int
) : Serializable, Parcelable {

    constructor(parcel: Parcel) : this(parcel.readString() ?: "null", parcel.readInt())

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeInt(age)
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}
```

**测试**

```
val user1 = User("Bob", 20)

// 序列化
val parcel = Parcel.obtain()
user1.writeToParcel(parcel, 0)
parcel.setDataPosition(0)

// 反序列化
val user = User.createFromParcel(parcel)
println("user: $user")

// 打印结果
user: User(name=Bob, age=20)
```

##### 3、总结

```
1、Serializable 是 Java 提供的持久序列化的一种规则，会保存整个类的全部信息，其中用到了反射，还有流的操作，所以大量使用可能会有性能问题。

2、Parcelable 是 Android 提供的内存序列化规则，不能用来持久序列化，序列化的时候按照字节长度将信息写入内存中，读取的时候也要按照写入的顺序读取，否则会数据错乱。

3、 Serializable 和 Parcelable 都是一种规则，Json 也是一种序列化规则，只不过 Json 相对于人来说，能让我们看懂，而前两个只有机器才能看懂。
```