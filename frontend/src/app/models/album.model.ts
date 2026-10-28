export interface AlbumResponse {
  id: number;
  name: string;
  coverImageRef: string | null;
  artistId: number;
  artistName: string;
  createdAt: string;
  updatedAt: string;
}