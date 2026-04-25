import { Component, effect, inject, OnInit } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators, AbstractControl, ValidationErrors } from '@angular/forms';
import { AddressService } from '@shared/services/address-service';
import { SearchService } from './search-service';
import { SearchCriteria } from '@shared/models/searchCriteria';
import { Person } from '@shared/models/person';
import { Router } from '@angular/router';
import { UserService } from '@pages/users/user-service';
import {PhoneFormatPipe} from "@shared/pipes";

@Component({
  selector: 'app-dashboard',
  imports: [TranslateModule, CommonModule, ReactiveFormsModule, PhoneFormatPipe],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss', '../users/add/add.component.scss'],
})
export class DashboardComponent implements OnInit {
  private addressService = inject(AddressService);
  private searchService = inject(SearchService);
  private userService = inject(UserService);
  private readonly router = inject(Router);

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
  shouldShowPersons: boolean;
  selectedPerson: Person | null = null;

  constructor(private fb: FormBuilder) {
    this.searchForm = this.fb.group(
      {
        firstName: [''],
        lastName: [''],
        street: [''],
        number: [''],
        postalCode: ['', [Validators.pattern('^[0-9]{4}$')]],
        city: [''],
        country: [''],
      },
      {
        // Form is valid if at least one criterion is filled in
        validators: [this.atLeastOneFilledValidator(['firstName', 'lastName', 'street', 'number', 'postalCode', 'city', 'country'])],
      },
    );

    effect(() => {
      const code = this.postalCode();
      if (code) {
        this.searchForm.get('postalCode')?.setValue(code, { emitEvent: false });
      }
    });

    this.shouldShowPersons = false;
  }

  onEdit(person: Person) {
    this.router.navigate(['/users/add'], {
      queryParams: { personId: person.id },
    });
  }

  onAdd() {
    this.router.navigate(['/users/add'], {
      queryParams: { personId: null },
    });
  }

  onCancelModal() {
    this.selectedPerson = null;
  }

  onDelete() {
    if (!this.selectedPerson) return;
    const id = this.selectedPerson.id;
    this.userService.deleteById(id).subscribe({
      next: () => {
        this.searchService.clearSearch();
        this.shouldShowPersons = false;
      },
      error: (error) => {
        console.error('Error deleting person', error);
      },
    });
    this.selectedPerson = null;
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
    this.addressService.resetStreetNames();
    this.searchService.clearSearch();
    this.shouldShowPersons = false;
  }

  private onPostalCodeChange(): void {
    this.searchForm.get('postalCode')?.statusChanges.subscribe((status) => {
      if (status === 'VALID') {
        this.getCityNames();
      }
    });
    this.searchForm.get('postalCode')?.valueChanges.subscribe(() => {
      if (this.searchForm.get('city')?.value !== undefined && this.searchForm.get('city')?.value !== '') {
        this.searchForm.patchValue({ city: '' });
      }
    });
  }

  onCitySelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    const cityName = input.value;
    if (cityName.length < 3) return;
    if (cityName) {
      this.addressService.cityNameSelected(cityName);
    }
  }

  onSubmit(): void {
    const searchCriteria = this.searchForm.value as SearchCriteria;
    this.searchService.search(searchCriteria);
    this.shouldShowPersons = true;
  }

  private getCityNames() {
    const postalCode = this.searchForm.get('postalCode')?.value;
    console.log('component -> getCityNames: ', postalCode);
    this.addressService.postalCodeSelected(+postalCode);
  }

  protected readonly event = event;

  /**
   * Validates that at least one of the given controls has a non-empty value.
   * Returns { atLeastOne: true } when all are empty.
   */
  private atLeastOneFilledValidator(controlNames: string[]) {
    return (group: AbstractControl): ValidationErrors | null => {
      const hasValue = controlNames.some((name) => {
        const v = group.get(name)?.value;
        return v !== null && v !== undefined && String(v).trim() !== '';
      });

      return hasValue ? null : { atLeastOne: true };
    };
  }

  protected readonly decodeURI = decodeURI;
}
