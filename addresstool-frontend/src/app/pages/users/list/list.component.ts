import { Component, inject, OnInit } from '@angular/core';
import { UserService } from '../user-service';
import { Person } from '@shared/models/person';
import { Router } from '@angular/router';
import { HotToastService } from '@ngxpert/hot-toast';

@Component({
  selector: 'app-person-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss', '../../dashboard/dashboard.component.scss', '../add/add.component.scss'],
  standalone: false,
})
export class ListComponent implements OnInit {
  private personService = inject(UserService);
  private router = inject(Router);
  private toast = inject(HotToastService);

  people: Person[] = [];
  isLoading = false;
  selectedPerson: Person | null = null;

  // Pagination
  currentPage:number = 0;
  pageSize:number = 5;
  totalElements:number = 0;
  totalPages:number = 0;
  pageNumbers: number[] = [];

  ngOnInit() {
    this.loadPeople();
  }

  loadPeople() {
    this.isLoading = true;
    this.personService.getPagedPersons(this.currentPage, this.pageSize).subscribe({
      next: (response) => {
        this.people = response.content;
        this.totalElements = response.totalElements;
        this.totalPages = response.totalPages;
        this.pageNumbers = Array.from({ length: this.totalPages }, (_, i) => i);
        this.isLoading = false;
      },
      error: (error) => {
        this.toast.error('Kan personen niet laden.');
        this.isLoading = false;
      },
    });
  }

  onPageChange(page: number) {
    this.currentPage = page;
    this.loadPeople();
  }

  onPageSizeChange() {
    this.currentPage = 0; // Reset to first page when page size changes
    this.loadPeople();
  }

  onCancelModal() {
    this.selectedPerson = null;
  }

  onDelete() {
    if (!this.selectedPerson) return;
    this.personService.deleteById(this.selectedPerson.id).subscribe({
      next: () => {
        this.toast.success('Persoon verwijderd');
        this.loadPeople();
      },
      error: () => {
        this.toast.error('Fout bij het verwijderen van persoon');
      },
    });
    this.selectedPerson = null;
  }

  editPerson(person: Person) {
    this.router.navigate(['/users/add'], { queryParams: { personId: person.id } });
  }

  deletePerson(person: Person) {
    this.selectedPerson = person;
  }
}
