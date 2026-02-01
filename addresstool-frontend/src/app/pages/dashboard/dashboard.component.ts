import {Component, effect, inject, OnInit} from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { CommonModule } from '@angular/common';
import {ReactiveFormsModule, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AddressService} from "@shared/services/address-service";
import {SearchService} from "./search-service";
import {SearchCriteria} from "@shared/models/searchCriteria";

@Component({
  selector: 'app-dashboard',
  imports: [TranslateModule, CommonModule, ReactiveFormsModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss', '../users/list/list.component.scss'],
})
export class DashboardComponent implements OnInit {

  private addressService = inject(AddressService);
  private searchService = inject(SearchService);

  // cities name signals
  cityNames = this.addressService.cityNames;
  cityNamesError = this.addressService.cityNamesError;
  // postal code signals
  postalCode = this.addressService.postalCode;
  postalCodeError = this.addressService.postalCodeError;
  // street
  streetNames = this.addressService.streets;
  streetNamesError = this.addressService.streetsError;

  // Search results
  persons = this.searchService.persons;
  searchError = this.searchService.searchError;

  searchForm: FormGroup;
  // Results of the search

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

    effect(() => {
      const code = this.postalCode();
      if (code) {
        this.searchForm.get('postalCode')?.setValue(code, {emitEvent: false});
      }
    });
  }

  ngOnInit(): void {
    this.initForm();
    this.onPostalCodeChange();
  }

  public initForm(): void {
    this.searchForm.setValue({
      firstName: '',
      lastName: '',
      street: '',
      number: '',
      postalCode: '',
      city: '',
      country: '',
    });
    this.addressService.resetPostalCode();
    this.addressService.resetCityName();

  }

  private onPostalCodeChange(): void {
    this.searchForm.get('postalCode')?.statusChanges.subscribe((status) => {
      if (status === 'VALID') {
        this.getCityNames();
      }
    });
    this.searchForm.get('postalCode')?.valueChanges.subscribe((value) => {
      if (this.searchForm.get('city')?.value !== undefined && this.searchForm.get('city')?.value !== '') {
        this.searchForm.patchValue({city: ''});
      }
    })
  }

  onCitySelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    const cityName = input.value;
    if (cityName) {
      this.addressService.cityNameSelected(cityName);
    }
  }

  onSubmit(): void {
    const searchCriteria = this.searchForm.value as SearchCriteria;
    this.searchService.search(searchCriteria);
  }

  private getCityNames() {
    const postalCode = this.searchForm.get('postalCode')?.value;
    console.log('component -> getCityNames: ', postalCode);
    this.addressService.postalCodeSelected(+postalCode)
  }

  protected readonly event = event;
}
