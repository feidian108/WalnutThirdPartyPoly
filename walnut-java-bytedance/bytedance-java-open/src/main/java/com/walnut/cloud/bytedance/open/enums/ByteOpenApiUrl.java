package com.walnut.cloud.bytedance.open.enums;


import com.walnut.cloud.bytedance.open.api.ByteOpenConfigStorage;
import com.walnut.cloud.bytedance.open.config.ByteOpenHostConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.walnut.cloud.bytedance.open.config.ByteOpenHostConfig.*;


public interface ByteOpenApiUrl {

    default String getUrl(ByteOpenConfigStorage config) {

        ByteOpenHostConfig hostConfig = null;
        if (config != null) {
            hostConfig = config.getHostConfig();
        }
        return buildUrl(hostConfig, this.getPrefix(), this.getPath());

    }

    String getPath();

    String getPrefix();

    @AllArgsConstructor
    @Getter
    enum OAuth2 implements ByteOpenApiUrl {

        DOU_OAUTH2_ACCESS_TOKEN_URL( API_DEFAULT_HOST_URL, "/platform/oauth/connect?client_key=%s&response_type=code&scope=%s&redirect_uri=%s&optionalScope=%s&state=%s" ),

        TOU_OAUTH2_ACCESS_TOKEN_URL( TOU_DEFAULT_HOST_URL, "/oauth/authorize?client_key=%s&response_type=code&scope=%s&redirect_uri=%s&state=%s" ),

        GUA_OAUTH2_ACCESS_TOKEN_URL( GUA_DEFAULT_HOST_URL, "/oauth/connect?client_key=%s&response_type=code&scope=%s&redirect_uri=%s&optionalScope=%s&state=%s" ),


        DOU_OAUTH_GET_ACCESS_TOKEN_URL( API_DEFAULT_HOST_URL, "/oauth/access_token/" ),
        TOU_OAUTH_GET_ACCESS_TOKEN_URL( TOU_DEFAULT_HOST_URL, "/oauth/access_token/" ),
        GUA_OAUTH_GET_ACCESS_TOKEN_URL( GUA_DEFAULT_HOST_URL, "/oauth/access_token/" ),


        DOU_OAUTH_REFRESH_ACCESS_TOKEN_URL( API_DEFAULT_HOST_URL, "/oauth/refresh_token/" ),
        DOU_OAUTH_RENEW_REFRESH_TOKEN_URL( API_DEFAULT_HOST_URL, "/oauth/renew_refresh_token/" )

        ;

        private final String prefix;
        private final String path;
    }

    @AllArgsConstructor
    @Getter
    enum User implements ByteOpenApiUrl {

        DOU_GET_FANS_LIST_URL( API_DEFAULT_HOST_URL, "/fans/list/?open_id=%s&cursor=%s&count=%s" ),
        DOU_GET_FOLLOW_LIST_URL( API_DEFAULT_HOST_URL, "/following/list/?open_id=%s&cursor=%s&count=%s" ),
        ;

        private final String prefix;
        private final String path;
    }

    @AllArgsConstructor
    @Getter
    enum DataExternal implements ByteOpenApiUrl {

        DOU_USER_ITEM_DATA_URL( API_DEFAULT_HOST_URL, "/data/external/user/item/?open_id=%s&date_type=%s" ),
        DOU_USER_FANS_DATA_URL( API_DEFAULT_HOST_URL, "/data/external/user/fans/?open_id=%s&date_type=%s" ),
        DOU_USER_LIKE_DATA_URL( API_DEFAULT_HOST_URL, "/data/external/user/like/?open_id=%s&date_type=%s" ),
        DOU_USER_COMMENT_DATA_URL( API_DEFAULT_HOST_URL, "/data/external/user/comment/?open_id=%s&date_type=%s" ),
        DOU_USER_SHARE_DATA_URL( API_DEFAULT_HOST_URL, "/data/external/user/share/?open_id=%s&date_type=%s" ),
        DOU_USER_PROFILE_DATA_URL( API_DEFAULT_HOST_URL, "/data/external/user/profile/?open_id=%s&date_type=%s" ),

        DOU_ITEM_BASE_DATA_URL( API_DEFAULT_HOST_URL, "/data/external/item/base/?open_id=%s&item_id=%s" ),
        DOU_ITEM_LIKE_DATA_URL( API_DEFAULT_HOST_URL, "/data/external/item/like/?open_id=%s&item_id=%s&date_type=%s" ),
        DOU_ITEM_COMMENT_DATA_URL( API_DEFAULT_HOST_URL, "/data/external/item/comment/?open_id=%s&item_id=%s&date_type=%s" ),
        DOU_ITEM_PLAY_DATA_URL( API_DEFAULT_HOST_URL, "/data/external/item/play/?open_id=%s&item_id=%s&date_type=%s" ),
        DOU_ITEM_SHARE_DATA_URL( API_DEFAULT_HOST_URL, "/data/external/item/share/?open_id=%s&item_id=%s&date_type=%s" ),

        DOU_FANS_DATA_URL( API_DEFAULT_HOST_URL, "/fans/data/?open_id=%s" ),
        DOU_FANS_SOURCE_URL( API_DEFAULT_HOST_URL, "/data/extern/fans/source/?open_id=%s" ),
        DOU_FANS_FAVOURITE_URL( API_DEFAULT_HOST_URL, "/data/extern/fans/favourite/?open_id=%s" ),
        DOU_FANS_COMMENT_URL( API_DEFAULT_HOST_URL, "/data/extern/fans/comment/?open_id=%s" ),

        DOU_HOT_SEARCH_SENTENCES_HOT_URL( API_DEFAULT_HOST_URL, "/hotsearch/sentences/"),
        DOU_HOT_SEARCH_TRENDING_SENTENCES_URL( API_DEFAULT_HOST_URL, "/hotsearch/trending/sentences/?cursor=%s&count=%s"),
        DOU_HOT_SEARCH_VIDEOS_URL( API_DEFAULT_HOST_URL, "/hotsearch/videos/?hot_sentence=%s" ),

        DOU_STAR_HOT_LIST_URL( API_DEFAULT_HOST_URL, "/star/hot_list/?hot_list_type=%s" ),
        DOU_STAR_AUTHOR_SCORE_URL( API_DEFAULT_HOST_URL, "/star/author_score/?open_id=%s" ),
        DOU_STAR_AUTHOR_SCORE_V2_URL( API_DEFAULT_HOST_URL, "/star/author_score_v2/?unique_id=%S" ),

        DOU_DISCOVERY_ENT_RANK_ITEM_URL( API_DEFAULT_HOST_URL, "/discovery/ent/rank/item/?type=%s&version=%s" ),
        DOU_DISCOVERY_ENT_RANK_VERSION_URL( API_DEFAULT_HOST_URL, "/discovery/ent/rank/version/?type=%s&count=%s&cursor=%s" ),

        DOU_MP_ITEM_CLICK_DISTRIBUTION_URL( API_DEFAULT_HOST_URL, "/data/external/anchor/mp_item_click_distribution/?open_id=%s&access_token=%s&mp_id=%s&date_type=%s" ),

        DOU_BILLBOARD_HOT_VIDEO_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/hot_video/" ),
        DOU_BILLBOARD_SPORT_OVERALL_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/sport/overall/" ),
        DOU_BILLBOARD_SPORT_BASKETBALL_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/sport/basketball/" ),
        DOU_BILLBOARD_SPORT_SOCCER_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/sport/soccer/" ),
        DOU_BILLBOARD_SPORT_COMPREHENSIVE_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/sport/comprehensive/" ),
        DOU_BILLBOARD_SPORT_FITNESS_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/sport/fitness/" ),
        DOU_BILLBOARD_SPORT_OUTDOORS_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/sport/outdoors/" ),
        DOU_BILLBOARD_SPORT_TABLE_TENNIS_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/sport/table_tennis/" ),
        DOU_BILLBOARD_SPORT_CULTURE_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/sport/culture/" ),

        DOU_BILLBOARD_AMUSEMENT_OVERALL_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/amusement/overall/" ),
        DOU_BILLBOARD_AMUSEMENT_NEW_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/amusement/new/" ),

        DOU_BILLBOARD_GAME_CONSOLE_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/game/console/" ),
        DOU_BILLBOARD_GAME_INF_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/game/inf/" ),

        DOU_BILLBOARD_FOOD_OVERALL_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/food/overall/" ),
        DOU_BILLBOARD_FOOD_NEW_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/food/new/" ),
        DOU_BILLBOARD_FOOD_TUTORIAL_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/food/tutorial/" ),
        DOU_BILLBOARD_FOOD_SHOP_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/food/shop/" ),

        DOU_BILLBOARD_DRAMA_OVERALL_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/drama/overall/" ),

        DOU_BILLBOARD_CAR_OVERALL_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/car/overall/" ),
        DOU_BILLBOARD_CAR_COMMENT_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/car/comment/" ),
        DOU_BILLBOARD_CAR_PLAY_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/car/play/" ),
        DOU_BILLBOARD_CAR_USE_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/car/use/" ),
        DOU_BILLBOARD_CAR_DRIVER_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/car/driver/" ),

        DOU_BILLBOARD_TRAVEL_OVERALL_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/travel/overall/" ),
        DOU_BILLBOARD_TRAVEL_NEW_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/travel/new/" ),

        DOU_BILLBOARD_COSPLAY_OVERALL_INF_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/cospa/overall/" ),
        DOU_BILLBOARD_COSPLAY_QING_MAN_INF_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/cospa/qing_man/" ),
        DOU_BILLBOARD_COSPLAY_OUT_SHOT_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/cospa/out_shot/" ),
        DOU_BILLBOARD_COSPLAY_PAINTING_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/cospa/painting/" ),
        DOU_BILLBOARD_COSPLAY_VOICE_CONTROL_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/cospa/voice_control/" ),
        DOU_BILLBOARD_COSPLAY_BRAIN_CAVITY_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/cospa/brain_cavity/" ),
        DOU_BILLBOARD_COSPLAY_NEW_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/cospa/new/" ),

        DOU_BILLBOARD_STARS_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/stars/" ),
        DOU_BILLBOARD_LIVE_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/live/" ),

        DOU_BILLBOARD_MUSIC_HOT_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/music/hot/" ),
        DOU_BILLBOARD_MUSIC_SOAR_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/music/soar/" ),
        DOU_BILLBOARD_MUSIC_ORIGINAL_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/music/original/" ),

        DOU_BILLBOARD_TOPIC_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/topic/" ),
        DOU_BILLBOARD_PROP_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/prop/" ),

        ;
        private final String prefix;
        private final String path;
    }
}
