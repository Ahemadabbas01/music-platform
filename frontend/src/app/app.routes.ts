import { Routes } from '@angular/router';
import { SongList } from './components/song-list/song-list';
import { SongDetail } from './components/song-detail/song-detail';

export const routes: Routes = [
    {path : '',component : SongList},
    {path : 'songs/:id',component : SongDetail}
];
