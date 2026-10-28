import { Component, computed, effect, inject, input, signal } from '@angular/core';
import { AlbumService } from '../../services/album.service';
import { Router, RouterLink } from '@angular/router';
import { AlbumResponse } from '../../models/album.model';
import { PageResponse } from '../../models/page-response.model';
import { HttpErrorResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-album-list',
  imports: [RouterLink,DatePipe],
  templateUrl: './album-list.html',
  styleUrl: './album-list.css',
})
export class AlbumList {
 private readonly albumService = inject(AlbumService);
  private readonly router = inject(Router);
  private readonly pageSize = 10;

  readonly page = input<string>('0')
  readonly q = input<string>('');

  readonly pageNumber = computed(() => {
    const raw = Number(this.page());
    return Number.isNaN(raw) || raw < 0 ? 0 : raw;
  });

  readonly response = signal<PageResponse<AlbumResponse> | null>(null);
  readonly loading = signal(false);
  readonly error = signal<string | null>(null);

  readonly albums = computed(() => this.response()?.content ?? []);
  readonly inputValue = signal<string>('');

  private debounceHandle: ReturnType<typeof setTimeout> | null = null;

  constructor() {
    effect(() => {
      this.loadAlbums(this.pageNumber(), this.q());
    });
    effect(() => {
       this.inputValue.set(this.q() ?? '');
    });
  }

  onInput(event: Event): void {
    const value = (event.target as HTMLInputElement).value;
    this.inputValue.set(value);

    if (this.debounceHandle) {
      clearTimeout(this.debounceHandle);
    }
    this.debounceHandle = setTimeout(() => {
      this.router.navigate([], {
        queryParams: { q: value, page: 0 },
        queryParamsHandling: 'merge'
      });
    }, 300);
  }

  goToPage(target: number): void {
    if (target < 0) return;
    this.router.navigate([], {
      queryParams: { page: target },
      queryParamsHandling: 'merge'
    });
  }

  private loadAlbums(page: number, q: string): void {
    this.loading.set(true);
    this.error.set(null);

     this.albumService.getAlbums({ page, size: this.pageSize, q }).subscribe({
      next: (response) => {
        this.response.set(response);
        this.loading.set(false);
      },
      error: (err: HttpErrorResponse) => {
        this.error.set(err.error?.message ?? 'Failed to load album');
        this.loading.set(false);
      }
    });
  }
}
