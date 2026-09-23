# ADR-003: Request/Response DTOs at API Boundary

## Status
Accepted

## Context

Initially, ArtistController, AlbumController, and SongController accepted
and returned JPA entities directly as their API contract. For example:

    POST /api/artists
    @RequestBody Artist artist

This exposed the full persistence model to API clients — including the
primary key, audit timestamps, and entity relationships.
## Problem
- Client can set id, createdAt, updatedAt, and relationships . expose the whole entity attacker may bad use of this bug
- API contract coupled to DB schema
- No place to put request validation

## Options considered

1. Keep entities as the API contract (status quo)
2. Add @JsonProperty(access = READ_ONLY) on sensitive fields
3. Introduce request/response DTOs, implemented as Java 17 records (chosen)

## Decision

Every API resource has:

- A **Request DTO** — a Java 17 record with only the fields a client is
  allowed to write. Constraint annotations (@NotBlank, @Size, @NotNull)
  enforce validation at the controller boundary via @Valid.
- A **Response DTO** — a record with the fields we expose to clients.
  Server-managed fields (id, createdAt, updatedAt) are included; internal
  state (Hibernate proxies, collections unless intended) is excluded.
- A **Mapper** — a utility class with a static method that converts an
  entity to its response DTO.

Implemented for: Artist, Album. Pending: Song.

## Consequences
+ Stable API contract independent of DB schema
+ Validation at the boundary
+ Mass assignment prevented by construction
- Extra classes and mapping code
- Manual mapping is verbose (Lombok/MapStruct possible later)