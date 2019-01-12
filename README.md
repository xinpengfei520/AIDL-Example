# AIDL-Example
AIDL-Example

## 建立 AIDL 文件 & 生成及找到相应的 .java 文件

 1. 先在当前工程的main目录下建立一个名为aidl的文件夹，再右键该文件夹，在该文件夹下面新建一个名字与AndroidManifest.xml中的package相同的包，再右键该包，新建你所需的AIDL文件；
 2. 点击 Make Project，注意导包问题，因为 AIDL 不会自动导包，因此引用的类必须手动导包，需要注意一点就是引入这个类的时候需要添加相对应的aidl文件，包名必须相同；
 3. 当 build 成功之后，就可以在 app/build/generated/source/aidl/debug 目录中找到系统为我们生成的.java 文件了；