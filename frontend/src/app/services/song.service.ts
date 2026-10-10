import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { PageResponse } from "../models/page-response.model";
import { SongResponse } from "../models/song.model";
import { Observable } from "rxjs";

@Injectable({providedIn : 'root'})
export class SongService{

    private readonly http =  inject(HttpClient);
    private readonly baseUrl = 'http://localhost:8080/api';

 getSongs(page: number = 0, size: number = 20): Observable<PageResponse<SongResponse>> {
    return this.http.get<PageResponse<SongResponse>>(
      `${this.baseUrl}/songs?page=${page}&size=${size}`
    );
  }

  
}