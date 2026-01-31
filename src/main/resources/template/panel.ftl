<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>${title!"Register"}</title>
    <style>
        body {
            font-family: system-ui, -apple-system, Segoe UI, Roboto, sans-serif; 
            max-width: 720px; 
            margin: 40px auto; 
            padding: 0 16px;
        }
        .panel {
            display: flex;
            flex-direction: column;
            gap: 20px;
        }
        .panel__user-info, .panel__user-posts, .panel__user-followers, .panel__user-followers-posts, .panel__user-followed {
            border: 1px solid #ccc;
            padding: 16px;
            border-radius: 8px;
        }
        .user-info__data {
            display: flex;
            flex-direction: column;
            gap: 8px;
        }
        .user-info__data--item {
            display: flex;
            flex-direction: row;
            gap: 10px;
        }
        .post {
            border-bottom: 1px solid #eee;
            padding: 8px 0;
            display: flex;
            flex-direction: row;
            gap: 10px;
        }
    </style>
</head>
    <body>
        <div class="panel">
            <div class="panel__user-info">
                <h1> User Info: </h1>
                <div class="user-info__data">
                    <div class="user-info__data--item">
                        <p>Username:</p>
                        <p>${userData.username!"PlaceHolderUserName"}</p>
                    </div>
                    <div class="user-info__data--item">
                        <p>Email:</p>
                        <p>${userData.email!"PlaceHolderEmail"}</p>
                    </div>
                    <div class="user-info__data--item">
                        <p>Display Name:</p>
                        <p>${userData.displayName!"PlaceHolderDisplayName"}</p>
                    </div>
                    <div class="user-info__data--item">
                        <p>Created At:</p>
                        <p>${userData.createdAt!"PlaceHolderCreatedAt"}</p>
                    </div>
                </div>
            </div>
            <div class="panel__user-posts">
                <h1> User Posts: </h1>
                <div>
                <#if userPosts?? && userPosts?size gt 0>
                    <#list userPosts as post>
                        <div class="post">
                            <#-- <div>${post.id}</div> -->
                            <div>${post.content}</div>
                        </div>
                    </#list>
                <#else>
                    <div>No posts available.</div>
                </#if>
                </div>
            </div>
            <div class="panel__user-followers">
                <h1> User Followers: </h1>
                <div>
                <#if userFollowers?? && userFollowers?size gt 0>
                    <#list userFollowers as follower>
                        <div class="follower">
                            <#-- <div>${follower.id!"placeholder"}</div> -->
                            <div>${follower.displayName!"placeholder"}</div>
                        </div>
                    </#list>
                <#else>
                    <div>No followers users available.</div>
                </#if>
                </div>
            </div>
            <div class="panel__user-followed">
                <h1> User Following: </h1>
                <div>
                <#if userFollowed?? && userFollowed?size gt 0>
                    <#list userFollowed as followed>
                        <div class="followed">
                            <#-- <div>${followed.id!"placeholder"}</div> -->
                            <div>${followed.displayName!"placeholder"}</div>
                        </div>
                    </#list>
                <#else>
                    <div>No followed users available.</div>
                </#if>
                </div>
            </div>
            <div class="panel__user-followers-posts">
                <h1> My and my followed users posts </h1>
                <div>
                <#if userTimeline?? && userTimeline?size gt 0>
                    <#list userTimeline as post>
                        <div class="followed">
                            <#-- <div>${post.id!"placeholder id"}</div> -->
                            <div>${post.content!"placeholder"}</div>
                        </div>
                    </#list>
                <#else>
                    <div>No posts available.</div>
                </#if>
                </div>
            </div>
        </div>
    </body>
</html>


