package cn.zzstc.lzm.user.data.bean

import org.greenrobot.essentials.StringUtils


class LoginInfo{
    var phone: String = ""
    var password: String = ""
    set(value) {
        field=StringUtils.digest(value, "SHA-256", "UTF-8").toLowerCase()
    }
    var deviceId: String = ""
    var deviceType: Int = 1
}

data class LoginResp(
    val token: String?,
    val user: User?
)

data class User(
    val avatar: String,
    val gender: Int,
    val nickname: String,
    val phone: String,
    val realname: String
)