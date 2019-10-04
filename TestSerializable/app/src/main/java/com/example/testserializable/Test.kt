package com.example.testserializable

import android.os.Parcel
import android.os.Parcelable
import java.io.*

data class User(
    val name: String,
    val age: Int
) : Serializable, Parcelable {

    constructor(parcel: Parcel) : this(parcel.readString() ?: "null", parcel.readInt()) {
    }

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

fun testSerializable() {
    val user1 = User("Bob", 20)
    val user2 = User("Tom", 21)

    val oos = ObjectOutputStream(FileOutputStream(File("test.txt")))
    oos.writeObject(user1)
    oos.writeObject(user2)
    oos.close()
}

fun testDeSerializable() {
    val ois = ObjectInputStream(FileInputStream(File("test.txt")))
    val user1 = ois.readObject() as User
    val user2 = ois.readObject() as User
    ois.close()
    println(user1)
    println(user2)
}

fun testParcelable() {
    val user1 = User("Bob", 20)

    // 序列化
    val parcel = Parcel.obtain()
    user1.writeToParcel(parcel, 0)
    parcel.setDataPosition(0)
    // 反序列化
    val user = User.createFromParcel(parcel)
    println("user: $user")
}