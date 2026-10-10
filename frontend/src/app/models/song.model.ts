import { ArtistSummary } from "./artist-summary.model";

export interface SongResponse{
    id : number;
    title : string;
    audioRef : string;
    albumId : number | null;
    albumName : string | null;
    genreId : number | null;
    genreName : string | null;
    artists : ArtistSummary[];
    createdAt : string;
    updatedAt : string;



}