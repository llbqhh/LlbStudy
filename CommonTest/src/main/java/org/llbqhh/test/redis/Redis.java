package org.llbqhh.test.redis;

import redis.clients.jedis.Jedis;

public class Redis {
    //private static final String HOST = "192.168.114.238";
    private static final String HOST = "192.168.1.39";
    private static final String PASSWORD = "";
    private static final int DBINDEX = 0;
    //private static final int PORT = 6379;
    private static final int PORT = 3532;

    private Redis() {
    }

    public static Jedis getJedis() {
        Jedis jedis = new Jedis(HOST, PORT);
        if (!PASSWORD.equals("")) {
            jedis.auth(PASSWORD);
        }
        if (DBINDEX > 0) {
            jedis.select(DBINDEX);
        }
        return jedis;
    }

    public static Jedis getJedis(int db) {
        Jedis jedis = new Jedis(HOST, PORT);
        if (!PASSWORD.equals("")) {
            jedis.auth(PASSWORD);
        }
        jedis.select(db);

        return jedis;
    }

    public static void clear() {
        Jedis jedis = getJedis();
        jedis.flushDB();
        jedis.close();
    }
}
