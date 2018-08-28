package com.wind.blog.common;


/**
 * 常量
 * 
 * @author qianchun
 * @date 2015年11月19日 上午11:06:19
 */
public class Constant {

    /**
     * redis缓存时间（单位秒）
     * 
     * @author qianchun
     * @date 2015年11月19日 上午11:43:37
     */
    public class RedisTimeout {
        public static final int SECONDS_TEN = 10;
        public static final int SECONDS_FIFTEEN = 15;
        public static final int SECOND_THIRTY = 30;

        public static final int MINUTES_TEN = 10 * 60;
        public static final int MINUTES_FIFTEEN = 15 * 60;
        public static final int MINUTES_THIRTY = 30 * 60;

        public static final int HOURS_ONE = 1 * 60 * 60;
        public static final int HOURS_TWO = 2 * 60 * 60;
        public static final int HOURS_THREE = 30 * 60;
        public static final int HOURS_SIX = 6 * 60 * 60;
        public static final int HOURS_TEN = 10 * 60 * 60;
        public static final int HOURS_TWELVE = 12 * 60 * 60;
        public static final int HOURS_EIGHTEEN = 18 * 60 * 60;

        public static final int DAY_ONE = 1 * 24 * 60 * 60;
        public static final int DAY_TWO = 2 * 24 * 60 * 60;
        public static final int DAY_THREE = 3 * 24 * 60 * 60;
        public static final int DAY_SEVEN = 7 * 24 * 60 * 60;
    }
    
    /**
     * 操作状态
     * 
     * @author qianchun
     * @date 2016年1月25日 下午4:53:31
     */
    public class MetaCode {
        public static final int SUCCESS = 1000;
        public static final int FAIL = 1001;
        public static final int PARAMS_ERROR = 1003;
        public static final int NOT_LOGIN = 1004;
        public static final int NO_RIGHT = 1005;
        
        public static final int USERNAME_PASSWORD_WRONG = 1100;
        public static final int USER_DISABLE = 1101;
    }
    
    public class MetaMsg {
        public static final String SUCCESS = "请求成功";
        public static final String FAIL = "请求失败";
        public static final String PARAMS_ERROR = "参数不正确";
        public static final String NOT_LOGIN = "请先登录";
        public static final String NO_RIGHT = "该用户没权限";
        
        public static final String USERNAME_PASSWORD_WRONG = "用户名或密码错误";
        public static final String USER_DISABLE = "该用户已经停用";
    }
    
    public class ServiceMsg {
    	public static final String SUCCESS = "请求成功";
    	public static final String FAIL = "请求失败";
    	public static final String PARAMS_ERROR = "参数不正确";
    	public static final String ID_INCREMENT_ERROR = "主键自增异常";
    }
    
    /**
     * 单身
     * 
     * @author qianchun
     * @date 2016年2月2日 下午12:05:52
     */
    public class Single {
        public static final int FALSE = 0;
        public static final int TRUE = 1;
    } 
    
    /**
     * 用户状态
     * 
     * @author qianchun
     * @date 2016年2月2日 下午12:05:43
     */
    public class UserStatus {
        public static final int DISABLE = 0;
        public static final int ENABLE = 1;
    }
    
    /**
     * 逻辑删除状态
     * 
     * @author qianchun
     * @date 2016年2月2日 下午12:05:10
     */
    public class DeleteStatus {
        public static final int ENABLE = 0;
        public static final int DISABLE = 1;
    }
    
    public class IsDelete {
        public static final int YES = 1;
        public static final int NO = 0;
    }
    
    /**
     * 性别 
     * 
     * @author qianchun
     * @date 2016年2月2日 下午12:04:58
     */
    public class Sex {
        public static final int FEMALE = 0;
        public static final int MALE = 1;
    }
    
    public class LoginType {
        public static final int EMAIL = 1;
        public static final int MOBILE = 2;
    }
    
    public class RelationType {
        public static final int NO_RELATION = 0;//没关系
        public static final int FRIEND_TO = 1;//关注
        public static final int FRIEND_BY = 2;//被关注
        public static final int FRIEND_EACH = 3;//互相关注
        public static final int DEFRIEND_TO = -1;//拉黑
        public static final int DEFRIEND_BY = -2;//被拉黑
        public static final int DEFRIEND_EACH = -3;//互相拉黑
        
    }
    
    public class RelationFocus {
        public static final int YES = 1;//特别关注
        public static final int NO = 0;//非特别关注
    }
    
    
    public static final int LIMIT_50 = 0;
    
    public class MongoSort {
    	public static final int DESC = -1;
    	public static final int ASC = 1;
    }
    
    
    //耗时日志模板
    public static final String LOG_UID_URL_DESC_COSTTIME = "用户uid:{}; 请求url:{}; 接口描述:{}; 耗时{}毫秒";
    //--------------------------------- article constant-----------------------------------------
    public class ArticleStatus {
    	public static final int DRAFT = 1;//1、草稿；
    	public static final int PRIVATE = 2;//私有；
    	public static final int PUBLISH = 3;//发布；
    	public static final int PUBLISH_CANCEL = 4;//取消发布；
    }
    
    public class ArticleFrom {
    	public static final String CSDNBLOGS = "CSDN";//CSDN博客
    	public static final String CNBLOGS= "博客园";//博客园
    }
    public class ArticleHomeUrl {
    	public static final String CSDNBLOGS = "http://blog.csdn.net";//CSDN博客
    	public static final String CNBLOGS= "http://www.cnblogs.com";//博客园
    }
    //--------------------------------- link constant-----------------------------------------
    public class LINK_IS_PARSE {
        public static final int NO = 0;
    	public static final int ING = 1;
    	public static final int YES = 2;
    }
    public static final int LINK_MAX_THREAD = 20;

    //--------------------------------- comment constant-----------------------------------------
    //评论根路径父ID
    public static final int COMMENT_ROOT_PID = 0;
    /**
     * 评论对象类型
     * 
     * @author qianchun
     * @date 2016年2月2日 下午12:04:20
     */
    public class CommentTargetType {
        public static final int MOMENT = 1;
        public static final int ARTICLE = 2;
    }
    
    /**
     * 回复操作类型
     * 1、评论；2、回复；3、转发
     * 
     * @author qianchun
     * @date 2016年2月2日 下午12:04:08
     */
    public class CommentOperateType {
        public static final int COMMENT = 1;
        public static final int REPLY = 2;
        public static final int REPOST = 3;
    }
    public static final int DEFAULT_PAGE_START = 1;
    public static final int DEFAULT_PAGE_LIMIT = 20;
    
    public class UserRecommend {
    	public static final int NEW_USER = 1;//新注册推荐用户
    	public static final int DEFAULT = 2;//默认推荐用户
    }
    public class MongoName {
    	public static final String DATABASE_WIND = "wind";
    	public static final String COLLECTION_LINK = "link";
    	public static final String COLLECTION_ARTICLE = "article";
    	public static final String COLLECTION_ARTICLE_LINK = "article_link";
    }


    //************************************************** rabbitmq ******************************************************

    /**
     * rabbitmq exchange
     */
    public class RabbitmqExchange {
        public static final String ARTICLE = "article_exchange";
        public static final String ARTICLE_LINK = "article_link_exchange";
    }

    /**
     * rabbitmq queue
     */
    public class RabbitmqQueue {
        public static final String ARTICLE = "article_queue";
        public static final String ARTICLE_LINK = "article_link_queue";
    }

    public class RabbitmqRoutingKey {
        public static final String ARTICLE = "article_routing";
        public static final String ARTICLE_LINK = "article_link_routing";
    }
}
