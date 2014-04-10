package com.boundary.sdk;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class SyslogDataFormatTest extends CamelTestSupport {
    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new BoundaryRoute(null, null);
    }

    //@Test
    public void testMarshalReverse() throws Exception {
        final byte[] bytes = new byte[]{0x1, 0x2, 0x3, 0x4};

        byte[] result = template.requestBody("direct:marshal", bytes, byte[].class);

        assertArrayEquals(new byte[]{0x4, 0x3, 0x2, 0x1}, result);
    }

    //@Test
    public void testUnmarshalReverse() throws Exception {
        final byte[] bytes = new byte[]{0x1, 0x2, 0x3, 0x4};

        byte[] result = template.requestBody("direct:unmarshal", bytes, byte[].class);

        assertArrayEquals(new byte[]{0x4, 0x3, 0x2, 0x1}, result);
    }
}
