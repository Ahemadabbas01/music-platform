import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { PageResponse } from '../models/page-response.model';
import { ArtistResponse } from '../models/artist.model';

@Injectable({ providedIn: 'root' })
export class ArtistService {

  private readonly http = inject(HttpClient);
  private readonly baseUrl = 'http://localhost:8080/api';

  getArtists(page: number = 0, size: number = 20, q?: string): Observable<PageResponse<ArtistResponse>> {
    const params: Record<string, string> = {
      page: String(page),
      size: String(size)
    };
    if (q && q.trim().length > 0) {
      params['q'] = q.trim();
    }
    return this.http.get<PageResponse<ArtistResponse>>(
      `${this.baseUrl}/artists`,
      { params }
    );
  }

  getArtistById(id: number): Observable<ArtistResponse> {
    return this.http.get<ArtistResponse>(`${this.baseUrl}/artists/${id}`);
  }
}