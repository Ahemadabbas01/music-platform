import { Component, computed, effect, inject, input, signal } from '@angular/core';
import { ArtistService } from '../../services/artist.service';
import { SongService } from '../../services/song.service';
import { PlayerService } from '../../services/player.service';
import { ArtistResponse } from '../../models/artist.model';
import { SongResponse } from '../../models/song.model';
import { HttpErrorResponse } from '@angular/common/http';
import { RouterLink } from '@angular/router';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-artist-detail',
  imports: [RouterLink,DatePipe],
  templateUrl: './artist-detail.html',
  styleUrl: './artist-detail.css',
})
export class ArtistDetail {

  private readonly artistService = inject(ArtistService);
  private readonly songService = inject(SongService);

  private readonly player = inject(PlayerService);
  readonly id = input.required<string>();

  readonly artist = signal<ArtistResponse | null>(null);
  readonly songs = signal<SongResponse[]>([]);

  readonly loading = signal(false);
  readonly error = signal<string | null>(null);

  readonly currentPlayingId = computed(() => this.player.isPlaying() ? this.player.currentSong()?.id ?? null : null);

  constructor() {
    effect(() => {
      const artistId = Number(this.id());
      if (!isNaN(artistId)) {
        this.load(artistId);
      }
    });
  }

  private load(id: number): void {
    this.loading.set(true);
    this.error.set(null);
    this.artist.set(null);
    this.songs.set([]);

    this.artistService.getArtistById(id).subscribe({
      next: (artist) => {
        this.artist.set(artist);
      },
      error: (err: HttpErrorResponse) => {
        this.error.set(err.error?.message ?? 'Failed to load artist');
        this.loading.set(false);
      }
    });

    this.songService.getSongs(0, 20, undefined, id).subscribe({
      next: (response) => {
        this.songs.set(response.content);
        this.loading.set(false);
      },
      error: (err: HttpErrorResponse) => {
        this.error.set(err.error?.message ?? 'Failed to load songs');
        this.loading.set(false);
      }
    });
  }

  onPlayClick(song: SongResponse): void {
    if (this.player.currentSong()?.id === song.id) {
      this.player.togglePlayPause();
    } else {
      this.player.play(song);
    }
  }
}
