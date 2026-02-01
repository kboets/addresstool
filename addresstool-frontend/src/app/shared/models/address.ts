import {City} from "@shared/models/city";

export interface Address {
  street: string;
  number: number;
  box: string;
  city: City;
}
