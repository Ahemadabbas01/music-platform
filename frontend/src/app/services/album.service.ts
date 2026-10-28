import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { PageResponse } from '../models/page-response.model';
import { AlbumResponse } from '../models/album.model';

@Injectable({ providedIn: 'root' })
export class AlbumService {

    private readonly http = inject(HttpClient);
    private readonly baseUrl = 'http://localhost:8080/api';

    getAlbums(opts: { page?: number; size?: number; q?: string; } = {}): Observable<PageResponse<AlbumResponse>> {
        const params: Record<string, string> = {
            page: String(opts.page ?? 0),
            size: String(opts.size ?? 20)
        };
        if (opts.q && opts.q.trim().length > 0) {
            params['q'] = opts.q.trim();
        }
        return this.http.get<PageResponse<AlbumResponse>>(
            `${this.baseUrl}/albums`,
            { params }
        );
    }


    getAlbumById(id: number): Observable<AlbumResponse> {
        return this.http.get<AlbumResponse>(`${this.baseUrl}/albums/${id}`);
    }
}