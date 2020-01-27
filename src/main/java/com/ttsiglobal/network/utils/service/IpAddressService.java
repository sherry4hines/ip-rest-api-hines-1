package com.ttsiglobal.network.utils.service;

import com.ttsiglobal.network.utils.model.CreateRequest;
import com.ttsiglobal.network.utils.model.IpAddress;

import java.util.List;

/**
 * Interface to be used in case we want to support multiple implementations
 */
public interface IpAddressService {
    List<IpAddress> listAllIpAddresses();
    int CreateIpAddresses(CreateRequest request);
    IpAddress AcquireIpAddress();
    IpAddress ReleaseIpAddress(CreateRequest request);
    boolean isValidCreateRequest(CreateRequest request);
    boolean isAcquiredIpAddress(String ipaddr);
}
