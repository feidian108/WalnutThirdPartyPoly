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
    enum Video implements ByteOpenApiUrl {

        DOU_VIDEO_UPLOAD_URL(API_DEFAULT_HOST_URL, "/video/upload/?open_id=%s"),
        DOU_VIDEO_PART_INIT_URL(API_DEFAULT_HOST_URL, "/video/part/init/?open_id=%s"),
        DOU_VIDEO_PART_UPLOAD_URL(API_DEFAULT_HOST_URL, "/video/part/upload/?open_id=%s"),
        DOU_VIDEO_PART_COMPLETE(API_DEFAULT_HOST_URL, "/video/part/complete/?upload_id=%s&open_id=%s"),
        DOU_VIDEO_CREATE_URL(API_DEFAULT_HOST_URL, "/video/create/?open_id=%s"),

        DOU_IMAGE_UPLOAD_URL(API_DEFAULT_HOST_URL, "/image/upload/?open_id=%s&upload_id=%s&part_number=&s"),
        DOU_IMAGE_CREATE_URL(API_DEFAULT_HOST_URL, "/image/create/?open_id=%s"),
        DOU_VIDEO_DELETE_URL(API_DEFAULT_HOST_URL, "/video/delete/?open_id=%s"),
        DOU_VIDEO_LIST_URL(API_DEFAULT_HOST_URL, "/video/list/?open_id=%s&cursor=%s&count=%s"),
        DOU_VIDEO_DATA_URL( API_DEFAULT_HOST_URL, "/video/data/?open_id=%s" ),

        TOU_VIDEO_UPLOAD_URL(TOU_DEFAULT_HOST_URL, "/toutiao/video/upload/?open_id=%s"),
        TOU_VIDEO_CREATE_URL(TOU_DEFAULT_HOST_URL, "/toutiao/video/create/?open_id=%s"),
        TOU_VIDEO_PART_INIT_URL(TOU_DEFAULT_HOST_URL, "/toutiao/video/part/init/?open_id=%s"),
        TOU_VIDEO_PART_UPLOAD_URL(TOU_DEFAULT_HOST_URL, "/toutiao/video/part/upload/?open_id=%s&upload_id=%s&part_number=&s"),
        TOU_VIDEO_PART_COMPLETE_URL(TOU_DEFAULT_HOST_URL, "/toutiao/video/part/complete/?upload_id=%s&open_id=%s"),
        TOU_VIDEO_LIST_URL(TOU_DEFAULT_HOST_URL, "/toutiao/video/list/?open_id=%s&cursor=%s&count=%s"),
        TOU_VIDEO_DATA_URL(TOU_DEFAULT_HOST_URL, "/toutiao/video/data/?open_id=%s"),

        GUA_VIDEO_UPLOAD_URL(GUA_DEFAULT_HOST_URL, "/xigua/video/upload/?open_id=%s"),
        GUA_VIDEO_CREATE_URL(GUA_DEFAULT_HOST_URL, "/xigua/video/create/?open_id=%s"),
        GUA_VIDEO_PART_INIT_URL(GUA_DEFAULT_HOST_URL, "/xigua/video/part/init/?open_id=%s"),
        GUA_VIDEO_PART_UPLOAD_URL(GUA_DEFAULT_HOST_URL, "/xigua/video/part/upload/?open_id=%s&upload_id=%s&part_number=&s"),
        GUA_VIDEO_PART_COMPLETE_URL(GUA_DEFAULT_HOST_URL, "/xigua/video/part/complete/?upload_id=%s&open_id=%s"),
        GUA_VIDEO_LIST_URL(GUA_DEFAULT_HOST_URL, "/xigua/video/list/?open_id=%s&cursor=%s&count=%s"),
        GUA_VIDEO_DATA_URL(GUA_DEFAULT_HOST_URL, "/xigua/video/data/?open_id=%s")
        ;

        private final String prefix;
        private final String path;
    }

    @AllArgsConstructor
    @Getter
    enum ItemComment implements ByteOpenApiUrl {

        DOU_ITEM_COMMENT_LIST_URL(API_DEFAULT_HOST_URL, "/item/comment/list/?open_id=%s&cursor=%s&count=%s&item_id=%s&sort_type=%s"),
        DOU_ITEM_COMMENT_REPLY_LIST_URL(API_DEFAULT_HOST_URL, "/item/comment/reply/list/?open_id=%s&cursor=%s&count=%s&item_id=%s&comment_id=%s&sort_type=%s"),
        DOU_ITEM_COMMENT_REPLY_URL(API_DEFAULT_HOST_URL, "/item/comment/reply/?open_id=%s"),

        DOU_MESSAGE_ONCE_SEND_URL(API_DEFAULT_HOST_URL, "/message/once/send/?open_id=%s")
        ;

        private final String prefix;
        private final String path;
    }

    @AllArgsConstructor
    @Getter
    enum VideoSearch implements ByteOpenApiUrl {

        DOU_VIDEO_SEARCH_URL(API_DEFAULT_HOST_URL, "/video/search/?open_id=%s&cursor=%s&count=%s&keyword=%s"),
        DOU_VIDEO_SEARCH_COMMENT_LIST_URL(API_DEFAULT_HOST_URL, "/video/search/comment/list/?cursor=%s&count=%s&sec_item_id=%s"),
        DOU_VIDEO_SEARCH_COMMENT_REPLY_URL(API_DEFAULT_HOST_URL, "/video/search/comment/reply/?open_id=%s"),
        DOU_VIDEO_SEARCH_COMMENT_REPLY_LIST_URL(API_DEFAULT_HOST_URL, "/video/search/comment/reply/list/?cursor=%s&count=%s&sec_item_id=%s&comment_id=%s"),
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

        DOU_BILLBOARD_COSPLAY_OVERALL_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/cospa/overall/" ),
        DOU_BILLBOARD_COSPLAY_QING_MAN_URL( API_DEFAULT_HOST_URL, "/data/extern/billboard/cospa/qing_man/" ),
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

    @AllArgsConstructor
    @Getter
    enum EnterpriseLeads implements ByteOpenApiUrl {

        DOU_ENTERPRISE_LEADS_USER_LIST_URL(API_DEFAULT_HOST_URL, "/enterprise/leads/user/list/?open_id=%s&cursor=%s&count=%s&start_time=%s&end_time=%s&leads_level=%s&action_type=%s"),
        DOU_ENTERPRISE_LEADS_USER_DETAIL_URL(API_DEFAULT_HOST_URL, "/enterprise/leads/user/detail/?open_id=%s&user_id=%s"),
        DOU_ENTERPRISE_LEADS_USER_ACTION_LIST_URL(API_DEFAULT_HOST_URL, "/enterprise/leads/user/action/list/?open_id=%s&count=%s&cursor=%s&user_id=%s&action_type=%s"),
        DOU_ENTERPRISE_LEADS_TAG_LIST_URL(API_DEFAULT_HOST_URL, "/enterprise/leads/tag/list/?open_id=%s&cursor=%s&count=%s"),
        DOU_ENTERPRISE_LEADS_TAG_USER_LIST_URL(API_DEFAULT_HOST_URL, "/enterprise/leads/tag/user/list/?open_id=%s&cursor=%s&count=%s&tag_id=%s"),
        DOU_ENTERPRISE_LEADS_TAG_CREATE(API_DEFAULT_HOST_URL, "/enterprise/leads/tag/create/?open_id=%s"),
        DOU_ENTERPRISE_LEADS_TAG_UPDATE(API_DEFAULT_HOST_URL, "/enterprise/leads/tag/update/?open_id=%s"),
        DOU_ENTERPRISE_LEADS_TAG_DELETE(API_DEFAULT_HOST_URL, "/enterprise/leads/tag/delete/?open_id=%s"),
        DOU_ENTERPRISE_LEADS_TAG_USER_UPDATE(API_DEFAULT_HOST_URL, " /enterprise/leads/tag/user/update/?open_id=%s"),

        DOU_ENTERPRISE_VIDEO_COMMENT_LIST_URL(API_DEFAULT_HOST_URL, "/video/comment/list/?open_id=%s&cursor=%s&count=%s&item_id=%s"),
        DOU_ENTERPRISE_VIDEO_COMMENT_REPLY_LIST_URL(API_DEFAULT_HOST_URL, "/video/comment/reply/list/?count=%s&item_id=%s&comment_id=%s&open_id=%s&cursor=%s"),
        DOU_ENTERPRISE_VIDEO_COMMENT_REPLY_URL(API_DEFAULT_HOST_URL, "/video/comment/reply/?open_id=%s"),
        DOU_ENTERPRISE_VIDEO_COMMENT_TOP_URL(API_DEFAULT_HOST_URL, "/video/comment/top/?open_id=%s"),

        DOU_ENTERPRISE_IM_MESSAGE_SEND_URL(API_DEFAULT_HOST_URL, "/enterprise/im/message/send/?open_id=%s"),
        DOU_ENTERPRISE_IM_CARD_SAVE_URL(API_DEFAULT_HOST_URL, "/enterprise/im/card/save/?open_id=%s"),
        DOU_ENTERPRISE_IM_CARD_LIST_URL(API_DEFAULT_HOST_URL, "/enterprise/im/card/list/?open_id=%s&cursor=%s&count=%s"),
        DOU_ENTERPRISE_IM_CARD_DELETE_URL(API_DEFAULT_HOST_URL, "/enterprise/im/card/delete/?open_id=%s"),

        ;
        private final String prefix;
        private final String path;
    }

    @AllArgsConstructor
    @Getter
    enum GoodLife implements ByteOpenApiUrl {

        DOU_GOOD_LIFE_SHOP_POI_URL(API_DEFAULT_HOST_URL, "/goodlife/v1/shop/poi/query/?page=%s&size=%s"),
        DOU_GOOD_LIFE_NAMEK_FULFILMENT_PREPARE_URL(API_DEFAULT_HOST_URL, "/namek/fulfilment/prepare/?encrypted_data=%s&code=%s"),
        DOU_GOOD_LIFE_FULFILMENT_CERTIFICATE_VERIFY_URL(API_DEFAULT_HOST_URL, "/goodlife/v1/fulfilment/certificate/verify/?verify_token=%s&poi_id=%s&encrypted_codes=%s&codes=%s&order_id=%s&code_with_time_list=%s"),
        DOU_GOOD_LIFE_FULFILMENT_CERTIFICATE_CANCEL_URL(API_DEFAULT_HOST_URL, "/goodlife/v1/fulfilment/certificate/cancel/?encrypted_code=%s"),
        DOU_GOOD_LIFE_FULFILMENT_CERTIFICATE_GET_URL(API_DEFAULT_HOST_URL, "/goodlife/v1/fulfilment/certificate/get/?encrypted_code=%s&codes=%s&order_id=%s"),
        DOU_GOOD_LIFE_FULFILMENT_CERTIFICATE_VERIFY_RECORD_URL(API_DEFAULT_HOST_URL, "/goodlife/v1/fulfilment/certificate/verify_record/query/?size=%s&cursor=%s&account_id=%s&poi_ids=%s&start_time=%s&end_time=%s"),
        DOU_GOOD_LIFE_SETTLE_LEDGER_QUERY_RECORD_BY_CERT_URL(API_DEFAULT_HOST_URL, "/goodlife/v1/settle/ledger/query_record_by_cert/?certificate_ids=%s"),

        DOU_POI_SUPPLIER_SYNC_URL(API_DEFAULT_HOST_URL, "/poi/supplier/sync/?contact_phone=%s&contact_tel=%s&images=%s&merchant_uid=%s&service_provider=%s&supplier_ext_id=%s&tags=%s&latitude=%s&status=%s&type_code=%s&type_name=%s&address=%s&avg_cost=%s&customer_info=%s&description=%s&name=%s&recommends=%s&services=%s&attributes=%s&longitude=%s&open_time=%s&poi_id=%s&type=%s"),
        DOU_POI_SUPPLIER_QUERY_URL(API_DEFAULT_HOST_URL, "/poi/supplier/query/?supplier_ext_id=%s"),
        DOU_POI_QUERY_URL(API_DEFAULT_HOST_URL, "/poi/query/?amap_id=%s"),
        DOU_POI_V2_SUPPLIER_QUERY_TASK_URL(API_DEFAULT_HOST_URL, "/poi/v2/supplier/query/task/?supplier_task_ids=%s"),
        DOU_POI_V2_SUPPLIER_QUERY_SUPPLIER_URL(API_DEFAULT_HOST_URL, "/poi/v2/supplier/query/supplier/?supplier_ext_id=%s"),
        DOU_POI_V2_SUPPLIER_MATCH_URL(API_DEFAULT_HOST_URL, "/poi/v2/supplier/match/"),
        DOU_POI_V2_SUPPLIER_QUERY_ALL_URL(API_DEFAULT_HOST_URL, "/poi/supplier/query_all/"),
        DOU_POI_SUPPLIER_QUERY_CALLBACK_URL(API_DEFAULT_HOST_URL, "/poi/supplier/query_callback/?task_id=%s"),


        DOU_POI_SKU_SYNC_URL(API_DEFAULT_HOST_URL, "/poi/sku/sync/"),
        DOU_POI_SKU_EXT_HOTEL_SKU_URL(API_DEFAULT_HOST_URL, "/poi/ext/hotel/sku/?spu_ext_id=%s&start_date=%s&end_date=%s"),
        DOU_POI_V2_SPU_SYNC_URL(API_DEFAULT_HOST_URL, "/poi/v2/spu/sync/"),
        DOU_POI_V2_SPU_STATUS_SYNC_URL(API_DEFAULT_HOST_URL, " /poi/v2/spu/status_sync/"),
        DOU_POI_V2_SPU_STOCK_UPDATE_URL(API_DEFAULT_HOST_URL, "/poi/v2/spu/stock_update/"),
        DOU_POI_V2_SPU_GET_URL(API_DEFAULT_HOST_URL, "/poi/v2/spu/get/?spu_ext_id=%s&need_spu_draft=%s&spu_draft_count=%s&supplier_ids_for_filter_reason=%s"),
        DOU_POI_V2_SPU_TAKE_RATE_SYNC_URL(API_DEFAULT_HOST_URL, "/poi/v2/spu/take_rate/sync/"),

        DOU_POI_ORDER_STATUS_URL(API_DEFAULT_HOST_URL, "/poi/order/status/"),
        DOU_POI_EXT_HOTEL_ORDER_COMMIT_URL(API_DEFAULT_HOST_URL, "/poi/ext/hotel/order/commit/"),
        DOU_POI_EXT_HOTEL_ORDER_STATUS_URL(API_DEFAULT_HOST_URL, "/poi/ext/hotel/order/status/"),
        DOU_POI_EXT_HOTEL_ORDER_CANCEL_URL(API_DEFAULT_HOST_URL, "/poi/ext/hotel/order/cancel/"),
        DOU_POI_ORDER_CONFIRM_URL(API_DEFAULT_HOST_URL, "/poi/order/confirm/"),
        DOU_POI_ORDER_CONFIRM_PREPARE_URL(API_DEFAULT_HOST_URL, "/poi/order/confirm/prepare/"),
        DOU_POI_ORDER_CONFIRM_CANCEL_PREPARE_URL(API_DEFAULT_HOST_URL, "/poi/order/confirm/cancel_prepare/"),
        DOU_POI_EXT_PRESALE_GROUPON_ORDER_CREATE_URL(API_DEFAULT_HOST_URL, "/poi/ext/presale_groupon/order/create/"),
        DOU_POI_EXT_PRESALE_GROUPON_ORDER_COMMIT_URL(API_DEFAULT_HOST_URL, "/poi/ext/presale_groupon/order/commit/"),
        DOU_POI_EXT_PRESALE_GROUPON_ORDER_CANCEL_URL(API_DEFAULT_HOST_URL, "/poi/ext/presale_groupon/order/cancel/"),
        DOU_POI_ORDER_BILL_TOKEN_URL(API_DEFAULT_HOST_URL, "/poi/order/bill/token/?bill_date=%s"),
        DOU_POI_ORDER_LIST_TOKEN_URL(API_DEFAULT_HOST_URL, "/poi/order/list/token/?order_date=%s"),

        DOU_POI_ORDER_SYNC_URL(API_DEFAULT_HOST_URL, "/poi/order/sync/"),

        DOU_DATA_EXTERNAL_POI_BASE_URL(API_DEFAULT_HOST_URL, "/data/external/poi/base/?poi_id=%s&begin_date=%s&end_date=%s"),
        DOU_DATA_EXTERNAL_POI_USER_URL(API_DEFAULT_HOST_URL, "/data/external/poi/user/?poi_id=%s&date_type=%s"),
        DOU_DATA_EXTERNAL_POI_SERVICE_BASE_URL(API_DEFAULT_HOST_URL, "/data/external/poi/service_base/?poi_id=%s&service_type=%s&begin_date=%s&end_date=%s"),
        DOU_DATA_EXTERNAL_POI_SERVICE_USER_URL(API_DEFAULT_HOST_URL, "/data/external/poi/service_user/?poi_id=%s&date_type=%s&service_type=%s"),
        DOU_DATA_EXTERNAL_POI_BILLBOARD_URL(API_DEFAULT_HOST_URL, "/data/external/poi/billboard/?billboard_type=%s"),
        DOU_DATA_EXTERNAL_POI_CLAIM_LIST_URL(API_DEFAULT_HOST_URL, "/data/external/poi/claim/list/?open_id=%s&cursor=%s&count=%s"),

        DOU_POI_BASE_QUERY_AMAP_URL(API_DEFAULT_HOST_URL, "/poi/base/query/amap/?amap_id=%s"),

        DOU_POI_V2_COUPON_SYNC_URL(API_DEFAULT_HOST_URL, "/poi/v2/coupon/sync/?access_token=%s"),
        DOU_POI_V2_COUPON_SYNC_COUPON_AVAILABLE_URL(API_DEFAULT_HOST_URL, "/poi/v2/coupon/sync/coupon_available/?access_token=%s"),


        ;
        private final String prefix;
        private final String path;
    }
}
