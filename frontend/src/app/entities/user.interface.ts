import { MemberOfOrganisation } from './member-of-organisation.interface';

export default interface User {
  id: number;
  name: string;
  memberOfOrganisations: MemberOfOrganisation[];
}
