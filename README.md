# FaceView
点头Yes摇头No，写两表情玩玩

![gif](https://github.com/chrisgong/FaceView/blob/master/Kapture%202019-08-27%20at%2017.18.13.gif?raw=true)

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
