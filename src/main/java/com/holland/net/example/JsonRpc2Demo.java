package com.holland.net.example;

import com.holland.net.json_rpc2.JsonRpc2;
import com.holland.net.json_rpc2.Request;
import com.holland.net.json_rpc2.Response;

import java.util.Map;

public class JsonRpc2Demo {
    public static void main(String[] args) throws InterruptedException {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            e.printStackTrace();
            System.exit(0);
        });

        syncSend();
        asyncMultiSend();

        Thread.sleep(10000);
        System.exit(0);
    }

    public static void syncSend() {
        final JsonRpc2 net = new JsonRpc2("https://eth-mainnet.alchemyapi.io/v2/demo");

        final Map<String, ?> eth_getBlockByNumber = net.sync.<Map<String, ?>>send(0, "eth_getBlockByNumber"
                , "0xdfaff9", false);
    }

    public static void asyncMultiSend() {
        final JsonRpc2 net = new JsonRpc2("https://eth-mainnet.alchemyapi.io/v2/demo");

        net.async.send(r -> {
                    final Response<?> eth_blockNumber = r.get(0);
                    final Response<?> eth_getBlockByNumber = r.get(1);

                    if (eth_blockNumber.failed())
                        throw new RuntimeException("eth_blockNumber.failed()");
                    if (eth_getBlockByNumber.failed())
                        throw new RuntimeException("eth_getBlockByNumber.failed()");

                    final String r0 = eth_blockNumber.<String>getResult();
                    final Map<String, ?> r1 = eth_getBlockByNumber.<Map<String, ?>>getResult();

                    System.out.println("r0 => " + r0);
                    System.out.println("r1 => " + r1);

                    System.exit(0);
                }
                , new Request(1,"eth_blockNumber")
                , new Request(2,"eth_blockNumber")
                , new Request(3,"eth_blockNumber")
                , new Request(4,"eth_blockNumber")
                , new Request(5,"eth_blockNumber")
                , new Request("eth_getBlockByNumber"
                        , new Object[]{"0xdfaff9", false})
        );
    }
}
