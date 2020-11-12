package com.zs.zs_jetpack.ui.v2ex

/**
 * @author zhangtianyang
 * V2ex最近热点的实体类
 */
data class V2exBeanItem(
    var content: String,
    var content_rendered: String,
    var created: Int,
    var id: Int,
    var last_modified: Int,
    var last_reply_by: String,
    var last_touched: Int,
    var member: Member,
    var node: Node,
    var replies: Int,
    var title: String,
    var url: String
)

data class Member(
    var avatar_large: String,
    var avatar_mini: String,
    var avatar_normal: String,
    var bio: Any?,
    var btc: Any?,
    var created: Int,
    var github: Any?,
    var id: Int,
    var location: Any?,
    var psn: Any?,
    var tagline: Any?,
    var twitter: Any?,
    var url: String,
    var username: String,
    var website: Any?
)

data class Node(
    var aliases: List<Any>,
    var avatar_large: String,
    var avatar_mini: String,
    var avatar_normal: String,
    var footer: String,
    var header: String,
    var id: Int,
    var name: String,
    var parent_node_name: String,
    var root: Boolean,
    var stars: Int,
    var title: String,
    var title_alternative: String,
    var topics: Int,
    var url: String
)