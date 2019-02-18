package org.llbqhh.test.py4j;

import py4j.GatewayServer;

/**
 * Created by lilibiao on 2017/9/22.
 */
public class Py4jTest {
    public static void main(String[] args) {
        GatewayServer gatewayServer = new GatewayServer(new Py4jTest(), 8765);
        gatewayServer.start();
    }

    public static String echo(String msg) {
        String echo ="echo:" + msg;
        return echo;
    }

    /**
     * 可以使用下面的python脚本调用java方法
     #coding:utf-8
     from py4j.java_gateway import GatewayClient, JavaGateway
     port = 8765
     client = GatewayClient(port=int(port))
     gateway = JavaGateway(client)
     printer = gateway.entry_point
     msg = u"中s"
     print msg
     req = printer.echo(msg)
     print(req)
     *
     */
}
