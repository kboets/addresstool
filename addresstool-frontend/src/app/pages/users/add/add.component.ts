import { Component, effect, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HotToastService } from '@ngxpert/hot-toast';
import { AddressService } from '@shared/services/address-service';
import { UserService } from '../user-service';
import { Person } from '@shared/models/person';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-list',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.scss', '../../dashboard/dashboard.component.scss'],
  standalone: false,
})
export class AddComponent implements OnInit {
  private addressService = inject(AddressService);
  private personService = inject(UserService);
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

  onSubmit() {
    if (this.personForm.valid) {
      const personData: Person = this.personForm.value;
      if (this.isEditing) {
        const personToUpdate = { ...personData, id: this.currentPersonId };
        this.personService.update(personToUpdate).subscribe({
          next: () => {
            this._toast.success('Persoon succesvol gewijzigd.');
            this.loadPeople(personData as Person);
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
}
