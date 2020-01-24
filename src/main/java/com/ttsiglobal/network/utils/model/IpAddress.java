package com.ttsiglobal.network.utils.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * The ip_addresses table contains the pool of assignable IP addresses and their status
 * ACQUIRED or AVAILABLE
 */
@Table(name="ip_addresses")
@Entity(name="IpAddress")
@ApiModel(description = "Pool of IP addresses that may be acquired ")
public class IpAddress implements Serializable {
    private static final long serialVersionUID = 102939L;
    public enum IpAddressStatus { ACQUIRED, AVAILABLE }

    @Id
    @Column(name = "ip_address", nullable = false)
    @ApiModelProperty(notes = "IP address in dot notation ex 10.10.1.4")
    private String ipAddress;

    @Enumerated(EnumType.STRING)
    @ApiModelProperty(notes = "Status of IP address AVAILABLE OR ACQUIRED")
    @Column(name = "address_status", nullable = false)
    private IpAddressStatus addressStatus;

    public IpAddress() {
    }

    public IpAddress(String addr, IpAddressStatus stat) {
        this.ipAddress = addr;
        this.addressStatus = stat;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public IpAddressStatus getAddressStatus() {
        return addressStatus;
    }

    public void setAddressStatus(IpAddressStatus addressStatus) {
        this.addressStatus = addressStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IpAddress ipAddress1 = (IpAddress) o;
        return ipAddress.equals(ipAddress1.ipAddress) &&
                addressStatus == ipAddress1.addressStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ipAddress, addressStatus);
    }

    @Override
    public String toString() {
        return "IpAddress{" +
                "ipAddress='" + ipAddress + '\'' +
                ", addressStatus=" + addressStatus +
                '}';
    }
}
