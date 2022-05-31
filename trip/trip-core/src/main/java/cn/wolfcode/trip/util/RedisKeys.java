package cn.wolfcode.trip.util;


import lombok.Getter;

/**

     *  redis key管理

     */

    @Getter
    public enum  RedisKeys{

        //短信验证码

        VERIFY_CODE("verify_code", 60 * 5L),

        //登录token

        LOGIN_TOKEN("user_login_token", 60 * 30L);

        private String prefix;

        private Long time;

        private RedisKeys(String prefix, Long time){

            this.prefix = prefix;

            this.time = time;

        }

        public String join(String ...keys){

            StringBuilder sb = new StringBuilder();

            sb.append(prefix);

            for (String key : keys) {

                sb.append(":").append(key);

            }

            return sb.toString();

        }

    }

