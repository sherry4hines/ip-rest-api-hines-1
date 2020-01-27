import { Component, OnInit } from '@angular/core';
import {IpaddressService} from '../../ipaddress.service';
import {CreateRequest} from '../../create-request.model';

@Component({
  selector: 'app-ip-address-create',
  templateUrl: './ip-address-create.component.html',
  styleUrls: ['./ip-address-create.component.scss']
})
export class IpAddressCreateComponent implements OnInit {
   private request: CreateRequest;
  private counter: number;

  constructor(private service: IpaddressService) { }

  ngOnInit() {
    this.request = new CreateRequest('', true);
  }

  createIpAddresses() {
    this.service.createItem(this.request).subscribe(response => {
      console.log(response);
      this.counter = response;
    });
    this.request = new CreateRequest('', true);

  }

}
