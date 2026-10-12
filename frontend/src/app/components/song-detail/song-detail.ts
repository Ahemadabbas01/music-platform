import { Component, effect, inject, input, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { SongService } from '../../services/song.service';
import { SongResponse } from '../../models/song.model';
import { HttpErrorResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-song-detail',
  imports: [RouterLink,DatePipe],
  templateUrl: './song-detail.html',
  styleUrl: './song-detail.css',
})

export class SongDetail {

  private readonly songService = inject(SongService);
  readonly id = input.required<string>();

  readonly song = signal<SongResponse | null>(null);
  readonly loading = signal(false);
  readonly error = signal<string | null>(null);

  constructor() {
    effect(() => {
      const songId = Number(this.id());
      if (!isNaN(songId)) {
        this.loadSong(songId);
      }
    });
  }

  private loadSong(id: number): void {
    this.loading.set(true);
    this.error.set(null);
    this.song.set(null);

    this.songService.getSongById(id).subscribe({
      next: (song) => {
        this.song.set(song);
        this.loading.set(false);
      },
      error: (err: HttpErrorResponse) => {
        this.error.set(err.error?.message ?? 'Failed to load song');
        this.loading.set(false);
      }
    });
  }
}
