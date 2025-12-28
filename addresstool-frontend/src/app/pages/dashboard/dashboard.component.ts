import {Component, inject, OnInit} from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { CommonModule } from '@angular/common';
import {ReactiveFormsModule, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AddressService} from "@shared/services/address-service";

@Component({
  selector: 'app-dashboard',
  imports: [TranslateModule, CommonModule, ReactiveFormsModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss', '../users/list/list.component.scss'],
})
export class DashboardComponent implements OnInit {

  private addressService = inject(AddressService);
  // cities names
  cities = this.addressService.cities;

  searchForm: FormGroup;
  // Results of the search
  submissions: Array<{
    firstName: string;
    lastName: string;
    street: string;
    number: string;
    postalCode: string;
    city: string;
    country: string;
  }> = [];

  constructor(private fb: FormBuilder) {
    this.searchForm = this.fb.group({
      firstName: [''],
      lastName: [''],
      street: [''],
      number: [''],
      postalCode: ['', [
        Validators.required,
        Validators.pattern('^[0-9]{4}$')]
        ],
      city: [''],
      country: [''],
    });
  }

  ngOnInit(): void {
    this.initForm();
    this.onPostalCodeChange();
  }

  private onPostalCodeChange(): void {
    this.searchForm.get('postalCode')?.statusChanges.subscribe((status) => {
      if (status === 'VALID') {
        this.getCities();
      }
    });
  }

  private initForm(): void {
    this.searchForm.setValue({
      firstName: '',
      lastName: '',
      street: '',
      number: '',
      postalCode: '',
      city: '',
      country: '',
    });
  }

  onSubmit(): void {
    console.log(this.searchForm.value);
    const criteria = this.searchForm.value as {
      firstName?: string;
      lastName?: string;
      street?: string;
      number?: string;
      postalCode?: string;
      city?: string;
    };
  }

  private getCities() {
    //console.log('before getCities -> postal code is valid');
    const postalCode = this.searchForm.get('postalCode')?.value;
    this.addressService.postalCodeSelected(+postalCode)
  }

  protected readonly event = event;
}
