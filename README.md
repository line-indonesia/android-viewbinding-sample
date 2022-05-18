# Android ViewBinding Sample App
In this repository, you'll find how to use View Binding in typical scenario, on Activity, Fragment, custom View, and list item.
The app itself only has one feature: Showing a generic transaction list. We do not aim to showcase many features in this repository.

Additionally, we also have a module for the timeless findViewById method. You can compare how to bind UI and layout using View Binding and findViewById. In section 4, we compare the build speed data of View Binding and findViewById.


Table of Contents:
1. [Application](#application)
2. [FindViewById](#findviewbyid)
3. [View Binding](#view-binding)
4. [Build Speed using 200 XML Layout](#build-speed-using-200-xml-layout)

# Application

![](screenshot/ViewPager.gif?raw=true)

This demo application consist of several UI:
1. Activity -> TransactionActivity 
2. Custom View -> NavigationBar
3. Fragment -> TransactionHistoryFragment
4. Adapter -> TransactionHistoryAdapter

There is 3 module available beside :app:
1. :common -> common resource, extension
2. :findviewbyid -> UI/UX with findViewById method to handle binding of UI to code
3. :viewbinding -> UI/UX with view binding feature to handle binding of UI to code

There is also 2 build variant:
1. findViewById
2. viewBinding

# FindViewById

![](screenshot/FindViewById.png?raw=true)

FindViewById is a method provided by Android. You need to manually call it for every component that you need

```
val viewPager = findViewById<ViewPager2>(R.id.viewPager)
viewPager.adapter = transactionFragmentAdapter
```

# View Binding

![](screenshot/ViewBinding.png?raw=true)

ViewBinding is the feature provided by Android to bind the UI.
You can enabled it in `build.gradle`

Before Android Gradle Plugin 4:
```
viewBinding {
    enabled = true
}
```

Android Gradle Plugin 4+:
```
buildFeatures {
    viewBinding = true
}
```

After that you can access binding class for each layout XML.
```
import com.linecorp.id.research.viewbinding.databinding.ActivityTransactionBinding

private lateinit var binding: ActivityTransactionBinding
    
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityTransactionBinding.inflate(layoutInflater)
    setContentView(binding.root)
}

binding.viewPager.adapter = transactionFragmentAdapter
```

# Build Speed using 200 XML Layout

Let's compare the build speed difference for View Binding and findViewById methods with a lot of XML layouts. We use 200 XML layouts to represent a medium-sized Android project.

You can duplicate your own layout by using [duplicate.sh](duplicate.sh). For example:

```
$ ./duplicate.sh findviewbyid/src/main/res/layout/item_transaction_history.xml 420

$ ./duplicate.sh viewbinding/src/main/res/layout/item_transaction_history.xml 420
```

We use `assemble` task to represent build speed. We also use [gradle-profiler](https://github.com/gradle/gradle-profiler) to get collect information. The profiler setting is located at [performance.scenarios](performance.scenarios) file.

```
build_view_binding {
    title = "Build for View Binding"
    versions = ["7.3.3"]
    tasks = ["assembleViewBindingDebug"]
    gradle-args = ["--parallel", "--rerun-tasks", "--offline"]
    cleanup-tasks = ["clean"]
}

build_findviewbyid {
    title = "Build for findViewById"
    versions = ["7.3.3"]
    tasks = ["assembleFindViewByIdDebug"]
    gradle-args = ["--parallel", "--rerun-tasks", "--offline"]
    cleanup-tasks = ["clean"]
}
```

Alternatively, you can use `./gradlew clean assembleViewBindingDebug  --rerun-tasks --offline --parallel --profile` for profiling.

### Results

- View Binding average = 6,070.75 ms
- FindViewById average = 3,198.62 ms

The detailed reports are located in [profile-out-viewbinding](profile-out-viewbinding/benchmark.html) and [profile-out-findviewbyid](profile-out-findviewbyid/benchmark.html) folder.

View Binding is expectedly to be slower than `findViewById`. This is because View Binding create layout binding (all 200 of them) even though the layout is not used. Also, View Binding executes three additional tasks:

```
:app:mergeViewBindingDebugShaders 	
:app:compileViewBindingDebugShaders 
:app:generateViewBindingDebugAssets 
```



