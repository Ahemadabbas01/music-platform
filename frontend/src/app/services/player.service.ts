import { Injectable, signal } from '@angular/core';
import { SongResponse } from '../models/song.model';

@Injectable({ providedIn: 'root' })
export class PlayerService {

  private readonly audio = new Audio();

  readonly currentSong = signal<SongResponse | null>(null);
  readonly isPlaying = signal(false);
  readonly currentTime = signal(0);
  readonly duration = signal(0);
  readonly error = signal<string | null>(null);

  constructor() {
    this.audio.addEventListener('timeupdate', () => {
      this.currentTime.set(this.audio.currentTime);
    });

    this.audio.addEventListener('loadedmetadata', () => {
      this.duration.set(this.audio.duration);
    });

    this.audio.addEventListener('ended', () => {
      this.isPlaying.set(false);
    });

    this.audio.addEventListener('error', () => {
      this.error.set('Could not load audio');
      this.isPlaying.set(false);
    });
  }

  play(song: SongResponse): void {
    // Same song already loaded — just resume (or restart if you prefer)
    if (this.currentSong()?.id === song.id && this.audio.src) {
      this.audio.currentTime = 0;
    } else {
      this.currentSong.set(song);
      this.audio.src = song.audioRef;
      this.currentTime.set(0);
      this.duration.set(0);
    }

    this.error.set(null);
    this.audio.play()
      .then(() => this.isPlaying.set(true))
      .catch(err => {
        console.error(err);
        this.error.set('Playback failed');
        this.isPlaying.set(false);
      });
  }

  togglePlayPause(): void {
    if (!this.currentSong()) return;

    if (this.isPlaying()) {
      this.audio.pause();
      this.isPlaying.set(false);
    } else {
      this.audio.play()
        .then(() => this.isPlaying.set(true))
        .catch(err => {
          console.error(err);
          this.error.set('Playback failed');
        });
    }
  }

  seek(seconds: number): void {
    if (this.audio.duration > 0) {
      this.audio.currentTime = Math.max(0, Math.min(seconds, this.audio.duration));
    }
  }

  setVolume(v: number): void {
    const clamped = Math.max(0, Math.min(1, v));
    this.audio.volume = clamped;
  }
}