import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { PageResponse } from "../models/page-response.model";
import { SongResponse } from "../models/song.model";
import { Observable } from "rxjs";

@Injectable({ providedIn: 'root' })
export class SongService {

  private readonly http = inject(HttpClient);
  private readonly baseUrl = 'http://localhost:8080/api';

  getSongs(opts: {
    page?: number;
    size?: number;
    q?: string;
    artistId?: number;
    albumId?: number;
  } = {}): Observable<PageResponse<SongResponse>> {
    const params: Record<string, string> = {
      page: String(opts.page ?? 0),
      size: String(opts.size ?? 20)
    };
    if (opts.q && opts.q.trim().length > 0) {
      params['q'] = opts.q.trim();
    }
    if (opts.artistId !== undefined && opts.artistId !== null) {
      params['artistId'] = String(opts.artistId);
    }
    if (opts.albumId !== undefined && opts.albumId !== null) {
      params['albumId'] = String(opts.albumId);
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