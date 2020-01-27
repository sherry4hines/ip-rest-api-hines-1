package com.ttsiglobal.network.utils.repo;

import com.ttsiglobal.network.utils.model.IpAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface IpAddressRepo  extends JpaRepository<IpAddress, String> {

    /**
     * Used to return an available IP address, setting the page size controls number of IP addresses returned
     * @param limit
     * @param avail
     * @return
     */
    @Query("select i from IpAddress i where i.addressStatus = :avail ")
    Page<IpAddress> findAvailableIpAddress(Pageable limit, IpAddress.IpAddressStatus avail);

    /**
     * Used to find a specific IP address to change or view its status
     * @param ipaddr
     * @return
     */
    @Query("select i from IpAddress i where i.ipAddress = :ipaddr ")
    IpAddress findByIpAddress(String ipaddr);

    /**
     * Used to identify overlapping entries in ip address table on new create request
     * @param addrs
     * @param acqrd
     * @return
     */
    @Query("select count(i.ipAddress) from IpAddress i where i.ipAddress IN  :addrs  and i.addressStatus = :acqrd")
    int countAcquiredAddrInList(@Param("addrs") Collection<String> addrs, IpAddress.IpAddressStatus acqrd);
}
