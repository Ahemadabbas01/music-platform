import { Component, inject } from '@angular/core';
import { PlayerService } from '../../services/player.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-player-bar',
  imports: [RouterLink],
  templateUrl: './player-bar.html',
  styleUrl: './player-bar.css',
})
export class PlayerBar {
  readonly player = inject(PlayerService);

  formatTime(seconds: number): string {
    if (!seconds || !isFinite(seconds)) return '0:00';
    const m = Math.floor(seconds / 60);
    const s = Math.floor(seconds % 60);
    return `${m}:${s.toString().padStart(2, '0')}`;
  }

  onSeek(event: Event): void {
    const value = Number((event.target as HTMLInputElement).value);
    this.player.seek(value);
  }

}
