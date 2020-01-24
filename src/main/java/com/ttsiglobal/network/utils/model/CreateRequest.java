package com.ttsiglobal.network.utils.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * The Create request is used to pass a CIDR block address and inclusive host count flag to ip address create function
 * CIDR format expected is aaa.bbb.ccc.ddd/xx  where aaa.bbb.ccc.ddd represents start address and xx represents IP network prefix
 */
@ApiModel(description = "Request body used for create and release requests ")
public class CreateRequest implements Serializable {
    private static final long serialVersionUID = 1234L;

    @ApiModelProperty(notes = "For create requests enter CIDR block, for release requests enter IP address")
    private String cidr;

    @ApiModelProperty(notes = "Include the network and broadcast addresses?")
    private boolean inclFirst;

    public CreateRequest(String cidr, boolean inclFirst) {
        this.cidr = cidr;
        this.inclFirst = inclFirst;
    }

    public String getCidr() {
        return cidr;
    }

    public void setCidr(String cidr) {
        this.cidr = cidr;
    }

    public boolean isInclFirst() {
        return inclFirst;
    }

    public void setInclFirst(boolean inclFirst) {
        this.inclFirst = inclFirst;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateRequest that = (CreateRequest) o;
        return inclFirst == that.inclFirst &&
                cidr.equals(that.cidr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cidr, inclFirst);
    }

    @Override
    public String toString() {
        return "CreateRequest{" +
                "cidr='" + cidr + '\'' +
                ", inclFirst=" + inclFirst +
                '}';
    }
}
