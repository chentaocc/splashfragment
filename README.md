# splashfragment和启动activity黑屏白屏问题
https://www.jianshu.com/p/bce1c6782f37

将启动页和MainActivity主布局合并在一起，而且在渲染Splash页面的时候，MainActivity的主布局是放在ViewStub里面的。同时数据可以在Splash页面展示的时候就进行加载，用户感知不到。
