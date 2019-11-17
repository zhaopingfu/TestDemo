
- 使用 `ViewPager2`  

- 添加 `view` 到 `window` 最好用 `activity.getWindow().getDecorView().addView()` 来做, 如果直接用 `windowManager` 来添加的话, 不好做动画