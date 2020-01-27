import { Component, OnInit } from '@angular/core';
import { IpaddressService} from '../../ipaddress.service';
import {CreateRequest} from '../../create-request.model';

@Component({
  selector: 'app-ip-address-list',
  templateUrl: './ip-address-list.component.html',
  styleUrls: ['./ip-address-list.component.scss']
})
export class IpAddressListComponent implements OnInit {
  private ipAddresses: any;

  constructor(public service: IpaddressService) {
    this.ipAddresses = [];
  }

  ngOnInit() {
    this.getAllIpAddresses();
  }

  getAllIpAddresses() {
    // Get saved list of students
    this.service.getList().subscribe(response => {
      console.log(response);
      this.ipAddresses = response;
    });
  }

  releaseIp(idx) {
    console.log('Index: ' + idx + ', IP Address: ' + this.ipAddresses[idx].ipAddress );
    const request = ({cidr: this.ipAddresses[idx].ipAddress, inclFirst: false }) as CreateRequest;
    console.log('Releasing ' + JSON.stringify(request));
    this.service.releaseIP(request).subscribe( response => {
     console.log(response);
     this.getAllIpAddresses();
    });
  }

  acquireIp() {
    // Get saved list of students
    this.service.acquireIP().subscribe(response => {
      console.log('Acquired: ' + response);
      this.getAllIpAddresses();
    });
  }

}
