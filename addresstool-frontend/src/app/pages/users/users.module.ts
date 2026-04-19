import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { UsersRoutingModule } from './users-routing.module';
import { AddComponent } from './add/add.component';
import { ListComponent } from './list/list.component';
import { PhoneFormatPipe } from '@shared/pipes/phone-format.pipe';

@NgModule({
  declarations: [AddComponent, ListComponent],
  imports: [CommonModule, UsersRoutingModule, ReactiveFormsModule, FormsModule, PhoneFormatPipe],
})
export class UsersModule {}
