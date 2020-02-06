package com.trust.messaging;

import java.io.IOException;

/**
 * @author NIsaev on 19.12.2019
 */
public class Main {
    public static void main(String[] args) throws IOException {
        try {
            Player player = new Player("localhost", 2535);
            player.doLogic();
        } catch (Exception t) {
            t.printStackTrace();
        }
    }
}
