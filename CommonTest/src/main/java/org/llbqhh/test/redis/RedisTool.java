package org.llbqhh.test.redis;

import redis.clients.jedis.Jedis;

public class RedisTool {
    public static Jedis jedis;

    static {
        jedis = new Jedis("lilibiao-test-01");
    }

    public static void changeDb() {
    }
}
