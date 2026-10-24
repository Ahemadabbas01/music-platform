import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { PlayerBar } from './components/player-bar/player-bar';

  @Component({
    selector: 'app-root',
    imports: [RouterOutlet,PlayerBar],
    templateUrl: './app.html',
    styleUrl: './app.css'
  })
  export class App {
    protected readonly title = signal('frontend');
  }
