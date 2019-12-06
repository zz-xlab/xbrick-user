package cn.zzstc.lzm.user.ui

import cn.zzstc.lzm.common.route.UserPath
import cn.zzstc.lzm.common.ui.BaseActivity
import cn.zzstc.lzm.user.R
import com.alibaba.android.arouter.launcher.ARouter

class TestActivity : BaseActivity() {

    override fun setup() {
        setContentView(R.layout.activity_test)
        ARouter.getInstance().build(UserPath.LOGIN)
            .withInt("test",100).navigation()
    }
}
