import com.android.build.gradle.internal.api.BaseVariantOutputImpl

plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdk = 32
    buildToolsVersion = "32.0.0"
    defaultConfig {
        applicationId = "com.yuk.miuihome"
        minSdk = 29
        targetSdk = 32
        versionCode = 4200
        versionName = "4.2.0" + (Common.getGitHeadRefsSuffix(rootProject))
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            setProguardFiles(listOf(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro", "proguard-log.pro"))
        }
        create("noResHook") {
            initWith(getByName("release"))
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/**"
            excludes += "/kotlin/**"
            excludes += "/*.txt"
            excludes += "/*.bin"
            excludes += "/okhttp3/**"
        }
        dex {
            useLegacyPackaging = true
        }
        applicationVariants.all {
            outputs.all {
                (this as BaseVariantOutputImpl).outputFileName = "MiuiHome-$versionName($versionCode)-$name.apk"
            }
        }
    }
    androidResources.additionalParameters("--allow-reserved-package-id", "--package-id", "0x64")
    ext.getGitHeadRefsSuffix = {
        try {
            // .git/HEAD描述当前目录所指向的分支信息，内容示例："ref: refs/heads/master\n"
            def headFile = new File(rootDir, '.git/HEAD')
            if (headFile.exists()) {
                String[] strings = headFile.getText('UTF-8').split(" ")
                if (strings.size() > 1) {
                    String refFilePath = '.git/' + strings[1]
                    // 根据HEAD读取当前指向的hash值，路径示例为：".git/refs/heads/master"
                    def refFile = new File(rootDir, refFilePath.replace("\n", ""))
                    // 索引文件内容为hash值+"\n"，
                    // 示例："90312cd9157587d11779ed7be776e3220050b308\n"
                    return "(${refFile.getText('UTF-8').substring(0, 7)})"
                }
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
        return ""
    }
}

dependencies {
    compileOnly("de.robv.android.xposed:api:82")
    implementation("com.microsoft.appcenter:appcenter-crashes:4.4.2")
    implementation("com.microsoft.appcenter:appcenter-analytics:4.4.2")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.3")
    implementation("androidx.recyclerview:recyclerview:1.2.1")
}
