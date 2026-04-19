import { Component, effect, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HotToastService } from '@ngxpert/hot-toast';
import { AddressService } from '@shared/services/address-service';
import { UserService } from '../user-service';
import { Person } from '@shared/models/person';
import { ActivatedRoute, Router } from '@angular/router';
import {CountryService} from "@shared/services/country-service";

@Component({
  selector: 'app-list',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.scss', '../../dashboard/dashboard.component.scss'],
  standalone: false,
})
export class AddComponent implements OnInit {
  private addressService = inject(AddressService);
  private personService = inject(UserService);
  private countryService = inject(CountryService);
  private readonly route = inject(ActivatedRoute);
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

  personForm: FormGroup;
  selectedCountry: any = null;
  countries: any[] = [];
  people: Person[] = [];
  isLoading = false;
  isEditing = false;
  currentPersonId?: number;
  shouldShowPeople: boolean;

  private readonly _fb = inject(FormBuilder);
  private readonly _toast = inject(HotToastService);

  constructor() {
    this.personForm = this._fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      birthDate: ['', Validators.required],
      phoneNumber: [''],
      countryCode: [''],
      address: this._fb.group({
        street: ['', Validators.required],
        number: [null, [Validators.required, Validators.min(1)]],
        box: [''],
        city: this._fb.group({
          name: ['', Validators.required],
          postalCode: ['', Validators.required],
          isMain: [true],
        }),
      }),
    });
    effect(() => {
      const code = this.postalCode();
      if (code) {
        this.personForm.get('address.city.postalCode')?.setValue(code, { emitEvent: false });
      }
    });
    this.shouldShowPeople = false;
  }

  ngOnInit() {
    this.onPostalCodeChange();
    this.loadCountries();

    this.route.queryParamMap.subscribe((params) => {
      const personId = params.get('personId');
      const editId = personId ? Number(personId) : null;
      if (!editId || Number.isNaN(editId)) return;

      this.personService.getById(editId).subscribe({
        next: (person: Person) => {
          this.editPerson(person);
          this.clearEditQueryParam();
        },
        error: () => {
          this._toast.error('Persoon werd niet gevonden');
          this.clearEditQueryParam();
        },
      });
    });
  }

  loadPeople(person?: Person) {
    this.isLoading = true;
    if (person) {
      this.personService.getById(this.currentPersonId).subscribe({
        next: (person: Person) => {
          this.people = [person];
          this.isLoading = false;
          this.shouldShowPeople = true;
        },
        error: (error) => {
          this._toast.error('Kan persoon niet laden. Fout: ', error);
          this.isLoading = false;
          this.shouldShowPeople = false;
        },
      });
    } else {
      this.personService.getAllPersons().subscribe({
        next: (people) => {
          this.people = people;
          this.isLoading = false;
          this.shouldShowPeople = true;
        },
        error: (error) => {
          this._toast.error('Kan personen niet laden. Probeer het later opnieuw.');
          this.isLoading = false;
          this.shouldShowPeople = false;
        },
      });
    }
  }

  private loadCountries() {
    this.countryService.getEuropeanCountries().subscribe({
      next: (countries) => {
        this.countries = countries;
        const belgium = countries.find(c => c.name.toLowerCase() === 'belgium');
        if (belgium) {
          this.selectedCountry = belgium;
          this.personForm.patchValue({
            countryCode: belgium.phoneCode
          });
        }
      },
      error: (error) => {
        this._toast.error('Kan landen niet laden.');
      }
    });
  }

  selectCountry(country: any) {
    this.selectedCountry = country;
    this.onCountryChange(country.phoneCode);
  }

  onCountryChange(selectedPhoneCode: string) {
    let currentNumber = this.personForm.get('phoneNumber')?.value || '';

    // Remove any existing phone code if present at the start
    // This is a simple logic, it assumes if the number starts with any of our known phone codes, we might want to replace it.
    // However, the requirement says "Once selected this international phone code is automatically added to the phone number"

    // Let's just prepend it if it's not already there or if it's different.
    // Better: just set the phone number to start with the code if it's empty or doesn't start with a '+'
    if (!currentNumber.startsWith('+')) {
       this.personForm.patchValue({
         phoneNumber: selectedPhoneCode + currentNumber
       });
    } else {
       // If it already starts with +, we might want to replace the old code with the new one.
       // This gets complicated without knowing the old code.
       // Let's assume the user wants the selected code to BE the prefix.

       // Find if currentNumber starts with any known phone code from our list
       const oldCode = this.countries.find(c => currentNumber.startsWith(c.phoneCode));
       if (oldCode) {
         const newNumber = selectedPhoneCode + currentNumber.substring(oldCode.phoneCode.length);
         this.personForm.patchValue({
           phoneNumber: newNumber
         });
       } else {
         // If it starts with + but not a known code, just replace the first part until first space or just prepend?
         // Simplest: just replace the prefix.
         this.personForm.patchValue({
           phoneNumber: selectedPhoneCode
         });
       }
    }
  }

  onSubmit() {
    if (this.personForm.valid) {
      this.verifyPhoneNumber();
      const { countryCode, ...personData } = this.personForm.value;
      if (this.isEditing) {
        const personToUpdate = { ...personData, id: this.currentPersonId };
        this.personService.update(personToUpdate).subscribe({
          next: () => {
            this._toast.success('Persoon succesvol gewijzigd.');
            this.loadPeople(personToUpdate as Person);
            this.resetForm();
          },
          error: (error) => {
            this._toast.error('Fout opgetreden tijdens wijzigen.');
          },
        });
      } else {
        this.personService.save(personData).subscribe({
          next: (savedPerson:Person) => {
            this._toast.success('Persoon succesvol toegevoegd.');
            this.resetForm();
            this.currentPersonId = savedPerson.id;
            this.loadPeople(savedPerson);
          },
          error: (error) => {
            this._toast.error('Fout opgetreden tijdens toevoegen. Fout boodschap: ' + error.error.message + '.');
          },
        });
      }
    } else {
      this._toast.error('Vul al de verplichte velden in.');
    }
  }

  onCitySelected(event: Event): void {
    if (this.isEditing) {
      this.addressService.resetCityName();
      this.addressService.resetPostalCode();
      this.personForm.patchValue({ address: { city: { postalCode: '' }, street: '' } });
    }
    const input = event.target as HTMLInputElement;
    const cityName = input.value;
    if (cityName) {
      this.addressService.cityNameSelected(cityName);
    }
  }

  editPerson(person: Person) {
    this.isEditing = true;
    this.currentPersonId = person.id;
    this.personForm.patchValue(person);

    if (person.phoneNumber) {
      const country = this.countries.find(c => person.phoneNumber.startsWith(c.phoneCode));
      if (country) {
        this.selectedCountry = country;
        this.personForm.patchValue({ countryCode: country.phoneCode });
      }
    }
  }

  deletePerson(id: number) {
    this.personService.deleteById(id).subscribe({
      next: () => {
        this._toast.success('Person deleted');
        //this.loadPeople();
      },
      error: (error) => {
        console.error('Error deleting person', error);
        this._toast.error('Failed to delete person');
      },
    });
  }

  resetForm() {
    this.personForm.reset({
      address: {
        city: {
          isMain: true,
        },
      },
    });
    this.isEditing = false;
    this.currentPersonId = undefined;
    this.addressService.resetPostalCode();
    this.addressService.resetCityName();
    this.addressService.resetStreetNames();
    this.people = [];
    this.shouldShowPeople = false;

    const belgium = this.countries.find((c) => c.name.toLowerCase() === 'belgium');
    if (belgium) {
      this.selectedCountry = belgium;
      this.personForm.patchValue({
        countryCode: belgium.phoneCode,
        phoneNumber: belgium.phoneCode,
      });
    }
  }

  public initForm(): void {
    this.personForm.setValue({
      firstName: '',
      lastName: '',
      birthDate: '',
      address: {
        street: '',
        number: null,
        box: '',
        city: {
          name: '',
          postalCode: '',
          isMain: true,
        },
      },
    });
    this.addressService.resetPostalCode();
    this.addressService.resetCityName();
  }

  private onPostalCodeChange(): void {
    this.personForm.get('address.city.postalCode')?.statusChanges.subscribe((status) => {
      if (status === 'VALID') {
        this.getCityNames();
      }
    });
    this.personForm.get('address.city.postalCode')?.valueChanges.subscribe(() => {
      if (this.personForm.get('address.city.name')?.value !== undefined && this.personForm.get('address.city.name')?.value !== '') {
        this.personForm.patchValue({ city: { name: '' } });
      }
    });
  }

  private getCityNames() {
    const postalCode = this.personForm.get('address.city.postalCode')?.value;
    console.log('component -> getCityNames: ', postalCode);
    this.addressService.postalCodeSelected(+postalCode);
  }

  private clearEditQueryParam(): void {
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: { editId: null },
      queryParamsHandling: 'merge',
      replaceUrl: true,
    });
  }

  private verifyPhoneNumber() {
    let currentNumber = this.personForm.get('phoneNumber')?.value || '';
    if (currentNumber !== '' && !currentNumber.startsWith('+')) {
      if (currentNumber.startsWith(0)) {
        // remove 0
        currentNumber = currentNumber.slice(1);
      }
      this.personForm.patchValue({
        phoneNumber: this.selectedCountry.phoneCode + currentNumber
      });
    }
  }

}
