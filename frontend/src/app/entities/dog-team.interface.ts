import { Registered } from "./registered.interface";
import { Dog } from "./dog.interface";

export interface DogTeam {
  id: number;
  handler: Registered;
  dog: Dog;
}
