import { Component, computed, effect, inject, input, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { SongService } from '../../services/song.service';
import { SongResponse } from '../../models/song.model';
import { PageResponse } from '../../models/page-response.model';

@Component({
  selector: 'app-song-list',
  imports: [RouterLink],
  templateUrl: './song-list.html',
  styleUrl: './song-list.css'
})
export class SongList {

  private readonly songService = inject(SongService);
  private readonly router = inject(Router);

  private readonly pageSize = 3;

  readonly page = input<string>('0');

  readonly pageParam = computed(() => {
    const raw = Number(this.page());
    return Number.isNaN(raw) || raw < 0 ? 0 : raw;
  });

  readonly response = signal<PageResponse<SongResponse> | null>(null);
  readonly loading = signal(false);
  readonly error = signal<string | null>(null);

  readonly songs = computed(() => this.response()?.content ?? []);

  constructor() {
    effect(() => {
      this.loadSongs(this.pageParam());
    });
  }

  goToPage(target: number): void {
    if (target < 0) return;
    this.router.navigate([], {
      queryParams: { page: target },
      queryParamsHandling: 'merge'
    });
  }

  private loadSongs(page: number): void {
    this.loading.set(true);
    this.error.set(null);

    this.songService.getSongs(page, this.pageSize).subscribe({
      next: (response) => {
        this.response.set(response);
        this.loading.set(false);
      },
      error: (err: HttpErrorResponse) => {
        this.error.set(err.error?.message ?? 'Failed to load songs');
        this.loading.set(false);
      }
    });
  }
}