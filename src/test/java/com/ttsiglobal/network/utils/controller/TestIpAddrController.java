package com.ttsiglobal.network.utils.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttsiglobal.network.utils.model.CreateRequest;
import com.ttsiglobal.network.utils.model.IpAddress;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestIpAddrController extends AbstractTest{
    private int reccnt ;
    private ObjectMapper mapper = new ObjectMapper();
    private String insStr;

    @Override
    @Before
    public void setUp() {

        super.setUp();
        jdbcTemplate.execute("delete from ip_addresses");
        jdbcTemplate.execute("insert into ip_addresses values ('120.0.0.1', 'AVAILABLE'), ('121.1.0.1', 'AVAILABLE'),('120.0.1.1', 'AVAILABLE'),('120.0.0.3', 'ACQUIRED')");
        reccnt = jdbcTemplate.queryForObject("select count(*) from ip_addresses", Integer.class);
    }

    // Test CreateIPAddresses
    @Test
    public void test_create_ip_addresses() throws Exception {
        String uri = "/ip-addresses/create";
        insStr = mapper.writeValueAsString(new CreateRequest("10.0.0.1/31", true ));
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .content(insStr).contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals("2", content);
    }


    // Test ListIPAddresses
    @Test
    public void test_get_all_ip_addresses() throws Exception {
        String uri = "/ip-addresses";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        IpAddress[] alist = super.mapFromJson(content, IpAddress[].class);
        assertTrue(alist.length == 4);
    }

    // Test AcquireIPAddress
    // Test ReleaseIPAddress
    @Test
    public void test_acquire_ip_address() throws Exception {
        String uri = "/ip-addresses/acquire";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertNotNull(content);
        uri = "/ip-addresses/release";
        insStr = mapper.writeValueAsString(new CreateRequest(content, true ));
        mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .content(insStr).contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        assertEquals(200, status);
    }

}
