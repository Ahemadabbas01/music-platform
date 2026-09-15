-- Album -> Artist
-- An artist cannot be deleted while an album references them.
ALTER TABLE album
    DROP CONSTRAINT fkmwc4fyyxb6tfi0qba26gcf8s1;

ALTER TABLE album
    ADD CONSTRAINT fk_album_artist
    FOREIGN KEY (artist_id)
    REFERENCES artist(id)
    ON DELETE RESTRICT;


-- Song ->  Album
-- Deleting an album keeps the songs and removes their album reference.
ALTER TABLE song
    DROP CONSTRAINT fkrcjmk41yqj3pl3iyii40niab0;

ALTER TABLE song
    ADD CONSTRAINT fk_song_album
    FOREIGN KEY (album_id)
    REFERENCES album(id)
    ON DELETE SET NULL;


-- SongArtist -> Song
-- Deleting a song automatically removes its bridge-table rows.
ALTER TABLE song_artist
    DROP CONSTRAINT fka29cre1dfpdj3gek88ukv43cc;

ALTER TABLE song_artist
    ADD CONSTRAINT fk_song_artist_song
    FOREIGN KEY (song_id)
    REFERENCES song(id)
    ON DELETE CASCADE;


-- SongArtist -> Artist
-- An artist cannot be deleted while song credits reference them.
ALTER TABLE song_artist
    DROP CONSTRAINT fk9tevojs24wnwin3di24wlao1m;

ALTER TABLE song_artist
    ADD CONSTRAINT fk_song_artist_artist
    FOREIGN KEY (artist_id)
    REFERENCES artist(id)
    ON DELETE RESTRICT;