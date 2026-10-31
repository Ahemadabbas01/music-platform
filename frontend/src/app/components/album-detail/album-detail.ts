import { Component, computed, effect, inject, input, signal } from '@angular/core';
import { AlbumService } from '../../services/album.service';
import { SongService } from '../../services/song.service';
import { PlayerService } from '../../services/player.service';
import { AlbumResponse } from '../../models/album.model';
import { SongResponse } from '../../models/song.model';
import { HttpErrorResponse } from '@angular/common/http';
import { RouterLink } from '@angular/router';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-album-detail',
  imports: [RouterLink,DatePipe],
  templateUrl: './album-detail.html',
  styleUrl: './album-detail.css',
})
export class AlbumDetail {

  private readonly albumService = inject(AlbumService);
  private readonly songService = inject(SongService);
  private readonly player = inject(PlayerService);
  readonly id = input.required<string>();

  readonly album = signal<AlbumResponse | null>(null);
  readonly songs = signal<SongResponse[]>([]);

  readonly loading = signal(false);
  readonly error = signal<string | null>(null);
  readonly currentPlayingId = computed(() => this.player.isPlaying() ? this.player.currentSong()?.id ?? null : null);

  constructor() {
    effect(() => {
      const albumId = Number(this.id());
      if (!isNaN(albumId)) {
        this.load(albumId);
      }
    });
  }

  private load(id: number): void {
    this.loading.set(true);
    this.error.set(null);
    this.album.set(null);
    this.songs.set([]);

    this.albumService.getAlbumById(id).subscribe({
      next: (album) => {
        this.album.set(album);
      },
      error: (err: HttpErrorResponse) => {
        this.error.set(err.error?.message ?? 'Failed to load album');
        this.loading.set(false);
      }
    });

    this.songService.getSongs({ albumId: id }).subscribe({
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
      this.player.play(song, this.songs()); // <-- Pass all songs on this album
    }
  }


}
