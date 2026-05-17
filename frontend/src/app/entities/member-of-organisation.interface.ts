import { OrganisationDto } from './organisation-dto.interface';

export type Role = 'admin' | 'trainer' | 'member';

export interface MemberOfOrganisation {
  id: number;
  organisation: OrganisationDto;
  role: Role;
}
