package com.example.lxliang.catoncloud.model

/**
 * Created by lxliang on 04/12/2017.
 */
class GetNearbyCatResponse {
    var moments: List<MomentsBean>? = null

    class MomentsBean {

        var id: String? = null
        var cat: String? = null
        var avatar: AvatarBean? = null
        var timestamp: String? = null
        var message: String? = null
        var thumbs: List<ThumbsBean>? = null

        class AvatarBean {

            var image: String? = null
        }

        class ThumbsBean {

            var image: String? = null
        }
    }
}