package cn.zzstc.lzm.user.ui

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.ObjectAnimator
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Property
import android.view.View
import android.widget.LinearLayout
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import cn.zzstc.lzm.common.data.Resource
import cn.zzstc.lzm.common.data.ResourceState
import cn.zzstc.lzm.common.listener.KeyboardListener
import cn.zzstc.lzm.common.listener.KeyboardUtil
import cn.zzstc.lzm.common.route.CommonPath
import cn.zzstc.lzm.common.route.UserPath
import cn.zzstc.lzm.common.ui.BaseActivity
import cn.zzstc.lzm.common.util.DisplayUtil
import cn.zzstc.lzm.common.util.TipManager
import cn.zzstc.lzm.user.R
import cn.zzstc.lzm.user.data.bean.LoginResp
import cn.zzstc.lzm.user.databinding.ActivityLoginBinding
import cn.zzstc.lzm.user.ui.vm.LoginVm
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import kotlinx.android.synthetic.main.activity_login.*


@Route(path = UserPath.LOGIN)
class LoginActivity : BaseActivity(), KeyboardListener {

    private lateinit var upAnimation: ObjectAnimator
    private lateinit var downAnimation: ObjectAnimator
    private lateinit var logoZoomIn: Animator
    private lateinit var logoZoomOut: Animator
    private lateinit var binding: ActivityLoginBinding
    private var keyboardUtil = KeyboardUtil()
    private lateinit var loginViewModel: LoginVm
    override fun setup() {
        loginViewModel = ViewModelProviders.of(this).get(LoginVm::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.loginVm = loginViewModel
        binding.loginActivity = this
        keyboardUtil.listen(this, this)

        upAnimation = ObjectAnimator.ofFloat(
            llLoginForm,
            Property.of<LinearLayout, Float>(
                LinearLayout::class.java,
                Float::class.java,
                "translationY"
            ),
            0f,
            DisplayUtil.dip2px(this, -158f)
        )
        downAnimation = ObjectAnimator.ofFloat(
            llLoginForm,
            Property.of<LinearLayout, Float>(
                LinearLayout::class.java,
                Float::class.java,
                "translationY"
            ),
            0f,
            DisplayUtil.dip2px(this, 0f)
        )
        upAnimation.duration = 300
        downAnimation.duration = 300
        logoZoomIn = AnimatorInflater.loadAnimator(this, R.animator.logo_zoom_in)
        logoZoomOut = AnimatorInflater.loadAnimator(this, R.animator.logo_zoom_out)
        etLoginPhone.addTextChangedListener {
            if (etLoginPhone.text.isNotEmpty()) ivLoginInputClear.visibility =
                View.VISIBLE else ivLoginInputClear.visibility = View.GONE
        }
        ivLoginInputClear.setOnClickListener { etLoginPhone.setText("") }
        etLoginPassword.addTextChangedListener {
            if (etLoginPassword.text.isNotEmpty()) cbPasswordSwitch.visibility =
                View.VISIBLE else cbPasswordSwitch.visibility = View.GONE
        }
        cbPasswordSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) etLoginPassword.transformationMethod =
                HideReturnsTransformationMethod.getInstance() else
                etLoginPassword.transformationMethod = PasswordTransformationMethod.getInstance()
        }
    }

    override fun keyboardChange(isOpen: Boolean) {
        if (isOpen) {
            upAnimation.start()
            logoZoomIn.setTarget(ivLoginLogo)
            logoZoomIn.start()
        } else {
            downAnimation.start()
            logoZoomOut.setTarget(ivLoginLogo)
            logoZoomOut.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        keyboardUtil.removeListener()
    }


    fun loginClick(view: View) {
        loginViewModel.login().observe(this,
            Observer<Resource<LoginResp>> { resource ->
                when (resource.state) {
                    ResourceState.Success -> ARouter.getInstance().build(CommonPath.MAIN_PAGE).navigation()
                    ResourceState.Failure -> TipManager.showQMUIDialog(
                        this,
                        resource.message,
                        QMUITipDialog.Builder.ICON_TYPE_FAIL,
                        true
                    )
                    ResourceState.Loading -> {
                    }
                }
            })
    }


}
