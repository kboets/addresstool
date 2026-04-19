import { Address } from '@shared/models/address';

export interface Person {
  id: number;
  firstName: string;
  lastName: string;
  birthDate: Date;
  phoneNumber: string;
  address: Address;
}
