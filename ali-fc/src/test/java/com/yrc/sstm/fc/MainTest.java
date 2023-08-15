package com.yrc.sstm.fc;

import org.junit.jupiter.api.Test;

class MainTest {
    @Test
    void testHandleRequest() {
        Main main = new Main();
        main.handleRequest(null, null, null);
    }
}