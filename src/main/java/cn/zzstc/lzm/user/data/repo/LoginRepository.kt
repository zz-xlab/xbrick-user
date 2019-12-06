package cn.zzstc.lzm.user.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cn.zzstc.lzm.common.data.NetResp
import cn.zzstc.lzm.common.data.NetworkBoundResource
import cn.zzstc.lzm.common.data.Resource
import cn.zzstc.lzm.common.data.SpAccessor
import cn.zzstc.lzm.common.net.NetworkManager
import cn.zzstc.lzm.user.const.ApiUrl
import cn.zzstc.lzm.user.data.bean.LoginInfo
import cn.zzstc.lzm.user.data.bean.LoginResp

class LoginRepository {
    fun login(loginInfo: LoginInfo): LiveData<Resource<LoginResp>> {
        return object : NetworkBoundResource<LoginResp, LoginInfo>() {

            override fun loadFromNet(): LiveData<NetResp<LoginResp>> = NetworkManager.post(ApiUrl.login(), loginInfo)

            override fun saveNetResult(resp: LoginResp) {
                SpAccessor.put(SpAccessor.TOKEN,resp.token)
            }

            override fun loadFromCache(): LiveData<LoginResp> =MutableLiveData()

        }.asLiveData()
    }
}