# Session Log — 2026-10-29

## Milestone: Phase 3 — Angular Frontend Core Read-Side Completed

The main read-side functionality of the Angular frontend is now implemented. Users can browse, search, view details, play songs, and navigate between songs, artists, and albums.

All three resources are now connected through their respective listing and detail pages.

## Work Completed During This Phase

### Backend — music-service

- Extended the `GET /api/songs` endpoint to support filtering by `artistId` and `albumId`.
- Updated the existing JPQL query to handle different filter combinations, including search text, artist ID, and album ID.
- Used conditional filtering with `:param IS NULL OR column = :param` so that filters remain optional.
- Added explicit `CAST` operations to avoid Hibernate 6 parameter type inference problems.
- Kept the main data query and count query consistent so pagination returns the correct `totalElements`.
- Completed the changes without introducing additional entities or DTOs.

### Frontend — Angular 20

- Created a shared header component with navigation links for Songs, Artists, and Albums.
- Implemented active navigation highlighting using `RouterLinkActive`.
- Updated the routing configuration:
  - `/` redirects to `/songs`.
  - Added `/artists` and `/artists/:id`.
  - Added `/albums` and `/albums/:id`.
- Created `ArtistService` and `AlbumService`, following the existing structure of `SongService`.
- Implemented the Artist List and Artist Detail pages.
- Implemented the Album List and Album Detail pages.
- Added navigation between related resources:
  - Song detail displays links to its album and artists.
  - Album detail provides navigation to its artist.
- Updated pagination so that it does not appear when there are no matching records.
- Refactored `getSongs()` and `getArtists()` to accept an options object instead of multiple positional parameters.

## Git Commits

The following changes were committed during this phase:

- `2857e9e` — Added artist and album filters to the music-service song listing API.
- `6033631` — Implemented header navigation, artist service, and routing changes.
- `a5dd0a0` — Added artist listing with search and pagination.
- `7040e80` — Implemented artist detail page with related songs and playback.
- `37af63e` — Refactored song and artist service methods to use options objects.
- `469bd4c` — Implemented album listing page.
- `e4af702` — Added album detail page with related songs.
- `80d3cde` — Implemented navigation links between songs, albums, and artists.

## Technical Learnings

### Angular

**1. Component input names must match route parameter names**

While using `withComponentInputBinding()`, I learned that Angular maps URL parameters directly to component inputs based on their names.

For example, if the URL contains `?page=2`, the component must have an input named `page`. Renaming it to `pageParam` prevents the automatic binding and Angular silently uses the default value.

**2. Signals must be invoked to access their actual values**

Angular signals are functions. Accessing `songs.length` does not return the number of songs because `songs` itself is a signal.

The correct approach is `songs().length`, which first retrieves the signal value and then accesses the array length.

**3. Detail pages follow a consistent implementation structure**

After implementing multiple detail pages, I established a reusable approach:

- Display a loading state.
- Handle API errors.
- Show the main resource information.
- Display metadata using `<dl>`.
- Render related records in a table.
- Provide a back-navigation link.

Following this structure made the Album Detail page easier to implement after completing Song Detail and Artist Detail.

**4. Independent API requests should have independent subscriptions**

Detail pages sometimes need data from two different endpoints. For example, an album page needs album information and its related songs.

I used separate `.subscribe()` calls with individual error handlers. This allows one request to succeed and display its data even if the other request fails.

Using `forkJoin` would make both requests dependent on successful completion, which was not suitable for this requirement.

**5. Avoid hiding existing data during secondary loading**

Used conditions such as `@if (loading() && !album())` to control the loading indicator.

This ensures that the loading message appears only when the main album information is not available. Once the album has loaded, its details remain visible even if the related songs request is still running.

### Backend — JPA and Hibernate

**1. Explicit casting is necessary for nullable JPQL parameters**

While working with Hibernate 6 and PostgreSQL, I encountered a problem with optional parameters used in conditions such as `:q IS NULL`.

Hibernate could not determine the parameter type correctly and inferred `bytea`, which caused PostgreSQL errors when executing functions such as `LOWER()`.

Resolved this by explicitly casting parameters:

- `CAST(:q AS String)`
- `CAST(:artistId AS Long)`

**2. Both pagination queries must use identical filtering conditions**

When implementing custom pagination queries, the main query retrieves the actual records, while the count query calculates the total matching records.

Both queries must contain the same filters and casting logic. Otherwise, the returned records and `totalElements` can become inconsistent.

**3. Derived repository methods are not always suitable for optional filters**

Spring Data methods such as `findByXAndY()` and `findByXOrY()` cannot properly represent multiple optional search conditions.

- `AND` conditions fail to match records when nullable parameters are involved.
- `OR` conditions can return unrelated records.

For this requirement, a custom JPQL query with conditional filtering was the more appropriate solution.

### Development and Testing

**1. A successful test does not automatically mean the correct feature is implemented**

During album listing development, I initially copied the artist service and forgot to update its API endpoint.

The application rendered a table successfully, but it was displaying artist data instead of album data.

I identified the issue by checking the actual request in the browser's Network tab and verifying the returned records.

Going forward, I will validate both the API URL and actual response values instead of checking only whether the UI renders.

**2. TypeScript generics do not validate API responses at runtime**

Using something like `http.get<PageResponse<AlbumResponse>>()` only provides compile-time type information.

It does not verify that the backend actually returned album records. An incorrect `/api/artists` URL can still compile successfully.

Therefore, checking actual response data is necessary.

**3. Search for old references after copying services**

When creating a new service based on an existing one, I should verify that no references to the previous resource remain.

For example:

`findstr /i "artist" album.service.ts`

This helps identify leftover endpoint names and other copy-paste mistakes.

## Bugs Identified and Resolved

- **Hibernate parameter type inference:** Missing explicit casting for `:q` caused PostgreSQL to receive an incorrect `bytea` parameter. Added the required casts.
- **Incorrect AlbumService endpoint:** The album service was calling `/api/artists` instead of `/api/albums`. Updated the endpoint.
- **Missing album detail heading:** Added the missing `<h2>` element to maintain consistency with other detail pages.
- **Incorrect signal access:** Replaced `songs.length` with `songs().length` in the album detail template.
- **Route input binding issue:** Renaming the `page` input to `pageParam` broke automatic URL parameter binding. Restored the original input name.

## Deferred Work and Technical Improvements

The following items were identified during development but intentionally left for future work.

**1. N+1 query problem in song listing**

Hibernate currently executes additional queries to retrieve artists for individual songs. This can increase database calls as the number of songs grows.

Possible solutions include:

- `@EntityBatchSize` on `Song.artists`.
- `@EntityGraph` on the repository search method.

This is not urgent for the current dataset, but should be addressed before production usage.

**2. Open Session in View is still enabled**

Disabling `open-in-view` previously caused `LazyInitializationException` because entity mapping was happening after the persistence context was no longer available.

The proper solution is to move mapping logic into the service layer and execute it within the `@Transactional` boundary.

This improvement is deferred intentionally.

**3. Automated testing is not implemented yet**

Current verification is performed manually using Postman and the browser.

Testing still needs to be introduced for:

- JUnit.
- MockMvc.
- Testcontainers.
- Angular frontend components and services.

**4. Duplicate name validation**

Duplicate name handling currently exists only for Genre, which returns `409 Conflict`.

Artist, Album, and Song do not have unique constraints implemented yet. Validation should be considered when uniqueness requirements are finalized.

**5. Detail page loading behavior**

There can be a short blank state when related songs load before the primary artist or album information.

A separate `ready` flag or individual loading states for each section could improve the user experience.

**6. Empty pagination handling**

Pagination is now hidden when the album list has no results.

The same behavior should also be verified and applied to Song List and Artist List if it is not already implemented.

**7. Search behavior differs between resources**

Song search currently uses substring matching, whereas artist and album searches use prefix matching.

This was an intentional decision during implementation, but search behavior should be reviewed later for a consistent user experience.

## Possible Next Development Phases

### 1. Frontend Styling and UI Improvements

The main functionality is working, but the interface still needs visual improvements.

Planned areas:

- Improve overall page layout.
- Establish consistent typography.
- Adjust spacing and alignment.
- Improve colors and visual hierarchy.
- Make listing and detail pages look more polished.
- Improve the player interface.

Estimated effort: 1–2 development sessions.

### 2. Next and Previous Track Functionality

Improve the music player by introducing queue management.

Planned features:

- Automatically play the next song when the current track ends.
- Add Previous and Next buttons.
- Maintain the current playback queue.
- Move queue-related state into `PlayerService`.

### 3. Phase 4 — Authentication

Introduce authentication and user-specific functionality.

Expected areas of implementation:

- JWT-based authentication.
- User registration.
- Login and logout.
- Protected Angular routes.
- Backend security configuration.
- User-specific features.

This will be a separate development phase because it requires several coordinated backend and frontend changes.

### 4. Deferred Technical Improvements

After the main frontend experience is polished, revisit production-readiness tasks:

- Resolve N+1 queries.
- Disable Open Session in View and move mapping into the transactional service layer.
- Introduce backend automated tests.
- Add frontend test coverage.
- Review duplicate validation.
- Improve loading states and search consistency.

## Knowledge Tracker

### Strong Understanding

Topics that I have implemented and can now work with confidently:

- DTO-based API design and separation of responsibilities.
- Bean Validation.
- Consistent API error responses.
- Pagination.
- Spring Data derived queries.
- Custom JPQL queries.
- Angular signals.
- Shared service state.
- URL-driven frontend functionality.
- CORS configuration.
- Static resource and audio file serving.

### Currently Developing

Topics that I understand conceptually but need more practical experience with:

- JPA lazy loading and N+1 query optimization.
- Hibernate 6 JPQL parameter casting.
- Multiple asynchronous requests on detail pages.
- Options-object design for service methods.

### New Concepts Learned in This Phase

- Angular `RouterLinkActive`.
- Angular `withComponentInputBinding()`.
- Optional JPQL filters using `:param IS NULL`.
- Explicit parameter casting in Hibernate 6.
- Debounce composition in search functionality.
- Handling independent API subscriptions.
- Managing loading states when multiple requests are involved.

## Development Environment Reference

**Backend**

```cmd
cd D:\music-platform\music-service
mvnw.cmd spring-boot:run
```

**Frontend**

```cmd
cd D:\music-platform\frontend
npm start
```

**PostgreSQL**

```cmd
docker exec -it music-postgres psql -U music_app -d music_db
```

**Audio Files**

Location:

```text
music-service/src/main/resources/static/audio/sample.mp3
```

**Important Environment Notes**

- JVM timezone is configured through the Maven plugin in `pom.xml`.
- Use CMD to start the backend instead of STS unless the STS run configuration also includes `-Duser.timezone=UTC`.
- Both backend and frontend servers must be running to test the complete application.
- PostgreSQL must also be available through Docker.

## Current Project Status

- All Phase 3 changes have been committed and pushed.
- Working tree is clean.
- Songs, artists, and albums support browsing, searching, detail views, and cross-navigation.
- Song playback is integrated into the frontend.
- Backend filtering and pagination are working.
- Phase 3 core read-side functionality is complete.
