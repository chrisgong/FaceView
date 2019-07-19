# FaceView
点头Yes摇头No，写两表情玩玩

![](https://im4.ezgif.com/tmp/ezgif-4-40e7126c920b.gif)

---

### 引入

```
<com.kinstalk.her.genie.view.widget.faceview.FaceView
    android:id="@+id/faceView"
    android:layout_width="400dp"
    android:layout_height="400dp"
    android:layout_centerInParent="true"
    android:gravity="center" />
```

### 初始化动画

```
faceView.startLoadingAnimator()
```

### 点头动画

```
faceView.startNodAnimator()
```

### 摇头动画

```
faceView.startShakeAnimator()
```

### 重置

```
faceView.restart();
```
