package cn.zzstc.lzm.user.connector

import android.content.Context
import cn.zzstc.lzm.common.connector.ITokenProvider
import cn.zzstc.lzm.common.data.SpAccessor
import cn.zzstc.lzm.common.net.NetworkManager
import cn.zzstc.lzm.common.route.ConnectorPath
import cn.zzstc.lzm.user.const.ApiUrl
import com.alibaba.android.arouter.facade.annotation.Route

@Route(path = ConnectorPath.TOKEN_SERVICE, name = "Token管理服务提供者")
class TokenProviderImpl : ITokenProvider {

    override fun init(context: Context?) {

    }

    override fun getToken(): String {
        return SpAccessor.getString(SpAccessor.TOKEN, "")
    }

    override fun refreshToken(): String {
        return NetworkManager.postSync<String>(ApiUrl.refreshToken(), getToken()).data ?: ""
    }

    override fun ignoreUrls(): Array<String> {
        return arrayOf(
            ApiUrl.login(),
            ApiUrl.logout(),
            ApiUrl.register(),
            ApiUrl.resetPassword(),
            ApiUrl.verify()
        )
    }

    override fun clientType(): String {
        return "app"
    }


}