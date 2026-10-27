import { Routes } from '@angular/router';
import { SongList } from './components/song-list/song-list';
import { SongDetail } from './components/song-detail/song-detail';
import { ArtistList } from './components/artist-list/artist-list';

export const routes: Routes = [
    { path: '', redirectTo: '/songs', pathMatch: 'full' },
    { path: 'songs', component: SongList },
    { path : 'songs/:id',component : SongDetail},
    { path: 'artists', component: ArtistList }

];
