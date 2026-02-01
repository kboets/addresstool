import {Address} from "@shared/models/address";

export interface Person {
  firstName: string;
  lastName: string;
  birthDate: Date;
  address: Address;
}
