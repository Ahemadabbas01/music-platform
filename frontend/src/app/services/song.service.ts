import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { PageResponse } from "../models/page-response.model";
import { SongResponse } from "../models/song.model";
import { Observable } from "rxjs";

@Injectable({ providedIn: 'root' })
export class SongService {

  private readonly http = inject(HttpClient);
  private readonly baseUrl = 'http://localhost:8080/api';

getSongs(page: number = 0, size: number = 20, q?: string): Observable<PageResponse<SongResponse>> {
  const params: Record<string, string> = {
    page: String(page),
    size: String(size)
  };
  if (q && q.trim().length > 0) {
    params['q'] = q.trim();
  }
  return this.http.get<PageResponse<SongResponse>>(
    `${this.baseUrl}/songs`,
    { params }
  );
}

  getSongById(id: number): Observable<SongResponse> {
    return this.http.get<SongResponse>(`${this.baseUrl}/songs/${id}`);
  }


}