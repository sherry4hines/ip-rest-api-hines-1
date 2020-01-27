package com.ttsiglobal.network.utils.service;

import com.ttsiglobal.network.utils.model.CreateRequest;
import com.ttsiglobal.network.utils.model.IpAddress;
import com.ttsiglobal.network.utils.repo.IpAddressRepo;
import org.apache.commons.net.util.SubnetUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Service to achieve required api tasks to maintain and view IP Addresses
 */
@Service
public class IpAddressServiceImpl implements IpAddressService {
    private final Logger LOG = LoggerFactory.getLogger("IpAddressServiceImpl");

    private final IpAddressRepo repo;

    public IpAddressServiceImpl(IpAddressRepo repo) {
        this.repo = repo;
    }

    @Override
    @Transactional( readOnly = true)
    public List<IpAddress> listAllIpAddresses() {
        List<IpAddress> results = new ArrayList<>();
        results.addAll(repo.findAll());
        return results;
    }

    @Override
    @Transactional
    public int CreateIpAddresses(CreateRequest request) {
        SubnetUtils utils = new SubnetUtils(request.getCidr());
        utils.setInclusiveHostCount(request.isInclFirst());
        List<IpAddress> addresses = new ArrayList<>();
        String[] addrs = utils.getInfo().getAllAddresses();
        for (String addr : addrs) {
            addresses.add(new IpAddress(addr, IpAddress.IpAddressStatus.AVAILABLE));
        }
        repo.saveAll(addresses);
        return addrs.length;
    }

    @Override
    @Transactional
    public IpAddress AcquireIpAddress() {
        Sort sort = Sort.by(Sort.Direction.DESC, "ipAddress");
        Page<IpAddress> addrPage = repo.findAvailableIpAddress( PageRequest.of(0, 1, sort), IpAddress.IpAddressStatus.AVAILABLE);
        IpAddress addr = addrPage.getContent().get(0);
        addr.setAddressStatus(IpAddress.IpAddressStatus.ACQUIRED);
        repo.save(addr);
        return addr;
    }

    @Override
    @Transactional
    public IpAddress ReleaseIpAddress(CreateRequest req) {
        IpAddress addr = repo.findByIpAddress(req.getCidr());
        addr.setAddressStatus(IpAddress.IpAddressStatus.AVAILABLE);
        repo.save(addr);
        return addr;
    }

    @Override
    public boolean isValidCreateRequest(CreateRequest request) {
        try {
            SubnetUtils utils = new SubnetUtils(request.getCidr());
            utils.setInclusiveHostCount(request.isInclFirst());
            String[] addrs = utils.getInfo().getAllAddresses();
            int cnt = repo.countAcquiredAddrInList(Arrays.asList(addrs), IpAddress.IpAddressStatus.ACQUIRED);
             if (cnt > 0) {
                 LOG.error("Overlapping IP addresses found: " + cnt);
                return false;
            }
        }  catch (IllegalArgumentException ex) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isAcquiredIpAddress(String ipaddr) {
        IpAddress addr = repo.findByIpAddress(ipaddr);
        return (addr != null && addr.getAddressStatus().equals(IpAddress.IpAddressStatus.ACQUIRED));
    }
}
