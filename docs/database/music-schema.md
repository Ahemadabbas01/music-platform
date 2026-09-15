# Music Database Schema

## Entities
artist
album
song
song_artist

### Artist
ARTIST
────────────────────────
id              BIGINT PK
name            NOT NULL
details         NULL
country         NULL
created_at      NOT NULL
updated_at      NOT NULL

### Album
ALBUM
────────────────────────
id              BIGINT PK
name            NOT NULL
cover_image_ref NULL
artist_id       BIGINT FK NOT NULL
created_at      NOT NULL
updated_at      NOT NULL
### Song
SONG
────────────────────────
id              BIGINT PK
title           NOT NULL
album_id        BIGINT FK NULL
audio_ref       NOT NULL
created_at      NOT NULL
updated_at      NOT NULL
### Song Artist
SONG_ARTIST
────────────────────────
song_id         BIGINT FK NOT NULL
artist_id       BIGINT FK NOT NULL

PRIMARY KEY (song_id, artist_id)
## Relationships

                 ┌──────────────┐
                 │    ARTIST    │
                 └──────┬───────┘
                        │
              ┌─────────┴─────────┐
              │                   │
             1│                  *│
              ▼                   ▼
        ┌───────────┐       ┌───────────┐
        │   ALBUM   │       │   SONG    │
        └─────┬─────┘       └─────┬─────┘
              │                   │
             1│                  *│
              │                   │
             *▼                   │
        ┌───────────┐             │
        │   SONG    │◄────────────┘
        │  ARTIST   │
        └───────────┘

Artist → Album =  1  : N
Album → Song  = 1 : N
Song → Artist  = M : N
Song → Album = N : 1

Album must have one Artist.
Song may have zero or one Album.
Album may have zero or more Songs.
Song may have zero or more Artists.
Artist may have zero or more Songs.

## Business Rules

Allowed:
song without album it is allowed
Song title does not have a UNIQUE constraint.
Multiple songs may have the same title.
Artist name does not have a UNIQUE constraint.
cover_image_ref may be NULL because an album can exist without a custom cover image.
An artist may exist without any songs or albums.
A song may have multiple artists.
An artist may have zero or more songs.
An album may have zero or more songs.
song.album_id become null when album is deleted.
Every album must have exactly one primary artist.

Not allowed:

song without audio not allowed
An artist cannot be deleted while albums or songs reference the artist.

## Foreign Key Behavior

1. Album deletion
Rule: When an album is removed from the system, its tracklist must not be destroyed. Instead, any songs associated with that album are kept intact, and their album reference is wiped out (NULL).
Impact: Songs are safely preserved in the database as standalone or "singles," losing only their collection grouping.

2. Song deletion
Rule: Deleting a song must automatically clean up the bridge table linking it to any performers, without touching the performers themselves.
Impact: The system eliminates dead relational links (song_artists rows) to prevent clutter, while the The associated artist records remain untouched.

3. Artist deletion:
Rule: An artist cannot be deleted if their catalog is still active. The database checks for existing track associations; if links exist, the deletion is blocked (REJECT). If no rows reference the artist, the deletion is allowed, the deletion goes through.
Impact: This protects against accidental data corruption, ensuring you never end up with ghost credits or untrackable artist references on active songs.