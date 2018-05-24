package io.github.biezhi.request.test;

import io.github.biezhi.request.Request;
import org.junit.Test;

/**
 * @author biezhi
 * @date 2017/9/30
 */
public class ProxyTest {

    @Test
    public void testGoogle() {
        String body = Request.get("https://google.com")
                .useProxy("23.106.138.59", 443)
                .body();
        System.out.println(body);
    }
}
