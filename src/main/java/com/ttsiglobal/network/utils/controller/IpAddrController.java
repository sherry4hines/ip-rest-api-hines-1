package com.ttsiglobal.network.utils.controller;

import com.ttsiglobal.network.utils.config.InvalidRequestException;
import com.ttsiglobal.network.utils.config.ResourceNotFoundException;
import com.ttsiglobal.network.utils.model.CreateRequest;
import com.ttsiglobal.network.utils.model.IpAddress;
import com.ttsiglobal.network.utils.service.IpAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/ip-addresses")
@Api(value="IP Address Management System", description="Operations pertaining to maintaining list of assignable IP addresses")
public class IpAddrController {

    @Autowired
    private IpAddressService service;

    /**
     * Retrieve a list of all assignable ip addresses
     * @return
     */
    @GetMapping("")
    @ApiOperation(value = "View a list of assignable IP addresses", response = List.class)
    public List<IpAddress> getAllIpAddresses() throws ResourceNotFoundException {

        List<IpAddress> addresses = service.listAllIpAddresses();
        if (addresses == null || addresses.isEmpty()) {
            throw new ResourceNotFoundException("There are no IP Addresses available in the pool.");
        }
        return addresses;
    }

    /**
     * Acquire an "AVAILABLE" ip address
     * @return
     */
    @GetMapping("acquire")
    @ApiOperation(value = "Acquire an available IP address and mark it ACQUIRED", response = List.class)
    public String acquireIpAddress() {
        return service.AcquireIpAddress();
    }

    /**
     * Release an acquired ip address, reverting status to AVAILABLE
     * @param request
     * @return
     */
    @PostMapping("release")
    @ApiOperation(value = "Release an acquired IP address and mark it AVAILABLE", response = List.class)
    public String releaseIpAddress(
            @RequestBody CreateRequest request ) throws InvalidRequestException {
        if (!service.isAcquiredIpAddress(request.getCidr())) {
            throw new InvalidRequestException("Invalid IP address specified or address specified is not acquired...");
        }
        return service.ReleaseIpAddress(request.getCidr());
    }

    /**
     * Create and add a list of ip addresses and place them in AVAILABLE status given a network CIDR block
    * to include the first ip address in the list, set inclFirst to true. The first ip address in a CIDR blck is usually reserved for broadcasting
     * @param request
     * @return
     */
    @PostMapping("create")
    @ApiOperation(value = "Add to list of assignable IP addresses. Requires CIDR block, optional flag to exclude network and broadcast ip addresses", response = List.class)
    public int createIpAddresses(
        @ApiParam(value = "Create request holding CIDR block and inclusive flag", required = true)     @RequestBody CreateRequest request
    ) throws InvalidRequestException {
        if (!service.isValidCreateRequest(request)) {
            throw new InvalidRequestException("Invalid CIDR address specified or range of IP addresses includes IP addresses already in use.");
        }
        return service.CreateIpAddresses(request);
    }
}
