import { Component, computed, effect, inject, input, signal } from '@angular/core';
import { ArtistService } from '../../services/artist.service';
import { Router, RouterLink } from '@angular/router';
import { PageResponse } from '../../models/page-response.model';
import { ArtistResponse } from '../../models/artist.model';
import { HttpErrorResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-artist-list',
  imports: [RouterLink, DatePipe],
  templateUrl: './artist-list.html',
  styleUrl: './artist-list.css',
})

export class ArtistList {

  private readonly artistService = inject(ArtistService);
  private readonly router = inject(Router);
  private readonly pageSize = 10;

  readonly page = input<string>('0')
  readonly q = input<string>('');

  readonly pageNumber = computed(() => {
    const raw = Number(this.page());
    return Number.isNaN(raw) || raw < 0 ? 0 : raw;
  });

  readonly response = signal<PageResponse<ArtistResponse> | null>(null);
  readonly loading = signal(false);
  readonly error = signal<string | null>(null);

  readonly artists = computed(() => this.response()?.content ?? []);
  readonly inputValue = signal<string>('');

  private debounceHandle: ReturnType<typeof setTimeout> | null = null;

  constructor() {
    effect(() => {
      this.loadArtists(this.pageNumber(), this.q());
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

  private loadArtists(page: number, q: string): void {
    this.loading.set(true);
    this.error.set(null);

     this.artistService.getArtists({ page, size: this.pageSize, q }).subscribe({
      next: (response) => {
        this.response.set(response);
        this.loading.set(false);
      },
      error: (err: HttpErrorResponse) => {
        this.error.set(err.error?.message ?? 'Failed to load artists');
        this.loading.set(false);
      }
    });
  }

}
