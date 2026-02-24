import {City} from "@shared/models/city";

export interface Address {
  street: string;
  number: string;
  box: string;
  city: City;
}
