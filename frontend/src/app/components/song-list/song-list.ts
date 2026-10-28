import { Component, computed, effect, inject, input, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { SongService } from '../../services/song.service';
import { SongResponse } from '../../models/song.model';
import { PageResponse } from '../../models/page-response.model';
import { PlayerService } from '../../services/player.service';

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
  readonly q = input<string>('');

  readonly pageNumber = computed(() => {
    const raw = Number(this.page());
    return Number.isNaN(raw) || raw < 0 ? 0 : raw;
  });

  readonly response = signal<PageResponse<SongResponse> | null>(null);
  readonly loading = signal(false);
  readonly error = signal<string | null>(null);

  readonly songs = computed(() => this.response()?.content ?? []);
 // readonly qParam = computed(() => this.q() ?? '');

  readonly inputValue = signal<string>('');

  private debounceHandle: ReturnType<typeof setTimeout> | null = null;

  private readonly player = inject(PlayerService);

  constructor() {
    effect(() => {
      this.loadSongs(this.pageNumber(), this.q());
    });
    effect(() => {
      // Keep the input in sync when the URL changes externally
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

  private loadSongs(page: number, q: string): void {
    this.loading.set(true);
    this.error.set(null);

    this.songService.getSongs({ page, size: this.pageSize, q }).subscribe({
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

  readonly currentPlayingId = computed(() =>
    this.player.isPlaying() ? this.player.currentSong()?.id ?? null : null
  );

  onPlayClick(song: SongResponse): void {
    if (this.player.currentSong()?.id === song.id) {
      this.player.togglePlayPause();
    } else {
      this.player.play(song);
    }
  }

}  