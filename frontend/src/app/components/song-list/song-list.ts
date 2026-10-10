import { Component, inject, OnInit, signal } from '@angular/core';
import { SongResponse } from '../../models/song.model';
import { SongService } from '../../services/song.service';

@Component({
  selector: 'app-song-list',
  imports: [],
  templateUrl: './song-list.html',
  styleUrl: './song-list.css',
})
export class SongList implements OnInit {

  private readonly songService = inject(SongService);

  readonly songs = signal<SongResponse[]>([]);
  readonly loading = signal(false);
  readonly error = signal<string | null>(null);


  ngOnInit(): void {
    this.loadSongs();
  }

  private loadSongs(): void {
    this.loading.set(true);
    this.error.set(null);

    this.songService.getSongs(0, 20).subscribe({
      next: (response) => {
        this.songs.set(response.content);
        this.loading.set(false);
      },
      error: (err) => {
        console.error(err);
        this.error.set('Failed to load songs');
        this.loading.set(false);
      }
    });
  }
}
