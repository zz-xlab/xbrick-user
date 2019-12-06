package cn.zzstc.lzm.user.ui.vm

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import cn.zzstc.lzm.common.data.Resource
import cn.zzstc.lzm.user.data.bean.LoginInfo
import cn.zzstc.lzm.user.data.bean.LoginResp
import cn.zzstc.lzm.user.data.repo.LoginRepository

class LoginVm : ViewModel() {
    private val loginInfo = LoginInfo()
    private val loginRepo = LoginRepository()
    val loginEnable : ObservableBoolean=ObservableBoolean(false)

    fun onPhoneChange(s: CharSequence, start: Int, before: Int, count: Int) {
        loginInfo.phone = s.toString()
        updateLoginEnable()
    }

    fun onPwdChange(s: CharSequence, start: Int, before: Int, count: Int) {
        loginInfo.password = s.toString()
        updateLoginEnable()
    }

    private fun updateLoginEnable(){
        loginEnable.set(loginInfo.phone.length>2&&loginInfo.password.length>2)
    }

    fun login():LiveData<Resource<LoginResp>> {
        return loginRepo.login(loginInfo)
    }

}