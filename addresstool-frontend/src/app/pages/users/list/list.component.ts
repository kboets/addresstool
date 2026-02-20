import { Component, effect, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HotToastService } from '@ngxpert/hot-toast';
import { AddressService } from '@shared/services/address-service';
import { UserService } from '../user-service';
import {Person} from "@shared/models/person";

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss', '../../dashboard/dashboard.component.scss'],
  standalone: false,
})
export class ListComponent implements OnInit {
  private addressService = inject(AddressService);
  private personService = inject(UserService);
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
  people: Person[] = [];
  isLoading = false;
  isEditing = false;
  currentPersonId?: number;

  private readonly _fb = inject(FormBuilder);
  private readonly _toast = inject(HotToastService);

  constructor() {
    this.personForm = this._fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      birthDate: ['', Validators.required],
      addressRecord: this._fb.group({
        street: ['', Validators.required],
        number: [null, [Validators.required, Validators.min(1)]],
        box: [''],
        cityRecord: this._fb.group({
          name: ['', Validators.required],
          postalCode: ['', Validators.required],
          isMain: [true],
        }),
      }),
    });
    effect(() => {
      const code = this.postalCode();
      if (code) {
        this.personForm.get('addressRecord.cityRecord.postalCode')?.setValue(code, { emitEvent: false });
      } else {
        console.log('postal code is undefined');
      }
    });
  }

  ngOnInit() {
    this.loadPeople();
    this.onPostalCodeChange();
  }

  loadPeople() {
    this.isLoading = true;
    this.personService.getAllPersons().subscribe({
      next: (people) => {
        this.people = people;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading people', error);
        this._toast.error('Failed to load people');
        this.isLoading = false;
      },
    });
  }

  onSubmit() {
    if (this.personForm.valid) {
      const personData: Person = this.personForm.value;
      if (this.isEditing) {
        const personToUpdate = { ...personData, id: this.currentPersonId };
        this.personService.update(personToUpdate).subscribe({
          next: () => {
            this._toast.success('Person updated successfully');
            this.loadPeople();
            this.resetForm();
          },
          error: (error) => {
            console.error('Error updating person', error);
            this._toast.error('Failed to update person');
          },
        });
      } else {
        this.personService.save(personData).subscribe({
          next: () => {
            this._toast.success('Person added successfully');
            this.loadPeople();
            this.resetForm();
          },
          error: (error) => {
            console.error('Error adding person', error);
            this._toast.error('Failed to add person');
          },
        });
      }
    } else {
      this._toast.error('Please fill in all required fields');
    }
  }

  onCitySelected(event: Event): void {
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
  }

  deletePerson(id: number) {
    this.personService.deleteById(id).subscribe({
      next: () => {
        this._toast.success('Person deleted');
        this.loadPeople();
      },
      error: (error) => {
        console.error('Error deleting person', error);
        this._toast.error('Failed to delete person');
      },
    });
  }

  resetForm() {
    this.personForm.reset({
      addressRecord: {
        cityRecord: {
          isMain: true,
        },
      },
    });
    this.isEditing = false;
    this.currentPersonId = undefined;
    this.addressService.resetPostalCode();
    this.addressService.resetCityName();
  }

  public initForm(): void {
    this.personForm.setValue({
      firstName: '',
      lastName: '',
      birthDate: '',
      addressRecord: {
        street: '',
        number: null,
        box: '',
        cityRecord: {
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
    this.personForm.get('addressRecord.cityRecord.postalCode')?.statusChanges.subscribe((status) => {
      if (status === 'VALID') {
        this.getCityNames();
      }
    });
    this.personForm.get('addressRecord.cityRecord.postalCode')?.valueChanges.subscribe((value) => {
      if (
        this.personForm.get('addressRecord.cityRecord.name')?.value !== undefined &&
        this.personForm.get('addressRecord.cityRecord.name')?.value !== ''
      ) {
        this.personForm.patchValue({ cityRecord: { name: '' } });
      }
    });
  }

  private getCityNames() {
    const postalCode = this.personForm.get('addressRecord.cityRecord.postalCode')?.value;
    console.log('component -> getCityNames: ', postalCode);
    this.addressService.postalCodeSelected(+postalCode);
  }
}
