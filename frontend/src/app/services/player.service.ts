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
  readonly queue = signal<SongResponse[]>([]);
  readonly queueIndex = signal<number>(-1);

  constructor() {
    this.audio.addEventListener('timeupdate', () => {
      this.currentTime.set(this.audio.currentTime);
    });

    this.audio.addEventListener('loadedmetadata', () => {
      this.duration.set(this.audio.duration);
    });

    this.audio.addEventListener('ended', () => {
      // Auto-play the next song if there is one
      if (this.queueIndex() + 1 < this.queue().length) {
        this.next();
      } else {
        this.isPlaying.set(false);
      }
    });

    this.audio.addEventListener('error', () => {
      this.error.set('Could not load audio');
      this.isPlaying.set(false);
    });
  }

play(song: SongResponse, newQueue?: SongResponse[]): void {
  // 1. Update the queue if a new one is provided
  if (newQueue) {
    this.queue.set(newQueue);
  }

  // 2. ALWAYS find the song's index in the queue and update queueIndex
  const index = this.queue().findIndex(s => s.id === song.id);
  if (index !== -1) {
    this.queueIndex.set(index);
  }

  // 3. Same song already loaded — just resume (or restart)
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

  next(): void {
    const nextIndex = this.queueIndex() + 1;
    if (nextIndex < this.queue().length) {
      this.play(this.queue()[nextIndex]);
    }
  }

  previous(): void {
    const prevIndex = this.queueIndex() - 1;
    if (prevIndex >= 0) {
      this.play(this.queue()[prevIndex]);
    }
  }
}