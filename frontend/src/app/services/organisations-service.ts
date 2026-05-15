import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { OrganisationDto } from '../entities/organisation-dto.interface';

@Injectable({
  providedIn: 'root',
})
export class OrganisationsService {
  private readonly baseUrl = 'http://localhost:8080/';

  private readonly http = inject(HttpClient);

  fetchOrganisationsOfCurrentUser() {
    return this.http.get<Array<OrganisationDto>>(this.baseUrl + 'organisations');
  }
}
